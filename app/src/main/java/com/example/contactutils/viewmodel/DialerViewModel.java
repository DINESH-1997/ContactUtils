package com.example.contactutils.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactutils.model.ContactModel;
import com.example.contactutils.repository.ContactsRepo;

import java.util.List;

public class DialerViewModel extends ViewModel {
    private MutableLiveData<ContactModel> dialer;
    private ContactsRepo contactsRepo;

    public void init(Context context, String number) {
        //this.context = context;
        if(dialer != null)
            return;
        contactsRepo = ContactsRepo.getInstance();
        dialer = contactsRepo.getDialerInfo(context, number);
    }

    public LiveData<ContactModel> getContacts() {
        return dialer;
    }
}
