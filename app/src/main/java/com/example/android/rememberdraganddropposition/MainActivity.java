package com.example.android.rememberdraganddropposition;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.interfaceitems.OnCustomerListChangedListener;
import com.interfaceitems.OnStartDragListener;
import com.interfaceitems.SampleData;
import com.interfaceitems.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCustomerListChangedListener , OnStartDragListener{


    public RecyclerView recyclerView;
    public CustomerListAdapter adapter;
    public ItemTouchHelper itemTouchHelper;
    public List<Customer> customerList;



    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;


    public static final String LIST_OF_SORTED_DATA_ID = "json_list";
    public static final String PREFERANCE_FILE = "preferance_file";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = this.getApplicationContext().getSharedPreferences(PREFERANCE_FILE , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setUpRecycleView();
    }




    public void setUpRecycleView(){
        recyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        customerList = getCustomerList();

        adapter = new CustomerListAdapter(customerList , this , this , this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }




    public List<Customer> getCustomerList(){
        List<Customer> customerList = SampleData.addSampleCustomers();
        List<Customer> sortedCustomers = new ArrayList<>();

        String jsonListOfSortedCustomerId = sharedPreferences.getString(LIST_OF_SORTED_DATA_ID , "");

        /// check for null

        if (!jsonListOfSortedCustomerId.isEmpty()){
            Gson gson = new Gson();
            List<Long> listOfSortedCustomerID = gson.fromJson(jsonListOfSortedCustomerId , new TypeToken<List<Long>>(){}.getType());


            // build sorted list

            if (listOfSortedCustomerID!=null && listOfSortedCustomerID.size()>0){
                for (Long id :listOfSortedCustomerID){
                    for (Customer customer : customerList){
                        if (customer.getId().equals(id)){
                            sortedCustomers.add(customer);
                            customerList.remove(customer);
                            break;
                        }
                    }
                }
            }


            if (customerList.size()>0){
                sortedCustomers.addAll(customerList);
            }

            return sortedCustomers;
        }
        else {
            return customerList;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onNoteListChanged(List<Customer> customers) {

        List<Long> listOfSortedCustomerId = new ArrayList<>();
        for (Customer customer : customers){
            listOfSortedCustomerId.add(customer.getId());
        }

        Gson gson = new Gson();
        String jsonLinkOfSortedCustomerId = gson.toJson(listOfSortedCustomerId);
        editor.putString(LIST_OF_SORTED_DATA_ID , jsonLinkOfSortedCustomerId).commit();
        editor.commit();

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
