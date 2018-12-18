package com.zoub.androiddemos.countdown_demo;

import android.service.quicksettings.TileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名：     AndroidDemos
 * 包名：       com.zoub.androiddemos.countdown_demo
 * 文件名：     CountDownManager
 * 创建者：     Zoub
 * 创建时间：   2018/12/18
 * 描述：      TODO
 */

public class CountDownManager {

    private static volatile CountDownManager manager;

    private BaseCountDown countDown;

    private List<BaseCountDown> countDowns = new ArrayList<>();

    private Map<String,CountDownEntity> countDownMap = new HashMap<>();
    private CountDownEntity entity;

    private CountDownManager() {
        super();
    }

    public static CountDownManager newInstance(){
        if (manager == null){
            synchronized (CountDownManager.class){
                if (manager == null){
                    manager = new CountDownManager();
                }
            }
        }
        return manager;
    }

    public void addCountDown(String key,long millisInFuture , long countDownInterval){
        countDown = new BaseCountDown(millisInFuture,countDownInterval);
        entity = new CountDownEntity(millisInFuture,countDown);
        countDownMap.put(key, entity);
    }

    public void start(){
        
    }

    public void pause(){

    }

    public void stop(){

    }

    public static class CountDownEntity{
        private long deadline;
        private BaseCountDown countDown;

        public CountDownEntity(long deadline,BaseCountDown countDown) {
            super();
            this.deadline = deadline;
            this.countDown = countDown;
        }

        public long getDeadline() {
            return deadline;
        }

        public void setDeadline(long deadline) {
            this.deadline = deadline;
        }

        public BaseCountDown getCountDown() {
            return countDown;
        }

        public void setCountDown(BaseCountDown countDown) {
            this.countDown = countDown;
        }
    }

}
