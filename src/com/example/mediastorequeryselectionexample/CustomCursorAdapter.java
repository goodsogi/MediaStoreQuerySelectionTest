package com.example.mediastorequeryselectionexample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

	private LayoutInflater inflater;

	public CustomCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		inflater = LayoutInflater.from(context);

	}

	@Override
	public void bindView(View view, Context arg1, Cursor c) {
		// here we are setting our data
		// that means, take the data from the cursor and put it in view
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		//String date = getDate(c.getString(c
		//		.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
		
		String date =c.getString(c
				.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
		textView.setText(date);
	}

	private String getDate(String dateTaken) {
		// create a DateFormatter object for displaying date in specified
		// format.
		String dateFormat = "yyyy/MM/dd";
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(dateTaken));

		return formatter.format(calendar.getTime());
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup parent) {
		// when the view will be created for first time,
		// we neet to tell the adpaters, how each item will look
		View view = inflater.inflate(android.R.layout.simple_list_item_1,
				parent,false);
		return view;
	}

}
