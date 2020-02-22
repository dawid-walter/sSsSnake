package com.ssssnake.gameObjects;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public Tile(int x, int y, int w, int h, Color color) {
        super(w, h, color);

        setTranslateX(x);
        setTranslateY(y);
    }
}