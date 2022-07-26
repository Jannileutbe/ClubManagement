package jle.exceptions;

public class ClubAlreadyExistsException extends Exception {
    public ClubAlreadyExistsException() {
        super("This club already exists!");
    }
}
