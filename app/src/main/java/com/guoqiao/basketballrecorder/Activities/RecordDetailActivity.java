package com.guoqiao.basketballrecorder.Activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.guoqiao.basketballrecorder.Adapter.RecordsAdapter;
import com.guoqiao.basketballrecorder.Beans.RecordBean;
import com.guoqiao.basketballrecorder.Beans.SingleRecordBean;
import com.guoqiao.basketballrecorder.R;
import com.guoqiao.basketballrecorder.Utils.Constant;
import com.guoqiao.basketballrecorder.Utils.GsonUtil;

import java.util.ArrayList;

public class RecordDetailActivity extends AppCompatActivity {
    private RecordBean recordBean;

    // stat view variables
    private ScrollView statView;
    private TextView totalScore;
    private TextView assist;
    private TextView rebound;
    private TextView steal;
    private TextView threePointShot;
    private TextView threePointScored;
    private TextView threePointMissed;
    private TextView threePointRate;
    private TextView twoPointShot;
    private TextView twoPointScored;
    private TextView twoPointMissed;
    private TextView twoPointRate;
    private TextView freeThrowPointShot;
    private TextView freeThrowPointScored;
    private TextView freeThrowPointMissed;
    private TextView freeThrowPointRate;

    // text view variables
    private ListView textViewList;
    private RecordsAdapter adapter;
    private ArrayList<String> records;

    // graph view
    private RelativeLayout graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_stat_view:
                statView.setVisibility(View.VISIBLE);
                textViewList.setVisibility(View.GONE);
                break;
            case R.id.action_text_view:
                statView.setVisibility(View.GONE);
                textViewList.setVisibility(View.VISIBLE);
                break;

            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void init(){
        recordBean = GsonUtil.stringToRecordBean(getIntent().getStringExtra("recordBean"));

        if(findViewById(R.id.graph_view) == null) {
            // init statistic view
            statView = (ScrollView) findViewById(R.id.stat_view);
            totalScore = (TextView) findViewById(R.id.stat_total_score);
            steal = (TextView) findViewById(R.id.stat_steal);
            rebound = (TextView) findViewById(R.id.stat_rebound);
            assist = (TextView) findViewById(R.id.stat_assist);
            threePointShot = (TextView) findViewById(R.id.stat_three_point_shot);
            threePointScored = (TextView) findViewById(R.id.stat_three_point_scored);
            threePointMissed = (TextView) findViewById(R.id.stat_three_point_missed);
            threePointRate = (TextView) findViewById(R.id.stat_three_point_rate);
            twoPointShot = (TextView) findViewById(R.id.stat_two_point_shot);
            twoPointScored = (TextView) findViewById(R.id.stat_two_point_scored);
            twoPointMissed = (TextView) findViewById(R.id.stat_two_point_missed);
            twoPointRate = (TextView) findViewById(R.id.stat_two_point_rate);
            freeThrowPointShot = (TextView) findViewById(R.id.stat_free_throw_shot);
            freeThrowPointScored = (TextView) findViewById(R.id.stat_free_throw_scored);
            freeThrowPointMissed = (TextView) findViewById(R.id.stat_free_throw_missed);
            freeThrowPointRate = (TextView) findViewById(R.id.stat_free_throw_rate);

            totalScore.setText(String.valueOf(recordBean.getTwoPointScored() * 2 + recordBean.getThreePointScored() * 3 + recordBean.getFreeThrowScored()));
            steal.setText(String.valueOf(recordBean.getSteal()));
            rebound.setText(String.valueOf(recordBean.getRebound()));
            assist.setText(String.valueOf(recordBean.getAssist()));
            threePointShot.setText(String.valueOf(recordBean.getThreePointScored() + recordBean.getThreePointMissed()));
            threePointScored.setText(String.valueOf(recordBean.getThreePointScored()));
            threePointMissed.setText(String.valueOf(recordBean.getThreePointMissed()));
            threePointRate.setText(String.valueOf(recordBean.getThreePointScored() * 100.0 / (recordBean.getThreePointMissed() + recordBean.getThreePointScored())) + "%");
            twoPointShot.setText(String.valueOf(recordBean.getTwoPointMissed() + recordBean.getTwoPointScored()));
            twoPointScored.setText(String.valueOf(recordBean.getTwoPointScored()));
            twoPointMissed.setText(String.valueOf(recordBean.getTwoPointMissed()));
            twoPointRate.setText(String.valueOf(recordBean.getTwoPointScored() * 100.0 / (recordBean.getTwoPointMissed() + recordBean.getTwoPointScored())) + "%");
            freeThrowPointShot.setText(String.valueOf(recordBean.getFreeThrowMissed() + recordBean.getFreeThrowScored()));
            freeThrowPointScored.setText(String.valueOf(recordBean.getFreeThrowScored()));
            freeThrowPointMissed.setText(String.valueOf(recordBean.getFreeThrowMissed()));
            freeThrowPointRate.setText(String.valueOf(recordBean.getFreeThrowScored() * 100.0 / (recordBean.getFreeThrowScored() + recordBean.getFreeThrowMissed())) + "%");


            // init text view
            textViewList = (ListView) findViewById(R.id.text_view);
            records = new ArrayList<>();
            for (SingleRecordBean singleRecordBean : recordBean.getRecords()) {
                records.add(singleRecordBean.getDescription());
            }
            adapter = new RecordsAdapter(records, this);
            textViewList.setAdapter(adapter);
        }

        // init graph view
        else {
            int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
            int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
            Log.e("MSG", "ScreenHeight: " + screenHeight);
            Log.e("MSG", "ScreenWidth: " + screenWidth);

            graphView = (RelativeLayout) findViewById(R.id.graph_view);
            for (SingleRecordBean singleRecordBean : recordBean.getRecords()) {
                int tag = singleRecordBean.getTag();
                if (tag == Constant.THREE_POINT || tag == Constant.TWO_POINT) {
                    ImageView imageView = new ImageView(this);

                    int x = (int) (singleRecordBean.getX() * screenWidth / screenHeight);
                    int y = (int) (singleRecordBean.getY() * screenHeight / ((screenWidth - 50) * 4.0/7));


                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(20, 20);
                    params.setMargins(x - 10, y - 10, 0, 0);
                    imageView.setLayoutParams(params);
                    if (singleRecordBean.isScored())
                        imageView.setBackgroundResource(R.drawable.circle);
                    else
                        imageView.setBackgroundResource(R.drawable.close);

                    graphView.addView(imageView);
                }
            }
        }
    }


}
