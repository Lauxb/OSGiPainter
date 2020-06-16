package com.dist.paint.triangle;

import com.dist.paint.shape.SimpleShape;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.swing.*;
import java.util.Hashtable;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 15:04
 */
public class TriangleActivator implements BundleActivator {

    private BundleContext m_context;
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Hello Painter === Shape Triangle !!!");
        m_context = bundleContext;
        Hashtable dict = new Hashtable<>();
        dict.put(SimpleShape.NAME_PROPERTY,"Triangle");
        dict.put(SimpleShape.ICON_PROPERTY,new ImageIcon(m_context.getBundle().getResource("triangle.png")));
        m_context.registerService(SimpleShape.class,new Triangle(),dict);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Goodbye Painter === Shape Triangle !!!");
    }
}
