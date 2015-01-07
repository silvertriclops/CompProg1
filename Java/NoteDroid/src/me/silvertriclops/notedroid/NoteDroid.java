package me.silvertriclops.notedroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class NoteDroid extends ActionBarActivity {
	
	private static final int ACTIVITY_VIEW_NOTE_LIST = 0;
	private static final int ACTIVITY_CREATE_NEW_NOTE = 1;
	
	public void openNoteList(View view) {
		Intent i = new Intent(this, NoteList.class);
		startActivityForResult(i, ACTIVITY_VIEW_NOTE_LIST);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_note_droid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_droid, menu);
        return true;
    }
    
    public void openAboutDialog(View view) {
    	Intent i = new Intent(this, AboutActivity.class);
    	startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void openNewNote(View view) {
    	Intent i = new Intent(this, NoteEditor.class);
    	i.putExtra(NotesDbAdapter.KEY_ROWID, new Long(-1));
    	i.putExtra(NoteEditor.NOTEEDITOR_MODE, NoteEditor.NOTEEDITOR_MODE_EDIT);
    	startActivityForResult(i, ACTIVITY_CREATE_NEW_NOTE);
    }
    
    public void openPreferences(View view) {
    	Intent i = new Intent(this, Preferences.class);
    	startActivity(i);
    }
}
