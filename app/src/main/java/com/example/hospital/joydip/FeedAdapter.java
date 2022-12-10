package com.example.hospital.joydip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital.joydip.firebasetemplate.DomainFeed;
import com.example.hospital.joydip.firebasetemplate.DomainUserInfo;
import com.example.hospital.joydip.firebasetemplate.FirebaseAuthCustom;
import com.example.hospital.joydip.firebasetemplate.WritingDocument;

import java.util.HashMap;
import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.Viewholder> {
    public  static  boolean isPatient=false;
    List<DomainFeed> list;
    Context context;

    public FeedAdapter(Context context, List<DomainFeed> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override


    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.recycler_feed_activity, parent, false);
        Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int pos) {
        String msg="";
        if(!isPatient)
       msg="A Request From: "+list.get(pos).from;
        else
        {
            msg="A Request Sent To: "+list.get(pos).to;
            if(list.get(pos).status.equals("true"))
                msg=msg+" (accepted)";
            else
                msg=msg+" (not accepted yet)";

            holder.request.setVisibility(View.GONE);
        }
        holder.nameFrom.setText(msg);
        if(list.get(pos).status.equals("true"))
            holder.request.setText("Done");

        ///
        holder.request.setOnClickListener(view -> {
            String to = list.get(pos).to;
            String from =list.get(pos).from;
            String status = "true";
            String docId = to + from;
            HashMap<String, Object> data = new HashMap<>();
            data.put("from", from);
            data.put("to", to);
            data.put("status", status);
            new WritingDocument().updateRequest(docId, data);
        });

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nameFrom;
        Button request;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nameFrom=itemView.findViewById(R.id.formTV);
            request = itemView.findViewById(R.id.requestBTN);

        }
    }
}
