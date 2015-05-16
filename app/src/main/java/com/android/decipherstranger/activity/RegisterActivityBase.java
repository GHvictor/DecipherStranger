package com.android.decipherstranger.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.dialog.CustomDialogSex;
import com.android.decipherstranger.util.StringUtils;

/**
 * Created by PengHaitao on 2015/2/10.
 */
public class RegisterActivityBase extends BaseActivity {

    private StringUtils stringUtils = null;
    private Drawable OkIcon = null;

    private EditText accountEdit = null;
    private EditText passwordEdit = null;
    private EditText rePawEdit = null;
    private EditText nameEdit = null;
    private EditText emailEdit = null;
    private EditText phoneEdit = null;
    private EditText birthIcon = null;
    private Button birthButton = null;
    private Button sexButton = null;
    private EditText sexIcon;

    private Button backButton = null;
    private Button nextStepButton = null;

    boolean accountBool = true, passwordBool = false, rePawBool = false, nameBool = false,
            emailBool = false, phoneBool = false, birthBool = false, sexBool = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_base);
        initView();
    }

    private void initView(){

        OkIcon = getResources().getDrawable(R.drawable.ok_icon);
        OkIcon.setBounds(new Rect(0, 0, OkIcon.getIntrinsicWidth(),OkIcon.getIntrinsicHeight()));

        this.backButton = (Button)super.findViewById(R.id.register_back_button);
        this.accountEdit = (EditText)super.findViewById(R.id.register_account_input);
        this.passwordEdit = (EditText)super.findViewById(R.id.register_password_input);
        this.rePawEdit = (EditText)super.findViewById(R.id.register_re_paw_input);
        this.nameEdit = (EditText)super.findViewById(R.id.register_username_input);
        this.emailEdit = (EditText)super.findViewById(R.id.register_email_input);
        this.phoneEdit = (EditText)super.findViewById(R.id.register_phone_input);
        this.birthIcon = (EditText)super.findViewById(R.id.register_birth_icon);
        this.birthButton = (Button)super.findViewById(R.id.register_birth_input);
        this.sexButton = (Button)super.findViewById(R.id.register_sex_input);
        this.sexIcon = (EditText)super.findViewById(R.id.register_sex_icon);
        this.nextStepButton = (Button)super.findViewById(R.id.next_step_btn);

        this.accountEdit.setOnClickListener(new accountOnClickListenerImpl());
        this.passwordEdit.setOnClickListener(new passwordOnClickListenerImpl());
        this.rePawEdit.setOnClickListener(new rePawOnClickListenerImpl());
        this.nameEdit.setOnClickListener(new nameOnClickListenerImpl());
        this.emailEdit.setOnClickListener(new emailOnClickListenerImpl());
        this.phoneEdit.setOnClickListener(new phoneOnClickListenerImpl());
        this.birthButton.setOnClickListener(new birthOnClickListenerImpl());
        this.sexButton.setOnClickListener(new sexOnClickListenerImpl());

        this.accountEdit.setOnFocusChangeListener(new accountOnFocusChangeListenerImpl());
        this.passwordEdit.setOnFocusChangeListener(new passwordOnFocusChangeListenerImpl());
        this.rePawEdit.setOnFocusChangeListener(new rePawOnFocusChangeListenerImpl());
        this.nameEdit.setOnFocusChangeListener(new nameOnFocusChangeListenerImpl());
        this.emailEdit.setOnFocusChangeListener(new emailOnFocusChangeListenerImpl());
        this.phoneEdit.setOnFocusChangeListener(new phoneOnFocusChangeListenerImpl());
        this.backButton.setOnClickListener(new backOnClickListenerImpl());
        this.nextStepButton.setOnClickListener(new nextStepOnclickListenerImpl());
    }

    private class accountOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.accountEdit.setText("");
            RegisterActivityBase.this.accountEdit.setTextColor(Color.parseColor("#000000"));
        }
    }

    private class passwordOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.passwordEdit.setText("");
            RegisterActivityBase.this.passwordEdit.setTextColor(Color.parseColor("#000000"));
            RegisterActivityBase.this.passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private class rePawOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.rePawEdit.setText("");
            RegisterActivityBase.this.rePawEdit.setTextColor(Color.parseColor("#000000"));
            RegisterActivityBase.this.rePawEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private class nameOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.nameEdit.setText("");
            RegisterActivityBase.this.nameEdit.setTextColor(Color.parseColor("#000000"));
        }
    }

    private class emailOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.emailEdit.setText("");
            RegisterActivityBase.this.emailEdit.setTextColor(Color.parseColor("#000000"));
        }
    }

    private class phoneOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.phoneEdit.setText("");
            RegisterActivityBase.this.phoneEdit.setTextColor(Color.parseColor("#000000"));
        }
    }

    private class birthOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Dialog dialog = new DatePickerDialog(RegisterActivityBase.this,
                    new DatePickerDialog.OnDateSetListener(){
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month += 1;
                            RegisterActivityBase.this.birthButton.setTextColor(Color.parseColor("#000000"));
                            RegisterActivityBase.this.birthButton.setText(year + "-" + month + "-" + day);
                            birthBool = true;
                        }
                    },1990,9,22);
            dialog.show();
            RegisterActivityBase.this.birthIcon.setError("Success", OkIcon);
        }
    }

    private class sexOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            CustomDialogSex.Builder builder = new CustomDialogSex.Builder(RegisterActivityBase.this);
            builder.setTitle("请选择性别");
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.setManButton(
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            RegisterActivityBase.this.birthButton.setTextColor(Color.parseColor("#000000"));
                            RegisterActivityBase.this.sexButton.setText("男");
                            sexBool = true;
                            dialog.dismiss();
                            RegisterActivityBase.this.sexIcon.setError("Success", OkIcon);
                        }
                    });
            builder.setWomanButton(
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            RegisterActivityBase.this.birthButton.setTextColor(Color.parseColor("#000000"));
                            RegisterActivityBase.this.sexButton.setText("女");
                            sexBool = true;
                            dialog.dismiss();
                            RegisterActivityBase.this.sexIcon.setError("Success", OkIcon);
                        }
                    });
            builder.create().show();
        }
    }

    private class accountOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == RegisterActivityBase.this.accountEdit.getId()){
                if (!focus){
                    accountBool = false;
                    RegisterActivityBase.this.accountEdit.setTextColor(Color.parseColor("#FF0000"));
                    if (RegisterActivityBase.this.accountEdit.getText().length() == 0){
                        RegisterActivityBase.this.accountEdit.setError("Error");
                        RegisterActivityBase.this.accountEdit.setText("用户名不能为空");
                    }else if (RegisterActivityBase.this.accountEdit.getText().length() < 6){
                        RegisterActivityBase.this.accountEdit.setError("Error");
                        RegisterActivityBase.this.accountEdit.setText("用户名长度不能小于6个字符");
                    }else if (!checkAccountTop(accountEdit.getText().toString())) {
                        RegisterActivityBase.this.accountEdit.setError("Error");
                        RegisterActivityBase.this.accountEdit.setText("用户名须以字母开头");
                    }else if (!checkAccount(accountEdit.getText().toString())) {
                        RegisterActivityBase.this.accountEdit.setError("Error");
                        RegisterActivityBase.this.accountEdit.setText("用户名只能由字母数字以及下划线组成");
                    }else if (!checkAccountUse(accountEdit.getText().toString())){
                        RegisterActivityBase.this.accountEdit.setError("Error");
                        RegisterActivityBase.this.accountEdit.setText("该用户名已经占用，请重新选择");
                    }else{
                        accountBool = true;
                        RegisterActivityBase.this.accountEdit.setError("Success",OkIcon);
                        RegisterActivityBase.this.accountEdit.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    private class passwordOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == RegisterActivityBase.this.passwordEdit.getId()){
                if (!focus){
                    passwordBool = false;
                    RegisterActivityBase.this.passwordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    RegisterActivityBase.this.passwordEdit.setTextColor(Color.parseColor("#FF0000"));
                    if (RegisterActivityBase.this.passwordEdit.getText().length() == 0){
                        RegisterActivityBase.this.passwordEdit.setError("Error");
                        RegisterActivityBase.this.passwordEdit.setText("密码不能为空");
                    }else if (RegisterActivityBase.this.passwordEdit.getText().length() < 6){
                        RegisterActivityBase.this.passwordEdit.setError("Error");
                        RegisterActivityBase.this.passwordEdit.setText("密码长度不能小于6个字符");
                    }else if (!checkPassword(passwordEdit.getText().toString())) {
                        RegisterActivityBase.this.passwordEdit.setError("Error");
                        RegisterActivityBase.this.passwordEdit.setText("密码只能由数字和字母组成");
                    }else{
                        passwordBool = true;
                        RegisterActivityBase.this.passwordEdit.setError("Success",OkIcon);
                        RegisterActivityBase.this.passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        RegisterActivityBase.this.passwordEdit.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    private class rePawOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == RegisterActivityBase.this.rePawEdit.getId()){
                if (!focus){
                    rePawBool = false;
                    String rePawC = RegisterActivityBase.this.rePawEdit.getText().toString();
                    String passwordC = RegisterActivityBase.this.passwordEdit.getText().toString();
                    if (!rePawC.equals(passwordC)) {
                        RegisterActivityBase.this.rePawEdit.setError("Error");
                        RegisterActivityBase.this.rePawEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        RegisterActivityBase.this.rePawEdit.setTextColor(Color.parseColor("#FF0000"));
                        rePawEdit.setText("两次密码不一致");
                    }else{
                        rePawBool = true;
                        RegisterActivityBase.this.rePawEdit.setError("Success",OkIcon);
                    }
                }
            }
        }
    }

    private class nameOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == RegisterActivityBase.this.nameEdit.getId()){
                if (!focus){
                    nameBool = false;
                    if (RegisterActivityBase.this.nameEdit.getText().length() == 0){
                        RegisterActivityBase.this.nameEdit.setError("Error");
                        RegisterActivityBase.this.nameEdit.setTextColor(Color.parseColor("#FF0000"));
                        RegisterActivityBase.this.nameEdit.setText("昵称不能为空");
                    }else{
                        nameBool = true;
                        RegisterActivityBase.this.nameEdit.setError("Success",OkIcon);
                    }
                }
            }
        }
    }

    private class emailOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == RegisterActivityBase.this.emailEdit.getId()){
                if (!focus){
                    emailBool = false;
                    RegisterActivityBase.this.emailEdit.setTextColor(Color.parseColor("#FF0000"));
                    if (RegisterActivityBase.this.emailEdit.getText().length() == 0){
                        RegisterActivityBase.this.emailEdit.setError("Error");
                        RegisterActivityBase.this.emailEdit.setText("邮箱不能为空");
                    }else if (!checkEmail(emailEdit.getText().toString())) {
                        RegisterActivityBase.this.emailEdit.setError("Error");
                        RegisterActivityBase.this.emailEdit.setText("请输入正确的邮箱地址");
                    }else{
                        emailBool = true;
                        RegisterActivityBase.this.emailEdit.setError("Success",OkIcon);
                        RegisterActivityBase.this.emailEdit.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    private class phoneOnFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view,boolean focus){
            if (view.getId() == RegisterActivityBase.this.phoneEdit.getId()){
                if (!focus){
                    phoneBool = false;
                    RegisterActivityBase.this.phoneEdit.setTextColor(Color.parseColor("#FF0000"));
                    if (RegisterActivityBase.this.phoneEdit.getText().length() == 0){
                        RegisterActivityBase.this.phoneEdit.setError("Error");
                        RegisterActivityBase.this.phoneEdit.setText("手机号不能为空");
                    }else if (!checkPhone(phoneEdit.getText().toString())) {
                        RegisterActivityBase.this.phoneEdit.setError("Error");
                        RegisterActivityBase.this.phoneEdit.setText("请输入正确的手机号码");
                    }else{
                        phoneBool = true;
                        RegisterActivityBase.this.phoneEdit.setError("Success",OkIcon);
                        RegisterActivityBase.this.phoneEdit.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    private class backOnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent it = new Intent(RegisterActivityBase.this,LoginActivity.class);
            startActivity(it);
            RegisterActivityBase.this.finish();
        }
    }
    private class nextStepOnclickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view){
            RegisterActivityBase.this.nextStepButton.setFocusable(true);
            RegisterActivityBase.this.nextStepButton.setFocusableInTouchMode(true);
            RegisterActivityBase.this.nextStepButton.requestFocus();
            RegisterActivityBase.this.nextStepButton.requestFocusFromTouch();
            if (accountBool && passwordBool && rePawBool && nameBool && sexBool && emailBool && phoneBool && birthBool){
                Intent it = new Intent(RegisterActivityBase.this,RegisterActivityPhoto.class);
                Bundle bundle = new Bundle();
                bundle.putString("account", String.valueOf(accountEdit.getText()));
                bundle.putString("possword", String.valueOf(passwordEdit.getText()));
                bundle.putString("name", String.valueOf(nameEdit.getText()));
                bundle.putString("sex", (String) sexButton.getText());
                bundle.putString("email", String.valueOf(emailEdit.getText()));
                bundle.putString("phone", String.valueOf(phoneEdit.getText()));
                bundle.putString("birth", (String) birthButton.getText());
                it.putExtras(bundle);
                startActivity(it);
            }else {
                 Toast toast = Toast.makeText(RegisterActivityBase.this, "请确认信息是否填写完整", Toast.LENGTH_LONG);
                 toast.show();
            }

        }
    }

    private boolean checkAccountTop(String s){
        return s.matches("^[a-zA-Z].{5,19}");    //     字母开头
    }

    private boolean checkAccount(String s){
        return s.matches("^[a-zA-Z][a-zA-Z0-9_]{5,19}");    //字母开头，允许6-20字节，允许字母数字下划线
    }

    private boolean checkAccountUse(String s){
        return true;                           //  检测用户名是否被使用
    }

    private boolean checkPassword(String s){
        return s.matches("^[a-zA-Z0-9]{6,20}");       //数字和字母
    }

    private boolean checkEmail(String s) {
        return s.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");     //test@email.com
    }

    private boolean checkPhone(String s) {
        return s.matches("^((13[0-9])|(14[5,7])|(15[^4,//D])|(17[0,5-7,9])|(18[^4,//D]]))[0-9]{8}$");     //手机号码
    }
}

