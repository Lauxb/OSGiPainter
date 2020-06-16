package com.dist.paint.star;

import com.dist.paint.shape.SimpleShape;

import java.awt.*;
import java.awt.geom.Path2D;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 15:19
 */
public class Star implements SimpleShape {
    @Override
    public void draw(Graphics2D g2, Point p) {
        int centerX = p.x;
        int centerY = p.y;

        int numRays = 5;
        double innerRadius = 10;
        double outerRadius = innerRadius * 2.63;
        double startAngleRad = Math.toRadians(-18);

        g2.setPaint(Color.red);
        Shape star = createStar(centerX, centerY, innerRadius, outerRadius, numRays, startAngleRad);
        g2.fill(star);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(star);
    }

    private Shape createStar(double centerX, double centerY,
                             double innerRadius, double outerRadius, int numRays,
                             double startAngleRad) {
        Path2D path = new Path2D.Double();
        double deltaAngleRad = Math.PI / numRays;
        for (int i = 0; i < numRays * 2; i++) {
            double angleRad = startAngleRad + i * deltaAngleRad;
            double ca = Math.cos(angleRad);
            double sa = Math.sin(angleRad);
            double relX = ca;
            double relY = sa;
            if ((i & 1) == 0) {
                relX *= outerRadius;
                relY *= outerRadius;
            } else {
                relX *= innerRadius;
                relY *= innerRadius;
            }
            if (i == 0) {
                path.moveTo(centerX + relX, centerY + relY);
            } else {
                path.lineTo(centerX + relX, centerY + relY);
            }
        }
        path.closePath();
        return path;
    }
}
