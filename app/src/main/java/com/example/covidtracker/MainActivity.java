package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
TextView activecases;
TextView recoveredcases;
TextView totalcases;
TextView testedyesterday;
GraphView graphView;
int a,b,c,d;


    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = " ";
            URL url;
            HttpURLConnection urlConnection;
            try {
                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1)
                {
                    char current=(char) data;
                    result=result + current;
                    data=reader.read();
                }
                return  result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject jsonObject=new JSONObject(s);
                String one,two,three,four;
                one=jsonObject.getString("activeCases");
                a=Integer.parseInt(one);
                Log.i("active cases",Integer.toString(a));
                two=jsonObject.getString("recovered");
                b=Integer.parseInt(two);
                three=jsonObject.getString("totalCases");
                c=Integer.parseInt(three);
                four=jsonObject.getString("previousDayTests");
                d=Integer.parseInt(four);
                BarGraphSeries<DataPoint> series=new BarGraphSeries<>(getDataPoint());
                graphView.addSeries(series);
                series.setDrawValuesOnTop(true);
                series.setValuesOnTopColor(Color.BLACK);
                series.setSpacing(10);
                activecases.setText(one);
                recoveredcases.setText(two);
                totalcases.setText(three);
                testedyesterday.setText(four);



/*
                JSONArray array=new JSONArray("cases12");

                for(int i=0;i<array.length();i++)
                {
                    JSONObject jsonPart=array.getJSONObject(i);
                    Log.i("main",jsonPart.getString("activecases12"));
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activecases=findViewById(R.id.activecases);
        recoveredcases=findViewById(R.id.recoveredcases);
        totalcases=findViewById(R.id.totalcases);
        testedyesterday=findViewById(R.id.testedyesterday);
        graphView=(GraphView) findViewById(R.id.graphview);


        DownloadTask task=new DownloadTask();

        try {
            task.execute("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");



        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private DataPoint[] getDataPoint() {
        DataPoint[] dp=new DataPoint[] {
                new DataPoint(1,a),
                new DataPoint(2,b),
                new DataPoint(3,c),
                new DataPoint(4,d)



        };
        return (dp);
    }
}