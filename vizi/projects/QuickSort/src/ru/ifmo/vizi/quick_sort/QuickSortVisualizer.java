package ru.ifmo.vizi.quick_sort;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Stack;

/**
 * QuickSort applet.
 */

public final class QuickSortVisualizer 
    extends Base implements AdjustmentListener {

    /**
     * QuickSort automata instance.
     */
    private final QuickSort auto;

    /**
     * QuickSort automata data.
     */
    private final QuickSort.Data data;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * Array for sorting
     */
    private final Stack mainArray;       

    /**
     * Left borders of sorting array
     */
    private final Stack leftBordersArray;

    /**
     * Right borders of sorting array
     */
    private final Stack rightBordersArray;

    /**
     * Number of elements in array.
     */
    private final AdjustablePanel elements;

     /**
     * Rectangle that contains barier value.
     */
    private final Rect rectBarier;

    /**
     * Barier message tamplate.
     */
    private final String barierMessage;

    /**
     * Rectangle that contains stack message.
     */
    private final Rect rectStack;

    /**
     * Stack message tamplate.
     */
    private final String stackMessage;

    /**
     * Maximal array value.
     */
    private final int maxValue;

    /**
     * Maximal array value string.
     */
    private final String maxValueString;

    /**
     * Array shape style set.
     */
    private final ShapeStyle[] styleSet;

    /**
     * Array with initial elements of array to be sorted.
     * It's necessary for quick Save/load,
     * because a lot of algorhitm steps needs time.
     */
    private int[] f;

    /**
     * Line which connects swapping elements
     */
    DoubleArrow dArrowSwap;

    /**
     * First element to swap (pointer for arrow)
     */
    int fromElement;

    /**
     * Second element to swap (pointer for arrow)
     */
    int toElement;

    /**
     * Is line shown
     */
    boolean isShown;

    /**
     * Creates a new QuickSort visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public QuickSortVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new QuickSort(locale);
        data = auto.d;
        data.visualizer = this;
        mainArray = new Stack();
        leftBordersArray = new Stack();
        rightBordersArray = new Stack();
        styleSet = ShapeStyle.loadStyleSet(config, "array");
        dArrowSwap = new DoubleArrow(styleSet);

        barierMessage = config.getParameter("barier-message");
        rectBarier = new Rect(
                     new ShapeStyle[]{new ShapeStyle(config, "barier-style", 
                     styleSet[0])}, "barier"
        );
        stackMessage = config.getParameter("stack-message");
        rectStack = new Rect(
                    new ShapeStyle[]{new ShapeStyle(config, "stack-style", 
                    styleSet[0])}, stackMessage
        );
        clientPane.add(rectBarier);
        clientPane.add(rectStack);
        clientPane.add(dArrowSwap);
        rectBarier.adjustSize();
        rectBarier.setLocation(10, 10);
        rectStack.adjustSize();
        rectStack.setLocation(10, 10);
        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);
        maxValue = config.getInteger("max-value");
        maxValueString = config.getParameter("max-value-string",
                                         Integer.toString(maxValue));
        setArraySize(elements.getValue());

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
                    int[] a = auto.d.a;
                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ArrayLengthComment"), 
                            new Double(elements.getMinimum()),
                            new Double(elements.getMaximum())
                        )
                    ).append(" */\n");

                    buffer.append("ArrayLength = ").append(a.length).append("\n");

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ElementsComment"), 
                            new Integer(0),
                            new Integer(maxValue)
                        )
                    ).append(" */\n");

                    buffer.append("Elements = ");
                    for (int i = 0; i < f.length; i++) {
                        buffer.append(f[i]).append(" ");
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
                f = a;

                tokenizer.expect("Step");
                tokenizer.expect("=");
                rewind(tokenizer.nextInt());

                tokenizer.expectEOF();

                return true;
            }
        };
        return panel;
    }

    /**
     * Adjusts array size to match current model size.
     */
    private void adjustArraySize() {
        int size = auto.d.a.length;
        if (mainArray.size() == 0) {            
            Rect rect = new Rect(styleSet);
            mainArray.push(rect);
            clientPane.add(rect);
        }
        while (mainArray.size() < size + 1) {
            Rect rect = new Rect(styleSet);
            mainArray.push(rect);
            clientPane.add(rect);
        }
        while (mainArray.size() > size + 1) {
            clientPane.remove( (Component) mainArray.pop());
        }
        while (leftBordersArray.size() < size) {
            Rect rect = new Rect(styleSet);
            leftBordersArray.push(rect);
            clientPane.add(rect);
        }
        while (leftBordersArray.size() > size) {
            clientPane.remove( (Component) leftBordersArray.pop());
        }
        while (rightBordersArray.size() < size) {
            Rect rect = new Rect(styleSet);
            rightBordersArray.push(rect);
            clientPane.add(rect);
        }
        while (rightBordersArray.size() > size) {
            clientPane.remove( (Component) rightBordersArray.pop());
        }
        clientPane.doLayout();
        elements.setValue(data.a.length);
        data.QSort_m = 0;
    }

    /**
     * Sets new array size.
     *
     * @param size new array size.
     */
    private void setArraySize(int size) {
        auto.d.a = new int[size];
        f = new int[size];
        auto.d.globL = new int[size];
        auto.d.globR = new int[size];
        randomize();
        adjustArraySize();
    }

    /**
     * Randomizes array values.
     */
    private void randomize() {
        for (int i = 0; i < data.a.length; i++) {
            f[i] = data.a[i] = (int) (Math.random() * maxValue) + 1;
        }
        data.ptr = 0;
        rewind(0);
    }
 
    /**
     * Rewinds algorithm to the specified step.
     *
     * @param step step of the algorith to rewind to.
     */
    private void rewind(int step) {
        adjustArraySize();
        auto.toStart();
        updateMainArray(-1, -1, -1, -1, 0, 0, 0, 0);
        while (!auto.isAtEnd() && auto.getStep() < step) {
            auto.stepForward(0);
        }
    }

    /**
     * Updates array view.
     *
     * @param leftCell left cell of highlighted part of array.
     * @param rightCell right cell of highlighted part of array.
     * @param passiveElementToSwap first element to swap.
     * @param activeElementToSwap second element to swap.
     * @param otherStyle style of not highlighted part of array.
     * @param centerStyle style of highlighted part of array.
     * @param swapPassiveStyle style of passive element.
     * @param swapActiveStyle style of active element.
     */
    public void updateMainArray(int leftCell, int rightCell, 
                                int passiveElementToSwap, 
                                int activeElementToSwap,
                                int otherStyle, int centerStyle, 
                                int swapPassiveStyle, int swapActiveStyle) {
        rectBarier.setMessage(I18n.message(barierMessage, new Integer(data.QSort_m)));
        Rectangle mb = rectBarier.getBounds();

        int currentCellStyle;                
        Rect rect = (Rect) mainArray.elementAt(0);
        rect.setMessage(" ");
        currentCellStyle = otherStyle;
        if (-1 == passiveElementToSwap) {
            currentCellStyle = swapPassiveStyle;
        }   
        if (-1 == activeElementToSwap) {
            currentCellStyle = swapActiveStyle;
        }
        rect.setStyle(currentCellStyle);
/*        rect = (Rect) mainArray.elementAt(mainArray.size() - 1);
        rect.setMessage(" ");
        currentCellStyle = otherStyle;
        if (mainArray.size() - 1 == passiveElementToSwap) {
            currentCellStyle = swapPassiveStyle;
        }   
        if (mainArray.size() - 1 == activeElementToSwap) {
            currentCellStyle = swapActiveStyle;
        }
        rect.setStyle(currentCellStyle);*/

        for (int i = 0; i < data.a.length; i++) {
            rect = (Rect) mainArray.elementAt(i + 1);
            rect.setMessage(Integer.toString(data.a[i]));
            currentCellStyle = otherStyle;
            if ((leftCell <= i) && (i <= rightCell)) {
                currentCellStyle = centerStyle;
            }
            if (i == passiveElementToSwap) {
                currentCellStyle = swapPassiveStyle;
            }   
            if (i == activeElementToSwap) {
                currentCellStyle = swapActiveStyle;
            }
            rect.setStyle(currentCellStyle);
        }
        update(true);
    }

    /**
     * Updates stack view
     *
     * @param amnt amount of elements in stack
     * @param eStyle style of elements in stack except last one
     * @param lastStyle style of last element in stack
     */
    public void updateStack(int amnt, int eStyle, int lastStyle) {
        int size = auto.d.a.length;
        for (int i = 0; i < amnt; i++) {
            Rect rect = (Rect) leftBordersArray.elementAt(i);
            rect.setMessage(Integer.toString(data.globL[i] + 1));
            if (i == (amnt - 1)) { rect.setStyle(lastStyle); } else { rect.setStyle(eStyle); };
        }
        for (int i = amnt; i < size; i++) {
            Rect rect = (Rect) leftBordersArray.elementAt(i);
            rect.setMessage(Integer.toString(0));
            rect.setStyle(4);
        }
        for (int i = 0; i < amnt; i++) {
            Rect rect = (Rect) rightBordersArray.elementAt(i);
            rect.setMessage(Integer.toString(data.globR[i] + 1));
            if (i == (amnt - 1)) { rect.setStyle(lastStyle); } else { rect.setStyle(eStyle); };
        }
        for (int i = amnt; i < size; i++) {
            Rect rect = (Rect) rightBordersArray.elementAt(i);
            rect.setMessage(Integer.toString(0));
            rect.setStyle(4);
        }
        clientPane.doLayout();
    }

    /**
     * Shows or hides arrow
     *
     * @param element1 element where the arrow starts
     * @param element2 element where the arrow ends
     * @param status defines whether the arroy is shown or not
     */
    protected void updateArrow(int element1, int element2, boolean status) {
        fromElement = element1;
        toElement = element2;
        isShown = status;
        clientPane.doLayout();
    }

    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    protected void layoutClientPane(int clientWidth, int clientHeight) {
        int n = mainArray.size();
        rectBarier.setBounds(15, 5, 
                             clientWidth - 30, clientHeight / 10 + 5);
        rectBarier.adjustFontSize(I18n.message(barierMessage, 
                                               new Integer(maxValue)));

        rectBarier.setLocation(15, 5);  
        Rectangle mb = rectBarier.getBounds();
        
        int width = Math.min(Math.round(clientWidth / (n + 1)), Math.round(clientHeight * 21 / 110));
        int height = width;
        int y = mb.y + mb.height + height / 5;
        int x = (clientWidth - width * n) / 2;

        for (int i = 0; i < n; i++) {
            Rect rect = (Rect) mainArray.elementAt(i);
            rect.setBounds(x + i * width, y, width + 1, height + 1);
            rect.adjustFontSize(maxValueString);
        }
        if (isShown) {
            dArrowSwap.setBounds(0, 0, clientWidth, clientHeight);
            dArrowSwap.setColor(Color.black);
            int lineHeight = height / 4;
            int x1 = x + width * fromElement + width / 3;
            int y1 = y - lineHeight - 1;
            int x2 = x + width * toElement + width * 2 / 3;
            int y2 = y - lineHeight - 1;
            dArrowSwap.setCoords(x1, y1, x2, y2, lineHeight);
        } else {
            dArrowSwap.setColor(Color.white);
        }

        int space = width / 10;
        space = (space < 5) ? 5 : space;
        rectStack.setBounds(15, y + height + space, 
                            clientWidth - 30, clientHeight / 10 + 5);
        rectStack.adjustFontSize(I18n.message(stackMessage, new Integer(0)));
        rectStack.setLocation(15, y + height + space);

        mb = rectStack.getBounds();
        for (int i = 0; i < data.ptr; i++) {
            Rect rect = (Rect) leftBordersArray.elementAt(i);
            rect.setBounds(x + i * width, y + height + mb.height + space * 2, width + 1, height + 1);
            rect.adjustFontSize(maxValueString);
        }
        for (int i = 0; i < data.ptr; i++) {
            Rect rect = (Rect) rightBordersArray.elementAt(i);
            rect.setBounds(x + i * width, y + height + mb.height + height + space * 3, width + 1, height + 1);
            rect.adjustFontSize(maxValueString);
        }
    }

    /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent event) {
        if (event.getSource() == elements) {
            setArraySize(event.getValue());
        }
    }


}