package android.podonin.com.timemanager.model;

/**
 * Created by User on 14.03.2018.
 */

public enum Efficiency {
    zero, one, two, three, four, five, six, seven, eight, nine, ten;

    public static Efficiency getEfficiency(int efficiency){
        switch (efficiency){
            case 0:
                return zero;
            case 1:
                return one;
            case 2:
                return two;
            case 3:
                return three;
            case 4:
                return four;
            case 5:
                return five;
            case 6:
                return six;
            case 7:
                return seven;
            case 8:
                return eight;
            case 9:
                return nine;
            default:
                    return ten;
        }
    }
}
