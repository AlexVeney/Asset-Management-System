/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Helper;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author avhomefolder
 */
public class ModifyProductController implements Initializable {
    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private Product currProduct;
    
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField inv;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField searchText;
    @FXML
    private TableView<Part> allPartsInInventoryTableView;
    @FXML
    private TableColumn<Part, Integer> partIDCol1;
    @FXML
    private TableColumn<Part, String> partNameCol1;
    @FXML
    private TableColumn<Part, Integer> inventoryLevelCol1;
    @FXML
    private TableColumn<Part, Double> priceCol1;
    @FXML
    private TableView<Part> partsAssociatedWithProductTablewView;
    @FXML
    private TableColumn<Part, Integer> partIDCol2;
    @FXML
    private TableColumn<Part, String> partNameCol2;
    @FXML
    private TableColumn<Part, Integer> inventoryLevelCol2;
    @FXML
    private TableColumn<Part, Double> priceCol2;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        allPartsInInventoryTableView.setItems(Inventory.getAllParts());
        partIDCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
       
       
    }    

    @FXML
    private void onActionSearchPartTableView(ActionEvent event) {
        if(!filteredParts.isEmpty()){
           filteredParts.clear();
       }
       
         allPartsInInventoryTableView.setItems(Helper.filterPartsTable(searchText, 
                 Inventory.getAllParts(), filteredParts));

    }

    @FXML
    private void onActionAddSelectedPartToProduct(ActionEvent event) {
        
        Part part = allPartsInInventoryTableView.getSelectionModel().getSelectedItem();
        
       
        currProduct.addAssociatedPart(part);
        
        setAssociatedTable(currProduct);
    }

    @FXML
    private void onActionDeleteSelectedPartFromProduct(ActionEvent event) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setContentText("Do you want to delete item?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
        Part part = partsAssociatedWithProductTablewView.getSelectionModel().getSelectedItem();

        currProduct.deleteAssociatedPart(part);
        
        setAssociatedTable(currProduct);
        } else {
            // stay on current page
        }
        
        
        
    }
    
     @FXML
    private void onActionSaveModifiedProduct(ActionEvent event) throws IOException {
        
 
        
        
        try{
            
            if(Integer.parseInt(min.getText()) > Integer.parseInt(max.getText())){
                throw new InventoryException();
            }
            if ((name.getText().isEmpty() || name.getText().equalsIgnoreCase("")) || inv.getText().isEmpty()
                || price.getText().isEmpty() || min.getText().isEmpty() || max.getText().isEmpty() ||
                    (Integer.parseInt(min.getText()) > Integer.parseInt(max.getText()))){
                 
                
                throw new NumberFormatException();

            }
            if(currProduct.getAllAssociatedParts().size() < 1){
                throw new Exception();
            }
            
        
            currProduct.setId(Integer.parseInt(id.getText()));
            currProduct.setName(name.getText());
            currProduct.setPrice(Double.parseDouble(price.getText()));
            currProduct.setStock(Integer.parseInt(inv.getText()));
            currProduct.setMin(Integer.parseInt(min.getText()));
            currProduct.setMax(Integer.parseInt(max.getText()));
           
            Helper action = new Helper();
            action.changePage(event, "/View/MainScreen.fxml");
        
        
         }catch(NumberFormatException f){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Invalid entry for one or more fields. Please enter valid values!");
            alert.showAndWait();
         }catch (InventoryException h){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Assignment");
            alert.setContentText("Min must be less than or equal to Max");
            alert.showAndWait();
         
         }catch(Exception g){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Error");
            alert.setContentText("Product must have atleast 1 associated part!");
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
    
    public void transferProduct(Product product){
        id.setText(String.valueOf(product.getId()));
        name.setText(product.getName());
        inv.setText(String.valueOf(product.getStock()));
        price.setText(String.valueOf(product.getPrice()));
        max.setText(String.valueOf(product.getMax()));
        min.setText(String.valueOf(product.getMin()));
        
        setAssociatedTable(product);
        currProduct = Inventory.lookupProduct(Integer.parseInt(id.getText()));
        
    }
     public void setAssociatedTable(Product product){
        partsAssociatedWithProductTablewView.setItems(product.getAllAssociatedParts());
        partIDCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
}
