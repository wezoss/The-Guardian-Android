package com.example.android.theguardian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by moham on 4/22/2019.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private Context context;
    private ArrayList<News> arrayList;
    private LayoutInflater inflater;
    private OnItemClickListner mOnItemClickListner;
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.article,parent,false);
        return new NewsHolder(v,mOnItemClickListner);
    }

    public NewsAdapter(Context context, ArrayList<News> arrayList,OnItemClickListner mOnItemClickListner) {
       inflater= LayoutInflater.from(context);
       this.context=context;
       this.arrayList=arrayList;
       this.mOnItemClickListner=mOnItemClickListner;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.authorName.setText(arrayList.get(position).getAuthorName());
        holder.title.setText(arrayList.get(position).getTitle());
        holder.section.setText(arrayList.get(position).getSection());
        holder.date.setText(dateFormatter(arrayList.get(position).getDate()));
        Picasso.get().load(arrayList.get(position).getThumbnail()).into(holder.thumbnail);

    }
    private String dateFormatter(String date){
        String[] formatted=date.split("T");


        return formatted[0];
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,date,section,authorName;
        ImageView thumbnail;
        OnItemClickListner onItemClickListner;
        public NewsHolder(View itemView,OnItemClickListner onItemClickListner) {
            super(itemView);
            thumbnail=itemView.findViewById(R.id.article_thumbnail);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            section=itemView.findViewById(R.id.section);
            authorName=itemView.findViewById(R.id.author_name);
            this.onItemClickListner=onItemClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        onItemClickListner.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemClickListner{
    void onItemClick(int position);
    }
}
