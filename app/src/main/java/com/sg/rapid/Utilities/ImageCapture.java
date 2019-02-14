package com.sg.rapid.Utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sg.rapid.Fragments.ProfileFragment;
import com.sg.rapid.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageCapture {

    public File file;
    public Uri fileUri;
    private static int RESULT_LOAD = 1;



    public void chooseImage(final Activity activity, final int requestCode) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.camera);
        dialog.setCancelable(true);

        TextView pictureType = dialog.findViewById(R.id.pictureType);
        LinearLayout select_camera = dialog.findViewById(R.id.select_camera);
        LinearLayout select_gallery = dialog.findViewById(R.id.select_gallery);

        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                OptionGalleryCamera.getInstance().setWhichImage("Camera");
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    file = new File(activity.getExternalCacheDir(),
                            String.valueOf(System.currentTimeMillis()) + ".jpg");
                    fileUri = Uri.fromFile(file);
                    fileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    activity.startActivityForResult(intent, requestCode);
                    activity.overridePendingTransition(0, 0);
                    cameraRequirement(intent, fileUri,activity);
                }

                dialog.dismiss();

            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                /*Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(gallery, requestCode);*/
                OptionGalleryCamera.getInstance().setWhichImage("Gallery");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"),requestCode);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    void cameraRequirement(Intent intent, Uri fileUri, Activity activity) {
        List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            activity.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public static void loadImageWithPicasso(Uri path , ImageView imageView, Context mContext){


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true)
                .circleCrop();

        Glide.with(mContext)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
               .apply(requestOptions)
                .into(imageView);

    }

    public static void loadprofilepic(Uri path , ImageView imageView, Context mContext){


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true)
                .circleCrop();

        Glide.with(mContext)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .apply(requestOptions)
                .into(imageView);

    }





}



