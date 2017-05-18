package com.yunyou.yike.model;

import android.os.Handler;

import com.yunyou.yike.entity.JobList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/5/18.
 */

public class JobActivityModel implements IModel.IJobActivityModel {
    @Override
    public void getJobData(final IModel.AsyCallback callback) {
        callback.startModel(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<JobList> lists = new ArrayList<JobList>();
                for (int i = 0; i < 3; i++) {
                    lists.add(new JobList("name", 0));
                }
                callback.success(lists);
            }
        }, 2000);
    }
}
