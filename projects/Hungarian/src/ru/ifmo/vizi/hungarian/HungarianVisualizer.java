package ru.ifmo.vizi.hungarian;

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

/**
 * Find maximum applet.
 * 
 * @author Georgiy Korneev
 * @version $Id: HungarianVisualizer.java,v 1.8 2006/04/05 12:05:28 geo Exp $
 */
public final class HungarianVisualizer extends Base implements
		AdjustmentListener {
	/**
	 * Find maximum automata instance.
	 */
	private final Hungarian auto;

	/**
	 * Find maximum automata data.
	 */
	private final Hungarian.Data data;

	private Rect[][] matrix, matrixConst;

	private Rect[] rowChange, columnChange, localMinimum;

	private final ShapeStyle[] matrixStyleSet, linearStyleSet;

	private final AdjustablePanel size;
	
	/**
	 * Creates a new Find Maximum visualizer.
	 * 
	 * @param parameters
	 *            visualizer parameters.
	 */
	public HungarianVisualizer(VisualizerParameters parameters) {
		super(parameters);
		System.err.println("!@#$%!!!!");
		auto = new Hungarian(locale);
		data = auto.d;
		data.visualizer = this;
		
		size = new AdjustablePanel(config, "matrix-size");
		size.addAdjustmentListener(this);
		
		//panel.add(new AutoControlsPane(config, auto, forefather, false),
		//		BorderLayout.NORTH);
		
		matrixStyleSet = ShapeStyle.loadStyleSet(config, "matrix-styleSet");
		linearStyleSet = ShapeStyle.loadStyleSet(config, "linear-styleSet");

		matrix = new Rect[3][3];
		matrixConst = new Rect[3][3];
		
		int n = matrix.length;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrix[i][j] = new Rect(matrixStyleSet);
				matrix[i][j].setMessage(String.valueOf(j + 1));
				matrix[i][j].setSize(200 / n, 200 / n);
				matrix[i][j].adjustFontSize();
				matrix[i][j]
						.setLocation(60 + (j) * 200 / n, 60 + (i) * 200 / n);
			}
			
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matrixConst[i][j] = new Rect(matrixStyleSet);
				matrixConst[i][j].setMessage(String.valueOf(j + 1));
				matrixConst[i][j].setSize(200 / n, 200 / n);
				matrixConst[i][j].adjustFontSize();
				matrixConst[i][j]
						.setLocation(360 + (j) * 200 / n, 60 + (i) * 200 / n);
			}
			
		}
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				clientPane.add(matrix[i][j]);
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				clientPane.add(matrixConst[i][j]);
		
		clientPane.setSize(2000, 1200);
		createInterface(auto);
	}

	public void adjustmentValueChanged(AdjustmentEvent t) {
		// TODO Auto-generated method stub
		auto.getController().doRestart();
		int n = t.getValue();
		data.matrix = new int[n][n];
		data.checkedColumns = new boolean[n];
		data.checkedRows = new boolean[n];
		data.columnChange = new int[n];
		data.rowChange = new int[n];
		data.cPartner = new int[n];
		data.partner = new int[n];
		for (int i = 0; i < n; i++)
			data.partner[i] = -1;
		data.used = new boolean[n];
		data.localMinimum = new int[n];
		randomize();
	}

	public void drawMatrix() {
		int[][] a = data.matrix;
		int n = a.length;
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++) {
				clientPane.remove(matrix[i][j]);
				clientPane.remove(matrixConst[i][j]);
			}
		matrix = new Rect[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = new Rect(matrixStyleSet);
				matrix[i][j].setMessage(String.valueOf(a[i][j]));
				matrix[i][j].setSize(200 / n, 200 / n);
				matrix[i][j].adjustFontSize();
				matrix[i][j]
						.setLocation(60 + (j) * 200 / n, 60 + (i) * 200 / n);
				if (data.checkedColumns != null)
				if ((data.checkedRows[i]) && (!data.checkedColumns[j]))
					matrix[i][j].setStyle(1);
				if ((data.localMinimum != null) && (data.br))
				if ((j == data.globalMinimumJ) && (i == data.globalMinimumI))
					matrix[i][j].setStyle(2);
				if ((data.partner != null) && (data.partner[i] == j))
					matrix[i][j].setStyle(3);
			}
		}
		matrixConst = new Rect[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrixConst[i][j] = new Rect(matrixStyleSet);
				matrixConst[i][j].setMessage(String.valueOf(a[i][j] + data.rowChange[i] + data.columnChange[j]));
				matrixConst[i][j].setSize(200 / n, 200 / n);
				matrixConst[i][j].adjustFontSize();
				matrixConst[i][j]
						.setLocation(360 + (j) * 200 / n, 60 + (i) * 200 / n);
				if ((data.partner != null) && (data.partner[i] == j))
					matrixConst[i][j].setStyle(3);
			}
		}
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				clientPane.add(matrix[i][j]);
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				clientPane.add(matrixConst[i][j]);
	}

	public void drawArrays() {
		int[] rC = data.rowChange;
		int[] cC = data.columnChange;
		int[] min = data.localMinimum;
		int n = rC.length;
		if (localMinimum != null) {
			for (int i = 0; i < rowChange.length; i++) {
				clientPane.remove(rowChange[i]);
				clientPane.remove(columnChange[i]);
				//clientPane.remove(localMinimum[i]);
			}
		}
		rowChange = new Rect[n];
		columnChange = new Rect[n];
		localMinimum = new Rect[n];
		for (int i = 0; i < n; i++) {

			rowChange[i] = new Rect(linearStyleSet);
			rowChange[i].setMessage(String.valueOf(rC[i]));
			rowChange[i].setSize(200 / 2 / n, 200 / 2 / n);
			rowChange[i].adjustFontSize();
			rowChange[i].setLocation(200 / 4 / n, 60 + 200 / 4 / n + 200
					/ n * i);
			clientPane.add(rowChange[i]);

			columnChange[i] = new Rect(linearStyleSet);
			columnChange[i].setMessage(String.valueOf(cC[i]));
			columnChange[i].setSize(200 / 2 / n, 200 / 2 / n);
			columnChange[i].adjustFontSize();
			columnChange[i].setLocation(60 + 200 / 4 / n + 200 / n * i,
					200 / 4 / n);
			clientPane.add(columnChange[i]);

			/*localMinimum[i] = new Rect(linearStyleSet);
			localMinimum[i].setMessage(String.valueOf(min[i]));
			localMinimum[i].setSize(120 / n * 3 / 2, 120 / n);
			localMinimum[i].adjustFontSize();
			localMinimum[i].setLocation(180 + i * 120 / n * 3 / 2, 120);
			clientPane.add(localMinimum[i]);
*/
		}
	}

	public void randomize() {
		int n = data.matrix.length;
		Random rnd = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				data.matrix[i][j] = (int)(Math.random() * 9) + 1;
			}
		}
		auto.getController().doRestart();
	}
	
	protected Component createControlsPane() {
		// TODO Auto-generated method stub
		Container panel = new Panel(new BorderLayout());
		
		Panel bottomPanel = new Panel();
		bottomPanel.add(new HintedButton(config, "button-random"){
            protected void click() {
                randomize();
            }
        });
		bottomPanel.add(size);
        panel.add(bottomPanel, BorderLayout.CENTER);
		
		
		panel.add(new AutoControlsPane(config, auto, forefather, false),
				BorderLayout.SOUTH);

		return panel;

	}

}
