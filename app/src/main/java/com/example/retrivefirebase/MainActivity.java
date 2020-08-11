package com.example.retrivefirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrivefirebase.ViewHolder.ClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference refrence;
    protected Dialog loadDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();

        refrence = database.getReference("User");  

    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Member, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Member, ViewHolder>(
                        Member.class,
                        R.layout.image_item,
                        ViewHolder.class,
                        refrence
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Member member, int i) {

                        viewHolder.setDetails(getApplicationContext(), member.getTitle(), member.getImage());
                    }

// Methods for viewing all the Data in new Activity.

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnclickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemclick(View view, int position) {
                                TextView mtitlev = view.findViewById(R.id.rtitle_view);
                                ImageView mimageView = view.findViewById(R.id.rimage_view);

                                String title = mtitlev.getText().toString();
                                Drawable drawable = mimageView.getDrawable();
                                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                                try {
                                    Intent intent = new Intent(view.getContext(), MainActivity2.class);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                    byte[] bytes = stream.toByteArray();
                                    intent.putExtra("image", bytes);
                                    intent.putExtra("title", title);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onItemLongclick(View view, int position) {
                                Toast.makeText(MainActivity.this, "Long Pressed " + position, Toast.LENGTH_SHORT).show();
                            }
                        });


                        return viewHolder;
                    }

                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
 

}