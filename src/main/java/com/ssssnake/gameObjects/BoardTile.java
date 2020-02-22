package com.ssssnake.gameObjects;


import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardTile extends StackPane {
    public BoardTile() {
        Rectangle border = new Rectangle(11, 11);
        border.setFill(null);
        border.setStroke(Color.LIGHTGREY);

        setAlignment(Pos.CENTER);
        getChildren().addAll(border);
    }
}
