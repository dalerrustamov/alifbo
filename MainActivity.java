package com.dalfay.alifbo;
//auto imports
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListViewNotes = findViewById(R.id.main_listview_notes);
    }

    //for adding the menu menu_main at the header
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //starting the next activity (redirecting to the NoteActivity on click on the add menu)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_main_new_note:
                //start note activity in NewNote mode
                Intent newNoteActivity = new Intent(this, NoteActivity.class);
                startActivity(newNoteActivity);
                break;
        }
        return true;
    }

    //listview
    @Override
    protected void onResume() {
        super.onResume();
        mListViewNotes.setAdapter(null);

        //we are just calling the array that we made in class Utilities
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        //so since if something goes wrong in the exception in the Utilities class, we return null, we check here if it returned null to see if smt went wrong

        if(notes == null || notes.size() == 0){
            Toast.makeText(this, "You have no saved notes", Toast.LENGTH_SHORT).show();
            return;
        }else{ //real work starts here
            NoteAdapter na = new NoteAdapter(this, R.layout.item_note, notes);
            mListViewNotes.setAdapter(na);

            //so now we have to make so if we click on the note, it opens it on the other page

            mListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //first lets get the filename (the long version with billiseconds on it)

                        String fileName = ((Note)mListViewNotes.getItemAtPosition(position)).getnDateTime() + Utilities.FILE_EXTENTION;
                        //so now we have the name of the file, so we need a method that loads the life, cast it to the note and gives it back to us
                        //redirecting to Utilities

                        //came back, it is better to lead the note in the activity that is gonna be opened after the click, not here

                        Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                        viewNoteIntent.putExtra("NOTE_FILE", fileName);
                        startActivity(viewNoteIntent);
                }

            });
        }
    }
}

