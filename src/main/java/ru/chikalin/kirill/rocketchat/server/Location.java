package ru.chikalin.kirill.rocketchat.server;

/**
 * Местоположение
 *
 * Date: 25/09/15
 *
 * @author Kirill Chikalin
 */
public class Location {
    private float longitude;
    private float latitude;

    public Location(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
