package com.dist.paint.frame;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020-05-22 11:05
 */
public class FrameActivator implements BundleActivator, Runnable {

    private BundleContext m_context = null;
    private PaintFrame m_frame = null;
    private ShapeTracker m_shapeTracker = null;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println();
        m_context = bundleContext;
        if (SwingUtilities.isEventDispatchThread()) {
            run();
        } else {
            try {
                SwingUtilities.invokeAndWait(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Hello Painter === Frame !!");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        m_shapeTracker.close();
        final PaintFrame frame = m_frame;
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(false);
            frame.dispose();
        });
        System.out.println("Goodbye Painter === Frame !!");
    }

    @Override
    public void run() {
        m_frame = new PaintFrame();
        m_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        m_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    m_context.getBundle(0).stop();
                }catch (BundleException exception){
                    exception.printStackTrace();
                }
            }
        });
        m_frame.setVisible(true);
        m_shapeTracker = new ShapeTracker(m_context,m_frame);
        m_shapeTracker.open();
    }
}
