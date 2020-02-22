package com.ssssnake.gameObjects;


import javafx.scene.layout.Pane;

public class GridBoard {
    int gridHeight;
    int gridWidth;

    public GridBoard(int gridHeight, int gridWidth, Pane pane) {

        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                BoardTile tile = new BoardTile();
                tile.setTranslateX(j * 11);
                tile.setTranslateY(i * 11);

                pane.getChildren().add(tile);
            }
        }
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }
}
