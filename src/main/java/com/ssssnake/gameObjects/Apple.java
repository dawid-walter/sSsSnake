package com.ssssnake.gameObjects;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Apple {

    public Apple() {
    }

    public Node getApple(int x, int y) {
        Tile appleTile = new Tile(40, 40, 10, 10, Color.RED);

        appleTile.setTranslateX((int) (Math.random() * x) * 11 + 1);    //randomowa pozycja jablka
        appleTile.setTranslateY((int) (Math.random() * y) * 11 + 1);

        Image image = new Image("apple.png");
        ImagePattern imagePattern = new ImagePattern(image);
        appleTile.setFill(imagePattern);

        return appleTile;
    }
}
