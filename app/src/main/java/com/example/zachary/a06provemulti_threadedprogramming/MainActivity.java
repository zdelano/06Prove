package com.example.zachary.a06provemulti_threadedprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Zachary Delano
 * @version 10.22.2016
 */
public class MainActivity extends AppCompatActivity {
    // member variables
    public  ArrayList<String> myList = new ArrayList<>();
    private ArrayAdapter adapter;
    private ProgressBar progressBar;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create a variable for the progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        // set it to 10 segments
        progressBar.setMax(10);
    }

    /**
     * Write to a file numbers 1-10, waiting 250 ms between each number
     * @param v
     */
    public void writeFile(View v) {
        // 4.1 - 4.2 Create a new file in the internal storage area
        String filename = "numbers.txt";
        File file = new File(getFilesDir(), filename);
        // 4.3 Print numbers 1-10 in the file
        CreateFile createFile = new CreateFile();
        createFile.setProgressBar(progressBar);
        createFile.execute(file);
    }

    /**
     * Load a file and read it line by line
     * @param v
     */
     public void loadFile (View v){
         String filename = "numbers.txt";
         File file = new File(getFilesDir(), filename);
         ListView myListView = (ListView) findViewById(R.id.list_view);
         // 5.1 Load the file numbers.txt and read it line by line
         // try with resources
         final LoadFile loadFile = new LoadFile(this, progressBar);
         loadFile.execute(file);
         adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myList);
         myListView.setAdapter(adapter);
         // have a new thread keep track of the progress bar
         new Thread (new Runnable() {
             int progressStatus = 0;
             public void run() {
                 while (progressStatus != 10) {
                     progressStatus = loadFile.getProgressBarProgress();
                     // have a handler take care of the list view
                     handler.post(new Runnable() {
                         public void run() {
                             adapter.notifyDataSetChanged();
                         }
                     });
                 }
             }
         }).start();
     }

    /**
     * Clear the adapter
     * @param v
     */
    public void clearAdapter(View v) {
        adapter.clear();
    }
}