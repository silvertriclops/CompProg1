package me.silvertriclops.notedroid;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NoteList extends ListActivity {
	private static final int ACTIVITY_CREATE_NOTE = 0;
	private static final int ACTIVITY_EDIT_NOTE = 1;
	private static final int ACTIVITY_PROPERTIES_NOTE = 2;
	public static final int INSERT_ID = Menu.FIRST;
	private static final int MENU_DELETE_NOTE_ID = Menu.FIRST + 1;
	private static final int MENU_SHOW_PROPERTIES_ID = Menu.FIRST + 2;
	
	private NotesDbAdapter mDbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_note_list);
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();
		fillData();
		registerForContextMenu(getListView());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		MenuItem item;
		item = menu.add(0, INSERT_ID, 0, R.string.NoteList_MenuCreateNote);
		item.setIcon(R.drawable.newnote32);
		return result;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createNote();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		long selectedId = ((AdapterContextMenuInfo) menuInfo).id;
		
		if (selectedId != -1) {
			menu.setHeaderTitle(mDbHelper.getNoteById(selectedId).getTitle());
		}
		
		menu.add(0, MENU_DELETE_NOTE_ID, 0, R.string.NoteList_MenuDeleteNote);
		menu.add(0, MENU_SHOW_PROPERTIES_ID, 0, R.string.NoteList_MenuShowProperties);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info;
		
		switch(item.getItemId()) {
		case MENU_DELETE_NOTE_ID:
			info = (AdapterContextMenuInfo) item.getMenuInfo();
			doDeleteNote(info.id);
			return true;
		case MENU_SHOW_PROPERTIES_ID:
			info = (AdapterContextMenuInfo) item.getMenuInfo();
			Intent i = new Intent(this, PropertiesActivity.class);
			i.putExtra(NotesDbAdapter.KEY_ROWID, info.id);
			startActivityForResult(i, ACTIVITY_PROPERTIES_NOTE);
		}
		return super.onContextItemSelected(item);
	}
	
	private void createNote() {
		Intent i = new Intent(this, NoteEditor.class);
		i.putExtra(NotesDbAdapter.KEY_ROWID, new Long(-1));
		i.putExtra(NoteEditor.NOTEEDITOR_MODE, NoteEditor.NOTEEDITOR_MODE_EDIT);
		startActivityForResult(i, ACTIVITY_CREATE_NOTE);
	}
	
	private void doDeleteNote(final long rowId) {
		String message = this.getString(R.string.NoteList_DeleteNoteQuestion);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setCancelable(false);
		
		builder.setPositiveButton(this.getString(R.string.NoteList_DeleteConfirm), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mDbHelper.deleteNote(rowId);
				fillData();
			}
		});
		builder.setNegativeButton(this.getString(R.string.NoteList_DeleteCancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
	}
	
	private void fillData() {
		Cursor c = mDbHelper.fetchAllNotes();
		startManagingCursor(c);
		
		String[] from = new String[] { NotesDbAdapter.KEY_TITLE };
		int[] to = new int[] { R.id.text1 };
		
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.notes_row, c, from, to);
		setListAdapter(notes);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(this, NoteEditor.class);
		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
		i.putExtra(NoteEditor.NOTEEDITOR_MODE, NoteEditor.NOTEEDITOR_MODE_SHOW);
		startActivityForResult(i, ACTIVITY_EDIT_NOTE);
	}
}