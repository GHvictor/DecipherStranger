package com.android.decipherstranger.Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Feng on 2015/04/11.
 */
public class NetConnect extends Thread {

    /*  information about Server, ip address and portal number  */
//    private String HostIp = "192.168.32.128";
      private String HostIp = "10.33.0.205";
    private int HostPort = 8283;

    /*  socket  */
    private Socket conSocket = null;

    private boolean connectedAlready = false;

    public void run() {
        try {
            sleep(100); // to wait for the connection to be stable
        } catch (Exception e) {
        }
        try {
            // initialization and establish connection
            InetAddress serverIp = InetAddress.getByName(HostIp);
            InetSocketAddress addr = new InetSocketAddress(serverIp, HostPort);

            conSocket = new Socket();
            conSocket.connect(addr, 2000);

            if (conSocket.isConnected()) {
                connectedAlready = true;
            } else {
                connectedAlready = false;
            }
        } catch (IOException e) {
            System.out.println("error occured");
        }
    }

    public Socket getSocket() {
        return conSocket;
    }

    public boolean connectedOrNot() {
        return connectedAlready;
    }
}