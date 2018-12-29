package app;

import main.com.j5.connect.J5ch;
import main.com.j5.connect.ResultSet;

public class Mascot extends Thread {

    @Override
    public void run() {
        String app = "JYW2J6wh9z8p8xjGFxO3M2JppGCyjQ";
        String sec = "hO2QHdapzbqbTFOaJgZTKXgT2gWqYS";
        String auth_x_2ch_ua  = "JaneStyle/4.0.0";
        String auth_useragent = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)";

        String host = "agree";
        String bbs = "liveanarchy";
        String useragent = "Criminalia";

        String title = "Button pushed! hehe (C)";
        String msg = "Thank you for using.";

        J5ch j5 = new J5ch(host, bbs);

        try {
            j5.auth5chAPI(app, sec, auth_useragent, auth_x_2ch_ua);

            j5.setUserAgent(useragent);

            ResultSet rs = j5.post(true, title, "", "", msg);

            System.out.println(rs);

        } catch(Exception e) {
            System.out.println("Oops");
            e.printStackTrace();
        }
    }
}
