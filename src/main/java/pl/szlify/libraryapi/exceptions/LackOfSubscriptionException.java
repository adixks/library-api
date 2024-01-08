package pl.szlify.libraryapi.exceptions;

public class LackOfSubscriptionException extends RuntimeException {
    public LackOfSubscriptionException() {
        super("The Subscription with the specified id does not exist");
    }
}
