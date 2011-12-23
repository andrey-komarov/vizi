package ru.ifmo.vizi.groebner_basis;

import java.util.Stack;
import java.awt.*;
import ru.ifmo.vizi.base.widgets.*;

public class Action extends Component {

	private Rect gfirst, gsymb, gsecond, gline, gresult;

	public Action(Rect first, Rect symb, Rect second, Rect line, Rect result) {
		gfirst = first;
		gsymb = symb;
		gsecond = second;
		gline = line;
		gresult = result;
	}

	public Point getLocation() {
		return gfirst.getLocation();
	}

	public Dimension getSize() {
		int maxWidth = Math.max(Math.max(gfirst.getSize().width, gsecond.getSize().width), Math.max(gresult.getSize().width, gline.getSize().width)) + gsymb.getSize().width;
		return new Dimension(maxWidth, - gfirst.getLocation().y + gresult.getLocation().y + gresult.getSize().height);
	}

	public void repaint() {
		gfirst.repaint();
		gsymb.repaint();
		gsecond.repaint();
		gline.repaint();
		gresult.repaint();
	}
	
	public void setLocation(int x, int y) {
		int maxWidth = Math.max(Math.max(gfirst.getSize().width, gsecond.getSize().width), gresult.getSize().width);
		gfirst.setLocation(x + gsymb.getSize().width, y);
		gsecond.setLocation(x + gsymb.getSize().width, y + gfirst.getSize().height);
		gsymb.setLocation(x, (gfirst.getLocation().y + gsecond.getLocation().y) / 2);
		gline.setSize(maxWidth, gline.getSize().height);
		gline.setLocation(x + gsymb.getSize().width, y + gfirst.getSize().height + gfirst.getSize().height);
		gresult.setLocation(x + gsymb.getSize().width, y + gfirst.getSize().height + gfirst.getSize().height);
	}

	public void setVisible(boolean v) {
		gfirst.setVisible(v);
		gsymb.setVisible(v);
		gsecond.setVisible(v);
		gline.setVisible(v);
		gresult.setVisible(v);
	}

	public boolean isVisible() {
		return gfirst.isVisible();
	}
}
