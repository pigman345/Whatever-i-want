package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy {
    public int Ex;
    public int Ey;
    public int Ew;
    public int Eh;
    public int Ecol;
    public int Erow;
    public Color Ecolor;

    public void draw(ShapeRenderer sr) {
        sr.rect(Ex,Ey, Ew, Eh, Ecolor, Ecolor, Ecolor, Ecolor);
    }

    public Enemy(int ex, int ey, int ew, int eh, int ecol, int erow, Color ecolor) {
        Ex = ex;
        Ey = ey;
        Ew = ew;
        Eh = eh;
        Ecol = ecol;
        Erow = erow;
        Ecolor = ecolor;
    }

    public int getEx() {
        return Ex;
    }

    public void setEx(int ex) {
        Ex = ex;
    }

    public int getEy() {
        return Ey;
    }

    public void setEy(int ey) {
        Ey = ey;
    }

    public int getEw() {
        return Ew;
    }

    public void setEw(int ew) {
        Ew = ew;
    }

    public int getEh() {
        return Eh;
    }

    public void setEh(int eh) {
        Eh = eh;
    }

    public int getEcol() {
        return Ecol;
    }

    public void setEcol(int ecol) {
        Ecol = ecol;
    }

    public int getErow() {
        return Erow;
    }

    public void setErow(int erow) {
        Erow = erow;
    }

    public Color getEcolor() {
        return Ecolor;
    }

    public void setEcolor(Color ecolor) {
        Ecolor = ecolor;
    }
}
