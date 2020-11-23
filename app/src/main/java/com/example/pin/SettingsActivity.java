package com.example.pin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import static com.example.pin.MainActivity.PIN;
import static com.example.pin.MainActivity.sharedPrefPin;


public class SettingsActivity extends AppCompatActivity implements Keystory {

    private TextInputEditText editTextPin;
    private Button savePinButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar myToolbarSettings = findViewById(R.id.my_toolbarSettings);
        setSupportActionBar(myToolbarSettings);
        myToolbarSettings.inflateMenu(R.menu.back_from_settings_menu);
        getSupportActionBar().setSubtitle(getString(R.string.toolbar_subtitle_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPin = findViewById(R.id.editTextNewPin);

        savePinButton = findViewById(R.id.buttonSavePin);

        savePinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePin();
                Toast.makeText(SettingsActivity.this, "New Pin is saved!", Toast.LENGTH_LONG).show();
            }
        });

        getDateFromSharedPref();
    }

    @Override
    public void savePin() {
        SharedPreferences.Editor myEditor = sharedPrefPin.edit().clear();
        String newPin = editTextPin.getText().toString();

        myEditor.putString(PIN, newPin);
        myEditor.apply();
    }

    private void getDateFromSharedPref() {
        String updatingPin = sharedPrefPin.getString(PIN, "");
        editTextPin.setText(updatingPin);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_back_from_settings:
                Intent intent = new Intent(SettingsActivity.this, NewNoteActivity.class);
                startActivity(intent);
                return true;

        }
        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_from_settings_menu, menu);
        return true;
    }

}
