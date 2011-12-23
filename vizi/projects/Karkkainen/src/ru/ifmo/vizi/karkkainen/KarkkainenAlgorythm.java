package ru.ifmo.vizi.karkkainen;

import java.util.*;

//�����, ����������� �������� ����������� - �������� ���������� 
//����������� ������� �� �������� �����. (SKEW)
//�� ������� ������� ��������� �������� ��������� ����
//�����: ����� �������, argentony@gmail.com

//������ ����� ������������ ��� ��������� ����������� ������, ����������� �� ����� ������.

public class KarkkainenAlgorythm {
    
    public KarkkainenAlgorythm() {} 
    
    private class KArray {
        //�����, � ������� ���������� ������ ����� ��������������� ����� �� 0 �� mk
        int[] arr; //������ ������
        int mk;    //������� ������� ���������, �������� ����������� �����

        public KArray(KArray old, int ext) {
            //�������� ������ - ����� old, �������� � ����� arr ext �����
            this.arr = new int[old.arr.length + ext];
            for (int i = 0; i < old.arr.length; i++) {
                this.arr[i] = old.arr[i];
            }
            this.mk = old.mk;
        }

        public KArray(int length) {
            //�������� ������ � �������� ����� length
            this.arr = new int[length];
            this.mk = 0;
        }

        public KArray(int[] array, int mk) {
            //�������� ������, � ������� ���������� ����� array � mk
            this.arr = new int[array.length];
            for (int i = 0; i < array.length; i++) {
                this.arr[i] = array[i];
            }
            this.mk = mk;
        }
    }

    private int[] radixSort(int[] link, KArray key, int depth) {
        //����������� ���������� �������� ������ key.arr ����� depth
        //��� �������� ������� key.arr ��������������
        //key.mk - ������������ ��������� �������� ��������� key.arr
        //������ link �������� ������ ������� ������ ��������� ����������� ��������
        //������� ���������� ������, ���������� �������� ������� link, �������������� 
        //���, ��� ��������� ��� ��������� ���� � ����������������� ����������� ������� 
        int[] ans = new int[link.length];  //�����
        
        //������ ��� �������� ���������� ��������� ������� ��������
        int[] count = new int[key.mk + 1];
        
        //������� ���������� ��������� ������� �������� ���������� ��������
        for (int i = 0; i < link.length; i++) {
            count[key.arr[link[i] + depth - 1]]++;
        }
        
        //������ ��� �������� �������, ���� ���� ������� ������� � ������ ���������
        int[] position = new int[key.mk + 1];
        
        //����� ���������� ��������� count
        int sum = 0; 
        //���������� ������� ������ ��������� ������� ��������
        for (int i = 0; i < key.mk + 1; i++) {
            position[i] = sum; 
            sum += count[i];
        }
        //��������� ������
        for (int i = 0; i < link.length; i++) {
            ans[position[key.arr[link[i] + depth - 1]]] = link[i];
            position[key.arr[link[i] + depth - 1]]++;
        }
        //���� ����, �� ��������� ���������� ������� ����� depth
        if (depth > 1) {
            ans = radixSort(ans, key, depth - 1);
        }
        return ans;
    }

    private KArray makeNext(int[] link, KArray key) {
        //��������� ������ ��� ������������ ������.
        //�������� ��������� ����� 3 ������������ ������� ���, 
        //����� ��������� �������� ���� ������������ ��������� ������������� �����
        //����� �������������� ����� �������, ����� � ������ ����� ������� ���� �����
        //��������������� ���������, ������ ������ �������� ������� �������� 
        //� 1 �� ������ 3, � �� ������ - ����� ... ������� �������� � 2  �� ������ 3
        
        //���������� �������� ����� 3
        link = radixSort(link, key, 3);
        
        //�������� ������ - ������
        KArray ans = new KArray(link.length);
        
        int num = 0; //������� ����� ���������
        // ��������� ������������� ��������� ����� 3 - (�0, c1, c2)
        int c0 = -1; 
        int c1 = -1;
        int c2 = -1;
        //���������� ���������� �������� �������������� ���������, 
        //����� ������ ��������� �������������� ����� ����� 1
        for (int i = 0; i < link.length; i++) {
            //��������� � ���������� � � ������ ������� ���������� ������ � �����������
            if (key.arr[link[i]    ] != c0 || 
                key.arr[link[i] + 1] != c1 || 
                key.arr[link[i] + 2] != c2) {
                num++;
                c0 = key.arr[link[i]];
                c1 = key.arr[link[i] + 1];
                c2 = key.arr[link[i] + 2];
            }
            //����������� ������� � ������ �������
            if (link[i] % 3 == 1) {
                //���� ����� ��������� � ������ �����
                ans.arr[link[i] / 3] = num;
            } else {
                //���� ����� ��������� �� ������ �����
                int shift = (link.length + 1) / 2; //������ ������ �����
                ans.arr[shift + link[i] / 3] = num;
            }
        }
        ans.mk = num; //����������� ������������� ��������
        if (num == ans.arr.length) {
            num += 0;
        }
        return ans;
    }

    private int[] getSuffMod12(KArray s) {
        //���������� ������, � ������� ���������� ������ ��������� s.arr,
        //������ ������ ������� �� ������� �� 3.
        //� ������ ����� ����� s.arr �������� � 1 �� ������ 3 
        //���������� �������� "�����������" ������� �������
        
        int n = s.arr.length;
        int n12 = n - (n + 2) / 3; //���������� ������ ���������
        if (n % 3 == 1) {
            n12++;  //���������� ������� ��� "������������" �������� 
        }
        int[] ans = new int[n12]; //�����
        int j = 0; //������� ���������� ��������� ������ ���������
        for (int i = 0; i < n; i++) {
            if (i % 3 != 0) {
                //���� ... �� ������� �� 3, ����������
                ans[j++] = i;
            }
        }
        if (n % 3 == 1) {
            //���� ����, �� ��������� "�����������" ������� �������
            ans[n12 - 1] = n; 
        }
        return ans;
    }
    
    private boolean lexLess(int a1, int a2, int b1, int b2) {
        //������ ��, ��� ���� ����� (a1, a2) �����������������
        //������ ���� ����� (b1, b2)
        return (a1 < b1 || a1 == b1 && a2 < b2);
    }

    private boolean lexLess(int a1, int a2, int a3, int b1, int b2, int b3) {
        //������ ��, ��� ������ ����� (a1, a2, a3) �����������������
        //������ ������ ����� (b1, b2, b3)
        return (a1 < b1 || a1 == b1 && lexLess(a2, a3, b2, b3));
    }
    
    private int[] buildSuffArray(KArray s, int recLevel) {

        if (s.arr.length == s.mk) {

            //������ ����� ����, ����������� ����������� ������
            data.recStr = new int[recLevel + 1][];
            data.recStr[recLevel] = (int[])s.arr.clone();

            data.tripplesNum = new int[recLevel + 1][][];
            data.tripplesNum[recLevel] = new int[0][0];

            data.pairsNum = new int[recLevel + 1][][];
            data.suff23Num = new int[recLevel + 1][];
            //����� ����� ����, ����������� ����������� ������

            int[] suffarr = new int[s.arr.length];
            for (int i = 0; i < s.arr.length; i++) {
                suffarr[s.arr[i] - 1] = i;
            }

            data.suffArr = new int[recLevel + 1][];
            data.suffArr[recLevel] = (int[]) suffarr.clone();


            return suffarr;
        }
        
        //��������� ������ ��������� (������� 1 � 2)
        int[] suffnum12 = getSuffMod12(s);
        
        //������ s, ����������� 3 - �� ������ ��� �������� ���������
        KArray exts = new KArray(s, 3);
        
        //����������� ��������� ����������� ������� ��� ��������� � ��������� 1 � 2
        KArray recS = makeNext(suffnum12, exts);
        int[] suffarr12 = buildSuffArray(recS, recLevel + 1);

        //������ ����� ����, ����������� ����������� ������
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
        //����� ����� ����, ����������� ����������� ������


        int shift = (suffarr12.length + 1) / 2; //���������� ��������� � �������� 1
        
        //��������� � ��������� 1 � 2 �������������� �� ����� � ����. �������
        //��� �������� � ����� ����������� 3 ����
        int[] num = new int[s.arr.length + 3];
        
        //���������� �������� �������� � ����. ������� ��� ��������� � ��������� 1 � 2
        for (int i = 0; i < suffarr12.length; i++) {
            if (suffarr12[i] < shift) {
                suffarr12[i] = 3 * suffarr12[i] + 1;
            } else {
                suffarr12[i] = 3 * (suffarr12[i] - shift) + 2;
            }
            num[suffarr12[i]] = i; //���������� �������
        }

        
        //������ ������� ��������� � ��������� 0
        //�������� ������������� � ������� ����������� �� "�������" 
        //(��������� � ����������� ������ ��������)
        int[] suffnum0 = new int[(s.arr.length + 2) / 3]; 
        int j = 0; //������� ������ suffnum0
        for (int i = 0; i < suffarr12.length; i++) {
            if (suffarr12[i] % 3 == 1) {
                suffnum0[j] = suffarr12[i] - 1;
                j++;
            }
        }
        //���������� ������ ��� ��������� � �������� 0
        int[] suffarr0 = radixSort(suffnum0, exts, 1);

        //������ ����� ����, ����������� ����������� ������
        data.suff23Num[recLevel + 1] = (int[]) num.clone();
        data.pairsNum[recLevel + 1] = new int[suffarr0.length][1];
        for (int i = 0; i < suffarr0.length; i++) {
            data.pairsNum[recLevel + 1][suffarr0[i] / 3][0] = i;
        }
        //����� ����� ����, ����������� ����������� ������



        //������� ���������� ��������
        int p0 = 0;  //��������������� ������� �� suffarr0
        int p12 = 0; //��������������� ������� �� suffarr12
        if (s.arr.length % 3 == 1) {
            //���� ��������� "�����������" ������� - ���������� ���
            p12 = 1; 
        }
        
        int[] suffarr = new int[s.arr.length]; //���������� ������ ��� s.arr
        
        for (int p = 0; p < suffarr.length; p++) {
            //������ ��, ��� ����� ����� ������� suffarr0[p0], � �� suffarr12[p12]
            boolean step0 = false; 
            
            if (p12 == suffarr12.length) {
                //���� ��������� ����� ���� �� ��������, �� ����� ����� ����� ������ 
                step0 = true;
            }
            if (p12 < suffarr12.length && p0 < suffarr0.length) {
                //���� � �� � �� �������� ���� - ����������
                int pos0  = suffarr0 [p0 ]; //����� � ������
                int pos12 = suffarr12[p12];
                if (pos12 % 3 == 1) {
                    //���� ���������� ������� � �������� 0 � ������� � �������� 1
                    if (lexLess(exts.arr[pos0 ], num[pos0  + 1], 
                                exts.arr[pos12], num[pos12 + 1])) {
                        step0 = true;
                    }
                } else {
                    //���� ���������� ������� � �������� 0 � ������� � �������� 2
                    if (lexLess(exts.arr[pos0 ], exts.arr[pos0  + 1], num[pos0  + 2],
                                exts.arr[pos12], exts.arr[pos12 + 1], num[pos12 + 2])) {
                        step0 = true;
                    }
                }
            }
            //����������� �������
            if (step0) {
                suffarr[p] = suffarr0[p0++];
            } else {
                suffarr[p] = suffarr12[p12++];
            }
        }

        //����������� ����������� �������.
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
                //���� ������ �� �������� �������� ��������� ������ 
                throw new Error("Wrong string format. Lower-case latin letters expected : " + (new String(data.s)));
            }
        }

        //������ ���� �������
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


