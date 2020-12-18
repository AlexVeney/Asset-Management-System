/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.InventoryException;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author Alex Veney
 */
public class Helper {

    Stage stage;
    Parent scene;

    public void changePage(ActionEvent event, String fxmlpath) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        scene = FXMLLoader.load(getClass().getResource(fxmlpath));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void changePageRButton(ActionEvent event, String fxmlpath) throws IOException {

        stage = (Stage) ((RadioButton) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        scene = FXMLLoader.load(getClass().getResource(fxmlpath));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public static boolean isInt(TextField text){

        if (text.getText().matches("^[0-9]*$")) {
            return true;
        } else {
            return false;
        }
    }

//    public static ObservableList filterTable(TextField keyword, ObservableList originalList, ObservableList newList) {
//        
//        //if search field is empty return all parts
//        if (keyword.getText().isEmpty() || originalList.isEmpty()) {
//            return originalList;
//        }
//
//        //if keyword is an int
//        if (isInt(keyword)) {
//            int numericKeyword = Integer.parseInt(keyword.getText());
//
//            if (originalList.get(0) instanceof Part) {
//                if (Inventory.lookupPart(numericKeyword) != null) {
//                    newList.add(Inventory.lookupPart(numericKeyword));
//                }
//            } else if(originalList.get(0) instanceof Product){
//                newList.add(Inventory.lookupProduct(numericKeyword));
//            }
//
//            //if keyword is a string   
//        } else {
//            String stringKeyword = keyword.getText();
//
//            if (originalList.get(0) instanceof Part) {
//                newList = Inventory.lookupPart(stringKeyword);
//            } else if(originalList.get(0) instanceof Product){
//                newList = Inventory.lookupProduct(stringKeyword);
//            }
//        }
//        if (!newList.isEmpty()) {
//            return newList;
//        } else {
//            return originalList;
//        }
//
//    }
    
    public static ObservableList<Part> filterPartsTable(TextField keyword, ObservableList<Part> originalList, ObservableList<Part> newList) {
        
        //if search field is empty return all parts
        if (keyword.getText().isEmpty() || originalList.isEmpty()) {
            return originalList;
        }

        //if keyword is an int
        if (isInt(keyword)) {
            int numericKeyword = Integer.parseInt(keyword.getText());

            if (originalList.get(0) instanceof Part) {
                if (Inventory.lookupPart(numericKeyword) != null) {
                    newList.add(Inventory.lookupPart(numericKeyword));
                }
            }

            //if keyword is a string   
        } else {
            String stringKeyword = keyword.getText();

            if (originalList.get(0) instanceof Part) {
                newList = Inventory.lookupPart(stringKeyword);
            } 
        }
        if (!newList.isEmpty()) {
            return newList;
        } else {
            return originalList;
        }

    }

    
    public static ObservableList<Product> filterProductsTable(TextField keyword, ObservableList<Product> originalList, ObservableList<Product> newList) {
        
        //if search field is empty return all parts
        if (keyword.getText().isEmpty() || originalList.isEmpty()) {
            return originalList;
        }

        //if keyword is an int
        if (isInt(keyword)) {
            int numericKeyword = Integer.parseInt(keyword.getText());

             if(originalList.get(0) instanceof Product){
                newList.add(Inventory.lookupProduct(numericKeyword));
            }

            //if keyword is a string   
        } else {
            String stringKeyword = keyword.getText();

             if(originalList.get(0) instanceof Product){
                newList = Inventory.lookupProduct(stringKeyword);
            }
        }
        if (!newList.isEmpty()) {
            return newList;
        } else {
            return originalList;
        }

    }
}
