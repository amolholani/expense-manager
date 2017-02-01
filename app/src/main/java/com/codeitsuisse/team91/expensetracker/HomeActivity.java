package com.codeitsuisse.team91.expensetracker;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity implements OnClickListener {
    Button hcreatebutton, heditbutton, HIncome, HTrans, HExpense;
    TextView SBudget, HcurrentBal, BalAmount;
    Spinner hdrop;
    String gotName = "General";
    ArrayList<String> Budget = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        // hdrop.setOnItemSelectedListener(this);
        hcreatebutton.setOnClickListener(this);
        heditbutton.setOnClickListener(this);
        HIncome.setOnClickListener(this);
        HExpense.setOnClickListener(this);
        HTrans.setOnClickListener(this);

        try {
            BudgetDrop info = new BudgetDrop(this);

            info.open();
            //info.createEntry("General", "00", "00");

            info.getData();
            info.close();

            adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_dropdown_item, info.BudgetCopy);

            adapter.notifyDataSetChanged();
            //hdrop.setAdapter(adapter);

            hdrop.setAdapter(adapter);


            hdrop.setOnItemSelectedListener(new OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3)
                {
                    String Amount = "1000";
                    String Budname = hdrop.getSelectedItem().toString();
                   BudgetDrop amt = new BudgetDrop(HomeActivity.this);
                   amt.open();
                   try{
                    Amount = amt.getAmount(Budname);

                    }
                   catch (Exception ef){
                    Toast.makeText(getBaseContext(), "Entry in db not done!",
                            Toast.LENGTH_LONG).show();
                   }
                    amt.close();
                    BalAmount.setText("\u20b9" + Amount);
                }
                public void onNothingSelected(AdapterView<?> arg0)
                {
                    BalAmount.setText("\u20b9 112121.00");
                    // TODO Auto-generated method stub
                }
            });

/*            String Amount = "100";
            String Budname = hdrop.getSelectedItem().toString();
            System.out.println("\n\n\n\n Budget::::"+ Budname);
            BudgetDrop amt = new BudgetDrop(this);
            amt.open();

                Amount = amt.getAmount(Budname);
                Dialog d = new Dialog(this);
                d.setTitle("Successful");
            }
            catch (Exception e){
                Dialog d = new Dialog(this);
                d.setTitle("UNSuccessful");
            }
            amt.close();
            BalAmount.setText("\u20b9" + Budname);
            */
        } catch (Exception e) {
            Dialog d = new Dialog(this);
            d.setTitle("Unsuccessful");
            TextView tv = new TextView(this);
            tv.setText(e.toString());
            d.setContentView(tv);
            d.show();
        }

        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdapterView.OnItemSelectedListener onSpinner =
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       // Dialog d;
                       // d = new Dialog(this);
                       // d.setTitle("Successful");
                        switch (view.getId()) {
                            case R.id.hdrop:
                                String Amount = "1000";
                                String Budname = hdrop.getSelectedItem().toString();
                               // BudgetDrop amt = new BudgetDrop(this);
                              //  amt.open();
                                try{
                                   // Amount = amt.getAmount(Budname);

                                }
                                catch (Exception e){
                                  //  Dialog d = new Dialog(this);
                                    //d.setTitle("Successful");
                                }
                              //  amt.close();
                                BalAmount.setText("\u20b9" + "10005");
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                    // hdrop.setOnItemSelectedListener();


                };*/
    }





    private void init() {
        hcreatebutton = (Button)findViewById(R.id.hcreatbutton);
        heditbutton = (Button) findViewById(R.id.heditbutton);
        HIncome = (Button) findViewById(R.id.HIncome);
        HTrans = (Button) findViewById(R.id.HTrans);
        HExpense = (Button) findViewById(R.id.HExpense);
        SBudget = (TextView) findViewById(R.id.SBudget);
        HcurrentBal = (TextView) findViewById(R.id.HcurrentBal);
        BalAmount = (TextView) findViewById(R.id.BalAmount);
        hdrop = (Spinner) findViewById(R.id.hdrop);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        try {
            switch (v.getId()) {
                case R.id.hcreatbutton:
                    Intent i1 = new Intent("android.intent.action.CREAT");
                    startActivity(i1);
                    break;
                case R.id.heditbutton:
                    Intent i2 = new Intent("android.intent.action.EDIT");
                    startActivity(i2);
                    break;
                case R.id.HIncome:

                    String budname2 = hdrop.getSelectedItem().toString();
                    Bundle basket2 = new Bundle();
                    basket2.putString("key2", budname2);
                    Intent i3 = new Intent("android.intent.action.INCOME");
                    i3.putExtras(basket2);
                    startActivity(i3);
                    break;
                case R.id.HExpense:

                    String budname = hdrop.getSelectedItem().toString();
                    Bundle basket = new Bundle();
                    basket.putString("key", budname);
                    Intent i4 = new Intent("android.intent.action.EXPENSE");
                    i4.putExtras(basket);
                    startActivity(i4);
                    break;
                case R.id.HTrans:

                    String budname1 = hdrop.getSelectedItem().toString();
                    Bundle basket1 = new Bundle();
                    basket1.putString("key1", budname1);
                    Intent i5 = new Intent("android.intent.action.TRANSACTION");
                    i5.putExtras(basket1);
                    startActivity(i5);
                    break;

            }
        }
        catch(Exception e){
            Dialog dia5 = new Dialog(this);
            dia5.setTitle("Please Wait");
            TextView tv5 = new TextView(this);
            tv5.setText("First create a Budget !!!");
            dia5.setContentView(tv5);
            dia5.show();
        }
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Dialog d = new Dialog(this);
        d.setTitle("Successful");
        switch (view.getId()) {
            case R.id.hdrop:
                String Amount = "1000";
                String Budname = hdrop.getSelectedItem().toString();
                BudgetDrop amt = new BudgetDrop(this);
                amt.open();
                try{
                    Amount = amt.getAmount(Budname);

                }
                catch (Exception e){
                   // Dialog d = new Dialog(this);
                    d.setTitle("Successful");
                }
                amt.close();
                BalAmount.setText("\u20b9" + "10005");
                break;
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        BalAmount.setText("\u20b9 1000.00");
    }*/

}
