package com.example.contactutils.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.contactutils.R;
import com.example.contactutils.adapter.ContactAdapter;
import com.example.contactutils.binder.ContactsRecyclerBinder;
import com.example.contactutils.databinding.ActivityMainBinding;
import com.example.contactutils.iface.IClickListener;
import com.example.contactutils.model.ContactModel;
import com.example.contactutils.viewmodel.ContactViewModel;

import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CONTACT_REQUEST_CODE = 100;
    private ContactViewModel contactViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        checkOrAskPermission();
    }

    private void initialize(){
        activityMainBinding.contactRecycler.setLayoutManager(new LinearLayoutManager(this));
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.init(this);
        contactViewModel.getContacts().observe(this, contactModels -> {
            activityMainBinding.contactRecycler.setAdapter(new ContactAdapter(contactModels, position -> {
                System.out.println("selected : " + contactModels.get(position).getName());
                contactViewModel.UpdateDataContact(this, contactModels.get(position));
            }));
        });
    }

    private void checkOrAskPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE}, CONTACT_REQUEST_CODE);
        }else{
            initialize();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTACT_REQUEST_CODE && grantResults.length > 0){
            Log.i("TAG", "permission granted");
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                initialize();
            else
                checkOrAskPermission();
        }else{
            checkOrAskPermission();
        }
    }
}