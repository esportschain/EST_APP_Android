package common.esportschain.esports.event;

/**
 * @author liangzhaoyou
 */
public class UMPushEvent {

    public String mType;
    public String mMsg;

    public UMPushEvent(String type, String msg) {
        this.mType = type;
        this.mMsg = msg;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }
}
