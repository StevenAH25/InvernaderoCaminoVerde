package com.example.invernaderocaminoverde;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Control extends AppCompatActivity {
    private TextView luz, humedad, temperatura;
    private Button salir, prueba;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        luz = (TextView) findViewById(R.id.luz);
        humedad = (TextView) findViewById(R.id.humedad);
        temperatura = (TextView) findViewById(R.id.temperatura);

        //actualizar = (Button)findViewById(R.id.buttonActualizar);
        salir = (Button) findViewById(R.id.buttonSalir);
        prueba = (Button) findViewById(R.id.buttonActualizar);


        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion(salir);
            }
        });

        /*actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {mostrarDatos(actualizar);
            }
        });*/

        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTemperature(prueba);
            }
        });

    }

    /*public void mostrarDatos(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int numero = (int)(Math.random()*10+1);
        String n = numero+"";
        DocumentReference docRef = db.collection("datos").document(n);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Datos dato = documentSnapshot.toObject(Datos.class);
                luz.setText(dato.getLuz()+"");
                humedad.setText(dato.getHumedad()+"");
                temperatura.setText(dato.getTemperatura()+"");
            }
        });

    }*/

    public void cerrarSesion(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void getTemperature(View view) {
        new WeatherTask(temperatura,humedad,luz).execute("Guayaquil");
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {
        private TextView temperatureTextView, humidity, light;

        public WeatherTask(TextView temperatureTextView, TextView humidity, TextView light) {
            this.temperatureTextView = temperatureTextView;
            this.humidity = humidity;
            this.light = light;
        }

        @Override
        protected String doInBackground(String... params) {
            String location = "Guayaquil";
            String appId = "40e6d3300512144fc4d15d6551844a28";
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+location+"&appid="+appId;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    return null;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject main = jsonObject.getJSONObject("main");
                    JSONObject clouds = jsonObject.getJSONObject("clouds");
                    double temp = main.getDouble("temp")-273;
                    int hum = main.getInt("humidity");
                    int lu = clouds.getInt("all");
                    temperatureTextView.setText(String.format("%.2f",temp));
                    humidity.setText(hum+""+"%");
                    light.setText(lu+""+"%");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}