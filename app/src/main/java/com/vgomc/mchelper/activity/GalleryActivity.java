package com.vgomc.mchelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.veinhorn.scrollgalleryview.HackyViewPager;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.BitmapUtil;
import com.vgomc.mchelper.utility.ToastUtil;

import org.xutils.common.util.DensityUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by weizhou1 on 2016/12/23.
 */

public class GalleryActivity extends BaseActivity {

    private ScrollGalleryView scrollGalleryView;
    private ViewPager viewPager;
    private File[] imageFiles;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_gallery);

        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        viewPager = (HackyViewPager) scrollGalleryView.findViewById(com.veinhorn.scrollgalleryview.R.id.viewPager);

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
        Arrays.sort(imageFiles, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                return -lhs.getName().compareTo(rhs.getName());
            }
        });
        for (final File imageFile : imageFiles) {
            // 预加载一遍，不然ScrollGalleryView会出现显示问题
            mediaInfoList.add(MediaInfo.mediaLoader(new MediaLoader() {
                @Override
                public boolean isImage() {
                    return true;
                }

                @Override
                public void loadMedia(Context context, ImageView imageView, SuccessCallback callback) {
                    setImageView(imageView, imageFile, DensityUtil.getScreenWidth(), DensityUtil.getScreenHeight());
                    callback.onSuccess();
                }

                @Override
                public void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback) {
                    setImageView(thumbnailView, imageFile, 100, 100);
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

    private void setImageView(final ImageView imageView, File file, int width, int height) {
        Bitmap bitmap = BitmapUtil.compressBmp(file.getAbsolutePath(), width, height);
        imageView.setImageBitmap(bitmap);
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
