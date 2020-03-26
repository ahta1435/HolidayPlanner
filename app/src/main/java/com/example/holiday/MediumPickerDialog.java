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

import com.example.holiday.TripPlanner;

public class MediumPickerDialog extends AppCompatDialogFragment  {
    private ImageView image;
    private ImageView bus, aero;
    private MediumDialog mediumDialog;
    Bitmap bitmap;
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

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 1;
                bus.setBackgroundColor(getResources().getColor(R.color.blue));
                aero.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        aero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 2;
                aero.setBackgroundColor(getResources().getColor(R.color.blue));
                bus.setBackgroundColor(getResources().getColor(R.color.white));
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
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
                byte[] byte_arr = stream.toByteArray();
                String  image= Base64.encodeToString(byte_arr, 1);*/
                //mediumDialog.applyText(image);
                mediumDialog.applyPic(id);
            }
        });
        image=view.findViewById(R.id.bus);
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
        void applyText(String image);
        void applyPic(int id);
    }
}
