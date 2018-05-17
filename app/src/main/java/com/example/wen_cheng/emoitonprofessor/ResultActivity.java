package com.example.wen_cheng.emoitonprofessor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    public PieChart mChart;
    public AudioManager audioManager;
    public int a1,b1,c1,d1;
    public int resa,resb,resc,resd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        getData();
        Declare();
        drawPie();
    }

    private void getData() {
        Bundle bu = this.getIntent().getExtras();
        a1 = bu.getInt("a");
        b1 = bu.getInt("b");
        c1 = bu.getInt("c");
        d1 = bu.getInt("d");
        resa = ((a1*100) / (a1+b1+c1+d1));
        resb = ((b1*100) / (a1+b1+c1+d1));
        resc = ((c1*100) / (a1+b1+c1+d1));
        resd = ((d1*100) / (a1+b1+c1+d1));
        Toast.makeText(ResultActivity.this,
                Integer.toString(resa) + Integer.toString(resb) +
                        Integer.toString(resc) + Integer.toString(resd),Toast.LENGTH_SHORT).show();

    }

    public void bclick(View v){
        switch (v.getId()){
            case R.id.gotoqes:{
                startActivity(new Intent(this,QuesActivity.class));
                this.finish();
            }
        }
    }

    private void Declare() {
        mChart = (PieChart)findViewById(R.id.mChart);
    }

    public void drawPie(){
        mChart.setUsePercentValues(false);            //使用百分比顯示
        mChart.getDescription().setEnabled(false);    //設置圖表描述
        mChart.setBackgroundColor(Color.WHITE);      //設置圖表顏色
        mChart.setExtraOffsets(5, 10, 60, 10);        //圖表外邊距
        mChart.setDragDecelerationFrictionCoef(0.95f);//轉動組尼係數
        mChart.setRotationAngle(0);                   //圖表起始角度
        mChart.setRotationEnabled(true);              //設置旋轉
        mChart.setHighlightPerTapEnabled(true);       //設置可否點擊亮度
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);// 設置顯示動畫

// 圖表文本屬性
       /* mChart.setDrawEntryLabels(true);             //TRUE值下述才有效果
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(10f);  */

//內部圓環屬性
        mChart.setDrawHoleEnabled(true);              //TRUE值下述才有效果
        mChart.setHoleRadius(28f);
        mChart.setTransparentCircleRadius(31f);
        mChart.setTransparentCircleColor(Color.BLACK);
        mChart.setTransparentCircleAlpha(50);
        mChart.setHoleColor(Color.WHITE);
        mChart.setDrawCenterText(true);
        //mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText("Test");
        mChart.setCenterTextSize(10f);
        mChart.setCenterTextColor(Color.RED);

        setData();

        Legend l = mChart.getLegend();
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.DEFAULT);
        l.setFormSize(10);
        l.setFormToTextSpace(10f);
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(10f);
        l.setYEntrySpace(8f);
        l.setYOffset(0f);
        l.setTextSize(14f);
        l.setTextColor(Color.parseColor("#ff9933"));
    }

    private void setData() {
        ArrayList<PieEntry> pieEntryList = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#f17548"));
        colors.add(Color.parseColor("#FF9933"));
        colors.add(Color.parseColor("#FFFF00"));
        colors.add(Color.parseColor("#00FFFF"));
        // PieEntry
        PieEntry res1 = new PieEntry(resa,"a");
        PieEntry res2 = new PieEntry(resb,"b");
        PieEntry res3 = new PieEntry(resc,"c");
        PieEntry res4 = new PieEntry(resd,"d");
        pieEntryList.add(res1);
        pieEntryList.add(res2);
        pieEntryList.add(res3);
        pieEntryList.add(res4);
        // PieDataSet
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "分析結果");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(10f);
        pieDataSet.setColors(colors);
        // PieData
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueTextColor(Color.BLUE);
        pieData.setValueTextSize(12f);
        //pieData.setValueTypeface(mTfLight);
        pieData.setValueFormatter(new PercentFormatter());
        mChart.setData(pieData);
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){

            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);

        }else if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){

            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

        }else if(keyCode == KeyEvent.KEYCODE_BACK){
        }
        return false;
    }
}
