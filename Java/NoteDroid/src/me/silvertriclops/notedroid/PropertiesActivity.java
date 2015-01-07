package me.silvertriclops.notedroid;

import java.text.DateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PropertiesActivity extends Activity {
	
	private NotesDbAdapter mDbHelper;
	private Long mRowId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_properties);
		
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mRowId = extras.getLong(NotesDbAdapter.KEY_ROWID);
		}
		
		Note note = mDbHelper.getNoteById(mRowId);
		
		TextView text1 = (TextView) this.findViewById(R.id.PropertiesDialog_Text1);
		text1.setText(this.getString(R.string.PropertiesDialog_Name) + ": " + note.getTitle());
		
		TextView text2 = (TextView) this.findViewById(R.id.PropertiesDialog_Text2);
		text1.setText(this.getString(R.string.PropertiesDialog_Type) + ": " + this.getString(R.string.PropertiesDialog_NoteType));
		
		TextView text3 = (TextView) this.findViewById(R.id.PropertiesDialog_Text3);
		String creationDate = DateFormat.getDateInstance(DateFormat.SHORT).format(note.getCreationDate());
		text1.setText(this.getString(R.string.PropertiesDialog_CreationDate) + ": " + creationDate);
		
		TextView text4 = (TextView) this.findViewById(R.id.PropertiesDialog_Text4);
		String modificationDate = DateFormat.getDateInstance(DateFormat.SHORT).format(note.getModificationDate());
		text1.setText(this.getString(R.string.PropertiesDialog_ModificationDate) + ": " + modificationDate);
	}
	
	public void closePropertiesDialog(View view) {
		finish();
	}
}
