package sample;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет
    private static BufferedReader reader;//reader считывет из консоли

    Controller controller;
    String loginRecipient;

    public ServerThread(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        try {
            try {
                clientSocket = new Socket("localhost", 1234);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                out.write("Client OK" + "\n");
                out.flush();
                while (true) {
                    String message = in.readLine();
                    if (message.startsWith("[")){
                        getOnlineUsers(message);
                    } else if (message.startsWith("new")){
                        System.out.println(message);
                        controller.messageFromClient = message;
                    } else System.out.println(message);
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();
                }
            } finally {
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void getOnlineUsers(String message) {
        List<User> onlineUsers = new ArrayList<>();
        String onlineUsersMessage = message.substring(1, message.length() - 1);
        String[] users = onlineUsersMessage.split(",");
        for (int i = 0; i < users.length; i++) {
            try {
                String[] s = users[i].split(":");
                String id = s[0];
                String login  = s[1];
                String password = s[2];
                String username = s[3];
                String dateStr = s[4];
                String[] split = dateStr.split("\\D");
                LocalDate birthday = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                String city = s[5];
                String description = s[6];
                User user = new User(id, login, password, username, birthday, city, description);
                onlineUsers.add(user);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        for (User onlineUser : onlineUsers) {
            System.out.println(onlineUser);
        }
        controller.showList(onlineUsers);
    }

    public void register(String registration) throws IOException {
        out.write(registration + "\n");
        out.flush();
        String message = in.readLine();
        System.out.println(message);
    }

    public void login(String entrance) throws IOException {
        out.write(entrance + "\n");
        out.flush();
        String message = in.readLine();
        String[] s = message.split(":");
        String id = s[0];
        String login  = s[1];
        String password = s[2];
        String username = s[3];
        String dateStr = s[4];
        String[] split = dateStr.split("\\D");
        LocalDate birthday = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        String city = s[5];
        String description = s[6];
        User user = new User(id, login, password, username, birthday, city, description);
        String loginRecipient = user.getLogin();
        this.loginRecipient = loginRecipient;
        System.out.println(user);
        controller.userLabel.setText(user.toString());
        controller.inclusionButtonsOn();
    }

    public void output(String output) throws IOException {
        out.write(output + "\n");
        out.flush();
        String message = in.readLine();
        System.out.println(message);
        controller.inclusionButtonsOff();
    }

    public void online(String online) throws IOException {
        out.write(online + "\n");
        out.flush();
        String message = in.readLine();
        getOnlineUsers(message);
    }

    public void correspondence(String correspondence) throws IOException {
        System.out.println(correspondence);
        out.write(correspondence + "\n");
        out.flush();
    }
}
