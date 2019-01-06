package dao;

import model.Post5ch;
import model.Thread5ch;
import model.Thread5ch;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DAO {
    private final String DATABASE = "data/database.db";
    Connection con;

    public DAO() {
        con = getConnection();
    }

    private Connection getConnection() {
        Connection result = null;

        try {
            Class.forName("org.sqlite.JDBC");
            result = DriverManager.getConnection("jdbc:sqlite:" + DATABASE);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found");
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByTitle(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql = "select key, title, end from threads where title like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Thread5ch foo = new Thread5ch();
                foo.setKey(rs.getString("key"));
                foo.setTitle(rs.getString("title"));
                foo.setEnd(rs.getInt("end"));

                result.add(foo);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getThreadEnd(Thread5ch th) {
        try {
            String sql = "select count(*) as end from posts where key = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, th.getKey());

            ResultSet rs = pst.executeQuery();

            return rs.getInt("end");

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public synchronized boolean insertEnd(Thread5ch th) {
        try {
            String sqlInsert = "update threads set end = ? where key = ?";
            PreparedStatement pstInsert = con.prepareStatement(sqlInsert);
            pstInsert.setInt(1, th.getEnd());
            pstInsert.setString(2, th.getKey());

            if(pstInsert.executeUpdate() > 0) {
                return true;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Thread5ch> getThreadList() {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql = "select key, title, end from threads order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Thread5ch foo = new Thread5ch();
                foo.setKey(rs.getString("key"));
                foo.setTitle(rs.getString("title"));
                foo.setEnd(rs.getInt("end"));

                result.add(foo);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Post5ch> getPosts(String key) {
        ArrayList<Post5ch> result = new ArrayList<Post5ch>();

        String sql = "select number, comment, time, name, mail, uid from posts where key = ? order by number";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, key);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Post5ch post = new Post5ch();
                post.setNumber(rs.getInt("number"));
                post.setComment(rs.getString("comment"));
                post.setTime(rs.getString("time"));
                post.setName(rs.getString("name"));
                post.setMail(rs.getString("mail"));
                post.setUid(rs.getString("uid"));

                result.add(post);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByKey(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql = "select key, title, end from threads where key like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Thread5ch foo = new Thread5ch();
                foo.setKey(rs.getString("key"));
                foo.setTitle(rs.getString("title"));
                foo.setEnd(rs.getInt("end"));

                result.add(foo);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByPostName(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql1 = "select key from posts where name like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs1 = pst.executeQuery();

            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

            while(rs1.next()) {
                hm.put(rs1.getString("key"), true);
            }

            ArrayList<String> keySet = new ArrayList<String>();

            for(String key: hm.keySet()) {
                result.addAll(getThreadListByKey(key));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByPostMail(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql1 = "select key from posts where mail like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs1 = pst.executeQuery();

            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

            while(rs1.next()) {
                hm.put(rs1.getString("key"), true);
            }

            ArrayList<String> keySet = new ArrayList<String>();

            for(String key: hm.keySet()) {
                result.addAll(getThreadListByKey(key));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByPostTime(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql1 = "select key from posts where Time like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs1 = pst.executeQuery();

            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

            while(rs1.next()) {
                hm.put(rs1.getString("key"), true);
            }

            ArrayList<String> keySet = new ArrayList<String>();

            for(String key: hm.keySet()) {
                result.addAll(getThreadListByKey(key));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByPostUid(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql1 = "select key from posts where uid like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs1 = pst.executeQuery();

            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

            while(rs1.next()) {
                hm.put(rs1.getString("key"), true);
            }

            ArrayList<String> keySet = new ArrayList<String>();

            for(String key: hm.keySet()) {
                result.addAll(getThreadListByKey(key));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByPostComment(String keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql1 = "select key from posts where comment like ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs1 = pst.executeQuery();

            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

            while(rs1.next()) {
                hm.put(rs1.getString("key"), true);
            }

            ArrayList<String> keySet = new ArrayList<String>();

            for(String key: hm.keySet()) {
                result.addAll(getThreadListByKey(key));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<Thread5ch> getThreadListByPostNumber(int keyword) {
        ArrayList<Thread5ch> result = new ArrayList<Thread5ch>();
        String sql1 = "select key from posts where number = ? order by key desc";

        try {
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setInt(1, keyword);
            ResultSet rs1 = pst.executeQuery();

            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();

            while(rs1.next()) {
                hm.put(rs1.getString("key"), true);
            }

            ArrayList<String> keySet = new ArrayList<String>();

            for(String key: hm.keySet()) {
                result.addAll(getThreadListByKey(key));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
