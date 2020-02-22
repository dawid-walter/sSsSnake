package com.ssssnake.gameObjects;


import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class Snake {
    private Deque<Node> snake = new ArrayDeque<>();
    public Directions movingDirection;
    int tileSize = 11;
    Color tileColor;

    public Snake(int snakeSize, Color tileColor, int snakeTileSize) {
        this.tileColor = tileColor;
        for (int i = 0; i < snakeSize; i++) {
            Node snakeTile = new Tile(i * 11 + 56, 12, snakeTileSize, snakeTileSize, tileColor);
            snake.add(snakeTile);
        }
    }

    public Deque<Node> getSnake() {
        return snake;
    }

    public Tile createSnakeTile(int x, int y, int snakeTileSize) {
        Tile snakeTile = new Tile(x, y, snakeTileSize, snakeTileSize, tileColor);
        return snakeTile;
    }

    public void moveLeft() {
        double snakeHeadXCoordinate = snake.peekLast().getTranslateX();
        double snakeHeadYCoordinate = snake.peekLast().getTranslateY();
        Node newSnakesHeadTile = snake.poll();
        newSnakesHeadTile.setTranslateX(snakeHeadXCoordinate - tileSize);
        newSnakesHeadTile.setTranslateY(snakeHeadYCoordinate);
        snake.offer(newSnakesHeadTile);
        movingDirection = Directions.LEFT;
    }

    public void moveRight() {
        double snakeHeadXCoordinate = snake.peekLast().getTranslateX();
        double snakeHeadYCoordinate = snake.peekLast().getTranslateY();
        Node newSnakesHeadTile = snake.poll();
        newSnakesHeadTile.setTranslateX(snakeHeadXCoordinate + tileSize);
        newSnakesHeadTile.setTranslateY(snakeHeadYCoordinate);
        snake.offer(newSnakesHeadTile);
        movingDirection = Directions.RIGHT;
    }

    public void moveUp() {
        double snakeHeadXCoordinate = snake.peekLast().getTranslateX();
        double snakeHeadYCoordinate = snake.peekLast().getTranslateY();
        Node newSnakesHeadTile = snake.poll();
        newSnakesHeadTile.setTranslateY(snakeHeadYCoordinate - tileSize);
        newSnakesHeadTile.setTranslateX(snakeHeadXCoordinate);
        snake.offer(newSnakesHeadTile);
        movingDirection = Directions.UP;
    }

    public void moveDown() {
        double snakeHeadXCoordinate = snake.peekLast().getTranslateX();
        double snakeHeadYCoordinate = snake.peekLast().getTranslateY();
        Node newSnakesHeadTile = snake.poll();
        newSnakesHeadTile.setTranslateY(snakeHeadYCoordinate + tileSize);
        newSnakesHeadTile.setTranslateX(snakeHeadXCoordinate);
        snake.offer(newSnakesHeadTile);
        movingDirection = Directions.DOWN;
    }

    public boolean checkSnakeHeadCollisionWitOtherTiles() {
        Set<Double> hashSet = new HashSet<>();
        for (Node s : snake) {
            hashSet.add(s.getTranslateX() * 3749 - s.getTranslateY() * 6383);
        }
        boolean collision = (snake.size() != hashSet.size());
        return collision;
    }
}

