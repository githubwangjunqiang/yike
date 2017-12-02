package com.yunyou.yike.entity;

/**
 * Created by ${王俊强} on 2017/6/9.
 */

public class MyUserInfo {


    /**
     * retcode : 2000
     * msg : 获取成功
     * data : {"user_id":"18","mobile":"15666666666","email":"","head_pic":"","password":"d0dcbf0d12a6b1e7fbfa2ce5848f3eff","sex":"1","birthday":"0","pay_points":"0","reg_time":"1496995674","last_login":"1496995682","card_pic":null,"oauth":"","openid":null,"province":"0","city":"0","district":"0","nickname":"15666666666","total_money":"0.00","is_lock":"1","qq":"","token":"2e2873c5f56e268ea3e04083ef8a7b5e","r_token":"QVNYu3t/HfUqREC3TbOJ7f7OQuzwXycX9PvErBs nNHyGrEJGRN nDco 1v58EICRP7IzPJ17cCfuzf2f Fa1a44knq1usr5","device_num":"140fe1da9e9d61023bc","login_count":"1","region_type":"0","invite_num":null,"invite_user":"0","type_of_work":null,"hobby":null,"points":"0","rank":"0.0","good_comment":"0","j_du":null,"w_du":null,"work_count":null,"user_name":null,"is_listen":"1","card_no":null,"tel":"15666666666"}
     */

    private int retcode;
    private String msg;
    private DataBean data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 18
         * mobile : 15666666666
         * email :
         * head_pic :
         * password : d0dcbf0d12a6b1e7fbfa2ce5848f3eff
         * sex : 1
         * birthday : 0
         * pay_points : 0
         * reg_time : 1496995674
         * last_login : 1496995682
         * card_pic : null    //证件照
         * oauth :
         * openid : null
         * province : 0
         * city : 0
         * district : 0
         * nickname : 15666666666
         * total_money : 0.00
         * is_lock : 1
         * qq :
         * token : 2e2873c5f56e268ea3e04083ef8a7b5e
         * r_token : QVNYu3t/HfUqREC3TbOJ7f7OQuzwXycX9PvErBs nNHyGrEJGRN nDco 1v58EICRP7IzPJ17cCfuzf2f Fa1a44knq1usr5
         * device_num : 140fe1da9e9d61023bc
         * login_count : 1
         * region_type : 0
         * invite_num : null
         * invite_user : 0
         * type_of_work : null
         * hobby : null
         * points : 0
         * rank : 0.0
         * good_comment : 0
         * j_du : null
         * w_du : null
         * work_count : null
         * user_name : null
         * is_listen : 1
         * card_no : null  //身份证号码
         * tel : 15666666666
         */

        private String user_id;
        private String mobile;
        private String email;
        private String head_pic;
        private String password;
        private String sex;
        private String birthday;
        private String pay_points;
        private String reg_time;
        private String last_login;
        private String card_pic;
        private String oauth;
        private String openid;
        private String province;
        private String city;
        private String district;
        private String nickname;
        private String total_money;
        private String is_lock;
        private String qq;
        private String token;
        private String r_token;
        private String device_num;
        private String login_count;
        private String region_type;
        private String invite_num;
        private String invite_user;
        private String type_of_work;
        private String hobby;
        private String points;
        private String rank;
        private String good_comment;
        private String j_du;
        private String w_du;
        private String work_count;
        private String user_name;
        private String is_listen;
        private String card_no;
        private String tel;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPay_points() {
            return pay_points;
        }

        public void setPay_points(String pay_points) {
            this.pay_points = pay_points;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getCard_pic() {
            return card_pic;
        }

        public void setCard_pic(String card_pic) {
            this.card_pic = card_pic;
        }

        public String getOauth() {
            return oauth;
        }

        public void setOauth(String oauth) {
            this.oauth = oauth;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getIs_lock() {
            return is_lock;
        }

        public void setIs_lock(String is_lock) {
            this.is_lock = is_lock;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getR_token() {
            return r_token;
        }

        public void setR_token(String r_token) {
            this.r_token = r_token;
        }

        public String getDevice_num() {
            return device_num;
        }

        public void setDevice_num(String device_num) {
            this.device_num = device_num;
        }

        public String getLogin_count() {
            return login_count;
        }

        public void setLogin_count(String login_count) {
            this.login_count = login_count;
        }

        public String getRegion_type() {
            return region_type;
        }

        public void setRegion_type(String region_type) {
            this.region_type = region_type;
        }

        public String getInvite_num() {
            return invite_num;
        }

        public void setInvite_num(String invite_num) {
            this.invite_num = invite_num;
        }

        public String getInvite_user() {
            return invite_user;
        }

        public void setInvite_user(String invite_user) {
            this.invite_user = invite_user;
        }

        public String getType_of_work() {
            return type_of_work;
        }

        public void setType_of_work(String type_of_work) {
            this.type_of_work = type_of_work;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getGood_comment() {
            return good_comment;
        }

        public void setGood_comment(String good_comment) {
            this.good_comment = good_comment;
        }

        public String getJ_du() {
            return j_du;
        }

        public void setJ_du(String j_du) {
            this.j_du = j_du;
        }

        public String getW_du() {
            return w_du;
        }

        public void setW_du(String w_du) {
            this.w_du = w_du;
        }

        public String getWork_count() {
            return work_count;
        }

        public void setWork_count(String work_count) {
            this.work_count = work_count;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIs_listen() {
            return is_listen;
        }

        public void setIs_listen(String is_listen) {
            this.is_listen = is_listen;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
