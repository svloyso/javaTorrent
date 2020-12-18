package ru.spbau.javacourse.torrent.tracker;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by svloyso on 07.11.16.
 */
public class TrackerData {
    static private final Map<Integer, TrackerFile> data = new HashMap<>();
    static int id_counter = 0;

    public static synchronized List<TrackerFile> getFiles() {
        return data.values().stream().collect(Collectors.toList());
    }

    public static synchronized int upload(TrackerFileInfo info, InetSocketAddress from) {
        TrackerFile f = new TrackerFile();
        f.id = id_counter++;
        f.info = info;
        f.seeders = new HashMap<>();
        f.seeders.put(from, LocalDateTime.now());
        data.put(f.id, f);
        return f.id;
    }

    public static synchronized Set<InetSocketAddress> sources(int id) {
        if(!data.containsKey(id)) {
            return null;
        }
        return data.get(id).seeders.entrySet().stream()
                .filter(e -> e.getValue().plus(Duration.ofMinutes(5)).isAfter(LocalDateTime.now()))
                .map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static synchronized boolean update(InetSocketAddress from, int id) {
        if(!data.containsKey(id)) {
            return false;
        }
        data.get(id).seeders.put(from, LocalDateTime.now());
        return true;
    }
}
