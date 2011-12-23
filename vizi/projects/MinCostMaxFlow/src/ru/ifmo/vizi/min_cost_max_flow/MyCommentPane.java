/*
*
* MyCommentPane (resizeable) class for MinCost-MaxFlow visualizer
* Copyied and little modified from vizi 0.4b7
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

import java.awt.*;
import java.util.StringTokenizer;
import ru.ifmo.vizi.base.Configuration;

/**
 * Pane the shows comments. Comments a auto-wrapped.
 * <p>
 * Used configuration parameters:
 * <table border="1">
 *      <tr>
 *          <th>Name</th>
 *          <th>Description</th>
 *          <th>Default</th>
 *      </th>
 *      <tr>
 *          <td>comment-height</td>
 *          <td>Comment pane height</td>
 *          <td>0</td>
 *      </tr>
 *      <tr>
 *          <td>comment-foreground</td>
 *          <td>Comment pane foreground color</td>
 *          <td>0x0000000</td>
 *      </tr>
 *      <tr>
 *          <td>comment-background</td>
 *          <td>Comment pane background color</td>
 *          <td>0xfffffff</td>
 *      </tr>
 *      <tr>
 *          <td>comment-*</td>
 *          <td>Comment pane font configuration. See {@link Configuration#getFont}</td>
 *      </tr>
 * </table>
 *
 * @author  Aleksey Sergushichev
 */
public class MyCommentPane extends Panel {
    /**
     * Comment pane height.
     */
    private int height;
    
    public int getHeight() {
        return height;
    }
    
    /**
     * Set height
     * 
     * @param height new value
     */
    public void setHeight(int height) {
        Rectangle bounds = getBounds();
        bounds.height = height;
        setBounds(bounds);
        
        this.height = height;
    }

    /**
     * Current comment.`
     */
    private String comment;

    /**
     * Draw buffer.
     */
    private Image buffer;

    /**
     * Constructs a new comment pane.
     *
     * @param config configuration source.
     * @param name name of the comment pane.
     */
    public MyCommentPane(Configuration config, String name) {
        setForeground(config.getColor(name + "-foreground"));
        setBackground(config.getColor(name + "-background"));
        setFont(config.getFont(name + "-font"));

        if (config.hasParameter(name + "-lines")) {
            height = (int) (getFontMetrics(getFont()).getHeight() * (config.getInteger(name + "-lines") + 0.3));
        } else {
            height = config.getInteger(name + "-height");
        }
    }

    /**
     * Sets new comment.
     *
     * @param comment comment to show.
     */
    public void setComment(String comment) {
        this.comment = comment;
        this.repaint();
    }

    /**
     * Gets prefferred size of the comment pane.
     *
     * @return preferred size.
     */
    public Dimension getPreferredSize() {
        return new Dimension(100, height);
    }

    /**
     * Gets minimum size of the comment pane.
     *
     * @return minimum size.
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * Gets maximium size of the comment pane.
     *
     * @return maximum size.
     */
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    /**
     * Updates comment pane.
     *
     * @param g graphic to update for.
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Paints comment paint.
     *
     * @param g graphics to paint on.
     */
    public void paint(Graphics g) {
        Dimension size = getSize();
        final int width  = size.width;
        final int height = size.height;

        if (buffer == null) {
            buffer = createImage(width, height);
        }
        Image buffer = this.buffer;
        Graphics bg = buffer.getGraphics();

        super.paint(bg);

        bg.clearRect(0, 0, width - 1, height - 1);
        bg.drawLine(0, 0, width - 1, 0);

        if (comment != null) {
            StringTokenizer tokenizer = new StringTokenizer(comment);
            FontMetrics metrics = bg.getFontMetrics();

            String str = "";
            int x = 10;
            int y = metrics.getHeight();
            if (tokenizer.hasMoreTokens()) {
                str = tokenizer.nextToken();
            }
            while (tokenizer.hasMoreTokens()) {
                final String token = tokenizer.nextToken();
                if (metrics.stringWidth(str + ' ' + token) > width - 2 * x) {
                    bg.drawString(str, x, y);
                    y += metrics.getHeight();
                    str = token;
                } else {
                    str += ' ' + token;
                }
            }
            if (0 != str.length()) {
                bg.drawString(str.trim(), x, y);
            }
        }
        bg.dispose();

        g.drawImage(buffer, 0, 0, null);
    }

    /**
     * Sets new bounds of comment pane.
     *
     * @param x the new <i>x</i>-coordinate.
     * @param y the new <i>y</i>-coordinate/
     * @param width the new <code>width</code>.
     * @param height the new <code>height</code>.
     */
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        buffer = null;
    }
}

