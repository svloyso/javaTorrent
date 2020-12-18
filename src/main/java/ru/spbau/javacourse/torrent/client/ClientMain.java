package ru.spbau.javacourse.torrent.client;

import ru.spbau.javacourse.torrent.common.Service;

/**
 * Created by svloyso on 08.11.16.
 */
public class ClientMain {
    public static void main(String args[]) {
        if(args.length < 1) {
            System.out.println("Needs tracker's address");
        }
        String trackerAddr = args[0];
        int port = 8081;
        if(args.length > 1) {
            port = Integer.valueOf(args[1]);
        }
        ClientWorker worker = new ClientWorker(trackerAddr);
        Service service = new Service();
        service.start(port, worker);
    }
}
