package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Completion {
    public int Cx;
    public int Cy;
    public int Cw;
    public int Ch;
    public int Ccol;
    public int Crow;
    public Color Ccolor;

    public void draw(ShapeRenderer sr) {
        sr.rect(Cx,Cy, Cw, Ch, Ccolor, Ccolor, Ccolor, Ccolor);
    }

    public Completion(int cx, int cy, int cw, int ch, int ccol, int crow, Color ccolor) {
        Cx = cx;
        Cy = cy;
        Cw = cw;
        Ch = ch;
        Ccol = ccol;
        Crow = crow;
        Ccolor = ccolor;
    }

    public int getCx() {
        return Cx;
    }

    public void setCx(int cx) {
        Cx = cx;
    }

    public int getCy() {
        return Cy;
    }

    public void setCy(int cy) {
        Cy = cy;
    }

    public int getCw() {
        return Cw;
    }

    public void setCw(int cw) {
        Cw = cw;
    }

    public int getCh() {
        return Ch;
    }

    public void setCh(int ch) {
        Ch = ch;
    }

    public int getCcol() {
        return Ccol;
    }

    public void setCcol(int ccol) {
        Ccol = ccol;
    }

    public int getCrow() {
        return Crow;
    }

    public void setCrow(int crow) {
        Crow = crow;
    }

    public Color getCcolor() {
        return Ccolor;
    }

    public void setCcolor(Color ccolor) {
        Ccolor = ccolor;
    }
}
