package com.example.carsapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GetCarModel extends AsyncTask<Void, Void, ArrayList<GetCarModel.VehicleModels>> {
    private ArrayList<HashMap<String, String>> carModels;
    private String vehicle_make_id;
    private Context context;
    private View view;
    private String url = "https://thawing-beach-68207.herokuapp.com/carmodelmakes/";
    ArrayList<VehicleModels> vehicle_model_list;



    //constructor to fetch the context, view and vehicle_make_id and make the url
    public GetCarModel(Context context, String vehicle_make_id, View view) {
        this.context = context;
        this.vehicle_make_id = vehicle_make_id;
        this.view = view;
        this.url = url + vehicle_make_id;
    }

    //custom class whose objects will populate the spinner
    class VehicleModels{
        public String vehicle_model, vehicle_model_id;


        public String getVehicle_model() {
            return vehicle_model;
        }

        public void setVehicle_model(String vehicle_model) {
            this.vehicle_model = vehicle_model;
        }

        public String getVehicle_model_id() {
            return vehicle_model_id;
        }

        public void setVehicle_model_id(String vehicle_model_id) {
            this.vehicle_model_id = vehicle_model_id;
        }

        @Override
        public String toString() {
            return getVehicle_model();
        }
    }

    @Override
    protected ArrayList<VehicleModels> doInBackground(Void... voids) {
        HttpHandler hh = new HttpHandler();
        carModels = new ArrayList<>();
        String jsonStr = hh.makeServiceCall(url);
        jsonStr =  "{\"cars_models\":"   + jsonStr + "}";


        //reading data from json
        if(jsonStr != null){
            try {
                JSONObject jObj = new JSONObject(jsonStr);
                JSONArray jArray = jObj.getJSONArray("cars_models");

                for(int i=0; i<jArray.length(); i++){
                    JSONObject carModel = jArray.getJSONObject(i);
                    String vehicle_model_id = carModel.getString("id");
                    String vehicle_model = carModel.getString("model");

                    HashMap<String, String> car = new HashMap<>();
                    car.put("vehicle_model_id", vehicle_model_id);
                    car.put("vehicle_model", vehicle_model);

                    carModels.add(car);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //Arraylist of VehicleModel objects to be send to spinner
        vehicle_model_list = new ArrayList<>();
        for(int i=0; i< carModels.size(); i++){
            String vehicle_model_id = carModels.get(i).get("vehicle_model_id");
            String vehicle_model = carModels.get(i).get("vehicle_model");
            //creating a VehicleModel object to insert into out ArrayList which will later populate the spinner
            VehicleModels vm = new VehicleModels();
            vm.setVehicle_model(vehicle_model);;
            vm.setVehicle_model_id(vehicle_model_id);

            //adding VehicleModel Object to arraylist
            vehicle_model_list.add(vm);
        }

        //returning the VehicleModels ArrayList which will populate spinner in onPostExecute()
        return vehicle_model_list;
    }



    @Override
    protected void onPostExecute(ArrayList<VehicleModels> result) {
        super.onPostExecute(result);

        //populating the spinner spinner_carmodel
        Spinner spinCarModel = view.findViewById(R.id.spinner_carmodel);
        ArrayAdapter<VehicleModels> model_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, result);
        model_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCarModel.setAdapter(model_adapter);
        model_adapter.notifyDataSetChanged();


    }
}
