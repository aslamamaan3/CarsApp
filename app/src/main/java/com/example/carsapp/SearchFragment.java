package com.example.carsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private Spinner spinCarMake, spinCarModel;
    private ListView carsListView;
    boolean dualpane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        //async task to populate car_make spinner
        new GetCarMake(this.getContext(), view).execute();

        //code to fetch id of carmake and use it for populating carmodel spinner
        spinCarMake = view.findViewById(R.id.spinner_carmake);
        spinCarMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                //fetching the vehicle_make id
                GetCarMake.VehicleMake vehicle_make = (GetCarMake.VehicleMake) spinCarMake.getSelectedItem();
                String vehicle_make_id = vehicle_make.getId();

                //async task to populate car_model spinner
                new GetCarModel(getContext(), vehicle_make_id, view).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //code to fetch id of carmodel and use it for populating listview
        spinCarModel = view.findViewById(R.id.spinner_carmodel);
        spinCarModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                //fetching the vehicle_make id
                GetCarMake.VehicleMake vehicle_make = (GetCarMake.VehicleMake) spinCarMake.getSelectedItem();
                String vehicle_make_id = vehicle_make.getId();

                //fetching the vehicle_model_id
                GetCarModel.VehicleModels vehicle_model = (GetCarModel.VehicleModels) spinCarModel.getSelectedItem();
                String vehicle_model_id = vehicle_model.getVehicle_model_id();

                new GetAvailableCars(getContext(), view, vehicle_make_id, vehicle_model_id).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //code for when an item is selected in listview

        carsListView = view.findViewById(R.id.carlistview);

      carsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



              //getting vehicle make
              GetCarMake.VehicleMake vehicle_make = (GetCarMake.VehicleMake) spinCarMake.getSelectedItem();
              String vehicle_make_string = vehicle_make.getVehicle_make();
              Log.i("carmake", vehicle_make_string);
              //getting vehicle model
              GetCarModel.VehicleModels vehicle_model = (GetCarModel.VehicleModels) spinCarModel.getSelectedItem();
              String vehicle_model_string = vehicle_model.getVehicle_model();
              Log.i("carmodel", vehicle_model_string);
              //getting vehicle id
              GetAvailableCars.Cars car = (GetAvailableCars.Cars) carsListView.getItemAtPosition(i);
              String carID = car.getCarId();
              Log.i("carID", carID);



              if(dualpane)
              {
                  Bundle bundle = new Bundle();
                  bundle.putString("carID", carID);
                  bundle.putString("carName", vehicle_make_string);
                  bundle.putString("carModel", vehicle_model_string);


                  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                  DetailsFragment details = new DetailsFragment();
                  details.setArguments(bundle);

                  fragmentTransaction.replace(R.id.detailsframe, details);
                  fragmentTransaction.commit();

                  Log.i("REPLCING FRAGMENT", "SUCCESSFULL BABY");
              }else{
                  Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
                  //sending details to the DetailsActivity
                  detailsIntent.putExtra(MainActivity.carIDExtra, carID);
                  detailsIntent.putExtra(MainActivity.carNameExtra, vehicle_make_string);
                  detailsIntent.putExtra(MainActivity.carModelExtra, vehicle_model_string);
                  Log.i("searchfragment", vehicle_make_string);
                  startActivity(detailsIntent);
              }



          }
      });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //checking if the pane is dualpane, by cheking the existance of our details frame
        //if details fram exists, it means app is in dual pane
        //then we will only inflate the details frame in main activity and we will not start a new activity to show details
        View detailsFrame = getActivity().findViewById(R.id.detailsframe);
        dualpane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
