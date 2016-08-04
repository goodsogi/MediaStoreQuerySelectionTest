package com.example.mediastorequeryselectionexample;

import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {
	private static final String LOG_TAG = "MediaStorequerySelectionExample";
	String[] months = { "Total", "This Month", "Last Month" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		makeSpinner();
	}

	// add clicklistener to spinner
	private void makeSpinner() {
		// create spinner adapter and attach it to spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, months);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				getDataFromMediaStore(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected void getDataFromMediaStore(int position) {
		Calendar calendar = Calendar.getInstance();
		calendar.getTime();
		Log.d(LOG_TAG, "current time: " + calendar.getTimeInMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		
		String[] projection = { MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DATE_TAKEN };

		// get millisecond of this month

		// if spinner position is 0, get all data. set selection null
		String selection = null;

		// get images taken this month
		if (position == 1) {
			selection = MediaStore.Images.Media.DATE_TAKEN + " >="
					+ getThisMonthInMillis(year, month);
			// get images taken last month
		} else if (position == 2) {
			
			if (month != 0) {
				selection = MediaStore.Images.Media.DATE_TAKEN + " >="
						+ getLastMonthInMillis(year, month) + " AND "
						+ MediaStore.Images.Media.DATE_TAKEN + " < "
						+ getThisMonthInMillis(year, month);
			} else {
				selection = MediaStore.Images.Media.DATE_TAKEN + " >="
				+ getThisMonthInMillis(year, month);
			}

		}
		Cursor imageCursor = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
				selection, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
		//
		// SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		// android.R.layout.simple_list_item_1, imageCursor,
		// new String[] { MediaStore.Images.Media.DATE_TAKEN }, new int[] {
		// android.R.id.text1 },
		// 0);

		// use CustomCursorAdapter to display formatted date
		CustomCursorAdapter adapter = new CustomCursorAdapter(this,
				imageCursor, false);

		// display data on listview
		ListView listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);

	}

	private long getLastMonthInMillis(int year, int month) {

		

		Log.d(LOG_TAG, "current month: " + month + " current year: " + year);

		Calendar searchCalendar = Calendar.getInstance();
		searchCalendar.set(Calendar.YEAR, year);
		// minus 1 to month for last month
		int searchMonth = (month != 0) ? month - 1 : month;
		searchCalendar.set(Calendar.MONTH, searchMonth);
		searchCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Log.d(LOG_TAG,
				"searchCalendar time: " + searchCalendar.getTimeInMillis());
		return searchCalendar.getTimeInMillis();

	}

	private long getThisMonthInMillis(int year, int month) {
		

		Log.d(LOG_TAG, "current month: " + month + " current year: " + year);

		Calendar searchCalendar = Calendar.getInstance();
		searchCalendar.set(Calendar.YEAR, year);

		searchCalendar.set(Calendar.MONTH, month);
		searchCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Log.d(LOG_TAG,
				"searchCalendar time: " + searchCalendar.getTimeInMillis());
		return searchCalendar.getTimeInMillis();
	}

	private long getMillSecondMonth(int position) {

		Calendar calendar = Calendar.getInstance();
		calendar.getTime();
		Log.d(LOG_TAG, "current time: " + calendar.getTimeInMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);

		Log.d(LOG_TAG, "current month: " + month + " current year: " + year);

		Calendar searchCalendar = Calendar.getInstance();
		searchCalendar.set(Calendar.YEAR, year);

		int searchMonth = (position == 1) ? month : (position != 0) ? month - 1
				: month;

		searchCalendar.set(Calendar.MONTH, searchMonth);
		searchCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Log.d(LOG_TAG,
				"searchCalendar time: " + searchCalendar.getTimeInMillis());
		return searchCalendar.getTimeInMillis();
	}

}
