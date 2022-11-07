import java.io.*;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean signUp() {
        File file = new File(username + ".txt");

        try {
            if (!(file.createNewFile())) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(password);
            bw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login() {
        File file = new File(username + ".txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            return password.equals(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
