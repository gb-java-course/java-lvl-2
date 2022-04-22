package ru.gb.level2.lesson1;

public class Wall extends TrackElement {

    private final int height;

    public Wall(int height) {
        this.height = height;
    }

    public void jump(int height) {
        setPassed(this.height <= height);
    }

    @Override
    public String toString() {
        return "Wall{" +
                "height=" + height +
                '}';
    }
}
