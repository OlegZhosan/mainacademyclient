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

    Controller mainController;
    String login;
    String password;
    Properties props;

    BufferedReader reader;
    PrintWriter writer;

    public ServerThread(Controller mainController) {
        this.mainController = mainController;
        props = new Properties();
        try {
            props.load(Files.newBufferedReader(Paths.get("client.cfg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(
                props.getProperty("host"),
                Integer.parseInt(props.getProperty("port", "1234"))
        )) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            String line = reader.readLine();
            if (! "Server Ok".equals(line)) {
                return;
            }
            writer.println(String.join(";", "login", login, password));
            line = reader.readLine();
            if (! "Login Ok".equals(line)) return;
            processMessages(reader, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessages(BufferedReader reader, PrintWriter writer) throws IOException {
        String line;
        while ((line=reader.readLine())!=null) {
            // 1 - обычное сообщение от другого клиента
            //TODO обработать и отобразить сообщение

            // 2 - сообщение от сервера
            if ("<<<".equals(line)) {
                List<User> users = new ArrayList<>();
                String str;
                while(!(line=reader.readLine()).equals("<<<")) {
                    String[] s = line.split(";");
                    User u = new User(Integer.parseInt(s[0]), s[1], s[2]);
                    users.add(u);
                }
                mainController.showList(users);
            }

        }
    }

    public void setLoginPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void showFriendsOnline() {
        writer.println("<<<");
    }
}
