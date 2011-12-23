package ru.ifmo.vizi.long_int;

import ru.ifmo.vizi.base.auto.*;
import java.util.Locale;

public final class LongInteger extends BaseAutoReverseAutomata {
    /**
      * Модель данных.
      */
    public final Data d = new Data();

    /**
      * Конструктор для языка
      */
    public LongInteger(Locale locale) {
        super("ru.ifmo.vizi.long_int.Comments", locale); 
        init(new Main(), d); 
    }

    /**
      * Данные.
      */
    public final class Data {
        /**
          * Экземпляр апплета.
          */
        public LongIntegerVisualizer visualizer = null;

        /**
          * Массив для данных.
          */
        public int[][] store = new int[200][100];

        /**
          * Максимальная длинна.
          */
        public int maxL = 0;

        /**
          * Массив для длинн чисел.
          */
        public int[] storeL = new int[200];

        /**
          * Переменная для пропуска коментариев.
          */
        public int commentLevel = 10000;

        /**
          * Переменная цикла.
          */
        public int i = 0;

        /**
          * Переменная цикла.
          */
        public int j = 0;

        /**
          * Временная.
          */
        public int t = 0;

        /**
          * Копирование из этой строчки.
          */
        public int indFrom = 0;

        /**
          * Копирование в эту строчку.
          */
        public int indTo = 0;

        /**
          * Копирование начиная с.
          */
        public int indFirst = 0;

        /**
          * Копирование длинною.
          */
        public int indLength = 0;

        /**
          * Номер текущей строчки хранения данных в рекурсии.
          */
        public int storeInd = 0;

        /**
          * Русская Строка.
          */
        public String sRu = new String();

        /**
          * English String.
          */
        public String sEn = new String();

        /**
          * Значёк текущего действия.
          */
        public String smu = new String(new char[]{'+'});

        /**
          * Какое число больше.
          */
        public boolean firstBig = true;

        /**
          * Переменная остатка.
          */
        public int ost = 0;

        /**
          * Временная переменная остатка.
          */
        public int tempOst = 0;

        /**
          * Количество отрезаемых разрядов.
          */
        public int k = 0;

        /**
          * Конечная длинна (Процедура FastSum).
          */
        public int FastSum_leng;

        /**
          * Длинна числа (Процедура KarSum).
          */
        public int KarSum_leng;

        public String toString() {
            return "" + d.storeInd;
        }
    }

    /**
      * Складывает.
      */
    private final class Sum extends BaseAutomata implements Automata {
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
        public Sum() {
            super( 
                "Sum", 
                0, // Номер начального состояния 
                11, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "Следующий шаг", 
                    "Складываем разряды", 
                    "Записываем: t mod 10", 
                    "Записываем: k div 10", 
                    "Инкремент", 
                    "Если остаток не равен 0", 
                    "Если остаток не равен 0 (окончание)", 
                    "Результат: переполнение", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
                    1, // Следующий шаг 
                    0, // Складываем разряды 
                    0, // Записываем: t mod 10 
                    0, // Записываем: k div 10 
                    -1, // Инкремент 
                    -1, // Если остаток не равен 0 
                    -1, // Если остаток не равен 0 (окончание) 
                    0, // Результат: переполнение 
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
                    if (d.i < Math.max(d.storeL[0], d.storeL[1])) {
                        state = 3; // Следующий шаг
                    } else {
                        state = 8; // Если остаток не равен 0
                    }
                    break;
                }
                case 3: { // Следующий шаг
                    state = 4; // Складываем разряды
                    break;
                }
                case 4: { // Складываем разряды
                    state = 5; // Записываем: t mod 10
                    break;
                }
                case 5: { // Записываем: t mod 10
                    state = 6; // Записываем: k div 10
                    break;
                }
                case 6: { // Записываем: k div 10
                    state = 7; // Инкремент
                    break;
                }
                case 7: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
                case 8: { // Если остаток не равен 0
                    if (d.ost != 0) {
                        state = 10; // Результат: переполнение
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // Если остаток не равен 0 (окончание)
                    }
                    break;
                }
                case 9: { // Если остаток не равен 0 (окончание)
                    state = END_STATE; 
                    break;
                }
                case 10: { // Результат: переполнение
                    stack.pushBoolean(true); 
                    state = 9; // Если остаток не равен 0 (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "ost");
                    d.ost = 0;
                    storeField(d, "smu");
                    d.smu = "+";
                    storeField(d, "tempOst");
                    d.tempOst = 0;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Следующий шаг
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    break;
                }
                case 4: { // Складываем разряды
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = d.store[0][d.i] + d.store[1][d.i] + d.ost;
                    break;
                }
                case 5: { // Записываем: t mod 10
                    startSection();
                    storeArray(d.store[2], d.i);
                    d.store[2][d.i] = d.tempOst % 10;
                    storeField(d, "ost");
                    d.ost = -10000;
                    break;
                }
                case 6: { // Записываем: k div 10
                    startSection();
                    storeField(d, "ost");
                    d.ost = d.tempOst / 10;
                    break;
                }
                case 7: { // Инкремент
                    startSection();
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 8: { // Если остаток не равен 0
                    break;
                }
                case 9: { // Если остаток не равен 0 (окончание)
                    break;
                }
                case 10: { // Результат: переполнение
                    startSection();
                    storeField(d, "sRu");
                    d.sRu = d.sRu + " (переполнение)";
                    storeField(d, "sEn");
                    d.sEn = d.sEn + " (repletion)";
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
                case 3: { // Следующий шаг
                    restoreSection();
                    break;
                }
                case 4: { // Складываем разряды
                    restoreSection();
                    break;
                }
                case 5: { // Записываем: t mod 10
                    restoreSection();
                    break;
                }
                case 6: { // Записываем: k div 10
                    restoreSection();
                    break;
                }
                case 7: { // Инкремент
                    restoreSection();
                    break;
                }
                case 8: { // Если остаток не равен 0
                    break;
                }
                case 9: { // Если остаток не равен 0 (окончание)
                    break;
                }
                case 10: { // Результат: переполнение
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
                        state = 7; // Инкремент
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Следующий шаг
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Складываем разряды
                    state = 3; // Следующий шаг
                    break;
                }
                case 5: { // Записываем: t mod 10
                    state = 4; // Складываем разряды
                    break;
                }
                case 6: { // Записываем: k div 10
                    state = 5; // Записываем: t mod 10
                    break;
                }
                case 7: { // Инкремент
                    state = 6; // Записываем: k div 10
                    break;
                }
                case 8: { // Если остаток не равен 0
                    state = 2; // Цикл
                    break;
                }
                case 9: { // Если остаток не равен 0 (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Результат: переполнение
                    } else {
                        state = 8; // Если остаток не равен 0
                    }
                    break;
                }
                case 10: { // Результат: переполнение
                    state = 8; // Если остаток не равен 0
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 9; // Если остаток не равен 0 (окончание)
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
                case 3: { // Следующий шаг
                    comment = LongInteger.this.getComment("Sum.showItem"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.i + 1)}; 
                    break;
                }
                case 4: { // Складываем разряды
                    comment = LongInteger.this.getComment("Sum.sumShow"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.store[0][d.i] + d.store[1][d.i]), new Integer(d.tempOst)}; 
                    break;
                }
                case 5: { // Записываем: t mod 10
                    comment = LongInteger.this.getComment("Sum.sumWrite"); 
                    args = new Object[]{new Integer(d.tempOst % 10)}; 
                    break;
                }
                case 6: { // Записываем: k div 10
                    comment = LongInteger.this.getComment("Sum.remainderSumWrite"); 
                    args = new Object[]{new Integer(d.tempOst / 10)}; 
                    break;
                }
                case 10: { // Результат: переполнение
                    comment = LongInteger.this.getComment("Sum.showOverF"); 
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
                case 3: { // Следующий шаг
                    d.visualizer.showIntegers(d.i, d.i, -1, 1, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 4: { // Складываем разряды
                    d.visualizer.showIntegers(d.i, d.i, d.i, 1, 1, 0);
                    d.visualizer.showTemp(2, 1);
                    break;
                }
                case 5: { // Записываем: t mod 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 2);
                    d.visualizer.showTemp(1, 0);
                    break;
                }
                case 6: { // Записываем: k div 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 0);
                    d.visualizer.showTemp(1, 2);
                    break;
                }
                case 10: { // Результат: переполнение
                    d.visualizer.showIntegers(d.i, d.i, d.i, 0, 0, 0);
                    d.visualizer.showTemp(0, 2);
                    break;
                }
            }
        }
    }

    /**
      * Вычитает.
      */
    private final class Mines extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 8;

        /**
          * Конструктор.
          */
        public Mines() {
            super( 
                "Mines", 
                0, // Номер начального состояния 
                8, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "Следующий шаг", 
                    "Вычитание разрядов", 
                    "Записываем: t mod 10", 
                    "Записываем: t / 10", 
                    "Инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
                    1, // Следующий шаг 
                    0, // Вычитание разрядов 
                    0, // Записываем: t mod 10 
                    0, // Записываем: t / 10 
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
                    state = 1; // Начало цикла
                    break;
                }
                case 1: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.i < d.maxL) {
                        state = 3; // Следующий шаг
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Следующий шаг
                    state = 4; // Вычитание разрядов
                    break;
                }
                case 4: { // Вычитание разрядов
                    state = 5; // Записываем: t mod 10
                    break;
                }
                case 5: { // Записываем: t mod 10
                    state = 6; // Записываем: t / 10
                    break;
                }
                case 6: { // Записываем: t / 10
                    state = 7; // Инкремент
                    break;
                }
                case 7: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "-";
                    storeField(d, "ost");
                    d.ost = 0;
                    storeField(d, "tempOst");
                    d.tempOst = 0;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Следующий шаг
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    break;
                }
                case 4: { // Вычитание разрядов
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = d.store[0][d.i] - d.store[1][d.i] + d.ost;
                    break;
                }
                case 5: { // Записываем: t mod 10
                    startSection();
                    storeArray(d.store[2], d.i);
                    d.store[2][d.i] = (d.tempOst + 1000) % 10;
                    storeField(d, "ost");
                    d.ost = -10000;
                    break;
                }
                case 6: { // Записываем: t / 10
                    startSection();
                    storeField(d, "ost");
                    d.ost = (int)Math.floor(d.tempOst / 10.);
                    break;
                }
                case 7: { // Инкремент
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
                case 1: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Следующий шаг
                    restoreSection();
                    break;
                }
                case 4: { // Вычитание разрядов
                    restoreSection();
                    break;
                }
                case 5: { // Записываем: t mod 10
                    restoreSection();
                    break;
                }
                case 6: { // Записываем: t / 10
                    restoreSection();
                    break;
                }
                case 7: { // Инкремент
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
                        state = 7; // Инкремент
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Следующий шаг
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Вычитание разрядов
                    state = 3; // Следующий шаг
                    break;
                }
                case 5: { // Записываем: t mod 10
                    state = 4; // Вычитание разрядов
                    break;
                }
                case 6: { // Записываем: t / 10
                    state = 5; // Записываем: t mod 10
                    break;
                }
                case 7: { // Инкремент
                    state = 6; // Записываем: t / 10
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл
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
                case 3: { // Следующий шаг
                    comment = LongInteger.this.getComment("Mines.showItem"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.i + 1)}; 
                    break;
                }
                case 4: { // Вычитание разрядов
                    comment = LongInteger.this.getComment("Mines.mines"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.i]), new Integer(d.store[0][d.i] - d.store[1][d.i]), new Integer(d.tempOst)}; 
                    break;
                }
                case 5: { // Записываем: t mod 10
                    comment = LongInteger.this.getComment("Mines.minesWrite"); 
                    args = new Object[]{new Integer(d.store[2][d.i])}; 
                    break;
                }
                case 6: { // Записываем: t / 10
                    comment = LongInteger.this.getComment("Mines.remainderMinesWrite"); 
                    args = new Object[]{new Integer(d.ost)}; 
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
                case 3: { // Следующий шаг
                    d.visualizer.showIntegers(d.i, d.i, -1, 1, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 4: { // Вычитание разрядов
                    d.visualizer.showIntegers(d.i, d.i, d.i, 1, 1, 0);
                    d.visualizer.showTemp(2, 1);
                    break;
                }
                case 5: { // Записываем: t mod 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 2);
                    d.visualizer.showTemp(1, 0);
                    break;
                }
                case 6: { // Записываем: t / 10
                    d.visualizer.showIntegers(d.i, d.i, d.i, 3, 3, 0);
                    d.visualizer.showTemp(1, 2);
                    break;
                }
            }
        }
    }

    /**
      * Умножает.
      */
    private final class power extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 15;

        /**
          * Конструктор.
          */
        public power() {
            super( 
                "power", 
                0, // Номер начального состояния 
                15, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "Начало цикла", 
                    "Шаг по второму числу", 
                    "Цикл", 
                    "Шаг по первому числу", 
                    "Умножение разрядов", 
                    "Записываем: t mod 10", 
                    "Записываем: t div 10", 
                    "Инкремент", 
                    "Если остаток не равен нулю", 
                    "Если остаток не равен нулю (окончание)", 
                    "Записываем в следующий разряд", 
                    "Инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // Начало цикла 
                    1, // Шаг по второму числу 
                    -1, // Цикл 
                    0, // Шаг по первому числу 
                    0, // Умножение разрядов 
                    0, // Записываем: t mod 10 
                    0, // Записываем: t div 10 
                    -1, // Инкремент 
                    -1, // Если остаток не равен нулю 
                    -1, // Если остаток не равен нулю (окончание) 
                    0, // Записываем в следующий разряд 
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
                    state = 1; // Начало цикла
                    break;
                }
                case 1: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.j < d.storeL[1]) {
                        state = 3; // Начало цикла
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Начало цикла
                    state = 4; // Шаг по второму числу
                    break;
                }
                case 4: { // Шаг по второму числу
                    stack.pushBoolean(false); 
                    state = 5; // Цикл
                    break;
                }
                case 5: { // Цикл
                    if (d.i < d.storeL[0]) {
                        state = 6; // Шаг по первому числу
                    } else {
                        state = 11; // Если остаток не равен нулю
                    }
                    break;
                }
                case 6: { // Шаг по первому числу
                    state = 7; // Умножение разрядов
                    break;
                }
                case 7: { // Умножение разрядов
                    state = 8; // Записываем: t mod 10
                    break;
                }
                case 8: { // Записываем: t mod 10
                    state = 9; // Записываем: t div 10
                    break;
                }
                case 9: { // Записываем: t div 10
                    state = 10; // Инкремент
                    break;
                }
                case 10: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 5; // Цикл
                    break;
                }
                case 11: { // Если остаток не равен нулю
                    if (d.ost > 0) {
                        state = 13; // Записываем в следующий разряд
                    } else {
                        stack.pushBoolean(false); 
                        state = 12; // Если остаток не равен нулю (окончание)
                    }
                    break;
                }
                case 12: { // Если остаток не равен нулю (окончание)
                    state = 14; // Инкремент
                    break;
                }
                case 13: { // Записываем в следующий разряд
                    stack.pushBoolean(true); 
                    state = 12; // Если остаток не равен нулю (окончание)
                    break;
                }
                case 14: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "j");
                    d.j = 0;
                    storeField(d, "smu");
                    d.smu = "*";
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    break;
                }
                case 4: { // Шаг по второму числу
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    storeField(d, "ost");
                    d.ost = 0;
                    break;
                }
                case 5: { // Цикл
                    break;
                }
                case 6: { // Шаг по первому числу
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    break;
                }
                case 7: { // Умножение разрядов
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = d.store[0][d.i] * d.store[1][d.j] + d.store[2][d.i + d.j] + d.ost;
                    break;
                }
                case 8: { // Записываем: t mod 10
                    startSection();
                    storeArray(d.store[2], d.i + d.j);
                    d.store[2][d.i + d.j] = d.tempOst % 10;
                    storeField(d, "ost");
                    d.ost = -10000;
                    break;
                }
                case 9: { // Записываем: t div 10
                    startSection();
                    storeField(d, "ost");
                    d.ost = d.tempOst / 10;
                    break;
                }
                case 10: { // Инкремент
                    startSection();
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 11: { // Если остаток не равен нулю
                    break;
                }
                case 12: { // Если остаток не равен нулю (окончание)
                    break;
                }
                case 13: { // Записываем в следующий разряд
                    startSection();
                    storeField(d, "tempOst");
                    d.tempOst = -10000;
                    storeField(d, "i");
                    d.i = d.i - 1;
                    storeArray(d.store[2], d.i + d.j + 1);
                    d.store[2][d.i + d.j + 1] = d.ost;
                    break;
                }
                case 14: { // Инкремент
                    startSection();
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
                case 1: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 4: { // Шаг по второму числу
                    restoreSection();
                    break;
                }
                case 5: { // Цикл
                    break;
                }
                case 6: { // Шаг по первому числу
                    restoreSection();
                    break;
                }
                case 7: { // Умножение разрядов
                    restoreSection();
                    break;
                }
                case 8: { // Записываем: t mod 10
                    restoreSection();
                    break;
                }
                case 9: { // Записываем: t div 10
                    restoreSection();
                    break;
                }
                case 10: { // Инкремент
                    restoreSection();
                    break;
                }
                case 11: { // Если остаток не равен нулю
                    break;
                }
                case 12: { // Если остаток не равен нулю (окончание)
                    break;
                }
                case 13: { // Записываем в следующий разряд
                    restoreSection();
                    break;
                }
                case 14: { // Инкремент
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
                        state = 14; // Инкремент
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Начало цикла
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Шаг по второму числу
                    state = 3; // Начало цикла
                    break;
                }
                case 5: { // Цикл
                    if (stack.popBoolean()) {
                        state = 10; // Инкремент
                    } else {
                        state = 4; // Шаг по второму числу
                    }
                    break;
                }
                case 6: { // Шаг по первому числу
                    state = 5; // Цикл
                    break;
                }
                case 7: { // Умножение разрядов
                    state = 6; // Шаг по первому числу
                    break;
                }
                case 8: { // Записываем: t mod 10
                    state = 7; // Умножение разрядов
                    break;
                }
                case 9: { // Записываем: t div 10
                    state = 8; // Записываем: t mod 10
                    break;
                }
                case 10: { // Инкремент
                    state = 9; // Записываем: t div 10
                    break;
                }
                case 11: { // Если остаток не равен нулю
                    state = 5; // Цикл
                    break;
                }
                case 12: { // Если остаток не равен нулю (окончание)
                    if (stack.popBoolean()) {
                        state = 13; // Записываем в следующий разряд
                    } else {
                        state = 11; // Если остаток не равен нулю
                    }
                    break;
                }
                case 13: { // Записываем в следующий разряд
                    state = 11; // Если остаток не равен нулю
                    break;
                }
                case 14: { // Инкремент
                    state = 12; // Если остаток не равен нулю (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл
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
                case 4: { // Шаг по второму числу
                    comment = LongInteger.this.getComment("power.show3"); 
                    args = new Object[]{new Integer(d.j + 1)}; 
                    break;
                }
                case 6: { // Шаг по первому числу
                    comment = LongInteger.this.getComment("power.show2"); 
                    args = new Object[]{new Integer(d.i + 1)}; 
                    break;
                }
                case 7: { // Умножение разрядов
                    comment = LongInteger.this.getComment("power.poow"); 
                    args = new Object[]{new Integer(d.store[0][d.i]), new Integer(d.store[1][d.j]), new Integer(d.store[0][d.i] * d.store[1][d.j]), new Integer(d.i + d.j + 1), new Integer(d.tempOst)}; 
                    break;
                }
                case 8: { // Записываем: t mod 10
                    comment = LongInteger.this.getComment("power.poowWrite"); 
                    args = new Object[]{new Integer(d.store[2][d.i + d.j])}; 
                    break;
                }
                case 9: { // Записываем: t div 10
                    comment = LongInteger.this.getComment("power.poowRemainderWrite"); 
                    args = new Object[]{new Integer(d.ost)}; 
                    break;
                }
                case 13: { // Записываем в следующий разряд
                    comment = LongInteger.this.getComment("power.writeRemainderpower"); 
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
                case 4: { // Шаг по второму числу
                    d.visualizer.showIntegers(-1, d.j, -1, 0, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 6: { // Шаг по первому числу
                    d.visualizer.showIntegers(d.i, d.j, -1, 1, 1, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 7: { // Умножение разрядов
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j, 1, 1, 1);
                    d.visualizer.showTemp(2, 1);
                    break;
                }
                case 8: { // Записываем: t mod 10
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j, 3, 3, 2);
                    d.visualizer.showTemp(1, 0);
                    break;
                }
                case 9: { // Записываем: t div 10
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j, 3, 3, 0);
                    d.visualizer.showTemp(1, 2);
                    break;
                }
                case 13: { // Записываем в следующий разряд
                    d.visualizer.showIntegers(d.i, d.j, d.i + d.j + 1, 3, 3, 2);
                    d.visualizer.showTemp(0, 1);
                    break;
                }
            }
        }
    }

    /**
      * Проверяет какое число по модулю больше.
      */
    private final class testFirstBig extends BaseAutomata implements Automata {
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
        public testFirstBig() {
            super( 
                "testFirstBig", 
                0, // Номер начального состояния 
                10, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "Если вторая цифра больше", 
                    "Если вторая цифра больше (окончание)", 
                    "Второе число больше", 
                    "Если первая цифра больше", 
                    "Если первая цифра больше (окончание)", 
                    "Первое число больше", 
                    "Инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // Если вторая цифра больше 
                    -1, // Если вторая цифра больше (окончание) 
                    -1, // Второе число больше 
                    -1, // Если первая цифра больше 
                    -1, // Если первая цифра больше (окончание) 
                    -1, // Первое число больше 
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
                    state = 1; // Начало цикла
                    break;
                }
                case 1: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.i >= 0) {
                        state = 3; // Если вторая цифра больше
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Если вторая цифра больше
                    if (d.store[0][d.i] < d.store[1][d.i]) {
                        state = 5; // Второе число больше
                    } else {
                        state = 6; // Если первая цифра больше
                    }
                    break;
                }
                case 4: { // Если вторая цифра больше (окончание)
                    state = 9; // Инкремент
                    break;
                }
                case 5: { // Второе число больше
                    stack.pushBoolean(true); 
                    state = 4; // Если вторая цифра больше (окончание)
                    break;
                }
                case 6: { // Если первая цифра больше
                    if (d.store[0][d.i] > d.store[1][d.i]) {
                        state = 8; // Первое число больше
                    } else {
                        stack.pushBoolean(false); 
                        state = 7; // Если первая цифра больше (окончание)
                    }
                    break;
                }
                case 7: { // Если первая цифра больше (окончание)
                    stack.pushBoolean(false); 
                    state = 4; // Если вторая цифра больше (окончание)
                    break;
                }
                case 8: { // Первое число больше
                    stack.pushBoolean(true); 
                    state = 7; // Если первая цифра больше (окончание)
                    break;
                }
                case 9: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = d.store[0].length / 2 - 1;
                    storeField(d, "firstBig");
                    d.firstBig = true;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Если вторая цифра больше
                    break;
                }
                case 4: { // Если вторая цифра больше (окончание)
                    break;
                }
                case 5: { // Второе число больше
                    startSection();
                    storeField(d, "firstBig");
                    d.firstBig = false; 
                    storeField(d, "i");
                    d.i = -1;
                    break;
                }
                case 6: { // Если первая цифра больше
                    break;
                }
                case 7: { // Если первая цифра больше (окончание)
                    break;
                }
                case 8: { // Первое число больше
                    startSection();
                    storeField(d, "i");
                    d.i = -1;
                    break;
                }
                case 9: { // Инкремент
                    startSection();
                    storeField(d, "i");
                    d.i = d.i - 1;
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
                case 3: { // Если вторая цифра больше
                    break;
                }
                case 4: { // Если вторая цифра больше (окончание)
                    break;
                }
                case 5: { // Второе число больше
                    restoreSection();
                    break;
                }
                case 6: { // Если первая цифра больше
                    break;
                }
                case 7: { // Если первая цифра больше (окончание)
                    break;
                }
                case 8: { // Первое число больше
                    restoreSection();
                    break;
                }
                case 9: { // Инкремент
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
                        state = 9; // Инкремент
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Если вторая цифра больше
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Если вторая цифра больше (окончание)
                    if (stack.popBoolean()) {
                        state = 5; // Второе число больше
                    } else {
                        state = 7; // Если первая цифра больше (окончание)
                    }
                    break;
                }
                case 5: { // Второе число больше
                    state = 3; // Если вторая цифра больше
                    break;
                }
                case 6: { // Если первая цифра больше
                    state = 3; // Если вторая цифра больше
                    break;
                }
                case 7: { // Если первая цифра больше (окончание)
                    if (stack.popBoolean()) {
                        state = 8; // Первое число больше
                    } else {
                        state = 6; // Если первая цифра больше
                    }
                    break;
                }
                case 8: { // Первое число больше
                    state = 6; // Если первая цифра больше
                    break;
                }
                case 9: { // Инкремент
                    state = 4; // Если вторая цифра больше (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл
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
      * Меняет числа местами.
      */
    private final class Replace extends BaseAutomata implements Automata {
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
        public Replace() {
            super( 
                "Replace", 
                0, // Номер начального состояния 
                4, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "Инкремент", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
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
                    state = 1; // Начало цикла
                    break;
                }
                case 1: { // Начало цикла
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.i < d.store[0].length) {
                        state = 3; // Инкремент
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Инкремент
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Инкремент
                    startSection();
                    storeField(d, "t");
                    d.t = d.store[0][d.i];
                    storeArray(d.store[0], d.i);
                    d.store[0][d.i] = d.store[1][d.i];
                    storeArray(d.store[1], d.i);
                    d.store[1][d.i] = d.t;
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
                case 1: { // Начало цикла
                    restoreSection();
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Инкремент
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
                        state = 3; // Инкремент
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Инкремент
                    state = 2; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл
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
      * Очищает содержимое (storeInd + 10)..(storeInd + 15).
      */
    private final class SuperClear extends BaseAutomata implements Automata {
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
        public SuperClear() {
            super( 
                "SuperClear", 
                0, // Номер начального состояния 
                5, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "цикл", 
                    "Очищает содержимое indTo (автомат)", 
                    "Шаг цикла", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // цикл 
                    CALL_AUTO_LEVEL, // Очищает содержимое indTo (автомат) 
                    -1, // Шаг цикла 
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
                    state = 2; // цикл
                    break;
                }
                case 2: { // цикл
                    if (d.i < (d.storeInd + 16)) {
                        state = 3; // Очищает содержимое indTo (автомат)
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Очищает содержимое indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 4; // Шаг цикла
                    }
                    break;
                }
                case 4: { // Шаг цикла
                    stack.pushBoolean(true); 
                    state = 2; // цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 10;
                    storeField(d, "i");
                    d.i = d.indTo;
                    break;
                }
                case 2: { // цикл
                    break;
                }
                case 3: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 4: { // Шаг цикла
                    startSection();
                    storeArray(d.storeL, d.i);
                    d.storeL[d.i] = 1;
                    storeField(d, "i");
                    d.i = d.i + 1;
                    storeField(d, "indTo");
                    d.indTo = d.i;
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
                case 2: { // цикл
                    break;
                }
                case 3: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 4: { // Шаг цикла
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
                case 2: { // цикл
                    if (stack.popBoolean()) {
                        state = 4; // Шаг цикла
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Очищает содержимое indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 2; // цикл
                    }
                    break;
                }
                case 4: { // Шаг цикла
                    state = 3; // Очищает содержимое indTo (автомат)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // цикл
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
                case 3: { // Очищает содержимое indTo (автомат)
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
                case 3: { // Очищает содержимое indTo (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Очищает содержимое indTo.
      */
    private final class Clear extends BaseAutomata implements Automata {
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
        public Clear() {
            super( 
                "Clear", 
                0, // Номер начального состояния 
                4, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Цикл", 
                    "Шаг цикла", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // Шаг цикла 
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
                    if (d.t < d.store[d.indFrom].length) {
                        state = 3; // Шаг цикла
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 3: { // Шаг цикла
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "t");
                    d.t = 0;
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = 0;
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Шаг цикла
                    startSection();
                     storeArray(d.store[d.indTo], d.t);
                     d.store[d.indTo][d.t] = 0; d.t += 1;
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
                case 3: { // Шаг цикла
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
                        state = 3; // Шаг цикла
                    } else {
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Шаг цикла
                    state = 2; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 2; // Цикл
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
      * Копирует число из indFrom в indTo.
      */
    private final class Copy extends BaseAutomata implements Automata {
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
        public Copy() {
            super( 
                "Copy", 
                0, // Номер начального состояния 
                5, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Очищает содержимое indTo (автомат)", 
                    "Начало цикла", 
                    "Цикл", 
                    "Шаг цикла", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Очищает содержимое indTo (автомат) 
                    -1, // Начало цикла 
                    -1, // Цикл 
                    -1, // Шаг цикла 
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
                    state = 1; // Очищает содержимое indTo (автомат)
                    break;
                }
                case 1: { // Очищает содержимое indTo (автомат)
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
                    if (d.i < d.indLength) {
                        state = 4; // Шаг цикла
                    } else {
                        state = END_STATE; 
                    }
                    break;
                }
                case 4: { // Шаг цикла
                    stack.pushBoolean(true); 
                    state = 3; // Цикл
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = d.indLength;
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Шаг цикла
                    startSection();
                     storeArray(d.store[d.indTo], d.i);
                     d.store[d.indTo][d.i] = d.store[d.indFrom][d.i + d.indFirst]; d.i += 1;
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
                case 1: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
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
                case 4: { // Шаг цикла
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Очищает содержимое indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Начало цикла
                    state = 1; // Очищает содержимое indTo (автомат)
                    break;
                }
                case 3: { // Цикл
                    if (stack.popBoolean()) {
                        state = 4; // Шаг цикла
                    } else {
                        state = 2; // Начало цикла
                    }
                    break;
                }
                case 4: { // Шаг цикла
                    state = 3; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 3; // Цикл
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
                case 1: { // Очищает содержимое indTo (автомат)
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
                case 1: { // Очищает содержимое indTo (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Быстро складывает и вычитает до ответа.
      */
    private final class FastSum extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 25;

        /**
          * Конструктор.
          */
        public FastSum() {
            super( 
                "FastSum", 
                0, // Номер начального состояния 
                25, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Очищает содержимое indTo (автомат)", 
                    "Цикл", 
                    "Цифра1", 
                    "Цифра1 (окончание)", 
                    "Прибавляем", 
                    "Цифра2", 
                    "Цифра2 (окончание)", 
                    "Прибавляем", 
                    "Цифра3", 
                    "Цифра3 (окончание)", 
                    "Прибавляем", 
                    "Цифра4", 
                    "Цифра4 (окончание)", 
                    "Прибавляем", 
                    "Инкремент", 
                    "Проверка длинны", 
                    "Проверка длинны (окончание)", 
                    "Запись длинны", 
                    "Если больше 9", 
                    "Уменьшаем", 
                    "Если меньше 0", 
                    "Увеличиваем", 
                    "Присвоение длинны", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    CALL_AUTO_LEVEL, // Очищает содержимое indTo (автомат) 
                    -1, // Цикл 
                    -1, // Цифра1 
                    -1, // Цифра1 (окончание) 
                    -1, // Прибавляем 
                    -1, // Цифра2 
                    -1, // Цифра2 (окончание) 
                    -1, // Прибавляем 
                    -1, // Цифра3 
                    -1, // Цифра3 (окончание) 
                    -1, // Прибавляем 
                    -1, // Цифра4 
                    -1, // Цифра4 (окончание) 
                    -1, // Прибавляем 
                    -1, // Инкремент 
                    -1, // Проверка длинны 
                    -1, // Проверка длинны (окончание) 
                    -1, // Запись длинны 
                    -1, // Если больше 9 
                    -1, // Уменьшаем 
                    -1, // Если меньше 0 
                    -1, // Увеличиваем 
                    -1, // Присвоение длинны 
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
                    state = 2; // Очищает содержимое indTo (автомат)
                    break;
                }
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // Цикл
                    }
                    break;
                }
                case 3: { // Цикл
                    if (d.i < d.store[0].length) {
                        state = 4; // Цифра1
                    } else {
                        state = 24; // Присвоение длинны
                    }
                    break;
                }
                case 4: { // Цифра1
                    if ((d.i - 2 * d.k) >= 0) {
                        state = 6; // Прибавляем
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // Цифра1 (окончание)
                    }
                    break;
                }
                case 5: { // Цифра1 (окончание)
                    state = 7; // Цифра2
                    break;
                }
                case 6: { // Прибавляем
                    stack.pushBoolean(true); 
                    state = 5; // Цифра1 (окончание)
                    break;
                }
                case 7: { // Цифра2
                    if ((d.i - d.k) >= 0) {
                        state = 9; // Прибавляем
                    } else {
                        stack.pushBoolean(false); 
                        state = 8; // Цифра2 (окончание)
                    }
                    break;
                }
                case 8: { // Цифра2 (окончание)
                    state = 10; // Цифра3
                    break;
                }
                case 9: { // Прибавляем
                    stack.pushBoolean(true); 
                    state = 8; // Цифра2 (окончание)
                    break;
                }
                case 10: { // Цифра3
                    if ((d.i - d.k) >= 0) {
                        state = 12; // Прибавляем
                    } else {
                        stack.pushBoolean(false); 
                        state = 11; // Цифра3 (окончание)
                    }
                    break;
                }
                case 11: { // Цифра3 (окончание)
                    state = 13; // Цифра4
                    break;
                }
                case 12: { // Прибавляем
                    stack.pushBoolean(true); 
                    state = 11; // Цифра3 (окончание)
                    break;
                }
                case 13: { // Цифра4
                    if ((d.i - d.k) >= 0) {
                        state = 15; // Прибавляем
                    } else {
                        stack.pushBoolean(false); 
                        state = 14; // Цифра4 (окончание)
                    }
                    break;
                }
                case 14: { // Цифра4 (окончание)
                    state = 16; // Инкремент
                    break;
                }
                case 15: { // Прибавляем
                    stack.pushBoolean(true); 
                    state = 14; // Цифра4 (окончание)
                    break;
                }
                case 16: { // Инкремент
                    state = 17; // Проверка длинны
                    break;
                }
                case 17: { // Проверка длинны
                    if (d.store[d.indTo][d.i - 1] != 0) {
                        state = 19; // Запись длинны
                    } else {
                        stack.pushBoolean(false); 
                        state = 18; // Проверка длинны (окончание)
                    }
                    break;
                }
                case 18: { // Проверка длинны (окончание)
                    stack.pushBoolean(false); 
                    state = 20; // Если больше 9
                    break;
                }
                case 19: { // Запись длинны
                    stack.pushBoolean(true); 
                    state = 18; // Проверка длинны (окончание)
                    break;
                }
                case 20: { // Если больше 9
                    if (d.store[d.indTo][d.i - 1] > 9) {
                        state = 21; // Уменьшаем
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // Если меньше 0
                    }
                    break;
                }
                case 21: { // Уменьшаем
                    stack.pushBoolean(true); 
                    state = 20; // Если больше 9
                    break;
                }
                case 22: { // Если меньше 0
                    if (d.store[d.indTo][d.i - 1] < 0) {
                        state = 23; // Увеличиваем
                    } else {
                        stack.pushBoolean(true); 
                        state = 3; // Цикл
                    }
                    break;
                }
                case 23: { // Увеличиваем
                    stack.pushBoolean(true); 
                    state = 22; // Если меньше 0
                    break;
                }
                case 24: { // Присвоение длинны
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "FastSum_leng");
                    d.FastSum_leng = 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 2;
                    break;
                }
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Цифра1
                    break;
                }
                case 5: { // Цифра1 (окончание)
                    break;
                }
                case 6: { // Прибавляем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 3][d.i - 2 * d.k];
                    break;
                }
                case 7: { // Цифра2
                    break;
                }
                case 8: { // Цифра2 (окончание)
                    break;
                }
                case 9: { // Прибавляем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] - d.store[d.storeInd + 3][d.i - d.k];
                    break;
                }
                case 10: { // Цифра3
                    break;
                }
                case 11: { // Цифра3 (окончание)
                    break;
                }
                case 12: { // Прибавляем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 6][d.i - d.k];
                    break;
                }
                case 13: { // Цифра4
                    break;
                }
                case 14: { // Цифра4 (окончание)
                    break;
                }
                case 15: { // Прибавляем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] - d.store[d.storeInd + 7][d.i - d.k];
                    break;
                }
                case 16: { // Инкремент
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 7][d.i];
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 17: { // Проверка длинны
                    break;
                }
                case 18: { // Проверка длинны (окончание)
                    break;
                }
                case 19: { // Запись длинны
                    startSection();
                    storeField(d, "FastSum_leng");
                    d.FastSum_leng = d.i;
                    break;
                }
                case 20: { // Если больше 9
                    break;
                }
                case 21: { // Уменьшаем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + 1;
                    storeArray(d.store[d.indTo], d.i - 1);
                    d.store[d.indTo][d.i - 1] = d.store[d.indTo][d.i - 1] - 10;
                    break;
                }
                case 22: { // Если меньше 0
                    break;
                }
                case 23: { // Увеличиваем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] - 1;
                    storeArray(d.store[d.indTo], d.i - 1);
                    d.store[d.indTo][d.i - 1] = d.store[d.indTo][d.i - 1] + 10;
                    break;
                }
                case 24: { // Присвоение длинны
                    startSection();
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = d.FastSum_leng;
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
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Цифра1
                    break;
                }
                case 5: { // Цифра1 (окончание)
                    break;
                }
                case 6: { // Прибавляем
                    restoreSection();
                    break;
                }
                case 7: { // Цифра2
                    break;
                }
                case 8: { // Цифра2 (окончание)
                    break;
                }
                case 9: { // Прибавляем
                    restoreSection();
                    break;
                }
                case 10: { // Цифра3
                    break;
                }
                case 11: { // Цифра3 (окончание)
                    break;
                }
                case 12: { // Прибавляем
                    restoreSection();
                    break;
                }
                case 13: { // Цифра4
                    break;
                }
                case 14: { // Цифра4 (окончание)
                    break;
                }
                case 15: { // Прибавляем
                    restoreSection();
                    break;
                }
                case 16: { // Инкремент
                    restoreSection();
                    break;
                }
                case 17: { // Проверка длинны
                    break;
                }
                case 18: { // Проверка длинны (окончание)
                    break;
                }
                case 19: { // Запись длинны
                    restoreSection();
                    break;
                }
                case 20: { // Если больше 9
                    break;
                }
                case 21: { // Уменьшаем
                    restoreSection();
                    break;
                }
                case 22: { // Если меньше 0
                    break;
                }
                case 23: { // Увеличиваем
                    restoreSection();
                    break;
                }
                case 24: { // Присвоение длинны
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
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Цикл
                    if (stack.popBoolean()) {
                        state = 22; // Если меньше 0
                    } else {
                        state = 2; // Очищает содержимое indTo (автомат)
                    }
                    break;
                }
                case 4: { // Цифра1
                    state = 3; // Цикл
                    break;
                }
                case 5: { // Цифра1 (окончание)
                    if (stack.popBoolean()) {
                        state = 6; // Прибавляем
                    } else {
                        state = 4; // Цифра1
                    }
                    break;
                }
                case 6: { // Прибавляем
                    state = 4; // Цифра1
                    break;
                }
                case 7: { // Цифра2
                    state = 5; // Цифра1 (окончание)
                    break;
                }
                case 8: { // Цифра2 (окончание)
                    if (stack.popBoolean()) {
                        state = 9; // Прибавляем
                    } else {
                        state = 7; // Цифра2
                    }
                    break;
                }
                case 9: { // Прибавляем
                    state = 7; // Цифра2
                    break;
                }
                case 10: { // Цифра3
                    state = 8; // Цифра2 (окончание)
                    break;
                }
                case 11: { // Цифра3 (окончание)
                    if (stack.popBoolean()) {
                        state = 12; // Прибавляем
                    } else {
                        state = 10; // Цифра3
                    }
                    break;
                }
                case 12: { // Прибавляем
                    state = 10; // Цифра3
                    break;
                }
                case 13: { // Цифра4
                    state = 11; // Цифра3 (окончание)
                    break;
                }
                case 14: { // Цифра4 (окончание)
                    if (stack.popBoolean()) {
                        state = 15; // Прибавляем
                    } else {
                        state = 13; // Цифра4
                    }
                    break;
                }
                case 15: { // Прибавляем
                    state = 13; // Цифра4
                    break;
                }
                case 16: { // Инкремент
                    state = 14; // Цифра4 (окончание)
                    break;
                }
                case 17: { // Проверка длинны
                    state = 16; // Инкремент
                    break;
                }
                case 18: { // Проверка длинны (окончание)
                    if (stack.popBoolean()) {
                        state = 19; // Запись длинны
                    } else {
                        state = 17; // Проверка длинны
                    }
                    break;
                }
                case 19: { // Запись длинны
                    state = 17; // Проверка длинны
                    break;
                }
                case 20: { // Если больше 9
                    if (stack.popBoolean()) {
                        state = 21; // Уменьшаем
                    } else {
                        state = 18; // Проверка длинны (окончание)
                    }
                    break;
                }
                case 21: { // Уменьшаем
                    state = 20; // Если больше 9
                    break;
                }
                case 22: { // Если меньше 0
                    if (stack.popBoolean()) {
                        state = 23; // Увеличиваем
                    } else {
                        state = 20; // Если больше 9
                    }
                    break;
                }
                case 23: { // Увеличиваем
                    state = 22; // Если меньше 0
                    break;
                }
                case 24: { // Присвоение длинны
                    state = 3; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 24; // Присвоение длинны
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
                case 2: { // Очищает содержимое indTo (автомат)
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
                case 2: { // Очищает содержимое indTo (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Быстро складывает 2 числа.
      */
    private final class KarSum extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 12;

        /**
          * Конструктор.
          */
        public KarSum() {
            super( 
                "KarSum", 
                0, // Номер начального состояния 
                12, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Начало цикла", 
                    "Очищает содержимое indTo (автомат)", 
                    "Цикл", 
                    "Шаг цикла", 
                    "Проверка длинны", 
                    "Проверка длинны (окончание)", 
                    "Запись длинны", 
                    "Если больше 9", 
                    "Если больше 9 (окончание)", 
                    "Уменьшаем", 
                    "Присвоение длинны", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Начало цикла 
                    CALL_AUTO_LEVEL, // Очищает содержимое indTo (автомат) 
                    -1, // Цикл 
                    -1, // Шаг цикла 
                    -1, // Проверка длинны 
                    -1, // Проверка длинны (окончание) 
                    -1, // Запись длинны 
                    -1, // Если больше 9 
                    -1, // Если больше 9 (окончание) 
                    -1, // Уменьшаем 
                    -1, // Присвоение длинны 
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
                    state = 2; // Очищает содержимое indTo (автомат)
                    break;
                }
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(false); 
                        state = 3; // Цикл
                    }
                    break;
                }
                case 3: { // Цикл
                    if (d.i < d.store[0].length) {
                        state = 4; // Шаг цикла
                    } else {
                        state = 11; // Присвоение длинны
                    }
                    break;
                }
                case 4: { // Шаг цикла
                    state = 5; // Проверка длинны
                    break;
                }
                case 5: { // Проверка длинны
                    if (d.store[d.indTo][d.i - 1] != 0) {
                        state = 7; // Запись длинны
                    } else {
                        stack.pushBoolean(false); 
                        state = 6; // Проверка длинны (окончание)
                    }
                    break;
                }
                case 6: { // Проверка длинны (окончание)
                    state = 8; // Если больше 9
                    break;
                }
                case 7: { // Запись длинны
                    stack.pushBoolean(true); 
                    state = 6; // Проверка длинны (окончание)
                    break;
                }
                case 8: { // Если больше 9
                    if (d.store[d.indTo][d.i - 1] > 9) {
                        state = 10; // Уменьшаем
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // Если больше 9 (окончание)
                    }
                    break;
                }
                case 9: { // Если больше 9 (окончание)
                    stack.pushBoolean(true); 
                    state = 3; // Цикл
                    break;
                }
                case 10: { // Уменьшаем
                    stack.pushBoolean(true); 
                    state = 9; // Если больше 9 (окончание)
                    break;
                }
                case 11: { // Присвоение длинны
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Начало цикла
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "KarSum_leng");
                    d.KarSum_leng = 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 10;
                    break;
                }
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Шаг цикла
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.storeInd + 8][d.i] + d.store[d.storeInd + 9][d.i];
                    storeField(d, "i");
                    d.i = d.i + 1;
                    break;
                }
                case 5: { // Проверка длинны
                    break;
                }
                case 6: { // Проверка длинны (окончание)
                    break;
                }
                case 7: { // Запись длинны
                    startSection();
                    storeField(d, "KarSum_leng");
                    d.KarSum_leng = d.i;
                    break;
                }
                case 8: { // Если больше 9
                    break;
                }
                case 9: { // Если больше 9 (окончание)
                    break;
                }
                case 10: { // Уменьшаем
                    startSection();
                    storeArray(d.store[d.indTo], d.i);
                    d.store[d.indTo][d.i] = d.store[d.indTo][d.i] + d.store[d.indTo][d.i - 1] / 10;
                    storeArray(d.store[d.indTo], d.i - 1);
                    d.store[d.indTo][d.i - 1] = d.store[d.indTo][d.i - 1] % 10;
                    break;
                }
                case 11: { // Присвоение длинны
                    startSection();
                    storeArray(d.storeL, d.indTo);
                    d.storeL[d.indTo] = d.KarSum_leng;
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
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child == null) {
                        child = new Clear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 3: { // Цикл
                    break;
                }
                case 4: { // Шаг цикла
                    restoreSection();
                    break;
                }
                case 5: { // Проверка длинны
                    break;
                }
                case 6: { // Проверка длинны (окончание)
                    break;
                }
                case 7: { // Запись длинны
                    restoreSection();
                    break;
                }
                case 8: { // Если больше 9
                    break;
                }
                case 9: { // Если больше 9 (окончание)
                    break;
                }
                case 10: { // Уменьшаем
                    restoreSection();
                    break;
                }
                case 11: { // Присвоение длинны
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
                case 2: { // Очищает содержимое indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 1; // Начало цикла
                    }
                    break;
                }
                case 3: { // Цикл
                    if (stack.popBoolean()) {
                        state = 9; // Если больше 9 (окончание)
                    } else {
                        state = 2; // Очищает содержимое indTo (автомат)
                    }
                    break;
                }
                case 4: { // Шаг цикла
                    state = 3; // Цикл
                    break;
                }
                case 5: { // Проверка длинны
                    state = 4; // Шаг цикла
                    break;
                }
                case 6: { // Проверка длинны (окончание)
                    if (stack.popBoolean()) {
                        state = 7; // Запись длинны
                    } else {
                        state = 5; // Проверка длинны
                    }
                    break;
                }
                case 7: { // Запись длинны
                    state = 5; // Проверка длинны
                    break;
                }
                case 8: { // Если больше 9
                    state = 6; // Проверка длинны (окончание)
                    break;
                }
                case 9: { // Если больше 9 (окончание)
                    if (stack.popBoolean()) {
                        state = 10; // Уменьшаем
                    } else {
                        state = 8; // Если больше 9
                    }
                    break;
                }
                case 10: { // Уменьшаем
                    state = 8; // Если больше 9
                    break;
                }
                case 11: { // Присвоение длинны
                    state = 3; // Цикл
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 11; // Присвоение длинны
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
                case 2: { // Очищает содержимое indTo (автомат)
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
                case 2: { // Очищает содержимое indTo (автомат)
                    child.drawState(); 
                    break;
                }
            }
        }
    }

    /**
      * Умножает Карацубой.
      */
    private final class Karpower extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 59;

        /**
          * Конструктор.
          */
        public Karpower() {
            super( 
                "Karpower", 
                0, // Номер начального состояния 
                59, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)", 
                    "Начало умножения", 
                    "Визуализирование", 
                    "Если нельзя умножить быстро", 
                    "Если нельзя умножить быстро (окончание)", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Готовим А", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим С", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Умножает Карацубой (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Готовим В", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим D", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Умножает Карацубой (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Готовим А", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим В", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Быстро складывает 2 числа (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим С", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим D", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Быстро складывает 2 числа (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Готовим А + В", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим С + D", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Умножает Карацубой (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Готовимся складывать", 
                    "Быстро складывает и вычитает до ответа (автомат)", 
                    "Визуализирование", 
                    "Умножаем по простому", 
                    "Если больше 9", 
                    "Если больше 9 (окончание)", 
                    "Уменьшаем", 
                    "Визуализирование", 
                    "Завершение", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат) 
                    -1, // Начало умножения 
                    0, // Визуализирование 
                    -1, // Если нельзя умножить быстро 
                    -1, // Если нельзя умножить быстро (окончание) 
                    0, // Визуализирование 
                    0, // Визуализирование 
                    -1, // Готовим А 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим С 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    0, // Визуализирование 
                    0, // Визуализирование 
                    -1, // Готовим В 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим D 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    0, // Визуализирование 
                    -1, // Готовим А 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим В 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Быстро складывает 2 числа (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим С 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим D 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Быстро складывает 2 числа (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    0, // Визуализирование 
                    -1, // Готовим А + В 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим С + D 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    0, // Визуализирование 
                    0, // Визуализирование 
                    0, // Визуализирование 
                    -1, // Готовимся складывать 
                    CALL_AUTO_LEVEL, // Быстро складывает и вычитает до ответа (автомат) 
                    0, // Визуализирование 
                    -1, // Умножаем по простому 
                    -1, // Если больше 9 
                    -1, // Если больше 9 (окончание) 
                    -1, // Уменьшаем 
                    0, // Визуализирование 
                    -1, // Завершение 
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
                    state = 1; // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    break;
                }
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // Начало умножения
                    }
                    break;
                }
                case 2: { // Начало умножения
                    state = 3; // Визуализирование
                    break;
                }
                case 3: { // Визуализирование
                    state = 4; // Если нельзя умножить быстро
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    if (d.k != 0) {
                        state = 6; // Визуализирование
                    } else {
                        state = 53; // Умножаем по простому
                    }
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    state = 58; // Завершение
                    break;
                }
                case 6: { // Визуализирование
                    state = 7; // Визуализирование
                    break;
                }
                case 7: { // Визуализирование
                    state = 8; // Готовим А
                    break;
                }
                case 8: { // Готовим А
                    state = 9; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // Готовим С
                    }
                    break;
                }
                case 10: { // Готовим С
                    state = 11; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 12; // Умножает Карацубой (автомат)
                    }
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 13; // Получаем результат
                    }
                    break;
                }
                case 13: { // Получаем результат
                    state = 14; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 15; // Визуализирование
                    }
                    break;
                }
                case 15: { // Визуализирование
                    state = 16; // Визуализирование
                    break;
                }
                case 16: { // Визуализирование
                    state = 17; // Готовим В
                    break;
                }
                case 17: { // Готовим В
                    state = 18; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 19; // Готовим D
                    }
                    break;
                }
                case 19: { // Готовим D
                    state = 20; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // Умножает Карацубой (автомат)
                    }
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 22; // Получаем результат
                    }
                    break;
                }
                case 22: { // Получаем результат
                    state = 23; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 24; // Визуализирование
                    }
                    break;
                }
                case 24: { // Визуализирование
                    state = 25; // Готовим А
                    break;
                }
                case 25: { // Готовим А
                    state = 26; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 27; // Готовим В
                    }
                    break;
                }
                case 27: { // Готовим В
                    state = 28; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 29; // Быстро складывает 2 числа (автомат)
                    }
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 30; // Получаем результат
                    }
                    break;
                }
                case 30: { // Получаем результат
                    state = 31; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 32; // Готовим С
                    }
                    break;
                }
                case 32: { // Готовим С
                    state = 33; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 34; // Готовим D
                    }
                    break;
                }
                case 34: { // Готовим D
                    state = 35; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 36; // Быстро складывает 2 числа (автомат)
                    }
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 37; // Получаем результат
                    }
                    break;
                }
                case 37: { // Получаем результат
                    state = 38; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 39; // Визуализирование
                    }
                    break;
                }
                case 39: { // Визуализирование
                    state = 40; // Готовим А + В
                    break;
                }
                case 40: { // Готовим А + В
                    state = 41; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 42; // Готовим С + D
                    }
                    break;
                }
                case 42: { // Готовим С + D
                    state = 43; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 44; // Умножает Карацубой (автомат)
                    }
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 45; // Получаем результат
                    }
                    break;
                }
                case 45: { // Получаем результат
                    state = 46; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 47; // Визуализирование
                    }
                    break;
                }
                case 47: { // Визуализирование
                    state = 48; // Визуализирование
                    break;
                }
                case 48: { // Визуализирование
                    state = 49; // Визуализирование
                    break;
                }
                case 49: { // Визуализирование
                    state = 50; // Готовимся складывать
                    break;
                }
                case 50: { // Готовимся складывать
                    state = 51; // Быстро складывает и вычитает до ответа (автомат)
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 52; // Визуализирование
                    }
                    break;
                }
                case 52: { // Визуализирование
                    stack.pushBoolean(true); 
                    state = 5; // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 53: { // Умножаем по простому
                    state = 54; // Если больше 9
                    break;
                }
                case 54: { // Если больше 9
                    if (d.store[d.storeInd + 2][0] > 9) {
                        state = 56; // Уменьшаем
                    } else {
                        stack.pushBoolean(false); 
                        state = 55; // Если больше 9 (окончание)
                    }
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    state = 57; // Визуализирование
                    break;
                }
                case 56: { // Уменьшаем
                    stack.pushBoolean(true); 
                    state = 55; // Если больше 9 (окончание)
                    break;
                }
                case 57: { // Визуализирование
                    stack.pushBoolean(false); 
                    state = 5; // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 58: { // Завершение
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Начало умножения
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "t");
                    d.t = Math.max(d.storeL[d.storeInd], d.storeL[d.storeInd + 1]);
                    storeArray(d.storeL, d.storeInd);
                    d.storeL[d.storeInd] = d.t;
                    storeArray(d.storeL, d.storeInd + 1);
                    d.storeL[d.storeInd + 1] = d.t;
                    break;
                }
                case 3: { // Визуализирование
                    startSection();
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 6: { // Визуализирование
                    startSection();
                    break;
                }
                case 7: { // Визуализирование
                    startSection();
                    break;
                }
                case 8: { // Готовим А
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // Готовим С
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 13: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 3;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 15: { // Визуализирование
                    startSection();
                    break;
                }
                case 16: { // Визуализирование
                    startSection();
                    break;
                }
                case 17: { // Готовим В
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 19: { // Готовим D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 22: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 7;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 24: { // Визуализирование
                    startSection();
                    break;
                }
                case 25: { // Готовим А
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 27: { // Готовим В
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 30: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 4;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 32: { // Готовим С
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 34: { // Готовим D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 37: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 5;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 39: { // Визуализирование
                    startSection();
                    break;
                }
                case 40: { // Готовим А + В
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd+ 4;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 4];
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 42: { // Готовим С + D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 5;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 5];
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 45: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 6;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 47: { // Визуализирование
                    startSection();
                    break;
                }
                case 48: { // Визуализирование
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 49: { // Визуализирование
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 50: { // Готовимся складывать
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 52: { // Визуализирование
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 53: { // Умножаем по простому
                    startSection();
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 1;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd][0] * d.store[d.storeInd + 1][0];
                    break;
                }
                case 54: { // Если больше 9
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    break;
                }
                case 56: { // Уменьшаем
                    startSection();
                    storeArray(d.store[d.storeInd + 2], 1);
                    d.store[d.storeInd + 2][1] = d.store[d.storeInd + 2][0] / 10;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd + 2][0] % 10;
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 2;
                    break;
                }
                case 57: { // Визуализирование
                    startSection();
                    break;
                }
                case 58: { // Завершение
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd - 8;
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
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // Начало умножения
                    restoreSection();
                    break;
                }
                case 3: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 6: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 7: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 8: { // Готовим А
                    restoreSection();
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // Готовим С
                    restoreSection();
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 13: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 15: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 16: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 17: { // Готовим В
                    restoreSection();
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 19: { // Готовим D
                    restoreSection();
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 22: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 24: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 25: { // Готовим А
                    restoreSection();
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 27: { // Готовим В
                    restoreSection();
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 30: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 32: { // Готовим С
                    restoreSection();
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 34: { // Готовим D
                    restoreSection();
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 37: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 39: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 40: { // Готовим А + В
                    restoreSection();
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 42: { // Готовим С + D
                    restoreSection();
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 45: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 47: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 48: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 49: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 50: { // Готовимся складывать
                    restoreSection();
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 52: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 53: { // Умножаем по простому
                    restoreSection();
                    break;
                }
                case 54: { // Если больше 9
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    break;
                }
                case 56: { // Уменьшаем
                    restoreSection();
                    break;
                }
                case 57: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 58: { // Завершение
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Начало умножения
                    state = 1; // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    break;
                }
                case 3: { // Визуализирование
                    state = 2; // Начало умножения
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    state = 3; // Визуализирование
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    if (stack.popBoolean()) {
                        state = 52; // Визуализирование
                    } else {
                        state = 57; // Визуализирование
                    }
                    break;
                }
                case 6: { // Визуализирование
                    state = 4; // Если нельзя умножить быстро
                    break;
                }
                case 7: { // Визуализирование
                    state = 6; // Визуализирование
                    break;
                }
                case 8: { // Готовим А
                    state = 7; // Визуализирование
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // Готовим А
                    }
                    break;
                }
                case 10: { // Готовим С
                    state = 9; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // Готовим С
                    }
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 11; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 13: { // Получаем результат
                    state = 12; // Умножает Карацубой (автомат)
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // Получаем результат
                    }
                    break;
                }
                case 15: { // Визуализирование
                    state = 14; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 16: { // Визуализирование
                    state = 15; // Визуализирование
                    break;
                }
                case 17: { // Готовим В
                    state = 16; // Визуализирование
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 17; // Готовим В
                    }
                    break;
                }
                case 19: { // Готовим D
                    state = 18; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // Готовим D
                    }
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 20; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 22: { // Получаем результат
                    state = 21; // Умножает Карацубой (автомат)
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 22; // Получаем результат
                    }
                    break;
                }
                case 24: { // Визуализирование
                    state = 23; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 25: { // Готовим А
                    state = 24; // Визуализирование
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 25; // Готовим А
                    }
                    break;
                }
                case 27: { // Готовим В
                    state = 26; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 27; // Готовим В
                    }
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 28; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 30: { // Получаем результат
                    state = 29; // Быстро складывает 2 числа (автомат)
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 30; // Получаем результат
                    }
                    break;
                }
                case 32: { // Готовим С
                    state = 31; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 32; // Готовим С
                    }
                    break;
                }
                case 34: { // Готовим D
                    state = 33; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 34; // Готовим D
                    }
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 35; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 37: { // Получаем результат
                    state = 36; // Быстро складывает 2 числа (автомат)
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 37; // Получаем результат
                    }
                    break;
                }
                case 39: { // Визуализирование
                    state = 38; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 40: { // Готовим А + В
                    state = 39; // Визуализирование
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 40; // Готовим А + В
                    }
                    break;
                }
                case 42: { // Готовим С + D
                    state = 41; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 42; // Готовим С + D
                    }
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 43; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 45: { // Получаем результат
                    state = 44; // Умножает Карацубой (автомат)
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 45; // Получаем результат
                    }
                    break;
                }
                case 47: { // Визуализирование
                    state = 46; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 48: { // Визуализирование
                    state = 47; // Визуализирование
                    break;
                }
                case 49: { // Визуализирование
                    state = 48; // Визуализирование
                    break;
                }
                case 50: { // Готовимся складывать
                    state = 49; // Визуализирование
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 50; // Готовимся складывать
                    }
                    break;
                }
                case 52: { // Визуализирование
                    state = 51; // Быстро складывает и вычитает до ответа (автомат)
                    break;
                }
                case 53: { // Умножаем по простому
                    state = 4; // Если нельзя умножить быстро
                    break;
                }
                case 54: { // Если больше 9
                    state = 53; // Умножаем по простому
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    if (stack.popBoolean()) {
                        state = 56; // Уменьшаем
                    } else {
                        state = 54; // Если больше 9
                    }
                    break;
                }
                case 56: { // Уменьшаем
                    state = 54; // Если больше 9
                    break;
                }
                case 57: { // Визуализирование
                    state = 55; // Если больше 9 (окончание)
                    break;
                }
                case 58: { // Завершение
                    state = 5; // Если нельзя умножить быстро (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 58; // Завершение
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
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 3: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.KarEnterShow"); 
                    break;
                }
                case 6: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.KarSplitShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8), new Integer(d.k)}; 
                    break;
                }
                case 7: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.ACShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 15: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.ResultAC"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 16: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.BDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.ResultBD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 39: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.ApBCpDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 47: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.ResultApBCpD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 48: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.takeIntegerForSum"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 49: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.ReSumShow"); 
                    args = new Object[]{new Integer(d.k)}; 
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 52: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.SumShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 57: { // Визуализирование
                    comment = LongInteger.this.getComment("Karpower.showSimplepower"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
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
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    child.drawState(); 
                    break;
                }
                case 3: { // Визуализирование
                    d.visualizer.showTempPower(0);
                    break;
                }
                case 6: { // Визуализирование
                    d.visualizer.showTempPower(1);
                    break;
                }
                case 7: { // Визуализирование
                    d.visualizer.showTempPower(2);
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 15: { // Визуализирование
                    d.visualizer.showTempPower(3);
                    break;
                }
                case 16: { // Визуализирование
                    d.visualizer.showTempPower(4);
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 24: { // Визуализирование
                    d.visualizer.showTempPower(5);
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    child.drawState(); 
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    child.drawState(); 
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 39: { // Визуализирование
                    d.visualizer.showTempPower(6);
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 47: { // Визуализирование
                    d.visualizer.showTempPower(7);
                    break;
                }
                case 48: { // Визуализирование
                    d.visualizer.showTempPower(8);
                    break;
                }
                case 49: { // Визуализирование
                    d.visualizer.showSum(0);
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    child.drawState(); 
                    break;
                }
                case 52: { // Визуализирование
                    d.visualizer.showSum(1);
                    break;
                }
                case 57: { // Визуализирование
                    d.visualizer.showSum(2);
                    break;
                }
            }
        }
    }

    /**
      * Умножает Карацубой.
      */
    private final class KarpowerMain extends BaseAutomata implements Automata {
        /**
          * Начальное состояние автомата.
          */
        private final int START_STATE = 0;

        /**
          * Конечное состояние автомата.
          */
        private final int END_STATE = 59;

        /**
          * Конструктор.
          */
        public KarpowerMain() {
            super( 
                "KarpowerMain", 
                0, // Номер начального состояния 
                59, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)", 
                    "Начало умножения", 
                    "Визуализирование", 
                    "Если нельзя умножить быстро", 
                    "Если нельзя умножить быстро (окончание)", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Готовим А", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим С", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Умножает Карацубой (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Готовим В", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим D", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Умножает Карацубой (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Готовим А", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим В", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Быстро складывает 2 числа (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим С", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим D", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Быстро складывает 2 числа (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Готовим А + В", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Готовим С + D", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Умножает Карацубой (автомат)", 
                    "Получаем результат", 
                    "Копирует число из indFrom в indTo (автомат)", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Визуализирование", 
                    "Готовимся складывать", 
                    "Быстро складывает и вычитает до ответа (автомат)", 
                    "Визуализирование", 
                    "Умножаем по простому", 
                    "Если больше 9", 
                    "Если больше 9 (окончание)", 
                    "Уменьшаем", 
                    "Визуализирование", 
                    "Завершение", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    CALL_AUTO_LEVEL, // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат) 
                    -1, // Начало умножения 
                    1, // Визуализирование 
                    -1, // Если нельзя умножить быстро 
                    -1, // Если нельзя умножить быстро (окончание) 
                    1, // Визуализирование 
                    1, // Визуализирование 
                    -1, // Готовим А 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим С 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    1, // Визуализирование 
                    1, // Визуализирование 
                    -1, // Готовим В 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим D 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    1, // Визуализирование 
                    -1, // Готовим А 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим В 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Быстро складывает 2 числа (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим С 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим D 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Быстро складывает 2 числа (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    1, // Визуализирование 
                    -1, // Готовим А + В 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    -1, // Готовим С + D 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Получаем результат 
                    CALL_AUTO_LEVEL, // Копирует число из indFrom в indTo (автомат) 
                    1, // Визуализирование 
                    1, // Визуализирование 
                    1, // Визуализирование 
                    -1, // Готовимся складывать 
                    CALL_AUTO_LEVEL, // Быстро складывает и вычитает до ответа (автомат) 
                    1, // Визуализирование 
                    -1, // Умножаем по простому 
                    -1, // Если больше 9 
                    -1, // Если больше 9 (окончание) 
                    -1, // Уменьшаем 
                    1, // Визуализирование 
                    -1, // Завершение 
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
                    state = 1; // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    break;
                }
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 2; // Начало умножения
                    }
                    break;
                }
                case 2: { // Начало умножения
                    state = 3; // Визуализирование
                    break;
                }
                case 3: { // Визуализирование
                    state = 4; // Если нельзя умножить быстро
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    if (d.k != 0) {
                        state = 6; // Визуализирование
                    } else {
                        state = 53; // Умножаем по простому
                    }
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    state = 58; // Завершение
                    break;
                }
                case 6: { // Визуализирование
                    state = 7; // Визуализирование
                    break;
                }
                case 7: { // Визуализирование
                    state = 8; // Готовим А
                    break;
                }
                case 8: { // Готовим А
                    state = 9; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 10; // Готовим С
                    }
                    break;
                }
                case 10: { // Готовим С
                    state = 11; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 12; // Умножает Карацубой (автомат)
                    }
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 13; // Получаем результат
                    }
                    break;
                }
                case 13: { // Получаем результат
                    state = 14; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 15; // Визуализирование
                    }
                    break;
                }
                case 15: { // Визуализирование
                    state = 16; // Визуализирование
                    break;
                }
                case 16: { // Визуализирование
                    state = 17; // Готовим В
                    break;
                }
                case 17: { // Готовим В
                    state = 18; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 19; // Готовим D
                    }
                    break;
                }
                case 19: { // Готовим D
                    state = 20; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 21; // Умножает Карацубой (автомат)
                    }
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 22; // Получаем результат
                    }
                    break;
                }
                case 22: { // Получаем результат
                    state = 23; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 24; // Визуализирование
                    }
                    break;
                }
                case 24: { // Визуализирование
                    state = 25; // Готовим А
                    break;
                }
                case 25: { // Готовим А
                    state = 26; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 27; // Готовим В
                    }
                    break;
                }
                case 27: { // Готовим В
                    state = 28; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 29; // Быстро складывает 2 числа (автомат)
                    }
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 30; // Получаем результат
                    }
                    break;
                }
                case 30: { // Получаем результат
                    state = 31; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 32; // Готовим С
                    }
                    break;
                }
                case 32: { // Готовим С
                    state = 33; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 34; // Готовим D
                    }
                    break;
                }
                case 34: { // Готовим D
                    state = 35; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 36; // Быстро складывает 2 числа (автомат)
                    }
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 37; // Получаем результат
                    }
                    break;
                }
                case 37: { // Получаем результат
                    state = 38; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 39; // Визуализирование
                    }
                    break;
                }
                case 39: { // Визуализирование
                    state = 40; // Готовим А + В
                    break;
                }
                case 40: { // Готовим А + В
                    state = 41; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 42; // Готовим С + D
                    }
                    break;
                }
                case 42: { // Готовим С + D
                    state = 43; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 44; // Умножает Карацубой (автомат)
                    }
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 45; // Получаем результат
                    }
                    break;
                }
                case 45: { // Получаем результат
                    state = 46; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 47; // Визуализирование
                    }
                    break;
                }
                case 47: { // Визуализирование
                    state = 48; // Визуализирование
                    break;
                }
                case 48: { // Визуализирование
                    state = 49; // Визуализирование
                    break;
                }
                case 49: { // Визуализирование
                    state = 50; // Готовимся складывать
                    break;
                }
                case 50: { // Готовимся складывать
                    state = 51; // Быстро складывает и вычитает до ответа (автомат)
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 52; // Визуализирование
                    }
                    break;
                }
                case 52: { // Визуализирование
                    stack.pushBoolean(true); 
                    state = 5; // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 53: { // Умножаем по простому
                    state = 54; // Если больше 9
                    break;
                }
                case 54: { // Если больше 9
                    if (d.store[d.storeInd + 2][0] > 9) {
                        state = 56; // Уменьшаем
                    } else {
                        stack.pushBoolean(false); 
                        state = 55; // Если больше 9 (окончание)
                    }
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    state = 57; // Визуализирование
                    break;
                }
                case 56: { // Уменьшаем
                    stack.pushBoolean(true); 
                    state = 55; // Если больше 9 (окончание)
                    break;
                }
                case 57: { // Визуализирование
                    stack.pushBoolean(false); 
                    state = 5; // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 58: { // Завершение
                    state = END_STATE; 
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 2: { // Начало умножения
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "t");
                    d.t = Math.max(d.storeL[d.storeInd], d.storeL[d.storeInd + 1]);
                    storeArray(d.storeL, d.storeInd);
                    d.storeL[d.storeInd] = d.t;
                    storeArray(d.storeL, d.storeInd + 1);
                    d.storeL[d.storeInd + 1] = d.t;
                    break;
                }
                case 3: { // Визуализирование
                    startSection();
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 6: { // Визуализирование
                    startSection();
                    break;
                }
                case 7: { // Визуализирование
                    startSection();
                    break;
                }
                case 8: { // Готовим А
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 10: { // Готовим С
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 13: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 3;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 15: { // Визуализирование
                    startSection();
                    break;
                }
                case 16: { // Визуализирование
                    startSection();
                    break;
                }
                case 17: { // Готовим В
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 19: { // Готовим D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 22: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 7;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 24: { // Визуализирование
                    startSection();
                    break;
                }
                case 25: { // Готовим А
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd] - d.k;
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 27: { // Готовим В
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 30: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 4;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 32: { // Готовим С
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    storeField(d, "indFirst");
                    d.indFirst = d.k;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 1] - d.k;
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 34: { // Готовим D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 1;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.k;
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 37: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 5;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 39: { // Визуализирование
                    startSection();
                    break;
                }
                case 40: { // Готовим А + В
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd+ 4;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 8;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 4];
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 42: { // Готовим С + D
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 5;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 9;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 5];
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 45: { // Получаем результат
                    startSection();
                    storeField(d, "indFrom");
                    d.indFrom = d.storeInd + 10;
                    storeField(d, "indTo");
                    d.indTo = d.storeInd + 6;
                    storeField(d, "indFirst");
                    d.indFirst = 0;
                    storeField(d, "indLength");
                    d.indLength = d.storeL[d.storeInd + 10];
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 47: { // Визуализирование
                    startSection();
                    break;
                }
                case 48: { // Визуализирование
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 49: { // Визуализирование
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 50: { // Готовимся складывать
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 52: { // Визуализирование
                    startSection();
                    storeField(d, "k");
                    d.k = Math.max(d.storeL[d.storeInd] / 2, d.storeL[d.storeInd + 1] / 2);
                    break;
                }
                case 53: { // Умножаем по простому
                    startSection();
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 1;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd][0] * d.store[d.storeInd + 1][0];
                    break;
                }
                case 54: { // Если больше 9
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    break;
                }
                case 56: { // Уменьшаем
                    startSection();
                    storeArray(d.store[d.storeInd + 2], 1);
                    d.store[d.storeInd + 2][1] = d.store[d.storeInd + 2][0] / 10;
                    storeArray(d.store[d.storeInd + 2], 0);
                    d.store[d.storeInd + 2][0] = d.store[d.storeInd + 2][0] % 10;
                    storeArray(d.storeL, d.storeInd + 2);
                    d.storeL[d.storeInd + 2] = 2;
                    break;
                }
                case 57: { // Визуализирование
                    startSection();
                    break;
                }
                case 58: { // Завершение
                    startSection();
                    storeField(d, "storeInd");
                    d.storeInd = d.storeInd - 8;
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
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child == null) {
                        child = new SuperClear(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 2: { // Начало умножения
                    restoreSection();
                    break;
                }
                case 3: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    break;
                }
                case 6: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 7: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 8: { // Готовим А
                    restoreSection();
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 10: { // Готовим С
                    restoreSection();
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 13: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 15: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 16: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 17: { // Готовим В
                    restoreSection();
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 19: { // Готовим D
                    restoreSection();
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 22: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 24: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 25: { // Готовим А
                    restoreSection();
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 27: { // Готовим В
                    restoreSection();
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 30: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 32: { // Готовим С
                    restoreSection();
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 34: { // Готовим D
                    restoreSection();
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child == null) {
                        child = new KarSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 37: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 39: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 40: { // Готовим А + В
                    restoreSection();
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 42: { // Готовим С + D
                    restoreSection();
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new Karpower(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 45: { // Получаем результат
                    restoreSection();
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child == null) {
                        child = new Copy(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 47: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 48: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 49: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 50: { // Готовимся складывать
                    restoreSection();
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child == null) {
                        child = new FastSum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 52: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 53: { // Умножаем по простому
                    restoreSection();
                    break;
                }
                case 54: { // Если больше 9
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    break;
                }
                case 56: { // Уменьшаем
                    restoreSection();
                    break;
                }
                case 57: { // Визуализирование
                    restoreSection();
                    break;
                }
                case 58: { // Завершение
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = START_STATE; 
                    }
                    break;
                }
                case 2: { // Начало умножения
                    state = 1; // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    break;
                }
                case 3: { // Визуализирование
                    state = 2; // Начало умножения
                    break;
                }
                case 4: { // Если нельзя умножить быстро
                    state = 3; // Визуализирование
                    break;
                }
                case 5: { // Если нельзя умножить быстро (окончание)
                    if (stack.popBoolean()) {
                        state = 52; // Визуализирование
                    } else {
                        state = 57; // Визуализирование
                    }
                    break;
                }
                case 6: { // Визуализирование
                    state = 4; // Если нельзя умножить быстро
                    break;
                }
                case 7: { // Визуализирование
                    state = 6; // Визуализирование
                    break;
                }
                case 8: { // Готовим А
                    state = 7; // Визуализирование
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // Готовим А
                    }
                    break;
                }
                case 10: { // Готовим С
                    state = 9; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 10; // Готовим С
                    }
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 11; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 13: { // Получаем результат
                    state = 12; // Умножает Карацубой (автомат)
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // Получаем результат
                    }
                    break;
                }
                case 15: { // Визуализирование
                    state = 14; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 16: { // Визуализирование
                    state = 15; // Визуализирование
                    break;
                }
                case 17: { // Готовим В
                    state = 16; // Визуализирование
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 17; // Готовим В
                    }
                    break;
                }
                case 19: { // Готовим D
                    state = 18; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // Готовим D
                    }
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 20; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 22: { // Получаем результат
                    state = 21; // Умножает Карацубой (автомат)
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 22; // Получаем результат
                    }
                    break;
                }
                case 24: { // Визуализирование
                    state = 23; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 25: { // Готовим А
                    state = 24; // Визуализирование
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 25; // Готовим А
                    }
                    break;
                }
                case 27: { // Готовим В
                    state = 26; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 27; // Готовим В
                    }
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 28; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 30: { // Получаем результат
                    state = 29; // Быстро складывает 2 числа (автомат)
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 30; // Получаем результат
                    }
                    break;
                }
                case 32: { // Готовим С
                    state = 31; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 32; // Готовим С
                    }
                    break;
                }
                case 34: { // Готовим D
                    state = 33; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 34; // Готовим D
                    }
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 35; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 37: { // Получаем результат
                    state = 36; // Быстро складывает 2 числа (автомат)
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 37; // Получаем результат
                    }
                    break;
                }
                case 39: { // Визуализирование
                    state = 38; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 40: { // Готовим А + В
                    state = 39; // Визуализирование
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 40; // Готовим А + В
                    }
                    break;
                }
                case 42: { // Готовим С + D
                    state = 41; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 42; // Готовим С + D
                    }
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 43; // Копирует число из indFrom в indTo (автомат)
                    }
                    break;
                }
                case 45: { // Получаем результат
                    state = 44; // Умножает Карацубой (автомат)
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 45; // Получаем результат
                    }
                    break;
                }
                case 47: { // Визуализирование
                    state = 46; // Копирует число из indFrom в indTo (автомат)
                    break;
                }
                case 48: { // Визуализирование
                    state = 47; // Визуализирование
                    break;
                }
                case 49: { // Визуализирование
                    state = 48; // Визуализирование
                    break;
                }
                case 50: { // Готовимся складывать
                    state = 49; // Визуализирование
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 50; // Готовимся складывать
                    }
                    break;
                }
                case 52: { // Визуализирование
                    state = 51; // Быстро складывает и вычитает до ответа (автомат)
                    break;
                }
                case 53: { // Умножаем по простому
                    state = 4; // Если нельзя умножить быстро
                    break;
                }
                case 54: { // Если больше 9
                    state = 53; // Умножаем по простому
                    break;
                }
                case 55: { // Если больше 9 (окончание)
                    if (stack.popBoolean()) {
                        state = 56; // Уменьшаем
                    } else {
                        state = 54; // Если больше 9
                    }
                    break;
                }
                case 56: { // Уменьшаем
                    state = 54; // Если больше 9
                    break;
                }
                case 57: { // Визуализирование
                    state = 55; // Если больше 9 (окончание)
                    break;
                }
                case 58: { // Завершение
                    state = 5; // Если нельзя умножить быстро (окончание)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 58; // Завершение
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
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 3: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.KarEnterShow"); 
                    break;
                }
                case 6: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.KarSplitShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8), new Integer(d.k)}; 
                    break;
                }
                case 7: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.ACShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 15: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.ResultAC"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 16: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.BDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 24: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.ResultBD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 39: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.ApBCpDShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 47: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.ResultApBCpD"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 48: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.takeIntegerForSum"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 49: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.ReSumShow"); 
                    args = new Object[]{new Integer(d.k)}; 
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 52: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.SumShow"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
                    break;
                }
                case 57: { // Визуализирование
                    comment = LongInteger.this.getComment("KarpowerMain.showSimplepower"); 
                    args = new Object[]{new Integer(d.storeInd / 8)}; 
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
                case 1: { // Очищает содержимое (storeInd + 10)..(storeInd + 15) (автомат)
                    child.drawState(); 
                    break;
                }
                case 3: { // Визуализирование
                    d.visualizer.showTempPower(0);
                    break;
                }
                case 6: { // Визуализирование
                    d.visualizer.showTempPower(1);
                    break;
                }
                case 7: { // Визуализирование
                    d.visualizer.showTempPower(2);
                    break;
                }
                case 9: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 11: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 12: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case 14: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 15: { // Визуализирование
                    d.visualizer.showTempPower(3);
                    break;
                }
                case 16: { // Визуализирование
                    d.visualizer.showTempPower(4);
                    break;
                }
                case 18: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 20: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 21: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case 23: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 24: { // Визуализирование
                    d.visualizer.showTempPower(5);
                    break;
                }
                case 26: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 28: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 29: { // Быстро складывает 2 числа (автомат)
                    child.drawState(); 
                    break;
                }
                case 31: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 33: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 35: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 36: { // Быстро складывает 2 числа (автомат)
                    child.drawState(); 
                    break;
                }
                case 38: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 39: { // Визуализирование
                    d.visualizer.showTempPower(6);
                    break;
                }
                case 41: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 43: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 44: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case 46: { // Копирует число из indFrom в indTo (автомат)
                    child.drawState(); 
                    break;
                }
                case 47: { // Визуализирование
                    d.visualizer.showTempPower(7);
                    break;
                }
                case 48: { // Визуализирование
                    d.visualizer.showTempPower(8);
                    break;
                }
                case 49: { // Визуализирование
                    d.visualizer.showSum(0);
                    break;
                }
                case 51: { // Быстро складывает и вычитает до ответа (автомат)
                    child.drawState(); 
                    break;
                }
                case 52: { // Визуализирование
                    d.visualizer.showSum(1);
                    break;
                }
                case 57: { // Визуализирование
                    d.visualizer.showSum(2);
                    break;
                }
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
        private final int END_STATE = 26;

        /**
          * Конструктор.
          */
        public Main() {
            super( 
                "Main", 
                0, // Номер начального состояния 
                26, // Номер конечного состояния 
                new String[]{ 
                    "Начальное состояние",  
                    "Обнуляем ответ", 
                    "Цикл", 
                    "Шаг цикла", 
                    "Сложение", 
                    "Сложение (окончание)", 
                    "Выполняем сложение", 
                    "Складывает (автомат)", 
                    "Вычитание", 
                    "Вычитание (окончание)", 
                    "Проверяет какое число по модулю больше (автомат)", 
                    "Выполняем вычитание", 
                    "Сравнение чисел", 
                    "Сравнение чисел (окончание)", 
                    "Проверка знака", 
                    "Меняет числа местами (автомат)", 
                    "Вычитает (автомат)", 
                    "Простое умножение", 
                    "Простое умножение (окончание)", 
                    "Выполняем умножение", 
                    "Умножает (автомат)", 
                    "Умножение алгоритмом Карацубы", 
                    "Умножение алгоритмом Карацубы (окончание)", 
                    "Выполняем алгоритм Карацубы", 
                    "Умножает Карацубой (автомат)", 
                    "Завершение", 
                    "Конечное состояние" 
                }, new int[]{ 
                    Integer.MAX_VALUE, // Начальное состояние,  
                    -1, // Обнуляем ответ 
                    -1, // Цикл 
                    -1, // Шаг цикла 
                    -1, // Сложение 
                    -1, // Сложение (окончание) 
                    -1, // Выполняем сложение 
                    CALL_AUTO_LEVEL, // Складывает (автомат) 
                    -1, // Вычитание 
                    -1, // Вычитание (окончание) 
                    CALL_AUTO_LEVEL, // Проверяет какое число по модулю больше (автомат) 
                    -1, // Выполняем вычитание 
                    -1, // Сравнение чисел 
                    -1, // Сравнение чисел (окончание) 
                    0, // Проверка знака 
                    CALL_AUTO_LEVEL, // Меняет числа местами (автомат) 
                    CALL_AUTO_LEVEL, // Вычитает (автомат) 
                    -1, // Простое умножение 
                    -1, // Простое умножение (окончание) 
                    -1, // Выполняем умножение 
                    CALL_AUTO_LEVEL, // Умножает (автомат) 
                    -1, // Умножение алгоритмом Карацубы 
                    -1, // Умножение алгоритмом Карацубы (окончание) 
                    -1, // Выполняем алгоритм Карацубы 
                    CALL_AUTO_LEVEL, // Умножает Карацубой (автомат) 
                    -1, // Завершение 
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
                    state = 1; // Обнуляем ответ
                    break;
                }
                case 1: { // Обнуляем ответ
                    stack.pushBoolean(false); 
                    state = 2; // Цикл
                    break;
                }
                case 2: { // Цикл
                    if (d.i < d.store[2].length) {
                        state = 3; // Шаг цикла
                    } else {
                        state = 4; // Сложение
                    }
                    break;
                }
                case 3: { // Шаг цикла
                    stack.pushBoolean(true); 
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Сложение
                    if (d.visualizer.combobox1.getSelectedIndex() == 0) {
                        state = 6; // Выполняем сложение
                    } else {
                        stack.pushBoolean(false); 
                        state = 5; // Сложение (окончание)
                    }
                    break;
                }
                case 5: { // Сложение (окончание)
                    state = 8; // Вычитание
                    break;
                }
                case 6: { // Выполняем сложение
                    state = 7; // Складывает (автомат)
                    break;
                }
                case 7: { // Складывает (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 5; // Сложение (окончание)
                    }
                    break;
                }
                case 8: { // Вычитание
                    if (d.visualizer.combobox1.getSelectedIndex() == 1) {
                        state = 10; // Проверяет какое число по модулю больше (автомат)
                    } else {
                        stack.pushBoolean(false); 
                        state = 9; // Вычитание (окончание)
                    }
                    break;
                }
                case 9: { // Вычитание (окончание)
                    state = 17; // Простое умножение
                    break;
                }
                case 10: { // Проверяет какое число по модулю больше (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 11; // Выполняем вычитание
                    }
                    break;
                }
                case 11: { // Выполняем вычитание
                    state = 12; // Сравнение чисел
                    break;
                }
                case 12: { // Сравнение чисел
                    if (!d.firstBig) {
                        state = 14; // Проверка знака
                    } else {
                        stack.pushBoolean(false); 
                        state = 13; // Сравнение чисел (окончание)
                    }
                    break;
                }
                case 13: { // Сравнение чисел (окончание)
                    state = 16; // Вычитает (автомат)
                    break;
                }
                case 14: { // Проверка знака
                    state = 15; // Меняет числа местами (автомат)
                    break;
                }
                case 15: { // Меняет числа местами (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 13; // Сравнение чисел (окончание)
                    }
                    break;
                }
                case 16: { // Вычитает (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 9; // Вычитание (окончание)
                    }
                    break;
                }
                case 17: { // Простое умножение
                    if (d.visualizer.combobox1.getSelectedIndex() == 2) {
                        state = 19; // Выполняем умножение
                    } else {
                        stack.pushBoolean(false); 
                        state = 18; // Простое умножение (окончание)
                    }
                    break;
                }
                case 18: { // Простое умножение (окончание)
                    state = 21; // Умножение алгоритмом Карацубы
                    break;
                }
                case 19: { // Выполняем умножение
                    state = 20; // Умножает (автомат)
                    break;
                }
                case 20: { // Умножает (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        stack.pushBoolean(true); 
                        state = 18; // Простое умножение (окончание)
                    }
                    break;
                }
                case 21: { // Умножение алгоритмом Карацубы
                    if (d.visualizer.combobox1.getSelectedIndex() == 3) {
                        state = 23; // Выполняем алгоритм Карацубы
                    } else {
                        stack.pushBoolean(false); 
                        state = 22; // Умножение алгоритмом Карацубы (окончание)
                    }
                    break;
                }
                case 22: { // Умножение алгоритмом Карацубы (окончание)
                    state = END_STATE; 
                    break;
                }
                case 23: { // Выполняем алгоритм Карацубы
                    state = 24; // Умножает Карацубой (автомат)
                    break;
                }
                case 24: { // Умножает Карацубой (автомат)
                    if (child.isAtEnd()) {
                        child = null; 
                        state = 25; // Завершение
                    }
                    break;
                }
                case 25: { // Завершение
                    stack.pushBoolean(true); 
                    state = 22; // Умножение алгоритмом Карацубы (окончание)
                    break;
                }
            }

            // Действие в текущем состоянии
            switch (state) {
                case 1: { // Обнуляем ответ
                    startSection();
                    storeField(d, "i");
                    d.i = 0; 
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Шаг цикла
                    startSection();
                     storeArray(d.store[2], d.i);
                     d.store[2][d.i] = 0; d.i += 1;
                    break;
                }
                case 4: { // Сложение
                    break;
                }
                case 5: { // Сложение (окончание)
                    break;
                }
                case 6: { // Выполняем сложение
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "+"; 
                    storeField(d, "sRu");
                    d.sRu = new String("Сложение завершено");
                    storeField(d, "sEn");
                    d.sEn = new String("Sum completed");
                    break;
                }
                case 7: { // Складывает (автомат)
                    if (child == null) {
                        child = new Sum(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 8: { // Вычитание
                    break;
                }
                case 9: { // Вычитание (окончание)
                    break;
                }
                case 10: { // Проверяет какое число по модулю больше (автомат)
                    if (child == null) {
                        child = new testFirstBig(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 11: { // Выполняем вычитание
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "-"; 
                    storeField(d, "sRu");
                    d.sRu = new String("Вычитание завершено");
                    storeField(d, "sEn");
                    d.sEn = new String("Subtraction completed");
                    break;
                }
                case 12: { // Сравнение чисел
                    break;
                }
                case 13: { // Сравнение чисел (окончание)
                    break;
                }
                case 14: { // Проверка знака
                    startSection();
                    break;
                }
                case 15: { // Меняет числа местами (автомат)
                    if (child == null) {
                        child = new Replace(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 16: { // Вычитает (автомат)
                    if (child == null) {
                        child = new Mines(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 17: { // Простое умножение
                    break;
                }
                case 18: { // Простое умножение (окончание)
                    break;
                }
                case 19: { // Выполняем умножение
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "smu");
                    d.smu = "*"; 
                    storeField(d, "sRu");
                    d.sRu = new String("Умножение завершено");
                    storeField(d, "sEn");
                    d.sEn = new String("Power completed");
                    break;
                }
                case 20: { // Умножает (автомат)
                    if (child == null) {
                        child = new power(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 21: { // Умножение алгоритмом Карацубы
                    break;
                }
                case 22: { // Умножение алгоритмом Карацубы (окончание)
                    break;
                }
                case 23: { // Выполняем алгоритм Карацубы
                    startSection();
                    storeField(d, "smu");
                    d.smu = "**";
                    storeField(d, "storeInd");
                    d.storeInd = -8;
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "sRu");
                    d.sRu = new String("Умножение алгоритмом Карацубы завершено");
                    storeField(d, "sEn");
                    d.sEn = new String("Algoritm Karatchuba's power completed");
                    break;
                }
                case 24: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new KarpowerMain(); 
                        child.toStart(); 
                    }
                    child.stepForward(level); 
                    step--; 
                    break;
                }
                case 25: { // Завершение
                    startSection();
                    storeField(d, "i");
                    d.i = 0;
                    storeField(d, "storeInd");
                    d.storeInd = 0;
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
                case 1: { // Обнуляем ответ
                    restoreSection();
                    break;
                }
                case 2: { // Цикл
                    break;
                }
                case 3: { // Шаг цикла
                    restoreSection();
                    break;
                }
                case 4: { // Сложение
                    break;
                }
                case 5: { // Сложение (окончание)
                    break;
                }
                case 6: { // Выполняем сложение
                    restoreSection();
                    break;
                }
                case 7: { // Складывает (автомат)
                    if (child == null) {
                        child = new Sum(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 8: { // Вычитание
                    break;
                }
                case 9: { // Вычитание (окончание)
                    break;
                }
                case 10: { // Проверяет какое число по модулю больше (автомат)
                    if (child == null) {
                        child = new testFirstBig(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 11: { // Выполняем вычитание
                    restoreSection();
                    break;
                }
                case 12: { // Сравнение чисел
                    break;
                }
                case 13: { // Сравнение чисел (окончание)
                    break;
                }
                case 14: { // Проверка знака
                    restoreSection();
                    break;
                }
                case 15: { // Меняет числа местами (автомат)
                    if (child == null) {
                        child = new Replace(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 16: { // Вычитает (автомат)
                    if (child == null) {
                        child = new Mines(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 17: { // Простое умножение
                    break;
                }
                case 18: { // Простое умножение (окончание)
                    break;
                }
                case 19: { // Выполняем умножение
                    restoreSection();
                    break;
                }
                case 20: { // Умножает (автомат)
                    if (child == null) {
                        child = new power(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 21: { // Умножение алгоритмом Карацубы
                    break;
                }
                case 22: { // Умножение алгоритмом Карацубы (окончание)
                    break;
                }
                case 23: { // Выполняем алгоритм Карацубы
                    restoreSection();
                    break;
                }
                case 24: { // Умножает Карацубой (автомат)
                    if (child == null) {
                        child = new KarpowerMain(); 
                        child.toEnd(); 
                    }
                    child.stepBackward(level); 
                    step++; 
                    break;
                }
                case 25: { // Завершение
                    restoreSection();
                    break;
                }
            }

            // Переход в предыдущее состояние
            switch (state) {
                case 1: { // Обнуляем ответ
                    state = START_STATE; 
                    break;
                }
                case 2: { // Цикл
                    if (stack.popBoolean()) {
                        state = 3; // Шаг цикла
                    } else {
                        state = 1; // Обнуляем ответ
                    }
                    break;
                }
                case 3: { // Шаг цикла
                    state = 2; // Цикл
                    break;
                }
                case 4: { // Сложение
                    state = 2; // Цикл
                    break;
                }
                case 5: { // Сложение (окончание)
                    if (stack.popBoolean()) {
                        state = 7; // Складывает (автомат)
                    } else {
                        state = 4; // Сложение
                    }
                    break;
                }
                case 6: { // Выполняем сложение
                    state = 4; // Сложение
                    break;
                }
                case 7: { // Складывает (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 6; // Выполняем сложение
                    }
                    break;
                }
                case 8: { // Вычитание
                    state = 5; // Сложение (окончание)
                    break;
                }
                case 9: { // Вычитание (окончание)
                    if (stack.popBoolean()) {
                        state = 16; // Вычитает (автомат)
                    } else {
                        state = 8; // Вычитание
                    }
                    break;
                }
                case 10: { // Проверяет какое число по модулю больше (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 8; // Вычитание
                    }
                    break;
                }
                case 11: { // Выполняем вычитание
                    state = 10; // Проверяет какое число по модулю больше (автомат)
                    break;
                }
                case 12: { // Сравнение чисел
                    state = 11; // Выполняем вычитание
                    break;
                }
                case 13: { // Сравнение чисел (окончание)
                    if (stack.popBoolean()) {
                        state = 15; // Меняет числа местами (автомат)
                    } else {
                        state = 12; // Сравнение чисел
                    }
                    break;
                }
                case 14: { // Проверка знака
                    state = 12; // Сравнение чисел
                    break;
                }
                case 15: { // Меняет числа местами (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 14; // Проверка знака
                    }
                    break;
                }
                case 16: { // Вычитает (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 13; // Сравнение чисел (окончание)
                    }
                    break;
                }
                case 17: { // Простое умножение
                    state = 9; // Вычитание (окончание)
                    break;
                }
                case 18: { // Простое умножение (окончание)
                    if (stack.popBoolean()) {
                        state = 20; // Умножает (автомат)
                    } else {
                        state = 17; // Простое умножение
                    }
                    break;
                }
                case 19: { // Выполняем умножение
                    state = 17; // Простое умножение
                    break;
                }
                case 20: { // Умножает (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 19; // Выполняем умножение
                    }
                    break;
                }
                case 21: { // Умножение алгоритмом Карацубы
                    state = 18; // Простое умножение (окончание)
                    break;
                }
                case 22: { // Умножение алгоритмом Карацубы (окончание)
                    if (stack.popBoolean()) {
                        state = 25; // Завершение
                    } else {
                        state = 21; // Умножение алгоритмом Карацубы
                    }
                    break;
                }
                case 23: { // Выполняем алгоритм Карацубы
                    state = 21; // Умножение алгоритмом Карацубы
                    break;
                }
                case 24: { // Умножает Карацубой (автомат)
                    if (child.isAtStart()) {
                        child = null; 
                        state = 23; // Выполняем алгоритм Карацубы
                    }
                    break;
                }
                case 25: { // Завершение
                    state = 24; // Умножает Карацубой (автомат)
                    break;
                }
                case END_STATE: { // Начальное состояние
                    state = 22; // Умножение алгоритмом Карацубы (окончание)
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
                    comment = LongInteger.this.getComment("Main.START_STATE"); 
                    break;
                }
                case 1: { // Обнуляем ответ
                    comment = LongInteger.this.getComment("Main.startStep3"); 
                    break;
                }
                case 6: { // Выполняем сложение
                    comment = LongInteger.this.getComment("Main.sumEnter"); 
                    break;
                }
                case 7: { // Складывает (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 10: { // Проверяет какое число по модулю больше (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 11: { // Выполняем вычитание
                    comment = LongInteger.this.getComment("Main.minesEnter"); 
                    break;
                }
                case 14: { // Проверка знака
                    comment = LongInteger.this.getComment("Main.replaceEnter"); 
                    break;
                }
                case 15: { // Меняет числа местами (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 16: { // Вычитает (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 19: { // Выполняем умножение
                    comment = LongInteger.this.getComment("Main.choicepower"); 
                    break;
                }
                case 20: { // Умножает (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case 23: { // Выполняем алгоритм Карацубы
                    comment = LongInteger.this.getComment("Main.choiceKarpower"); 
                    break;
                }
                case 24: { // Умножает Карацубой (автомат)
                    comment = child.getComment(); 
                    args = new Object[0]; 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    comment = LongInteger.this.getComment("Main.END_STATE"); 
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
                    d.i = 0;
                    d.j = 0;
                    d.ost = -10000; 
                    d.tempOst = -10000;
                    d.storeInd = 0;
                    d.commentLevel = 10001;
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    break;
                }
                case 6: { // Выполняем сложение
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 7: { // Складывает (автомат)
                    child.drawState(); 
                    break;
                }
                case 10: { // Проверяет какое число по модулю больше (автомат)
                    child.drawState(); 
                    break;
                }
                case 11: { // Выполняем вычитание
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 14: { // Проверка знака
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 15: { // Меняет числа местами (автомат)
                    child.drawState(); 
                    break;
                }
                case 16: { // Вычитает (автомат)
                    child.drawState(); 
                    break;
                }
                case 19: { // Выполняем умножение
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    d.visualizer.showTemp(0, 0);
                    break;
                }
                case 20: { // Умножает (автомат)
                    child.drawState(); 
                    break;
                }
                case 23: { // Выполняем алгоритм Карацубы
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    break;
                }
                case 24: { // Умножает Карацубой (автомат)
                    child.drawState(); 
                    break;
                }
                case END_STATE: { // Конечное состояние
                    d.i = -10000;
                    d.j = -10000;
                    d.ost = -10000;
                    d.tempOst = -10000;
                    d.commentLevel = 10000;
                    d.visualizer.antiSOFE = true;
                    d.visualizer.rePaint();
                    d.visualizer.showIntegers(-1, -1, -1, 0, 0, 0);
                    break;
                }
            }
        }
    }
}
