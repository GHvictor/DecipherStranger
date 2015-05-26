package com.android.decipherstranger.Network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;

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
    
    private MyApplication application = null;

    public ClientListenThread(Context context, Socket s, MyApplication application) {
        this.clContext = context;
        this.clSocket = s;
        this.application = application;
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
                    System.out.println("++++++++++++这是一条消息");
                    int msgType = jsonObj.getInt("re_type");            // type of message received
                    switch (msgType) {
                        case GlobalMsgUtils.msgLogin:
                            Intent itLogin = new Intent("com.android.decipherstranger.LOGIN");
                            itLogin.putExtra("result", jsonObj.getString("re_message"));
                            if(jsonObj.getString("re_message").equals(MyStatic.resultTrue)) {
                                application.setName(jsonObj.getString("re_name"));
                                application.setPortrait(ChangeUtils.toBitmap(jsonObj.getString("re_photo")));
                                if (jsonObj.getInt("re_gender") == 1) {
                                    application.setSex("男");
                                } else {
                                    application.setSex("女");
                                }
                                application.setPhone(jsonObj.getString("re_phone"));
                                application.setEmail(jsonObj.getString("re_email"));
                                application.setBirth(jsonObj.getString("re_birth"));
                            }
                            clContext.sendBroadcast(itLogin);
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
                            itMessage.putExtra("msgType", 0);
                            clContext.sendBroadcast(itMessage);
                            System.out.println("发送成功1");
                            break;
                        case GlobalMsgUtils.msgShake:
                            Intent itShake = new Intent("com.android.decipherstranger.SHAKE");
                            if (jsonObj.getString("re_message").equals(MyStatic.resultFalse)){
                                itShake.putExtra("reResult", false);
                            }else {
                                itShake.putExtra("reResult", true);
                                itShake.putExtra("reAccount", jsonObj.getString("re_account"));
                                itShake.putExtra("rePhoto", jsonObj.getString("re_photo"));
                                itShake.putExtra("reGender", jsonObj.getInt("re_gender"));
                                itShake.putExtra("reName", jsonObj.getString("re_name"));
                            }
                            clContext.sendBroadcast(itShake);
                            break;
                        case GlobalMsgUtils.msgFriendList:
                            Intent itFriend = new Intent("com.android.decipherstranger.FRIEND");
                            if(jsonObj.getString("re_message").equals(MyStatic.resultFalse)){
                                itFriend.putExtra("reResult", false);
                                itFriend.putExtra("isfinsh", false);
                            }
                            else if(jsonObj.getString("re_message").equals(MyStatic.resultTrue)){
                                itFriend.putExtra("reResult", true);
                                itFriend.putExtra("reAccount", jsonObj.getString("re_account"));
                                itFriend.putExtra("reName", jsonObj.getString("re_name"));
                                itFriend.putExtra("reGender", jsonObj.getString("re_gender"));
                                itFriend.putExtra("rePhoto", jsonObj.getString("re_photo"));
                            }
                            else{
                                itFriend.putExtra("reResult", false);
                                itFriend.putExtra("isfinish", true);
                            }
                            clContext.sendBroadcast(itFriend);
                            Log.v("Test","发送啦！！！");
                            break;
                        case GlobalMsgUtils.msgGameOneReceive:
                            Intent itGameRec = new Intent("com.android.decipherstranger.GAMEONE");
                            itGameRec.putExtra("reGrade", jsonObj.getInt("re_grade"));
                            itGameRec.putExtra("reSum", jsonObj.getInt("re_sum"));
                            itGameRec.putExtra("reRock", jsonObj.getInt("re_rock"));
                            itGameRec.putExtra("reScissors", jsonObj.getInt("re_scissors"));
                            itGameRec.putExtra("rePaper", jsonObj.getInt("re_paper"));
                            clContext.sendBroadcast(itGameRec);
                            break;
                        case GlobalMsgUtils.msgGameOneSend:
                            break;
                        case GlobalMsgUtils.msgAddFriend:
                            Intent itAddFriend = new Intent("com.android.decipherstranger.MESSAGE");
                            if(jsonObj.getString("re_message").equals(MyStatic.resultTrue)) {
                                itAddFriend.putExtra("Friend", "Friend");
                                itAddFriend.putExtra("reAccount", jsonObj.getString("re_account"));
                                itAddFriend.putExtra("rePhoto", jsonObj.getString("re_photo"));
                                itAddFriend.putExtra("reGender", jsonObj.getInt("re_gender"));
                                itAddFriend.putExtra("reName", jsonObj.getString("re_name"));
                                itAddFriend.putExtra("reResult", true);
                            }
                            else{
                                itAddFriend.putExtra("Friend", "Friend");
                                itAddFriend.putExtra("reResult", false);
                            }
                            clContext.sendBroadcast(itAddFriend);
                            System.out.println("发送成功Add");
                            break;
                        case GlobalMsgUtils.msgVoice:
                            Intent itVoice = new Intent("com.android.decipherstranger.MESSAGE");
                            itVoice.putExtra("reMessage", jsonObj.getString("re_message"));
                            itVoice.putExtra("reSender", jsonObj.getString("re_sender"));
                            itVoice.putExtra("reDate", jsonObj.getString("re_date"));
                            itVoice.putExtra("reTime", jsonObj.getString("re_time"));
                            itVoice.putExtra("msgType", 1);
                            clContext.sendBroadcast(itVoice);
                            System.out.println("发送成功2");
                            break;
                        case GlobalMsgUtils.msgNearBy:
                            System.out.println("daacallll");
                            Intent itNearBy = new Intent("com.android.decipherstranger.NEARBY");
                            if(jsonObj.getString("re_message").equals(MyStatic.resultFalse)){
                                itNearBy.putExtra("reResult", false);
                                itNearBy.putExtra("isfinsh", false);
                            }
                            else if(jsonObj.getString("re_message").equals(MyStatic.resultTrue)){
                                itNearBy.putExtra("reResult", true);
                                itNearBy.putExtra("reAccount", jsonObj.getString("re_account"));
                                itNearBy.putExtra("reName", jsonObj.getString("re_name"));
                                itNearBy.putExtra("rePhoto", jsonObj.getString("re_photo"));
                                itNearBy.putExtra("reGender", jsonObj.getInt("re_gender"));
                                itNearBy.putExtra("reLongtitude", jsonObj.getString("re_longtitude"));
                                itNearBy.putExtra("reLatitude", jsonObj.getString("re_latitude"));
                                itNearBy.putExtra("reDistance", jsonObj.getString("re_distance"));
                            }
                            else{
                                itNearBy.putExtra("reResult", false);
                                itNearBy.putExtra("isfinish", true);
                            }
                            clContext.sendBroadcast(itNearBy);
                            Log.v("Test","发送啦！！！");
                            break;
                        case GlobalMsgUtils.msgChangeInf:
                            Intent itChange = new Intent("com.android.decipherstranger.CHANGE");
                            itChange.putExtra("reResult", true);
                            clContext.sendBroadcast(itChange);
                            break;
                        case GlobalMsgUtils.msgImage:
                            Intent itImage = new Intent("com.android.decipherstranger.MESSAGE");
                            itImage.putExtra("reMessage", jsonObj.getString("re_message"));
                            itImage.putExtra("reSender", jsonObj.getString("re_sender"));
                            itImage.putExtra("reDate", jsonObj.getString("re_date"));
                            itImage.putExtra("msgType", 2);
                            clContext.sendBroadcast(itImage);
                            System.out.println("发送成功3");
                            break;
                        case GlobalMsgUtils.msgDelFri:
                            Intent itDelFri = new Intent("com.android.decipherstranger.MESSAGE");
                            itDelFri.putExtra("Friend", "Friend");
                            itDelFri.putExtra("Del", "Del");
                            itDelFri.putExtra("reAccount", jsonObj.getString("re_message"));
                            clContext.sendBroadcast(itDelFri);
                            break;
                        case GlobalMsgUtils.msgShowFri:
                            Intent itShowFri = new Intent("com.android.decipherstranger.SHOWFRI");
                            itShowFri.putExtra("reEmail", jsonObj.getString("re_email"));
                            itShowFri.putExtra("rePhone", jsonObj.getString("re_phone"));
                            itShowFri.putExtra("reBirth", jsonObj.getString("re_birth"));
                            clContext.sendBroadcast(itShowFri);
                            break;
                        case GlobalMsgUtils.msgOffMsg:
                            if(jsonObj.getString("re_message").equals(MyStatic.resultFalse)){
                                break;
                            }else {
                                Intent itOffMsg = new Intent("com.android.decipherstranger.MESSAGE");
                                itOffMsg.putExtra("reMessage", jsonObj.getString("re_message"));
                                itOffMsg.putExtra("reSender", jsonObj.getString("re_sender"));
                                itOffMsg.putExtra("reDate", jsonObj.getString("re_date"));
                                if (jsonObj.getInt("re_msgtype") == 2) {
                                    itOffMsg.putExtra("msgType", 2);
                                } else if (jsonObj.getInt("re_msgtype") == 1) {
                                    itOffMsg.putExtra("msgType", 1);
                                    itOffMsg.putExtra("reTime", jsonObj.getString("re_time"));
                                } else {
                                    itOffMsg.putExtra("msgType", 0);
                                }
                                clContext.sendBroadcast(itOffMsg);
                                break;
                            }
                        case GlobalMsgUtils.msgSendInv:
                            break;
                        case GlobalMsgUtils.msgReceiveInv:
                            Intent itReInv = new Intent("com.android.decipherstranger.INVITATION");
                            if(jsonObj.getString("re_message").equals(MyStatic.resultTrue)){
                                itReInv.putExtra("reResult", true);
                                itReInv.putExtra("reAccount", jsonObj.getString("re_account"));
                                itReInv.putExtra("rePhoto", jsonObj.getString("re_photo"));
                                itReInv.putExtra("reGender", jsonObj.getInt("re_gender"));
                                itReInv.putExtra("reName", jsonObj.getString("re_name"));
                            }else{
                                itReInv.putExtra("reResult", false);
                            }
                            clContext.sendBroadcast(itReInv);
                            break;
                        default:
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
