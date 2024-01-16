package pl.szlify.libraryapi.exceptions;

import java.time.LocalTime;

public class BookAdditionTimeExceededException extends RuntimeException {
    public BookAdditionTimeExceededException(LocalTime deadline) {
        super("Books cannot be added after the hour " + deadline);
    }
}
