/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

package com.example.android.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * increment
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message as a toast
            Toast.makeText(this, getText(R.string.toast_100), Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * decrement
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an error message as a toast
            Toast.makeText(this, getText(R.string.toast_1), Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Getting user name from name field
        EditText insertName = (EditText) findViewById(R.id.drinker_name);
        String gotName = insertName.getText().toString();

        //Checking if users wants to adding whipped cream
        CheckBox isWithWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean withWhipped = isWithWhippedCream.isChecked();

        //Checking if users wants to adding chocolate
        CheckBox isWithChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean withChocolate = isWithChocolate.isChecked();

        //Displaying Order Summary
        int price = calculatePrice(withWhipped, withChocolate);
        String priceMessege = createOrderSummary(gotName, price, withWhipped, withChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email, gotName));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessege);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        displayMessage (priceMessege);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants whipped cream topping
     * @return total prices
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        //Adding $1 if users wants whipped cream topping
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        //Adding $2 if users wants whipped cream topping
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * Showing the summary of the order.
     *
     * @param name inserting users name
     * @param price inserting price after being calculate
     * @param withChocolate inserting condition about chocolate topping (user want it or not)
     * @param withWhipped inserting condition about whipped topping (user want it or not)
     * @return a summary messages
     */
    private String createOrderSummary(String name, int price, boolean withWhipped, boolean withChocolate) {
        String priceMessege = getString(R.string.order_summary_name, name);
        priceMessege += "\n" + getString(R.string.order_summary_whipped, withWhipped);
        priceMessege += "\n" + getString(R.string.order_summary_chocolate, withChocolate);
        priceMessege += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessege += "\n" + getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(price));
        priceMessege += "\n" + getString(R.string.thank_you);
        return priceMessege;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}