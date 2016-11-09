package ru.spbau.javacourse.torrent.client;

import ru.spbau.javacourse.torrent.common.ServiceWorker;
import ru.spbau.javacourse.torrent.tracker.TrackerMain;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by svloyso on 08.11.16.
 */
public class ClientWorker extends ServiceWorker {
    Socket clientSocket;
    InetSocketAddress tracker;
    public ClientWorker(String trackerAddr) {
        tracker = InetSocketAddress.createUnresolved(trackerAddr, TrackerMain.PORT);
    }
    @Override
    public void init(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

    }
}
