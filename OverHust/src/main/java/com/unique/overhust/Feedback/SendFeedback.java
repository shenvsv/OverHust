package com.unique.overhust.Feedback;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.unique.overhust.CommonUtils.ShareContext;

/**
 * Created by fhw on 12/27/13.
 */
public class SendFeedback {
    private String feedbackBody;
    private String feedbackContact;
    private String handsetinfo;
    private int mType;

    public SendFeedback(String fdBody, String fdContact, int type) {
        this.feedbackBody = fdBody;
        this.feedbackContact = fdContact;
        this.mType = type;
        getHandsetinfo();
        new SendEmailTask(feedbackBody, feedbackContact, mType).execute();

//        try {
//            GmailSender sender = new GmailSender("overhustdsnc@gmail.com", "overhust");
//            sender.sendMail(subject, feedbackBody, feedbackFrom, recipients);
//            Log.e("sendmail", "ok");
//        } catch (Exception e) {
//            Log.e("SendMail", e.getMessage(), e);
//        }
    }

    public String getHandsetinfo() {
        TelephonyManager tm = (TelephonyManager) ShareContext.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        handsetinfo = "手机型号:" + Build.MODEL +
                ",SDK版本:" + Build.VERSION.SDK +
                ",系统版本:" + Build.VERSION.RELEASE +
                ",手机厂商:" + Build.MANUFACTURER;
        try {
            handsetinfo = handsetinfo + ",设备ID:" + tm.getDeviceId();
        } catch (Exception e) {
            Log.e("exception",""+e);
        }
        return handsetinfo;
    }

    class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private final String subject = "用户反馈";
        private final String installSubject = "安装信息";
        private final String recipients = "overhustdsnc@gmail.com";
        private String mFeedbackBody;
        private String mFeedbackContact;
        private int Type;

        public SendEmailTask(String feedbackBody, String feedbackContact, int type) {
            this.mFeedbackBody = feedbackBody;
            this.mFeedbackContact = feedbackContact;
            this.Type = type;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (Type == 1) {
                    GmailSender sender = new GmailSender("overhustdsnc@gmail.com", "overhust");
                    sender.sendMail(subject, "反馈内容:\n" + mFeedbackBody + "\n\n" + "联系方式:\n" + mFeedbackContact + "\n\n手机信息:\n" + handsetinfo, recipients, recipients);
                }
                if (Type == 2) {
                    GmailSender sender = new GmailSender("overhustdsnc@gmail.com", "overhust");
                    sender.sendMail(installSubject, mFeedbackBody+mFeedbackContact+"手机信息:\n" + handsetinfo, recipients, recipients);
                }
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

        }
    }
}
