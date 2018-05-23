package com.dalfay.alifbo;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Daler on 2/12/2018.
 */

//implements Serializable for saving purposes
public class Note implements Serializable{

    //the three variables that I need for the note
    private long nDateTime;
    private String nTittle;
    private String nContent;

    //constructor
    public Note(long nDateTime, String nTittle, String nContent) {
        this.nDateTime = nDateTime;
        this.nTittle = nTittle;
        this.nContent = nContent;
    }

    //setters for the sake of completeness

    public void setnDateTime(long nDateTime) {
        this.nDateTime = nDateTime;
    }

    public void setnTittle(String nTittle) {
        this.nTittle = nTittle;
    }

    public void setnContent(String nContent) {
        this.nContent = nContent;
    }

    //getters since all the variables were private

    public long getnDateTime() {
        return nDateTime;
    }

    public String getnTittle() {
        return nTittle;
    }

    public String getnContent() {
        return nContent;
    }

    //getDateTimeAsString

    public String getDateTimeFormatted(Context context){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss" //date format since we dont want 13 digits date
                , context.getResources().getConfiguration().locale);

        sdf.setTimeZone(TimeZone.getDefault()); //get the default timezode from the android device
        return sdf.format(new Date(nDateTime)); //imported the date package
        //done
    }

}
