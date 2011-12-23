package ru.ifmo.vizi.Kruskal;

import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.widgets.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import ru.ifmo.vizi.Kruskal.widgets.MyLine;

public final class KruskalVisualizer extends Base 
    implements AdjustmentListener, MouseMotionListener, MouseListener
{
    
    /**
     * Kruskal automata instance.
     */
    private final Kruskal auto;

    /**
     * Kruskal automata data.
     */
    private final Kruskal.Data data;

    /**
     * Number of vertexes.
     * Instance of {@link AdjustablePanel}.
     */
    private final AdjustablePanel vertexPanel;

    /**
     * Number of edges.
     * Instance of {@link AdjustablePanel}.
     */
    private final AdjustablePanel edgePanel;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * Edges shape style set.
     */
    private ShapeStyle[] styleSet;

    /**
     * Vertex shape style set.
     */
    private ShapeStyle[] vertexStyleSet;

    /**
     * Number of vertexes in the graph.
    */
    private int vertexNumber;

    /**
     * Radius of vertexes circles
    */
    private int vertexRadius;

    /**
     * Default boldness of lines
    */
    private int boldness = 2;

    /**
     * Edges List (cells with values).
     * Vector of {@link Rect}.
     */
    private Stack edges;

    /**
     * Vertexes (circles with values)
     * Vector of {@link Ellipse}.
     */
    private Stack vertexes;

    /**
     * Lines matching to edges
     * Vector of {@link MyLine}.
     */
    private Stack lines;

    /**
     * Weights of edges(circles with values)
     * Vector of {@link Ellipse}.
     */
    private Stack weights;

    /**
     * Template for comments at edges list
     * Vector of {@link Rect}.
     */
    private Stack comments;

    /**
     * Number of rows to layout edges list
    */
    private int rows;

    /**
     * Maximum number of edges
    */
    private int maxEdges;

    
    /**
     * Ratio to divide edges for drawing weight-showing circle
    */
    private double ratio2;

    /**
     * Ratio of shift from borders to applet dimensions
    */
    private double shiftRatio;

    /**
     * Ratio of shown size of rectangles to their real size
    */
    private double fillRatio;

    /**
     * Maximal edge weight value.
     */
    private int inf;

    /**
     * Widest possible string matching to weight.
     */
    private String widestString;

    /**
     * Border of active edge.
     */
    private Rect edgeRect;

     /**
     * Rect in edit mode to increase weight of edge.
     */
    private Rect editInc;

     /**
     * {@link Rect} in edit mode to decrease weight of edge.
     */
    private Rect editDec;
    
     /**
     * {@link Rect} in edit mode to delete edge.
     */
    private Rect editDel; 

    /**
     * First vertex of edited edge.
    */
    private int activeVertex1;
    
    /**
     * Second vertex of edited edge.
    */
    private int activeVertex2;

    /**
      Edge to be added.
    */
    private MyLine newEdge;

    /**
     * Edited edge in edit mode.
    */
    private int editedEdge;

    /**
      Shows if the graph is editable by user.
    */                                      
    private boolean editable = false;

    /**
     * Button to switch visualize/edit modes.
     * {@link MultiButton}  
    */
    private final MultiButton switchButton;

    /**
     * Shows if it is needed to show weights.
    */
    private boolean bShowWeights = true;

    /**
     * Number of edges of graph.
    */
    private int edgeNumber;

    /**
     * Number of edge parameters(first vertex, second vertex, weight) to show.
    */
    private int showEdgeParam = 2;

    /**
     * Creates a new Kruskal visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public KruskalVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new Kruskal(locale);
        data = auto.d;
        data.visualizer = this;

        vertexPanel = new AdjustablePanel(config, "elements");
        vertexPanel.addAdjustmentListener(this);
        edgePanel = new AdjustablePanel(config, "edges");
        edgePanel.addAdjustmentListener(this);

        styleSet = ShapeStyle.loadStyleSet(config, "line");
        vertexStyleSet = ShapeStyle.loadStyleSet(config, "vertexesStyleSet");       

        switchButton = new MultiButton(config, new String[] {"button-edit", "button-vis"}) {
            protected int click(int state) {
                doRestart();
                editable = !editable;
                return 1 - state;
            }
        };


        edgeRect = new Rect(styleSet);
        clientPane.add(edgeRect);
        edgeRect.adjustSize();
        newEdge = new MyLine(styleSet, Math.max(1, boldness / 2));
        editInc = new Rect(styleSet);
        editInc.setMessage("+");
        editInc.setStyle(1);
        clientPane.add(editInc);
        editDec = new Rect(styleSet);
        editDec.setStyle(1);
        editDec.setMessage("-");
        clientPane.add(editDec);
        editDel = new Rect(styleSet);
        editDel.setBounds(0, 0, 0 ,0);
        editDel.setMessage("x");
        clientPane.add(editDel);

        fillRatio = config.getDouble("fillRatio");
        shiftRatio = config.getDouble("shiftRatio");
        ratio2 = config.getDouble("ratio2");

        maxEdges = config.getInteger("maxEdges");
        rows = config.getInteger("rows");
        inf = config.getInteger("max-value");
        widestString = config.getParameter("max-value-string");

        edges = new Stack();
        vertexes = new Stack();
        lines = new Stack();
        weights = new Stack();
        comments = new Stack();


        for (int i = 0; i < 3 * maxEdges / rows; i++) {
                Rect rect = new Rect(styleSet);
                comments.push(rect);
                clientPane.add(rect);
        }

        changeVertexNumber(vertexPanel.getValue());
        
        clientPane.addMouseMotionListener(this);
        clientPane.addMouseListener(this);
  
        activeVertex1 = -1;
        activeVertex2 = -1;
        editedEdge = -1;

        createInterface(auto);
    }

   /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    public Component createControlsPane() {
        Panel panel = new Panel(new BorderLayout());
        panel.add(new AutoControlsPane(config, auto, forefather, false), BorderLayout.CENTER);
        Panel bottomPanel = new Panel();
        bottomPanel.add(vertexPanel);
        bottomPanel.add(edgePanel);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(new HintedButton(config, "button-random"){
                    protected void click() {
                        randomize();
                    }
        });

        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    int[] l = auto.d.l;
                    int[] r = auto.d.r;
                    int[] weights = auto.d.weights;

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("VertexNumberComment"), 
                            new Integer(vertexPanel.getMinimum()),
                            new Integer(vertexPanel.getMaximum())
                        )
                    ).append(" */\n");

                    buffer.append("VertexNumber = ").append(auto.d.color.length).append("\n");

                    buffer.append("/*").append(
                        I18n.message(
                            config.getParameter("EdgeNumberComment"),
                            new Integer(edgePanel.getMinimum()),
                            new Integer(edgePanel.getMaximum())
                        )
                    ).append("*/\n");

                    buffer.append("EdgeNumber = ").append(l.length).append("\n");

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("EdgesListComment"), 
                            new Integer(1),
                            new Integer(vertexPanel.getValue()),
                            new Integer(1),
                            new Integer(inf)
                        )
                    ).append(" */\n");

                    buffer.append("Edges = ").append("\n");
                    for (int i = 0; i < l.length; i++) {
                        buffer.append(l[i] + 1).append(" ").append(r[i] + 1).append(" ")
                                .append(weights[i]).append("\n");
                    }

                    buffer.append("\n/* ").append(
                        config.getParameter("StepComment")
                    ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        }

            saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("VertexNumber");
                tokenizer.expect("=");

                int[] l, r, weights, color;

                int vertNumb = tokenizer.nextInt(
                    vertexPanel.getMinimum(), 
                    vertexPanel.getMaximum()
                );

                color = new int[vertNumb];

                tokenizer.expect("EdgeNumber");
                tokenizer.expect("=");

                int edgeNumb = tokenizer.nextInt(
                    vertNumb - 1,
                    Math.min((vertNumb * vertNumb - vertNumb) / 2, maxEdges)                        
                );

                l = new int[edgeNumb];
                r = new int[edgeNumb];
                weights = new int[edgeNumb];

                tokenizer.expect("Edges");
                tokenizer.expect("=");

                for (int i = 0; i < l.length; i++) {
                    l[i] = tokenizer.nextInt(1, vertNumb) - 1;
                    r[i] = tokenizer.nextInt(1, vertNumb) - 1;
                    weights[i] = tokenizer.nextInt(1, inf);
                    for (int j = 0; j < i; j++) {
                        if (((l[j] == l[i]) && (r[i] == r[j])) || 
                                ((l[j] == r[i]) && (r[j] == l[i]))) {
                                         Object[] args = new Object[] {
                                                new Integer( tokenizer.lineno() ),
                                                new Integer( l[i] + 1 ),
                                                new Integer( r[i] + 1 )
                                         };
                                         System.out.println(config.getParameter("wrongLoad-message"));
                                         String string = java.text.MessageFormat.format( config.getParameter("wrongLoad-message"), args);
                                         throw new Exception( string );
                                 }
                    }
                }

                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

                auto.d.color = color;
                auto.d.l = l;
                auto.d.r = r;
                auto.d.weights = weights;
                vertexNumber = vertNumb;
                edgeNumber = edgeNumb;

                adjustVertexes();
                adjustEdges();

                doRestart();
                updateView(-1, 0);
                while ((auto.getStep() < step) && (!auto.isAtEnd())) auto.stepForward(0);

                return true;
            }
        };

        bottomPanel.add(switchButton);

       
        return panel;
    }

    /**
     * Adjusts number of edges to match current model number of edges.
     */
    private void adjustEdges() {

        clientPane.remove(edgeRect);
        clientPane.remove(newEdge);

        while (edges.size() > 0) {
            Rect[] rect = (Rect[]) edges.pop();
            for (int i = 0; i < 3; i++) clientPane.remove((Component) rect[i]);
            clientPane.remove((Component) lines.pop());
            clientPane.remove((Component) weights.pop());
        }
            
        while (edges.size() < edgeNumber) {
            Rect[] rect = new Rect[3];
            for (int i = 0; i < 3; i++) {
                rect[i] = new Rect(styleSet);
                clientPane.add(rect[i]);
            }
            edges.push(rect);
       }

       while (weights.size() < edgeNumber) {
            Ellipse ellipse = new Ellipse(styleSet);
            clientPane.add(ellipse);
            weights.push(ellipse);
       }

       while (lines.size() < edgeNumber) {
            MyLine line = new MyLine(styleSet, boldness);
            clientPane.add(line);
            lines.push(line);
        }
        clientPane.add(edgeRect);
        clientPane.add(newEdge);
        edgeRect.setBounds(0, 0, 0, 0);
        edgePanel.setValue(edgeNumber);
    }

    /**
     * Adjusts number of vertexes to match current model number of vertexes.
     */
    private void adjustVertexes() {
        vertexNumber = auto.d.color.length;

        while (vertexes.size() > 0) {
            clientPane.remove((Component) vertexes.pop());
        }
        while (vertexes.size() < vertexNumber) {
            Ellipse ellipse = new Ellipse(vertexStyleSet);
            clientPane.add(ellipse);
            vertexes.push(ellipse);
        }
        vertexPanel.setValue(vertexNumber);
    }

    /**
     * Sets new edges number.
     *
     * @param size new edges number.
     */
     private void doRestart(){
        while (!auto.isAtStart()) auto.stepBackward(0);
     }

    private void changeEdgeNumber(int newEdgeNumber) {
        if (edgeNumber == newEdgeNumber) return;
        while (!auto.isAtStart()) auto.stepBackward(0);
        data.l = new int[newEdgeNumber];
        data.r = new int[newEdgeNumber];
        data.weights = new int[newEdgeNumber];
        edgeNumber = newEdgeNumber;
        adjustEdges();
        randomize();
    }

   /**
     * Sets new vertexes number.
     *
     * @param size new vertexes number.
     */
    private void changeVertexNumber(int newVertexNumber) {
        if (vertexNumber == newVertexNumber) return;
        while (!auto.isAtStart()) auto.stepBackward(0);
        data.color = new int[newVertexNumber];
        adjustVertexes();
        edgePanel.setMinimum(0);
        edgePanel.setMaximum(((vertexNumber - 1) * vertexNumber) / 2);
        if (edgePanel.getMaximum() > maxEdges) edgePanel.setMaximum(maxEdges);
        changeEdgeNumber(vertexNumber - 1);
    }

    /**
     * Creates random graph.
     */
    private void randomize() {
        while (!auto.isAtStart()) auto.stepBackward(0);
        vertexNumber = data.color.length;
        int[] a = new int[vertexNumber];
        for (int i = 0; i < vertexNumber; i++) a[i] = i;
        boolean was[][] = new boolean[vertexNumber][vertexNumber];
        for (int i = 0; i < vertexNumber; i++) {
            int j = (int) (Math.random() * vertexNumber);
            int k = (int) (Math.random() * vertexNumber);
            int t = a[j];
            a[j] = a[k];
            a[k] = t;
        }
        for (int i = 0; i < Math.min(vertexNumber - 1, edgeNumber); i++) {
            data.l[i] = a[i];
            data.r[i] = a[i+1];
            data.weights[i] = (int) (Math.random() * inf) + 1;          
            was[a[i]][a[i+1]] = true;
            was[a[i+1]][a[i]] = true;
        }

        int k = (vertexNumber * vertexNumber - 3 * vertexNumber);
        for (int i = vertexNumber - 1; i < edgeNumber; i++) {
            int j = (int) (Math.random() * k) + 1;
            int t = 0;
            for (int l = 0; l < vertexNumber; l++) {
                for (int m = 0; m < vertexNumber; m++) {
                    if ((!was[l][m]) && (l != m)) t++;
                    if (t == j) {
                        data.l[i] = l;
                        data.r[i] = m;
                        data.weights[i] = (int) (Math.random() * inf) + 1;
                        was[l][m] = true;
                        was[m][l] = true;
                        k -= 2;
                        break;
                    }
                }
                if (t == j) break;
            }
        }
        updateView(-1, 0);
    }

    /**
     * Updates view.
     *
     * @param activeEdge current active edge.
     * @param activeStyle style of active edge.
     */
    public void updateView(int activeEdge, int activeStyle) {
        if (!auto.isAtStart()) {
                editable = false;
                switchButton.setState(0);
        }
        edgeRect.setBounds(0, 0, 0, 0);

        for (int i = activeEdge; i < edgeNumber; i++) {
            if (i < 0) continue; 
            Rect[] edge = (Rect[]) edges.elementAt(i);
            edge[0].setMessage(Integer.toString(data.l[i] + 1));
            edge[1].setMessage(Integer.toString(data.r[i] + 1));
            edge[2].setMessage(Integer.toString(data.weights[i]));
            for (int j = 0; j < 3; j++) {
                edge[j].setStyle((activeEdge == i) ? activeStyle : 0);
            }
            MyLine line = (MyLine) lines.elementAt(i);
            line.setStyle(edge[0].getStyle());
            Ellipse ellipse = (Ellipse) weights.elementAt(i);
            ellipse.setStyle(line.getStyle());       
            ellipse.setMessage(Integer.toString(data.weights[i]));
        }

        for (int i = 0; i < edgeNumber; i++) {
            MyLine line = (MyLine) lines.elementAt(i);
            line.setBold((i == activeEdge) ? 2 * boldness : boldness);
        }

        for (int i = 0; i < vertexNumber; i++) {
            Ellipse ellipse = (Ellipse) vertexes.elementAt(i);
            ellipse.setStyle(i == activeVertex1 ? 3 : 0);
            if (i == activeVertex2) {
                ellipse.setStyle(3);
            }
            if ((activeEdge < edgeNumber) && (activeEdge > -1)) {
                if (data.color[i] == data.color[data.l[activeEdge]]) {
                    ellipse.setStyle(1);
                } 
                if (data.color[i] == data.color[data.r[activeEdge]]) {
                    ellipse.setStyle(2);                
                } 
            }

        }

        if ((activeEdge >= 0) && (activeEdge < edges.size())) {
            Rect[] edge = (Rect[]) edges.elementAt(activeEdge); 
            Rectangle rectangle = edge[0].getBounds();
            edgeRect.setBounds(rectangle.x - 1, rectangle.y - 1, 
                        rectangle.width * showEdgeParam + 2, rectangle.height + 2);
        }
        clientPane.doLayout();
    }

    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    public void layoutClientPane(int clientWidth, int clientHeight) {
        if (vertexNumber == 0) return;
        clientPane.repaint();

        int rectWidth, rectHeight, graphWidth;
        int edgesListWidth = (int) (clientWidth * ratio2);
        int columns = (maxEdges / rows);
        rectHeight = (int) ((1 - 2 * shiftRatio) * clientHeight / (rows + 1));
        rectWidth = edgesListWidth / columns;
        graphWidth = clientWidth - edgesListWidth;
        
        int xc = graphWidth / 2;
        int yc = clientHeight / 2;
        int radius = (int) (Math.min(xc, yc) * (1 - 2 * shiftRatio));
        vertexRadius = Math.min (clientHeight / 20, clientWidth / 20); 
        double alpha = 2 * Math.PI / vertexNumber;
        for (int i = 0; i < vertexNumber; i++) {
            Ellipse ellipse = (Ellipse) vertexes.elementAt(i);
            int x = xc + (int) (radius * Math.sin(i * alpha));
            int y = yc - (int) (radius * Math.cos(i * alpha));
            ellipse.setBounds(x - vertexRadius, y - vertexRadius,
                            2 * vertexRadius, 2 * vertexRadius);
            ellipse.setMessage(Integer.toString(i + 1));
            ellipse.adjustFontSize("8");
        }

        for (int i = 0; i < edgeNumber; i++) {
            MyLine line = (MyLine) lines.elementAt(i);
            int left = data.l[i];
            int right = data.r[i];
            if (left > right) {
                int temp = left;
                left = right;
                right = temp;
            }
            int x1, x2, y1, y2;
            double rat = 0.23;
            x1 = xc + (int) (radius * Math.sin(left * alpha));
            y1 = yc - (int) (radius * Math.cos(left * alpha));
            x2 = xc + (int) (radius * Math.sin(right * alpha));
            y2 = yc - (int) (radius * Math.cos(right * alpha));
            line.setPoints(x1, y1, x2, y2);
            Ellipse ellipse = (Ellipse) weights.elementAt(i);
            int weightRadius = Math.min(clientHeight / 28, clientWidth / 28);
            if ((line.getBold() > 0) && (bShowWeights)) {
                ellipse.setBounds( (int) (x1 + (x2 - x1) * rat - weightRadius), 
                                (int) (y1 + (y2 - y1) * rat - weightRadius),
                                2 * weightRadius, 2 * weightRadius);
            } else ellipse.setBounds(0, 0, 0, 0);
            ellipse.adjustFontSize(widestString);
            ellipse.setMessage(Integer.toString(data.weights[i]));
        }

        for (int i = 0; i < edgeNumber; i++) {
            Rect[] rect = (Rect[]) edges.elementAt(i);
            MyLine line = (MyLine) lines.elementAt(i);
            int column = i / rows;
            int row = i % rows + 1;
            int x = graphWidth + column * rectWidth;
            int y = (int) (clientHeight * shiftRatio) + row * rectHeight;
            int width = (int) (fillRatio * rectWidth / showEdgeParam);
            for (int j = 0; j < showEdgeParam; j++) {
                rect[j].setBounds(x + (int) j * width, y, width, (int) (rectHeight * fillRatio));
                rect[j].adjustFontSize(widestString);
                }
        }
        columns = edgeNumber / rows;
        if (columns * rows < edgeNumber) columns++;
        for (int i = 0; i < comments.size(); i++) {
               Rect rect = (Rect) comments.elementAt(i);
            if (i < showEdgeParam * columns) {
                int column = i;
                int x = graphWidth + (column / showEdgeParam) * rectWidth 
                        + (column % showEdgeParam) * (int) (rectWidth * fillRatio / showEdgeParam);
                int y = (int) (clientHeight * shiftRatio);
                int width = (int) (fillRatio * rectWidth / showEdgeParam);
                rect.setBounds(x, y, width, (int) (fillRatio * rectHeight));
            } else {
                rect.setBounds(0, 0, 0, 0);
            }
            if (i % showEdgeParam == 0) {
                rect.setMessage(config.getParameter("v1-message"));
            }
            if (i % showEdgeParam == 1) {
                rect.setMessage(config.getParameter("v2-message"));
            }
            if (i % showEdgeParam == 2) {
                rect.setMessage(config.getParameter("w-message"));
            }
            rect.setStyle(3);
            rect.adjustFontSize();
        }
    }

    
   /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == vertexPanel) {
            changeVertexNumber(e.getValue());
        }
        if (e.getSource() == edgePanel) {
            changeEdgeNumber(e.getValue());
        }
    }

    /**
     * Checks if an ellipse contains a point
     * @param ellipse Ellipse to check
     * @param point Point to check
    */
    private boolean containsPoint(Ellipse ellipse, Point point) {
        Rectangle rect = ellipse.getBounds();
        int xc = rect.x + rect.width / 2;
        int yc = rect.y + rect.height / 2;
        int x = point.x;
        int y = point.y;
        return ((xc - x) * (xc - x) + (yc - y) * (yc - y) < rect.width * rect.width);           
    }


    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseMoved(MouseEvent e) {
        if (!editable) return;
        int x = e.getX();
        int y = e.getY();
        int old = editedEdge;
        editedEdge = -1;
        for (int i = 0; i < edgeNumber; i++) {
            Ellipse ellipse = (Ellipse) weights.elementAt(i);
            Rectangle rect = ellipse.getBounds();
            if ((y >= rect.y) && (y <= rect.y + rect.height) &&
                (x >= rect.x + rect.width / 3) && 
                (x <= rect.x + 3 * rect.width / 2)) {
                    editedEdge = i;                             
                }
        }
        if (editedEdge == -1) {
            editInc.setBounds(0, 0, 0, 0);
            editDec.setBounds(0, 0, 0, 0);
            editDel.setBounds(0, 0, 0 ,0);
        }
        else {
            Ellipse ellipse = (Ellipse) weights.elementAt(editedEdge);
            Rectangle rect = ellipse.getBounds();
            editInc.setBounds(rect.x + rect.width, rect.y - rect.height / 4, 
                            rect.width / 2, rect.height / 2);
            editDel.setBounds(rect.x + rect.width, rect.y + rect.height/4, 
                            rect.width /2, rect.height / 2);
            editDec.setBounds(rect.x + rect.width, rect.y + 3 * (rect.height/4),
                            rect.width / 2, rect.height / 2);
        }

        clientPane.doLayout();
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseDragged(MouseEvent e) {
        if (activeVertex1 == -1) return;
        if (!editable) return;
        int old = activeVertex2;
        activeVertex2 = -1;
        Point point = e.getPoint();
        Ellipse ellipse = (Ellipse) vertexes.elementAt(activeVertex1);
        Rectangle rect = ellipse.getBounds();
        newEdge.setPoints(rect.x + rect.width / 2, rect.y + rect.height / 2, e.getX(), e.getY());
        for (int i = 0; i < vertexNumber; i++) {
            ellipse = (Ellipse) vertexes.elementAt(i);
            if (containsPoint(ellipse, point)) {
                activeVertex2 = i; 
            }
        }
        if (activeVertex2 != old) updateView(1000, 0);  
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mousePressed(MouseEvent e) {
        if (editedEdge != -1) return;
        if (!editable) return;
        Point point = e.getPoint();
        activeVertex1 = -1;
        for (int i = 0; i < vertexNumber; i++) {
            Ellipse ellipse = (Ellipse) vertexes.elementAt(i);
            if (containsPoint(ellipse, point)) {
                activeVertex1 = i; 
            } 
        }
        updateView(1000, 0);    
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseExited(MouseEvent e) {
        editedEdge = -1;
        editInc.setBounds(0, 0, 0, 0);
        editDec.setBounds(0, 0, 0, 0);
        editDel.setBounds(0, 0, 0, 0);
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseReleased(MouseEvent e) {

        if (!editable) return;

        int vert2;
        boolean canAdd = false;
        Point point = e.getPoint();
        vert2 = -1;
            
        if (activeVertex1 != -1) {
            canAdd = true;
            for (int i = 0; i < vertexNumber; i++) {
                Ellipse ellipse = (Ellipse) vertexes.elementAt(i);
                if (containsPoint(ellipse, point)) vert2 = i; 
            }   
            if (vert2 != activeVertex1) {
                for (int i = 0; i < edgeNumber; i++) {
                    if (((data.l[i] == activeVertex1) && (data.r[i] == vert2)) ||
                        ((data.r[i] == activeVertex1) && (data.l[i] == vert2))) {
                            canAdd = false;
                        }
                }
            } else canAdd = false;
        }

        if (edgeNumber == maxEdges) canAdd = false;
        if (vert2 == -1) canAdd = false;

        if (canAdd) {
            int[] l = new int[edgeNumber + 1];
            int[] r = new int[edgeNumber + 1];
            int[] weig = new int[edgeNumber + 1];
            for (int i = 0; i < edgeNumber; i++) {
                l[i] = data.l[i];
                r[i] = data.r[i];
                weig[i] = data.weights[i];
            } 
            l[edgeNumber] = activeVertex1;
            r[edgeNumber] = vert2;
            weig[edgeNumber] = (int) (Math.random() * (inf) + 1);
            edgePanel.setValue(edgeNumber + 1);
            data.l = l;
            data.r = r;
            data.weights = weig;
        }

        newEdge.setPoints(0, 0, 0, 0);
        activeVertex1 = -1;
        activeVertex2 = -1;
        updateView(1000, 0);
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseClicked(MouseEvent e) {

        if (editedEdge == -1) return;

        int x = e.getX();
        int y = e.getY();
        Rectangle incRect = editInc.getBounds();
        Rectangle decRect = editDec.getBounds();
        Rectangle delRect = editDel.getBounds();

        if (delRect.contains(x, y)) {
            int[] l = new int[edgeNumber - 1];
            int[] r = new int[edgeNumber - 1];
            int[] weig = new int[edgeNumber - 1];
            int j = 0;
            for (int i = 0; i < edgeNumber; i++) {
                if (i != editedEdge) {
                    l[j] = data.l[i];
                    r[j] = data.r[i];
                    weig[j] = data.weights[i];
                    j++;
                }
            }
            edgePanel.setValue(edgeNumber - 1);
            data.l = l;
            data.r = r;
            data.weights = weig;
            editInc.setBounds(0, 0, 0, 0);
            editDec.setBounds(0, 0, 0, 0);
            editDel.setBounds(0, 0, 0, 0);
            editedEdge = -1;
            updateView(-1, 0);
            return;
        }

        if (incRect.contains(x, y)) {
            if (data.weights[editedEdge] <= inf - 1) data.weights[editedEdge]++;
            updateView(-1, 0);
        }
        if (decRect.contains(x, y)) {
            if (data.weights[editedEdge] > 1) data.weights[editedEdge]--;
            updateView(-1, 0);
        }
    }

    /**
     * Shows or hides weights.
     * @param bShow Whether to show weights.
    */
    public void showWeights( boolean bShow ) {
        bShowWeights = bShow;
        showEdgeParam = 2 + (bShow ? 1 : 0);
    }

}