package ru.spbau.javacourse.torrent.query;

import ru.spbau.javacourse.torrent.tracker.TrackerData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;

/**
 * Created by svloyso on 07.11.16.
 */
public class SourcesQuery extends Query {
    private int id;
    InetSocketAddress from;
    @Override
    public void receive(DataInputStream in, InetSocketAddress from) throws IOException {
        id = in.readInt();
        this.from = from;
    }

    @Override
    public void response(DataOutputStream out) throws IOException {
        Set<InetSocketAddress> sources = TrackerData.sources(id);
        if(sources == null) {
            out.writeInt(0);
            return;
        }
        out.writeInt(sources.size());
        IOException[] e = { null };
        sources.stream().forEach(ee -> {
            try {
                out.write(ee.getAddress().getAddress());
                out.writeShort(ee.getPort());
            } catch (IOException e1) {
                e[0] = e1;
            }
        });

        if(e[0] != null) throw e[0];
    }
}
