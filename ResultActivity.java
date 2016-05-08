package com.stock_search.arjun.stockmarketviewer;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;

import android.support.design.widget.TabLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ResultActivity extends AppCompatActivity {

    public String jsstr1;
    private static String urlString;
    public static String ans;
    SharedPreferences.Editor editor;
    private Menu menu;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());





        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.Company_Name);
        //Log.d("Received",message);
        setTitle(message);    //setting title to the stock name

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //setting up back button

        //Calling Async Task
        try {
            run_async(message);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }

    public String run_async(String message) throws ExecutionException, InterruptedException {
        urlString = "http://arjunaws-env.us-west-2.elasticbeanstalk.com/?Sym="+message+"&feed="+message;
        //urlString = "http://arjunaws-env.us-west-2.elasticbeanstalk.com/?Sym="+"AAPL";


        try {

            ans = new ProcessJSON().execute(urlString).get();
            Log.d("ans",ans);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    return ans;

    }



    //Async Task

    public class ProcessJSON extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);
            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream){

            //send data to fragment 1 through bundle


            if(stream !=null){
                settab1data(stream);        //setting stream

                //adding code for the tabbed layout

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.addTab(tabLayout.newTab().setText("CURRENT"));
                tabLayout.addTab(tabLayout.newTab().setText("HISTORICAL"));
                tabLayout.addTab(tabLayout.newTab().setText("NEWS"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                final PagerAdapter adapter = new PagerAdapter (getSupportFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                //Code for Tabbed layout ends here



                // Get the full HTTP Data as JSONObject
                // Log.d("The stream:",stream);
                //settab1data(stream);

                    //JSONObject reader= new JSONObject(stream);

                    // Get the JSONObject "lkpobj"...........................
                    //JSONObject lkpobj = reader.getJSONOb("");
                    // Get the value of key "cName" under JSONObject "lkpobj"
                    //String cName = reader.getString("Name");
                    // Get the value of key "cSymbol" under JSONObject "lkpobj"
                    //String cSymbol = reader.getString("Symbol");

                    //if(tv.getParent()!=null)
                    //  ((ViewGroup)tv.getParent()).removeView(tv); // <- fix
                    //Cur.addView(tv);
                    //Cur.addView(tv);


                    //tv.setText(tv.getText()+ "\tlkpobj...\n");
                    //tv.setText(tv.getText()+ "\n"+ cName + "\n");
                    //tv.setText(tv.getText()+ "\n"+ cSymbol + "\n");
                    //Cur.addView(tv);
                    // process other data as this way..............




            } // if statement end
        } // onPostExecute() end
    } // ProcessJSON class end










    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.result_menu, menu);

        this.menu = menu;       //added for the Yellow color change of the menu item on click

        Drawable drawable = menu.findItem(R.id.fav).getIcon();



        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.Company_Name);
        SharedPreferences pref=getApplication().getSharedPreferences("FAVFILE", MODE_PRIVATE);
        String favarray=pref.getString("company","");
        Log.d("aY",favarray);

        if(favarray!="")     // case when there is an element in shared preferences
        {

            //Obtain the String array from the Array List
            favarray=favarray.replace("[","");
            favarray=favarray.replace("]","");
            favarray=favarray.replaceAll(" ","");
            String[] ffav=favarray.split(",");  //String ffav has the final list of all fav companies

            List valid = Arrays.asList(ffav);

            if (valid.contains(message))
            {
                //editor = pref.edit();
                //editor.clear();
                // editor.commit();

                if (drawable != null) {
                    drawable.mutate();
                    drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                    Log.d("yel","Setting Yellow");
                }



            }
            else        //add this company to the shared preferences
            {

                if (drawable != null) {
                    drawable.mutate();
                    drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                }



            }


        }





/*
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

*/

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;

            case R.id.fb:
                try {
                    fb_share();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.fav:
            //change color

                //Change the color of Menu to yellow
                //Menu menu= (Menu) this.findViewById(R.id.fav);

                MenuItem MI = menu.findItem(R.id.fav);
                Drawable drawable = MI.getIcon();
                //Set the color to yellow again
                if (drawable != null) {
                    drawable.mutate();
                    drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                    Log.d("yel","Setting Yellow");
                }




                try {
                    add_fav();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    //code for fetching JSON data asyncronosly using ASYNC



    public String gettab1data()
    {
        if(jsstr1!=null)
        {
           // Log.d("get jsstrd",jsstr1);
            return jsstr1;
        }

    return "Empty";
    }

    public void settab1data(String jsstr1)
    {
        //Log.d("set jsstrd","set jsstrd");
        //Log.d("set jsstrd ",jsstr1);
        this.jsstr1=jsstr1;

    }


    public void fb_share() throws JSONException {
        Log.d("fb","you clicked fb");
        String Name = null,Symbol = null,LastPrice = null;
        String Yahoo_URL=null;

        //parsing the data obtained from JSON

        String data=gettab1data();

        if(data!="Empty")
        {
            JSONObject reader= new JSONObject(data);
            Name = reader.getString("Name");
            Symbol = reader.getString("Symbol");
            LastPrice=reader.getString("LastPrice");
            Yahoo_URL="http://chart.finance.yahoo.com/t?s="+Symbol+"&lang=en-US&width=1080&height=480";


            //Just Put a Toast Message about Sharing

            Toast.makeText(getApplicationContext(), "Sharing "+Name+".!!",Toast.LENGTH_LONG).show();
        }


        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        //shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {


        //});

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Current Stock Price of "+Name+" is "+LastPrice)
                    .setContentDescription(
                            "Stock Information of "+Name+"( "+Symbol+")")
                    .setContentUrl(Uri.parse("http://dev.markitondemand.com/MODApis/"))

                    .setImageUrl(Uri.parse(Yahoo_URL))
                    .build();

            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                @Override
                public void onSuccess(Sharer.Result result)
                {
                    Log.d("fb Success",result.toString());
                    Toast.makeText(getBaseContext(), "You Shared this Post!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel()
                {
                    Toast.makeText(getApplicationContext(), "Post Cancelled!",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FacebookException error)
                {
                    Toast.makeText(getApplicationContext(), "Post Failed!",
                            Toast.LENGTH_LONG).show();
                }
            });
            shareDialog.canShow(linkContent);
            shareDialog.show(linkContent);





        }

    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void add_fav() throws JSONException

    {
        ArrayList<String> favorite;
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.Company_Name);

        Log.d("cal","You called me now!!");
        //first check the shared preferences file
        //Now retrieve the fav list
        SharedPreferences pref=getApplication().getSharedPreferences("FAVFILE", MODE_PRIVATE);
        String favarray=pref.getString("company","");

        Log.d("BUG",favarray);


        if(!favarray.equals(""))     // case when there is an element in shared preferences
        {
            Log.d("ENT","Entered to add");

            //Obtain the String array from the Array List
            favarray=favarray.replace("[","");
            favarray=favarray.replace("]","");
            favarray=favarray.replaceAll(" ","");   //remove all the spaces
            String[] ffav=favarray.split(",");  //String ffav has the final list of all fav companies

            List valid = Arrays.asList(ffav);

            if (valid.contains(message))
            {
                Toast.makeText(getApplicationContext(), "This company is already in favorite list!!",Toast.LENGTH_LONG).show();

                //editor = pref.edit();
                //editor.clear();
                // editor.commit();

            }
            else        //add this company to the shared preferences
            {


                ArrayList<String> NwFav = new ArrayList<String>(Arrays.asList(ffav));

                NwFav.add(message);

                // Now set the shared preference
                editor = pref.edit();
                editor.putString("company", NwFav.toString());
                editor.commit();

                Toast.makeText(getApplicationContext(), "Company added to Favorite List!!",Toast.LENGTH_LONG).show();

            }







        }
        //Log.d("NF",favarray);
        else        //first time case
        {
            favorite = new ArrayList<String>(10);       //create a new Array List and populate the value
            Log.d("favnul","Fav array is null");
            favorite.add(message);

            // Now set the shared preference
            editor = pref.edit();
            editor.putString("company", favorite.toString());
            editor.commit();
            Toast.makeText(getApplicationContext(), "Company added to Favorite List!!",Toast.LENGTH_LONG).show();

        }




    }







}
