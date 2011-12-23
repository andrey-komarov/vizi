package ru.ifmo.vizi.ApproxTour;

import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.widgets.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.*;
import java.util.Stack;

import ru.ifmo.vizi.ApproxTour.widgets.*;
/**
 * ApproxTour applet.
 *
 * @author  Georgiy Korneev
 * @version $Id: ApproxTourVisualizer.java,v 1.5 2004/03/23 16:40:01 geo Exp $
 */
public final class ApproxTourVisualizer extends Base
    implements AdjustmentListener, MouseMotionListener, MouseListener
{
    /**
     * ApproxTour automata instance.
     */
    private final ApproxTour auto;

    /**
     * ApproxTour automata data.
     */
    private final ApproxTour.Data data;

    private final AdjustablePanel elements;

    private ShapeStyle[] styleSet;

    private ShapeStyle[] vertexStyleSet;

    /**
     * X&Y of vertices.
     */
    private int[] x = new int[22];
    private int[] y = new int[22];
    private int lineStyle[][] = new int[22][22];

    private Stack vertexes;
    private Stack edges;

    private final MultiButton switchButton;

    private int vertexRadius = 10;
    private int boldness = 2;
    private int nb;
    private int activeVertex1 = 0;
    private int activeVertex = 0;
    private int width;
    private int height;

    private boolean editable = false;
    private boolean beforeMST = true;
    private boolean drawCycle = false;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * Creates a new ApproxTour visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public ApproxTourVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new ApproxTour(locale);
        data = auto.d;
        data.visualizer = this;


        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);

        vertexes = new Stack();
        edges = new Stack();

        styleSet = ShapeStyle.loadStyleSet(config, "line");
        nb = config.getInteger("bars");
        vertexStyleSet = ShapeStyle.loadStyleSet(config, "vertexesStyleSet");

        switchButton = new MultiButton(config, new String[] {"button-edit", "button-vis"}) {
            protected int click(int state) {
                if(!editable)reset();
                editable = !editable;
                updateView(true, 0, false);
                return 1 - state;
            }
        };

        clientPane.addMouseMotionListener(this);
        clientPane.addMouseListener(this);

        createInterface(auto);
        data.count = elements.getValue();
        update(true);
        randomize(0);
        adjustVertex();
        adjustEdges();
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
        bottomPanel.add(elements);
        bottomPanel.add(new HintedButton(config, "button-random"){
            protected void click() {
                reset();
                randomize(0);
                updateView(true, 0, false);
            }
        });

        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("vertexCountComment"), 
                            new Integer(elements.getMinimum()),
                            new Integer(elements.getMaximum())
                        )
                    ).append(" */\n");

                    buffer.append("VertexCount = ").append(data.count).append("\n");

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("VertexListComment"), 
                            new Integer(0),
                            new Integer(nb)
                        )
                    ).append(" */\n");

                    buffer.append("Vertices = ").append("\n");
                    for (int i = 1; i <= data.count; i++) {
                        buffer.append(x[i]).append(" ").append(y[i]).append("\n");
                    }

                    buffer.append("\n/* ").append(
                        config.getParameter("StepComment")
                    ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        };

        saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("VertexCount");
                tokenizer.expect("=");

                int count = tokenizer.nextInt(
                    elements.getMinimum(), 
                    elements.getMaximum()
                );

                tokenizer.expect("Vertices");
                tokenizer.expect("=");

                int x1[] = new int[22];
                int y1[] = new int[22];

                for (int i = 1; i <= count; i++) {
                    x1[i] = tokenizer.nextInt(0, nb);
                    y1[i] = tokenizer.nextInt(0, nb);
                    for (int j = 1; j < i; j++) {
                        if (((x1[j] == x1[i]) && (y1[i] == y1[j])) || 
                                ((x1[j] == y1[i]) && (y1[j] == x1[i]))) {
                                         Object[] args = new Object[] {
                                                new Integer( tokenizer.lineno() ),
                                                new Integer( x1[i] ),
                                                new Integer( y1[i] )
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

                reset();
                elements.setValue(count);
                x = x1;
                y = y1;
                data.count = count;
                updateArray();
                adjustVertex();
                adjustEdges();

                updateView(true, -1, false);
                while ((auto.getStep() < step) && (!auto.isAtEnd())) auto.stepForward(0);

                return true;
            }
        };

        bottomPanel.add(switchButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Randomizes graph.
     */
    private void randomize(int from) {
        int i = from;
        while(i <= data.count) {
            i++;
            x[i] = (int) (Math.random() * nb) + 1;
            y[i] = (int) (Math.random() * nb) + 1;
            int j = 1;
            while((j < i-1)&&(!((x[i]==x[j])&&(y[i]==y[j])))) {
                j++;
                if((x[i]==x[j])&&(y[i]==y[j])){i--;};
            }

        };
        updateArray();
    }

    private void updateArray(){
        for(int i = 1; i <= data.count; i++){
            for(int j = 1; j <= data.count; j++){
                data.a[i][j] = (int)Math.sqrt((x[i] - x[j])*(x[i] - x[j]) + (y[i] - y[j])*(y[i] - y[j]));
            };
        }
    }

    /**
     * Restarts algorithm.
     */

    private void reset() {
        while (!auto.isAtStart()) auto.stepBackward(0);
    }

    /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == elements) {
            reset();
            int oldVal = data.count;
            data.count = e.getValue();
            adjustVertex();
            adjustEdges();
            randomize(oldVal);
            update(true);
            updateView(true, 0, false);
        }
    }

    private void adjustEdges(){

        while (edges.size() > 0) {
            clientPane.remove((Component) edges.pop());
        }
        for(int i=1; i < data.count; i++){
            for(int j=i+1; j <= data.count; j++){
                MyLine line = new MyLine(styleSet, boldness);
                clientPane.add(line);
                edges.push(line);
            }
        }
    }

    private void adjustVertex(){

        while (vertexes.size() > 0) {
            clientPane.remove((Component) vertexes.pop());
        }
        while (vertexes.size() < data.count) {
            Ellipse ellipse = new Ellipse(vertexStyleSet);
            clientPane.add(ellipse);
            vertexes.push(ellipse);
        }
    }

    public void updateView(boolean before/*Prim*/, int active/*Vertex*/, boolean draw/*OnlyCycle*/){
        if (!auto.isAtStart()) {
                editable = false;
                switchButton.setState(0);
        }

        if(before!=beforeMST){
            beforeMST = before;
            adjustEdges();
        };

        if(active != 0){
            for(int i = 1; i <= data.count; i++){
                Ellipse ellipse = (Ellipse) vertexes.elementAt(i-1);
                if(i == active){ellipse.setStyle(1);}else{ellipse.setStyle(data.q[i]?2:0);};
            };
        };

        drawCycle = draw;

        clientPane.doLayout();
    }

    private int xToCP(int a){
        return (int)(((double)a/(double)nb)*(double)(width - 2*vertexRadius) + vertexRadius);
    }
    private int yToCP(int a){
        return (int)(((double)a/(double)nb)*(double)(height - 2*vertexRadius) + vertexRadius);
    }

    private int xFromCP(int a){
        return (int)((double)((a-vertexRadius)*nb)/(double)(width - 2*vertexRadius));
    }
    private int yFromCP(int a){
        return (int)((double)((a-vertexRadius)*nb)/(double)(height - 2*vertexRadius));
    }
    
    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */

    protected void layoutClientPane(int clientWidth, int clientHeight) {
        height = clientHeight;
        width = clientWidth;
        clientPane.repaint();
        vertexRadius = Math.min (clientHeight/(30), clientWidth/(30));
        for (int i = 0; i < data.count; i++) {
            Ellipse ellipse = (Ellipse) vertexes.elementAt(i);
            if(i!=activeVertex1 - 1){
                int ex = xToCP(x[i+1]);
                int ey = yToCP(y[i+1]);
                ellipse.setBounds(ex - vertexRadius, ey - vertexRadius,
                                2 * vertexRadius, 2 * vertexRadius);
            };
            ellipse.setMessage(Integer.toString(i + 1));
            ellipse.adjustFontSize("8");
        }

        for(int i = 1; i<=data.count; i++){
            for(int j = 1; j<=data.count; j++){
                if((!data.endAlg)&&(!editable)){
                    lineStyle[i][j] = (((beforeMST)||(data.p[i]==j)||(data.p[j]==i)))?0:-1;
                }else{
                    lineStyle[i][j] = -1;
                };
            }
        }

        if(drawCycle){
            for(int i = 1; i < data.c[0]; i++){
                lineStyle[data.c[i]][data.c[i+1]] = 1;
                lineStyle[data.c[i+1]][data.c[i]] = 1;
            }
        }

        int i1 = 0;

        for(int i=1; i<data.count; i++){
            for(int j=i+1; j<=data.count; j++){
                MyLine line = (MyLine) edges.elementAt(i1);
                int x1, x2, y1, y2;
                double rat = 0.23;
                x1 = xToCP(x[i]);
                y1 = yToCP(y[i]);
                x2 = xToCP(x[j]);
                y2 = yToCP(y[j]);
                if(lineStyle[i][j] == -1){
                    line.setVisible(false);
                }else{
                    line.setVisible(true);
                    line.setStyle(lineStyle[i][j]);
                }
                line.setPoints(x1, y1, x2, y2);
                i1++;
            };
        }
    }


    private boolean containsPoint(Ellipse ellipse, Point point) {
        Rectangle rect = ellipse.getBounds();
        int xc = rect.x + rect.width / 2;
        int yc = rect.y + rect.height / 2;
        int x = point.x;
        int y = point.y;
        return ((xc - x) * (xc - x) + (yc - y) * (yc - y) < vertexRadius*vertexRadius);
    }

// ------------------------------MOUSE------------------------------ //
    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseDragged(MouseEvent e) {
        if ((!editable)||(activeVertex1 == 0)||((e.getModifiers() & MouseEvent.BUTTON1_MASK) == 0)) return;
        Point point = e.getPoint();
        Ellipse ellipse = (Ellipse) vertexes.elementAt(activeVertex1-1);
        int ex = point.x;
        int ey = point.y;
/*        if((ex < vertexRadius)||(ex > width - vertexRadius)||(ey < vertexRadius)||(ey > height - vertexRadius)){
        }else{
            ellipse.setBounds(ex - vertexRadius, ey - vertexRadius,
                                2 * vertexRadius, 2 * vertexRadius);
        };*/
        if(ex < vertexRadius){ex = vertexRadius;};
        if(ex > width - vertexRadius){ex = width - vertexRadius;};
        if(ey < vertexRadius){ey = vertexRadius;};
        if(ey > height - vertexRadius){ey = height - vertexRadius;};
            ellipse.setBounds(ex - vertexRadius, ey - vertexRadius,
                                2 * vertexRadius, 2 * vertexRadius);
        updateView(true, -1, false);
    
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mousePressed(MouseEvent e) {
        if ((!editable)||((e.getModifiers() & MouseEvent.BUTTON1_MASK) == 0)) return;
        Point point = e.getPoint();
        activeVertex1 = 0;
        for (int i = 1; i <= data.count; i++) {
            Ellipse ellipse = (Ellipse) vertexes.elementAt(i-1);
            if (containsPoint(ellipse, point)) {
                activeVertex1 = i; 
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
    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseReleased(MouseEvent e) {
    
        if ((!editable)||(activeVertex1 == 0)||((e.getModifiers() & MouseEvent.BUTTON1_MASK) == 0)) return;
        Point point = e.getPoint();
        int ex = point.x;
        int ey = point.y;
        Ellipse ellipse = (Ellipse) vertexes.elementAt(activeVertex1-1);

        if(ex < vertexRadius){ex = vertexRadius;};
        if(ex > width - vertexRadius){ex = width - vertexRadius;};
        if(ey < vertexRadius){ey = vertexRadius;};
        if(ey > height - vertexRadius){ey = height - vertexRadius;};
            ellipse.setBounds(ex - vertexRadius, ey - vertexRadius,
                                2 * vertexRadius, 2 * vertexRadius);

        x[activeVertex1] = xFromCP(ex);
        y[activeVertex1] = yFromCP(ey);
        activeVertex1 = 0; 
        updateArray();
        updateView(true, -1, false);

    }

    /**
     * Invoked on mouse event
     * @param e Event to process
    */
    public void mouseClicked(MouseEvent e) {
    }

// ------------------------------//MOUSE//------------------------------ //


}       
