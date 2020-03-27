package com.example.holiday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import com.example.holiday.TripPlanner;

public class MediumPickerDialog extends AppCompatDialogFragment  {
    private ImageView bus, aero, train;
    private MediumDialog mediumDialog;
    int id = 1;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_fragment,null);
        //BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        //bitmap = drawable.getBitmap();

        bus = view.findViewById(R.id.bus);
        aero = view.findViewById(R.id.plane);
        train=view.findViewById(R.id.train);

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 1;
                bus.setBackgroundColor(getResources().getColor(R.color.blue));
                aero.setBackgroundColor(getResources().getColor(R.color.white));
                train.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 3;
                bus.setBackgroundColor(getResources().getColor(R.color.white));
                aero.setBackgroundColor(getResources().getColor(R.color.white));
                train.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });

        aero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 2;
                aero.setBackgroundColor(getResources().getColor(R.color.blue));
                bus.setBackgroundColor(getResources().getColor(R.color.white));
                train.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        builder.setView(view).
                setTitle("Choose Medium")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{mediumDialog.applyPic(id);}catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mediumDialog=(MediumDialog)context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
    public interface MediumDialog{
        void applyPic(int id) throws FileNotFoundException;
    }
}
