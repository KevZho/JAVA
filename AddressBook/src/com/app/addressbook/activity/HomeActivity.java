package com.app.addressbook.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.addressbook.contact.Contact;
import com.app.addressbook.contact.ContactArrayAdapter;

import hr.infinum.fer.is45328.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;

/**
 * Activity koji se otvara prilikom pokretanja aplikacije. Pokreće učitavanje JSON datoteke s
 * kontaktima i prikazuje kontakte u listView-u.
 *
 * @author Igor Smolkovič
 *
 */
public class HomeActivity extends Activity {

	/**
	 * Statički array koji pamti kontakte.
	 */
	private static ArrayList<Contact> contacts;

	/**
	 * Datoteka iz koje se čitaju kontakti.
	 */
	private final static String DATABASE_FILE = "people.json";
	
	/**
	 * Request code za activity_add.
	 */
	private final static int REQUEST_CODE_ADD = 0;
	
	/**
	 * Referenca na adapter.
	 */
	ContactArrayAdapter adapter;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		/*
		 * Učitaj kontakte iz JSON datoteke.
		 */
		parseContactsDatabase();
		
		/*
		 * Omogući dodavanje kontakata.
		 */
		Button btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						HomeActivity.this, 
						AddContactActivity.class
				);
				startActivityForResult(intent, REQUEST_CODE_ADD);
			}
		});
		
		/*
		 * Stvori adapter i omogući prikaz kontakata u listView-u.
		 */
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new ContactArrayAdapter(this, getContacts());
		listView.setAdapter(adapter);
		
		/*
		 * Omogući otvaranje profila prilikom klika na pojedini list item.
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Contact contact = contacts.get(position);
				Intent intent = new Intent(
						HomeActivity.this,
						ProfileActivity.class
				);
				
				intent.putExtra("contact", contact);
				
				startActivity(intent);
			}
		});
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**
		 * Ako je dodan kontakt treba osvježiti listView.
		 */
		if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
			adapter.notifyDataSetChanged();
		}
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
	
	/**
	 * Statička metoda koja omogućava pristupanje do kontakata iz bilo kojeg Activitiy-a.
	 *
	 * @return referenca na listu kontakata
	 */
	public static ArrayList<Contact> getContacts() {
		if (contacts == null) {
			contacts = new ArrayList<>();
		}
		return contacts;
	}
	
	/**
	 * Metoda koja učitava kontakte iz JSON datoteke.
	 */
	private void parseContactsDatabase() {
		contacts = new ArrayList<>();
		
		/*
		 * Pročitaj JSON datoteku.
		 */
		String jsonContent = readJSONFile();
		
		try {
			JSONObject jsonObj = new JSONObject(jsonContent);
			JSONArray jsonArray = jsonObj.getJSONArray("people");
			
			/*
			 * Dodaj svaki pročitani kontakt u listu kontakata.
			 */
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject  arrayItem = jsonArray.getJSONObject(i);
				JSONObject person = arrayItem.getJSONObject("person");
				
				String name = person.getString("name");
				String phone = person.getString("phone");
				/*
				 * Formatiraj telefonski broj.
				 */
				if (!phone.isEmpty() && phone.length() >= 9) {
					phone = phone.substring(0, 3) + " " + phone.substring(3, 6) + " " + phone.substring(6);
				}
				String email = person.getString("email");
				String note = person.getString("note");
				String facebook = person.getString("facebook_profile");
				
				contacts.add(new Contact(name, phone, email, note, facebook));
			}
		} catch (JSONException e) {
		}
	}
	
	
	/**
	 * Metoda koja učitava JSON datoteku.
	 *
	 * @return sadržaj JSON datoteke
	 */
	public String readJSONFile() {
		/*
		 * Dohvati assetManager, stvori objekt za spremanje pročitanih
		 * stringova. 
		 */
		AssetManager manager = this.getAssets();
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader reader = null;
		try {
			/*
			 * Koristi bufferirani input stream; datoteka može biti i veća.
			 */
			reader = new BufferedReader(
					new InputStreamReader(manager.open(DATABASE_FILE), "UTF-8")
			);
			
			String current;
			while ((current = reader.readLine())  != null) {
				sBuilder.append(current);
			}
			
			/*
			 * Zatvori otvoreni stream.
			 */
			reader.close();
		} catch (IOException e) {
		}
		
		return sBuilder.toString();
	}
}
