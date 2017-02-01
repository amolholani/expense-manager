package com.codeitsuisse.team91.expensetracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CreatBudget extends ActionBarActivity implements View.OnClickListener {
    TextView name, description, initBal;
    EditText budgetName, budgetdescription, InitBal;
    Button create, cancel;
    String budName,amount,descript;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_budget);
        init();

        create.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void init(){
        name = (TextView)findViewById(R.id.name);
        description = (TextView)findViewById(R.id.description);
        initBal = (TextView)findViewById(R.id.initBal);
        budgetName = (EditText)findViewById(R.id.budgetName);
        budgetdescription = (EditText)findViewById(R.id.budgetdescription);
        InitBal = (EditText)findViewById(R.id.InitBal);
        create = (Button)findViewById(R.id.create);
        cancel = (Button)findViewById(R.id.cancel);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creat_budget, menu);
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
        switch(v.getId()) {


            case R.id.create:
                String sbudgetname = budgetName.getText().toString();
                String sInitbal = InitBal.getText().toString();
                if(TextUtils.isEmpty(sbudgetname) || budgetName.getText().toString().equals(" ")) {
                    budgetName.setError("Enter Budget Name");
                    return;
                }
                else {
                    budgetName.setError(null);
                    //return;

                }
                if(TextUtils.isEmpty(sInitbal)) {
                    InitBal.setError("Enter Initial Balance");
                    return;
                }
                else {
                    InitBal.setError(null);
                    //return;

                }
                boolean didItWork = true;
                try {
                    budName = budgetName.getText().toString();
                    amount = InitBal.getText().toString();
                    descript = budgetdescription.getText().toString();
                   BudgetDrop entry = new BudgetDrop(CreatBudget.this);
                   entry.open();
                   entry.createEntry(budName, amount, descript);
                   entry.close();
                }catch (Exception e){
                    didItWork = false;
                    Dialog d = new Dialog(this);
                    d.setTitle("Unsuccessful");
                    TextView tv = new TextView(this);
                    tv.setText("Budget Not Created");
                    d.setContentView(tv);
                    d.show();
                }finally {
                    if(didItWork){
                        Dialog d = new Dialog(this);
                        d.setTitle("Successful");
                        TextView tv = new TextView(this);
                        tv.setText("Budget Created");
                        Toast.makeText(getBaseContext(), "Successfully",
                                Toast.LENGTH_LONG).show();
                        d.setContentView(tv);
                        d.show();
                        finish();
                        //Intent i = new Intent("android.intent.action.HOME");
                        //startActivity(i);



                    }
                }

                break;

            case R.id.cancel:
                finish();
        }
    }
}
