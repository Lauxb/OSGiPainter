package com.dist.paint.frame;

import com.dist.paint.shape.SimpleShape;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import javax.swing.*;
import java.awt.*;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/11 15:02
 */
public class DefaultShape implements SimpleShape {

    private SimpleShape m_shape;
    private ImageIcon m_icon;
    private BundleContext m_context;
    private ServiceReference<SimpleShape> m_ref;

    public DefaultShape() {
    }

    public DefaultShape(BundleContext context, ServiceReference<SimpleShape> reference) {
        this.m_context = context;
        this.m_ref = reference;
    }

    @Override
    public void draw(Graphics2D g2, Point p) {
        if (m_context != null) {
            try {
                if (m_shape == null) {
                    // Get the shape service.
                    m_shape = m_context.getService(m_ref);
                }
                // Draw the shape
                m_shape.draw(g2, p);
                return;
            } catch (Exception exception) {
                System.out.println("m_shape err:" + exception.getMessage());
            }
        }
        if (m_icon == null) {
            try {
                assert m_context != null;
                m_icon = new ImageIcon(m_context.getBundle().getResource("underc.png"));
            } catch (Exception exception) {
                System.out.println("m_icon err:" + exception.getMessage());
                g2.setColor(Color.red);
                g2.fillRect(0, 0, 60, 60);
                return;
            }
        }
        g2.drawImage(m_icon.getImage(), 0, 0, null);
    }

    public void dispose() {
        if (m_shape != null) {
            m_context.ungetService(m_ref);
            m_context = null;
            m_ref = null;
            m_shape = null;
        }
    }

}

