package com.dist.paint.circle;

import com.dist.paint.shape.SimpleShape;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.swing.*;
import java.util.Hashtable;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 14:07
 */
public class CircleActivator implements BundleActivator {

    private BundleContext m_context;

    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Hello Painter === Shape Circle!!!");
        m_context = bundleContext;
        Hashtable dict = new Hashtable();
        dict.put(SimpleShape.NAME_PROPERTY, "Circle");
        dict.put(SimpleShape.ICON_PROPERTY, new ImageIcon(m_context.getBundle().getResource("circle.png")));
        m_context.registerService(SimpleShape.class.getName(), new Circle(), dict);
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Goodbye Painter === Shape Circle!!!");
    }
}
