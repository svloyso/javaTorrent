package ru.spbau.javacourse.torrent.query;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by svloyso on 08.11.16.
 */
public class StatQuery extends Query {
    private int id;
    private InetSocketAddress from;
    @Override
    public void receive(DataInputStream in, InetSocketAddress from) throws IOException {
        this.from = from;
        id = in.readInt();
    }

    @Override
    public void response(DataOutputStream out) throws IOException {

    }
}
