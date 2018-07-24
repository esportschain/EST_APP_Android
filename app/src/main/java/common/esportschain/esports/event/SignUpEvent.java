package common.esportschain.esports.event;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/19
 *
 * 注册成功发送一个事件的总订阅
 */

public class SignUpEvent {

    public String mSignUpSuccess;

    public SignUpEvent(String SignUpSuccess) {
        this.mSignUpSuccess = SignUpSuccess;
    }

    public String getmSignUpSuccess() {
        return mSignUpSuccess;
    }

    public void setmSignUpSuccess(String mSignUpSuccess) {
        this.mSignUpSuccess = mSignUpSuccess;
    }
}
