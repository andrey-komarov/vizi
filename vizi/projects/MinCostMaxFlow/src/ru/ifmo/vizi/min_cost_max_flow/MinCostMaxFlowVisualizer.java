/*
*
* MinCost-MaxFlow visualizer class
*
* Copyright (C) 2008 by Aleksey Sergushichev <alsergbox@gmail.com>
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or (at
* your option) any later version.
*
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
* USA
*
*/

package ru.ifmo.vizi.min_cost_max_flow;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.Panel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.PopupMenu;
import java.awt.event.*;

import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.widgets.*;

/**
 * @author Aleksey Sergushichev
 */
public class MinCostMaxFlowVisualizer extends Base
        implements ActionListener {

//    Styles:
    private final int ORD_VERTEX = 3;
    private final int COST_MSG = 6;
    private final int FLOW_MSG = 6;
    private final int HL_EDGE = 2;
    private final int ORD_EDGE = 1;
    private final int HL_DISTANCE = 4;
    private final int ORD_DISTANCE = 7;
    private final int HL_POTENTIAL = 4;
    private final int ORD_POTENTIAL = 7;
    private final int EXT_VERT_LABEL = 9;
    private final int HL_EDGE_INFO = 10;
    private final int ORD_EDGE_INFO = 3;
    private final int EXMPL_VERTEX = 6;
    /**
     * Infinity character
     */
    private final String INFINITY = "\u221e";
    /**
     * MinCost MaxFlow automata instance
     */
    private final MinCostMaxFlow auto;
    /**
     * MinCost MaxFlow automata data
     */
    private final MinCostMaxFlow.Data data;
    /**
     * Save/load dialog
     */
    private SaveLoadDialog saveLoadDialog;
    /**
     * Start/Stop edit mode button
     */
    private MultiButton editModeButton;
    /**
     * Edit mode flag
     */
    private boolean editMode;
    /**
     * Array shape style set
     */
    private final ShapeStyle[] styleSet;
    /**
     * Main panel
     */
    private Container panel;
    /**
     * Auto controls pane
     */
    private AutoControlsPane autoControlsPane;
    /**
     * Popup menu for client pane
     */
    private PopupMenu clientPanePopupMenu;
    /**
     * AddVertex item for clientPanePopupMenu
     */
    private MenuItem addVertexItem;
    /**
     * RemoveVertex item for clientPanePopupMenu
     */
    private MenuItem removeVertexItem;
    /**
     * Cost message rectangle
     */
    private Rect costRect;
    /**
     * Flow message rectangle
     */
    private Rect flowRect;
    /**
     * Relative extended radius on x-axis
     */
    private float radX;
    /**
     * Relative extended radius on y-axis
     */
    private float radY;
    /**
     * Extended radius
     */
    private int rad;
    
    private class ExampleVertex extends Shape {
        /**
         * Creates new graph with specific style set
         * 
         * @param styleSet shape's style set
         */
        public ExampleVertex(ShapeStyle[] styleSet) {
            super(styleSet);
        }

        public Dimension fit(Dimension size) {
            return new Dimension(0, 0);
        }
        
        public void paint(Graphics g) {
            Dimension size = getSize();
            ExtendedVertex ev = new ExtendedVertex(styleSet);            
            ev.setDistance("7");
            ev.setPotential("3");
            ev.setMessage("1");
            int radius = Math.min(7*size.height/2/8 ,7*size.width/8/8);
            ev.setSize(radius * 2, radius * 2);   
            int vx = size.width/2;
            int vy = size.height/2;
            // Little hack
            int t = data.highlight;
            data.highlight = 0;
            ev.paint(g.create(vx - radius , vy - radius,
                     ev.getSize().width , ev.getSize().height));
            data.highlight = t;
            
            Rect rect = new Rect(styleSet);
            rect.setStyle(EXMPL_VERTEX);            
            rect.setSize(3*size.width/8, size.height/2);
            rect.adjustFontSize(config.getParameter("example-label"));
            Font minFont = rect.getLook().getTextFont(style);
            rect.adjustFontSize(config.getParameter("example-potential"));
            if (rect.getLook().getTextFont(style).getSize() < minFont.getSize() )
                minFont = rect.getLook().getTextFont(style);
            rect.adjustFontSize(config.getParameter("example-distance"));
            if (rect.getLook().getTextFont(style).getSize() < minFont.getSize() )
                minFont = rect.getLook().getTextFont(style);
            rect.getLook().setTextFont(minFont);
            rect.setLocation(0,0);
            rect.setMessage(config.getParameter("example-label"));            
            rect.paint(g.create(0, 0 , rect.getSize().width, rect.getSize().height));            
            Font font = rect.getLook().getTextFont(style);            
            Dimension msgSize;
            msgSize = (new Message(rect.getMessage(),0)).calculateSize(getFontMetrics(font), 0);
            g.drawLine(rect.getLocation().x + (rect.getSize().width - msgSize.width)/2,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16,
                       rect.getLocation().x + (rect.getSize().width - msgSize.width)/2 + msgSize.width,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16);
            g.drawLine(rect.getLocation().x + (rect.getSize().width - msgSize.width)/2 + msgSize.width,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16,
                       Math.round(vx - (float)Math.sqrt(1./4)*radius), Math.round(vy - (float)Math.sqrt(1./4)*radius));            
            
            rect.setLocation(0, size.height - rect.getSize().height);
            rect.setMessage(config.getParameter("example-potential"));            
            rect.paint(g.create(rect.getLocation().x, rect.getLocation().y,
                       rect.getSize().width, rect.getSize().height));            
            font = rect.getLook().getTextFont(style);            
            msgSize = (new Message(rect.getMessage(),0)).calculateSize(getFontMetrics(font), 0);
            g.drawLine(rect.getLocation().x + (rect.getSize().width - msgSize.width)/2,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16,
                       rect.getLocation().x + (rect.getSize().width - msgSize.width)/2 + msgSize.width,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16);
            g.drawLine(rect.getLocation().x + (rect.getSize().width - msgSize.width)/2 + msgSize.width,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16,
                       Math.round(vx - (float)Math.sqrt(1./3)*radius), Math.round(vy + (float)Math.sqrt(1./3)*radius));            
            
            rect.setLocation(size.width - rect.getSize().width, size.height - rect.getSize().height);
            rect.setMessage(config.getParameter("example-distance"));            
            rect.paint(g.create(rect.getLocation().x, rect.getLocation().y,
                       rect.getSize().width, rect.getSize().height));            
            font = rect.getLook().getTextFont(style);            
            msgSize = (new Message(rect.getMessage(),0)).calculateSize(getFontMetrics(font), 0);
            g.drawLine(rect.getLocation().x + (rect.getSize().width - msgSize.width)/2,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16,
                       rect.getLocation().x + (rect.getSize().width - msgSize.width)/2 + msgSize.width,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16);
            g.drawLine(rect.getLocation().x + (rect.getSize().width - msgSize.width)/2,
                       rect.getLocation().y + 7*(rect.getSize().height + msgSize.height)/16,
                       Math.round(vx + (float)Math.sqrt(1./3)*radius), Math.round(vy + (float)Math.sqrt(1./3)*radius));            
        }
    }
    
    private ExampleVertex exampleVertex;
            
    /**
     * My comment pane (resizeable)
     */
    private MyCommentPane commentPane;
    /**
     * Minimal size (used for convert relative sizes to absolute)
     */
    private int minSize;

    private int headerHeight;
    
    /**
     * Draws arc
     * 
     * @param g Graphics to paint
     * @param x x-coordinate of center
     * @param y y-coordinate if center
     * @param r - ark radius
     * @param startAngle - start angle
     * @param angle - angle of ark
     */    
    private void drawArc(Graphics g, double x, double y, double r, double startAngle, double angle) {
        int count = 100;
        double dphi = angle / count;
        for (int i = 0; i < count; ++i) {
            g.drawLine(Math.round((float) (x + r * Math.cos(startAngle))),
                    Math.round((float) (y + r * Math.sin(startAngle))),
                    Math.round((float) (x + r * Math.cos(startAngle + dphi))),
                    Math.round((float) (y + r * Math.sin(startAngle + dphi))));
            startAngle += dphi;
        }

    }

    class PointerFromVertex extends Shape {

        private Vertex from;
        private Point to;

        public PointerFromVertex(ShapeStyle[] styleSet, Vertex from, Point to) {
            super(styleSet);
            this.from = from;
            this.to = to;
        }

        public Dimension fit(Dimension size) {
            return new Dimension(0, 0);
        }

        public void paint(Graphics g) {
            Dimension size = getSize();
            g.setColor(look.getBorderColor(style));
            
            float dx = to.x - from.x * size.width;
            float dy = to.y - from.y * size.height;
            float d = (float) Math.sqrt(dx * dx + dy * dy);
            float cos = dx / d;
            float sin = dy / d;
            float radius = -1 + (float) (minSize * config.getDouble("ordinary-radius"));

            float[] xpoints = new float[4];
            float[] ypoints = new float[4];
            double edge_angle = config.getDouble("edge-angle");
            double phi = config.getDouble("pointer-angle");
            double pointerLength = config.getDouble("pointer-length") * radius / config.getDouble("ordinary-radius");
            double cos_t = cos * Math.cos(edge_angle) - sin * Math.sin(edge_angle);
            double sin_t = sin * Math.cos(edge_angle) + cos * Math.sin(edge_angle);
            xpoints[0] = from.x * size.width + (float) cos_t * radius;
            ypoints[0] = from.y * size.height + (float) sin_t * radius;
            cos_t = cos * Math.cos(edge_angle) + sin * Math.sin(edge_angle);
            sin_t = sin * Math.cos(edge_angle) - cos * Math.sin(edge_angle);
            xpoints[3] = from.x * size.width + (float) cos_t * radius;
            ypoints[3] = from.y * size.height + (float) sin_t * radius;
            
            xpoints[1] = to.x - (float) (pointerLength * Math.cos(phi) * cos + radius*Math.sin(edge_angle)*sin);
            ypoints[1] = to.y - (float) (pointerLength * Math.cos(phi) * sin - radius*Math.sin(edge_angle)*cos);
            
            xpoints[2] = to.x - (float) (pointerLength * Math.cos(phi) * cos - radius*Math.sin(edge_angle)*sin);
            ypoints[2] = to.y - (float) (pointerLength * Math.cos(phi) * sin + radius*Math.sin(edge_angle)*cos);
            

            Polygon poly = new Polygon();
            
            for (int k = 0; k < 4; ++k) {
                poly.addPoint(Math.round(xpoints[k]), Math.round(ypoints[k]));
            }
            
            g.drawPolyline(poly.xpoints, poly.ypoints, 4);
            

            float finishx = to.x;
            float finishy = to.y;

            double pcos = cos * Math.cos(phi) - sin * Math.sin(phi);
            double psin = sin * Math.cos(phi) + cos * Math.sin(phi);


            poly = new Polygon();
            poly.addPoint(Math.round(finishx), Math.round(finishy));
            float pointerDx = (float) (pointerLength * pcos);
            float pointerDy = (float) (pointerLength * psin);

            poly.addPoint(Math.round(finishx - pointerDx), Math.round(finishy - pointerDy));

            pcos = cos * Math.cos(phi) + sin * Math.sin(phi);
            psin = sin * Math.cos(phi) - cos * Math.sin(phi);

            pointerDx = (float) (pointerLength * pcos);
            pointerDy = (float) (pointerLength * psin);
            poly.addPoint(Math.round(finishx - pointerDx), Math.round(finishy - pointerDy));
            g.setColor(look.getBorderColor(style));
            g.fillPolygon(poly);
        }
    }

    /**
     * Класс для всех ребер
     */
    private final class Edges extends Shape {

        /**
         * Creates new graph with specific style set
         * 
         * @param styleSet shape's style set
         */
        public Edges(ShapeStyle[] styleSet) {
            super(styleSet);
        }

        public Dimension fit(Dimension size) {
            return new Dimension(0, 0);
        }

        /**
         * Paints this component
         * 
         * @param g Graphics contex to paint
         */
        public void paint(Graphics g) {
            Dimension size = getSize();
            g.drawLine(0, 0, size.width, 0);
            //Обычное ребро (без информации)
            for (int i = 0; i < data.n; ++i) {
                for (int j = 0; j < data.n; ++j) {
                    if (data.edge[i][j] == null) {
                        continue;
                    }

                    if ((data.edge[i][j].highlighted) || (data.reverseEdge[j][i].highlighted))  {
                        setStyle(HL_EDGE);
                    } // Выделенное ребро
                    else {
                        setStyle(ORD_EDGE);
                    } // Обычное ребро 
                    g.setColor(look.getBorderColor(style));

                    float dx = (vertices[j].x - vertices[i].x) * size.width;
                    float dy = (vertices[j].y - vertices[i].y) * size.height;
                    float d = (float) Math.sqrt(dx * dx + dy * dy);
                    float cos = dx / d;
                    float sin = dy / d;
                    float radius = 0;
                    if (data.extendedDraw) {
                        radius = -1 + (float) (minSize * config.getDouble("extended-radius"));
                    } else {
                        radius = -1 + (float) (minSize * config.getDouble("ordinary-radius"));
                    }

                    float[] xpoints = new float[4];
                    float[] ypoints = new float[4];
                    double edge_angle = config.getDouble("edge-angle");
                    double co = Math.pow(data.edge[i][j].capacity / config.getDouble("max-capacity"), 0.75);
                    // Non linear because 1/9 is invisible
                    edge_angle = Math.asin(Math.sin(edge_angle) * co);
                    double phi = config.getDouble("pointer-angle");
                    double pointerLength = config.getDouble("pointer-length") * radius / config.getDouble("ordinary-radius");
                    double cos_t = cos * Math.cos(edge_angle) - sin * Math.sin(edge_angle);
                    double sin_t = sin * Math.cos(edge_angle) + cos * Math.sin(edge_angle);
                    xpoints[0] = vertices[i].x * size.width + (float) cos_t * radius;
                    ypoints[0] = vertices[i].y * size.height + (float) sin_t * radius;
                    xpoints[2] = vertices[j].x * size.width - (float) cos_t * radius - (float) (pointerLength * Math.cos(phi) * cos);
                    ypoints[2] = vertices[j].y * size.height - (float) sin_t * radius - (float) (pointerLength * Math.cos(phi) * sin);
                    cos_t = cos * Math.cos(edge_angle) + sin * Math.sin(edge_angle);
                    sin_t = sin * Math.cos(edge_angle) - cos * Math.sin(edge_angle);
                    xpoints[1] = vertices[j].x * size.width - (float) cos_t * radius - (float) (pointerLength * Math.cos(phi) * cos);
                    ypoints[1] = vertices[j].y * size.height - (float) sin_t * radius - (float) (pointerLength * Math.cos(phi) * sin);
                    xpoints[3] = vertices[i].x * size.width + (float) cos_t * radius;
                    ypoints[3] = vertices[i].y * size.height + (float) sin_t * radius;

                    xpoints[3] = xpoints[0] + (xpoints[3] - xpoints[0]) * data.edge[i][j].flow / data.edge[i][j].capacity;
                    ypoints[3] = ypoints[0] + (ypoints[3] - ypoints[0]) * data.edge[i][j].flow / data.edge[i][j].capacity;
                    xpoints[2] = xpoints[1] + (xpoints[2] - xpoints[1]) * data.edge[i][j].flow / data.edge[i][j].capacity;
                    ypoints[2] = ypoints[1] + (ypoints[2] - ypoints[1]) * data.edge[i][j].flow / data.edge[i][j].capacity;

                    Polygon poly = new Polygon();
                    for (int k = 0; k < 4; ++k) {
                        poly.addPoint(Math.round(xpoints[k]), Math.round(ypoints[k]));
                    }
                    g.setColor(look.getFillColor(style));
                    g.fillPolygon(poly);

                    cos_t = cos * Math.cos(edge_angle) - sin * Math.sin(edge_angle);
                    sin_t = sin * Math.cos(edge_angle) + cos * Math.sin(edge_angle);
                    xpoints[0] = vertices[i].x * size.width + (float) cos_t * radius;
                    ypoints[0] = vertices[i].y * size.height + (float) sin_t * radius;
                    cos_t = cos * Math.cos(edge_angle) + sin * Math.sin(edge_angle);
                    sin_t = sin * Math.cos(edge_angle) - cos * Math.sin(edge_angle);
                    xpoints[1] = vertices[j].x * size.width - (float) cos_t * radius - (float) (pointerLength * Math.cos(phi) * cos);
                    ypoints[1] = vertices[j].y * size.height - (float) sin_t * radius - (float) (pointerLength * Math.cos(phi) * sin);
                    cos_t = cos * Math.cos(edge_angle) - sin * Math.sin(edge_angle);
                    sin_t = sin * Math.cos(edge_angle) + cos * Math.sin(edge_angle);
                    xpoints[2] = vertices[j].x * size.width - (float) cos_t * radius - (float) (pointerLength * Math.cos(phi) * cos);
                    ypoints[2] = vertices[j].y * size.height - (float) sin_t * radius - (float) (pointerLength * Math.cos(phi) * sin);
                    cos_t = cos * Math.cos(edge_angle) + sin * Math.sin(edge_angle);
                    sin_t = sin * Math.cos(edge_angle) - cos * Math.sin(edge_angle);
                    xpoints[3] = vertices[i].x * size.width + (float) cos_t * radius;
                    ypoints[3] = vertices[i].y * size.height + (float) sin_t * radius;

                    poly = new Polygon();
                    for (int k = 0; k < 4; ++k) {
                        poly.addPoint(Math.round(xpoints[k]), Math.round(ypoints[k]));
                    }
                    g.setColor(look.getBorderColor(style));
                    g.drawPolygon(poly);

                    float dvector_x = cos * radius;
                    float dvector_y = sin * radius;
                    float finishx = vertices[j].x * size.width - dvector_x;
                    float finishy = vertices[j].y * size.height - dvector_y;

                    double pcos = cos * Math.cos(phi) - sin * Math.sin(phi);
                    double psin = sin * Math.cos(phi) + cos * Math.sin(phi);


                    poly = new Polygon();
                    poly.addPoint(Math.round(finishx), Math.round(finishy));
                    float pointerDx = (float) (pointerLength * pcos);
                    float pointerDy = (float) (pointerLength * psin);

                    poly.addPoint(Math.round(finishx - pointerDx), Math.round(finishy - pointerDy));

                    pcos = cos * Math.cos(phi) + sin * Math.sin(phi);
                    psin = sin * Math.cos(phi) - cos * Math.sin(phi);

                    pointerDx = (float) (pointerLength * pcos);
                    pointerDy = (float) (pointerLength * psin);
                    poly.addPoint(Math.round(finishx - pointerDx), Math.round(finishy - pointerDy));
                    g.setColor(look.getBorderColor(style));
                    g.fillPolygon(poly);

                }
            }

            // Информация для обычного ребра
            for (int i = 0; i < data.n; ++i) {
                for (int j = 0; j < data.n; ++j) {
                    int styleNo;
                    if (data.edge[i][j] == null) {
                        continue;
                    }

                    if ((data.edge[i][j].highlighted) || (data.reverseEdge[j][i].highlighted))  {
                        setStyle(HL_EDGE_INFO);
                        styleNo = HL_EDGE_INFO;
                    } // Выделенное ребро
                    else {
                        setStyle(ORD_EDGE_INFO);
                        styleNo = ORD_EDGE_INFO;
                    } // Обычное ребро 
                    g.setColor(look.getBorderColor(style));

                    float dx = (vertices[j].x - vertices[i].x) * size.width;
                    float dy = (vertices[j].y - vertices[i].y) * size.height;
                    float d = (float) Math.sqrt(dx * dx + dy * dy);
                    float cos = dx / d;
                    float sin = dy / d;
                    float radius;
                    if (data.extendedDraw) {
                        radius = (float) (minSize * config.getDouble("extended-radius"));
                    } else {
                        radius = (float) (minSize * config.getDouble("ordinary-radius"));
                    }
                    float dvector_x = cos * radius;
                    float dvector_y = sin * radius;
                    float startx = vertices[i].x * size.width + dvector_x;
                    float starty = vertices[i].y * size.height + dvector_y;
                    float finishx = vertices[j].x * size.width - dvector_x;
                    float finishy = vertices[j].y * size.height - dvector_y;

                    float costCoordinate = (float) config.getDouble("cost-coordinate");
                    float costCenterX = startx * (1 - costCoordinate) + finishx * costCoordinate;
                    float costCenterY = starty * (1 - costCoordinate) + finishy * costCoordinate;
                    int costDiameter = Math.round((float) config.getDouble("cost-diameter") * minSize);
                    Ellipse el = new Ellipse(styleSet, new Long(data.edge[i][j].cost).toString());
                    el.setStyle(styleNo);
                    el.setSize(costDiameter, costDiameter);
                    el.adjustFontSize();
                    el.paint(g.create(Math.round(costCenterX - costDiameter / 2.0f),
                            Math.round(costCenterY - costDiameter / 2.0f),
                            el.getSize().width, el.getSize().height));

                    float capacityCoordinate = (float) config.getDouble("capacity-coordinate");
                    float capacityCenterX = startx * (1 - capacityCoordinate) + finishx * capacityCoordinate;
                    float capacityCenterY = starty * (1 - capacityCoordinate) + finishy * capacityCoordinate;
                    float capacityWidth = (float) config.getDouble("capacity-width") * minSize;
                    float capacityHeight = (float) config.getDouble("capacity-height") * minSize;

                    el = new Ellipse(styleSet, new Long(data.edge[i][j].flow).toString() + "/" +
                            new Long(data.edge[i][j].capacity));
                    el.setStyle(styleNo);
                    el.setSize(Math.round(capacityWidth), Math.round(capacityHeight));
                    el.adjustFontSize();
                    el.paint(g.create(Math.round(capacityCenterX - capacityWidth / 2.0f),
                            Math.round(capacityCenterY - capacityHeight / 2.0f),
                            el.getSize().width, el.getSize().height));

                }
            }

        }
    }
    private final Edges edges;

    /**
     * Класс вершины
     */
    private final class Vertex {

        // левый верхний угол имеет координаты (0,0)
        public float x; // x-координата вершины в относительных единицах
        public float y; // y-координата вершины в относительных единицах
        public String label; //метка вершины (номер, S или T)
        public Ellipse ellipse;
        public ExtendedVertex extVertex;
        public Shape shape;
        public int number;

        public void setLabel(String label) {
            this.label = label;
            ellipse.setMessage(label);
            extVertex.setMessage(label);
        }

        public void adjustSize() {
            int radius = Math.round((float) config.getDouble("ordinary-radius") * minSize);
            this.ellipse.setSize(radius * 2, radius * 2);
            this.ellipse.adjustFontSize();
            radius = Math.round((float) config.getDouble("extended-radius") * minSize);
            this.extVertex.setSize(radius * 2, radius * 2);
        }

        public Vertex(float x, float y, String label, int number, Ellipse ellipse, ExtendedVertex extVertex) {
            this.x = x;
            this.y = y;
            this.label = label;
            this.number = number;
            this.ellipse = ellipse;
            this.ellipse.setMessage(label);
            this.ellipse.setStyle(ORD_VERTEX);
            this.extVertex = extVertex;
            this.extVertex.setMessage(label);
            this.extVertex.setStyle(ORD_VERTEX);
            this.shape = ellipse;
        }
    }
    private Vertex[] vertices;

    /**
     * Extended vertex with potential and distance
     */
    private final class ExtendedVertex extends Shape {

        /**
         * Creates new graph with specific style set
         * 
         * @param styleSet shape's style set
         */
        public ExtendedVertex(ShapeStyle[] styleSet) {
            super(styleSet);
        }

        protected Dimension fit(Dimension size) {
            return new Dimension(0, 0);
        }
        String potential = null;
        String distance = null;

        /**
         * Set distance message
         * 
         * @param dist distance message to set (number or infinity)
         */
        public void setDistance(String dist) {
            distance = dist;
        }

        /**
         * Set potential message
         * 
         * @param pot potential message to set (number or infinity)
         */
        public void setPotential(String pot) {
            potential = pot;
        }

        /**
         * Paints this component
         * 
         * @param g graphics context for painting
         */
        public void paint(Graphics g) {
            Dimension size = getSize();
            g.setColor(look.getFillColor(style));
            g.fillOval(0, 0, size.width - 1, size.height - 1);
            g.setColor(look.getBorderColor(style));
            g.drawOval(0, 0, size.width - 1, size.height - 1);

            g.drawLine(size.width / 2, size.height / 2, size.width / 2, size.height - 1);
            g.drawLine(0, size.height / 2, size.width - 1, size.height / 2);

            Rect rect = new Rect(styleSet, getMessage());
            rect.setStyle(EXT_VERT_LABEL);
            rect.setSize(size.width / 2, 8 * size.height / 20);
            rect.adjustFontSize();
            rect.paint(g.create(size.width / 2 - rect.getSize().width / 2, size.height / 2 - rect.getSize().height,
                    rect.getSize().width, rect.getSize().height));

            rect = new Rect(styleSet, potential);

            if (data.highlight == 2) {
                rect.setStyle(HL_POTENTIAL);
            } else {
                rect.setStyle(ORD_POTENTIAL);
            }

            rect.setSize(7 * size.width / 20, 7 * size.height / 20); //sqrt(2)*1/4
            rect.adjustFontSize();
            rect.paint(g.create(size.width / 2 - rect.getSize().width, size.height / 2 + 1,
                    rect.getSize().width, rect.getSize().height));

            rect = new Rect(styleSet, distance);
            if (data.highlight == 1) {
                rect.setStyle(HL_DISTANCE);
            } else {
                rect.setStyle(ORD_DISTANCE);
            }

            rect.setSize(7 * size.width / 20, 7 * size.height / 20); //sqrt(2)*1/4
            rect.adjustFontSize();
            rect.paint(g.create(1 + size.width / 2, 1 + size.height / 2,
                    rect.getSize().width, rect.getSize().height));
        }
    }

    /**
     * Mouse listener for client pane
     */
    private class ClientPaneListener implements MouseListener, MouseMotionListener, ActionListener {

        private PointerFromVertex pointer;
        private Vertex curVertex;
        private Button leftButton;
        private Button midButton;
        private Button rightButton;
        private Edge curEdge;
        private int field; // 1 - cost, 2 - capacity
        private Vertex curVertexForPopup;
        private Point curPointForPopup;

        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();
//            System.out.println(1);
            if (source == leftButton) {
                switch (field) {
                    case 1: {
                        if (curEdge.cost == config.getInteger("min-cost")) {
                            break;
                        }
                        --curEdge.cost;
                        ++data.reverseEdge[curEdge.v][curEdge.u].cost;
                        break;
                    }

                    case 2: {
                        if (curEdge.capacity == config.getInteger("min-capacity")) {
                            break;
                        }
                        --curEdge.capacity;
                        break;
                    }
                }

                edges.repaint();
            } else if (source == midButton) {
                data.reverseEdge[curEdge.v][curEdge.u] = null;
                data.edge[curEdge.u][curEdge.v] = null;
                curEdge = null;
                drawGraph();
            } else if (source == rightButton) {
                switch (field) {
                    case 1: {
                        if (curEdge.cost == config.getInteger("max-cost")) {
                            break;
                        }
                        ++curEdge.cost;
                        --data.reverseEdge[curEdge.v][curEdge.u].cost;
                        break;
                    }

                    case 2: {
                        if (curEdge.capacity == config.getInteger("max-capacity")) {
                            break;
                        }
                        ++curEdge.capacity;
                        break;
                    }
                }
                edges.repaint();
            } else if (source == removeVertexItem) {
                clientPane.remove(curVertexForPopup.shape);
                Vertex[] vertices_ = new Vertex[data.n - 1];
                Edge[][] edge_ = new Edge[data.n - 1][data.n - 1];
                Edge[][] reverseEdge_ = new Edge[data.n - 1][data.n - 1];
                data.dist = new long[data.n - 1];
                data.phi = new long[data.n - 1];
                data.prev = new Edge[data.n - 1];
                data.used = new boolean[data.n - 1];
                int count = 0;
                for (int i = 0; i < data.n; ++i) {
                    if (i == curVertexForPopup.number) {
                        continue;
                    }
                    vertices_[count++] = vertices[i];
                }

                count = 0;
                for (int i = 0; i < data.n; ++i) {
                    if (i == curVertexForPopup.number) {
                        continue;
                    }
                    int count2 = 0;
                    for (int j = 0; j < data.n; ++j) {
                        if (j == curVertexForPopup.number) {
                            continue;
                        }
                        edge_[count][count2] = data.edge[i][j];
                        if (edge_[count][count2] != null) {
                            edge_[count][count2].u = count;
                            edge_[count][count2].v = count2;
                        }
                        reverseEdge_[count][count2] = data.reverseEdge[i][j];
                        if (reverseEdge_[count][count2] != null) {
                            reverseEdge_[count][count2].u = count;
                            reverseEdge_[count][count2].v = count2;
                        }
                        ++count2;
                    }
                    ++count;
                }
                data.edge = edge_;
                data.reverseEdge = reverseEdge_;
                vertices = vertices_;
                --data.n;
                for (int i = 2; i < data.n; ++i) {
                    vertices[i].setLabel(new Integer(i - 1).toString());
                    vertices[i].number = i;
                }
                drawGraph();
            } else if (source == addVertexItem) {
                Vertex[] vertices_ = new Vertex[data.n + 1];
                Edge[][] edge_ = new Edge[data.n + 1][data.n + 1];
                Edge[][] reverseEdge_ = new Edge[data.n + 1][data.n + 1];
                data.dist = new long[data.n + 1];
                data.phi = new long[data.n + 1];
                data.prev = new Edge[data.n + 1];
                data.used = new boolean[data.n + 1];

                for (int i = 0; i < data.n; ++i) {
                    vertices_[i] = vertices[i];
                }
                for (int i = 0; i < data.n; ++i) {
                    for (int j = 0; j < data.n; ++j) {
                        edge_[i][j] = data.edge[i][j];
                        reverseEdge_[i][j] = data.reverseEdge[i][j];
                    }
                }
                vertices_[data.n] = new Vertex((float) curPointForPopup.x / clientPane.getSize().width,
                        (float) (curPointForPopup.y - headerHeight) / (clientPane.getSize().height - headerHeight),
                        new Integer(data.n - 1).toString(), data.n,
                        new Ellipse(styleSet), new ExtendedVertex(styleSet));
                data.edge = edge_;
                data.reverseEdge = reverseEdge_;
                vertices = vertices_;
                ++data.n;
                drawGraph();
            }
        }

        private Point findPointOfEvent(MouseEvent event) {
            Dimension size = clientPane.getSize();
            size.setSize(size.width, size.height - headerHeight);
            for (int i = 0; i < data.n; ++i) {
                for (int j = 0; j < data.n; ++j) {
                    if (data.edge[i][j] == null) {
                        continue;
                    }

                    float dx = (vertices[j].x - vertices[i].x) * size.width;
                    float dy = (vertices[j].y - vertices[i].y) * size.height;
                    float d = (float) Math.sqrt(dx * dx + dy * dy);
                    float cos = dx / d;
                    float sin = dy / d;
                    float radius;
                    if (data.extendedDraw) {
                        radius = (float) (minSize * config.getDouble("extended-radius"));
                    } else {
                        radius = (float) (minSize * config.getDouble("ordinary-radius"));
                    }
                    float dvector_x = cos * radius;
                    float dvector_y = sin * radius;
                    float startx = vertices[i].x * size.width + dvector_x;
                    float starty = vertices[i].y * size.height + dvector_y + headerHeight;                    
                    float finishx = vertices[j].x * size.width - dvector_x;
                    float finishy = vertices[j].y * size.height - dvector_y + headerHeight;

                    float costCoordinate = (float) config.getDouble("cost-coordinate");
                    float costCenterX = startx * (1 - costCoordinate) + finishx * costCoordinate;
                    float costCenterY = starty * (1 - costCoordinate) + finishy * costCoordinate;

                    float costRadius = (float) config.getDouble("cost-diameter") * minSize / 2.0f;

                    if (Math.pow(costCenterX - event.getX(), 2) +
                            Math.pow(costCenterY - event.getY(), 2) <= Math.pow(costRadius, 2)) {
                        curEdge = data.edge[i][j];
                        field = 1;
                        return new Point(Math.round(costCenterX), Math.round(costCenterY));
                    }


                    float capacityCoordinate = (float) config.getDouble("capacity-coordinate");
                    float capacityCenterX = startx * (1 - capacityCoordinate) + finishx * capacityCoordinate;
                    float capacityCenterY = starty * (1 - capacityCoordinate) + finishy * capacityCoordinate;
                    float capacityHRadius = (float) config.getDouble("capacity-width") * minSize / 2.0f;
                    float capacityVRadius = (float) config.getDouble("capacity-height") * minSize / 2.0f;

                    if (Math.pow((capacityCenterX - event.getX()) / capacityHRadius, 2) +
                            Math.pow((capacityCenterY - event.getY()) / capacityVRadius, 2) <= 1) {
                        curEdge = data.edge[i][j];
                        field = 2;
                        return new Point(Math.round(capacityCenterX), Math.round(capacityCenterY));
                    }
                }
            }
            return null;
        }

        private Vertex findVertexOfEvent(MouseEvent event) {
            Dimension size = event.getComponent().getSize();
            size.setSize(size.width, size.height - headerHeight);
            double radius = config.getDouble("ordinary-radius") * minSize;
            for (int i = 0; i < data.n; ++i) {
                if (Math.pow(vertices[i].x * size.width - event.getX(), 2) +
                        Math.pow(vertices[i].y * size.height - event.getY() + headerHeight, 2) <= radius * radius) {
                    return vertices[i];
                }
            }
            return null;
        }

        public void mouseMoved(MouseEvent event) {
//            System.out.println("  Moved");
            if (curVertex != null) {
                Point evPoint = event.getPoint();
                evPoint.y -= headerHeight;
                pointer.to = evPoint;
                drawGraph();
            }
        }

        public void mouseExited(MouseEvent event) {
//            System.out.println("Exited");
            if (pointer != null) {
                clientPane.remove(pointer);
                curVertex = null;
                pointer = null;
                drawGraph();
            }
        }

        public void mouseEvent(MouseEvent event) {
//            System.out.println("Event");
        }

        public void mouseEntered(MouseEvent event) {
//            System.out.println("Entered");
        }

        public void mouseReleased(MouseEvent event) {
//            System.out.println("Released");
            if (event.getClickCount() > 0) {
                return;
            }
            curVertex = null;
        }

        public void mouseDragged(MouseEvent event) {
//            System.out.println("Dragged");
            if (curVertex != null) {
                
                curVertex.x = (float) event.getX() / event.getComponent().getSize().width;
                if (curVertex.x < radX) {
                    curVertex.x = radX;
                }
                if (curVertex.x > 1 - radX) {
                    curVertex.x = 1 - radX;
                }

                curVertex.y = (float) (event.getY() - headerHeight) / (event.getComponent().getSize().height - headerHeight);
                if (curVertex.y < radY) {
                    curVertex.y = radY;
                }
                if (curVertex.y > 1 - radY) {
                    curVertex.y = 1 - radY;
                }
                drawGraph();
            }
        }

        public void mousePressed(MouseEvent event) {
//            System.out.println("Pressed");
            if (event.getModifiers() - MouseEvent.BUTTON1_MASK != 0) {
                return;
            }
            if (curVertex == null) {
                curVertex = findVertexOfEvent(event);
            } else {
                Vertex endVertex = findVertexOfEvent(event);
                if (endVertex != null) {
                    if (endVertex != curVertex) {
                        if (data.edge[curVertex.number][endVertex.number] != null) {
                            data.edge[curVertex.number][endVertex.number] = null;
                            data.reverseEdge[endVertex.number][curVertex.number] = null;
                        }
                        if (data.edge[endVertex.number][curVertex.number] != null) {
                            data.edge[endVertex.number][curVertex.number] = null;
                            data.reverseEdge[curVertex.number][endVertex.number] = null;
                        }

                        long cost = config.getInteger("min-cost") +
                                Math.round(Math.random() * (config.getInteger("max-cost") -
                                config.getInteger("min-cost")));
                        long capacity = config.getInteger("min-capacity") +
                                Math.round(Math.random() * (config.getInteger("max-capacity") -
                                config.getInteger("min-capacity")));

                        data.edge[curVertex.number][endVertex.number] = new Edge(curVertex.number,
                                endVertex.number,
                                capacity, cost, 0, false);
                        data.reverseEdge[endVertex.number][curVertex.number] = new Edge(endVertex.number,
                                curVertex.number,
                                0, -cost, 0, true);
                    }
                }

                clientPane.remove(pointer);
                pointer = null;
                curVertex = null;

                drawGraph();
            }
        }

        public void mouseClicked(MouseEvent event) {
//            System.out.println("Clicked");
            if ((event.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
                if (leftButton != null) {
                    clientPane.remove(leftButton);
                    clientPane.remove(midButton);
                    clientPane.remove(rightButton);
                    leftButton = null;
                    rightButton = null;
                    midButton = null;
                }
                curVertexForPopup = findVertexOfEvent(event);
                if (curVertexForPopup == null) {
                    curPointForPopup = event.getPoint();
                    removeVertexItem.setEnabled(false);
                    if (data.n - 2 == config.getInteger("max-number-of-vertices")) {
                        addVertexItem.setEnabled(false);
                    } else {
                        addVertexItem.setEnabled(true);
                    }
                } else {
                    if ((curVertexForPopup.number == data.source) || (curVertexForPopup.number == data.sink) ||
                            (data.n - 2 == config.getInteger("min-number-of-vertices"))) {
                        removeVertexItem.setEnabled(false);
                    } else {
                        removeVertexItem.setEnabled(true);
                    }
                    addVertexItem.setEnabled(false);
                }
                if ((event.getY() <= headerHeight + rad) || (event.getX() <= rad)
                        || (event.getY() >= event.getComponent().getSize().height - rad)
                        || (event.getX() >= event.getComponent().getSize().width - rad))
                    
                    return;
                clientPanePopupMenu.show(clientPane, event.getX(), event.getY());
                
                return;
            }
            if ((curVertex != null)) {
                               
                Dimension size = clientPane.getSize();
                size.setSize(size.width, size.height - headerHeight);
                
                Point evPoint = event.getPoint();
                evPoint.y -= headerHeight;
                pointer = new PointerFromVertex(styleSet, curVertex, evPoint);

                pointer.setLocation(0, headerHeight);
                
                pointer.setSize(size);
                clientPane.add(pointer);
            } else {
                Point t = findPointOfEvent(event);
                if (leftButton != null) {
                    clientPane.remove(leftButton);
                    clientPane.remove(midButton);
                    clientPane.remove(rightButton);
                    leftButton = null;
                    rightButton = null;
                    midButton = null;
                }
                if (t != null) {
                    int buttonSize = Math.round((float) config.getDouble("button-size") * minSize);
                    int buttonHeight = Math.round((float) config.getDouble("button-height") * minSize);
                    leftButton = new Button("-");
                    leftButton.setSize(buttonSize, buttonSize);
                    leftButton.setLocation(t.x - 3 * buttonSize / 2, t.y - buttonHeight - buttonSize);
                    leftButton.addActionListener(this);
                    clientPane.add(leftButton);
                    midButton = new Button("x");
                    midButton.setSize(buttonSize, buttonSize);
                    midButton.setLocation(t.x - buttonSize / 2, t.y - buttonHeight - buttonSize);
                    midButton.addActionListener(this);
                    clientPane.add(midButton);
                    rightButton = new Button("+");
                    rightButton.setSize(buttonSize, buttonSize);
                    rightButton.setLocation(t.x + buttonSize / 2, t.y - buttonHeight - buttonSize);
                    rightButton.addActionListener(this);
                    clientPane.add(rightButton);
                    clientPane.repaint();

                }
            }
        }
    }
    private ClientPaneListener clientPaneListener = new ClientPaneListener();

    /**
     * Creates a new Find Maximum visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public MinCostMaxFlowVisualizer(VisualizerParameters parameters) {

        super(parameters);
        auto = new MinCostMaxFlow(locale);
        data = auto.d;
        data.visualizer = this;

        styleSet = ShapeStyle.loadStyleSet(config, "graph");
        
        exampleVertex = new ExampleVertex(styleSet);
        clientPane.add(exampleVertex);

        edges = new Edges(styleSet);
        edges.setLocation(0, 0);
        clientPane.add(edges);

        clientPanePopupMenu = new PopupMenu();

        addVertexItem = new MenuItem(config.getParameter("add-vertex-message"));
        addVertexItem.addActionListener(clientPaneListener);
        removeVertexItem = new MenuItem(config.getParameter("remove-vertex-message"));
        removeVertexItem.addActionListener(clientPaneListener);

        clientPanePopupMenu.add(addVertexItem);
        clientPanePopupMenu.add(removeVertexItem);
        clientPane.add(clientPanePopupMenu);

        costRect = new Rect(styleSet, I18n.message(config.getParameter("cost-message"), new Integer(0)));
        costRect.setStyle(COST_MSG);
        clientPane.add(costRect);

        flowRect = new Rect(styleSet, I18n.message(config.getParameter("flow-message"), new Integer(0)));
        flowRect.setStyle(FLOW_MSG);
        clientPane.add(flowRect);

        int examplesCount = config.getInteger("examples-count");

        examples = new String[examplesCount];
        for (int i = 1; i <= examplesCount; ++i) {
            examples[i - 1] = config.getParameter("example-" + new Integer(i));
        }

        try {
            createGraphFromText(examples[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        createInterface(auto);
        

    }

    private class ExampleListener implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            try {
                stopEditMode();
                createGraphFromText(examples[Integer.parseInt(event.getItem().toString()) - 1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stopEditMode();

        }
    }
    /**
     * Listener for example choice
     */
    private ExampleListener exampleListener = new ExampleListener();

    /**
     * Sets comment string.
     *
     * @param comment New comment.
     */
    public void setComment(String comment) {
        commentPane.setComment(comment);
    }
    private String[] examples;

    /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    public Component createControlsPane()   {

        panel = new Panel(new BorderLayout());
        commentPane = new MyCommentPane(config, "my-comment");
        panel.add(commentPane, BorderLayout.NORTH);

        autoControlsPane = new AutoControlsPane(config, auto, forefather, true);

        panel.add(autoControlsPane, BorderLayout.CENTER);

        Panel bottomPanel = new Panel();

        editModeButton = new MultiButton(config, new String[]{"button-start-edit-mode", "button-stop-edit-mode"});

        editModeButton.addActionListener(this);
        bottomPanel.add(editModeButton);
        editMode = false;

        if (config.getBoolean("button-ShowSaveLoad")) {
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("/*").append(I18n.message(config.getParameter("NumberOfVerticesComment"),
                            new Integer(config.getInteger("min-number-of-vertices")),
                            new Integer(config.getInteger("max-number-of-vertices")))).append("*/\n");

                    buffer.append("NumberOfVertices = ").append(data.n - 2).append("\n");

                    buffer.append("/*").append(I18n.message(config.getParameter("SourceCoordinatesComment"),
                            new Integer(0),
                            new Integer(1))).append("*/\n");

                    buffer.append("SourceCoordinates =\n");

                    buffer.append(vertices[data.source].x).append(" ").append(vertices[data.source].y).append("\n");

                    buffer.append("/*").append(I18n.message(config.getParameter("SinkCoordinatesComment"),
                            new Integer(0),
                            new Integer(1))).append("*/\n");

                    buffer.append("SinkCoordinates =\n");

                    buffer.append(vertices[data.sink].x).append(" ").append(vertices[data.sink].y).append("\n");

                    buffer.append("/*").append(I18n.message(config.getParameter("CoordinatesComment"),
                            new Integer(0),
                            new Integer(1))).append("*/\n");

                    buffer.append("Coordinates = \n");

                    for (int i = 0; i < data.n; ++i) {
                        if ((i != data.source) && (i != data.sink)) {
                            buffer.append(vertices[i].x).append(" ").append(vertices[i].y).append("\n");
                        }
                    }

                    buffer.append("/*").append(I18n.message(config.getParameter("EdgesComment"),
                            new Integer(data.n),
                            new Integer(config.getInteger("min-cost")),
                            new Integer(config.getInteger("max-cost")),
                            new Integer(config.getInteger("min-capacity")),
                            new Integer(config.getInteger("max-capacity")))).append("*/\n");

                    buffer.append("Edges = \n");

                    for (int i = 0; i < data.n; ++i) {
                        for (int j = 0; j < data.n; ++j) {
                            if (data.edge[i][j] != null) {
                                buffer.append(vertices[i].label).append(" ").append(vertices[j].label).append(" ").append(data.edge[i][j].cost).append(" ").append(data.edge[i][j].capacity).append("\n");
                            }
                        }
                    }

                    buffer.append("/* ").append(
                            config.getParameter("StepComment")).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());

                    saveLoadDialog.show(buffer.toString());
                }
            });
        }

        Label examplesLabel = new Label(config.getParameter("examples-message"));

        int examplesCount = config.getInteger("examples-count");

        Choice examplesChoice = new Choice();
        for (int i = 1; i <= examplesCount; ++i) {
            examplesChoice.add(new Integer(i).toString());
        }
        examplesChoice.addItemListener(exampleListener);

        bottomPanel.add(examplesLabel);
        bottomPanel.add(examplesChoice);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        saveLoadDialog = new SaveLoadDialog(config, forefather) {

            public boolean load(String text) throws Exception {
                stopEditMode();
                createGraphFromText(text);
                return true;
            }
        };

        return panel;

    }

    private void createGraphFromText(String text) throws Exception {
        if ((clientPane != null) && (vertices != null)) {
            for (int i = 0; i < data.n; ++i) {
                clientPane.remove(vertices[i].shape);
            }
        }
        SmartTokenizer tokenizer = new SmartTokenizer(text, config);
        tokenizer.expect("NumberOfVertices");
        tokenizer.expect("=");
        data.n = tokenizer.nextInt(
                config.getInteger("min-number-of-vertices"),
                config.getInteger("max-number-of-vertices")) + 2; // Source and sink
        data.edge = new Edge[data.n][data.n];
        data.dist = new long[data.n];
        data.phi = new long[data.n];
        data.prev = new Edge[data.n];
        data.used = new boolean[data.n];
        data.reverseEdge = new Edge[data.n][data.n];
        vertices = new Vertex[data.n];

        tokenizer.expect("SourceCoordinates");
        tokenizer.expect("=");

        vertices[data.source] = new Vertex((float) tokenizer.nextDouble(0, 1),
                (float) tokenizer.nextDouble(0, 1),
                "S",
                data.source,
                new Ellipse(styleSet),
                new ExtendedVertex(styleSet));

        tokenizer.expect("SinkCoordinates");
        tokenizer.expect("=");

        vertices[data.sink] = new Vertex((float) tokenizer.nextDouble(0, 1),
                (float) tokenizer.nextDouble(0, 1),
                "T",
                data.sink,
                new Ellipse(styleSet),
                new ExtendedVertex(styleSet));

        tokenizer.expect("Coordinates");
        tokenizer.expect("=");

        for (int i = 1; i <= data.n - 2; ++i) {
            vertices[i + 1] = new Vertex((float) tokenizer.nextDouble(0, 1),
                    (float) tokenizer.nextDouble(0, 1),
                    new Integer(i).toString(),
                    i + 1,
                    new Ellipse(styleSet),
                    new ExtendedVertex(styleSet));
        }

        tokenizer.expect("Edges");
        tokenizer.expect("=");

        while (true) {
            String s = tokenizer.nextWord();
            if (s.equals("Step")) {
                break;
            }
            int first;
            if (s.equals("S")) {
                first = data.source;
            } else if (s.equals("T")) {
                first = data.sink;
            } else {
                try {
                    tokenizer.pushBack();
                    first = tokenizer.nextInt() + 1;
                } catch (NumberFormatException e) {
                    first = -1;
                }
                if ((first < 2) || (first > data.n - 1)) {
                    throw new Exception(I18n.message(config.getParameter("VertexExpectedException"),
                            new Integer(data.n - 2),
                            s,
                            new Integer(tokenizer.lineno())));
                }

            }
            s = tokenizer.nextWord();
            int second;
            if (s.equals("S")) {
                second = data.source;
            } else if (s.equals("T")) {
                second = data.sink;
            } else {
                try {
                    tokenizer.pushBack();
                    second = tokenizer.nextInt() + 1;
                } catch (NumberFormatException e) {
                    second = -1;
                }

                if ((second < 2) || (second > data.n - 1)) {
                    throw new Exception(I18n.message(config.getParameter("VertexExpectedException"),
                            new Integer(data.n - 2),
                            s,
                            new Integer(tokenizer.lineno())));
                }

            }

            if (first == second) {
                throw new Exception(I18n.message(config.getParameter("EqualVerticesException"),
                        new Integer(tokenizer.lineno())));
            }
            if (data.edge[first][second] != null) {
                throw new Exception(I18n.message(config.getParameter("ParallelEdgesException"),
                        new Integer(tokenizer.lineno())));
            }
            if (data.edge[second][first] != null) {
                throw new Exception(I18n.message(config.getParameter("ReversedEdgesException"),
                        new Integer(tokenizer.lineno())));
            }
            long cost = tokenizer.nextInt(config.getInteger("min-cost"), config.getInteger("max-cost"));
            long capacity = tokenizer.nextInt(config.getInteger("min-capacity"), config.getInteger("max-capacity"));
            data.edge[first][second] = new Edge(first, second, capacity, cost, 0, false);
            data.reverseEdge[second][first] = new Edge(second, first, 0, -cost, 0, true);

        }

        tokenizer.expect("=");
        int step = tokenizer.nextInt();

        tokenizer.expectEOF();

        auto.getController().rewind(step);
    }

    /**
     * Redraws graph
     */
    protected void drawGraph() {
        costRect.setMessage(I18n.message(config.getParameter("cost-message"), new Long(data.cost)));
        costRect.adjustFontSize();

        long flow = 0;
        for (int i = 0; i < data.n; ++i) {
            flow += (data.edge[data.source][i] == null) ? 0 : data.edge[data.source][i].flow;
        }
        flowRect.setMessage(I18n.message(config.getParameter("flow-message"), new Long(flow)));
        flowRect.setFont(costRect.getFont());
        flowRect.adjustSize(costRect.getMessage());

        if (data.extendedDraw) {
            clientPane.add(exampleVertex);
            for (int i = 0; i < data.n; ++i) {
                clientPane.remove(vertices[i].ellipse);
                if (data.dist[i] != -1) {
                    vertices[i].extVertex.setDistance(new Long(data.dist[i]).toString());
                } else {
                    vertices[i].extVertex.setDistance(INFINITY);
                }
                if (data.phi[i] != -1) {
                    vertices[i].extVertex.setPotential(new Long(data.phi[i]).toString());
                } else {
                    vertices[i].extVertex.setPotential(INFINITY);
                }
                vertices[i].shape = vertices[i].extVertex;
                clientPane.add(vertices[i].extVertex);
            }
        } else {
            clientPane.remove(exampleVertex);
            for (int i = 0; i < data.n; ++i) {
                clientPane.remove(vertices[i].extVertex);
                vertices[i].shape = vertices[i].ellipse;
                clientPane.add(vertices[i].ellipse);
            }

        }
        clientPane.remove(edges);
        clientPane.add(edges);
        clientPane.doLayout();
        clientPane.repaint();

    }

      
    /**
     * Invoked when client pane should be layouted
     * 
     * @param clientWidth client pane width
     * @param clientHeight client pane height
     */
    protected void layoutClientPane(int clientWidth, int clientHeight) {

        
        headerHeight = Math.round(clientHeight * (float)config.getDouble("header-height"));
        clientHeight -= headerHeight;
        minSize = Math.min(clientWidth, clientHeight);
        
        
        radX = (float)config.getDouble("extended-radius")*minSize/clientWidth;
        radY = (float)config.getDouble("extended-radius")*minSize/clientHeight;
        rad = Math.round((float)config.getDouble("extended-radius")*minSize);
        
        // Header        
        exampleVertex.setSize(7*clientWidth/16, headerHeight);
        exampleVertex.setLocation(clientWidth - exampleVertex.getSize().width, 0);
        
        costRect.setLocation(Math.round((float) config.getDouble("cost-message-x") * clientWidth),
                Math.round((float) config.getDouble("cost-message-y") * headerHeight));
        costRect.setSize(Math.round((float) config.getDouble("cost-message-width") * clientWidth),
                Math.round((float) config.getDouble("cost-message-height") * headerHeight));
        costRect.adjustFontSize();

        flowRect.setLocation(Math.round((float) config.getDouble("flow-message-x") * clientWidth),
                Math.round((float) config.getDouble("flow-message-y") * headerHeight));
        flowRect.setSize(Math.round((float) config.getDouble("flow-message-width") * clientWidth),
                Math.round((float) config.getDouble("flow-message-height") * headerHeight));
        flowRect.adjustFontSize(costRect.getMessage());
        // End of header

               
        if (clientPaneListener.leftButton != null) {
            clientPane.remove(clientPaneListener.leftButton);
            clientPane.remove(clientPaneListener.rightButton);
            clientPane.remove(clientPaneListener.midButton);
            clientPaneListener.leftButton = null;
            clientPaneListener.midButton = null;
            clientPaneListener.rightButton = null;
        }

        edges.setSize(clientWidth, clientHeight);
        edges.setLocation(0, headerHeight);

        for (int i = 0; i < data.n; ++i) {
            vertices[i].adjustSize();
            Dimension vsize = vertices[i].shape.getSize();
            vertices[i].shape.setLocation(Math.round(clientWidth * vertices[i].x - vsize.width / 2.0f),
                    Math.round(clientHeight * vertices[i].y - vsize.height / 2.0f) + headerHeight);
        }
        

    }

    /**
     * Invoked when an action occurs.
     *
     * @param event event to process.
     */
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == editModeButton) {
            if (editModeButton.getState() == 0) {
                startEditMode();
            } else {
                stopEditMode();
            }
        }
    }

    /**
     * Starts edit mode
     */
    public void startEditMode() {
        if (editMode) {
            return;
        }
        actions.setKeyMapping(KeyEvent.VK_RIGHT, "");
        actions.setKeyMapping(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_MASK, "");
        actions.setKeyMapping(KeyEvent.VK_LEFT, "");
        actions.setKeyMapping(KeyEvent.VK_LEFT, KeyEvent.SHIFT_MASK, "");
        
        editMode = true;
        auto.getController().rewind(0);
        autoControlsPane.setEnabled(false);
        auto.getController().setEnabled(false);        
        autoControlsPane.setEnabled(false);
        autoControlsPane.setVisible(false);
        commentPane.setHeight(commentPane.getHeight() + config.getInteger("my-comment-height-difference"));
        setComment(config.getParameter("edit-comment"));
        clientPane.remove(costRect);
        clientPane.remove(flowRect);
        clientPane.remove(exampleVertex);
        clientPane.addMouseListener(clientPaneListener);
        clientPane.addMouseMotionListener(clientPaneListener);
        editModeButton.setState(1);
    }

    /**
     * Stops edit mode
     */
    public void stopEditMode() {
        if (!editMode) {
            return;
        }
        
        editMode = false;
        clientPane.removeMouseListener(clientPaneListener);
        clientPane.removeMouseMotionListener(clientPaneListener);
        editModeButton.setState(0);
        commentPane.setHeight(commentPane.getHeight() - config.getInteger("my-comment-height-difference"));
        autoControlsPane.setVisible(true);
        autoControlsPane.setEnabled(true);
        auto.getController().setEnabled(true);
        auto.getController().rewind(0);
        
        actions.setKeyMapping(KeyEvent.VK_RIGHT, "next-step");
        actions.setKeyMapping(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_MASK, "next-big-step");
        actions.setKeyMapping(KeyEvent.VK_LEFT, "prev-step");
        actions.setKeyMapping(KeyEvent.VK_LEFT, KeyEvent.SHIFT_MASK, "prev-big-step");        
        
        clientPane.add(costRect);
        clientPane.add(flowRect);
        clientPane.add(exampleVertex);
        panel.repaint();

        drawGraph();
    }
}
