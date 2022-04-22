package ru.gb.level2.lesson1;

public class Lesson1 {

    public static void main(String[] args) {
        TrackElement[] trackElements = {
            new RunTrack(100),
            new Wall(10),
            new RunTrack(200),
            new Wall(20),
            new RunTrack(300),
            new Wall(30)
        };

        Movable[] participants = {
            new Cat("SweetPaws", 30, 300),
            new Human("John", 20, 200),
            new Robot("C3PO", 10, 50)
        };

        for (TrackElement trackElement : trackElements) {
            for (Movable participant : participants) {
                if (trackElement instanceof RunTrack) {
                    participant.run((RunTrack) trackElement);
                }else if (trackElement instanceof Wall) {
                    participant.jump((Wall) trackElement);
                }
            }
        }
    }
}
