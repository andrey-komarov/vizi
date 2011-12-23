package ru.ifmo.vizi.articulation_points;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Shape;
import ru.ifmo.vizi.base.widgets.Ellipse;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

/**
 * Articulation points applet.
 *
 * @author  Alexander Kirakozov
 * @version $Id: ArticulationPoints.java
 */
public final class ArticulationPointsVisualizer 
	extends Base 
	implements AdjustmentListener, ActionListener
{

	/**
	 *  Articulation points automata instance.
	 */
	private final ArticulationPoints auto;

	/**
	 *  Articulation points automata data.
	 */
	private final ArticulationPoints.Data data;

	/**
	 * Shape of edges.
	 */	
	private final class Edges extends Shape {

		/**
		 * Creates the new edges with specified style set.
		 *
		 * @param styleSet shape's style set.
		 */
		public Edges(ShapeStyle[] styleSet){
			super(styleSet);
		}
		
		protected Dimension fit(Dimension size){
			return new Dimension(0, 0);
		}

		/*
		 * Paints this component.
		 * 
		 * @param g graphics context for painting.
		 */
		public void paint(Graphics g){
			Dimension size = getSize();
			int number = data.numberOfVertices;
			double arc = 2 * Math.PI / number;
			int diameterOfVertex = Math.max(size.width, size.height) / 30;
			int a = size.width / 4 - diameterOfVertex,
			    b = size.height / 2 - diameterOfVertex;
			Point center = new Point(a + diameterOfVertex / 2,
			    						 b + diameterOfVertex / 2);

			for (int i = 0; i < data.numberOfVertices; i++){
				for (int j = i + 1; j < data.numberOfVertices; j++){
					if (data.aMatrix[i][j] != 0){
						Point A = new Point(center.x + (int) ( a * Math.cos(arc * i) ) + diameterOfVertex / 2,
                                            center.y + (int) ( b * Math.sin(arc * i) ) + diameterOfVertex / 2);
						Point B = new Point(center.x + (int) ( a * Math.cos(arc * j) ) + diameterOfVertex / 2,
											center.y + (int) ( b * Math.sin(arc * j) ) + diameterOfVertex / 2);

						if (data.aMatrix[i][j] != 4 && data.aMatrix[j][i] != 4){
							setStyle(data.aMatrix[i][j]);
							g.setColor(look.getFillColor(style));			

							if (data.activeAuto == 2){
								if (data.findLow_activeVertexInDfnum == i && data.findLow_currentVertex == j){
									setStyle(data.aMatrix[j][i]);
									g.setColor(look.getFillColor(style));			

									paintPointer(g, B, A);
								} else
								if (data.findLow_activeVertexInDfnum == j && data.findLow_currentVertex == i){
									setStyle(data.aMatrix[i][j]);
									g.setColor(look.getFillColor(style));			

									paintPointer(g, A, B);
								} else {
									g.drawLine(A.x, A.y, B.x, B.y);
								}
							} else {
								g.drawLine(A.x, A.y, B.x, B.y);
							}
						} else {
							setStyle(4);
							g.setColor(look.getFillColor(style));			
							if (data.aMatrix[i][j] == 4){
								paintPointer(g, A, B);
							} else {
								paintPointer(g, B, A);
							}
						}
					}
				}
			}
		
			super.paint(g);
		}

		/*
		 * Paints this component.
		 * 
		 * @param g graphics context for painting.
		 * @param A first vertex of pointer.
		 * @param B second vertex of pointer.
		 */
		public void paintPointer(Graphics g, Point A, Point B){
			Point center;
			Point P = new Point();
			double angle;
			double indentAngle = Math.PI / 10;
			double length = 8;
			
			center = new Point(Math.abs(A.x + B.x) / 2,
							   Math.abs(A.y + B.y) / 2);

			angle = Math.atan2 ((double) (A.y - B.y), (double) (A.x - B.x));

			P.x = center.x;
			P.y = center.y;
			P.translate((int) Math.rint(length * Math.cos(angle + indentAngle)), 
			            (int) Math.rint(length * Math.sin(angle + indentAngle)));
			g.drawLine(P.x, P.y, center.x, center.y);

			P.x = center.x;
			P.y = center.y;
			P.translate((int) Math.rint(length * Math.cos(angle - indentAngle)), 
			            (int) Math.rint(length * Math.sin(angle - indentAngle)));
			g.drawLine(P.x, P.y, center.x, center.y);

			g.drawLine(A.x, A.y, B.x, B.y);
		}

	}

	/**
	 * Edges view.
	 */ 
	private final Edges edges;
	
	/**
	 * Shape of adjacency matrix.
	 */	
	private final class AdjacencyMatrix extends Shape 
										implements MouseListener
	{

		/**
		 * Creates a new adjacency matrix with specified style set.
		 *
		 * @param styleSet shape's style set.
		 */
		public AdjacencyMatrix(ShapeStyle[] styleSet){
			super(styleSet);
			addMouseListener(this);
		}

		/**
		 * Invoked when mouse exited.
		 * 
		 * @param event mouse event.
		 */
		public void mouseExited(MouseEvent event){
		}

		/**
		 * Invoked when mouse button clicked.
		 * 
		 * @param event mouse event.
		 */
		public void mouseClicked(MouseEvent event){
			if (editMode) {
				Dimension size = getSize();
				int number = data.numberOfVertices + 1;
				int stepx =  size.width / number;
				int stepy =  size.height / number;
				int x = event.getX();
				int y = event.getY();
				int i, j;
    
				for (i = 0; i*stepx < x; i++){
				}
				i -= 2;
				for (j = 0; j*stepy < y; j++){
				}
				j -= 2;
    
				if ((i != j) && (i >= 0) && (j >= 0) && (i < number) && (j < number)){
					if (data.aMatrix[i][j] != 0){
						data.aMatrix[i][j] = 0;
						data.aMatrix[j][i] = 0;
					} else {
						data.aMatrix[i][j] = 3;
						data.aMatrix[j][i] = 3;
					}
				}
					
				edges.repaint();
				repaint();
			}
		}

		/**
		 * Invoked when mouse dragged.
		 * 
		 * @param event mouse event.
		 */
		public void mouseDragged(MouseEvent event){
		}

		/**
		 * Invoked when mouse button realeased.
		 * 
		 * @param event mouse event.
		 */
		public void mouseReleased(MouseEvent event){
		}

		/**
		 * Invoked when mouse entered.
		 * 
		 * @param event mouse event.
		 */
		public void mouseEntered(MouseEvent event){
		}

		/**
		 * Invoked when mouse button pressed.
		 * 
		 * @param event mouse event.
		 */
		public void mousePressed(MouseEvent event){
		}
		
		protected Dimension fit(Dimension size){
			return new Dimension(0, 0);
		}

		/**
		 * Paints this component.
		 * 
		 * @param g graphics context for painting.
		 */
		public void paint(Graphics g){
			Dimension size = getSize();
			int number = data.numberOfVertices + 1;
			int stepx =  size.width / number;
			int stepy =  size.height / number;

			setStyle(0);
			g.setColor(look.getFillColor(style));
			g.fillRect(stepx, 0, stepx * (number - 1), stepy);
			g.fillRect(0, stepy, stepx, stepy * (number - 1));

			for (int i = 1; i < number; i++){
				g.fillRect(stepx * i , stepy * i, stepx, stepy);
			}
			
			for (int i = 1; i < number; i++){
				for (int j = 1; j < number; j++){
					if (data.aMatrix[i-1][j-1] > 3){
						setStyle(data.aMatrix[i-1][j-1]);
						g.setColor(look.getFillColor(style));
						g.fillRect(stepx * i , stepy * j, stepx, stepy);
					}
				}
			}

			setStyle(9);
			g.setColor(look.getFillColor(style));
			for (int i = 0; i <= number; i++){
				g.drawLine(0, stepy * i, number * stepx, stepy * i);
				g.drawLine(stepx * i, 0, stepx * i, number * stepy);
			}
			
			paintMessage(g);

			super.paint(g);
		}

		/**
		 * Paints messages.
		 * 
		 * @param g graphics context for painting.
		 */
		private void paintMessage(Graphics g){
			Dimension size = getSize();
			int number = data.numberOfVertices + 1;
			int stepx =  size.width / number;
			int stepy =  size.height / number;
			Dimension textSize = new Dimension();
			Point indent = new Point();

			Font font;
			int kegl = stepx;

			do {
				font = new Font("Courier", Font.PLAIN, kegl);
				kegl--;
			} while (g.getFontMetrics(font).stringWidth("14") >= stepx);

			g.setFont (font);
			g.setColor(look.getBorderColor(style));
			textSize.height = g.getFontMetrics(font).getAscent();
			indent.y = (stepy + textSize.height) / 2;
			for (int i = 1; i < number; i++){
				textSize.width  = g.getFontMetrics(font).stringWidth(Integer.toString(i-1));
				indent.x = (stepx - textSize.width) / 2;

				g.drawString(Integer.toString(i-1), stepx * i + indent.x, indent.y);
				g.drawString(Integer.toString(i-1), indent.x, stepy * i + indent.y);
			}
			
			textSize.width  = g.getFontMetrics(font).stringWidth("+");
			indent.x = (stepx - textSize.width) / 2;
			for (int i = 1; i < number; i++){
				for (int j = 1; j < number; j++){
					if (i != j) {
						if (data.aMatrix[i-1][j-1] != 0){
							if (data.aMatrix[i-1][j-1] == 3 || data.aMatrix[i-1][j-1] == 10) {
								g.setColor(look.getBorderColor(style));
							} else {
								setStyle(2);
								g.setColor(look.getFillColor(style));
							}
							g.drawString("+", stepx * i + indent.x, stepy * j + indent.y);
						} else {
							g.setColor(look.getBorderColor(style));
							g.drawString("-", stepx * i + indent.x, stepy * j + indent.y);
						}
					}
				}
			}
		}
	}

	/**
	 * Adjacency matrix view.
	 */ 
	private final AdjacencyMatrix matrix;

	/**
	 * Shape of vectots.
	 */ 
	private final class Vectors extends Shape {

		/**
		 * Creates the new vectors with specified style set.
		 *
		 * @param styleSet shape's style set.
		 */
		public Vectors(ShapeStyle[] styleSet){
			super(styleSet);
		}
		
		protected Dimension fit(Dimension size){
			return new Dimension(0, 0);
		}

		/**
		 * Paints this component.
		 * 
		 * @param g graphics context for painting.
		 */
		public void paint(Graphics g){
			Dimension size = getSize();
			int number = data.numberOfVertices + 1;
			int stepx =  size.width / 2;
			int stepy = size.height / number;
			

			Font font;
			Dimension textSize = new Dimension();
			Point indent = new Point();
			int kegl = stepx;

			do {
				font = new Font("Courier", Font.PLAIN, kegl);
				kegl--;
			} while (g.getFontMetrics(font).stringWidth("dfnum") >= stepx);

			g.setFont(font);
			textSize.height = g.getFontMetrics(font).getAscent();
			indent.y = (stepy + textSize.height) / 2;
			
			if (data.activeAuto == 1) {
				textSize.width  = g.getFontMetrics(font).stringWidth("stack");
				indent.x = (stepx - textSize.width) / 2;
				g.drawString("stack", indent.x, indent.y);
				
				if (data.DFS_head > 0){
					setStyle(1);
					g.setColor(look.getFillColor(style));
					g.fillRect(0, stepy * data.DFS_head, stepx, stepy);
				}

				g.setColor(look.getBorderColor(style));
				for (int i = 1; i <= data.DFS_head; i++){
						textSize.width  = g.getFontMetrics(font).stringWidth(Integer.toString(data.DFS_stack[i]));
						indent.x = (stepx - textSize.width) / 2;
						g.drawString(Integer.toString(data.DFS_stack[i]), indent.x, stepy * i + indent.y);
				}
			} else 
			if (data.activeAuto >= 2){
				int num = -1;

				if (data.activeAuto == 2){
					num = data.findLow_activeVertexInLow;
				} else 
				if (data.activeAuto == 3) {
					num = data.findArticulationPoints_activeVertexInLow;
				}

				textSize.width  = g.getFontMetrics(font).stringWidth("low");
				indent.x = (stepx - textSize.width) / 2;
				g.drawString("low", indent.x, indent.y);
				
				if (data.activeAuto == 2) {
					if (data.findLow_currentVertex != -1){
						setStyle(5);
						g.setColor(look.getFillColor(style));
						g.fillRect(0, stepy * (data.findLow_currentVertex + 1), stepx, stepy);
					}
				}
				if (num != -1){
					setStyle(1);
					g.setColor(look.getFillColor(style));
					g.fillRect(0, stepy * (num + 1), stepx, stepy);
				}

				if (data.activeAuto == 4){
					if (data.findBridges_activeVertexInLow1 != -1){
						setStyle(1);
						g.setColor(look.getFillColor(style));
						g.fillRect(0, stepy * (data.findBridges_activeVertexInLow1 + 1), stepx, stepy);
						g.fillRect(0, stepy * (data.findBridges_activeVertexInLow2 + 1), stepx, stepy);
					}
				}

				g.setColor(look.getBorderColor(style));
				for (int i = 1; i < number; i++){
					if (data.low[i-1] != 0) {
						textSize.width  = g.getFontMetrics(font).stringWidth(Integer.toString(data.low[i-1]));
						indent.x = (stepx - textSize.width) / 2;
						g.drawString(Integer.toString(data.low[i-1]), indent.x, stepy * i + indent.y);
					} else {
						textSize.width  = g.getFontMetrics(font).stringWidth("x");
						indent.x = (stepx - textSize.width) / 2;
						g.drawString("x", indent.x, stepy * i + indent.y);
					}
				}
			}			

			if (data.activeAuto > 0) {
				textSize.width  = g.getFontMetrics(font).stringWidth("dfnum");
				indent.x = (stepx - textSize.width) / 2;
				g.drawString("dfnum", stepx + indent.x, indent.y);

				if (data.activeAuto == 1){
					if (data.DFS_activeVertex != -1) {
						g.setColor(look.getFillColor(style));
						g.fillRect(stepx, stepy * (data.DFS_activeVertex + 1), stepx, stepy);
					}
				} else 
				if (data.activeAuto == 2){
					if (data.findLow_activeVertexInDfnum != -1){
						setStyle(1);
						g.setColor(look.getFillColor(style));
						g.fillRect(stepx, stepy * (data.findLow_activeVertexInDfnum + 1), stepx, stepy);
					}
				} else 
				if (data.activeAuto == 3){
					if (data.findArticulationPoints_currentVertex != -1){
						setStyle(5);
						g.setColor(look.getFillColor(style));
						g.fillRect(stepx, stepy * (data.findArticulationPoints_currentVertex + 1), stepx, stepy);
					}
				} else 
				if (data.activeAuto == 4){
					if (data.findBridges_activeVertexInDfnum1 != -1){
						setStyle(1);
						g.setColor(look.getFillColor(style));
						g.fillRect(stepx, stepy * (data.findBridges_activeVertexInDfnum1 + 1), stepx, stepy);
						g.fillRect(stepx, stepy * (data.findBridges_activeVertexInDfnum2 + 1), stepx, stepy);
					}
				}

				g.setColor(look.getBorderColor(style));
				for (int i = 1; i < number; i++){
					if (data.dfnumber[i-1] != 0) {
						textSize.width  = g.getFontMetrics(font).stringWidth(Integer.toString(data.dfnumber[i-1]));
						indent.x = (stepx - textSize.width) / 2;
						g.drawString(Integer.toString(data.dfnumber[i-1]), stepx + indent.x, stepy * i + indent.y);
					} else {
						textSize.width  = g.getFontMetrics(font).stringWidth("x");
						indent.x = (stepx - textSize.width) / 2;
						g.drawString("x", stepx + indent.x, stepy * i + indent.y);
					}
				}
			}

			for (int i = 0; i < 3; i++){
				g.drawLine(stepx * i, 0, stepx * i, number * stepy);
			}
			for (int i = 0; i < number + 1; i++){
				g.drawLine(0, stepy * i, 2 * stepx, stepy * i);
			}

			super.paint(g);
		}
	}
	
	/**
	 * Vectors view.
	 */ 
	private final Vectors vectors;

	/**
	 * Stack of vertices.
	 * Vector of {@link Ellipse}.
	 */
	private final Stack vertices;

	/**
	 * Number of vertices.
	 */
	private final AdjustablePanel elements;

    /**
     * Is edit mode enabled.
     */
    private boolean editMode;

    /**
     * Button Start/Stop edit mode.
     */
    private MultiButton editModeButton;

	/**
	 * Array shape style set.
	 */
	private final ShapeStyle[] styleSet;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

	/** 
	 * Creates a new Find Maximum visualizer.
	 *
	 * @param parameters visualizer parameters.
	 */
	public ArticulationPointsVisualizer(VisualizerParameters parameters) {
		super(parameters);
		
		auto = new ArticulationPoints(locale);
		data = auto.d;
		data.visualizer = this;
		vertices = new Stack();
		
		styleSet = ShapeStyle.loadStyleSet(config, "graph");

		edges = new Edges(styleSet);
		edges.setStyle(2);

		matrix = new AdjacencyMatrix(styleSet);
		matrix.setStyle(2);
		clientPane.add(matrix);

		vectors = new Vectors(styleSet);
		vectors.setStyle(2);
		clientPane.add(vectors);

		elements = new AdjustablePanel(config, "elements");
		elements.addAdjustmentListener(this);

		setNumberOfVertices(data.numberOfVertices);

		createInterface(auto);
    }

    /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    public Component createControlsPane() {
        Panel panel = new Panel(new BorderLayout());

		panel.add(new AutoControlsPane(config, auto, forefather, true), BorderLayout.CENTER);

		Panel bottomPanel = new Panel();
        editModeButton = new MultiButton(config, new String[] {
                                                     "button-startEditMode", 
                                                     "button-stopEditMode"});
        if (config.getBoolean("button-ShowEditMode")) {
	        editModeButton.addActionListener(this);
    	    bottomPanel.add(editModeButton);

			bottomPanel.add(new HintedButton(config, "button-random") {
				protected void click(){
					randomize();
				}
			});
		}
		editMode = false;

        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    int number = data.numberOfVertices;
                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("NumberOfVerticesComment"), 
                            new Integer(elements.getMinimum()),
                            new Integer(elements.getMaximum())
                        )
                    ).append(" */\n");

                    buffer.append("NumberOfVertices = ").append(number).append("\n");

                    buffer.append("/* ").append(
                    	config.getParameter("AdjacencyMatrixComment") 
                    ).append(" */\n");

                    buffer.append("AdjacencyMatrix = \n");
                    for (int i = 0; i < number; i++){
						for (int j = 0; j < number; j++) {
							if (i == j){
                        		buffer.append("x").append(" ");
							} else {
								if (data.aMatrix[i][j] == 0){
                        			buffer.append("0").append(" ");
								} else {
	                        		buffer.append("1").append(" ");
								}
							}
						}
						buffer.append("\n");
                    }

                    buffer.append("\n/* ").append(
                        config.getParameter("StepComment")
                    ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        }

        bottomPanel.add(elements);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("NumberOfVertices");
                tokenizer.expect("=");
                int number = tokenizer.nextInt(elements.getMinimum(),
											   elements.getMaximum());
				int[][] aMatrix = new int[number][number];

                tokenizer.expect("AdjacencyMatrix");
                tokenizer.expect("=");
                for (int i = 0; i < number; i++) {
                	for (int j = 0; j < number; j++) {
						if (i == j){
                			tokenizer.expect("x");
						} else {
							if (i < j) {
								int tmp = tokenizer.nextInt(0, 1);
								if (tmp == 0) {
        	            			aMatrix[i][j] = 0;
								} else {
									aMatrix[i][j] = 3;
									aMatrix[j][i] = 3;
								}
							} else {
								int tmp;
								if (aMatrix[j][i] == 0) {
        	            			tmp = 0;
								} else {
									tmp = 1;
								}
                				tokenizer.nextInt(tmp, tmp);
							}
						}
					}
                }

                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

				auto.d.numberOfVertices = number;
                auto.d.aMatrix = aMatrix;
                rewind(step);

                return true;
            }
        };

        return panel;
    }

	/**
	 * Adjusts graph size to match current model size.
	 */
	private void adjustGraphSize(){
		int number = data.numberOfVertices;
		
		while (vertices.size() < number){
			Ellipse ellipse = new Ellipse(styleSet);

			vertices.push(ellipse);
			clientPane.add(ellipse);
		}
		while (vertices.size() > number){
			clientPane.remove((Component) vertices.pop());
		}
		clientPane.remove(edges);
		clientPane.add(edges);

		clientPane.doLayout();
	}

	/**
	 * Sets new number of vertices.
	 *
	 * @param number new number of vertices.
	 */
	private void setNumberOfVertices(int number){
		data.numberOfVertices = number;
		data.aMatrix = new int[number][number];
		data.visited = new boolean[number];
		data.dfnumber = new int[number];
		data.low = new int[number];
		data.articulationPoints = new int[number];
		data.way = new int[number];

		randomize();
		adjustGraphSize();
	}

	/**
	 *  Generate graph.
	 */
	private void randomize() {
		int number = data.numberOfVertices;

		for(int i = 0; i < number; i++) {
			for (int j = i + 1; j < number; j++) {
				if (Math.random() < 2.5 / number) {
					data.aMatrix[i][j] = 3;
					data.aMatrix[j][i] = 3;
				} else {
					data.aMatrix[i][j] = 0;
					data.aMatrix[j][i] = 0;
				}
			}
		}
		rewind(0);
	}
	
	/**
	 * Rewinds algorithm to the specified step.
	 *
	 * @param step of the algorithm to rewind to.
	 */
	private void rewind(int step) {
		adjustGraphSize();
		auto.toStart();

		data.activeAuto = 0;
		for (int i = 0; i < data.numberOfVertices; i++){
			Ellipse vertex = (Ellipse) vertices.elementAt(i);
			vertex.setStyle(0);

			data.dfnumber[i] = 0;
			data.visited[i] = false;
			data.low[i] = 0;
			data.articulationPoints[i] = 0;
		}

		for (int i = 0; i < data.numberOfVertices; i++){
			for (int j = 0; j < data.numberOfVertices; j++){
				if (data.aMatrix[i][j] != 0){
					data.aMatrix[i][j] = 3;
				}
			}
		}

		while(!auto.isAtEnd() && auto.getStep() < step) {
			auto.stepForward(0);
		}
	}

	/**
	 * Invoked on adjustment event.
	 *
	 * @param event event to process.
	 */
	public void adjustmentValueChanged(AdjustmentEvent event) {
		if (event.getSource() == elements) {
			setNumberOfVertices(event.getValue());
		}
	}

	/**
	 * Updates vertex view.
	 * 
	 * @param activeVertex current active vertex.
	 */
	public void updateVertex(int activeVertex){
		for (int i = 0; i < data.numberOfVertices; i++){
			Ellipse vertex = (Ellipse) vertices.elementAt(i);
			if (data.visited[i]){
				vertex.setStyle(2);
			} else 
			if (data.articulationPoints[i] == 1){
				vertex.setStyle(6);
			} else { 
				vertex.setStyle(0);
			}
		}
		
		if (activeVertex != -1){
			Ellipse vertex = (Ellipse) vertices.elementAt(activeVertex);
			vertex.setStyle(1);
		}

		if (data.activeAuto == 2){
			if (data.findLow_currentVertex != -1){
				Ellipse vertex = (Ellipse) vertices.elementAt(data.findLow_currentVertex);
				vertex.setStyle(5);
			}
		} else
		if (data.activeAuto == 3){
			if (data.findArticulationPoints_currentVertex != -1){
				Ellipse vertex = (Ellipse) vertices.elementAt(data.findArticulationPoints_currentVertex);
				vertex.setStyle(5);
			}
		} else 
		if (data.activeAuto == 4){
			if (data.findBridges_activeVertexInLow1 != -1){
				Ellipse vertex = (Ellipse) vertices.elementAt(data.findBridges_activeVertexInLow1);
				vertex.setStyle(1);
				vertex = (Ellipse) vertices.elementAt(data.findBridges_activeVertexInLow2);
				vertex.setStyle(1);
			}
		}

		update(true);
	}

    /**
     * Starts edit mode of array elements.
     * Updates comments, creates and shows text fields and so on.
     */
    public void startEditMode() {
		if (!editMode){
			editMode = true;
			rewind(0);
        	editModeButton.setState(1);
		}
	}

    /**
     * Stops edit mode of array elements. 
     * Hides text fields, applies changes.
     */
    public void stopEditMode() {
		if (editMode){
			editMode = false;
    	    editModeButton.setState(0);
		}
	}

    /**
     * Invoked when an action occurs.
     *
     * @param event event to process.
     */
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == editModeButton) {
            if (editModeButton.getState() == 0) {
                startEditMode();
            } else {
                stopEditMode();
            }
        }
    }

	/**
	 * Invoked when client pane shoud be layouted.
	 * 
	 * @param clientWidth client pane width.
	 * @param clientHeigth client pane heigth.
	 */
	protected void layoutClientPane(int clientWidth, int clientHeight){
		
		// Vertices.

		int number = data.numberOfVertices;
		double arc = 2 * Math.PI / number;
		int diameterOfVertex = Math.max(clientWidth, clientHeight) / 30;
		int a = clientWidth / 4 - diameterOfVertex,
		    b = clientHeight / 2 - diameterOfVertex;
		Point center = new Point(a + diameterOfVertex / 2,
		    					 b + diameterOfVertex / 2);

		for (int i = 0; i < number; i++) {
			Ellipse vertex = (Ellipse) vertices.elementAt(i);

			vertex.setBounds(center.x + (int) ( a * Math.cos(arc * i) ), 
						     center.y + (int) ( b * Math.sin(arc * i) ), 
						     diameterOfVertex, diameterOfVertex);
			vertex.setMessage(Integer.toString(i));
		}

		// Edges.

		edges.setBounds(0, 0, clientWidth, clientHeight);

		// Adjacency matrix.

		matrix.setBounds(clientWidth / 2, 0, clientWidth / 3, clientHeight);

		// Vectors.

		vectors.setBounds(clientWidth * 5 / 6, 0, clientWidth / 6 - 5, clientHeight);

		update(true);
	}
}	
