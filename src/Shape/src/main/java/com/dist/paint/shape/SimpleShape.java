package com.dist.paint.shape;

import java.awt.*;

/**
 * @author Lauxb
 * @version 1.0.0
 * @description
 * @date Created in 2020/6/15 11:13
 */
public interface SimpleShape {

    /**
     * A service property for the name of the shape.
     **/
    public static final String NAME_PROPERTY = "simple.shape.name";

    /**
     * A service property for the icon of the shape.
     **/
    public static final String ICON_PROPERTY = "simple.shape.icon";

    /**
     * Draw this shape at the given position.
     *
     * @param g2 The graphics object used for painting.
     * @param p The position to paint the shape.
     **/
    void draw(Graphics2D g2, Point p);
}
