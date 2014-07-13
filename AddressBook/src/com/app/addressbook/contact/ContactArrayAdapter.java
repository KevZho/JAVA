package com.app.addressbook.contact;

import hr.infinum.fer.is45328.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Razred koji nasljeđuje {@link ArrayAdapter} i omogućava vraćanje viewa
 * na traženoj poziciji.
 * 
 * @author Igor Smolkovič
 *
 */
public class ContactArrayAdapter extends ArrayAdapter<Contact> {

	/**
	 * Konstruktor.
	 *
	 * @param context context
	 * @param contacts lista kontakata
	 */
	public ContactArrayAdapter(Context context, ArrayList<Contact> contacts) {
		super(context, 0, contacts);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			
			convertView = inflater.inflate(
					R.layout.list_item, 
					parent, 
					false
				);
		}
		
		/*
		 * Dohvati kontakt.
		 */
		Contact contact = getItem(position);
		TextView nameTextView  = (TextView) convertView.findViewById(R.id.tvName);
		
		/*
		 * Dohvati ime i prezime.
		 */
		nameTextView.setText(contact.getName());
		
		TextView numberEmailTextView = (TextView) convertView.findViewById(R.id.tvNumberEmail);
		
		/*
		 * Dohvati telefonski broj i email.
		 */
		String numberEmail = contact.getPhone() + ", " + contact.getEmail();
		numberEmailTextView.setText(numberEmail);
		
		return convertView;
	}
}
