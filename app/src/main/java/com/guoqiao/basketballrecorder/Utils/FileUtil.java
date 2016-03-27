package com.guoqiao.basketballrecorder.Utils;

import android.os.Environment;
import android.util.Log;

import com.guoqiao.basketballrecorder.Beans.HistoryBean;
import com.guoqiao.basketballrecorder.Beans.RecordBean;
import com.guoqiao.basketballrecorder.Beans.SingleRecordBean;

import java.io.BufferedReader;
import java.io.File;
import android.text.format.Time;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guoqiao on 3/23/16.
 * The first line is player name
 * The second line
 */
public class FileUtil {
    public static File createDir(String name){
        File root = new File(Environment.getExternalStorageDirectory() + "/" + name);

        if(!root.exists()){
            if (root.mkdir()) {
                Log.e("MSG","Directory is created!");
            } else {
                Log.e("MSG", "Failed to create directory!");
            }
        }

        return root;
    }


    public static ArrayList<RecordBean> getRecords(){
        File root = createDir("Records");
        File files[] = root.listFiles();
        ArrayList<RecordBean> recordBeans = new ArrayList<>();

        for(File file : files){
            recordBeans.add(readFileAndSetRecord(file));
        }

        return recordBeans;
    }

    public static void storeRecord(RecordBean recordBean){
        // check if external storage is available or not
        String state = Environment.getExternalStorageState();
        Log.e("MSG", state);
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Log.e("MSG","External Storage Available");

            File root = createDir("Records");

            // get current time
            Time now = new Time();
            now.setToNow();

            File newRecord = new File(root.getAbsolutePath() + "/" + now.toString().substring(0, 15) + ".txt");

            // write records to file
            writeFile(newRecord, recordBean);
        }
        else{
            Log.e("MSG","External Storage Not Available");
        }
    }

    private static RecordBean readFileAndSetRecord(File file){
        RecordBean recordBean = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            // read basic info
            String playername = bufferedReader.readLine();
            String teamOneName = bufferedReader.readLine();
            int teamOneScore  = Integer.valueOf(bufferedReader.readLine());
            String teamTwoName = bufferedReader.readLine();
            int teamTwoScore  = Integer.valueOf(bufferedReader.readLine());

            // read records
            ArrayList<SingleRecordBean> records = new ArrayList<>();
            String line;
            while((line = bufferedReader.readLine()) != null){
                SingleRecordBean singleRecordBean = new SingleRecordBean();
                records.add(singleRecordBean.stringToSingleRecordBean(line));
            }

            recordBean = new RecordBean(playername, teamOneName, teamTwoName, teamOneScore, teamTwoScore, records);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordBean;
    }

    private static void writeFile(File file, RecordBean recordBean){
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write((recordBean.getPlayer() + "\n").getBytes());
            outputStream.write((recordBean.getTeamOneName() + "\n").getBytes());
            outputStream.write((String.valueOf(recordBean.getTeamOneScore()) + "\n").getBytes());
            outputStream.write((recordBean.getTeamTwoName() + "\n").getBytes());
            outputStream.write((String.valueOf(recordBean.getTeamTwoScore()) + "\n").getBytes());
            for (SingleRecordBean record : recordBean.getRecords()) {
                outputStream.write((record.toString() + "\n").getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
