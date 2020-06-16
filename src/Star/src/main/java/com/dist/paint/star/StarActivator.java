package com.dist.paint.star;

import com.dist.paint.shape.SimpleShape;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.swing.*;
import java.util.Hashtable;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 15:17
 */
public class StarActivator implements BundleActivator {
    private BundleContext m_context;
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Hello Painter === Shape Star !!!");
        m_context = bundleContext;
        Hashtable dict = new Hashtable<>();
        dict.put(SimpleShape.NAME_PROPERTY,"Star");
        dict.put(SimpleShape.ICON_PROPERTY,new ImageIcon(m_context.getBundle().getResource("star.png")));
        m_context.registerService(SimpleShape.class,new Star(),dict);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Goodbye Painter === Shape Star !!!");
    }
}
