package com.dalfay.alifbo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CSstudent on 2/15/2018.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(@NonNull Context context, int resource, @NonNull  ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        //if convertview is null, then we have to build it by ourselves
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_note,null);
        }

        //now we are gonna put each note that we get in a position in a listview
        Note note = getItem(position);

        //making sure the user is not trying to save an empty note
        if (note != null){
            TextView tittle = (TextView) convertView.findViewById(R.id.list_note_tittle);
            TextView date = (TextView) convertView.findViewById(R.id.list_note_date);
            TextView content = (TextView) convertView.findViewById(R.id.list_note_content);

            tittle.setText(note.getnTittle());
            date.setText(note.getDateTimeFormatted(getContext())); //the format was defined in note.java, and we need to put context here to be able to convert time from millisecs which is in UTC to the local timezone of the device
            content.setText(note.getnContent());

            //if the note is really long we dont want it take a big space in the list-view
            if (note.getnTittle().length() > 30){
                tittle.setText(note.getnTittle().substring(0,30)); //we only want 50 first spaces there
            }else{ //if thats not the case and the content is already short, we just set it to it's original value
                tittle.setText(note.getnTittle());
            }
            //if the note is really long we dont want it take a big space in the list-view
            if (note.getnContent().length() > 50){
                content.setText(note.getnContent().substring(0,50)); //we only want 50 first spaces there
            }else{ //if thats not the case and the content is already short, we just set it to it's original value
                content.setText(note.getnContent());
            }
        }

        return convertView;
    }
}
