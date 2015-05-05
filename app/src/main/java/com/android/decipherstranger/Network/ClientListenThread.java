package com.android.decipherstranger.Network;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.util.Log;

import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            String reMsg = new String();
            String test = new String();
            while (true) {
                while(true){
                    test = bufferedReader.readLine();
                    if(test.contains("+++++")){
                        reMsg += test.substring(0, test.length()-5);
                        break;
                    }
                    else if(test != null){
                        reMsg += test;
                    }
                    else {
                        break;
                    }
                }
                if (reMsg != null) {
                    System.out.println(reMsg);
                    JSONObject jsonObj = new JSONObject(reMsg);

                    Log.v("能不能接到aaaaa", reMsg);
                    int msgType = jsonObj.getInt("re_type");            // type of message received
                    switch (msgType) {
                        case GlobalMsgUtils.msgLogin:
                            Intent itLogin = new Intent("com.android.decipherstranger.LOGIN");
                            itLogin.putExtra("result", jsonObj.getString("re_message"));
                            clContext.sendBroadcast(itLogin);
                            System.out.println("aaaaaaaJustatest");
                            break;
                        case GlobalMsgUtils.msgRegister:
                            Intent itRegister = new Intent("com.android.decipherstranger.REGISTER");
                            itRegister.putExtra("result", jsonObj.getString("re_message"));
                            clContext.sendBroadcast(itRegister);
                            break;
                        case GlobalMsgUtils.msgMessage:
                            Intent itMessage = new Intent("com.android.decipherstranger.MESSAGE");
                            itMessage.putExtra("reMessage", jsonObj.getString("re_message"));
                            itMessage.putExtra("reSender", jsonObj.getString("re_sender"));
                            itMessage.putExtra("reDate", jsonObj.getString("re_date"));
                            clContext.sendBroadcast(itMessage);
                            break;
                        case GlobalMsgUtils.msgShake:
                            Intent itShake = new Intent("com.android.decipherstranger.SHAKE");
                            if (jsonObj.getString("re_message").equals(MyStatic.resultFalse)){
                                itShake.putExtra("reResult", false);
                            }
                            else {
                                JSONArray jsonArrShake = new JSONArray(jsonObj.getString("re_message"));
                                itShake.putExtra("reResult", true);
                                for (int i = 0; i < jsonArrShake.length(); i++) {
                                    JSONObject jsonObjShake = jsonArrShake.getJSONObject(i);
                                    itShake.putExtra("reAccount", jsonObjShake.getString("re_account"));
                                    itShake.putExtra("rePhoto", jsonObjShake.getString("re_photo"));
                                    itShake.putExtra("reGender", jsonObjShake.getInt("re_gender"));
                                    itShake.putExtra("reName", jsonObjShake.getString("re_name"));
                                }
                            }
                            clContext.sendBroadcast(itShake);
                            break;
                        case GlobalMsgUtils.msgFriendList:
                            Intent itFriend = new Intent("com.android.decipherstranger.FRIEND");
                            if(jsonObj.getString("re_message").equals(MyStatic.resultFalse)){
                                itFriend.putExtra("reResult", false);
                            }
                            else {
                                JSONArray jsonArrFri = new JSONArray(jsonObj.getString("re_message"));
                                itFriend.putExtra("reResult", true);

                                int sum = jsonArrFri.length();
                                System.out.println("qqqqq"+sum);
                                ArrayList<User> friendList = new ArrayList<User> ();


                                for(int i = 0; i < sum; i++) {
                                    JSONObject jsonObjFri = jsonArrFri.getJSONObject(i);
                                    /*
                                    Bitmap bitmap = ChangeUtils.toBitmap(jsonObjFri.getString("re_photo"));
                                    User user = new User();
                                    user.setAccount(jsonObjFri.getString("re_account"));
                                    user.setUsername(jsonObjFri.getString("re_name"));
                                    user.setPortrait(bitmap);
                                    friendList.add(user);*/
                                    itFriend.putExtra(Integer.toString(i), jsonObjFri.getString("re_account")+":"+jsonObjFri.getString("re_name")+":"+jsonObjFri.getString("re_photo"));
                                }
                                //itFriend.putExtra("friend", (Serializable)friendList);
                                itFriend.putExtra("sum", sum);
                                /*
                                ArrayList<User> lll = (ArrayList<User>) itFriend.getSerializableExtra("friend");
                                for(int i = 0 ; i < lll.size(); i++) {
                                    System.out.println(lll.get(i).getAccount() + "+++++++" + lll.get(i).getUsername() + "++++++" + lll.size());
                                }*/
                                //itFriend.putExtra("friend","lalalal");
                            }
                            clContext.sendBroadcast(itFriend);
                            Log.v("Test","发送啦！！！");
                            break;
                        case GlobalMsgUtils.msgGameOneRecieve:
                            Intent itGameRec = new Intent("com.android.decipherstranger.GAMEONE");
                            itGameRec.putExtra("reGrade", jsonObj.getInt("re_grade"));
                            itGameRec.putExtra("reRock", jsonObj.getInt("re_rock"));
                            itGameRec.putExtra("reScissors", jsonObj.getInt("re_scissors"));
                            itGameRec.putExtra("rePaper", jsonObj.getInt("re_paper"));
                            clContext.sendBroadcast(itGameRec);
                            break;
                        case GlobalMsgUtils.msgGameOneSend:
                            break;
                        case GlobalMsgUtils.msgVoice:
                            break;
                    }
                    reMsg = "";
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
