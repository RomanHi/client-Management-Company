package ru.tenet;


import java.util.Arrays;

public class Main {
    public static void main(String[] args)  {
        int test[] = {0x83, 0x13, 0x3f, 0xfc, 0x10, 0x10};
        char pars[] = new char[100];
        byte b[] = new byte[test.length];
        for (int i = 0; i < test.length; i++) {
            b[i] = (byte) test[i];
        }

//        System.out.println(new String(b, "Cp866"));
        System.out.println(new String(b));
        System.out.println(Byte.toUnsignedInt((byte) -12));
        System.out.println();
        System.out.println(Arrays.toString(b));
        System.out.println(Integer.toHexString(crc16(b)));

    }

    static int crc16(final byte[] buffer) {
        int crc = 0xFFFF;

        for (int j = 0; j < buffer.length; j++) {
            crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
            crc ^= (buffer[j] & 0xff);//byte to int, trunc sign
            crc ^= ((crc & 0xff) >> 4);
            crc ^= (crc << 12) & 0xffff;
            crc ^= ((crc & 0xFF) << 5) & 0xffff;
        }
        crc &= 0xffff;
        return crc;
    }

}
