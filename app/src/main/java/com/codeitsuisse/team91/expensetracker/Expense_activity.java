package com.codeitsuisse.team91.expensetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.Calendar;

import com.codeitsuisse.team91.expensetracker.R.*;


public class Expense_activity extends ActionBarActivity implements View.OnClickListener{


    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView, e_amount;
    private Button setdate;
    private String budget_name;
    private int year, month, day;
    private Button Cancel, Save;
    String b_name, dd, mm ,yy, cat, amount;
    Spinner edrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_expense_activity);
        initialise();

        Cancel.setOnClickListener(this);
        Save.setOnClickListener(this);

        Bundle gotbasket = getIntent().getExtras();
        budget_name = gotbasket.getString("key");
        setTitle("Expense (" + budget_name + ")");

    }

    private void initialise() {

        setdate = (Button) findViewById(R.id.setdate);
        calendar = Calendar.getInstance();
        Save = (Button) findViewById(R.id.esave_button);
        Cancel = (Button) findViewById(R.id.ecancel);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setdate.setText(new StringBuilder().append(day).append('/').append(month + 1).append('/').append(year));

        e_amount = (TextView) findViewById(id.editText);
        edrop = (Spinner) findViewById(R.id.edropdown);



    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT)
                .show();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        setdate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_expense_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case id.esave_button:
                boolean diditwork = true;
                String bud_name = budget_name;
                //String expense = e_amount.getText().toString();


                if (TextUtils.isEmpty(e_amount.getText().toString()) || e_amount.getText().toString().equals(" ")) {
                    e_amount.setError("Enter amount ");
                    return;
                } else {
                    e_amount.setError(null);
                }


                try {
                    b_name = budget_name;
                    amount = e_amount.getText().toString();
                    cat = edrop.getSelectedItem().toString();
                    dd = String.valueOf(day);
                    mm = String.valueOf(month);
                    yy = String.valueOf(year);

                    BudgetDrop entry1 = new BudgetDrop(Expense_activity.this);
                    entry1.open();
                    String Amount ;
                    Amount = entry1.getAmount(budget_name);
                    int num = Integer.parseInt(Amount);
                    int num1 = Integer.parseInt(amount);

                    if(num > num1) {

                        num = num - num1;
                        Amount = Integer.toString(num);
                        entry1.updateEntry(budget_name, Amount, "");


                        Data_exp entry = new Data_exp(Expense_activity.this);
                        entry.open();
                        entry.createEntry(b_name, amount, cat, dd, mm, yy);
                        entry.close();
                        entry1.close();

                    }

                    else {
                        /*Dialog dia1 = new Dialog(this);
                        dia1.setTitle("Unsuccessful");
                        TextView tv1 = new TextView(this);
                        tv1.setText("Not Enough Money!!");
                        diditwork = false;*/
                        entry1.close();
                        diditwork = false;
                        Toast.makeText(getBaseContext(), "Exceeded Limit of expense! \n Please Enter amount again ",
                                Toast.LENGTH_LONG).show();
                    }


                } catch (Exception ef1) {
                    diditwork = false;
                    Dialog dia = new Dialog(this);
                    dia.setTitle("Unsuccessful");
                    TextView tv = new TextView(this);
                    tv.setText(ef1.toString());
                    dia.setContentView(tv);
                    dia.show();
                } finally {
                    if (diditwork) {
                        diditwork = false;
                        Dialog dia5 = new Dialog(this);
                        dia5.setTitle("Successful");
                        TextView tv5 = new TextView(this);
                        tv5.setText("Entry  Done");
                        dia5.setContentView(tv5);
                        dia5.show();
                        // Intent i = new Intent("android.intent.action.MAIN");
                        //startActivity(i);
                        // finish();
                    }
                }
                break;
            case id.ecancel:

                finish();
        }
    }
}
