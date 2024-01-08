package pl.szlify.libraryapi.exceptions;

public class LackOfClientException extends RuntimeException {
    public LackOfClientException() {
        super("The Client with the specified id does not exist");
    }
}
