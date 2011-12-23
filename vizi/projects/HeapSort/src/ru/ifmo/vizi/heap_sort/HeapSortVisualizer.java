package ru.ifmo.vizi.heap_sort;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.Ellipse;
import ru.ifmo.vizi.base.widgets.Shape;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

/**
 * Heap sort applet.
 *
 * @author  Ilya Pimenov
 * @version $Id: HeapSortVisulizer.java, v 0.4 2004/04/24 22:40:01 mar Exp $
 */
public final class HeapSortVisualizer
    extends Base implements AdjustmentListener, ActionListener {

    /**
     * Heap Sort automata instance.
     */
    private final HeapSort auto;

    /**
     * Heap Sort automata data.
     */
    private final HeapSort.Data data;

    /**
     * Cells with array elements.
     * Vector of {@link Rect}.
     */
    private final Stack cells;

    /**
     * HeapPoints with array element.
     * Vector of {@link Ellipse}.
     */
    private final Stack heapPoints;

    /**
     * HeapLines
     * Vector of {@link Line}.
     */
    private final Stack heapLines;

    /**
     * Cells nums 
     * Vector of {@link Rect}
     */
    private final Stack cellsNums;

    /**
     * Points nums 
     * Vector of {@link Rect}
     */
    private final Stack heapPointsNums;

    /**
     * If cell was moved as sorted
     */
    private boolean wasMoved[];

    /**
     * Array with initial elements of array to be sorted.
     * Needed to make s/l dialog
     */
    private int[] initVals;

    /**
     * Number of elements in array.
     */
    private final AdjustablePanel elements;

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
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;

    /**
     * If array was loaded from the s/l dialog
     */
    private boolean loaded;

    /**
     * If edit mode is enabled.
     */
    private boolean editMode;

    /**
     * Whether visualistaion is drawn
     */
    private boolean drawIt;

    /**
     * Textfields for edit mode
     */
    private final Stack editModeStack;

    /**
     * Template of message for edit mode.
     */
    private final String editModeMessage;

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
     * Creates a new Heap Sort visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public HeapSortVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new HeapSort(locale);
        data = auto.d;
        data.visualizer = this;

        cells = new Stack();
        heapPoints = new Stack();
        heapLines = new Stack();
        cellsNums = new Stack();
        heapPointsNums = new Stack();
        editModeStack = new Stack();

        editMode = false;
        buttonPressed = false;
        drawIt = true;
        if (config.getBoolean("button-ShowEditMode")) {
            editModeMessage = config.getParameter("editMode-message");
        } else {
            editModeMessage = "";
        }

        styleSet = ShapeStyle.loadStyleSet(config, "array");

        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);

        maxValue = config.getInteger("max-value");
        maxValueString = config.getParameter("max-value-string", Integer.toString(maxValue));
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
      
        AutoControlPane myPane = new AutoControlsPane(config, auto, forefather, true), BorderLayout.CENTER;
        panel.add(myPane);
        panel.remove(myPane);

        Panel bottomPanel = new Panel();
        if (config.getBoolean("button-ShowEditMode")) {
            editModeButton = new MultiButton(config, new String[] {
                                                     "button-startEditMode", 
                                                     "button-stopEditMode"});
            editModeButton.addActionListener(this);
            bottomPanel.add(editModeButton);
        };
        bottomPanel.add(new HintedButton(config, "button-random"){
            protected void click() {
                stopEditMode();
                randomize();
            }
        });
        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    stopEditMode();
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();
                    int[] a = auto.d.a;
                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("ArrayLengthComment"), 
                            new Integer(elements.getMinimum()),
                            new Integer(elements.getMaximum())
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
                    for (int i = 0; i < initVals.length; i++) {
                        buffer.append(initVals[i]).append(" ");
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
                try {
                   drawIt = false;
                   loaded = true;
                   SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                   tokenizer.expect("ArrayLength");
                   tokenizer.expect("=");
                   int newLength =  tokenizer.nextInt(
                       elements.getMinimum(), 
                       elements.getMaximum()
                   );
                   int[] a = new int[newLength];
                   tokenizer.expect("Elements");
                   tokenizer.expect("=");
                   for (int i = 0; i < a.length; i++) {
                       a[i] = tokenizer.nextInt(0, maxValue);
                   }
                   tokenizer.expect("Step");
                   tokenizer.expect("=");
                   int step = tokenizer.nextInt();
                   tokenizer.expectEOF();
                   drawIt = true;
                   data.a = (int[]) (a.clone());
                   initVals = (int[]) (a.clone());
                   rewind(step);
                   clientPane.doLayout();
                   loaded = false;
               }finally {
                   drawIt = true;
                   loaded = false;
               }
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
        while (cells.size() < size) {
            Rect rect = new Rect(styleSet);
            cells.push(rect);
            clientPane.add(rect);
        }
        while (cells.size() > size) {
            clientPane.remove((Component) cells.pop());
        }

        while (cellsNums.size() < size){
            Rect rect = new Rect(styleSet);
            rect.setStyle(4);
            rect.setMessage(Integer.toString(cellsNums.size() + 1));
            cellsNums.push(rect);
            clientPane.add(rect);
        }
        while (cellsNums.size() > size){
            clientPane.remove((Component) cellsNums.pop());
        }

        while (heapPoints.size() < size) {
            Ellipse ellipse = new Ellipse(styleSet);
            heapPoints.push(ellipse);
            clientPane.add(ellipse);
        }
        while (heapPoints.size() > size) {
            clientPane.remove((Component) heapPoints.pop());
        }

        size -= 1;
        while (heapLines.size() < size) {
            Line line = new Line(styleSet);
            heapLines.push(line);
            clientPane.add(line);
        }
        while (heapLines.size() > size) {
            clientPane.remove((Component) heapLines.pop());
        }

        size += 1;
        while (heapPointsNums.size() < size){
            Rect rect = new Rect(styleSet);
            rect.setMessage(Integer.toString(heapPointsNums.size() + 1));
            rect.setStyle(4);
            heapPointsNums.push(rect);
            clientPane.add(rect);
        }
        while (heapPointsNums.size() > size){
            clientPane.remove((Component) heapPointsNums.pop());
        }
        clientPane.doLayout();
        elements.setValue(data.a.length);
    }

    /**
     * Sets new array size.
     *
     * @param size new array size.
     */
    private void setArraySize(int size) {
        if (!loaded){
            auto.d.a = new int[size];
            initVals = new int[size];
            wasMoved = new boolean[size];
        }
        for (int i = 0; i < size; i++){
         wasMoved[i] = false;
        }
        if (!loaded){
            randomize();
        }else {
           makeAllStart();
           rewind(0);
        }
        adjustArraySize();
    }


    /**
     * Makes all changes (accepted in the past) to defaults
     */
    public void makeAllStart(){
        data.heapSize = data.a.length - 1;
        for (int i = 0; i < data.a.length; i++){
            wasMoved[i] = false;
        }
        for (int i = 0; i < heapLines.size(); i++){
           ((Line)heapLines.elementAt(i)).setArrowed(false);
        }
        clientPane.doLayout();
    }

    /**
     * Randomizes array values.
     */
    private void randomize() {
        for (int i = 0; i < data.a.length; i++) {
            data.a[i] = (int) (Math.random() * maxValue) + 1;
        }
        initVals = (int[])(data.a.clone());
        makeAllStart();
        rewind(0);
    }

    /**
     * Rewinds algorithm to the specified step.
     *
     * @param step spet of the algorith to rewind to.
     */
    private void rewind(int step) {
        adjustArraySize();
        auto.d.heapSize = data.a.length - 1;
        auto.d.iHeapify = 0;
        auto.toStart();
        updateAll(0, 0, 0);
        while (!auto.isAtEnd() && auto.getStep() < step) {
            auto.stepForward(0);
        }
        /*
        if (auto.isAtEnd()){
            updateAll(0, 0, 3, -1);
        }
        */
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
                setArraySize(event.getValue());
                data.heapSize = data.a.length - 1;
                for (int i = 0; i < data.a.length; i++){
                   wasMoved[i] = false;
                }
                for (int i = 0; i < heapLines.size(); i++){
                   ((Line)heapLines.elementAt(i)).setArrowed(false);
                }
                clientPane.doLayout();
            } else {
                while (editModeStack.size() > value) {
                    clientPane.remove((Component) editModeStack.pop());
                }
                while (editModeStack.size() < value) {
                    TextField text = new TextField((new Integer((int) (Math.random() * maxValue) + 1)).toString());
                    text.setBackground(styleSet[0].getFillColor());
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
     * Updates array view.
     *
     * @param activeCell current active cell.
     * @param activeStyle style of active cell.
     */
    public void updateArray(int activeCell, int activeStyle) {
        for (int i = 0; i < data.a.length; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setMessage(Integer.toString(data.a[i]));
            rect.setStyle(i == activeCell ? activeStyle : 0);
        }
        update(true);
    }

    /**
     * Updates all: heap and array
     *
     * @param firstActiveCell active element
     * @param secondActiveCell active element
     * @param activeStyle style of active cell.
     */
    public void updateAll(int firstActiveCell, int secondActiveCell, int activeStyle){
        for (int i = 0; i < data.a.length; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setMessage(Integer.toString(data.a[i]));
            rect.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 0);
        }
        /* It seems to me, this is unneeded
        for (int i = 0; i < data.a.length; i++){
            Rect rect = (Rect) cellsNums.elementAt(i);
            rect.setMessage(Integer.toString(i) + 1);
            rect.setStyle(4);
        }
        */
        for (int i = 0; i < data.a.length; i++) {
            Ellipse ellipse = (Ellipse) heapPoints.elementAt(i);
            ellipse.setMessage(Integer.toString(data.a[i]));
            ellipse.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 0);
        }
        /* It seems to me, this is unneeded
        for (int i = 0; i < data.a.length; i++){
            Rect rect = (Rect) heapPointsNums.elementAt(i);
            rect.setMessage(Integer.toString(i) + 1);
            rect.setStyle(4);
        }
        */
        for (int i = 0; i < data.a.length - 1; i++) {
            Line line = (Line) heapLines.elementAt(i);
            line.setColor(Color.black);
        }
        update(true);
    }

    /**
     * Updates all: heap and array
     *
     * @param firstActiveCell active element
     * @param secondActiveCell active element
     * @param activeStyle style of active cell.
     * @param length number of elements that are not proccessed yet
     * @param arrowed if arrow is needed
     */
    public void updateAll(int firstActiveCell, int secondActiveCell, int activeStyle,
         int length, int arrowedLine){
        for (int i = 0; i < data.a.length; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setMessage(Integer.toString(data.a[i]));
            if (i <= length) {
                rect.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 0);
                if (wasMoved[i]){
                  rect.setLocation(rect.getBounds().x - 10, rect.getBounds().y);
                  Rect messRect = (Rect) cellsNums.elementAt(i);
                  messRect.setLocation(messRect.getBounds().x - 10, messRect.getBounds().y);
                  wasMoved[i] = false;
                }
            }else {
                rect.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 3);
                if (!wasMoved[i]){
                  rect.setLocation(rect.getBounds().x + 10, rect.getBounds().y);
                  Rect messRect = (Rect) cellsNums.elementAt(i);
                  messRect.setLocation(messRect.getBounds().x + 10, messRect.getBounds().y);
                  wasMoved[i] = true;
                }
            }
        }
        /* It seems to me, this is unneeded
        for (int i = 0; i < data.a.length; i++){
            Rect rect = (Rect) cellsNums.elementAt(i);
            rect.setMessage(Integer.toString(i) + 1);
            rect.setStyle(4);
        }
        */
        for (int i = 0; i < data.a.length; i++) {
            Ellipse ellipse = (Ellipse) heapPoints.elementAt(i);
            ellipse.setMessage(Integer.toString(data.a[i]));
            if (i <= length) {
                ellipse.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 0);
            }else {
                ellipse.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 3);
            }
        }

        for (int i = 0; i < data.a.length - 1; i++) {
            Line line = (Line) heapLines.elementAt(i);
            line.setColor(Color.black);
            line.setArrowed(false);
            if (i >= length){
                line.setColor(Color.white);
            }
            if (i == arrowedLine){
                line.setArrowed(true);
            }
        }
        update(true);
    }

    /**
     * Updates all: heap and array
     *
     * @param firstActiveCell active element
     * @param secondActiveCell active element
     * @param activeStyle style of active cell.
     * @param length number of elements that are not proccessed yet
     */
    public void updateAll(int firstActiveCell, int secondActiveCell, int activeStyle,
         int length){
        for (int i = 0; i < data.a.length; i++) {
            Rect rect = (Rect) cells.elementAt(i);
            rect.setMessage(Integer.toString(data.a[i]));
            if (i <= length) {
                rect.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 0);
                if (wasMoved[i]){
                  rect.setLocation(rect.getBounds().x - 10, rect.getBounds().y);
                  Rect messRect = (Rect) cellsNums.elementAt(i);
                  messRect.setLocation(messRect.getBounds().x - 10, messRect.getBounds().y);
                  wasMoved[i] = false;
                }
            }else {
                rect.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 3);
                if (!wasMoved[i]){
                  rect.setLocation(rect.getBounds().x + 10, rect.getBounds().y);
                  Rect messRect = (Rect) cellsNums.elementAt(i);
                  messRect.setLocation(messRect.getBounds().x + 10, messRect.getBounds().y);
                  wasMoved[i] = true;
                }
            }
        }
        /* It seems to me, this is unneeded
        for (int i = 0; i < data.a.length; i++){
            Rect rect = (Rect) cellsNums.elementAt(i);
            rect.setMessage(Integer.toString(i) + 1);
            rect.setStyle(4);
        }
        */
        for (int i = 0; i < data.a.length; i++) {
            Ellipse ellipse = (Ellipse) heapPoints.elementAt(i);
            ellipse.setMessage(Integer.toString(data.a[i]));
            if (i <= length) {
                ellipse.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 0);
            }else {
                ellipse.setStyle((i == firstActiveCell)||(i == secondActiveCell) ? activeStyle : 3);
            }
        }

        for (int i = 0; i < data.a.length - 1; i++) {
            Line line = (Line) heapLines.elementAt(i);
            line.setColor(Color.black);
            line.setArrowed(false);
            if (i >= length){
                line.setColor(Color.white);
            }
        }
        update(true);
    }

    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    protected void layoutClientPane(int clientWidth, int clientHeight){
        if (!drawIt) {
            return;
        }
        if (!editMode){
            int n = cells.size();            
            int cellWidth = Math.round(clientWidth / (n + 1));
            int cellHeight = Math.min(cellWidth, (clientHeight) / 9);
            int cellY = clientHeight / 10;
            int cellX = (clientWidth - cellWidth * n) / 2;
            int leftPadding = 10;
            {
                for (int i = 0; i < n; i++){
                    Rect rect = (Rect) cells.elementAt(i);
                    if (i <= data.heapSize){
                        rect.setBounds(cellX + i * cellWidth, cellY, cellWidth + 1, cellHeight + 1);
                    }else {
                        rect.setBounds(cellX + i * cellWidth + leftPadding, cellY, cellWidth + 1, cellHeight + 1);
                    }
                    rect.adjustFontSize(maxValueString);
                }
            }
            {
                n = cellsNums.size();
                for (int i = 0; i < n; i++){
                    Rect rect = (Rect) cellsNums.elementAt(i);
                    if (i <= data.heapSize){
                        rect.setBounds(cellX + i * cellWidth, 0, cellWidth + 1, cellY);
                    }else {
                        rect.setBounds(cellX + i * cellWidth + leftPadding, 0, cellWidth + 1, cellY);
                    }
                    rect.adjustFontSize(maxValueString);
                }
            }
            {
                int[] hPointsCoordX;
                int[] hPointsCoordY;
                int width;
                int height;
                {
                    n = heapPoints.size();
                    hPointsCoordX = new int[n];
                    hPointsCoordY = new int[n];
                    int tmp = n;
                    int layer;
                    int xTemp;
                    int allLayers;
                    allLayers = 0;
                    while (tmp != 0){
                        tmp /= 2;
                        allLayers++;
                    }
                    width = Math.round(clientWidth / ( n + 4 ));
                    height = width;
                    for (int i = 0; i < n; i++){
                        Ellipse ellipse = (Ellipse) heapPoints.elementAt(i);
                        tmp = i + 1;
                        layer = 0;
                        xTemp = 1;
                        while (tmp != 0){
                            tmp /= 2;
                            layer++;
                            xTemp *= 2;
                        }
                        if (i != 0){
                            hPointsCoordX[i] = hPointsCoordX[( i - 1 ) / 2] + (clientWidth / xTemp) * ( ( ( i - 1 ) % 2 ) * 2 - 1 );
                            hPointsCoordY[i] = cellY + ( ( clientHeight - cellY - cellHeight - height / 2 ) / allLayers ) * layer + 2 * height / 3;
                        }else {
                            hPointsCoordX[i] = clientWidth / xTemp;
                            hPointsCoordY[i] = cellY + ( ( clientHeight - cellY - cellHeight - height / 2 ) / allLayers ) * layer + 2 * height / 3 + height / 4;
                        }
                        ellipse.setBounds(hPointsCoordX[i] - width / 2, hPointsCoordY[i] - height / 2 - height / 4, width + 1, height + 1);
                        ellipse.adjustFontSize(maxValueString);
                    }       
                }
                n = heapPointsNums.size();
                for (int i = 0; i < n; i++){
                    Rect rect = (Rect) heapPointsNums.elementAt(i);
                    rect.setBounds(hPointsCoordX[i] - width / 2, hPointsCoordY[i] - height - height / 6, width, height / 2);
                    rect.adjustFontSize(maxValueString);
                }
                {
                    n = heapLines.size();
                    for (int i = 0; i < n; i++){
                        Line line = (Line) heapLines.elementAt(i);
                        line.setBounds(0, 0, clientWidth, clientHeight);
                        line.setCoord(hPointsCoordX[i / 2], hPointsCoordY[i / 2] - height / 4,
                            hPointsCoordX[i + 1], hPointsCoordY[i + 1] - height / 4);
                        line.setDRadius(width / 2);
                    }
                }
            }
        } else {
            int n = cells.size();
            for (int i = 0; i < n; i++){
                Rect rect = (Rect) cells.elementAt(i);
                rect.setBounds(-1, -1, -1, -1);
            }
            n = heapPoints.size();
            for (int i = 0; i < n; i++)
            {
                Ellipse ellipse = (Ellipse) heapPoints.elementAt(i);
                ellipse.setBounds(-1, -1, -1, -1);
            }
            n = heapLines.size();
            for (int i = 0; i < n; i++)
            {
                Line line = (Line) heapLines.elementAt(i);
                line.setBounds(-1, -1, -1, -1);
            }
            n = cellsNums.size();
            for (int i = 0; i < n; i++)
            {
                Rect rect = (Rect) cellsNums.elementAt(i);
                rect.setBounds(-1, -1, -1, -1);
            }
            n = heapPointsNums.size();
            for (int i = 0; i < n; i++)
            {
                Rect rect = (Rect) heapPointsNums.elementAt(i);
                rect.setBounds(-1, -1, -1, -1);
            }
            n = editModeStack.size();            
            int cellWidth = Math.round(clientWidth / (n + 1));
            int cellHeight = Math.min(cellWidth, (clientHeight) / 9);
            int cellY = clientHeight / 10;
            int cellX = (clientWidth - cellWidth * n) / 2;
            {
                for (int i = 0; i < n; i++){
                    TextField text = (TextField) editModeStack.elementAt(i);
                    text.setBounds(cellX + i * cellWidth, cellY, cellWidth + 1, cellHeight + 1);
                    adjustTextFontSize(text, maxValueString);
                }
            }
            /* It seems to me, this is unneeded
            dArrowSwap.setBounds(-1, -1, 0, 0);
            rectLeft.setBounds(-1, -1, 0, 0);
            rectRight.setBounds(-1, -1, 0, 0);
            */
        }
    }

    /**
     * Starts edit mode.
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
            for (int i = 0; i < initVals.length; i++) {
                TextField tf = new TextField((new Integer(initVals[i])).toString());
                tf.setBackground(styleSet[0].getFillColor());
                tf.addKeyListener(new KeyListener() {
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
                editModeStack.push(tf);
                clientPane.add(tf);
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
            if (initVals.length != editModeStack.size()) {
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
                
            if (!updated && (initVals[i] != val)) {
                    updated = true;
                }
                cc[i] = val;
            }
            editModeButton.setState(0);
            if (updated) {
                setArraySize(cc.length);
                auto.d.a = (int[]) (cc.clone());
            initVals = (int[]) (cc.clone());
                auto.toStart();
/*
                hideAuxArrays(false);
                for (int i = 0; i < leftBorder.size(); i++) {
                    Rect rectl = (Rect) leftBorder.elementAt(i);
                    rectl.setStyle(0);
                    Rect rectr = (Rect) rightBorder.elementAt(i);
                    rectr.setStyle(0);
                }
                stackVisible = 0;
                clientPane.remove(stackRect);
*/
                clientPane.repaint();
            }
            while (editModeStack.size() > 0) {
                clientPane.remove((Component) editModeStack.pop());
            }
            drawIt = false;
            rewind(auto.getStep());
            drawIt = true;
            update(true);
            clientPane.doLayout();
        }
    }


/*    public void stopEditMode() {
        if (editMode) {
            editMode = false;
            boolean updated = false;
            if (initVals.length != editModeStack.size()) {
                initVals = new int[editModeStack.size()];
                updated = true;
            }
            for (int i = 0; i < initVals.length; i++) {
                TextField tf = (TextField) editModeStack.elementAt(i);
                String str = tf.getText();
                str = str.trim();
                int val;
                try {
                    val = (new Integer(str)).intValue();
                } catch (Exception e) {
                    val = 0;
                };
                val = (val > maxValue) ? maxValue : ((val < 0) ? 0 : val);
                if (initVals[i] != val) {
                    updated = true;
                    initVals[i] = val;
                }
            }
            editModeButton.setState(0);
            if (updated) {
                int[] ff = (int[])(initVals.clone());
                setArraySize(initVals.length);
                auto.d.a = (int[]) (ff.clone());
                initVals = (int[]) (ff.clone());
                auto.toStart();
                clientPane.repaint();
            }
            while (editModeStack.size() > 0) {
                clientPane.remove((Component) editModeStack.pop());
            }
            drawIt = false;
            rewind(auto.getStep());
            drawIt = true;
            update(true);
            clientPane.doLayout();
        }
    }
*/

    /**
     * Adjust TextField's font size to show its message.
     *
     * @param text given TextField.
     */
    public void adjustTextFontSize(TextField text, String maxMessage) {
        Dimension size = text.getSize();
        Font font = styleSet[0].getTextFont();
        
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
        /* I made it to be uneeded, it seems to me =)
        updateBrackets(-1, 0, OUTOFBOUND);
        for (int i = 0; i < mainArray.size(); i++) {
             ((Rect) mainArray.elementAt(i)).setStyle(4);
        }
        rectStack.setBounds(-1, -1, 0, 0);
        for (int i = 0; i < leftBordersArray.size(); i++) {
             ((Rect) leftBordersArray.elementAt(i)).setStyle(4);
             ((Rect) rightBordersArray.elementAt(i)).setStyle(4);
        }
        update(false);
        */
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