/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import Model.Helper;
import Model.InHouse;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author avhomefolder
 */
public class MainScreenController implements Initializable {
   
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
 
  
    @FXML
    private TextField searchPartsText;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partsInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField searchProductText;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableView<Product> productsTableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        productsTableView.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
    }  
   
    
    @FXML
    private void onActionSearchPartsTable(ActionEvent event) {
       
       if(!filteredParts.isEmpty()){
           filteredParts.clear();
       }
       partsTableView.setItems(Helper.filterPartsTable(searchPartsText, 
               Inventory.getAllParts(), filteredParts));

       
    }
    
    @FXML
    private void onActionSearchProductsTable(ActionEvent event) {
        if(!filteredProducts.isEmpty()){
           filteredProducts.clear();
       }
      productsTableView.setItems(Helper.filterProductsTable(searchProductText, 
                Inventory.getAllProducts(), filteredProducts));


    }

    @FXML
    private void onActionAddPartScreen(ActionEvent event) throws IOException {
        Helper action = new Helper();
        action.changePage(event, "/View/AddPartInhouse.fxml");
    }

    @FXML
    private void onActionModifyPartScreen(ActionEvent event) throws IOException {
        try{
        FXMLLoader loader = new FXMLLoader();
    
        if(partsTableView.getSelectionModel().getSelectedItem() instanceof InHouse){
            loader.setLocation(getClass().getResource("/View/ModifyPartInhouse.fxml"));
            loader.load();
            ModifyPartInhouseController ADMController = loader.getController();
            ADMController.transferInHousePart(partsTableView.getSelectionModel().getSelectedItem());
         }else{
            loader.setLocation(getClass().getResource("/View/ModifyPartOutsourced.fxml"));
            loader.load();
            ModifyPartOutsourcedController ADMController = loader.getController();
            ADMController.transferOutsourcedPart(partsTableView.getSelectionModel().getSelectedItem());
        }
        Stage stage;
        
        stage =(Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene1 = loader.getRoot();
        stage.setScene(new Scene(scene1));
        stage.show();
        
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
       
    }
    
    @FXML
    private void onActionModifyProductScreen(ActionEvent event) throws IOException {
         try{
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
            loader.load();
            ModifyProductController ADMController = loader.getController();
            ADMController.transferProduct(productsTableView.getSelectionModel().getSelectedItem());

            Stage stage;

            stage =(Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene1 = loader.getRoot();
            stage.setScene(new Scene(scene1));
            stage.show();
        
        }catch(NullPointerException c){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
         
         
    }

    @FXML
    private void onActionDeleteSelectedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Do you want to delete item?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            Part part = partsTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
       
        onActionSearchPartsTable(event);
        } else {
            // stay on current page
        }
        
        
        
    }
    
    @FXML
    private void onActionAddProductScreen(ActionEvent event) throws IOException {
        Helper action = new Helper();
        action.changePage(event, "/View/AddProduct.fxml");
    }

    

    @FXML
    private void onActionDeleteSelectedProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Do you want to delete item?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(product);
       
        onActionSearchPartsTable(event);
        } else {
            // stay on current page
        }
        
       
    }

    @FXML
    private void onActionExitApplication(ActionEvent event) {
        
        System.exit(0);
        
    }
      
    //Helper Method
    public void search(String value){
        
        
        for(Part part : Inventory.getAllParts()){
            if(part.getName().toLowerCase().contains(value.toLowerCase())){
                filteredParts.add(part);
            }
        }
     
    }

    
}
