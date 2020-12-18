package ru.spbau.javacourse.torrent.query;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by svloyso on 07.11.16.
 */
public abstract class Query {
    private static final Map<Byte, Class<? extends Query>> queries = new HashMap<>();
    static {
        queries.put((byte)1, ListQuery.class);
        queries.put((byte)2, UploadQuery.class);
        queries.put((byte)3, SourcesQuery.class);
        queries.put((byte)4, UpdateQuery.class);
    }
    public static Query recieveQuery(DataInputStream in, InetSocketAddress from) throws IOException {
        Byte b = in.readByte();
        Query q = null;
        if(!queries.containsKey(b)) {
            return null;
        }
        try {
            q = queries.get(b).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            assert false; // Should never happen
        }
        q.receive(in, from);
        return q;
    }

    abstract public void receive(DataInputStream in, InetSocketAddress from) throws IOException;
    abstract public void response(DataOutputStream out) throws IOException;
}
