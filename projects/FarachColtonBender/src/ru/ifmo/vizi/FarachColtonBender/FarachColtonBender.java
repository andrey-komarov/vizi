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
          * Левая граница поиска.
          */
        public int left = 0;

        /**
          * Правая граница поиска.
          */
        public int right = 0;

        /**
          * Массив дла поиска.
          */
        public int[] h = null;

        /**
          * Текущая левая граница поиска.
          */
        public int l = 0;

        /**
          * Текущая правая граница поиска.
          */
        public int r = 0;

        /**
          * Текущий минимум.
          */
        public int a = 0;

        /**
          * Нижняя граница для элементов дерева.
          */
        public int lowCellsBound = 0;

        /**
          * Нижняя граница минимумов.
          */
        public int lowMinBound = 0;

        /**
          * Высота дерева.
          */
        public int height = 0;

        /**
          * Символ, обозначающий текущий минимум.
          */
        public String minim = \u0022\u221E\u0022;

        /**
          * Значок бесконечности.
          */
        public String infinity = \u0022\u221E\u0022;

        /**
          * Левый элемент массива.
          */
        public String tl = \u0022\u221E\u0022;

        /**
          * Правый элемент массива.
          */
        public String tr = \u0022\u221E\u0022;

        public String toString() {
            return "" + d.a;
        }
    }

    /**
      * Ищет минимум в массиве, используя Алгоритм Фараха-Колтона, Бендера.
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
                    "Инициализация", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Инициализация 
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
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Инициализация
                    startSection();
                    storeField(d, "lowCellsBound");
                    d.lowCellsBound = d.h.length / 2;
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
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Инициализация
                    state = START_STATE; 
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 1; // Инициализация
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
                    args = new Object[]{new Integer(0), new Integer(-1)}; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = FarachColtonBender.this.getComment("Main.END_STATE"); 
                    args = new Object[]{new Integer(d.a)}; 
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
                case END_STATE: { // Конечное состояние
                    	d.visualizer.drawCells(0, 0, 0, 0, 0, 0, true);
                    	d.visualizer.drawBrackets(d.l, d.r, false, true);
                    	d.visualizer.drawMin(true);
                    break;
                }
            }
        }
    }
}
