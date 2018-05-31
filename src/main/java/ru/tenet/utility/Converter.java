package ru.tenet.utility;

public class Converter {


    public static int mergeTwoByteToInt(short leftNumber, short rightNumber) {
        String left = Integer.toHexString(leftNumber);
        String right = Integer.toHexString(rightNumber);
        if (left.length() == 1) {
            left = "0" + left;
        }
        if (right.length() == 1) {
            right = "0" + right;
        }
        String str = "0x"+left+right;
        return Integer.decode(str);
    }

    public static double mergeTwoByteToDouble(short leftNumber, short rightNumber) {
        return Double.valueOf(leftNumber + "." + rightNumber);
    }

    public static double mergeOneByteToDouble(short number) {
        String s = Integer.toHexString(number);
        String leftNumber=s.substring(0,1);
        String rightNumber = s.substring(1);

        return Double.valueOf(leftNumber + "." + rightNumber);
    }

}
