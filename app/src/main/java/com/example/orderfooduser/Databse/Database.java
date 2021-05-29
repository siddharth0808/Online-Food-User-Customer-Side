package com.example.orderfooduser.Databse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.orderfooduser.POJO.OrderModel;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="OrderDB.db";
    private static final int DB_VER=1;
    public Database(Context context){
        super(context,DB_NAME,null,DB_VER);
    }

    public List<OrderModel> getCarts(){
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();

        String [] sqlSelect={"ProductId","ProductName","Quantity","Price"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);
        Cursor c= qb.query(db,sqlSelect,null,null,null,null,null);

        final List<OrderModel> result= new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new OrderModel(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price"))
                        ));
            }while (c.moveToNext());
        }
    return  result;
    }

    public void addToCart(OrderModel orderModel){
        SQLiteDatabase db= getReadableDatabase();
        String query= String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price) VALUES('%s','%s','%s','%s');",
                orderModel.getProductId(),
                orderModel.getProductName(),
                orderModel.getQuantity(),
                orderModel.getPrice());
        db.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase db= getReadableDatabase();
        String query= String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}
