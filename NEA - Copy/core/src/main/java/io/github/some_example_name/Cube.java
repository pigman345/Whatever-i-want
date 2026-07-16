package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cube {
    public int Cx;
    public int Cy;
    public int Ch;
    public int Cw;

    public Color Ccolour;
    public void draw(ShapeRenderer sr) {
        sr.rect(Cx,Cy,Ch,Cw,Ccolour,Ccolour,Ccolour,Ccolour);
    }


    public Cube(int cx, int cy, int ch, int cw, Color ccolour) {
        Cx = cx;
        Cy = cy;
        Ch = ch;
        Cw = cw;
        Ccolour = ccolour;

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

    public int getCh() {
        return Ch;
    }

    public void setCh(int ch) {
        Ch = ch;
    }

    public int getCw() {
        return Cw;
    }

    public void setCw(int cw) {
        Cw = cw;
    }

    public Color getCcolour() {
        return Ccolour;
    }

    public void setCcolour(Color ccolour) {
        Ccolour = ccolour;
    }
}

