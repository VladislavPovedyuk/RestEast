package dk.telenor.entity;

/**
 * @author Vladyslav Povediuk.
 */
public class SecurityToken {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SecurityToken(){}

    public SecurityToken(String token){
        this.token = token;
    }
}
