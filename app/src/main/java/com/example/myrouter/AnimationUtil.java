package com.example.myrouter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;


import java.util.ArrayList;

public class AnimationUtil {
    
    private ArrayList<ObjectAnimator> animators;
    private View mTarget;
    
    public AnimationUtil(){
        animators = new ArrayList<>();
    }

    public AnimationUtil target(View view){
        mTarget = view;
        return this;
    }

    public AnimationUtil translationX(float...values) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mTarget, "translationX", values);
        animators.add(animatorX);
        return this;
    }

    public AnimationUtil translationY(float...values) {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mTarget, "translationY", values);
        animators.add(animatorY);
        return this;
    }

    public AnimationUtil alpha(float...values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTarget, "alpha", values);
        animators.add(animator);
        return this;
    }

    public AnimationUtil scaleX(float...values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTarget, "scaleX", values);
        animators.add(animator);
        return this;
    }

    public AnimationUtil scaleY(float...values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTarget, "scaleY", values);
        animators.add(animator);
        return this;
    }

    public AnimationUtil rotation(float...values){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTarget, "rotation", values);
        animators.add(animator);
        return this;
    }

    private void performAnimators(long duration, ArrayList<ObjectAnimator> animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        for (ObjectAnimator an :
                animators) {
            animatorSet.playTogether(an);
        }
        animatorSet.setTarget(mTarget);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    
    public void start(long duration) {
            if(animators.isEmpty()) {
//                XLog.e("请先添加要执行的动画");
                return;
            }
            if (null == mTarget){
//                XLog.e("请设置要执行动画的目标view");
                return;
            }
        performAnimators(duration,animators);
    }

}
