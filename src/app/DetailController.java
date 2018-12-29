package app;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.com.j5.connect.J5ch;
import model.Post5ch;
import model.Thread5ch;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetailController extends BorderPane implements Initializable {

    private Thread5ch th;
    private ArrayList<Post5ch> posts;

    @FXML
    TableView<Post5ch> tableDetail;

    @FXML
    TableColumn number;

    @FXML
    TableColumn comment;

    @FXML
    TableColumn time;

    @FXML
    TableColumn name;

    @FXML
    TableColumn mail;

    @FXML
    TableColumn uid;

    @FXML
    Button buttonOpenHtml;

    public DetailController(Thread5ch th) {
        this.th = th;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("thread_detail.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDetailTable() {
        tableDetail.setItems(FXCollections.observableArrayList());

        DAO dao = new DAO();

        posts = dao.getPosts(th.getKey());

        this.posts = posts;

        int max = -1;
        for(Post5ch post: posts) {
            if(post.getNumber() > max) {
                max = post.getNumber();
            }
        }

        System.out.println(max);

        for(Post5ch tmp: posts) {
            tableDetail.getItems().add(tmp);
        }
    }

    @FXML
    public void generateHtml() {
        Generator5ch g5 = new Generator5ch();

        g5.generateHtml(th, posts);

        existsHtml();
    }

    @FXML
    public void generateDat() {
        Generator5ch g5 = new Generator5ch();

        g5.generateDat(th, posts);
    }

    @FXML
    public void openHtml() {
        File html = new File("data/html/" + th.getKey() + ".html");
        Desktop desktop = Desktop.getDesktop();

        try {
            URI uri = new URI("file://" + html.getAbsolutePath());
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void existsHtml() {
        buttonOpenHtml.setDisable(true);
        File html = new File("data/html/" + th.getKey() + ".html");

        if(html.exists()) {
            buttonOpenHtml.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TableColumnのセット
        number.setCellValueFactory(new PropertyValueFactory<Post5ch, Integer>("number"));
        comment.setCellValueFactory(new PropertyValueFactory<Post5ch, String>("comment"));
        time.setCellValueFactory(new PropertyValueFactory<Post5ch, String>("time"));
        name.setCellValueFactory(new PropertyValueFactory<Post5ch, String>("name"));
        mail.setCellValueFactory(new PropertyValueFactory<Post5ch, String>("mail"));
        uid.setCellValueFactory(new PropertyValueFactory<Post5ch, String>("uid"));

        updateDetailTable();
    }

}
