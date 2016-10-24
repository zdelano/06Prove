package com.example.zachary.a06provemulti_threadedprogramming;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Zachary on 10/22/2016.
 */

public class LoadFile extends AsyncTask<File, Integer, List> {
    @Override
    protected List doInBackground(File... params) {
        // 5.1 Load the file numbers.txt and read it line by line
        // try with resources
        try (BufferedReader br = new BufferedReader(new FileReader(params[0]))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                // 5.2 Store each number in the list
                myActivity.myList.add(line);
                // 5.3 Sleep the thread
                publishProgress(i);
                Thread.sleep(250);
                i++;
            }
        } catch (IOException eIO) {
            // Catch any IO exception
            System.out.println("ERROR: Could not read file");
        } catch (Exception e) {
            System.out.println("ERROR: Could not sleep thread while loading\n");
        }
        publishProgress(0);
        return null;
    }

    /**
     * On Progress Update
     * @param numbers
     */
    protected void onProgressUpdate(Integer... numbers) {
        progressBar.setProgress(numbers[0]);
    }

    /**
     * Non default constructor
     * @param a
     */
    LoadFile(MainActivity a, ProgressBar p) {
        setParent(a);
        setProgressBar(p);
    }

    /**
     * Set the parent to main activity
     * @param a
     */
    public void setParent (MainActivity a) {
        myActivity = a;
    }

    /**
     * Set the progress bar
     * @param p
     */
    public void setProgressBar(ProgressBar p) {
        progressBar = p;
    }

    /**
     * Get the progressbar progress
     * @return progressBar.getProgress()
     */
    int getProgressBarProgress() { return progressBar.getProgress(); }

    // member variables
    private MainActivity myActivity;
    private ProgressBar progressBar;
}
