package net.tavda.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.tavda.FuelLeak.TorchDamaging;

public class TorchDamagingController {
    @FXML
    private TextField d;
    @FXML
    private TextField H;
    @FXML
    private TextArea result;

    public void calc(ActionEvent actionEvent) {
        Double var_d;
        Double var_H;
        try {
            var_d = Double.parseDouble(d.getText());
            var_H = Double.parseDouble(H.getText());
        } catch (Exception ex) {
            result.setText(ex.toString());
            return;
        }
        TorchDamaging torchDamaging = new TorchDamaging();
        torchDamaging.setD(var_d);
        torchDamaging.setH(var_H);
        result.setText(torchDamaging.Calculate());
    }
}
