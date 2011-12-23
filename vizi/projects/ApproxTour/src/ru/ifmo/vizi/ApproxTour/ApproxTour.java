package ru.ifmo.vizi.ApproxTour;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class ApproxTour extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public ApproxTour(Locale locale) {
        super("ru.ifmo.vizi.ApproxTour.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public ApproxTourVisualizer visualizer = null;

        /**
          * Количество элементов.
          */
        public int count = 0;

        /**
          * Матрица смежности.
          */
        public int[][] a = new int[22][22];

        /**
          * Переменная цикла.
          */
        public int i = 0;

        /**
          * Переменная цикла.
          */
        public int j = 0;

        /**
          * Массив ключей вершин.
          */
        public int[] key = new int[22];

        /**
          * Массив меток вершин при проверке графа.
          */
        public boolean[] w = new boolean[22];

        /**
          * Количество вершин в черном ящике.
          */
        public int m = 0;

        /**
          * Минимальная вершина.
          */
        public int min = 0;

        /**
          * Массив родителей вершин.
          */
        public int[] p = new int[22];

        /**
          * Дети.
          */
        public int[][] ch = new int[22][22];

        /**
          * Массив меток вершин.
          */
        public boolean[] q = new boolean[22];

        /**
          * Вершина.
          */
        public int k = 0;

        /**
          * Конец поиска в глубину.
          */
        public boolean dfsEnd = false;

        /**
          * Цикл.
          */
        public int[] c = new int[22];

        /**
          * Конец алгоритма.
          */
        public boolean endAlg = false;

        public String toString() {
            StringBuffer s=new StringBuffer();
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
        private final int END_STATE = 4;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                4, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Алгоритм Прима (автомат)", 
                    "Автомат обхода дерева (автомат)", 
                    "end", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Алгоритм Прима (автомат) 
                    CALL_AUTO_LEVEL, // Автомат обхода дерева (автомат) 
                    -1, // end 
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
                    state = 1; // Алгоритм Прима (автомат)
                    break;
                }
                case 1: { // Алгоритм Прима (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // Автомат обхода дерева (автомат)
                    }
                    break;
                }
                case 2: { // Автомат обхода дерева (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 3; // end
                    }
                    break;
                }
                case 3: { // end
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Алгоритм Прима (автомат)
                    if (child == null) {
                        child = new Prim(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Автомат обхода дерева (автомат)
                    if (child == null) {
                        child = new DFS(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // end
                    startSection();
                    storeField(d, "endAlg");
                    d.endAlg = true;
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
                case 1: { // Алгоритм Прима (автомат)
                    if (child == null) {
                        child = new Prim(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // Автомат обхода дерева (автомат)
                    if (child == null) {
                        child = new DFS(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // end
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Алгоритм Прима (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Автомат обхода дерева (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Алгоритм Прима (автомат)
                    }
                    break;
                }
                case 3: { // end
                    state = 2; // Автомат обхода дерева (автомат)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // end
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
                    comment = ApproxTour.this.getComment("Main.START_STATE"); 
                    args = new Object[]{new Integer(d.count)}; 
                    break;
                }
                case 1: { // Алгоритм Прима (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 2: { // Автомат обхода дерева (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = ApproxTour.this.getComment("Main.END_STATE"); 
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
                    d.visualizer.updateView(true, -1, false);
                    break;
                }
                case 1: { // Алгоритм Прима (автомат)
                    child.drawState(); 
                    break;
                }
                case 2: { // Автомат обхода дерева (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.visualizer.updateView(false, -1, true);
                    break;
                }
            }
        }
    }

    /**
      * Алгоритм Прима.
      */
    private final class Prim extends BaseAutomata implements Automata {
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
        public Prim() {
            super( 
                "Prim", 
                0, // Номер начального состояния 
                9, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла 1", 
                    "Цикл 1", 
                    "Шаг цикла 1", 
                    "Инициализация ключей вершин", 
                    "Главный цикл", 
                    "Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)", 
                    "Автомат обновления ключей вершин (автомат)", 
                    "Декремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 1 
                    -1, // Цикл 1 
                    -1, // Шаг цикла 1 
                    -1, // Инициализация ключей вершин 
                    -1, // Главный цикл 
                    CALL_AUTO_LEVEL, // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат) 
                    CALL_AUTO_LEVEL, // Автомат обновления ключей вершин (автомат) 
                    -1, // Декремент 
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
                    state = 1; // Инициализация цикла 1
                    break;
                }
                case 1: { // Инициализация цикла 1
                    stack.pushBoolean(false); 
                    state = 2; // Цикл 1
                    break;
                }
                case 2: { // Цикл 1
                    if ((d.i <= d.count)) {
                        state = 3; // Шаг цикла 1
                    } else {
                        state = 4; // Инициализация ключей вершин
                    }
                    break;
                }
                case 3: { // Шаг цикла 1
                    stack.pushBoolean(true); 
                    state = 2; // Цикл 1
                    break;
                }
                case 4: { // Инициализация ключей вершин
                    stack.pushBoolean(false); 
                    state = 5; // Главный цикл
                    break;
                }
                case 5: { // Главный цикл
                    if (d.m > 0) {
                        state = 6; // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 7; // Автомат обновления ключей вершин (автомат)
                    }
                    break;
                }
                case 7: { // Автомат обновления ключей вершин (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 8; // Декремент
                    }
                    break;
                }
                case 8: { // Декремент
                    stack.pushBoolean(true); 
                    state = 5; // Главный цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация цикла 1
                    startSection();
                    storeField(d, "m");
                    d.m = d.count;
                    storeField(d, "i");
                    d.i = 1;
                    storeField(d, "min");
                    d.min = 0;
                    break;
                }
                case 2: { // Цикл 1
                    break;
                }
                case 3: { // Шаг цикла 1
                    startSection();
                    storeArray(d.q, d.i);
                    d.q[d.i] = true;
                    storeArray(d.key, d.i);
                    d.key[d.i] = 1000000;
                    storeField(d, "i");
                    d.i = d.i + 1;   
                    break;
                }
                case 4: { // Инициализация ключей вершин
                    startSection();
                    storeArray(d.key, 1);
                    d.key[1] = 0;   
                    break;
                }
                case 5: { // Главный цикл
                    break;
                }
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    if (child == null) {
                        child = new extractMinimum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 7: { // Автомат обновления ключей вершин (автомат)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Декремент
                    startSection();
                    storeField(d, "m");
                    d.m = d.m - 1;
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
                case 1: { // Инициализация цикла 1
                    restoreSection();
                    break;
                }
                case 2: { // Цикл 1
                    break;
                }
                case 3: { // Шаг цикла 1
                    restoreSection();
                    break;
                }
                case 4: { // Инициализация ключей вершин
                    restoreSection();
                    break;
                }
                case 5: { // Главный цикл
                    break;
                }
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    if (child == null) {
                        child = new extractMinimum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 7: { // Автомат обновления ключей вершин (автомат)
                    if (child == null) {
                        child = new updateKeys(); 
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
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация цикла 1
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл 1
                    if (stack.popBoolean()) {
                        state = 3; // Шаг цикла 1
                    } else {
                        state = 1; // Инициализация цикла 1
                    }
                    break;
                }
                case 3: { // Шаг цикла 1
                    state = 2; // Цикл 1
                    break;
                }
                case 4: { // Инициализация ключей вершин
                    state = 2; // Цикл 1
                    break;
                }
                case 5: { // Главный цикл
                    if (stack.popBoolean()) {
                        state = 8; // Декремент
                    } else {
                        state = 4; // Инициализация ключей вершин
                    }
                    break;
                }
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 5; // Главный цикл
                    }
                    break;
                }
                case 7: { // Автомат обновления ключей вершин (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    }
                    break;
                }
                case 8: { // Декремент
                    state = 7; // Автомат обновления ключей вершин (автомат)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 5; // Главный цикл
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
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // Автомат обновления ключей вершин (автомат)
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
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    child.drawState(); 
                    break;
                }
                case 7: { // Автомат обновления ключей вершин (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Автомат извлечения из черного ящика вершины с минимальным ключом.
      */
    private final class extractMinimum extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 5;

        /**
          * Конструктор.
          */
        public extractMinimum() {
            super( 
                "extractMinimum", 
                0, // Номер начального состояния 
                5, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла 2", 
                    "Цикл2", 
                    "шаг цикла 2", 
                    "Помечаем минимальную вершину", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 2 
                    -1, // Цикл2 
                    -1, // шаг цикла 2 
                    -1, // Помечаем минимальную вершину 
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
                    state = 1; // Инициализация цикла 2
                    break;
                }
                case 1: { // Инициализация цикла 2
                    stack.pushBoolean(false); 
                    state = 2; // Цикл2
                    break;
                }
                case 2: { // Цикл2
                    if ((d.i <= d.count)) {
                        state = 3; // шаг цикла 2
                    } else {
                        state = 4; // Помечаем минимальную вершину
                    }
                    break;
                }
                case 3: { // шаг цикла 2
                    stack.pushBoolean(true); 
                    state = 2; // Цикл2
                    break;
                }
                case 4: { // Помечаем минимальную вершину
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация цикла 2
                    startSection();
                    storeField(d, "min");
                    d.min = 0;
                    storeField(d, "i");
                    d.i = 1;    
                    break;
                }
                case 2: { // Цикл2
                    break;
                }
                case 3: { // шаг цикла 2
                    startSection();
                    if ((d.q[d.i]) & ((d.min == 0) || (d.key[d.min] > d.key[d.i]))) {
                    storeField(d, "min");
                    d.min = d.i;
                    }
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 4: { // Помечаем минимальную вершину
                    startSection();
                    storeArray(d.q, d.min);
                    d.q[d.min] = false;
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
                case 1: { // Инициализация цикла 2
                    restoreSection();
                    break;
                }
                case 2: { // Цикл2
                    break;
                }
                case 3: { // шаг цикла 2
                    restoreSection();
                    break;
                }
                case 4: { // Помечаем минимальную вершину
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация цикла 2
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл2
                    if (stack.popBoolean()) {
                        state = 3; // шаг цикла 2
                    } else {
                        state = 1; // Инициализация цикла 2
                    }
                    break;
                }
                case 3: { // шаг цикла 2
                    state = 2; // Цикл2
                    break;
                }
                case 4: { // Помечаем минимальную вершину
                    state = 2; // Цикл2
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 4; // Помечаем минимальную вершину
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
      * Автомат обновления ключей вершин.
      */
    private final class updateKeys extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 4;

        /**
          * Конструктор.
          */
        public updateKeys() {
            super( 
                "updateKeys", 
                0, // Номер начального состояния 
                4, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла 3", 
                    "Цикл3", 
                    "шаг цикла 3", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 3 
                    -1, // Цикл3 
                    -1, // шаг цикла 3 
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
                    state = 1; // Инициализация цикла 3
                    break;
                }
                case 1: { // Инициализация цикла 3
                    stack.pushBoolean(false); 
                    state = 2; // Цикл3
                    break;
                }
                case 2: { // Цикл3
                    if ((d.i <= d.count)) {
                        state = 3; // шаг цикла 3
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // шаг цикла 3
                    stack.pushBoolean(true); 
                    state = 2; // Цикл3
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация цикла 3
                    startSection();
                    storeField(d, "i");
                    d.i = 1;    
                    break;
                }
                case 2: { // Цикл3
                    break;
                }
                case 3: { // шаг цикла 3
                    startSection();
                    if ((d.q[d.i]) & (0 < d.a[d.min][d.i]) & (d.a[d.min][d.i] < d.key[d.i])) {
                        storeArray(d.p, d.i);
                        d.p[d.i] = d.min;
                        storeArray(d.key, d.i);
                        d.key[d.i] = d.a[d.min][d.i];
                    }
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
                case 1: { // Инициализация цикла 3
                    restoreSection();
                    break;
                }
                case 2: { // Цикл3
                    break;
                }
                case 3: { // шаг цикла 3
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация цикла 3
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл3
                    if (stack.popBoolean()) {
                        state = 3; // шаг цикла 3
                    } else {
                        state = 1; // Инициализация цикла 3
                    }
                    break;
                }
                case 3: { // шаг цикла 3
                    state = 2; // Цикл3
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл3
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
      * Автомат обхода дерева.
      */
    private final class DFS extends BaseAutomata implements Automata {
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
        public DFS() {
            super( 
                "DFS", 
                0, // Номер начального состояния 
                14, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "init", 
                    "initLoop", 
                    "loopInitStep", 
                    "инициализация начальных параметров", 
                    "loop", 
                    "Условие", 
                    "Условие (окончание)", 
                    "Условие", 
                    "Условие (окончание)", 
                    "", 
                    "up", 
                    "end", 
                    "down", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // init 
                    -1, // initLoop 
                    -1, // loopInitStep 
                    1, // инициализация начальных параметров 
                    -1, // loop 
                    -1, // Условие 
                    -1, // Условие (окончание) 
                    -1, // Условие 
                    -1, // Условие (окончание) 
                    1, //  
                    -1, // up 
                    1, // end 
                    1, // down 
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
                    state = 1; // init
                    break;
                }
                case 1: { // init
                    stack.pushBoolean(false); 
                    state = 2; // initLoop
                    break;
                }
                case 2: { // initLoop
                    if (d.i <= d.count) {
                        state = 3; // loopInitStep
                    } else {
                        state = 4; // инициализация начальных параметров
                    }
                    break;
                }
                case 3: { // loopInitStep
                    stack.pushBoolean(true); 
                    state = 2; // initLoop
                    break;
                }
                case 4: { // инициализация начальных параметров
                    stack.pushBoolean(false); 
                    state = 5; // loop
                    break;
                }
                case 5: { // loop
                    if (!(d.dfsEnd)) {
                        state = 6; // Условие
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // Условие
                    if (d.ch[d.k][0] == 0) {
                        state = 8; // Условие
                    } else {
                        state = 13; // down
                    }
                    break;
                }
                case 7: { // Условие (окончание)
                    stack.pushBoolean(true); 
                    state = 5; // loop
                    break;
                }
                case 8: { // Условие
                    if (d.p[d.k] != 0) {
                        state = 10; 
                    } else {
                        state = 12; // end
                    }
                    break;
                }
                case 9: { // Условие (окончание)
                    stack.pushBoolean(true); 
                    state = 7; // Условие (окончание)
                    break;
                }
                case 10: { 
                    state = 11; // up
                    break;
                }
                case 11: { // up
                    stack.pushBoolean(true); 
                    state = 9; // Условие (окончание)
                    break;
                }
                case 12: { // end
                    stack.pushBoolean(false); 
                    state = 9; // Условие (окончание)
                    break;
                }
                case 13: { // down
                    stack.pushBoolean(false); 
                    state = 7; // Условие (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // init
                    startSection();
                    storeField(d, "k");
                    d.k = 1;
                    storeField(d, "i");
                    d.i = 1;
                    break;
                }
                case 2: { // initLoop
                    break;
                }
                case 3: { // loopInitStep
                    startSection();
                    storeArray(d.q, d.i);
                    d.q[d.i] = false;
                    storeArray(d.c, d.i);
                    d.c[d.i] = 0;
                    
                    storeArray(d.ch[ d.p[d.i] ], 0);
                    d.ch[ d.p[d.i] ][0] = d.ch[ d.p[d.i] ][0] + 1;
                    storeArray(d.ch[ d.p[d.i] ] ,  d.ch[d.p[d.i]][0] );
                    d.ch[ d.p[d.i] ] [ d.ch[d.p[d.i]][0] ] = d.i;
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 4: { // инициализация начальных параметров
                    startSection();
                    storeArray(d.q, d.k);
                    d.q[d.k] = true;
                    storeArray(d.c, 0);
                    d.c[0] = d.c[0] + 1;
                    storeArray(d.c, d.c[0]);
                    d.c[d.c[0]] = d.k;
                    break;
                }
                case 5: { // loop
                    break;
                }
                case 6: { // Условие
                    break;
                }
                case 7: { // Условие (окончание)
                    break;
                }
                case 8: { // Условие
                    break;
                }
                case 9: { // Условие (окончание)
                    break;
                }
                case 10: { 
                    startSection();
                    break;
                }
                case 11: { // up
                    startSection();
                    storeField(d, "k");
                    d.k = d.p[d.k];
                    break;
                }
                case 12: { // end
                    startSection();
                    storeField(d, "dfsEnd");
                    d.dfsEnd = true;
                    storeArray(d.c, 0);
                    d.c[0] = d.c[0] + 1;
                    storeArray(d.c, d.c[0]);
                    d.c[d.c[0]] = 1;
                    break;
                }
                case 13: { // down
                    startSection();
                    storeArray(d.ch[d.k], 0);
                    d.ch[d.k][0] = d.ch[d.k][0] - 1;
                    storeField(d, "k");
                    d.k = d.ch[d.k][ d.ch[d.k][0] + 1 ];
                    storeArray(d.q, d.k);
                    d.q[d.k] = true;
                    storeArray(d.c, 0);
                    d.c[0] = d.c[0] + 1;
                    storeArray(d.c, d.c[0]);
                    d.c[d.c[0]] = d.k;
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
                case 1: { // init
                    restoreSection();
                    break;
                }
                case 2: { // initLoop
                    break;
                }
                case 3: { // loopInitStep
                    restoreSection();
                    break;
                }
                case 4: { // инициализация начальных параметров
                    restoreSection();
                    break;
                }
                case 5: { // loop
                    break;
                }
                case 6: { // Условие
                    break;
                }
                case 7: { // Условие (окончание)
                    break;
                }
                case 8: { // Условие
                    break;
                }
                case 9: { // Условие (окончание)
                    break;
                }
                case 10: { 
                    restoreSection();
                    break;
                }
                case 11: { // up
                    restoreSection();
                    break;
                }
                case 12: { // end
                    restoreSection();
                    break;
                }
                case 13: { // down
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // init
                    state = START_STATE; 
                    break;
                }
                case 2: { // initLoop
                    if (stack.popBoolean()) {
                        state = 3; // loopInitStep
                    } else {
                        state = 1; // init
                    }
                    break;
                }
                case 3: { // loopInitStep
                    state = 2; // initLoop
                    break;
                }
                case 4: { // инициализация начальных параметров
                    state = 2; // initLoop
                    break;
                }
                case 5: { // loop
                    if (stack.popBoolean()) {
                        state = 7; // Условие (окончание)
                    } else {
                        state = 4; // инициализация начальных параметров
                    }
                    break;
                }
                case 6: { // Условие
                    state = 5; // loop
                    break;
                }
                case 7: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Условие (окончание)
                    } else {
                        state = 13; // down
                    }
                    break;
                }
                case 8: { // Условие
                    state = 6; // Условие
                    break;
                }
                case 9: { // Условие (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // up
                    } else {
                        state = 12; // end
                    }
                    break;
                }
                case 10: { 
                    state = 8; // Условие
                    break;
                }
                case 11: { // up
                    state = 10; 
                    break;
                }
                case 12: { // end
                    state = 8; // Условие
                    break;
                }
                case 13: { // down
                    state = 6; // Условие
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 5; // loop
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
                case 4: { // инициализация начальных параметров
                    comment = ApproxTour.this.getComment("DFS.dfsInit"); 
                    args = new Object[]{new Integer(d.k)}; 
                    break;
                }
                case 10: { 
                    comment = ApproxTour.this.getComment("DFS.stepUp"); 
                    args = new Object[]{new Integer(d.k), new Integer(d.p[d.k])}; 
                    break;
                }
                case 12: { // end
                    comment = ApproxTour.this.getComment("DFS.endDFS"); 
                    break;
                }
                case 13: { // down
                    comment = ApproxTour.this.getComment("DFS.stepDown"); 
                    args = new Object[]{new Integer(d.c[d.c[0]])}; 
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
                case 4: { // инициализация начальных параметров
                    d.visualizer.updateView(false, d.k, false);
                    break;
                }
                case 10: { 
                    d.visualizer.updateView(false, d.p[d.k], true);
                    break;
                }
                case 12: { // end
                    d.visualizer.updateView(false, -1, true);
                    break;
                }
                case 13: { // down
                    d.visualizer.updateView(false, d.k, true);
                    break;
                }
            }
        }
    }
}
