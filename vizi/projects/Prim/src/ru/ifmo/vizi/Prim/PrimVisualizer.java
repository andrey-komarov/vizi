package ru.ifmo.vizi.Prim;

import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.widgets.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import ru.ifmo.vizi.Prim.widgets.*;

public final class PrimVisualizer extends Base 
    implements AdjustmentListener, MouseMotionListener, MouseListener
{
    
    /**
     * Prim automata instance.
     */
    private final Prim auto;

    /**
     * Prim automata data.
     */
    private final Prim.Data data;

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
      Edge to be added.
    */
    private myLine newEdge;

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
     * Vector of {@link myLine}.
     */
    private Stack lines;

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
     * Widest possible string matching to weight.
     */
    private String widestString;

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
     * Symbol of infinity;
     */
    private final String INFINITY_SYMBOL = "\u221E";

    /**
      Shows if the user deletes an edge
    */                                      
    private boolean deleteEdge = false;

    /**
     * First vertex of actived edge.
    */
    private int activeEdge1 = 0;

    /**
     * First vertex of edited edge.
    */
    private int editedEdge1 = 0;

    /**
     * Second vertex of edited edge.
    */
    private int editedEdge2 = 0;

    /**
     * Corner between the neighboring vertexes.
    */
    private double alpha;

    /**
     * Radius of weights circles.
    */
    private int weightRadius;

    /**
     * Radius of buttons of editing.
    */
    private int editRadius;

    /**
     * Graph's radius.
    */
    private int radiusX;

    /**
     * Graph's radius.
    */
    private int radiusY;

    /**
     * Point of graph's center.
    */
    private int xc;

    /**
     * Point of graph's center.
    */
    private int yc;

    /**
      * Constant for construction the graph.
    */
    private double constRat = 0.21;

    /**
      * Shows if the graph is editable by user.
    */                                      
    private boolean editable = false;

    /**
     * Button to switch visualize/edit modes.
     * {@link MultiButton}  
    */
    private final MultiButton switchButton;

    /**
     * Number of edges of graph.
    */
    private int edgeNumber;

    /**
     * Creates a new Prim visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public PrimVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new Prim(locale);
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
                  auto.getController().rewind(0);
                  updateView();
                  editable = !editable;
                  return 1 - state;
            }
        };

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

        newEdge = new myLine(styleSet, boldness);
        newEdge.setStyle(1);
        newEdge.setBold(1);
        newEdge.setBounds(0, 0, 0 ,0);
        clientPane.add(newEdge);

        widestString = config.getParameter("max-value-string");
        edgeNumber = 4;
        
        edges = new Stack();
        vertexes = new Stack();
        lines = new Stack();

        changeVertexNumber(vertexPanel.getValue());
        
        clientPane.addMouseMotionListener(this);
        clientPane.addMouseListener(this);

        edgePanel.setMinimum(4);
        edgePanel.setMaximum(10);
        data.countVertex = 5;

        createInterface(auto);
        
        randomize(true);
    }

    public Component createControlsPane() {
        Panel panel = new Panel(new BorderLayout());
        panel.add(new AutoControlsPane(config, auto, forefather, false), BorderLayout.CENTER);
        Panel bottomPanel = new Panel();
        bottomPanel.add(vertexPanel);
        bottomPanel.add(edgePanel);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(new HintedButton(config, "button-random"){
                    protected void click() {
                        randomize(true);
                    }
        });

        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("VertexNumberComment"), 
                            new Integer(vertexPanel.getMinimum()),
                            new Integer(vertexPanel.getMaximum())
                        )
                    ).append(" */\n");

                    buffer.append("VertexNumber = ").append(auto.d.countVertex).append("\n");

                    buffer.append("/*").append(
                        I18n.message(
                            config.getParameter("EdgeNumberComment"),
                            new Integer(edgePanel.getMinimum()),
                            new Integer(edgePanel.getMaximum())
                        )
                    ).append("*/\n");
                
                    int l = 0;
                    for (int i = 1; i <= auto.d.countVertex; i++) {
                        for (int j = i+1; j <= auto.d.countVertex; j++) {
                            if (auto.d.a[i][j] > 0) l++;
                        }
                    }

                    buffer.append("EdgeNumber = ").append(l).append("\n");

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("EdgesListComment"), 
                            new Integer(1),
                            new Integer(vertexPanel.getValue()),
                            new Integer(1),
                            new Integer(99)
                        )
                    ).append(" */\n");

                    buffer.append("Edges = ").append("\n");
                    for (int i = 1; i <= auto.d.countVertex; i++) {
                        for (int j = i+1; j <= auto.d.countVertex; j++) {
                            if (auto.d.a[i][j] > 0) {
                                buffer.append(i).append(" ").append(j).append(" ").append(auto.d.a[i][j]).append("\n");
                            }
                        }
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

                int ll, rr, ww;

                int vertNumb = tokenizer.nextInt(
                    vertexPanel.getMinimum(), 
                    vertexPanel.getMaximum()
                );

                int[][] a = new int[21][21];

                tokenizer.expect("EdgeNumber");
                tokenizer.expect("=");

                int edgeNumb = tokenizer.nextInt(
                    vertNumb - 1,
                    Math.min((vertNumb * vertNumb - vertNumb) / 2, 20)                        
                );

                tokenizer.expect("Edges");
                tokenizer.expect("=");

                for (int i = 1; i <= edgeNumb; i++) {
                    ll = tokenizer.nextInt(1, vertNumb);
                    rr = tokenizer.nextInt(1, vertNumb);
                    ww = tokenizer.nextInt(1, 99);
                    if (a[ll][rr] > 0) {
                        Object[] args = new Object[] {
                            new Integer( tokenizer.lineno() ),
                            new Integer( ll ),
                            new Integer( rr )
                        };
                        System.out.println(config.getParameter("wrongLoad-message"));
                        String string = java.text.MessageFormat.format( config.getParameter("wrongLoad-message"), args);
                        throw new Exception( string );
                    }
                    a[ll][rr] = ww;
                    a[rr][ll] = ww;
                }

                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

                auto.d.a = a;
                auto.d.countVertex = vertNumb;
                vertexNumber = vertNumb;
                edgeNumber = edgeNumb;

                adjustVertexes();
                auto.getController().rewind(step);
                updateView();

                return true;
            }
        };

        bottomPanel.add(switchButton);

        return panel; 
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
     * Invoked on adjustment event.
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == vertexPanel) {
            changeVertexNumber(e.getValue());
        }
        if (e.getSource() == edgePanel) {
            changeEdgeNumber(e.getValue());
        }
        updateView();
    }

    /**
     * Adjusts number of vertexes to match current model number of vertexes.
     */
    private void adjustVertexes() {
        vertexNumber = data.countVertex;
        vertexPanel.setValue(vertexNumber);
    }

    /**
     * Sets new edges number.
     * @param size new edges number.
     */
    private void changeEdgeNumber(int newEdgeNumber) {
        while (!auto.isAtStart()) auto.stepBackward(0);
        edgeNumber = newEdgeNumber;
        randomize(false);
    }

   /**
     * Sets new vertexes number.
     * @param size new vertexes number.
     */
    private void changeVertexNumber(int newVertexNumber) {
        while (!auto.isAtStart()) auto.stepBackward(0);
        vertexNumber = newVertexNumber;
        data.countVertex = newVertexNumber;
        adjustVertexes();
        edgePanel.setMinimum(vertexNumber - 1);
        edgePanel.setMaximum(Math.min(20, vertexNumber * (vertexNumber - 1) / 2));
        randomize(true);
    }

    /**
      *  Construction of the random graph.
    */
    public void randomize(boolean clearGraph) {
        while (!auto.isAtStart()) auto.stepBackward(0);

        if (clearGraph) {
            for (int i = 0; i <= 20; i++) {
                data.q[i] = true;
                for (int j = 0; j <= 20; j++) {
                    data.a[i][j] = 0;
                }
            }

            int x, y, k;
            int[] qwe = new int[21];
            for (int i = 1; i <= vertexNumber; i++) qwe[i] = i;
            for (int i = 1; i <= vertexNumber; i++) {
                x = (int) (Math.random() * (vertexNumber)) + 1;
                y = (int) (Math.random() * (vertexNumber)) + 1;
                k = qwe[x];
                qwe[x] = qwe[y];
                qwe[y] = k;
            }
            for (int i = 2; i <= vertexNumber; i++) {
                data.a[qwe[i]][qwe[i-1]] = (int) (Math.random() * 99) + 1;
                data.a[qwe[i-1]][qwe[i]] = data.a[qwe[i]][qwe[i-1]];
            }
        }

        int x = 0, y = 0, k;
        while (true) {
            k = 0;
            for (int i = 0; i <= 20; i++) {
                for (int j = i + 1; j <= 20; j++) {
                    if (data.a[i][j] > 0) {
                        if ((i == 0)||(j == 0)||(i > vertexNumber)||(j > vertexNumber)||(i == j)) {
                            data.a[i][j] = 0;
                            data.a[j][i] = 0;
                        }
                        else {
                            k++;
                            x = i;
                            y = j;
                        }
                    }
                }
            }
            if (k == edgeNumber) break;
            if (k > edgeNumber) {
                data.a[x][y] = 0;
                data.a[y][x] = 0;
            }
            else {
                x = (int) (Math.random() * (vertexNumber)) + 1;
                y = (int) (Math.random() * (vertexNumber)) + 1;
                if ((x != y) && (data.a[x][y] == 0)) {
                    data.a[x][y] = (int) (Math.random() * 99) + 1;
                    data.a[y][x] = data.a[x][y];
                }
            }
        }
        updateView();
    }

    public void updateView() {
        if (!auto.isAtStart()) {
                editable = false;
                switchButton.setState(0);
        }

        clientPane.doLayout();
    }

    public void layoutClientPane(int clientWidth, int clientHeight) {
        if (vertexNumber == 0) return;
        clientPane.repaint();

        if (vertexNumber == 7) constRat = 0.25;
        int rectSize, graphWidth, graphHeight;
        vertexRadius = Math.min (clientHeight / 18, clientWidth / 18); 
        weightRadius = Math.min(clientHeight / 40, clientWidth / 40);
        editRadius = Math.min(clientHeight / 35, clientWidth / 35);
        graphWidth = clientWidth - 3 * vertexRadius;
        graphHeight = (int) (clientHeight * 0.9) - (int) (3 * vertexRadius);
        rectSize = (int) (clientHeight * 0.05) * 2;
        xc = graphWidth / 2 + (int) (1.5 * vertexRadius);
        yc = graphHeight / 2 + (int) (0.5 * vertexRadius);
        radiusX = (int) (xc - vertexRadius);
        radiusY = (int) (yc - vertexRadius - 2);
        alpha = 2 * Math.PI / vertexNumber;

        while (!(lines.empty())) {
            clientPane.remove((Component) lines.pop());
        } 

        while (!(edges.empty())) {
            Rect rect = new Rect(styleSet);
            rect = (Rect) edges.pop();  
            clientPane.remove((Component) rect);
        } 

        while (!(vertexes.empty())) {
            clientPane.remove((Component) vertexes.pop());
        }

        clientPane.remove((Component) newEdge);

        for (int i = 1; i <= vertexNumber; i++) {
            Ellipse ellipse = new Ellipse(vertexStyleSet);
            if (data.q[i]) {
                ellipse.setStyle(0);
            }
            else {
                ellipse.setStyle(4);
            }
            if ((data.step == 1) && (data.min == i)) { 
                ellipse.setStyle(3); 
            }
            int x = xc + (int) (radiusX * Math.sin(i * alpha));
            int y = yc - (int) (radiusY * Math.cos(i * alpha));
            ellipse.setBounds(x - vertexRadius, y - vertexRadius, 2 * vertexRadius, 2 * vertexRadius);
            ellipse.setMessage(Integer.toString(i));
            ellipse.adjustFontSize("10");
            if (data.step == 2) {
                for (int k = 1; k <= data.nstack; k++) {
                    if (i == data.st[k]) {
                        ellipse.setStyle(2);
                        break;
                    }
                }
            }
            clientPane.add(ellipse);
            vertexes.push(ellipse);
        }

        clientPane.add(newEdge);

        for (int i = 1; i <= vertexNumber; i++) {
            Rect rect = new Rect(styleSet);
            rect.setBounds((int) (1.5 * (i + 2) * rectSize), (int) (clientHeight - 1.5 * rectSize), (int) (1.5 * rectSize), rectSize);
            if (data.q[i]) {
                rect.setStyle(0);
            }
            else {
                rect.setStyle(4);
            }
            if ((data.step == 1) && (data.min == i)) {
                rect.setStyle(3);
            }
            if (data.step == 2) {
                for (int k = 1; k <= data.nstack; k++) {
                    if (i == data.st[k]) {
                        rect.setStyle(2);
                        break;
                    }
                }
            }
            if (data.key[i] == 1000000) {
                rect.setMessage(INFINITY_SYMBOL);
            }
            else {
                rect.setMessage(Integer.toString(data.key[i]));
            }
            rect.adjustFontSize(widestString);
            clientPane.add(rect);
            edges.push(rect);
        }

        {
            Rect rect = new Rect(styleSet);
            rect.setBounds((int) (1.5 * rectSize), (int) (clientHeight - 1.5 * rectSize), (int) (3 * rectSize), rectSize);
            rect.setStyle(5);
            rect.setMessage("Keys = ");
            rect.adjustFontSize(widestString);
            clientPane.add(rect);
            edges.push(rect);
        }

        for (int i = 1; i <= vertexNumber; i++) {
            Rect rect = new Rect(styleSet);
            rect.setBounds((int) (1.5 * (i + 2) * rectSize), (int) (clientHeight - 2.5 * rectSize), (int) (1.5 * rectSize), rectSize);
            rect.setStyle(5);
            rect.setMessage(Integer.toString(i));
            rect.adjustFontSize(widestString);
            clientPane.add(rect);
            edges.push(rect);
        }

        for (int i = 2; i <= vertexNumber; i++) {
            if (!(data.q[i])) {
                data.a[i][data.p[i]] = -data.a[i][data.p[i]];   
                data.a[data.p[i]][i] = -data.a[data.p[i]][i];   
            }
        }

        for (int i = 1; i <= vertexNumber; i++) {
            for (int j = i + 1; j <= vertexNumber; j++) {
                if (data.a[i][j] != 0) {
                    double rat = constRat;
                    if (i + 1 == j) rat = 0.35;
                    if ((i + j) % 2 == 1) rat = 1 - rat;
                    int x1, x2, y1, y2;
                    x1 = xc + (int) (radiusX * Math.sin(i * alpha));
                    y1 = yc - (int) (radiusY * Math.cos(i * alpha));
                    x2 = xc + (int) (radiusX * Math.sin(j * alpha));
                    y2 = yc - (int) (radiusY * Math.cos(j * alpha));

                    Rect rect = new Rect(styleSet);
                    rect = new Rect(styleSet);
                    rect.setBounds( (int) (x1 + (x2 - x1) * rat - weightRadius), 
                                       (int) (y1 + (y2 - y1) * rat - weightRadius),
                                        2 * weightRadius, 2 * weightRadius);
                    rect.adjustFontSize(widestString);
                    rect.setMessage(Integer.toString(Math.abs(data.a[i][j])));
                    if ((data.step != 5) || ((data.step == 5) && (data.a[i][j] < 0))) {
                        clientPane.add(rect);
                        edges.push(rect);
                    }
                }
            }
        }

        for (int i = 1; i <= vertexNumber; i++) {
            for (int j = i + 1; j <= vertexNumber; j++) {
                if (data.a[i][j] != 0) {
                    int x1, x2, y1, y2;
                    x1 = xc + (int) (radiusX * Math.sin(i * alpha));
                    y1 = yc - (int) (radiusY * Math.cos(i * alpha));
                    x2 = xc + (int) (radiusX * Math.sin(j * alpha));
                    y2 = yc - (int) (radiusY * Math.cos(j * alpha));

                    myLine line = new myLine(styleSet, boldness);
                    line.setPoints(x1, y1, x2, y2);
                    if (data.a[i][j] > 0) {
                        line.setStyle(1);
                        line.setBold(1);
                    }
                    else {
                        if ((data.step == 1) && ((data.min == i) || (data.min == j))) {
                            line.setStyle(3); 
                            line.setBold(3);
                        }
                        else {
                            line.setStyle(4);
                            line.setBold(3);
                        }
                    }
                    if (data.step == 2) {
                        for (int k = 1; k <= data.nstack; k++) {
                            if (((i == data.st[k])&&(j == data.p[data.st[k]]))||((j == data.st[k])&&(i == data.p[data.st[k]]))) {
                                line.setStyle(2);
                                line.setBold(3);
                                break;
                            }
                        }
                    }
                    if ((data.step != 5) || ((data.step == 5) && (data.a[i][j] < 0))) {
                        clientPane.add(line);
                        lines.push(line);
                    }
                }
            }
        }

        for (int i = 2; i <= vertexNumber; i++) {
            if (!(data.q[i])) {
                data.a[i][data.p[i]] = -data.a[i][data.p[i]];   
                data.a[data.p[i]][i] = -data.a[data.p[i]][i];   
            }
        }
        if ((editable) && (editedEdge1 != 0)) {
            int i = editedEdge1;
            int j = editedEdge2;
            double rat = constRat;
            if (i + 1 == j) rat = 0.35;
            if ((i + j) % 2 == 1) rat = 1 - rat;
            int x1, x2, y1, y2;
            x1 = xc + (int) (radiusX * Math.sin(i * alpha));
            y1 = yc - (int) (radiusY * Math.cos(i * alpha));
            x2 = xc + (int) (radiusX * Math.sin(j * alpha));
            y2 = yc - (int) (radiusY * Math.cos(j * alpha));
            int xx = (int) (x1 + (x2 - x1) * rat) + weightRadius;
            int yy = (int) (y1 + (y2 - y1) * rat - 1.5 * editRadius);
            editInc.setBounds(xx, yy, editRadius, editRadius);
            editDel.setBounds(xx, yy + editRadius, editRadius, editRadius);
            editDec.setBounds(xx, yy + 2 * editRadius, editRadius, editRadius);
        }
        else {
            editInc.setBounds(0, 0, 0, 0);
            editDec.setBounds(0, 0, 0, 0);
            editDel.setBounds(0, 0, 0 ,0);
        }
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseMoved(MouseEvent e) {
        if (!editable) return;
        int x = e.getX();
        int y = e.getY();

        for (int i = 1; i <= vertexNumber; i++) {
            for (int j = i + 1; j <= vertexNumber; j++) {
                if (data.a[i][j] != 0) {
                    double rat = constRat;
                    if (i + 1 == j) rat = 0.35;
                    if ((i + j) % 2 == 1) rat = 1 - rat;
                    int x1, x2, y1, y2;
                    x1 = xc + (int) (radiusX * Math.sin(i * alpha));
                    y1 = yc - (int) (radiusY * Math.cos(i * alpha));
                    x2 = xc + (int) (radiusX * Math.sin(j * alpha));
                    y2 = yc - (int) (radiusY * Math.cos(j * alpha));
                    int xx1 = (int) (x1 + (x2 - x1) * rat - weightRadius);
                    int yy1 = (int) (y1 + (y2 - y1) * rat - 1.5 * editRadius);
                    int xx2 = xx1 + 3 * editRadius;
                    int yy2 = yy1 + 3 * editRadius;
                    if ((x >= xx1) && (x <= xx2) && (y >= yy1) && (y <= yy2)) {
                        editedEdge1 = i;
                        editedEdge2 = j;
                        updateView();
                        return;
                    }
                }
            }
        }
        editedEdge1 = 0;
        editedEdge2 = 0;
        updateView();
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseDragged(MouseEvent e) {
        if (activeEdge1 == 0) return;
        if (!editable) return;
        Point point = e.getPoint();
        Ellipse ellipse = (Ellipse) vertexes.elementAt(activeEdge1-1);
        Rectangle rect = ellipse.getBounds();
        newEdge.setPoints(rect.x + rect.width / 2, rect.y + rect.height / 2, e.getX(), e.getY());
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mousePressed(MouseEvent e) {
        if (!editable) return;
        Point point = e.getPoint();
        activeEdge1 = 0;
        for (int i = 1; i <= vertexNumber; i++) {
            Ellipse ellipse = (Ellipse) vertexes.elementAt(i-1);
            if (containsPoint(ellipse, point)) {
                activeEdge1 = i; 
            } 
        }
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
        int vert2 = 0;
        boolean canAdd = false;
        Point point = e.getPoint();
            
        if (activeEdge1 != 0) {
            canAdd = true;
            for (int i = 1; i <= vertexNumber; i++) {
                Ellipse ellipse = (Ellipse) vertexes.elementAt(i-1);
                if (containsPoint(ellipse, point)) vert2 = i; 
            }   
            if (vert2 != activeEdge1) {
                if (data.a[activeEdge1][vert2] > 0) canAdd = false;
            } else canAdd = false;
        }

        if (edgePanel.getMaximum() == edgePanel.getValue()) canAdd = false;
        if (vert2 == 0) canAdd = false;

        if (canAdd) {
            int edgeNumb = edgePanel.getValue() + 1;
            data.a[activeEdge1][vert2] = (int) (Math.random() * 99) + 1;
            data.a[vert2][activeEdge1] = data.a[activeEdge1][vert2];
            deleteEdge = true;
            edgePanel.setValue(edgeNumb);
        }

        newEdge.setPoints(0, 0, 0, 0);
        activeEdge1 = 0;
        updateView();   
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseClicked(MouseEvent e) {

        if ((editable) && (editedEdge1 != 0)) {
            int x = e.getX();
            int y = e.getY();

            int i = editedEdge1;
            int j = editedEdge2;
            double rat = constRat;
            if (i + 1 == j) rat = 0.35;
            if ((i + j) % 2 == 1) rat = 1 - rat;
            int x1, x2, y1, y2;
            x1 = xc + (int) (radiusX * Math.sin(i * alpha));
            y1 = yc - (int) (radiusY * Math.cos(i * alpha));
            x2 = xc + (int) (radiusX * Math.sin(j * alpha));
            y2 = yc - (int) (radiusY * Math.cos(j * alpha));
            int xx = (int) (x1 + (x2 - x1) * rat) + weightRadius;
            int yy = (int) (y1 + (y2 - y1) * rat - 1.5 * editRadius);
            if ((x >= xx) && (x <= xx + editRadius) && (y >= yy) && (y < yy + editRadius)) {
                if (data.a[i][j] < 99) {
                    data.a[i][j]++;
                    data.a[j][i]++;
                    updateView();
                }
            }
            if ((x >= xx) && (x <= xx + editRadius) && (y >= yy + editRadius) && (y < yy + 2*editRadius)) {
                if (edgePanel.getMinimum() < edgePanel.getValue()) {
                    int edgeNumb = edgePanel.getValue() - 1;
                    data.a[i][j] = 0;
                    data.a[j][i] = 0;
                    deleteEdge = true;
                    edgePanel.setValue(edgeNumb);
                    editInc.setBounds(0, 0, 0, 0);
                    editDec.setBounds(0, 0, 0, 0);
                    editDel.setBounds(0, 0, 0, 0);
                    updateView();
                }

            }
            if ((x >= xx) && (x <= xx + editRadius) && (y >= yy + 2*editRadius) && (y <= yy + 3*editRadius)) {
                if (data.a[i][j] > 1) {
                    data.a[i][j]--;
                    data.a[j][i]--;
                    updateView();
                }
            }
        }
    }            
}