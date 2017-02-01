package com.codeitsuisse.team91.expensetracker;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditBudget extends ActionBarActivity implements View.OnClickListener {

    Button canclebutton,deletebutton,savebutton;
    TextView name,description,initBal;
    EditText budgetName,budgetdescription,InitBal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);
        init();
        canclebutton.setOnClickListener(this);
        deletebutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
    }
    public void init() {
        canclebutton = (Button)findViewById(R.id.canclebutton);
        savebutton = (Button) findViewById(R.id.savebutton);
        deletebutton= (Button) findViewById(R.id.deletebutton);
        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        initBal = (TextView) findViewById(R.id.initBal);
        budgetName = (EditText) findViewById(R.id.budgetName);
        budgetdescription = (EditText) findViewById(R.id.budgetdescription);
        InitBal = (EditText) findViewById(R.id.InitBal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_budget, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
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
        switch (v.getId()){

            case R.id.deletebutton:
                boolean f1 = true;
                String dbudName = budgetName.getText().toString();
                String duInitBal = InitBal.getText().toString();
                BudgetDrop delete = new BudgetDrop(this);
                if(dbudName.equals("")){
                    f1 = false;
                    Toast.makeText(getBaseContext(), "Pllllllllllllllllllllease Enter the budget name!! ",
                            Toast.LENGTH_LONG).show();

                }
                try {
                    delete.open();
                    delete.deleteEntry(dbudName);
                    delete.close();
                } catch (Exception e) {
                    f1 = false;
                    Dialog d = new Dialog(this);
                    d.setTitle("Unsuccessful");
                    TextView tv = new TextView(this);
                    tv.setText(e.toString());
                    d.setContentView(tv);
                    d.show();
                }
                finally {
                    if (f1) {
                        Dialog d = new Dialog(this);
                        d.setTitle("successful");
                        TextView tv = new TextView(this);
                        tv.setText("Deleted");
                        d.setContentView(tv);
                        d.show();
                    }
                }
                break;

            case R.id.canclebutton:
                finish();
                break;

            case R.id.savebutton:
                boolean f = true;
                String budName = budgetName.getText().toString();
                String uInitBal = InitBal.getText().toString();
                String uDescript = budgetdescription.getText().toString();
                BudgetDrop update = new BudgetDrop(this);
                if(budName.equals("")){
                    f = false;
                    Toast.makeText(getBaseContext(), "Please Enter the budget name!! ",
                            Toast.LENGTH_LONG).show();

                }
                try {
                    update.open();
                    update.updateEntry(budName, uInitBal, uDescript);
                    update.close();
                }catch (Exception e){
                    f = false;
                    Dialog d = new Dialog(this);
                    d.setTitle("Unsuccessful");
                    TextView tv = new TextView(this);
                    tv.setText(e.toString());
                    d.setContentView(tv);
                    d.show();

                }
                finally {
                    if(f) {
                        Dialog d = new Dialog(this);
                        d.setTitle("successful");
                        TextView tv = new TextView(this);
                        tv.setText("Saved");
                        d.setContentView(tv);
                        d.show();
                    }
                }
                break;


        }
    }
}
