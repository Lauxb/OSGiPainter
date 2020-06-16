package com.dist.paint.square;

import com.dist.paint.shape.SimpleShape;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.swing.*;
import java.util.Hashtable;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 14:53
 */
public class SquareActivator implements BundleActivator {
    private BundleContext m_bundleContext;
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Hello Painter === Shape Square!!!");
        m_bundleContext = bundleContext;
        Hashtable dict = new Hashtable<>();
        dict.put(SimpleShape.NAME_PROPERTY,"Square");
        dict.put(SimpleShape.ICON_PROPERTY,new ImageIcon(m_bundleContext.getBundle().getResource("square.png")));
        m_bundleContext.registerService(SimpleShape.class,new Square(),dict);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Goodbye Painter === Shape Square!!!");
    }
}
