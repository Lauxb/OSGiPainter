package com.dist.paint.frame;

import com.dist.paint.shape.SimpleShape;

import javax.swing.*;
import java.awt.*;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020-05-22 14:22
 */
public class ShapeComponent extends JComponent {
    private static final long serialVersionUID = 1L;
    private PaintFrame m_frame;
    private String m_shapeName;

    public ShapeComponent(PaintFrame frame, String shapeName) {
        this.m_frame = frame;
        this.m_shapeName = shapeName;
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        SimpleShape shape = m_frame.getShape(m_shapeName);
        shape.draw(g2, new Point(getWidth() / 2, getHeight() / 2));
    }

}
