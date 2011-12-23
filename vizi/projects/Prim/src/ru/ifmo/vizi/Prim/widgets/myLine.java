package ru.ifmo.vizi.Prim.widgets;

import ru.ifmo.vizi.base.widgets.ShapeStyle;
import ru.ifmo.vizi.base.widgets.Shape;

import java.awt.*;

public class myLine extends Shape {

	int x1, y1, x2, y2;
	int bold = 1;

	public myLine(ShapeStyle[] styleSet, int bold) {
		super(styleSet, "");
		this.bold = bold;
	}
	
	public void setBold(int bold) { 
		this.bold = bold; 
		setPoints(x1, y1, x2, y2);
	}

	public int getBold() {
		return( bold );
	}

	public void setPoints(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		int width = x2 > x1 ? x2 - x1 : x1 - x2;
		int height = y2 > y1 ? y2 - y1 : y1 - y2;

		width = Math.max(width, bold);
		height = Math.max(height, bold);

		int minx = x1 < x2 ? x1 : x2;
		int miny = y1 < y2 ? y1 : y2;

		setLocation(minx, miny);
		setSize(width, height);

	}

	public void paint(Graphics g) {
		g.setColor(look.getFillColor(style));
		for (int i = 0; i < bold; i++) {
			if (getSize().width < getSize().height) {
				if ((x2 < x1) == (y2 < y1))
					g.drawLine(i, 0, i + getSize().width - bold, getSize().height - bold);
				else
					g.drawLine(i, getSize().height - bold, i + getSize().width - bold, 0);
			} 
			else {
				if ((x2 < x1) == (y2 < y1))
					g.drawLine(0, i, getSize().width - bold, i + getSize().height - bold);
				else
					g.drawLine(0, i + getSize().height - bold, getSize().width - bold, i);

			}
		}
		super.paint(g);
	}

	public Dimension fit(Dimension d) {
        return new Dimension(
        	getSize().width,
            getSize().height
        );

	}


}