package ru.spbau.javacourse.torrent.query;

import ru.spbau.javacourse.torrent.tracker.TrackerData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;

/**
 * Created by svloyso on 07.11.16.
 */
public class UpdateQuery extends Query {
    short port;
    Set<Integer> ids;
    InetSocketAddress from;
    @Override
    public void receive(DataInputStream in, InetSocketAddress from) throws IOException {
        this.from = from;
        int count;
        port = in.readShort();
        count = in.readInt();
        while(count-- > 0) {
            ids.add(in.readInt());
        }
    }

    @Override
    public void response(DataOutputStream out) throws IOException {
        boolean res = ids.stream().map(id -> TrackerData.update(from, id)).reduce((b1, b2) -> b1 && b2).orElse(false);
        out.writeBoolean(res);
    }
}
