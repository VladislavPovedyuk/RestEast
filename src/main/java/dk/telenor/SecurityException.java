package dk.telenor;


import java.io.Serializable;

/**
 * @author Vladyslav Povediuk.
 */

public class SecurityException extends ApplicationException implements Serializable {
    public static final long serialVersionUID = 1L;

    public SecurityException() {}

    public SecurityException(String msg) {
        super(msg);
    }
}
