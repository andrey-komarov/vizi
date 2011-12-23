package ru.ifmo.vizi.merge_sort;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class MergeSort extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public MergeSort(Locale locale) {
        super("ru.ifmo.vizi.merge_sort.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Массив, подлежащий сортировке.
          */
        public int[] a = new int[]{15, 14, 65, 24, 98, 73, 21, 7};

        /**
          * Массив для хранения левых границ сортируемых                              массивов (необходим для рекурсии).
          */
        public int[] l = new int[]{0, 0, 0, 0, 0, 0, 0};

        /**
          * Массив для хранения правых границ сортируемых                              массивов (необходим для рекурсии).
          */
        public int[] r = new int[]{0, 0, 0, 0, 0, 0, 0};

        /**
          * Переменная для хранения глубины рекурсии.
          */
        public int h = 0;

        /**
          * Экземпляр апплета.
          */
        public MergeSortVisualizer visualizer = null;

        /**
          * Вспомогательный массив (Процедура Merge).
          */
        public int[] Merge_b;

        /**
          * Указатель на текущий элемент                                   левой половины массива (Процедура Merge).
          */
        public int Merge_i;

        /**
          * Указатель на текущий элемент                                   правой половины массива (Процедура Merge).
          */
        public int Merge_j;

        /**
          * Счётчик цикла по всему массиву (Процедура Merge).
          */
        public int Merge_k;

        public String toString() {
            StringBuffer s = new StringBuffer();
            return s.toString();
        }
    }

    /**
      * Главный автомат.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 3;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                3, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Подготовка к запуску автомата сортировки массива", 
                    "Автомат, сортирующий часть массива (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Подготовка к запуску автомата сортировки массива 
                    CALL_AUTO_LEVEL, // Автомат, сортирующий часть массива (автомат) 
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
                    state = 1; // Подготовка к запуску автомата сортировки массива
                    break;
                }
                case 1: { // Подготовка к запуску автомата сортировки массива
                    state = 2; // Автомат, сортирующий часть массива (автомат)
                    break;
                }
                case 2: { // Автомат, сортирующий часть массива (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к запуску автомата сортировки массива
                    startSection();
                    storeField(d, "h");
                    d.h = -1;
                    storeArray(d.l, 0);
                    d.l[0] = 0;
                    storeArray(d.r, 0);
                    d.r[0] = d.a.length - 1;
                    break;
                }
                case 2: { // Автомат, сортирующий часть массива (автомат)
                    if (child == null) {
                        child = new MSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
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
                case 1: { // Подготовка к запуску автомата сортировки массива
                    restoreSection();
                    break;
                }
                case 2: { // Автомат, сортирующий часть массива (автомат)
                    if (child == null) {
                        child = new MSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Подготовка к запуску автомата сортировки массива
                    state = START_STATE; 
                    break;
                }
                case 2: { // Автомат, сортирующий часть массива (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Подготовка к запуску автомата сортировки массива
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Автомат, сортирующий часть массива (автомат)
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
                    comment = MergeSort.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 2: { // Автомат, сортирующий часть массива (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = MergeSort.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.hideStack();
                    d.visualizer.updateBrackets(-1, 0);
                    d.visualizer.updateMainArray(0, 0, d.a.length - 1, 0, 0, 0, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // Подготовка к запуску автомата сортировки массива
                    d.visualizer.hideStack();
                    d.visualizer.updateMainArray(0, 0, d.a.length - 1, 0, 0, 0, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // Автомат, сортирующий часть массива (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.hideStack();
                    d.visualizer.updateBrackets(-1, 1);
                    d.visualizer.updateMainArray(0, 0, d.a.length - 1, 9, 9, 9, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * Автомат, сортирующий часть массива.
      */
    private final class MSort extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 11;

        /**
          * Конструктор.
          */
        public MSort() {
            super( 
                "MSort", 
                0, // Номер начального состояния 
                11, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Подготовка к сортировке указанной части массива", 
                    "Проверяет длину сортируемой части массива", 
                    "Проверяет длину сортируемой части массива (окончание)", 
                    "Массив состоит из одного элемента", 
                    "Массив состоит из нескольких элементов.                                    Сортировка левой половины", 
                    "Автомат, сортирующий часть массива (автомат)", 
                    "Массив состоит из нескольких элементов.                                    Сортировка правой половины", 
                    "Автомат, сортирующий часть массива (автомат)", 
                    "Автомат, реализующий слияние массивов (автомат)", 
                    "Слияние завершено. Массив отсортирован.", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Подготовка к сортировке указанной части массива 
                    -1, // Проверяет длину сортируемой части массива 
                    -1, // Проверяет длину сортируемой части массива (окончание) 
                    0, // Массив состоит из одного элемента 
                    0, // Массив состоит из нескольких элементов.                                    Сортировка левой половины 
                    CALL_AUTO_LEVEL, // Автомат, сортирующий часть массива (автомат) 
                    0, // Массив состоит из нескольких элементов.                                    Сортировка правой половины 
                    CALL_AUTO_LEVEL, // Автомат, сортирующий часть массива (автомат) 
                    CALL_AUTO_LEVEL, // Автомат, реализующий слияние массивов (автомат) 
                    1, // Слияние завершено. Массив отсортирован. 
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
                    state = 1; // Подготовка к сортировке указанной части массива
                    break;
                }
                case 1: { // Подготовка к сортировке указанной части массива
                    state = 2; // Проверяет длину сортируемой части массива
                    break;
                }
                case 2: { // Проверяет длину сортируемой части массива
                    if (d.l[d.h] == d.r[d.h]) {
                        state = 4; // Массив состоит из одного элемента
                    } else {
                        state = 5; // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    }
                    break;
                }
                case 3: { // Проверяет длину сортируемой части массива (окончание)
                    state = END_STATE; 
                    break;
                }
                case 4: { // Массив состоит из одного элемента
                    stack.pushBoolean(true); 
                    state = 3; // Проверяет длину сортируемой части массива (окончание)
                    break;
                }
                case 5: { // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    state = 6; // Автомат, сортирующий часть массива (автомат)
                    break;
                }
                case 6: { // Автомат, сортирующий часть массива (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    }
                    break;
                }
                case 7: { // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    state = 8; // Автомат, сортирующий часть массива (автомат)
                    break;
                }
                case 8: { // Автомат, сортирующий часть массива (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 9; // Автомат, реализующий слияние массивов (автомат)
                    }
                    break;
                }
                case 9: { // Автомат, реализующий слияние массивов (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // Слияние завершено. Массив отсортирован.
                    }
                    break;
                }
                case 10: { // Слияние завершено. Массив отсортирован.
                    stack.pushBoolean(false); 
                    state = 3; // Проверяет длину сортируемой части массива (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к сортировке указанной части массива
                    startSection();
                    storeField(d, "h");
                    d.h = d.h + 1;
                    break;
                }
                case 2: { // Проверяет длину сортируемой части массива
                    break;
                }
                case 3: { // Проверяет длину сортируемой части массива (окончание)
                    break;
                }
                case 4: { // Массив состоит из одного элемента
                    startSection();
                    storeField(d, "h");
                    d.h = d.h - 1;
                    break;
                }
                case 5: { // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    startSection();
                    storeArray(d.l, d.h + 1);
                    d.l[d.h + 1] = d.l[d.h];
                    storeArray(d.r, d.h + 1);
                    d.r[d.h + 1] = (d.l[d.h] + d.r[d.h]) / 2;
                    break;
                }
                case 6: { // Автомат, сортирующий часть массива (автомат)
                    if (child == null) {
                        child = new MSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    startSection();
                    storeArray(d.l, d.h + 1);
                    d.l[d.h + 1] = (d.l[d.h] + d.r[d.h]) / 2 + 1;
                    storeArray(d.r, d.h + 1);
                    d.r[d.h + 1] = d.r[d.h];
                    break;
                }
                case 8: { // Автомат, сортирующий часть массива (автомат)
                    if (child == null) {
                        child = new MSort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // Автомат, реализующий слияние массивов (автомат)
                    if (child == null) {
                        child = new Merge(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // Слияние завершено. Массив отсортирован.
                    startSection();
                    storeField(d, "h");
                    d.h = d.h - 1;
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
                case 1: { // Подготовка к сортировке указанной части массива
                    restoreSection();
                    break;
                }
                case 2: { // Проверяет длину сортируемой части массива
                    break;
                }
                case 3: { // Проверяет длину сортируемой части массива (окончание)
                    break;
                }
                case 4: { // Массив состоит из одного элемента
                    restoreSection();
                    break;
                }
                case 5: { // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    restoreSection();
                    break;
                }
                case 6: { // Автомат, сортирующий часть массива (автомат)
                    if (child == null) {
                        child = new MSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    restoreSection();
                    break;
                }
                case 8: { // Автомат, сортирующий часть массива (автомат)
                    if (child == null) {
                        child = new MSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // Автомат, реализующий слияние массивов (автомат)
                    if (child == null) {
                        child = new Merge(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // Слияние завершено. Массив отсортирован.
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Подготовка к сортировке указанной части массива
                    state = START_STATE; 
                    break;
                }
                case 2: { // Проверяет длину сортируемой части массива
                    state = 1; // Подготовка к сортировке указанной части массива
                    break;
                }
                case 3: { // Проверяет длину сортируемой части массива (окончание)
                    if (stack.popBoolean()) {
                        state = 4; // Массив состоит из одного элемента
                    } else {
                        state = 10; // Слияние завершено. Массив отсортирован.
                    }
                    break;
                }
                case 4: { // Массив состоит из одного элемента
                    state = 2; // Проверяет длину сортируемой части массива
                    break;
                }
                case 5: { // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    state = 2; // Проверяет длину сортируемой части массива
                    break;
                }
                case 6: { // Автомат, сортирующий часть массива (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    }
                    break;
                }
                case 7: { // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    state = 6; // Автомат, сортирующий часть массива (автомат)
                    break;
                }
                case 8: { // Автомат, сортирующий часть массива (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    }
                    break;
                }
                case 9: { // Автомат, реализующий слияние массивов (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // Автомат, сортирующий часть массива (автомат)
                    }
                    break;
                }
                case 10: { // Слияние завершено. Массив отсортирован.
                    state = 9; // Автомат, реализующий слияние массивов (автомат)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Проверяет длину сортируемой части массива (окончание)
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
                case 1: { // Подготовка к сортировке указанной части массива
                    comment = MergeSort.this.getComment("MSort.StepPreparingForSortingArrayPart"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                             new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 4: { // Массив состоит из одного элемента
                    comment = MergeSort.this.getComment("MSort.StepOneElementInArrayPart"); 
                    args = new Object[]{new Integer(d.a[d.l[d.h + 1]]),                                     new Integer(d.l[d.h + 1] + 1)}; 
                    break;
                }
                case 5: { // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    comment = MergeSort.this.getComment("MSort.StepManyElementsLeftSide"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                                     new Integer(d.r[d.h + 1] + 1),                                     new Integer(d.r[d.h + 1] + 2),                                     new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 6: { // Автомат, сортирующий часть массива (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    comment = MergeSort.this.getComment("MSort.StepManyElementsRightSide"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                                     new Integer(d.l[d.h + 1] + 1),                                     new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 8: { // Автомат, сортирующий часть массива (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 9: { // Автомат, реализующий слияние массивов (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // Слияние завершено. Массив отсортирован.
                    comment = MergeSort.this.getComment("MSort.StepAfterMerging"); 
                    args = new Object[]{new Integer(d.l[d.h + 1] + 1),                                     new Integer(d.r[d.h + 1] + 1)}; 
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
                case 1: { // Подготовка к сортировке указанной части массива
                    d.visualizer.showStack(d.h, 0, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.l[d.h], d.r[d.h],
                                                 3, 3, 3, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 4: { // Массив состоит из одного элемента
                    d.visualizer.showStack(d.h + 1, 2, true);
                    d.visualizer.updateMainArray(d.l[d.h + 1], d.l[d.h + 1],
                                                 d.l[d.h + 1], 4, 4, 4, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 5: { // Массив состоит из нескольких элементов.                                    Сортировка левой половины
                    d.visualizer.showStack(d.h, 0, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.r[d.h + 1],
                                                 d.r[d.h], 1, 1, 2, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 6: { // Автомат, сортирующий часть массива (автомат)
                    child.drawState(); 
                    break;
                }
                case 7: { // Массив состоит из нескольких элементов.                                    Сортировка правой половины
                    d.visualizer.showStack(d.h, 1, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.l[d.h + 1] - 1,
                                                 d.r[d.h], 1, 1, 2, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 8: { // Автомат, сортирующий часть массива (автомат)
                    child.drawState(); 
                    break;
                }
                case 9: { // Автомат, реализующий слияние массивов (автомат)
                    child.drawState(); 
                    break;
                }
                case 10: { // Слияние завершено. Массив отсортирован.
                    d.visualizer.showStack(d.h + 1, 2, true);
                    d.visualizer.updateMainArray(d.l[d.h + 1], d.l[d.h + 1],
                                                 d.r[d.h + 1], 4, 4, 4, 0);
                    d.visualizer.hideAuxArrays(true);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * Автомат, реализующий слияние массивов.
      */
    private final class Merge extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 17;

        /**
          * Конструктор.
          */
        public Merge() {
            super( 
                "Merge", 
                0, // Номер начального состояния 
                17, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Подготовка к копорованию элементов", 
                    "Цикл копирования элементов", 
                    "Копирование элемента", 
                    "Подготовка к слиянию массивов", 
                    "Цикл слияния массивов", 
                    "Проверка длин массивов", 
                    "Проверка длин массивов (окончание)", 
                    "Проверка длины первого массива", 
                    "Проверка длины первого массива (окончание)", 
                    "Первый массив закончился", 
                    "Второй массив закончился", 
                    "Надо сравненить элементы массивов", 
                    "Сравнение элементов массивов", 
                    "Сравнение элементов массивов (окончание)", 
                    "Элемент второго массива меньше                                                элемента первого массива", 
                    "Элемент первого массива не                                                превосходит элемента второго                                                массива", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Подготовка к копорованию элементов 
                    -1, // Цикл копирования элементов 
                    -1, // Копирование элемента 
                    1, // Подготовка к слиянию массивов 
                    -1, // Цикл слияния массивов 
                    -1, // Проверка длин массивов 
                    -1, // Проверка длин массивов (окончание) 
                    -1, // Проверка длины первого массива 
                    -1, // Проверка длины первого массива (окончание) 
                    1, // Первый массив закончился 
                    1, // Второй массив закончился 
                    1, // Надо сравненить элементы массивов 
                    -1, // Сравнение элементов массивов 
                    -1, // Сравнение элементов массивов (окончание) 
                    1, // Элемент второго массива меньше                                                элемента первого массива 
                    1, // Элемент первого массива не                                                превосходит элемента второго                                                массива 
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
                    state = 1; // Подготовка к копорованию элементов
                    break;
                }
                case 1: { // Подготовка к копорованию элементов
                    stack.pushBoolean(false); 
                    state = 2; // Цикл копирования элементов
                    break;
                }
                case 2: { // Цикл копирования элементов
                    if (d.Merge_k <= d.r[d.h]) {
                        state = 3; // Копирование элемента
                    } else {
                        state = 4; // Подготовка к слиянию массивов
                    }
                    break;
                }
                case 3: { // Копирование элемента
                    stack.pushBoolean(true); 
                    state = 2; // Цикл копирования элементов
                    break;
                }
                case 4: { // Подготовка к слиянию массивов
                    stack.pushBoolean(false); 
                    state = 5; // Цикл слияния массивов
                    break;
                }
                case 5: { // Цикл слияния массивов
                    if (d.Merge_k <= d.r[d.h]) {
                        state = 6; // Проверка длин массивов
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // Проверка длин массивов
                    if ((d.Merge_i >= d.l[d.h + 1]) || (d.Merge_j > d.r[d.h])) {
                        state = 8; // Проверка длины первого массива
                    } else {
                        state = 12; // Надо сравненить элементы массивов
                    }
                    break;
                }
                case 7: { // Проверка длин массивов (окончание)
                    stack.pushBoolean(true); 
                    state = 5; // Цикл слияния массивов
                    break;
                }
                case 8: { // Проверка длины первого массива
                    if (d.Merge_i >= d.l[d.h + 1]) {
                        state = 10; // Первый массив закончился
                    } else {
                        state = 11; // Второй массив закончился
                    }
                    break;
                }
                case 9: { // Проверка длины первого массива (окончание)
                    stack.pushBoolean(true); 
                    state = 7; // Проверка длин массивов (окончание)
                    break;
                }
                case 10: { // Первый массив закончился
                    stack.pushBoolean(true); 
                    state = 9; // Проверка длины первого массива (окончание)
                    break;
                }
                case 11: { // Второй массив закончился
                    stack.pushBoolean(false); 
                    state = 9; // Проверка длины первого массива (окончание)
                    break;
                }
                case 12: { // Надо сравненить элементы массивов
                    state = 13; // Сравнение элементов массивов
                    break;
                }
                case 13: { // Сравнение элементов массивов
                    if (d.Merge_b[d.Merge_i] > d.Merge_b[d.Merge_j]) {
                        state = 15; // Элемент второго массива меньше                                                элемента первого массива
                    } else {
                        state = 16; // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    }
                    break;
                }
                case 14: { // Сравнение элементов массивов (окончание)
                    stack.pushBoolean(false); 
                    state = 7; // Проверка длин массивов (окончание)
                    break;
                }
                case 15: { // Элемент второго массива меньше                                                элемента первого массива
                    stack.pushBoolean(true); 
                    state = 14; // Сравнение элементов массивов (окончание)
                    break;
                }
                case 16: { // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    stack.pushBoolean(false); 
                    state = 14; // Сравнение элементов массивов (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Подготовка к копорованию элементов
                    startSection();
                    storeField(d, "Merge_k");
                    d.Merge_k = d.l[d.h];
                    storeField(d, "Merge_b");
                    d.Merge_b = (int[]) (d.a.clone());
                    break;
                }
                case 2: { // Цикл копирования элементов
                    break;
                }
                case 3: { // Копирование элемента
                    startSection();
                    storeArray(d.Merge_b, d.Merge_k);
                    d.Merge_b[d.Merge_k] = d.a[d.Merge_k];
                    storeField(d, "Merge_k");
                    d.Merge_k = d.Merge_k + 1;
                    break;
                }
                case 4: { // Подготовка к слиянию массивов
                    startSection();
                    storeField(d, "Merge_i");
                    d.Merge_i = d.l[d.h];
                    storeField(d, "Merge_j");
                    d.Merge_j = d.l[d.h + 1];
                    storeField(d, "Merge_k");
                    d.Merge_k = d.l[d.h];
                    break;
                }
                case 5: { // Цикл слияния массивов
                    break;
                }
                case 6: { // Проверка длин массивов
                    break;
                }
                case 7: { // Проверка длин массивов (окончание)
                    break;
                }
                case 8: { // Проверка длины первого массива
                    break;
                }
                case 9: { // Проверка длины первого массива (окончание)
                    break;
                }
                case 10: { // Первый массив закончился
                    startSection();
                    storeArray(d.a, d.Merge_k);
                    d.a[d.Merge_k] = d.Merge_b[d.Merge_j];
                    storeField(d, "Merge_j");
                    d.Merge_j = d.Merge_j + 1;
                    storeField(d, "Merge_k");
                    d.Merge_k = d.Merge_k + 1;
                    break;
                }
                case 11: { // Второй массив закончился
                    startSection();
                    storeArray(d.a, d.Merge_k);
                    d.a[d.Merge_k] = d.Merge_b[d.Merge_i];
                    storeField(d, "Merge_i");
                    d.Merge_i = d.Merge_i + 1;
                    storeField(d, "Merge_k");
                    d.Merge_k = d.Merge_k + 1;
                    break;
                }
                case 12: { // Надо сравненить элементы массивов
                    startSection();
                    break;
                }
                case 13: { // Сравнение элементов массивов
                    break;
                }
                case 14: { // Сравнение элементов массивов (окончание)
                    break;
                }
                case 15: { // Элемент второго массива меньше                                                элемента первого массива
                    startSection();
                    storeArray(d.a, d.Merge_k);
                    d.a[d.Merge_k] = d.Merge_b[d.Merge_j];
                    storeField(d, "Merge_j");
                    d.Merge_j = d.Merge_j + 1;
                    storeField(d, "Merge_k");
                    d.Merge_k = d.Merge_k + 1;
                    break;
                }
                case 16: { // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    startSection();
                    storeArray(d.a, d.Merge_k);
                    d.a[d.Merge_k] = d.Merge_b[d.Merge_i];
                    storeField(d, "Merge_i");
                    d.Merge_i = d.Merge_i + 1;
                    storeField(d, "Merge_k");
                    d.Merge_k = d.Merge_k + 1;
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
                case 1: { // Подготовка к копорованию элементов
                    restoreSection();
                    break;
                }
                case 2: { // Цикл копирования элементов
                    break;
                }
                case 3: { // Копирование элемента
                    restoreSection();
                    break;
                }
                case 4: { // Подготовка к слиянию массивов
                    restoreSection();
                    break;
                }
                case 5: { // Цикл слияния массивов
                    break;
                }
                case 6: { // Проверка длин массивов
                    break;
                }
                case 7: { // Проверка длин массивов (окончание)
                    break;
                }
                case 8: { // Проверка длины первого массива
                    break;
                }
                case 9: { // Проверка длины первого массива (окончание)
                    break;
                }
                case 10: { // Первый массив закончился
                    restoreSection();
                    break;
                }
                case 11: { // Второй массив закончился
                    restoreSection();
                    break;
                }
                case 12: { // Надо сравненить элементы массивов
                    restoreSection();
                    break;
                }
                case 13: { // Сравнение элементов массивов
                    break;
                }
                case 14: { // Сравнение элементов массивов (окончание)
                    break;
                }
                case 15: { // Элемент второго массива меньше                                                элемента первого массива
                    restoreSection();
                    break;
                }
                case 16: { // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Подготовка к копорованию элементов
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл копирования элементов
                    if (stack.popBoolean()) {
                        state = 3; // Копирование элемента
                    } else {
                        state = 1; // Подготовка к копорованию элементов
                    }
                    break;
                }
                case 3: { // Копирование элемента
                    state = 2; // Цикл копирования элементов
                    break;
                }
                case 4: { // Подготовка к слиянию массивов
                    state = 2; // Цикл копирования элементов
                    break;
                }
                case 5: { // Цикл слияния массивов
                    if (stack.popBoolean()) {
                        state = 7; // Проверка длин массивов (окончание)
                    } else {
                        state = 4; // Подготовка к слиянию массивов
                    }
                    break;
                }
                case 6: { // Проверка длин массивов
                    state = 5; // Цикл слияния массивов
                    break;
                }
                case 7: { // Проверка длин массивов (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Проверка длины первого массива (окончание)
                    } else {
                        state = 14; // Сравнение элементов массивов (окончание)
                    }
                    break;
                }
                case 8: { // Проверка длины первого массива
                    state = 6; // Проверка длин массивов
                    break;
                }
                case 9: { // Проверка длины первого массива (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Первый массив закончился
                    } else {
                        state = 11; // Второй массив закончился
                    }
                    break;
                }
                case 10: { // Первый массив закончился
                    state = 8; // Проверка длины первого массива
                    break;
                }
                case 11: { // Второй массив закончился
                    state = 8; // Проверка длины первого массива
                    break;
                }
                case 12: { // Надо сравненить элементы массивов
                    state = 6; // Проверка длин массивов
                    break;
                }
                case 13: { // Сравнение элементов массивов
                    state = 12; // Надо сравненить элементы массивов
                    break;
                }
                case 14: { // Сравнение элементов массивов (окончание)
                    if (stack.popBoolean()) {
                        state = 15; // Элемент второго массива меньше                                                элемента первого массива
                    } else {
                        state = 16; // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    }
                    break;
                }
                case 15: { // Элемент второго массива меньше                                                элемента первого массива
                    state = 13; // Сравнение элементов массивов
                    break;
                }
                case 16: { // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    state = 13; // Сравнение элементов массивов
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 5; // Цикл слияния массивов
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
                case 4: { // Подготовка к слиянию массивов
                    comment = MergeSort.this.getComment("Merge.StepPreparingForMerging"); 
                    args = new Object[]{new Integer(d.l[d.h] + 1),                             new Integer(d.l[d.h + 1]),                             new Integer(d.l[d.h + 1] + 1),                             new Integer(d.r[d.h] + 1)}; 
                    break;
                }
                case 10: { // Первый массив закончился
                    comment = MergeSort.this.getComment("Merge.StepFirstArrayIsEmpty"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_j - 1])}; 
                    break;
                }
                case 11: { // Второй массив закончился
                    comment = MergeSort.this.getComment("Merge.StepSecondArrayIsEmpty"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i - 1])}; 
                    break;
                }
                case 12: { // Надо сравненить элементы массивов
                    comment = MergeSort.this.getComment("Merge.StepBeforeCompareElements"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i]),                                         new Integer(d.Merge_b[d.Merge_j])}; 
                    break;
                }
                case 15: { // Элемент второго массива меньше                                                элемента первого массива
                    comment = MergeSort.this.getComment("Merge.StepSecondElementIsLower"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i]),                                                 new Integer(d.Merge_b[d.Merge_j - 1])}; 
                    break;
                }
                case 16: { // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    comment = MergeSort.this.getComment("Merge.StepFirstElementNotGreater"); 
                    args = new Object[]{new Integer(d.Merge_b[d.Merge_i - 1]),                                                 new Integer(d.Merge_b[d.Merge_j])}; 
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
                case 1: { // Подготовка к копорованию элементов
                    break;
                }
                case 3: { // Копирование элемента
                    break;
                }
                case 4: { // Подготовка к слиянию массивов
                    d.visualizer.showStack(d.h, 2, true);
                    d.visualizer.updateMainArray(d.l[d.h], d.l[d.h + 1] - 1,
                                                 d.r[d.h], 1, 1, 2, 0);
                    d.visualizer.showAuxArrays(d.l[d.h], d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.l[d.h] - 1, d.l[d.h + 1] - 1, 1,
                                                 0, 2, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 10: { // Первый массив закончился
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i, d.Merge_j - 1,
                                                 1, 0, 2, 3);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 11: { // Второй массив закончился
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i - 1, d.Merge_j,
                                                 1, 0, 3, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 12: { // Надо сравненить элементы массивов
                    d.visualizer.updateMainArray(d.l[d.h], d.Merge_k - 1,
                                                 d.r[d.h], 5, 5, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i, d.Merge_j,
                                                 1, 0, 2, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 15: { // Элемент второго массива меньше                                                элемента первого массива
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i, d.Merge_j - 1,
                                                 1, 0, 2, 3);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 16: { // Элемент первого массива не                                                превосходит элемента второго                                                массива
                    d.visualizer.updateMainArray(d.l[d.h],
                                                 d.Merge_k - 1,
                                                 d.r[d.h],
                                                 5, 6, 8, 7);
                    d.visualizer.showAuxArrays(d.l[d.h],
                                               d.l[d.h + 1] - 1,
                                               d.r[d.h]);
                    d.visualizer.updateAuxArrays(d.Merge_i - 1, d.Merge_j,
                                                 1, 0, 3, 2);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }
}
