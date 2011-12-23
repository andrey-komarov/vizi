package ru.ifmo.vizi.quick_sort;

import java.awt.*;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

/**
 * Simple line, without any arrows or something.
 *
 * @author  Ilya Pimenov, Dmitry Kochelaev
 * @version $Id: Line.java,v 0.1 2004/04/04 20:54:33 mar exp $
 */
public final class DoubleArrow extends ru.ifmo.vizi.base.widgets.Shape {

    /**
     * X  coordinate of the first line point
     */
    int x1;

    /**
     * Y  coordinate of the first line point
     */
    int y1;

    /**
     * X  coordinate of the second line point
     */
    int x2;

    /**
     * Y  coordinate of the second line point
     */
    int y2;

    /**
     * Line height
     **/
    int height;

    /**
     * Line color
     */
    Color lineColor;


    /**
     * Creates a new Line with specifed style set, width and
     * (0, 0)(100, 100) coordinates
     *
     * @param styleSet shape's style set.
     * @param width line width
     */
    public DoubleArrow(ShapeStyle[] styleSet) {
        super(styleSet, "");

        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 100;
        this.y2 = 100;

        lineColor = Color.black;

        setStyle(0);
    }

    /**
     * Creates a new Line with specifed style set, width and
     * coordinates
     *
     * @param styleSet shape's style set.
     * @param width line width
     * @x1 A.x coordinate
     * @y1 A.y coordinate
     * @x2 B.x coordinate
     * @y2 B.y coordinate
     */
    public DoubleArrow(ShapeStyle[] styleSet, int x1, int y1, int x2, int y2) {
        super(styleSet, "");

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        lineColor = Color.black;

        setStyle(0);
    }

    /**
     * Returns minimum shape size to contain message with specified size.
     *
     * @param size message size.
     *
     * @return minimum shape size.
     */
    protected Dimension fit(Dimension size) {
        return new Dimension(
                (int) 0,
                (int) 0
        );
    }

    /**
     * Gets x1 line coordinate. 
     *
     * @return x1 line coordinate.
     */
    public int getX1() {
        return x1;
    }

    /**
     * Gets y1 line coordinate. 
     *
     * @return y1 line coordinate.
     */
    public int getY1() {
        return y1;
    }

     /**
     * Gets x2 line coordinate. 
     *
     * @return x2 line coordinate.
     */
    public int getX2() {
        return x2;
    }

     /**
     * Gets y2 line coordinate. 
     *
     * @return y2 line coordinate.
     */
    public int getY2() {
        return y2;
    }

    public void setCoords(int x1, int y1, int x2, int y2, int height) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.height = height;
    }

    /**
     * Sets line color.
     * 
     * @param clr - color value.
     */
    public void setColor(Color clr){
      this.lineColor = clr;
    }

    /**
     * Paints this component.
     *
     * @param g graphics context for painting.
     */
    public void paint(Graphics g) {
        Dimension size = getSize();
        super.paint(g);
        g.setColor(lineColor);
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, x1, y1 + height);
        g.drawLine(x1, y1 + height, x1 - height / 4, y1 + height / 2);
        g.drawLine(x1, y1 + height, x1 + height / 4, y1 + height / 2);
        g.drawLine(x2, y2, x2, y2 + height);
        g.drawLine(x2, y2 + height, x2 - height / 4, y2 + height / 2);
        g.drawLine(x2, y2 + height, x2 + height / 4, y2 + height / 2);
    }
}
