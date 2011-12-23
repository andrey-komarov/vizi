package ru.ifmo.vizi.karkkainen;

import java.util.*;

//Класс, реализующий алгоритм Кярккяйнена - Сандерса построения 
//суффиксного массива за линейное время. (SKEW)
//За алфавит принято множество строчных латинских букв
//Автор: Антон Феськов, argentony@gmail.com

//Данный класс используется для получения необходимых данных, возникающих во время работы.

public class KarkkainenAlgorythm {
    
    public KarkkainenAlgorythm() {} 
    
    private class KArray {
        //Класс, в котором содержится массив целых неотрицательных чисел от 0 до mk
        int[] arr; //Массив данных
        int mk;    //Верхняя граница интервала, которому принадлежат числа

        public KArray(KArray old, int ext) {
            //Создание класса - копии old, добавляя в конец arr ext нулей
            this.arr = new int[old.arr.length + ext];
            for (int i = 0; i < old.arr.length; i++) {
                this.arr[i] = old.arr[i];
            }
            this.mk = old.mk;
        }

        public KArray(int length) {
            //Создание класса с массивом длины length
            this.arr = new int[length];
            this.mk = 0;
        }

        public KArray(int[] array, int mk) {
            //Создание класса, в котором содержатся копии array и mk
            this.arr = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                this.arr[i] = array[i];
            }
            this.mk = mk;
        }
    }

    private int[] radixSort(int[] link, KArray key, int depth) {
        //Поразрядная сортировка подстрок строки key.arr длины depth
        //Все элементы массива key.arr неотрицательны
        //key.mk - максимальное возможное значение элементов key.arr
        //Массив link содержит номера позиций первых элементов сортируемых подстрок
        //Функция возвращает массив, содержащий элементы массива link, переставленные 
        //так, что задаваемы ими подстроки идут в лексикографически неубывающем порядке 
        int[] ans = new int[link.length];  //ответ
        
        //Массив для подсчета количества вхождений каждого значения
        int[] count = new int[key.mk + 1];
        
        //Подсчет количества вхождений каждого значения последнего элемента
        for (int i = 0; i < link.length; i++) {
            count[key.arr[link[i] + depth - 1]]++;
        }
        
        //Массив для хранения позиции, куда надо ставить элемент с данным значением
        int[] position = new int[key.mk + 1];
        
        //Сумма предыдущих элементов count
        int sum = 0; 
        //Вычисление позиций первых элементов каждого значения
        for (int i = 0; i < key.mk + 1; i++) {
            position[i] = sum; 
            sum += count[i];
        }
        //Генерация ответа
        for (int i = 0; i < link.length; i++) {
            ans[position[key.arr[link[i] + depth - 1]]] = link[i];
            position[key.arr[link[i] + depth - 1]]++;
        }
        //Если надо, то сортируем оставшийся префикс длины depth
        if (depth > 1) {
            ans = radixSort(ans, key, depth - 1);
        }
        return ans;
    }

    private KArray makeNext(int[] link, KArray key) {
        //Генерация строки для рекурсивного спуска.
        //Нумирует подстроки длины 3 натуральными числами так, 
        //чтобы сравнение подстрок было эквивалентно сравнению соответвующих чисел
        //Числа переставляются таким образом, чтобы в первой части массива были числа
        //соответствующие суффиксам, номера первых символов которых сравнимы 
        //с 1 по модулю 3, а во второй - числа ... которых сравнимы с 2  по модулю 3
        
        //Сортировка подстрок длины 3
        link = radixSort(link, key, 3);
        
        //Создание класса - ответа
        KArray ans = new KArray(link.length);
        
        int num = 0; //текущий номер подстроки
        // Последняя просмотренная подстрока длины 3 - (с0, c1, c2)
        int c0 = -1; 
        int c1 = -1;
        int c2 = -1;
        //Изначально запоминаем заведомо несуществующую подстроку, 
        //чтобы первая подстрока гарантированно имела номер 1
        for (int i = 0; i < link.length; i++) {
            //Сравнение с предыдущей и в случае отличия увеличение номера и запоминание
            if (key.arr[link[i]    ] != c0 || 
                key.arr[link[i] + 1] != c1 || 
                key.arr[link[i] + 2] != c2) {
                num++;
                c0 = key.arr[link[i]];
                c1 = key.arr[link[i] + 1];
                c2 = key.arr[link[i] + 2];
            }
            //Запоминание номеров в нужном порядке
            if (link[i] % 3 == 1) {
                //Если нужно поставить в первую часть
                ans.arr[link[i] / 3] = num;
            } else {
                //Если нужно поставить во вторую часть
                int shift = (link.length + 1) / 2; //Размер первой части
                ans.arr[shift + link[i] / 3] = num;
            }
        }
        ans.mk = num; //Запоминание максимального элемента
        if (num == ans.arr.length) {
            num += 0;
        }
        return ans;
    }

    private int[] getSuffMod12(KArray s) {
        //Возвращает массив, в котором содержатся номера суффиксов s.arr,
        //индекс начала которых не делится на 3.
        //В случае когда длина s.arr сравнима с 1 по модулю 3 
        //необходимо добавить "виртуальный" нулевой суффикс
        
        int n = s.arr.length;
        int n12 = n - (n + 2) / 3; //Количество нужных суффиксов
        if (n % 3 == 1) {
            n12++;  //Увеличение размера для "виртуального" суффикса 
        }
        int[] ans = new int[n12]; //Ответ
        int j = 0; //Текущее количество найденных нужных суффиксов
        for (int i = 0; i < n; i++) {
            if (i % 3 != 0) {
                //Если ... не делится на 3, запоминаем
                ans[j++] = i;
            }
        }
        if (n % 3 == 1) {
            //Если надо, то добавляем "виртуальный" нулевой суффикс
            ans[n12 - 1] = n; 
        }
        return ans;
    }
    
    private boolean lexLess(int a1, int a2, int b1, int b2) {
        //Правда ли, что пара чисел (a1, a2) лексикографически
        //меньше пары чисел (b1, b2)
        return (a1 < b1 || a1 == b1 && a2 < b2);
    }

    private boolean lexLess(int a1, int a2, int a3, int b1, int b2, int b3) {
        //Правда ли, что тройка чисел (a1, a2, a3) лексикографически
        //меньше тройки чисел (b1, b2, b3)
        return (a1 < b1 || a1 == b1 && lexLess(a2, a3, b2, b3));
    }
    
    private int[] buildSuffArray(KArray s, int recLevel) {

        if (s.arr.length == s.mk) {

            //Начало куска кода, копирующего необходимые данные
            data.recStr = new int[recLevel + 1][];
            data.recStr[recLevel] = (int[])s.arr.clone();

            data.tripplesNum = new int[recLevel + 1][][];
            data.tripplesNum[recLevel] = new int[0][0];

            data.pairsNum = new int[recLevel + 1][][];
            data.suff23Num = new int[recLevel + 1][];
            //Конец куска кода, копирующего необходимые данные

            int[] suffarr = new int[s.arr.length];
            for (int i = 0; i < s.arr.length; i++) {
                suffarr[s.arr[i] - 1] = i;
            }

            data.suffArr = new int[recLevel + 1][];
            data.suffArr[recLevel] = (int[]) suffarr.clone();


            return suffarr;
        }
        
        //Генерация нужных суффиксов (остатки 1 и 2)
        int[] suffnum12 = getSuffMod12(s);
        
        //Строка s, дополненная 3 - мя нулями для удобства сравнения
        KArray exts = new KArray(s, 3);
        
        //Рекурсивное получение суффиксного массива для суффиксов с остатками 1 и 2
        KArray recS = makeNext(suffnum12, exts);
        int[] suffarr12 = buildSuffArray(recS, recLevel + 1);

        //Начало куска кода, копирующего необходимые данные
        if (s.arr.length % 3 == 1) {
            data.recStr[recLevel] = new int[s.arr.length + 1];
            for (int i = 0; i < s.arr.length; i++) {
                data.recStr[recLevel][i] = s.arr[i];
            }
        } else {
            data.recStr[recLevel] = (int[]) s.arr.clone();
        }
        data.tripplesNum[recLevel] = new int[recS.arr.length][1];
        int s1n = (recS.arr.length + 1) / 2;
        for (int i = 0; i < s1n; i++) {
            data.tripplesNum[recLevel][2 * i][0] = recS.arr[i];
        }
        for (int i = s1n; i < recS.arr.length; i++) {
            data.tripplesNum[recLevel][2 * (i - s1n) + 1][0] = recS.arr[i];
        }
        //Конец куска кода, копирующего необходимые данные


        int shift = (suffarr12.length + 1) / 2; //Количество суффиксов с остатком 1
        
        //Суффиксам с остатками 1 и 2 сопоставляется их номер в суфф. массиве
        //Для удобства в конец добавляются 3 нуля
        int[] num = new int[s.arr.length + 3];
        
        //Вычисление реальных значений в суфф. массиве для суффиксов с остатками 1 и 2
        for (int i = 0; i < suffarr12.length; i++) {
            if (suffarr12[i] < shift) {
                suffarr12[i] = 3 * suffarr12[i] + 1;
            } else {
                suffarr12[i] = 3 * (suffarr12[i] - shift) + 2;
            }
            num[suffarr12[i]] = i; //Заполнение номеров
        }

        
        //Массив номеров суффиксов с остатками 0
        //Суффиксы перечисляются в порядке возрастания их "хвостов" 
        //(суффиксов с отброшенным первым символом)
        int[] suffnum0 = new int[(s.arr.length + 2) / 3]; 
        int j = 0; //Текущий размер suffnum0
        for (int i = 0; i < suffarr12.length; i++) {
            if (suffarr12[i] % 3 == 1) {
                suffnum0[j] = suffarr12[i] - 1;
                j++;
            }
        }
        //Суффиксный массив для суффиксов с остатком 0
        int[] suffarr0 = radixSort(suffnum0, exts, 1);

        //Начало куска кода, копирующего необходимые данные
        data.suff23Num[recLevel + 1] = (int[]) num.clone();
        data.pairsNum[recLevel + 1] = new int[suffarr0.length][1];
        for (int i = 0; i < suffarr0.length; i++) {
            data.pairsNum[recLevel + 1][suffarr0[i] / 3][0] = i;
        }
        //Конец куска кода, копирующего необходимые данные



        //Слияние суффиксных массивов
        int p0 = 0;  //Рассматриваемый элемент из suffarr0
        int p12 = 0; //Рассматриваемый элемент из suffarr12
        if (s.arr.length % 3 == 1) {
            //Если добавляли "виртуальный" суффикс - пропускаем его
            p12 = 1; 
        }
        
        int[] suffarr = new int[s.arr.length]; //Суффиксный массив для s.arr
        
        for (int p = 0; p < suffarr.length; p++) {
            //Правда ли, что нужно брать суффикс suffarr0[p0], а не suffarr12[p12]
            boolean step0 = false; 
            
            if (p12 == suffarr12.length) {
                //Если суффиксов этого типа не осталось, то точно нужно брать другие 
                step0 = true;
            }
            if (p12 < suffarr12.length && p0 < suffarr0.length) {
                //Если и те и те суффиксы есть - сравниваем
                int pos0  = suffarr0 [p0 ]; //Номер в строке
                int pos12 = suffarr12[p12];
                if (pos12 % 3 == 1) {
                    //Если сравниваем суффикс с остатком 0 и суффмкс с остатком 1
                    if (lexLess(exts.arr[pos0 ], num[pos0  + 1], 
                                exts.arr[pos12], num[pos12 + 1])) {
                        step0 = true;
                    }
                } else {
                    //Если сравниваем суффикс с остатком 0 и суффмкс с остатком 2
                    if (lexLess(exts.arr[pos0 ], exts.arr[pos0  + 1], num[pos0  + 2],
                                exts.arr[pos12], exts.arr[pos12 + 1], num[pos12 + 2])) {
                        step0 = true;
                    }
                }
            }
            //Запоминание нужного
            if (step0) {
                suffarr[p] = suffarr0[p0++];
            } else {
                suffarr[p] = suffarr12[p12++];
            }
        }

        //Копирование суффиксного массива.
        data.suffArr[recLevel] = (int[]) suffarr.clone();

        return suffarr;
    }
    

    Karkkainen.Data data;

    void suffArray(Karkkainen.Data data) {
        this.data = data;
        
        int[] ts = new int[data.s.length];  
        for (int i = 0; i < data.s.length; i++) {
            ts[i] = data.s[i] - 'a';
            if (ts[i] < 0 || ts[i] > 25) {
                //Если символ не является строчной латинской буквой 
                throw new Error("Wrong string format. Lower-case latin letters expected : " + (new String(data.s)));
            }
        }

        //Замена букв числами
        int[] c = new int[26];
        for (int i = 0; i < ts.length; i++) {
            c[ts[i]]++;
        }
        int q = 0;
        for (int i = 0; i < 26; i++) {
            if (c[i] > 0) {
                q++;
                c[i] = q;
            }
        }
                
        for (int i = 0; i < ts.length; i++) {
            ts[i] = c[ts[i]];
        }

        buildSuffArray(new KArray(ts, q), 0);
    }
}


