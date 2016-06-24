package com.lee.android.readxml;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.lee.android.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

/**
 * Created by bobo.li on 2016/3/31.
 */
public class ReadXmlFile {

    private static Context mContext;

    //for pull xml
    // Persistent storage for notification policy
    static final String TAG = "NotificationService";
    static final boolean DBG = true;
    private AtomicFile mPolicyFile;
    private static final int DB_VERSION = 1;

    private static final String TAG_NOTIFICATION_POLICY = "notification-permision-default";
    private static final String ATTR_VERSION = "version";

    // Obsolete:  converted if present, but not resaved to disk.
    private static final String TAG_BLOCKED_PKGS = "blocked-packages";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_RANKING = "ranking";
    private static final String ATT_NAME = "name";
    private static final String ATT_PRIORITY = "priority";
    private static final String ATT_PEEKABLE = "peekable";

    private static final String ATT_KEYGUARD = "keyguard";
    private static final String ATT_STATUSBAR = "statusbar";
    private static final String ATT_SUBSCRIPT = "subscript";
    private static final String ATT_BANNER = "banner";

    private static final String ATTR_NAME = "name";


    private static final String ATT_VERSION = "version";
    private static final int XML_VERSION = 1;

    private static final String ATT_UID = "uid";
    private static final String ATT_VISIBILITY = "visibility";

    public ReadXmlFile(Context context){
        mContext = context;
        try {
            loadPolicyFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadPolicyFile() throws FileNotFoundException {

        //1文件路径读取方法
        File systemDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"//value_xml/notification_policy_default.xml");

//        判断文件是否存在
        if(systemDir.exists()){

            InputStream infile = new FileInputStream(systemDir); //读取存储卡目录下面的文件
            Toast.makeText(mContext,"存在RXFile3",Toast.LENGTH_SHORT).show(); //android 6.0路径下面此语句不能执行
            try {
    //                infile = mPolicyFile.openRead();
                    readPolicyXml(infile, false);
    //                Log.d("bobo.li", "mPolicyFile:"+mPolicyFile.getBaseFile().getAbsolutePath());
                } catch (FileNotFoundException e) {
                    // No data yet
                } catch (IOException e) {
                    Log.wtf(TAG, "Unable to read notification policy", e);
                } catch (NumberFormatException e) {
                    Log.wtf(TAG, "Unable to parse notification policy", e);
                } catch (XmlPullParserException e) {
                    Log.wtf(TAG, "Unable to parse notification policy", e);
                } finally {
        //                IOUtils.closeQuietly(infile);
        //            Toast.makeText(this,"zhixing",Toast.LENGTH_SHORT).show();
                }
            Toast.makeText(mContext,"存在RXFile",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(mContext,"不存在",Toast.LENGTH_SHORT).show();
        }
//        mPolicyFile = new AtomicFile(new File(systemDir, "notification_policy_default.xml"));
//        if (DBG)
//            Log.d(TAG, "loadPolicyFile");
//        synchronized(mPolicyFile) {
//            mBlockedPackages.clear();
//            Log.d("bobo.li2","systemDir:"+systemDir.toString());
//            FileInputStream infile = new InputStream(systemDir);

//        InputStream infile = this.getResources().openRawResource(R.raw.notification_policy_default);



    }

        private void readPolicyXml(InputStream stream, boolean forRestore)
            throws XmlPullParserException, NumberFormatException, IOException {
        //创建一个读xml文件的Pull实例
        final XmlPullParser parser = Xml.newPullParser();
        parser.setInput(stream, StandardCharsets.UTF_8.name());

        int type;
        String tag;
        int version = DB_VERSION;
        while ((type = parser.next()) != END_DOCUMENT) {
            tag = parser.getName();
            if (type == START_TAG) {
                if (TAG_NOTIFICATION_POLICY.equals(tag)) {
                    version = Integer.parseInt(
                            parser.getAttributeValue(null, ATTR_VERSION));
                }
                else if (TAG_BLOCKED_PKGS.equals(tag)) {
                    while ((type = parser.next()) != END_DOCUMENT) {
                        tag = parser.getName();
                        if (TAG_PACKAGE.equals(tag)) {
//                            mBlockedPackages.add(
//                                    parser.getAttributeValue(null, ATTR_NAME));
                        } else if (TAG_BLOCKED_PKGS.equals(tag) && type == END_TAG) {
                            break;
                        }
                    }
                }
            }
            readXml(parser);
        }
    }

    public void readXml(XmlPullParser parser)
            throws XmlPullParserException, IOException {
//        final PackageManager pm = mContext.getPackageManager();
        int type = parser.getEventType();
        if (type != XmlPullParser.START_TAG) return;
        String tag = parser.getName();
        if (!TAG_RANKING.equals(tag)) return;
//        mRecords.clear();
//        mRestoredWithoutUids.clear();
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
            tag = parser.getName();
            if (type == XmlPullParser.END_TAG && TAG_RANKING.equals(tag)) {
                return;
            }

            if (type == XmlPullParser.START_TAG) {
                if (TAG_PACKAGE.equals(tag)) {
//                    int uid = safeInt(parser, ATT_UID, Record.UNKNOWN_UID);

                    int priority = safeInt(parser, ATT_PRIORITY, 2);
                    boolean peekable = safeBool(parser, ATT_PEEKABLE, true);
//                    int vis = safeInt(parser, ATT_VISIBILITY, 1);
                    boolean keyguard = safeBool(parser,ATT_KEYGUARD, true);
                    boolean statusbar = safeBool(parser,ATT_STATUSBAR, true);
                    boolean subscript = safeBool(parser,ATT_SUBSCRIPT, true);
                    boolean banner = safeBool(parser,ATT_BANNER, true);

                    String name = parser.getAttributeValue(null, ATT_NAME);

                    if (!TextUtils.isEmpty(name)) {
/*
                        if (forRestore) {
                            try {
//                                uid = pm.getPackageUid(name, UserHandle.USER_OWNER);
                            } catch (Exception e) {
                                // noop
                            }
                        }
*/
                        Toast.makeText(mContext,"存在ReadXmlFile",Toast.LENGTH_SHORT).show();
//                        mXmlValue.setText(priority+":"+peekable+":"+keyguard+":"+statusbar+":"+subscript+":"+banner);

                    }
                }
            }
        }
        throw new IllegalStateException("Failed to reach END_DOCUMENT");
    }



    private static int safeInt(XmlPullParser parser, String att, int defValue) {
        final String val = parser.getAttributeValue(null, att);
        return tryParseInt(val, defValue);
    }

    private static int tryParseInt(String value, int defValue) {
        if (TextUtils.isEmpty(value)) return defValue;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    private static boolean safeBool(XmlPullParser parser, String att, boolean defValue) {
        final String val = parser.getAttributeValue(null, att);
        return tryParseBool(val, defValue);
    }

    private static boolean tryParseBool(String value, boolean defValue) {
        if (TextUtils.isEmpty(value)) return defValue;
        return Boolean.valueOf(value);
    }

}
