package ru.spbau.javacourse.torrent.common;

import java.net.Socket;

/**
 * Created by svloyso on 08.11.16.
 */
public abstract class ServiceWorker implements Runnable {
    abstract public void init(Socket clientSocket);
}
