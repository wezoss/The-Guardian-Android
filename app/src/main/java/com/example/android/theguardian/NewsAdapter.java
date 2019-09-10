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
import java.util.ArrayList;

/**
 * Created by moham on 4/22/2019.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private ArrayList<News> arrayList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.article, parent, false);
        return new NewsHolder(v, mOnItemClickListener);
    }

    public NewsAdapter(Context context, ArrayList<News> arrayList, OnItemClickListener mOnItemClickListner) {
        inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.mOnItemClickListener = mOnItemClickListner;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.authorName.setText(arrayList.get(position).getAuthorName());
        holder.title.setText(arrayList.get(position).getTitle());
        holder.section.setText(arrayList.get(position).getSection());
        holder.date.setText(dateFormatter(arrayList.get(position).getDate()));
        Picasso.get().load(arrayList.get(position).getThumbnail()).into(holder.thumbnail);

    }

    private String dateFormatter(String date) {
        String[] formatted = date.split("T");


        return formatted[0];
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TODO some people like to call views with some prefixes like tv for TextView, btn for Button and so on
        //Example tvTitle, tvDate, tvSection, tvAuthorName
        //ivThumbnail
        TextView title, date, section, authorName;
        ImageView thumbnail;
        OnItemClickListener onItemClickListener;

        public NewsHolder(View itemView, OnItemClickListener onItemClickListner) {
            super(itemView);
            //TODO you can use ButterKnife library, it is easier than the boilerplate findViewById calls
            thumbnail = itemView.findViewById(R.id.article_thumbnail);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            section = itemView.findViewById(R.id.section);
            authorName = itemView.findViewById(R.id.author_name);
            this.onItemClickListener = onItemClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
