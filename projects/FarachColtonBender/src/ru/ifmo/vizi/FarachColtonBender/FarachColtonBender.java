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

        /**
          * Няшки построены.
          */
        public boolean ready = false;

        /**
          * Левая граница поиска.
          */
        public int left = -1;

        /**
          * Правая граница поиска.
          */
        public int right = -1;

        /**
          * Левая граница поиска — 2.
          */
        public int left2 = -1;

        /**
          * Правая граница поиска ­— 2.
          */
        public int right2 = -1;

        /**
          * Левый кусок.
          */
        public int leftPiece = -1;

        /**
          * Правый кусок.
          */
        public int rightPiece = -1;

        /**
          * Первая часть ответа.
          */
        public int ans1 = -1;

        /**
          * Вторая часть ответа.
          */
        public int ans2 = -1;

        /**
          * Третья часть ответа.
          */
        public int ans3 = -1;

        /**
          * Первый индекс ответа.
          */
        public int index1 = -1;

        /**
          * Второй индекс ответа.
          */
        public int index2 = -1;

        /**
          * Третий часть ответа.
          */
        public int index3 = -1;

        /**
          * Ответ на задачу.
          */
        public int ans = -1;

        /**
          * Логарифм.
          */
        public int log2 = -1;

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
        private final int END_STATE = 114;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                114, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "build all auxillary structures", 
                    "build all auxillary structures (окончание)", 
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
                    "building finished", 
                    "LR", 
                    "LR (окончание)", 
                    "atata2", 
                    "atata3", 
                    "ololo19", 
                    "ololo19 (окончание)", 
                    "ololo20", 
                    "ololo21", 
                    "ololo22", 
                    "ololo22 (окончание)", 
                    "ololo23", 
                    "ololo24", 
                    "ololo25", 
                    "ololo30", 
                    "ololo31", 
                    "ololo32", 
                    "ololo33", 
                    "ololo34", 
                    "ololo34 (окончание)", 
                    "ololo35", 
                    "ololo355", 
                    "ololo36", 
                    "ololo37", 
                    "ololo38", 
                    "ololo38 (окончание)", 
                    "ololo39", 
                    "ololo39", 
                    "ololo40", 
                    "ololo41", 
                    "ololo42", 
                    "ololo42 (окончание)", 
                    "ololo43", 
                    "ololo44", 
                    "", 
                    " (окончание)", 
                    "ololo45", 
                    "445", 
                    "ololo458", 
                    "ololo46", 
                    "ololo46 (окончание)", 
                    "ololo47", 
                    "ololo48", 
                    "ololo48 (окончание)", 
                    "ololo49", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // build all auxillary structures 
                    -1, // build all auxillary structures (окончание) 
                    1, // Start of cycle 
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
                    1, // DFSCommnet 
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
                    1, // cleanup after traversal 
                    -1, // ololo3 
                    -1, // search for first occurence of number in index 
                    -1, //  
                    -1, //  (окончание) 
                    0, // First occurence of index[i] found 
                    -1, // index[i] already was used 
                    -1, // increment loop variable 
                    1, // splitting depth into pieces 
                    0, // ololo4 
                    0, // build array for sparce table 
                    -1, // initialization of j 
                    -1, // find maximum in piece 
                    -1, // ололошеньки 
                    -1, // ололошеньки (окончание) 
                    0, // new maximum found 
                    0, // increment j 
                    -1, //  
                    1, // maximums found 
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
                    1, // ololo10 
                    -1, // ololo11 
                    -1, // ololo12 
                    -1, // ololo13 
                    0, // ololo14 
                    -1, // ololo15 
                    1, // ololo15 
                    1, // building finished 
                    -1, // LR 
                    -1, // LR (окончание) 
                    -1, // atata2 
                    0, // atata3 
                    0, // ololo19 
                    -1, // ololo19 (окончание) 
                    -1, // ololo20 
                    -1, // ololo21 
                    -1, // ololo22 
                    -1, // ololo22 (окончание) 
                    0, // ololo23 
                    -1, // ololo24 
                    -1, // ololo25 
                    -1, // ololo30 
                    -1, // ololo31 
                    -1, // ololo32 
                    -1, // ololo33 
                    -1, // ololo34 
                    -1, // ololo34 (окончание) 
                    -1, // ololo35 
                    -1, // ololo355 
                    0, // ololo36 
                    -1, // ololo37 
                    -1, // ololo38 
                    -1, // ololo38 (окончание) 
                    -1, // ololo39 
                    -1, // ololo39 
                    0, // ololo40 
                    -1, // ololo41 
                    -1, // ololo42 
                    -1, // ololo42 (окончание) 
                    -1, // ololo43 
                    -1, // ololo44 
                    -1, //  
                    -1, //  (окончание) 
                    0, // ololo45 
                    0, // 445 
                    0, // ololo458 
                    -1, // ololo46 
                    -1, // ololo46 (окончание) 
                    -1, // ololo47 
                    -1, // ololo48 
                    -1, // ololo48 (окончание) 
                    -1, // ololo49 
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
                    state = 1; // build all auxillary structures
                    break;
                }
                case 1: { // build all auxillary structures
                    if (!d.ready) {
                        state = 3; // Start of cycle
                    } else {
                        stack.pushBoolean(false); 
                        state = 2; // build all auxillary structures (окончание)
                    }
                    break;
                }
                case 2: { // build all auxillary structures (окончание)
                    state = 69; // building finished
                    break;
                }
                case 3: { // Start of cycle
                    stack.pushBoolean(false); 
                    state = 4; // Cycle over elements to build cartesian tree
                    break;
                }
                case 4: { // Cycle over elements to build cartesian tree
                    if (d.i < d.array.length) {
                        state = 5; // Backup stack size
                    } else {
                        state = 16; 
                    }
                    break;
                }
                case 5: { // Backup stack size
                    stack.pushBoolean(false); 
                    state = 6; // Drop all elements above a[i]
                    break;
                }
                case 6: { // Drop all elements above a[i]
                    if (d.stackSize > 0 && d.array[d.i] > d.array[d.stack[d.stackSize - 1]]) {
                        state = 7; // decrementStackSize
                    } else {
                        state = 8; // check for new right son
                    }
                    break;
                }
                case 7: { // decrementStackSize
                    stack.pushBoolean(true); 
                    state = 6; // Drop all elements above a[i]
                    break;
                }
                case 8: { // check for new right son
                    if (d.stackSize != 0) {
                        state = 10; // right son assignment
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // check for new right son (окончание)
                    }
                    break;
                }
                case 9: { // check for new right son (окончание)
                    state = 11; // check for new left son
                    break;
                }
                case 10: { // right son assignment
                    stack.pushBoolean(true); 
                    state = 9; // check for new right son (окончание)
                    break;
                }
                case 11: { // check for new left son
                    if (d.oldStackSize != d.stackSize) {
                        state = 13; // left son assignment
                    } else {
                        stack.pushBoolean(false); 
                        state = 12; // check for new left son (окончание)
                    }
                    break;
                }
                case 12: { // check for new left son (окончание)
                    state = 14; // push element to stack
                    break;
                }
                case 13: { // left son assignment
                    stack.pushBoolean(true); 
                    state = 12; // check for new left son (окончание)
                    break;
                }
                case 14: { // push element to stack
                    state = 15; // Cycle step
                    break;
                }
                case 15: { // Cycle step
                    stack.pushBoolean(true); 
                    state = 4; // Cycle over elements to build cartesian tree
                    break;
                }
                case 16: { 
                    state = 17; // DFSCommnet
                    break;
                }
                case 17: { // DFSCommnet
                    state = 18; // ololo2
                    break;
                }
                case 18: { // ololo2
                    stack.pushBoolean(false); 
                    state = 19; // Traversal over tree
                    break;
                }
                case 19: { // Traversal over tree
                    if (d.stackSize > 0) {
                        state = 20; // Try to go left
                    } else {
                        state = 33; // cleanup after traversal
                    }
                    break;
                }
                case 20: { // Try to go left
                    if (d.passed[d.stack[d.stackSize - 1]] == 0 && d.leftSon[d.stack[d.stackSize - 1]] != -1) {
                        state = 22; // go left
                    } else {
                        state = 23; // advancePointer
                    }
                    break;
                }
                case 21: { // Try to go left (окончание)
                    stack.pushBoolean(true); 
                    state = 19; // Traversal over tree
                    break;
                }
                case 22: { // go left
                    stack.pushBoolean(true); 
                    state = 21; // Try to go left (окончание)
                    break;
                }
                case 23: { // advancePointer
                    if (d.leftSon[d.stack[d.stackSize - 1]] == -1) {
                        state = 25; // advancePointer1
                    } else {
                        stack.pushBoolean(false); 
                        state = 24; // advancePointer (окончание)
                    }
                    break;
                }
                case 24: { // advancePointer (окончание)
                    state = 26; // Try to go right
                    break;
                }
                case 25: { // advancePointer1
                    stack.pushBoolean(true); 
                    state = 24; // advancePointer (окончание)
                    break;
                }
                case 26: { // Try to go right
                    if (d.passed[d.stack[d.stackSize - 1]] == 1 && d.rightSon[d.stack[d.stackSize - 1]] != -1) {
                        state = 28; // GoGoRight
                    } else {
                        state = 29; // check if we are not root
                    }
                    break;
                }
                case 27: { // Try to go right (окончание)
                    stack.pushBoolean(false); 
                    state = 21; // Try to go left (окончание)
                    break;
                }
                case 28: { // GoGoRight
                    stack.pushBoolean(true); 
                    state = 27; // Try to go right (окончание)
                    break;
                }
                case 29: { // check if we are not root
                    if (d.stackSize > 1) {
                        state = 31; // processing of vertex finished
                    } else {
                        state = 32; // the end of DFS
                    }
                    break;
                }
                case 30: { // check if we are not root (окончание)
                    stack.pushBoolean(false); 
                    state = 27; // Try to go right (окончание)
                    break;
                }
                case 31: { // processing of vertex finished
                    stack.pushBoolean(true); 
                    state = 30; // check if we are not root (окончание)
                    break;
                }
                case 32: { // the end of DFS
                    stack.pushBoolean(false); 
                    state = 30; // check if we are not root (окончание)
                    break;
                }
                case 33: { // cleanup after traversal
                    state = 34; // ololo3
                    break;
                }
                case 34: { // ololo3
                    stack.pushBoolean(false); 
                    state = 35; // search for first occurence of number in index
                    break;
                }
                case 35: { // search for first occurence of number in index
                    if (d.i < 2 * d.array.length - 1) {
                        state = 36; 
                    } else {
                        state = 41; // splitting depth into pieces
                    }
                    break;
                }
                case 36: { 
                    if (d.first[d.index[d.i]] == -1) {
                        state = 38; // First occurence of index[i] found
                    } else {
                        state = 39; // index[i] already was used
                    }
                    break;
                }
                case 37: { //  (окончание)
                    state = 40; // increment loop variable
                    break;
                }
                case 38: { // First occurence of index[i] found
                    stack.pushBoolean(true); 
                    state = 37; //  (окончание)
                    break;
                }
                case 39: { // index[i] already was used
                    stack.pushBoolean(false); 
                    state = 37; //  (окончание)
                    break;
                }
                case 40: { // increment loop variable
                    stack.pushBoolean(true); 
                    state = 35; // search for first occurence of number in index
                    break;
                }
                case 41: { // splitting depth into pieces
                    state = 42; // ololo4
                    break;
                }
                case 42: { // ololo4
                    stack.pushBoolean(false); 
                    state = 43; // build array for sparce table
                    break;
                }
                case 43: { // build array for sparce table
                    if (d.i < d.maximums.length) {
                        state = 44; // initialization of j
                    } else {
                        state = 51; // maximums found
                    }
                    break;
                }
                case 44: { // initialization of j
                    stack.pushBoolean(false); 
                    state = 45; // find maximum in piece
                    break;
                }
                case 45: { // find maximum in piece
                    if (d.j < d.pieceSize && d.i * d.pieceSize + d.j < d.depth.length) {
                        state = 46; // ололошеньки
                    } else {
                        state = 50; 
                    }
                    break;
                }
                case 46: { // ололошеньки
                    if (d.maximums[d.i] > d.depth[d.i * d.pieceSize + d.j]) {
                        state = 48; // new maximum found
                    } else {
                        stack.pushBoolean(false); 
                        state = 47; // ололошеньки (окончание)
                    }
                    break;
                }
                case 47: { // ололошеньки (окончание)
                    state = 49; // increment j
                    break;
                }
                case 48: { // new maximum found
                    stack.pushBoolean(true); 
                    state = 47; // ололошеньки (окончание)
                    break;
                }
                case 49: { // increment j
                    stack.pushBoolean(true); 
                    state = 45; // find maximum in piece
                    break;
                }
                case 50: { 
                    stack.pushBoolean(true); 
                    state = 43; // build array for sparce table
                    break;
                }
                case 51: { // maximums found
                    stack.pushBoolean(false); 
                    state = 52; // row in sparse table
                    break;
                }
                case 52: { // row in sparse table
                    if ((1 << d.i) <= d.maximums.length) {
                        state = 53; // init loop variable
                    } else {
                        state = 62; // ololo10
                    }
                    break;
                }
                case 53: { // init loop variable
                    stack.pushBoolean(false); 
                    state = 54; // column in sparse table
                    break;
                }
                case 54: { // column in sparse table
                    if ((d.j + (1 << d.i)) <= d.maximums.length) {
                        state = 55; // compute next variable in sparse table
                    } else {
                        state = 61; // ololo9
                    }
                    break;
                }
                case 55: { // compute next variable in sparse table
                    state = 56; // ololo5
                    break;
                }
                case 56: { // ololo5
                    if (d.table[d.i - 1][d.j] > d.table[d.i - 1][d.j + (1 << (d.i - 1))]) {
                        state = 58; // ololo6
                    } else {
                        state = 59; // ololo7
                    }
                    break;
                }
                case 57: { // ololo5 (окончание)
                    state = 60; // ololo8
                    break;
                }
                case 58: { // ololo6
                    stack.pushBoolean(true); 
                    state = 57; // ololo5 (окончание)
                    break;
                }
                case 59: { // ololo7
                    stack.pushBoolean(false); 
                    state = 57; // ololo5 (окончание)
                    break;
                }
                case 60: { // ololo8
                    stack.pushBoolean(true); 
                    state = 54; // column in sparse table
                    break;
                }
                case 61: { // ololo9
                    stack.pushBoolean(true); 
                    state = 52; // row in sparse table
                    break;
                }
                case 62: { // ololo10
                    stack.pushBoolean(false); 
                    state = 63; // ololo11
                    break;
                }
                case 63: { // ololo11
                    if (d.i < d.maximums.length) {
                        state = 64; // ololo12
                    } else {
                        state = 68; // ololo15
                    }
                    break;
                }
                case 64: { // ololo12
                    stack.pushBoolean(false); 
                    state = 65; // ololo13
                    break;
                }
                case 65: { // ololo13
                    if (d.j < d.pieceSize - 1 && d.i * d.pieceSize + d.j < d.depth.length) {
                        state = 66; // ololo14
                    } else {
                        state = 67; // ololo15
                    }
                    break;
                }
                case 66: { // ololo14
                    stack.pushBoolean(true); 
                    state = 65; // ololo13
                    break;
                }
                case 67: { // ololo15
                    stack.pushBoolean(true); 
                    state = 63; // ololo11
                    break;
                }
                case 68: { // ololo15
                    stack.pushBoolean(true); 
                    state = 2; // build all auxillary structures (окончание)
                    break;
                }
                case 69: { // building finished
                    state = 70; // LR
                    break;
                }
                case 70: { // LR
                    if (d.left2 > d.right2) {
                        state = 72; // atata2
                    } else {
                        stack.pushBoolean(false); 
                        state = 71; // LR (окончание)
                    }
                    break;
                }
                case 71: { // LR (окончание)
                    state = 73; // atata3
                    break;
                }
                case 72: { // atata2
                    stack.pushBoolean(true); 
                    state = 71; // LR (окончание)
                    break;
                }
                case 73: { // atata3
                    state = 74; // ololo19
                    break;
                }
                case 74: { // ololo19
                    if (d.leftPiece == d.rightPiece) {
                        state = 76; // ololo20
                    } else {
                        state = 83; // ololo30
                    }
                    break;
                }
                case 75: { // ololo19 (окончание)
                    state = END_STATE; 
                    break;
                }
                case 76: { // ololo20
                    stack.pushBoolean(false); 
                    state = 77; // ololo21
                    break;
                }
                case 77: { // ololo21
                    if (d.i <= d.right2) {
                        state = 78; // ololo22
                    } else {
                        state = 82; // ololo25
                    }
                    break;
                }
                case 78: { // ololo22
                    if (d.depth[d.i] < d.ans) {
                        state = 80; // ololo23
                    } else {
                        stack.pushBoolean(false); 
                        state = 79; // ololo22 (окончание)
                    }
                    break;
                }
                case 79: { // ololo22 (окончание)
                    state = 81; // ololo24
                    break;
                }
                case 80: { // ololo23
                    stack.pushBoolean(true); 
                    state = 79; // ololo22 (окончание)
                    break;
                }
                case 81: { // ololo24
                    stack.pushBoolean(true); 
                    state = 77; // ololo21
                    break;
                }
                case 82: { // ololo25
                    stack.pushBoolean(true); 
                    state = 75; // ololo19 (окончание)
                    break;
                }
                case 83: { // ololo30
                    stack.pushBoolean(false); 
                    state = 84; // ololo31
                    break;
                }
                case 84: { // ololo31
                    if (d.tmp != 0) {
                        state = 85; // ololo32
                    } else {
                        stack.pushBoolean(false); 
                        state = 86; // ololo33
                    }
                    break;
                }
                case 85: { // ololo32
                    stack.pushBoolean(true); 
                    state = 84; // ololo31
                    break;
                }
                case 86: { // ololo33
                    if (d.i < (d.leftPiece + 1) * d.pieceSize) {
                        state = 87; // ololo34
                    } else {
                        state = 91; // ololo36
                    }
                    break;
                }
                case 87: { // ololo34
                    if (d.depth[d.i] < d.ans1) {
                        state = 89; // ololo35
                    } else {
                        stack.pushBoolean(false); 
                        state = 88; // ololo34 (окончание)
                    }
                    break;
                }
                case 88: { // ololo34 (окончание)
                    state = 90; // ololo355
                    break;
                }
                case 89: { // ololo35
                    stack.pushBoolean(true); 
                    state = 88; // ololo34 (окончание)
                    break;
                }
                case 90: { // ololo355
                    stack.pushBoolean(true); 
                    state = 86; // ololo33
                    break;
                }
                case 91: { // ololo36
                    stack.pushBoolean(false); 
                    state = 92; // ololo37
                    break;
                }
                case 92: { // ololo37
                    if (d.i <= d.right2) {
                        state = 93; // ololo38
                    } else {
                        state = 97; // ololo40
                    }
                    break;
                }
                case 93: { // ololo38
                    if (d.depth[d.i] < d.ans3) {
                        state = 95; // ololo39
                    } else {
                        stack.pushBoolean(false); 
                        state = 94; // ololo38 (окончание)
                    }
                    break;
                }
                case 94: { // ololo38 (окончание)
                    state = 96; // ololo39
                    break;
                }
                case 95: { // ololo39
                    stack.pushBoolean(true); 
                    state = 94; // ololo38 (окончание)
                    break;
                }
                case 96: { // ololo39
                    stack.pushBoolean(true); 
                    state = 92; // ololo37
                    break;
                }
                case 97: { // ololo40
                    stack.pushBoolean(false); 
                    state = 98; // ololo41
                    break;
                }
                case 98: { // ololo41
                    if (d.i < d.rightPiece * d.pieceSize) {
                        state = 99; // ololo42
                    } else {
                        state = 103; 
                    }
                    break;
                }
                case 99: { // ololo42
                    if (d.depth[d.i] < d.ans2) {
                        state = 101; // ololo43
                    } else {
                        stack.pushBoolean(false); 
                        state = 100; // ololo42 (окончание)
                    }
                    break;
                }
                case 100: { // ololo42 (окончание)
                    state = 102; // ololo44
                    break;
                }
                case 101: { // ololo43
                    stack.pushBoolean(true); 
                    state = 100; // ololo42 (окончание)
                    break;
                }
                case 102: { // ololo44
                    stack.pushBoolean(true); 
                    state = 98; // ololo41
                    break;
                }
                case 103: { 
                    if (d.index2 != -1) {
                        state = 105; // ololo45
                    } else {
                        state = 106; // 445
                    }
                    break;
                }
                case 104: { //  (окончание)
                    state = 107; // ololo458
                    break;
                }
                case 105: { // ololo45
                    stack.pushBoolean(true); 
                    state = 104; //  (окончание)
                    break;
                }
                case 106: { // 445
                    stack.pushBoolean(false); 
                    state = 104; //  (окончание)
                    break;
                }
                case 107: { // ololo458
                    state = 108; // ololo46
                    break;
                }
                case 108: { // ololo46
                    if (d.ans2 > d.ans) {
                        state = 110; // ololo47
                    } else {
                        stack.pushBoolean(false); 
                        state = 109; // ololo46 (окончание)
                    }
                    break;
                }
                case 109: { // ololo46 (окончание)
                    state = 111; // ololo48
                    break;
                }
                case 110: { // ololo47
                    stack.pushBoolean(true); 
                    state = 109; // ololo46 (окончание)
                    break;
                }
                case 111: { // ololo48
                    if (d.ans3 > d.ans) {
                        state = 113; // ololo49
                    } else {
                        stack.pushBoolean(false); 
                        state = 112; // ololo48 (окончание)
                    }
                    break;
                }
                case 112: { // ololo48 (окончание)
                    stack.pushBoolean(false); 
                    state = 75; // ololo19 (окончание)
                    break;
                }
                case 113: { // ololo49
                    stack.pushBoolean(true); 
                    state = 112; // ololo48 (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // build all auxillary structures
                    break;
                }
                case 2: { // build all auxillary structures (окончание)
                    break;
                }
                case 3: { // Start of cycle
                    startSection();
                    storeField(d, "i");
                    							d.i = 0;
                    storeField(d, "stack");
                    							d.stack = new int[d.array.length];
                    storeField(d, "stackSize");
                    							d.stackSize = 0;
                    break;
                }
                case 4: { // Cycle over elements to build cartesian tree
                    break;
                }
                case 5: { // Backup stack size
                    startSection();
                    storeField(d, "oldStackSize");
                    						d.oldStackSize = d.stackSize;
                    break;
                }
                case 6: { // Drop all elements above a[i]
                    break;
                }
                case 7: { // decrementStackSize
                    startSection();
                    storeField(d, "stackSize");
                    							d.stackSize = d.stackSize - 1;
                    break;
                }
                case 8: { // check for new right son
                    break;
                }
                case 9: { // check for new right son (окончание)
                    break;
                }
                case 10: { // right son assignment
                    startSection();
                    storeArray(d.rightSon, d.stack[d.stackSize - 1]);
                    							d.rightSon[d.stack[d.stackSize - 1]] = d.i;
                    break;
                }
                case 11: { // check for new left son
                    break;
                }
                case 12: { // check for new left son (окончание)
                    break;
                }
                case 13: { // left son assignment
                    startSection();
                    storeArray(d.leftSon, d.i);
                    							d.leftSon[d.i] = d.stack[d.stackSize];
                    break;
                }
                case 14: { // push element to stack
                    startSection();
                    storeArray(d.stack, d.stackSize);
                    						d.stack[d.stackSize] = d.i;
                    storeField(d, "stackSize");
                    						d.stackSize = d.stackSize + 1;
                    break;
                }
                case 15: { // Cycle step
                    startSection();
                    storeField(d, "i");
                    						d.i = d.i + 1;
                    break;
                }
                case 16: { 
                    break;
                }
                case 17: { // DFSCommnet
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
                case 18: { // ololo2
                    startSection();
                    break;
                }
                case 19: { // Traversal over tree
                    break;
                }
                case 20: { // Try to go left
                    break;
                }
                case 21: { // Try to go left (окончание)
                    break;
                }
                case 22: { // go left
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
                case 23: { // advancePointer
                    break;
                }
                case 24: { // advancePointer (окончание)
                    break;
                }
                case 25: { // advancePointer1
                    startSection();
                    storeArray(d.passed, d.stack[d.stackSize - 1]);
                    											d.passed[d.stack[d.stackSize - 1]] = d.passed[d.stack[d.stackSize - 1]] + 1;
                    break;
                }
                case 26: { // Try to go right
                    break;
                }
                case 27: { // Try to go right (окончание)
                    break;
                }
                case 28: { // GoGoRight
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
                case 29: { // check if we are not root
                    break;
                }
                case 30: { // check if we are not root (окончание)
                    break;
                }
                case 31: { // processing of vertex finished
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
                case 32: { // the end of DFS
                    startSection();
                    storeField(d, "stackSize");
                    													d.stackSize = d.stackSize - 1;
                    break;
                }
                case 33: { // cleanup after traversal
                    startSection();
                    storeField(d, "stage1");
                    						d.stage1 = true;
                    break;
                }
                case 34: { // ololo3
                    startSection();
                    storeField(d, "i");
                    						d.i = 0;
                    break;
                }
                case 35: { // search for first occurence of number in index
                    break;
                }
                case 36: { 
                    break;
                }
                case 37: { //  (окончание)
                    break;
                }
                case 38: { // First occurence of index[i] found
                    startSection();
                    storeArray(d.first, d.index[d.i]);
                    									d.first[d.index[d.i]] = d.i;
                    break;
                }
                case 39: { // index[i] already was used
                    startSection();
                    break;
                }
                case 40: { // increment loop variable
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 41: { // splitting depth into pieces
                    startSection();
                    storeField(d, "i");
                    						d.i = 0;
                    break;
                }
                case 42: { // ololo4
                    startSection();
                    break;
                }
                case 43: { // build array for sparce table
                    break;
                }
                case 44: { // initialization of j
                    startSection();
                    storeField(d, "j");
                    							d.j = 0;
                    break;
                }
                case 45: { // find maximum in piece
                    break;
                }
                case 46: { // ололошеньки
                    break;
                }
                case 47: { // ололошеньки (окончание)
                    break;
                }
                case 48: { // new maximum found
                    startSection();
                    storeArray(d.maximums, d.i);
                    										d.maximums[d.i] = d.depth[d.i * d.pieceSize + d.j];
                    break;
                }
                case 49: { // increment j
                    startSection();
                    storeField(d, "j");
                    								d.j = d.j + 1;
                    break;
                }
                case 50: { 
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 51: { // maximums found
                    startSection();
                    storeField(d, "i");
                    						d.i = 1;
                    break;
                }
                case 52: { // row in sparse table
                    break;
                }
                case 53: { // init loop variable
                    startSection();
                    storeField(d, "j");
                    							d.j = 0;
                    break;
                }
                case 54: { // column in sparse table
                    break;
                }
                case 55: { // compute next variable in sparse table
                    startSection();
                    break;
                }
                case 56: { // ololo5
                    break;
                }
                case 57: { // ololo5 (окончание)
                    break;
                }
                case 58: { // ololo6
                    startSection();
                    storeArray(d.table[d.i], d.j);
                    										d.table[d.i][d.j] = d.table[d.i - 1][d.j + (1 << (d.i - 1))];
                    break;
                }
                case 59: { // ololo7
                    startSection();
                    storeArray(d.table[d.i], d.j);
                    										d.table[d.i][d.j] = d.table[d.i - 1][d.j];
                    break;
                }
                case 60: { // ololo8
                    startSection();
                    storeField(d, "j");
                    								d.j = d.j + 1;
                    break;
                }
                case 61: { // ololo9
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 62: { // ololo10
                    startSection();
                    storeField(d, "i");
                    						d.i = 0;
                    break;
                }
                case 63: { // ololo11
                    break;
                }
                case 64: { // ololo12
                    startSection();
                    storeField(d, "j");
                    							d.j = 0;
                    storeArray(d.table2[d.i], 0);
                    							d.table2[d.i][0] = d.depth[d.i * d.pieceSize + d.j];
                    break;
                }
                case 65: { // ololo13
                    break;
                }
                case 66: { // ololo14
                    startSection();
                    storeArray(d.table2[d.i], d.j + 1);
                    								d.table2[d.i][d.j + 1] = d.depth[d.i * d.pieceSize + d.j + 1] - d.depth[d.i * d.pieceSize + d.j];
                    storeField(d, "j");
                    								d.j = d.j + 1;
                    break;
                }
                case 67: { // ololo15
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 68: { // ololo15
                    startSection();
                    storeField(d, "ready");
                    						d.ready = true;
                    break;
                }
                case 69: { // building finished
                    startSection();
                    storeField(d, "left2");
                    				d.left2 = d.first[d.left - 1];
                    storeField(d, "right2");
                    				d.right2 = d.first[d.right - 1];
                    storeField(d, "leftPiece");
                    				d.leftPiece = d.left2 / d.pieceSize;
                    storeField(d, "rightPiece");
                    				d.rightPiece = d.right2 / d.pieceSize;
                    storeField(d, "i");
                    				d.i = d.left2;
                    storeField(d, "ans1");
                    				d.ans1 = -1;
                    storeField(d, "ans2");
                    				d.ans2 = -1;
                    storeField(d, "ans3");
                    				d.ans3 = -1;
                    break;
                }
                case 70: { // LR
                    break;
                }
                case 71: { // LR (окончание)
                    break;
                }
                case 72: { // atata2
                    startSection();
                    storeField(d, "tmp");
                    						d.tmp = d.left2;
                    storeField(d, "left2");
                    						d.left2 = d.right2;
                    storeField(d, "right2");
                    						d.right2 = d.tmp;
                    storeField(d, "tmp");
                    						d.tmp = d.leftPiece;
                    storeField(d, "leftPiece");
                    						d.leftPiece = d.rightPiece;
                    storeField(d, "rightPiece");
                    						d.rightPiece = d.tmp;
                    break;
                }
                case 73: { // atata3
                    startSection();
                    break;
                }
                case 74: { // ololo19
                    break;
                }
                case 75: { // ololo19 (окончание)
                    break;
                }
                case 76: { // ololo20
                    startSection();
                    storeField(d, "i");
                    						d.i = d.left2;
                    storeField(d, "ans");
                    						d.ans = 1000000;
                    storeField(d, "index1");
                    						d.index1 = -1;
                    break;
                }
                case 77: { // ololo21
                    break;
                }
                case 78: { // ololo22
                    break;
                }
                case 79: { // ololo22 (окончание)
                    break;
                }
                case 80: { // ololo23
                    startSection();
                    storeField(d, "ans");
                    									d.ans = d.depth[d.i];
                    storeField(d, "index1");
                    									d.index1 = d.i;
                    break;
                }
                case 81: { // ololo24
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 82: { // ololo25
                    startSection();
                    storeField(d, "ans");
                    						d.ans = d.array[d.index[d.index1]];
                    break;
                }
                case 83: { // ololo30
                    startSection();
                    storeField(d, "log2");
                    						d.log2 = 0;
                    storeField(d, "tmp");
                    						d.tmp = d.rightPiece - d.leftPiece - 2;
                    storeField(d, "i");
                    						d.i = d.left2;
                    storeField(d, "ans1");
                    						d.ans1 = 1000000;
                    storeField(d, "ans2");
                    						d.ans2 = 1000000;
                    storeField(d, "ans3");
                    						d.ans3 = 1000000;
                    storeField(d, "index1");
                    						d.index1 = -1;
                    storeField(d, "index2");
                    						d.index2 = -1;
                    storeField(d, "index3");
                    						d.index3 = -1;
                    break;
                }
                case 84: { // ololo31
                    break;
                }
                case 85: { // ololo32
                    startSection();
                    storeField(d, "log2");
                    							d.log2 = d.log2 + 1;
                    storeField(d, "tmp");
                    							d.tmp = d.tmp / 2;
                    break;
                }
                case 86: { // ololo33
                    break;
                }
                case 87: { // ololo34
                    break;
                }
                case 88: { // ololo34 (окончание)
                    break;
                }
                case 89: { // ololo35
                    startSection();
                    storeField(d, "index1");
                    									d.index1 = d.i;
                    storeField(d, "ans1");
                    									d.ans1 = d.depth[d.i];
                    break;
                }
                case 90: { // ololo355
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 91: { // ololo36
                    startSection();
                    storeField(d, "i");
                    						d.i = (d.rightPiece - 1) * d.pieceSize;
                    storeField(d, "ans1");
                    						d.ans1 = d.array[d.index[d.index1]];
                    break;
                }
                case 92: { // ololo37
                    break;
                }
                case 93: { // ololo38
                    break;
                }
                case 94: { // ololo38 (окончание)
                    break;
                }
                case 95: { // ololo39
                    startSection();
                    storeField(d, "ans3");
                    									d.ans3 = d.depth[d.i];
                    storeField(d, "index3");
                    									d.index3 = d.i;
                    break;
                }
                case 96: { // ololo39
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 97: { // ololo40
                    startSection();
                    storeField(d, "i");
                    						d.i = (d.leftPiece + 1) * d.pieceSize;
                    storeField(d, "ans3");
                    						d.ans3 = d.array[d.index[d.index3]];
                    break;
                }
                case 98: { // ololo41
                    break;
                }
                case 99: { // ololo42
                    break;
                }
                case 100: { // ololo42 (окончание)
                    break;
                }
                case 101: { // ololo43
                    startSection();
                    storeField(d, "ans2");
                    									d.ans2 = d.depth[d.i];
                    storeField(d, "index2");
                    									d.index2 = d.i;
                    break;
                }
                case 102: { // ololo44
                    startSection();
                    storeField(d, "i");
                    							d.i = d.i + 1;
                    break;
                }
                case 103: { 
                    break;
                }
                case 104: { //  (окончание)
                    break;
                }
                case 105: { // ololo45
                    startSection();
                    storeField(d, "ans2");
                    								d.ans2 = d.array[d.index[d.index2]];
                    break;
                }
                case 106: { // 445
                    startSection();
                    storeField(d, "ans2");
                    								d.ans2 = -1;
                    break;
                }
                case 107: { // ololo458
                    startSection();
                    storeField(d, "ans");
                    						d.ans = d.ans1;
                    break;
                }
                case 108: { // ololo46
                    break;
                }
                case 109: { // ololo46 (окончание)
                    break;
                }
                case 110: { // ololo47
                    startSection();
                    storeField(d, "ans");
                    								d.ans = d.ans2;
                    break;
                }
                case 111: { // ololo48
                    break;
                }
                case 112: { // ololo48 (окончание)
                    break;
                }
                case 113: { // ololo49
                    startSection();
                    storeField(d, "ans");
                    								d.ans = d.ans3;
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
                case 1: { // build all auxillary structures
                    break;
                }
                case 2: { // build all auxillary structures (окончание)
                    break;
                }
                case 3: { // Start of cycle
                    restoreSection();
                    break;
                }
                case 4: { // Cycle over elements to build cartesian tree
                    break;
                }
                case 5: { // Backup stack size
                    restoreSection();
                    break;
                }
                case 6: { // Drop all elements above a[i]
                    break;
                }
                case 7: { // decrementStackSize
                    restoreSection();
                    break;
                }
                case 8: { // check for new right son
                    break;
                }
                case 9: { // check for new right son (окончание)
                    break;
                }
                case 10: { // right son assignment
                    restoreSection();
                    break;
                }
                case 11: { // check for new left son
                    break;
                }
                case 12: { // check for new left son (окончание)
                    break;
                }
                case 13: { // left son assignment
                    restoreSection();
                    break;
                }
                case 14: { // push element to stack
                    restoreSection();
                    break;
                }
                case 15: { // Cycle step
                    restoreSection();
                    break;
                }
                case 16: { 
                    break;
                }
                case 17: { // DFSCommnet
                    restoreSection();
                    break;
                }
                case 18: { // ololo2
                    restoreSection();
                    break;
                }
                case 19: { // Traversal over tree
                    break;
                }
                case 20: { // Try to go left
                    break;
                }
                case 21: { // Try to go left (окончание)
                    break;
                }
                case 22: { // go left
                    restoreSection();
                    break;
                }
                case 23: { // advancePointer
                    break;
                }
                case 24: { // advancePointer (окончание)
                    break;
                }
                case 25: { // advancePointer1
                    restoreSection();
                    break;
                }
                case 26: { // Try to go right
                    break;
                }
                case 27: { // Try to go right (окончание)
                    break;
                }
                case 28: { // GoGoRight
                    restoreSection();
                    break;
                }
                case 29: { // check if we are not root
                    break;
                }
                case 30: { // check if we are not root (окончание)
                    break;
                }
                case 31: { // processing of vertex finished
                    restoreSection();
                    break;
                }
                case 32: { // the end of DFS
                    restoreSection();
                    break;
                }
                case 33: { // cleanup after traversal
                    restoreSection();
                    break;
                }
                case 34: { // ololo3
                    restoreSection();
                    break;
                }
                case 35: { // search for first occurence of number in index
                    break;
                }
                case 36: { 
                    break;
                }
                case 37: { //  (окончание)
                    break;
                }
                case 38: { // First occurence of index[i] found
                    restoreSection();
                    break;
                }
                case 39: { // index[i] already was used
                    restoreSection();
                    break;
                }
                case 40: { // increment loop variable
                    restoreSection();
                    break;
                }
                case 41: { // splitting depth into pieces
                    restoreSection();
                    break;
                }
                case 42: { // ololo4
                    restoreSection();
                    break;
                }
                case 43: { // build array for sparce table
                    break;
                }
                case 44: { // initialization of j
                    restoreSection();
                    break;
                }
                case 45: { // find maximum in piece
                    break;
                }
                case 46: { // ололошеньки
                    break;
                }
                case 47: { // ололошеньки (окончание)
                    break;
                }
                case 48: { // new maximum found
                    restoreSection();
                    break;
                }
                case 49: { // increment j
                    restoreSection();
                    break;
                }
                case 50: { 
                    restoreSection();
                    break;
                }
                case 51: { // maximums found
                    restoreSection();
                    break;
                }
                case 52: { // row in sparse table
                    break;
                }
                case 53: { // init loop variable
                    restoreSection();
                    break;
                }
                case 54: { // column in sparse table
                    break;
                }
                case 55: { // compute next variable in sparse table
                    restoreSection();
                    break;
                }
                case 56: { // ololo5
                    break;
                }
                case 57: { // ololo5 (окончание)
                    break;
                }
                case 58: { // ololo6
                    restoreSection();
                    break;
                }
                case 59: { // ololo7
                    restoreSection();
                    break;
                }
                case 60: { // ololo8
                    restoreSection();
                    break;
                }
                case 61: { // ololo9
                    restoreSection();
                    break;
                }
                case 62: { // ololo10
                    restoreSection();
                    break;
                }
                case 63: { // ololo11
                    break;
                }
                case 64: { // ololo12
                    restoreSection();
                    break;
                }
                case 65: { // ololo13
                    break;
                }
                case 66: { // ololo14
                    restoreSection();
                    break;
                }
                case 67: { // ololo15
                    restoreSection();
                    break;
                }
                case 68: { // ololo15
                    restoreSection();
                    break;
                }
                case 69: { // building finished
                    restoreSection();
                    break;
                }
                case 70: { // LR
                    break;
                }
                case 71: { // LR (окончание)
                    break;
                }
                case 72: { // atata2
                    restoreSection();
                    break;
                }
                case 73: { // atata3
                    restoreSection();
                    break;
                }
                case 74: { // ololo19
                    break;
                }
                case 75: { // ololo19 (окончание)
                    break;
                }
                case 76: { // ololo20
                    restoreSection();
                    break;
                }
                case 77: { // ololo21
                    break;
                }
                case 78: { // ololo22
                    break;
                }
                case 79: { // ololo22 (окончание)
                    break;
                }
                case 80: { // ololo23
                    restoreSection();
                    break;
                }
                case 81: { // ololo24
                    restoreSection();
                    break;
                }
                case 82: { // ololo25
                    restoreSection();
                    break;
                }
                case 83: { // ololo30
                    restoreSection();
                    break;
                }
                case 84: { // ololo31
                    break;
                }
                case 85: { // ololo32
                    restoreSection();
                    break;
                }
                case 86: { // ololo33
                    break;
                }
                case 87: { // ololo34
                    break;
                }
                case 88: { // ololo34 (окончание)
                    break;
                }
                case 89: { // ololo35
                    restoreSection();
                    break;
                }
                case 90: { // ololo355
                    restoreSection();
                    break;
                }
                case 91: { // ololo36
                    restoreSection();
                    break;
                }
                case 92: { // ololo37
                    break;
                }
                case 93: { // ololo38
                    break;
                }
                case 94: { // ololo38 (окончание)
                    break;
                }
                case 95: { // ololo39
                    restoreSection();
                    break;
                }
                case 96: { // ololo39
                    restoreSection();
                    break;
                }
                case 97: { // ololo40
                    restoreSection();
                    break;
                }
                case 98: { // ololo41
                    break;
                }
                case 99: { // ololo42
                    break;
                }
                case 100: { // ololo42 (окончание)
                    break;
                }
                case 101: { // ololo43
                    restoreSection();
                    break;
                }
                case 102: { // ololo44
                    restoreSection();
                    break;
                }
                case 103: { 
                    break;
                }
                case 104: { //  (окончание)
                    break;
                }
                case 105: { // ololo45
                    restoreSection();
                    break;
                }
                case 106: { // 445
                    restoreSection();
                    break;
                }
                case 107: { // ololo458
                    restoreSection();
                    break;
                }
                case 108: { // ololo46
                    break;
                }
                case 109: { // ololo46 (окончание)
                    break;
                }
                case 110: { // ololo47
                    restoreSection();
                    break;
                }
                case 111: { // ololo48
                    break;
                }
                case 112: { // ololo48 (окончание)
                    break;
                }
                case 113: { // ololo49
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // build all auxillary structures
                    state = START_STATE; 
                    break;
                }
                case 2: { // build all auxillary structures (окончание)
                    if (stack.popBoolean()) {
                        state = 68; // ololo15
                    } else {
                        state = 1; // build all auxillary structures
                    }
                    break;
                }
                case 3: { // Start of cycle
                    state = 1; // build all auxillary structures
                    break;
                }
                case 4: { // Cycle over elements to build cartesian tree
                    if (stack.popBoolean()) {
                        state = 15; // Cycle step
                    } else {
                        state = 3; // Start of cycle
                    }
                    break;
                }
                case 5: { // Backup stack size
                    state = 4; // Cycle over elements to build cartesian tree
                    break;
                }
                case 6: { // Drop all elements above a[i]
                    if (stack.popBoolean()) {
                        state = 7; // decrementStackSize
                    } else {
                        state = 5; // Backup stack size
                    }
                    break;
                }
                case 7: { // decrementStackSize
                    state = 6; // Drop all elements above a[i]
                    break;
                }
                case 8: { // check for new right son
                    state = 6; // Drop all elements above a[i]
                    break;
                }
                case 9: { // check for new right son (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // right son assignment
                    } else {
                        state = 8; // check for new right son
                    }
                    break;
                }
                case 10: { // right son assignment
                    state = 8; // check for new right son
                    break;
                }
                case 11: { // check for new left son
                    state = 9; // check for new right son (окончание)
                    break;
                }
                case 12: { // check for new left son (окончание)
                    if (stack.popBoolean()) {
                        state = 13; // left son assignment
                    } else {
                        state = 11; // check for new left son
                    }
                    break;
                }
                case 13: { // left son assignment
                    state = 11; // check for new left son
                    break;
                }
                case 14: { // push element to stack
                    state = 12; // check for new left son (окончание)
                    break;
                }
                case 15: { // Cycle step
                    state = 14; // push element to stack
                    break;
                }
                case 16: { 
                    state = 4; // Cycle over elements to build cartesian tree
                    break;
                }
                case 17: { // DFSCommnet
                    state = 16; 
                    break;
                }
                case 18: { // ololo2
                    state = 17; // DFSCommnet
                    break;
                }
                case 19: { // Traversal over tree
                    if (stack.popBoolean()) {
                        state = 21; // Try to go left (окончание)
                    } else {
                        state = 18; // ololo2
                    }
                    break;
                }
                case 20: { // Try to go left
                    state = 19; // Traversal over tree
                    break;
                }
                case 21: { // Try to go left (окончание)
                    if (stack.popBoolean()) {
                        state = 22; // go left
                    } else {
                        state = 27; // Try to go right (окончание)
                    }
                    break;
                }
                case 22: { // go left
                    state = 20; // Try to go left
                    break;
                }
                case 23: { // advancePointer
                    state = 20; // Try to go left
                    break;
                }
                case 24: { // advancePointer (окончание)
                    if (stack.popBoolean()) {
                        state = 25; // advancePointer1
                    } else {
                        state = 23; // advancePointer
                    }
                    break;
                }
                case 25: { // advancePointer1
                    state = 23; // advancePointer
                    break;
                }
                case 26: { // Try to go right
                    state = 24; // advancePointer (окончание)
                    break;
                }
                case 27: { // Try to go right (окончание)
                    if (stack.popBoolean()) {
                        state = 28; // GoGoRight
                    } else {
                        state = 30; // check if we are not root (окончание)
                    }
                    break;
                }
                case 28: { // GoGoRight
                    state = 26; // Try to go right
                    break;
                }
                case 29: { // check if we are not root
                    state = 26; // Try to go right
                    break;
                }
                case 30: { // check if we are not root (окончание)
                    if (stack.popBoolean()) {
                        state = 31; // processing of vertex finished
                    } else {
                        state = 32; // the end of DFS
                    }
                    break;
                }
                case 31: { // processing of vertex finished
                    state = 29; // check if we are not root
                    break;
                }
                case 32: { // the end of DFS
                    state = 29; // check if we are not root
                    break;
                }
                case 33: { // cleanup after traversal
                    state = 19; // Traversal over tree
                    break;
                }
                case 34: { // ololo3
                    state = 33; // cleanup after traversal
                    break;
                }
                case 35: { // search for first occurence of number in index
                    if (stack.popBoolean()) {
                        state = 40; // increment loop variable
                    } else {
                        state = 34; // ololo3
                    }
                    break;
                }
                case 36: { 
                    state = 35; // search for first occurence of number in index
                    break;
                }
                case 37: { //  (окончание)
                    if (stack.popBoolean()) {
                        state = 38; // First occurence of index[i] found
                    } else {
                        state = 39; // index[i] already was used
                    }
                    break;
                }
                case 38: { // First occurence of index[i] found
                    state = 36; 
                    break;
                }
                case 39: { // index[i] already was used
                    state = 36; 
                    break;
                }
                case 40: { // increment loop variable
                    state = 37; //  (окончание)
                    break;
                }
                case 41: { // splitting depth into pieces
                    state = 35; // search for first occurence of number in index
                    break;
                }
                case 42: { // ololo4
                    state = 41; // splitting depth into pieces
                    break;
                }
                case 43: { // build array for sparce table
                    if (stack.popBoolean()) {
                        state = 50; 
                    } else {
                        state = 42; // ololo4
                    }
                    break;
                }
                case 44: { // initialization of j
                    state = 43; // build array for sparce table
                    break;
                }
                case 45: { // find maximum in piece
                    if (stack.popBoolean()) {
                        state = 49; // increment j
                    } else {
                        state = 44; // initialization of j
                    }
                    break;
                }
                case 46: { // ололошеньки
                    state = 45; // find maximum in piece
                    break;
                }
                case 47: { // ололошеньки (окончание)
                    if (stack.popBoolean()) {
                        state = 48; // new maximum found
                    } else {
                        state = 46; // ололошеньки
                    }
                    break;
                }
                case 48: { // new maximum found
                    state = 46; // ололошеньки
                    break;
                }
                case 49: { // increment j
                    state = 47; // ололошеньки (окончание)
                    break;
                }
                case 50: { 
                    state = 45; // find maximum in piece
                    break;
                }
                case 51: { // maximums found
                    state = 43; // build array for sparce table
                    break;
                }
                case 52: { // row in sparse table
                    if (stack.popBoolean()) {
                        state = 61; // ololo9
                    } else {
                        state = 51; // maximums found
                    }
                    break;
                }
                case 53: { // init loop variable
                    state = 52; // row in sparse table
                    break;
                }
                case 54: { // column in sparse table
                    if (stack.popBoolean()) {
                        state = 60; // ololo8
                    } else {
                        state = 53; // init loop variable
                    }
                    break;
                }
                case 55: { // compute next variable in sparse table
                    state = 54; // column in sparse table
                    break;
                }
                case 56: { // ololo5
                    state = 55; // compute next variable in sparse table
                    break;
                }
                case 57: { // ololo5 (окончание)
                    if (stack.popBoolean()) {
                        state = 58; // ololo6
                    } else {
                        state = 59; // ololo7
                    }
                    break;
                }
                case 58: { // ololo6
                    state = 56; // ololo5
                    break;
                }
                case 59: { // ololo7
                    state = 56; // ololo5
                    break;
                }
                case 60: { // ololo8
                    state = 57; // ololo5 (окончание)
                    break;
                }
                case 61: { // ololo9
                    state = 54; // column in sparse table
                    break;
                }
                case 62: { // ololo10
                    state = 52; // row in sparse table
                    break;
                }
                case 63: { // ololo11
                    if (stack.popBoolean()) {
                        state = 67; // ololo15
                    } else {
                        state = 62; // ololo10
                    }
                    break;
                }
                case 64: { // ololo12
                    state = 63; // ololo11
                    break;
                }
                case 65: { // ololo13
                    if (stack.popBoolean()) {
                        state = 66; // ololo14
                    } else {
                        state = 64; // ololo12
                    }
                    break;
                }
                case 66: { // ololo14
                    state = 65; // ololo13
                    break;
                }
                case 67: { // ololo15
                    state = 65; // ololo13
                    break;
                }
                case 68: { // ololo15
                    state = 63; // ololo11
                    break;
                }
                case 69: { // building finished
                    state = 2; // build all auxillary structures (окончание)
                    break;
                }
                case 70: { // LR
                    state = 69; // building finished
                    break;
                }
                case 71: { // LR (окончание)
                    if (stack.popBoolean()) {
                        state = 72; // atata2
                    } else {
                        state = 70; // LR
                    }
                    break;
                }
                case 72: { // atata2
                    state = 70; // LR
                    break;
                }
                case 73: { // atata3
                    state = 71; // LR (окончание)
                    break;
                }
                case 74: { // ololo19
                    state = 73; // atata3
                    break;
                }
                case 75: { // ololo19 (окончание)
                    if (stack.popBoolean()) {
                        state = 82; // ololo25
                    } else {
                        state = 112; // ololo48 (окончание)
                    }
                    break;
                }
                case 76: { // ololo20
                    state = 74; // ololo19
                    break;
                }
                case 77: { // ololo21
                    if (stack.popBoolean()) {
                        state = 81; // ololo24
                    } else {
                        state = 76; // ololo20
                    }
                    break;
                }
                case 78: { // ololo22
                    state = 77; // ololo21
                    break;
                }
                case 79: { // ololo22 (окончание)
                    if (stack.popBoolean()) {
                        state = 80; // ololo23
                    } else {
                        state = 78; // ololo22
                    }
                    break;
                }
                case 80: { // ololo23
                    state = 78; // ololo22
                    break;
                }
                case 81: { // ololo24
                    state = 79; // ololo22 (окончание)
                    break;
                }
                case 82: { // ololo25
                    state = 77; // ololo21
                    break;
                }
                case 83: { // ololo30
                    state = 74; // ololo19
                    break;
                }
                case 84: { // ololo31
                    if (stack.popBoolean()) {
                        state = 85; // ololo32
                    } else {
                        state = 83; // ololo30
                    }
                    break;
                }
                case 85: { // ololo32
                    state = 84; // ololo31
                    break;
                }
                case 86: { // ololo33
                    if (stack.popBoolean()) {
                        state = 90; // ololo355
                    } else {
                        state = 84; // ololo31
                    }
                    break;
                }
                case 87: { // ololo34
                    state = 86; // ololo33
                    break;
                }
                case 88: { // ololo34 (окончание)
                    if (stack.popBoolean()) {
                        state = 89; // ololo35
                    } else {
                        state = 87; // ololo34
                    }
                    break;
                }
                case 89: { // ololo35
                    state = 87; // ololo34
                    break;
                }
                case 90: { // ololo355
                    state = 88; // ololo34 (окончание)
                    break;
                }
                case 91: { // ololo36
                    state = 86; // ololo33
                    break;
                }
                case 92: { // ololo37
                    if (stack.popBoolean()) {
                        state = 96; // ololo39
                    } else {
                        state = 91; // ololo36
                    }
                    break;
                }
                case 93: { // ololo38
                    state = 92; // ololo37
                    break;
                }
                case 94: { // ololo38 (окончание)
                    if (stack.popBoolean()) {
                        state = 95; // ololo39
                    } else {
                        state = 93; // ololo38
                    }
                    break;
                }
                case 95: { // ololo39
                    state = 93; // ololo38
                    break;
                }
                case 96: { // ololo39
                    state = 94; // ololo38 (окончание)
                    break;
                }
                case 97: { // ololo40
                    state = 92; // ololo37
                    break;
                }
                case 98: { // ololo41
                    if (stack.popBoolean()) {
                        state = 102; // ololo44
                    } else {
                        state = 97; // ololo40
                    }
                    break;
                }
                case 99: { // ololo42
                    state = 98; // ololo41
                    break;
                }
                case 100: { // ololo42 (окончание)
                    if (stack.popBoolean()) {
                        state = 101; // ololo43
                    } else {
                        state = 99; // ololo42
                    }
                    break;
                }
                case 101: { // ololo43
                    state = 99; // ololo42
                    break;
                }
                case 102: { // ololo44
                    state = 100; // ololo42 (окончание)
                    break;
                }
                case 103: { 
                    state = 98; // ololo41
                    break;
                }
                case 104: { //  (окончание)
                    if (stack.popBoolean()) {
                        state = 105; // ololo45
                    } else {
                        state = 106; // 445
                    }
                    break;
                }
                case 105: { // ololo45
                    state = 103; 
                    break;
                }
                case 106: { // 445
                    state = 103; 
                    break;
                }
                case 107: { // ololo458
                    state = 104; //  (окончание)
                    break;
                }
                case 108: { // ololo46
                    state = 107; // ololo458
                    break;
                }
                case 109: { // ololo46 (окончание)
                    if (stack.popBoolean()) {
                        state = 110; // ololo47
                    } else {
                        state = 108; // ololo46
                    }
                    break;
                }
                case 110: { // ololo47
                    state = 108; // ololo46
                    break;
                }
                case 111: { // ololo48
                    state = 109; // ololo46 (окончание)
                    break;
                }
                case 112: { // ololo48 (окончание)
                    if (stack.popBoolean()) {
                        state = 113; // ololo49
                    } else {
                        state = 111; // ololo48
                    }
                    break;
                }
                case 113: { // ololo49
                    state = 111; // ololo48
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 75; // ololo19 (окончание)
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
                case 3: { // Start of cycle
                    comment = FarachColtonBender.this.getComment("Main.Begin"); 
                    args = new Object[]{FarachColtonBenderVisualizer.treeAsString(d.array)}; 
                    break;
                }
                case 4: { // Cycle over elements to build cartesian tree
                    if (d.i < d.array.length) {
                        comment = FarachColtonBender.this.getComment("Main.CartesianTreeLoop.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.CartesianTreeLoop.false"); 
                    }
                    args = new Object[]{new Integer((d.i < d.array.length) ? (d.array[d.i]) : 0)}; 
                    break;
                }
                case 6: { // Drop all elements above a[i]
                    if (d.stackSize > 0 && d.array[d.i] > d.array[d.stack[d.stackSize - 1]]) {
                        comment = FarachColtonBender.this.getComment("Main.dropSmallElements.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.dropSmallElements.false"); 
                    }
                    args = new Object[]{new Integer(d.stackSize == 0 ? 0: (d.array[d.stack[d.stackSize - 1]])), new Integer(d.array[d.i])}; 
                    break;
                }
                case 8: { // check for new right son
                    if (d.stackSize != 0) {
                        comment = FarachColtonBender.this.getComment("Main.checkHasRightSon.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.checkHasRightSon.false"); 
                    }
                    break;
                }
                case 11: { // check for new left son
                    if (d.oldStackSize != d.stackSize) {
                        comment = FarachColtonBender.this.getComment("Main.checkHasLeftSon.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.checkHasLeftSon.false"); 
                    }
                    break;
                }
                case 14: { // push element to stack
                    comment = FarachColtonBender.this.getComment("Main.pushElementToStack"); 
                    args = new Object[]{new Integer(d.array[d.i])}; 
                    break;
                }
                case 16: { 
                    comment = FarachColtonBender.this.getComment("Main.TreeBuilt"); 
                    break;
                }
                case 17: { // DFSCommnet
                    comment = FarachColtonBender.this.getComment("Main.DFSComment"); 
                    break;
                }
                case 19: { // Traversal over tree
                    if (d.stackSize > 0) {
                        comment = FarachColtonBender.this.getComment("Main.DFS.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.DFS.false"); 
                    }
                    args = new Object[]{new Integer(1 + (d.stackSize == 0 ? 0 : d.stack[d.stackSize - 1])), new Integer(d.array[d.stackSize == 0 ? 0 : d.stack[d.stackSize - 1]])}; 
                    break;
                }
                case 20: { // Try to go left
                    if (d.passed[d.stack[d.stackSize - 1]] == 0 && d.leftSon[d.stack[d.stackSize - 1]] != -1) {
                        comment = FarachColtonBender.this.getComment("Main.GoLeft.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.GoLeft.false"); 
                    }
                    break;
                }
                case 22: { // go left
                    comment = FarachColtonBender.this.getComment("Main.GoGoLeft"); 
                    break;
                }
                case 28: { // GoGoRight
                    comment = FarachColtonBender.this.getComment("Main.GoGoRight"); 
                    break;
                }
                case 31: { // processing of vertex finished
                    comment = FarachColtonBender.this.getComment("Main.goUp"); 
                    break;
                }
                case 33: { // cleanup after traversal
                    comment = FarachColtonBender.this.getComment("Main.traversalFinished"); 
                    break;
                }
                case 35: { // search for first occurence of number in index
                    if (d.i < 2 * d.array.length - 1) {
                        comment = FarachColtonBender.this.getComment("Main.searchFirstOccurence.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.searchFirstOccurence.false"); 
                    }
                    args = new Object[]{new Integer((d.i == 2 * d.array.length - 1 ? 0 : d.index[d.i]) + 1)}; 
                    break;
                }
                case 38: { // First occurence of index[i] found
                    comment = FarachColtonBender.this.getComment("Main.newElementFound"); 
                    args = new Object[]{new Integer(d.index[d.i] + 1)}; 
                    break;
                }
                case 39: { // index[i] already was used
                    comment = FarachColtonBender.this.getComment("Main.notNewElementFound"); 
                    args = new Object[]{new Integer(d.index[d.i] + 1)}; 
                    break;
                }
                case 41: { // splitting depth into pieces
                    comment = FarachColtonBender.this.getComment("Main.splittingIntoPieces1"); 
                    break;
                }
                case 42: { // ololo4
                    comment = FarachColtonBender.this.getComment("Main.ololo4"); 
                    break;
                }
                case 43: { // build array for sparce table
                    if (d.i < d.maximums.length) {
                        comment = FarachColtonBender.this.getComment("Main.buildArrayForSparseTable.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.buildArrayForSparseTable.false"); 
                    }
                    args = new Object[]{new Integer(d.i + 1)}; 
                    break;
                }
                case 48: { // new maximum found
                    comment = FarachColtonBender.this.getComment("Main.improveMaximum"); 
                    args = new Object[]{new Integer(d.depth[d.i * d.pieceSize + d.j]), new Integer(d.maximums[d.i])}; 
                    break;
                }
                case 49: { // increment j
                    comment = FarachColtonBender.this.getComment("Main.advanceIterator"); 
                    break;
                }
                case 51: { // maximums found
                    comment = FarachColtonBender.this.getComment("Main.maximumsFound"); 
                    break;
                }
                case 55: { // compute next variable in sparse table
                    comment = FarachColtonBender.this.getComment("Main.newVarInSparseTable"); 
                    args = new Object[]{new Integer(d.i), new Integer(d.j + 1), new Integer(d.i - 1), new Integer(d.table[d.i - 1][d.j]), new Integer(d.j + (1 << (d.i - 1))), new Integer(d.table[d.i - 1][d.j + (1 << (d.i - 1))])}; 
                    break;
                }
                case 56: { // ololo5
                    if (d.table[d.i - 1][d.j] > d.table[d.i - 1][d.j + (1 << (d.i - 1))]) {
                        comment = FarachColtonBender.this.getComment("Main.ololo5.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo5.false"); 
                    }
                    break;
                }
                case 58: { // ololo6
                    comment = FarachColtonBender.this.getComment("Main.ololo6"); 
                    break;
                }
                case 59: { // ololo7
                    comment = FarachColtonBender.this.getComment("Main.ololo7"); 
                    break;
                }
                case 62: { // ololo10
                    comment = FarachColtonBender.this.getComment("Main.ololo10"); 
                    break;
                }
                case 66: { // ololo14
                    comment = FarachColtonBender.this.getComment("Main.ololo14"); 
                    break;
                }
                case 68: { // ololo15
                    comment = FarachColtonBender.this.getComment("Main.ololo15"); 
                    break;
                }
                case 69: { // building finished
                    comment = FarachColtonBender.this.getComment("Main.buildingFinished"); 
                    break;
                }
                case 74: { // ololo19
                    if (d.leftPiece == d.rightPiece) {
                        comment = FarachColtonBender.this.getComment("Main.ololo19.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo19.false"); 
                    }
                    break;
                }
                case 77: { // ololo21
                    if (d.i <= d.right2) {
                        comment = FarachColtonBender.this.getComment("Main.ololo21.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo21.false"); 
                    }
                    args = new Object[]{new Integer(d.i)}; 
                    break;
                }
                case 78: { // ololo22
                    if (d.depth[d.i] < d.ans) {
                        comment = FarachColtonBender.this.getComment("Main.ololo22.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo22.false"); 
                    }
                    break;
                }
                case 84: { // ololo31
                    if (d.tmp != 0) {
                        comment = FarachColtonBender.this.getComment("Main.ololo31.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo31.false"); 
                    }
                    args = new Object[]{new Integer(d.log2), new Integer(d.tmp)}; 
                    break;
                }
                case 85: { // ololo32
                    comment = FarachColtonBender.this.getComment("Main.ololo32"); 
                    break;
                }
                case 91: { // ololo36
                    comment = FarachColtonBender.this.getComment("Main.ololo36"); 
                    args = new Object[]{new Integer(d.array[d.index[d.index1]])}; 
                    break;
                }
                case 97: { // ololo40
                    comment = FarachColtonBender.this.getComment("Main.ololo40"); 
                    args = new Object[]{new Integer(d.array[d.index[d.index3]])}; 
                    break;
                }
                case 98: { // ololo41
                    if (d.i < d.rightPiece * d.pieceSize) {
                        comment = FarachColtonBender.this.getComment("Main.ololo41.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.ololo41.false"); 
                    }
                    args = new Object[]{new Integer(d.i)}; 
                    break;
                }
                case 105: { // ololo45
                    comment = FarachColtonBender.this.getComment("Main.ololo45"); 
                    args = new Object[]{new Integer(d.array[d.index[d.index2]])}; 
                    break;
                }
                case 106: { // 445
                    comment = FarachColtonBender.this.getComment("Main.ololo445"); 
                    break;
                }
                case 107: { // ololo458
                    comment = FarachColtonBender.this.getComment("Main.ololo458"); 
                    args = new Object[]{new Integer(d.ans1), new Integer(d.ans2), new Integer(d.ans3)}; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = FarachColtonBender.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Integer(d.ans)}; 
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
                    break;
                }
                case 7: { // decrementStackSize
                    							d.visualizer.drawCartesianTree(d.i, d.stackSize);
                    break;
                }
                case 14: { // push element to stack
                    						d.visualizer.drawCartesianTree(d.i + 1, d.stackSize);
                    break;
                }
                case 16: { 
                    					d.visualizer.drawCartesianTree(d.array.length, 0);
                    break;
                }
                case 17: { // DFSCommnet
                    					d.visualizer.drawCellsForDFS(0, -1);
                    break;
                }
                case 18: { // ololo2
                    						d.visualizer.drawCellsForDFS(1, d.root);
                    break;
                }
                case 22: { // go left
                    									d.visualizer.drawCellsForDFS(d.pos, d.stack[d.stackSize - 1]);
                    break;
                }
                case 28: { // GoGoRight
                    											d.visualizer.drawCellsForDFS(d.pos, d.stack[d.stackSize - 1]);
                    break;
                }
                case 31: { // processing of vertex finished
                    													d.visualizer.drawCellsForDFS(d.pos, d.stack[d.stackSize - 1]);
                    break;
                }
                case 33: { // cleanup after traversal
                    						d.visualizer.drawCellsForDFS(d.pos, -1);
                    						d.visualizer.drawCartesianTree(0, 0);
                    						d.visualizer.redrawIndex();
                    break;
                }
                case 38: { // First occurence of index[i] found
                    									d.visualizer.redrawIndex();
                    break;
                }
                case 39: { // index[i] already was used
                    break;
                }
                case 40: { // increment loop variable
                    break;
                }
                case 41: { // splitting depth into pieces
                    						d.visualizer.drawMaximums(0);
                    						d.visualizer.drawDepth(-1);
                    						d.visualizer.redrawIndex();
                    break;
                }
                case 42: { // ololo4
                    						d.visualizer.drawDepth(0);			
                    break;
                }
                case 48: { // new maximum found
                    										d.visualizer.drawMaximums(d.i + 1);
                    break;
                }
                case 49: { // increment j
                    								d.visualizer.drawDepth(d.i * d.pieceSize + d.j);
                    break;
                }
                case 51: { // maximums found
                    						d.visualizer.drawDepth(-1);
                    						d.visualizer.drawTable(0, 0, -1, -1, -1, -1);
                    break;
                }
                case 58: { // ololo6
                    										d.visualizer.drawTable(d.i + 1, d.j + 1, d.i - 1, d.j + (1 << (d.i - 1)), d.i - 1, d.j);
                    break;
                }
                case 59: { // ololo7
                    										d.visualizer.drawTable(d.i + 1, d.j + 1, d.i - 1, d.j, d.i - 1, d.j + (1 << (d.i - 1)));
                    break;
                }
                case 62: { // ololo10
                    						d.visualizer.drawTable(d.maximums.length, d.maximums.length, -1, -1, -1, -1);
                    						d.visualizer.drawTable2(0, 0);
                    break;
                }
                case 66: { // ololo14
                    								d.visualizer.drawTable2(d.i + 1, d.j + 1);
                    break;
                }
                case 68: { // ololo15
                    						d.visualizer.drawTable2(d.maximums.length, d.depth.length % d.pieceSize + 1);
                    break;
                }
                case 69: { // building finished
                    				d.visualizer.drawMainArray();
                    				d.visualizer.drawCellsForDFSWithoutTree(2 * d.array.length - 1);
                    				d.visualizer.redrawIndex();
                    				d.visualizer.drawTable(d.array.length, d.array.length, -1, -1, -1, -1);
                    				d.visualizer.drawTable2(d.maximums.length, d.depth.length % d.pieceSize + 1);
                    break;
                }
                case 73: { // atata3
                    				d.visualizer.debug("LP: ", d.leftPiece);
                    				d.visualizer.debug("RP: ", d.rightPiece);
                    				d.visualizer.debug("Left2: ", d.left2);
                    				d.visualizer.debug("Right2: ", d.right2);
                    				d.visualizer.debug("Left: ", d.left);
                    				d.visualizer.debug("Right: ", d.right);
                    				d.visualizer.debug("First: ", d.first);
                    break;
                }
                case END_STATE: { // Конечное состояние
                    				d.visualizer.debug("index: ", d.index1);
                    				d.visualizer.debug("ans: ", d.ans);				
                    break;
                }
            }
        }
    }
}
