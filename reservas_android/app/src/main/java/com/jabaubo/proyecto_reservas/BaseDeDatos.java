package com.jabaubo.proyecto_reservas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Calendar;

public class BaseDeDatos extends SQLiteOpenHelper {
    SQLiteDatabase dbWrite;
    private static final String DATABASE_NAME = "datos123.db";
    private static final int DATABASE_VERSION = 1;

    public BaseDeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablaRestaurante = " CREATE TABLE restaurante (" +
                "nombre TEXT PRIMARY KEY," +
                "telefono1 INTEGER," +
                "telefono2 INTEGER," +
                "direccion TEXT)";
        String tablaHorario = " CREATE TABLE horario (" +
                "dia TEXT PRIMARY KEY," +
                "abierto INTEGER," +
                "hora_inicio_m TEXT," +
                "hora_fin_m TEXT," +
                "hora_inicio_t TEXT," +
                "hora_fin_t TEXT)";
        String tablaVacaciones = " CREATE TABLE vacaciones (" +
                "inicio TEXT PRIMARY KEY ," +
                "fin TEXT )";
        db.execSQL(tablaRestaurante);
        System.out.println("tabla1 creada");
        db.execSQL(tablaHorario);
        System.out.println("tabla2 creada");
        db.execSQL(tablaVacaciones);
        System.out.println("tabla3 creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes definir cómo actualizar la base de datos en caso de cambios en el esquema
        onCreate(db);
    }
    public void comprobarDatos(){
        String[] vacaciones = leerVacaciones();
        if (vacaciones[0] == null || vacaciones[0].equals("null")){
            System.out.println("Cargando vacaciones por defecto");
            cargarVacacionesPrueba();
        }
        String[] restaurante = leerRestaurante();
        if (restaurante[0] == null || restaurante[0].equals("null")){
            System.out.println("Cargando restaurante por defecto");
            cargarRestaurantePrueba();
        }
        String[][] horario = leerHorario();
        if (horario[0][0] == null || horario[0][0].equals("null")){
            System.out.println("Cargando horario por defecto");
            cargarHorarioPrueba();
        }
    }

    public String[] leerRestaurante() {
        dbWrite = this.getWritableDatabase();
        String[] col = {"nombre", "telefono1", "telefono2", "direccion"};
        Cursor cursor = dbWrite.query("restaurante", col, null, null, null, null, null);
        String[] resultados = new String[4];
        while (cursor.moveToNext()) {
            resultados[0] = cursor.getString(0);
            resultados[1] = String.valueOf(cursor.getInt(1));
            resultados[2] = String.valueOf(cursor.getInt(2));
            resultados[3] = cursor.getString(3);
            // Hacer algo con los datos recuperados
        }
        dbWrite.close();
        return resultados;
    }
    public String[][] leerHorario(){
        dbWrite = this.getWritableDatabase();
        String[] col = {"dia", "abierto", "hora_inicio_m", "hora_fin_m","hora_inicio_t", "hora_fin_t"};
        Cursor cursor = dbWrite.query("horario", col, null, null, null, null, null);
        String[][] resultados = new String[7][6];
        int contador = 0;
        while (cursor.moveToNext()) {
            resultados[contador][0]=cursor.getString(0);
            resultados[contador][1]=String.valueOf(cursor.getInt(1));
            resultados[contador][2]=cursor.getString(2);
            resultados[contador][3]=cursor.getString(3);
            resultados[contador][4]=cursor.getString(4);
            resultados[contador][5]=cursor.getString(5);
            contador++;
        }
        dbWrite.close();
        return resultados;
    }
    public String[] leerVacaciones(){
        dbWrite = this.getWritableDatabase();
        String[] col = {"inicio", "fin"};
        Cursor cursor = dbWrite.query("vacaciones", col, null, null, null, null, null);
        String[] resultados = new String[2];
        while (cursor.moveToNext()){
            resultados[0] = cursor.getString(0);
            resultados[1] = cursor.getString(1);
        }
        dbWrite.close();
        return resultados;
    }

    public void cargarVacacionesPrueba(){
        dbWrite = this.getWritableDatabase();
        dbWrite.delete("vacaciones", null, null);
        ContentValues cv = new ContentValues();
        cv.put("inicio","01/10/2024");
        cv.put("fin","01/10/2024");
        System.out.println("AAAAAAAAAAAAAAA");
        dbWrite.insert("vacaciones", null, cv);
        String[] col = {"inicio", "fin"};
        Cursor cursor = dbWrite.query("vacaciones", col, null, null, null, null, null);
        while (cursor.moveToNext()) {
            System.out.printf("%s ; %s \n",
                    cursor.getString(0),
                    cursor.getString(1));
        }
        dbWrite.close();
    }
    public void cargarHorarioPrueba(){
        dbWrite = this.getWritableDatabase();
        dbWrite.delete("horario", null, null);
        ContentValues cv;

        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        for (int i  = 0 ; i < dias.length ; i++){
            cv = new ContentValues();
            cv.put("dia", dias[i]);
            if (i%2==0){
                cv.put("abierto", 1);
            }
            else {
                cv.put("abierto", 0);
            }
            cv.put("hora_inicio_m", "0"+(i+1)+":00");
            cv.put("hora_fin_m", "0"+(i+1)+":10");
            cv.put("hora_inicio_t", (i+12)+":00");
            cv.put("hora_fin_t",(i+12)+":10");
            dbWrite.insert("horario", null, cv);
            System.out.println("consulta no boom");
        }
        String[] col = {"dia", "abierto", "hora_inicio_m", "hora_fin_m","hora_inicio_t", "hora_fin_t"};
        System.out.println("Aqui hace boom");
        Cursor cursor = dbWrite.query("horario", col, null, null, null, null, null);
        System.out.println(cursor.getCount());
        dbWrite.close();
    }
    public void cargarRestaurantePrueba(){
        dbWrite = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", "Reservante");
        cv.put("telefono1", "123456789");
        cv.put("telefono2", "123456789");
        cv.put("direccion", "España");
        dbWrite.delete("restaurante", null, null);
        dbWrite.insert("restaurante", null, cv);
        dbWrite.close();
    }

    public void guardarRestaurante(String nombre, int tlf1, int tlf2, String direccion) {
        dbWrite = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("telefono1", tlf1);
        cv.put("telefono2", tlf2);
        cv.put("direccion", direccion);
        System.out.println(nombre);
        dbWrite.delete("restaurante", null, null);
        dbWrite.insert("restaurante", null, cv);
        dbWrite.close();
    }
    public void guardarHorario(String[][] horario){
        dbWrite = this.getWritableDatabase();
        dbWrite.delete("horario", null, null);
        ContentValues cv;
        for (int i  = 0 ; i < horario.length ; i++){
            cv = new ContentValues();
            cv.put("dia", horario[i][0]);
            cv.put("abierto", Integer.valueOf(horario[i][1]));
            cv.put("hora_inicio_m", horario[i][2]);
            cv.put("hora_fin_m", horario[i][3]);
            cv.put("hora_inicio_t", horario[i][4]);
            cv.put("hora_fin_t",horario[i][5]);
            dbWrite.insert("horario", null, cv);
            System.out.println("consulta no boom");
        }
        dbWrite.close();
    }
    public void guardarVacaciones(String[] fechas){
        dbWrite = this.getWritableDatabase();
        dbWrite.delete("vacaciones", null, null);
        ContentValues cv = new ContentValues();
        cv.put("inicio",fechas[0]);
        cv.put("fin",fechas[1]);
        dbWrite.insert("vacaciones", null, cv);

        String[] col = {"inicio", "fin"};
        Cursor cursor = dbWrite.query("vacaciones", col, null, null, null, null, null);
        System.out.println(cursor.getCount());
        while (cursor.moveToNext()) {
            System.out.printf("%s ; %s \n",
                    cursor.getString(0),
                    cursor.getString(1));
        }
        dbWrite.close();
    }
    public void sincronizarAPI(){
        final JSONObject[] jsonCompleto = new JSONObject[1];
        try {
            Runnable runnable= new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        System.out.println("sututututuut");
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getall");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();
                        //Ver si la respuesta es correcta
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            // Si es correcta la leemos
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            jsonCompleto[0] = new JSONObject(response.toString());
                            connection.disconnect();
                        } else {
                            connection.disconnect();
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (ProtocolException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
        System.out.println("####################### VACACIONES #######################");
        String[] vacacionesBBDD = leerVacaciones();
        System.out.println("INICIO BBDD: " +vacacionesBBDD[0]);
        System.out.println("FIN BBDD: " + vacacionesBBDD[1]);
        System.out.println("INICIO API: " +jsonCompleto[0].getJSONArray("datos_vacaciones").getJSONObject(0).getString("inicio"));
        System.out.println("FIN API: " + jsonCompleto[0].getJSONArray("datos_vacaciones").getJSONObject(0).getString("fin"));
        System.out.println("####################### RESTAURANTE #######################");
        String[] datosRestauranteBBDD = leerRestaurante();
        System.out.println("NOMBRE: " + datosRestauranteBBDD[0]);
        System.out.println("TLF1: " + datosRestauranteBBDD[1]);
        System.out.println("TLF2: " + datosRestauranteBBDD[2]);
        System.out.println("DIRECCION: " + datosRestauranteBBDD[3]);
        System.out.println("####################### HORARIO #######################");
        String[][] horarioBBDD = leerHorario();
        for (int i = 0 ; i < horarioBBDD.length ; i++){
            System.out.println("DÍA: " + horarioBBDD[i][0].substring(0,4) +
                    "\t\tCERRADO: " + horarioBBDD[i][1]+
                    " INICIO M: " + horarioBBDD[i][2]+
                    " FIN M: " + horarioBBDD[i][3]+
                    " INICIO T: " + horarioBBDD[i][4]+
                    " FIN T: " + horarioBBDD[i][5]);
        }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
