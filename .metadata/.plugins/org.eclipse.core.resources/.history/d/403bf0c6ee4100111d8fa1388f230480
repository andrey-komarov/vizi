package ru.ifmo.vizi.FarachColtonBender.widgets;

import ru.ifmo.vizi.base.widgets.ShapeStyle;
import ru.ifmo.vizi.base.widgets.Shape;

import java.awt.*;


public class myLine extends Shape {

	int x1, y1, x2, y2;

	public myLine(ShapeStyle[] styleSet) {
		super(styleSet, "");
	}

	public void setPoints(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		int width = x2 > x1 ? x2 - x1 : x1 - x2;
		int height = y2 > y1 ? y2 - y1 : y1 - y2;

		int minx = x1 < x2 ? x1 : x2;
		int miny = y1 < y2 ? y1 : y2;

		setLocation(minx, miny);
		setSize(width, height);

	}

	public void paint(Graphics g) {
		if (x2 < x1)
			g.drawLine(0, 0, getSize().width - 1, getSize().height - 1);
		else
			g.drawLine(0, getSize().height - 1, getSize().width - 1, 0);

		super.paint(g);
	}

	public Dimension fit(Dimension d) {
        return new Dimension(
        	getSize().width,
            getSize().height
        );

	}


}
