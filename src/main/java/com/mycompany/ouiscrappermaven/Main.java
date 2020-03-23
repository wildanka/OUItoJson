/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ouiscrappermaven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author KAHFIGAH
 */
public class Main {

    private static String OUI_URL = "http://standards-oui.ieee.org/oui/oui.txt";

    public static void main(String[] args) {
        readOUI();
    }

    public static void readOUI() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        HashMap<String, OUI> ouiMap = new HashMap<>();

        try {
            URL ouiURL = new URL(OUI_URL);
            Scanner scanner = new Scanner(ouiURL.openStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("(hex)")) {
                    String[] splits = line.split("\\(hex\\)");
                    ouiMap.put(splits[0].trim(), new OUI(splits[1].trim()));
                }
            }
            objectMapper.writeValue(new File("oui.json"), ouiMap);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
