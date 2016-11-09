package ru.spbau.javacourse.torrent.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class Service {
    private static final Logger LOG = Logger.getLogger(Service.class.getName());

    public class MasterThread implements Runnable {
        CountDownLatch start;
        AtomicBoolean stopFlag;
        ServerSocket serverSocket;
        ServiceWorker worker;
        public MasterThread(ServerSocket serverSocket, CountDownLatch start, AtomicBoolean stopFlag, ServiceWorker worker) {
            this.start = start;
            this.stopFlag = stopFlag;
            this.serverSocket = serverSocket;
            this.worker = worker;
        }
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                return;
            }
            LOG.info("Starting service...");
            while (!stopFlag.get()) {
                LOG.info("Waiting someone to connects");
                try(Socket clientSocket = serverSocket.accept()) {
                    worker.init(clientSocket);
                    Thread th = new Thread(worker);
                    th.setDaemon(true);
                    th.start();
                } catch (IOException e) {
                    LOG.warning("Lost Connection");
                    if(serverSocket.isClosed()) {
                        LOG.info("Stopping service...");
                        return;
                    }
                }
            }
        }
    }

    public void start(int port, ServiceWorker worker) {
        CountDownLatch start = new CountDownLatch(1);
        AtomicBoolean stopFlag = new AtomicBoolean(false);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            Thread masterThread = new Thread(new MasterThread(serverSocket, start, stopFlag, worker));
            masterThread.start();
            while(!stopFlag.get()) {
                String cmd = console.readLine();
                if(cmd.equalsIgnoreCase("start")) {
                    start.countDown();
                } else if(cmd.equalsIgnoreCase("stop")) {
                    stopFlag.set(true);
                } else {
                    System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
