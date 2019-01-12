package com.oklib.view_lib.span;

import android.animation.ValueAnimator;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.util.SpanUtils;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view_lib.span.util.BlurMaskFilterSpan;
import com.oklib.view_lib.span.util.ForegroundAlphaColorSpan;
import com.oklib.view_lib.span.util.ForegroundAlphaColorSpanGroup;
import com.oklib.view_lib.span.util.ShadowSpan;


/**
 * 时间：2017/8/27
 * 作者：蓝天
 * 描述：span字体多样式样例
 */

public class SpanActivity extends BaseAppActivity {
    private SpanUtils mSpanUtils;
    private SpannableStringBuilder animSsb;
    private int lineHeight;
    private float textSize;
    private ValueAnimator valueAnimator;
    private Shader mShader;
    private float mShaderWidth;
    private Matrix matrix;
    private BlurMaskFilterSpan mBlurMaskFilterSpan;
    private ShadowSpan mShadowSpan;
    private ForegroundAlphaColorSpan mForegroundAlphaColorSpan;
    private ForegroundAlphaColorSpanGroup mForegroundAlphaColorSpanGroup;
    private String mPrinterString;
    private float density;
    private TextView tvAboutSpan;
    private TextView tvAboutAnimRainbow;
    private TextView tvAboutAnimBlur;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_span;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void initView() {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtil.show("事件触发了");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };

        tvAboutSpan = (TextView) findViewById(R.id.tv_about_span);
        tvAboutAnimRainbow = (TextView) findViewById(R.id.tv_about_anim_span);

        // 响应点击事件的话必须设置以下属性
        tvAboutSpan.setMovementMethod(LinkMovementMethod.getInstance());
        // 去掉点击事件后的高亮
        tvAboutSpan.setHighlightColor(ContextCompat.getColor(this, android.R.color.transparent));
        lineHeight = tvAboutSpan.getLineHeight();
        textSize = tvAboutSpan.getTextSize();
        density = getResources().getDisplayMetrics().density;

        tvAboutSpan.setText(new SpanUtils(context)
                //appendLine("SpanUtils").setBackgroundColor(Color.LTGRAY).setBold().setForegroundColor(Color.YELLOW).setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("前景色").setForegroundColor(Color.GREEN)
                .appendLine("背景色").setBackgroundColor(Color.LTGRAY)
                .appendLine("行高顶部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
                .appendLine("行高居中对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
                .appendLine("行高底部对齐").setLineHeight(2 * lineHeight, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("测试段落缩，首行缩进两字，其他行不缩进").setLeadingMargin((int) textSize * 2, 10).setBackgroundColor(Color.GREEN)
                .appendLine("测试引用，后面的字是为了凑到两行的效果").setQuoteColor(Color.GREEN, 10, 10).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试列表项，后面的字是为了凑到两行的效果").setBullet(Color.GREEN, 20, 10).setBackgroundColor(Color.LTGRAY).setBackgroundColor(Color.GREEN)
                .appendLine("测试图标文字顶部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试图标文字居中对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.GREEN)
                .appendLine("测试图标文字底部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_high, 20, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试图标顶部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_TOP).setBackgroundColor(Color.GREEN)
                .appendLine("测试图标居中对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_CENTER).setBackgroundColor(Color.LTGRAY)
                .appendLine("测试图标底部对齐，后面的字是为了凑到两行的效果").setIconMargin(R.drawable.shape_spannable_block_low, 20, SpanUtils.ALIGN_BOTTOM).setBackgroundColor(Color.GREEN)
                .appendLine("32dp字体").setFontSize(32, true)
                .appendLine("2倍字体").setFontProportion(2)
                .appendLine("横向2倍字体").setFontXProportion(1.5f)
                .appendLine("删除线").setStrikethrough()
                .appendLine("下划线").setUnderline()
                .append("测试").appendLine("上标").setSuperscript()
                .append("测试").appendLine("下标").setSubscript()
                .appendLine("粗体").setBold()
                .appendLine("斜体").setItalic()
                .appendLine("粗斜体").setBoldItalic()
                .appendLine("monospace字体").setFontFamily("monospace")
                .appendLine("自定义字体").setTypeface(Typeface.createFromAsset(getAssets(), "fonts/dnmbhs.ttf"))
                .appendLine("相反对齐").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .appendLine("居中对齐").setAlign(Layout.Alignment.ALIGN_CENTER)
                .appendLine("正常对齐").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("测试").appendLine("点击事件").setClickSpan(clickableSpan)
                .append("测试").appendLine("Url").setUrl("https://github.com/Blankj/AndroidUtilCode")
                .append("测试").appendLine("模糊").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .appendLine("颜色渐变").setShader(new LinearGradient(0, 0,
                        64 * density * 4, 0,
                        getResources().getIntArray(R.array.rainbow),
                        null,
                        Shader.TileMode.REPEAT)).setFontSize(64, true)
                .appendLine("图片着色").setFontSize(64, true).setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.cheetah),
                        Shader.TileMode.REPEAT,
                        Shader.TileMode.REPEAT))
                .appendLine("阴影效果").setFontSize(64, true).setBackgroundColor(Color.BLACK).setShadow(24, 8, 8, Color.WHITE)

                .append("测试小图对齐").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_TOP)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_CENTER)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_BASELINE)
                .appendImage(R.drawable.shape_spannable_block_low, SpanUtils.ALIGN_BOTTOM)
                .appendLine("end").setBackgroundColor(Color.LTGRAY)

                .append("测试大图字体顶部对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_TOP)
                .appendLine()

                .append("测试大图字体居中对齐").setBackgroundColor(Color.LTGRAY)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_CENTER)
                .appendLine()

                .append("测试大图字体底部对齐").setBackgroundColor(Color.GREEN)
                .appendImage(R.drawable.shape_spannable_block_high, SpanUtils.ALIGN_BOTTOM)
                .appendLine()

                .append("测试空格").appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN).appendSpace(100).appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN)
                .create());

        initAnimSpan();
        startAnim();
    }

    @Override
    protected void initNet() {

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initAnimSpan() {
        mShaderWidth = 64 * density * 4;
        mShader = new LinearGradient(0, 0,
                mShaderWidth, 0,
                getResources().getIntArray(R.array.rainbow),
                null,
                Shader.TileMode.REPEAT);
        matrix = new Matrix();

        mBlurMaskFilterSpan = new BlurMaskFilterSpan(25);

        mShadowSpan = new ShadowSpan(8, 8, 8, Color.WHITE);

        mForegroundAlphaColorSpan = new ForegroundAlphaColorSpan(Color.TRANSPARENT);

        mForegroundAlphaColorSpanGroup = new ForegroundAlphaColorSpanGroup(0);

        mPrinterString = "打印动画，后面的文字是为了测试打印效果...";

        mSpanUtils = new SpanUtils(context)
                .appendLine("彩虹动画").setFontSize(64, true).setShader(mShader)
                .appendLine("模糊动画").setFontSize(64, true).setSpans(mBlurMaskFilterSpan)
                .appendLine("阴影动画").setFontSize(64, true).setBackgroundColor(Color.BLACK).setSpans(mShadowSpan)
                .appendLine("透明动画").setFontSize(64, true).setSpans(mForegroundAlphaColorSpan);
        for (int i = 0, len = mPrinterString.length(); i < len; ++i) {
            ForegroundAlphaColorSpan span = new ForegroundAlphaColorSpan(Color.TRANSPARENT);
            mSpanUtils.append(mPrinterString.substring(i, i + 1)).setSpans(span);
            mForegroundAlphaColorSpanGroup.addSpan(span);
        }
        animSsb = mSpanUtils.create();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void startAnim() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // shader
                matrix.reset();
                matrix.setTranslate((Float) animation.getAnimatedValue() * mShaderWidth, 0);
                mShader.setLocalMatrix(matrix);

                // blur
                mBlurMaskFilterSpan.setRadius(25 * (1.00001f - (Float) animation.getAnimatedValue()));

                // oklib_shadow
                mShadowSpan.setDx(16 * (0.5f - (Float) animation.getAnimatedValue()));
                mShadowSpan.setDy(16 * (0.5f - (Float) animation.getAnimatedValue()));

                // alpha
                mForegroundAlphaColorSpan.setAlpha((int) (255 * (Float) animation.getAnimatedValue()));

                // printer
                mForegroundAlphaColorSpanGroup.setAlpha((Float) animation.getAnimatedValue());

                // update
                tvAboutAnimRainbow.setText(animSsb);
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(600 * 3);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onDestroy() {
        valueAnimator.cancel();
        super.onDestroy();
    }
}
