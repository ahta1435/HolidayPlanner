package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaneRequestActivity extends AppCompatActivity {
   private RecyclerView plane;
   private FirebaseFirestore firebaseFirestore;
   private FirestoreRecyclerAdapter<Planes,PlaneViewHolder> firestoreRecyclerAdapter;
   int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_request);
        plane=(RecyclerView)findViewById(R.id.Plane_list);
        String tripId=getIntent().getStringExtra("TripId");
        String startAirport=getIntent().getStringExtra("startAirport");
        String destinationAirport=getIntent().getStringExtra("destinationAirport");
        String DOJ=getIntent().getStringExtra("dateOfJourney");
        plane.setHasFixedSize(true);
        plane.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=firebaseFirestore.collection("Planes");
        Query query=collectionReference.whereEqualTo("starting",startAirport).whereEqualTo("destination",destinationAirport);
        FirestoreRecyclerOptions<Planes> response = new FirestoreRecyclerOptions.Builder<Planes>()
                .setQuery(query, Planes.class)
                .build();
        firestoreRecyclerAdapter= new FirestoreRecyclerAdapter<Planes,PlaneViewHolder>(response) {
            @NonNull
            @Override
            public PlaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
              View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.planes_item,parent,false);
                return new PlaneViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PlaneViewHolder holder, int position, @NonNull Planes model) {
                     holder.book.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                             final String DocId= snapshot.getId();
                             Intent intent=new Intent(PlaneRequestActivity.this,UserDetails.class);
                             intent.putExtra("Document",DocId);
                             intent.putExtra("TripId",tripId);
                             startActivity(intent);
                         }
                     });
                             holder.setPlanes(model);
            }
            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Toast.makeText(PlaneRequestActivity.this,"Error:"+e.toString(),Toast.LENGTH_LONG).show();
            }
        };
        plane.setAdapter(firestoreRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
       if (firestoreRecyclerAdapter!=null){
           firestoreRecyclerAdapter.stopListening();
       }
    }

    public static class PlaneViewHolder extends RecyclerView.ViewHolder{
        private TextView  starting,ending,price;
        private CardView book;
        public PlaneViewHolder(@NonNull View itemView) {
            super(itemView);
            starting=(TextView)itemView.findViewById(R.id.From);
            ending=(TextView)itemView.findViewById(R.id.to);
            price=(TextView)itemView.findViewById(R.id.price);
           book=(CardView)itemView.findViewById(R.id.Book);
        }
        void setPlanes(Planes planes){
            String st=planes.getStarting();
            starting.setText(st);
            String dest=planes.getDestination();
            ending.setText(dest);
            int bud=planes.getPrice();
            price.setText("$"+bud);

        }
    }
}
