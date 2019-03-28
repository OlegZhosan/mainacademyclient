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
    private Button friendListButton;//кнопка "друзья онлайн"
    @FXML
    private Button enterButton;//кнопка "enter"
    @FXML
    private ListView<User> userList;//список друзей нолайн в левой стороне окна
    @FXML
    private PasswordField passwordField;//текстовое поле для ввода пароля
    @FXML
    private TextField loginField;//текстовое поле для ввода логина


    ServerThread st;//соединение с классом ServerThread

    //метод залогинивания клиента
    public void processLogin() {
        String login = loginField.getText();//считывае из поля логин
        String password = passwordField.getText();//считывает из поля пароль
        st = new ServerThread(this);//создает новый обьект класса ServerThread
        st.setDaemon(true);//обьявление каждого созданного потока демоном
        st.setLoginPassword(login, password);//передает в ServerThread считанный логин и пароль
        st.start();//запускает поток

        //активности и неактивность кнопок по умолчанию и после нажатия
        if (st.isAlive()) {//если поток запущен
            enterButton.disableProperty().set(true);//кнопку отключить
            friendListButton.disableProperty().set(false);//кнопку включить
//            showOnlineFriends();
        }
    }

    //метод вывода списока друзей нолайн
    //загружает обновленный список пользователей онлайн
    public void showList(List<User> users) {
        //TODO Показать список в окне
        Platform.runLater(()->userList.setItems(FXCollections.observableList(users)));
        //Platform - стандартный класс JavaFX
        //Platform.runLater - для того, что бы не бросало на этом месте Exeption
        //Platform.runLater - остроченный запуск!!!
    }

    public void showOnlineFriends() {
        st.showFriendsOnline();
    }
}
