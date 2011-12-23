package ru.ifmo.vizi.karkkainen;

import ru.ifmo.vizi.base.ui.*;
import ru.ifmo.vizi.base.*;
import ru.ifmo.vizi.base.widgets.Rect;
import ru.ifmo.vizi.base.widgets.ShapeStyle;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
    
public class MyArray {
    double px, py, sx, sy, shx, shy;

    boolean visible;

    Rect[] arr;
    int[] st;

    public MyArray() {
        arr = new Rect[0];
        st = new int[0];
    }

    public MyArray(double px, double py, double sx, double sy, double shx, double shy, boolean visible, int size) {
        this.px = px;
        this.py = py;
        this.sx = sx;
        this.sy = sy;
        this.shx = shx;
        this.shy = shy;
        this.visible = visible;
        this.st = new int[0];         
        arr = new Rect[size];
    }

    public void remove(Container clientPane) {
        for (int i = 0; i < arr.length; i++) {
            clientPane.remove(arr[i]);
        }
    }

    public void create(ShapeStyle[] style, Container clientPane) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Rect(style);
            clientPane.add(arr[i]);
        }
    }

    public void upDate(String[] val, int[] style) {
        visible = true;
        for (int i = 0; i < arr.length; i++) {
            arr[i].setMessage(val[i]);
        }
        st = (int[]) style.clone();
    }
    
    private double calcPosNormal(double d, double t, double dsh, double p, double sh, int i) {
        return d + t * (dsh + p + i * sh);
    }

    private void setBounds(Rect r, double x, double y, double dx, double dy) {
       int ix1 = (int)Math.round(x);
       int iy1 = (int)Math.round(y);
       int ix2 = (int)Math.round(x + dx);
       int iy2 = (int)Math.round(y + dy);
       r.setBounds(ix1, iy1, ix2 - ix1 + 1, iy2 - iy1 + 1);
    }


    public void draw(int w, int h, double aspectRatio) {
        double dx = 0;
        double dy = 0;
        double tw = w;
        double th = h;
        if (w * aspectRatio < h) {
            dy = (h - w * aspectRatio) / 2;
            th = h - 2 * dy;
        } else {
            dx = (w - h / aspectRatio) / 2;
            tw = w - 2 * dx;
        }

        for (int i = 0; i < arr.length; i++) {
            double dsh = 0;
            if (visible && st[i] >= 0) {
                arr[i].setStyle(st[i]);
            } else {
                dsh = 2;
            }
            double posx = calcPosNormal(dx, tw, dsh, px, shx, i);
            double posy = calcPosNormal(dy, th, dsh, py, shy, i);            
            setBounds(arr[i], posx, posy, tw * sx, th * sy);            
        }
    }

}
