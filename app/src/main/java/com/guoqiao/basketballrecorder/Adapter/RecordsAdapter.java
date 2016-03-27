package com.guoqiao.basketballrecorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guoqiao.basketballrecorder.R;

import java.util.ArrayList;

/**
 * Created by Guoqiao on 3/25/16.
 */
public class RecordsAdapter extends BaseAdapter {
    private ArrayList<String> records;
    Context context;

    public RecordsAdapter(ArrayList<String> records, Context context) {
        this.records = records;
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item_record, null, false);

        TextView recordText = (TextView) itemView.findViewById(R.id.record_text);
        recordText.setText(records.get(position));

        return itemView;
    }
}
