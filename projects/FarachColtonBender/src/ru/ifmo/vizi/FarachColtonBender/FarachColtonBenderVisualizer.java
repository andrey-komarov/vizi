package ru.ifmo.vizi.FarachColtonBender;

import ru.ifmo.vizi.FarachColtonBender.widgets.myLine;
import ru.ifmo.vizi.base.Base;
import ru.ifmo.vizi.base.VisualizerParameters;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.*;

public final class FarachColtonBenderVisualizer extends Base implements
		AdjustmentListener {

	static String treeAsString(int[] a) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < a.length; i++) {
			sb.append("(" + (i + 1) + ", " + a[i] + ")"
					+ ((i == a.length - 1) ? "" : ", "));
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Find maximum automata instance.
	 */
	private final FarachColtonBender auto;

	/**
	 * Find maximum automata data.
	 */
	private final FarachColtonBender.Data data;

	private Rect[] array;

	private Rect[] tree;

	private int[] treeX, treeY;

	private Collection<Rect> lines;

	private Collection<Rect> treeShown;
	
	private int lastCompleted, lastStackSize;

	private int stackSize, completed;

	private final ShapeStyle[] linearStyleSet, lineStyleSet;

	private final AdjustablePanel size;

	private final AdjustablePanel leftBorder;

	private final AdjustablePanel rightBorder;

	private int mainWidth, windowWidth, windowHeight;

	/**
	 * Creates a new Find Maximum visualizer.
	 * 
	 * @param parameters
	 *            visualizer parameters.
	 */
	public FarachColtonBenderVisualizer(VisualizerParameters parameters) {
		super(parameters);
		auto = new FarachColtonBender(locale);
		data = auto.d;
		data.visualizer = this;

		size = new AdjustablePanel(config, "array-size");
		size.addAdjustmentListener(this);

		leftBorder = new AdjustablePanel(config, "left-border");
		leftBorder.addAdjustmentListener(this);

		rightBorder = new AdjustablePanel(config, "right-border");
		rightBorder.addAdjustmentListener(this);

		linearStyleSet = ShapeStyle.loadStyleSet(config, "linear-styleSet");
		lineStyleSet = ShapeStyle.loadStyleSet(config, "line-styleSet");

		lines = new ArrayList<Rect>();
		treeShown = new ArrayList<Rect>();
		
		randomize();

		clientPane.setSize(2000, 1200);
		createInterface(auto);
	}

	void drawMainArray() {
		for (int i = 0; i < array.length; i++) {
			clientPane.add(array[i]);
		}
	}
	
	void redraw() {
		clear();
		drawMainArray();
		drawCartesianTree(lastCompleted, lastStackSize);
	}
	
	void clear() {
		System.err.println("Clearing...");
		clientPane.removeAll();
		layoutClientPane(windowWidth, windowHeight);
		if (array == null)
			return;
		for (int i = 0; i < data.array.length; i++) {
			if (array[i] != null)
				clientPane.remove(array[i]);
		}
		
		for (int i = 0; i < tree.length; i++) {
			if (tree[i] != null)
				clientPane.remove(tree[i]);
		}

		
		for (Rect r : treeShown)
			clientPane.remove(r);
		for (Rect l : lines)
			clientPane.remove(l);
		
	}

	public void adjustmentValueChanged(AdjustmentEvent t) {
		if (t.getSource() == size) {
			System.err.println("========== Size changed ===============");
			clear();
			auto.getController().doRestart();
			int n = t.getValue();
			System.err.println("	... to " + n);
			data.array = new int[n];
			array = new Rect[n];
			leftBorder.setMaximum(n);
			leftBorder.setValue(1);
			rightBorder.setMaximum(n);
			rightBorder.setValue(n);
			randomize();
		}
	}

	public void randomize() {
		int n = data.array.length;
		System.err.println("randomize " + n);
		Random rnd = new Random();
		for (int i = 0; i < n; i++) {
			data.array[i] = (int) (Math.random() * 99) + 1;
		}
		clear();
		array = new Rect[n];
		tree = new Rect[n];
		for (int i = 0; i < n; i++) {
			array[i] = new Rect(linearStyleSet);
			tree[i] = new Rect(linearStyleSet);
			array[i].setMessage(String.valueOf(data.array[i]));
			array[i].setSize(mainWidth, mainWidth);
			array[i].adjustFontSize();
			array[i].setLocation(mainWidth * i, 0);
			clientPane.add(array[i]);
		}
		layoutClientPane(windowWidth, windowHeight);
		auto.getController().doRestart();
	}

	void layoutMainArray() {
		System.err.println("Layout main array");
		int n = data.array.length;
		mainWidth = windowWidth / n;
		int height = windowHeight / 10;
		mainWidth = Math.min(mainWidth, height);

		for (int i = 0; i < n; i++) {
			Rect now = array[i];
			if (now != null) {
				now.setSize(mainWidth, mainWidth);
				now.setBounds(i * mainWidth, 0, mainWidth, mainWidth);
				now.adjustFontSize();
			}
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
		if (data.array == null || array == null)
			return;

		windowHeight = clientHeight;
		windowWidth = clientWidth;

		layoutMainArray();
		layoutTree();
	}

	protected Component createControlsPane() {
		Container panel = new Panel(new BorderLayout());

		Panel bottomPanel = new Panel();
		bottomPanel.add(new HintedButton(config, "button-random") {
			protected void click() {
				randomize();
			}
		});
		bottomPanel.add(size);
		bottomPanel.add(leftBorder);
		bottomPanel.add(rightBorder);
		panel.add(bottomPanel, BorderLayout.CENTER);

		panel.add(new AutoControlsPane(config, auto, forefather, false),
				BorderLayout.SOUTH);

		return panel;
	}

	void drawCartesianTree(int completed, int remainInStack) {
		System.err.println("drawCartesianTree(" + completed + ", " + remainInStack + ")");
		lastCompleted = completed;
		lastStackSize = stackSize;
		int n = data.array.length;

		for (Rect l : lines) {
			clientPane.remove(l);
		}
		
		for (Rect r : treeShown) {
			clientPane.remove(r);
		}
		
		lines.clear();

		int[] stack = new int[n];
		int[] leftSon = new int[completed];
		int[] rightSon = new int[completed];
		Arrays.fill(leftSon, -1);
		Arrays.fill(rightSon, -1);
		
		int stackSize = 0;
		for (int i = 0; i < completed; i++) {
			int oldStackSize = stackSize;
			while (stackSize > 0 && data.array[stack[stackSize - 1]] < data.array[i])
				stackSize--;

			if (stackSize != 0) {
				int top = stack[stackSize - 1];
				rightSon[top] = i;
			}
			
			if (oldStackSize != stackSize) {
				int top = stack[stackSize];
				leftSon[i] = top;
			}
			
			stack[stackSize++] = i;			
		}

		for (int i = 0; i < completed; i++) {
			tree[i].setStyle(1);
		}
		for (int i = 0; i < remainInStack; i++) {
			tree[stack[i]].setStyle(2);
		}
		
		System.err.println("X :" + Arrays.toString(treeX));
		System.err.println("Y :" + Arrays.toString(treeY));		
		
		for (int i = 0; i < completed; i++) {
			clientPane.add(tree[i]);
			treeShown.add(tree[i]);
			
			if (leftSon[i] != -1) {
				System.err.println(leftSon[i] + " <- " + i);
				Rect l = new Rect(linearStyleSet);				
				l.setBounds(treeX[leftSon[i]] + mainWidth / 2, treeY[i] + mainWidth / 2, treeX[i] - treeX[leftSon[i]] - mainWidth / 2, 2);
				lines.add(l);
				clientPane.add(l);
				l = new Rect(linearStyleSet);				
				l.setBounds(treeX[leftSon[i]] + mainWidth / 2, treeY[i] + mainWidth / 2, 2, treeY[leftSon[i]] - treeY[i]);
				lines.add(l);
				clientPane.add(l);
				
			}
			
			if (rightSon[i] != -1) {
				System.err.println(i + " -> " + rightSon[i]);
				Rect l = new Rect(linearStyleSet);				
				l.setBounds(treeX[i], treeY[i] + mainWidth / 2, treeX[rightSon[i]] - treeX[i] + mainWidth / 2, 2);
				lines.add(l);
				clientPane.add(l);
				l = new Rect(linearStyleSet);				
				l.setBounds(treeX[rightSon[i]] + mainWidth / 2, treeY[i] + mainWidth / 2, 2, treeY[rightSon[i]] - treeY[i] - mainWidth / 2);
				lines.add(l);
				clientPane.add(l);
				
			}
			
		}

	}

	void layoutTree() {
		System.err.println("layoutTree");
		int n = data.array.length;
		Pair[] pairs = new Pair[n];
		for (int i = 0; i < n; i++)
			pairs[i] = new Pair(data.array[i], i);
		Arrays.sort(pairs);
		int[] height = new int[n];
		for (int i = 0; i < n; i++)
			height[pairs[i].b] = i;

		int[] stack = new int[n];
		stackSize = 0;
		completed = 0;
		treeX = new int[n];
		treeY = new int[n];
		int delta = (windowHeight - 2 * mainWidth) / (n + 1);
		for (int i = 0; i < tree.length; i++) {
			Rect now = tree[i];
			now.setStyle(1);
			now.setMessage("" + data.array[i]);
			now.setSize(mainWidth, mainWidth);
			int h = 2 * mainWidth + delta * height[i];
			now.setBounds(i * mainWidth, h, mainWidth, mainWidth);
			treeX[i] = i * mainWidth;
			treeY[i] = h;
			now.adjustFontSize();
		}
		drawCartesianTree(lastCompleted, lastStackSize);
	}

	static class Pair implements Comparable<Pair> {
		int a, b;

		public Pair(int a, int b) {
			super();
			this.a = a;
			this.b = b;
		}

		public int compareTo(Pair o) {
			if (o.a != a) {
				return o.a - a;
			}
			return o.b - b;
		}
	}

}
