package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class Controller {

    @FXML
    private Button friendListButton;
    @FXML
    private Button enterButton;
    @FXML
    private ListView<User> userList;
    ServerThread st;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;

    public void processLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();
        st = new ServerThread(this);
        st.setDaemon(true);
        st.setLoginPassword(login, password);
        st.start();
        if (st.isAlive()) {
            enterButton.disableProperty().set(true);
            friendListButton.disableProperty().set(false);
//            showOnlineFriends();
        }
    }

    public void showList(List<User> users) {
        //TODO Показать список в окне
        Platform.runLater(()->userList.setItems(FXCollections.observableList(users)));
    }

    public void showOnlineFriends() {
        st.showFriendsOnline();
    }
}
