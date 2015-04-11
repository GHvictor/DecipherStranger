package com.android.decipherstranger.Network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

public class NetworkService {

	private static NetworkService instanceServer;
    private static String jsonArray[] = new String[50];
   // private static JSONObject jsonObjSend = new JSONObject();
	
	public static NetworkService getInstance() {
		if(instanceServer == null) {
            instanceServer = new NetworkService();
		}
		return instanceServer;
	}
	
	private boolean isConnectedServer;
	private NetConnect netConServer;
	private ClientListenThread listenThreadServer;
	private ClientSendThread sendThreadServer;
	private Socket socketServer;
	private Context ContextServer;
	
	// here, it should always do nothing except set mIsConnected to false
	private NetworkService() {
        isConnectedServer = false;
	}
	
	public void onInit(Context context) {
        ContextServer = context;
	}
	
	public void setupConnection() {
        netConServer = new NetConnect();
        netConServer.start();
		try {
            netConServer.join();
		} catch(Exception e) {}
		
		if(netConServer == null || !netConServer.connectedOrNot())
		{
            isConnectedServer = false;
			return;
		} else {
            socketServer = netConServer.getSocket();
            isConnectedServer = true;
			startListen(ContextServer);
			
			if(socketServer != null) {
				System.out.println("socket is not null");
			} else {
				System.out.println("socket is null");
			}
		}
	}
	
	private void startListen(Context context0) {
        listenThreadServer = new ClientListenThread(context0,socketServer);
        listenThreadServer.start();

        sendThreadServer = new ClientSendThread();
	}
	
	public boolean getIsConnected() {
		return isConnectedServer;
	}

	public void sendUpload(String s){
        jsonArray = s.split("-");
        JSONObject jsonObjSend = new JSONObject();
        /*
		sendUpload(type + "");
		sendUpload(sentence);
		*/
        for(int i = 0; i < jsonArray.length ;i+=2){
            try {
                jsonObjSend.put(jsonArray[i],jsonArray[i+1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        sendThreadServer.start(socketServer,jsonObjSend);
	}
	
	/*  synchronized so only one send action is happening at a time  */
    /*
	private synchronized void sendUpload(String buff)
	{

		//buff = buff.replace("\n", GlobalStrings.replaceOfReturn);
        sendThreadServer.start(socketServer,buff);
	}*/
	
	public void closeConnection() {
		try{
			if(listenThreadServer != null) {
                listenThreadServer.closeBufferedReader();
			}
		} catch (Exception e) {}
		try{
			if(socketServer != null) {
                socketServer.close();
			}
		} catch (Exception e) {}
        socketServer = null;
        isConnectedServer = false;
	}
}
