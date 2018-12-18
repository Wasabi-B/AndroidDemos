package com.zoub.androiddemos.countdown_demo;

import android.os.CountDownTimer;

/**
 * 项目名：     AndroidDemos
 * 包名：       com.zoub.androiddemos.countdown_demo
 * 文件名：     BaseCountDown
 * 创建者：     Zoub
 * 创建时间：   2018/12/18
 * 描述：      TODO
 */

public class BaseCountDown extends CountDownTimer {

    public BaseCountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {

    }


}
