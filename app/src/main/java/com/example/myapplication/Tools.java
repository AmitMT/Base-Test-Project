package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Tools {
    private final Context context;

    public Tools(Context context) {
        this.context = context;
    }

    public int dpToPx(int dps) {
        // Get the screen's density scale
        final float scale =
                context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dps * scale + 0.5f);
    }

    public File createFile(String fileName) {
        File externalDirectory = new File(context.getExternalFilesDir(null).toString());
        return new File(externalDirectory, fileName);
    }

    public void writeToFile(File file, String text) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write((text + System.lineSeparator()).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(File file, int readLength) {
        byte[] fileContent;
        if (readLength == -1) {
            // -1 means no specified length, return all the content in the file
            fileContent = new byte[(int) file.length()];
        } else
            fileContent = new byte[readLength];
        try {
            FileInputStream fis = new FileInputStream(file);
            int numOfBytesRead = fis.read(fileContent);
            fis.close();
            return new String(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // In the case of an error, return null
    }

    public ImageView createImageView(int height, int width, RelativeLayout.LayoutParams params) {
        ImageView image = new ImageView(context);
        if (params == null)
            image.setLayoutParams(new RelativeLayout.LayoutParams(dpToPx(height), dpToPx(width)));
        else
            image.setLayoutParams(params);
        return image;
    }

    public int[] getUniqueValuesRandomArray(int arraySize) {
        int[] arr = new int[arraySize];
        Random rnd = new Random();
        int randomNumber;
        for (int i = 0; i < arr.length; i++) {
            randomNumber = rnd.nextInt(arr.length);
            for (int j = 0; j < i; j++) {
                if (arr[j] == randomNumber) {
                    randomNumber = rnd.nextInt(arr.length);
                    j = -1;
                }
            }
            arr[i] = randomNumber;
        }
        return arr;
    }

    public String generateCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return formatter.format(new Date());
    }

    public SharedPreferences defineSharedPreferences(String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public String getDataFromSharedPreferences(SharedPreferences sp, String key, String defaultValue) {
        return sp.getString(key, defaultValue); // To switch between output types, change all instances of 'String' to desired type
    }

    @SuppressLint("ApplySharedPref")
    public void insertDataToSharedPreferences(SharedPreferences sp, String key, String data) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, data);
        editor.commit();
        // To switch between input types, change all instances of 'String' to desired type (excluding 'key')
    }

    public void createToast(String text) {
        Toast toast = Toast.makeText(this.context,
                text,
                Toast.LENGTH_LONG);
        toast.show();
    }

    public void createAlertDialog(String title, String message) {
        new AlertDialog.Builder(this.context)
                .setTitle(title)
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // Continue with operation
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public boolean doesExistArr(int[] arr, int ele) {
        for (int x : arr) if (ele == x) return true;
        return false;
    }

    public void printToLog(String tag, String data) {
        Log.d(tag, data);
    }
}
