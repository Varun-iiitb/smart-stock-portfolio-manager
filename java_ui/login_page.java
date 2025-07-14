package com.example.tradesight;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class login_page extends Application {
    Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Tradesight Login");

        // Load image
        Image logo = new Image(getClass().getResourceAsStream("/tradesight.png"));
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(300);

        // Username
        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(250);
        username.getStyleClass().add("username-btn");

        Label errormessage = new Label("Invalid username or password");
        errormessage.setStyle("-fx-text-fill: red;");
        errormessage.setVisible(false);

        // Password
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.getStyleClass().add("password-btn");

        TextField password1 = new TextField();
        password1.textProperty().bindBidirectional(password.textProperty());
        password1.setVisible(false);
        password1.setManaged(false);
        password1.getStyleClass().add("password-btn");

        Button visible1 = new  Button("👁");
        visible1.setPrefHeight(45);
        visible1.setOnAction(e -> {
            password.setVisible(false);
            password.setManaged(false);
            password1.setVisible(true);
            password1.setManaged(true);
        });

        HBox hbox1 = new HBox(10);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().addAll(password, password1, visible1);

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-btn");

        loginButton.setOnAction(e -> {
            String request = login_verification.loginUser(username.getText(), password1.getText());
            if (request.equals("Login successful")) {
                dashboard dash = new dashboard();
                Scene dashscene = new Scene(dash.getlayout(username.getText(), window), 1000, 1000);
                dashscene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                window.setScene(dashscene);
            } else if (request.equals("Invalid credentials")) {
                errormessage.setVisible(true);
                username.clear();
                password.clear();
            }
        });

        // Register section
        Label newLabel = new Label("Don't have an account?");
        newLabel.setStyle("-fx-font-size: 16px;-fx-text-fill: white;");
        Button registerButton = new Button("Sign up");
        registerButton.getStyleClass().add("signup-btn");

        registerButton.setOnAction(e -> {
            register_page regPage = new register_page();
            Scene registerScene = new Scene(regPage.getLayout(window), 1000, 1000);
            registerScene.getStylesheets().add(getClass().getResource("/register.css").toExternalForm());
            window.setScene(registerScene);
        });

        HBox hbox = new HBox(10, newLabel, registerButton);
        hbox.setAlignment(Pos.CENTER);

        // Layout
        VBox layout = new VBox(15, logoView, username, hbox1, loginButton, hbox);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));

        // ✅ Add background image here
        Image backgroundImg = new Image(getClass().getResource("/background.jpg").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        layout.setBackground(new Background(bgImage));

        // Scene
        Scene scene = new Scene(layout, 1000, 1000);
        scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
