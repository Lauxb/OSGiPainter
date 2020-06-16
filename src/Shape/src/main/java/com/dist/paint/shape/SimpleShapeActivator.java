package com.dist.paint.shape;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/11 17:35
 */
public class SimpleShapeActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) {
        System.out.println("Hello Painter === SimpleShape!!");
    }

    @Override
    public void stop(BundleContext bundleContext) {
        System.out.println("Goodbye Painter === SimpleShape!!");
    }
}
