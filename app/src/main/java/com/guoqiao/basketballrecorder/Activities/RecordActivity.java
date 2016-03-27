package com.guoqiao.basketballrecorder.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.guoqiao.basketballrecorder.Adapter.RecordsAdapter;
import com.guoqiao.basketballrecorder.Beans.RecordBean;
import com.guoqiao.basketballrecorder.Beans.SingleRecordBean;
import com.guoqiao.basketballrecorder.R;
import com.guoqiao.basketballrecorder.Utils.Constant;
import com.guoqiao.basketballrecorder.Utils.FileUtil;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    private String player;
    private String teamOneName;
    private String teamTwoName;

    private boolean flag = false;
    private RelativeLayout basketballCourtWrapper;
    private ImageView basketballCourt;
    private View scoreBtn;
    private View missBtn;
    private int x;
    private int y;

    private ListView recordsListView;
    private ArrayList<String> records;
    private ArrayList<SingleRecordBean> singleRecordBeans;
    private RecordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_finish:
                createFinishDialog();
                break;
        }

        return true;
    }

    public void init(){
        // intent variable
        player = getIntent().getStringExtra("player");
        teamOneName = getIntent().getStringExtra("teamOneName");
        teamTwoName = getIntent().getStringExtra("teamTwoName");

        basketballCourtWrapper = (RelativeLayout) findViewById(R.id.basketball_court_wrapper);
        basketballCourt = (ImageView) findViewById(R.id.basketball_court_view);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scoreBtn = inflater.inflate(R.layout.circle_button, null, false);
        missBtn = inflater.inflate(R.layout.circle_button, null, false);
        scoreBtn.setClickable(true);
        missBtn.setClickable(true);

        courtOnTouch();
        functionBtnOnClick();

        singleRecordBeans = new ArrayList<>();
        recordsListView = (ListView) findViewById(R.id.records);
        records = new ArrayList<>();
        adapter = new RecordsAdapter(records, this);
        recordsListView.setAdapter(adapter);
    }

    public void functionBtnOnClick(){
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description;
                // if it's a three point or two point
                if(threePoint()){
                    description = Constant.THREE_POINT_SCORE_DESCRIPTION;
                }
                else{
                    description = Constant.TWO_POINT_SCORE_DESCRIPTION;
                }

                // store to singleRecordBeans
                SingleRecordBean singleRecordBean = new SingleRecordBean();
                singleRecordBean.setX(x);
                singleRecordBean.setY(y);
                singleRecordBean.setScored(true);
                singleRecordBean.setTag(Constant.THREE_POINT);
                singleRecordBean.setDescription(description);
                singleRecordBeans.add(singleRecordBean);

                records.add(description);
                adapter.notifyDataSetChanged();

                basketballCourtWrapper.removeView(scoreBtn);
                basketballCourtWrapper.removeView(missBtn);
                flag = false;
            }
        });

        missBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description;
                // if it's a three point or two point
                if(threePoint()){
                    description = Constant.THREE_POINT_MISS_DESCRIPTION;
                }
                else{
                    description = Constant.TWO_POINT_MISS_DESCRIPTION;
                }

                // store to singleRecordBeans
                SingleRecordBean singleRecordBean = new SingleRecordBean();
                singleRecordBean.setX(x);
                singleRecordBean.setY(y);
                singleRecordBean.setScored(false);
                singleRecordBean.setTag(Constant.THREE_POINT);
                singleRecordBean.setDescription(description);
                singleRecordBeans.add(singleRecordBean);

                records.add(description);
                adapter.notifyDataSetChanged();

                basketballCourtWrapper.removeView(scoreBtn);
                basketballCourtWrapper.removeView(missBtn);
                flag = false;
            }
        });
    }


    public void courtOnTouch(){
        basketballCourt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = (int) event.getX();
                        y = (int) event.getY();

                        if (flag) {
                            basketballCourtWrapper.removeView(scoreBtn);
                            basketballCourtWrapper.removeView(missBtn);
                            flag = false;
                            break;
                        }

                        // add circle button
                        RelativeLayout.LayoutParams paramsOne = new RelativeLayout.LayoutParams(50, 50);
                        paramsOne.setMargins(x - 50, y - 80, 0, 0);
                        RelativeLayout.LayoutParams paramsTwo = new RelativeLayout.LayoutParams(50, 50);
                        paramsTwo.setMargins(x + 50, y - 80, 0, 0);

                        scoreBtn.setLayoutParams(paramsOne);
                        missBtn.setLayoutParams(paramsTwo);

                        basketballCourtWrapper.addView(scoreBtn);
                        basketballCourtWrapper.addView(missBtn);

                        // start animation
                        Animation transUpLeft = AnimationUtils.loadAnimation(RecordActivity.this, R.anim.transition_up_left);
                        Animation transUpRight = AnimationUtils.loadAnimation(RecordActivity.this, R.anim.transition_up_right);

                        scoreBtn.startAnimation(transUpLeft);
                        missBtn.startAnimation(transUpRight);
                        flag = true;
                        break;
                    default:
                        break;

                }

                return true;
            }
        });
    }

    public void createFinishDialog(){
        View dialog = getLayoutInflater().inflate(R.layout.dialog_match_finish, null, false);
        final EditText teamOneScore = (EditText) dialog.findViewById(R.id.team_one_score);
        final EditText teamTwoScore = (EditText) dialog.findViewById(R.id.team_two_score);

        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setView(dialog)
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // all records store into filesystem
                        RecordBean recordBean = new RecordBean(player, teamOneName, teamTwoName,
                                Integer.valueOf(teamOneScore.getText().toString()),
                                Integer.valueOf(teamTwoScore.getText().toString()), singleRecordBeans);

                        FileUtil.storeRecord(recordBean);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public boolean threePoint(){
        // height and width of the screen



        return true;
    }

}
