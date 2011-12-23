package ru.ifmo.vizi.heap_sort;

import java.awt.*;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

/**
 * Simple line, without any arrows or something.
 *
 * @author  Ilya Pimenov
 * @version $Id: Line.java,v 0.2 2004/04/04 20:54:33 mar exp $
 */
public final class Line extends ru.ifmo.vizi.base.widgets.Shape {

    /**
     * X  coordinate of the first line point
     */
    private int x1;

    /**
     * Y  coordinate of the first line point
     */
    private int y1;

    /**
     * X  coordinate of the second line point
     */
    private int x2;

    /**
     * Y  coordinate of the second line point
     */
    private int y2;

    /**
     * Line color
     */
    private Color lineColor;

    /**
     * Element radius, on whom line shows
     */
    int dRadius;

    /**
     * If arrow needed
     */
    private boolean arrowed = false;

    /**
     * Creates a new Line with specifed style set
     * (0, 0)(100, 100) coordinates
     * Black color
     *
     * @param styleSet shape's style set.
     * @param width line width
     */
    public Line(ShapeStyle[] styleSet) {
        super(styleSet, "");
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 100;
        this.y2 = 100;
        this.dRadius = 0;

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
    public Line(ShapeStyle[] styleSet, int x1, int y1, int x2, int y2, Color clr) {
        super(styleSet, "");

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        lineColor = clr;
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
     * Sets line cordinates.
     *
     * @param x1 a.x line cordinate. 
     * @param y1 a.y line cordinate. 
     * @param x2 b.x line cordinate. 
     * @param y2 b.y line cordinate. 
     */
    public void setCoord(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Sets dRound
     */           
    public void setDRadius(int newDRadius){
        this.dRadius = newDRadius;
    }

    /**
     * Sets arrow type
     */
    public void setArrowed(boolean arr){
      this.arrowed = arr;
    }

    /**
     * Gets arrow type
     */
    public boolean getArrowed(){
      return this.arrowed;      
    }

    /**
     * Sets line color.
     * 
     * @param clr color value.
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

        if ( arrowed ){

            double dX1 = x1;
            double dY1 = y1;
            double dX2 = x2;
            double dY2 = y2;

            double dF = Math.PI / 12;
            int partOf = 3;
            int maxA = 15;
            int minA = 7;

            if (x1 > x2){
                if (y1 > y2){
                    double Angle = Math.atan((dX1 - dX2)/(dY1 - dY2));
                    int ddXRadius = (int)( dRadius * Math.cos( Angle));
                    int ddYRadius = (int)( dRadius * Math.sin( Angle));
                    dX2 += ddXRadius;
                    dY2 += ddYRadius;
                    ddXRadius = (int)( dRadius * Math.cos( Angle));
                    ddYRadius = (int)( dRadius * Math.sin( Angle));
                    dX1 -= ddXRadius;
                    dY1 -= ddYRadius;

                    int a = (int)( Math.sqrt( (dX2 - dX1)*(dX2 - dX1) + (dY2 - dY1)*(dY2 - dY1) ) / partOf );
                    if (a > maxA){
                        a = maxA;
                    }
                    if (a < minA){
                        a = minA;
                    }
                    int ddX1 = (int)( a * Math.sin( Angle - dF ));
                    int ddY1 = (int)( a * Math.cos( Angle - dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 + ddX1, (int)dY2 + ddY1);
                    int ddX2 = (int)( a * Math.sin( Angle + dF ));
                    int ddY2 = (int)( a * Math.cos( Angle + dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 + ddX2, (int)dY2 + ddY2);
//                    g.drawLine((int)dX2 + ddX1, (int)dY2 + ddY1, (int)dX2 + ddX2, (int)dY2 + ddY2);

                    Angle = Math.PI / 2 - Angle;
                    ddX1 = (int)( a * Math.cos( Angle - dF ));
                    ddY1 = (int)( a * Math.sin( Angle - dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 - ddX1, (int)dY1 - ddY1);
                    ddX2 = (int)( a * Math.cos( Angle + dF ));
                    ddY2 = (int)( a * Math.sin( Angle + dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 - ddX2, (int)dY1 - ddY2);
//                    g.drawLine((int)dX1 - ddX1, (int)dY1 - ddY1, (int)dX1 - ddX2, (int)dY1 - ddY2);
                }else {
                    double Angle = Math.atan((dY2 - dY1)/(dX1 - dX2));
                    int ddXRadius = (int)( dRadius * Math.cos( Angle));
                    int ddYRadius = (int)( dRadius * Math.sin( Angle));
                    dX2 += ddXRadius;
                    dY2 -= ddYRadius;
                    ddXRadius = (int)( dRadius * Math.cos( Angle));
                    ddYRadius = (int)( dRadius * Math.sin( Angle));
                    dX1 -= ddXRadius;
                    dY1 += ddYRadius;

                    int a = (int)( Math.sqrt( (dX2 - dX1)*(dX2 - dX1) + (dY2 - dY1)*(dY2 - dY1) ) / partOf );
                    if (a > maxA){
                        a = maxA;
                    }
                    if (a < minA){
                        a = minA;
                    }
                    int ddX1 = (int)( a * Math.cos( Angle - dF ));
                    int ddY1 = (int)( a * Math.sin( Angle - dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 + ddX1, (int)dY2 - ddY1);
                    int ddX2 = (int)( a * Math.cos( Angle + dF ));
                    int ddY2 = (int)( a * Math.sin( Angle + dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 + ddX2, (int)dY2 - ddY2);
//                    g.drawLine((int)dX2 + ddX1, (int)dY2 - ddY1, (int)dX2 + ddX2, (int)dY2 - ddY2);

                    Angle = Math.PI / 2 - Angle;
                    ddX1 = (int)( a * Math.sin( Angle - dF ));
                    ddY1 = (int)( a * Math.cos( Angle - dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 - ddX1, (int)dY1 + ddY1);
                    ddX2 = (int)( a * Math.sin( Angle + dF ));
                    ddY2 = (int)( a * Math.cos( Angle + dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 - ddX2, (int)dY1 + ddY2);
//                    g.drawLine((int)dX1 - ddX1, (int)dY1 + ddY1, (int)dX1 - ddX2, (int)dY1 + ddY2);
                }
            }else {
                if (y1 > y2){
                    double Angle = Math.atan((dY1 - dY2)/(dX2 - dX1));
                    int ddXRadius = (int)( dRadius * Math.sin( Angle));
                    int ddYRadius = (int)( dRadius * Math.cos( Angle));
                    dX1 += ddXRadius;
                    dY1 -= ddYRadius;
                    ddXRadius = (int)( dRadius * Math.sin( Angle));
                    ddYRadius = (int)( dRadius * Math.cos( Angle));
                    dX2 -= ddXRadius;
                    dY2 += ddYRadius;

                    int a = (int)( Math.sqrt( (dX2 - dX1)*(dX2 - dX1) + (dY2 - dY1)*(dY2 - dY1) ) / partOf );
                    if (a > maxA){
                        a = maxA;
                    }
                    if (a < minA){
                        a = minA;
                    }
                    int ddX1 = (int)( a * Math.cos( Angle - dF ));
                    int ddY1 = (int)( a * Math.sin( Angle - dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 + ddX1, (int)dY1 - ddY1);
                    int ddX2 = (int)( a * Math.cos( Angle + dF ));
                    int ddY2 = (int)( a * Math.sin( Angle + dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 + ddX2, (int)dY1 - ddY2);
//                    g.drawLine((int)dX1 + ddX1, (int)dY1 - ddY1, (int)dX1 + ddX2, (int)dY1 - ddY2);

                    Angle = Math.PI / 2 - Angle;
                    ddX1 = (int)( a * Math.sin( Angle - dF ));
                    ddY1 = (int)( a * Math.cos( Angle - dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 - ddX1, (int)dY2 + ddY1);
                    ddX2 = (int)( a * Math.sin( Angle + dF ));
                    ddY2 = (int)( a * Math.cos( Angle + dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 - ddX2, (int)dY2 + ddY2);
//                    g.drawLine((int)dX2 - ddX1, (int)dY2 + ddY1, (int)dX2 - ddX2, (int)dY2 + ddY2);
                }else {
                    double Angle = Math.atan((dX2 - dX1)/(dY2 - dY1));
                    int ddXRadius = (int)( dRadius * Math.sin( Angle));
                    int ddYRadius = (int)( dRadius * Math.cos( Angle));
                    dX1 += ddXRadius;
                    dY1 += ddYRadius;
                    ddXRadius = (int)( dRadius * Math.sin( Angle));
                    ddYRadius = (int)( dRadius * Math.cos( Angle));
                    dX2 -= ddXRadius;
                    dY2 -= ddYRadius;

                    int a = (int)( Math.sqrt( (dX2 - dX1)*(dX2 - dX1) + (dY2 - dY1)*(dY2 - dY1) ) / partOf );
                    if (a > maxA){
                        a = maxA;
                    }
                    if (a < minA){
                        a = minA;
                    }
                    int ddX1 = (int)( a * Math.sin( Angle - dF ));
                    int ddY1 = (int)( a * Math.cos( Angle - dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 + ddX1, (int)dY1 + ddY1);
                    int ddX2 = (int)( a * Math.sin( Angle + dF ));
                    int ddY2 = (int)( a * Math.cos( Angle + dF ));
                    g.drawLine((int)dX1, (int)dY1, (int)dX1 + ddX2, (int)dY1 + ddY2);
//                    g.drawLine((int)dX1 + ddX1, (int)dY1 + ddY1, (int)dX1 + ddX2, (int)dY1 + ddY2);

                    Angle = Math.PI / 2 - Angle;
                    ddX1 = (int)( a * Math.cos( Angle - dF ));
                    ddY1 = (int)( a * Math.sin( Angle - dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 - ddX1, (int)dY2 - ddY1);
                    ddX2 = (int)( a * Math.cos( Angle + dF ));
                    ddY2 = (int)( a * Math.sin( Angle + dF ));
                    g.drawLine((int)dX2, (int)dY2, (int)dX2 - ddX2, (int)dY2 - ddY2);
//                    g.drawLine((int)dX2 - ddX1, (int)dY2 - ddY1, (int)dX2 - ddX2, (int)dY2 - ddY2);
                }
            }
            g.drawLine((int)dX1, (int)dY1, (int)dX2, (int)dY2);
        }else {
            g.drawLine(x1, y1, x2, y2);
        }

    }


}