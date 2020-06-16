package com.example.evreka_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class MainActivity extends AppCompatActivity {

    Random random;
    private EditText username,password;
    private String uName,uPassword;
    private Button loginButton;
    Map<String, ContainerInfo> containers;
    int containerId;
    int temperature, fullnessRate;
    private String sensorId;
    double latitude, longitude;
    private FirebaseDatabase database;
    private DatabaseReference id, sensor, temp, fr, container, lat, lng;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignIdToVariables();

        auth = FirebaseAuth.getInstance();

        // NETWORK CONNECTION
        if (!isNetworkConnected(MainActivity.this)){
            showCustomDialog();
        } else {
            Log.i("INTERNET : ","You have internet connection");
        }

        auth.signOut();
        // generateRandomData();

    }

    private boolean isNetworkConnected(MainActivity login) {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeMobil = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((activeWifi.isConnected() && activeWifi != null ) && (activeMobil.isConnected() && activeMobil != null)) {
            return true;
        } else {
            return false;
        }
    }

    // Show Dialog If No Internet Conenction
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
    }

    // Signin function : FIREBASE
    public void signIn(String email, String password){

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this,OperatorActivity.class);
                    startActivity(intent);

                    Log.d("Login_Success", "Login is successfull");
                    FirebaseUser user = auth.getCurrentUser();
                    updateUI(user);

                    finish();
                } else {
                    Log.w("Login_Unsuccess", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);                }
            }
        });
    }

    // Change UI according to user data.
    public void  updateUI(FirebaseUser account){
        if(account != null){
            startActivity(new Intent(getApplicationContext(),OperatorActivity.class));
            finish();
        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
        }
    }

    void assignIdToVariables() {
        database = FirebaseDatabase.getInstance();
        id = database.getReference("id");
        container = id.child("containerId");
        sensor = container.child("sensorId");
        temp = container.child("temperature");
        fr = container.child("fullnessRate");
        lat = container.child("lat");
        lng = container.child("lng");

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);

        uName = username.getText().toString();
        uPassword = password.getText().toString();


        loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uName = username.getText().toString();
                uPassword = password.getText().toString();

                if (!uName.equals("") && !uPassword.equals("")){
                    signIn(uName,uPassword);
                }
            }
        });
    }

    // USED Only 1 time to add data in firebase 1000 times
    void generateRandomData() {

        containers = new HashMap<>();

        random = new Random();
        int min = 1000000;
        int max = 9999999;
        int minFR = 1, maxFR = 100, minT = -30, maxT = 50;

        for (int i = 0; i < 1000; i++) {
            containerId = ThreadLocalRandom.current().nextInt(min, max + 1);
            sensorId = UUID.randomUUID().toString();
            temperature = ThreadLocalRandom.current().nextInt(minT, maxT + 1);
            fullnessRate = ThreadLocalRandom.current().nextInt(minFR, maxFR + 1);
            double u = random.nextDouble();
            double v = random.nextDouble();
            latitude = Math.toDegrees(Math.acos(u * 2 - 1)) - 90;
            longitude = 360 * v - 180;
            id.setValue(containerId);

            container.setValue(containerId);

            sensor.setValue(sensorId);

            temp.setValue(temperature);

            fr.setValue(fullnessRate);

            lat.setValue(latitude);

            lng.setValue(longitude);


            containers.put(sensorId, new ContainerInfo(sensorId, temperature, fullnessRate, containerId + "", latitude, longitude));

        }
        id.setValue(containers);

    }
}