package com.android.decipherstranger.Network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyApplication;
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
                    int msgType = jsonObj.getInt("re_type");            // type of message received
                    switch (msgType) {
                        case GlobalMsgUtils.msgLogin:
                            Intent itLogin = new Intent("com.android.decipherstranger.LOGIN");
                            itLogin.putExtra("result", jsonObj.getString("re_message"));
                            application.setName(jsonObj.getString("re_name"));
                            application.setPortrait(ChangeUtils.toBitmap(jsonObj.getString("re_photo")));
                            if (jsonObj.getInt("re_gender") == 1){ application.setSex("男"); }
                            else { application.setSex("女"); }
                            application.setPhone(jsonObj.getString("re_phone"));
                            application.setEmail(jsonObj.getString("re_email"));
                            application.setBirth(jsonObj.getString("re_birth"));
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
                            clContext.sendBroadcast(itMessage);
                            System.out.println("发送成功");
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
                                itFriend.putExtra("isfinsh", false);
                            }
                            else if(jsonObj.getString("re_message").equals(MyStatic.resultTrue)){
                                itFriend.putExtra("reResult", true);
                                itFriend.putExtra("reAccount", jsonObj.getString("re_account"));
                                itFriend.putExtra("reName", jsonObj.getString("re_name"));
                                itFriend.putExtra("rePhoto", jsonObj.getString("re_photo"));
                                //JSONArray jsonArrFri = new JSONArray(jsonObj.getString("re_message"));
                                //int sum = jsonArrFri.length();
                                //System.out.println("qqqqq"+sum);
                                //ArrayList<User> friendList = new ArrayList<User> ();
                                //JSONObject jsonObjFri = jsonArrFri.getJSONObject(i);
                                /*
                                Bitmap bitmap = ChangeUtils.toBitmap(jsonObjFri.getString("re_photo"));
                                User user = new User();
                                user.setAccount(jsonObjFri.getString("re_account"));
                                user.setUsername(jsonObjFri.getString("re_name"));
                                user.setPortrait(bitmap);
                                friendList.add(user);*/

                                /*itFriend.putExtra(Integer.toString(i), jsonObjFri.getString("re_account")+":"+jsonObjFri.getString("re_name")+":"+jsonObjFri.getString("re_photo"));
                                itFriend.putExtra("friend", (Serializable)friendList);
                                itFriend.putExtra("sum", sum);*/
                                /*
                                ArrayList<User> lll = (ArrayList<User>) itFriend.getSerializableExtra("friend");
                                for(int i = 0 ; i < lll.size(); i++) {
                                    System.out.println(lll.get(i).getAccount() + "+++++++" + lll.get(i).getUsername() + "++++++" + lll.size());
                                }*/
                                //8itFriend.putExtra("reResult", true);
                            }
                            else{
                                itFriend.putExtra("reResult", false);
                                itFriend.putExtra("isfinish", true);
                            }
                            clContext.sendBroadcast(itFriend);
                            Log.v("Test","发送啦！！！");
                            break;
                        case GlobalMsgUtils.msgGameOneRecieve:
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
                        case GlobalMsgUtils.msgVoice:
                            break;
                        case GlobalMsgUtils.msgNearBy:
                            Intent itNearBy = new Intent("com.android.decipherstranger.NEARBY");
                            //itNearBy.putExtra("",);
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
