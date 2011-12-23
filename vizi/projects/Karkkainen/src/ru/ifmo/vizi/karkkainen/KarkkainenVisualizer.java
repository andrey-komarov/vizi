package ru.ifmo.vizi.karkkainen;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.*;

/**
 * Karkkainen-Sanders suffix array constructing applet.
 *
 * @author  Anton Feskov
 */
public final class KarkkainenVisualizer 
    extends Base 
    implements AdjustmentListener
{
    /**
     * └тЄюьрЄ рыуюЁшЄьр ╩ Ёъърщэхэр
     */
    private final Karkkainen auto;

    /**
     * ─рээ√х ртЄюьрЄр рыуюЁшЄьр ╩ Ёъърщэхэр
     */
    private final Karkkainen.Data data;


    /**
     * Number of elements in array.
     */
    private final AdjustablePanel elements;

    /**
     * Array shape style set.
     */
    private final ShapeStyle[] styleArray;

    /**
     * Save/load dialog.
     */
    private SaveLoadDialog saveLoadDialog;
    

    private MyArray s = new MyArray();
    private MyArray sNum = new MyArray();

    private MyArray[] recStr = new MyArray[0];
    private MyArray[] recStrNum = new MyArray[0];
    
    private MyArray[][] tripples = new MyArray[0][0];

    /**
     * Creates a new Find Maximum visualizer.
     *
     * @param parameters visualizer parameters.
     */
    public KarkkainenVisualizer(VisualizerParameters parameters) {
        super(parameters);
        auto = new Karkkainen(locale);
        data = auto.d;
        data.visualizer = this;


        styleArray = ShapeStyle.loadStyleSet(config, "array");

        elements = new AdjustablePanel(config, "elements");
        elements.addAdjustmentListener(this);

        randomize(7);

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
        bottomPanel.add(new HintedButton(config, "button-random"){
            protected void click() {
                randomize(data.s.length);
            }
        });

        if (config.getBoolean("button-ShowSaveLoad")) { 
            bottomPanel.add(new HintedButton(config, "button-SaveLoad") {
                protected void click() {
                    saveLoadDialog.center();
                    StringBuffer buffer = new StringBuffer();

                    buffer.append("/* ").append(
                        I18n.message(
                            config.getParameter("StringComment"), 
                            new Integer(elements.getMinimum()),
                            new Integer(elements.getMaximum())
                        )
                    ).append(" */\n");

                    char[] s = data.s;

                    buffer.append("String = ");
                    for (int i = 0; i < s.length; i++) {
                        buffer.append(s[i]);
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
                
                tokenizer.expect("String");
                tokenizer.expect("=");

                char[] s = tokenizer.nextWord().toCharArray();
                if (s.length < elements.getMinimum() || s.length > elements.getMaximum()) {
                    throw new Exception(config.getParameter("OutOfBoundsComment"));
                }
                for (int i = 0; i < s.length; i++) {
                    if (s[i] < 'a' || 'z' < s[i]) {
                        throw new Exception(I18n.message(
                            config.getParameter("WrongCharacterComment"), 
                            new Integer(i),
                            new Character(s[i])
                        ));
                    }
                }
                tokenizer.expect("Step");
                tokenizer.expect("=");
                int step = tokenizer.nextInt();
                tokenizer.expectEOF();
                                
                init(s);

                auto.getController().rewind(step);

                return true;
            }
        };

        return panel;
    }

    /**
     * Rewinds algorithm to the specified step.
     *
     * @param step spet of the algorith to rewind to.
     */
    private void rewind(int step) {
        while (!auto.isAtStart() && auto.getStep() > step) {
            auto.stepBackward(0);
        }
        draw();
    }
    

    /**
     * Invoked on adjustment event.
     *
     * @param event event to process.
     */
    public void adjustmentValueChanged(AdjustmentEvent event) {
        if (event.getSource() == elements) {
            randomize(event.getValue());
        }
    }


    private Vector all = new Vector();

    private void fill(int[] a, int val) {
        for (int i = 0; i < a.length; i++) {
            a[i] = val;
        }
    }



    private void init(char[] str) {
        //Очистка панели
        for (int i = 0; i < all.size(); i++) {
            ((MyArrayContainer)all.elementAt(i)).arr.visible = false;
        }
        s.visible = false;

        clientPane.doLayout();

        for (int i = 0; i < all.size(); i++) {
            ((MyArrayContainer)all.elementAt(i)).remove();
        }
        s.remove(clientPane);

        //Перемотка алгоритма в начало и инициализация

        rewind(0);
        data.s = str;
        int size = data.s.length;

        //Построение всех фигур
        all = new Vector();
        //Выделение нужных даных непосредственно из алгоритма.
        (new KarkkainenAlgorythm()).suffArray(data);

        //Вычисление некоторых констант
        double sx;
        if (data.s.length % 3 == 1) {
            aspectRatio = (2.0 * (4 + data.recStr.length)) / (3 + data.s.length);        
            sx = 1.0 / (3 + data.s.length);
        } else {
            aspectRatio = (2.0 * (4 + data.recStr.length)) / (2 + data.s.length);            
            sx = 1.0 / (2 + data.s.length);
        }
        
        double sy = sx / aspectRatio;

        s = new MyArray(sx, sy, sx, sy, sx, 0, true, data.s.length);

        int recStrN = data.recStr.length;
             
        {   //Номера над строками
            data.sNumSt = new int[data.s.length];
            fill(data.sNumSt, 1);
            int[] tmp = new int[data.s.length];
            for (int i = 0; i < data.s.length; i++) {
                tmp[i] = i;
            }
            sNum = new MyArray(sx, 0.5 * sy, sx, 0.5 * sy, sx, 0, true, data.s.length);
            all.insertElementAt(new MyArrayContainer(sNum, tmp, data.sNumSt, styleArray), all.size());
        } 

        //Рекурсивно построенные строки
        data.recStrSt = new int[recStrN][];
        recStr = new MyArray[recStrN];
        data.recStrNumSt = new int[recStrN][];
        recStrNum = new MyArray[recStrN];
        for (int i = 0; i < recStrN; i++) {
            
            recStr[i] = new MyArray(sx, (2 * i + 3) * sy, sx, sy, sx, 0, true, data.recStr[i].length);
            data.recStrSt[i] = new int[data.recStr[i].length];
            fill(data.recStrSt[i], -1);

            data.recStrNumSt[i] = new int[data.recStr[i].length];
            fill(data.recStrNumSt[i], -1);
            recStrNum[i] = new MyArray(sx, (2 * i + 2.5) * sy, sx, sy / 2, sx, 0, true, data.recStr[i].length);
        }

        for (int i = 0; i < recStr.length; i++) {
            all.insertElementAt(new MyArrayContainer(recStr[i], data.recStr[i], data.recStrSt[i], styleArray), all.size());
        }

        for (int i = 0; i < recStr.length; i++) {
            int[] tmp = new int[data.recStr[i].length];
            for (int j = 0; j < tmp.length; j++) {
                tmp[j] = j;
            }
            all.insertElementAt(new MyArrayContainer(recStrNum[i], tmp, data.recStrNumSt[i], styleArray), all.size());
        }

//        System.out.println("=) 1");


    
        tripples = new MyArray[recStrN][];
        data.tripplesSt = new int[recStrN][][];
        for (int i = 0; i < recStrN; i++) {
          //  System.out.println(i);
            int n = data.recStr[i].length;
            int n12 = n - (n + 2) / 3; //Количество нужных суффиксов
            tripples[i] = new MyArray[n12];
            data.tripplesSt[i] = new int[n12][];
            int c = 0;
            for (int j = 0; j < data.recStr[i].length; j++) {
            //    System.out.println("---" + j);
                if (j % 3 == 0) continue;
                int[] t = new int[3];
                for (int k = 0; k < 3; k++) {
               //     System.out.println("-----------" + k + " " + data.recStr[i].length);
                    t[k] = ((j + k) < data.recStr[i].length) ? data.recStr[i][j + k] : 0;
                }                      
             //   System.out.println("bugoga " + c + " n:" + n + " n12:" + n12);
                data.tripplesSt[i][c] = new int[t.length];
                fill(data.tripplesSt[i][c], -1);
             //   System.out.println("dd");
                tripples[i][c] = new MyArray((1 + j) * sx, (2 * i + 4.5) * sy, sx, sy, 0, sy, true, t.length);
             //   System.out.println("dd");
                all.insertElementAt(new MyArrayContainer(tripples[i][c], t, data.tripplesSt[i][c], styleArray), all.size());
                c++;
            }
        }
       // System.out.println("3453525");
        
   

        data.tripplesNumSt = new int[recStrN][][];
        data.recS1St = new int[recStrN][];
        for (int i = 0; i < recStrN; i++) {
            int m = data.tripplesNum[i].length;
            
            data.tripplesNumSt[i] = new int[m][1];

            int p = 2;
            for (int j = 0; j < m; j++) {
                data.tripplesNumSt[i][j][0] = -1;
                all.insertElementAt(new MyArrayContainer(new MyArray(p * sx, (2 * i + 8) * sy, sx, sy, sx, 0, true, 1), 
                                    data.tripplesNum[i][j], data.tripplesNumSt[i][j], styleArray), all.size());        
                p += (j % 2 == 0 ? 1 : 2);
            }

            int tlen = data.recStr[i].length;
            if (data.recStr[i][tlen - 1] == 0) tlen--;
            data.recS1St[i] = new int[tlen];
            fill(data.recS1St[i], -1);
            all.insertElementAt(new MyArrayContainer(new MyArray(2 * sx, (2 * i + 7.5) * sy, sx, sy, sx, 0, true, tlen), 
                                                     data.recStr[i], data.recS1St[i], styleArray), all.size());        

        }

        data.lastSuffArrSt = new int[data.suffArr[recStrN - 1].length][1];
        for (int i = 0; i < data.suffArr[recStrN - 1].length; i++) {
            data.lastSuffArrSt[i][0] = -1;
            all.insertElementAt(new MyArrayContainer(new MyArray((1 + i) * sx, (2 * recStrN + 2.5) * sy, sx, sy, sx, 0, true, 1), 
                                                     new int[]{data.suffArr[recStrN - 1][i]}, data.lastSuffArrSt[i], styleArray), all.size());        
        }


        data.suffArrSt = new int[recStrN][];
        data.suffArrSt2 = new int[recStrN][];
        for (int i = 0; i < recStrN; i++) {
            data.suffArrSt[i] = new int[data.suffArr[i].length];
            data.suffArrSt2[i] = new int[data.suffArr[i].length];
            fill(data.suffArrSt[i], -1);
            fill(data.suffArrSt2[i], -1);
            double dsh = i == 0 ? 2.5 : 4;
            double ms = i == 0 ? 1 : 0.9;
            int len = data.recStr[i].length;
            if (data.recStr[i][len - 1] == 0) len--;
            int sl = data.recStr[0].length;
            if (i == 0) {
                sl = data.s.length;
            }
            all.insertElementAt(new MyArrayContainer(new MyArray((1 + sl - ms * len) *sx, (2 * i + dsh) * sy, ms * sx, ms * sy, ms * sx, 0, true, data.suffArr[i].length), 
                                                     data.suffArr[i], data.suffArrSt[i], styleArray), all.size());        
            all.insertElementAt(new MyArrayContainer(new MyArray((1 + sl - ms * len) *sx, (2 * i + 7.5) * sy, ms * sx, ms * sy, ms * sx, 0, true, data.suffArr[i].length), 
                                                     data.suffArr[i], data.suffArrSt2[i], styleArray), all.size());                                                     

        }


        data.invSuffArr = new int[recStrN][][];
        data.invSuffArrSt = new int[recStrN][][];
        data.invSuffArrNumSt = new int[recStrN][][];
        for (int i = 0; i < recStrN; i++) {
            int m = data.suffArr[i].length;
            data.invSuffArr[i] = new int[m][1];
            data.invSuffArrSt[i] = new int[m][1];
            data.invSuffArrNumSt[i] = new int[m][1];
            for (int j = 0; j < m; j++) {
                data.invSuffArr[i][data.suffArr[i][j]][0] = j;
            }
            double ms = 0.9;
            for (int j = 0; j < m; j++) {
                data.invSuffArrSt[i][j][0] = -1;
                int len = data.recStr[i].length;
                if (data.recStr[i][len - 1] == 0) len--;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - j)) * sx, (2 * i + 6) * sy, ms * sx, ms * sy, ms * sx, 0, true, 1), 
                                                         data.invSuffArr[i][j], data.invSuffArrSt[i][j], styleArray), all.size());
                data.invSuffArrNumSt[i][j][0] = -1;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - j)) * sx, (2 * i + 5.5) * sy, ms * sx, 0.5 * sy, ms * sx, 0, true, 1), 
                                                         new int[]{j}, data.invSuffArrNumSt[i][j], styleArray), all.size());

            }
            
        }

        data.trp23Num = new int[recStrN][][];
        data.trp23NumSt = new int[recStrN][][];
        for (int i = 0; i < recStrN; i++) {
            int m = data.suffArr[i].length;
            data.trp23Num[i] = new int[m][1];
            data.trp23NumSt[i] = new int[m][1];
            for (int j = 0; j < m; j++) {
                data.trp23NumSt[i][j][0] = -1;
            }
            int c = (m + 1) / 2;
            for (int j = 0; j < c; j++) {
                data.trp23Num[i][2 * j][0] = data.invSuffArr[i][j][0];
                all.insertElementAt(new MyArrayContainer(new MyArray((2 + 3 * j) * sx, (2 * i + 2.5) * sy, sx, sy, sx, 0, true, 1), 
                                                         data.invSuffArr[i][j], data.trp23NumSt[i][j], styleArray), all.size());

            }
            for (int j = c; j < m; j++) {
                data.trp23Num[i][2 * (j - c) + 1][0] = data.invSuffArr[i][j][0];
                all.insertElementAt(new MyArrayContainer(new MyArray((3 + 3 * (j - c)) * sx, (2 * i + 2.5) * sy, sx, sy, sx, 0, true, 1), 
                                                         data.invSuffArr[i][j], data.trp23NumSt[i][j], styleArray), all.size());
            }
        }


        data.suff23List = new int[recStrN][][];
        data.suff23ListSt = new int[recStrN][][];
        data.suff23ListSt2 = new int[recStrN][][];
        data.suff23ListNumSt = new int[recStrN][][];
        for (int i = 1; i < recStrN; i++) {
            int m = data.suffArr[i].length;        
            data.suff23List[i] = new int[m][1];
            data.suff23ListSt[i] = new int[m][1];
            data.suff23ListSt2[i] = new int[m][1];
            data.suff23ListNumSt[i] = new int[m][1];
            int len = data.recStr[i].length;
            if (data.recStr[i][len - 1] == 0) len--;
            for (int j = 0; j < m; j++) {
                data.suff23ListSt[i][j][0] = -1;
                data.suff23ListSt2[i][j][0] = -1;
            }
            int c = (m + 1) / 2;
            double ms = 0.9;
            for (int j = 0; j < c; j++) {
                data.suff23List[i][data.invSuffArr[i][j][0]][0] = 1 + 3 * j;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - data.invSuffArr[i][j][0])) * sx, (2 * i + 8) * sy, ms * sx, ms * sy, ms * sx, 0, true, 1), 
                                                         new int[]{1 + 3 * j}, data.suff23ListSt[i][data.invSuffArr[i][j][0]], styleArray), all.size());
            }
            for (int j = c; j < m; j++) {
                data.suff23List[i][data.invSuffArr[i][j][0]][0] = 2 + 3 * (j - c);
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - data.invSuffArr[i][j][0])) * sx, (2 * i + 8) * sy, ms * sx, ms * sy, ms * sx, 0, true, 1), 
                                                         new int[]{2 + 3 * (j - c)}, data.suff23ListSt[i][data.invSuffArr[i][j][0]], styleArray), all.size());
            }
            for (int j = 0; j < c; j++) {
                data.suff23List[i][data.invSuffArr[i][j][0]][0] = 1 + 3 * j;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - data.invSuffArr[i][j][0])) * sx, (2 * i + 4) * sy, ms * sx, ms * sy, ms * sx, 0, true, 1), 
                                                         new int[]{1 + 3 * j}, data.suff23ListSt2[i][data.invSuffArr[i][j][0]], styleArray), all.size());
            }
            for (int j = c; j < m; j++) {
                data.suff23List[i][data.invSuffArr[i][j][0]][0] = 2 + 3 * (j - c);
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - data.invSuffArr[i][j][0])) * sx, (2 * i + 4) * sy, ms * sx, ms * sy, ms * sx, 0, true, 1), 
                                                         new int[]{2 + 3 * (j - c)}, data.suff23ListSt2[i][data.invSuffArr[i][j][0]], styleArray), all.size());
            }

            for (int j = 0; j < m; j++) {
                data.suff23ListNumSt[i][j][0] = -1;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + data.recStr[0].length - ms * (len - j)) * sx, (2 * i + 7.5) * sy, ms * sx, sy / 2, ms * sx, 0, true, 1), 
                                                         new int[]{j}, data.suff23ListNumSt[i][j], styleArray), all.size());
            }
        }

        data.pairs = new int[recStrN][][];
        data.pairsSt = new int[recStrN][][];
        for (int i = 1; i < recStrN; i++) {
            int m = data.recStr[i - 1].length - data.suffArr[i].length;
            data.pairs[i] = new int[m][2];
            data.pairsSt[i] = new int[m][2];
            double ms = 0.9;
            for (int j = 0; j < m; j++) {
                data.pairs[i][j][0] = data.recStr[i - 1][3 * j];
                data.pairs[i][j][1] = data.trp23Num[i][2 * j][0];
                fill(data.pairsSt[i][j], -1);
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + j) * sx, (2 * i + 4) * sy, ms * sx, ms * sy, 0, ms * sy, true, 2), 
                                                         data.pairs[i][j], data.pairsSt[i][j], styleArray), all.size());
            }    
        } 

        data.pairsNumSt = new int[recStrN][][];
        for (int i = 1; i < recStrN; i++) {
            int m = data.recStr[i - 1].length - data.suffArr[i].length;
            data.pairsNumSt[i] = new int[m][1];
            double ms = 0.9;
            for (int j = 0; j < m; j++) {
                data.pairsNumSt[i][j][0] = -1;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + j) * sx, (2 * i + 6) * sy, ms * sx, ms * sy, 0, ms * sy, true, 1), 
                                                         data.pairsNum[i][j], data.pairsNumSt[i][j], styleArray), all.size());
            }
        }


        data.suff0List = new int[recStrN][][];
        data.suff0ListSt = new int[recStrN][][];
        data.suff0ListSt2 = new int[recStrN][][];
        data.suff0ListNumSt = new int[recStrN][][];
        for (int i = 1; i < recStrN; i++) {
            int m = data.recStr[i - 1].length - data.suffArr[i].length;
            data.suff0List[i] = new int[m][1];
            data.suff0ListSt[i] = new int[m][1];
            data.suff0ListSt2[i] = new int[m][1];
            data.suff0ListNumSt[i] = new int[m][1];
            for (int j = 0; j < m; j++) {
                data.suff0List[i][data.pairsNum[i][j][0]][0] = 3 * j;
            }
            double ms = 0.9;
            for (int j = 0; j < m; j++) {
                data.suff0ListSt[i][j][0] = -1;
                data.suff0ListSt2[i][j][0] = -1;
                data.suff0ListNumSt[i][j][0] = -1;
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + j * ms) * sx, (2 * i + 8) * sy, ms * sx, ms * sy, 0, ms * sy, true, 1), 
                                                         data.suff0List[i][j], data.suff0ListSt[i][j], styleArray), all.size());
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + j * ms) * sx, (2 * i + 4) * sy, ms * sx, ms * sy, 0, ms * sy, true, 1), 
                                                         data.suff0List[i][j], data.suff0ListSt2[i][j], styleArray), all.size());
                all.insertElementAt(new MyArrayContainer(new MyArray((1 + j * ms) * sx, (2 * i + 7.5) * sy, ms * sx, sy / 2, 0, ms * sy, true, 1), 
                                                         new int[]{j}, data.suff0ListNumSt[i][j], styleArray), all.size());
            }
        }
         

        

                    
       
       
        for (int i = 0; i < all.size(); i++) {
            ((MyArrayContainer)all.elementAt(i)).create();
        }

        data.sSt = new int[data.s.length];
        fill(data.sSt, 1);
        s.create(styleArray, clientPane);
       
        draw();
    }


    private void randomize(int size) {
        char[] str = new char[size];
        for (int i = 0; i < size; i++) {
            str[i] = (char)('a' + Math.round((Math.random() * 25)));
        }
        init(str);
    }






    public void draw() { 
        for (int i = 0; i < all.size(); i++) {
         //   System.out.println("---" + i);
            ((MyArrayContainer)all.elementAt(i)).upDate();
        }
        s.upDate(convert(data.s), data.sSt);
        clientPane.doLayout();
    }


    /**
     * Invoked when client pane shoud be layouted.
     *
     * @param clientWidth client pane width.
     * @param clientHeight client pane height.
     */
    protected void layoutClientPane(int clientWidth, int clientHeight) {
        for (int i = 0; i < all.size(); i++) {
            ((MyArrayContainer)all.elementAt(i)).draw(clientWidth, clientHeight);
        }        
        s.draw(clientWidth, clientHeight, aspectRatio);
    }

    private double aspectRatio = 1;
    

    public class MyArrayContainer {
        MyArray arr;
        int[] val, style;
        ShapeStyle[] shSt;

        public MyArrayContainer(MyArray arr, int[] val, int[] style, ShapeStyle[] shSt) {
            this.arr = arr;
            this.val = val;
            this.style = style;
            this.shSt = shSt;
        }

        public void upDate() {
            arr.upDate(convert(val), style);
        }

        public void draw(int w, int h) {
            arr.draw(w, h, aspectRatio);
        }

        public void create() {
            arr.create(shSt, clientPane);
        }

        public void remove() {
            arr.remove(clientPane);
        }
    }


    String[] convert(int[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = Integer.toString(arr[i]);
        }
        return ans;
    }
        
    String[] convert(char[] s) {
        String[] ans = new String[s.length];
        for (int i = 0; i < s.length; i++) {
            ans[i] = "" + s[i];
        }
        return ans;
    }

    String[] convert(String s) {
        return convert(s.toCharArray());
    }

    
}
