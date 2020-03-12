package iacaasystem.entity;

/**
 *  @author: ZhaoZezhong
 *  @advertisement: welcome to https://zhaozezhong.fun
 *  @Date: 2020/2/24 17:50
 *  @Description: Admin基础类
 */
public class Admin {
    private String account;
    private String passWord;
    private String adminName;
    private String adminPhonenumber;

    public Admin(String account, String passWord, String adminName, String adminPhonenumber) {
        this.account = account;
        this.passWord = passWord;
        this.adminName = adminName;
        this.adminPhonenumber = adminPhonenumber;
    }

    public Admin() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhonenumber() {
        return adminPhonenumber;
    }

    public void setAdminPhonenumber(String adminPhonenumber) {
        this.adminPhonenumber = adminPhonenumber;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "account='" + account + '\'' +
                ", passWord='" + passWord + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPhonenumber='" + adminPhonenumber + '\'' +
                '}';
    }
}
