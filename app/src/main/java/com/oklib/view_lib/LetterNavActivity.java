package com.oklib.view_lib;

import android.view.View;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.view.letters_nav.LetterSideBar;
import com.oklib.view.letters_nav.OnLetterTouchListener;
import com.oklib.view.letters_nav.adapter.BaseSortRecyclerViewAdapter;
import com.oklib.view.letters_nav.adapter.LettersNavRcvAdapter;
import com.oklib.view.letters_nav.bean.LettersNavBean;
import com.oklib.view.letters_nav.widget.LetterNavRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间：2017/8/20
 * 作者：蓝天
 * 描述：字母导航·RecyclerView
 */

public class LetterNavActivity extends BaseAppActivity implements BaseSortRecyclerViewAdapter.OnRecyclerViewClickListener, OnLetterTouchListener {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_letter_nav;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {
        initLettersNav();
    }

    private LetterNavRecyclerView rvLettersNavList;
    private List<LettersNavBean> mDatas;
    private LettersNavRcvAdapter adapter;
    private LetterSideBar sideBar;
    private TextView tvDialog;

    private void initLettersNav() {
        tvDialog = (TextView) findViewById(R.id.tv_dialog);
        sideBar = (LetterSideBar) findViewById(R.id.sidebar);
        rvLettersNavList = (LetterNavRecyclerView) findViewById(R.id.rvLettersNavList);
        rvLettersNavList.setAdapter(adapter = new LettersNavRcvAdapter(this, mDatas = new ArrayList<>()));

        //请求接口，刷新本地数据源
        List<LettersNavBean> medicalRecordsList = new ArrayList<>();
        medicalRecordsList.add(new LettersNavBean("张三"));
        medicalRecordsList.add(new LettersNavBean("李四"));
        medicalRecordsList.add(new LettersNavBean("王五"));
        medicalRecordsList.add(new LettersNavBean("赵六"));
        medicalRecordsList.add(new LettersNavBean("飞散"));
        medicalRecordsList.add(new LettersNavBean("胡歌"));
        medicalRecordsList.add(new LettersNavBean("德华"));
        medicalRecordsList.add(new LettersNavBean("国荣"));
        medicalRecordsList.add(new LettersNavBean("菲菲"));
        updateList(medicalRecordsList);


        //item点击回调，可选
        adapter.setRecyclerViewClickListener(this);//依赖于implements BaseSortRecyclerViewAdapter.OnRecyclerViewClickListener

        //字母导航触碰事件回调，可选
        sideBar.setLetterTouchListener(rvLettersNavList, adapter, tvDialog, this);//依赖于implements OnLetterTouchListener
    }

    //刷新本地数据源
    private void updateList(List<LettersNavBean> medicalRecordsList) {
        mDatas = medicalRecordsList;
        adapter.updateRecyclerView(mDatas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLetterTouch(String letter, int position) {

    }

    @Override
    public void onActionUp() {

    }

    @Override
    public void onClick(View itemView, int pos) {

    }
}
