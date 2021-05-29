package com.example.orderfooduser.POJO;

public class Model {
    private  String itemImgUri;
    private  String itemName;
    private  String itemPrice;
    private  String itemDescription;

    public Model() {
    }

    public Model(String itemImgUri, String itemName, String itemPrice, String itemDescription) {
        this.itemImgUri = itemImgUri;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
    }

    public String getItemImgUri() {
        return itemImgUri;
    }

    public void setItemImgUri(String itemImgUri) {
        this.itemImgUri = itemImgUri;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
