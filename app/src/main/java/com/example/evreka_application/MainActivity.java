package com.example.evreka_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    Random random;
    Button loginButton;

    private FirebaseDatabase database;
    private DatabaseReference id,sensor,temp,fr,container;

    Map<String ,ContainerInfo> containers;

    int containerId;
    int temperature,fullnessRate;
    String sensorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignIdToVariables();
       // generateRandomData();
    }

    // USED Only 1 to add data in firebase 1000 times

    void generateRandomData(){

        containers = new HashMap<>();

        random = new Random();
        int min = 1000000;
        int max = 9999999;
        int minFR = 1, maxFR = 100, minT = -30, maxT = 50;

        for (int i = 0 ; i < 1000 ;  i++ ){
            containerId =  ThreadLocalRandom.current().nextInt(min, max + 1);
            sensorId = UUID.randomUUID().toString();
            temperature = ThreadLocalRandom.current().nextInt(minT, maxT + 1);
            fullnessRate = ThreadLocalRandom.current().nextInt(minFR, maxFR + 1);

            id.setValue(containerId);

            container.setValue(containerId);

            sensor.setValue(sensorId);

            temp.setValue(temperature);

            fr.setValue(fullnessRate);

            containers.put(sensorId, new ContainerInfo( sensorId, temperature, fullnessRate, containerId));

        }
        id.setValue(containers);

    }

    void assignIdToVariables(){
        database = FirebaseDatabase.getInstance();
        id = database.getReference("id");
        container = id.child("containerId");
        sensor = container.child("sensorId");
        temp = container.child("temperature");
        fr = container.child("fullnessRate");


        loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OperatorActivity.class);
                startActivity(intent);

            }
        });
    }
}