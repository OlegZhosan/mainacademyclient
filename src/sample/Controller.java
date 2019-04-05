package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class Controller {
    @FXML public TabPane tabPane;
    @FXML public Tab messagingTab;
    @FXML public TextField messageField;
    @FXML public Label messageLabel;
    @FXML public TextField loginField;
    @FXML public PasswordField passwordField;
    @FXML public TextField usernameField;
    @FXML public TextField birthdayField;
    @FXML public TextField cityField;
    @FXML public TextField descriptionField;
    @FXML public TextField loginForLoginField;
    @FXML public PasswordField passwordForLoginField;
    @FXML public Label userLabel;
    @FXML public ListView<User> onlineUsersList;
    @FXML public Button loginButton;
    @FXML public Button onlineUsersButton;
    @FXML public Button messagingButton;
    @FXML public Button outputButton;
    @FXML public Button startButton;

    private ServerThread serverThread;

    private List<User> onlineUsers;
    private String loginSender;
    String messageFromClient;

    public void startSeverThread(){
        serverThread = new ServerThread(this);
        serverThread.start();
        loginButton.disableProperty().set(false);
    }

    public void registrationButton() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();
        String birthday = birthdayField.getText();
        String city = cityField.getText();
        String description = descriptionField.getText();
        String registration = ("register").concat(":").concat(login).concat(":").concat(password).concat(":").concat(username).concat(":").concat(birthday).concat(":").concat(city).concat(":").concat(description);
        serverThread.register(registration);
    }

    public void loginButton() throws IOException {
        String login = loginForLoginField.getText();
        String password = passwordForLoginField.getText();
        String entrance = ("login").concat(":").concat(login).concat(":").concat(password);
        serverThread.login(entrance);
    }

    public void outputButton() throws IOException {
        String output = "output";
        serverThread.output(output);
    }

    public void onlineUsers() throws IOException {
        String online = "online";
        serverThread.online(online);
    }

    public void showList(List<User> users) {
        Platform.runLater(()->onlineUsersList.setItems(FXCollections.observableList(users)));
        onlineUsers = users;
    }

    public void goToMessaging() {
        int getNumber = onlineUsersList.getSelectionModel().getSelectedIndex();
        tabPane.getSelectionModel().select(2);
        this.loginSender = onlineUsers.get(getNumber).getLogin();
    }

    public void sendMessageButton() throws IOException {
        String correspondence = ("message").concat(":").concat(loginSender).concat(":").concat(serverThread.loginRecipient).concat(":").concat(messageField.getText());
        serverThread.correspondence(correspondence);
    }

    public void inclusionButtonsOn() {
        loginButton.disableProperty().set(true);//кнопку отключить
        startButton.disableProperty().set(true);//кнопку отключить
        outputButton.disableProperty().set(false);//кнопку включить
        onlineUsersButton.disableProperty().set(false);//кнопку включить
        messagingButton.disableProperty().set(false);//кнопку включить
    }

    public void inclusionButtonsOff() {
        loginButton.disableProperty().set(false);
        startButton.disableProperty().set(false);
        outputButton.disableProperty().set(true);
        onlineUsersButton.disableProperty().set(true);
        messagingButton.disableProperty().set(true);
    }

    public void getMessageButton(ActionEvent actionEvent) {
        messageLabel.setText(messageFromClient);
    }
}
