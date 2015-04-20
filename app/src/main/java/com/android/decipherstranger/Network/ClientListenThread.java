package com.android.decipherstranger.Network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.decipherstranger.util.GlobalMsgUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Feng on 2015/04/11.
 */
public class ClientListenThread extends Thread {
    private Context clContext;
    private Socket clSocket;

    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public ClientListenThread(Context context, Socket s) {
        this.clContext = context;
        this.clSocket = s;
    }

    public void run() {
        super.run();
        try {
            inputStreamReader = new InputStreamReader(clSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            String reMsg = null;

            while (true) {
                if ((reMsg = bufferedReader.readLine()) != null) {
                    JSONObject jsonObj = new JSONObject(reMsg.toString());

                    Log.v("能不能接到", reMsg);
                    int msgType = jsonObj.getInt("re_type");            // type of message received
                    switch (msgType) {
                        case GlobalMsgUtils.msgLogin:
                            Intent itLogin = new Intent("com.android.decipherstranger.LOGIN");
                            itLogin.putExtra("result", jsonObj.getString("re_message"));
                            clContext.sendBroadcast(itLogin);
                            break;
                        case GlobalMsgUtils.msgRegister:
                            break;
                        case GlobalMsgUtils.msgMessage:
                            break;
                        case GlobalMsgUtils.msgShake:
                            Intent itShake = new Intent("com.android.decipherstranger.SHAKE");
                            JSONArray jsonArray = new JSONArray(jsonObj.getString("re_message"));
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjShake = jsonArray.getJSONObject(i);
                                itShake.putExtra("reAccount", jsonObjShake.getString("re_account"));
                                itShake.putExtra("rePhoto", jsonObjShake.getString("re_photo"));
                                itShake.putExtra("reGender", jsonObjShake.getString("re_gender"));
                                itShake.putExtra("reName", jsonObjShake.getString("re_name"));
                            }
                            clContext.sendBroadcast(itShake);
                            break;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void closeBufferedReader() {
        try {
            bufferedReader = null;
        } catch (Exception e) {
        }
    }
}
