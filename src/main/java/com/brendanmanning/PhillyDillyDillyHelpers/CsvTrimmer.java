package com.brendanmanning.PhillyDillyDillyHelpers;

import com.brendanmanning.PhillyDillyDilly.DateTime;
import com.brendanmanning.PhillyDillyDilly.Incident;
import com.brendanmanning.PhillyDillyDilly.MalformedIncidentException;
import com.brendanmanning.PhillyDillyDilly.PDDConfig;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * PDD
 * Copyright 2018 Brendan Manning. All rights reserved.
 */
public class CsvTrimmer {

    File inputFile = null;
    File outputFile = null;

    public CsvTrimmer(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void startingAt(int year) {

        try {
            if (!this.outputFile.exists()) {
                this.outputFile.createNewFile();
            }
        } catch (IOException ioe) {
            System.exit(-1);
        }
        try (BufferedReader br = new BufferedReader(new FileReader( PDDConfig.getInstance().FullPhillyCrimeDataCsv ))) {
            PrintWriter file = new PrintWriter(new FileWriter(PDDConfig.getInstance().PhillyCrimeDataCsv));
            for(String line; (line = br.readLine()) != null; ) {
                try {
                    if(new Incident(line).getDate().year() >= year) {
                        file.println(line);
                    }
                } catch (MalformedIncidentException mie) {
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
