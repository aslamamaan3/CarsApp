package com.example.carsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GetCarMake extends AsyncTask<Void, Void, ArrayList<GetCarMake.VehicleMake>> {

    private static String url = "https://thawing-beach-68207.herokuapp.com/carmakes";
    private ArrayList<HashMap<String, String>> carMakes;
    private Context context;
    private View view;

    //constructor to fetch the context, view
    public GetCarMake(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    //custom class whose objects will populate the spinner
    class VehicleMake{
        String vehicle_make, id;

        public String getVehicle_make() {
            return vehicle_make;
        }

        public void setVehicle_make(String vehicle_make) {
            this.vehicle_make = vehicle_make;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        @Override
        public String toString() {
            return getVehicle_make();
        }
    }

    @Override
    protected ArrayList<VehicleMake> doInBackground(Void...voids) {
        HttpHandler hh = new HttpHandler();
        carMakes = new ArrayList<HashMap<String, String>>();
        String jsonStr = hh.makeServiceCall(url);
        jsonStr = "{\"cars_list\":"   + jsonStr + "}";

        if(jsonStr != null){
            try {
                JSONObject jObj = new JSONObject(jsonStr);
                JSONArray jArray = jObj.getJSONArray("cars_list");

                for(int i =0 ; i<jArray.length(); i++){
                    JSONObject carObject = jArray.getJSONObject(i);
                    String id = carObject.getString("id");
                    String make = carObject.getString("vehicle_make");

                    HashMap<String, String> carMake = new HashMap<>();
                    carMake.put("id", id);
                    carMake.put("vehicle_make", make);

                    carMakes.add(carMake);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //this block of code makes an arraylist of VehicleMake which will be later used in arrayadapter for spinner
        ArrayList<VehicleMake> vehicle_make_list = new ArrayList<>();
        for(int i=0; i<carMakes.size(); i++){
            String vehicle_make = carMakes.get(i).get("vehicle_make");
            String id = carMakes.get(i).get("id");
            VehicleMake vml = new VehicleMake();
            vml.setId(id);
            vml.setVehicle_make(vehicle_make);
            vehicle_make_list.add(vml);
        }

        //returning the VehicleMake ArrayList which will populate spinner in onPostExecute()
        return vehicle_make_list;
    }

    @Override
    protected void onPostExecute(ArrayList<VehicleMake> result) {
        super.onPostExecute(result);

        //populating the spinner spinner_carmake
        Spinner spinCarMake = view.findViewById(R.id.spinner_carmake);
        ArrayAdapter<VehicleMake> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, result);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCarMake.setAdapter(adapter);

    }




}
