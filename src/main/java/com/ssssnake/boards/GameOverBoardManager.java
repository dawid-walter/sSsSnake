package com.ssssnake.boards;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssssnake.gameObjects.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOverBoardManager {
    private final int GRID_WIDTH = 30;
    private final int GRID_HEIGHT = 30;
    int TILE_SIZE = 11;
    private AnchorPane gameOverPane;
    private Scene gameOverScene;
    private Stage gameOverStage;

    private Stage stage;

    public GameOverBoardManager() {
        gameOverPane = new AnchorPane();
        gameOverScene = new Scene(gameOverPane, GRID_WIDTH * TILE_SIZE + 150, GRID_WIDTH * TILE_SIZE);
        gameOverStage = new Stage();
        gameOverStage.setScene(gameOverScene);
        createStarAgainButton();
        createBackToMainMenuButton();
        displayGameOverMessage();
        displayHighScore();
    }

    public void getGameOverScreen(Stage stage) {
        this.stage = stage;
        this.stage.hide();
        gameOverStage.show();
    }

    public void createStarAgainButton() {
        Button startButton = new Button("Start Again");
        startButton.setLayoutX(100);
        startButton.setLayoutY(290);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameBoardManager gameManager = new GameBoardManager();
                gameManager.startNewGame(gameOverStage);
            }
        });
        gameOverPane.getChildren().add(startButton);
    }

    public void createBackToMainMenuButton() {
        Button startButton = new Button("Back to Main Menu");
        startButton.setLayoutX(300);
        startButton.setLayoutY(290);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainMenuBoardManager mainMenuManager = new MainMenuBoardManager();
                mainMenuManager.getMainMenuScreen(gameOverStage);
            }
        });
        gameOverPane.getChildren().add(startButton);
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

    public void displayPlayerScore() {


    }

    public void displayGameOverMessage() {
        Text gameOver = new Text(220, 22, "GAME OVER");
        gameOverPane.getChildren().add(gameOver);
    }

    public void displayHighScore() {
        List<Player> playersHighScores = deSerializeListFromJson();
        StringBuilder sb = new StringBuilder();
        Collections.sort(playersHighScores, Collections.reverseOrder());
        for (int i = 0; i < playersHighScores.size(); i++) {
            sb.append((i + 1) + ": " + playersHighScores.get(i));
            sb.append(System.getProperty("line.separator"));
        }
        Text highScores = new Text(80, 50, sb.toString());
        highScores.setTextAlignment(TextAlignment.JUSTIFY);
        gameOverPane.getChildren().add(highScores);
    }
}