package com.qianyu.communicate.test;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianyu.communicate.R;

import com.qianyu.communicate.base.BaseActivity;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.PixelUtil;
import org.haitao.common.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class TestArcMenuActivity extends BaseActivity {

    @InjectView(R.id.mImg1)
    ImageView mImg1;
    @InjectView(R.id.mImg2)
    ImageView mImg2;
    @InjectView(R.id.mImg3)
    ImageView mImg3;
    @InjectView(R.id.mImg4)
    ImageView mImg4;
    @InjectView(R.id.mImg5)
    ImageView mImg5;
    @InjectView(R.id.mImg6)
    ImageView mImg6;
    @InjectView(R.id.mHeadImg)
    SimpleDraweeView mHeadImg;
    @InjectView(R.id.mGifLogo)
    SimpleDraweeView mGifLogo;
    private boolean isMenuOpen = false;
    private List<ImageView> imgViews = new ArrayList<>();

    @Override
    public int getRootViewId() {
        return R.layout.activity_arc_menu;
    }

    @Override
    public void setViews() {
        imgViews.add(mImg1);
        imgViews.add(mImg2);
        imgViews.add(mImg3);
        imgViews.add(mImg4);
        imgViews.add(mImg5);
        imgViews.add(mImg6);
        mHeadImg.setImageURI("http://img2.imgtn.bdimg.com/it/u=266057472,3867980612&fm=27&gp=0.jpg");

        Fresco.initialize(this);
        Uri uri = Uri.parse("http://img.huofar.com/data/jiankangrenwu/shizi.gif");
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        mGifLogo.setController(draweeController);
    }

    @OnClick({R.id.mImg1, R.id.mImg2, R.id.mImg3, R.id.mImg4, R.id.mImg5, R.id.mImg6, R.id.mHeadImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mImg1:
                ToastUtil.shortShowToast("==========mImg1========");
                break;
            case R.id.mImg2:
                ToastUtil.shortShowToast("==========mImg2========");
                break;
            case R.id.mImg3:
                ToastUtil.shortShowToast("==========mImg3========");
                break;
            case R.id.mImg4:
                ToastUtil.shortShowToast("==========mImg4========");
                break;
            case R.id.mImg5:
                ToastUtil.shortShowToast("==========mImg5========");
                break;
            case R.id.mImg6:
                ToastUtil.shortShowToast("==========mImg6========");
                break;
            case R.id.mHeadImg:
                if (!isMenuOpen) {
                    showOpenAnim(PixelUtil.dp2px(TestArcMenuActivity.this,100));
                } else {
                    showCloseAnim(PixelUtil.dp2px(TestArcMenuActivity.this,100));
                }
                break;
        }
    }

    //打开扇形菜单的属性动画， dp为半径长度
    private void showOpenAnim(int px) {
        //for循环来开始小图标的出现动画
        for (int i = 0; i < imgViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            //标题1与x轴负方向角度为20°，标题2为100°，转换为弧度
            AppLog.e(i + "==============showOpenAnim===========" + 20 * (i * 2 + 1));
            double a = (i < 3 ? -1 : 1) * Math.cos(45 * Math.PI / 180 * (i < 3 ? i : (i - 3)));
            double b = (i < 3 ? -1 : 1) * Math.sin(45 * Math.PI / 180 * (i < 3 ? i : (i - 3)));
            double x = a * px;
            double y = b * px;
            set.playTogether(
                    ObjectAnimator.ofFloat(imgViews.get(i), "translationX", 0, (float) x),
                    ObjectAnimator.ofFloat(imgViews.get(i), "translationY", 0, (float) y)
                    , ObjectAnimator.ofFloat(imgViews.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //菜单状态置打开
                    isMenuOpen = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mHeadImg, "rotation", 0, 90).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();

    }

    //关闭扇形菜单的属性动画，参数与打开时相反
    private void showCloseAnim(int px) {
        //for循环来开始小图标的出现动画
        for (int i = 0; i < imgViews.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            double a = (i < 3 ? -1 : 1) * Math.cos(45 * Math.PI / 180 * (i < 3 ? i : (i - 3)));
            double b = (i < 3 ? -1 : 1) * Math.sin(45 * Math.PI / 180 * (i < 3 ? i : (i - 3)));
            double x = a * px;
            double y = b * px;

            set.playTogether(
                    ObjectAnimator.ofFloat(imgViews.get(i), "translationX", (float) x, 0),
                    ObjectAnimator.ofFloat(imgViews.get(i), "translationY", (float) y, 0),
                    ObjectAnimator.ofFloat(imgViews.get(i), "alpha", 1, 0).setDuration(2000)
            );
//      set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(500);
            set.start();

            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //菜单状态置关闭
                    isMenuOpen = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        //转动加号大图标本身45°
        ObjectAnimator rotate = ObjectAnimator.ofFloat(mHeadImg, "rotation", 0, 90).setDuration(300);
        rotate.setInterpolator(new BounceInterpolator());
        rotate.start();


    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
