package ru.ifmo.vizi.hungarian;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Hungarian extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public Hungarian(Locale locale) {
        super("ru.ifmo.vizi.hungarian.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public HungarianVisualizer visualizer = null;

        /**
          * Основная матрица.
          */
        public int[][] matrix = new int[][]{{1, 2, 3}, {2, 3, 1}, {3, 2, 1}};

        /**
          * Changes for rows.
          */
        public int[] rowChange = new int[]{0, 0, 0};

        /**
          * Partners for rows.
          */
        public int[] partner = new int[]{-1, -1, -1};

        /**
          * Partners for columns.
          */
        public int[] cPartner = new int[]{-1, -1, -1};

        /**
          * Changes for columns.
          */
        public int[] columnChange = new int[]{0, 0, 0};

        /**
          * Local minimums.
          */
        public int[] localMinimum = new int[]{1000, 1000, 1000};

        /**
          * Checked rows.
          */
        public boolean[] checkedRows = null;

        /**
          * Checked columns.
          */
        public boolean[] checkedColumns = null;

        /**
          * Used columns.
          */
        public boolean[] used = new boolean[3];

        /**
          * Cycle counter.
          */
        public int i = 0;

        /**
          * Cycle counter.
          */
        public int j = 0;

        /**
          * Cycle counter.
          */
        public int dj = 0;

        /**
          * swap.
          */
        public int swp = 0;

        /**
          * Cycle counter.
          */
        public int di = 0;

        /**
          * Difference.
          */
        public int diff = 0;

        /**
          * Current row.
          */
        public int currentRow = -1;

        /**
          * Current minimum.
          */
        public int currentMinimum = -1;

        /**
          * Last row.
          */
        public int lastRow = -1;

        /**
          * Global minimum J.
          */
        public int globalMinimumJ = -1;

        /**
          * Global minimum I.
          */
        public int globalMinimumI = -1;

        /**
          * break.
          */
        public boolean br = true;

        public String toString() {
            		return "";
        }
    }

    /**
      * Ищет максимум в массиве.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 54;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                54, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Start of cycle", 
                    "Cycle", 
                    "Initialization of local minimum", 
                    "Cycle", 
                    "Initialization of one element", 
                    "Moving to next row", 
                    "Searching for new row partner", 
                    "Initialization of loop variable", 
                    "Searching for minimum", 
                    "Testing next element for minimum", 
                    "Testing next element for minimum (окончание)", 
                    "Updating minimum", 
                    "Testing current element for freedom", 
                    "Testing current element for freedom (окончание)", 
                    "Testing for minimum", 
                    "Testing for minimum (окончание)", 
                    "Assignment", 
                    "Increasing counter", 
                    "Initializing variables for new loops", 
                    "Loor for i", 
                    "Loor for j", 
                    "Checking cell for beeing checked", 
                    "Checking cell for beeing checked (окончание)", 
                    "Decreasing element", 
                    "Checking cell for beeing checked", 
                    "Checking cell for beeing checked (окончание)", 
                    "Decreasing element", 
                    "Increasing dj", 
                    "Changing deltaRows", 
                    "Changing deltaRows (окончание)", 
                    "Change one element", 
                    "Changing deltaColumns", 
                    "Changing deltaColumns (окончание)", 
                    "Change one element", 
                    "Increasing di", 
                    "Initializing variables for new loops", 
                    "if", 
                    "if (окончание)", 
                    "step", 
                    "Checking is it possible to come out of loop", 
                    "Checking is it possible to come out of loop (окончание)", 
                    "Going out of loop", 
                    "Not going out of loop", 
                    "Drawing all objects", 
                    "Recalculating partners for some rows", 
                    "Recalculate partner for last row", 
                    "Initialize di", 
                    "Searching for partner for globalMinimumJ", 
                    "Check", 
                    "Check (окончание)", 
                    "reCalc", 
                    "aaaaaaa", 
                    "Last recalculation", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Start of cycle 
                    -1, // Cycle 
                    -1, // Initialization of local minimum 
                    -1, // Cycle 
                    -1, // Initialization of one element 
                    0, // Moving to next row 
                    -1, // Searching for new row partner 
                    -1, // Initialization of loop variable 
                    -1, // Searching for minimum 
                    0, // Testing next element for minimum 
                    -1, // Testing next element for minimum (окончание) 
                    -1, // Updating minimum 
                    0, // Testing current element for freedom 
                    -1, // Testing current element for freedom (окончание) 
                    -1, // Testing for minimum 
                    -1, // Testing for minimum (окончание) 
                    0, // Assignment 
                    -1, // Increasing counter 
                    -1, // Initializing variables for new loops 
                    -1, // Loor for i 
                    -1, // Loor for j 
                    -1, // Checking cell for beeing checked 
                    -1, // Checking cell for beeing checked (окончание) 
                    -1, // Decreasing element 
                    -1, // Checking cell for beeing checked 
                    -1, // Checking cell for beeing checked (окончание) 
                    -1, // Decreasing element 
                    -1, // Increasing dj 
                    -1, // Changing deltaRows 
                    -1, // Changing deltaRows (окончание) 
                    -1, // Change one element 
                    -1, // Changing deltaColumns 
                    -1, // Changing deltaColumns (окончание) 
                    -1, // Change one element 
                    -1, // Increasing di 
                    -1, // Initializing variables for new loops 
                    -1, // if 
                    -1, // if (окончание) 
                    -1, // step 
                    -1, // Checking is it possible to come out of loop 
                    -1, // Checking is it possible to come out of loop (окончание) 
                    0, // Going out of loop 
                    0, // Not going out of loop 
                    0, // Drawing all objects 
                    -1, // Recalculating partners for some rows 
                    0, // Recalculate partner for last row 
                    -1, // Initialize di 
                    -1, // Searching for partner for globalMinimumJ 
                    -1, // Check 
                    -1, // Check (окончание) 
                    -1, // reCalc 
                    -1, // aaaaaaa 
                    0, // Last recalculation 
                    Integer.MAX_VALUE, // Конечное состояние 
                } 
            ); 
        }

        /**
          * Сделать один шаг автомата в перед.
          */
        protected void doStepForward(int level) {
            // Переход в следующее состояние
            switch (state) {
                case START_STATE: { // Начальное состояние
                    state = 1; // Start of cycle
                    break;
                }
                case 1: { // Start of cycle
                    stack.pushBoolean(false); 
                    state = 2; // Cycle
                    break;
                }
                case 2: { // Cycle
                    if (d.i < d.matrix.length - 1) {
                        state = 3; // Initialization of local minimum
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Initialization of local minimum
                    stack.pushBoolean(false); 
                    state = 4; // Cycle
                    break;
                }
                case 4: { // Cycle
                    if (d.j < d.matrix.length) {
                        state = 5; // Initialization of one element
                    } else {
                        state = 6; // Moving to next row
                    }
                    break;
                }
                case 5: { // Initialization of one element
                    stack.pushBoolean(true); 
                    state = 4; // Cycle
                    break;
                }
                case 6: { // Moving to next row
                    stack.pushBoolean(false); 
                    state = 7; // Searching for new row partner
                    break;
                }
                case 7: { // Searching for new row partner
                    if (d.br) {
                        state = 8; // Initialization of loop variable
                    } else {
                        stack.pushBoolean(false); 
                        state = 45; // Recalculating partners for some rows
                    }
                    break;
                }
                case 8: { // Initialization of loop variable
                    stack.pushBoolean(false); 
                    state = 9; // Searching for minimum
                    break;
                }
                case 9: { // Searching for minimum
                    if (d.j < d.matrix.length) {
                        state = 10; // Testing next element for minimum
                    } else {
                        state = 19; // Initializing variables for new loops
                    }
                    break;
                }
                case 10: { // Testing next element for minimum
                    if ((!d.checkedColumns[d.j]) && (d.matrix[d.lastRow][d.j] < d.matrix[d.localMinimum[d.j]][d.j])) {
                        state = 12; // Updating minimum
                    } else {
                        stack.pushBoolean(false); 
                        state = 11; // Testing next element for minimum (окончание)
                    }
                    break;
                }
                case 11: { // Testing next element for minimum (окончание)
                    state = 13; // Testing current element for freedom
                    break;
                }
                case 12: { // Updating minimum
                    stack.pushBoolean(true); 
                    state = 11; // Testing next element for minimum (окончание)
                    break;
                }
                case 13: { // Testing current element for freedom
                    if ((!d.checkedColumns[d.j])) {
                        state = 15; // Testing for minimum
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // Testing current element for freedom (окончание)
                    }
                    break;
                }
                case 14: { // Testing current element for freedom (окончание)
                    state = 18; // Increasing counter
                    break;
                }
                case 15: { // Testing for minimum
                    if ((d.globalMinimumJ == -1) || (d.matrix[d.localMinimum[d.j]][d.j] < d.matrix[d.globalMinimumI][d.globalMinimumJ])) {
                        state = 17; // Assignment
                    } else {
                        stack.pushBoolean(false); 
                        state = 16; // Testing for minimum (окончание)
                    }
                    break;
                }
                case 16: { // Testing for minimum (окончание)
                    stack.pushBoolean(true); 
                    state = 14; // Testing current element for freedom (окончание)
                    break;
                }
                case 17: { // Assignment
                    stack.pushBoolean(true); 
                    state = 16; // Testing for minimum (окончание)
                    break;
                }
                case 18: { // Increasing counter
                    stack.pushBoolean(true); 
                    state = 9; // Searching for minimum
                    break;
                }
                case 19: { // Initializing variables for new loops
                    stack.pushBoolean(false); 
                    state = 20; // Loor for i
                    break;
                }
                case 20: { // Loor for i
                    if (d.di < d.matrix.length) {
                        stack.pushBoolean(false); 
                        state = 21; // Loor for j
                    } else {
                        state = 36; // Initializing variables for new loops
                    }
                    break;
                }
                case 21: { // Loor for j
                    if (d.dj < d.matrix.length) {
                        state = 22; // Checking cell for beeing checked
                    } else {
                        state = 29; // Changing deltaRows
                    }
                    break;
                }
                case 22: { // Checking cell for beeing checked
                    if ((d.checkedColumns[d.dj])) {
                        state = 24; // Decreasing element
                    } else {
                        stack.pushBoolean(false); 
                        state = 23; // Checking cell for beeing checked (окончание)
                    }
                    break;
                }
                case 23: { // Checking cell for beeing checked (окончание)
                    state = 25; // Checking cell for beeing checked
                    break;
                }
                case 24: { // Decreasing element
                    stack.pushBoolean(true); 
                    state = 23; // Checking cell for beeing checked (окончание)
                    break;
                }
                case 25: { // Checking cell for beeing checked
                    if ((d.checkedRows[d.di])) {
                        state = 27; // Decreasing element
                    } else {
                        stack.pushBoolean(false); 
                        state = 26; // Checking cell for beeing checked (окончание)
                    }
                    break;
                }
                case 26: { // Checking cell for beeing checked (окончание)
                    state = 28; // Increasing dj
                    break;
                }
                case 27: { // Decreasing element
                    stack.pushBoolean(true); 
                    state = 26; // Checking cell for beeing checked (окончание)
                    break;
                }
                case 28: { // Increasing dj
                    stack.pushBoolean(true); 
                    state = 21; // Loor for j
                    break;
                }
                case 29: { // Changing deltaRows
                    if ((d.checkedRows[d.di])) {
                        state = 31; // Change one element
                    } else {
                        stack.pushBoolean(false); 
                        state = 30; // Changing deltaRows (окончание)
                    }
                    break;
                }
                case 30: { // Changing deltaRows (окончание)
                    state = 32; // Changing deltaColumns
                    break;
                }
                case 31: { // Change one element
                    stack.pushBoolean(true); 
                    state = 30; // Changing deltaRows (окончание)
                    break;
                }
                case 32: { // Changing deltaColumns
                    if ((d.checkedColumns[d.di])) {
                        state = 34; // Change one element
                    } else {
                        stack.pushBoolean(false); 
                        state = 33; // Changing deltaColumns (окончание)
                    }
                    break;
                }
                case 33: { // Changing deltaColumns (окончание)
                    state = 35; // Increasing di
                    break;
                }
                case 34: { // Change one element
                    stack.pushBoolean(true); 
                    state = 33; // Changing deltaColumns (окончание)
                    break;
                }
                case 35: { // Increasing di
                    stack.pushBoolean(true); 
                    state = 20; // Loor for i
                    break;
                }
                case 36: { // Initializing variables for new loops
                    state = 37; // if
                    break;
                }
                case 37: { // if
                    if (d.used[d.globalMinimumJ]) {
                        state = 39; // step
                    } else {
                        stack.pushBoolean(false); 
                        state = 38; // if (окончание)
                    }
                    break;
                }
                case 38: { // if (окончание)
                    state = 40; // Checking is it possible to come out of loop
                    break;
                }
                case 39: { // step
                    stack.pushBoolean(true); 
                    state = 38; // if (окончание)
                    break;
                }
                case 40: { // Checking is it possible to come out of loop
                    if (!d.used[d.globalMinimumJ]) {
                        state = 42; // Going out of loop
                    } else {
                        state = 43; // Not going out of loop
                    }
                    break;
                }
                case 41: { // Checking is it possible to come out of loop (окончание)
                    state = 44; // Drawing all objects
                    break;
                }
                case 42: { // Going out of loop
                    stack.pushBoolean(true); 
                    state = 41; // Checking is it possible to come out of loop (окончание)
                    break;
                }
                case 43: { // Not going out of loop
                    stack.pushBoolean(false); 
                    state = 41; // Checking is it possible to come out of loop (окончание)
                    break;
                }
                case 44: { // Drawing all objects
                    stack.pushBoolean(true); 
                    state = 7; // Searching for new row partner
                    break;
                }
                case 45: { // Recalculating partners for some rows
                    if (d.globalMinimumI != d.i) {
                        state = 46; // Recalculate partner for last row
                    } else {
                        state = 53; // Last recalculation
                    }
                    break;
                }
                case 46: { // Recalculate partner for last row
                    state = 47; // Initialize di
                    break;
                }
                case 47: { // Initialize di
                    stack.pushBoolean(false); 
                    state = 48; // Searching for partner for globalMinimumJ
                    break;
                }
                case 48: { // Searching for partner for globalMinimumJ
                    if (d.di < d.matrix.length) {
                        state = 49; // Check
                    } else {
                        stack.pushBoolean(true); 
                        state = 45; // Recalculating partners for some rows
                    }
                    break;
                }
                case 49: { // Check
                    if ((d.checkedRows[d.di]) &&((!d.checkedColumns[d.globalMinimumJ] == false) || (d.matrix[d.di][d.globalMinimumJ] < d.matrix[d.globalMinimumI][d.globalMinimumJ]))) {
                        state = 51; // reCalc
                    } else {
                        stack.pushBoolean(false); 
                        state = 50; // Check (окончание)
                    }
                    break;
                }
                case 50: { // Check (окончание)
                    state = 52; // aaaaaaa
                    break;
                }
                case 51: { // reCalc
                    stack.pushBoolean(true); 
                    state = 50; // Check (окончание)
                    break;
                }
                case 52: { // aaaaaaa
                    stack.pushBoolean(true); 
                    state = 48; // Searching for partner for globalMinimumJ
                    break;
                }
                case 53: { // Last recalculation
                    stack.pushBoolean(true); 
                    state = 2; // Cycle
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Start of cycle
                    startSection();
                    storeField(d, "i");
                    d.i = -1;
                    break;
                }
                case 2: { // Cycle
                    break;
                }
                case 3: { // Initialization of local minimum
                    startSection();
                    storeField(d, "localMinimum");
                    					d.localMinimum = new int[d.matrix.length];
                    storeField(d, "j");
                    					d.j = 0;
                    break;
                }
                case 4: { // Cycle
                    break;
                }
                case 5: { // Initialization of one element
                    startSection();
                    storeArray(d.localMinimum, d.j);
                    						d.localMinimum[d.j] = d.i + 1;
                    storeField(d, "j");
                    						d.j = d.j + 1;
                    break;
                }
                case 6: { // Moving to next row
                    startSection();
                                storeField(d, "i");
                                		d.i = d.i + 1;
                    storeField(d, "currentRow");
                    					d.currentRow = d.i;
                    storeField(d, "currentMinimum");
                    					d.currentMinimum = 0;
                    storeField(d, "j");
                    					d.j = 0;
                    storeField(d, "checkedRows");
                    					d.checkedRows = new boolean[d.matrix.length];
                    storeField(d, "checkedColumns");
                    					d.checkedColumns = new boolean[d.matrix.length];
                    storeArray(d.checkedRows, d.i);
                    					d.checkedRows[d.i] = true;
                    storeField(d, "lastRow");
                    					d.lastRow = d.i;
                    storeField(d, "br");
                    					d.br = true;
                    break;
                }
                case 7: { // Searching for new row partner
                    break;
                }
                case 8: { // Initialization of loop variable
                    startSection();
                    storeField(d, "j");
                    					d.j = 0;
                    storeField(d, "globalMinimumI");
                    					d.globalMinimumI = -1;
                    storeField(d, "globalMinimumJ");
                    					d.globalMinimumJ = -1;
                    break;
                }
                case 9: { // Searching for minimum
                    break;
                }
                case 10: { // Testing next element for minimum
                    break;
                }
                case 11: { // Testing next element for minimum (окончание)
                    break;
                }
                case 12: { // Updating minimum
                    startSection();
                    storeArray(d.localMinimum, d.j);
                    								d.localMinimum[d.j] = d.lastRow; 
                    break;
                }
                case 13: { // Testing current element for freedom
                    break;
                }
                case 14: { // Testing current element for freedom (окончание)
                    break;
                }
                case 15: { // Testing for minimum
                    break;
                }
                case 16: { // Testing for minimum (окончание)
                    break;
                }
                case 17: { // Assignment
                    startSection();
                    storeField(d, "globalMinimumJ");
                    									d.globalMinimumJ = d.j;
                    storeField(d, "globalMinimumI");
                    									d.globalMinimumI = d.localMinimum[d.j];
                    break;
                }
                case 18: { // Increasing counter
                    startSection();
                    storeField(d, "j");
                    						d.j = d.j + 1;
                    break;
                }
                case 19: { // Initializing variables for new loops
                    startSection();
                    storeField(d, "di");
                    							d.di = 0;
                    storeField(d, "dj");
                    							d.dj = 0;
                    storeField(d, "diff");
                    							d.diff = d.matrix[d.globalMinimumI][d.globalMinimumJ];
                    break;
                }
                case 20: { // Loor for i
                    break;
                }
                case 21: { // Loor for j
                    break;
                }
                case 22: { // Checking cell for beeing checked
                    break;
                }
                case 23: { // Checking cell for beeing checked (окончание)
                    break;
                }
                case 24: { // Decreasing element
                    startSection();
                    storeArray(d.matrix[d.di], d.dj);
                    									d.matrix[d.di][d.dj] = d.matrix[d.di][d.dj] + d.diff;
                    break;
                }
                case 25: { // Checking cell for beeing checked
                    break;
                }
                case 26: { // Checking cell for beeing checked (окончание)
                    break;
                }
                case 27: { // Decreasing element
                    startSection();
                    storeArray(d.matrix[d.di], d.dj);
                    									d.matrix[d.di][d.dj] = d.matrix[d.di][d.dj] - d.diff;
                    break;
                }
                case 28: { // Increasing dj
                    startSection();
                    storeField(d, "dj");
                    									d.dj = d.dj + 1;
                    break;
                }
                case 29: { // Changing deltaRows
                    break;
                }
                case 30: { // Changing deltaRows (окончание)
                    break;
                }
                case 31: { // Change one element
                    startSection();
                    storeArray(d.rowChange, d.di);
                    										d.rowChange[d.di] = d.rowChange[d.di] + d.diff;
                    break;
                }
                case 32: { // Changing deltaColumns
                    break;
                }
                case 33: { // Changing deltaColumns (окончание)
                    break;
                }
                case 34: { // Change one element
                    startSection();
                    storeArray(d.columnChange, d.di);
                    										d.columnChange[d.di] = d.columnChange[d.di] - d.diff;
                    break;
                }
                case 35: { // Increasing di
                    startSection();
                    storeField(d, "di");
                    							d.di = d.di + 1;
                    storeField(d, "dj");
                    							d.dj = 0;		
                    break;
                }
                case 36: { // Initializing variables for new loops
                    startSection();
                    storeArray(d.checkedColumns, d.globalMinimumJ);
                    							d.checkedColumns[d.globalMinimumJ] = true;
                    break;
                }
                case 37: { // if
                    break;
                }
                case 38: { // if (окончание)
                    break;
                }
                case 39: { // step
                    startSection();
                    storeArray(d.checkedRows, d.cPartner[d.globalMinimumJ]);
                    										d.checkedRows[d.cPartner[d.globalMinimumJ]] = true;
                    break;
                }
                case 40: { // Checking is it possible to come out of loop
                    break;
                }
                case 41: { // Checking is it possible to come out of loop (окончание)
                    break;
                }
                case 42: { // Going out of loop
                    startSection();
                    storeField(d, "br");
                    							d.br = false;
                    break;
                }
                case 43: { // Not going out of loop
                    startSection();
                    storeField(d, "lastRow");
                    							d.lastRow = d.cPartner[d.globalMinimumJ];
                    break;
                }
                case 44: { // Drawing all objects
                    startSection();
                    storeField(d, "i");
                    					d.i = d.i;
                    break;
                }
                case 45: { // Recalculating partners for some rows
                    break;
                }
                case 46: { // Recalculate partner for last row
                    startSection();
                    storeField(d, "swp");
                    					d.swp = d.partner[d.globalMinimumI];
                    storeArray(d.partner, d.globalMinimumI);
                    					d.partner[d.globalMinimumI] = d.globalMinimumJ;
                    storeArray(d.cPartner, d.globalMinimumJ);
                    					d.cPartner[d.globalMinimumJ] = d.globalMinimumI;
                    storeArray(d.checkedRows, d.globalMinimumI);
                    					d.checkedRows[d.globalMinimumI] =false;
                    storeArray(d.checkedColumns, d.globalMinimumJ);
                    					d.checkedColumns[d.globalMinimumJ] = false;
                    storeArray(d.used, d.globalMinimumJ);
                    					d.used[d.globalMinimumJ] = true;
                    storeField(d, "globalMinimumJ");
                    					d.globalMinimumJ = d.swp;
                    break;
                }
                case 47: { // Initialize di
                    startSection();
                    storeField(d, "di");
                    					d.di = 0;
                    break;
                }
                case 48: { // Searching for partner for globalMinimumJ
                    break;
                }
                case 49: { // Check
                    break;
                }
                case 50: { // Check (окончание)
                    break;
                }
                case 51: { // reCalc
                    startSection();
                    storeField(d, "globalMinimumI");
                    							d.globalMinimumI = d.di;
                    break;
                }
                case 52: { // aaaaaaa
                    startSection();
                    storeField(d, "di");
                    						d.di = d.di + 1;
                    break;
                }
                case 53: { // Last recalculation
                    startSection();
                    storeArray(d.partner, d.globalMinimumI);
                    				d.partner[d.globalMinimumI] = d.globalMinimumJ;
                    storeArray(d.cPartner, d.globalMinimumJ);
                    				d.cPartner[d.globalMinimumJ] = d.globalMinimumI;
                    storeArray(d.used, d.globalMinimumJ);
                    				d.used[d.globalMinimumJ] = true;	
                    storeField(d, "checkedColumns");
                    				d.checkedColumns = new boolean[d.matrix.length];
                    storeField(d, "checkedRows");
                    				d.checkedRows = new boolean[d.matrix.length];
                    break;
                }
            }
        }

        /**
          * Сделать один шаг автомата назад.
          */
        protected void doStepBackward(int level) {
            // Обращение действия в текущем состоянии
            switch (state) {
                case 1: { // Start of cycle
                    restoreSection();
                    break;
                }
                case 2: { // Cycle
                    break;
                }
                case 3: { // Initialization of local minimum
                    restoreSection();
                    break;
                }
                case 4: { // Cycle
                    break;
                }
                case 5: { // Initialization of one element
                    restoreSection();
                    break;
                }
                case 6: { // Moving to next row
                    restoreSection();
                    break;
                }
                case 7: { // Searching for new row partner
                    break;
                }
                case 8: { // Initialization of loop variable
                    restoreSection();
                    break;
                }
                case 9: { // Searching for minimum
                    break;
                }
                case 10: { // Testing next element for minimum
                    break;
                }
                case 11: { // Testing next element for minimum (окончание)
                    break;
                }
                case 12: { // Updating minimum
                    restoreSection();
                    break;
                }
                case 13: { // Testing current element for freedom
                    break;
                }
                case 14: { // Testing current element for freedom (окончание)
                    break;
                }
                case 15: { // Testing for minimum
                    break;
                }
                case 16: { // Testing for minimum (окончание)
                    break;
                }
                case 17: { // Assignment
                    restoreSection();
                    break;
                }
                case 18: { // Increasing counter
                    restoreSection();
                    break;
                }
                case 19: { // Initializing variables for new loops
                    restoreSection();
                    break;
                }
                case 20: { // Loor for i
                    break;
                }
                case 21: { // Loor for j
                    break;
                }
                case 22: { // Checking cell for beeing checked
                    break;
                }
                case 23: { // Checking cell for beeing checked (окончание)
                    break;
                }
                case 24: { // Decreasing element
                    restoreSection();
                    break;
                }
                case 25: { // Checking cell for beeing checked
                    break;
                }
                case 26: { // Checking cell for beeing checked (окончание)
                    break;
                }
                case 27: { // Decreasing element
                    restoreSection();
                    break;
                }
                case 28: { // Increasing dj
                    restoreSection();
                    break;
                }
                case 29: { // Changing deltaRows
                    break;
                }
                case 30: { // Changing deltaRows (окончание)
                    break;
                }
                case 31: { // Change one element
                    restoreSection();
                    break;
                }
                case 32: { // Changing deltaColumns
                    break;
                }
                case 33: { // Changing deltaColumns (окончание)
                    break;
                }
                case 34: { // Change one element
                    restoreSection();
                    break;
                }
                case 35: { // Increasing di
                    restoreSection();
                    break;
                }
                case 36: { // Initializing variables for new loops
                    restoreSection();
                    break;
                }
                case 37: { // if
                    break;
                }
                case 38: { // if (окончание)
                    break;
                }
                case 39: { // step
                    restoreSection();
                    break;
                }
                case 40: { // Checking is it possible to come out of loop
                    break;
                }
                case 41: { // Checking is it possible to come out of loop (окончание)
                    break;
                }
                case 42: { // Going out of loop
                    restoreSection();
                    break;
                }
                case 43: { // Not going out of loop
                    restoreSection();
                    break;
                }
                case 44: { // Drawing all objects
                    restoreSection();
                    break;
                }
                case 45: { // Recalculating partners for some rows
                    break;
                }
                case 46: { // Recalculate partner for last row
                    restoreSection();
                    break;
                }
                case 47: { // Initialize di
                    restoreSection();
                    break;
                }
                case 48: { // Searching for partner for globalMinimumJ
                    break;
                }
                case 49: { // Check
                    break;
                }
                case 50: { // Check (окончание)
                    break;
                }
                case 51: { // reCalc
                    restoreSection();
                    break;
                }
                case 52: { // aaaaaaa
                    restoreSection();
                    break;
                }
                case 53: { // Last recalculation
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Start of cycle
                    state = START_STATE; 
                    break;
                }
                case 2: { // Cycle
                    if (stack.popBoolean()) {
                        state = 53; // Last recalculation
                    } else {
                        state = 1; // Start of cycle
                    }
                    break;
                }
                case 3: { // Initialization of local minimum
                    state = 2; // Cycle
                    break;
                }
                case 4: { // Cycle
                    if (stack.popBoolean()) {
                        state = 5; // Initialization of one element
                    } else {
                        state = 3; // Initialization of local minimum
                    }
                    break;
                }
                case 5: { // Initialization of one element
                    state = 4; // Cycle
                    break;
                }
                case 6: { // Moving to next row
                    state = 4; // Cycle
                    break;
                }
                case 7: { // Searching for new row partner
                    if (stack.popBoolean()) {
                        state = 44; // Drawing all objects
                    } else {
                        state = 6; // Moving to next row
                    }
                    break;
                }
                case 8: { // Initialization of loop variable
                    state = 7; // Searching for new row partner
                    break;
                }
                case 9: { // Searching for minimum
                    if (stack.popBoolean()) {
                        state = 18; // Increasing counter
                    } else {
                        state = 8; // Initialization of loop variable
                    }
                    break;
                }
                case 10: { // Testing next element for minimum
                    state = 9; // Searching for minimum
                    break;
                }
                case 11: { // Testing next element for minimum (окончание)
                    if (stack.popBoolean()) {
                        state = 12; // Updating minimum
                    } else {
                        state = 10; // Testing next element for minimum
                    }
                    break;
                }
                case 12: { // Updating minimum
                    state = 10; // Testing next element for minimum
                    break;
                }
                case 13: { // Testing current element for freedom
                    state = 11; // Testing next element for minimum (окончание)
                    break;
                }
                case 14: { // Testing current element for freedom (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Testing for minimum (окончание)
                    } else {
                        state = 13; // Testing current element for freedom
                    }
                    break;
                }
                case 15: { // Testing for minimum
                    state = 13; // Testing current element for freedom
                    break;
                }
                case 16: { // Testing for minimum (окончание)
                    if (stack.popBoolean()) {
                        state = 17; // Assignment
                    } else {
                        state = 15; // Testing for minimum
                    }
                    break;
                }
                case 17: { // Assignment
                    state = 15; // Testing for minimum
                    break;
                }
                case 18: { // Increasing counter
                    state = 14; // Testing current element for freedom (окончание)
                    break;
                }
                case 19: { // Initializing variables for new loops
                    state = 9; // Searching for minimum
                    break;
                }
                case 20: { // Loor for i
                    if (stack.popBoolean()) {
                        state = 35; // Increasing di
                    } else {
                        state = 19; // Initializing variables for new loops
                    }
                    break;
                }
                case 21: { // Loor for j
                    if (stack.popBoolean()) {
                        state = 28; // Increasing dj
                    } else {
                        state = 20; // Loor for i
                    }
                    break;
                }
                case 22: { // Checking cell for beeing checked
                    state = 21; // Loor for j
                    break;
                }
                case 23: { // Checking cell for beeing checked (окончание)
                    if (stack.popBoolean()) {
                        state = 24; // Decreasing element
                    } else {
                        state = 22; // Checking cell for beeing checked
                    }
                    break;
                }
                case 24: { // Decreasing element
                    state = 22; // Checking cell for beeing checked
                    break;
                }
                case 25: { // Checking cell for beeing checked
                    state = 23; // Checking cell for beeing checked (окончание)
                    break;
                }
                case 26: { // Checking cell for beeing checked (окончание)
                    if (stack.popBoolean()) {
                        state = 27; // Decreasing element
                    } else {
                        state = 25; // Checking cell for beeing checked
                    }
                    break;
                }
                case 27: { // Decreasing element
                    state = 25; // Checking cell for beeing checked
                    break;
                }
                case 28: { // Increasing dj
                    state = 26; // Checking cell for beeing checked (окончание)
                    break;
                }
                case 29: { // Changing deltaRows
                    state = 21; // Loor for j
                    break;
                }
                case 30: { // Changing deltaRows (окончание)
                    if (stack.popBoolean()) {
                        state = 31; // Change one element
                    } else {
                        state = 29; // Changing deltaRows
                    }
                    break;
                }
                case 31: { // Change one element
                    state = 29; // Changing deltaRows
                    break;
                }
                case 32: { // Changing deltaColumns
                    state = 30; // Changing deltaRows (окончание)
                    break;
                }
                case 33: { // Changing deltaColumns (окончание)
                    if (stack.popBoolean()) {
                        state = 34; // Change one element
                    } else {
                        state = 32; // Changing deltaColumns
                    }
                    break;
                }
                case 34: { // Change one element
                    state = 32; // Changing deltaColumns
                    break;
                }
                case 35: { // Increasing di
                    state = 33; // Changing deltaColumns (окончание)
                    break;
                }
                case 36: { // Initializing variables for new loops
                    state = 20; // Loor for i
                    break;
                }
                case 37: { // if
                    state = 36; // Initializing variables for new loops
                    break;
                }
                case 38: { // if (окончание)
                    if (stack.popBoolean()) {
                        state = 39; // step
                    } else {
                        state = 37; // if
                    }
                    break;
                }
                case 39: { // step
                    state = 37; // if
                    break;
                }
                case 40: { // Checking is it possible to come out of loop
                    state = 38; // if (окончание)
                    break;
                }
                case 41: { // Checking is it possible to come out of loop (окончание)
                    if (stack.popBoolean()) {
                        state = 42; // Going out of loop
                    } else {
                        state = 43; // Not going out of loop
                    }
                    break;
                }
                case 42: { // Going out of loop
                    state = 40; // Checking is it possible to come out of loop
                    break;
                }
                case 43: { // Not going out of loop
                    state = 40; // Checking is it possible to come out of loop
                    break;
                }
                case 44: { // Drawing all objects
                    state = 41; // Checking is it possible to come out of loop (окончание)
                    break;
                }
                case 45: { // Recalculating partners for some rows
                    if (stack.popBoolean()) {
                        state = 48; // Searching for partner for globalMinimumJ
                    } else {
                        state = 7; // Searching for new row partner
                    }
                    break;
                }
                case 46: { // Recalculate partner for last row
                    state = 45; // Recalculating partners for some rows
                    break;
                }
                case 47: { // Initialize di
                    state = 46; // Recalculate partner for last row
                    break;
                }
                case 48: { // Searching for partner for globalMinimumJ
                    if (stack.popBoolean()) {
                        state = 52; // aaaaaaa
                    } else {
                        state = 47; // Initialize di
                    }
                    break;
                }
                case 49: { // Check
                    state = 48; // Searching for partner for globalMinimumJ
                    break;
                }
                case 50: { // Check (окончание)
                    if (stack.popBoolean()) {
                        state = 51; // reCalc
                    } else {
                        state = 49; // Check
                    }
                    break;
                }
                case 51: { // reCalc
                    state = 49; // Check
                    break;
                }
                case 52: { // aaaaaaa
                    state = 50; // Check (окончание)
                    break;
                }
                case 53: { // Last recalculation
                    state = 45; // Recalculating partners for some rows
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Cycle
                    break;
                }
            }
        }

        /**
          * Комментарий к текущему состоянию
          */
        public String getComment() {
            String comment = ""; 
            Object[] args = null; 
            // Выбор комментария
            switch (state) {
                case START_STATE: { // Начальное состояние
                    comment = Hungarian.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 6: { // Moving to next row
                    comment = Hungarian.this.getComment("Main.GoOn"); 
                    break;
                }
                case 10: { // Testing next element for minimum
                    if ((!d.checkedColumns[d.j]) && (d.matrix[d.lastRow][d.j] < d.matrix[d.localMinimum[d.j]][d.j])) {
                        comment = Hungarian.this.getComment("Main.TestForMin.true"); 
                    } else {
                        comment = Hungarian.this.getComment("Main.TestForMin.false"); 
                    }
                    args = new Object[]{new Integer(d.j + 1)}; 
                    break;
                }
                case 13: { // Testing current element for freedom
                    if ((!d.checkedColumns[d.j])) {
                        comment = Hungarian.this.getComment("Main.TestColForFree.true"); 
                    } else {
                        comment = Hungarian.this.getComment("Main.TestColForFree.false"); 
                    }
                    break;
                }
                case 17: { // Assignment
                    comment = Hungarian.this.getComment("Main.Assign"); 
                    break;
                }
                case 42: { // Going out of loop
                    comment = Hungarian.this.getComment("Main.GoingOut"); 
                    break;
                }
                case 43: { // Not going out of loop
                    comment = Hungarian.this.getComment("Main.NotGoingOut"); 
                    break;
                }
                case 44: { // Drawing all objects
                    comment = Hungarian.this.getComment("Main.DrawAll"); 
                    break;
                }
                case 46: { // Recalculate partner for last row
                    comment = Hungarian.this.getComment("Main.recalcPartnerForLastRow"); 
                    args = new Object[]{new Integer(d.globalMinimumI + 1)}; 
                    break;
                }
                case 53: { // Last recalculation
                    comment = Hungarian.this.getComment("Main.lastRecalc"); 
                    args = new Object[]{new Integer(d.globalMinimumI + 1)}; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = Hungarian.this.getComment("Main.END_STATE"); 
                    break;
                }
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
                case START_STATE: { // Начальное состояние
                                    d.visualizer.drawMatrix();
                    				d.visualizer.drawArrays();
                    break;
                }
                case 6: { // Moving to next row
                    	           		d.visualizer.drawMatrix();
                    					d.visualizer.drawArrays();
                    break;
                }
                case 10: { // Testing next element for minimum
                    						d.visualizer.drawMatrix();
                    						d.visualizer.drawArrays();
                    break;
                }
                case 13: { // Testing current element for freedom
                    						d.visualizer.drawArrays();
                    break;
                }
                case 17: { // Assignment
                    									d.visualizer.drawMatrix();
                    									d.visualizer.drawArrays();
                    break;
                }
                case 42: { // Going out of loop
                    							d.visualizer.drawMatrix();
                    							d.visualizer.drawArrays();
                    break;
                }
                case 43: { // Not going out of loop
                    							 d.visualizer.drawMatrix();
                    							d.visualizer.drawArrays();
                    break;
                }
                case 44: { // Drawing all objects
                    					d.visualizer.drawMatrix();
                    					d.visualizer.drawArrays();
                    break;
                }
                case 46: { // Recalculate partner for last row
                    					d.visualizer.drawMatrix();
                    					d.visualizer.drawArrays();
                    break;
                }
                case 53: { // Last recalculation
                    				d.visualizer.drawMatrix();
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.drawMatrix();
                    break;
                }
            }
        }
    }
}
