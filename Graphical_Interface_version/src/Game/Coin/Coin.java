package Game.Coin;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;

public class Coin {
    private int amount;

    private static Coin gInstance;

    private Coin() {}

    public static Coin getInstance() {
        if (gInstance == null) {
            gInstance = new Coin();
        }
        return gInstance;
    }

    public boolean addCoin(int amount) {
        this.amount += amount;
        return true;
    }

    public boolean minusCoin(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            return true;
        } else {
            //System.out.println("Out of Coins!");
            return false;
        }
        
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int x) {
        this.amount = x;
    }

    public void setAmountFromFile() {
        try {
            File file = new File("Graphical_Interface_version\\src\\Game\\Coin\\Coin.txt");
            Scanner reader = new Scanner(file);
            String line = reader.nextLine();
            int a = Integer.parseInt(line);
            this.amount = a;
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    public void saveAmountToFile() {
        try {
            File file = new File("Graphical_Interface_version\\src\\Game\\Coin\\Coin.txt");
            FileWriter writer = new FileWriter(file);
            writer.write("" + amount);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
}
