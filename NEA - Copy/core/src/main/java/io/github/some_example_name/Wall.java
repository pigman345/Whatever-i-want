package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Wall {


        public int Wlx;
        public int Wly;
        public int Wlw;
        public int Wlh;
        public int Wlcol;
        public int Wlrow;
        public Color Wlcolor;

        public void draw(ShapeRenderer sr) {
            sr.rect(Wlx, Wly, Wlw, Wlh, Wlcolor, Wlcolor, Wlcolor, Wlcolor);
        }
        public Wall(int wlx, int wly, int wlh, int wlw, int wlcol, int wlrow, Color wlcolor) {
            Wlx = wlx;
            Wly = wly;
            Wlh = wlh;
            Wlw = wlw;
            Wlcol = wlcol;
            Wlrow = wlrow;
            Wlcolor = wlcolor;

        }
        public Color getWlcolor() {
            return Wlcolor;
        }

        public void setWlcolor(Color wlcolor) {
            Wlcolor = wlcolor;
        }

        public int getWlrow() {
            return Wlrow;
        }

        public void setWlrow(int wlrow) {
            Wlrow = wlrow;
        }

        public int getWlcol() {
            return Wlcol;
        }

        public void setWlcol(int wlcol) {
            Wlcol = wlcol;
        }

        public int getWlw() {
            return Wlw;
        }

        public void setWlw(int wlw) {
            Wlw = wlw;
        }

        public int getWlh() {
            return Wlh;
        }

        public void setWlh(int wlh) {
            Wlh = wlh;
        }

        public int getWly() {
            return Wly;
        }

        public void setWly(int wly) {
            Wly = wly;
        }

        public int getWlx() {
            return Wlx;
        }

        public void setWlx(int wlx) {
            Wlx = wlx;
        }



    }

