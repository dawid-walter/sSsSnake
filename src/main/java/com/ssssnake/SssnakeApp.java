package com.ssssnake;

import com.ssssnake.boards.MainMenuBoardManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class SssnakeApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MainMenuBoardManager manager = new MainMenuBoardManager();
            primaryStage = manager.getMainMenuStage();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}