package com.student.portretov.lab5_database;

/**
 * Created by adminvp on 9/8/17.
 */

public class Order {

    private long id;
    private String login;
    private String password;
    private String email;
    private String title;

    public Order() {}

    public Order(String login, String password, String email, String title) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.title = title;
    }

    public Order(long id, String login, String password, String email, String title) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return
                "Заявка №" + id + " - '" + title + "\'" +
                ", Логин ='" + login + '\'' +
                ", Пароль ='" + password + '\'' +
                ", Е-мэил ='" + email + '\'';
    }
}
