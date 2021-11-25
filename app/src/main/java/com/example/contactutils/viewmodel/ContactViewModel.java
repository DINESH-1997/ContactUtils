package com.example.contactutils.viewmodel;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.contactutils.model.ContactModel;
import com.example.contactutils.repository.ContactsRepo;

import java.util.List;

public class ContactViewModel extends ViewModel {

    private MutableLiveData<List<ContactModel>> mContacts;
    private ContactsRepo contactsRepo;
    //private Context context;

    public void init(Context context) {
        //this.context = context;
        if(mContacts != null)
            return;
        contactsRepo = ContactsRepo.getInstance();
        mContacts = contactsRepo.getContactsList(context);
    }

    public LiveData<List<ContactModel>> getContacts() {
        return mContacts;
    }

    public void UpdateDataContact(Context context, ContactModel contact) {
        if(contactsRepo != null)
            mContacts.postValue(contactsRepo.saveOrDeleteContactToSharedPref(context, contact));
    }
}
