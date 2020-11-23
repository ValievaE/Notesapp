package com.example.pin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class NewNoteActivity extends AppCompatActivity {


    //public static final int REQUEST_CODE_TO_NOTES = 2;

    private ImageButton calendarBtn;
    private EditText editTextDeadline;
    private EditText editTextNewTitle;
    private EditText editTextNewNote;
    private CheckBox checkBox;
    private DatePickerDialog datePickerDialog;
    private Note availableNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);


        editTextDeadline = findViewById(R.id.editTextDeadline);
        calendarBtn = findViewById(R.id.imageButtonCalendar);
        editTextNewTitle = findViewById(R.id.editTextNewTitle);
        editTextNewNote = findViewById(R.id.editTextNewNote);
        checkBox = findViewById(R.id.checkBoxDeadline);


        Toolbar myToolbarNewNote = findViewById(R.id.my_toolbarNewNote);
        setSupportActionBar(myToolbarNewNote);
        myToolbarNewNote.inflateMenu(R.menu.savenote_menu);
        getSupportActionBar().setSubtitle(getString(R.string.toolbar_subtitle_newnote));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseDate();
        if (getIntent().getBooleanExtra("isViewUpdate", false)) {
            availableNote = (Note) getIntent().getSerializableExtra("note");
            updateNote();
        }


    }

    private void updateNote() {
        editTextNewTitle.setText(availableNote.getTitle());
        editTextNewNote.setText(availableNote.getNoteText());
        editTextDeadline.setText(availableNote.getDate());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_saveNote:
                saveNewNote();
                return true;
        }
        //return super.onOptionsItemSelected(item);
        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savenote_menu, menu);
        return true;
    }

    private void saveNewNote() {
        final Note note = new Note();
        note.setTitle(editTextNewTitle.getText().toString());
        note.setNoteText(editTextNewNote.getText().toString());
        note.setDate(editTextDeadline.getText().toString());

        if (availableNote != null) {
            note.setId(availableNote.getId());
        }

        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NoteDataBase.getNoteDataBase(getApplicationContext()).noteDao().insertNote(note);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNoteTask().execute();

    }

    private void chooseDate() {

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar todayCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(
                        NewNoteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month = month + 1;
                                editTextDeadline.setText(dayOfMonth + "." + month + "." + year);
                                datePickerDialog.dismiss();
                                checkBox.setChecked(true);
                            }
                        },
                        todayCalendar.get(Calendar.YEAR),
                        todayCalendar.get(Calendar.MONTH),
                        todayCalendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

            }
        });
    }


}



