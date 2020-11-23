package com.example.pin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AllNotesActivity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_SETTINGS = 3;
    public static final int REQUEST_CODE_UPDATE_NOTE = 4;
    public static final int REQUEST_CODE_SHOW_NOTES = 5;

    private List<Note> notesList;
    private NotesAdapter notesAdapter;
    private RecyclerView notesRecyclerView;
    private int noteClickedPosition = -1;
    private NotesComparator notesComparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Toolbar myToolbarNotes = findViewById(R.id.my_toolbarNotes);
        setSupportActionBar(myToolbarNotes);
        myToolbarNotes.inflateMenu(R.menu.settings_menu);
        getSupportActionBar().setSubtitle(getString(R.string.toolbar_subtitle_notes));

        FloatingActionButton btnAdd = findViewById(R.id.fabAddNote);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NewNoteActivity.class), REQUEST_CODE_ADD_NOTE);
            }
        });

        notesRecyclerView = findViewById(R.id.rcView);
        notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        notesList = new ArrayList<>();
        notesAdapter = new NotesAdapter(notesList, this);
        notesRecyclerView.setAdapter(notesAdapter);


        getNotes(REQUEST_CODE_SHOW_NOTES, false);


    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
        intent.putExtra("isViewUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);

    }

    public void onLongNoteClicked(final Note note, int position) {
        final int deletePosition = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(AllNotesActivity.this);
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setTitle(R.string.alert_dialog_title);
        builder.setMessage(R.string.alert_dialog_text);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            private DialogInterface dialog;
            private int position;

            @Override
            public void onClick(DialogInterface dialog, int position) {
                this.dialog = dialog;
                this.position = position;
                @SuppressLint("StaticFieldLeak")
                class DeleteNoteTask extends AsyncTask<Void, Void, Void> {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        NoteDataBase.getNoteDataBase(getApplicationContext()).noteDao().deleteNote(notesList.get(deletePosition));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Intent intent = new Intent();
                        intent.putExtra("isNotDeleted", true);
                        setResult(RESULT_OK, intent);
                        //finish();
                    }
                }
                new DeleteNoteTask().execute();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void getNotes(final int requestCode, final boolean isNotDeleted) {
        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDataBase.getNoteDataBase(getApplicationContext()).noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (requestCode == REQUEST_CODE_SHOW_NOTES) {
                    if (isNotDeleted) {
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                    } else {
                        notesList.addAll(notes);
                        notesAdapter.notifyDataSetChanged();
                    }
                } else if (requestCode == REQUEST_CODE_ADD_NOTE) {
                    notesList.add(0, notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                    notesRecyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
                    notesList.remove(noteClickedPosition);
                    notesList.add(noteClickedPosition, notes.get(noteClickedPosition));
                    notesAdapter.notifyItemChanged(noteClickedPosition);
                }
            }
        }
        new GetNoteTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNotDeleted", false));
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        notesComparator = new NotesComparator();
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivityForResult(new Intent(getApplicationContext(), SettingsActivity.class), REQUEST_CODE_SETTINGS);
                return true;
            case R.id.action_sort_up:
                Collections.sort(notesList, notesComparator);
                notesAdapter.notifyDataSetChanged();
                return true;
            case R.id.action_sort_down:
                Collections.sort(notesList, Collections.reverseOrder(notesComparator));
                notesAdapter.notifyDataSetChanged();
                return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }


}
