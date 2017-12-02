package com.yunyou.yike.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.angel.adapter.SWRecyclerAdapter;
import com.angel.adapter.SWViewHolder;
import com.yan.pullrefreshlayout.utils.StringReplaceUtil;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.BankList;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/20.
 */

public class BankAdapter extends SWRecyclerAdapter<BankList.DataBean> {
    public BankAdapter(Context context, List<BankList.DataBean> list) {
        super(context, list);
    }

    private boolean editing = false;
    private int index;
    private boolean delft;

    public boolean isDelft() {
        return delft;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        index++;
        this.editing = editing;
        if (editing) {
            delft = false;
        }
        notifyDataSetChanged();
    }

    @Override
    protected void setAutoUtilsView(View view) {
        AutoUtils.auto(view);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.itme_bank_list;
    }

    @Override
    public void bindData(SWViewHolder holder, final int position, final BankList.DataBean item) {
        if (item != null) {
            if (item.getBank_name() != null) {
                holder.setText(R.id.itme_banktvname, item.getBank_name());
            }
            if (item.getAccount_bank() != null) {
                String account_bank = item.getAccount_bank();
                if (account_bank.length() > 4) {
                    account_bank = StringReplaceUtil.bankCardReplaceWithStar(account_bank);
                }
                holder.setText(R.id.itme_banktvnumber, account_bank);
            }
            RadioButton mButton = (RadioButton) holder.getView(R.id.btn_itme);
            if (item.getIs_default().equals("0")) {
                mButton.setChecked(false);
            } else {
                if (!mButton.isChecked()) {
                    mButton.setChecked(true);
                }
            }

            showButton(mButton, holder.getView(R.id.itme_cardlayout));
            mButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && item.getIs_default().equals("0")) {
                        for (int i = 0; i < getList().size(); i++) {
                            getList().get(i).setIs_default("0");
                        }
                        getList().get(position).setIs_default("1");
                        delft = true;
                        notifyDataSetChanged();
                    }
                }
            });

        }

    }

    /**
     * 获取选择的默认id
     */
    public String getId() {
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getIs_default().equals("1")) {
                return getList().get(i).getId();
            }
        }
        return null;
    }

    /**
     * 显示按钮
     *
     * @param mButton
     */
    private void showButton(final RadioButton mButton, final View view) {
        if (editing) {
            if (mButton.getVisibility() == View.GONE) {
                mButton.setVisibility(View.VISIBLE);

                ObjectAnimator animator = ObjectAnimator.ofFloat(mButton, "scale",
                        0F, 1F);
                animator.setDuration(500);
                animator.setInterpolator(new OvershootInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animan) {
                        Float animatedValue = (Float) animan.getAnimatedValue();
                        mButton.setScaleY(animatedValue);
                        mButton.setScaleX(animatedValue);
                    }
                });
                animator.start();

                final ObjectAnimator anim = ObjectAnimator.ofFloat(view, "adf",
                        1F, 0.7F);
                anim.setDuration(500);
                anim.setInterpolator(new BounceInterpolator());
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedFraction = (float) animation.getAnimatedValue();
                        view.setScaleX(animatedFraction);
                        view.setScaleY(animatedFraction);
                    }
                });
                anim.start();
            }

        } else {
            if (mButton.getVisibility() == View.VISIBLE) {
                mButton.setVisibility(View.GONE);
                if (index != 0) {
                    final ObjectAnimator anim = ObjectAnimator.ofFloat(view, "adf",
                            0.7F, 1F);
                    anim.setDuration(500);
                    anim.setInterpolator(new AnticipateOvershootInterpolator());
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedFraction = (float) animation.getAnimatedValue();
                            view.setScaleX(animatedFraction);
                            view.setScaleY(animatedFraction);
                        }
                    });
                    anim.start();
                } else {
                    view.setScaleX(1F);
                    view.setScaleY(1F);
                }
            }

        }
    }
}
