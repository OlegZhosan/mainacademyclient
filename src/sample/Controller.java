//отвечает за работу окна графического интерфейса???

package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class Controller {

    public Tab idDialog;
    public Tab idUserLogin;
    public TabPane idTabPane;

    ServerThread st;//соединение с классом ServerThread

    //кнопка "друзья онлайн"
    @FXML
    private Button friendListButton;

    //кнопка "enter"
    @FXML
    private Button enterButton;

    //список друзей нолайн в левой стороне окна
    @FXML
    private ListView<User> userList;

    //текстовое поле для ввода пароля
    @FXML
    private PasswordField passwordField;

    //текстовое поле для ввода логина
    @FXML
    private TextField loginField;

    //метод залогинивания клиента
    public void processLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();
        st = new ServerThread(this);
        st.setDaemon(true);//???
        st.setLoginPassword(login, password);//залогинивание???
        st.start();

        //активности и неактивность кнопок по умолчанию и после нажатия
        if (st.isAlive()) {
            enterButton.disableProperty().set(true);
            friendListButton.disableProperty().set(false);
        }

        //переключение между владками
        idTabPane.getSelectionModel().select(1);
    }

    //метод вывода списока друзей нолайн
    public void showList(List<User> users) {
        Platform.runLater(()->userList.setItems(FXCollections.observableList(users)));
        //Platform.runLater - для того, что бы не бросало на этом месте Exeption
        //Platform.runLater - остроченный запуск!!!
    }

    //метод кнопки вывода списка друзей онлайн
    public void showOnlineFriends() {
        st.showFriendsOnline();
    }

    public void processExit(ActionEvent actionEvent) {
        st.processExit();

        //переключение между владками
        idTabPane.getSelectionModel().select(0);
    }
}
