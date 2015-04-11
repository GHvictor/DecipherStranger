package com.android.decipherstranger.Network;

import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientSendThread{

	private Socket socketServer;
	private JSONObject strToSend;
	
	public synchronized void start(Socket socket, JSONObject jsonSend) {
		this.socketServer = socket;
		this.strToSend = jsonSend;
		
		try {
			OutputStreamWriter ost0=new OutputStreamWriter(socketServer.getOutputStream());
			ost0.write(strToSend+"\n");
			ost0.flush();
			Log.d("mStrToSend = " +strToSend, "++++++++++++++++++++++++++");
		} catch(Exception e){
			Log.d("cannot send to ","++++++++++++++++++++++++++++++");
		}
	}
}
