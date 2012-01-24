package ru.ifmo.vizi.FarachColtonBender;

import ru.ifmo.vizi.base.Base;
import ru.ifmo.vizi.base.VisualizerParameters;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.*;

public final class FarachColtonBenderVisualizer extends Base implements
		AdjustmentListener {

	static void debug(String comment, int val) {
		System.err.println(comment + " : " + val);
	}
	
	static void debug(String comment, int[] a) {
		System.err.println(comment + " : " + Arrays.toString(a));
	}
	
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

	private Collection<Rect> treeShown, DFSCells, maximumsShown, tableShown, table2Shown;
	
	private Rect[] depth, index, maximums;

	private Rect[][] table, table2;
	
	private int lastCompleted, lastStackSize;

	private int stackSize;

	private final ShapeStyle[] linearStyleSet;

	private final AdjustablePanel size;

	private final AdjustablePanel leftBorder;

	private final AdjustablePanel rightBorder;

	private int mainWidth, windowWidth, windowHeight, DFSWidth, table2Width;

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
		ShapeStyle.loadStyleSet(config, "line-styleSet");

		lines = new ArrayList<Rect>();
		treeShown = new ArrayList<Rect>();
		DFSCells = new ArrayList<Rect>();
		maximumsShown = new ArrayList<Rect>();
		tableShown = new ArrayList<Rect>();
		table2Shown = new ArrayList<Rect>();
		
		randomize();

		clientPane.setSize(2000, 1200);
		createInterface(auto);
	}

	void initArrays() {
		System.err.println("Init arrays");
		Arrays.fill(data.leftSon, -1);
		Arrays.fill(data.rightSon, -1);
		Arrays.fill(data.index, -1);
		Arrays.fill(data.depth, -1);
		Arrays.fill(data.stack2, -1);
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
		for (int i = 0; i < n; i++) {
			data.array[i] = (int) (Math.random() * 99) + 1;
		}
		clear();
		array = new Rect[n];
		tree = new Rect[n];
		index = new Rect[2 * n + 1];
		depth = new Rect[2 * n + 1];
		data.maximums = new int[(2 * n + data.pieceSize - 2) / data.pieceSize];
		maximums = 	new Rect[data.maximums.length];
		table = new Rect[maximums.length][maximums.length];
		data.table = new int[maximums.length][maximums.length];
		data.table[0] = data.maximums;
		data.leftSon = new int[n];
		data.rightSon = new int[n];
		data.passed = new int[2 * n + 1];
		data.depth = new int[2 * n + 1];
		data.index = new int[2 * n + 1];
		data.first = new int[n];
		data.table2 = new int[maximums.length][data.pieceSize];
		table2 = new Rect[maximums.length][data.pieceSize];
		Arrays.fill(data.maximums, -1);
		Arrays.fill(data.first, -1);
		data.stage1 = false;
		for (int i = 0; i < n; i++) {
			array[i] = new Rect(linearStyleSet);
			tree[i] = new Rect(linearStyleSet);
			array[i].setMessage(String.valueOf(data.array[i]));
			array[i].setSize(mainWidth, mainWidth);
			array[i].adjustFontSize();
			array[i].setLocation(mainWidth * i, 0);
			clientPane.add(array[i]);
		}
		for (int i = 0; i < index.length; i++) {
			index[i] = new Rect(linearStyleSet);
			depth[i] = new Rect(linearStyleSet);
		}

		for (int i = 0; i < maximums.length; i++) {
			maximums[i] = new Rect(linearStyleSet);
		}
		
		table[0] = maximums;
		for (int i = 1; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				table[i][j] = new Rect(linearStyleSet);
			}
		}
		
		for (int i = 0; i < table2.length; i++) {
			for (int j = 0; j < table2[i].length; j++) {
				table2[i][j] = new Rect(linearStyleSet);
			}
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
		layoutCellsForDFS();
		layoutMaximums();
		layoutTable();
		layoutTable2();
	}

	protected Component createControlsPane() {
		Container panel = new Panel(new BorderLayout());

		Panel bottomPanel = new Panel();
		bottomPanel.add(new HintedButton(config, "button-random") {
			private static final long serialVersionUID = 1L;

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
		
		int stackSize = 0;
		for (int i = 0; i < completed; i++) {
			while (stackSize > 0 && data.array[stack[stackSize - 1]] < data.array[i])
				stackSize--;
			stack[stackSize++] = i;			
		}

		for (int i = 0; i < completed; i++) {
			tree[i].setStyle(1);
		}
		for (int i = 0; i < remainInStack; i++) {
			tree[stack[i]].setStyle(2);
		}
		
		for (int i = 0; i < completed; i++) {
			clientPane.add(tree[i]);
			treeShown.add(tree[i]);
			
			if (data.leftSon[i] != -1) {
				Rect l = new Rect(linearStyleSet);				
				l.setBounds(treeX[data.leftSon[i]] + mainWidth / 2, treeY[i] + mainWidth / 2, treeX[i] - treeX[data.leftSon[i]] - mainWidth / 2, 2);
				lines.add(l);
				clientPane.add(l);
				l = new Rect(linearStyleSet);				
				l.setBounds(treeX[data.leftSon[i]] + mainWidth / 2, treeY[i] + mainWidth / 2, 2, treeY[data.leftSon[i]] - treeY[i]);
				lines.add(l);
				clientPane.add(l);				
			}
			
			if (data.rightSon[i] != -1) {
				Rect l = new Rect(linearStyleSet);				
				l.setBounds(treeX[i], treeY[i] + mainWidth / 2, treeX[data.rightSon[i]] - treeX[i] + mainWidth / 2, 2);
				lines.add(l);
				clientPane.add(l);
				l = new Rect(linearStyleSet);				
				l.setBounds(treeX[data.rightSon[i]] + mainWidth / 2, treeY[i] + mainWidth / 2, 2, treeY[data.rightSon[i]] - treeY[i] - mainWidth / 2);
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

		stackSize = 0;
		treeX = new int[n];
		treeY = new int[n];
		int delta = (windowHeight - 2 * mainWidth) / (n + 1);
		for (int i = 0; i < Math.min(tree.length, data.array.length); i++) {
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

	void layoutCellsForDFS() {
		int n = data.index.length;
		DFSWidth = mainWidth / 2;
		for (int i = 0; i < n; i++) {
			index[i].setBounds(i * DFSWidth + (i / 3) * 2, mainWidth, DFSWidth, DFSWidth);
			depth[i].setBounds(i * DFSWidth + (i / 3) * 2, mainWidth + DFSWidth, DFSWidth, DFSWidth);
			index[i].adjustFontSize();
			depth[i].adjustFontSize();
		}
	}
	
	void layoutMaximums() {
		for (int i = 0; i < data.maximums.length; i++) {
			maximums[i].setBounds(mainWidth * i, 3 * mainWidth, mainWidth, mainWidth);
			maximums[i].adjustFontSize();
		}
	}
	
	void layoutTable() {
		for (int i = 1; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				table[i][j].setBounds(mainWidth * j, (3 + i) * mainWidth, mainWidth, mainWidth);
				table[i][j].adjustFontSize();
			}
		}
	}
	
	void layoutTable2() {
		
		table2Width = mainWidth;
		table2Width = Math.min(table2Width, ((windowHeight - 2 * mainWidth)/ maximums.length));
		
		for (int i = 0; i < table2.length; i++) {
			for (int j = 0; j < table2[i].length; j++) {
				table2[i][j].setBounds(windowWidth - (table2[i].length - j) * table2Width, 2 * mainWidth + i * table2Width, table2Width, table2Width);
				table2[i][j].adjustFontSize();
			}
		}
	}
	
	void drawTable2(int x, int y) {
		for (Rect r : table2Shown) {
			clientPane.remove(r);
		}
		table2Shown.clear();
		
		System.err.println("2: " + x + ", " + y);
		
		for (int i = 0; i < x; i++) {
			table2[i][0].setStyle(4);
			for (int j = 0; (i != x - 1 && j < table2[i].length) || (i == x - 1 && j < y); j++) {
				table2[i][j].setMessage("" + data.table2[i][j]);
				table2[i][j].adjustFontSize();
				clientPane.add(table2[i][j]);
				table2Shown.add(table2[i][j]);
			}
		}
	}
	
	void drawTable(int rows, int column, int fromX, int fromY, int fromX2, int fromY2) {
		for (Rect r : tableShown) {
			clientPane.remove(r);
		}
		tableShown.clear();
		
		for (int i = 0; i < Math.min(30, rows - 1); i++) {
			for (int j = 0; j <= table[0].length - (1 << i); j++) {
				table[i][j].setMessage("" + data.table[i][j]);
				table[i][j].adjustFontSize();
				table[i][j].setStyle(1);
				clientPane.add(table[i][j]);
				tableShown.add(table[i][j]);
			}
		}
		
		for (int j = 0; j < column && (j <= table[0].length - (1 << (rows - 1))); j++) {
			System.err.println(data.table[rows - 1][j] + " ");
			table[rows - 1][j].setMessage("" + data.table[rows - 1][j]);
			table[rows - 1][j].adjustFontSize();
			table[rows - 1][j].setStyle(1);
			clientPane.add(table[rows - 1][j]);
			tableShown.add(table[rows - 1][j]);			
		}
		
		if (fromX != -1) {
			table[fromX][fromY].setStyle(3);
			table[fromX2][fromY2].setStyle(2);
			table[rows - 1][column - 1].setStyle(3);
		}
		
	}
	
	void drawMaximums(int cnt) {
		for (Rect r : maximumsShown) {
			clientPane.remove(r);			
		}
		maximumsShown.clear();
		for (int i = 0; i < cnt; i++) {
			maximums[i].setMessage("" + data.maximums[i]);
			maximums[i].adjustFontSize();
			maximums[i].setStyle(1);
			clientPane.add(maximums[i]);
			maximumsShown.add(maximums[i]);
		}
	}
	
	void drawDepth(int pos) {
		for (int i = 0; i < depth.length; i++) {
			depth[i].setStyle(1);
		}
		if (pos != -1 && pos < depth.length) 
			depth[pos].setStyle(3);
	}
	
	void drawCellsForDFS(int count, int colored) {
		System.err.println("draw cells for DFS(" + count + ", " + colored + ")");
		for (Rect r : DFSCells) {
			clientPane.remove(r);
		}
		DFSCells.clear();
		for (int i = 0; i < count; i++) {
			index[i].setMessage("" + (data.index[i] + 1));
			index[i].adjustFontSize();
			depth[i].setMessage("" + data.depth[i]);
			depth[i].adjustFontSize();
			tree[data.index[i]].setStyle(0);
		}
		drawCartesianTree(data.array.length, 0);
		if (colored != -1) {
			tree[colored].setStyle(3);
		}
		for (int i = 0; i < count; i++) {
			DFSCells.add(index[i]);
			DFSCells.add(depth[i]);
			clientPane.add(index[i]);
			clientPane.add(depth[i]);
		}
	}
	
	void redrawIndex() {
		for (int i = 0; i < data.index.length; i++) {
			index[i].setStyle(1);
		}
		for (int i = 0; i < data.first.length; i++) {
			if (data.first[i] != -1)
				index[data.first[i]].setStyle(3);
		}
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
