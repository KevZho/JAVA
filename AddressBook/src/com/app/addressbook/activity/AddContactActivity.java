package com.app.addressbook.activity;


import com.app.addressbook.contact.Contact;
import hr.infinum.fer.is45328.R;
import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity koji omogućava dodavanje kontakata. Od korisnika se zatraži unos te se napravi
 * validacija unosa. Prema potrebi se prikazuju poruke o krivom unosu.
 *
 * @author Igor Smolkovič
 *
 */
public class AddContactActivity extends Activity {

	/*
	 * Reference na sve editText-ove.
	 */
	private EditText editName;
	private EditText editPhone;
	private EditText editEmail;
	private EditText editNote;
	private EditText editFacebook;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
	
		editPhone = (EditText) findViewById(R.id.etPhone);
		editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		editName = (EditText) findViewById(R.id.etName);
		editEmail = (EditText) findViewById(R.id.etEmail);
		editNote = (EditText) findViewById(R.id.etNote);
		editFacebook = (EditText) findViewById(R.id.etFacebook);
		
		/*
		 * Ako je pritisnut gumb odustani.
		 */
		Button btnCancel = (Button) findViewById(R.id.btnAdd_Cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		/*
		 * Ako je pritisnut gumb spremi. 
		 */
		Button btnSave = (Button) findViewById(R.id.btnAdd_Save);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (validate() == false) {
					return;
				}
				setResult(RESULT_OK);
				addContact();
				finish();
			}
		});
	}

	/**
	 * Metoda koja provjerava podatke unesene u text edit-e.
	 *
	 * @return <code>true</code> ako su podaci ispravni, inače <code>false</code>.
	 */
	private boolean validate() {
		String email = editEmail.getText().toString();
		if (editName.getText().toString().isEmpty()) {
			Toast.makeText(AddContactActivity.this, "Potrebno unijeti ime i prezime!", Toast.LENGTH_LONG).show();
			return false;
		} else if (editPhone.getText().toString().isEmpty()) {
			Toast.makeText(AddContactActivity.this, "Potrebno unijeti broj telefona!", Toast.LENGTH_LONG).show();
			return false;
		} else if (email.isEmpty()) {
			Toast.makeText(AddContactActivity.this, "Potrebno unijeti e-mail!", Toast.LENGTH_LONG).show();
			return false;
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			Toast.makeText(AddContactActivity.this, "E-mail nije ispravan", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
	
	/**
	 * Metoda koja dodaje novi kontakt u listu kontakata.
	 */
	private void addContact() {
		HomeActivity.getContacts().add(
				new Contact(
						editName.getText().toString(),
						editPhone.getText().toString(),
						editEmail.getText().toString(),
						editNote.getText().toString(),
						editFacebook.getText().toString()
				)
		);
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
