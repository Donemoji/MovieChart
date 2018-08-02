package com.j1.moviechart;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView tv_date;
    DatePickerDialog datePickerDialog;


    StringBuffer currentDate;
    StringBuffer currentDate2;
    String date;

    ListView listView;

    ArrayList<Items> items = new ArrayList<>();
    MyAdapter adapter;


    String apiKey = "2e8766f5ebd5350ed1a6e373dd35a2f4";

    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_date = findViewById(R.id.tv_date);

        listView = findViewById(R.id.listview);
        //



        adapter = new MyAdapter(items, getLayoutInflater());


        listView.setAdapter(adapter);

        adView = findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            currentDate = new StringBuffer();
            currentDate2 = new StringBuffer();
            currentDate.append(i+"");
            currentDate2.append(i+"년 ");
            if(i1<10){
                currentDate.append("0"+(i1+1));
                currentDate2.append("0"+(i1+1)+"월 ");
            }else if(i1>=10){
                currentDate.append(""+(i1+1));
                currentDate2.append(""+(i1+1)+"월 ");
            }

            if(i2<10){
                currentDate.append("0"+i2);
                currentDate2.append("0"+i2+"일 ");
            }else if(i2>=10){
                currentDate.append(""+i2);
                currentDate2.append(""+i2+"일 ");
            }
            currentDate2.append("박스오피스 TOP 10");
            date = currentDate.toString();

            tv_date.setText(currentDate2.toString());

            //선택된 날짜의 박스오피스 불러오기
            new Thread(){
                @Override
                public void run() {
                    final String address = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml"
                            +"?key="+apiKey
                            +"&targetDt="+date
                            +"&itemPerPage=10";


                    String opdate=null;
                    String rank=null;
                    String peopleAcc=null;
                    String title = null;


                    try {
                        URL url = new URL(address);

                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is);

                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp = factory.newPullParser();
                        xpp.setInput(isr);

                        int eventType = xpp.getEventType();

                        while(eventType!=XmlPullParser.END_DOCUMENT){
                            Log.i("Tag", eventType+"");
                            switch (eventType){
                                case XmlPullParser.START_DOCUMENT:
                                    break;
                                case XmlPullParser.START_TAG:
                                    Log.i("ss", xpp.getName());
                                    if(xpp.getName().equals("rank")){
                                        xpp.next();
                                        rank = xpp.getText();
                                    }else if(xpp.getName().equals("movieNm")){
                                        xpp.next();
                                        title = xpp.getText();
                                    }else if(xpp.getName().equals("openDt")){
                                        xpp.next();
                                        opdate = xpp.getText();
                                    }else if(xpp.getName().equals("audiAcc")){
                                        xpp.next();
                                        peopleAcc = xpp.getText();
                                    }
                                    break;
                                case XmlPullParser.END_TAG:
                                    Log.i("Tag", rank+title+opdate+peopleAcc);

                                    if(xpp.getName().equals("dailyBoxOffice")){
                                        Log.i("Tag", rank+title+opdate+peopleAcc);
                                        items.add(new Items(opdate, title, rank, peopleAcc));


                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

                                    }
                                    break;
                            }

                            eventType = xpp.next();

                        }



                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        }
    };

    public void btnClicked(View v){

        Date presentDate = new Date();

        datePickerDialog = new DatePickerDialog(this, dateListener, 2018,2,6);
        datePickerDialog.show();








    }












}
