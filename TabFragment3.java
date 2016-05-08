package com.stock_search.arjun.stockmarketviewer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class TabFragment3 extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setRetainInstance(true);
        View v=inflater.inflate(R.layout.tab_fragment_3, container, false);

        // obtain the string from gettab1data() method in Result Activity
        ResultActivity activity = (ResultActivity) getActivity();
        String myDataFromActivity = activity.gettab1data();
        JSONObject reader= null;
        try {
            reader = new JSONObject(myDataFromActivity);
            String cSymbol = reader.getString("Symbol");

            String ans = new ProcessJSON().execute(cSymbol).get();
            Log.d("kabali:",ans);

            JSONObject ob1 = new JSONObject(ans);
            JSONObject ob2=ob1.getJSONObject("d");
            JSONArray J1=ob2.getJSONArray("results");

            int len=J1.length();
            Log.d("JARR", String.valueOf(len));


            //obtain all the text View fields to fill up data

            TextView nh1= (TextView)v.findViewById(R.id.nh1);
            TextView nh2= (TextView)v.findViewById(R.id.nh2);
            TextView nh3= (TextView)v.findViewById(R.id.nh3);
            TextView nh4= (TextView)v.findViewById(R.id.nh4);

            TextView con1= (TextView)v.findViewById(R.id.con1);
            TextView con2= (TextView)v.findViewById(R.id.con2);
            TextView con3= (TextView)v.findViewById(R.id.con3);
            TextView con4= (TextView)v.findViewById(R.id.con4);

            TextView pub1= (TextView)v.findViewById(R.id.pub1);
            TextView pub2= (TextView)v.findViewById(R.id.pub2);
            TextView pub3= (TextView)v.findViewById(R.id.pub3);
            TextView pub4= (TextView)v.findViewById(R.id.pub4);

            TextView dat1= (TextView)v.findViewById(R.id.dat1);
            TextView dat2= (TextView)v.findViewById(R.id.dat2);
            TextView dat3= (TextView)v.findViewById(R.id.dat3);
            TextView dat4= (TextView)v.findViewById(R.id.dat4);

            View v1= (View)v.findViewById(R.id.v1);
            View v2= (View)v.findViewById(R.id.v2);
            View v3= (View)v.findViewById(R.id.v3);







            if(len>0)       //There is atleast 1 News available
            {
                //Set the first Row
                JSONObject IN1=J1.getJSONObject(0);
                final String U1=IN1.getString("Url");
                String C1=IN1.getString("Description");
                String P1="Publisher : "+IN1.getString("Source");
                String T1=IN1.getString("Title");

                String TM1="Date : "+IN1.getString("Date");
                TM1=TM1.replace("T",", ");
                TM1=TM1.replace("Z","");


                //Set all the data for 1st News field
                nh1.setText(T1);
                con1.setText(C1);
                pub1.setText(P1);
                dat1.setText(TM1);

                //Setting On Click Listener

                nh1.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Log.d("cl1","You clicked 1 bro!!!");

                                               String url1= null;

                                                   url1 = U1;

                                               url1=url1.replaceAll("'\'","");
                                               Log.d("url1",url1);

                                               Uri uri = Uri.parse(url1);
                                               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                               startActivity(intent);
                                           }
                                       }


                );




                if(len>1)
                {

                    //Setting Second Item
                    JSONObject IN2=J1.getJSONObject(1);
                    final String U2=IN2.getString("Url");
                    String C2=IN2.getString("Description");
                    String P2="Publisher : "+IN2.getString("Source");
                    String T2=IN2.getString("Title");

                    String TM2="Date : "+IN2.getString("Date");
                    TM2=TM2.replace("T",", ");
                    TM2=TM2.replace("Z","");




                    nh2.setText(T2);
                    con2.setText(C2);
                    pub2.setText(P2);
                    dat2.setText(TM2);



                    nh2.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Log.d("cl2","You clicked 2 bro!!!");

                                                   String url2= null;

                                                       url2 = U2;

                                                   url2=url2.replaceAll("'\'","");
                                                   Log.d("url2",url2);

                                                   Uri uri = Uri.parse(url2);
                                                   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                   startActivity(intent);
                                               }
                                           }


                    );



                    if(len>2)
                    {

                        JSONObject IN3=J1.getJSONObject(2);
                        String T3=IN3.getString("Title");
                        String C3=IN3.getString("Description");
                        String P3="Publisher : "+IN3.getString("Source");
                        final String U3=IN3.getString("Url");


                        String TM3="Date : "+IN3.getString("Date");
                        TM3=TM3.replace("T",", ");
                        TM3=TM3.replace("Z","");

                        nh3.setText(T3);
                        con3.setText(C3);
                        pub3.setText(P3);
                        dat3.setText(TM3);



                        nh3.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       Log.d("cl3","You clicked 3 bro!!!");

                                                       String url3= null;

                                                           url3 = U3;

                                                       url3=url3.replaceAll("'\'","");
                                                       Log.d("url3",url3);

                                                       Uri uri = Uri.parse(url3);
                                                       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                       startActivity(intent);
                                                   }
                                               }


                        );







                        if(len>3)       //set all 4 items
                        {


                            Log.d("IN1",IN1.toString());


                            JSONObject IN4=J1.getJSONObject(3);

                            String T4=IN4.getString("Title");

                            final String U4=IN4.getString("Url");

                            String C4=IN4.getString("Description");

                            String P4="Publisher : "+IN4.getString("Source");







                            String TM4="Date : "+IN4.getString("Date");
                            TM4=TM4.replace("T",", ");
                            TM4=TM4.replace("Z","");


                            Log.d("T1",T1);






                            nh4.setText(T4);
                            con4.setText(C4);
                            pub4.setText(P4);
                            dat4.setText(TM4);


                            nh4.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           Log.d("cl4","You clicked 4 bro!!!");

                                                           String url4= null;

                                                               url4 = U4;

                                                           url4=url4.replaceAll("'\'","");
                                                           Log.d("url4",url4);

                                                           Uri uri = Uri.parse(url4);
                                                           Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                           startActivity(intent);
                                                       }
                                                   }


                            );



                        }


                        else
                        {

                            nh4.setVisibility(View.GONE);

                            con4.setVisibility(View.GONE);

                            pub4.setVisibility(View.GONE);

                            dat4.setVisibility(View.GONE);

                            v3.setVisibility(View.GONE);


                        }


                    }

                    else
                    {


                        nh3.setVisibility(View.GONE);
                        nh4.setVisibility(View.GONE);

                        con3.setVisibility(View.GONE);
                        con4.setVisibility(View.GONE);

                        pub3.setVisibility(View.GONE);
                        pub4.setVisibility(View.GONE);

                        dat3.setVisibility(View.GONE);
                        dat4.setVisibility(View.GONE);

                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.GONE);
                    }



                }

                else        //case when News feed has only 1 element remove all the views for 2nd, 3rd and 4rth
                {

                    //remove all the other views
                    nh2.setVisibility(View.GONE);
                    nh3.setVisibility(View.GONE);
                    nh4.setVisibility(View.GONE);

                    con2.setVisibility(View.GONE);
                    con3.setVisibility(View.GONE);
                    con4.setVisibility(View.GONE);

                    pub2.setVisibility(View.GONE);
                    pub3.setVisibility(View.GONE);
                    pub4.setVisibility(View.GONE);

                    dat2.setVisibility(View.GONE);
                    dat3.setVisibility(View.GONE);
                    dat4.setVisibility(View.GONE);




                }





            }

            else        //case when News feed has only 1 element remove all the views for 2nd, 3rd and 4rth
            {

                //remove all the other views
                nh2.setVisibility(View.GONE);
                nh3.setVisibility(View.GONE);
                nh4.setVisibility(View.GONE);

                con2.setVisibility(View.GONE);
                con3.setVisibility(View.GONE);
                con4.setVisibility(View.GONE);

                pub2.setVisibility(View.GONE);
                pub3.setVisibility(View.GONE);
                pub4.setVisibility(View.GONE);

                dat2.setVisibility(View.GONE);
                dat3.setVisibility(View.GONE);
                dat4.setVisibility(View.GONE);




            }







        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }











        return v;



    }


    //Async Task

    public class ProcessJSON extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... strings){

            String stream=strings[0];
            String urlString ="http://cs-server.usc.edu:10904/index_BING.php?feed="+stream;

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);
            Log.d("loaper",stream);

            return stream;
        }

        protected void onPostExecute(String stream){





        } // onPostExecute() end

    } // ProcessJSON class end


}