package com.guoqiao.basketballrecorder.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.guoqiao.basketballrecorder.Adapter.RecordsAdapter;
import com.guoqiao.basketballrecorder.Beans.RecordBean;
import com.guoqiao.basketballrecorder.Beans.SingleRecordBean;
import com.guoqiao.basketballrecorder.R;
import com.guoqiao.basketballrecorder.Utils.AnimationUtil;
import com.guoqiao.basketballrecorder.Utils.Constant;
import com.guoqiao.basketballrecorder.Utils.FileUtil;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    FileUtil fileUtil = new FileUtil(this);

    private String player;
    private String teamOneName;
    private String teamTwoName;

    private int threePointScored = 0;
    private int threePointMissed = 0;
    private int twoPointScored = 0;
    private int twoPointMissed = 0;
    private int freeThrowScored = 0;
    private int freeThrowMissed = 0;
    private int rebound = 0;
    private int steal = 0;
    private int assist = 0;
    private int tag;
    private int teamOneScore = 0;
    private int teamTwoScore = 0;

    private ImageView functionBtn;
    private ImageView stealBtn;
    private ImageView reboundBtn;
    private ImageView assistBtn;
    private boolean extraFunctionShow = false;

    private boolean flag = false;
    private boolean threePoint = false;
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
        scoreBtn = inflater.inflate(R.layout.score_btn, null, false);
        missBtn = inflater.inflate(R.layout.miss_btn, null, false);
        scoreBtn.setClickable(true);
        missBtn.setClickable(true);

        functionBtn = (ImageView) findViewById(R.id.extra_function);
        stealBtn = (ImageView) findViewById(R.id.function_steal);
        assistBtn = (ImageView) findViewById(R.id.function_assist);
        reboundBtn = (ImageView) findViewById(R.id.function_rebound);

        courtOnTouch();
        functionBtnOnClick();
        extraFunctionBtnOnClick();

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
                if(tag == Constant.THREE_POINT){
                    description = Constant.THREE_POINT_SCORE_DESCRIPTION;
                    threePointScored++;
                }
                else if(tag == Constant.TWO_POINT){
                    description = Constant.TWO_POINT_SCORE_DESCRIPTION;
                    twoPointScored++;
                }
                else{
                    description = Constant.FREE_THROW_SCORE_DESCRIPTION;
                    freeThrowScored++;
                }

                addSingleRecord(true, tag, description);

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

                if(tag == Constant.THREE_POINT){
                    description = Constant.THREE_POINT_MISS_DESCRIPTION;
                    threePointMissed++;
                }
                else if(tag == Constant.TWO_POINT){
                    description = Constant.TWO_POINT_MISS_DESCRIPTION;
                    twoPointMissed++;
                }
                else{
                    description = Constant.FREE_THROW_MISS_DESCRIPTION;
                    freeThrowMissed++;
                }

                addSingleRecord(false, tag, description);

                records.add(description);
                adapter.notifyDataSetChanged();

                basketballCourtWrapper.removeView(scoreBtn);
                basketballCourtWrapper.removeView(missBtn);
                flag = false;
            }
        });

        stealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steal++;
                records.add(Constant.STEAL_DESCRIPTION);
                adapter.notifyDataSetChanged();
                addSingleRecord(false, Constant.STEAL, Constant.STEAL_DESCRIPTION);
                hideExtraFunctionBtns();
            }
        });

        reboundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rebound++;
                records.add(Constant.REBOUND_DESCRIPTION);
                adapter.notifyDataSetChanged();
                addSingleRecord(false, Constant.REBOUND, Constant.REBOUND_DESCRIPTION);
                hideExtraFunctionBtns();
            }
        });

        assistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assist++;
                records.add(Constant.ASSIST_DESCRIPTION);
                adapter.notifyDataSetChanged();
                addSingleRecord(false, Constant.ASSIST, Constant.ASSIST_DESCRIPTION);
                hideExtraFunctionBtns();
            }
        });
    }


    public void extraFunctionBtnOnClick(){
        functionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if extra function btns not shown, show btns
                if (!extraFunctionShow) {
                    AnimationUtil.startUpShowAnimation(RecordActivity.this, assistBtn);
                    AnimationUtil.startLeftShowAnimation(RecordActivity.this, reboundBtn);
                    AnimationUtil.startUpLeftShowAnimation(RecordActivity.this, stealBtn);

                    AnimationUtil.startRotateRightAnimation(RecordActivity.this, functionBtn);

                    extraFunctionShow = true;
                } else {
                    hideExtraFunctionBtns();
                }
            }
        });
    }


    public void courtOnTouch(){
        basketballCourt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("MSG", "Onclick called");

                if(extraFunctionShow){
                    hideExtraFunctionBtns();
                    extraFunctionShow = false;
                }

                // if already btns show, remove the btns
                if(flag){
                    basketballCourtWrapper.removeView(scoreBtn);
                    basketballCourtWrapper.removeView(missBtn);
                    flag = false;
                }
                // else show btns
                else {
                    if(threePoint){
                        tag = Constant.THREE_POINT;
                    }
                    else{
                        tag = Constant.TWO_POINT;
                    }
                    showScoreMissBtns();
                }

            }
        });

        basketballCourt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.e("MSG", "OnLongClick called");
                if (basketballCourtWrapper.getChildCount() > 5) {
                    basketballCourtWrapper.removeView(scoreBtn);
                    basketballCourtWrapper.removeView(missBtn);
                }
                tag = Constant.FREE_THROW;
                showScoreMissBtns();
                return true;
            }
        });

        basketballCourt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int H = v.getHeight();
                        int W = v.getWidth();
                        x = (int) event.getX();
                        y = (int) event.getY();
                        threePoint = ThreeOrNot(H, W, x, y);
                        break;
                    default:
                        break;

                }

                return false;
            }
        });
    }

    public void showScoreMissBtns(){
        // add circle button
        RelativeLayout.LayoutParams paramsOne = new RelativeLayout.LayoutParams(50, 50);
        paramsOne.setMargins(x - 100, y - 80, 0, 0);
        RelativeLayout.LayoutParams paramsTwo = new RelativeLayout.LayoutParams(50, 50);
        paramsTwo.setMargins(x + 50, y - 80, 0, 0);

        scoreBtn.setLayoutParams(paramsOne);
        missBtn.setLayoutParams(paramsTwo);

        basketballCourtWrapper.addView(scoreBtn);
        basketballCourtWrapper.addView(missBtn);

        AnimationUtil.startUpLeftShowAnimation(RecordActivity.this, scoreBtn);
        AnimationUtil.startUpRightAnimation(RecordActivity.this, missBtn);

        flag = true;
    }


    public void createFinishDialog(){
        View dialog = getLayoutInflater().inflate(R.layout.dialog_match_finish, null, false);
        final EditText teamOneScoreEditText = (EditText) dialog.findViewById(R.id.team_one_score);
        final EditText teamTwoScoreEditText = (EditText) dialog.findViewById(R.id.team_two_score);

        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setView(dialog)
                .setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teamOneScore = Integer.valueOf(teamOneScoreEditText.getText().toString());
                        teamTwoScore = Integer.valueOf(teamTwoScoreEditText.getText().toString());

                        // all records store into filesystem
                        RecordBean recordBean = new RecordBean(player, teamOneName, teamTwoName,
                                teamOneScore,
                                teamTwoScore,
                                threePointScored, threePointMissed, twoPointScored, twoPointMissed, freeThrowScored, freeThrowMissed,
                                rebound, steal, assist, singleRecordBeans);

                        fileUtil.storeRecord(recordBean);

                        finish();
                    }
                })
                .setNeutralButton("Send Email", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View emailDialog = getLayoutInflater().inflate(R.layout.dialog_send_email, null);

                        EditText emailAddr = (EditText) emailDialog.findViewById(R.id.email_address);
                        final String email = emailAddr.getText().toString();

                        new AlertDialog.Builder(RecordActivity.this)
                                .setView(emailDialog)
                                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendEmail(email);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }


    public void hideExtraFunctionBtns(){
        AnimationUtil.startUpHideAnimation(RecordActivity.this, assistBtn);
        AnimationUtil.startLeftHideAnimation(RecordActivity.this, reboundBtn);
        AnimationUtil.startUpLeftHideAnimation(RecordActivity.this, stealBtn);

        AnimationUtil.startRotateLeftAnimation(RecordActivity.this, functionBtn);

        extraFunctionShow = false;
    }

    public void addSingleRecord(boolean scored, int tag, String description){
        // store to singleRecordBeans
        SingleRecordBean singleRecordBean = new SingleRecordBean();
        singleRecordBean.setX(x);
        singleRecordBean.setY(y);
        singleRecordBean.setScored(scored);
        singleRecordBean.setTag(tag);
        singleRecordBean.setDescription(description);
        singleRecordBeans.add(singleRecordBean);
    }

    public boolean ThreeOrNot(int H, int W, int x, int y) {
        if(y <= H/6.0) {
            if(W/2.0 - 5.0/12*W <= x && W/2.0 + 5.0/12*W >= x) {
                return false;
            }
        }
        else {
            double dis = Math.pow((x - W/2.0),2.0) + Math.pow((y - H/8.0),2);
            return dis > Math.pow((5.0/12.0*W ),2);
        }
        return true;
    }

    public void removeLastRecord(View view){
        if(records.size() == 0)
            return;

        records.remove(records.size()-1);
        adapter.notifyDataSetChanged();

        // rollback to previous version
        SingleRecordBean singleRecordBean = singleRecordBeans.get(singleRecordBeans.size()-1);
        switch (singleRecordBean.getTag()){
            case Constant.TWO_POINT:
                if(singleRecordBean.isScored()){
                    twoPointScored--;
                }
                else{
                    twoPointMissed--;
                }
                break;
            case Constant.THREE_POINT:
                if(singleRecordBean.isScored()){
                    threePointScored--;
                }
                else{
                    threePointMissed--;
                }
                break;
            case Constant.FREE_THROW:
                if(singleRecordBean.isScored()){
                    freeThrowScored--;
                }
                else{
                    freeThrowMissed--;
                }
                break;
            case Constant.STEAL:
                steal--;
                break;
            case Constant.REBOUND:
                rebound--;
                break;
            case Constant.ASSIST:
                assist--;
                break;
            default:
                break;
        }
        singleRecordBeans.remove(singleRecordBeans.size() - 1);
    }

    public String constructEmailContent(){
        String ret = "This is feedback from BasketballRecorder";
        return ret;
    }

    public void sendEmail(String emailAddr){
        String [] TO = {emailAddr};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        String content = constructEmailContent();

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "BasketballRecorder Feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RecordActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
