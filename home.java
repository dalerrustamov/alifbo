package com.dalfay.alifbo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    //wiring up the buttons

    public Button NotesButton;

    public void init(){
        NotesButton = (Button)findViewById(R.id.button4);
        NotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toy = new Intent(home.this, MainActivity.class);

                startActivity(toy);

            }
        });
    }

    public Button ToDoListButton;

    public void init2(){
        ToDoListButton = (Button)findViewById(R.id.button5);
        ToDoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toy2 = new Intent(home.this, ToDoListActivity.class);

                startActivity(toy2);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //just call the function that's job is to redirect the user to the new activity on click
        init();
        init2();
    }


}
