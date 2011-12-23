package ru.ifmo.vizi.long_int;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.*;
//import ru.ifmo.vizi.base.widgets.Rect;
//import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.*;
//import java.awt.event.AdjustmentEvent;
//import java.awt.event.AdjustmentListener;
import java.util.Stack;

/**
 * Find maximum applet.
 *
 * @author  Georgiy Korneev
 * @version $Id: LongIntegerVisualizer.java,v 1.5 2004/03/23 16:40:01 geo Exp $
 */

public final class LongIntegerVisualizer extends Base 
    implements  AdjustmentListener
{
    /**
     * Find maximum automata instance.
     */
    private final LongInteger auto;

    /**
     * Find maximum automata data.
     */
    private final LongInteger.Data data;

    /**
     * Cells with array elements.
     * Vector of {@link Rect}.
     */
    private final Stack cells;

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
     * Rectangle that contains ramainder value.
     */
    private final Rect label1;

    /**
     * Rectangle that contains sum value.
     */
    private final Rect label2;

    /**
     * Rectangle that contains ramainder value.
     */
    private final Rect label3;

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

    myChoice combobox1 = new myChoice(config, "choice1"){
        public void click(){
            auto.toStart();
            showIntegers(-1, -1, -1, 0, 0, 0);
            data.smu = SMU[this.getSelectedIndex()];
            auto.drawState();
        }
    };
 
    /**
     * Значки
     */
    String[] SMU = new String[]{"+", "-", "*", "**"};

    /**
     * Надписи на экране
     */
    Rect[] labels = new Rect[30];

    /**
     * Злементы редакитрования
     */
    TextField[] edits = new TextField[60];

    /**
     * Против переполнения
     */
    public boolean antiSOFE = false;

    /**
     * Размеры аплета
     */
    int oldClientWidth, oldClientHeight;
   
    /**
     * Старая максимальная длинна
     */
    int oldMaxL = 0;

    /**
     * Элементов использовано
     */
    int cellsUse = 0;

    /**
     * Текущее направление движения
     */
    int direct = 1;
 
    /**
     * Признак редактируемости
     */
    boolean isEdit = false;

    /**
     * Надписи на Labels
     */

    private final String label2Message;
    private final String label3Message;

    /**
     * Надписи на Labels
     */
    String[] captions = new String[]{"", "A", "B", "C", "D", "= A", "= C", "= A * C", "= B", "= D",
        "= B * D", "= (A + B)", "= (C + D)", "= (A + B) * (C + D)", "", "**", "**", "**", "", "",
        "", "AB", "CD", "+ (A*B) 0..0  0..0", "- (A*B) 0..0", "+ [(A+B)*(C+D)] 0..0", "- (BD) 0..0", "+ BD", "= AB * CD"};

    /**
     * Конструктор
     */
    public LongIntegerVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new LongInteger(locale);
        data = auto.d;
        data.visualizer = this;
        cells = new Stack();

        label2Message = config.getParameter("t-message");
        label3Message = config.getParameter("k-message");

        styleSet = ShapeStyle.loadStyleSet(config, "array");
        label1 = new Rect(new ShapeStyle[]{new ShapeStyle(config, "label-style-r", styleSet[0])}, "");
        label2 = new Rect(new ShapeStyle[]{new ShapeStyle(config, "label-style-r", styleSet[0])}, "");
        label3 = new Rect(new ShapeStyle[]{new ShapeStyle(config, "label-style-r", styleSet[0])}, "");
        clientPane.add(label1);
        clientPane.add(label2);
        clientPane.add(label3);
        for(int i = 0; i < labels.length; i++){
            labels[i] = new Rect(new ShapeStyle[]{new ShapeStyle(config, "label-style-" + (i < 15 ? "l" : "r"), styleSet[0])}, "");
            clientPane.add(labels[i]);
        }

        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);

        maxValue = config.getInteger("max-value");
        maxValueString = config.getParameter("max-value-string", Integer.toString(maxValue));
//        setArraySize(elements.getValue());

        initValue();
        setCellsSize(20 * getMaxL());

        createInterface(auto);
    }

    /**
     * This method creates panel with visualizer controls.
     *
     * @return controls pane.
     */
    public Component createControlsPane() {
        Panel panel = new Panel(new BorderLayout());

        panel.add(new AutoControlsPane(config, auto, forefather, true)/*{
            public void actionPerformed(ActionEvent event) {
                if(isEdit){} else {
                if((data.smu == "**") && (event.getSource().getClass().getName() == "ru.ifmo.vizi.base.ui.HintedButton")){
                    String bLabel = new String(((Button) event.getSource()).getLabel());
                    if(bLabel.equals("<<")){ direct = 0; data.commentLevel = 10000; }
                    if(bLabel.equals(">>")){ direct = 1; data.commentLevel = 10000; }
                    if (bLabel.equals("<<<")) {
                        direct = 0;
                        data.commentLevel = data.storeInd;
                        if(data.commentLevel < 0) { data.commentLevel = 0; }
                        auto.stepBackward(0);
                    } else
                    if (bLabel.equals(">>>")) {
                        direct = 1;
                        data.commentLevel = data.storeInd;
                        if(data.commentLevel < 0) { data.commentLevel = 0; }
                        auto.stepForward(0);
                    } else { super.actionPerformed(event); }
                } else { super.actionPerformed(event); }
                }
            }
        }*/, BorderLayout.NORTH);

        Panel bottomPanel = new Panel();
        bottomPanel.add(combobox1);
        bottomPanel.add(elements);
        panel.add(bottomPanel, BorderLayout.CENTER);
              bottomPanel = new Panel();  
        bottomPanel.add(new HintedButton(config, "button-random"){
            public void click() {
                randomize();
            }
        });            
        for(int i = 0; i < edits.length; i++){ edits[i] = null; }     
        String[] st = new String[]{"button-edit", "button-apply"};
        bottomPanel.add(new myMultiButton(config, st){
            public void press() {
                auto.stepBackward(100);
                if(this.getState() == 1){ 
                    isEdit = true;
                    int maxL = getMaxL();
                    for(int z = 0; z < 2; z++)
                    for(int i = 0; i < maxL; i++) {
                        int ind = maxL - 1 - i;
                        edits[i + z * maxL] = new TextField("" + data.store[z][ind], 1);
                        clientPane.add(edits[i + z * maxL]);
                    }
                    this.kol = 2 * maxL;
                } else {
                    isEdit = false;
                    for(int i = 0; i < edits.length; i++)
                        if(edits[i] != null){ 
                            edits[i].setBounds(0,0,0,0);
                        }
                    for(int i = 0; i < this.kol; i++){
                        int z = i / (this.kol / 2), ind = (this.kol / 2) - 1 - i % (this.kol / 2);
                        int v = 0;
                        try{
                            v = Integer.parseInt(edits[i].getText());
                        }catch(Exception e){ 
                            System.out.println("Error in Integer: " + edits[i].getText());
                        }
                        data.store[z][ind] = v;
                        while(data.store[z][ind] > 10){ data.store[z][ind] /= 10; }
                    }
                }    
                rePaint();
            }
        });                 
        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();

                    buffer.append("/* ")
                    .append( I18n.message(config.getParameter("ArrayLengthComment"), new Integer(elements.getMinimum()), new Integer(elements.getMaximum())) )
                    .append(" */\n");

                    buffer.append("IntegerLength = ").append(elements.getValue()).append("\n");

                    for(int z = 0; z < 2; z++){
                        buffer.append("/* ")
                        .append( I18n.message(config.getParameter("ElementsComment"), new Integer(0), new Integer(maxValue), new Integer(z + 1)) )
                        .append(" */\n");
                        buffer.append("Integer" + (z + 1) + " = ");
                        for (int i = elements.getValue() - 1; i >= 0; i--) {
                            buffer.append(data.store[z][i]).append(" ");
                        }
                        buffer.append("\n");
                    }

                    buffer.append("/* ")
                    .append(config.getParameter("ChoiceComment"))
                    .append(" */\n");
                    buffer.append("Choice = ").append(combobox1.getSelectedIndex() + 1).append("\n");

                    buffer.append("/* ")
                    .append(config.getParameter("StepComment"))
                    .append(" */\n");
                    buffer.append("Step = ").append(auto.getStep());

                    saveLoadDialog.show(buffer.toString());
                }
            });
        }
        saveLoadDialog = new SaveLoadDialog(config, forefather) {
            public boolean load(String text) throws Exception {
                freeEdit();
                SmartTokenizer tokenizer = new SmartTokenizer(text, config);
                tokenizer.expect("IntegerLength");
                tokenizer.expect("=");
                int l = tokenizer.nextInt(elements.getMinimum(), elements.getMaximum());
                elements.setValue(l);

                data.store = new int[data.store.length][data.store[0].length];
                for(int z = 0; z < 2; z++){
                    tokenizer.expect("Integer" + (z + 1));
                    tokenizer.expect("=");
                    for (int i = l - 1; i >= 0; i--) {
                        data.store[z][i] = tokenizer.nextInt(0, 9);
                    }
                }

                tokenizer.expect("Choice");
                tokenizer.expect("=");
                int index = tokenizer.nextInt(1, 4);
                combobox1.select(index - 1);

                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();

                rewind(step);
                return true;
            }
        };
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void rewind(int step) {
        auto.toStart();
        oldMaxL = 0;
        rePaint();
        while (!auto.isAtEnd() && auto.getStep() < step) {
            auto.stepForward(0);
        }
    }

    /**
     * Изментить размер cells
     */
    private void setCellsSize(int size) {
        boolean change = false;
        size += 3;
//        if( (!auto.isAtEnd()) ){ size += 1; }
        while (cells.size() < size) {
            Rect rect = new Rect(styleSet);
            cells.push(rect);
            clientPane.add(rect);
            change = true;
        }
        while (cells.size() > size) {
            clientPane.remove((Component) cells.pop());
            change = true;
        }
        if(change){ clientPane.doLayout(); }
    }

    /**
     * Отменить редактирование
     */
    void freeEdit(){
        if(isEdit){
            for(int i = 0; i < edits.length; i++)
                if(edits[i] != null){ 
                    edits[i].setBounds(0,0,0,0);
                }
            isEdit = false;
        }
    }

    /**
     * Приметить изменение размера чисел
     */
    public void adjustmentValueChanged(AdjustmentEvent event) {
        if (event.getSource() == elements) {
            freeEdit();
            auto.toStart();
            randomize();
            getMaxL();
        }
    }
    
    /**
     * Взять случайные числа
     */
    public void randomize(){
        for(int i = 0; i < data.store[0].length; i++){
            data.store[0][i] = 0;
            data.store[1][i] = 0;
            data.store[2][i] = 0;
        }                        
        for(int i = 0; i < elements.getValue(); i++){
            data.store[0][i] = (int) (Math.random() * (maxValue + 1));
            data.store[1][i] = (int) (Math.random() * (maxValue + 1));
        }
        auto.toStart();
        showIntegers(-1, -1, -1, 0, 0, 0);
    }

    /**
     * Инициализация
     */
    public void initValue(){
        for(int z = 0; z < data.store.length; z++){
            for(int i = 0; i < data.store[z].length; i++){
	        data.store[z][i] = 0;
	    }
        }
        data.store[0][0] = 7;
        data.store[0][1] = 5;
        data.store[0][2] = 3;
        data.store[0][3] = 0;
        data.store[0][4] = 0;
        data.store[0][5] = 0;
        data.store[0][6] = 0;
        data.store[0][7] = 0;
        data.store[1][0] = 1;
        data.store[1][1] = 3;
        data.store[1][2] = 2;
        data.store[1][3] = 4;
        data.store[1][4] = 0;
        data.store[1][5] = 0;
        data.store[1][6] = 0;
        data.store[1][7] = 0;
    }

    /**
     * Получить максимальную длинну числа
     */
    int getMaxL(){
        int maxL = 1;
        for(int z = 0; z < 3; z++){
            int l = 1;
            for(int i = data.store[z].length - 1; i >= 0; i--){
                if(data.store[z][i] != 0){
                    if(l < (i + 1)){
                        l = i + 1;
                    }
                    break;
                }
            }
            data.storeL[z] = l;
            if(maxL < l){
                maxL = l;
            }
        }
        data.maxL = maxL;
        if(oldMaxL != maxL){
            oldMaxL = maxL;
            setCellsSize(20 * maxL);
        }
        return maxL;
    }

    /**
     * Переривосать
     */
    void rePaint(){
        clientPane.doLayout();
    }

    /**
     * Убрать все надписи
     */
    void hideLabels(){
        label1.setMessage("");
        label2.setMessage("");
        label3.setMessage("");
        for(int i = 0; i < labels.length; i++){
            labels[i].setMessage((i < captions.length) ? captions[i] : "");
            labels[i].setBounds(0,0,0,0);
        }
    }
 
    /**
     * Вывести числа на экран
     */
    public void showIntegers(int activeCell_A, int activeCell_B, int activeCell_C, int activeStyle_A, int activeStyle_B, int activeStyle_C) {
        int activeCell[] = new int[]{activeCell_A, activeCell_B, activeCell_C};
        int activeStyle[] = new int[]{activeStyle_A, activeStyle_B, activeStyle_C};
        int maxL = getMaxL();
        Rect rect;
            int width = Math.round(oldClientWidth / Math.max((maxL + 3), 6));
            int dY = oldClientHeight / 40;
            if(dY % 2 == 1) dY += 1;
            int height = Math.min(width, (oldClientHeight - dY - 1) / 5 - dY);
            width = Math.min(width, height);
            int x = (oldClientWidth - width * maxL) / 2;
            int y = (oldClientHeight - height * 5 / 2 - 4 * dY) / 2;
        boolean notNul;
        for(int z = 0; z < 3; z++){
            notNul = false;
            for(int i = 0; i < maxL; i++) {
                rect = (Rect) cells.elementAt(i + z * maxL);
                int ind = maxL - 1 - i;
                int line = z + data.storeInd;
                if(line < 0){ line += 8; }
                if( (!notNul) && ((data.store[line][ind] != 0) || ((z == 2)&&(ind == (data.i + data.j)) || (ind == Math.max(data.i, data.j)) || (ind == 0))) ){ notNul = true; }
                if( !notNul || (i >= data.store[line].length) ){
                    rect.setMessage("");
                } else {
                    rect.setMessage(Integer.toString(data.store[line][ind]));
                }	
                rect.setStyle(( ((ind == activeCell[z] / 100)&&(0 != activeCell[z] / 100)) || (ind == activeCell[z] % 100)) ? activeStyle[z] : 0);
                rect.setBounds(x + i * width, y + z * (height + dY), width + 1, height + 1);
                rect.adjustFontSize(maxValueString);
                if((isEdit) && (z < 2)){
                    edits[i + z * maxL].setBounds(rect.getLocation().x, rect.getLocation().y, width + 1, height + 1);
                    edits[i + z * maxL].setFont(rect.getLook().getTextFont(styleSet[0]));
                }
            }                                                                                                                       
        }
        Rect line = (Rect) cells.elementAt(3 * maxL);
        line.setBounds(x - width / 2, y + 2 * height + dY * 3 / 2, width * (maxL + 1), 1);
        line.setMessage("");

        cellsUse = 3 * maxL + 1;
        for(int i = cellsUse; i < cells.size(); i++){
            ((Rect) cells.elementAt(i)).setBounds(0,0,0,0);
        }
         rect = (Rect) cells.elementAt(0);
        hideLabels();
        label1.setBounds(rect.getLocation().x - width - dY, rect.getLocation().y + height + dY / 2 - height / 2, width + 1, height + 1);
        label1.setMessage(data.smu);
        label1.adjustFontSize(maxValueString);
        label2.setMessage("");
        label3.setMessage("");
        update(true);
    }
 
    /**
     * Вывести временные переменные на экран
     */
    public void showTemp(int activeStyle_TempOst, int activeStyle_Ost) {
        int maxL = getMaxL();
            int width = Math.round(oldClientWidth / Math.max((maxL + 2), 6));
            int dY = oldClientHeight / 40;
            if(dY % 2 == 1) dY += 1;
            int height = Math.min(width, (oldClientHeight - dY - 1) / 5 - dY);
            width = Math.min(width, height);
            int x = (oldClientWidth - width * maxL) / 2;
        Rect mb1 = (Rect) cells.elementAt(0);
        Rect mb2 = (Rect) cells.elementAt(cellsUse - 2);

        Rect rect1 = (Rect) cells.elementAt(cellsUse);
        if(data.tempOst != -10000){ rect1.setMessage("" + data.tempOst); } else { rect1.setMessage(""); }
        rect1.setStyle(activeStyle_TempOst);
        rect1.setBounds(Math.min(mb1.getLocation().x + width, mb2.getLocation().x - width), mb2.getLocation().y + height + dY, width + 1, height + 1);
        rect1.adjustFontSize(maxValueString);
        label2.setMessage(label2Message);
        label2.setBounds(rect1.getLocation().x - width - 3, rect1.getLocation().y, width + 1, height + 1);
        label2.adjustFontSize(maxValueString);

        Rect rect2 = (Rect) cells.elementAt(cellsUse + 1);
        if(data.ost != -10000){ rect2.setMessage("" + data.ost); } else { rect2.setMessage(""); }
        rect2.setStyle(activeStyle_Ost);
        rect2.setBounds(Math.max(mb2.getLocation().x, rect1.getLocation().x + 2 * width), mb2.getLocation().y + height + dY, width + 1, height + 1);
        rect2.adjustFontSize(maxValueString);
        label3.setMessage(label3Message);
        label3.setBounds(rect2.getLocation().x - width - 3, rect2.getLocation().y, width + 1, height + 1);
        label3.adjustFontSize(maxValueString);
        update(true);
    }
 
    /**
     * Изменение размеров аплета
     */
    protected void layoutClientPane(int clientWidth, int clientHeight) {
        oldClientWidth = clientWidth;
        oldClientHeight = clientHeight;
        if(!antiSOFE){ auto.drawState(); }
        antiSOFE = false;
    }

    /**
     * Вывести массив на экран
     */
    void printArray(int x, int y, int maxL, int[] mass, int index, int count, int width, int height, int dY, int labelIndex, int sty){
        int smuL = 0;
        if(maxL == -1){ maxL = count; }
        for(int i = 0; i < maxL; i++){
            Rect rect = (Rect) cells.elementAt(cellsUse);
            cellsUse += 1;
            rect.setBounds(x + (i) * width, y, width + 1, height + 1);
            rect.setStyle(sty);
            int ind = index + maxL - 1 - i;
            if( (ind >= 0) && (ind < (index + count)) ){ rect.setMessage("" + mass[ind]); } else { rect.setMessage(""); }
            rect.adjustFontSize(maxValueString);
        }
        labels[labelIndex].setBounds(x + maxL * width + 2, y, (width + 1) * captions[labelIndex].length(), height + 1);
        labels[labelIndex].setMessage(captions[labelIndex]);
        labels[labelIndex].adjustFontSize(maxValueString);
        if(labelIndex == 5){ smuL = 15; }
        if(labelIndex == 8){ smuL = 16; }
        if(labelIndex == 11){ smuL = 17; }
        labels[smuL].setBounds(x - width - 3, y + height / 2 + dY / 2, width + 1, height + 1);
        labels[smuL].adjustFontSize(maxValueString);
        if((labelIndex == 7) || (labelIndex == 10) || (labelIndex == 13)){
            Rect rect = (Rect) cells.elementAt(cellsUse);
            cellsUse += 1;
            rect.setMessage("");
            rect.setBounds(x - 3 * dY, y - dY / 2, maxL * width + 6 * dY, 1);
        }
    }

    /**
     * мой максимум чисел
     */
    int myMax(int i1, int i2, int i3, int i4, int i5, int i6){
        return Math.max(i1, Math.max(i2, Math.max(i3, Math.max(i4, Math.max(i5, i6)))));
    }

    /**
     *  Вывести временные вычисления в алгоритме Карацубы
     */
    public void showTempPower(int state){
/*        if(data.storeInd > data.commentLevel){ while(data.storeInd > data.commentLevel) if(direct == 0){auto.stepBackward(0);}else{auto.stepForward(0);} } else */{
//        printStore(24);
        cellsUse = 0;
        hideLabels();
        int sInd = data.storeInd;
        int[][] mass = data.store;
        int[] l = data.storeL;
        int k = Math.max(l[sInd] / 2, l[sInd + 1] / 2);
        int lA = l[sInd] - k, lB = k, lC = l[sInd + 1] - k, lD = k;

        int maxL = myMax(l[sInd], l[sInd + 1], l[sInd + 3] + l[sInd + 7] + 2, l[sInd + 4], l[sInd + 5], l[sInd + 6] + 4) + 4;
        int width = Math.round(oldClientWidth / Math.max(maxL, 6));
        int dY = oldClientHeight / 100;
            if(dY % 2 == 1){ dY += 1; }
        int height = Math.min(width, (oldClientHeight - 6 * dY) / 8 - dY);
        width = Math.min(width, height);

         int d; if(state == 0){ d = 0; }else{ d = 1; }
         int max0 = Math.max(l[sInd], l[sInd + 1]) - 2;
         int x00 = oldClientWidth / 2 + width * (max0 / 2 - l[sInd]);
         int x01 = x00 + width * (lA + d);
         int x10 = oldClientWidth / 2 + width * (max0 / 2 - l[sInd + 1]);
         int x11 = x10 + width * (lC + d);
         int max1 = Math.max(lA, Math.max(lC, l[sInd + 3]));
         int max2 = Math.max(lB, Math.max(lD, l[sInd + 7]));
         int max3 = Math.max(l[sInd + 4], Math.max(l[sInd + 5], l[sInd + 6]));
         int x2 = (oldClientWidth  - (max1 + max2 + 5) * width) / 2;
         int x3 = (oldClientWidth - width * (max3 + 3)) / 2;

        printArray(x00, dY, -1, mass[sInd], lB, lA, width, height, dY, 1 * d, 0);
        printArray(x01, dY, -1, mass[sInd],  0, lB, width, height, dY, 2 * d, 0);
        printArray(x10, dY + height + dY, -1, mass[sInd + 1], lD, lC, width, height, dY, 3 * d, 0);
        printArray(x11, dY + height + dY, -1, mass[sInd + 1],  0, lD, width, height, dY, 4 * d, 0);

        if(state >= 2)
        printArray(x2, 2 * height + 6 * dY, max1, mass[sInd], lB, lA, width, height, dY, 5, ((state == 2) || (state == 3)) ? 3 : 0);
        if(state >= 2)
        printArray(x2, 3 * height + 7 * dY, max1, mass[sInd + 1], lD, lC, width, height, dY, 6, ((state == 2) || (state == 3)) ? 3 : 0);
        if(state >= 3)
        printArray(x2, 4 * height + 8 * dY, max1, mass[sInd + 3], 0, l[sInd + 3], width, height, dY, 7, (state == 3) ? 2 : ((state == 8) ? 1 : 0));

        if(state >= 4)
        printArray(x2 + (max1 + 3) * width, 2 * height + 6 * dY, max2, mass[sInd], 0, lB, width, height, dY, 8, ((state == 4) || (state == 5)) ? 3 : 0);
        if(state >= 4)
        printArray(x2 + (max1 + 3) * width, 3 * height + 7 * dY, max2, mass[sInd + 1], 0, lD, width, height, dY, 9, ((state == 4) || (state == 5)) ? 3 : 0);
        if(state >= 5)
        printArray(x2 + (max1 + 3) * width, 4 * height + 8 * dY, max2, mass[sInd + 7], 0, l[sInd + 7], width, height, dY, 10, (state == 5) ? 2 : ((state == 8) ? 1 : 0));

        if(state >= 6)
        printArray(x3, 5 * height + 11 * dY, max3, mass[sInd + 4], 0, l[sInd + 4], width, height, dY, 11, ((state == 6) || (state == 7)) ? 3 : 0);
        if(state >= 6)
        printArray(x3, 6 * height + 12 * dY, max3, mass[sInd + 5], 0, l[sInd + 5], width, height, dY, 12, ((state == 6) || (state == 7)) ? 3 : 0);
        if(state >= 7)
        printArray(x3, 7 * height + 13 * dY, max3, mass[sInd + 6], 0, l[sInd + 6], width, height, dY, 13, (state == 7) ? 2 : ((state == 8) ? 1 : 0));

        for(int i = cellsUse; i < cells.size(); i++){
            ((Rect) cells.elementAt(i)).setBounds(0,0,0,0);
        }
    }}

    /**
     * Вывести массив на экран
     */
    void printArray2(int x, int y, int[] mass, int maxL, int nuls, int width, int height, int dY, int sty, int labelIndex){
        labelIndex += 20;
        boolean notNul = false;
        for(int i = 0; i < maxL; i++){
            Rect rect = (Rect) cells.elementAt(cellsUse);
            cellsUse += 1;
            rect.setBounds(x + i * width, y, width + 1, height + 1);
            int ind = maxL - 1 - i - nuls;
            if(ind < 0){
                rect.setMessage("0");
            } else {
                if((!notNul) && ((mass[ind] != 0) || (ind == 0))){
                    notNul = true;
                    if((labelIndex == 24) || (labelIndex == 26)) ((Rect) cells.elementAt(cellsUse - 2)).setMessage("-");
                }
                if(!notNul){
                    rect.setMessage("");
                } else {
                    rect.setMessage("" + mass[ind]);
                }
            }
            rect.adjustFontSize(maxValueString);
            rect.setStyle((ind < 0) ? 3 : sty);
        }
        labels[labelIndex].setBounds(x + maxL * width + 2, y, (width + 1) * 6, height + 1);
        labels[labelIndex].setMessage(captions[labelIndex]);
        labels[labelIndex].adjustFontSize(maxValueString);
        if((labelIndex == 23) || (labelIndex == 28)){
            Rect rect = (Rect) cells.elementAt(cellsUse);
            cellsUse += 1;
            rect.setMessage("");
            rect.setBounds(x - dY, y - dY / 2, maxL * width + 2 * dY, 1);
                 rect = (Rect) cells.elementAt(cellsUse);
            cellsUse += 1;
            rect.setMessage("");
            rect.setBounds(x + (maxL + 2) * width - dY, y - dY / 2, 4 * width + 2 * dY, 1);
        }
        if(labelIndex == 21){
            labels[15].setBounds(x - width - 3, y + height / 2, width + 1, height + 1);
            labels[15].adjustFontSize(maxValueString);
            labels[16].setBounds(x + (maxL + 4) * width + width / 4, y + height / 2, width + 1, height + 1);
            labels[16].adjustFontSize(maxValueString);
        }
    }

    /**
     * Makes small step forward.
     */
    public void doNextStep() {
    	if (!isEdit)
	        if (!auto.isAtEnd()) 
    	        auto.stepForward(0);
    }

    /**
     * Makes small step backward.
     */
    public void doPrevStep() {
    	if (!isEdit)
	        if (!auto.isAtStart())
    	        auto.stepBackward(0);
    }

    /**
     * Показать конечную сумму алгоритма Карацубы
     */
    public void showSum(int state){
/*        if(data.storeInd > data.commentLevel){ while(data.storeInd > data.commentLevel) if(direct == 0){auto.stepBackward(0);}else{auto.stepForward(0);} } else*/ {
        int sInd = data.storeInd;
        int[][] mass = data.store;
        int[] l = data.storeL;
        int k = Math.max(l[sInd] / 2, l[sInd + 1] / 2);
        int maxL = myMax(l[sInd], l[sInd + 1], l[sInd + 3] + 2 * k, l[sInd + 6] + k, l[sInd + 7] + k + 1, l[sInd + 2]);
        if(state == 2){ maxL = myMax(l[sInd], l[sInd + 1], l[sInd + 2], 0, 0, 0);}
        int width = Math.round(oldClientWidth / (maxL + 7));
        int dY = oldClientHeight / 20;
        int height = Math.min(width, (oldClientHeight - 4 * dY) / 8);
        width = Math.min(width, height);
        int x = (oldClientWidth - width * (maxL + 6)) / 2 + 5;

        cellsUse = 0;
        hideLabels();
        if(state == 2){ labels[15].setMessage("*"); labels[16].setMessage("*"); }

        printArray2(x, dY,                   mass[sInd + 0], maxL, 0, width, height, dY, 0, 1);
        printArray2(x, dY + height,          mass[sInd + 1], maxL, 0, width, height, dY, 0, 2);
        if(state != 2){
        printArray2(x, dY + 2 * height + dY, mass[sInd + 3], maxL,2*k,width, height, dY, 0, 3);
        printArray2(x, dY + 3 * height + dY, mass[sInd + 3], maxL, k, width, height, dY, 0, 4);
        printArray2(x, dY + 4 * height + dY, mass[sInd + 6], maxL, k, width, height, dY, 0, 5);
        printArray2(x, dY + 5 * height + dY, mass[sInd + 7], maxL, k, width, height, dY, 0, 6);
        printArray2(x, dY + 6 * height + dY, mass[sInd + 7], maxL, 0, width, height, dY, 0, 7);
        }
        printArray2(x, dY + 7 * height +2*dY,mass[sInd + 2], maxL, 0, width, height, dY, (state == 1) ? 2 : 0, 8);
        for(int i = cellsUse; i < cells.size(); i++){
            ((Rect) cells.elementAt(i)).setBounds(0,0,0,0);
        }
    }}

    /**
     * Вывести массив в консоль
     */
    void printStore(int n){
        for(int z = 0; z < n; z++){
            System.out.print("" + z + ": " + ((z<10)? " " : ""));
            for(int i = 0; i < 30; i++){
                System.out.print(data.store[z][i]);
            }
            System.out.println(" (" + data.storeL[z] + ")");
        }
    }
}

/**
 * Мой Choice
 */
class myChoice extends Choice implements ItemListener{
    protected myChoice() {
	addItemListener(this);
    }

    protected myChoice(Configuration config, String name){
        this();
        int n = Integer.parseInt(config.getParameter(name + "-kol"));
        for(int i = 1; i <= n; i++){
            this.add(config.getParameter(name + "-message" + i));
        }
    }

    public void itemStateChanged(ItemEvent e){
        click();
    }

    protected void click(){
    }
}

/**
 * Моя MultiButton
 */
class myMultiButton extends HintedButton {
    /**
     * Button captions.
     */
    private final String captions[];

    /**
     * Per-caption hints.
     */
    private final String hints[];

    /**
     * Current state.
     */
    private int state;

    public int kol = 0;

    /**
     * Creates a new <code>MultiButton</code>.
     *
     * @param config visualizer configuration.
     * @param states name of the paremeter to get configuration from.
     */
    public myMultiButton(Configuration config, String[] states) {
        captions = new String[states.length];
        hints = new String[states.length];
        for (int i = 0; i < states.length; i++) {
            captions[i] = config.getParameter(states[i]);
            hints[i] = config.getParameter(states[i] + "-hint");
        }
        setState(0);
    }

    /**
     * Gets state of the button.
     *
     * @return current state.
     */
    public final int getState() {
        return state;
    }

    /**                                                	
     * Sets state of the button.
     *
     * @param state new state.
     */
    public final void setState(int state) {
        if (state < 0) state = 0;
        if (state >= captions.length) {
            state = captions.length - 1;
        }
        this.state = state;
        setLabel(captions[state]);
        setHint(hints[state]);
    }

    /**
     * Invoked when users click on button.
     *
     * @param state current button state.
     *
     * @return button state to set.
     */
    protected int click(int state) {
        state = (state + 1) % captions.length;
        return state;
    }

    /**
     * Click to button
     */
    protected final void click() {
        setState(click(state));
        press();
    }

    /**
     * Press to Button
     */
    public void press(){
 
    }
}
