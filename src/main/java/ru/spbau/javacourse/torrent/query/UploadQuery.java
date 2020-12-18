package ru.spbau.javacourse.torrent.query;

import ru.spbau.javacourse.torrent.tracker.TrackerData;
import ru.spbau.javacourse.torrent.tracker.TrackerFileInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;

/**
 * Created by svloyso on 07.11.16.
 */
public class UploadQuery extends Query {
    private String name;
    private long size;
    private InetSocketAddress from;
    @Override
    public void receive(DataInputStream in, InetSocketAddress from) throws IOException {
        name = in.readUTF();
        size = in.readLong();
        this.from = from;
    }

    @Override
    public void response(DataOutputStream out) throws IOException {
        int id = TrackerData.upload(new TrackerFileInfo(name, size), from);
        out.writeInt(id);
    }
}
