package common.esportschain.esports.event;

/**
 * @author liangzhaoyou
 */
public class UMPushDeviceEvent {

    public String mDeviceEvent;

    public UMPushDeviceEvent(String device) {
        this.mDeviceEvent = device;
    }

    public String getmDeviceEvent() {
        return mDeviceEvent;
    }

    public void setmDeviceEvent(String mDeviceEvent) {
        this.mDeviceEvent = mDeviceEvent;
    }
}
