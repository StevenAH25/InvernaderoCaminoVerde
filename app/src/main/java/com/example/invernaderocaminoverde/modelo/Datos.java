package com.example.invernaderocaminoverde.modelo;

public class Datos {
    private int luz;
    private int humedad;
    private int temperatura;
    public static int numero = 1;

    public Datos(){

    }

    public Datos(int luz, int humedad, int temperatura) {
        this.luz = luz;
        this.humedad = humedad;
        this.temperatura = temperatura;
    }

    public int getLuz() {
        return luz;
    }

    public int getHumedad() {
        return humedad;
    }

    public int getTemperatura() {
        return temperatura;
    }


}
