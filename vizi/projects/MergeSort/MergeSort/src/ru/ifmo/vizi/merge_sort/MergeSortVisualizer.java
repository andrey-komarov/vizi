package ru.ifmo.vizi.merge_sort;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Stack;

/**
 * MergeSort visualizer.
 *
 * @author Dmirty Paraschenko
 * @version 2004/05/11, 21:49:12
 */
public final class MergeSortVisualizer
        extends Base
        implements AdjustmentListener, ActionListener {
    /**
     * Merge sort automata instance.
     */
    private final MergeSort auto;

    /**
     * Merge sort automata data.
     */
    private final MergeSort.Data data;

    /**
     * Cells with main array elements.
     */
    private final Stack mainArray;

    /**
     * Cells with elements of left auxiliary array.
     */
    private final Stack leftAuxArray;

    /**
     * Cells with elements of right auxiliary array.
     */
    private final Stack rightAuxArray;

    /**
     * Cells with left borders of sorted array parts.
     */
    private final Stack leftBorder;

    /**
     * Cells with right borders of sorted array parts.
     */
    private final Stack rightBorder;

    /**
     * Cells with edit fields needed for edit mode.
     */
    private final Stack editModeStack;

    /**
     * Array of Stacks with horisontal brackets.
     */
    private Stack[] bracketsArray;

    /**
     * Number of elements in array.
     */
    private final AdjustablePanel elements;

    /**
     * Maximal possible value of array's element.
     */
    private final int maxValue;

    /**
     * Maximal possible width of array's element.
     */
    private final String maxValueString;

    /**
     * Main array shape style set.
     */
    private final ShapeStyle[] mainStyleSet;

    /**
     * Auxiliary arrays shape style set.
     */
    private final ShapeStyle[] auxStyleSet;

    /**
     * Shape style set of stack with borders.
     */
    private final ShapeStyle[] borderStyleSet;

    /**
     * Shape style set of brackets.
     */
    private final ShapeStyle[] bracketsStyleSet;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * Position of first array left cell.
     */
    private int firstCellL;

    /**
     * Position of first array right cell.
     */
    private int firstCellC;

    /**
     * Position of second array right cell.
     */
    private int firstCellR;

    /**
     * Array with initial elements of array to be sorted.
     * It's necessary for quick Save/load,
     * because a lot of algorhitm steps needs time.
     */
    private int[] c;

    /**
     * Template of first array message.
     */
    private final String firstArrayMessage;

    /**
     * Template of second array message.
     */
    private final String secondArrayMessage;

    /**
     * Template of message of stack with borders.
     */
    private final String stackMessage;

    /**
     * Template of message of edit mode.
     */
    private final String editModeMessage;

    /**
     * Height of stack message.
     */
    private final int stackHeight;

    /**
     * Rectangle which contains first array message.
     */
    private final Rect firstArrayRect;

    /**
     * Rectangle which contains second array message.
     */
    private final Rect secondArrayRect;

    /**
     * Rectangle which contains stack message.
     */
    private final Rect stackRect;

    /**
     * Visibility of auxiliary array messages.
     */
    private int auxArrayVisible;

    /**
     * Visibility of stack messages.
     */
    private int stackVisible;

    /**
     * Is edit mode enabled.
     */
    private boolean editMode;

    /**
     * Button Start/Stop edit mode.
     */
    private MultiButton editModeButton;

    /**
     * Is last pressed button right arrow.
     * This variable needed to right draw action after forward step of algorhitm.
     */
    private boolean buttonPressed;

    /**
     * Depth of recursion.
     */
    private int recursionDepth;

    /**
     * State of recursion.
     */
    private int recursionState;

    /**
     * Should or not to draw states (needed for faster working Save/Load dialog).
     */
    private boolean drawStates;

    /**
     * Width of holes between array cells.
     */
    private int holeWidth;
    
    /**
     * Height of brackets.
     */
    private int bracketHeight;

    /**
     * Minimal height of one line of text.
     */
    private int minimalTextHeight;

    /**
     * Total visibility of stack.
     */
    private boolean showStack;

    /**
     * Stack width (in percents).
     */
    private final int stackWidth;

    /**
     * Main area top indent.
     */
    private final int topIndent;

    /**
     * Main area bottom indent.
     */
    private final int bottomIndent;

    /**
     * Main area left indent.
     */
    private final int leftIndent;

    /**
     * Main area right indent.
     */
    private final int rightIndent;

    /**
     * Stack area top indent.
     */
    private final int topStackIndent;

    /**
     * Stack area bottom indent.
     */
    private final int bottomStackIndent;

    /**
     * Stack area left indent.
     */
    private final int leftStackIndent;

    /**
     * Stack area right indent.
     */
    private final int rightStackIndent;

    /**
     * Minimal width of stack message
     */
    private final int minimalStackTextWidth;

    /**
     * Minimal width of auxiliary array messages
     */
    private final int minimalAuxArraysTextWidth;

    private boolean loaded;
    /**
     * Creates a new Merge Sort visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public MergeSortVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new MergeSort(locale);
        data = auto.d;
        data.visualizer = this;
        mainArray = new Stack();
        leftAuxArray = new Stack();
        rightAuxArray = new Stack();
        leftBorder = new Stack();
        rightBorder = new Stack();
        editModeStack = new Stack();
        bracketsArray = null;
        loaded = false;

        auxArrayVisible = 0;
        stackVisible = 0;
        editMode = false;
        buttonPressed = false;
        recursionDepth = 0;
        drawStates = true;

        mainStyleSet = ShapeStyle.loadStyleSet(config, "main-array");
        auxStyleSet = ShapeStyle.loadStyleSet(config, "aux-arrays");
        borderStyleSet = ShapeStyle.loadStyleSet(config, "border-arrays");
        bracketsStyleSet = ShapeStyle.loadStyleSet(config, "horisontal-brackets");

        firstArrayMessage = config.getParameter("first-array-message");
        secondArrayMessage = config.getParameter("second-array-message");
        stackMessage = config.getParameter("stack-message");

        int r = 1;
        for (int i = 0; i < stackMessage.length(); i++) {
            if (stackMessage.charAt(i) == '\n') {
              r++;
            }
        }
        stackHeight = r;
        firstArrayRect = new Rect(
                new ShapeStyle[] {new ShapeStyle(config, "array-message-style",
                                                 mainStyleSet[0])}
                , firstArrayMessage
                );
        secondArrayRect = new Rect(
                new ShapeStyle[] {new ShapeStyle(config, "array-message-style",
                                                 mainStyleSet[0])}
                , secondArrayMessage
                );
        stackRect = new Rect(
                new ShapeStyle[] {new ShapeStyle(config, "stack-message-style",
                                                 mainStyleSet[0])}
                , stackMessage
                );
        if (config.getBoolean("editMode-button-visibility")) {
            editModeMessage = config.getParameter("editMode-message");
        } else {
            editModeMessage = "";
        }

        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);

        holeWidth = config.getInteger("hole-width");
        bracketHeight = config.getInteger("bracket-height");
        minimalTextHeight = config.getInteger("minimal-text-height");

        maxValue = config.getInteger("max-value");
        maxValueString = config.getParameter("max-value-string",
                                             Integer.toString(maxValue));
        
        int sw = config.getInteger("stack-width");
        stackWidth = (sw < 0) ? 0 : ((sw > 100) ? 100: sw);

        topIndent = config.getInteger("top-indent");
        bottomIndent = config.getInteger("bottom-indent");
        leftIndent = config.getInteger("left-indent");
        rightIndent = config.getInteger("right-indent");

        topStackIndent = config.getInteger("top-stack-indent");
        bottomStackIndent = config.getInteger("bottom-stack-indent");
        leftStackIndent = config.getInteger("left-stack-indent");
        rightStackIndent = config.getInteger("right-stack-indent");

        minimalStackTextWidth = config.getInteger("minimal-stack-text-width");
        minimalAuxArraysTextWidth = config.getInteger("minimal-aux-arrays-text-width");

        setArraySize(elements.getValue());

        createInterface(auto);
    }

    /**
     * Creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    public Component createControlsPane() {
        Panel panel = new Panel(new BorderLayout());
        
        panel.add(new AutoControlsPane(config, auto, forefather, true),
                  BorderLayout.NORTH);
        
        Panel centerPanel = new Panel();

        if (config.getBoolean("editMode-button-visibility")) {
            editModeButton = new MultiButton(config, new String[] {
                                                     "button-startEditMode", 
                                                     "button-stopEditMode"});
            editModeButton.addActionListener(this);
            centerPanel.add(editModeButton);
        };

        if (config.getBoolean("stack-checkBox-visibility")) {
            showStack = true;
            Checkbox cb = new java.awt.Checkbox(config.getParameter("checkBox-message"), 
                                                true);
            cb.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    showStack = e.getStateChange() == 1;
                    if (!showStack) {
                        clientPane.remove(stackRect);
                    } else {
                        if (stackVisible != 0) {
                            clientPane.add(stackRect);
                     showStack(recursionDepth, recursionState, true);
                        }
                    }
                    clientPane.repaint();
                    clientPane.doLayout();
                }
            });
            centerPanel.add(cb);
        } else {
            showStack = false;
        }
        centerPanel.add(new HintedButton(config, "button-random") {
            protected void click() {
                stopEditMode();
                randomize();
            }
        });

        centerPanel.add(elements);
        panel.add(centerPanel, BorderLayout.CENTER);
        if (config.getBoolean("button-ShowSaveLoad")) {
            centerPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    stopEditMode();
                    saveLoadDialog.setComment("");
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    int[] a = auto.d.a;
                    buffer.append("/* ").append(
                            I18n.message(
                            config.getParameter("ArrayLengthComment"),
                            new Double(elements.getMinimum()),
                            new Double(elements.getMaximum())
                            )
                            ).append(" */\n");

                    buffer.append("ArrayLength = ").append(a.length).append(
                            "\n");
                    buffer.append("/* ").append(
                            I18n.message(
                            config.getParameter("ElementsComment"),
                            new Integer(0),
                            new Integer(maxValue)
                            )
                            ).append(" */\n");
                    buffer.append("Elements = ");
                    for (int i = 0; i < a.length; i++) {
                        buffer.append(c[i]).append(" ");
                    }
                    buffer.append("\n/* ").append(
                            config.getParameter("StepComment")
                            ).append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());
                    saveLoadDialog.show(buffer.toString());
                }
            });
        }
        centerPanel.add(elements);

        saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                loaded = true;
                drawStates = false;
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("ArrayLength");
                tokenizer.expect("=");
                int[] a = new int[tokenizer.nextInt(
                        (int) elements.getMinimum(),
                        (int) elements.getMaximum()
                        )];

                tokenizer.expect("Elements");
                tokenizer.expect("=");
                for (int i = 0; i < a.length; i++) {
                    a[i] = tokenizer.nextInt(0, maxValue);
                }
                auto.d.a = a;
                c = a;
                int size = a.length;
                int ls = 1;
                while (size > 0) {
                    ls++;
                    size = size / 2;
                }
                auto.d.l = new int[ls];
                auto.d.r = new int[ls];

                tokenizer.expect("Step");
                tokenizer.expect("=");
                rewind(tokenizer.nextInt());

                tokenizer.expectEOF();

                drawStates = true;
                clientPane.doLayout();
                loaded = false;
                return true;
            }
        };
        return panel;
    }

    /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent event) {
        if (event.getSource() == elements) {
            int value = event.getValue();
            if (!editMode) {
                setArraySize(value);
            } else {
                while (editModeStack.size() > value) {
                    clientPane.remove((Component) editModeStack.pop());
                }
                while (editModeStack.size() < value) {
                    TextField text = new TextField((new Integer((int) (Math.random() * maxValue) + 1)).toString());
                    text.setBackground(mainStyleSet[0].getFillColor());
                    text.addKeyListener(new KeyListener() {
                        public void keyPressed(KeyEvent e) {
                            if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || 
                                (e.getKeyCode() == KeyEvent.VK_LEFT)) {
                                buttonPressed = true;
                            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                stopEditMode();
                            }
                        }
                        public void keyReleased(KeyEvent e) {
                            if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || 
                                (e.getKeyCode() == KeyEvent.VK_LEFT)) {
                                buttonPressed = false;
                            }
                        }
                        public void keyTyped(KeyEvent e) {
                        }
                    });
                    editModeStack.push(text);
                    clientPane.add(text);
                }
                clientPane.doLayout();
            }
        }
    }

    /**
     * Adjusts array size to match current model size.
     */
    private void adjustArraySize() {
        int size = auto.d.a.length;
        while (mainArray.size() < size) {
            Rect rect = new Rect(mainStyleSet);
            mainArray.push(rect);
            clientPane.add(rect);
        }
        while (mainArray.size() > size) {
            clientPane.remove((Component) mainArray.pop());
        }
        int n = (size + 1) / 2;
        while (leftAuxArray.size() > n) {
            clientPane.remove((Component) leftAuxArray.pop());
        }
        while (leftAuxArray.size() < n) {
            Rect rect = new Rect(auxStyleSet);
            leftAuxArray.push(rect);
            clientPane.add(rect);
        }
        while (rightAuxArray.size() > n) {
            clientPane.remove((Component) rightAuxArray.pop());
        }
        while (rightAuxArray.size() < n) {
            Rect rect = new Rect(auxStyleSet);
            rightAuxArray.push(rect);
            clientPane.add(rect);
        }
        n = auto.d.l.length;
        while (leftBorder.size() > n) {
            clientPane.remove((Component) leftBorder.pop());
        }
        while (leftBorder.size() < n) {
            Rect rect = new Rect(borderStyleSet);
            leftBorder.push(rect);
            clientPane.add(rect);
        }
        while (rightBorder.size() > n) {
            clientPane.remove((Component) rightBorder.pop());
        }
        while (rightBorder.size() < n) {
            Rect rect = new Rect(borderStyleSet);
            rightBorder.push(rect);
            clientPane.add(rect);
        }
        clientPane.repaint();
        clientPane.doLayout();
        elements.setValue(data.a.length);
    }

    /**
     * Sets new array size.
     *
     * @param size new size of array.
     */
    private void setArraySize(int size) {
        int ls = 1;
        int tmp = size;
        while (tmp > 0) {
            ls++;
            tmp = tmp / 2;
        }
        auto.d.h = 0;
        if (bracketsArray != null) {
            for (int r = 0; r < bracketsArray.length; r++) {
                while (bracketsArray[r].size() > 0) {
                    clientPane.remove((Component) bracketsArray[r].pop());
                }
            }
        }
        bracketsArray = new Stack[ls];
        bracketsArray[0] = new Stack();
        bracketsArray[0].push(new HorisontalBracket(bracketsStyleSet, 0, size - 1));
        clientPane.add((HorisontalBracket) bracketsArray[0].peek());
        for (int r = 1; r < ls; r++) {
            bracketsArray[r] = new Stack();
            int p = 0;
            while (true) {
                if (p >= bracketsArray[r - 1].size()) {
                    break;
                }
                HorisontalBracket bracket = (HorisontalBracket) bracketsArray[r - 1].elementAt(p);
                if (bracket.left != bracket.right) {
                    int m = (bracket.left + bracket.right) / 2;
                    bracketsArray[r].push(new HorisontalBracket(bracketsStyleSet, bracket.left, m));
                    clientPane.add((HorisontalBracket) bracketsArray[r].peek());
                    bracketsArray[r].push(new HorisontalBracket(bracketsStyleSet, m + 1, bracket.right));
                    clientPane.add((HorisontalBracket) bracketsArray[r].peek());
                }
                p++;
            }
        }
        if (!loaded) {
            auto.d.a = new int[size];
            c = new int[size];
            auto.d.l = new int[ls];
            auto.d.r = new int[ls];
            randomize();
        }
        adjustArraySize();
    }

    /**
     * Randomizes array values.
     */
    private void randomize() {
        for (int i = 0; i < data.a.length; i++) {
            data.a[i] = (int) (Math.random() * maxValue) + 1;
            c[i] = data.a[i];
        }
        rewind(0);
    }

    /**
     * Rewinds algorithm to the specified step.
     *
     * @param step step of the algorith to rewind to.
     */
    private void rewind(int step) {
        hideAuxArrays(false);
        hideStack();
        adjustArraySize();
        auto.toStart();
        recursionDepth = 0;
        updateMainArray(0, 0, data.a.length, 0, 0, 0, 0);
        while (!auto.isAtEnd() && auto.getStep() < step) {
            auto.stepForward(0);
        }
    }

    /**
     * Hides stack and stack message.
     */
    public void hideStack() {
        if (stackVisible != 0) {
            for (int i = 0; i < leftBorder.size(); i++) {
                Rect rectl = (Rect) leftBorder.elementAt(i);
                rectl.setStyle(0);
                Rect rectr = (Rect) rightBorder.elementAt(i);
                rectr.setStyle(0);
            }
            recursionDepth = 0;
            stackVisible = 0;
            clientPane.remove(stackRect);
            clientPane.repaint();
        }
    }

    /**
     * Shows stack and stack message.
     *
     * @param depth depth of recursion (number of elements in the stack)
     * @param state state of recursion (number of sorter subarrays)
     * @param brackets should or not to update brackets
     */
    public void showStack(int depth, int state, boolean brackets) {
        recursionDepth = depth;
        recursionState = state;
        if (brackets) {
            updateBrackets(depth, state);
        }
        if (!showStack) {
            if (stackVisible == 0) {
                stackVisible = 1;
            }
            return;
        }
        for (int r = 0; r < leftBorder.size(); r++) {
            Rect rectl = (Rect) leftBorder.elementAt(r);
            rectl.setMessage(Integer.toString(data.l[r] + 1));
            rectl.setStyle((r <= depth) ? ((r == depth) ? 2 : 1) : 0);
            Rect rectr = (Rect) rightBorder.elementAt(r);
            rectr.setMessage(Integer.toString(data.r[r] + 1));
            rectr.setStyle((r <= depth) ? ((r == depth) ? 2 : 1) : 0);
        }
        if (stackVisible == 0) {
            clientPane.add(stackRect);
            stackVisible = 1;
        }
        clientPane.repaint();
    }

    /**
     * Updates brackets color.
     *
     * @param depth depth of recursion (number of elements in the stack)
     * @param state state of recursion (number of sorter subarrays)
     */
    public void updateBrackets(int depth, int state) {
        for (int r = 0; r < bracketsArray.length; r++) {
            if (r <= depth) {
                for (int i = 0; i < bracketsArray[r].size(); i++) {
                    HorisontalBracket bracket = (HorisontalBracket) bracketsArray[r].elementAt(i);
                    if (bracket.left < data.l[r]) {
                        bracket.setStyle(1);
                    } else if ((bracket.left == data.l[r]) && (bracket.right == data.r[r])) {
                        bracket.setStyle(2);
                    } else {
                        bracket.setStyle(0);
                    }
                }
            } else {
                if (depth == -1) {
                    if (state != 0) {
                        state = 1;
                    }
                    for (int i = 0; i < bracketsArray[r].size(); i++) {
                        ((HorisontalBracket) bracketsArray[r].elementAt(i)).setStyle(state);
                    }
                } else {
                    int m = (state == 0) ? data.l[depth] : ((state == 1) ? 
                        (data.l[depth] + data.r[depth]) / 2 + 1 : 
                        data.r[depth] + 1);
                    for (int i = 0; i < bracketsArray[r].size(); i++) {
                        HorisontalBracket bracket = (HorisontalBracket) bracketsArray[r].elementAt(i);
                        if (bracket.left < m) {
                            bracket.setStyle(1);
                        } else {
                            bracket.setStyle(0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates main array view.
     *
     * @param leftCell left cell to be showed.
     * @param currentCell array cell situated between left and right cells.
     * @param rightCell right cell to be showed.
     * @param leftStyle style of cells [leftCell, currentCell - 1]
     * @param currentStyle style of current cell
     * @param rightStyle style of cells [currentCell + 1, rightCell]
     * @param normalStyle style of other cells of array.
     */
    public void updateMainArray(int leftCell, int currentCell, int rightCell,
                             int leftStyle, int currentStyle, int rightStyle,
                             int normalStyle) {
        for (int i = 0; i < data.a.length; i++) {
            Rect rect = (Rect) mainArray.elementAt(i);
            rect.setMessage(Integer.toString(data.a[i]));
            if ((leftCell <= i) && (i <= rightCell)) {
                if (i < currentCell) {
                    rect.setStyle(leftStyle);
                } else if (i == currentCell) {
                    rect.setStyle(currentStyle);
                } else {
                    rect.setStyle(rightStyle);
                }
            } else {
                rect.setStyle(normalStyle);
            }
        }
        update(true);
    }

    /**
     * Hides both auxiliary arrays.
     *
     * @param update update visualizer view.
     */
    public void hideAuxArrays(boolean update) {
        clientPane.remove(firstArrayRect);
        clientPane.remove(secondArrayRect);
        for (int i = 0; i < leftAuxArray.size(); i++) {
            Rect rectl = (Rect) leftAuxArray.elementAt(i);
            rectl.setStyle(4);
            Rect rectr = (Rect) rightAuxArray.elementAt(i);
            rectr.setStyle(4);
        }
        auxArrayVisible = 0;
        if (update) {
            clientPane.repaint();
        }
    }

    /**
     * Sets borders of both auxiliary arrays
     *     ([leftCell, centerCell] & [centerCell + 1, rightCell]).
     *
     * @param leftCell left cell of first auxiliary array.
     * @param centerCell right cell of first auxiliary array.
     * @param rightCell right cell of second auxiliary array.
     */
    public void showAuxArrays(int leftCell, int centerCell, int rightCell) {
        firstCellL = leftCell;
        firstCellC = centerCell;
        firstCellR = rightCell;
        if (auxArrayVisible != 2) {
            clientPane.add(firstArrayRect);
            clientPane.add(secondArrayRect);
            auxArrayVisible = 2;
        }
    }

    /**
     * Updates view of first auxiliary array.
     *
     * @param currentCell current cell of array (0..length - 1).
     * @param leftStyle style of cells at the left of current cell.
     * @param currentStyle style of current cell.
     * @param rightStyle style of cells at the right of current cell.
     */
    private void updateFirstAuxArray(int currentCell, int leftStyle,
                                     int currentStyle, int rightStyle) {
        int n = leftAuxArray.size();
        for (int i = 0; i < n; i++) {
            Rect rect = (Rect) leftAuxArray.elementAt(i);
            if (i > (firstCellC - firstCellL)) {
                rect.setStyle(4);
                continue;
            }
            rect.setMessage(Integer.toString(data.Merge_b[firstCellL + i]));
            if (i < currentCell) {
                rect.setStyle(leftStyle);
            } else if (currentCell < i) {
                rect.setStyle(rightStyle);
            } else {
                rect.setStyle(currentStyle);
            }
        }
    }

    /**
     * Updates view of second auxiliary array.
     *
     * @param currentCell current cell of array (0..length - 1).
     * @param leftStyle style of cells at the left of current cell.
     * @param currentStyle style of current cell.
     * @param rightStyle style of cells at the right of current cell.
     */
    private void updateSecondAuxArray(int currentCell, int leftStyle,
                                      int currentStyle, int rightStyle) {
        int n = rightAuxArray.size();
        for (int i = 0; i < n; i++) {
            Rect rect = (Rect) rightAuxArray.elementAt(i);
            if (i > (firstCellR - firstCellC - 1)) {
                rect.setStyle(4);
                continue;
            }
            rect.setMessage(Integer.toString(data.Merge_b[firstCellC + 1 + i]));
            if (i < currentCell) {
                rect.setStyle(leftStyle);
            } else if (currentCell < i) {
                rect.setStyle(rightStyle);
            } else {
                rect.setStyle(currentStyle);
            }
        }
    }

    /**
     * Updates view of auxiliary arrays.
     *
     * @param firstCell current cell of first array.
     * @param secondCell current cell of second array.
     * @param leftStyle style of cells at the left of current ones.
     * @param rightStyle style of cells at the right of current ones.
     * @param firstStyle style of first array current cell.
     * @param secondStyle style of seond array curent cell.
     */
    public void updateAuxArrays(int firstCell, int secondCell,
                                int leftStyle, int rightStyle,
                                int firstStyle, int secondStyle) {
        updateFirstAuxArray(firstCell - firstCellL, leftStyle,
                            firstStyle, rightStyle);
        updateSecondAuxArray(secondCell - firstCellC - 1, leftStyle,
                             secondStyle, rightStyle);
        update(true);
    }

    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    protected void layoutClientPane(int clientWidth, int clientHeight) {
        if (!drawStates) {
            return;
        }
        if (!editMode) {
            int n = mainArray.size();
            int nn = leftBorder.size();
            int cw = (showStack) ? (clientWidth) * (100 - stackWidth) / 100 : clientWidth;
            int size = Math.round((cw - (leftIndent + rightIndent)) / (n + (data.l.length * 2 * holeWidth) / 100));
            size = size < (clientHeight - (topIndent + bottomIndent) - auto.d.l.length * bracketHeight) / 4 ?
                   size : (clientHeight - (topIndent + bottomIndent) - auto.d.l.length * bracketHeight) / 4;
            int y = topIndent + (clientHeight - (topIndent + bottomIndent) + auto.d.l.length * bracketHeight - (4 * size)) / 2;
            int x = leftIndent + (cw - (leftIndent + rightIndent) - size * n) / 2;
            int[] state = new int[n];
            for (int i = 0; i < n; i++) {
                state[i] = 0;
            }
            int sum = 0;
            for (int i = 1; i <= recursionDepth; i++) {
                if (auto.d.l[i] > 0) {
                    if (state[auto.d.l[i] - 1] == 0) {
                        state[auto.d.l[i] - 1] = 1;
                        sum += 1;
                    }
                }
                if (state[auto.d.r[i]] == 0) {
                    state[auto.d.r[i]] = 1;
                    sum += 1;
                }
            }
            int tx = x * 100 - (sum * holeWidth * size / 2);
            for (int i = 0; i < n; i++) {
                Rect rect = (Rect) mainArray.elementAt(i);
                rect.setBounds(tx / 100, y, size + 1, size + 1);
                rect.adjustFontSize(maxValueString);
                tx += (100 + state[i] * holeWidth) * size;
            }
            for (int r = 0; r < bracketsArray.length; r++) {
                for (int i = 0; i < bracketsArray[r].size(); i++) {
                    HorisontalBracket bracket = (HorisontalBracket) bracketsArray[r].elementAt(i);
                    bracket.setCoords(((Rect) mainArray.elementAt(bracket.left)).getBounds().x, 
                                      ((Rect) mainArray.elementAt(bracket.right)).getBounds().x + size + 1,
                                      y - (bracketHeight + 1) * (r + 1), bracketHeight);
                }
            }

            Rectangle mb1 = firstArrayRect.getBounds();
            Rectangle mb2 = secondArrayRect.getBounds();
            mb1.width = size + (cw - (leftIndent + rightIndent) - size * (leftAuxArray.size())) / 2 - leftIndent - 3 > minimalAuxArraysTextWidth ?
                        size + (cw - (leftIndent + rightIndent) - size * (leftAuxArray.size())) / 2 - leftIndent - 3 : minimalAuxArraysTextWidth;
            mb1.height = size > minimalTextHeight ? size : minimalTextHeight;
            mb2.width = mb1.width;
            mb2.height = mb1.height;
            firstArrayRect.setBounds(mb1);
            secondArrayRect.setBounds(mb2);
            if (firstArrayMessage.length() >= secondArrayMessage.length()) {
                firstArrayRect.adjustFontSize(firstArrayMessage);
                secondArrayRect.adjustFontSize(firstArrayMessage);
            } else {
                firstArrayRect.adjustFontSize(secondArrayMessage);
                secondArrayRect.adjustFontSize(secondArrayMessage);
            }
            x = size + (cw - size * (leftAuxArray.size()) - sum) / 2;
            y = y + size * 5 / 3;
            firstArrayRect.setLocation(x - mb1.width - 3,
                                       y + (size - mb1.height) / 2);
            for (int i = 0; i < leftAuxArray.size(); i++) {
                Rect rect = (Rect) leftAuxArray.elementAt(i);
                rect.setBounds(x + i * size, y, size + 1, size + 1);
                rect.adjustFontSize(maxValueString);
            }
            y = y + size * 4 / 3;
            secondArrayRect.setLocation(x - mb1.width - 3,
                                        y + (size - mb2.height) / 2);
            for (int i = 0; i < rightAuxArray.size(); i++) {
                Rect rect = (Rect) rightAuxArray.elementAt(i);
                rect.setBounds(x + i * size, y, size + 1, size + 1);
                rect.adjustFontSize(maxValueString);
            }
            if (showStack) {
                size = ((clientWidth * stackWidth / 100) - (leftStackIndent + rightStackIndent)) * 2 / 5;
                size = size > Math.round((clientHeight - (topStackIndent + bottomStackIndent)) / (nn + stackHeight)) ?
                        (int) Math.round((clientHeight - (topStackIndent + bottomStackIndent)) / (nn + stackHeight)) : size;
                x = clientWidth - rightStackIndent - ((clientWidth * stackWidth / 100) - (leftStackIndent + rightStackIndent) + (size * 5 / 2)) / 2;
                y = topStackIndent + (clientHeight - (topStackIndent + bottomStackIndent) - ((nn + stackHeight) * size)) / 2 + stackHeight * size;
                mb1 = stackRect.getBounds();
                mb1.width = (clientWidth * stackWidth / 100 - (leftStackIndent + rightStackIndent) / 3) > minimalStackTextWidth ? 
                            (clientWidth * stackWidth / 100 - (leftStackIndent + rightStackIndent) / 3) : minimalStackTextWidth;
                mb1.height = size > minimalTextHeight ? 
                             size * stackHeight : 
                             minimalTextHeight * stackHeight;
                stackRect.setBounds(mb1);
                stackRect.adjustFontSize(stackMessage);
                stackRect.setLocation(x + (size * 5 / 4) - (mb1.width / 2),
                                      y - mb1.height);
                for (int i = 0; i < leftBorder.size(); i++) {
                    Rect rect = (Rect) leftBorder.elementAt(i);
                    rect.setBounds(x, y + i * size, size + 1, size + 1);
                    rect.adjustFontSize(maxValueString);
                }
                x = x + size * 3 / 2;
                for (int i = 0; i < rightBorder.size(); i++) {
                    Rect rect = (Rect) rightBorder.elementAt(i);
                    rect.setBounds(x, y + i * size, size + 1, size + 1);
                    rect.adjustFontSize(maxValueString);
                }
            } else {
                for (int i = 0; i < rightBorder.size(); i++) {
                    Rect rect = (Rect) rightBorder.elementAt(i);
                    rect.setBounds(-10, -10, 0, 0);
                }
                for (int i = 0; i < leftBorder.size(); i++) {
                    Rect rect = (Rect) leftBorder.elementAt(i);
                    rect.setBounds(-10, -10, 0, 0);
                }
            }
        } else {
            int n = editModeStack.size();
            int nn = leftBorder.size();
            int cw = (showStack) ? (clientWidth) * (100 - stackWidth) / 100 : clientWidth;
            int size = Math.round((cw - (leftIndent + rightIndent)) / (n + (data.l.length * 2 * holeWidth) / 100));
            size = size < (clientHeight - (topIndent + bottomIndent) - auto.d.l.length * bracketHeight) / 4 ?
                   size : (clientHeight - (topIndent + bottomIndent) - auto.d.l.length * bracketHeight) / 4;
            int y = topIndent + (clientHeight - (topIndent + bottomIndent) + auto.d.l.length * bracketHeight - (4 * size)) / 2;
            int x = leftIndent + (cw - (leftIndent + rightIndent) - size * n) / 2;
            for (int i = 0; i < n; i++) {
                TextField text = (TextField) editModeStack.elementAt(i);
                text.setBounds(x + i * size, y, size + 1, size + 1);
                adjustTextFontSize(text, maxValueString);
            }
            for (int r = 0; r < bracketsArray.length; r++) {
                for (int i = 0; i < bracketsArray[r].size(); i++) {
                    ((HorisontalBracket) bracketsArray[r].elementAt(i)).setCoords(-5, -5, 0, 0);
                }
            }
        }
    }

    /**
     * Starts edit mode of array elements.
     * Updates comments, creates and shows text fields and so on.
     */
    public void startEditMode() {
        if (!editMode) {
            buttonPressed = false;
            hideAllArrays();
            editMode = true;
            while (editModeStack.size() > 0) {
                clientPane.remove((Component) editModeStack.pop());
            }
            for (int i = 0; i < c.length; i++) {
                TextField text = new TextField((new Integer(c[i])).toString());
                text.setBackground(mainStyleSet[0].getFillColor());
                text.addKeyListener(new KeyListener() {
                    public void keyPressed(KeyEvent e) {
                        if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || 
                            (e.getKeyCode() == KeyEvent.VK_LEFT)) {
                            buttonPressed = true;
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            stopEditMode();
                        }
                    }
                    public void keyReleased(KeyEvent e) {
                        if ((e.getKeyCode() == KeyEvent.VK_RIGHT) || 
                            (e.getKeyCode() == KeyEvent.VK_LEFT)) {
                            buttonPressed = false;
                        }
                    }
                    public void keyTyped(KeyEvent e) {
                    }
                });
                editModeStack.push(text);
                clientPane.add(text);
            }
            editModeButton.setState(1);
            setComment(editModeMessage);
            clientPane.doLayout();
        }
    }

    /**
     * Stops edit mode of array elements. 
     * Hides text fields, applies changes.
     */
    public void stopEditMode() {
        if (editMode) {
            editMode = false;
            boolean updated = false;
            if (c.length != editModeStack.size()) {
                updated = true;
            }
            int[] cc = new int[editModeStack.size()];
            for (int i = 0; i < cc.length; i++) {
                TextField text = (TextField) editModeStack.elementAt(i);
                String str = text.getText();
                str = str.trim();
                int val;
                try {
                    val = (new Integer(str)).intValue();
                } catch (Exception e) {
                    val = 0;
                };
                val = (val > maxValue) ? maxValue : ((val < 0) ? 0 : val);
                
				if (!updated && (c[i] != val)) {
                    updated = true;
                }
                cc[i] = val;
            }
            editModeButton.setState(0);
            if (updated) {
                setArraySize(cc.length);
                auto.d.a = (int[]) (cc.clone());
				c = (int[]) (cc.clone());
                auto.toStart();
                hideAuxArrays(false);
                for (int i = 0; i < leftBorder.size(); i++) {
                    Rect rectl = (Rect) leftBorder.elementAt(i);
                    rectl.setStyle(0);
                    Rect rectr = (Rect) rightBorder.elementAt(i);
                    rectr.setStyle(0);
                }
                stackVisible = 0;
                clientPane.remove(stackRect);
                clientPane.repaint();
            }
            while (editModeStack.size() > 0) {
                clientPane.remove((Component) editModeStack.pop());
            }
            drawStates = false;
            rewind(auto.getStep());
         drawStates = true;
            update(true);
        }
        clientPane.doLayout();
    }

    /**
     * Adjust TextField's font size to show its message.
     *
     * @param text given TextField.
     */
    public void adjustTextFontSize(TextField text, String maxMessage) {
        Dimension size = text.getSize();
        Font font = mainStyleSet[0].getTextFont();
        
        int l = 3;
        int r = 1000;
        while (l < r) {
            int m = (l + r + 1) / 2;
            FontMetrics metrics = text.getFontMetrics(new Font(font.getName(), font.getStyle(), m));
            Dimension minSize = new Dimension((int) Math.round(metrics.stringWidth(maxMessage) * 1.3), 
                                              (int) Math.round(metrics.getHeight() * 1.3));

            if (size.width < minSize.width || size.height < minSize.height) {
                r = m - 1;
            } else {
                l = m;
            }
        }
        text.setFont(new Font(font.getName(), font.getStyle(), l));
    }
    
    /**
     * Hides all Rect arrays on clientPane.
     */
    public void hideAllArrays() {
        hideStack();
        hideAuxArrays(false);
        for (int i = 0; i < mainArray.size(); i++) {
             ((Rect) mainArray.elementAt(i)).setStyle(10);
        }
        update(false);
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
     * Sets comment string.
     *
     * @param comment New comment.
     */
    public void setComment(String comment) {
        super.setComment(editMode ? editModeMessage : comment);
    }

    /**
     * Makes big small step forward.
     */
    public void doNextStep() {
        if (!editMode) {
            super.doNextStep();
        } else if (!buttonPressed) {
            stopEditMode();
            super.doNextStep();
        }
    }

    /**
     * Makes big step forward.
     */
    public void doNextBigStep() {
        if (!editMode) {
            super.doNextBigStep();
        } else if (!buttonPressed) {
            stopEditMode();
            super.doNextBigStep();
        }
    }

    /**
     * Makes big small step backward.
     */
    public void doPrevStep() {
        if (!editMode) {
            super.doPrevStep();
        } else if (!buttonPressed) {
            stopEditMode();
            super.doPrevStep();
        }
    }

    /**
     * Makes big step backward.
     */
    public void doPrevBigStep() {
        if (!editMode) {
            super.doPrevBigStep();
        } else if (!buttonPressed) {
            stopEditMode();
            super.doPrevBigStep();
        }
    }
}


