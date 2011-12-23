package ru.ifmo.vizi.btree;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class BTree extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public BTree(Locale locale) {
        super("ru.ifmo.vizi.btree.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public BTreeVisualizer visualizer = null;

        /**
          * Корень дерева.
          */
        public treeNode root = null;

        /**
          * Текущий узел.
          */
        public treeNode x = null;

        /**
          * Ключ.
          */
        public char k = 'A';

        /**
          * ???.
          */
        public int t = 2;

        /**
          * ???.
          */
        public treeNode y = null;

        /**
          * ???.
          */
        public int m = 0;

        /**
          * Русская Строка.
          */
        public String sRu = new String();

        /**
          * English String.
          */
        public String sEn = new String();

        /**
          *  (Процедура SplitChild).
          */
        public treeNode SplitChild_z;

        /**
          * Переменная цикла (Процедура SplitChild).
          */
        public int SplitChild_j;

        /**
          * Переменная цикла (Процедура InsertNonfull).
          */
        public boolean InsertNonfull_flag;

        /**
          * Переменная цикла (Процедура InsertNonfull).
          */
        public int InsertNonfull_i;

        public String toString() {
            return("");
        }
    }

    /**
      * Разрезает вершину.
      */
    private final class SplitChild extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 16;

        /**
          * Конструктор.
          */
        public SplitChild() {
            super( 
                "SplitChild", 
                0, // Номер начального состояния 
                16, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "XZ", 
                    "XZ", 
                    "XZ (окончание)", 
                    "Начало цикла", 
                    "Цикл", 
                    "XZ", 
                    "Начало цикла", 
                    "Цикл", 
                    "XZ", 
                    "Начало цикла", 
                    "Цикл", 
                    "XZ", 
                    "XZ", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // XZ 
                    -1, // XZ 
                    -1, // XZ (окончание) 
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // XZ 
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // XZ 
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // XZ 
                    -1, // XZ 
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
                    state = 1; // Начало цикла
                    break;
                }
                case 1: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.SplitChild_j <= d.t - 1) {
                        state = 3; // XZ
                    } else {
                        state = 4; // XZ
                    }
                    break;
                }
                case 3: { // XZ
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
                case 4: { // XZ
                    if (!d.y.leaf) {
                        state = 6; // Начало цикла
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // XZ (окончание)
                    }
                    break;
                }
                case 5: { // XZ (окончание)
                    state = 9; // Начало цикла
                    break;
                }
                case 6: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 7; // Цикл
                    break;
                }
                case 7: { // Цикл
                    if (d.SplitChild_j <= d.t) {
                        state = 8; // XZ
                    } else {
                        stack.pushBoolean(true); 
                        state = 5; // XZ (окончание)
                    }
                    break;
                }
                case 8: { // XZ
                    stack.pushBoolean(true); 
                    state = 7; // Цикл
                    break;
                }
                case 9: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 10; // Цикл
                    break;
                }
                case 10: { // Цикл
                    if (d.m <= d.SplitChild_j) {
                        state = 11; // XZ
                    } else {
                        state = 12; // Начало цикла
                    }
                    break;
                }
                case 11: { // XZ
                    stack.pushBoolean(true); 
                    state = 10; // Цикл
                    break;
                }
                case 12: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 13; // Цикл
                    break;
                }
                case 13: { // Цикл
                    if (d.m <= d.SplitChild_j) {
                        state = 14; // XZ
                    } else {
                        state = 15; // XZ
                    }
                    break;
                }
                case 14: { // XZ
                    stack.pushBoolean(true); 
                    state = 13; // Цикл
                    break;
                }
                case 15: { // XZ
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "SplitChild_z");
                    d.SplitChild_z = new treeNode();
                    storeField(d.SplitChild_z, "leaf");
                    d.SplitChild_z.leaf = d.y.leaf;
                    storeField(d.SplitChild_z, "n");
                    d.SplitChild_z.n = d.t - 1;
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = 1;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // XZ
                    startSection();
                    storeArray(d.SplitChild_z.keys, d.SplitChild_j);
                    d.SplitChild_z.keys[d.SplitChild_j] = d.y.keys[d.SplitChild_j + d.t];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j + 1;
                    break;
                }
                case 4: { // XZ
                    break;
                }
                case 5: { // XZ (окончание)
                    break;
                }
                case 6: { // Начало цикла
                    startSection();
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = 1;
                    break;
                }
                case 7: { // Цикл
                    break;
                }
                case 8: { // XZ
                    startSection();
                    storeArray(d.SplitChild_z.links, d.SplitChild_j);
                    d.SplitChild_z.links[d.SplitChild_j] = d.y.links[d.SplitChild_j + d.t];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j + 1;
                    break;
                }
                case 9: { // Начало цикла
                    startSection();
                    storeField(d.y, "n");
                    d.y.n = d.t - 1;
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.x.n + 1;
                    break;
                }
                case 10: { // Цикл
                    break;
                }
                case 11: { // XZ
                    startSection();
                    storeArray(d.x.links, d.SplitChild_j + 1);
                    d.x.links[d.SplitChild_j + 1] = d.x.links[d.SplitChild_j];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j - 1;
                    break;
                }
                case 12: { // Начало цикла
                    startSection();
                    storeArray(d.x.links, d.m + 1);
                    d.x.links[d.m + 1] = d.SplitChild_z;
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.x.n;
                    break;
                }
                case 13: { // Цикл
                    break;
                }
                case 14: { // XZ
                    startSection();
                    storeArray(d.x.keys, d.SplitChild_j + 1);
                    d.x.keys[d.SplitChild_j + 1] = d.x.keys[d.SplitChild_j];
                    storeField(d, "SplitChild_j");
                    d.SplitChild_j = d.SplitChild_j - 1;
                    break;
                }
                case 15: { // XZ
                    startSection();
                    storeArray(d.x.keys, d.m);
                    d.x.keys[d.m] = d.y.keys[d.t];
                    storeField(d.x, "n");
                    d.x.n = d.x.n + 1;
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
                case 1: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // XZ
                    restoreSection();
                    break;
                }
                case 4: { // XZ
                    break;
                }
                case 5: { // XZ (окончание)
                    break;
                }
                case 6: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 7: { // Цикл
                    break;
                }
                case 8: { // XZ
                    restoreSection();
                    break;
                }
                case 9: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 10: { // Цикл
                    break;
                }
                case 11: { // XZ
                    restoreSection();
                    break;
                }
                case 12: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 13: { // Цикл
                    break;
                }
                case 14: { // XZ
                    restoreSection();
                    break;
                }
                case 15: { // XZ
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Начало цикла
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл
                    if (stack.popBoolean()) {
                        state = 3; // XZ
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // XZ
                    state = 2; // Цикл
                    break;
                }
                case 4: { // XZ
                    state = 2; // Цикл
                    break;
                }
                case 5: { // XZ (окончание)
                    if (stack.popBoolean()) {
                        state = 7; // Цикл
                    } else {
                        state = 4; // XZ
                    }
                    break;
                }
                case 6: { // Начало цикла
                    state = 4; // XZ
                    break;
                }
                case 7: { // Цикл
                    if (stack.popBoolean()) {
                        state = 8; // XZ
                    } else {
                        state = 6; // Начало цикла
                    }
                    break;
                }
                case 8: { // XZ
                    state = 7; // Цикл
                    break;
                }
                case 9: { // Начало цикла
                    state = 5; // XZ (окончание)
                    break;
                }
                case 10: { // Цикл
                    if (stack.popBoolean()) {
                        state = 11; // XZ
                    } else {
                        state = 9; // Начало цикла
                    }
                    break;
                }
                case 11: { // XZ
                    state = 10; // Цикл
                    break;
                }
                case 12: { // Начало цикла
                    state = 10; // Цикл
                    break;
                }
                case 13: { // Цикл
                    if (stack.popBoolean()) {
                        state = 14; // XZ
                    } else {
                        state = 12; // Начало цикла
                    }
                    break;
                }
                case 14: { // XZ
                    state = 13; // Цикл
                    break;
                }
                case 15: { // XZ
                    state = 13; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 15; // XZ
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

    /**
      * .
      */
    private final class InsertNonfull extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 23;

        /**
          * Конструктор.
          */
        public InsertNonfull() {
            super( 
                "InsertNonfull", 
                0, // Номер начального состояния 
                23, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "XZ", 
                    "XZ", 
                    "Начало цикла", 
                    "Если текущая вершина лист", 
                    "Если текущая вершина лист (окончание)", 
                    "InThis", 
                    "Цикл", 
                    "XZ1", 
                    "XZ2", 
                    "XZ", 
                    "Цикл", 
                    "XZ1", 
                    "XZ2", 
                    "XZ", 
                    "XZ (окончание)", 
                    "XZ", 
                    "Разрезает вершину (автомат)", 
                    "XZ", 
                    "XZ", 
                    "XZ (окончание)", 
                    "XZ", 
                    "XZ", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // XZ 
                    -1, // XZ 
                    -1, // Начало цикла 
                    -1, // Если текущая вершина лист 
                    -1, // Если текущая вершина лист (окончание) 
                    0, // InThis 
                    -1, // Цикл 
                    -1, // XZ1 
                    0, // XZ2 
                    0, // XZ 
                    -1, // Цикл 
                    -1, // XZ1 
                    -1, // XZ2 
                    -1, // XZ 
                    -1, // XZ (окончание) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Разрезает вершину (автомат) 
                    0, // XZ 
                    -1, // XZ 
                    -1, // XZ (окончание) 
                    -1, // XZ 
                    0, // XZ 
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
                    state = 1; // XZ
                    break;
                }
                case 1: { // XZ
                    stack.pushBoolean(false); 
                    state = 2; // XZ
                    break;
                }
                case 2: { // XZ
                    if (d.InsertNonfull_flag) {
                        state = 3; // Начало цикла
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Начало цикла
                    state = 4; // Если текущая вершина лист
                    break;
                }
                case 4: { // Если текущая вершина лист
                    if (d.x.leaf) {
                        state = 6; // InThis
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 5: { // Если текущая вершина лист (окончание)
                    stack.pushBoolean(true); 
                    state = 2; // XZ
                    break;
                }
                case 6: { // InThis
                    stack.pushBoolean(false); 
                    state = 7; // Цикл
                    break;
                }
                case 7: { // Цикл
                    if ((1 <= d.InsertNonfull_i) && (d.k < d.x.keys[d.InsertNonfull_i])) {
                        state = 8; // XZ1
                    } else {
                        state = 9; // XZ2
                    }
                    break;
                }
                case 8: { // XZ1
                    stack.pushBoolean(true); 
                    state = 7; // Цикл
                    break;
                }
                case 9: { // XZ2
                    stack.pushBoolean(true); 
                    state = 5; // Если текущая вершина лист (окончание)
                    break;
                }
                case 10: { // XZ
                    stack.pushBoolean(false); 
                    state = 11; // Цикл
                    break;
                }
                case 11: { // Цикл
                    if ((1 <= d.InsertNonfull_i) && (d.k < d.x.keys[d.InsertNonfull_i])) {
                        state = 12; // XZ1
                    } else {
                        state = 13; // XZ2
                    }
                    break;
                }
                case 12: { // XZ1
                    stack.pushBoolean(true); 
                    state = 11; // Цикл
                    break;
                }
                case 13: { // XZ2
                    state = 14; // XZ
                    break;
                }
                case 14: { // XZ
                    if (d.x.links[d.InsertNonfull_i].n == 2 * d.t - 1) {
                        state = 16; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 15; // XZ (окончание)
                    }
                    break;
                }
                case 15: { // XZ (окончание)
                    state = 22; // XZ
                    break;
                }
                case 16: { // XZ
                    state = 17; // Разрезает вершину (автомат)
                    break;
                }
                case 17: { // Разрезает вершину (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 18; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    state = 19; // XZ
                    break;
                }
                case 19: { // XZ
                    if (d.x.keys[d.InsertNonfull_i] < d.k) {
                        state = 21; // XZ
                    } else {
                        stack.pushBoolean(false); 
                        state = 20; // XZ (окончание)
                    }
                    break;
                }
                case 20: { // XZ (окончание)
                    stack.pushBoolean(true); 
                    state = 15; // XZ (окончание)
                    break;
                }
                case 21: { // XZ
                    stack.pushBoolean(true); 
                    state = 20; // XZ (окончание)
                    break;
                }
                case 22: { // XZ
                    stack.pushBoolean(false); 
                    state = 5; // Если текущая вершина лист (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // XZ
                    startSection();
                    storeField(d, "InsertNonfull_flag");
                    d.InsertNonfull_flag = true;
                    break;
                }
                case 2: { // XZ
                    break;
                }
                case 3: { // Начало цикла
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.x.n;
                    break;
                }
                case 4: { // Если текущая вершина лист
                    break;
                }
                case 5: { // Если текущая вершина лист (окончание)
                    break;
                }
                case 6: { // InThis
                    startSection();
                    break;
                }
                case 7: { // Цикл
                    break;
                }
                case 8: { // XZ1
                    startSection();
                    storeArray(d.x.keys, d.InsertNonfull_i + 1);
                    d.x.keys[d.InsertNonfull_i + 1] = d.x.keys[d.InsertNonfull_i];
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i - 1;
                    break;
                }
                case 9: { // XZ2
                    startSection();
                    storeArray(d.x.keys, d.InsertNonfull_i + 1);
                    d.x.keys[d.InsertNonfull_i + 1] = d.k;
                    storeField(d.x, "n");
                    d.x.n = d.x.n + 1;
                    storeField(d, "InsertNonfull_flag");
                    d.InsertNonfull_flag = false;
                    break;
                }
                case 10: { // XZ
                    startSection();
                    break;
                }
                case 11: { // Цикл
                    break;
                }
                case 12: { // XZ1
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i - 1;
                    break;
                }
                case 13: { // XZ2
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i + 1;
                    break;
                }
                case 14: { // XZ
                    break;
                }
                case 15: { // XZ (окончание)
                    break;
                }
                case 16: { // XZ
                    startSection();
                    storeField(d, "m");
                    d.m = d.InsertNonfull_i;
                    storeField(d, "y");
                    d.y = d.x.links[d.InsertNonfull_i];
                    break;
                }
                case 17: { // Разрезает вершину (автомат)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 18: { // XZ
                    startSection();
                    break;
                }
                case 19: { // XZ
                    break;
                }
                case 20: { // XZ (окончание)
                    break;
                }
                case 21: { // XZ
                    startSection();
                    storeField(d, "InsertNonfull_i");
                    d.InsertNonfull_i = d.InsertNonfull_i + 1;
                    break;
                }
                case 22: { // XZ
                    startSection();
                    storeField(d, "x");
                    d.x = d.x.links[d.InsertNonfull_i];
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
                case 1: { // XZ
                    restoreSection();
                    break;
                }
                case 2: { // XZ
                    break;
                }
                case 3: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 4: { // Если текущая вершина лист
                    break;
                }
                case 5: { // Если текущая вершина лист (окончание)
                    break;
                }
                case 6: { // InThis
                    restoreSection();
                    break;
                }
                case 7: { // Цикл
                    break;
                }
                case 8: { // XZ1
                    restoreSection();
                    break;
                }
                case 9: { // XZ2
                    restoreSection();
                    break;
                }
                case 10: { // XZ
                    restoreSection();
                    break;
                }
                case 11: { // Цикл
                    break;
                }
                case 12: { // XZ1
                    restoreSection();
                    break;
                }
                case 13: { // XZ2
                    restoreSection();
                    break;
                }
                case 14: { // XZ
                    break;
                }
                case 15: { // XZ (окончание)
                    break;
                }
                case 16: { // XZ
                    restoreSection();
                    break;
                }
                case 17: { // Разрезает вершину (автомат)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 18: { // XZ
                    restoreSection();
                    break;
                }
                case 19: { // XZ
                    break;
                }
                case 20: { // XZ (окончание)
                    break;
                }
                case 21: { // XZ
                    restoreSection();
                    break;
                }
                case 22: { // XZ
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // XZ
                    state = START_STATE; 
                    break;
                }
                case 2: { // XZ
                    if (stack.popBoolean()) {
                        state = 5; // Если текущая вершина лист (окончание)
                    } else {
                        state = 1; // XZ
                    }
                    break;
                }
                case 3: { // Начало цикла
                    state = 2; // XZ
                    break;
                }
                case 4: { // Если текущая вершина лист
                    state = 3; // Начало цикла
                    break;
                }
                case 5: { // Если текущая вершина лист (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // XZ2
                    } else {
                        state = 22; // XZ
                    }
                    break;
                }
                case 6: { // InThis
                    state = 4; // Если текущая вершина лист
                    break;
                }
                case 7: { // Цикл
                    if (stack.popBoolean()) {
                        state = 8; // XZ1
                    } else {
                        state = 6; // InThis
                    }
                    break;
                }
                case 8: { // XZ1
                    state = 7; // Цикл
                    break;
                }
                case 9: { // XZ2
                    state = 7; // Цикл
                    break;
                }
                case 10: { // XZ
                    state = 4; // Если текущая вершина лист
                    break;
                }
                case 11: { // Цикл
                    if (stack.popBoolean()) {
                        state = 12; // XZ1
                    } else {
                        state = 10; // XZ
                    }
                    break;
                }
                case 12: { // XZ1
                    state = 11; // Цикл
                    break;
                }
                case 13: { // XZ2
                    state = 11; // Цикл
                    break;
                }
                case 14: { // XZ
                    state = 13; // XZ2
                    break;
                }
                case 15: { // XZ (окончание)
                    if (stack.popBoolean()) {
                        state = 20; // XZ (окончание)
                    } else {
                        state = 14; // XZ
                    }
                    break;
                }
                case 16: { // XZ
                    state = 14; // XZ
                    break;
                }
                case 17: { // Разрезает вершину (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 16; // XZ
                    }
                    break;
                }
                case 18: { // XZ
                    state = 17; // Разрезает вершину (автомат)
                    break;
                }
                case 19: { // XZ
                    state = 18; // XZ
                    break;
                }
                case 20: { // XZ (окончание)
                    if (stack.popBoolean()) {
                        state = 21; // XZ
                    } else {
                        state = 19; // XZ
                    }
                    break;
                }
                case 21: { // XZ
                    state = 19; // XZ
                    break;
                }
                case 22: { // XZ
                    state = 15; // XZ (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // XZ
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
                case 6: { // InThis
                    comment = BTree.this.getComment("InsertNonfull.DrawStep1"); 
                    break;
                }
                case 9: { // XZ2
                    comment = BTree.this.getComment("InsertNonfull.StepAfterLoop1"); 
                    break;
                }
                case 10: { // XZ
                    comment = BTree.this.getComment("InsertNonfull.DrawStep2"); 
                    break;
                }
                case 17: { // Разрезает вершину (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 18: { // XZ
                    comment = BTree.this.getComment("InsertNonfull.test"); 
                    break;
                }
                case 22: { // XZ
                    comment = BTree.this.getComment("InsertNonfull.FinalStep"); 
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
                case 6: { // InThis
                    d.visualizer.showTree(d.x);
                    break;
                }
                case 9: { // XZ2
                    d.visualizer.showTree(null);
                    break;
                }
                case 10: { // XZ
                    d.visualizer.showTree(d.x);
                    break;
                }
                case 17: { // Разрезает вершину (автомат)
                    child.drawState(); 
                    break;
                }
                case 18: { // XZ
                    d.visualizer.showTree(null);
                    break;
                }
                case 22: { // XZ
                    d.visualizer.showTree(null);
                    break;
                }
            }
        }
    }

    /**
      * Добавляет ключ в дерево.
      */
    private final class Insert extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 9;

        /**
          * Конструктор.
          */
        public Insert() {
            super( 
                "Insert", 
                0, // Номер начального состояния 
                9, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "XZ", 
                    "Если корень полный", 
                    "Если корень полный (окончание)", 
                    "XZ", 
                    "Разрезает вершину (автомат)", 
                    "XZ", 
                    " (автомат)", 
                    " (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // XZ 
                    -1, // Если корень полный 
                    -1, // Если корень полный (окончание) 
                    -1, // XZ 
                    CALL_AUTO_LEVEL, // Разрезает вершину (автомат) 
                    0, // XZ 
                    CALL_AUTO_LEVEL, //  (автомат) 
                    CALL_AUTO_LEVEL, //  (автомат) 
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
                    state = 1; // XZ
                    break;
                }
                case 1: { // XZ
                    state = 2; // Если корень полный
                    break;
                }
                case 2: { // Если корень полный
                    if (d.y.n == 2 * d.t - 1) {
                        state = 4; // XZ
                    } else {
                        state = 8; //  (автомат)
                    }
                    break;
                }
                case 3: { // Если корень полный (окончание)
                    state = END_STATE; 
                    break;
                }
                case 4: { // XZ
                    state = 5; // Разрезает вершину (автомат)
                    break;
                }
                case 5: { // Разрезает вершину (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 6; // XZ
                    }
                    break;
                }
                case 6: { // XZ
                    state = 7; //  (автомат)
                    break;
                }
                case 7: { //  (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 3; // Если корень полный (окончание)
                    }
                    break;
                }
                case 8: { //  (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // Если корень полный (окончание)
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // XZ
                    startSection();
                    storeField(d, "sRu");
                    d.sRu = new String("Готово! Ключ вставлен на нужное место.");
                    storeField(d, "sEn");
                    d.sEn = new String("Completed!");
                    
                    storeField(d, "x");
                    d.x = d.root;
                    storeField(d, "y");
                    d.y = d.root;
                    break;
                }
                case 2: { // Если корень полный
                    break;
                }
                case 3: { // Если корень полный (окончание)
                    break;
                }
                case 4: { // XZ
                    startSection();
                    storeField(d, "x");
                    d.x = new treeNode();
                    storeField(d, "root");
                    d.root = d.x;
                    storeField(d.x, "leaf");
                    d.x.leaf = false;
                    storeField(d.x, "n");
                    d.x.n = 0;
                    storeArray(d.x.links, 1);
                    d.x.links[1] = d.y;
                    storeField(d, "m");
                    d.m = 1;
                    break;
                }
                case 5: { // Разрезает вершину (автомат)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 6: { // XZ
                    startSection();
                    storeField(d, "x");
                    d.x = d.root;
                    break;
                }
                case 7: { //  (автомат)
                    if (child == null) {
                        child = new InsertNonfull(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { //  (автомат)
                    if (child == null) {
                        child = new InsertNonfull(); 
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
                case 1: { // XZ
                    restoreSection();
                    break;
                }
                case 2: { // Если корень полный
                    break;
                }
                case 3: { // Если корень полный (окончание)
                    break;
                }
                case 4: { // XZ
                    restoreSection();
                    break;
                }
                case 5: { // Разрезает вершину (автомат)
                    if (child == null) {
                        child = new SplitChild(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 6: { // XZ
                    restoreSection();
                    break;
                }
                case 7: { //  (автомат)
                    if (child == null) {
                        child = new InsertNonfull(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { //  (автомат)
                    if (child == null) {
                        child = new InsertNonfull(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // XZ
                    state = START_STATE; 
                    break;
                }
                case 2: { // Если корень полный
                    state = 1; // XZ
                    break;
                }
                case 3: { // Если корень полный (окончание)
                    if (stack.popBoolean()) {
                        state = 7; //  (автомат)
                    } else {
                        state = 8; //  (автомат)
                    }
                    break;
                }
                case 4: { // XZ
                    state = 2; // Если корень полный
                    break;
                }
                case 5: { // Разрезает вершину (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 4; // XZ
                    }
                    break;
                }
                case 6: { // XZ
                    state = 5; // Разрезает вершину (автомат)
                    break;
                }
                case 7: { //  (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // XZ
                    }
                    break;
                }
                case 8: { //  (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // Если корень полный
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Если корень полный (окончание)
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
                case 5: { // Разрезает вершину (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 6: { // XZ
                    comment = BTree.this.getComment("Insert.test"); 
                    break;
                }
                case 7: { //  (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 8: { //  (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
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
                case 1: { // XZ
                    d.visualizer.showTree(null);
                    break;
                }
                case 5: { // Разрезает вершину (автомат)
                    child.drawState(); 
                    break;
                }
                case 6: { // XZ
                    d.visualizer.showTree(null);
                    break;
                }
                case 7: { //  (автомат)
                    child.drawState(); 
                    break;
                }
                case 8: { //  (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Удаляет ключ из дерева.
      */
    private final class Delete extends BaseAutomata implements Automata {
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
        public Delete() {
            super( 
                "Delete", 
                0, // Номер начального состояния 
                2, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
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
                    state = 1; // Начало цикла
                    break;
                }
                case 1: { // Начало цикла
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
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
                case 1: { // Начало цикла
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Начало цикла
                    state = START_STATE; 
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 1; // Начало цикла
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
                    "Выбрано добавление", 
                    "Выбрано добавление (окончание)", 
                    "Добавляет ключ в дерево (автомат)", 
                    "Выбрано удаление", 
                    "Выбрано удаление (окончание)", 
                    "Удаляет ключ из дерева (автомат)", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Выбрано добавление 
                    -1, // Выбрано добавление (окончание) 
                    CALL_AUTO_LEVEL, // Добавляет ключ в дерево (автомат) 
                    -1, // Выбрано удаление 
                    -1, // Выбрано удаление (окончание) 
                    CALL_AUTO_LEVEL, // Удаляет ключ из дерева (автомат) 
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
                    state = 1; // Выбрано добавление
                    break;
                }
                case 1: { // Выбрано добавление
                    if (d.visualizer.combobox1.getSelectedIndex() == 0) {
                        state = 3; // Добавляет ключ в дерево (автомат)
                    } else {
                        stack.pushBoolean(false); 
                        state = 2; // Выбрано добавление (окончание)
                    }
                    break;
                }
                case 2: { // Выбрано добавление (окончание)
                    state = 4; // Выбрано удаление
                    break;
                }
                case 3: { // Добавляет ключ в дерево (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 2; // Выбрано добавление (окончание)
                    }
                    break;
                }
                case 4: { // Выбрано удаление
                    if (d.visualizer.combobox1.getSelectedIndex() == 1) {
                        state = 6; // Удаляет ключ из дерева (автомат)
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // Выбрано удаление (окончание)
                    }
                    break;
                }
                case 5: { // Выбрано удаление (окончание)
                    state = END_STATE; 
                    break;
                }
                case 6: { // Удаляет ключ из дерева (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 5; // Выбрано удаление (окончание)
                    }
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Выбрано добавление
                    break;
                }
                case 2: { // Выбрано добавление (окончание)
                    break;
                }
                case 3: { // Добавляет ключ в дерево (автомат)
                    if (child == null) {
                        child = new Insert(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 4: { // Выбрано удаление
                    break;
                }
                case 5: { // Выбрано удаление (окончание)
                    break;
                }
                case 6: { // Удаляет ключ из дерева (автомат)
                    if (child == null) {
                        child = new Delete(); 
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
                case 1: { // Выбрано добавление
                    break;
                }
                case 2: { // Выбрано добавление (окончание)
                    break;
                }
                case 3: { // Добавляет ключ в дерево (автомат)
                    if (child == null) {
                        child = new Insert(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 4: { // Выбрано удаление
                    break;
                }
                case 5: { // Выбрано удаление (окончание)
                    break;
                }
                case 6: { // Удаляет ключ из дерева (автомат)
                    if (child == null) {
                        child = new Delete(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Выбрано добавление
                    state = START_STATE; 
                    break;
                }
                case 2: { // Выбрано добавление (окончание)
                    if (stack.popBoolean()) {
                        state = 3; // Добавляет ключ в дерево (автомат)
                    } else {
                        state = 1; // Выбрано добавление
                    }
                    break;
                }
                case 3: { // Добавляет ключ в дерево (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Выбрано добавление
                    }
                    break;
                }
                case 4: { // Выбрано удаление
                    state = 2; // Выбрано добавление (окончание)
                    break;
                }
                case 5: { // Выбрано удаление (окончание)
                    if (stack.popBoolean()) {
                        state = 6; // Удаляет ключ из дерева (автомат)
                    } else {
                        state = 4; // Выбрано удаление
                    }
                    break;
                }
                case 6: { // Удаляет ключ из дерева (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 4; // Выбрано удаление
                    }
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 5; // Выбрано удаление (окончание)
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
                    comment = BTree.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 3: { // Добавляет ключ в дерево (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 6: { // Удаляет ключ из дерева (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = BTree.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new String(d.sRu), new String(d.sEn)}; 
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
                    d.visualizer.showTree(null);
                    break;
                }
                case 3: { // Добавляет ключ в дерево (автомат)
                    child.drawState(); 
                    break;
                }
                case 6: { // Удаляет ключ из дерева (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.showTree(null);
                    break;
                }
            }
        }
    }
}
