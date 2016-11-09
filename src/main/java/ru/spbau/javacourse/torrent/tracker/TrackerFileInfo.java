package ru.spbau.javacourse.torrent.tracker;

/**
 * Created by svloyso on 07.11.16.
 */
public class TrackerFileInfo {
    public String name;
    public long size;
    public TrackerFileInfo(String name, long size) {
        this.name = name;
        this.size = size;
    }
}
