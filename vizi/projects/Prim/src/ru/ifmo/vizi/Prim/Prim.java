package ru.ifmo.vizi.Prim;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class Prim extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public Prim(Locale locale) {
        super("ru.ifmo.vizi.Prim.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Матрица смежностей.
          */
        public int[][] a = new int[21][21];

        /**
          * Массив ключей вершин.
          */
        public int[] key = new int[21];

        /**
          * Массив меток вершин.
          */
        public boolean[] q = new boolean[21];

        /**
          * Массив меток вершин при проверке графа.
          */
        public boolean[] w = new boolean[21];

        /**
          * Массив родителей вершин.
          */
        public int[] p = new int[21];

        /**
          * Стек.
          */
        public int[] st = new int[21];

        /**
          * Количество вершин в черном ящике.
          */
        public int m = 0;

        /**
          * Количество вершин.
          */
        public int countVertex = 0;

        /**
          * Минимальная вершина.
          */
        public int min = 0;

        /**
          * Идентификатор шага алгоритма.
          */
        public int step = 0;

        /**
          * Переменная цикла.
          */
        public int i = 0;

        /**
          * Переменная цикла.
          */
        public int j = 0;

        /**
          * Размер стека.
          */
        public int nstack = 0;

        /**
          * Комментарии.
          */
        public String comment = new String();

        /**
          * Экземпляр визуализатора.
          */
        public PrimVisualizer visualizer = null;

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
        private final int END_STATE = 6;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                6, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Проверка графа (автомат)", 
                    "Провека графа на связность", 
                    "Провека графа на связность (окончание)", 
                    "Алгоритм Прима (автомат)", 
                    "Пометка шага 5", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Проверка графа (автомат) 
                    0, // Провека графа на связность 
                    -1, // Провека графа на связность (окончание) 
                    CALL_AUTO_LEVEL, // Алгоритм Прима (автомат) 
                    -1, // Пометка шага 5 
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
                    state = 1; // Проверка графа (автомат)
                    break;
                }
                case 1: { // Проверка графа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // Провека графа на связность
                    }
                    break;
                }
                case 2: { // Провека графа на связность
                    if (d.m == d.countVertex) {
                        state = 4; // Алгоритм Прима (автомат)
                    } else {
                        stack.pushBoolean(false); 
                        state = 3; // Провека графа на связность (окончание)
                    }
                    break;
                }
                case 3: { // Провека графа на связность (окончание)
                    state = END_STATE; 
                    break;
                }
                case 4: { // Алгоритм Прима (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 5; // Пометка шага 5
                    }
                    break;
                }
                case 5: { // Пометка шага 5
                    stack.pushBoolean(true); 
                    state = 3; // Провека графа на связность (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Проверка графа (автомат)
                    if (child == null) {
                        child = new CheckGraph(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Провека графа на связность
                    break;
                }
                case 3: { // Провека графа на связность (окончание)
                    break;
                }
                case 4: { // Алгоритм Прима (автомат)
                    if (child == null) {
                        child = new PrimAlgorithm(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 5: { // Пометка шага 5
                    startSection();
                    storeField(d, "step");
                    						d.step = 5;
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
                case 1: { // Проверка графа (автомат)
                    if (child == null) {
                        child = new CheckGraph(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // Провека графа на связность
                    break;
                }
                case 3: { // Провека графа на связность (окончание)
                    break;
                }
                case 4: { // Алгоритм Прима (автомат)
                    if (child == null) {
                        child = new PrimAlgorithm(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 5: { // Пометка шага 5
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Проверка графа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Провека графа на связность
                    state = 1; // Проверка графа (автомат)
                    break;
                }
                case 3: { // Провека графа на связность (окончание)
                    if (stack.popBoolean()) {
                        state = 5; // Пометка шага 5
                    } else {
                        state = 2; // Провека графа на связность
                    }
                    break;
                }
                case 4: { // Алгоритм Прима (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // Провека графа на связность
                    }
                    break;
                }
                case 5: { // Пометка шага 5
                    state = 4; // Алгоритм Прима (автомат)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Провека графа на связность (окончание)
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
                    comment = Prim.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Проверка графа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 2: { // Провека графа на связность
                    if (d.m == d.countVertex) {
                        comment = Prim.this.getComment("Main.connectednessCheck.true"); 
                    } else {
                        comment = Prim.this.getComment("Main.connectednessCheck.false"); 
                    }
                    break;
                }
                case 4: { // Алгоритм Прима (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 5: { // Пометка шага 5
                    comment = Prim.this.getComment("Main.MarkStep5"); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = Prim.this.getComment("Main.END_STATE"); 
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
                case 1: { // Проверка графа (автомат)
                    child.drawState(); 
                    break;
                }
                case 4: { // Алгоритм Прима (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    				d.visualizer.updateView();
                    break;
                }
            }
        }
    }

    /**
      * Алгоритм Прима.
      */
    private final class PrimAlgorithm extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 13;

        /**
          * Конструктор.
          */
        public PrimAlgorithm() {
            super( 
                "PrimAlgorithm", 
                0, // Номер начального состояния 
                13, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла 1", 
                    "Цикл 1", 
                    "Шаг цикла 1", 
                    "Инициализация ключей вершин", 
                    "Главный цикл", 
                    "Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)", 
                    "Извлечение из черного ящика минимальной вершины", 
                    "Автомат обновления ключей вершин (автомат)", 
                    "Проверка конца алгоритма", 
                    "Проверка конца алгоритма (окончание)", 
                    "Обновление ключей вершин", 
                    "Декремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 1 
                    -1, // Цикл 1 
                    -1, // Шаг цикла 1 
                    0, // Инициализация ключей вершин 
                    -1, // Главный цикл 
                    CALL_AUTO_LEVEL, // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат) 
                    0, // Извлечение из черного ящика минимальной вершины 
                    CALL_AUTO_LEVEL, // Автомат обновления ключей вершин (автомат) 
                    -1, // Проверка конца алгоритма 
                    -1, // Проверка конца алгоритма (окончание) 
                    0, // Обновление ключей вершин 
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
                    if ((d.i <= d.countVertex)) {
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
                        state = 7; // Извлечение из черного ящика минимальной вершины
                    }
                    break;
                }
                case 7: { // Извлечение из черного ящика минимальной вершины
                    state = 8; // Автомат обновления ключей вершин (автомат)
                    break;
                }
                case 8: { // Автомат обновления ключей вершин (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 9; // Проверка конца алгоритма
                    }
                    break;
                }
                case 9: { // Проверка конца алгоритма
                    if (d.m > 1) {
                        state = 11; // Обновление ключей вершин
                    } else {
                        stack.pushBoolean(false); 
                        state = 10; // Проверка конца алгоритма (окончание)
                    }
                    break;
                }
                case 10: { // Проверка конца алгоритма (окончание)
                    state = 12; // Декремент
                    break;
                }
                case 11: { // Обновление ключей вершин
                    stack.pushBoolean(true); 
                    state = 10; // Проверка конца алгоритма (окончание)
                    break;
                }
                case 12: { // Декремент
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
                    				d.m = d.countVertex;
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
                case 7: { // Извлечение из черного ящика минимальной вершины
                    startSection();
                    storeField(d, "step");
                    					d.step = 1;
                    storeField(d, "nstack");
                    					d.nstack = 0;
                    break;
                }
                case 8: { // Автомат обновления ключей вершин (автомат)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 9: { // Проверка конца алгоритма
                    break;
                }
                case 10: { // Проверка конца алгоритма (окончание)
                    break;
                }
                case 11: { // Обновление ключей вершин
                    startSection();
                    break;
                }
                case 12: { // Декремент
                    startSection();
                    storeField(d, "m");
                    					d.m = d.m - 1;
                    storeField(d, "step");
                    					d.step = 2;
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
                case 7: { // Извлечение из черного ящика минимальной вершины
                    restoreSection();
                    break;
                }
                case 8: { // Автомат обновления ключей вершин (автомат)
                    if (child == null) {
                        child = new updateKeys(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 9: { // Проверка конца алгоритма
                    break;
                }
                case 10: { // Проверка конца алгоритма (окончание)
                    break;
                }
                case 11: { // Обновление ключей вершин
                    restoreSection();
                    break;
                }
                case 12: { // Декремент
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
                        state = 12; // Декремент
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
                case 7: { // Извлечение из черного ящика минимальной вершины
                    state = 6; // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    break;
                }
                case 8: { // Автомат обновления ключей вершин (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 7; // Извлечение из черного ящика минимальной вершины
                    }
                    break;
                }
                case 9: { // Проверка конца алгоритма
                    state = 8; // Автомат обновления ключей вершин (автомат)
                    break;
                }
                case 10: { // Проверка конца алгоритма (окончание)
                    if (stack.popBoolean()) {
                        state = 11; // Обновление ключей вершин
                    } else {
                        state = 9; // Проверка конца алгоритма
                    }
                    break;
                }
                case 11: { // Обновление ключей вершин
                    state = 9; // Проверка конца алгоритма
                    break;
                }
                case 12: { // Декремент
                    state = 10; // Проверка конца алгоритма (окончание)
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
                case 1: { // Инициализация цикла 1
                    comment = Prim.this.getComment("PrimAlgorithm.InitLoop1"); 
                    break;
                }
                case 3: { // Шаг цикла 1
                    comment = Prim.this.getComment("PrimAlgorithm.StepLoop1"); 
                    break;
                }
                case 4: { // Инициализация ключей вершин
                    comment = Prim.this.getComment("PrimAlgorithm.InitKeys"); 
                    break;
                }
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 7: { // Извлечение из черного ящика минимальной вершины
                    comment = Prim.this.getComment("PrimAlgorithm.ExtractMin"); 
                    args = new Object[]{new Integer(d.min)}; 
                    break;
                }
                case 8: { // Автомат обновления ключей вершин (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // Обновление ключей вершин
                    comment = Prim.this.getComment("PrimAlgorithm.UpdateKeysOfVertex"); 
                    args = new Object[]{d.comment}; 
                    break;
                }
                case 12: { // Декремент
                    comment = Prim.this.getComment("PrimAlgorithm.Decrement"); 
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
                case 4: { // Инициализация ключей вершин
                    				d.visualizer.updateView();	
                    break;
                }
                case 6: { // Автомат извлечения из черного ящика вершины с минимальным ключом (автомат)
                    child.drawState(); 
                    break;
                }
                case 7: { // Извлечение из черного ящика минимальной вершины
                    					d.visualizer.updateView();	
                    break;
                }
                case 8: { // Автомат обновления ключей вершин (автомат)
                    child.drawState(); 
                    break;
                }
                case 11: { // Обновление ключей вершин
                    							d.visualizer.updateView();	
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
                    if ((d.i <= d.countVertex)) {
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
                case 1: { // Инициализация цикла 2
                    comment = Prim.this.getComment("extractMinimum.InitLoop2"); 
                    break;
                }
                case 3: { // шаг цикла 2
                    comment = Prim.this.getComment("extractMinimum.StepLoop2"); 
                    break;
                }
                case 4: { // Помечаем минимальную вершину
                    comment = Prim.this.getComment("extractMinimum.MarkMinimalVertex"); 
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
        private final int END_STATE = 7;

        /**
          * Конструктор.
          */
        public updateKeys() {
            super( 
                "updateKeys", 
                0, // Номер начального состояния 
                7, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла 3", 
                    "Цикл3", 
                    "шаг цикла 3", 
                    "Инициализация цикла 6", 
                    "Цикл6", 
                    "шаг цикла 6", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 3 
                    -1, // Цикл3 
                    -1, // шаг цикла 3 
                    -1, // Инициализация цикла 6 
                    -1, // Цикл6 
                    -1, // шаг цикла 6 
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
                    if ((d.i <= d.countVertex)) {
                        state = 3; // шаг цикла 3
                    } else {
                        state = 4; // Инициализация цикла 6
                    }
                    break;
                }
                case 3: { // шаг цикла 3
                    stack.pushBoolean(true); 
                    state = 2; // Цикл3
                    break;
                }
                case 4: { // Инициализация цикла 6
                    stack.pushBoolean(false); 
                    state = 5; // Цикл6
                    break;
                }
                case 5: { // Цикл6
                    if ((d.i <= d.nstack)) {
                        state = 6; // шаг цикла 6
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 6: { // шаг цикла 6
                    stack.pushBoolean(true); 
                    state = 5; // Цикл6
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
                    storeField(d, "nstack");
                    						d.nstack = d.nstack + 1;
                    storeArray(d.st, d.nstack);
                    						d.st[d.nstack] = d.i;
                    					}
                    storeField(d, "i");
                    					d.i = d.i + 1;
                    break;
                }
                case 4: { // Инициализация цикла 6
                    startSection();
                    storeField(d, "i");
                    				d.i = 1;
                    storeField(d, "comment");
                    				d.comment = "";	
                    break;
                }
                case 5: { // Цикл6
                    break;
                }
                case 6: { // шаг цикла 6
                    startSection();
                    				        if (d.i > 1) {
                    storeField(d, "comment");
                    						d.comment = d.comment + ", ";
                    					}
                    storeField(d, "comment");
                    					d.comment = d.comment + Integer.toString(d.st[d.i]);
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
                case 4: { // Инициализация цикла 6
                    restoreSection();
                    break;
                }
                case 5: { // Цикл6
                    break;
                }
                case 6: { // шаг цикла 6
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
                case 4: { // Инициализация цикла 6
                    state = 2; // Цикл3
                    break;
                }
                case 5: { // Цикл6
                    if (stack.popBoolean()) {
                        state = 6; // шаг цикла 6
                    } else {
                        state = 4; // Инициализация цикла 6
                    }
                    break;
                }
                case 6: { // шаг цикла 6
                    state = 5; // Цикл6
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 5; // Цикл6
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
                case 1: { // Инициализация цикла 3
                    comment = Prim.this.getComment("updateKeys.InitLoop3"); 
                    break;
                }
                case 3: { // шаг цикла 3
                    comment = Prim.this.getComment("updateKeys.StepLoop3"); 
                    break;
                }
                case 4: { // Инициализация цикла 6
                    comment = Prim.this.getComment("updateKeys.InitLoop6"); 
                    break;
                }
                case 6: { // шаг цикла 6
                    comment = Prim.this.getComment("updateKeys.StepLoop6"); 
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
            }
        }
    }

    /**
      * Проверка графа.
      */
    private final class CheckGraph extends BaseAutomata implements Automata {
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
        public CheckGraph() {
            super( 
                "CheckGraph", 
                0, // Номер начального состояния 
                7, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Инициализация цикла 4", 
                    "Цикл 4", 
                    "Шаг цикла 4", 
                    "Инициализация цикла 5", 
                    "Цикл 5", 
                    "Шаг цикла 5", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация цикла 4 
                    -1, // Цикл 4 
                    -1, // Шаг цикла 4 
                    -1, // Инициализация цикла 5 
                    -1, // Цикл 5 
                    -1, // Шаг цикла 5 
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
                    state = 1; // Инициализация цикла 4
                    break;
                }
                case 1: { // Инициализация цикла 4
                    stack.pushBoolean(false); 
                    state = 2; // Цикл 4
                    break;
                }
                case 2: { // Цикл 4
                    if ((d.nstack > 0)) {
                        state = 3; // Шаг цикла 4
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Шаг цикла 4
                    state = 4; // Инициализация цикла 5
                    break;
                }
                case 4: { // Инициализация цикла 5
                    stack.pushBoolean(false); 
                    state = 5; // Цикл 5
                    break;
                }
                case 5: { // Цикл 5
                    if ((d.j <= d.countVertex)) {
                        state = 6; // Шаг цикла 5
                    } else {
                        stack.pushBoolean(true); 
                        state = 2; // Цикл 4
                    }
                    break;
                }
                case 6: { // Шаг цикла 5
                    stack.pushBoolean(true); 
                    state = 5; // Цикл 5
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация цикла 4
                    startSection();
                    storeArray(d.w, 1);
                    				d.w[1] = true;
                    storeArray(d.st, 1);
                    				d.st[1] = 1;
                    storeField(d, "nstack");
                    				d.nstack = 1;
                    				d.m = 1;
                    break;
                }
                case 2: { // Цикл 4
                    break;
                }
                case 3: { // Шаг цикла 4
                    startSection();
                    storeField(d, "i");
                    					d.i = d.st[d.nstack];
                    					d.nstack = d.nstack - 1;
                    break;
                }
                case 4: { // Инициализация цикла 5
                    startSection();
                    storeField(d, "j");
                    					d.j = 1;
                    break;
                }
                case 5: { // Цикл 5
                    break;
                }
                case 6: { // Шаг цикла 5
                    startSection();
                    						if (!(d.w[d.j]) & (d.a[d.i][d.j] > 0)) {
                    storeArray(d.w, d.j);
                    							d.w[d.j] = true;
                    storeField(d, "nstack");
                    							d.nstack = d.nstack + 1;
                    storeArray(d.st, d.nstack);
                    							d.st[d.nstack] = d.j;
                    storeField(d, "m");
                    							d.m = d.m + 1;
                    						}
                    storeField(d, "j");
                    						d.j = d.j + 1;	
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
                case 1: { // Инициализация цикла 4
                    restoreSection();
                    break;
                }
                case 2: { // Цикл 4
                    break;
                }
                case 3: { // Шаг цикла 4
                    restoreSection();
                    break;
                }
                case 4: { // Инициализация цикла 5
                    restoreSection();
                    break;
                }
                case 5: { // Цикл 5
                    break;
                }
                case 6: { // Шаг цикла 5
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация цикла 4
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл 4
                    if (stack.popBoolean()) {
                        state = 5; // Цикл 5
                    } else {
                        state = 1; // Инициализация цикла 4
                    }
                    break;
                }
                case 3: { // Шаг цикла 4
                    state = 2; // Цикл 4
                    break;
                }
                case 4: { // Инициализация цикла 5
                    state = 3; // Шаг цикла 4
                    break;
                }
                case 5: { // Цикл 5
                    if (stack.popBoolean()) {
                        state = 6; // Шаг цикла 5
                    } else {
                        state = 4; // Инициализация цикла 5
                    }
                    break;
                }
                case 6: { // Шаг цикла 5
                    state = 5; // Цикл 5
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл 4
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
                case 1: { // Инициализация цикла 4
                    comment = Prim.this.getComment("CheckGraph.InitLoop4"); 
                    break;
                }
                case 3: { // Шаг цикла 4
                    comment = Prim.this.getComment("CheckGraph.StepLoop4"); 
                    break;
                }
                case 4: { // Инициализация цикла 5
                    comment = Prim.this.getComment("CheckGraph.InitLoop5"); 
                    break;
                }
                case 6: { // Шаг цикла 5
                    comment = Prim.this.getComment("CheckGraph.StepLoop5"); 
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
            }
        }
    }
}
