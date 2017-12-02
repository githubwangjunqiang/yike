package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/20.
 */

public class BankList {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"3","bank_name":"农业银行","account_bank":"6222000000000000","is_default":"1"},{"id":"2","bank_name":"农业银行","account_bank":"6222000000000000","is_default":"0"}]
     */

    private int retcode;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3
         * bank_name : 农业银行
         * account_bank : 6222000000000000
         * is_default : 1
         */

        private String id;
        private String bank_name;
        private String account_bank;
        private String is_default;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getAccount_bank() {
            return account_bank;
        }

        public void setAccount_bank(String account_bank) {
            this.account_bank = account_bank;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
