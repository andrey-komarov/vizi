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
        private final int END_STATE = 39;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                39, // Номер конечного состояния 
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
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Start of cycle 
                    0, // Cycle over elements to build cartesian tree 
                    -1, // Backup stack size 
                    0, // Drop all elements above a[i] 
                    0, // decrementStackSize 
                    0, // check for new right son 
                    -1, // check for new right son (окончание) 
                    -1, // right son assignment 
                    0, // check for new left son 
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
                    0, // search for first occurence of number in index 
                    -1, //  
                    -1, //  (окончание) 
                    0, // First occurence of index[i] found 
                    -1, // index[i] already was used 
                    -1, // increment loop variable 
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
                        state = END_STATE; 
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
                case END_STATE: { // Начальное состояние
                    state = 33; // search for first occurence of number in index
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
                case END_STATE: { // Конечное состояние
                    break;
                }
            }
        }
    }
}
