package com.oklib.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by 蓝天 on 2016/11/16.
 * Describe：软键盘监听
 */

public class KeyBoardListener {

    private View rootView;//activity的根视图
    int rootViewVisibleHeight;//纪录根视图的显示高度
    private OnKeyBoardChangeListener onKeyBoardChangeListener;
    public KeyBoardListener(Activity activity) {
        //获取activity的根视图
        rootView = activity.getWindow().getDecorView();
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }
                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onKeyBoardChangeListener != null) {
                        onKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onKeyBoardChangeListener != null) {
                        onKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        });
    }
    private void setOnKeyBoardChangeListener(OnKeyBoardChangeListener onKeyBoardChangeListener) {
        this.onKeyBoardChangeListener = onKeyBoardChangeListener;
    }
    public interface OnKeyBoardChangeListener {
        void keyBoardShow(int height);
        void keyBoardHide(int height);
    }
    public static void setListener(Activity activity, OnKeyBoardChangeListener onKeyBoardChangeListener) {
        KeyBoardListener softKeyBoardListener = new KeyBoardListener(activity);
        softKeyBoardListener.setOnKeyBoardChangeListener(onKeyBoardChangeListener);
    }

}
