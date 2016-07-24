package com.bigspawn.network;

import com.bigspawn.app_interface.ServerFrame;

import java.io.*;
import java.net.Socket;

/**
 * Created by bigsp on 24.07.2016.
 */
public class ServerThread extends Thread {
    private int ID = -1;
    private String name;
    private Socket socket;
    private SocketServer socketServer;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ServerFrame serverFrame;
    private DataInputStream dateInputStream;
    private DataOutputStream dataOutputStream;

    public ServerThread(Socket socket, SocketServer socketServer) {
        super();
        this.socket = socket;
        this.socketServer = socketServer;
        this.ID = socket.getPort();
        this.serverFrame = socketServer.getServerFrame();
    }

    @Override
    public void run() {

    }

    public void openConnection() {

    }

    public void closeConnection() {

    }

    public void sendMessage(Message message) {

    }
}
