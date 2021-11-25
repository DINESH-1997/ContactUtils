package com.example.contactutils.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;

import com.example.contactutils.R;
import com.example.contactutils.databinding.ActivityNotificationBinding;
import com.example.contactutils.utils.GlobalValues;
import com.example.contactutils.viewmodel.DialerViewModel;

public class NotificationActivity extends AppCompatActivity {

    private DialerViewModel dialerViewModel;
    private ActivityNotificationBinding activityNotificationBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNotificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initialize(getIntent().getStringExtra(GlobalValues.DIALER_NUMBER));
    }

    private void initialize(String number) {
        if(number != null) {
            dialerViewModel = ViewModelProviders.of(this).get(DialerViewModel.class);
            dialerViewModel.init(this, number);
            dialerViewModel.getContacts().observe(this, contactModel -> {
                activityNotificationBinding.setDialer(contactModel);
            });
            activityNotificationBinding.close.setOnClickListener(view -> finish());
        }else{
            finish();
        }
    }
}