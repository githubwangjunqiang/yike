package com.yunyou.yike.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baidu.mapapi.search.core.PoiInfo;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.SpannerAdapterWorkerStyle;
import com.yunyou.yike.adapter.SpannerAdapterWorkerType;
import com.yunyou.yike.dagger2.DaggerWorkerCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.WorkerStyle;
import com.yunyou.yike.entity.WorkerType;
import com.yunyou.yike.http.cconstant.RxHttpConstant;
import com.yunyou.yike.presenter.DecorationWorkerPresenter;
import com.yunyou.yike.utils.To;
import com.yunyou.yike.utils.ZhengZebiaodashiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class DecorationWorkerActivity extends BaseMVPActivity<IView.IDecorationWorkerView,
        DecorationWorkerPresenter> implements IView.IDecorationWorkerView {
    private TextView mTextViewTitle, mTextViewAddress;
    private EditText mEditTextPrice;
    private ImageView mImageViewBack;
    private Spinner mSpinnerType, mSpinnerStyle;
    private TextView mButtonStartTime, mButtonEndTime;
    private int startyear, startMonth, startDay;
    private int yearEnd, monthEnd, dayOfMonthEnd;
    private LinearLayout mLinearLayoutAddress, mLinearLayoutWorkNum;
    private SpannerAdapterWorkerType mSpannerAdapterWorkerType;
    private SpannerAdapterWorkerStyle mSpannerAdapterWorkerStyle;
    private List<WorkerType.DataBean> mWorkers;
    private List<WorkerStyle.DataBean> mWorkerStyles;
    private final String PRICE = "￥";
    @Inject
    DecorationWorkerPresenter mPresenter;

    @Override
    protected int getStateLayoutID() {
        return R.id.worker_statelayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.lock_worker_layout;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_decoration_worker;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);
        mSpinnerStyle = optionView(R.id.worker_spstyle);
        mLinearLayoutWorkNum = optionView(R.id.worker_number);
        mButtonStartTime = optionView(R.id.worker_btnstarttime);
        mTextViewAddress = optionView(R.id.textView_address);
        mSpinnerType = optionView(R.id.worker_sptype);
        mButtonEndTime = optionView(R.id.worker_btn_endtime);
        mEditTextPrice = optionView(R.id.map_address_price);
        mLinearLayoutAddress = optionView(R.id.worker_map_address);
        mTextViewTitle.setText(R.string.zhuangxiugongren);
        mTextViewTitle.setText(R.string.wanshandingdan);

        startRefresh(null);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//返回
                finish();
            }
        });
        mButtonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//开始时间
                showTimeDialog(true, mButtonStartTime);
            }
        });
        mButtonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//结束时间
                showTimeDialog(false, mButtonEndTime);
            }
        });
        mLinearLayoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击选择地址
                Intent intent = new Intent(DecorationWorkerActivity.this, MapAddressActivity.class);
                startActivity(intent);
            }
        });
        mLinearLayoutWorkNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击选择人数 工种

            }
        });
        mEditTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1 && !ZhengZebiaodashiUtils.isNumeric(s.toString())) {
                    mEditTextPrice.setText(null);
                    To.oo("请先输入数字");
                    return;
                }

                if (s.length() > 0 && !s.toString().contains(PRICE)) {
                    mEditTextPrice.setText(PRICE + s);
                    mEditTextPrice.setSelection((PRICE + s).length());
                } else if (s.length() < 2 && s.toString().contains(PRICE)) {
                    mEditTextPrice.setText(null);
                }
            }
        });

    }

    private PoiInfo mPoiInfo;

    @Override
    protected void rogerMessage(EventBusMessage message) {
        if (message.getMsgCode() == EventBusMessage.MAPADDRESS) {
            if (message.getObject() instanceof PoiInfo) {
                mPoiInfo = (PoiInfo) message.getObject();
                String address = mPoiInfo.city + "\n" + mPoiInfo.address;
                if (!TextUtils.isEmpty(address)) {
                    mTextViewAddress.setText(address);
                }
            }
        }
    }

    @Override
    public void startRefresh(Object object) {
        mPresenter.loodWorkType();
    }

    @Override
    protected DecorationWorkerPresenter mPresenterCreate() {
        DaggerWorkerCompcoent.builder().presenterMobule(new PresenterMobule())
                .appCompcoent(((App) getApplication()).getAppCompcoent())
                .build().inJect(this);
        return mPresenter;
    }

    /**
     * 显示时间选择对话框
     */
    private void showTimeDialog(final boolean isStartTime, final TextView button) {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, null, 0, 0, 0);
        if (isStartTime) {
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            if (startyear != 0) {
                datePickerDialog.getDatePicker().updateDate(startyear, startMonth - 1, startDay);
            }
            if (yearEnd != 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(yearEnd, (monthEnd - 1), dayOfMonthEnd);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            }
        } else {
            if (yearEnd != 0) {
                datePickerDialog.getDatePicker().updateDate(yearEnd, monthEnd - 1, dayOfMonthEnd);
            }
            if (startyear > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(startyear, (startMonth - 1), startDay);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            } else {
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            }
        }
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DatePicker datePicker = datePickerDialog.getDatePicker();
                int nY = datePicker.getYear();
                int nM = datePicker.getMonth() + 1;
                int nD = datePicker.getDayOfMonth();
                button.setText(nY + "." + nM + "." + nD);
                if (isStartTime) {
                    startyear = nY;
                    startMonth = nM;
                    startDay = nD;
                    showTimePictureDailog(button);
                } else {
                    yearEnd = nY;
                    monthEnd = nM;
                    dayOfMonthEnd = nD;
                }
            }
        });

        datePickerDialog.show();
    }

    /**
     * 显示  小时时间对话框
     *
     * @param button
     */
    private void showTimePictureDailog(final TextView button) {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time;
                if (hourOfDay < 10) {
                    time = "0" + hourOfDay;
                } else {
                    time = hourOfDay + "";
                }
                button.append(" -- " + time + ":" + (minute == 30 ? "30" : "00"));
            }
        }, 0, 0, true).show();
    }


    @Override
    public void showWorkerTypeSuccess(WorkerType workerType) {
        if (mWorkers == null) {
            mWorkers = new ArrayList<>();
        }
        mWorkers.clear();
        mWorkers.add(new WorkerType.DataBean(getString(R.string.qingxuanzegonzhong)));
        mWorkers.addAll(workerType.getData());
        if (mSpannerAdapterWorkerType != null) {
            mSpannerAdapterWorkerType.notifyDataSetChanged();
        } else {
            mSpannerAdapterWorkerType = new SpannerAdapterWorkerType(this, mWorkers);
            mSpinnerType.setAdapter(mSpannerAdapterWorkerType);
            mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    To.oo(mWorkers.get(position).getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        mPresenter.loodWorkStyle();
    }

    @Override
    public void showWorkerStyleSuccess(WorkerStyle workerStyle) {
        if (mWorkerStyles == null) {
            mWorkerStyles = new ArrayList<>();
        }
        mWorkerStyles.clear();
        mWorkerStyles.add(new WorkerStyle.DataBean(getString(R.string.qingxuanzegonzhong)));
        mWorkerStyles.addAll(workerStyle.getData());
        if (mSpannerAdapterWorkerStyle != null) {
            mSpannerAdapterWorkerStyle.notifyDataSetChanged();
        } else {
            mSpannerAdapterWorkerStyle = new SpannerAdapterWorkerStyle(this, mWorkerStyles);
            mSpinnerStyle.setAdapter(mSpannerAdapterWorkerStyle);
            mSpinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    To.oo(mWorkerStyles.get(position).getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    /*
     *  点击协议
     */
    public void toWebView(View view) {
        WebViewActivity.startWebViewActivity(this, RxHttpConstant.YIGEXIEYI,
                "艺科协议");
    }

    /*
     *  点击确定按钮
     */
    public void submit(View view) {
        To.oo("还没开发 等徐旭");
    }
}
