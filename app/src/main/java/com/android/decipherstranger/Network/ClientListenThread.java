package com.android.decipherstranger.Network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.decipherstranger.activity.LoginActivity;
import com.android.decipherstranger.activity.MainActivity;
import com.android.decipherstranger.activity.MainPageActivity.MainPage;
import com.android.decipherstranger.util.GlobalMsgUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListenThread extends Thread{
	private Context clContext;
	private Socket clSocket;
	
	private InputStreamReader inputStreamReader;
	private BufferedReader bufferedReader;
	
	public ClientListenThread(Context par,Socket s)
	{
		this.clContext=par;
		this.clSocket=s;
	}

	public void run()
	{
		super.run();
		try{
            inputStreamReader=new InputStreamReader(clSocket.getInputStream());
            bufferedReader=new BufferedReader(inputStreamReader);
			String reMsg=null;
			
			while (true) {
				if ((reMsg = bufferedReader.readLine()) != null) {
                    JSONObject jsonObj = new JSONObject(reMsg.toString());

                    Log.v("能不能接到",reMsg);
					int msgType = jsonObj.getInt("re_type");			// type of message received
					
					//String actualMsg = mBuffRder0.readLine();
                    //uponReceivedMsg();
                    //Log.v("能不能",actualMsg);
                    /*switch (msgType){
                        case GlobalMsgUtils.msgLogin:
                            Intent it = new Intent(clContext, MainPage.class);
                            it.putExtra("result",jsonObj.getString("re_message"));
                            clContext.sendBroadcast(it);

                           // clContext.
                        break;
                        case GlobalMsgUtils.msgMessage:


                    }*/
				}
			}
		}catch(Exception e){}
	}
	
	public void uponReceivedMsg() {
//		NetworkService.getInstance().sendUpload(GlobalMsgTypes.msgMsgReceived, "xxxxx");
	}
	
	public void closeBufferedReader()
	{
		try {
            bufferedReader = null;
		} catch(Exception e) {}
	}
}
