package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private TextField txtPassi;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnCorrelate;

    @FXML
    private Button btnCammino;

    @FXML
    private ComboBox<String> boxPorzioni;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCammino(ActionEvent event) {

    }

    @FXML
    void doCorrelate(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String cs = txtCalorie.getText();
    	Integer c;
    	try {
    		c = Integer.parseInt(cs);
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Errore: il valore non è un valido numero intero\n");
    		return ;
    	}
    	
    	String msg = model.creaGrafo(c);
    	
    	
    	txtResult.appendText(msg);
    	
    	this.boxPorzioni.getItems().clear();
    	this.boxPorzioni.getItems().addAll(model.getVertici());
    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
