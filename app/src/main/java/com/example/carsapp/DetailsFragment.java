package com.example.carsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {




    private OnFragmentInteractionListener mListener;
    private TextView make_model, price, carDetails, lastUpdated;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_land, container, false);

        Bundle bundle = getArguments();
        Log.i("BEFORE", "BEFORE SETTING UP VALUES");


        if (bundle != null) {
            Log.i("BUNDLE IS NOT NULLE", "SUCCESS");
            String carID = bundle.getString("carID");
            String carMake = bundle.getString("carName");
            String carModel = bundle.getString("carModel");

            make_model = view.findViewById(R.id.make_model);
            price = view.findViewById(R.id.price);
            carDetails = view.findViewById(R.id.carDescription);
            make_model.setText(carMake + " - " + carModel);

            new GetCarDetailsForFragment(this.getContext(), view, carID).execute();

        }
        return view;
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
