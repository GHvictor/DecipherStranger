package com.android.decipherstranger.Network;

import android.content.Context;

import com.android.decipherstranger.activity.Base.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

/**
 * Created by Feng on 2015/04/11.
 */
public class NetworkService {

    private static NetworkService instanceServer;
    private static String jsonArray[] = new String[50];

    public static NetworkService getInstance() {
        if (instanceServer == null) {
            instanceServer = new NetworkService();
        }
        return instanceServer;
    }

    private boolean isConnectedServer;
    private NetConnect serNetCon;
    private ClientListenThread serListenThread;
    private ClientSendThread serSendThread;
    private Socket serSocket;
    private Context serContext;
    private MyApplication application = null;

    // here, it should always do nothing except set mIsConnected to false
    private NetworkService() {
        isConnectedServer = false;
    }

    public void onInit(Context context,MyApplication application) {
        serContext = context;
        this.application = application;
    }

    public void onInit(Context context) {
        serContext = context;
    }

    public void setupConnection() {
        serNetCon = new NetConnect();
        serNetCon.start();
        try {
            serNetCon.join();
        } catch (Exception e) {
        }

        if (serNetCon == null || !serNetCon.connectedOrNot()) {
            isConnectedServer = false;
            return;
        } else {
            serSocket = serNetCon.getSocket();
            isConnectedServer = true;
            startListen(serContext);

            if (serSocket != null) {
                System.out.println("socket is not null");
            } else {
                System.out.println("socket is null");
            }
        }
    }

    private void startListen(Context context0) {
        serListenThread = new ClientListenThread(context0, serSocket, application);
        serListenThread.start();

        serSendThread = new ClientSendThread();
    }

    public boolean getIsConnected() {
        return isConnectedServer;
    }

    public void sendUpload(String s) {
        jsonArray = s.split(":");
        JSONObject jsonObjSend = new JSONObject();

        for (int i = 0; i < jsonArray.length; i += 2) {
            try {
                jsonObjSend.put(jsonArray[i], jsonArray[i + 1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        serSendThread.start(serSocket, jsonObjSend);
    }

    public void closeConnection() {
        try {
            if (serListenThread != null) {
                serListenThread.closeBufferedReader();
            }
        } catch (Exception e) {
        }
        try {
            if (serSocket != null) {
                serSocket.close();
            }
        } catch (Exception e) {
        }
        serSocket = null;
        isConnectedServer = false;
    }
}
