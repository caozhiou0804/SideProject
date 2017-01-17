package com.junit.caozhiou.sideproject.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.HomeActivity;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.entity.GetuiBean;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class DemoIntentService extends GTIntentService {

    private static final String TAG = "GetuiSdkDemo";

    /**
     * 为了观察透传数据变化.
     */
    private static int cnt;

    public DemoIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.d(TAG, "onReceiveServicePid -> " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        Log.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        Log.d(TAG, "onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);

        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.d(TAG, "receiver payload = " + data);

            // 测试消息为了观察数据变化
            if (data.equals("收到一条透传测试消息")) {
                data = data + "-" + cnt;
                cnt++;
            }
            sendMessage(data, 0);
            Intent intentAct = new Intent();
            intentAct.setClass(context, HomeActivity.class);
            intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            GetuiBean geTuiBean = null;
            try {
                geTuiBean = new Gson().fromJson(data, GetuiBean.class);

            } catch (Exception e) {
                // TODO: handle exception
            }
            showNotification(context, intentAct,geTuiBean);
        }

        Log.d(TAG, "----------------------------------------------------------------------------------------------");
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        MyApplication.getInstance().setClientId(clientid);
        sendMessage(clientid, 1);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.d(TAG, "onReceiveOnlineState -> " + (online ? "online" : "offline"));
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.d(TAG, "onReceiveCommandResult -> " + cmdMessage);

        int action = cmdMessage.getAction();

        if (action == PushConsts.SET_TAG_RESULT) {
            setTagResult((SetTagCmdMessage) cmdMessage);
        } else if ((action == PushConsts.THIRDPART_FEEDBACK)) {
            feedbackResult((FeedbackCmdMessage) cmdMessage);
        }
    }

    private void setTagResult(SetTagCmdMessage setTagCmdMsg) {
        String sn = setTagCmdMsg.getSn();
        String code = setTagCmdMsg.getCode();

        String text = "设置标签失败, 未知异常";
        switch (Integer.valueOf(code)) {
            case PushConsts.SETTAG_SUCCESS:
                text = "设置标签成功";
                break;

            case PushConsts.SETTAG_ERROR_COUNT:
                text = "设置标签失败, tag数量过大, 最大不能超过200个";
                break;

            case PushConsts.SETTAG_ERROR_FREQUENCY:
                text = "设置标签失败, 频率过快, 两次间隔应大于1s且一天只能成功调用一次";
                break;

            case PushConsts.SETTAG_ERROR_REPEAT:
                text = "设置标签失败, 标签重复";
                break;

            case PushConsts.SETTAG_ERROR_UNBIND:
                text = "设置标签失败, 服务未初始化成功";
                break;

            case PushConsts.SETTAG_ERROR_EXCEPTION:
                text = "设置标签失败, 未知异常";
                break;

            case PushConsts.SETTAG_ERROR_NULL:
                text = "设置标签失败, tag 为空";
                break;

            case PushConsts.SETTAG_NOTONLINE:
                text = "还未登陆成功";
                break;

            case PushConsts.SETTAG_IN_BLACKLIST:
                text = "该应用已经在黑名单中,请联系售后支持!";
                break;

            case PushConsts.SETTAG_NUM_EXCEED:
                text = "已存 tag 超过限制";
                break;

            default:
                break;
        }

        Log.d(TAG, "settag result sn = " + sn + ", code = " + code + ", text = " + text);
    }

    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appid = feedbackCmdMsg.getAppid();
        String taskid = feedbackCmdMsg.getTaskId();
        String actionid = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();

        Log.d(TAG, "onReceiveCommandResult -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nactionid = " + actionid + "\nresult = " + result
                + "\ncid = " + cid + "\ntimestamp = " + timestamp);
    }

    private void sendMessage(String data, int what) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = data;
//        DemoApplication.sendMessage(msg);
    }

    // 通知栏显示当前播放信息，利用通知和 PendingIntent来启动对应的activity
    public void showNotification(Context context, Intent intent, GetuiBean geTuiBean) {
        String title = "";
        String content = "";
        if (!TextUtils.isEmpty(geTuiBean.getMessage_title())) {
            title = geTuiBean.getMessage_title();
        }
        if (!TextUtils.isEmpty(geTuiBean.getMessage_content())) {
            content = geTuiBean.getMessage_content();
        }

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setAutoCancel(true);
        Notification mNotification = builder.build();
        mNotification.icon = R.drawable.icon_inner;// notification通知栏图标
        mNotification.defaults = Notification.DEFAULT_SOUND;
        mNotification.defaults = Notification.DEFAULT_VIBRATE;

        // 自定义布局
        RemoteViews contentView = new RemoteViews(context.getPackageName(),
                R.layout.notification);
        contentView.setImageViewResource(R.id.notification_icon,
                R.drawable.icon_inner);
        contentView.setTextViewText(R.id.notification_title, title);
        contentView.setTextViewText(R.id.notification_name, content);
        mNotification.contentView = contentView;
        // PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
        // intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);// 解决华为推送消息不能跳转问题
        // notifcation.flags = Notification.FLAG_NO_CLEAR;// 永久在通知栏里
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
        // 使用自定义下拉视图时，不需要再调用setLatestEventInfo()方法，但是必须定义contentIntent
        mNotification.contentIntent = contentIntent;
        mNotificationManager.notify((int) (System.currentTimeMillis() / 1000),
                mNotification);

    }
}
