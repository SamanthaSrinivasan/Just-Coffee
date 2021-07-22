package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    int CoffeeCount=1;       // Global Variable
    /**
     * This method is called when the + button is clicked.
     * if-return - stops at 100 (to delete values greater than 100)
     * toast- to pop up error msg
     */
    public void increment(View view) {
        if(CoffeeCount==100)
        {
            Toast.makeText(this,"You cannot order more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        CoffeeCount=CoffeeCount+1;
        displayQuantity(CoffeeCount);
    }

    /**
     * This method is called when the - button is clicked.
     * if-return - stops at 1 (to delete negative values: -1,-2)
     */
    public void decrement(View view) {
        if(CoffeeCount==1)
        {
            Toast.makeText(this,"You cannot order less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        CoffeeCount=CoffeeCount-1;
        displayQuantity(CoffeeCount);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        /Find username/
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox nutellaCheckBox = (CheckBox) findViewById(R.id.nutella_checkbox);
        boolean hasnutella = nutellaCheckBox.isChecked();

        CheckBox cocoaPowderCheckBox = (CheckBox) findViewById(R.id.cocoaPowder_checkbox);
        boolean hascocoaPowder = cocoaPowderCheckBox.isChecked();

        int price=calcPrice(hasWhippedCream,hasnutella,hascocoaPowder);
        String priceMessage= createOrderSummary(name,price,hasWhippedCream,hasnutella,hascocoaPowder);
        displayMessage(priceMessage);

        /* Intent to redirect to mail app*/
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));  //only email apps will handle
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee ordered by " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    /**
     * Calculates price of order
     * @return - total price
     */
    private int calcPrice(boolean addWhippedCream,boolean addnutella,boolean addcocoaPowder) {
        int baseprice = 10;

        if(addWhippedCream)
        {
            baseprice = baseprice + 5;
        }

        if(addnutella)
        {
            baseprice = baseprice +6;
        }

        if(addcocoaPowder)
        {
            baseprice = baseprice +7;
        }

        int price= CoffeeCount * baseprice;
        return price;
    }

    /**
     * Creates summary of the order
     *
     * @param name of the user
     * @param price of the order
     * @param addWhippedCream - weather user wants Whipped cream or not
     * @param addnutella - weather user wants nutella or not
     * @param addcocoaPowder - weather user wants Cocoa powder or not
     * @return text summary
     */
    private String createOrderSummary(String name,int price, boolean addWhippedCream, boolean addnutella, boolean addcocoaPowder)
    {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd Whipped Cream? " +  addWhippedCream;
        priceMessage = priceMessage + "\nAdd Nutella? " +  addnutella;
        priceMessage = priceMessage + "\nAdd Cocoa Powder? " +  addcocoaPowder;
        priceMessage = priceMessage + "\nQuantity: " + CoffeeCount;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberofcoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberofcoffees);
    }

    private void displayMessage(String message)
    {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
