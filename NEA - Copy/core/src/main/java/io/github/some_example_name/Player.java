package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
    public int Px;
    public int Py;
    public int Pw;
    public int Ph;
    public int Pcol;
    public int Prow;
    public Boolean PlayerAlive;
    public Color Pcolor;

    public void draw(ShapeRenderer sr) {
        sr.rect(Px,Py, Pw, Ph, Pcolor, Pcolor, Pcolor, Pcolor);
    }



    public Player(int px, int py, int pw, int ph, int pcol, int prow, Color pcolor,Boolean playerAlive) {
        Px = px;
        Py = py;
        Pw = pw;
        Ph = ph;
        Pcol = pcol;
        Prow = prow;
        Pcolor = pcolor;
        PlayerAlive = playerAlive;
    }

    public Color getPcolor() {
        return Pcolor;
    }

    public void setPcolor(Color pcolor) {
        Pcolor = pcolor;
    }

    public int getPw() {
        return Pw;
    }

    public void setPw(int pw) {
        Pw = pw;
    }

    public int getPh() {
        return Ph;
    }

    public void setPh(int ph) {
        Ph = ph;
    }

    public int getPy() {
        return Py;
    }

    public void setPy(int py) {
        Py = py;
    }

    public int getPx() {
        return Px;
    }

    public void setPx(int px) {
        Px = px;
    }

    public int getPcol() {
        return Pcol;
    }

    public void setPcol(int pcol) {
        Pcol = pcol;
    }

    public int getProw() {
        return Prow;
    }

    public void setProw(int prow) {
        Prow = prow;
    }

    public Boolean getPlayerAlive() {
        return PlayerAlive;
    }

    public void setPlayerAlive(Boolean playerAlive) {
        PlayerAlive = playerAlive;
    }
}
