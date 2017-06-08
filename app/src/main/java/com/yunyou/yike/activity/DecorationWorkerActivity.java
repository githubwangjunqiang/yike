package com.yunyou.yike.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baidu.mapapi.model.LatLng;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.SpannerAdapterWorkerStyle;
import com.yunyou.yike.adapter.SpannerAdapterWorkerType;
import com.yunyou.yike.dagger2.DaggerWorkerCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.CityId;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.Myaddress;
import com.yunyou.yike.entity.OrderSuccess;
import com.yunyou.yike.entity.WorkerStyle;
import com.yunyou.yike.entity.WorkerType;
import com.yunyou.yike.http.cconstant.RxHttpConstant;
import com.yunyou.yike.presenter.DecorationWorkerPresenter;
import com.yunyou.yike.utils.DateUtil;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;
import com.yunyou.yike.utils.ZhengZebiaodashiUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class DecorationWorkerActivity extends BaseMVPActivity<IView.IDecorationWorkerView,
        DecorationWorkerPresenter> implements IView.IDecorationWorkerView {
    private TextView mTextViewTitle, mTextViewAddress;
    private EditText mEditTextPrice, mEditTextPeoPle, mEditTextEwmarks;
    private ImageView mImageViewBack;
    private CheckBox mCheckBox;
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
    private static final String DECORATING = "isDecoratingWorkers";
    @Inject
    DecorationWorkerPresenter mPresenter;
    private String order_type = "1";//1（装修订单） 2 （建筑订单） 3 （安装订单） 4（团队订

    /**
     * 启动发布找工人订单
     *
     * @param context    上下问
     * @param order_type 工作类型
     */
    public static void startDecorationWorkerActivity(Context context, String order_type) {
        Intent intent = new Intent(context, DecorationWorkerActivity.class);
        intent.putExtra(DECORATING, order_type);
        context.startActivity(intent);
    }

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
        order_type = getIntent().getStringExtra(DECORATING);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mCheckBox = optionView(R.id.worker_checkox);
        mImageViewBack = optionView(R.id.title_ivback);
        mSpinnerStyle = optionView(R.id.worker_spstyle);
        mEditTextPeoPle = optionView(R.id.textView3);
        mEditTextEwmarks = optionView(R.id.editText);
        mLinearLayoutWorkNum = optionView(R.id.worker_number);
        mButtonStartTime = optionView(R.id.worker_btnstarttime);
        mTextViewAddress = optionView(R.id.textView_address);
        mSpinnerType = optionView(R.id.worker_sptype);
        mButtonEndTime = optionView(R.id.worker_btn_endtime);
        mEditTextPrice = optionView(R.id.map_address_price);
        mLinearLayoutAddress = optionView(R.id.worker_map_address);
        mTextViewTitle.setText(R.string.zhuangxiugongren);
        mTextViewTitle.setText(R.string.wanshandingdan);

        startRefresh(false);
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

    private Myaddress mMyaddress;

    @Override
    protected void rogerMessage(EventBusMessage message) {
        if (message.getMsgCode() == EventBusMessage.MAPADDRESS) {
            if ((message.getObject() != null) && (message.getObject() instanceof Myaddress)) {
                mMyaddress = (Myaddress) message.getObject();
                String address = mMyaddress.getAddress() + "\n" + mMyaddress.getAddressInfo();
                if (!TextUtils.isEmpty(address)) {
                    mTextViewAddress.setText(address);
                }
                String city = mMyaddress.getCity();
                if (!TextUtils.isEmpty(city)) {
                    int indexOf = city.lastIndexOf("市");
                    if (indexOf != -1) {
                        String substring = city.substring(0, indexOf);
                        mMyaddress.setCity(substring);
                    }
                }
            }
        }
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
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

    private String WorkerTypeID;

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
                    WorkerTypeID = mWorkers.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if (order_type.equals("1")) {
            mPresenter.accidentServer();
        } else {
            mPresenter.loodWorkStyle();
        }
    }

    private String WorkerStyleID;

    @Override
    public void showWorkerStyleSuccess(WorkerStyle workerStyle) {
        setWorkerAdapter(workerStyle);


    }

    /**
     * 设置风格 或者 额外服务
     *
     * @param workerStyle
     */
    private void setWorkerAdapter(WorkerStyle workerStyle) {
        if (mWorkerStyles == null) {
            mWorkerStyles = new ArrayList<>();
        }
        mWorkerStyles.clear();
        mWorkerStyles.add(new WorkerStyle.DataBean(getString(R.string.qingxuanzefengge)));
        mWorkerStyles.addAll(workerStyle.getData());
        if (mSpannerAdapterWorkerStyle != null) {
            mSpannerAdapterWorkerStyle.notifyDataSetChanged();
        } else {
            mSpannerAdapterWorkerStyle = new SpannerAdapterWorkerStyle(this, mWorkerStyles);
            mSpinnerStyle.setAdapter(mSpannerAdapterWorkerStyle);
            mSpinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    WorkerStyleID = mWorkerStyles.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void releaseOrdersSuccess(OrderSuccess orderSuccess) {
        To.dd(orderSuccess.getMsg());
        finish();
    }

    @Override
    public void accidentServerSuccess(WorkerStyle orderSuccess) {
        setWorkerAdapter(orderSuccess);
    }

    @Override
    public void getCityIdSuccess(CityId strings) {

        CityId.DataBean data = strings.getData();
        if (mMyaddress.getProvince().equals(data.getName())) {
            mMyaddress.setProvinceId(data.getId());
        }
        if (mMyaddress.getCity().equals(data.getName())) {
            mMyaddress.setCityId(data.getId());
        }
        if (mMyaddress.getCounty().equals(data.getName())) {
            mMyaddress.setCountyId(data.getId());
        }
        if (!TextUtils.isEmpty(mMyaddress.getProvinceId()) &&
                !TextUtils.isEmpty(mMyaddress.getCityId()) &&
                !TextUtils.isEmpty(mMyaddress.getCountyId())) {
            releaseOrders();
        }

    }

    /**
     * 正式发布订单
     */
    private void releaseOrders() {
        try {
            Map<String, String> map = new ArrayMap<>();
            map.put("province_id", mMyaddress.getProvinceId());
            map.put("city_id", mMyaddress.getCityId());
            map.put("district", mMyaddress.getCountyId());
            map.put("address", mMyaddress.getAddress() + mMyaddress.getAddressInfo());


            Calendar calendar = Calendar.getInstance();
            calendar.set(startyear, startMonth, startDay);
            Calendar calendarend = Calendar.getInstance();
            calendarend.set(yearEnd, monthEnd, dayOfMonthEnd);
            int submitCountDay = DateUtil.getSubmitCountDay(calendar.getTimeInMillis(),
                    calendarend.getTimeInMillis());


            map.put("day_num", submitCountDay + "");
            map.put("start_time", String.valueOf(calendar.getTimeInMillis() / 1000));
            map.put("end_time", String.valueOf(calendarend.getTimeInMillis() / 1000));


            map.put("work_type", WorkerTypeID);
            map.put("people_num", mEditTextPeoPle.getText().toString().trim());
            if ("1".equals(order_type)) {//装潢
                map.put("style", WorkerStyleID);
            } else {
                map.put("accident_server", WorkerStyleID);
            }
            map.put("remarks", mEditTextEwmarks.getText().toString().trim());
            String money = mEditTextPrice.getText().toString().trim();
            if (money.contains("￥")) {
                money = money.substring(1, money.length());
            }
            map.put("money", money);
            map.put("order_type", order_type);
            LatLng latLng = mMyaddress.getLatLng();
            if (latLng == null) {
                To.ss(mEditTextEwmarks, "亲，您的经纬度缺失，请重新选择地址吧");
                return;
            }
            map.put("j_du", String.valueOf(latLng.longitude));
            map.put("w_du", String.valueOf(latLng.latitude));
            map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
            mPresenter.releaseOrders(map);
        } catch (Exception e) {
            e.printStackTrace();
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


        if (startyear == 0 || startMonth == 0 || startDay == 0) {
            To.ss(view, "请选择开始时间");
            return;
        }
        if (yearEnd == 0 || monthEnd == 0 || dayOfMonthEnd == 0) {
            To.ss(view, "请选择结束时间");
            return;
        }
        if (TextUtils.isEmpty(WorkerTypeID)) {
            To.ss(view, "请选择工种");
            return;
        }
        if (TextUtils.isEmpty(WorkerStyleID)) {
            To.ss(view, "请选择风格或者额外服务");
            return;
        }
        String trim = mEditTextPeoPle.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.ss(view, "请选择需要的人数");
            return;
        }
        String remarks = mEditTextEwmarks.getText().toString().trim();
        if (TextUtils.isEmpty(remarks)) {
            To.ss(view, "请输入一两句留言吧亲");
            return;
        }
        String money = mEditTextPrice.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            To.ss(view, "请输入总价");
            return;
        }
        if (!mCheckBox.isChecked()) {
            To.ss(view, "请阅读艺科协议，同意后请勾选我已阅读");
            return;
        }


        if (mMyaddress == null) {
            To.ss(view, "请选择地址");
            return;
        }
        mMyaddress.setProvinceId(null);
        mMyaddress.setCityId(null);
        mMyaddress.setCityId(null);

        Map<String, String> mapProvince = new ArrayMap<>();
        mapProvince.put("name", mMyaddress.getProvince());
        mPresenter.getCityId(mapProvince);
        Map<String, String> mapCity = new ArrayMap<>();
        mapCity.put("name", mMyaddress.getCity());
        mPresenter.getCityId(mapCity);
        Map<String, String> mapCounty = new ArrayMap<>();
        mapCounty.put("name", mMyaddress.getCounty());
        mPresenter.getCityId(mapCounty);
    }
}
