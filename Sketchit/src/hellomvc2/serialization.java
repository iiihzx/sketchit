package hellomvc2;

import java.io.*;
import java.util.ArrayList;

public class serialization {

    public static void ser(String name, ArrayList<shapedemo> shapes) {
        try {
            FileOutputStream file = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            for(shapedemo i:shapes) {
                out.writeObject(i);
                System.out.println("Deserialized Employee...");
                System.out.println("Name: " + i.name);
            }

            out.close();
            file.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        }


    }
}
