package com.guoqiao.basketballrecorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guoqiao.basketballrecorder.Beans.RecordBean;
import com.guoqiao.basketballrecorder.R;

import java.util.ArrayList;

/**
 * Created by Guoqiao on 3/23/16.
 */
public class HistoryAdapter extends BaseAdapter {
    private ArrayList<RecordBean> recordBeans;
    private Context context;

    public HistoryAdapter(ArrayList<RecordBean> recordBeans, Context context) {
        this.recordBeans = recordBeans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return recordBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_item_history, null, false);

        TextView teamName = (TextView) view.findViewById(R.id.teamName);
        TextView playerName = (TextView) view.findViewById(R.id.player_name);

        RecordBean recordBean = recordBeans.get(position);
        teamName.setText(recordBean.getTeamOneName() + " VS " + recordBean.getTeamTwoName());
        playerName.setText("Player: " + recordBean.getPlayer());

        return view;
    }
}
