/*
 *  SubArrayAdapter
 *
 *  Author: Kyle LePoidevin-Gonzales
 *
 * https://stackoverflow.com/questions/36579485/how-do-i-use-an-array-of-objects-with-the-android-arrayadapter
 *      For general form of custom array adapter.
 *      Author Benjamin Molina, Apr 12, 2016, no licence stated
 *
 * https://stackoverflow.com/questions/11281952/listview-with-customized-row-layout-android
 *      For use of RelativeLayout.
 *      Author Sajmon, Jul 1, 2012, no licence stated
 */

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
            headerSub.name = (TextView) row.findViewById(R.id.list_entry_title);
            headerSub.cost = (TextView) row.findViewById(R.id.list_entry_summary);
            headerSub.date = (TextView) row.findViewById(R.id.list_entry_date);

            row.setTag(headerSub);

        } else {
            headerSub = (HeaderSub) row.getTag();
        }

        Sub item = data.get(position);
        headerSub.name.setText(item.getName());
        headerSub.cost.setText(item.strGetCost());
        headerSub.date.setText(item.getStrDate());

        return row;
    }

    private class HeaderSub{
        private TextView name;
        private TextView date;
        private TextView cost;
    }
}
