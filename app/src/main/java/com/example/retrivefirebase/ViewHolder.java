package com.example.retrivefirebase;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClicklistner.onItemclick(view,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClicklistner.onItemLongclick(view,getAdapterPosition());
                return false;
            }
        });

    }
    public  void setDetails(Context context, String title, String image){
        TextView mtitletv = view.findViewById(R.id.rtitle_view);
        ImageView mImageview = view.findViewById(R.id.rimage_view);

        mtitletv.setText(title);
        Picasso.get().load(image).into(mImageview);

        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }

    private ViewHolder.ClickListener mClicklistner;

    protected interface ClickListener {
        void onItemclick(View view,int position);
        void onItemLongclick(View view,int position);

    }

    public void setOnclickListener(ViewHolder.ClickListener clickListener){
        mClicklistner = clickListener;
    }
}
