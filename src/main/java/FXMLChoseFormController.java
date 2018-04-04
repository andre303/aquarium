import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLChoseFormController implements Initializable{

    @FXML
    private JFXButton chose_3;

    @FXML
    private JFXButton chose_1;

    @FXML
    private JFXButton chose_2;

    @FXML
    void onAcrionFirstButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Ви вибрали рівень 1");
        alert.setHeaderText(null);
        alert.showAndWait();
        DataClass.aglae_quantiy = 10;
        Platform.exit();
    }

    @FXML
    void onAcrionSecondButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Ви вибрали рівень 2");
        alert.setHeaderText(null);
        alert.showAndWait();
        DataClass.aglae_quantiy = 20;
        Platform.exit();
    }

    @FXML
    void onAcrionThirdButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Ви вибрали рівень 3");
        alert.setHeaderText(null);
        alert.showAndWait();
        DataClass.aglae_quantiy = 40;
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
