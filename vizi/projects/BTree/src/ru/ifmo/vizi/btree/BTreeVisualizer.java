package ru.ifmo.vizi.btree;

import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.widgets.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import ru.ifmo.vizi.btree.widgets.*;

/**
 * BTree applet.
 *
 * @author  Mandrikov Evgeniy
 */

public final class BTreeVisualizer 
    extends Base 
    implements AdjustmentListener
{
    /**
     * BTree automata instance.
     */
    private final BTree auto;

    /**
     * BTree automata data.
     */
    private final BTree.Data data;

    /**
     * Cells with array elements.
     * Vector of {@link Rect}.
     */
    private final Stack cells;

    private int cellNum;
    private int t = 2;

    /**
     * Vector of {@link Line}.
     */
    private final Stack lines;

    private int lineNum;

    private int selected;
    private int selType;

    private int[] width = new int[26];


    /**
     * Array shape style set.
     */
    private final ShapeStyle[] styleSet;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     *
     */
    myChoice combobox1;

    /**
     *
     */
    myChoice combobox2;

    /**
     * Creates a new BTree visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public BTreeVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new BTree(locale);
        data = auto.d;
        data.visualizer = this;
        cells = new Stack();
        lines = new Stack();

        styleSet = ShapeStyle.loadStyleSet(config, "tree");

        data.leaf[data.root] = true;

        createInterface(auto);
    }

    /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    public Component createControlsPane() {
        Container panel = new Panel(new BorderLayout());

        panel.add(new AutoControlsPane(config, auto, forefather, false), BorderLayout.CENTER);
        
        Panel bottomPanel = new Panel();


        bottomPanel.add(new HintedButton(config, "button-restart") {
            protected void click() {
/*    	auto.toStart();
    	clientPane.doLayout();*/
// !!!YYEESS!!!
auto.toStart();
auto.stepForward(0);
/*auto.drawState();
auto.stepBackward(0);*/
            }
        });


//        bottomPanel.add(new HintedButton(config, "button-random") {
//            protected void click() {
//                randomize();
//            }
//        });

        combobox1 = new myChoice() {
            public void click(){
                if (!auto.isAtStart()) {
                    auto.stepForward(1);
                    System.out.println("Click1!!!");
                }
                auto.toStart();
                auto.drawState();
            }
        };
        for (int i = 1; i <= Integer.parseInt(config.getParameter("choice1-col")); i++) {
            combobox1.add(config.getParameter("choice1-message" + i));
        }
        bottomPanel.add(combobox1);

        combobox2 = new myChoice() {
            public void click() {
                if (!auto.isAtStart()) {
                    auto.stepForward(1);
                    System.out.println("Click2!!!");
                }
                auto.toStart();
                auto.drawState();
            }
        };
        for (char i = 'A'; i <= 'Z'; i++) {
            combobox2.add(i+"");
        }
        bottomPanel.add(combobox2);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }


    /**
     *
     */
    public char get() {
        int i = combobox2.getSelectedIndex();
        char ch = 'A';
        ch += i;
        return ch;
    }

    private void re(int node) {
        int sum = 0;
        width[node] = 5 + data.n[node] * 14 + 5;
        for (int i = 1; i <= data.n[node] + 1; i++) {
            if (data.c[node][i] != 0) {
                re(data.c[node][i]);
                sum += width[data.c[node][i]];
            }
        }
        if (width[node] < sum) {
            width[node] = sum;
        }
    }

    private Rect nextCell() {
        Rect rect;
        if (cellNum + 1 > cells.size()) {
            rect = new Rect(styleSet);
            cells.push(rect);
            clientPane.add(rect);
        } else {
            rect = (Rect) cells.elementAt(cellNum);
        }
        rect.setStyle(0);
        cellNum++;
        return rect;
    }

    private myLine nextLine() {
        myLine line;
        if (lineNum + 1 > lines.size()) {
            line = new myLine(styleSet);
            lines.push(line);
            clientPane.add(line);
        } else {
            line = (myLine) lines.elementAt(lineNum);
        }
        line.setStyle(2);
        lineNum++;
        return line;
    }

    private void showNode(int node, int x, int y) {
        Rect rect;
        myLine line;
        int nx;

        rect = nextCell();
        rect.setMessage("");
        if (data.n[node] == 0) {
            nx = x + (width[node] - 10) / 2;
            rect.setBounds(nx, y, 10, 20);
        } else {
            nx = x + (width[node] - data.n[node] * 14) / 2;
            rect.setBounds(nx, y, data.n[node] * 14, 20);
        }

        if ((node == selected) && (selType == 1)) {
            rect = nextCell();
            rect.setStyle(3);
            rect.setMessage("");
            rect.setBounds(nx - 1, y - 1, data.n[node] * 14 + 2, 20 + 2);
        }

        for (int i = 1; i <= data.n[node]; i++) {
            rect = nextCell();
            rect.setStyle(1);
            if ((node == selected) && (selType == 2)) {
                rect.setStyle(2);
            }
            rect.setMessage(data.key[node][i] + "");
            rect.setBounds(nx + 14 * (i - 1), y, 14, 20);
        }

        int sum = 0;
        for (int i = 1; i <= data.n[node] + 1; i++) {
            if (data.c[node][i] != 0) {
                showNode(data.c[node][i], x + sum, y + 40);
            
                line = nextLine();
                line.setPoints(nx, y + 20, x + sum + width[data.c[node][i]] / 2 + 1, y + 40);
            
                nx += 14;
                sum += width[data.c[node][i]];
            }
        }
    }

    public void showTree(int sel, int typ) {
        selected = sel;
        selType = typ;
        clientPane.doLayout();
    }

    public void showDebug(int i) {
        System.out.println("DEBUG");
    }

    /**
     * Creates random BTree.
     */
    private void randomize() {
        // while (!auto.isAtStart()) auto.stepBackward(0);
    }

    /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent event) {
        System.out.println("ValueChanged!!!");
    }

    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    public void layoutClientPane(int clientWidth, int clientHeight) {
        while (!(lines.empty())) {
            clientPane.remove((Component) lines.pop());
        }
        while (!(cells.empty())) {
            clientPane.remove((Component) cells.pop());
        }
        clientPane.repaint();
        cellNum = 0;
        lineNum = 0;
        re(data.root);
        showNode(data.root, (clientWidth - width[data.root]) / 2, 10);
    }
}

/**
 * Мой Choice
 */
class myChoice extends Choice implements ItemListener{
    protected myChoice() {
	addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e){
        click();
    }

    protected void click() {
    }
}
