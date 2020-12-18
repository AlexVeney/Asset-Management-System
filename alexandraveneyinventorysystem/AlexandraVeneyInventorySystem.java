/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexveneyinventorysystem;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alex Veney
 */
public class AlexVeneyInventorySystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //TODO: generate id numner
        
        InHouse part1 = new InHouse(Inventory.generateId(), "Mouse", 9.99,
                5, 1, 6, 0001);
        InHouse part2 = new InHouse(Inventory.generateId(), "Keyboard", 12.99,
                1, 1, 2, 0002);
        InHouse part3 = new InHouse(Inventory.generateId(), "Mouse Pad", 5.00,
                2, 0, 3, 0003);
        InHouse part4 = new InHouse(Inventory.generateId(), "Pens", 1.50,
                2, 1, 10, 0004);
        
        Outsourced part5 = new Outsourced(Inventory.generateId(), "Desk", 999.99,
                1, 1, 2, "Staples");
        Outsourced part6 = new Outsourced(Inventory.generateId(), "Chair", 195.00,
                1, 1, 2, "Office Depot");
        Outsourced part7 = new Outsourced(Inventory.generateId(), "Pen Holder", 5.00,
                2, 0, 3, "Walmart");
        Outsourced part8 = new Outsourced(Inventory.generateId(), "Cup", 1.50,
                2, 1, 2, "Target");
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        
        Product product1 = new Product(Inventory.generateId(), "Keyboard and Mouse Set", 25.00, 1, 0, 4);
        Product product2 = new Product(Inventory.generateId(), "Office Set", 1000, 1, 0, 1);
        Product product3 = new Product(Inventory.generateId(), "Pen and Holder Set ", 10.50, 1, 1, 2);
        
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        
        product2.addAssociatedPart(part5);
        product2.addAssociatedPart(part6);
        
        product3.addAssociatedPart(part4);
        product3.addAssociatedPart(part7);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
        launch(args);
    }
    
}
