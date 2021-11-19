package com.example.contactutils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactutils.databinding.ContactRecyclerBinding;
import com.example.contactutils.iface.IClickListener;
import com.example.contactutils.model.ContactModel;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> {

    private List<ContactModel> contacts;
    private IClickListener iClickListener;

    public ContactAdapter(List<ContactModel> contacts, IClickListener iClickListener) {
        this.contacts = contacts;
        this.iClickListener = iClickListener;
    }

    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactRecyclerBinding v = ContactRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position) {
       holder.contactRecyclerBinding.setContacts(contacts.get(position));
       holder.contactRecyclerBinding.executePendingBindings();
       holder.contactRecyclerBinding.checkbox.setOnClickListener(view -> holder.contactRecyclerBinding.parent.performClick());
       holder.contactRecyclerBinding.parent.setOnClickListener(view -> {
           if(iClickListener != null)
               iClickListener.selectedContact(position);
       });
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    static class ContactVH extends RecyclerView.ViewHolder{

        ContactRecyclerBinding contactRecyclerBinding;

        public ContactVH(@NonNull ContactRecyclerBinding contactRecyclerBinding) {
            super(contactRecyclerBinding.getRoot());
            this.contactRecyclerBinding = contactRecyclerBinding;
        }
    }

}
