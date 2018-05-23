package com.dalfay.alifbo;
//auto imports
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by CSstudent on 2/13/2018.
 */
//function for saving
public class Utilities {

    public static final String FILE_EXTENTION = ".bin";

    public static boolean saveNote (Context context, Note note) { //context and note will be provided from the activity
        //for saving the file we need the file name
        //the unique filename will be the time of creation of the note
        String fileName = String.valueOf(note.getnDateTime()) + FILE_EXTENTION; //.bin because we are saving it as a binary file

        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false; //inform the user something went wrong,
            //usually nothing goes wrong unless user doesnt have enough storage
        }
        return true;

        }


    //we had this savenote function to save the note, now we need to load the notes to show it on the viewlist
    //It means we just need to do the reverse of what we did above

    public static ArrayList<Note> getAllSavedNotes(Context context){
        ArrayList<Note> notes = new ArrayList<>();

        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();
        //karoche if we see the files finished by .bin, we know they are notefiles

        for (String file : filesDir.list()){
            if (file.endsWith(FILE_EXTENTION)){
                noteFiles.add(file);
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for(int i = 0; i<noteFiles.size(); i++){
            try{
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);

                notes.add((Note)ois.readObject());

                fis.close();
                ois.close();

                //so the loop goes forever until we convert all the files from .bin

            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return notes;
    }

    //so above we had a metod that returns all the notes, now we need another methos that returns one note and by the name of it

    public static Note getNoteByName(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);
        Note note;

        //now we need a safety check to see if the file exists

        if(file.exists()){
            //we continue
            FileInputStream fis;
            ObjectInputStream ois;

        try{
            fis = context.openFileInput(fileName);
            ois = new ObjectInputStream(fis);

            note = (Note) ois.readObject();

            fis.close();
            ois.close();

        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
            return note;
        }
        return null;
    }


    //we are just gonna call it from our noteactivity
    public static void deleteNote(Context context, String fileName) {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);

        if (file.exists()){
            file.delete();
        }
    }
}

