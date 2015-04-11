package com.android.decipherstranger.dialog;

/**
 * Created by Administrator on 2015/3/13 0013.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.android.decipherstranger.R;

public class CustomDialogSex extends Dialog {

    public CustomDialogSex(Context context) {
        super(context);
    }

    public CustomDialogSex(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String negativeButtonText;
        private DialogInterface.OnClickListener manButtonClickListener;
        private DialogInterface.OnClickListener womanButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setManButton(DialogInterface.OnClickListener listener){
            this.manButtonClickListener = listener;
            return this;
        }

        public Builder setWomanButton(DialogInterface.OnClickListener listener){
            this.womanButtonClickListener = listener;
            return this;
        }

        public CustomDialogSex create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialogSex dialog = new CustomDialogSex(context,R.style.Dialog);
            final View layout = inflater.inflate(R.layout.dialog_layout_sex, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            //set the man button
            ((Button) layout.findViewById(R.id.register_sex_man)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    manButtonClickListener.onClick(dialog,
                            DialogInterface.BUTTON_POSITIVE);
                }
            });
            //set the woman button
            ((Button) layout.findViewById(R.id.register_sex_woman)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    womanButtonClickListener.onClick(dialog,
                            DialogInterface.BUTTON_POSITIVE);
                }
            });
            dialog.setContentView(layout);
            return dialog;
        }
    }
}