package com.xiaoxiao.picdownlaod;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;


public class ShowLargePicActivity extends Activity {

    String pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_load);
        pic = getIntent().getStringExtra("pic");
        initWidget();
    }

    public void initWidget() {

        PhotoView photoView = findViewById(R.id.disc_pic);
        final ImageView imgDownload = findViewById(R.id.img_download);
        final ImageView imgBack = findViewById(R.id.img_back);
        Glide.with(ShowLargePicActivity.this).load(pic).error(R.mipmap.photo_pic).into(photoView);

        imgDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(ShowLargePicActivity.this).load(pic).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                        // 下载成功回调函数
                        // 数据处理方法，保存bytes到文件
                        bytesToImageFile(bytes);
                    }
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        // 下载失败回调
                        try {
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    }
                });
            }
        });

        imgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
		/*ImageLoader.getInstance().loadImage(pic,new SimpleImageLoadingListener(){
			
			 @Override  
	            public void onLoadingComplete(String imageUri, View view,  
	                    Bitmap loadedImage) {  
	                super.onLoadingComplete(imageUri, view, loadedImage);  
	                
	                discPic.setImageBitmap(loadedImage);  
	                LayoutParams para;
	                para = discPic.getLayoutParams();
	                int height = loadedImage.getHeight();
	                int width = loadedImage.getWidth();
//	                float f=mContext.getResources().getDisplayMetrics().density;
//	               float bit=width/300;
//	                height=(int)(height/bit);
//	               width=(int)(300/1.5*f);
//	               height=(int)(height/1.5*f);
	                para.height = height;
	                para.width = width;
	                discPic.setLayoutParams(para);
			 }
		});*/

        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (imgBack.getVisibility() == View.VISIBLE) {
                    imgBack.setVisibility(View.GONE);
                } else {
                    imgBack.setVisibility(View.VISIBLE);
                }

                if (imgDownload.getVisibility() == View.VISIBLE) {
                    imgDownload.setVisibility(View.GONE);
                } else {
                    imgDownload.setVisibility(View.VISIBLE);
                }


            }
        });


        photoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    @SuppressLint("ShowToast")
    private void bytesToImageFile(byte[] bytes) {
        try {
            String name = String.valueOf(System.currentTimeMillis());
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() +  "/" + Environment.DIRECTORY_DCIM + "/xiaoxiao";
            FileUtil.makeRootDirectory(dir);
            File file = new File(dir, name + ".jpg");

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();
            MediaScanner mediaScanner = new MediaScanner(ShowLargePicActivity.this);
            mediaScanner.scanFile(file, "image/.jpg");
            Toast.makeText(ShowLargePicActivity.this, "图片已保存到" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
