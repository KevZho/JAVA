package com.app.addressbook.activity;

import com.app.addressbook.contact.Contact;

import hr.infinum.fer.is45328.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity koji omogućava detaljan uvid profila osobe, zvanje osobe i pregled
 * facebook profila.
 *
 * @author Igor Smolkovič
 *
 */
public class ProfileActivity extends Activity {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		/*
		 * Postavi podatke dobivenog korisnika.
		 */
		final Contact contact = (Contact) getIntent().getSerializableExtra("contact");
		setText(contact);

		/*
		 * Omogući poziv osobe. Broj je sigurno dobro zadan
		 * te ne treba provjera.
		 */
		Button btnCall = (Button) findViewById(R.id.btnProfile_Call);
		btnCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phone = contact.getPhone();
				
				Intent intent = new Intent(
						Intent.ACTION_CALL, 
						Uri.parse("tel:" + phone)
					);
				startActivity(intent);
			}
		});
		
		/*
		 * Omogući otvaranje facebook profila.
		 */
		Button btnProfile = (Button) findViewById(R.id.btnProfile_ViewProfile);
		btnProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String fb = contact.getFacebook();
				if (!fb.matches("htt(p|ps)://www.facebook.com/.+")) {
					Toast.makeText(
							ProfileActivity.this,
							"Facebook profil nije zadan ili nije ispravan!",
							Toast.LENGTH_LONG
					).show();
					return;
				}
				
				Intent intent = new Intent(
						Intent.ACTION_VIEW, 
						Uri.parse(fb)
					);
				startActivity(intent);
			}
		});
	}

	/**
	 * Metoda koja mijenja text na textView-ovima.
	 */
	private void setText(Contact contact) {
		TextView tvName = (TextView) findViewById(R.id.profile_tvName_value);
		TextView tvPhone = (TextView) findViewById(R.id.profile_tvPhone_value);
		TextView tvEmail = (TextView) findViewById(R.id.profile_tvEmail_value);
		TextView tvNote = (TextView) findViewById(R.id.profile_tvNote_value);
		TextView tvFacebook = (TextView) findViewById(R.id.profile_tvFacebook_value);

		tvName.setText(contact.getName());
		tvPhone.setText(contact.getPhone());
		tvEmail.setText(contact.getEmail());
		tvNote.setText(contact.getNote());
		tvFacebook.setText(contact.getFacebook());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
