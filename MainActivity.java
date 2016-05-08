package com.stock_search.arjun.stockmarketviewer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.app.AlertDialog;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.stock_search.arjun.stockmarketviewer.Constants.FIRST_COLUMN;
import static com.stock_search.arjun.stockmarketviewer.Constants.SECOND_COLUMN;
import static com.stock_search.arjun.stockmarketviewer.Constants.THIRD_COLUMN;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;



public class MainActivity extends AppCompatActivity {

    Switch switchButton;
    public final static String Company_Name = "com.stock_search.arjun.stockmarketviewer.MESSAGE";
    private static String urlString;
    SharedPreferences.Editor editor;

    //private BaseAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Main Activity code for auto complete

        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        acTextView.setAdapter(new SuggestionAdapter(this,acTextView.getText().toString()));

       //Auto complete code ends


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //call the Favorite list method

        try {
            update_fav();




        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Code for Toggle Button
        switchButton = (Switch) findViewById(R.id.auto_refresh);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    Log.d("on","Oned");
                    //If Toggle Siwtch is on then call the update_fav() method

                    try {
                        try {
                            update_fav();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("of","offed");
                }
            }
        });







    }
//






    //Display the results and sending comtrol to the results page
    public void Results_Display(View view) {
        // Do something in response to button

       // Intent intent = new Intent(this, ResultActivity.class); //creating an intent to start a new Activity or the result activity
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        String message = acTextView.getText().toString();
        message=message.trim();
        String[] splited = message.split("\\s+");       //remove the company name and Stock market field
        message=splited[0];     //splited[0] holds the company name
        if(message.equals(""))      //case for empty message
        {
            Log.d("empty",message);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please enter a Stock Name/Symbol")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            //return;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

        else            //start an Async task to check the validity of the input string
        {

            try {
                String ans = new TestValid(this).execute(message).get();        //call to check the validity of company
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }


      //  Log.d("The company:", message);
      //  intent.putExtra(Company_Name, message);
      //  startActivity(intent);
    }

//clear method
    public void clear(View v) {
        AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        acTextView.setText("");

    }


    public void update_fav() throws ExecutionException, InterruptedException, JSONException {
        Log.d("Cal","Called me to fill up the fav list");




        ArrayList<String> favorites= ((MyApplication) getApplicationContext()).favorites;

        //Now retrieve the fav list
        SharedPreferences pref=getApplication().getSharedPreferences("FAVFILE", MODE_PRIVATE);
        String favarray=pref.getString("company","");


       // editor = pref.edit();
       // editor.clear();
       // editor.commit();



        favarray=favarray.replace("[","");
        favarray=favarray.replace("]","");
        favarray=favarray.replaceAll(" ","");
        String[] ffav=favarray.split(",");  //String ffav has the final list of all fav companies
        //Log.d("fir",ffav[0]);
        //Log.d("Sec",ffav[1]);
        //Log.d("Thir",ffav[2]);
        Log.d("mfav",favarray);

        //calling the Async task in loop to get the data



            //Start Async Task to obtain the details of Fav items
            //urlString = "http://arjunaws-env.us-west-2.elasticbeanstalk.com/?Sym="+ffav[i];

            String ans = new ProcessJSON().execute(favarray).get(); //ans holds the required data
            Log.d("ld",ans);







    }


    //Async Task

    public class ProcessJSON extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... strings){


            String stream = "";

            String favarray=strings[0];

            if(!favarray.equals(" "))
            {
                Log.d("ISSUE",favarray);
                favarray=favarray.replaceAll(" ","");
                //favarray=favarray.replaceAll(",","");
                String[] indfav =favarray.split(",");
                //Log.d("1st",indfav[0]);
                //Log.d("2nd",indfav[1]);
                int j=0;
                String[] js_obtained =new String[indfav.length];
                //
                Log.d("NOW",String.valueOf(indfav.length));

                if (indfav.length>1)
                {
                    for (int i=0;i<indfav.length;i++)
                    {
                        String urlString ="http://arjunaws-env.us-west-2.elasticbeanstalk.com/?Sym="+indfav[i];

                        HTTPDataHandler hh = new HTTPDataHandler();
                        stream = stream+ hh.GetHTTPData(urlString)+"1f";
                        Log.d("boo",stream);

                    }

                }

            }




            //String stream = null;
            //String urlString = strings[0];

            //HTTPDataHandler hh = new HTTPDataHandler();
            //stream = hh.GetHTTPData(urlString);
            // Return the data from specified url
            return stream;
                }

        protected void onPostExecute(String stream){

            int I=0,J=0,K=0;
            final ArrayList<HashMap<String, String>> list;
            final Pattern p=Pattern.compile("1f");
            Matcher m=p.matcher(stream);
            int count=0;
            while(m.find())
            {
                count +=1;
            }


            int n=count;

            final String[] mCOMP =new String[n];

            String[] mMART=new String[n];

            String[] mCMCC=new String[n];

            Log.d("Kon",String.valueOf(n));

            String[] India=stream.split("1f");      //Check Notepad for how the string comes out

            String Name=null, Symbol=null,MC=null,CP=null, SP=null;

            for (int k=0;k<India.length;k++)
            {
                if (India[k].equals("1f"))
                {
                    continue;
                }

                else
                {
                    try {
                        JSONObject reader= new JSONObject(India[k]);
                        Name = reader.getString("Name");

                        Log.d("NM",Name);

                        Symbol = reader.getString("Symbol");
                        SP= reader.getString("LastPrice");
                        SP="$ "+SP;

                        Log.d("SP",SP);

                        CP = reader.getString("ChangePercent");
                        //round to 2 decimal places
                        double iCp = Double.parseDouble(CP);
                        iCp=(double)Math.round(iCp*100)/100;

                        Log.d("CP",String.valueOf(iCp));

                        MC=reader.getString("MarketCap");



                        double iMc =Double.parseDouble(MC);
                        if(iMc>1000000000)
                        {
                            iMc =iMc/1000000000;
                            iMc=(double)Math.round(iMc*100)/100;
                            MC=String.valueOf(iMc)+" Billion";
                        }

                        else if(iMc>1000000)
                        {
                            iMc =iMc/1000000;
                            iMc=(double)Math.round(iMc*100)/100;
                            MC=String.valueOf(iMc)+" Million";

                        }


                        Log.d("MC",MC);


                        String iCOMP=Symbol+"\n"+Name;

                        String iMART=SP+"\nMarket Cap:";

                        String iCMCC;

                        iCMCC=iCp +"%\n "+ MC;           //This needs to be edited for greem color







                        //Add this to the adaptor array

                        mCOMP[I++]=iCOMP;
                        mMART[J++]=iMART;
                        mCMCC[K++]=iCMCC;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }



            }


            Log.d("GOT",stream);

            final SwipeMenuListView listView=(SwipeMenuListView)findViewById(R.id.listView1);
            list=new ArrayList<HashMap<String,String>>();

            for(int v=0;v<n;v++)
            {

                HashMap<String,String> temp=new HashMap<String, String>();


                if((mCMCC[v]!=null && !mCMCC[v].isEmpty())
                        && (mMART[v]!=null && !mMART[v].isEmpty())
                         && (mCOMP[v]!=null && !mCOMP[v].isEmpty()))
                {
                    temp.put(FIRST_COLUMN,mCOMP[v]);
                    Log.d("SUB",mCOMP[v]);
                    temp.put(SECOND_COLUMN,mMART[v]);
                    temp.put(THIRD_COLUMN,mCMCC[v]);
                    list.add(temp);
                }







            }


            //code for setting the adapter
            final ListViewAdapters adapter=new ListViewAdapters(MainActivity.this, list);
            listView.setAdapter(adapter);



            //code for setting the action listener


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                {
                    Intent intent = new Intent(view.getContext(), ResultActivity.class);

                    int pos=position;
                    String cDtl =mCOMP[pos];

                    String[] cinfo= cDtl.split("\n");

                    Log.d("juju",cinfo[0]);

                    //Not start the next activity by adding this company name in message intent
                    intent.putExtra(Company_Name, cinfo[0]);
                    startActivity(intent);


                    //Toast.makeText(MainActivity.this, mCOMP[pos]+" Clicked", Toast.LENGTH_SHORT).show();
                }

            });




            //Adator for Contextual delete

/*

            MyAdapter myAdapter = new MyAdapter();
            SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(myAdapter, MainActivity.this,
                    new OnDismissCallback() {
                        @Override
                        public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                            for (int position : reverseSortedPositions) {
                                mAdapter.remove(position);
                            }
                        }
                    }
            );
            swipeUndoAdapter.setAbsListView(mDynamicListView);
            mDynamicListView.setAdapter(swipeUndoAdapter);
            mDynamicListView.enableSimpleSwipeUndo();

        */

            //code for the swipe menu
            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {


                    // create "open" item
                    SwipeMenuItem openItem = new SwipeMenuItem(
                            getApplicationContext());
                    // set item background
                    openItem.setBackground(new ColorDrawable(Color.rgb(124,252,0)));
                    // set item width
                    openItem.setWidth(dp2px(90));


                    // set item title
                    openItem.setTitle("Back");
                    // set item title fontsize
                    openItem.setTitleSize(20);
                    // set item title font color
                    openItem.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(openItem);

                    // create "delete" item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(
                            getApplicationContext());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                            0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth(dp2px(90));

                    // set a icon
                    deleteItem.setIcon(R.mipmap.del);
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };

            // set creator
            listView.setMenuCreator(creator);

            listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    int p=position;
                    switch (index) {
                        case 0:
                            // open
                            return false;
                            //break;
                        case 1:
                            // delete
                            delete_row(p);
                            Log.d("swipe",String.valueOf(p));
                            break;

                    }
                    // false : close the menu; true : not close the menu
                    return false;
                }


                void delete_row(int position)
                {
                    Log.d("PS", String.valueOf(position));

                    list.remove(position);
                    Log.d("PI",list.toString());

                    adapter.notifyDataSetChanged();

                    //Remove the data from shared preferences
                    int pos=position;
                    String cDtl =mCOMP[pos];

                    String[] cinfo= cDtl.split("\n");

                    Log.d("DIL",cinfo[0]);

                    String del=cinfo[0];

                    SharedPreferences pref=getApplication().getSharedPreferences("FAVFILE", MODE_PRIVATE);
                    editor = pref.edit();
                    String favarray=pref.getString("company","");

                    if(!favarray.equals(""))     // case when there is an element in shared preferences
                    {
                        //Obtain the String array from the Array List
                        favarray=favarray.replace("[","");
                        favarray=favarray.replace("]","");
                        favarray=favarray.replaceAll(" ","");   //remove all the spaces
                        String[] ffav=favarray.split(",");  //String ffav has the final list of all fav companies

                        List valid = new ArrayList(Arrays.asList(ffav));

                        if (valid.contains(del))        //del hold the string to be deleted
                        {
                            //Toast.makeText(getApplicationContext(), "This company is already in favorite list!!",Toast.LENGTH_LONG).show();

                            Log.d("BON",valid.toString());
                            valid.remove(del);      //removing from the list and update back the preferences list file

                            Log.d("REMAIN",valid.toString());

                            editor.putString("company", valid.toString());
                            editor.commit();


                        }

                        else
                        {

                            /*
                            //Alert for invalid symbol
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Error!!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do things
                                            //return;
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            */
                            return;

                        }



                    }







                }












            });



            // Right
            listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

            // Left
            listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


















            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);   //disablibg automatic adding of keyboard




        } // onPostExecute() end

    } // ProcessJSON class end



    class AppAdapter extends BaseAdapter {



        @Override
        public int getViewTypeCount() {
            // menu type count
            return 2;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            //return type;

            return 1;
        }


    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // Create different menus depending on the view type
            switch (menu.getViewType()) {
                case 0:
                    // create menu of type 0
                    break;
                case 1:
                    // create menu of type 1
                    break;

            }
        }

    };

















    //Main Async Task to check the validity of company and starting the Result Activity

    private class TestValid extends AsyncTask<String, Void, String> {
        private Context context;
        AlertDialog alertDialog;
        public TestValid(Context context){

            this.context=context;
        }

        protected String doInBackground(String... strings) {
            String urlString ="http://arjunaws-env.us-west-2.elasticbeanstalk.com/?Sym="+strings[0];
            HTTPDataHandler hh = new HTTPDataHandler();
            String obtained = hh.GetHTTPData(urlString);
            Log.d("obt",obtained);

            return  obtained;
        }

        protected void onPostExecute(String result) {


            //process the JSON here
            JSONObject reader= null;

            if (result.contains("SUCCESS"))
            {

                Log.d("SUC","SUCCESS");
                //start the result Activity
                String Symbol= null;
                try {

                    reader = new JSONObject(result);
                    Symbol = reader.getString("Symbol");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, ResultActivity.class);
                intent.putExtra(Company_Name, Symbol);
                startActivity(intent);

            }

            else if (result.contains("matches"))
            {

            Log.d("FAIL","FAILURE CASE");

                //Alert for invalid symbol
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Invalid Symbol!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                //return;
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;


            }


            else if(result.contains("APP_SPECIFIC_ERROR"))
            {
                //Alert for invalid symbol
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("No Information is available for this Stock! Pls try different company!!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                                //return;
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return;




            }
            /*try {
                reader = new JSONObject(result);
                String Status=reader.getString("Status");
                Log.d("Sha",Status);


                if(Status.equals("SUCCESS"))
                {
                    //start the result Activity
                    String Symbol=reader.getString("Symbol");
                    Intent intent = new Intent(context, ResultActivity.class);
                    intent.putExtra(Company_Name, Symbol);
                    startActivity(intent);


                }

                else if (Status.equals(""))
                {
                    String Message=reader.getString("Message");
                    Log.d("RS2",Message);




                        //Alert for invalid symbol
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Invalid Symbol!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do things
                                        //return;
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;





                }












            } catch (JSONException e) {
                e.printStackTrace();
            }


        */




        }
    }









    public void RefreshOnce(View v){

        Log.d("On","Once");
        try {
            update_fav();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }









}






