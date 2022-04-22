package ru.gb.level2.lesson1;

public class RunTrack extends TrackElement {

    private final int length;

    public RunTrack(int length) {
        this.length = length;
    }

    public void run(int length) {
        setPassed(this.length <= length);
    }

    @Override
    public String toString() {
        return "RunTrack{" +
                "length=" + length +
                '}';
    }
}
