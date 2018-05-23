package com.dalfay.alifbo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    //just connecting the input boxes in the activity_note_xml layout by their IDs
    private EditText mEditTitle;
    private EditText mEditContent;
    //we need anothet variable to hold the name of the note (filename) to lead the note for editing
    private  String mNoteFileName;
    private Note mLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //just connecting the input boxes in the activity_note_xml layout by their IDs
        mEditTitle = (EditText) findViewById(R.id.note_edit_tittle);
        mEditContent = (EditText) findViewById(R.id.note_edit_content);

        //we need to assign this variable to the existing note's filename to be able to load it for editing
        mNoteFileName = getIntent().getStringExtra("NOTE_FILE");
        //lets check if we really got it or not
        if(mNoteFileName != null && !mNoteFileName.isEmpty()){
            mLoadedNote = Utilities.getNoteByName(this, mNoteFileName);

            //lets check if we really could load the note or not
            if (mLoadedNote != null){
                mEditTitle.setText(mLoadedNote.getnTittle());
                mEditContent.setText(mLoadedNote.getnContent());
            }
        }
    }

    //for adding the save button and delete button from menu_note_new at the header
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_note_save: //for saving
                saveNote(); //function is below
                break;

            case R.id.action_note_delete: //for deleting
                deleteNote(); //function is below
                break;
        }
        return true; //tell the api that we handled tha click
    }

    //is beeing called by onOptionsItemSelected(MenuItem item)


    //SAVE
    private void saveNote(){
        Note note;

        //we dont want to let the users save an empty note
        if(mEditTitle.getText().toString().trim().isEmpty() || mEditContent.getText().toString().trim().isEmpty()){//we just dont save the note
            Toast.makeText(this, "Tittle and Content fields can't be blank",Toast.LENGTH_SHORT).show();
            return;
        }


        //so there is a contradiction here, since we are using the same activity to load the already saved note
        //since we are using the time as our filename, we dont want the same note be saved twice

        if(mLoadedNote == null){ //it means the user wants to create a new note
            note = new Note (System.currentTimeMillis(), mEditTitle.getText().toString(), mEditContent.getText().toString());
        }else{ //it means the user wants to edit the existing note


            //scott: delete the old note and save the updated version with the new date


            Utilities.deleteNote(getApplicationContext(), mLoadedNote.getnDateTime()+Utilities.FILE_EXTENTION);
            Toast.makeText(getApplicationContext(), mEditTitle.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
            finish(); //exits the activity and returns to mainactivity




            //so instead of coming up with a new file name, I am just getting the filename from it's old name which is the time it was saved
            note = new Note (System.currentTimeMillis(), mEditTitle.getText().toString(), mEditContent.getText().toString());
        }

        if (Utilities.saveNote(this, note)){
            Toast.makeText (this, "Note saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Something went wrong while saving your note. Make sure you have enough space on your device", Toast.LENGTH_SHORT).show();
        }
        finish(); //quit the activity and return to main
    }


    //DELETE
    private void deleteNote() {
        //again we have 2 cases, is it a new note or existing note that you wanna delete?
        if(mLoadedNote == null){ //it means it is a brand new note, and if the user doenst wanna save it, we just quit the activity
            finish();
        }else{ //it means it was an existing note, and we need to remove it from the storage
            //now we need to warn the user if he is sure to delete the file or not
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("You are about to delete '" + mEditTitle.getText().toString()+ "'")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(), mLoadedNote.getnDateTime()+Utilities.FILE_EXTENTION);
                            Toast.makeText(getApplicationContext(), mEditTitle.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                            finish(); //exits the activity and returns to mainactivity
                        }
                    })
                    //if hhe hits no, it means the user just needs to stay here
                    .setNegativeButton("No", null)
                    .setCancelable(false); //if he clicks somewhere else on the screen, nothing happens
            dialog.show();

        }
    }
}
