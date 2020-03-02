/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Helper;
import Model.InHouse;
import Model.Inventory;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author avhomefolder
 */
public class ModifyPartInhouseController implements Initializable {


    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField inv;
    @FXML
    private TextField price;
    @FXML
    private TextField machineID;
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
    private void onActionModifyPartInhouse(ActionEvent event) throws IOException {
        //DO NOTHING BECAUSE IT IS ON THE CORRECT SCREEN 
    }

    @FXML
    private void onActionModifyPartOutsourced(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader();
    
            loader.setLocation(getClass().getResource("/View/ModifyPartOutsourced.fxml"));
            loader.load();
            ModifyPartOutsourcedController ADMController = loader.getController();
            int idNum = Integer.parseInt(id.getText());
            Part part = Inventory.lookupPart(idNum);
            ADMController.transferOutsourcedPart(part);
            
            
            Stage stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
            Parent scene1 = loader.getRoot();
            stage.setScene(new Scene(scene1));
            stage.show();
    

    }

    
      @FXML
    private void onActionSavePart(ActionEvent event) throws IOException {
        try{
            if(!Helper.isInt(machineID)){
                
                throw new InventoryException();
                
            }else if ((name.getText().isEmpty() || name.getText().equalsIgnoreCase("")) || inv.getText().isEmpty()
                || price.getText().isEmpty() || min.getText().isEmpty() || max.getText().isEmpty()
                || machineID.getText().isEmpty()) {
                
                throw new NumberFormatException();

            }else if(Integer.parseInt(min.getText()) > Integer.parseInt(max.getText())){
                
                throw new Exception();
                
            }
        
        int partID = Integer.parseInt(id.getText());
        Part origPart = Inventory.lookupPart(partID);
        int index = Inventory.getAllParts().indexOf(origPart);
        InHouse newPart = (InHouse)Inventory.createPart(partID, name, inv, price, min, max, machineID);
        Inventory.updatePart(index, newPart);
        Helper action = new Helper();
        action.changePage(event, "/View/MainScreen.fxml");
        
        
         }catch(NumberFormatException f){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("One or more fields have been left blank. Please enter a valid value!");
            alert.showAndWait();
         }catch(InventoryException h){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Assignment");
            alert.setContentText("Machine id is invalid");
            alert.showAndWait();
         }catch(Exception g){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Assignment");
            alert.setContentText("Min must be less than or equal to Max");
            alert.showAndWait();
         }
        
        
    }
    
    public void transferInHousePart(Part part){
        id.setText(String.valueOf(part.getId()));
        name.setText(part.getName());
        inv.setText(String.valueOf(part.getStock()));
        price.setText(String.valueOf(part.getPrice()));
        
        if(part instanceof InHouse){
          machineID.setText(String.valueOf(((InHouse)(part)).getMachineId()));  
        }else{
          machineID.setText("INSERT Machine ID HERE"); 
        }
        
            
        max.setText(String.valueOf(part.getMax()));
        min.setText(String.valueOf(part.getMin()));
        
        
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
