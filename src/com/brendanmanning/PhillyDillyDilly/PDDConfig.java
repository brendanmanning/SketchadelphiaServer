package com.brendanmanning.PhillyDillyDilly;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Created by Pharoah610 on 10/5/18.
 */
public class PDDConfig {

    private static PDDConfig ourInstance = new PDDConfig();
    public static PDDConfig getInstance() {
        return ourInstance;
    }
    private PDDConfig() {}

    //public File PhillyCrimeDataCsv = new File("/Users/Pharoah610/PDD/assets/PhillyCrimeDataIncidents.csv");
   //public File FullPhillyCrimeDataCsv = new File("/Users/Pharoah610/PDD/assets/FullPhillyCrimeDataIncidents.csv");
    //File MapViewTemplate = new File("/Users/Pharoah610/PDD/assets/MapViewTemple.html");
    //File MapViewOutputDirectory = new File("/Users/Pharoah610/Documents/PDDMaps/");

    public File PhillyCrimeDataCsv = new File("/root/PhillyCrimeDataIncidents.csv");
     public File FullPhillyCrimeDataCsv = new File("/root/FullPhillyCrimeDataIncidents.csv");

   File MapViewTemplate = new File("/root/MapViewTemple.html");
   File MapViewOutputDirectory = new File("/root/maps/");

    public SimpleDateFormat PhillyCrimeDataDateFormat = new SimpleDateFormat("y-MM-dd HH:mm:ss");

    int GRIDS_EW = 75;
    int GRIDS_NS = 150;

    Coordinates CityHallCoordinates = new Coordinates(39.9524, -75.1636);
}
