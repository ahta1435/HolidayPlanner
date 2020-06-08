package com.example.holiday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HistoryOfBookings extends AppCompatActivity {
     private RecyclerView historyRecycler;
     private FirebaseRecyclerAdapter<History,HistoryViewHolder> firebaseRecyclerAdapter;
     private DatabaseReference databaseReference;
     private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_of_bookings);
        historyRecycler=(RecyclerView)findViewById(R.id.HistoryRecycler);
        historyRecycler.setHasFixedSize(true);
        historyRecycler.setLayoutManager(new LinearLayoutManager(this));
        databaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();
        Query query=databaseReference.child("History").child(userId);
        FirebaseRecyclerOptions<History> firebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<History>()
                .setQuery(query,History.class)
                .build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<History, HistoryViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull HistoryViewHolder holder, int position, @NonNull History model) {
                holder.btn_cancel_ticket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      databaseReference.child("History").child(userId)
                              .child(getRef(position).getKey()).removeValue();
                    }
                });
                holder.setHistory(model);
            }

            @NonNull
            @Override
            public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout,parent,false);
                return new HistoryViewHolder(view);
            }
        };
        historyRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseRecyclerAdapter!=null){
            firebaseRecyclerAdapter.stopListening();
        }
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        private TextView s,t,ticket;
        private ImageView i;
        private Button btn_cancel_ticket;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            s=(TextView)itemView.findViewById(R.id.journey_from_airport);
            t=(TextView)itemView.findViewById(R.id.journey_to);
            i=(ImageView) itemView.findViewById(R.id.means);
            btn_cancel_ticket=(Button)itemView.findViewById(R.id.cancel_ticket);
            ticket=(TextView)itemView.findViewById(R.id.date_of_ticket);
        }
        public void setHistory(History history){
            String start=history.getStarting();
            s.setText(start);
            String destination=history.getDestination();
            t.setText(destination);
            String date=history.getDate();
            String[] str1=date.split("-");
            ticket.setText(str1[0]+"\n"+str1[1]+"\n"+str1[2]);
            String imageUrl=history.getImage();
            Glide.with(i).load(imageUrl).into(i);
        }
    }
}
