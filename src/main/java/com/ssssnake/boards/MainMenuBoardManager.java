package com.ssssnake.boards;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class MainMenuBoardManager {
    private final int GRID_WIDTH = 30;
    private final int GRID_HEIGHT = 30;
    int TILE_SIZE = 11;
    private AnchorPane mainMenuPane;
    private Scene mainMenuScene;
    private Stage mainMenuStage;

    private Stage stage;

    public MainMenuBoardManager() {
        mainMenuPane = new AnchorPane();
        mainMenuScene = new Scene(mainMenuPane, GRID_WIDTH * TILE_SIZE + 150, GRID_WIDTH * TILE_SIZE);
        mainMenuStage = new Stage();
        mainMenuStage.setScene(mainMenuScene);
        createStartButton();
        createSnakeAscii();
        //creatBackground();
        //createLogo();
    }

    public Stage getMainMenuStage() {
        return mainMenuStage;
    }

    public void createStartButton() {
        Button startButton = new Button("Start");
        startButton.setLayoutX(200);
        startButton.setLayoutY(200);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameBoardManager gameManager = new GameBoardManager();
                gameManager.startNewGame(mainMenuStage);
            }
        });
        mainMenuPane.getChildren().add(startButton);
    }

    public void creatBackground() {
        Image image = new Image("index.jpg", 256, 256, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainMenuPane.setBackground(new Background(backgroundImage));
    }

    public void createLogo() {
        ImageView logo = new ImageView("microsoftedgenewlogo.jpg");
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        logo.setLayoutX(100);
        logo.setLayoutY(150);
        mainMenuPane.getChildren().add(logo);
    }

    public void getMainMenuScreen(Stage stage) {
        this.stage = stage;
        this.stage.hide();
        mainMenuStage.show();
    }

    public void createSnakeAscii() {
        String snake = "\n" +
                "\n" +
                "                          .-=-.          .--.\n" +
                "              __        .'     '.       /  \" )\n" +
                "      _     .'  '.     /   .-.   \\     /  .-'\\\n" +
                "     ( \\   / .-.  \\   /   /   \\   \\   /  /    ^\n" +
                "      \\ `-` /   \\  `-'   /     \\   `-`  /\n" +
                "       `-.-`     '.____.'       `.____.'\n" +
                "\n" +
                "\n";
        Text text = new Text(10, 10, snake);
        mainMenuPane.getChildren().add(text);

    }
}
