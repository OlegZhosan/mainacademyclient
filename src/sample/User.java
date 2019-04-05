package sample;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private String id;
    private String login;
    private String password;
    private String username;
    private LocalDate birthday;
    private String city;
    private String description;

    public User(String id, String login, String password, String username, LocalDate birthday, String city, String description) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.username = username;
        this.birthday = birthday;
        this.city = city;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(username, user.username) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(city, user.city) &&
                Objects.equals(description, user.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, username, birthday, city, description);
    }

    @Override
    public String toString() {
        return "User{ " +
                "id='" + id + '\n' +
                username + '\n' +
                "birthday=" + birthday + '\n' +
                "city='" + city + "' }";
    }
}
