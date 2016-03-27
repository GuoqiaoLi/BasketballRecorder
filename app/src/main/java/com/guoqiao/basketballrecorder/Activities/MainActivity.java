package com.guoqiao.basketballrecorder.Activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.guoqiao.basketballrecorder.Adapter.HistoryAdapter;
import com.guoqiao.basketballrecorder.Beans.RecordBean;
import com.guoqiao.basketballrecorder.R;
import com.guoqiao.basketballrecorder.Utils.FileUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // local variable
    private ListView historyListView;
    private HistoryAdapter adapter;
    private ArrayList<RecordBean> recordBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if(id == R.id.action_new_record){
            Intent intent = new Intent(this, RecordActivity.class);
            intent.putExtra("player", "guoqiao");
            intent.putExtra("teamOneName", "Huren");
            intent.putExtra("teamTwoName", "Yongshi");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historyListView = (ListView) findViewById(R.id.record_history);
        recordBeans = FileUtil.getRecords();
        adapter = new HistoryAdapter(recordBeans, this);
        historyListView.setAdapter(adapter);
    }

    public void historyListItemClick(){
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
