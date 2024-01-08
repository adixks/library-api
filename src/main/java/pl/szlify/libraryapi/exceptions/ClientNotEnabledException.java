package pl.szlify.libraryapi.exceptions;

public class ClientNotEnabledException extends RuntimeException {
    public ClientNotEnabledException() {
        super("Client is not enabled");
    }
}
