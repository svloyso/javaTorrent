package ru.spbau.javacourse.torrent.tracker;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.Map;

public class TrackerFile {
    public int id;
    public TrackerFileInfo info;
    public Map<InetSocketAddress, LocalDateTime> seeders;
}
