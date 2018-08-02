package com.j1.moviechart;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PC on 2018-03-07.
 */

public class MyAdapter extends BaseAdapter {

    ArrayList<Items> items;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<Items> items, LayoutInflater inflater) {
        this.items = items;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = inflater.inflate(R.layout.item_layout, null);
        }

        Items item = items.get(i);

        TextView date = view.findViewById(R.id.tv_showdate);
        TextView title = view.findViewById(R.id.tv_title);
        TextView peopleAcc = view.findViewById(R.id.tv_peoplecnt);
        TextView rank = view.findViewById(R.id.tv_rank);

        date.setText(item.date);
        peopleAcc.setText(item.peopleAcc);
        title.setText(item.title);
        rank.setText(item.rank);






        return view;
    }
}
