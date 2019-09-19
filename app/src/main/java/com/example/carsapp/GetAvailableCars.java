package com.example.carsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAvailableCars extends AsyncTask<Void, Void, ArrayList<GetAvailableCars.Cars>> {

    private ArrayList<HashMap<String, String>> carsListMap;
    private String vehicle_make_id, vehicle_model_id;
    private Context context;
    private View view;
    private String url = "https://thawing-beach-68207.herokuapp.com/cars/";
    ArrayList<Cars> carsArrayList;
    private static final String TAG  = "error dikhao";


    //constructor to fetch the context, view and vehicle_make_id, vehicle_model_id and make the url
    public GetAvailableCars(Context context, View view, String vehicle_make_id, String vehicle_model_id) {
        this.vehicle_make_id = vehicle_make_id;
        this.vehicle_model_id = vehicle_model_id;
        this.context = context;
        this.view = view;
        this.url = url = url  + vehicle_make_id + "/" + vehicle_model_id + "/" + "92603";

    }

    //custom class whose objects will populate the listview
    class Cars{
        public String carId, vehicle_make;

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getVehicle_make() {
            return vehicle_make;
        }

        public void setVehicle_make(String vehicle_make) {
            this.vehicle_make = vehicle_make;
        }

        @Override
        public String toString() {
            return getVehicle_make();
        }
    }


    @Override
    protected ArrayList<Cars> doInBackground(Void... voids) {

        HttpHandler hh = new HttpHandler();
        carsListMap = new ArrayList<>();
        String jsonStr = hh.makeServiceCall(url);

        if(jsonStr != null){

            try {

                JSONObject jObj = new JSONObject(jsonStr);
                JSONArray jArray = jObj.getJSONArray("lists");

                for(int i=0; i<jArray.length(); i++){
                    JSONObject carObj = jArray.getJSONObject(i);
                    String vehicle_make = carObj.getString("vehicle_make");
                    String id = carObj.getString("id");

                    HashMap<String, String> car = new HashMap<>();
                    car.put("id", id);
                    car.put("vehicle_make", vehicle_make);

                    carsListMap.add(car);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Log.i(TAG, carsListMap.size() + "");
        //Arraylist of Cars objects to be send to ListView
        carsArrayList = new ArrayList<>();
        for(int i = 0; i<carsListMap.size(); i++){
            String id = carsListMap.get(i).get("id");
            String vehicle_make = carsListMap.get(i).get("vehicle_make");

            Cars c = new Cars();
            c.setCarId(id);
            c.setVehicle_make(vehicle_make);

           //adding car object to arraylist
           carsArrayList.add(c);

        }
        //returning the Cars ArrayList which will populate ListView in onPostExecute()
        return carsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Cars> result) {
        super.onPostExecute(result);

        //populating the ListView carlistview
        ListView carListView = view.findViewById(R.id.carlistview);
        ArrayAdapter<Cars> adapter = new ArrayAdapter<>(context, R.layout.activity_listtext, result);
        carListView.setAdapter(adapter);
    }

}
