package com.stock_search.arjun.stockmarketviewer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Arjun on 03-05-2016.
 */
public class MyDialogFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_layout, container, false);
        //getDialog().setTitle("Simple Dialog");

        //Call to the Async Task to load the Image
        // obtain the string from gettab1data() method in Result Activity
        ResultActivity activity = (ResultActivity) getActivity();
        String myDataFromActivity = activity.gettab1data();
        JSONObject reader = null;
        try {
            reader = new JSONObject(myDataFromActivity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Symbol = null;
        try {
            Symbol = reader.getString("Symbol");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Calling the Image View Code
        String Yahoo_URL="http://chart.finance.yahoo.com/t?s="+Symbol+"&lang=en-US&width=1080&height=480";
        new DownloadImageTask((ImageView) rootView.findViewById(R.id.yahoo)).execute(Yahoo_URL);

        return rootView;
    }

//code for zoom

    ImageView mImageView;
    PhotoViewAttacher mAttacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("onc","onc");
        /*code that needs to be added
        getActivity().setContentView(R.layout.activity_main);

        // Any implementation of ImageView can be used!
        mImageView = (ImageView) getActivity().findViewById(R.id.yahoo);

        // Set the Drawable displayed
        Drawable bitmap = getResources().getDrawable(result);
        mImageView.setImageDrawable(bitmap);

        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        mAttacher = new PhotoViewAttacher(mImageView);
        */

    }


// If you later call mImageView.setImageDrawable/setImageBitmap/setImageResource/etc then you just need to call
   // mAttacher.update();

//Code for the Image View to Obtain it using HTTP



    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            Log.d("onc","onc");
            //getActivity().setContentView(R.layout.activity_main);

            // Any implementation of ImageView can be used!
            //mImageView = (ImageView) getActivity().findViewById(R.id.yahoo);

            Drawable d = new BitmapDrawable(getResources(), result);
            // Set the Drawable displayed
            //Drawable bitmap = getResources().getDrawable(result);
            bmImage.setImageDrawable(d);

            // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
            mAttacher = new PhotoViewAttacher(bmImage);


        }


    }



}
