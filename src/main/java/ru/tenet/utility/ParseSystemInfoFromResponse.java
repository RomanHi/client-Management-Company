package ru.tenet.utility;

import ru.tenet.model.Tv7SensorInfo;

public class ParseSystemInfoFromResponse {

    public static Tv7SensorInfo tv7Sensor(int[] response){
        if (response.length>19||response[2]!=0x0E)
            throw new IllegalStateException("unexpected long response");
        if (response[1] == 0x83) {
            throw new IllegalStateException("error response");
        }
        return null;
    }

}
