package com.ssssnake.boards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssssnake.gameObjects.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.ssssnake.gameObjects.Directions.*;
import static javafx.scene.paint.Color.*;

public class GameBoardManager {
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private Stage stage;

    String playerName = "newPlayer";
    int points = 0;
    int applePoints = 100;

    //Grid and background parameters:
    int TILE_SIZE = 11;
    private final int GRID_WIDTH = 30;
    private final int GRID_HEIGHT = 30;
    Color BACKGROUND_COLOUR = WHITE;
    Color gridColor = GREY;

    //Snake parameters
    Directions moveToDirections = RIGHT;
    Color SNAKE_COLOUR = BLUE;
    int STARTUP_SNAKE_LENGHT = 10;
    int SNAKE_TILE_SIZE = TILE_SIZE - 1;
    int actualSnakeSize;

    //Timer parameters'
    private AnimationTimer timer;
    private long lastUpdate = 0;
    long TIMER_SPEED = 100_000_000;

    Label pointsLabel = new Label();
    Label bonusPointsLabel = new Label();
    Label snakeLengthLabel = new Label();

    Snake snake = new Snake(STARTUP_SNAKE_LENGHT, SNAKE_COLOUR, SNAKE_TILE_SIZE);
    Deque<Node> snakeTilesContainer = snake.getSnake();

    Apple appleCreator = new Apple();
    Node apple = appleCreator.getApple(GRID_WIDTH, GRID_HEIGHT);

    private Stage mainMenuStage;

    public GameBoardManager() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GRID_WIDTH * TILE_SIZE + 150, GRID_WIDTH * TILE_SIZE);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        createKeyListener();
        createContent();
    }

    public void createKeyListener() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                case UP:
                    moveToDirections = UP;
                    break;
                case S:
                case DOWN:
                    moveToDirections = DOWN;
                    break;
                case A:
                case LEFT:
                    moveToDirections = LEFT;
                    break;
                case D:
                case RIGHT:
                    moveToDirections = RIGHT;
                    break;
                default:
            }
        });
    }

    public void startNewGame(Stage stage) {
        this.stage = stage;
        this.stage.hide();
        gameStage.show();
    }


    private Parent createContent() {
        gamePane.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOUR, CornerRadii.EMPTY, Insets.EMPTY)));

        pointsLabel.setStyle("-fx-text-fill: black; -fx-font-size: 1em");
        pointsLabel.setLayoutX(350);
        pointsLabel.setLayoutY(12);

        bonusPointsLabel.setStyle("-fx-text-fill: black; -fx-font-size: 1em");
        bonusPointsLabel.setLayoutX(350);
        bonusPointsLabel.setLayoutY(24);

        snakeLengthLabel.setStyle("-fx-text-fill: black; -fx-font-size: 1em");
        snakeLengthLabel.setLayoutX(350);

        gamePane.getChildren().addAll(pointsLabel, bonusPointsLabel, snakeLengthLabel);

        for (Node s : snakeTilesContainer) {
            gamePane.getChildren().addAll(s);
        }

        gamePane.getChildren().add(apple);

        new GridBoard(GRID_HEIGHT, GRID_WIDTH, gamePane);

        Player.playersHighScores = deSerializeListFromJson();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now - lastUpdate >= TIMER_SPEED) {
                    onUpdate();
                    lastUpdate = now;
                }
            }
        };

        timer.start();

        return gamePane;
    }

    private void onUpdate() {


        actualSnakeSize = snakeTilesContainer.size();
        points += 1;
        if (applePoints > 0) {
            applePoints = applePoints - 2;
        }
        pointsLabel.setText("Points: " + String.valueOf(points));
        bonusPointsLabel.setText("Bonus points: " + String.valueOf(applePoints));
        snakeLengthLabel.setText("Snake size: " + String.valueOf(actualSnakeSize));

        switch (moveToDirections) {
            case UP:
                if (snake.movingDirection == DOWN) {
                    snake.moveDown();
                } else {
                    snake.moveUp();
                }
                break;

            case DOWN:
                if (snake.movingDirection == UP) {
                    snake.moveUp();
                } else {
                    snake.moveDown();
                }
                break;

            case LEFT:
                if (snake.movingDirection == RIGHT) {
                    snake.moveRight();
                } else {
                    snake.moveLeft();
                }
                break;

            case RIGHT:
                if (snake.movingDirection == LEFT) {
                    snake.moveLeft();
                } else {
                    snake.moveRight();
                }
                break;
            default:
                snake.moveRight();
        }
        checkStates();

        if (checkIfSnakeEatApple()) {
            createNewApple();
        }
    }

    private void checkStates() {
        if ((snakeTilesContainer.peekLast().getTranslateX() >= GRID_WIDTH * TILE_SIZE) || (snakeTilesContainer.peekLast().getTranslateX() <= 0) || (snakeTilesContainer.peekLast().getTranslateY() >= GRID_HEIGHT * TILE_SIZE) || (snakeTilesContainer.peekLast().getTranslateY() <= 0)) {  //sprawdzanie czy glowa weza nie styka sie z krawedzia pola gry, glowa moze przejsc przez krawedz
            snakeTilesContainer.peekLast().setVisible(false);
            timer.stop();
            addPlayerToHighScores();
            serializeListToJson(Player.getPlayersHighScores());
            displayGameOverMessage();
            GameOverBoardManager gameOverBoardManager = new GameOverBoardManager();
            gameOverBoardManager.getGameOverScreen(gameStage);
        }

        if (snake.checkSnakeHeadCollisionWitOtherTiles()) {
            timer.stop();
            addPlayerToHighScores();
            if (Player.getPlayersHighScores().size() >= 14) {
                Player.getPlayersHighScores().remove(Player.getPlayersHighScores().size() -1);
            }
            serializeListToJson(Player.getPlayersHighScores());
            displayGameOverMessage();
            GameOverBoardManager gameOverBoardManager = new GameOverBoardManager();
            gameOverBoardManager.getGameOverScreen(gameStage);
        }
    }

    private boolean checkIfSnakeEatApple() {
        return (snakeTilesContainer.peekLast().getTranslateX() == (apple.getTranslateX()) && snakeTilesContainer.peekLast().getTranslateY() == apple.getTranslateY());
    }

    private void createNewApple() {
        Tile appleTurnedToSnakeTile = snake.createSnakeTile((int) apple.getTranslateX(), (int) apple.getTranslateY(), SNAKE_TILE_SIZE);
        snakeTilesContainer.addFirst(appleTurnedToSnakeTile);
        gamePane.getChildren().addAll(appleTurnedToSnakeTile);
        gamePane.getChildren().remove(apple);
        apple = appleCreator.getApple(GRID_WIDTH, GRID_HEIGHT);
        gamePane.getChildren().addAll(apple);
        points += applePoints;
        applePoints = 100;
    }

    private void addPlayerToHighScores() {
        Player player = new Player(playerName, points, actualSnakeSize);
        Player.addPlayerToHighScores(player);
    }

    private void serializeListToJson(List list) {
        if (list.size() >= 15) {
            list.remove(list.size() - 1);
        }
        Gson gson = new Gson();
        try (FileWriter file = new FileWriter("snakeHighScoresList.json")) {
            gson.toJson(list, file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cant find file " + e);
        }
    }

    private List<Player> deSerializeListFromJson() {
        Gson gson = new Gson();
        List<Player> listFromJson = new ArrayList<>();
        try (Reader reader = new FileReader("snakeHighScoresList.json")) {
            Type playerListType = new TypeToken<ArrayList<Player>>() {
            }.getType();
            listFromJson = gson.fromJson(reader, playerListType);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cant find file " + e);
        }
        return listFromJson;
    }

    private void displayGameOverMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Game over ");
        sb.append(playerName);
        sb.append(", you score is: ");
        sb.append(points);
        sb.append(System.getProperty("line.separator"));
        sb.append("===================================================================");
        sb.append(System.getProperty("line.separator"));
        sb.append("HighScores:");
        System.out.println(sb);

        Player.displayPlayerHighScores();
    }
}
