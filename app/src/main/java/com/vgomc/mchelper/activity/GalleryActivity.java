package com.vgomc.mchelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.veinhorn.scrollgalleryview.HackyViewPager;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.fragment.SettingFragment;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhou1 on 2016/12/23.
 */

public class GalleryActivity extends BaseActivity {

    private ScrollGalleryView scrollGalleryView;
    private ImageView imageView;
    private ViewPager viewPager;
    private File[] imageFiles;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_gallery);

        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        viewPager = (HackyViewPager) scrollGalleryView.findViewById(com.veinhorn.scrollgalleryview.R.id.viewPager);
        imageView = (ImageView) findViewById(R.id.imageView);

        List<MediaInfo> mediaInfoList = new ArrayList<>();
        File file = new File(FileServiceProvider.getExternalPhotoPath(mContext));
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.toLowerCase().endsWith(FileServiceProvider.SUFFIX_PHOTO);
            }
        };
        if (!file.exists() || !file.isDirectory() || file.listFiles(filenameFilter).length <= 0) {
            ToastUtil.showToast(mContext, "暂无照片");
            return;
        }
        imageFiles = file.listFiles(filenameFilter);
        for (final File imageFile : imageFiles) {
            // 预加载一遍，不然ScrollGalleryView会出现显示问题
            setImageView(imageView, imageFile);
            mediaInfoList.add(MediaInfo.mediaLoader(new MediaLoader() {
                @Override
                public boolean isImage() {
                    return true;
                }

                @Override
                public void loadMedia(Context context, ImageView imageView, SuccessCallback callback) {
                    setImageView(imageView, imageFile);
                    callback.onSuccess();
                }

                @Override
                public void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback) {
                    setImageView(thumbnailView, imageFile);
                    callback.onSuccess();
                }
            }));
        }

        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(mediaInfoList);
    }

    private void setImageView(final ImageView imageView, File file) {
        // To get image using Fresco
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.fromFile(file))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchDecodedImage(imageRequest, mContext);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {

            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                // You can use the bitmap in only limited ways
                // No need to do any cleanup.
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                // No cleanup required here.
            }

        }, CallerThreadExecutor.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_bar_share:
                int i = viewPager.getCurrentItem();
                if (imageFiles.length <= i) {
                    ToastUtil.showToast(mContext, "无法分享");
                } else {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFiles[i]));
                    share.setType("*/*");//此处可发送多种文件
                    mContext.startActivity(Intent.createChooser(share, "Share"));
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
