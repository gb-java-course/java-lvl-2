package ru.gb.level2.lesson2;

public class MySizeArrayException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Array should have size %s, but wasn't";

    public MySizeArrayException(String desiredSize) {
        super(String.format(MESSAGE_FORMAT, desiredSize));
    }

    public MySizeArrayException(String desiredSize, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, desiredSize), cause);
    }
}
