package com.example.contactutils.repository;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.contactutils.model.ContactModel;
import com.example.contactutils.utils.GlobalValues;
import com.example.contactutils.utils.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ContactsRepo {

    private List<ContactModel> contactModelList;
    private static ContactsRepo instance;
    private List<ContactModel> updatedSavedContacts;

    public static ContactsRepo getInstance() {
        if(instance == null) {
            instance = new ContactsRepo();
        }
        return instance;
    }

    public MutableLiveData<List<ContactModel>> getContactsList(Context context) {
        MutableLiveData<List<ContactModel>> data = new MutableLiveData<>();
        data.setValue(getContacts(context));
        return data;
    }

    public List<ContactModel> getContacts(Context context) {
        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
        };

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (cursor != null) {
            try {
                HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();
                int indexOfNormalizedNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
                int indexOfDisplayName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int indexOfDisplayNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                contactModelList = new ArrayList<>();

                while (cursor.moveToNext()) {
                    String normalizedNumber = cursor.getString(indexOfNormalizedNumber);
                    if (normalizedNumbersAlreadyFound.add(normalizedNumber)) {
                        ContactModel contact = new ContactModel();
                        String displayName = cursor.getString(indexOfDisplayName);
                        String displayNumber = cursor.getString(indexOfDisplayNumber);
                        Log.i("TAG", "Name: " + displayName);
                        Log.i("TAG", "Phone Number: " + displayNumber);
                        contact.setMobile_number(displayNumber);
                        contact.setName(displayName);
                        contact.setSelected(isAlreadyPresentInSavedContacts(getSavedContacts(context), displayNumber));
                        contactModelList.add(contact);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return contactModelList;
    }

    public List<ContactModel> saveOrDeleteContactToSharedPref(Context context, ContactModel contact) {
        Gson gson = new Gson();
        List<ContactModel> savedContacts = getSavedContacts(context);
        if(savedContacts == null){
            savedContacts = new ArrayList<>();
            savedContacts.add(contact);
            String json = gson.toJson(savedContacts);
            SharedPreferences.getInstance(context).writeToSharedPreference(GlobalValues.CONTACTS, json);
        }else{
            if(!(removeIfContactAlreadyExist(savedContacts, contact.getMobile_number()))){
                savedContacts.add(contact);
                String json = gson.toJson(savedContacts);
                SharedPreferences.getInstance(context).writeToSharedPreference(GlobalValues.CONTACTS, json);
            }else{
                String json = gson.toJson(updatedSavedContacts);
                SharedPreferences.getInstance(context).writeToSharedPreference(GlobalValues.CONTACTS, json);
            }
        }
        return getContacts(context);
    }

    private List<ContactModel> getSavedContacts(Context context) {
        Gson gson = new Gson();
        return gson.fromJson(SharedPreferences.getInstance(context).readFromSharedPreference(GlobalValues.CONTACTS), new TypeToken<List<ContactModel>>(){}.getType());
    }

    private boolean removeIfContactAlreadyExist(List<ContactModel> contacts, String number) {
        for (int i = 0; i < contacts.size(); i++) {
            if(contacts.get(i).getMobile_number().equalsIgnoreCase(number)){
                contacts.remove(i);
                updatedSavedContacts = contacts;
                return true;
            }
        }

        return false;
    }

    private boolean isAlreadyPresentInSavedContacts(List<ContactModel> contacts, String number) {
        if(contacts != null) {
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).getMobile_number().equalsIgnoreCase(number)) {
                    return true;
                }
            }
        }
        return false;
    }
}
