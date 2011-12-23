package ru.ifmo.vizi.groebner_basis;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;
import ru.ifmo.vizi.base.widgets.ShapeLook;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Stack;

/**
 * Groebner Basis applet.
 *
 * @author  Alexandrov Anton
 */
public final class GroebnerBasisVisualizer 
    extends Base 
{
    /**
     * Find maximum automata instance.
     */
    private final GroebnerBasis auto;

    /**
     * Groebner basis automata data.
     */
    private final GroebnerBasis.Data data;

	/**
	 * Polynomials styleset.
	 */
	private final ShapeStyle[] styleSet;

	/**
	 * Headers styleset.
	 */
	private final ShapeStyle[] headerStyleSet;

	/**
	 * Line styleset
	 */
	private final ShapeStyle[] lineStyleSet;

	/**
	 * Ideals.
	 */
	private Stack g, g1;

	/**
	 * Labels for ideals.
	 */
	private Stack l, l1;

	/**
	 * First polynimoial in current action.
	 */
	private Polynomial first, first1;

	/**
	 * Second polynomial in current action.
	 */
	private Polynomial second, second1;

	/**
	 * A result of the action with "first" and "second"
	 */
	private Polynomial result, result1;

	/**
	 * A symbol for the current action.
	 */
	private char symb, symb1;

	/**
	 * A rectangle for "first".
	 */
	private Rect gfirst, gfirst1;

	/**
	 * A rectangle for "second".
	 */
	private Rect gsecond, gsecond1;

	/**
	 * A rectangle for "result".
	 */
	private Rect gresult, gresult1;

	/**
	 * A rectangle for "symb".
	 */
	private Rect gsymb, gsymb1;

	/**
	 * A line between the "second" and the "result".
	 */
	private Rect line, line1;

	/**
	 * The actions.
	 */
	public Action action, action1;

	/**
	 * A rectangle with result of calculating
	 */
	private Rect res;

	/*
	 * A rectangle with a reminder
	 */
	private Rect reminder;

	/**
	 * "clientPane" size
	 */
	private int width, height;

	/**
	 * Labels showed on the screen.
	 */
	public Stack labels;

	/**
	 * All using labels.
	 */
	private Stack all;

	/**
	 * Amount of actions on screen.
	 */
	private int actionsCount = 0;

	/**
	 * True if it's first time we're in layoutClientPane.
	 */
	boolean firstly = true;

	/**
	 * Headers for ideals.
	 */
	private Rect header, header1;

	/**
	 * The distances between borders and the text
	 */
	private int indentx = 10, indenty = 10;

	/**
	 * Initial size of clientpane
	 */
	private int w0 = -1, h0 = -1;

	/**
	 * Initial font sizes.
	 */
	private int f, f0, f1;

	/**
	 * Maximal count of rows those can to be situated on clientPane.
	 */
	private final int maxCount;

	/**
	 * A list of polynomials in G1 displaying to choose one to delete.
	 */
	private Choice plist;

	/**
	 * A polynomial to add.
	 */
	private TextField pedit;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * Creates a new Groebner Basis visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public GroebnerBasisVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new GroebnerBasis(locale);
		data = auto.d;
        data.visualizer = this;

		g = new Stack();
		g1 = new Stack();
		l = new Stack();
		l1 = new Stack();
		first = new Polynomial("0");
		second = new Polynomial("0");
		result = new Polynomial("0");
		first1 = new Polynomial("0");
		second1 = new Polynomial("0");
		result1 = new Polynomial("0");
		symb = ' ';
		labels = new Stack();
		all = new Stack();

		maxCount = config.getInteger("maxCount");
        styleSet = ShapeStyle.loadStyleSet(config, "default");
		headerStyleSet = ShapeStyle.loadStyleSet(config, "header");
		f0 = styleSet[0].getTextFont().getSize();
		f1 = headerStyleSet[0].getTextFont().getSize();
		lineStyleSet = ShapeStyle.loadStyleSet(config, "line");
		for (int i = 0; i < data.g1.length; ++i) {
			g1.push(data.g1[i]);
			Rect rect = new Rect(styleSet,((Polynomial)g1.elementAt(i)).toString());
			l1.push(rect);
        	clientPane.add(rect);
		}

		header = new Rect(styleSet, "G2");
		header1 = new Rect(styleSet, "G1");
		clientPane.add(header);
		clientPane.add(header1);

		gfirst = new Rect(styleSet, first.toString());
		clientPane.add(gfirst);
		gfirst.setVisible(false);
		gsecond = new Rect(styleSet, second.toString());
		clientPane.add(gsecond);
		gsecond.setVisible(false);
		gsymb = new Rect(styleSet, "" + symb);
		clientPane.add(gsymb);
		gsymb.setVisible(false);
		line = new Rect(lineStyleSet);
		clientPane.add(line);
		line.setVisible(false);
		gresult = new Rect(styleSet, result.toString());
		clientPane.add(gresult);
		gresult.setVisible(false);
		action = new Action(gfirst, gsymb, gsecond, line, gresult);
		all.push(action);

		gfirst1 = new Rect(styleSet, first1.toString());
		clientPane.add(gfirst1);
		gfirst1.setVisible(false);
		gsecond1 = new Rect(styleSet, second1.toString());
		clientPane.add(gsecond1);
		gsecond1.setVisible(false);
		gsymb1 = new Rect(styleSet, "" + symb1);
		clientPane.add(gsymb1);
		gsymb1.setVisible(false);
		line1 = new Rect(lineStyleSet);
		clientPane.add(line1);
		line1.setVisible(false);
		gresult1 = new Rect(styleSet, result1.toString());
		clientPane.add(gresult1);
		gresult1.setVisible(false);
		action1 = new Action(gfirst1, gsymb1, gsecond1, line1, gresult1);
		all.push(action1);

		res = new Rect(styleSet);
		clientPane.add(res);
		res.setVisible(false);
		all.push(res);
		reminder = new Rect(styleSet);
		clientPane.add(reminder);
		reminder.setVisible(false);
		all.push(reminder);

        createInterface(auto);

		width = clientPane.getSize().width;
		height = clientPane.getSize().height;
    }

    /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */

    public Component createControlsPane() {
        Container panel = new Panel(new BorderLayout());

        panel.add(new AutoControlsPane(config, auto, forefather, true), BorderLayout.NORTH);

		Panel bottomPanel = new Panel();
        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("PolynomialsCountComment"), 
                            new Integer(config.getInteger("MinCount")),
                            new Integer(config.getInteger("MaxCount"))
                        )
                    ).append(" */\n");

					buffer.append("PolynomialsCount = ").append(auto.length(data.g1)).append("\n");

                    buffer.append("/* ").append(
                    	config.getParameter("PolynomialsComment")
                    ).append(" */\n");

					buffer.append("Polynomials = \n");

                    for (int i = 0; i < auto.length(data.g1); i++) {
                       	buffer.append(data.g1[i].toSLString()).append("\n");
                    }

                    buffer.append("/* ").append(
                        config.getParameter("StepComment")
                    ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        }
		plist = new HintedChoice();
		for (int i = 0; i < auto.length(data.g1); ++i) {
			plist.add(data.g1[i].toSLString());
		}
		plist.setFont(styleSet[2].getTextFont());

		pedit = new TextField();
		pedit.setFont(styleSet[2].getTextFont());
		pedit.setColumns(10);

		bottomPanel.add(pedit, BorderLayout.SOUTH);

		bottomPanel.add(new HintedButton(config, "AddButton") {
			protected void click() {
				if (Polynomial.isValid(pedit.getText())) {
					auto.getController().rewind(0);
					data.g1 = auto.add(new Polynomial(pedit.getText()));
					printIdeals(new boolean[] {false, true});
					refreshPlist();
				}
				else {
					setComment(config.getParameter("PE"));
				}
			}
		});

		bottomPanel.add(plist, BorderLayout.SOUTH);

		bottomPanel.add(new HintedButton(config, "DeleteButton") {
			protected void click() {
				if ((data.g1 == null) || (auto.length(data.g1) == 0)) return;
				auto.getController().rewind(0);
				data.g = data.g1;
				data.g1 = auto.subtract(plist.getSelectedIndex());
				printIdeals(new boolean[] {false, true});
				refreshPlist();
			}
		});

		panel.add(bottomPanel, BorderLayout.SOUTH);

        saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
				tokenizer.wordChars(42, 46);
				tokenizer.wordChars(94,94);
				
				tokenizer.expect("PolynomialsCount");
				tokenizer.expect("=");
				data.n = tokenizer.nextInt(
					config.getInteger("MinCount"),
					config.getInteger("MaxCount")
				);
				data.g1 = new Polynomial[data.n];
				
				tokenizer.expect("Polynomials");
				tokenizer.expect("=");
                for (int i = 0; i < data.n; i++) {
					String ss = new String(tokenizer.nextWord());
                    data.g1[i] = new Polynomial(ss);
                }

                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

                auto.getController().rewind(step);

                return true;
            }
        };

        return panel;
    }

	/**
	 * Sets new size of font to rect.
	 */
	private void setFontSize(int w, int h, Rect r, int font0) {
		ShapeLook sl = r.getLook();
		Font font = sl.getTextFont(r.getStyleSet()[r.getStyle()]);
		font = new Font(font.getName(), font.getStyle(), (int)Math.round(font0 * Math.min((double)w / w0, (double)h / h0)));
		ShapeStyle[] newStyleSet = new ShapeStyle[r.getStyleSet().length];
		for (int i = 0; i < r.getStyleSet().length; ++i) {
			ShapeStyle st1 = r.getStyleSet()[i];
			newStyleSet[i] = new ShapeStyle(st1.getTextColor(), font, st1.getTextAlign(), st1.getMessageAlign(), st1.getPadding(), st1.getBorderColor(), st1.getBorderStatus(), st1.getFillColor(), st1.getFillStatus(), st1.getAspect(), st1.getAspectStatus());
		}
		r.setStyleSet(newStyleSet);
		r.adjustSize();
	}

	/**
	 * Refreshs plist.
	 */
	public void refreshPlist() {
		plist.removeAll();
		for (int i = 0; i < auto.length(data.g1); ++i) {
			plist.add(data.g1[i].toSLString());
		}
		plist.repaint();
	}

    /**
     * Invoked when client pane shoud be layouted.
a     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
	public void layoutClientPane(int w, int h) {
		if (w * h == 0) return;
		width = w;
		height = h;
		if (firstly) {
			w0 = w;
			h0 = h;
			firstly = false;
		}

		// adjusting ideals
		f = f0;
	   	if (Math.max(l1.size(), l.size()) > maxCount) f = f0 * maxCount / Math.max(l1.size(), l.size());
		
		if ((l != null) && (l.size() > 0) && (((Rect)l.elementAt(0)).isVisible())) {
			setFontSize(w, h, header, f1);
		}
		header.setLocation(indentx + (w - 2 * indentx) / 3, indenty);
		for (int i = 0; i < l.size(); ++i) {
			if (!((Rect)l.elementAt(i)).isVisible()) break;
			setFontSize(w, h, (Rect)l.elementAt(i), f);
			((Rect)l.elementAt(i)).setLocation(indentx + (w - 2 * indentx) / 3, header.getLocation().y + header.getSize().height + i * ((Rect)l.elementAt(i)).getSize().height);
		}

		if ((l1 != null) && (l1.size() > 0) && (((Rect)l1.elementAt(0)).isVisible())) {
			setFontSize(w, h, header1, f1);
		}
		header1.setLocation(indentx, indenty);
		for (int i = 0; i < l1.size(); ++i) {
			if (!((Rect)l1.elementAt(i)).isVisible()) break;
			setFontSize(w, h, (Rect)l1.elementAt(i), f);
			((Rect)l1.elementAt(i)).setLocation(indentx, header1.getLocation().y + header1.getSize().width + i * ((Rect)l1.elementAt(i)).getSize().height);
		}

		// adjusting action
/*
		setFontSize(w, h, gfirst, f0);
		setFontSize(w, h, gsecond, f0);
		setFontSize(w, h, gresult, f0);
		setFontSize(w, h, gsymb, f0);
		gfirst.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3, h * indenty / h0);
		gsecond.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3,  gfirst.getLocation().y + gfirst.getSize().height);
		gresult.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3, gsecond.getLocation().y + gsecond.getSize().height);
		gsymb.setLocation(Math.min(gfirst.getLocation().x, gsecond.getLocation().x) - gsymb.getSize().width, (gfirst.getLocation().y + gsecond.getLocation().y) / 2);
*/
/*		line.setSize(l, (int) (2 * Math.min((double)w / w0, (double)(h) / h0)));
		line.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3, gsecond.getLocation().y + gsecond.getSize().height);
*/
		// adjusting action1
/*		setFontSize(w, h, gfirst1, f0);
		setFontSize(w, h, gsecond1, f0);
		setFontSize(w, h, gresult1, f0);
		setFontSize(w, h, gsymb1, f0);
		gfirst1.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3, h * indenty / h0);
		gsecond1.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3,  gfirst1.getLocation().y + gfirst1.getSize().height);
		gresult1.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3, gsecond1.getLocation().y + gsecond1.getSize().height);
		gsymb1.setLocation(Math.min(gfirst1.getLocation().x, gsecond1.getLocation().x) - gsymb1.getSize().width, (gfirst1.getLocation().y + gsecond1.getLocation().y) / 2);
*/
		int l1 = Math.max(Math.max(gfirst1.getSize().width, gsecond1.getSize().width), gresult1.getSize().width);
/*		line1.setSize(l1, (int) (2 * Math.min((double)w / w0, (double)(h) / h0)));
		line1.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3, gsecond1.getLocation().y + gsecond1.getSize().height);

		// adjusting reminder
		setFontSize(w, h, reminder, f0);
		reminder.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx/ w0) / 3, h - h * indenty / h0 - reminder.getSize().height);

		// adjusting "res"
		setFontSize(w, h, res, f0);
		res.setLocation(w * indentx / w0 + 2 * (w - 2 * w * indentx/ w0) / 3, h * indenty / h0);
/*
		gfirst.repaint();
		gsecond.repaint();
		gresult.repaint();
		gsymb.repaint();
		line.repaint();
		header.repaint();
		header1.repaint();
		reminder.repaint();
		res.repaint();
*/
		redraw();
	}

	/**
	 * Shows and hides "res".
	 */
	public void showResult(boolean visible) {
		res.setVisible(visible);
		res.repaint();
	}

	/**
	 * Shows and hides "reminder".
	 */
	public void showReminder(boolean visible) {
		reminder.setVisible(visible);
		reminder.repaint();
	}

	/**
	 * Hides all except ideals.
	 */
	public Stack hide() {
//		showReminder(false);
//		showResult(false);
//		showAction(false);
		for (int i = 0; i < labels.size(); ++i) {
			((Component)labels.elementAt(i)).setVisible(false);
			((Component)labels.elementAt(i)).repaint();
		}
		actionsCount = 0;
		return new Stack();
	}

	public Stack hideAction() {
		Stack s = new Stack();
		for (int i = 0; i < labels.size(); ++i) {
			if ((labels.elementAt(i) != action) && (labels.elementAt(i) != action1)) {
				s.push(labels.elementAt(i));
			}
		}
		action.setVisible(false);
		action1.setVisible(false);
		action.repaint();
		action1.repaint();
		actionsCount = 0;
		return s;
	}

	/**
	 * Hides ideals.
	 */
	public void hideIdeals(int[] p, int[] c) {
		if (p == null) return;
		if (p.length == 2) {
			printIdeals(new boolean[] {false, false}, c);
			return;
		}
		if (p[0] == 0) {
			printIdeals(new boolean[] {false, true}, c);
			return;
		}
		printIdeals(new boolean[] {true, false}, c);
	}

	/**
	 * Prints ideals.
	 */
	public void printIdeals(boolean b[]) {
		int[] r = new int[0];
		printIdeals(b, r);
	}

    /**
     * Prints ideals.
     */

	public void printIdealsWithPolynomials(boolean[] b, Polynomial[] p) {
		Stack q = new Stack();
		for (int i = 0; i < p.length; ++i) {
			for (int j = 0; j < g1.size(); ++j) {
				if (((Polynomial)g1.elementAt(j)).equals(p[i])) {
					q.push(new Integer(i));
					break;
				}
			}
		}
		int[] m = new int[q.size()];
		for (int i = 0; i < m.length; ++i) {
			m[i] = ((Integer)q.elementAt(i)).intValue();
		}
		printIdeals(b, m);
	}

	/**
	 * Prints ideals.
	 */
	public void printIdeals(boolean[] b, int[] m) {
		for (int i = 0; i < l1.size(); ++i) {
			clientPane.remove((Rect)l1.elementAt(i));
		}
		for (int i = 0; i < l.size(); ++i) {
			clientPane.remove((Rect)l.elementAt(i));
		}
		header.setVisible(false);
		header1.setVisible(false);
		update(true);

		g = new Stack();
		g1 = new Stack();
		l = new Stack();
		l1 = new Stack();
		
		if (b[1]) {
			header1.setVisible(true);
			boolean[] t = new boolean[data.g1.length];
			g1 = new Stack();
			l1 = new Stack();

			for (int i = 0; i < data.g1.length; ++i) {
				if ((data.g1[i] != null) && (Math.abs(data.g1[i].m[0].c) > 1e-9)) {
					g1.push(data.g1[i]);
					Rect rect = new Rect(styleSet,((Polynomial)data.g1[i]).toString());
					l1.push(rect);
        			clientPane.add(rect);
					t[i] = true;
				}
				else {
					t[i] = false;
				}
			}
	
			if (m != null) {
				for (int i = 0; i < m.length; ++i) {
					if ((m[i] < 0) || (!t[m[i]])) continue;
					((Rect)(l1.elementAt(m[i]))).setStyle(1);
				}
			}
		}

		if (b[0]) {
			header.setVisible(true);
			boolean[] t = new boolean[data.g.length];
			g = new Stack();
			l = new Stack();

			for (int i = 0; i < data.g.length; ++i) {
				if ((data.g[i] != null) && (Math.abs(data.g[i].m[0].c) > 1e-9)) {
					g.push(data.g[i]);
					Rect rect = new Rect(styleSet,((Polynomial)data.g[i]).toString());
					l.push(rect);
					clientPane.add(rect);
					t[i] = true;
				}
				else {
					t[i] = false;
				}
			}
	
			if (m != null) {
				for (int i = 0; i < m.length; ++i) {
					if ((m[i] >= 0) || (!t[Math.abs(m[i]) - 1])) continue;
					((Rect)(l.elementAt(Math.abs(m[i]) - 1))).setStyle(1);
				}
			}
		}

		layoutClientPane(width, height);
	}

	/**
	 * Prints action
	 */

	public Stack printAction(char s, Polynomial a, Polynomial b, Polynomial c, int maxCount) {
		if (s == '/') {
			s = '\u00F7';
		}
		if (s == '*') {
			s = '\u00D7';
		}
		if ((actionsCount < maxCount) && (actionsCount == 1)) {
			first1 = new Polynomial(a);
			second1 = new Polynomial(b);
			result1 = new Polynomial(c);
			symb1 = s;

			gfirst1.setMessage(first1.toString());
			gsecond1.setMessage(second1.toString());
			gresult1.setMessage(result1.toString());
			gsymb1.setMessage("" + symb1);

			Stack t = new Stack();
			t = (Stack)labels.clone();
			if (!action1.isVisible()) {
				t.push(action1);
			}
			return t;
		}
		first = new Polynomial(a);
		second = new Polynomial(b);
		result = new Polynomial(c);
		symb = s;

		gfirst.setMessage(first.toString());
		gsecond.setMessage(second.toString());
		gresult.setMessage(result.toString());
		gsymb.setMessage("" + symb);

//		showAction(true);
//		layoutClientPane(width, height);
		Stack t = new Stack();
		t = (Stack)labels.clone();
		if (!action.isVisible()) {
			t.push(action);
		}
		actionsCount = 1;
		return t;
	}

	public Stack printReminder(Polynomial a) {
		reminder.setMessage("Rem = " + a.toString());
//		showReminder(true);
//		layoutClientPane(width, height);
		Stack s = new Stack();
		s = (Stack)labels.clone();
		if (!reminder.isVisible()) {
			s.push(reminder);
		}
		return s;
	}

	public Stack printResult() {
		res.setMessage(data.comment);
//		showResult(true);
//		layoutClientPane(width, height);
		Stack t = new Stack();
		t = (Stack)labels.clone();
		if (!res.isVisible()) {
			t.push(res);
		}
		return t;
	}

	public void redraw() {
		res.setMessage(data.comment);
		for (int i = 0; i < clientPane.getComponentCount(); ++i) {
			boolean del = true;
			for (int j = 0; del && (j < l.size()); ++j) {
				if (clientPane.getComponent(i) == l.elementAt(j)) del = false;
			}
			for (int j = 0; del && (j < l1.size()); ++j) {
				if (clientPane.getComponent(i) == l1.elementAt(j)) del = false;
			}
			del &= (clientPane.getComponent(i) != header1) && (clientPane.getComponent(i) != header);
			if (del) {
				clientPane.getComponent(i).setVisible(false);
				clientPane.getComponent(i).repaint();
			}
		}
		int w = width, h = height;
		line.setSize(Math.max(Math.max(gfirst.getSize().width, gsecond.getSize().width), gresult.getSize().width), line.getSize().height);
		if ((labels == null) || (labels.size() == 0)) return;
		int l1 = Math.max(Math.max(gfirst1.getSize().width, gsecond1.getSize().width), gresult1.getSize().width);
		line1.setSize(l1, (int) (2 * Math.min((double)w / w0, (double)(h) / h0)));
		int l = Math.max(Math.max(gfirst.getSize().width, gsecond.getSize().width), gresult.getSize().width);
		line.setSize(l, (int) (2 * Math.min((double)w / w0, (double)(h) / h0)));
		int dy = h * indenty / h0;
		int dx = w * indentx / w0 + 2 * (w - 2 * w * indentx / w0) / 3;
		int len = labels.size();
		for (int i = 0; i < len; ++i) {
			if ((labels.elementAt(i) != action) && (labels.elementAt(i) != action1)) {
				setFontSize(w, h, (Rect)labels.elementAt(i), f0);
			}
			else {
				if (labels.elementAt(i) == action) {
					setFontSize(w, h, gfirst, f0);
					setFontSize(w, h, gsymb, f0);
					setFontSize(w, h, gsecond, f0);
					setFontSize(w, h, gresult, f0);
				}
				else {
					setFontSize(w, h, gfirst1, f0);
					setFontSize(w, h, gsymb1, f0);
					setFontSize(w, h, gsecond1, f0);
					setFontSize(w, h, gresult1, f0);
				}
			}
			((Component)labels.elementAt(i)).setLocation(dx, dy);
			dy = ((Component)labels.elementAt(i)).getLocation().y + ((Component)labels.elementAt(i)).getSize().height;
			((Component)labels.elementAt(i)).setVisible(true);
			((Component)labels.elementAt(i)).repaint();
		}
	}
}

