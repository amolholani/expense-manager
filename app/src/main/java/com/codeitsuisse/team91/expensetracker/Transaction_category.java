package com.codeitsuisse.team91.expensetracker;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Transaction_category extends ActionBarActivity implements OnClickListener {

    private Spinner types;
    private Button Cancel, Show;
    private String budget_name;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_category);

        init();

        Cancel.setOnClickListener(this);
        Show.setOnClickListener(this);

        Bundle gotbasket = getIntent().getExtras();
        budget_name = gotbasket.getString("key1");
        setTitle("Transactions (" + budget_name + ")");
    }

    private void init() {
        types = (Spinner)findViewById(R.id.s_types);
        Cancel = (Button)findViewById(R.id.t_cancel);
        Show = (Button)findViewById(R.id.show1);
        display = (TextView)findViewById(R.id.display);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction_category, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.t_cancel:
                finish();
                break;

            case R.id.show1:
                try {
                    String show12 = "", show13 = "";
                    String selected = types.getSelectedItem().toString();

                    if (selected.equals("Income Transaction")) {

                        try {
                            Data_in trans2 = new Data_in(Transaction_category.this);

                            trans2.open();
                            show13 = trans2.getData1(budget_name);
                            trans2.close();

                        } catch (Exception et) {
                            Dialog dia23 = new Dialog(this);
                            dia23.setTitle("Unsuccessful");
                            TextView tvx = new TextView(this);
                            tvx.setText(et.toString());
                            dia23.setContentView(tvx);
                            dia23.show();
                            //et.printStackTrace();
                        }

                        if (show13.equals("")) {
                            display.setText("Noting to show");

                        } else {
                            display.setText(show13);
                        }

                    }
                    if (selected.equals("Expense Transaction")) {

                        try {
                            Data_exp trans1 = new Data_exp(Transaction_category.this);

                            trans1.open();
                            show12 = trans1.getData1(budget_name);
                            trans1.close();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (!show12.equals("")) {
                            display.setText(show12);
                        } else {
                            display.setText("Noting to show");
                        }
                    }

                }
                catch(Exception k){
                    Dialog dia3 = new Dialog(this);
                    dia3.setTitle("Unsuccessful");
                    TextView tvx = new TextView(this);
                    tvx.setText(k.toString());
                    dia3.setContentView(tvx);
                    dia3.show();
                }
        }
    }
}
