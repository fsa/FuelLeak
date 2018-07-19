package net.tavda.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import net.tavda.FuelLeak.AirFuelBlast;

public class AirFuelBlastController {
    @FXML
    private TextField R;
    @FXML
    private TextField M_r;
    @FXML
    private ComboBox<String> substance;
    @FXML
    private ComboBox<String> st_zagr;
    @FXML
    private TextArea result;

    public void calc(ActionEvent actionEvent) {
        Double var_R;
        Double var_M_r;
        String var_substance;
        String var_st_zagr;
        try {
            var_R = Double.parseDouble(R.getText());
            var_M_r = Double.parseDouble(M_r.getText());
            var_substance = substance.getValue();
            var_st_zagr = st_zagr.getValue();
            AirFuelBlast airFuelBlast = new AirFuelBlast();
            airFuelBlast.setR(var_R);
            airFuelBlast.setM_g(var_M_r);
            airFuelBlast.setSubstance(var_substance);
            airFuelBlast.setSt_zagr(var_st_zagr);
            result.setText(airFuelBlast.Calculate());
        } catch (Exception ex) {
            result.setText(ex.toString());
        }
    }
}
