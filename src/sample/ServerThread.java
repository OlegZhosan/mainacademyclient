//реализация работы клиента с сервером

package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ServerThread extends Thread {

    Controller mainController;//соединение с классом Controller
    String login;
    String password;
    Properties props;//соединение со стандартным классом Properties

    BufferedReader reader;
    PrintWriter writer;

    public ServerThread(Controller mainController) {
        this.mainController = mainController;//???
        props = new Properties();//???
        try {
            props.load(Files.newBufferedReader(Paths.get("client.cfg")));//вытягивание свойств подключения к серверу из файла "client.cfg"
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(
                props.getProperty("host"),//вытягивание свойств подключения к серверу из файла "client.cfg"
                Integer.parseInt(props.getProperty("port", "1234"))//вытягивание свойств подключения к серверу из файла "client.cfg"
                //1234 - значение для порта по умолчанию
        )) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //(new InputStreamReader(socket.getInputStream()))???
            writer = new PrintWriter(socket.getOutputStream(), true);
            String line = reader.readLine();//строка, получаемая от сервера

            //залогинится
            if (! "Server Ok".equals(line)) return;//если от сервера не получена строка "Server Ok" - возврат отсюда
            writer.println(String.join(";", "login", login, password));//???

            //добавление в список потоков онлайн???

            line = reader.readLine();//???
            if (! "Login Ok".equals(line)) return;//если от сервера не получена строка "Login Ok" - возврат отсюда

            processMessages(reader, writer);//???
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод обработки полученного сообщения
    private void processMessages(BufferedReader reader, PrintWriter writer) throws IOException {
        String line;
        while ((line=reader.readLine())!=null) {//пока полученное сообщение что то содержит
            // 1 - обычное сообщение от другого клиента
            //TODO обработать и отобразить сообщение

            // 2 - сообщение от сервера
            if ("<<<".equals(line)) {//если полученное сообщение содержит "<<<"
                List<User> users = new ArrayList<>();//создай ArrayList из User
                while(!(line=reader.readLine()).equals("<<<")) {//пока полеченное сообщение не содержит "<<<"
                    String[] s = line.split(";");//разделяй полученные строки по символу ";"
                    User u = new User(Integer.parseInt(s[0]), s[1], s[2]);//приесвоение значений обьекту User
                    users.add(u);//добавляй этот обьект в с список users
                }
                mainController.showList(users);//вызов метода showList
            }
        }
    }

    //залогинивание???
    public void setLoginPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    //метод кнопки вывода списка друзей онлайн
    //передает серверу строку "<<<"
    //вызов списка пользователей онлайн по нажатию на кнопку???
    public void showFriendsOnline() {
        writer.println("<<<");
    }

    public void processExit() {
        writer.println(">>>exit<<<");
    }
}
