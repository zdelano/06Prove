package com.example.zachary.a06provemulti_threadedprogramming;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Zachary on 10/22/2016.
 */

public class CreateFile extends AsyncTask<File, Integer, List> {
    @Override
    protected List doInBackground(File... params) {
        // 4.3 Print numbers 1-10 in the file
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(params[0]));
            for (int i = 1; i <= 10; i++) {
                // write to the file
                writer.write(i + "\n");
                publishProgress(i);
                // make it look like it's a lengthy process...
                Thread.sleep(250);
            }
            writer.flush();
            writer.close();
            publishProgress(0);
        } catch (IOException eIO) {
            System.out.println("ERROR: Could not write to file\n");
        } catch (Exception e) {
            System.out.println("ERROR: Could not sleep thread while writing\n");
        }
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
     * Set the progress bar
     * @param p
     */
    public void setProgressBar(ProgressBar p) {
        progressBar = p;
    }

    // reference to progress bar
    private ProgressBar progressBar;
}
