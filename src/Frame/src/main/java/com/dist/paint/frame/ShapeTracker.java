package com.dist.paint.frame;

import com.dist.paint.shape.SimpleShape;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import javax.swing.*;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020-05-22 14:51
 */
public class ShapeTracker extends ServiceTracker<SimpleShape,Object> {
    // Flag indicating an added shape.
    private static final int ADDED = 1;
    // Flag indicating a modified shape.
    private static final int MODIFIED = 2;
    // Flag indicating a removed shape.
    private static final int REMOVED = 3;
    // The bundle context used for tracking.
    private final BundleContext m_context;
    // The application object to notify.
    private final PaintFrame m_frame;

    public ShapeTracker(BundleContext context,PaintFrame frame){
        super(context, SimpleShape.class,null);
        this.m_context = context;
        this.m_frame = frame;
    }


    @Override
    public Object addingService(ServiceReference<SimpleShape> ref) {
        SimpleShape shape = new DefaultShape(m_context, ref);
        processShapeOnEventThread(ADDED, ref, shape);
        return shape;
    }

    /**
     * Overrides the <tt>ServiceTracker</tt> functionality to inform the
     * application object about the modified service.
     *
     * @param ref The service reference of the modified service.
     * @param svc The service object of the modified service.
     **/
    @Override
    public void modifiedService(ServiceReference<SimpleShape> ref, Object svc) {
        processShapeOnEventThread(MODIFIED, ref, (SimpleShape) svc);
    }

    /**
     * Overrides the <tt>ServiceTracker</tt> functionality to inform the
     * application object about the removed service.
     *
     * @param ref The service reference of the removed service.
     * @param svc The service object of the removed service.
     **/
    @Override
    public void removedService(ServiceReference<SimpleShape> ref, Object svc) {
        processShapeOnEventThread(REMOVED, ref, (SimpleShape) svc);
        ((DefaultShape) svc).dispose();
    }

    private void processShapeOnEventThread(int action, ServiceReference<SimpleShape> ref, SimpleShape shape) {
        if ((m_context.getBundle(0).getState() & (Bundle.STARTING | Bundle.ACTIVE)) == 0) {
            return;
        }

        try {
            if (SwingUtilities.isEventDispatchThread()) {
                processShape(action, ref, shape);
            } else {
                SwingUtilities.invokeAndWait(new ShapeRunnable(action, ref, shape));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Actually performs the processing of the service notification. Invokes the
     * appropriate callback method on the application object depending on the
     * action type of the notification.
     *
     * @param action The type of action associated with the notification.
     * @param ref The service reference of the corresponding service.
     * @param shape The service object of the corresponding service.
     **/
    private void processShape(int action, ServiceReference<SimpleShape> ref, SimpleShape shape) {
        String name = (String) ref.getProperty(SimpleShape.NAME_PROPERTY);

        switch (action) {
            case MODIFIED:
                m_frame.removeShape(name);
                // Purposely let this fall through to the 'add' case to
                // reload the service.
                System.out.println("MODIFIED:" + name);
            case ADDED:
                Icon icon = (Icon) ref.getProperty(SimpleShape.ICON_PROPERTY);
                m_frame.addShape(name, icon, shape);
                System.out.println("ADDED:" + name);
                break;

            case REMOVED:
                m_frame.removeShape(name);
                System.out.println("REMOVED:" + name);
                break;
        }
    }

    /**
     * Simple class used to process service notification handling on the Swing
     * event thread.
     **/
    private class ShapeRunnable implements Runnable {
        private final int m_action;
        private final ServiceReference<SimpleShape> m_ref;
        private final SimpleShape m_shape;

        /**
         * Constructs an object with the specified action, service reference, and
         * service object for processing on the Swing event thread.
         *
         * @param action The type of action associated with the notification.
         * @param ref The service reference of the corresponding service.
         * @param shape The service object of the corresponding service.
         **/
        public ShapeRunnable(int action, ServiceReference<SimpleShape> ref, SimpleShape shape) {
            m_action = action;
            m_ref = ref;
            m_shape = shape;
        }

        /**
         * Calls the <tt>processShape()</tt> method.
         **/
        public void run() {
            processShape(m_action, m_ref, m_shape);
        }
    }
}
