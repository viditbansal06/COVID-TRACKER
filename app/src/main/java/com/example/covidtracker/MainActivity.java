package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> activeCases=new ArrayList<>();
    static ArrayList<String> newInfected=new ArrayList<>();
    static ArrayList<String> recovered=new ArrayList<>();
    static ArrayList<String> newRecovered=new ArrayList<>();
    static ArrayList<String> totalInfected=new ArrayList<>();
    static ArrayList<String> region=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

TextView activecases;
TextView recoveredcases;
TextView totalcases;
TextView testedyesterday;
ListView listView;
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
                activecases.setText(one);
                recoveredcases.setText(two);
                totalcases.setText(three);
                testedyesterday.setText(four);
                //JSONObject jsonObject1=new JSONObject(s);
                String s1=jsonObject.getString("regionData");
                JSONArray array=new JSONArray(s1);

                for(int i=0;i<array.length();i++)
                {
                    JSONObject jsonpart=array.getJSONObject(i);
                    region.add(jsonpart.getString("region"));
                    activeCases.add(jsonpart.getString("activeCases"));
                    newInfected.add(jsonpart.getString("newInfected"));
                    recovered.add(jsonpart.getString("recovered"));
                    newRecovered.add(jsonpart.getString("newRecovered"));
                    totalInfected.add(jsonpart.getString("totalInfected"));

                }
                arrayAdapter.notifyDataSetChanged();




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
        listView=findViewById(R.id.listView);
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,region);
        listView.setAdapter(arrayAdapter);
        DownloadTask task=new DownloadTask();

        try {
            task.execute("https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/LATEST?disableRedirect=true");



        }catch (Exception e)
        {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),statewisedata.class);
                intent.putExtra("index",position);


                startActivity(intent);
            }
        });

    }

}