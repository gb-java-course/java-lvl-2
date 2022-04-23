package ru.gb.level2.lesson2;

public class MyArrayDataException extends RuntimeException {

    private static final String MESSAGE_FORMAT = "Array has wrong data in %d:%d element";

    public MyArrayDataException(int x, int y) {
        super(String.format(MESSAGE_FORMAT, x, y));
    }

    public MyArrayDataException(int x, int y, Throwable cause) {
        super(String.format(MESSAGE_FORMAT, x, y), cause);
    }
}
