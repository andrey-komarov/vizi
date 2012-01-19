package ru.ifmo.vizi.FarachColtonBender.widgets;

import ru.ifmo.vizi.base.widgets.ShapeStyle;
import ru.ifmo.vizi.base.widgets.Shape;

import java.awt.*;


public class myBracket extends Shape {
	int number;

	public myBracket(ShapeStyle[] styleSet) {
		super(styleSet, "");
	}

	public int getNumb() {
		return number;
	}

	public void setNumb(int number) {
		this.number = number;
	}


	public void setParams(int x, int y, int side, int width) {
		setLocation(x, y);
		setSize(3 * width, side + 2 * width);
	}


	public void paint(Graphics g) {
		look.getBorderColor(style);
		g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);

		g.setColor(look.getFillColor(style));
		g.fillRect(1, 1, getSize().width - 2, getSize().height - 2);
		super.paint(g);
	}

	public Dimension fit(Dimension d) {
        return new Dimension(
        	getSize().width,
            getSize().height
        );

	}


}
