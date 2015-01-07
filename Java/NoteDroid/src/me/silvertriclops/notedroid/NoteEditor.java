package me.silvertriclops.notedroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEditor extends Activity {
	
	public static final String NOTEEDITOR_MODE = "NOTEEDITOR_MODE";
	public static final String NOTEEDITOR_MODE_EDIT = "NOTEEDITOR_MODE_EDIT";
	public static final String NOTEEDITOR_MODE_SHOW = "NOTEEDITOR_MODE_SHOW";
	
	public static final int MENU_SAVE = Menu.FIRST;
	public static final int MENU_CANCEL = Menu.FIRST + 1;
	
	private EditText mTitleText;
	private EditText mBodyText;
	private String mNoteMode;
	private Long mRowId;
	
	private NotesDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();
		
		setContentView(R.layout.activity_note_editor);
		
		mTitleText = (EditText) findViewById(R.id.NoteEditor_NoteName);
		mBodyText = (EditText) findViewById(R.id.NoteEditor_Body);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mRowId = extras.getLong(NotesDbAdapter.KEY_ROWID);
			mNoteMode = extras.getString(NoteEditor.NOTEEDITOR_MODE);
		}
		
		if (mNoteMode.equals(NOTEEDITOR_MODE_EDIT)) {
			this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		}
		
		populateFields();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		MenuItem item;
		
		item = menu.add(0, MENU_SAVE, 0, R.string.NoteEditor_SaveMenu);
		item.setIcon(R.drawable.cancel32);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case MENU_SAVE:
			saveAndExit();
			return true;
		case MENU_CANCEL:
			exitWithoutSave();
			return true;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	private void saveAndExit() {
		saveData();
		setResult(RESULT_OK);
		finish();
	}
	
	private void exitWithoutSave() {
		setResult(RESULT_OK);
		finish();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(NotesDbAdapter.KEY_ROWID, mRowId);
		outState.putString(NoteEditor.NOTEEDITOR_MODE, mNoteMode);
	}
	
	private void saveData() {
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();
		
		if ((title == null) || (title.length() == 0)) {
			title = this.getString(R.string.NoteEditor_NewNote);
		}
		
		if (mRowId == -1) {
			long id = mDbHelper.createNote(title, body);
			if (id > 0) {
				mRowId = id;
				
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					extras.putLong(NotesDbAdapter.KEY_ROWID, mRowId);
					extras.putString(NoteEditor.NOTEEDITOR_MODE, mNoteMode);
				}
			} else {
				mDbHelper.updateNote(mRowId, title, body);
				Toast.makeText(getApplicationContext(), getString(R.string.savedNote), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void populateFields() {
		if (mRowId != -1) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			startManagingCursor(note);
			mTitleText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
			mBodyText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
		} else {
			mTitleText.setHint(R.string.NoteEditor_NewNote);
		}
	}
}
