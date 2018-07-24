package common.esportschain.esports.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 * @author liangzhaoyou
 * @date 2018/6/11
 */
@Entity
public class UserInfo {

    @Id
    private Long id;

    @Property(nameInDb = "uid")
    private String UId;

    @Property(nameInDb = "Email")
    private String Email;

    @Property(nameInDb = "nickName")
    private String NickName;

    @Property(nameInDb = "avatar")
    private String Avatar;

    @Property(nameInDb = "password")
    private String Password;

    @Property(nameInDb = "isProducer")
    private String IsProducer;

    @Property(nameInDb = "key")
    private String Key;

    @Property(nameInDb = "token")
    private String token;

    @Property(nameInDb = "authkey")
    private int authkey;

    @Property(nameInDb = "authkeys")
    private String authkeys;


    @Property(nameInDb = "oneKey")
    private String oneKey;

    @Property(nameInDb = "twoKey")
    private String twoKey;

    @Property(nameInDb = "threeKey")
    private String threeKey;

    @Property(nameInDb = "fourKey")
    private String fourKey;





    @Property(nameInDb = "ontInt")
    private int oneInt;

    @Property(nameInDb = "threeInt")
    private int threeInt;


    @Property(nameInDb = "twoInt")
    private int twoInt;

    @Generated(hash = 2131181706)
    public UserInfo(Long id, String UId, String Email, String NickName, String Avatar,
            String Password, String IsProducer, String Key, String token, int authkey,
            String authkeys, String oneKey, String twoKey, String threeKey, String fourKey,
            int oneInt, int threeInt, int twoInt) {
        this.id = id;
        this.UId = UId;
        this.Email = Email;
        this.NickName = NickName;
        this.Avatar = Avatar;
        this.Password = Password;
        this.IsProducer = IsProducer;
        this.Key = Key;
        this.token = token;
        this.authkey = authkey;
        this.authkeys = authkeys;
        this.oneKey = oneKey;
        this.twoKey = twoKey;
        this.threeKey = threeKey;
        this.fourKey = fourKey;
        this.oneInt = oneInt;
        this.threeInt = threeInt;
        this.twoInt = twoInt;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public String getNickName() {
        return this.NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getAvatar() {
        return this.Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getIsProducer() {
        return this.IsProducer;
    }

    public void setIsProducer(String IsProducer) {
        this.IsProducer = IsProducer;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOneKey() {
        return this.oneKey;
    }

    public void setOneKey(String oneKey) {
        this.oneKey = oneKey;
    }

    public String getTwoKey() {
        return this.twoKey;
    }

    public void setTwoKey(String twoKey) {
        this.twoKey = twoKey;
    }

    public String getThreeKey() {
        return this.threeKey;
    }

    public void setThreeKey(String threeKey) {
        this.threeKey = threeKey;
    }

    public int getOneInt() {
        return this.oneInt;
    }

    public void setOneInt(int oneInt) {
        this.oneInt = oneInt;
    }

    public int getTwoInt() {
        return this.twoInt;
    }

    public void setTwoInt(int twoInt) {
        this.twoInt = twoInt;
    }

    public String getUId() {
        return this.UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getKey() {
        return this.Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAuthkey() {
        return this.authkey;
    }

    public void setAuthkey(int authkey) {
        this.authkey = authkey;
    }

    public String getFourKey() {
        return this.fourKey;
    }

    public void setFourKey(String fourKey) {
        this.fourKey = fourKey;
    }

    public int getThreeInt() {
        return this.threeInt;
    }

    public void setThreeInt(int threeInt) {
        this.threeInt = threeInt;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAuthkeys() {
        return this.authkeys;
    }

    public void setAuthkeys(String authkeys) {
        this.authkeys = authkeys;
    }

}
