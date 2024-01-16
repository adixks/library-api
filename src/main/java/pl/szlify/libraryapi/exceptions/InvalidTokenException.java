package pl.szlify.libraryapi.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid token ");
    }
}
