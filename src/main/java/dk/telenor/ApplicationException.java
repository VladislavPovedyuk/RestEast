package dk.telenor;

import java.io.Serializable;

/**
 * @author Vladyslav Povediuk.
 */
public class ApplicationException extends Exception implements Serializable {
    public static final long serialVersionUID = 1L;

    public ApplicationException() {}

    public ApplicationException(String msg) {
        super(msg);
    }
}
