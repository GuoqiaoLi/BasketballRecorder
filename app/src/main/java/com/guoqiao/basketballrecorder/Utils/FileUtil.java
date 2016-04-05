package com.guoqiao.basketballrecorder.Utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
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
    private Context context;

    public FileUtil(Context context){
        this.context = context;
    }

    public File createDir(){
        File root = context.getDir("records", Context.MODE_PRIVATE);
        return root;
    }


    public ArrayList<RecordBean> getRecords(){
        File root = createDir();
        File files[] = root.listFiles();
        ArrayList<RecordBean> recordBeans = new ArrayList<>();

        for(File file : files){
            recordBeans.add(readFileAndSetRecord(file));
        }

        return recordBeans;
    }

    public void storeRecord(RecordBean recordBean){
            Log.e("MSG","External Storage Available");

            File root = createDir();

            // get current time
            Time now = new Time();
            now.setToNow();

            File newRecord = new File(root, now.toString().substring(0, 15) + ".txt");

            // write records to file
            writeFile(newRecord, recordBean);

    }

    private RecordBean readFileAndSetRecord(File file){
        RecordBean recordBean = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // read records
            String line;
            while((line = bufferedReader.readLine()) != null){
                recordBean = GsonUtil.stringToRecordBean(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordBean;
    }

    private void writeFile(File file, RecordBean recordBean){
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write((recordBean.toString() + "\n").getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
