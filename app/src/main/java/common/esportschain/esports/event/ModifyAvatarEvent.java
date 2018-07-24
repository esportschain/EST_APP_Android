package common.esportschain.esports.event;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/20
 */

public class ModifyAvatarEvent {
    public String modifyAvatar;

    public ModifyAvatarEvent(String modifyAvatar) {
        this.modifyAvatar = modifyAvatar;
    }

    public String getMidifyAvatar() {
        return modifyAvatar;
    }

    public void setMidifyAvatar(String modifyAvatar) {
        this.modifyAvatar = modifyAvatar;
    }
}
