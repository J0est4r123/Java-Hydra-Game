package com.example.jawad2_assignment1; // Define Package

// Import Necessary Libraries
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

// Start of GameController Class
public class HydraGame extends Application {
    // Create Stage
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize Game and scene
        FXMLLoader fxmlLoader = new FXMLLoader(HydraGame.class.getResource("hydragame-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 850);

        // Set the window name
        stage.setTitle("Hydra Game");

        // Import Icon
        Image icon = new Image("File:HydraIcon.png");

        // Set the window icon
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    // The Main method
    public static void main(String[] args) {
        launch();
    }
}