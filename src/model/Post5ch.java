package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Post5ch {
    int number;
    String name;
    String mail;
    String time;
    String uid;
    String comment;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public IntegerProperty numberProperty() {
        return new SimpleIntegerProperty(number);
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty mailProperty() {
        return new SimpleStringProperty(mail);
    }

    public StringProperty timeProperty() {
        return new SimpleStringProperty(time);
    }

    public StringProperty uidProperty() {
        return new SimpleStringProperty(uid);
    }

    public StringProperty commentProperty() {
        return new SimpleStringProperty(comment);
    }

    public Post5ch() {
    }
}

