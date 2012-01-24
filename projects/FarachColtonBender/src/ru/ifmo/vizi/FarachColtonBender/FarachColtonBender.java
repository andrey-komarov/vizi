package ru.ifmo.vizi.FarachColtonBender;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class FarachColtonBender extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public FarachColtonBender(Locale locale) {
        super("ru.ifmo.vizi.FarachColtonBender.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public FarachColtonBenderVisualizer visualizer = null;

        /**
          * Основной массив.
          */
        public int[] array = new int[]{3, 8, 10, 4, 23};

        /**
          * Количество обработанных вершин в построении декартова дерева.
          */
        public int verticesPassed = 0;

        /**
          * Стек.
          */
        public int[] stack = new int[5];

        /**
          * Ещё стек.
          */
        public int[] stack2 = new int[5];

        /**
          * Размер стека. Внезапно.
          */
        public int stackSize = 0;

        /**
          * Счётчик цикла.
          */
        public int i = 0;

        /**
          * Левый сын в декартовом дереве.
          */
        public int[] leftSon = new int[5];

        /**
          * Правый сын в декартовом дереве.
          */
        public int[] rightSon = new int[5];

        /**
          * oldStackSize.
          */
        public int oldStackSize = -1;

        /**
          * .
          */
        public int tmp = 0;

        /**
          * Номер вершины в DFS.
          */
        public int[] index = new int[11];

        /**
          * Глубина в DFS.
          */
        public int[] depth = new int[11];

        /**
          * Корень дерева.
          */
        public int root = -1;

        /**
          * Позиция в массиве обхода DFS.
          */
        public int pos = -1;

        /**
          * Сыновей пройдено.
          */
        public int[] passed = null;

        /**
          * Первое вхождение.
          */
        public int[] first = null;

        /**
          * Построено дерево, сделан обход.
          */
        public boolean stage1 = false;

        /**
          * Размер куска.
          */
        public int pieceSize = 3;

        /**
          * Максиммумы на кусочках.
          */
        public int[] maximums = null;

        /**
          * Переменная цикла — 2.
          */
        public int j = -1;

        /**
          * Собственно, таблица.
          */
        public int[][] table = null;

        /**
          * ±1-таблица.
          */
        public int[][] table2 = null;

        public String toString() {
            		return "";
        }
    }

    /**
      * Ищет максимум на отрезке в массиве.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 67;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                67, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Start of cycle", 
                    "Cycle over elements to build cartesian tree", 
                    "Backup stack size", 
                    "Drop all elements above a[i]", 
                    "decrementStackSize", 
                    "check for new right son", 
                    "check for new right son (окончание)", 
                    "right son assignment", 
                    "check for new left son", 
                    "check for new left son (окончание)", 
                    "left son assignment", 
                    "push element to stack", 
                    "Cycle step", 
                    "", 
                    "DFSCommnet", 
                    "ololo2", 
                    "Traversal over tree", 
                    "Try to go left", 
                    "Try to go left (окончание)", 
                    "go left", 
                    "advancePointer", 
                    "advancePointer (окончание)", 
                    "advancePointer1", 
                    "Try to go right", 
                    "Try to go right (окончание)", 
                    "GoGoRight", 
                    "check if we are not root", 
                    "check if we are not root (окончание)", 
                    "processing of vertex finished", 
                    "the end of DFS", 
                    "cleanup after traversal", 
                    "ololo3", 
                    "search for first occurence of number in index", 
                    "", 
                    " (окончание)", 
                    "First occurence of index[i] found", 
                    "index[i] already was used", 
                    "increment loop variable", 
                    "splitting depth into pieces", 
                    "ololo4", 
                    "build array for sparce table", 
                    "initialization of j", 
                    "find maximum in piece", 
                    "ололошеньки", 
                    "ололошеньки (окончание)", 
                    "new maximum found", 
                    "increment j", 
                    "", 
                    "maximums found", 
                    "row in sparse table", 
                    "init loop variable", 
                    "column in sparse table", 
                    "compute next variable in sparse table", 
                    "ololo5", 
                    "ololo5 (окончание)", 
                    "ololo6", 
                    "ololo7", 
                    "ololo8", 
                    "ololo9", 
                    "ololo10", 
                    "ololo11", 
                    "ololo12", 
                    "ololo13", 
                    "ololo14", 
                    "ololo15", 
                    "ololo15", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Start of cycle 
                    0, // Cycle over elements to build cartesian tree 
                    -1, // Backup stack size 
                    0, // Drop all elements above a[i] 
                    0, // decrementStackSize 
                    -1, // check for new right son 
                    -1, // check for new right son (окончание) 
                    -1, // right son assignment 
                    -1, // check for new left son 
                    -1, // check for new left son (окончание) 
                    -1, // left son assignment 
                    0, // push element to stack 
                    -1, // Cycle step 
                    0, //  
                    0, // DFSCommnet 
                    0, // ololo2 
                    0, // Traversal over tree 
                    -1, // Try to go left 
                    -1, // Try to go left (окончание) 
                    0, // go left 
                    -1, // advancePointer 
                    -1, // advancePointer (окончание) 
                    -1, // advancePointer1 
                    -1, // Try to go right 
                    -1, // Try to go right (окончание) 
                    0, // GoGoRight 
                    -1, // check if we are not root 
                    -1, // check if we are not root (окончание) 
                    0, // processing of vertex finished 
                    -1, // the end of DFS 
                    0, // cleanup after traversal 
                    -1, // ololo3 
                    -1, // search for first occurence of number in index 
                    -1, //  
                    -1, //  (окончание) 
                    0, // First occurence of index[i] found 
                    -1, // index[i] already was used 
                    -1, // increment loop variable 
                    0, // splitting depth into pieces 
                    0, // ololo4 
                    0, // build array for sparce table 
                    -1, // initialization of j 
                    -1, // find maximum in piece 
                    -1, // ололошеньки 
                    -1, // ололошеньки (окончание) 
                    0, // new maximum found 
                    0, // increment j 
                    -1, //  
                    0, // maximums found 
                    -1, // row in sparse table 
                    -1, // init loop variable 
                    -1, // column in sparse table 
                    0, // compute next variable in sparse table 
                    -1, // ololo5 
                    -1, // ololo5 (окончание) 
                    0, // ololo6 
                    0, // ololo7 
                    -1, // ololo8 
                    -1, // ololo9 
                    0, // ololo10 
                    -1, // ololo11 
                    -1, // ololo12 
                    -1, // ololo13 
                    0, // ololo14 
                    -1, // ololo15 
                    0, // ololo15 
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
                    state = 2; // Cycle over elements to build cartesian tree
                    break;
                }
                case 2: { // Cycle over elements to build cartesian tree
                    if (d.i < d.array.length) {
                        state = 3; // Backup stack size
                    } else {
                        state = 14; 
                    }
                    break;
                }
                case 3: { // Backup stack size
                    stack.pushBoolean(false); 
                    state = 4; // Drop all elements above a[i]
                    break;
                }
                case 4: { // Drop all elements above a[i]
                    if (d.stackSize > 0 && d.array[d.i] > d.array[d.stack[d.stackSize - 1]]) {
                        state = 5; // decrementStackSize
                    } else {
                        state = 6; // check for new right son
                    }
                    break;
                }
                case 5: { // decrementStackSize
                    stack.pushBoolean(true); 
                    state = 4; // Drop all elements above a[i]
                    break;
                }
                case 6: { // check for new right son
                    if (d.stackSize != 0) {
                        state = 8; // right son assignment
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // check for new right son (окончание)
                    }
                    break;
                }
                case 7: { // check for new right son (окончание)
                    state = 9; // check for new left son
                    break;
                }
                case 8: { // right son assignment
                    stack.pushBoolean(true); 
                    state = 7; // check for new right son (окончание)
                    break;
                }
                case 9: { // check for new left son
                    if (d.oldStackSize != d.stackSize) {
                        state = 11; // left son assignment
                    } else {
                        stack.pushBoolean(false); 
                        state = 10; // check for new left son (окончание)
                    }
                    break;
                }
                case 10: { // check for new left son (окончание)
                    state = 12; // push element to stack
                    break;
                }
                case 11: { // left son assignment
                    stack.pushBoolean(true); 
                    state = 10; // check for new left son (окончание)
                    break;
                }
                case 12: { // push element to stack
                    state = 13; // Cycle step
                    break;
                }
                case 13: { // Cycle step
                    stack.pushBoolean(true); 
                    state = 2; // Cycle over elements to build cartesian tree
                    break;
                }
                case 14: { 
                    state = 15; // DFSCommnet
                    break;
                }
                case 15: { // DFSCommnet
                    state = 16; // ololo2
                    break;
                }
                case 16: { // ololo2
                    stack.pushBoolean(false); 
                    state = 17; // Traversal over tree
                    break;
                }
                case 17: { // Traversal over tree
                    if (d.stackSize > 0) {
                        state = 18; // Try to go left
                    } else {
                        state = 31; // cleanup after traversal
                    }
                    break;
                }
                case 18: { // Try to go left
                    if (d.passed[d.stack[d.stackSize - 1]] == 0 && d.leftSon[d.stack[d.stackSize - 1]] != -1) {
                        state = 20; // go left
                    } else {
                        state = 21; // advancePointer
                    }
                    break;
                }
                case 19: { // Try to go left (окончание)
                    stack.pushBoolean(true); 
                    state = 17; // Traversal over tree
                    break;
                }
                case 20: { // go left
                    stack.pushBoolean(true); 
                    state = 19; // Try to go left (окончание)
                    break;
                }
                case 21: { // advancePointer
                    if (d.leftSon[d.stack[d.stackSize - 1]] == -1) {
                        state = 23; // advancePointer1
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // advancePointer (окончание)
                    }
                    break;
                }
                case 22: { // advancePointer (окончание)
                    state = 24; // Try to go right
                    break;
                }
                case 23: { // advancePointer1
                    stack.pushBoolean(true); 
                    state = 22; // advancePointer (окончание)
                    break;
                }
                case 24: { // Try to go right
                    if (d.passed[d.stack[d.stackSize - 1]] == 1 && d.rightSon[d.stack[d.stackSize - 1]] != -1) {
                        state = 26; // GoGoRight
                    } else {
                        state = 27; // check if we are not root
                    }
                    break;
                }
                case 25: { // Try to go right (окончание)
                    stack.pushBoolean(false); 
                    state = 19; // Try to go left (окончание)
                    break;
                }
                case 26: { // GoGoRight
                    stack.pushBoolean(true); 
                    state = 25; // Try to go right (окончание)
                    break;
                }
                case 27: { // check if we are not root
                    if (d.stackSize > 1) {
                        state = 29; // processing of vertex finished
                    } else {
                        state = 30; // the end of DFS
                    }
                    break;
                }
                case 28: { // check if we are not root (окончание)
                    stack.pushBoolean(false); 
                    state = 25; // Try to go right (окончание)
                    break;
                }
                case 29: { // processing of vertex finished
                    stack.pushBoolean(true); 
                    state = 28; // check if we are not root (окончание)
                    break;
                }
                case 30: { // the end of DFS
                    stack.pushBoolean(false); 
                    state = 28; // check if we are not root (окончание)
                    break;
                }
                case 31: { // cleanup after traversal
                    state = 32; // ololo3
                    break;
                }
                case 32: { // ololo3
                    stack.pushBoolean(false); 
                    state = 33; // search for first occurence of number in index
                    break;
                }
                case 33: { // search for first occurence of number in index
                    if (d.i < 2 * d.array.length - 1) {
                        state = 34; 
                    } else {
                        state = 39; // splitting depth into pieces
                    }
                    break;
                }
                case 34: { 
                    if (d.first[d.index[d.i]] == -1) {
                        state = 36; // First occurence of index[i] found
                    } else {
                        state = 37; // index[i] already was used
                    }
                    break;
                }
                case 35: { //  (окончание)
                    state = 38; // increment loop variable
                    break;
                }
                case 36: { // First occurence of index[i] found
                    stack.pushBoolean(true); 
                    state = 35; //  (окончание)
                    break;
                }
                case 37: { // index[i] already was used
                    stack.pushBoolean(false); 
                    state = 35; //  (окончание)
                    break;
                }
                case 38: { // increment loop variable
                    stack.pushBoolean(true); 
                    state = 33; // search for first occurence of number in index
                    break;
                }
                case 39: { // splitting depth into pieces
                    state = 40; // ololo4
                    break;
                }
                case 40: { // ololo4
                    stack.pushBoolean(false); 
                    state = 41; // build array for sparce table
                    break;
                }
                case 41: { // build array for sparce table
                    if (d.i < d.maximums.length) {
                        state = 42; // initialization of j
                    } else {
                        state = 49; // maximums found
                    }
                    break;
                }
                case 42: { // initialization of j
                    stack.pushBoolean(false); 
                    state = 43; // find maximum in piece
                    break;
                }
                case 43: { // find maximum in piece
                    if (d.j < d.pieceSize && d.i * d.pieceSize + d.j < d.depth.length) {
                        state = 44; // ололошеньки
                    } else {
                        state = 48; 
                    }
                    break;
                }
                case 44: { // ололошеньки
                    if (d.maximums[d.i] < d.depth[d.i * d.pieceSize + d.j]) {
                        state = 46; // new maximum found
                    } else {
                        stack.pushBoolean(false); 
                        state = 45; // ололошеньки (окончание)
                    }
                    break;
                }
                case 45: { // ололошеньки (окончание)
                    state = 47; // increment j
                    break;
                }
                case 46: { // new maximum found
                    stack.pushBoolean(true); 
                    state = 45; // ололошеньки (окончание)
                    break;
                }
                case 47: { // increment j
                    stack.pushBoolean(true); 
                    state = 43; // find maximum in piece
                    break;
                }
                case 48: { 
                    stack.pushBoolean(true); 
                    state = 41; // build array for sparce table
                    break;
                }
                case 49: { // maximums found
                    stack.pushBoolean(false); 
                    state = 50; // row in sparse table
                    break;
                }
                case 50: { // row in sparse table
                    if ((1 << d.i) <= d.maximums.length) {
                        state = 51; // init loop variable
                    } else {
                        state = 60; // ololo10
                    }
                    break;
                }
                case 51: { // init loop variable
                    stack.pushBoolean(false); 
                    state = 52; // column in sparse table
                    break;
                }
                case 52: { // column in sparse table
                    if ((d.j + (1 << d.i)) <= d.maximums.length) {
                        state = 53; // compute next variable in sparse table
                    } else {
                        state = 59; // ololo9
                    }
                    break;
                }
                case 53: { // compute next variable in sparse table
                    state = 54; // ololo5
                    break;
                }
                case 54: { // ololo5
                    if (d.table[d.i - 1][d.j] < d.table[d.i - 1][d.j + (1 << (d.i - 1))]) {
                        state = 56; // ololo6
                    } else {
                        state = 57; // ololo7
                    }
                    break;
                }
                case 55: { // ololo5 (окончание)
                    state = 58; // ololo8
                    break;
                }
                case 56: { // ololo6
                    stack.pushBoolean(true); 
                    state = 55; // ololo5 (окончание)
                    break;
                }
                case 57: { // ololo7
                    stack.pushBoolean(false); 
                    state = 55; // ololo5 (окончание)
                    break;
                }
                case 58: { // ololo8
                    stack.pushBoolean(true); 
                    state = 52; // column in sparse table
                    break;
                }
                case 59: { // ololo9
                    stack.pushBoolean(true); 
                    state = 50; // row in sparse table
                    break;
                }
                case 60: { // ololo10
                    stack.pushBoolean(false); 
                    state = 61; // ololo11
                    break;
                }
                case 61: { // ololo11
                    if (d.i < d.maximums.length) {
                        state = 62; // ololo12
                    } else {
                        state = 66; // ololo15
                    }
                    break;
                }
                case 62: { // ololo12
                    stack.pushBoolean(false); 
                    state = 63; // ololo13
                    break;
                }
                case 63: { // ololo13
                    if (d.j < d.pieceSize - 1 && d.i * d.pieceSize + d.j < d.depth.length) {
                        state = 64; // ololo14
                    } else {
                        state = 65; // ololo15
                    }
                    break;
                }
                case 64: { // ololo14
                    stack.pushBoolean(true); 
                    state = 63; // ololo13
                    break;
                }
                case 65: { // ololo15
                    stack.pushBoolean(true); 
                    state = 61; // ololo11
                    break;
                }
                case 66: { // ololo15
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Start of cycle
                    startSection();
                    storeField(d, "i");
                    		d.i = 0;
                    storeField(d, "stack");
                    		d.stack = new int[d.array.length];
                    storeField(d, "stackSize");
                    		d.stackSize = 0;
                    break;
                }
                case 2: { // Cycle over elements to build cartesian tree
                    break;
                }
                case 3: { // Backup stack size
                    startSection();
                    storeField(d, "oldStackSize");
                    			d.oldStackSize = d.stackSize;
                    break;
                }
                case 4: { // Drop all elements above a[i]
                    break;
                }
                case 5: { // decrementStackSize
                    startSection();
                    storeField(d, "stackSize");
                    				d.stackSize = d.stackSize - 1;
                    break;
                }
                case 6: { // check for new right son
                    break;
                }
                case 7: { // check for new right son (окончание)
                    break;
                }
                case 8: { // right son assignment
                    startSection();
                    storeArray(d.rightSon, d.stack[d.stackSize - 1]);
                    				d.rightSon[d.stack[d.stackSize - 1]] = d.i;
                    break;
                }
                case 9: { // check for new left son
                    break;
                }
                case 10: { // check for new left son (окончание)
                    break;
                }
                case 11: { // left son assignment
                    startSection();
                    storeArray(d.leftSon, d.i);
                    				d.leftSon[d.i] = d.stack[d.stackSize];
                    break;
                }
                case 12: { // push element to stack
                    startSection();
                    storeArray(d.stack, d.stackSize);
                    			d.stack[d.stackSize] = d.i;
                    storeField(d, "stackSize");
                    			d.stackSize = d.stackSize + 1;
                    break;
                }
                case 13: { // Cycle step
                    startSection();
                    storeField(d, "i");
                    			d.i = d.i + 1;
                    break;
                }
                case 14: { 
                    break;
                }
                case 15: { // DFSCommnet
                    startSection();
                    storeField(d, "stackSize");
                    		d.stackSize = 1;
                    storeField(d, "root");
                    		d.root = d.stack[0];
                    storeField(d, "stack");
                    		d.stack = new int[2 * d.array.length + 1];
                    storeArray(d.stack, 0);
                    		d.stack[0] = d.root;
                    storeArray(d.index, 0);
                    		d.index[0] = d.root;
                    storeArray(d.depth, 0);
                    		d.depth[0] = 1;
                    storeField(d, "pos");
                    		d.pos = 1;
                    break;
                }
                case 16: { // ololo2
                    startSection();
                    break;
                }
                case 17: { // Traversal over tree
                    break;
                }
                case 18: { // Try to go left
                    break;
                }
                case 19: { // Try to go left (окончание)
                    break;
                }
                case 20: { // go left
                    startSection();
                    storeArray(d.stack, d.stackSize);
                    						d.stack[d.stackSize] = d.leftSon[d.stack[d.stackSize - 1]];
                    storeArray(d.passed, d.stack[d.stackSize - 1]);
                    						d.passed[d.stack[d.stackSize - 1]] = d.passed[d.stack[d.stackSize - 1]] + 1;
                    storeArray(d.depth, d.pos);
                    						d.depth[d.pos] = d.depth[d.pos - 1] + 1;
                    storeArray(d.index, d.pos);
                    						d.index[d.pos] = d.leftSon[d.stack[d.stackSize - 1]];
                    storeField(d, "pos");
                    						d.pos = d.pos + 1;
                    storeField(d, "stackSize");
                    						d.stackSize = d.stackSize + 1;
                    break;
                }
                case 21: { // advancePointer
                    break;
                }
                case 22: { // advancePointer (окончание)
                    break;
                }
                case 23: { // advancePointer1
                    startSection();
                    storeArray(d.passed, d.stack[d.stackSize - 1]);
                    								d.passed[d.stack[d.stackSize - 1]] = d.passed[d.stack[d.stackSize - 1]] + 1;
                    break;
                }
                case 24: { // Try to go right
                    break;
                }
                case 25: { // Try to go right (окончание)
                    break;
                }
                case 26: { // GoGoRight
                    startSection();
                    storeArray(d.stack, d.stackSize);
                    								d.stack[d.stackSize] = d.rightSon[d.stack[d.stackSize - 1]];
                    storeArray(d.passed, d.stack[d.stackSize - 1]);
                    								d.passed[d.stack[d.stackSize - 1]] = d.passed[d.stack[d.stackSize - 1]] + 1;
                    storeArray(d.depth, d.pos);
                    								d.depth[d.pos] = d.depth[d.pos - 1] + 1;
                    storeArray(d.index, d.pos);
                    								d.index[d.pos] = d.rightSon[d.stack[d.stackSize - 1]];
                    storeField(d, "pos");
                    								d.pos = d.pos + 1;
                    storeField(d, "stackSize");
                    								d.stackSize = d.stackSize + 1;
                    break;
                }
                case 27: { // check if we are not root
                    break;
                }
                case 28: { // check if we are not root (окончание)
                    break;
                }
                case 29: { // processing of vertex finished
                    startSection();
                    storeArray(d.index, d.pos);
                    										d.index[d.pos] = d.stack[d.stackSize - 2];
                    storeArray(d.depth, d.pos);
                    										d.depth[d.pos] = d.depth[d.pos - 1] - 1;
                    storeField(d, "pos");
                    										d.pos = d.pos + 1;
                    storeField(d, "stackSize");
                    										d.stackSize = d.stackSize - 1;
                    break;
                }
                case 30: { // the end of DFS
                    startSection();
                    storeField(d, "stackSize");
                    										d.stackSize = d.stackSize - 1;
                    break;
                }
                case 31: { // cleanup after traversal
                    startSection();
                    storeField(d, "stage1");
                    			d.stage1 = true;
                    break;
                }
                case 32: { // ololo3
                    startSection();
                    storeField(d, "i");
                    			d.i = 0;
                    break;
                }
                case 33: { // search for first occurence of number in index
                    break;
                }
                case 34: { 
                    break;
                }
                case 35: { //  (окончание)
                    break;
                }
                case 36: { // First occurence of index[i] found
                    startSection();
                    storeArray(d.first, d.index[d.i]);
                    						d.first[d.index[d.i]] = d.i;
                    break;
                }
                case 37: { // index[i] already was used
                    startSection();
                    break;
                }
                case 38: { // increment loop variable
                    startSection();
                    storeField(d, "i");
                    				d.i = d.i + 1;
                    break;
                }
                case 39: { // splitting depth into pieces
                    startSection();
                    storeField(d, "i");
                    			d.i = 0;
                    break;
                }
                case 40: { // ololo4
                    startSection();
                    break;
                }
                case 41: { // build array for sparce table
                    break;
                }
                case 42: { // initialization of j
                    startSection();
                    storeField(d, "j");
                    				d.j = 0;
                    break;
                }
                case 43: { // find maximum in piece
                    break;
                }
                case 44: { // ололошеньки
                    break;
                }
                case 45: { // ололошеньки (окончание)
                    break;
                }
                case 46: { // new maximum found
                    startSection();
                    storeArray(d.maximums, d.i);
                    							d.maximums[d.i] = d.depth[d.i * d.pieceSize + d.j];
                    break;
                }
                case 47: { // increment j
                    startSection();
                    storeField(d, "j");
                    					d.j = d.j + 1;
                    break;
                }
                case 48: { 
                    startSection();
                    storeField(d, "i");
                    				d.i = d.i + 1;
                    break;
                }
                case 49: { // maximums found
                    startSection();
                    storeField(d, "i");
                    			d.i = 1;
                    break;
                }
                case 50: { // row in sparse table
                    break;
                }
                case 51: { // init loop variable
                    startSection();
                    storeField(d, "j");
                    				d.j = 0;
                    break;
                }
                case 52: { // column in sparse table
                    break;
                }
                case 53: { // compute next variable in sparse table
                    startSection();
                    break;
                }
                case 54: { // ololo5
                    break;
                }
                case 55: { // ololo5 (окончание)
                    break;
                }
                case 56: { // ololo6
                    startSection();
                    storeArray(d.table[d.i], d.j);
                    							d.table[d.i][d.j] = d.table[d.i - 1][d.j + (1 << (d.i - 1))];
                    break;
                }
                case 57: { // ololo7
                    startSection();
                    storeArray(d.table[d.i], d.j);
                    							d.table[d.i][d.j] = d.table[d.i - 1][d.j];
                    break;
                }
                case 58: { // ololo8
                    startSection();
                    storeField(d, "j");
                    					d.j = d.j + 1;
                    break;
                }
                case 59: { // ololo9
                    startSection();
                    storeField(d, "i");
                    				d.i = d.i + 1;
                    break;
                }
                case 60: { // ololo10
                    startSection();
                    storeField(d, "i");
                    			d.i = 0;
                    break;
                }
                case 61: { // ololo11
                    break;
                }
                case 62: { // ololo12
                    startSection();
                    storeField(d, "j");
                    				d.j = 0;
                    storeArray(d.table2[d.i], 0);
                    				d.table2[d.i][0] = d.depth[d.i * d.pieceSize + d.j];
                    break;
                }
                case 63: { // ololo13
                    break;
                }
                case 64: { // ololo14
                    startSection();
                    storeArray(d.table2[d.i], d.j + 1);
                    					d.table2[d.i][d.j + 1] = d.depth[d.i * d.pieceSize + d.j + 1] - d.depth[d.i * d.pieceSize + d.j];
                    storeField(d, "j");
                    					d.j = d.j + 1;
                    break;
                }
                case 65: { // ololo15
                    startSection();
                    storeField(d, "i");
                    				d.i = d.i + 1;
                    break;
                }
                case 66: { // ololo15
                    startSection();
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
                case 2: { // Cycle over elements to build cartesian tree
                    break;
                }
                case 3: { // Backup stack size
                    restoreSection();
                    break;
                }
                case 4: { // Drop all elements above a[i]
                    break;
                }
                case 5: { // decrementStackSize
                    restoreSection();
                    break;
                }
                case 6: { // check for new right son
                    break;
                }
                case 7: { // check for new right son (окончание)
                    break;
                }
                case 8: { // right son assignment
                    restoreSection();
                    break;
                }
                case 9: { // check for new left son
                    break;
                }
                case 10: { // check for new left son (окончание)
                    break;
                }
                case 11: { // left son assignment
                    restoreSection();
                    break;
                }
                case 12: { // push element to stack
                    restoreSection();
                    break;
                }
                case 13: { // Cycle step
                    restoreSection();
                    break;
                }
                case 14: { 
                    break;
                }
                case 15: { // DFSCommnet
                    restoreSection();
                    break;
                }
                case 16: { // ololo2
                    restoreSection();
                    break;
                }
                case 17: { // Traversal over tree
                    break;
                }
                case 18: { // Try to go left
                    break;
                }
                case 19: { // Try to go left (окончание)
                    break;
                }
                case 20: { // go left
                    restoreSection();
                    break;
                }
                case 21: { // advancePointer
                    break;
                }
                case 22: { // advancePointer (окончание)
                    break;
                }
                case 23: { // advancePointer1
                    restoreSection();
                    break;
                }
                case 24: { // Try to go right
                    break;
                }
                case 25: { // Try to go right (окончание)
                    break;
                }
                case 26: { // GoGoRight
                    restoreSection();
                    break;
                }
                case 27: { // check if we are not root
                    break;
                }
                case 28: { // check if we are not root (окончание)
                    break;
                }
                case 29: { // processing of vertex finished
                    restoreSection();
                    break;
                }
                case 30: { // the end of DFS
                    restoreSection();
                    break;
                }
                case 31: { // cleanup after traversal
                    restoreSection();
                    break;
                }
                case 32: { // ololo3
                    restoreSection();
                    break;
                }
                case 33: { // search for first occurence of number in index
                    break;
                }
                case 34: { 
                    break;
                }
                case 35: { //  (окончание)
                    break;
                }
                case 36: { // First occurence of index[i] found
                    restoreSection();
                    break;
                }
                case 37: { // index[i] already was used
                    restoreSection();
                    break;
                }
                case 38: { // increment loop variable
                    restoreSection();
                    break;
                }
                case 39: { // splitting depth into pieces
                    restoreSection();
                    break;
                }
                case 40: { // ololo4
                    restoreSection();
                    break;
                }
                case 41: { // build array for sparce table
                    break;
                }
                case 42: { // initialization of j
                    restoreSection();
                    break;
                }
                case 43: { // find maximum in piece
                    break;
                }
                case 44: { // ололошеньки
                    break;
                }
                case 45: { // ололошеньки (окончание)
                    break;
                }
                case 46: { // new maximum found
                    restoreSection();
                    break;
                }
                case 47: { // increment j
                    restoreSection();
                    break;
                }
                case 48: { 
                    restoreSection();
                    break;
                }
                case 49: { // maximums found
                    restoreSection();
                    break;
                }
                case 50: { // row in sparse table
                    break;
                }
                case 51: { // init loop variable
                    restoreSection();
                    break;
                }
                case 52: { // column in sparse table
                    break;
                }
                case 53: { // compute next variable in sparse table
                    restoreSection();
                    break;
                }
                case 54: { // ololo5
                    break;
                }
                case 55: { // ololo5 (окончание)
                    break;
                }
                case 56: { // ololo6
                    restoreSection();
                    break;
                }
                case 57: { // ololo7
                    restoreSection();
                    break;
                }
                case 58: { // ololo8
                    restoreSection();
                    break;
                }
                case 59: { // ololo9
                    restoreSection();
                    break;
                }
                case 60: { // ololo10
                    restoreSection();
                    break;
                }
                case 61: { // ololo11
                    break;
                }
                case 62: { // ololo12
                    restoreSection();
                    break;
                }
                case 63: { // ololo13
                    break;
                }
                case 64: { // ololo14
                    restoreSection();
                    break;
                }
                case 65: { // ololo15
                    restoreSection();
                    break;
                }
                case 66: { // ololo15
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
                case 2: { // Cycle over elements to build cartesian tree
                    if (stack.popBoolean()) {
                        state = 13; // Cycle step
                    } else {
                        state = 1; // Start of cycle
                    }
                    break;
                }
                case 3: { // Backup stack size
                    state = 2; // Cycle over elements to build cartesian tree
                    break;
                }
                case 4: { // Drop all elements above a[i]
                    if (stack.popBoolean()) {
                        state = 5; // decrementStackSize
                    } else {
                        state = 3; // Backup stack size
                    }
                    break;
                }
                case 5: { // decrementStackSize
                    state = 4; // Drop all elements above a[i]
                    break;
                }
                case 6: { // check for new right son
                    state = 4; // Drop all elements above a[i]
                    break;
                }
                case 7: { // check for new right son (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // right son assignment
                    } else {
                        state = 6; // check for new right son
                    }
                    break;
                }
                case 8: { // right son assignment
                    state = 6; // check for new right son
                    break;
                }
                case 9: { // check for new left son
                    state = 7; // check for new right son (окончание)
                    break;
                }
                case 10: { // check for new left son (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // left son assignment
                    } else {
                        state = 9; // check for new left son
                    }
                    break;
                }
                case 11: { // left son assignment
                    state = 9; // check for new left son
                    break;
                }
                case 12: { // push element to stack
                    state = 10; // check for new left son (окончание)
                    break;
                }
                case 13: { // Cycle step
                    state = 12; // push element to stack
                    break;
                }
                case 14: { 
                    state = 2; // Cycle over elements to build cartesian tree
                    break;
                }
                case 15: { // DFSCommnet
                    state = 14; 
                    break;
                }
                case 16: { // ololo2
                    state = 15; // DFSCommnet
                    break;
                }
                case 17: { // Traversal over tree
                    if (stack.popBoolean()) {
                        state = 19; // Try to go left (окончание)
                    } else {
                        state = 16; // ololo2
                    }
                    break;
                }
                case 18: { // Try to go left
                    state = 17; // Traversal over tree
                    break;
                }
                case 19: { // Try to go left (окончание)
                    if (stack.popBoolean()) {
                        state = 20; // go left
                    } else {
                        state = 25; // Try to go right (окончание)
                    }
                    break;
                }
                case 20: { // go left
                    state = 18; // Try to go left
                    break;
                }
                case 21: { // advancePointer
                    state = 18; // Try to go left
                    break;
                }
                case 22: { // advancePointer (окончание)
                    if (stack.popBoolean()) {
                        state = 23; // advancePointer1
                    } else {
                        state = 21; // advancePointer
                    }
                    break;
                }
                case 23: { // advancePointer1
                    state = 21; // advancePointer
                    break;
                }
                case 24: { // Try to go right
                    state = 22; // advancePointer (окончание)
                    break;
                }
                case 25: { // Try to go right (окончание)
                    if (stack.popBoolean()) {
                        state = 26; // GoGoRight
                    } else {
                        state = 28; // check if we are not root (окончание)
                    }
                    break;
                }
                case 26: { // GoGoRight
                    state = 24; // Try to go right
                    break;
                }
                case 27: { // check if we are not root
                    state = 24; // Try to go right
                    break;
                }
                case 28: { // check if we are not root (окончание)
                    if (stack.popBoolean()) {
                        state = 29; // processing of vertex finished
                    } else {
                        state = 30; // the end of DFS
                    }
                    break;
                }
                case 29: { // processing of vertex finished
                    state = 27; // check if we are not root
                    break;
                }
                case 30: { // the end of DFS
                    state = 27; // check if we are not root
                    break;
                }
                case 31: { // cleanup after traversal
                    state = 17; // Traversal over tree
                    break;
                }
                case 32: { // ololo3
                    state = 31; // cleanup after traversal
                    break;
                }
                case 33: { // search for first occurence of number in index
                    if (stack.popBoolean()) {
                        state = 38; // increment loop variable
                    } else {
                        state = 32; // ololo3
                    }
                    break;
                }
                case 34: { 
                    state = 33; // search for first occurence of number in index
                    break;
                }
                case 35: { //  (окончание)
                    if (stack.popBoolean()) {
                        state = 36; // First occurence of index[i] found
                    } else {
                        state = 37; // index[i] already was used
                    }
                    break;
                }
                case 36: { // First occurence of index[i] found
                    state = 34; 
                    break;
                }
                case 37: { // index[i] already was used
                    state = 34; 
                    break;
                }
                case 38: { // increment loop variable
                    state = 35; //  (окончание)
                    break;
                }
                case 39: { // splitting depth into pieces
                    state = 33; // search for first occurence of number in index
                    break;
                }
                case 40: { // ololo4
                    state = 39; // splitting depth into pieces
                    break;
                }
                case 41: { // build array for sparce table
                    if (stack.popBoolean()) {
                        state = 48; 
                    } else {
                        state = 40; // ololo4
                    }
                    break;
                }
                case 42: { // initialization of j
                    state = 41; // build array for sparce table
                    break;
                }
                case 43: { // find maximum in piece
                    if (stack.popBoolean()) {
                        state = 47; // increment j
                    } else {
                        state = 42; // initialization of j
                    }
                    break;
                }
                case 44: { // ололошеньки
                    state = 43; // find maximum in piece
                    break;
                }
                case 45: { // ололошеньки (окончание)
                    if (stack.popBoolean()) {
                        state = 46; // new maximum found
                    } else {
                        state = 44; // ололошеньки
                    }
                    break;
                }
                case 46: { // new maximum found
                    state = 44; // ололошеньки
                    break;
                }
                case 47: { // increment j
                    state = 45; // ололошеньки (окончание)
                    break;
                }
                case 48: { 
                    state = 43; // find maximum in piece
                    break;
                }
                case 49: { // maximums found
                    state = 41; // build array for sparce table
                    break;
                }
                case 50: { // row in sparse table
                    if (stack.popBoolean()) {
                        state = 59; // ololo9
                    } else {
                        state = 49; // maximums found
                    }
                    break;
                }
                case 51: { // init loop variable
                    state = 50; // row in sparse table
                    break;
                }
                case 52: { // column in sparse table
                    if (stack.popBoolean()) {
                        state = 58; // ololo8
                    } else {
                        state = 51; // init loop variable
                    }
                    break;
                }
                case 53: { // compute next variable in sparse table
                    state = 52; // column in sparse table
                    break;
                }
                case 54: { // ololo5
                    state = 53; // compute next variable in sparse table
                    break;
                }
                case 55: { // ololo5 (окончание)
                    if (stack.popBoolean()) {
                        state = 56; // ololo6
                    } else {
                        state = 57; // ololo7
                    }
                    break;
                }
                case 56: { // ololo6
                    state = 54; // ololo5
                    break;
                }
                case 57: { // ololo7
                    state = 54; // ololo5
                    break;
                }
                case 58: { // ololo8
                    state = 55; // ololo5 (окончание)
                    break;
                }
                case 59: { // ololo9
                    state = 52; // column in sparse table
                    break;
                }
                case 60: { // ololo10
                    state = 50; // row in sparse table
                    break;
                }
                case 61: { // ololo11
                    if (stack.popBoolean()) {
                        state = 65; // ololo15
                    } else {
                        state = 60; // ololo10
                    }
                    break;
                }
                case 62: { // ololo12
                    state = 61; // ololo11
                    break;
                }
                case 63: { // ololo13
                    if (stack.popBoolean()) {
                        state = 64; // ololo14
                    } else {
                        state = 62; // ololo12
                    }
                    break;
                }
                case 64: { // ololo14
                    state = 63; // ololo13
                    break;
                }
                case 65: { // ololo15
                    state = 63; // ololo13
                    break;
                }
                case 66: { // ololo15
                    state = 61; // ololo11
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 66; // ololo15
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
                    comment = FarachColtonBender.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Start of cycle
                    comment = FarachColtonBender.this.getComment("Main.Begin"); 
                    args = new Object[]{FarachColtonBenderVisualizer.treeAsString(d.array)}; 
                    break;
                }
                case 2: { // Cycle over elements to build cartesian tree
                    if (d.i < d.array.length) {
                        comment = FarachColtonBender.this.getComment("Main.CartesianTreeLoop.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.CartesianTreeLoop.false"); 
                    }
                    args = new Object[]{new Integer((d.i < d.array.length) ? (d.array[d.i]) : 0)}; 
                    break;
                }
                case 4: { // Drop all elements above a[i]
                    if (d.stackSize > 0 && d.array[d.i] > d.array[d.stack[d.stackSize - 1]]) {
                        comment = FarachColtonBender.this.getComment("Main.dropSmallElements.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.dropSmallElements.false"); 
                    }
                    args = new Object[]{new Integer(d.stackSize == 0 ? 0: (d.array[d.stack[d.stackSize - 1]])), new Integer(d.array[d.i])}; 
                    break;
                }
                case 6: { // check for new right son
                    if (d.stackSize != 0) {
                        comment = FarachColtonBender.this.getComment("Main.checkHasRightSon.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.checkHasRightSon.false"); 
                    }
                    break;
                }
                case 9: { // check for new left son
                    if (d.oldStackSize != d.stackSize) {
                        comment = FarachColtonBender.this.getComment("Main.checkHasLeftSon.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.checkHasLeftSon.false"); 
                    }
                    break;
                }
                case 12: { // push element to stack
                    comment = FarachColtonBender.this.getComment("Main.pushElementToStack"); 
                    args = new Object[]{new Integer(d.array[d.i])}; 
                    break;
                }
                case 14: { 
                    comment = FarachColtonBender.this.getComment("Main.TreeBuilt"); 
                    break;
                }
                case 15: { // DFSCommnet
                    comment = FarachColtonBender.this.getComment("Main.DFSComment"); 
                    break;
                }
                case 17: { // Traversal over tree
                    if (d.stackSize > 0) {
                        comment = FarachColtonBender.this.getComment("Main.DFS.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.DFS.false"); 
                    }
                    args = new Object[]{new Integer(1 + (d.stackSize == 0 ? 0 : d.stack[d.stackSize - 1])), new Integer(d.array[d.stackSize == 0 ? 0 : d.stack[d.stackSize - 1]])}; 
                    break;
                }
                case 18: { // Try to go left
                    if (d.passed[d.stack[d.stackSize - 1]] == 0 && d.leftSon[d.stack[d.stackSize - 1]] != -1) {
                        comment = FarachColtonBender.this.getComment("Main.GoLeft.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.GoLeft.false"); 
                    }
                    break;
                }
                case 20: { // go left
                    comment = FarachColtonBender.this.getComment("Main.GoGoLeft"); 
                    break;
                }
                case 26: { // GoGoRight
                    comment = FarachColtonBender.this.getComment("Main.GoGoRight"); 
                    break;
                }
                case 29: { // processing of vertex finished
                    comment = FarachColtonBender.this.getComment("Main.goUp"); 
                    break;
                }
                case 31: { // cleanup after traversal
                    comment = FarachColtonBender.this.getComment("Main.traversalFinished"); 
                    break;
                }
                case 33: { // search for first occurence of number in index
                    if (d.i < 2 * d.array.length - 1) {
                        comment = FarachColtonBender.this.getComment("Main.searchFirstOccurence.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.searchFirstOccurence.false"); 
                    }
                    args = new Object[]{new Integer((d.i == 2 * d.array.length - 1 ? 0 : d.index[d.i]) + 1)}; 
                    break;
                }
                case 36: { // First occurence of index[i] found
                    comment = FarachColtonBender.this.getComment("Main.newElementFound"); 
                    args = new Object[]{new Integer(d.index[d.i] + 1)}; 
                    break;
                }
                case 37: { // index[i] already was used
                    comment = FarachColtonBender.this.getComment("Main.notNewElementFound"); 
                    args = new Object[]{new Integer(d.index[d.i] + 1)}; 
                    break;
                }
                case 39: { // splitting depth into pieces
                    comment = FarachColtonBender.this.getComment("Main.splittingIntoPieces1"); 
                    break;
                }
                case 40: { // ololo4
                    comment = FarachColtonBender.this.getComment("Main.ololo4"); 
                    break;
                }
                case 41: { // build array for sparce table
                    if (d.i < d.maximums.length) {
                        comment = FarachColtonBender.this.getComment("Main.buildArrayForSparseTable.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.buildArrayForSparseTable.false"); 
                    }
                    args = new Object[]{new Integer(d.i + 1)}; 
                    break;
                }
                case 46: { // new maximum found
                    comment = FarachColtonBender.this.getComment("Main.improveMaximum"); 
                    args = new Object[]{new Integer(d.depth[d.i * d.pieceSize + d.j]), new Integer(d.maximums[d.i])}; 
                    break;
                }
                case 47: { // increment j
                    comment = FarachColtonBender.this.getComment("Main.advanceIterator"); 
                    break;
                }
                case 49: { // maximums found
                    comment = FarachColtonBender.this.getComment("Main.maximumsFound"); 
                    break;
                }
                case 53: { // compute next variable in sparse table
                    comment = FarachColtonBender.this.getComment("Main.newVarInSparseTable"); 
                    args = new Object[]{new Integer(d.i), new Integer(d.j + 1), new Integer(d.i - 1), new Integer(d.table[d.i - 1][d.j]), new Integer(d.j + (1 << (d.i - 1))), new Integer(d.table[d.i - 1][d.j + (1 << (d.i - 1))])}; 
                    break;
                }
                case 54: { // ololo5
                    if (d.table[d.i - 1][d.j] < d.table[d.i - 1][d.j + (1 << (d.i - 1))]) {
                        comment = FarachColtonBender.this.getComment("Main.ololo5.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo5.false"); 
                    }
                    break;
                }
                case 56: { // ololo6
                    comment = FarachColtonBender.this.getComment("Main.ololo6"); 
                    break;
                }
                case 57: { // ololo7
                    comment = FarachColtonBender.this.getComment("Main.ololo7"); 
                    break;
                }
                case 60: { // ololo10
                    comment = FarachColtonBender.this.getComment("Main.ololo10"); 
                    break;
                }
                case 64: { // ololo14
                    comment = FarachColtonBender.this.getComment("Main.ololo14"); 
                    break;
                }
                case 66: { // ololo15
                    comment = FarachColtonBender.this.getComment("Main.ololo15"); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = FarachColtonBender.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Integer(d.i)}; 
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
                    		d.visualizer.clear();
                    		d.visualizer.drawMainArray();
                    		d.visualizer.initArrays();
                    break;
                }
                case 5: { // decrementStackSize
                    				d.visualizer.drawCartesianTree(d.i, d.stackSize);
                    break;
                }
                case 12: { // push element to stack
                    			d.visualizer.drawCartesianTree(d.i + 1, d.stackSize);
                    break;
                }
                case 14: { 
                    		d.visualizer.drawCartesianTree(d.array.length, 0);
                    break;
                }
                case 15: { // DFSCommnet
                    		d.visualizer.debug("root", d.root);
                    		d.visualizer.debug("left", d.leftSon);
                    		d.visualizer.debug("right", d.rightSon);
                    		d.visualizer.drawCellsForDFS(0, -1);
                    break;
                }
                case 16: { // ololo2
                    			d.visualizer.drawCellsForDFS(1, d.root);
                    break;
                }
                case 20: { // go left
                    						d.visualizer.drawCellsForDFS(d.pos, d.stack[d.stackSize - 1]);
                    break;
                }
                case 26: { // GoGoRight
                    								d.visualizer.drawCellsForDFS(d.pos, d.stack[d.stackSize - 1]);
                    break;
                }
                case 29: { // processing of vertex finished
                    										d.visualizer.drawCellsForDFS(d.pos, d.stack[d.stackSize - 1]);
                    break;
                }
                case 31: { // cleanup after traversal
                    			d.visualizer.drawCellsForDFS(d.pos, -1);
                    			d.visualizer.drawCartesianTree(0, 0);
                    			d.visualizer.redrawIndex();
                    break;
                }
                case 36: { // First occurence of index[i] found
                    						d.visualizer.redrawIndex();
                    break;
                }
                case 37: { // index[i] already was used
                    break;
                }
                case 38: { // increment loop variable
                    break;
                }
                case 39: { // splitting depth into pieces
                    			d.visualizer.drawMaximums(0);
                    			d.visualizer.drawDepth(-1);
                    break;
                }
                case 40: { // ololo4
                    			d.visualizer.drawDepth(0);			
                    break;
                }
                case 46: { // new maximum found
                    							d.visualizer.drawMaximums(d.i + 1);
                    break;
                }
                case 47: { // increment j
                    					d.visualizer.drawDepth(d.i * d.pieceSize + d.j);
                    break;
                }
                case 49: { // maximums found
                    			d.visualizer.drawDepth(-1);
                    			d.visualizer.drawTable(0, 0, -1, -1, -1, -1);
                    break;
                }
                case 56: { // ololo6
                    							d.visualizer.drawTable(d.i + 1, d.j + 1, d.i - 1, d.j + (1 << (d.i - 1)), d.i - 1, d.j);
                    break;
                }
                case 57: { // ololo7
                    							d.visualizer.drawTable(d.i + 1, d.j + 1, d.i - 1, d.j, d.i - 1, d.j + (1 << (d.i - 1)));
                    break;
                }
                case 60: { // ololo10
                    			d.visualizer.drawTable(d.maximums.length, d.maximums.length, -1, -1, -1, -1);
                    			d.visualizer.drawTable2(0, 0);
                    break;
                }
                case 64: { // ololo14
                    					d.visualizer.drawTable2(d.i + 1, d.j + 1);
                    break;
                }
                case 66: { // ololo15
                    			d.visualizer.drawTable2(d.maximums.length, d.depth.length % d.pieceSize);
                    break;
                }
                case END_STATE: { // Конечное состояние
                    break;
                }
            }
        }
    }
}
