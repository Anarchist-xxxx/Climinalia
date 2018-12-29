package model;

public class ThreadSearchItem {
    public static final int KEY = 0;
    public static final int TITLE = 1;

    int option;
    String name;

    public ThreadSearchItem(int option, String name) {
        this.option = option;
        this.name = name;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
