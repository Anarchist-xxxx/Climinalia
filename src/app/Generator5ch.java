package app;

import model.Post5ch;
import model.Thread5ch;

import java.io.*;
import java.util.ArrayList;

public class Generator5ch {

    public boolean generateHtml(Thread5ch th, ArrayList<Post5ch> posts) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/html/" + th.getKey() + ".html")));

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"ja\">");
            pw.println("<head>");
            pw.println("<meta charset=\"UTF-8\">");
            pw.println("<title>" + th.getTitle() + "</title>");
            pw.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/thread.css\">");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<div class=\"wrap\">");
            pw.println("<div class=\"thread\">");
            pw.println("<div class=\"thread-header\">");
            pw.println("<h1 class=\"title\">");
            pw.println(th.getTitle());
            pw.println("</h1>");
            pw.println("<p class=\"key\">");
            pw.println(th.getKey());
            pw.println("</p>");
            pw.println("<p class=\"end\">");
            pw.println(th.getEnd() + "コメント");
            pw.println("</p>");
            pw.println("</div>");
            pw.println("<div class=\"thread-body\">");

            for(Post5ch post: posts) {
                pw.println("<div class=\"post\">");
                pw.println("<div class=\"meta\">");
                pw.println("<p>");
                pw.println("<span class=\"number\">" + post.getNumber() + "</span>");
                pw.println("<span class=\"name\">" + post.getName() + "</span>");
                pw.println("<span class=\"date\">" + post.getTime() + "</span>");
                pw.println("<span class=\"uid\">");
                if(post.getUid() != null) {
                    pw.print(post.getUid());
                }
                pw.println("</span>");
                pw.println("</p>");
                pw.println("</div>");
                pw.println("<div class=\"message\">");
                pw.println("<p>");
                pw.println(post.getComment());
                pw.println("</p>");
                pw.println("</div>");
                pw.println("</div>");
            }

            pw.println("</div>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");

            pw.close();

            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println(posts.size());

        return false;
    }

    public boolean generateDat(Thread5ch th, ArrayList<Post5ch> posts) {
        try {

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data/dat/" + th.getKey() + ".dat")));

            System.out.println(posts.size()

            );

            Post5ch first = posts.get(0);

            pw.print(first.getName() + "<>");
            pw.print(first.getMail() + "<>");
            pw.print(first.getTime());
            if(first.getUid() != null || !"".equals(first.getUid())) {
                pw.print("ID:" + first.getUid());
            }
            pw.print("<>");
            pw.print(first.getComment());
            pw.print("<>");
            pw.print(th.getTitle());
            pw.println();

            posts.remove(0);

            for(Post5ch post: posts) {
                pw.print(post.getName() + "<>");
                pw.print(post.getMail() + "<>");
                pw.print(post.getTime());
                if(post.getUid() != null || !"".equals(post.getUid())) {
                    pw.print("ID:" + post.getUid());
                }
                pw.print("<>");
                pw.print(post.getComment());
                pw.print("<>");
                pw.println();
            }

            pw.close();

        } catch(IOException e) {
            e.printStackTrace();
            return false;
        } catch(IndexOutOfBoundsException e) {
            System.out.println("ArrayList void");
            //要素がないー
            return false;
        }

        return true;
    }
}
