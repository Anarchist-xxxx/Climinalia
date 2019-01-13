package app;

import dao.DAO;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.com.j5.connect.J5ch;
import main.com.j5.connect.ResultSet;
import model.PostSearchItem;
import model.Thread5ch;
import model.ThreadSearchItem;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FrameController implements Initializable {
    @FXML
    private TableView<Thread5ch> listTable;

    @FXML
    private TableColumn<Thread5ch, String> columnKey;

    @FXML
    private TableColumn<Thread5ch, Integer> columnEnd;

    @FXML
    private TableColumn<Thread5ch, String> columnTitle;

    @FXML
    private TableColumn<Thread5ch, String> columnStartTime;

    @FXML
    private TableColumn<Thread5ch, String> columnEndTime;

    @FXML
    private Button buttonUpdate;

    @FXML
    private TabPane threadTabPane;

    @FXML
    private ChoiceBox<ThreadSearchItem> choiceThreadSearch;

    @FXML
    private TextField textFieldThreadSearch;

    @FXML
    private Button buttonThreadSearch;

    @FXML
    private ChoiceBox<PostSearchItem> choicePostSearch;

    @FXML
    private TextField textFieldPostSearch;

    @FXML
    private Button buttonPostSearch;

    @FXML
    public void buttonUpdateAction(ActionEvent event) {
        updateList();
    }

    @FXML
    public void threadSearchAction(ActionEvent event) {
        if(choiceThreadSearch.getValue() != null) {
            DAO dao = new DAO();

            switch(choiceThreadSearch.getValue().getOption()) {
                case ThreadSearchItem.KEY:
                    updateList(dao.getThreadListByKey(textFieldThreadSearch.getText()));
                    break;

                case ThreadSearchItem.TITLE:
                    updateList(dao.getThreadListByTitle(textFieldThreadSearch.getText()));
                    break;
            }
        }
    }

    @FXML
    public void postSearchAction(ActionEvent event) {
        if(choicePostSearch.getValue() != null) {
            DAO dao = new DAO();

            switch(choicePostSearch.getValue().getOption()) {
                case PostSearchItem.NUMBER:

                    int target = 0;
                    try {
                        target = Integer.parseInt(textFieldPostSearch.getText());
                    } catch(NumberFormatException e) {
                        System.out.println("不正な値"); //あとで編集
                    }
                    updateList(dao.getThreadListByPostNumber(target));
                    break;

                case PostSearchItem.NAME:
                    updateList(dao.getThreadListByPostName(textFieldPostSearch.getText()));
                    break;

                case PostSearchItem.MAIL:
                    updateList(dao.getThreadListByPostMail(textFieldPostSearch.getText()));
                    break;

                case PostSearchItem.TIME:
                    updateList(dao.getThreadListByPostTime(textFieldPostSearch.getText()));
                    break;

                case PostSearchItem.UID:
                    updateList(dao.getThreadListByPostUid(textFieldPostSearch.getText()));
                    break;

                case PostSearchItem.COMMENT:
                    updateList(dao.getThreadListByPostComment(textFieldPostSearch.getText()));
                    break;
            }
        }

    }

    @FXML
    public void openSetting(ActionEvent event) {
        // n/a
    }

    @FXML
    public void anarchyAction(ActionEvent event) {
        Mascot k = new Mascot();

        k.start();
    }

    @FXML
    public void openFixPanelAction(ActionEvent event) {
        try {
            openFixPanel();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void openFixPanel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fix_panel.fxml"));
        BorderPane root = (BorderPane) loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void initialize(URL url, ResourceBundle resourcebundle) {
        checkFiles();

        TableView.TableViewSelectionModel<Thread5ch> selectionModel = listTable.getSelectionModel();

        //テーブルの行をクリックしたとき
        selectionModel.selectedItemProperty().addListener((Observable observable) -> {
            if(selectionModel.getSelectedItem() != null) {
                Thread5ch th = selectionModel.getSelectedItem();

                Tab tab = new Tab();
                String tabTitle = th.getTitle();

                if(tabTitle.length() >= 12) {
                    tabTitle = tabTitle.substring(0, 11) + "..";
                }

                tab.setText(tabTitle);
                tab.setContent(new DetailController(th));

                threadTabPane.getTabs().add(tab);
                threadTabPane.getSelectionModel().select(tab);
            }

        });

        threadTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        //TableColumnに値をセット！
        columnKey.setCellValueFactory(new PropertyValueFactory<Thread5ch, String>("key"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<Thread5ch, Integer>("end"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Thread5ch, String>("title"));
        columnStartTime.setCellValueFactory(new PropertyValueFactory<Thread5ch, String>("startTime"));
        columnEndTime.setCellValueFactory(new PropertyValueFactory<Thread5ch, String>("endTime"));

        //ChoiceBox
        choiceThreadSearch.getItems().addAll(
                new ThreadSearchItem(ThreadSearchItem.KEY, "Key"),
                new ThreadSearchItem(ThreadSearchItem.TITLE, "Title")
        );

        choicePostSearch.getItems().addAll(
                new PostSearchItem(PostSearchItem.NUMBER, "Number"),
                new PostSearchItem(PostSearchItem.NAME, "Name"),
                new PostSearchItem(PostSearchItem.MAIL, "Mail"),
                new PostSearchItem(PostSearchItem.TIME, "Time"),
                new PostSearchItem(PostSearchItem.UID, "Uid"),
                new PostSearchItem(PostSearchItem.COMMENT, "Comment")
        );

        //一覧初期化！
        updateList();
    }

    public void updateList(ArrayList<Thread5ch> list) {
        listTable.setItems(FXCollections.observableArrayList());

        for(Thread5ch foo: list) {
            listTable.getItems().add(foo);
            System.out.println("StartTime: " + foo.getStartTime());
        }
    }

    public void updateList() {
        listTable.setItems(FXCollections.observableArrayList());

        DAO dao = new DAO();

        ArrayList<Thread5ch> list = list = dao.getThreadList();

        for(Thread5ch foo: list) {
            listTable.getItems().add(foo);
        }
    }

    private void checkFiles() {
        File dirData = new File("data/");

        if(!dirData.exists()) {
            dirData.mkdir();
        }

        File dirHtml = new File("data/html/");

        if(!dirHtml.exists()) {
            dirHtml.mkdir();
        }

        File dirCss = new File("data/html/css/");

        if(!dirCss.exists()) {
            dirCss.mkdir();
        }

        File css = new File("data/html/css/thread.css");

        if(!css.exists()) {
            try {
                InputStream is = getClass().getResourceAsStream("/thread.css");
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(css)));

                String line;

                while((line = br.readLine()) != null) {
                    pw.println(line);
                }

                pw.close();
            } catch(IOException e) {
                e.printStackTrace();
            }

        }

        File dirDat = new File("data/dat/");

        if(!dirDat.exists()) {
            dirDat.mkdir();
        }

        File db = new File("data/database.db");

        if(!db.exists()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "data/database.dbが存在しません", ButtonType.OK);
            alert.getDialogPane().setHeaderText("エラー");
            alert.showAndWait().orElse(ButtonType.NO);
            System.exit(0);
        }

        //行のチェック
        DAO dao = new DAO();
        dao.fixColumn();
    }

}
