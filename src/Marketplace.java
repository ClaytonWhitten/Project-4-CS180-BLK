import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Marketplace {
    private ArrayList<Seller> allSellers;
    
    public Marketplace() {
        allSellers = new ArrayList<>();
        BufferedReader bfr = null;
        ArrayList<String> lines = new ArrayList<>();
        try {
            File f = new File("sellers.txt");
            FileReader fr = new FileReader(f);
            bfr = new BufferedReader(fr);
            String currentLine = bfr.readLine();
            while (currentLine != null) {
                lines.add(currentLine);
                currentLine = bfr.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            allSellers.add(new Seller(lines.get(i), "seller"));
        }
    }
}
