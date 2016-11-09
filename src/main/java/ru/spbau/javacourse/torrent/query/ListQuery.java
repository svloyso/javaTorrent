package ru.spbau.javacourse.torrent.query;

import ru.spbau.javacourse.torrent.tracker.TrackerData;
import ru.spbau.javacourse.torrent.tracker.TrackerFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by svloyso on 07.11.16.
 */
public class ListQuery extends Query {

    @Override
    public void receive(DataInputStream in, InetSocketAddress from) throws IOException {
       /* EMPTY */
    }

    @Override
    public void response(final DataOutputStream out) throws IOException {
        List<TrackerFile> files = TrackerData.getFiles();
        out.writeInt(files.size());
        final IOException[] e = {null};
        files.stream().forEach(f -> {
            try {
                out.writeInt(f.id);
                out.writeUTF(f.info.name);
                out.writeLong(f.info.size);
            } catch (IOException e1) {
                e[0] = e1;
            }
        });
        if(e[0] != null) throw e[0];
    }
}
