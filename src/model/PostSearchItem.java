package model;

public class PostSearchItem {
    public static final int NUMBER = 0;
    public static final int NAME = 1;
    public static final int MAIL = 2;
    public static final int TIME = 3;
    public static final int UID = 4;
    public static final int COMMENT = 5;

    private int option;
    private String name;

    public PostSearchItem(int option, String name) {
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
