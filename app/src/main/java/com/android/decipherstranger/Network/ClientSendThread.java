package com.android.decipherstranger.Network;

import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Feng on 2015/04/11.
 */
public class ClientSendThread {

    private Socket socketServer;
    private JSONObject strToSend;

    public synchronized void start(Socket socket, JSONObject jsonSend) {
        this.socketServer = socket;
        this.strToSend = jsonSend;

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socketServer.getOutputStream());
            outputStreamWriter.write(strToSend + "\n");
            outputStreamWriter.flush();
            //System.out.println(strToSend);
            Log.d("mStrToSend = " + strToSend, "发送啦");
        } catch (Exception e) {
            Log.d("cannot send to ", "发送失败了");
        }
    }
}
