package ru.spbau.javacourse.torrent.tracker;

import ru.spbau.javacourse.torrent.common.Service;
import ru.spbau.javacourse.torrent.common.ServiceWorker;

/**
 * Created by svloyso on 08.11.16.
 */
public class TrackerMain {
    public static final int PORT = 8081;
    public static void main(String args[]) {
        ServiceWorker worker = new ServerWorker();
        Service service = new Service();
        service.start(PORT, worker);
    }
}
