package com.example.lepoidev_subbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kyle on 2018-02-01.
 * HEAVILY ADAPTED FROM BENJAMIN MOLINA's POST
 * https://stackoverflow.com/questions/36579485/how-do-i-use-an-array-of-objects-with-the-android-arrayadapter
 * https://stackoverflow.com/questions/11281952/listview-with-customized-row-layout-android
 */

public class SubArrayAdapter extends ArrayAdapter<Sub> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Sub> data = null;

    public SubArrayAdapter(Context context, int resource, ArrayList<Sub> objects){
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        HeaderSub headerSub = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            headerSub = new HeaderSub();
            headerSub.name = (TextView) row.findViewById(R.id.nameTextView);
            headerSub.cost = (TextView) row.findViewById(R.id.costTextView);
            //headerSub.comment = (TextView) row.findViewById(R.id.commentTextView);
            headerSub.date = (TextView) row.findViewById(R.id.dateTextView);

            row.setTag(headerSub);

        } else {
            headerSub = (HeaderSub) row.getTag();
        }

        Sub item = data.get(position);
        headerSub.name.setText(item.getName());
        headerSub.cost.setText(String.valueOf(item.getCost()));
        headerSub.date.setText((CharSequence) item.getDate());

        return row;
    }

    private class HeaderSub{
        private TextView name;
        private TextView date;
        private TextView cost;
        private TextView comment;
    }
}
