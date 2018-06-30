/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package walidatorkartkredytowych;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Kiro
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private TextField number;
    @FXML
    private Button ButtonCheckNumber, ButtonLoadFile;
    @FXML
    private TextArea Allnumber;

    private int[] numberArrey;
    private int scoresumdigit, score;
    String onenumber;

    //  Stage stagej = (Stage) ap.getScene().getWindow();
    @FXML
    private void Checknumber(ActionEvent event) {
    onenumber = number.getText();
    mainnumber();

    }

    private void mainnumber(){
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Wynik walidacji");

        if (checkstring(onenumber )) {
            numberArrey = onenumber.chars().map(x -> x - '0').toArray();
            if (validationNumber(numberArrey)) {
                alert.setHeaderText("Sprawdzany numer jest prawidłowy");
                savenumber();
            } else {
                alert.setHeaderText("Sprawdzany numer nie jest prawidłowy");
            }
        } else {
            alert.setHeaderText("Numer karty jest błędnie zapisany");
        }
        alert.showAndWait();
    }
    
    @FXML
    private void Getfile(ActionEvent event) {
        
        Stage stage = (Stage) ap.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz Plik");
        FileChooser.ExtensionFilter fileExtensions
                = new FileChooser.ExtensionFilter(
                        "Pliki tekstowe", "*txt");
        fileChooser.getExtensionFilters().add(fileExtensions);

        File plik = fileChooser.showOpenDialog(stage);

        if (plik != null) {
	  File file = new File(plik.getAbsoluteFile()+"");
	  Scanner in;
            try {
                in = new Scanner(file);
                
                onenumber  = in.nextLine();
                while(!onenumber.isEmpty()){
                    mainnumber();
                    onenumber  = in.nextLine();
                }
                
            } catch (FileNotFoundException | NoSuchElementException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private boolean validationNumber(int number[]) {
        int[] temp;
        temp = number;
        score = 0;
        for (int i = 0; i < number.length; i++) {
            if (i % 2 == 0) {
                temp[i] *= 2;
                if (temp[i] > 9) {
                    while (temp[i] != 0) {
                        scoresumdigit += temp[i] % 10;
                        temp[i] /= 10;
                    }
                    temp[i] = scoresumdigit;
                    scoresumdigit = 0;
                }
            }
            score += temp[i];
        }
        return score % 10 == 0;
    }
    
    public void savenumber(){
       if(Allnumber.getText().isEmpty()){
                Allnumber.setText(Allnumber.getText()+onenumber);
        }
        else{
              Allnumber.setText(Allnumber.getText()+"\n"+onenumber);
        }
      
    
    }

    public boolean checkstring(String number) {

        if (number.isEmpty()) {
            return false;
        }
        if (number.length() != 16) {
            return false;
        }
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) < '0' || number.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
