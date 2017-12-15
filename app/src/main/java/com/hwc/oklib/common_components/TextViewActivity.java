package com.hwc.oklib.common_components;

import android.graphics.Color;
import android.view.View;

import com.hwc.oklib.Common;
import com.hwc.oklib.R;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.bean.FunctionDetailBean;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.FadingTextView;
import com.hwc.oklib.view.stv.MoveEffectAdjuster;
import com.hwc.oklib.view.stv.OpportunityDemoAdjuster;
import com.hwc.oklib.view.stv.RippleAdjuster;
import com.hwc.oklib.view.stv.SuperTextView;

import java.util.concurrent.TimeUnit;

import static com.hwc.oklib.Common.BASE_RES;


/**
 * 时间：2017/8/3
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：TextView炫酷效果演示
 */

public class TextViewActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_textview_style;
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initTitle() {
        CommonToolBar tb_toolbar = findView(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle(getIntent().getStringExtra(Common.TITLE), 17, R.color.app_white_color)//中间标题
                .setRightTitle("更多", 14, R.color.app_white_color)//右标题
                .setRightTitleListener(new View.OnClickListener() {//有标题监听
                    @Override
                    public void onClick(View v) {
                        mBeans.add(new FunctionDetailBean("activity_textview_style.xml", BASE_RES +"/layout/activity_textview_style.xml"));
                        showDetail();
                    }
                });
    }

    @Override
    protected void initView() {
        //淡入淡出TextView
        FadingTextView fadingTextView = (FadingTextView) findViewById(R.id.fadingTextView);
        fadingTextView.setTimeout(500, TimeUnit.MILLISECONDS);//毫秒
    }

    @Override
    protected void initNet() {

    }


    private SuperTextView stv_17;
    private SuperTextView stv_18;
    private SuperTextView stv_19;
    private SuperTextView stv_20;
    private SuperTextView stv_21;
    private SuperTextView stv_22;

    private void superTextView() {
        stv_17 = findView(R.id.stv_17);
        stv_18 = findView(R.id.stv_18);
        stv_19 = findView(R.id.stv_19);
        stv_20 = findView(R.id.stv_20);
        stv_21 = findView(R.id.stv_21);
        stv_22 = findView(R.id.stv_22);

        stv_17.setAdjuster(new MoveEffectAdjuster())
                .setAutoAdjust(true)
                .startAnim();

        stv_18.setAdjuster(new RippleAdjuster(getResources().getColor(R.color.oklib_opacity_5_a58fed)));

        OpportunityDemoAdjuster opportunityDemoAdjuster1 = new OpportunityDemoAdjuster();
        opportunityDemoAdjuster1.setOpportunity(SuperTextView.Adjuster.Opportunity.BEFORE_DRAWABLE);
        stv_19.setAdjuster(opportunityDemoAdjuster1);
        stv_19.setAutoAdjust(true);

        OpportunityDemoAdjuster opportunityDemoAdjuster2 = new OpportunityDemoAdjuster();
        opportunityDemoAdjuster2.setOpportunity(SuperTextView.Adjuster.Opportunity.BEFORE_TEXT);
        stv_20.setAdjuster(opportunityDemoAdjuster2);
        stv_20.setAutoAdjust(true);

        OpportunityDemoAdjuster opportunityDemoAdjuster3 = new OpportunityDemoAdjuster();
        opportunityDemoAdjuster3.setOpportunity(SuperTextView.Adjuster.Opportunity.AT_LAST);
        stv_21.setAdjuster(opportunityDemoAdjuster3);
        stv_21.setAutoAdjust(true);

        stv_22.setFrameRate(60);
        stv_22.setShaderStartColor(Color.RED);

//    stv_22.setShaderStartColor(Color.BLUE);
//    stv_22.setShaderEndColor(Color.YELLOW);
    }

}
