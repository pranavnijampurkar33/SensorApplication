package com.example.pratham.testintegration03.filereader;

import android.net.ParseException;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.pratham.testintegration03.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import android.util.Log;

public class GraphMain extends AppCompatActivity {


    //int[] rainfall = {10,20,30,40,50,60};
    float[] userWork = {0,0};
    List<Integer> blinkData = new ArrayList<>();
    String[] userState = {"Working","Non-functional"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_main);

        //fillChartVal();

        showPieChart();


    }

    private void showPieChart() {

        fillChartVal();

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < userWork.length; i++){
            pieEntries.add(new PieEntry(userWork[i], userState[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Shows Workinghours of Employee for ");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setData(pieData);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void fillChartVal() {

        String allData = readStoredData();
        if(allData != null) {
            int j = 0, i = 0;
            float activeState = 0;
            float userWorkValue;
            while (i < allData.length()) {
                if (allData.charAt(i) == 'B') {
                    if (allData.charAt(i + 3) == '\n' || allData.charAt(i + 3) == 'n') {
                        if (isParsable(allData.substring(i + 2, i + 3))) {
                            blinkData.add(Integer.parseInt(allData.substring(i + 2, i + 3)));
                            //System.out.println(allData.substring(i + 2, i + 3));
                        } else {
                            Toast.makeText(getApplicationContext(), "The file /RegentSensorAppData/Notes.txt contains invalid data", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (isParsable(allData.substring(i + 2, i + 4))) {
                            blinkData.add(Integer.parseInt(allData.substring(i + 2, i + 4)));
                            // System.out.println(allData.substring(i + 2, i + 4));
                        } else {
                            Toast.makeText(getApplicationContext(), "The file /RegentSensorAppData/Notes.txt contains invalid data", Toast.LENGTH_LONG).show();
                        }
                    }
                    j++;
                    i++;
                } else {
                    i++;
                }
            }

            for (int blinkCount : blinkData) {
                if (blinkCount >= 15) {
                    activeState++;
                }
            }
            System.out.println("activeState : " + activeState + "blinkaData: " + blinkData.size());
            activeState = activeState / blinkData.size() * 100;
            System.out.println(activeState);
            userWork[0] = activeState;
            userWork[1] = 100 - activeState;
            String t = Float.toString(activeState);
            Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
        }
    }

    public String readStoredData(){

        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();
            String fpath = Environment.getExternalStorageDirectory()+"/RegentSensorAppData/Notes.txt";
            File checkFile = new File(fpath);
            if(checkFile.exists()) {
                br = new BufferedReader(new FileReader(fpath));
                String line = "";
                while ((line = br.readLine()) != null) {
                    output.append(line + "n");
                }
                response = output.toString();
                br.close();
            }
            else{
                Toast.makeText(getApplicationContext(),"File /RegentSensorAppData/Notes.txt is not there, Run BLUESERIAL first", Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;

    }
    public static boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(ParseException e){
            parsable = false;
        }
        return parsable;
    }
}
