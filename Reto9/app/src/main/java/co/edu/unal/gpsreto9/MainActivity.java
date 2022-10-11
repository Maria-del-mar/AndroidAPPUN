package co.edu.unal.gpsreto9;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import co.edu.unal.gpsreto9.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code=101;
    private double lat,lng;
    public int rad=0;
    Button school,bank,hosp,res;
    TextView radius1,radius2,radius3,radius4,radius5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        school=findViewById(R.id.school);
        bank=findViewById(R.id.bank);
        hosp=findViewById(R.id.hospital);
        res=findViewById(R.id.rest);
        radius1 =findViewById(R.id.rad1);
        radius2 =findViewById(R.id.rad2);
        radius3 =findViewById(R.id.rad3);
        radius4 =findViewById(R.id.rad4);
        radius5 =findViewById(R.id.rad5);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat+","+lng);
                stringBuilder.append("&radius="+rad);
                stringBuilder.append("&type=school");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyBHv2CqqrAfYpdVDLrfoxbZdDPN2g7qgPQ");

                String url = stringBuilder.toString();
                Object dataFetch[]=new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;

                co.edu.unal.gpsreto9.FetchData fetchData = new co.edu.unal.gpsreto9.FetchData();
                fetchData.execute(dataFetch);
            }
        });


        hosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat+","+lng);
                stringBuilder.append("&radius="+rad);
                stringBuilder.append("&type=hospital");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyBHv2CqqrAfYpdVDLrfoxbZdDPN2g7qgPQ");

                String url = stringBuilder.toString();
                Object dataFetch[]=new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;

                co.edu.unal.gpsreto9.FetchData fetchData = new co.edu.unal.gpsreto9.FetchData();
                fetchData.execute(dataFetch);
            }
        });

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat+","+lng);
                stringBuilder.append("&radius="+rad);
                stringBuilder.append("&type=restaurant");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyBHv2CqqrAfYpdVDLrfoxbZdDPN2g7qgPQ");

                String url = stringBuilder.toString();
                Object dataFetch[]=new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;

                co.edu.unal.gpsreto9.FetchData fetchData = new co.edu.unal.gpsreto9.FetchData();
                fetchData.execute(dataFetch);
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat+","+lng);
                stringBuilder.append("&radius="+rad);
                stringBuilder.append("&type=bank");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=AIzaSyBHv2CqqrAfYpdVDLrfoxbZdDPN2g7qgPQ");

                String url = stringBuilder.toString();
                Object dataFetch[]=new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;

                co.edu.unal.gpsreto9.FetchData fetchData = new co.edu.unal.gpsreto9.FetchData();
                fetchData.execute(dataFetch);
            }
        });

        radius1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rad=200;
            }
        });
        radius2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rad=500;
            }
        });
        radius3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rad=1000;
            }
        });
        radius4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rad=5000;
            }
        });
        radius5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rad=10000;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        getCurrentLocation();
    }

    private void getCurrentLocation(){

        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback= new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Toast.makeText(getApplicationContext(),"La ubicación actual es="+locationResult
                ,Toast.LENGTH_LONG ).show();

                if(locationResult==null){
                    Toast.makeText(getApplicationContext(),"Ubicación actual nula"
                            ,Toast.LENGTH_LONG ).show();
                    return;
                }

                for(Location location:locationResult.getLocations()){

                if(location!=null){

                    Toast.makeText(getApplicationContext(),"Ubicación actual es="+location.getLongitude()
                            ,Toast.LENGTH_LONG ).show();

                }

                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);

        Task<Location> task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){
                    lat=location.getLatitude();
                    lng=location.getLongitude();

                    LatLng latLng=new LatLng(lat,lng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Estas aquí"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                }
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (Request_code){
            case Request_code:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    getCurrentLocation();
                }
        }
    }
}