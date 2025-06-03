package com.Trachtenberg.jacob.v2;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 pager;
    private SeekBar slider;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager  = findViewById(R.id.pdfPager);
        slider = findViewById(R.id.pageSlider);
        TextView indicator = findViewById(R.id.pageIndicator);

        try {
            PdfPageAdapter adapter = new PdfPageAdapter(this);
            pager.setAdapter(adapter);
            pager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            pager.setOffscreenPageLimit(2);

            /* ★ 3-D VERTICAL PAGE-TURN ★ */
            pager.setPageTransformer((page, pos) -> {
                page.setCameraDistance(20000f);
                page.setPivotY(pos < 0 ? 0 : page.getHeight());
                page.setRotationX(-90f * pos);
                page.setAlpha(1f - Math.abs(pos));
            });

            slider.setMax(adapter.getItemCount() - 1);
            slider.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar s, int v, boolean from) {
                        if (from) pager.setCurrentItem(v, true);
                    }
                    public void onStartTrackingTouch(SeekBar s) {}
                    public void onStopTrackingTouch(SeekBar s) {}
                });

            pager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override public void onPageSelected(int p) {
                        slider.setProgress(p);
                        indicator.setText((p + 1) + "/" + adapter.getItemCount());
                    }
                });

        } catch (Exception e) { e.printStackTrace(); }
    }
}
