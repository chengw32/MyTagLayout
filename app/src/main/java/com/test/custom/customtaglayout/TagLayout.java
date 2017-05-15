package com.test.custom.customtaglayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author 陈国武
 * Time 2017/5/12.
 * Des:
 */

public class TagLayout extends ViewGroup {

    private List<List<ChildViewATT>> views = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = 0 ;
        int measureHeight = 0 ;


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //系统的规范（给定的）宽高
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY){
            measureWidth = widthSpec ;
            measureHeight = heightSpec ;
        }else {

            int totalWidth = 0 ;
            int totalWidth_ = 0 ;
            int totalHeight = 0 ;

            List<ChildViewATT> lineViews = new ArrayList<>();

            for (int i = 0 ; i < getChildCount() ; i++  ){
                View childView = getChildAt(i);
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                int leftMargin = layoutParams.leftMargin;
                int rightMargin = layoutParams.rightMargin;
                int topMargin = layoutParams.topMargin;
                int bottomMargin = layoutParams.bottomMargin;

                int childWidth = childView.getMeasuredWidth();
                int childHeidth = childView.getMeasuredHeight();
                TextView test = (TextView) childView;
                String ssss = test.getText().toString();
                totalWidth_ += (leftMargin + rightMargin + childWidth );
                if (totalWidth_ >= widthSpec){
                    totalHeight += (topMargin + bottomMargin + childHeidth );
                    totalWidth = 0 ;//宽度重置
                    totalWidth_ = (leftMargin + rightMargin + childWidth );//宽度重置
                    lineViews.add(new ChildViewATT(childView,leftMargin + totalWidth,topMargin + totalHeight ,leftMargin + childWidth + totalWidth,topMargin + childHeidth + totalHeight));
                    views.add(lineViews);
                    lineViews = new ArrayList<>();

                    measureWidth = widthSpec ;
                }else {
                    lineViews.add(new ChildViewATT(childView,leftMargin + totalWidth,topMargin + totalHeight ,leftMargin + childWidth + totalWidth,topMargin + childHeidth + totalHeight));
                    measureWidth = totalWidth ;
                }

                totalWidth += (leftMargin + rightMargin + childWidth );
                measureHeight = topMargin + bottomMargin + childHeidth + totalHeight;

            }

        }

        //最终目的就是给父容器设置一个宽高
        setMeasuredDimension(measureWidth,measureHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < views.size(); i++) {
            List<ChildViewATT> childViewATTs = views.get(i);
            for (int j = 0; j < childViewATTs.size(); j++) {
                ChildViewATT childViewATT = childViewATTs.get(j);
                View childView = childViewATT.getV();
                childView.layout(childViewATT.getL(),childViewATT.getT(),childViewATT.getR(),childViewATT.getB());
            }
        }

    }


    class ChildViewATT{
        private int l,  t,  r,  b ;
        private View v ;
        ChildViewATT(View child, int l, int t, int r, int b){
            this.v = child;
            this.l = l ;
            this.t = t ;
            this.r = r ;
            this.b = b ;
        }

        public int getL() {
            return l;
        }

        public int getT() {
            return t;
        }

        public int getR() {
            return r;
        }

        public int getB() {
            return b;
        }

        public View getV() {
            return v;
        }

        @Override
        public String toString() {
            return "ChildViewATT{" +
                    "l=" + l +
                    ", t=" + t +
                    ", r=" + r +
                    ", b=" + b +
                    ", v=" + v +
                    '}';
        }
    }


}
