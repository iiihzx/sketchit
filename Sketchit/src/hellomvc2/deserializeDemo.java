package hellomvc2;

import java.io.*;
import java.util.ArrayList;

public  class deserializeDemo {

    public static void des(String filename, ArrayList<shapedemo> aa) {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            shapedemo object1=null;
            while(true) {
                object1 = (shapedemo) in.readObject();
                if(object1==null){
                    break;
                }
                aa.add(object1);
            }
            in.close();
            file.close();

            System.out.println("Object has been deserialized ");
            System.out.println("name = " + object1.name);
        } catch (IOException i) {
           // i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

    }
}
