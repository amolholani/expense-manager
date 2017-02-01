package com.codeitsuisse.team91.expensetracker;

import java.sql.SQLException;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class Income extends ActionBarActivity implements OnClickListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private EditText i_amount;
    private Button setdate, Save, Cancel;
    private int year, month, day;
    private String budget_name;
    Spinner idropdown;
    String b_name, dd, mm, yy, cat, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        init();

        Cancel.setOnClickListener(this);
        Save.setOnClickListener(this);

        Bundle gotbasket = getIntent().getExtras();
        budget_name = gotbasket.getString("key2");
        setTitle("Income (" + budget_name + ")");

    }

    private void init() {

        setdate = (Button) findViewById(R.id.isetdate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setdate.setText(new StringBuilder().append(day).append('/').append(month + 1).append('/').append(year));
        Save = (Button) findViewById(R.id.isave_button);
        Cancel = (Button) findViewById(R.id.icancel_button);

        i_amount = (EditText) findViewById(R.id.iamount_text);
        idropdown = (Spinner) findViewById(R.id.idropdown);


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT)
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
        getMenuInflater().inflate(R.menu.menu_income, menu);
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
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {




            case R.id.isave_button:

                boolean diditwork = true;
                String bud_name = budget_name;
                // String bud_name = "hii";
                //String expense = e_amount.getText().toString();


                if (TextUtils.isEmpty(i_amount.getText().toString()) || i_amount.getText().toString().equals(" ")) {
                    i_amount.setError("Enter amount ");
                    return;
                } else {
                    i_amount.setError(null);
                }

                try {
                    b_name = budget_name;
                    amount = i_amount.getText().toString();
                    cat = idropdown.getSelectedItem().toString();
                    dd = String.valueOf(day);
                    mm = String.valueOf(month);
                    yy = String.valueOf(year);

                    // adding the income
                    BudgetDrop entry11 = new BudgetDrop(Income.this);
                    entry11.open();
                    String Amount ;
                    Amount = entry11.getAmount(budget_name);
                    int num = Integer.parseInt(Amount);
                    int num1 = Integer.parseInt(amount);
                    Toast.makeText(getBaseContext(), "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ",
                            Toast.LENGTH_LONG).show();
                    num = num1 + num;
                    Amount = Integer.toString(num);
                    entry11.updateEntry(budget_name, Amount, "");


                    Data_in entry2 = new Data_in(Income.this);
                    entry2.open();
                    entry2.createEntry(b_name, amount, cat, dd, mm, yy);

                    entry2.close();
                    entry11.close();

                } catch (SQLException e) {

                    diditwork = false;
                   /* Dialog dia = new Dialog(this);
                    dia.setTitle("Unsuccessful");
                    TextView tv = new TextView(this);
                    tv.setText("Entry Not Done");
                    dia.setContentView(tv);
                    dia.show();*/
                    Toast.makeText(getBaseContext(), "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ",
                            Toast.LENGTH_LONG).show();

                }finally {
                    if (diditwork) {
                        Toast.makeText(getBaseContext(), "Income amount has been added!",
                                Toast.LENGTH_LONG).show();
                        diditwork = false;
                        Dialog dia11 = new Dialog(this);
                        dia11.setTitle("Successful");
                        TextView tv11 = new TextView(this);
                        tv11.setText("Entry  Done");
                        dia11.setContentView(tv11);
                        dia11.show();
                        // Intent i = new Intent("android.intent.action.MAIN");
                        //startActivity(i);
                        finishActivity(1);
                    }
                }
                break;


            case R.id.icancel_button:
                finish();
                break;
        }
    }
}
