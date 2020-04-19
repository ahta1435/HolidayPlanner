package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class BusRequestActivity extends AppCompatActivity {
    private RecyclerView rView;
    private FirestoreRecyclerAdapter<Buses,BusHolder> firestoreRecyclerAdapter;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_request);
        String startPoint=getIntent().getStringExtra("startBusPoint");
        String destinationPoint=getIntent().getStringExtra("destinationBusPoint");
        String TripId=getIntent().getStringExtra("TripId");
        final int res=getIntent().getIntExtra("id",0);
        rView=(RecyclerView)findViewById(R.id.bus_recycler);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);
        firebaseFirestore=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=firebaseFirestore.collection("Buses");
        Query query=collectionReference.whereEqualTo("starting",startPoint)
                     .whereEqualTo("destination",destinationPoint);
        FirestoreRecyclerOptions<Buses> response=new FirestoreRecyclerOptions.Builder<Buses>()
                .setQuery(query,Buses.class)
                .build();
        firestoreRecyclerAdapter=new FirestoreRecyclerAdapter<Buses, BusHolder>(response) {
            @Override
            protected void onBindViewHolder(@NonNull BusHolder holder, int position, @NonNull Buses model) {
                        holder.setBuses(model);
            }
            @NonNull
            @Override
            public BusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.buses_list,parent,false);
                return new BusHolder(view);
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Toast.makeText(BusRequestActivity.this,"Error:"+e.toString(),Toast.LENGTH_LONG).show();
            }
        };
       rView.setAdapter(firestoreRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firestoreRecyclerAdapter!=null){
            firestoreRecyclerAdapter.stopListening();
        }
    }

    public static class BusHolder extends RecyclerView.ViewHolder{
         private TextView from;
         private TextView to;
         private TextView price;
        public BusHolder(@NonNull View itemView) {
            super(itemView);
            from=(TextView)itemView.findViewById(R.id.From);
            to=(TextView)itemView.findViewById(R.id.to);
            price=(TextView)itemView.findViewById(R.id.price);
        }
        void setBuses(Buses buses){
            String starting_stand=buses.getStarting();
            from.setText(starting_stand);
            String destination_stand=buses.getDestination();
            to.setText(destination_stand);
            int pr=buses.getPrice();
            price.setText("$"+pr);
        }
    }
}
