package com.brendanmanning.CrimeStatsParser;

import com.brendanmanning.PhillyDillyDilly.DateTime;
import com.brendanmanning.PhillyDillyDilly.PDDConfig;

import javax.swing.text.DateFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Data File: ");
        String location = scanner.nextLine();

        System.out.println(location);
        int i = 0;
        String[] headerNumbers; String headers; String sampleLine;
        String tableFormat = "|";
        try (BufferedReader br = new BufferedReader(new FileReader( new File(location)))) {
            for (String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
                if(i>3)
                    break;
                System.out.println(line.split(",").length);
                i++;
                /*if(i > 3)
                    break;
                String[] components = line.split(",");
                if(i == 0) {
                    for (int j = 0; j < components.length; j++) {
                        System.out.printf("| %30s ", (j + 1) + "");
                    }
                    System.out.println();
                }
                for(int j = 0; j < components.length; j++)
                    System.out.printf("| %30s ", components[j]);
                System.out.println();
                i++;*/
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        System.out.print("Latitude: ");
        double lat = Double.parseDouble(scanner.nextLine());

        System.out.println("Longitude: ");
        double lon = Double.parseDouble(scanner.nextLine());

        System.out.print("Date: ");
        String date = scanner.nextLine();
        System.out.print("Date format: ");
        String format = scanner.nextLine();

        try {
            DateTime dateTime = new DateTime(new SimpleDateFormat(format).parse(date));
        } catch (ParseException pe) {
            System.out.println("Error parsing the date");
        }
    }
}
