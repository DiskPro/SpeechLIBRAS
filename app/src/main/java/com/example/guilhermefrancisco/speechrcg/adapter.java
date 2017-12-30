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

    public adapter(Context cntxt, ArrayList<Cell> galleryList)
    {
        this.galleryList = galleryList;
        this.cntxt = cntxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup vG, int i) {
        View view = LayoutInflater.from(vG.getContext()).inflate(R.layout.cell, vG, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(adapter.ViewHolder viewHolder, int i)
    {
        viewHolder.letra.setText(galleryList.get(i).getLetra());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageResource(galleryList.get(i).getImg());
        viewHolder.img.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view)
            {
                Toast.makeText(cntxt, viewHolder.letra.getText() , Toast.LENGTH_SHORT).show();
            }
    });
    }

    @Override
    public int getItemCount()
    {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView letra;
        private ImageView img;
        public ViewHolder(View view){
            super(view);

            letra = (TextView) view.findViewById(R.id.letra);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
