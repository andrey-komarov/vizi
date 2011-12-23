package ru.ifmo.vizi.suff_auto;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;
import ru.ifmo.vizi.base.widgets.Shape;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Vector;

/**
 * Suffix Automaton applet.
 * 
 * @author Sergey Poromov
 */
public final class SuffixAutomatonVisualizer extends Base implements
		AdjustmentListener {
	/**
	 * Suffix automaton automata instance.
	 */
	private final SuffixAutomaton auto;

	/**
	 * Suffix automaton automata data.
	 */
	private final SuffixAutomaton.Data data;

	/**
	 * Length of string.
	 */
	private final AdjustablePanel length;

	/**
	 * Array shape style set.
	 */
	private final ShapeStyle[] styleSet;

	/**
	 * Save/load dialog.
	 */
	private SaveLoadDialog saveLoadDialog;

	/**
	 * TextField for string.
	 */
	private TextField pedit;

	/**
	 * Shape for suffix links.
	 */
	private Suffixes suffs;

	/**
	 * Shapes for states.
	 */
	private Vector rects;

	/**
	 * Shape for transitions.
	 */
	private Edges edges;
	/**
	 * Show or not terminate states.
	 */
	private boolean terminateshow = false;

	/**
	 * Array of showing states configuration.
	 */
	private Vector states;

	/**
	 * Creates a new Suffix automaton visualizer.
	 * 
	 * @param parameters
	 *            visualizer parameters.
	 */
	public SuffixAutomatonVisualizer(VisualizerParameters parameters) {
		super(parameters);
		auto = new SuffixAutomaton(locale);
		data = auto.d;
		data.visualizer = this;

		styleSet = ShapeStyle.loadStyleSet(config, "array");

		length = new AdjustablePanel(config, "length");
		length.addAdjustmentListener(this);
		edges = new Edges(styleSet, rects);
		suffs = new Suffixes(styleSet, rects);
		rects = new Vector();
		states = new Vector();

		init();

		createInterface(auto);
	}

	/**
	 * This method initializes automata.
	 */
	private void init() {
		if (pedit != null) {
			pedit.setText(auto.d.s);
		}
		data.l = new int[2 * data.s.length() + 1];
		data.next = new int[2 * data.s.length() + 1][256];
		data.suff = new int[2 * data.s.length() + 1];
		data.upper = new boolean[2 * data.s.length() + 1];
		data.chars = new String[2 * data.s.length() + 1];
		for (int i = 0; i < data.next.length; i++) {
			for (int j = 0; j < data.next[0].length; j++) {
				data.next[i][j] = -1;
			}
		}
	}

	/**
	 * This method creates panel with visualizer controls.
	 * 
	 * @return controls pane.
	 */
	public Component createControlsPane() {
		Container panel = new Panel(new BorderLayout());

		panel.add(new AutoControlsPane(config, auto, forefather, true),
				BorderLayout.CENTER);

		Panel bottomPanel = new Panel();

		bottomPanel.add(new HintedButton(config, "button-random") {
			protected void click() {
				randomize(data.s.length());
			}
		});

		pedit = new TextField();
		pedit.setFont(styleSet[2].getTextFont());
		pedit.setColumns(10);
		pedit.setText(data.s);
		bottomPanel.add(pedit, BorderLayout.SOUTH);

		bottomPanel.add(new HintedButton(config, "button-add") {
			protected void click() {
				if (pedit.getText().length() > length.getMaximum()) {
					pedit.setText(pedit.getText().substring(0,
							length.getMaximum()));
				}
				if (pedit.getText().length() > 0) {
					auto.d.s = pedit.getText();
					init();
					auto.getController().rewind(0);
				}
			}
		});
		if (config.getBoolean("button-ShowSaveLoad")) {
			bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
				protected void click() {
					saveLoadDialog.center();
					StringBuffer buffer = new StringBuffer();
					buffer.append("/* ").append(
							I18n.message(config.getParameter("StringComment"),
									new Integer(length.getMinimum()),
									new Integer(length.getMaximum()))).append(
							" \n");

					char[] s = data.s.toCharArray();

					buffer.append("String = ");
					for (int i = 0; i < s.length; i++) {
						buffer.append(s[i]);
					}

					buffer.append("\n/* ").append(
							config.getParameter("StepComment")).append(" */\n");
					buffer.append("Step = ").append(auto.getStep());
					saveLoadDialog.show(buffer.toString());
				}
			});
		}
		bottomPanel.add(length);
		bottomPanel.add(new HintedButton(config, "button-showterm") {
			protected void click() {
				terminateshow = !terminateshow;
				draw();
			}
		});

		panel.add(bottomPanel, BorderLayout.SOUTH);

		saveLoadDialog = new SaveLoadDialog(config, forefather) {
			public boolean load(String text) throws Exception {
				SmartTokenizer tokenizer = new SmartTokenizer(text, config);

				tokenizer.expect("String");
				tokenizer.expect("=");

				char[] s = tokenizer.nextWord().toCharArray();
				if (s.length < length.getMinimum()
						|| s.length > length.getMaximum()) {
					throw new Exception(config
							.getParameter("OutOfBoundsComment"));
				}
				for (int i = 0; i < s.length; i++) {
					if (s[i] < 'a' || 'z' < s[i]) {
						throw new Exception(I18n.message(config
								.getParameter("WrongCharacterComment"),
								new Integer(i), new Character(s[i])));
					}
				}
				tokenizer.expect("Step");
				tokenizer.expect("=");
				int step = tokenizer.nextInt();
				tokenizer.expectEOF();

				auto.d.s = new String(s);
				init();
				auto.getController().rewind(step);

				return true;
			}
		};
		return panel;
	}

	/**
	 * Invoked on adjustment event.
	 * 
	 * @param event
	 *            event to process.
	 */
	public void adjustmentValueChanged(AdjustmentEvent event) {
		if (event.getSource() == length) {
			randomize(event.getValue());
		}
	}

	/**
	 * This method randomizes string.
	 */
	private void randomize(int size) {
		char[] str = new char[size];
		for (int i = 0; i < size; i++) {
			str[i] = (char) ('a' + Math.round((Math.random() * 25)));
		}
		auto.d.s = new String(str);
		init();
		auto.getController().rewind(0);
	}

	/**
	 * Redrawing everything.
	 */
	public void draw() {
		clientPane.doLayout();
	}

	/**
	 * This method updates style of state.
	 * 
	 * @param i
	 *            number of state.
	 * @param c
	 *            new style.
	 */
	public void updateRect(int i, int c) {
		if (i >= 0 && i < data.nv) {
			states.setSize(data.nv);
			states.setElementAt(new Integer(c), i);
		}
	}

	/**
	 * This method sets style of all states to default.
	 */
	public void clearStates() {
		states.setSize(data.nv);
		for (int i = 0; i < data.nv; i++) {
			states.setElementAt(new Integer(0), i);
		}
	}

	/**
	 * Invoked when client pane shoud be layouted.
	 * 
	 * @param clientWidth
	 *            client pane width.
	 * @param clientHeight
	 *            client pane height.
	 */
	protected void layoutClientPane(int clientWidth, int clientHeight) {
		states.setSize(data.nv);
		for (int i = 0; i < data.nv; i++) {
			if (states.elementAt(i) == null) {
				states.setElementAt(new Integer(0), i);
			}
		}
		int upcount = 0;
		for (int i = 0; i < data.nv; i++) {
			if (data.upper[i]) {
				upcount++;
			}
		}
		rects.removeAllElements();
		int rupy = (int) (clientHeight * 0.15);
		int rhei = (int) (clientHeight * 0.1);
		int rupx = (int) (clientWidth / (3 * upcount + 1));
		int cup = 0;
		int lastup = 0;
		for (int i = 0; i < data.nv; i++) {
			if (data.upper[i]) {
				Vertex r = new Vertex(styleSet, data.chars[i], i, data.l[i]);
				r.setBounds(rupx * (3 * cup + 1), rupy, 2 * rupx, rhei);
				cup++;
				lastup = cup;
				r.setStyle(((Integer) states.elementAt(i)).intValue());
				rects.insertElementAt(r, rects.size());
			} else {
				Vertex r = new Vertex(styleSet, data.chars[i], i, data.l[i]);
				r.setBounds(rupx * (3 * lastup - 3),
						clientHeight - rupy - rhei, 2 * rupx, rhei);
				r.setStyle(((Integer) states.elementAt(i)).intValue());
				rects.insertElementAt(r, rects.size());
			}
		}
		if (terminateshow && data.nv > 0) {
			int p = data.last;
			while (p >= 0) {
				Vertex r = (Vertex) rects.elementAt(p);
				// r.setStyle(r.getStyle() + 3);
				r.setStyle(6);
				p = data.suff[p];
			}
		}
		clientPane.removeAll();
		edges = new Edges(styleSet, rects);
		suffs = new Suffixes(styleSet, rects);
		edges.setBounds(0, 0, clientWidth, clientHeight);
		clientPane.add(edges);
		suffs.setBounds(0, 0, clientWidth, clientHeight);
		clientPane.add(suffs);
		for (int i = 0; i < data.nv; i++) {
			clientPane.add((Component) rects.elementAt(i));
		}
		clientPane.repaint();
	}

	/**
	 * Shape for states of automaton.
	 * 
	 * @author Sergey Poromov
	 */
	private class Vertex extends Shape {
		private Rect[] rects = new Rect[3];

		public Vertex(ShapeStyle[] styleSet, String ch, int n, int l) {
			super(styleSet);
			rects[0] = new Rect(styleSet, "" + ch);
			rects[1] = new Rect(styleSet, "" + n);
			rects[2] = new Rect(styleSet, "" + l);
		}

		protected Dimension fit(Dimension size) {
			Dimension s1 = rects[0].getSize();
			Dimension s2 = rects[0].getSize();
			Dimension s3 = rects[0].getSize();
			return new Dimension(Math.max(s1.width, s2.width + s3.width), Math
					.max(s2.height, s3.height)
					+ s1.height);
		}

		public void paint(Graphics g) {
			Dimension size = getSize();
			rects[0].setSize(size.width, size.height / 2 + 1);
			rects[1].setSize(size.width / 2 + 1, size.height / 2);
			rects[2].setSize(size.width - size.width / 2, size.height / 2);
			for (int i = 0; i < 3; i++) {
				rects[i].adjustFontSize();
			}
			rects[0].paint(g.create(0, 0, size.width, size.height / 2));
			rects[1].paint(g.create(0, size.height / 2, size.width / 2,
					size.height / 2));
			rects[2].paint(g.create(size.width / 2, size.height / 2, size.width
					- size.width / 2, size.height / 2));
		}

		public void setStyle(int style) {
			super.setStyle(style);
			if (rects == null) {
				return;
			}
			for (int i = 0; i < rects.length; i++) {
				if (rects[i] != null) {
					rects[i].setStyle(style);
				}
			}
		}

	}

	/**
	 * Shape for transitions.
	 * 
	 * @author Sergey Poromov
	 */
	private class Edges extends Shape {

		protected Vector rects;

		public Edges(ShapeStyle[] styleSet, Vector rects) {
			super(styleSet);
			this.rects = rects;
		}

		protected Dimension fit(Dimension size) {
			return new Dimension(0, 0);
		}

		protected void paintPointer(Graphics g, Point A, Point B) {
			Point P = new Point();
			double angle;
			double indentAngle = Math.PI / 8;
			double length = 10;

			angle = Math.atan2((double) (A.y - B.y), (double) (A.x - B.x));

			P.x = B.x;
			P.y = B.y;
			P.translate(
					(int) Math.rint(length * Math.cos(angle + indentAngle)),
					(int) Math.rint(length * Math.sin(angle + indentAngle)));
			g.drawLine(P.x, P.y, B.x, B.y);

			P.x = B.x;
			P.y = B.y;
			P.translate(
					(int) Math.rint(length * Math.cos(angle - indentAngle)),
					(int) Math.rint(length * Math.sin(angle - indentAngle)));
			g.drawLine(P.x, P.y, B.x, B.y);
		}

		protected void draw1(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = r1.getLocation().x + r1.getSize().width;
			int y1 = (int) (r1.getLocation().y + config.getDouble("shift")
					* r1.getSize().height);
			int x2 = r2.getLocation().x;
			int y2 = (int) (r2.getLocation().y + config.getDouble("shift")
					* r2.getSize().height);
			g.drawLine(x1, y1, x2, y2);
			paintPointer(g, new Point(x1, y1), new Point(x2, y2));
		}

		protected void draw2(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = (int) (r1.getLocation().x + (1 - config.getDouble("shift"))
					* r1.getSize().width);
			int y1 = r1.getLocation().y + r1.getSize().height;
			int x2 = (int) (r2.getLocation().x + config.getDouble("shift")
					* r2.getSize().width);
			int y2 = r2.getLocation().y;
			paintPointer(g, new Point(x1, y1), new Point(x2, y2));
			g.drawLine(x1, y1, x2, y2);
		}

		protected void draw3(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = (int) (r1.getLocation().x + (1 - config.getDouble("shift"))
					* r1.getSize().width);
			int y1 = r1.getLocation().y;
			int x2 = (int) (r2.getLocation().x + config.getDouble("shift")
					* r2.getSize().width);
			int y2 = r2.getLocation().y + r2.getSize().height;
			paintPointer(g, new Point(x1, y1), new Point(x2, y2));
			g.drawLine(x1, y1, x2, y2);
		}

		protected void draw4(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = (int) (r1.getLocation().x + (1 - config.getDouble("shift"))
					* r1.getSize().width);
			int y1 = r1.getLocation().y;
			int x2 = (int) (r2.getLocation().x + config.getDouble("shift")
					* r2.getSize().width);
			int y2 = r2.getLocation().y;
			double c = 0.1;
			int h = (int) (r1.getLocation().y * (1 - c) * (x2 - x1) / getSize().width);
			int ch = 10;
			g.drawArc(x1, y1 - h - ch, x2 - x1, 2 * h, 0, 180);
			g.drawLine(x1, y1, x1, y1 - ch);
			g.drawLine(x2, y2, x2, y2 - ch);
			paintPointer(g, new Point(x2, y2 - 10), new Point(x2, y2));
		}

		protected void draw5(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = (int) (r1.getLocation().x + (1 - config.getDouble("shift"))
					* r1.getSize().width);
			int y1 = r1.getLocation().y + r1.getSize().height;
			int x2 = (int) (r2.getLocation().x + config.getDouble("shift")
					* r2.getSize().width);
			int y2 = r2.getLocation().y + r2.getSize().height;
			double c = 0.1;
			int h = (int) ((getSize().height - y1) * (1 - c) * (x2 - x1) / getSize().width);
			int ch = 10;
			g.drawArc(x1, y1 - h + ch, x2 - x1, 2 * h, 180, 180);
			g.drawLine(x1, y1, x1, y1 + ch);
			g.drawLine(x2, y2, x2, y2 + ch);
			paintPointer(g, new Point(x2, y2 + 10), new Point(x2, y2));
		}

		public void paint(Graphics g) {
			g.setColor(config.getColor("edges"));
			for (int i = 0; i < data.nv; i++) {
				for (int j = 0; j < data.next[i].length; j++) {
					if (data.next[i][j] != -1) {
						int k = data.next[i][j];
						if (data.upper[i] && data.upper[k]
								&& data.l[k] == data.l[i] + 1) {
							draw1(g, i, k);
							continue;
						}
						if (data.upper[i] && !data.upper[k]) {
							draw2(g, i, k);
							continue;
						}
						if (!data.upper[i] && data.upper[k]) {
							draw3(g, i, k);
							continue;
						}
						if (data.upper[i] && data.upper[k]) {
							draw4(g, i, k);
							continue;
						}
						if (!data.upper[i] && !data.upper[k]) {
							draw5(g, i, k);
						}
					}
				}
			}
		}
	}

	/**
	 * Shape for suffix links.
	 * 
	 * @author Sergey Poromov
	 */
	private class Suffixes extends Edges {
		public Suffixes(ShapeStyle[] styleSet, Vector rects) {
			super(styleSet, rects);
			this.rects = rects;
		}

		protected Dimension fit(Dimension size) {
			return new Dimension(0, 0);
		}

		protected void draw1(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = r1.getLocation().x;
			int y1 = (int) (r1.getLocation().y + (1 - config.getDouble("shift"))
					* r1.getSize().height);
			int x2 = r2.getLocation().x + r2.getSize().width;
			int y2 = (int) (r2.getLocation().y + (1 - config.getDouble("shift"))
					* r2.getSize().height);
			g.drawLine(x1, y1, x2, y2);
			paintPointer(g, new Point(x1, y1), new Point(x2, y2));
		}

		protected void draw4(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = (int) (r1.getLocation().x + (1 - config.getDouble("shift"))
					* r1.getSize().width);
			int y1 = r1.getLocation().y + r1.getSize().height;
			int x2 = (int) (r2.getLocation().x + config.getDouble("shift")
					* r2.getSize().width);
			int y2 = r2.getLocation().y + r2.getSize().height;
			double c = 0.1;
			int h = (int) (r2.getLocation().y * (1 - c) * (x1 - x2) / getSize().width);
			int ch = 10;
			g.drawArc(x2, y2 - h + ch, x1 - x2, 2 * h, 180, 180);
			g.drawLine(x1, y1, x1, y1 + ch);
			g.drawLine(x2, y2, x2, y2 + ch);
			paintPointer(g, new Point(x2, y2 + 10), new Point(x2, y2));
		}

		protected void draw5(Graphics g, int a, int b) {
			Vertex r1 = (Vertex) rects.elementAt(a);
			Vertex r2 = (Vertex) rects.elementAt(b);
			int x1 = (int) (r1.getLocation().x + (1 - config.getDouble("shift"))
					* r1.getSize().width);
			int y1 = r1.getLocation().y + r1.getSize().height;
			int x2 = (int) (r2.getLocation().x + config.getDouble("shift")
					* r2.getSize().width);
			int y2 = r2.getLocation().y;
			double c = 0.1;
			int h = (int) ((getSize().height - y1) * (1 - c) * (x1 - x2) / getSize().width);
			int ch = 10;
			g.drawArc(x2, y2 - h - ch, x1 - x2, 2 * h, 0, 180);
			g.drawLine(x1, y2, x1, y2 - ch);
			g.drawLine(x2, y2, x2, y2 - ch);
			paintPointer(g, new Point(x2, y2 - 10), new Point(x2, y2));
		}

		public void paint(Graphics g) {
			g.setColor(config.getColor("suffix"));
			for (int i = 1; i < data.nv; i++) {
				int k = data.suff[i];
				if (k < 0 || k >= data.nv)
					continue;
				if (data.upper[i] && data.upper[k]
						&& data.l[k] + 1 == data.l[i]) {
					draw1(g, i, k);
					continue;
				}
				if (data.upper[i] && !data.upper[k]) {
					draw2(g, i, k);
					continue;
				}
				if (!data.upper[i] && data.upper[k]) {
					draw3(g, i, k);
					continue;
				}
				if (data.upper[i] && data.upper[k]) {
					draw4(g, i, k);
					continue;
				}
				if (!data.upper[i] && !data.upper[k]) {
					draw5(g, i, k);
				}
			}
		}
	}
}
