package ru.gb.level2.lesson1;

public class Cat implements Movable {

    private final String name;
    private final int jumpHeight;
    private final int runLength;

    private boolean succeed = true;

    public Cat(String name, int jumpHeight, int runLength) {
        this.name = name;
        this.jumpHeight = jumpHeight;
        this.runLength = runLength;
    }

    @Override
    public void run(RunTrack track) {
        if (!succeed) {
            return;
        }

        track.run(runLength);
        succeed = track.isPassed();

        System.out.printf("%s %s the %s\n",
                name, succeed ? "ran through" : "couldn't ran through", track);
    }

    @Override
    public void jump(Wall wall) {
        if (!succeed) {
            return;
        }

        wall.jump(jumpHeight);
        succeed = wall.isPassed();

        System.out.printf("%s %s the %s\n",
                name, succeed ? "jumped over" : "couldn't jump over", wall);
    }
}
