package api.dto;

public class Credentials {
    public String email;
    public String password;

    public Credentials() {}

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
