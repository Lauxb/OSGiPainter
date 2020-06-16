package com.dist.paint.square;

import com.dist.paint.shape.SimpleShape;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 14:51
 */
public class Square implements SimpleShape {
    @Override
    public void draw(Graphics2D g2, Point p) {
        int x = p.x - 25;
        int y = p.y - 25;
        GradientPaint gradient = new GradientPaint(x, y, Color.BLUE, x + 50, y, Color.WHITE);
        g2.setPaint(gradient);
        g2.fill(new Rectangle2D.Double(x, y, 50, 50));
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(new Rectangle2D.Double(x, y, 50, 50));
    }
}
