package com.interfaceitems;

import com.example.android.rememberdraganddropposition.Customer;

import java.util.List;

/**
 * Created by android on 5/28/2017.
 */

public interface OnCustomerListChangedListener {


    void onNoteListChanged(List<Customer> customers);
}
