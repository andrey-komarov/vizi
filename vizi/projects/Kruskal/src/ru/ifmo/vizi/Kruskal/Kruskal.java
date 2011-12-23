package ru.ifmo.vizi.Kruskal;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Kruskal extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public Kruskal(Locale locale) {
        super("ru.ifmo.vizi.Kruskal.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Массив левых концов ребер.
          */
        public int[] l = new int[4];

        /**
          * Массив правых концов ребер.
          */
        public int[] r = new int[4];

        /**
          * Массив весов ребер.
          */
        public int[] weights = new int[4];

        /**
          * Текущее ребро.
          */
        public int edgeMin = 0;

        /**
          * Цвета вершин.
          */
        public int[] color = new int[5];

        /**
          * Экземпляр визуализатора.
          */
        public KruskalVisualizer visualizer = null;

        /**
          * Переменная цикла (Процедура Main).
          */
        public int Main_i;

        /**
          * Количество выбранных ребер (Процедура Main).
          */
        public int Main_m;

        /**
          * Переменная цикла (Процедура Sort).
          */
        public int Sort_i;

        /**
          * Переменная цикла 2 (Процедура Sort).
          */
        public int Sort_j;

        /**
          * Временная переменная (Процедура Sort).
          */
        public int Sort_temp;

        /**
          * Временная переменная (Процедура Join).
          */
        public int Join_temp;

        /**
          * Переменная цикла (Процедура Join).
          */
        public int Join_i;

        public String toString() {
            return("");
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
        private final int END_STATE = 14;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                14, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Сортировка ребер по весам (автомат)", 
                    "Инициализация цикла", 
                    "Инициализация системы непересекающихся множеств", 
                    "Инициализация вершины", 
                    "Инициализация количества выбранных ребер нулем", 
                    "Добавление ребер в каркас", 
                    "Проверка ребра", 
                    "Проверка ребра (окончание)", 
                    "Слияние компонент связности (автомат)", 
                    "Добавление ребра", 
                    "Переход к следующему", 
                    "найдено ли дерево", 
                    "найдено ли дерево (окончание)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Сортировка ребер по весам (автомат) 
                    0, // Инициализация цикла 
                    -1, // Инициализация системы непересекающихся множеств 
                    -1, // Инициализация вершины 
                    -1, // Инициализация количества выбранных ребер нулем 
                    -1, // Добавление ребер в каркас 
                    0, // Проверка ребра 
                    -1, // Проверка ребра (окончание) 
                    CALL_AUTO_LEVEL, // Слияние компонент связности (автомат) 
                    0, // Добавление ребра 
                    -1, // Переход к следующему 
                    0, // найдено ли дерево 
                    -1, // найдено ли дерево (окончание) 
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
                    state = 1; // Сортировка ребер по весам (автомат)
                    break;
                }
                case 1: { // Сортировка ребер по весам (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // Инициализация цикла
                    }
                    break;
                }
                case 2: { // Инициализация цикла
                    stack.pushBoolean(false); 
                    state = 3; // Инициализация системы непересекающихся множеств
                    break;
                }
                case 3: { // Инициализация системы непересекающихся множеств
                    if (d.Main_i < d.color.length) {
                        state = 4; // Инициализация вершины
                    } else {
                        state = 5; // Инициализация количества выбранных ребер нулем
                    }
                    break;
                }
                case 4: { // Инициализация вершины
                    stack.pushBoolean(true); 
                    state = 3; // Инициализация системы непересекающихся множеств
                    break;
                }
                case 5: { // Инициализация количества выбранных ребер нулем
                    stack.pushBoolean(false); 
                    state = 6; // Добавление ребер в каркас
                    break;
                }
                case 6: { // Добавление ребер в каркас
                    if (d.edgeMin < d.l.length) {
                        state = 7; // Проверка ребра
                    } else {
                        state = 12; // найдено ли дерево
                    }
                    break;
                }
                case 7: { // Проверка ребра
                    if (d.color[d.l[d.edgeMin]] != d.color[d.r[d.edgeMin]]) {
                        state = 9; // Слияние компонент связности (автомат)
                    } else {
                        stack.pushBoolean(false); 
                        state = 8; // Проверка ребра (окончание)
                    }
                    break;
                }
                case 8: { // Проверка ребра (окончание)
                    state = 11; // Переход к следующему
                    break;
                }
                case 9: { // Слияние компонент связности (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // Добавление ребра
                    }
                    break;
                }
                case 10: { // Добавление ребра
                    stack.pushBoolean(true); 
                    state = 8; // Проверка ребра (окончание)
                    break;
                }
                case 11: { // Переход к следующему
                    stack.pushBoolean(true); 
                    state = 6; // Добавление ребер в каркас
                    break;
                }
                case 12: { // найдено ли дерево
                    if (d.Main_m == d.color.length - 1) {
                        stack.pushBoolean(true); 
                        state = 13; // найдено ли дерево (окончание)
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // найдено ли дерево (окончание)
                    }
                    break;
                }
                case 13: { // найдено ли дерево (окончание)
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Сортировка ребер по весам (автомат)
                    if (child == null) {
                        child = new Sort(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Инициализация цикла
                    startSection();
                    storeField(d, "Main_i");
                    d.Main_i = 0;
                    break;
                }
                case 3: { // Инициализация системы непересекающихся множеств
                    break;
                }
                case 4: { // Инициализация вершины
                    startSection();
                    storeArray(d.color, d.Main_i);
                    d.color[d.Main_i] = d.Main_i;
                    storeField(d, "Main_i");
                    d.Main_i = d.Main_i + 1;
                    break;
                }
                case 5: { // Инициализация количества выбранных ребер нулем
                    startSection();
                    storeField(d, "Main_m");
                    d.Main_m = 0;
                    storeField(d, "edgeMin");
                    d.edgeMin = 0;
                    break;
                }
                case 6: { // Добавление ребер в каркас
                    break;
                }
                case 7: { // Проверка ребра
                    break;
                }
                case 8: { // Проверка ребра (окончание)
                    break;
                }
                case 9: { // Слияние компонент связности (автомат)
                    if (child == null) {
                        child = new Join(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // Добавление ребра
                    startSection();
                    storeField(d, "Main_m");
                    d.Main_m = d.Main_m + 1;
                    break;
                }
                case 11: { // Переход к следующему
                    startSection();
                    storeField(d, "edgeMin");
                    d.edgeMin = d.edgeMin + 1;
                    break;
                }
                case 12: { // найдено ли дерево
                    break;
                }
                case 13: { // найдено ли дерево (окончание)
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
                case 1: { // Сортировка ребер по весам (автомат)
                    if (child == null) {
                        child = new Sort(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // Инициализация цикла
                    restoreSection();
                    break;
                }
                case 3: { // Инициализация системы непересекающихся множеств
                    break;
                }
                case 4: { // Инициализация вершины
                    restoreSection();
                    break;
                }
                case 5: { // Инициализация количества выбранных ребер нулем
                    restoreSection();
                    break;
                }
                case 6: { // Добавление ребер в каркас
                    break;
                }
                case 7: { // Проверка ребра
                    break;
                }
                case 8: { // Проверка ребра (окончание)
                    break;
                }
                case 9: { // Слияние компонент связности (автомат)
                    if (child == null) {
                        child = new Join(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // Добавление ребра
                    restoreSection();
                    break;
                }
                case 11: { // Переход к следующему
                    restoreSection();
                    break;
                }
                case 12: { // найдено ли дерево
                    break;
                }
                case 13: { // найдено ли дерево (окончание)
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Сортировка ребер по весам (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Инициализация цикла
                    state = 1; // Сортировка ребер по весам (автомат)
                    break;
                }
                case 3: { // Инициализация системы непересекающихся множеств
                    if (stack.popBoolean()) {
                        state = 4; // Инициализация вершины
                    } else {
                        state = 2; // Инициализация цикла
                    }
                    break;
                }
                case 4: { // Инициализация вершины
                    state = 3; // Инициализация системы непересекающихся множеств
                    break;
                }
                case 5: { // Инициализация количества выбранных ребер нулем
                    state = 3; // Инициализация системы непересекающихся множеств
                    break;
                }
                case 6: { // Добавление ребер в каркас
                    if (stack.popBoolean()) {
                        state = 11; // Переход к следующему
                    } else {
                        state = 5; // Инициализация количества выбранных ребер нулем
                    }
                    break;
                }
                case 7: { // Проверка ребра
                    state = 6; // Добавление ребер в каркас
                    break;
                }
                case 8: { // Проверка ребра (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Добавление ребра
                    } else {
                        state = 7; // Проверка ребра
                    }
                    break;
                }
                case 9: { // Слияние компонент связности (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // Проверка ребра
                    }
                    break;
                }
                case 10: { // Добавление ребра
                    state = 9; // Слияние компонент связности (автомат)
                    break;
                }
                case 11: { // Переход к следующему
                    state = 8; // Проверка ребра (окончание)
                    break;
                }
                case 12: { // найдено ли дерево
                    state = 6; // Добавление ребер в каркас
                    break;
                }
                case 13: { // найдено ли дерево (окончание)
                    if (stack.popBoolean()) {
                        state = 12; // найдено ли дерево
                    } else {
                        state = 12; // найдено ли дерево
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 13; // найдено ли дерево (окончание)
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
                    comment = Kruskal.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Сортировка ребер по весам (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 2: { // Инициализация цикла
                    comment = Kruskal.this.getComment("Main.LoopInit"); 
                    break;
                }
                case 7: { // Проверка ребра
                    if (d.color[d.l[d.edgeMin]] != d.color[d.r[d.edgeMin]]) {
                        comment = Kruskal.this.getComment("Main.cond.true"); 
                    } else {
                        comment = Kruskal.this.getComment("Main.cond.false"); 
                    }
                    break;
                }
                case 9: { // Слияние компонент связности (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // Добавление ребра
                    comment = Kruskal.this.getComment("Main.AddEdge"); 
                    break;
                }
                case 12: { // найдено ли дерево
                    if (d.Main_m == d.color.length - 1) {
                        comment = Kruskal.this.getComment("Main.isFound.true"); 
                    } else {
                        comment = Kruskal.this.getComment("Main.isFound.false"); 
                    }
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = Kruskal.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.showWeights(true);
                    d.visualizer.updateView(-1, 0);
                    break;
                }
                case 1: { // Сортировка ребер по весам (автомат)
                    child.drawState(); 
                    break;
                }
                case 2: { // Инициализация цикла
                    d.visualizer.showWeights(false);    
                    d.visualizer.updateView(-1, 0);
                    break;
                }
                case 7: { // Проверка ребра
                    d.visualizer.updateView(d.edgeMin, 3);
                    break;
                }
                case 9: { // Слияние компонент связности (автомат)
                    child.drawState(); 
                    break;
                }
                case 10: { // Добавление ребра
                    d.visualizer.updateView(d.edgeMin, 1);
                    break;
                }
                case 12: { // найдено ли дерево
                    d.visualizer.updateView(1000, 0);
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.updateView(1000, 0);
                    break;
                }
            }
        }
    }

    /**
      * Сортировка ребер по весам.
      */
    private final class Sort extends BaseAutomata implements Automata {
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
        public Sort() {
            super( 
                "Sort", 
                0, // Номер начального состояния 
                11, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла", 
                    "Цикл по i", 
                    "init", 
                    "Цикл по j", 
                    "Условие перестановки", 
                    "Условие перестановки (окончание)", 
                    "Перестановка", 
                    "Инкремент j", 
                    "Инкремент i", 
                    "Показ отсортированного списка", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 
                    -1, // Цикл по i 
                    -1, // init 
                    -1, // Цикл по j 
                    -1, // Условие перестановки 
                    -1, // Условие перестановки (окончание) 
                    -1, // Перестановка 
                    -1, // Инкремент j 
                    -1, // Инкремент i 
                    0, // Показ отсортированного списка 
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
                    state = 1; // Инициализация цикла
                    break;
                }
                case 1: { // Инициализация цикла
                    stack.pushBoolean(false); 
                    state = 2; // Цикл по i
                    break;
                }
                case 2: { // Цикл по i
                    if (d.Sort_i < d.l.length) {
                        state = 3; // init
                    } else {
                        state = 10; // Показ отсортированного списка
                    }
                    break;
                }
                case 3: { // init
                    stack.pushBoolean(false); 
                    state = 4; // Цикл по j
                    break;
                }
                case 4: { // Цикл по j
                    if (d.Sort_j < d.l.length) {
                        state = 5; // Условие перестановки
                    } else {
                        state = 9; // Инкремент i
                    }
                    break;
                }
                case 5: { // Условие перестановки
                    if (d.weights[d.Sort_i] > d.weights[d.Sort_j]) {
                        state = 7; // Перестановка
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // Условие перестановки (окончание)
                    }
                    break;
                }
                case 6: { // Условие перестановки (окончание)
                    state = 8; // Инкремент j
                    break;
                }
                case 7: { // Перестановка
                    stack.pushBoolean(true); 
                    state = 6; // Условие перестановки (окончание)
                    break;
                }
                case 8: { // Инкремент j
                    stack.pushBoolean(true); 
                    state = 4; // Цикл по j
                    break;
                }
                case 9: { // Инкремент i
                    stack.pushBoolean(true); 
                    state = 2; // Цикл по i
                    break;
                }
                case 10: { // Показ отсортированного списка
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация цикла
                    startSection();
                    storeField(d, "Sort_i");
                    d.Sort_i = 0;
                    break;
                }
                case 2: { // Цикл по i
                    break;
                }
                case 3: { // init
                    startSection();
                    storeField(d, "Sort_j");
                    d.Sort_j = d.Sort_i + 1;
                    break;
                }
                case 4: { // Цикл по j
                    break;
                }
                case 5: { // Условие перестановки
                    break;
                }
                case 6: { // Условие перестановки (окончание)
                    break;
                }
                case 7: { // Перестановка
                    startSection();
                    storeField(d, "Sort_temp");
                    d.Sort_temp = d.weights[d.Sort_i];
                    storeArray(d.weights, d.Sort_i);
                    d.weights[d.Sort_i] = d.weights[d.Sort_j];
                    storeArray(d.weights, d.Sort_j);
                    d.weights[d.Sort_j] = d.Sort_temp;
                    storeField(d, "Sort_temp");
                    d.Sort_temp = d.l[d.Sort_i];
                    storeArray(d.l, d.Sort_i);
                    d.l[d.Sort_i] = d.l[d.Sort_j];
                    storeArray(d.l, d.Sort_j);
                    d.l[d.Sort_j] = d.Sort_temp;
                    storeField(d, "Sort_temp");
                    d.Sort_temp = d.r[d.Sort_i];
                    storeArray(d.r, d.Sort_i);
                    d.r[d.Sort_i] = d.r[d.Sort_j];
                    storeArray(d.r, d.Sort_j);
                    d.r[d.Sort_j] = d.Sort_temp;
                    break;
                }
                case 8: { // Инкремент j
                    startSection();
                    storeField(d, "Sort_j");
                    d.Sort_j = d.Sort_j + 1;
                    break;
                }
                case 9: { // Инкремент i
                    startSection();
                    storeField(d, "Sort_i");
                    d.Sort_i = d.Sort_i + 1;
                    break;
                }
                case 10: { // Показ отсортированного списка
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
                case 1: { // Инициализация цикла
                    restoreSection();
                    break;
                }
                case 2: { // Цикл по i
                    break;
                }
                case 3: { // init
                    restoreSection();
                    break;
                }
                case 4: { // Цикл по j
                    break;
                }
                case 5: { // Условие перестановки
                    break;
                }
                case 6: { // Условие перестановки (окончание)
                    break;
                }
                case 7: { // Перестановка
                    restoreSection();
                    break;
                }
                case 8: { // Инкремент j
                    restoreSection();
                    break;
                }
                case 9: { // Инкремент i
                    restoreSection();
                    break;
                }
                case 10: { // Показ отсортированного списка
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация цикла
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл по i
                    if (stack.popBoolean()) {
                        state = 9; // Инкремент i
                    } else {
                        state = 1; // Инициализация цикла
                    }
                    break;
                }
                case 3: { // init
                    state = 2; // Цикл по i
                    break;
                }
                case 4: { // Цикл по j
                    if (stack.popBoolean()) {
                        state = 8; // Инкремент j
                    } else {
                        state = 3; // init
                    }
                    break;
                }
                case 5: { // Условие перестановки
                    state = 4; // Цикл по j
                    break;
                }
                case 6: { // Условие перестановки (окончание)
                    if (stack.popBoolean()) {
                        state = 7; // Перестановка
                    } else {
                        state = 5; // Условие перестановки
                    }
                    break;
                }
                case 7: { // Перестановка
                    state = 5; // Условие перестановки
                    break;
                }
                case 8: { // Инкремент j
                    state = 6; // Условие перестановки (окончание)
                    break;
                }
                case 9: { // Инкремент i
                    state = 4; // Цикл по j
                    break;
                }
                case 10: { // Показ отсортированного списка
                    state = 2; // Цикл по i
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 10; // Показ отсортированного списка
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
                case 10: { // Показ отсортированного списка
                    comment = Kruskal.this.getComment("Sort."); 
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
                case 10: { // Показ отсортированного списка
                    d.visualizer.showWeights(true);
                    d.visualizer.updateView(-1, 0);
                    break;
                }
            }
        }
    }

    /**
      * Слияние компонент связности.
      */
    private final class Join extends BaseAutomata implements Automata {
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
        public Join() {
            super( 
                "Join", 
                0, // Номер начального состояния 
                7, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация", 
                    "Проход по вершинам", 
                    "Условие перекраски", 
                    "Условие перекраски (окончание)", 
                    "Перекраска", 
                    "Инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация 
                    -1, // Проход по вершинам 
                    -1, // Условие перекраски 
                    -1, // Условие перекраски (окончание) 
                    -1, // Перекраска 
                    -1, // Инкремент 
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
                    stack.pushBoolean(false); 
                    state = 2; // Проход по вершинам
                    break;
                }
                case 2: { // Проход по вершинам
                    if (d.Join_i < d.color.length) {
                        state = 3; // Условие перекраски
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Условие перекраски
                    if (d.color[d.Join_i] == d.Join_temp) {
                        state = 5; // Перекраска
                    } else {
                        stack.pushBoolean(false); 
                        state = 4; // Условие перекраски (окончание)
                    }
                    break;
                }
                case 4: { // Условие перекраски (окончание)
                    state = 6; // Инкремент
                    break;
                }
                case 5: { // Перекраска
                    stack.pushBoolean(true); 
                    state = 4; // Условие перекраски (окончание)
                    break;
                }
                case 6: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 2; // Проход по вершинам
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    startSection();
                    storeField(d, "Join_i");
                    d.Join_i = 0;
                    storeField(d, "Join_temp");
                    d.Join_temp = d.color[d.l[d.edgeMin]];
                    break;
                }
                case 2: { // Проход по вершинам
                    break;
                }
                case 3: { // Условие перекраски
                    break;
                }
                case 4: { // Условие перекраски (окончание)
                    break;
                }
                case 5: { // Перекраска
                    startSection();
                    storeArray(d.color, d.Join_i);
                    d.color[d.Join_i] = d.color[d.r[d.edgeMin]];
                    break;
                }
                case 6: { // Инкремент
                    startSection();
                    storeField(d, "Join_i");
                    d.Join_i = d.Join_i + 1;
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
                case 2: { // Проход по вершинам
                    break;
                }
                case 3: { // Условие перекраски
                    break;
                }
                case 4: { // Условие перекраски (окончание)
                    break;
                }
                case 5: { // Перекраска
                    restoreSection();
                    break;
                }
                case 6: { // Инкремент
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
                case 2: { // Проход по вершинам
                    if (stack.popBoolean()) {
                        state = 6; // Инкремент
                    } else {
                        state = 1; // Инициализация
                    }
                    break;
                }
                case 3: { // Условие перекраски
                    state = 2; // Проход по вершинам
                    break;
                }
                case 4: { // Условие перекраски (окончание)
                    if (stack.popBoolean()) {
                        state = 5; // Перекраска
                    } else {
                        state = 3; // Условие перекраски
                    }
                    break;
                }
                case 5: { // Перекраска
                    state = 3; // Условие перекраски
                    break;
                }
                case 6: { // Инкремент
                    state = 4; // Условие перекраски (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Проход по вершинам
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
            }

            return java.text.MessageFormat.format(comment, args); 
        }

        /**
          * Выполняет действия по отрисовке состояния
          */
        public void drawState() {
            switch (state) {
            }
        }
    }
}
