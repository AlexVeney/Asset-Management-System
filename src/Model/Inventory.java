/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/**
 *
 * @author avhomefolder
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);

    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);

    }

    public static Part lookupPart(int partId) {

        for (Part part : Inventory.getAllParts()) {
            if (part.getId() == partId) {
                return part;
            }
        }

        return null;

    }

    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> holderList = FXCollections.observableArrayList();

        for (Part part : Inventory.getAllParts()) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                holderList.add(part);
            }
        }

        return holderList;
    }

    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId) {
                return product;
            }
        }

        return null;

    }

    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> holderList = FXCollections.observableArrayList();

        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                holderList.add(product);
            }
        }

        return holderList;

    }

    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);

    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);

    }

    public static void deleteProduct(Product selectedProduct) {

        allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //New
    public static Part createPart(int id, TextField name, TextField inv, TextField price,
            TextField min, TextField max, TextField field) {

//        verifyData(id, name, inv, price,
//         min,max,field);
       //int partID = Integer.parseInt(id.getText());
        int partID = id;
        String partName = name.getText();
        int partInv = Integer.parseInt(inv.getText());
        double partPrice = Double.parseDouble(price.getText());
        int partMin = Integer.parseInt(min.getText());
        int partMax = Integer.parseInt(max.getText());

        Part newPart;
        int partMachineID;
        String partCompanyName;

        if (field.getText().matches("^[0-9]*$")) {
            partMachineID = Integer.parseInt(field.getText());
            newPart = new InHouse(partID, partName, partPrice, partInv, partMin, partMax, partMachineID);
        } else {
            partCompanyName = field.getText();
            newPart = new Outsourced(partID, partName, partPrice, partInv, partMin, partMax, partCompanyName);
        }

        return newPart;

    }

    public static int generateId(){
        Random rand = new Random();
        int genID = 1000 + rand.nextInt(5000);
        
        return genID;
    }
}
