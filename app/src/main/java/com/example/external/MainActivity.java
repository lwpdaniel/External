package com.example.external;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.io.IOException;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;

    private String filename="balabag.txt";
    private String filepath="balabag externalio";
    File myFile;
    String myTxt="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtbox = findViewById(R.id.textBox);
        final Button btnRead = findViewById(R.id.buttonRead);
        final Button btnClear = findViewById(R.id.buttonClear);
        final Button btnWrite = findViewById(R.id.buttonWrite);

        btnRead.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fileIn = openFileInput("mytextfile.txt");
                            InputStreamReader InputRead = new InputStreamReader(fileIn);

                            char[] inputBuffer = new char[READ_BLOCK_SIZE];
                            String s = "";
                            int charRead;

                            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                                // char to string conversion
                                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                                s += readstring;
                            }
                            InputRead.close();
                            txtbox.setText(s);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

        );
        btnClear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtbox.setText("");


                    }
                }
        );
        btnWrite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileOutputStream fos = new FileOutputStream(myFile);
                            fos.write(txtbox.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), "File saved succsefully",
                                Toast.LENGTH_LONG).show();

                    }
                }

        );


        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnWrite.setEnabled(false);
        }

    }
    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;

    }

}