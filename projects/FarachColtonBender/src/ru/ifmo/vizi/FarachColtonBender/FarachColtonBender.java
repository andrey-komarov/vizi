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
        public int[] stack = null;

        /**
          * Размер стека. Внезапно.
          */
        public int stackSize = 0;

        /**
          * Счётчик цикла.
          */
        public int i = 0;

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
        private final int END_STATE = 7;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                7, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Start of cycle", 
                    "Cycle over elements to build cartesian tree", 
                    "Drop all elements above a[i]", 
                    "decrementStackSize", 
                    "push element to stack", 
                    "Cycle step", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Start of cycle 
                    0, // Cycle over elements to build cartesian tree 
                    0, // Drop all elements above a[i] 
                    -1, // decrementStackSize 
                    0, // push element to stack 
                    -1, // Cycle step 
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
                        stack.pushBoolean(false); 
                        state = 3; // Drop all elements above a[i]
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Drop all elements above a[i]
                    if (d.stackSize > 0 && d.array[d.i] > d.stack[d.stackSize - 1]) {
                        state = 4; // decrementStackSize
                    } else {
                        state = 5; // push element to stack
                    }
                    break;
                }
                case 4: { // decrementStackSize
                    stack.pushBoolean(true); 
                    state = 3; // Drop all elements above a[i]
                    break;
                }
                case 5: { // push element to stack
                    state = 6; // Cycle step
                    break;
                }
                case 6: { // Cycle step
                    stack.pushBoolean(true); 
                    state = 2; // Cycle over elements to build cartesian tree
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
                case 3: { // Drop all elements above a[i]
                    break;
                }
                case 4: { // decrementStackSize
                    startSection();
                    storeField(d, "stackSize");
                    				d.stackSize = d.stackSize - 1;
                    break;
                }
                case 5: { // push element to stack
                    startSection();
                    storeArray(d.stack, d.stackSize);
                    			d.stack[d.stackSize] = d.array[d.i];
                    storeField(d, "stackSize");
                    			d.stackSize = d.stackSize + 1;
                    break;
                }
                case 6: { // Cycle step
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
                case 3: { // Drop all elements above a[i]
                    break;
                }
                case 4: { // decrementStackSize
                    restoreSection();
                    break;
                }
                case 5: { // push element to stack
                    restoreSection();
                    break;
                }
                case 6: { // Cycle step
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
                        state = 6; // Cycle step
                    } else {
                        state = 1; // Start of cycle
                    }
                    break;
                }
                case 3: { // Drop all elements above a[i]
                    if (stack.popBoolean()) {
                        state = 4; // decrementStackSize
                    } else {
                        state = 2; // Cycle over elements to build cartesian tree
                    }
                    break;
                }
                case 4: { // decrementStackSize
                    state = 3; // Drop all elements above a[i]
                    break;
                }
                case 5: { // push element to stack
                    state = 3; // Drop all elements above a[i]
                    break;
                }
                case 6: { // Cycle step
                    state = 5; // push element to stack
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Cycle over elements to build cartesian tree
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
                case 3: { // Drop all elements above a[i]
                    if (d.stackSize > 0 && d.array[d.i] > d.stack[d.stackSize - 1]) {
                        comment = FarachColtonBender.this.getComment("Main.dropSmallElements.true"); 
                    } else {
                        comment = FarachColtonBender.this.getComment("Main.dropSmallElements.false"); 
                    }
                    args = new Object[]{new Integer(d.stackSize == 0 ? 0: (d.stack[d.stackSize - 1])), new Integer(d.array[d.i])}; 
                    break;
                }
                case 5: { // push element to stack
                    comment = FarachColtonBender.this.getComment("Main.pushElementToStack"); 
                    args = new Object[]{new Integer(d.array[d.i])}; 
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
                    break;
                }
                case 4: { // decrementStackSize
                    				d.visualizer.drawCartesianTree(d.i + 1, d.stackSize);
                    break;
                }
                case 5: { // push element to stack
                    			d.visualizer.drawCartesianTree(d.i + 1, d.stackSize);
                    break;
                }
                case END_STATE: { // Конечное состояние
                    break;
                }
            }
        }
    }
}
