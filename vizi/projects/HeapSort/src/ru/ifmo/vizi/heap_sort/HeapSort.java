package ru.ifmo.vizi.heap_sort;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class HeapSort extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public HeapSort(Locale locale) {
        super("ru.ifmo.vizi.heap_sort.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Массив для сортировки.
          */
        public int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        /**
          * Экземпляр апплета.
          */
        public HeapSortVisualizer visualizer = null;

        /**
          * Размер кучи.
          */
        public int heapSize = a.length - 1;

        /**
          * Переменная для передачи в Heapify.
          */
        public int iHeapify = 0;

        /**
          * Левый ребёнок (Процедура Heapify).
          */
        public int Heapify_l;

        /**
          * Правый ребёнок (Процедура Heapify).
          */
        public int Heapify_r;

        /**
          * Наибольший из детей (Процедура Heapify).
          */
        public int Heapify_largest;

        /**
          * Переменная для обмена (Процедура Heapify).
          */
        public int Heapify_swapTmp;

        /**
          * Переменная цикла (Процедура BuildHeap).
          */
        public int BuildHeap_i;

        /**
          * Переменная цикла (Процедура MakeHeapSort).
          */
        public int MakeHeapSort_i;

        /**
          * Переменная для обмена (Процедура MakeHeapSort).
          */
        public int MakeHeapSort_swapTmp;

        public String toString() {
            StringBuffer s = new StringBuffer();
            return s.toString();
        }
    }

    /**
      * Проcеивает элемент.
      */
    private final class Heapify extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 18;

        /**
          * Конструктор.
          */
        public Heapify() {
            super( 
                "Heapify", 
                0, // Номер начального состояния 
                18, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Показ детей", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Есть ребёнок больший родителя", 
                    "Родитель больше своего левого ребёнка", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Есть ребёнок больший родителя", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Меняем наибольшего", 
                    "Теперь следует просеять дальше", 
                    "Проcеивает элемент (автомат)", 
                    "Показ детей", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    0, // Инициализация 
                    0, // Условие 
                    -1, // Условие (окончание) 
                    -1, // Показ детей 
                    0, // Условие 
                    -1, // Условие (окончание) 
                    -1, // Есть ребёнок больший родителя 
                    -1, // Родитель больше своего левого ребёнка 
                    0, // Условие 
                    -1, // Условие (окончание) 
                    -1, // Есть ребёнок больший родителя 
                    0, // Условие 
                    -1, // Условие (окончание) 
                    1, // Меняем наибольшего 
                    1, // Теперь следует просеять дальше 
                    CALL_AUTO_LEVEL, // Проcеивает элемент (автомат) 
                    -1, // Показ детей 
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
                    state = 1; // Инициализация
                    break;
                }
                case 1: { // Инициализация
                    state = 2; // Условие
                    break;
                }
                case 2: { // Условие
                    if (d.Heapify_l <= d.heapSize) {
                        state = 4; // Показ детей
                    } else {
                        state = 17; // Показ детей
                    }
                    break;
                }
                case 3: { // Условие (окончание)
                    state = END_STATE; 
                    break;
                }
                case 4: { // Показ детей
                    state = 5; // Условие
                    break;
                }
                case 5: { // Условие
                    if (d.a[d.iHeapify] < d.a[d.Heapify_l]) {
                        state = 7; // Есть ребёнок больший родителя
                    } else {
                        state = 8; // Родитель больше своего левого ребёнка
                    }
                    break;
                }
                case 6: { // Условие (окончание)
                    state = 9; // Условие
                    break;
                }
                case 7: { // Есть ребёнок больший родителя
                    stack.pushBoolean(true); 
                    state = 6; // Условие (окончание)
                    break;
                }
                case 8: { // Родитель больше своего левого ребёнка
                    stack.pushBoolean(false); 
                    state = 6; // Условие (окончание)
                    break;
                }
                case 9: { // Условие
                    if (d.Heapify_r <= d.heapSize && d.a[d.Heapify_largest] < d.a[d.Heapify_r] ) {
                        state = 11; // Есть ребёнок больший родителя
                    } else {
                        stack.pushBoolean(false); 
                        state = 10; // Условие (окончание)
                    }
                    break;
                }
                case 10: { // Условие (окончание)
                    state = 12; // Условие
                    break;
                }
                case 11: { // Есть ребёнок больший родителя
                    stack.pushBoolean(true); 
                    state = 10; // Условие (окончание)
                    break;
                }
                case 12: { // Условие
                    if (d.Heapify_largest != d.iHeapify) {
                        state = 14; // Меняем наибольшего
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // Условие (окончание)
                    }
                    break;
                }
                case 13: { // Условие (окончание)
                    stack.pushBoolean(true); 
                    state = 3; // Условие (окончание)
                    break;
                }
                case 14: { // Меняем наибольшего
                    state = 15; // Теперь следует просеять дальше
                    break;
                }
                case 15: { // Теперь следует просеять дальше
                    state = 16; // Проcеивает элемент (автомат)
                    break;
                }
                case 16: { // Проcеивает элемент (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 13; // Условие (окончание)
                    }
                    break;
                }
                case 17: { // Показ детей
                    stack.pushBoolean(false); 
                    state = 3; // Условие (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    startSection();
                    storeField(d, "Heapify_l");
                    d.Heapify_l = d.iHeapify * 2 + 1;
                    storeField(d, "Heapify_r");
                    d.Heapify_r = d.iHeapify * 2 + 1 + 1;
                    break;
                }
                case 2: { // Условие
                    break;
                }
                case 3: { // Условие (окончание)
                    break;
                }
                case 4: { // Показ детей
                    startSection();
                    break;
                }
                case 5: { // Условие
                    break;
                }
                case 6: { // Условие (окончание)
                    break;
                }
                case 7: { // Есть ребёнок больший родителя
                    startSection();
                    storeField(d, "Heapify_largest");
                    d.Heapify_largest = d.Heapify_l;
                    break;
                }
                case 8: { // Родитель больше своего левого ребёнка
                    startSection();
                    storeField(d, "Heapify_largest");
                    d.Heapify_largest = d.iHeapify;
                    break;
                }
                case 9: { // Условие
                    break;
                }
                case 10: { // Условие (окончание)
                    break;
                }
                case 11: { // Есть ребёнок больший родителя
                    startSection();
                    storeField(d, "Heapify_largest");
                    d.Heapify_largest = d.Heapify_r;
                    break;
                }
                case 12: { // Условие
                    break;
                }
                case 13: { // Условие (окончание)
                    break;
                }
                case 14: { // Меняем наибольшего
                    startSection();
                    storeField(d, "Heapify_swapTmp");
                    d.Heapify_swapTmp = d.a[d.Heapify_largest];
                    storeArray(d.a, d.Heapify_largest);
                    d.a[d.Heapify_largest] = d.a[d.iHeapify];
                    storeArray(d.a, d.iHeapify);
                    d.a[d.iHeapify] = d.Heapify_swapTmp;
                    break;
                }
                case 15: { // Теперь следует просеять дальше
                    startSection();
                    storeField(d, "iHeapify");
                    d.iHeapify = d.Heapify_largest;
                    break;
                }
                case 16: { // Проcеивает элемент (автомат)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 17: { // Показ детей
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
                case 1: { // Инициализация
                    restoreSection();
                    break;
                }
                case 2: { // Условие
                    break;
                }
                case 3: { // Условие (окончание)
                    break;
                }
                case 4: { // Показ детей
                    restoreSection();
                    break;
                }
                case 5: { // Условие
                    break;
                }
                case 6: { // Условие (окончание)
                    break;
                }
                case 7: { // Есть ребёнок больший родителя
                    restoreSection();
                    break;
                }
                case 8: { // Родитель больше своего левого ребёнка
                    restoreSection();
                    break;
                }
                case 9: { // Условие
                    break;
                }
                case 10: { // Условие (окончание)
                    break;
                }
                case 11: { // Есть ребёнок больший родителя
                    restoreSection();
                    break;
                }
                case 12: { // Условие
                    break;
                }
                case 13: { // Условие (окончание)
                    break;
                }
                case 14: { // Меняем наибольшего
                    restoreSection();
                    break;
                }
                case 15: { // Теперь следует просеять дальше
                    restoreSection();
                    break;
                }
                case 16: { // Проcеивает элемент (автомат)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 17: { // Показ детей
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация
                    state = START_STATE; 
                    break;
                }
                case 2: { // Условие
                    state = 1; // Инициализация
                    break;
                }
                case 3: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 13; // Условие (окончание)
                    } else {
                        state = 17; // Показ детей
                    }
                    break;
                }
                case 4: { // Показ детей
                    state = 2; // Условие
                    break;
                }
                case 5: { // Условие
                    state = 4; // Показ детей
                    break;
                }
                case 6: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 7; // Есть ребёнок больший родителя
                    } else {
                        state = 8; // Родитель больше своего левого ребёнка
                    }
                    break;
                }
                case 7: { // Есть ребёнок больший родителя
                    state = 5; // Условие
                    break;
                }
                case 8: { // Родитель больше своего левого ребёнка
                    state = 5; // Условие
                    break;
                }
                case 9: { // Условие
                    state = 6; // Условие (окончание)
                    break;
                }
                case 10: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // Есть ребёнок больший родителя
                    } else {
                        state = 9; // Условие
                    }
                    break;
                }
                case 11: { // Есть ребёнок больший родителя
                    state = 9; // Условие
                    break;
                }
                case 12: { // Условие
                    state = 10; // Условие (окончание)
                    break;
                }
                case 13: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Проcеивает элемент (автомат)
                    } else {
                        state = 12; // Условие
                    }
                    break;
                }
                case 14: { // Меняем наибольшего
                    state = 12; // Условие
                    break;
                }
                case 15: { // Теперь следует просеять дальше
                    state = 14; // Меняем наибольшего
                    break;
                }
                case 16: { // Проcеивает элемент (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 15; // Теперь следует просеять дальше
                    }
                    break;
                }
                case 17: { // Показ детей
                    state = 2; // Условие
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Условие (окончание)
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
                    comment = HeapSort.this.getComment("Heapify.START_STATE"); 
                    break;
                }
                case 1: { // Инициализация
                    comment = HeapSort.this.getComment("Heapify.Initialization1"); 
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.a[d.iHeapify + 1 - 1])}; 
                    break;
                }
                case 2: { // Условие
                    if (d.Heapify_l <= d.heapSize) {
                        comment = HeapSort.this.getComment("Heapify.ifChildsCond.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.ifChildsCond.false"); 
                    }
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.Heapify_l + 1), new Integer(d.a[d.iHeapify + 1 - 1]), new Integer(d.a[( d.Heapify_l + 1 - 1 ) %  d.a.length])}; 
                    break;
                }
                case 5: { // Условие
                    if (d.a[d.iHeapify] < d.a[d.Heapify_l]) {
                        comment = HeapSort.this.getComment("Heapify.compareLeftChild.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.compareLeftChild.false"); 
                    }
                    args = new Object[]{new Integer(d.Heapify_l + 1), new Integer(d.iHeapify + 1), new Integer(d.a[d.Heapify_l + 1 - 1]), new Integer(d.a[d.iHeapify + 1 - 1])}; 
                    break;
                }
                case 9: { // Условие
                    if (d.Heapify_r <= d.heapSize && d.a[d.Heapify_largest] < d.a[d.Heapify_r] ) {
                        comment = HeapSort.this.getComment("Heapify.rightChildCompare.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.rightChildCompare.false"); 
                    }
                    args = new Object[]{new Integer(d.Heapify_r + 1), new Integer(d.Heapify_largest + 1), new Integer(d.a[(d.Heapify_r + 1 - 1) % d.a.length]), new Integer(d.a[d.Heapify_largest + 1 - 1])}; 
                    break;
                }
                case 12: { // Условие
                    if (d.Heapify_largest != d.iHeapify) {
                        comment = HeapSort.this.getComment("Heapify.isLargest.true"); 
                    } else {
                        comment = HeapSort.this.getComment("Heapify.isLargest.false"); 
                    }
                    args = new Object[]{new Integer(d.Heapify_largest + 1), new Integer(d.iHeapify + 1),new Integer(d.a[d.Heapify_largest + 1 - 1]), new Integer(d.a[d.iHeapify + 1 - 1])}; 
                    break;
                }
                case 14: { // Меняем наибольшего
                    comment = HeapSort.this.getComment("Heapify.swapLargest"); 
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.Heapify_largest + 1), new Integer(d.a[d.iHeapify + 1 - 1]), new Integer(d.a[d.Heapify_largest + 1 - 1])}; 
                    break;
                }
                case 15: { // Теперь следует просеять дальше
                    comment = HeapSort.this.getComment("Heapify.proccedHeapify"); 
                    args = new Object[]{new Integer(d.Heapify_largest + 1), new Integer(d.a[d.Heapify_largest + 1 - 1])}; 
                    break;
                }
                case 16: { // Проcеивает элемент (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = HeapSort.this.getComment("Heapify.END_STATE"); 
                    args = new Object[]{new Integer(d.iHeapify + 1), new Integer(d.a[d.iHeapify + 1 - 1])}; 
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
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // Инициализация
                    d.visualizer.updateAll(d.iHeapify, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // Условие
                    d.visualizer.updateAll(d.iHeapify, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 4: { // Показ детей
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 5: { // Условие
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 7: { // Есть ребёнок больший родителя
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 8: { // Родитель больше своего левого ребёнка
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_l, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 9: { // Условие
                    d.visualizer.updateAll(d.Heapify_largest, d.Heapify_r, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 11: { // Есть ребёнок больший родителя
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_r, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 12: { // Условие
                    d.visualizer.updateAll(d.Heapify_largest, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 14: { // Меняем наибольшего
                    d.visualizer.updateAll(d.iHeapify, d.Heapify_largest, 2, d.heapSize, d.Heapify_largest - 1);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 15: { // Теперь следует просеять дальше
                    d.visualizer.updateAll(d.Heapify_largest, d.Heapify_largest, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 16: { // Проcеивает элемент (автомат)
                    child.drawState(); 
                    break;
                }
                case 17: { // Показ детей
                    d.visualizer.updateAll(d.iHeapify, d.iHeapify, 1, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * Превращает массив в кучу.
      */
    private final class BuildHeap extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 10;

        /**
          * Конструктор.
          */
        public BuildHeap() {
            super( 
                "BuildHeap", 
                0, // Номер начального состояния 
                10, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "bHeapRaport", 
                    "Присваивает значение размера кучи", 
                    "Объясняет почему начинаем с середины", 
                    "Начало цикла", 
                    "Цикл", 
                    "Присвоить значение переменной автомата Heapify", 
                    "Проcеивает элемент (автомат)", 
                    "Декремент", 
                    "heapRaport", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    1, // bHeapRaport 
                    1, // Присваивает значение размера кучи 
                    1, // Объясняет почему начинаем с середины 
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // Присвоить значение переменной автомата Heapify 
                    CALL_AUTO_LEVEL, // Проcеивает элемент (автомат) 
                    -1, // Декремент 
                    1, // heapRaport 
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
                    state = 1; // bHeapRaport
                    break;
                }
                case 1: { // bHeapRaport
                    state = 2; // Присваивает значение размера кучи
                    break;
                }
                case 2: { // Присваивает значение размера кучи
                    state = 3; // Объясняет почему начинаем с середины
                    break;
                }
                case 3: { // Объясняет почему начинаем с середины
                    state = 4; // Начало цикла
                    break;
                }
                case 4: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 5; // Цикл
                    break;
                }
                case 5: { // Цикл
                    if (0 <= d.BuildHeap_i) {
                        state = 6; // Присвоить значение переменной автомата Heapify
                    } else {
                        state = 9; // heapRaport
                    }
                    break;
                }
                case 6: { // Присвоить значение переменной автомата Heapify
                    state = 7; // Проcеивает элемент (автомат)
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // Декремент
                    }
                    break;
                }
                case 8: { // Декремент
                    stack.pushBoolean(true); 
                    state = 5; // Цикл
                    break;
                }
                case 9: { // heapRaport
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // bHeapRaport
                    startSection();
                    break;
                }
                case 2: { // Присваивает значение размера кучи
                    startSection();
                    storeField(d, "heapSize");
                    d.heapSize = d.a.length - 1;
                    break;
                }
                case 3: { // Объясняет почему начинаем с середины
                    startSection();
                    break;
                }
                case 4: { // Начало цикла
                    startSection();
                    storeField(d, "BuildHeap_i");
                    d.BuildHeap_i = d.a.length / 2 - 1;
                    break;
                }
                case 5: { // Цикл
                    break;
                }
                case 6: { // Присвоить значение переменной автомата Heapify
                    startSection();
                    storeField(d, "iHeapify");
                    d.iHeapify = d.BuildHeap_i;
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Декремент
                    startSection();
                    storeField(d, "BuildHeap_i");
                    d.BuildHeap_i = d.BuildHeap_i - 1;
                    break;
                }
                case 9: { // heapRaport
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
                case 1: { // bHeapRaport
                    restoreSection();
                    break;
                }
                case 2: { // Присваивает значение размера кучи
                    restoreSection();
                    break;
                }
                case 3: { // Объясняет почему начинаем с середины
                    restoreSection();
                    break;
                }
                case 4: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 5: { // Цикл
                    break;
                }
                case 6: { // Присвоить значение переменной автомата Heapify
                    restoreSection();
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // Декремент
                    restoreSection();
                    break;
                }
                case 9: { // heapRaport
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // bHeapRaport
                    state = START_STATE; 
                    break;
                }
                case 2: { // Присваивает значение размера кучи
                    state = 1; // bHeapRaport
                    break;
                }
                case 3: { // Объясняет почему начинаем с середины
                    state = 2; // Присваивает значение размера кучи
                    break;
                }
                case 4: { // Начало цикла
                    state = 3; // Объясняет почему начинаем с середины
                    break;
                }
                case 5: { // Цикл
                    if (stack.popBoolean()) {
                        state = 8; // Декремент
                    } else {
                        state = 4; // Начало цикла
                    }
                    break;
                }
                case 6: { // Присвоить значение переменной автомата Heapify
                    state = 5; // Цикл
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Присвоить значение переменной автомата Heapify
                    }
                    break;
                }
                case 8: { // Декремент
                    state = 7; // Проcеивает элемент (автомат)
                    break;
                }
                case 9: { // heapRaport
                    state = 5; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 9; // heapRaport
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
                    comment = HeapSort.this.getComment("BuildHeap.START_STATE"); 
                    break;
                }
                case 1: { // bHeapRaport
                    comment = HeapSort.this.getComment("BuildHeap.buildHeapRaport"); 
                    break;
                }
                case 2: { // Присваивает значение размера кучи
                    comment = HeapSort.this.getComment("BuildHeap.SetHeapSize"); 
                    break;
                }
                case 3: { // Объясняет почему начинаем с середины
                    comment = HeapSort.this.getComment("BuildHeap.DescribeWhyHalf"); 
                    args = new Object[]{new Integer(d.a.length / 2 - 1 + 1), new Integer(d.a[d.a.length / 2 - 1 + 1 - 1])}; 
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 9: { // heapRaport
                    comment = HeapSort.this.getComment("BuildHeap.HeapAlready"); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = HeapSort.this.getComment("BuildHeap.END_STATE"); 
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
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // bHeapRaport
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 2: { // Присваивает значение размера кучи
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    child.drawState(); 
                    break;
                }
                case 9: { // heapRaport
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.updateAll(0, 0, 0, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * Сортирует массив кучей.
      */
    private final class MakeHeapSort extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 10;

        /**
          * Конструктор.
          */
        public MakeHeapSort() {
            super( 
                "MakeHeapSort", 
                0, // Номер начального состояния 
                10, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Превращает массив в кучу (автомат)", 
                    "Начало цикла", 
                    "Цикл", 
                    "Перемещаем корень в конец", 
                    "Уменьшаем размер кучи", 
                    "Присвоить значени переменной автомата Heapify", 
                    "Проcеивает элемент (автомат)", 
                    "Декремент", 
                    "makes all end", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Превращает массив в кучу (автомат) 
                    -1, // Начало цикла 
                    -1, // Цикл 
                    1, // Перемещаем корень в конец 
                    0, // Уменьшаем размер кучи 
                    -1, // Присвоить значени переменной автомата Heapify 
                    CALL_AUTO_LEVEL, // Проcеивает элемент (автомат) 
                    -1, // Декремент 
                    -1, // makes all end 
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
                    state = 1; // Превращает массив в кучу (автомат)
                    break;
                }
                case 1: { // Превращает массив в кучу (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // Начало цикла
                    }
                    break;
                }
                case 2: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 3; // Цикл
                    break;
                }
                case 3: { // Цикл
                    if (0 < d.MakeHeapSort_i) {
                        state = 4; // Перемещаем корень в конец
                    } else {
                        state = 9; // makes all end
                    }
                    break;
                }
                case 4: { // Перемещаем корень в конец
                    state = 5; // Уменьшаем размер кучи
                    break;
                }
                case 5: { // Уменьшаем размер кучи
                    state = 6; // Присвоить значени переменной автомата Heapify
                    break;
                }
                case 6: { // Присвоить значени переменной автомата Heapify
                    state = 7; // Проcеивает элемент (автомат)
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // Декремент
                    }
                    break;
                }
                case 8: { // Декремент
                    stack.pushBoolean(true); 
                    state = 3; // Цикл
                    break;
                }
                case 9: { // makes all end
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Превращает массив в кучу (автомат)
                    if (child == null) {
                        child = new BuildHeap(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Начало цикла
                    startSection();
                    storeField(d, "MakeHeapSort_i");
                    d.MakeHeapSort_i = d.a.length - 1;
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Перемещаем корень в конец
                    startSection();
                    storeField(d, "MakeHeapSort_swapTmp");
                    d.MakeHeapSort_swapTmp = d.a[0];
                    storeArray(d.a, 0);
                    d.a[0] = d.a[d.MakeHeapSort_i];
                    storeArray(d.a, d.MakeHeapSort_i);
                    d.a[d.MakeHeapSort_i] = d.MakeHeapSort_swapTmp;
                    break;
                }
                case 5: { // Уменьшаем размер кучи
                    startSection();
                    storeField(d, "heapSize");
                    d.heapSize = d.heapSize - 1;
                    break;
                }
                case 6: { // Присвоить значени переменной автомата Heapify
                    startSection();
                    storeField(d, "iHeapify");
                    d.iHeapify = 0;
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Декремент
                    startSection();
                    storeField(d, "MakeHeapSort_i");
                    d.MakeHeapSort_i = d.MakeHeapSort_i - 1;
                    break;
                }
                case 9: { // makes all end
                    startSection();
                    d.heapSize = -1;
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
                case 1: { // Превращает массив в кучу (автомат)
                    if (child == null) {
                        child = new BuildHeap(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Перемещаем корень в конец
                    restoreSection();
                    break;
                }
                case 5: { // Уменьшаем размер кучи
                    restoreSection();
                    break;
                }
                case 6: { // Присвоить значени переменной автомата Heapify
                    restoreSection();
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child == null) {
                        child = new Heapify(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // Декремент
                    restoreSection();
                    break;
                }
                case 9: { // makes all end
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Превращает массив в кучу (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Начало цикла
                    state = 1; // Превращает массив в кучу (автомат)
                    break;
                }
                case 3: { // Цикл
                    if (stack.popBoolean()) {
                        state = 8; // Декремент
                    } else {
                        state = 2; // Начало цикла
                    }
                    break;
                }
                case 4: { // Перемещаем корень в конец
                    state = 3; // Цикл
                    break;
                }
                case 5: { // Уменьшаем размер кучи
                    state = 4; // Перемещаем корень в конец
                    break;
                }
                case 6: { // Присвоить значени переменной автомата Heapify
                    state = 5; // Уменьшаем размер кучи
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Присвоить значени переменной автомата Heapify
                    }
                    break;
                }
                case 8: { // Декремент
                    state = 7; // Проcеивает элемент (автомат)
                    break;
                }
                case 9: { // makes all end
                    state = 3; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 9; // makes all end
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
                    comment = HeapSort.this.getComment("MakeHeapSort.START_STATE"); 
                    break;
                }
                case 1: { // Превращает массив в кучу (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 4: { // Перемещаем корень в конец
                    comment = HeapSort.this.getComment("MakeHeapSort.swapFirstNLast"); 
                    break;
                }
                case 5: { // Уменьшаем размер кучи
                    comment = HeapSort.this.getComment("MakeHeapSort.decHeapSize"); 
                    args = new Object[]{new Integer(d.heapSize + 1 + 1), new Integer(d.a[d.iHeapify + 1 + 1 - 1])}; 
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = HeapSort.this.getComment("MakeHeapSort.END_STATE"); 
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
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // Превращает массив в кучу (автомат)
                    child.drawState(); 
                    break;
                }
                case 4: { // Перемещаем корень в конец
                    d.visualizer.updateAll(d.MakeHeapSort_i, 0, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 5: { // Уменьшаем размер кучи
                    d.visualizer.updateAll(0, 0, 2, d.heapSize);
                    d.visualizer.stopEditMode();
                    break;
                }
                case 7: { // Проcеивает элемент (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.updateAll(0, 0, 3, -1);
                    d.visualizer.stopEditMode();
                    break;
                }
            }
        }
    }

    /**
      * Сортирует массив натуральных чисел.
      */
    private final class Main extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 2;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                2, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Сортирует массив кучей (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Сортирует массив кучей (автомат) 
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
                    state = 1; // Сортирует массив кучей (автомат)
                    break;
                }
                case 1: { // Сортирует массив кучей (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = END_STATE; 
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Сортирует массив кучей (автомат)
                    if (child == null) {
                        child = new MakeHeapSort(); 
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
                case 1: { // Сортирует массив кучей (автомат)
                    if (child == null) {
                        child = new MakeHeapSort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Сортирует массив кучей (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 1; // Сортирует массив кучей (автомат)
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
                    comment = HeapSort.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Сортирует массив кучей (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = HeapSort.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.updateAll(0, 0, 0);
                    d.visualizer.makeAllStart();
                    d.visualizer.stopEditMode();
                    break;
                }
                case 1: { // Сортирует массив кучей (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.stopEditMode();
                    d.visualizer.updateAll(0, 0, 3, -1);
                    break;
                }
            }
        }
    }
}
