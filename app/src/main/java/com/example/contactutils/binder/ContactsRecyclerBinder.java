package com.example.contactutils.binder;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactutils.adapter.ContactAdapter;
import com.example.contactutils.model.ContactModel;

import java.util.List;

public class ContactsRecyclerBinder {

    ContactAdapter contactAdapter;

    public ContactsRecyclerBinder(ContactAdapter contactAdapter) {
        this.contactAdapter = contactAdapter;
    }

    public ContactAdapter getContactAdapter() {
        return contactAdapter;
    }

    public void setContactAdapter(ContactAdapter contactAdapter) {
        this.contactAdapter = contactAdapter;
    }

    @BindingAdapter("android:loadData")
    public static void setRecyclerData(RecyclerView recyclerView, ContactAdapter contactAdapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(contactAdapter);
    }
}
