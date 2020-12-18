package ru.spbau.javacourse.torrent.tracker;

import ru.spbau.javacourse.torrent.common.Service;
import ru.spbau.javacourse.torrent.common.ServiceWorker;
import ru.spbau.javacourse.torrent.query.Query;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by svloyso on 07.11.16.
 */
public class ServerWorker extends ServiceWorker {
    private Socket socket;
    private static final Logger LOG = Logger.getLogger(Service.class.getName());

    @Override
    public void init(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try(DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()))
        {
            Query q = Query.recieveQuery(in, (InetSocketAddress)socket.getRemoteSocketAddress());
            if(q == null) throw new IOException("Invalid query");
            q.response(out);
        } catch (IOException e) {
            LOG.warning("Connection " + socket.getInetAddress() + " was closed by exception: " + e.getMessage());
        }
    }
}
