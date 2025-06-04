package com.Trachtenberg.jacob.v2;

import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.*;

public class PdfPageAdapter extends RecyclerView.Adapter<PdfPageAdapter.PageVH> {

    private final PdfRenderer renderer;
    private final SparseArray<Bitmap> cache = new SparseArray<>();

    public PdfPageAdapter(Context ctx) throws IOException {
        File tmp = File.createTempFile("trac",".pdf",ctx.getCacheDir());
        try (InputStream in = ctx.getAssets().openFd("Trac.pdf").createInputStream();
             OutputStream out = new FileOutputStream(tmp)) {
            byte[] buf = new byte[8192]; int n;
            while ((n = in.read(buf))!=-1) out.write(buf,0,n);
        }
        renderer = new PdfRenderer(
                ParcelFileDescriptor.open(tmp, ParcelFileDescriptor.MODE_READ_ONLY));
    }

    @NonNull
    @Override public PageVH onCreateViewHolder(@NonNull ViewGroup p,int v){
        ImageView iv=new ImageView(p.getContext());
        iv.setAdjustViewBounds(true); iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return new PageVH(iv);
    }

    @Override public void onBindViewHolder(@NonNull PageVH h,int pos){
        Bitmap bmp=cache.get(pos);
        if(bmp==null){
            PdfRenderer.Page page=renderer.openPage(pos);
            bmp=Bitmap.createBitmap(page.getWidth(),page.getHeight(),Bitmap.Config.ARGB_8888);
            page.render(bmp,null,null,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            page.close();
            cache.put(pos,bmp);
        }
        h.iv.setImageBitmap(bmp);
    }

    @Override public int getItemCount(){ return renderer.getPageCount(); }

    static class PageVH extends RecyclerView.ViewHolder{
        ImageView iv; PageVH(ImageView v){ super(v); iv=v; }
    }
}
