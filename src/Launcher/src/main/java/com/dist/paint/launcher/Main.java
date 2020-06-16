package com.dist.paint.launcher;

import org.apache.felix.framework.FrameworkFactory;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.launch.Framework;

import java.io.File;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 10:57
 */
public class Main {
    public static void main(String[] args) throws Exception{
        Map config=getDefaultConfig();
        Framework framework=new FrameworkFactory().newFramework(config);
        stopFrameOnExit(framework);
        framework.init();
        syncBundles(config,framework);
        framework.start();
        framework.waitForStop(0);
        System.exit(0);
    }
    private static Map<String,String> getDefaultConfig(){
        Map<String,String> config=new HashMap<>();
        config.put("org.osgi.framework.system.packages.extra","javax.swing");
        config.put("felix.auto.deploy.action","uninstall,install,update,start");
        config.put("felix.auto.deploy.dir","bundle");
        config.put("org.osgi.framework.storage.clean","onFirstInit");
        config.put("felix.log.level","4");
        return config;
    }
    private static void stopFrameOnExit(Framework framework){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try{
                framework.stop();
                framework.waitForStop(0);
            }catch(Exception ex){
                System.err.println("Error stopping framework: "+ex);
            }
        },"Felix Shutdown Hook"));
    }
    private static void syncBundles(Map config, Framework framework){
        AutoProcessor.process(config,framework.getBundleContext());
        try{
            Path path=new File("bundle").toPath();
            FileSystem fileSystem=path.getFileSystem();
            WatchService watchService=fileSystem.newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.OVERFLOW);
            new Thread(()->{
                while(true){
                    try{
                        WatchKey key=watchService.take();
                        AutoProcessor.process(config,framework.getBundleContext());
                        key.reset();
                    }catch(InterruptedException ex){
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            },"Check bundle directory");
        }catch(Exception ex){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,null,ex);
            ex.printStackTrace();
        }
    }
}
