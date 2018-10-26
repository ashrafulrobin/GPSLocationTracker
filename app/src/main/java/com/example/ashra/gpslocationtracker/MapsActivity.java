package com.example.ashra.gpslocationtracker;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //GSA=google service Available

        if(GSA()){
            Toast.makeText(this,"Service Perfect",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);

            initMap();
        }
        else {


        }

    }


    private void initMap(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public boolean GSA() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS)
            return true;

        else
            if(googleApiAvailability.isUserResolvableError(isAvailable)){

                Dialog dialog = googleApiAvailability.getErrorDialog(this,isAvailable,0);
                dialog.show();
            }

            else
            {

                Toast.makeText(this,"Can't connect to play service",Toast.LENGTH_LONG);
            }

            return false;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //gotoLocation(23.819337, 90.426915,10);

     //   LatLng sydney = new LatLng(23.819337, 90.426915);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    //LatLng & camera access

    private void gotoLocation(double lat,double lng){

      LatLng latLng = new LatLng(lat,lng); //p//
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        mMap.animateCamera(cameraUpdate);
    }
//map Zoom
    private void gotoLocationzoom(double lat,double lng,float zoom){

        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.animateCamera(cameraUpdate);
    }

    public void Search(View view) throws IOException {  //  IOException ?

        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();

   //PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP
        Geocoder geocoder = new Geocoder(this);

        List<Address> list =geocoder.getFromLocationName(location,1);
        Address address = list.get(0);
        String locality = address.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        gotoLocationzoom(lat,lng,15);
        LatLng latLng = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(latLng).title(location)); //logo

    }



}
