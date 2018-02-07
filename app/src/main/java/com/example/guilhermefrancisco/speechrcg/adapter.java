package com.example.guilhermefrancisco.speechrcg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {
    private ArrayList<Cell> galleryList;
    private Context cntxt;

    //Responsible for adapter object
    public adapter(Context cntxt, ArrayList<Cell> galleryList)
    {
        this.galleryList = galleryList;
        this.cntxt = cntxt;
    }

    //This is getting a bit annoying now. This is just responsible for the ViewHolder object.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup vG, int i) {
        View view = LayoutInflater.from(vG.getContext()).inflate(R.layout.cell, vG, false);
        return new ViewHolder(view);
    }

    //Sets images and text for the viewholder, along with displaying the category in which the image is present (example: Letra)
    @Override
    public void onBindViewHolder(final adapter.ViewHolder viewHolder, int i)
    {
        viewHolder.letter.setText(galleryList.get(i).getLetter());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource(galleryList.get(i).getImg());
        viewHolder.img.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view)
            {
                Toast.makeText(cntxt, viewHolder.letter.getText() , Toast.LENGTH_SHORT).show();
            }
    });
    }

    //Just returns the amount of items present in the gallery.
    @Override
    public int getItemCount()
    {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView letter;
        private ImageView img;
        public ViewHolder(View view){
            super(view);

            letter = (TextView) view.findViewById(R.id.letter);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
