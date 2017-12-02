package com.yunyou.yike.ui_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.yunyou.yike.R;
import com.yunyou.yike.adapter.MyAdapter;
import com.yunyou.yike.entity.WorkerType;
import com.yunyou.yike.utils.To;

import java.util.List;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogWorkerType extends Dialog {
    private CallBack mCallBack;
    private Adapter mAdapter;
    private Button mButton;
    private List<WorkerType.DataBean> list;


    public DiaLogWorkerType(Context context, List<WorkerType.DataBean> list, String type_of_work, CallBack callBack) {
        super(context, R.style.dialogWindowAnimPhoto);
        this.mCallBack = callBack;
        this.list = list;
        init(type_of_work);


    }

    /**
     * 初始化数据
     *
     * @param type_of_work
     */
    private void init(String type_of_work) {
        try {
            if (!TextUtils.isEmpty(type_of_work)) {
                if (type_of_work.contains(",")) {
                    String[] split = type_of_work.split(",");
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < split.length; j++) {
                            if (list.get(i).getId().equals(split[j])) {
                                list.get(i).setChecked(true);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(type_of_work)) {
                            list.get(i).setChecked(true);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ListView mXListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_workertype);
        mXListView = (ListView) findViewById(R.id.dialog_listview_workertyeo);
        mButton = (Button) findViewById(R.id.dialog_worker_btn);
        setAdapter();
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                StringBuffer buffername = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked()) {
                        buffer.append(list.get(i).getId()).append(",");
                        buffername.append(list.get(i).getName()).append("-");
                    }
                }
                if (TextUtils.isEmpty(buffer)) {
                    To.ee("你还没有选择任何工种");
                    return;
                }
                buffer.deleteCharAt(buffer.length() - 1);
                buffername.deleteCharAt(buffer.length() - 1);
                if (mCallBack != null) {
                    mCallBack.submit(buffer.toString(), buffername.toString());
                    dismiss();
                }
            }
        });
    }


    public interface CallBack {
        void submit(String stringId, String names);
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter = new Adapter(getContext(), list);
            mXListView.setAdapter(mAdapter);
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnimPhoto);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private class Adapter extends MyAdapter<WorkerType.DataBean> {

        public Adapter(Context context, List<WorkerType.DataBean> data) {
            super(context, data);
        }

        @Override
        public View getView(int position, android.view.View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.itme_dialog_workertypr, null);
                holder.tv = (CheckedTextView) view.findViewById(R.id.itme_dialog_worker_tv);
                view.setTag(holder);
            }
            holder = (ViewHolder) view.getTag();
            final WorkerType.DataBean dataBean = getData().get(position);
            if (dataBean != null) {
                holder.tv.setText(dataBean.getName() + "");
                holder.tv.setChecked(dataBean.isChecked());
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBean.setChecked(!dataBean.isChecked());
                        notifyDataSetChanged();
                    }
                });
            }
            return view;
        }

    }

    private class ViewHolder {
        CheckedTextView tv;
    }


}
