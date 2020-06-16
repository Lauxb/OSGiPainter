package com.dist.paint.circle;

import com.dist.paint.shape.SimpleShape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 14:08
 */
public class Circle implements SimpleShape {
    @Override
    public void draw(Graphics2D g2, Point p) {
        int x = p.x - 25;
        int y = p.y - 25;

        g2.setPaint(Color.MAGENTA);
        g2.fill(new Ellipse2D.Double(x, y, 50, 50));
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(new Ellipse2D.Double(x, y, 50, 50));
    }
}
