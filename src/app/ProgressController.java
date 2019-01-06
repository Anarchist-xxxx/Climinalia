package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {
    @FXML
    ProgressBar pb;

    @FXML
    Label now;

    @FXML
    Label end;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pb.setProgress(0.0);
    }

    public void setEnd(int end) {
        this.end.setText(Integer.toString(end));
    }

    public void setNow(int now) {
        this.now.setText(Integer.toString(now));
        pb.setProgress(1.0 / now);
    }
}
