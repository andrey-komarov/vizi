package ru.ifmo.vizi.merge_sort;

import java.awt.*;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

/**
 * Simple Horisontal bracket.
 *
 * @author Dmitry Paraschenko
 * @version 2004/15/10, 23:37
 */
public final class HorisontalBracket extends 
                   ru.ifmo.vizi.base.widgets.Shape {
    /**
     * X  coordinate of the first line point
     */
    int x1;

    /**
     * X  coordinate of the second line point
     */
    int x2;

    /**
     * Y  coordinate of the line points
     */
    int y;

    /**
     * Line height
     **/
    int height;

    /**
     * Number of left border cell
     */
    public final int left;

    /**
     * Number of right border cell
     */
    public final int right;
    
    /**
     * Creates a new Line with specified style set, width and
     * (0, 0)(0, 0) coordinates and zero height
     *
     * @param styleSet shape's style set.
     * @param width line width
     */
    public HorisontalBracket(ShapeStyle[] styleSet, int left, int right) {
        super(styleSet, "");

        this.left = left;
        this.right = right;

        setStyle(0);
        setCoords(0, 0, 0, 0);
    }

    /**
     * Returns minimum shape size to contain message with specified size.
     *
     * @param size message size.
     *
     * @return minimum shape size.
     */
    protected Dimension fit(Dimension size) {
        return size;
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
     * Gets x2 line coordinate. 
     *
     * @return x2 line coordinate.
     */
    public int getX2() {
        return x2;
    }

     /**
     * Gets y line coordinate. 
     *
     * @return y line coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets speified coordinates and height of HorisaontalBracket.
     * @param x1 X coordinate of first line point
     * @param x2 X coordinate of second line point
     * @param y Y coordinate of line points
     * @param height height of bracket
     */
    public void setCoords(int x1, int x2,  int y, int height) {
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;
        this.height = height;
        this.setBounds(x1, y, x2, y + height);
    }

    /**
     * Paints this component.
     *
     * @param g graphics context for painting.
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(style.getTextColor());
        g.drawLine(0, 0, x2 - x1 - 1, 0);
        g.drawLine(0, 0, 0, height - 1);
        g.drawLine(x2 - x1 - 1, 0, x2 - x1 - 1, height - 1);
    }
}
