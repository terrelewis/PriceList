package com.example.acer.pricelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Acer on 1/4/2016.
 */
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    ArrayList<DatModle> al;
    int j;

    public PriceAdapter(ArrayList <DatModle> arrayList, int k) {
        this.al=arrayList;
        j=k;
    }

    @Override
    public PriceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_det, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PriceAdapter.ViewHolder viewHolder, int i) {

        TextView _txt;
        TextView _price;

        _txt = viewHolder.txt1;
        _price=viewHolder.txt2;

        _txt.setText(al.get(i).getType());
        if(j==1)
           _price.setText(al.get(i).getWiPrice());
        else
        if(j==2)
            _price.setText(al.get(i).getwPrice());
        else
            if(j==3)
                _price.setText(al.get(i).getiPrice());

    }

    @Override
    public int getItemCount() {
        return al.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt1;
public TextView txt2;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt1 = (TextView) itemView.findViewById(R.id.item_name);
            this.txt2 = (TextView) itemView.findViewById(R.id.item_price);

        }
    }


}











