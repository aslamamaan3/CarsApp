package com.example.carsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carsapp.HttpHandler;
import com.example.carsapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class GetCarDetails extends AsyncTask<Void, Void, String[]> {

    private String carID;
    private View view;
    private DetailsActivity activity;
    private String url = "https://thawing-beach-68207.herokuapp.com/cars/";

    public GetCarDetails(DetailsActivity context ,String carID) {
        this.carID = carID;
        //this.view = view;
        this.activity = context;
        this.url = url + carID;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        String allDetails[] = null;
        HttpHandler hh = new HttpHandler();
        String jsonStr = hh.makeServiceCall(url);
        jsonStr =  "{\"cars_details\":"   + jsonStr + "}";
        Log.i("URL", url);


        if(jsonStr != null){
            try {
                JSONObject jObj = new JSONObject(jsonStr);
                JSONArray jArray = jObj.getJSONArray("cars_details");
                Log.i("jasonArray", ""+jArray.length());
                JSONObject carDetails = jArray.getJSONObject(0);
                Log.i("jasonObjet", ""+carDetails);
                String price = carDetails.getString("price");
                String location = carDetails.getString("veh_description");
                String image_url = carDetails.getString("image_url");
                String last_updated = carDetails.getString("updated_at");
                Log.i("INBackgroung", price);
                allDetails = new String[]{price, location, image_url, last_updated};

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return allDetails;
    }



    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);

        TextView price = activity.findViewById(R.id.price);
        price.setText("Price: " +result[0]+ " Dollars");

        TextView description = activity.findViewById(R.id.carDescription);
        description.setText(result[1]);

        ImageView carImage = activity.findViewById(R.id.carImage);

        Picasso.with(activity).load(result[2]).into(carImage);

        TextView lastUpdated = activity.findViewById(R.id.lastUpdated);
        lastUpdated.setText("Last Updated: " + result[3]);



    }
}
