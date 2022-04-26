package ru.gb.level2.lesson2;

public class Lesson2 {

    private static final int DESIRED_ARRAY_COLUMN_SIZE = 4;
    private static final int DESIRED_ARRAY_ROW_SIZE = 4;

    public static void main(String[] args) {
        checkValid4x4Array();
        checkInvalidSize3x4Array();
        checkInvalidDataArray();

        //sum of array elements 136
        //Exception thrown [ru.gb.level2.lesson2.MySizeArrayException] with message [Array should have size 4x4, but wasn't]
        //Exception thrown [ru.gb.level2.lesson2.MyArrayDataException] with message [Array has wrong data in 2:1 element]
    }

    private static void checkValid4x4Array() {
        String[][] array = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        calcAndShowSumOfArrayElements(array);
    }

    private static void checkInvalidSize3x4Array() {
        String[][] array = {
                {"1", "2", "3"},
                {"5", "6", "7"},
                {"9", "10", "11"},
                {"13", "14", "15"}
        };

        calcAndShowSumOfArrayElements(array);
    }

    private static void checkInvalidDataArray() {
        String[][] array = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "a", "11", "12"},
                {"13", "14", "15", "16"}
        };

        calcAndShowSumOfArrayElements(array);
    }

    private static void calcAndShowSumOfArrayElements(String[][] array) {
        try {
            int sum = calcSumOfArrayElements(array);
            System.out.printf("sum of array elements %d%n", sum);
        } catch (MyArrayDataException | MySizeArrayException e) {
            System.out.printf("Exception thrown [%s] with message [%s]%n",
                    e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    private static int calcSumOfArrayElements(String[][] array) {
        checkArraySize(array);

        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i ,j);
                }
            }
        }

        return sum;
    }

    private static void checkArraySize(String[][] array) {
        MySizeArrayException exception =
                new MySizeArrayException(String.format("%dx%d", DESIRED_ARRAY_COLUMN_SIZE, DESIRED_ARRAY_ROW_SIZE));
        if (array.length != DESIRED_ARRAY_COLUMN_SIZE) {
            throw exception;
        } else {
            for (String[] strings : array) {
                if (strings.length != DESIRED_ARRAY_ROW_SIZE) {
                    throw exception;
                }
            }
        }
    }

}
