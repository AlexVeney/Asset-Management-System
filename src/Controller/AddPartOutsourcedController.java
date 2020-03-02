/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Helper;
import Model.Inventory;
import Model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author avhomefolder
 */
public class AddPartOutsourcedController implements Initializable {

   
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField inv;
    @FXML
    private TextField price;
    @FXML
    private TextField companyName;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionAddPartInhouse(ActionEvent event) throws IOException {
        Helper action = new Helper();
        action.changePageRButton(event, "/View/AddPartInhouse.fxml");
    }

    @FXML
    private void onActionAddPartOutsourced(ActionEvent event) throws IOException {
        Helper action = new Helper();
        action.changePageRButton(event, "/View/AddPartOutsourced.fxml");
    }

    @FXML
    private void onActionSavePart(ActionEvent event) throws IOException {
       
        try{
              if ((name.getText().isEmpty() || name.getText().equalsIgnoreCase("")) || inv.getText().isEmpty()
                || price.getText().isEmpty() || min.getText().isEmpty() || max.getText().isEmpty()
                || companyName.getText().isEmpty()) {
                
                throw new NumberFormatException();

            }else if(Integer.parseInt(min.getText()) > Integer.parseInt(max.getText())){
                
                throw new Exception();
            }
            
            Outsourced newPart = (Outsourced)Inventory.createPart(Inventory.generateId(), name, inv, price, min, max, companyName);

            Inventory.addPart(newPart);
            Helper action = new Helper();
            action.changePage(event, "/View/MainScreen.fxml");
         }catch(NumberFormatException f){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("One or more fields are blank. Provide valid input!");
            alert.showAndWait();
         }catch(Exception g){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Assignment");
            alert.setContentText("Min must be less than or equal to Max");
            alert.showAndWait();
         }
    }
    
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setContentText("Do you want to cancel and go back to previous page?");

        ButtonType buttonBack = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonBack, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonBack){
            Helper action = new Helper();
                action.changePage(event, "/View/MainScreen.fxml");
        } else {
            // stay on current page
        }
    }
    
}
