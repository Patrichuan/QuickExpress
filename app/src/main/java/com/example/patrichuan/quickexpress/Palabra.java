package com.example.patrichuan.quickexpress;

import android.content.Context;

/**
 * Created by Patrichuan on 14/01/2015.
 */
public class Palabra {

    String[] mTestArray;

    Palabra (Context context) {
        mTestArray = context.getResources().getStringArray(R.array.Respuestas);
    }

    public int NumAlAzar (int min, int max) {
        return min + (int)(Math.random() * ((max-min)));
    }

    public String getPalabra () {
        int NumAlAzar = NumAlAzar(0, mTestArray.length);
        System.out.println(NumAlAzar);
        return mTestArray[NumAlAzar];
    };
}
