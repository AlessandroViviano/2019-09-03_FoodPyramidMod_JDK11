package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneAdiacente;
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
    	txtResult.clear();
    	
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("Errore: devi scegliere una porzione\n");
    		return ;
    	}
    	
    	Integer N;
    	try {
    		N = Integer.parseInt(txtPassi.getText());
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Errore: il valore "+txtPassi.getText()+" non è un numero intero\n");
    		return ;
    	}
    	
    	model.trovaCammino(N, porzione);
    	if(model.getCamminoMax() == null) {
    		txtResult.appendText("Non ho trovato un cammino di lunghezza N\n");
    	}else {
    		txtResult.appendText("Trovato un cammino "+model.getPesoMax()+"\n");
    		for(String vertice: model.getCamminoMax()) {
    			txtResult.appendText(vertice+"\n");
    		}
    	}
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	String porzione = boxPorzioni.getValue();
    	if(porzione == null) {
    		txtResult.appendText("Errore: devi scegliere una porzione\n");
    		return ;
    	}
    	List<PorzioneAdiacente> vicini = model.getAdiacenti(porzione);
    	
    	for(PorzioneAdiacente pa: vicini) {
    		txtResult.appendText(pa.getPorzione()+" "+pa.getPeso()+"\n");
    	}
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
