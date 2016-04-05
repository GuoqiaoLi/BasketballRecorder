package com.guoqiao.basketballrecorder.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

    private TextView welcomeView;

    FileUtil fileUtil = new FileUtil(this);


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
        if (id == R.id.action_show_history) {
            historyListView.setVisibility(View.VISIBLE);
            welcomeView.setVisibility(View.GONE);
        }

        else if(id == R.id.action_welcome){
            historyListView.setVisibility(View.GONE);
            welcomeView.setVisibility(View.VISIBLE);
        }

        else if(id == R.id.action_new_record){
            newMatchDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historyListView = (ListView) findViewById(R.id.record_history);
        recordBeans = fileUtil.getRecords();
        adapter = new HistoryAdapter(recordBeans, this);
        historyListView.setAdapter(adapter);

        welcomeView = (TextView) findViewById(R.id.welcome_view);

        historyListItemClick();
    }

    public void historyListItemClick(){
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RecordDetailActivity.class);
                intent.putExtra("recordBean", recordBeans.get(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    public void newMatchDialog(){
        View newMatch = getLayoutInflater().inflate(R.layout.dialog_new_match, null);

        final EditText playerName = (EditText) newMatch.findViewById(R.id.player_name);
        final EditText teamOneName = (EditText) newMatch.findViewById(R.id.team_one_name);
        final EditText teamTwoName = (EditText) newMatch.findViewById(R.id.team_two_name);

        new AlertDialog.Builder(this).setTitle("New Match")
                .setView(newMatch)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                        intent.putExtra("player", playerName.getText().toString());
                        intent.putExtra("teamOneName", teamOneName.getText().toString());
                        intent.putExtra("teamTwoName", teamTwoName.getText().toString());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
