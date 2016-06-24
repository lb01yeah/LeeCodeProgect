package com.lee.android.readxml;

/**
 * Created by android on 16-6-23.
 */
public class WriteXmlFile {

    /*
    //写入数据
    private void handleSavePolicyFile() {
        if (DBG) Log.d(TAG, "handleSavePolicyFile");
        synchronized (mPolicyFile) {
            final FileOutputStream stream;
            try {
                stream = mPolicyFile.startWrite();
            } catch (IOException e) {
                Log.w(TAG, "Failed to save policy file", e);
                return;
            }

            try {
                writePolicyXml(stream, false *//*forBackup*//*);
                mPolicyFile.finishWrite(stream);
            } catch (IOException e) {
                Log.w(TAG, "Failed to save policy file, restoring backup", e);
                mPolicyFile.failWrite(stream);
            }
        }
//        BackupManager.dataChanged(getContext().getPackageName());
    }

    private void writePolicyXml(OutputStream stream, boolean forBackup) throws IOException {
        XmlSerializer out = new XmlSerializer();
        out.setOutput(stream, StandardCharsets.UTF_8.name());
        out.startDocument(null, true);
        out.startTag(null, TAG_NOTIFICATION_POLICY);
        out.attribute(null, ATTR_VERSION, Integer.toString(DB_VERSION));
//        mZenModeHelper.writeXml(out, forBackup);
//        mRankingHelper.writeXml(out, forBackup);
        writeXml(out, forBackup);
        out.endTag(null, TAG_NOTIFICATION_POLICY);
        out.endDocument();
    }

    public void writeXml(XmlSerializer out, boolean forBackup) throws IOException {
        out.startTag(null, TAG_RANKING);
        out.attribute(null, ATT_VERSION, Integer.toString(XML_VERSION));

        final int N = mRecords.size();
        for (int i = 0; i < N; i++) {
            final Record r = mRecords.valueAt(i);
            if (forBackup && UserHandle.getUserId(r.uid) != UserHandle.USER_OWNER) {
                continue;
            }
            out.startTag(null, TAG_PACKAGE);
            out.attribute(null, ATT_NAME, r.pkg);
            *//*if (r.priority != DEFAULT_PRIORITY) {
                out.attribute(null, ATT_PRIORITY, Integer.toString(r.priority));
            }
            if (r.peekable != DEFAULT_PEEKABLE) {
                out.attribute(null, ATT_PEEKABLE, Boolean.toString(r.peekable));
            }
            if (r.visibility != DEFAULT_VISIBILITY) {
                out.attribute(null, ATT_VISIBILITY, Integer.toString(r.visibility));
            }
            if (r.keyguard != DEFAULT_KEYGUARD) {
                out.attribute(null, ATT_KEYGUARD, Boolean.toString(r.keyguard));
            }
            if (r.statusbar != DEFAULT_STATUSBAR) {
                out.attribute(null, ATT_STATUSBAR, Boolean.toString(r.statusbar));
            }
            if (r.subscript != DEFAULT_SUBSCRIPT) {
                out.attribute(null, ATT_SUBSCRIPT, Boolean.toString(r.subscript));
            }
            if (r.banner != DEFAULT_BANNER) {
                out.attribute(null, ATT_BANNER, Boolean.toString(r.banner));
            }
            if (!forBackup) {
                out.attribute(null, ATT_UID, Integer.toString(r.uid));
            }*//*
            out.endTag(null, TAG_PACKAGE);
        }
        out.endTag(null, TAG_RANKING);
    }*/

}
