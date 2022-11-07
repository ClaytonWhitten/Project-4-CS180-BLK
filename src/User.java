import java.io.*;

public class User {

    private String username;
    private String password;

    private String type;

    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

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
            bw.write(password + ";" + type);
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
        String firstLine = null;
        try {
            br = new BufferedReader(new FileReader(file));
            firstLine = br.readLine();
            return password.equals(firstLine.substring(0, firstLine.indexOf(";")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                type = firstLine.substring(firstLine.indexOf(";") + 1);
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
