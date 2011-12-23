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

class treeNode {
    boolean leaf;
    int n;
    char keys[];
    treeNode links[];

    int width;

    /**
     * Constructor
     */
    public treeNode() {
        this.leaf = false;
        this.n = 0;
        this.keys = new char[6];
        this.links = new treeNode[6];

        this.width = 0;
    }
}

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

    private treeNode selected;


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
    Choice combobox1;

    /**
     *
     */
    Choice combobox2;

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

        // ???
        data.root = new treeNode();
        data.root.leaf = true;
        data.root.n = 0;

        //for (char k = 'Z'; k >= 'A'; k--) {
        //    treeInsert(k);
        //}

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

//        bottomPanel.add(new HintedButton(config, "button-random") {
//            protected void click() {
//                randomize();
//            }
//        });

        combobox1 = new Choice();
        for (int i = 1; i <= Integer.parseInt(config.getParameter("choice1-col")); i++) {
            combobox1.add(config.getParameter("choice1-message" + i));
        }
        bottomPanel.add(combobox1);

        combobox2 = new Choice();
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
    public void treeSplitChild(treeNode x, int i, treeNode y) {
      // +???
        System.out.println("Split called");

        treeNode z = new treeNode();
        z.leaf = y.leaf;
        z.n = t - 1;
        for (int j = 1; j <= t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }
        if (!y.leaf) {
            for (int j = 1; j <= t; j++) {
                z.links[j] = y.links[j + t];
            }
        }
        y.n = t - 1;
        for (int j = x.n + 1; j >= i + 1; j--) {
            x.links[j + 1] = x.links[j];
        }
        x.links[i + 1] = z;
        for (int j = x.n; j >= i; j--) {
            x.keys[j + 1] = x.keys[j];
        }
        x.keys[i] = y.keys[t];
        x.n++;
    }

    public void treeInsert(char k) {
      // +???
      System.out.println("treeInsert called: " + k);
      treeNode r = data.root;
      if (r.n == 2 * t - 1) {
          treeNode s = new treeNode();
          data.root = s;
          s.leaf = false;
          s.n = 0;
          s.links[1] = r;
          treeSplitChild(s, 1, r);
          treeInsertNonfull(s, k);
      } else {
          treeInsertNonfull(r, k);
      }
    }

    public void treeInsertNonfull(treeNode x, char k) {
      // +???
        System.out.println("InsertNonfull called");
        int i = x.n;
        if (x.leaf) {
            while ((i >= 1) && (k < x.keys[i])) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = k;
            x.n++;
        } else {
            while ((i >= 1) && (k < x.keys[i])) {
              i--;
            }
            i++;
            if (x.links[i].n == 2 * t - 1) {
                treeSplitChild(x, i, x.links[i]);
                if (k > x.keys[i]) {
                    i++;
                }
            }
            treeInsertNonfull(x.links[i], k);
       }
    }

    /**
     *
     */
    private void re(treeNode Node) {
        int sum = 0;

        Node.width = 5 + Node.n * 14 + 5;

        for (int i = 1; i <= Node.n + 1; i++) {
            if (Node.links[i] != null) {
                re(Node.links[i]);
                sum += Node.links[i].width;
            }
        }

        if (Node.width < sum) {
            Node.width = sum;
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
        cellNum++;

        return rect;
    }

    private void showNode(treeNode Node, int x, int y) {
        Rect rect;
        myLine line;
        int nx;

        rect = nextCell();
        rect.setStyle(0);
        rect.setMessage("");
        if (Node.n == 0) {
            nx = x + (Node.width - 10) / 2;
            rect.setBounds(nx, y, 10, 20);
        } else {
            nx = x + (Node.width - Node.n * 14) / 2;
            rect.setBounds(nx, y, Node.n * 14, 20);
        }

        for (int i = 1; i <= Node.n; i++) {
            rect = nextCell();
            rect.setStyle(1);
            if (Node == selected) { rect.setStyle(2); }
            rect.setMessage(Node.keys[i] + "");
            rect.setBounds(nx + 14 * (i - 1), y, 14, 20);
        }

        int sum = 0;
        for (int i = 1; i <= Node.n + 1; i++) {
            if (Node.links[i] != null) {
                showNode(Node.links[i], x + sum, y + 40);
            
                if (lineNum + 1 > lines.size()) {
                    line = new myLine(styleSet);
                    lines.push(line);
                    clientPane.add(line);
                } else {
                    line = (myLine) lines.elementAt(lineNum);
                }
                line.setStyle(2);
                line.setPoints(nx, y + 20, x + sum + Node.links[i].width / 2, y + 40);
                lineNum++;
            
                nx += 14;
                sum += Node.links[i].width;
            }
        }
    }

    public void showTree(treeNode sel) {
        selected = sel;
        clientPane.doLayout();
    }

    public void showDebug(int i) {
        i = data.root.links[i].n;
        System.out.println(Integer.toString(i));
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
    }

    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    public void layoutClientPane(int clientWidth, int clientHeight) {
        clientPane.repaint();
        cellNum = 0;
        lineNum = 0;
        re(data.root);
        showNode(data.root, (clientWidth - data.root.width) / 2, 10);
    }
}
