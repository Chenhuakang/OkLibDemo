package com.hwc.oklib;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bmoblib.BmobInsertHelp;
import com.bmoblib.BmobPayHelp;
import com.bmoblib.bean.Sponsor;
import com.bmoblib.bean.UserBean;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.login.UserManager;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.EditTextWithDelete;
import com.orhanobut.logger.Logger;

import cn.bmob.v3.exception.BmobException;

/**
 * 时间：2017/8/22
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：赞助商、支持者购买广告位
 */

public class PayActivity extends BaseAppActivity {
    private final double MIN_PAY = 100;//最少支付金额
    public static final String ALIPAY = "alipay";
    public static final String WECHAT_PAY = "wechatPay";
    public static final String PAY_TYPE_KEY = "payTypKey";
    public String payType = "payType";//支付类型
    private String mOrderId;//支付成功后订单
    private String payTag = "";//赞助或打赏

    private EditTextWithDelete ed_headPortrait;
    private EditTextWithDelete ed_name;
    private EditTextWithDelete ed_introduce;
    private EditTextWithDelete ed_sum;
    private EditTextWithDelete ed_referralLinks;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initVariable() {
        payType = getIntent().getStringExtra(PAY_TYPE_KEY);
    }

    private CommonToolBar tb_toolbar;

    @Override
    protected void initTitle() {
        tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("确定赞助", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        final String headPortrait = ed_headPortrait.getText().toString();//头像
                        final String productName = ed_name.getText().toString();//名称
                        final String introduce = ed_introduce.getText().toString();//介绍
                        double price = MIN_PAY;//金额
                        final String sum = ed_sum.getText().toString();//金额
                        final String referralLinks = ed_referralLinks.getText().toString();//推广地址

                        if (TextUtils.isEmpty(headPortrait)) {
                            ToastUtil.show(ed_headPortrait.getHint().toString());
                            return;
                        }
                        if (TextUtils.isEmpty(productName)) {
                            ToastUtil.show(ed_name.getHint().toString());
                            return;
                        }
                        if (TextUtils.isEmpty(introduce)) {
                            ToastUtil.show(ed_introduce.getHint().toString());
                            return;
                        }
                        if (TextUtils.isEmpty(sum)) {
                            ToastUtil.show(ed_sum.getHint().toString());
                            return;
                        } else {
                            try {
                                price = Double.valueOf(sum);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                price = MIN_PAY;
                            }
                        }
                        if (price < MIN_PAY) {
                            ToastUtil.show("赞助金额最少需要￥" + MIN_PAY + "以上");
                            return;
                        }
                        if (TextUtils.isEmpty(referralLinks)) {
                            ToastUtil.show(ed_referralLinks.getHint().toString());
                            return;
                        }
                        String describe = "OkLib库赞助：" + payTag;

                        final double mSum;//支付金额
                        mSum = price;

                        if (TextUtils.equals(payType, ALIPAY)) {
                            //支付宝购买
                            BmobPayHelp.getInstance().alipay(context, productName, price, describe, new BmobPayHelp.OnPayListener() {
                                @Override
                                public void success() {
                                    //赞助
                                    Sponsor sponsor = new Sponsor();
                                    sponsor.setHeadPortrait(headPortrait);
                                    sponsor.setName(productName);
                                    sponsor.setIntroduce(introduce);
                                    sponsor.setSum(mSum);
                                    sponsor.setReferralLinks(referralLinks);
                                    sponsor.setOrderId(mOrderId);
                                    BmobInsertHelp.insertSponsor(sponsor, new BmobInsertHelp.OnInsertSuccessListener() {
                                        @Override
                                        public void success(String objectId) {
                                            processStateWin(getString(R.string.pay_insert_success));
                                        }

                                        @Override
                                        public void fail(BmobException e) {
                                            processStateWin(getString(R.string.pay_insert_fail));
                                        }
                                    });
                                }

                                @Override
                                public void fail() {
                                    processStateWin(getString(R.string.pay_fail));
                                }

                                //订单回调先于支付成功方法回调
                                @Override
                                public void orderId(String orderId) {
                                    Logger.d("orderId:" + orderId);
                                    mOrderId = orderId;
                                }
                            });
                        } else if (TextUtils.equals(payType, WECHAT_PAY)) {
                            //微信购买
                            BmobPayHelp.getInstance().wechatPay(context, productName, price, describe, new BmobPayHelp.OnPayListener() {
                                @Override
                                public void success() {
                                    Log.d("TAG", "微信success");
                                }

                                @Override
                                public void fail() {
                                    Log.d("TAG", "微信fail");
                                }

                                @Override
                                public void orderId(String orderId) {
                                    Log.d("TAG", "微信" + orderId);
                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        ed_headPortrait = findView(R.id.ed_headPortrait);
        ed_name = findView(R.id.ed_name);
        ed_introduce = findView(R.id.ed_introduce);
        ed_sum = findView(R.id.ed_sum);
        ed_referralLinks = findView(R.id.ed_referralLinks);

        UserBean userBean = UserManager.getUserBean();
        ed_headPortrait.setText(userBean.getHeadPicture());
        ed_name.setText(userBean.getNickName());
        ed_introduce.setText(userBean.getIntro());
        ed_referralLinks.setText("");
    }

    @Override
    protected void initNet() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BmobPayHelp.REQUESTPERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    BmobPayHelp.getInstance().installBmobPayPlugin("bp.db");
                } else {
                    //提示没有权限，安装不了
                    Toast.makeText(AppOkLib.application, "您拒绝了权限，这样无法安装支付插件", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 作者：黄伟才
     * 描述：状态提示窗口
     */
    private void processStateWin(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.show();
    }
}
