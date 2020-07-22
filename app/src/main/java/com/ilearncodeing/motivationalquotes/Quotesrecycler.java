package com.ilearncodeing.motivationalquotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

class Quotesrecycler extends RecyclerView.Adapter<Quotesrecycler.ViewHolder> {

    ArrayList<String> quotes;
    Context context;




    public Quotesrecycler(ArrayList<String> quotes, Context context ) {

        this.quotes = quotes;
        this.context = context;



    }

    @NonNull
    @Override
    public Quotesrecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quoteholder,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Quotesrecycler.ViewHolder holder, int position) {


        Glide.with(context)
                .load(quotes.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .into(holder.imageView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.linearLayout.setLayoutParams(layoutParams);


        holder.dload_btn.setOnClickListener(V-> {


            download_button_action(position);


        });

        holder.share_btn.setOnClickListener(V-> {



            share_button_action(position);

        });

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout linearLayout;
        Button dload_btn;
        Button share_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.quoteimage);
            linearLayout = itemView.findViewById(R.id.qhlinear);
            dload_btn = itemView.findViewById(R.id.dload_btn);
            share_btn = itemView.findViewById(R.id.share_btn);



        }
    }




    @SuppressLint("StaticFieldLeak")
    public class bitmapfromurl extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmap;
        String myfilename;

        public bitmapfromurl(String myfilename) {
            this.myfilename = myfilename;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            try {
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                File directory;
                directory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory, this.myfilename);
                if (!file.exists()) {
                    Log.d("path", file.toString());
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "filename", "quotespot.com");
                        fos.flush();
                        fos.close();

                        Toast.makeText(context,"image saved to gallery ", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(context,"image saved to gallery ", Toast.LENGTH_SHORT).show();
                }

            } catch (NullPointerException ignored) {

            }


        }
    }
    @SuppressLint("StaticFieldLeak")
    public class bitmapfromurlshare extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmap;
        String myfilename;

        public bitmapfromurlshare(String myfilename) {
            this.myfilename = myfilename;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            try {
                Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                File directory;
                directory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory, this.myfilename);
                if (!file.exists()) {
                    Log.d("path", file.toString());
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        //MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "filename", "quotespot.com");
                        fos.flush();
                        fos.close();

                        Intent shareIntent;

                        Uri bmpUri = FileProvider.getUriForFile(
                                context,
                                context.getPackageName()+".provider",
                                new File(file.getPath()));
                        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey please check this application " + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
                        shareIntent.setType("image/jpeg");
                        context.startActivity(Intent.createChooser(shareIntent,"Share with"));








                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {

                    Intent shareIntent;
                    String path=file.getPath();
                    Uri bmpUri = FileProvider.getUriForFile(
                            context,
                            context.getPackageName()+".provider",
                            new File(path));
                    shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey please check this application " + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
                    shareIntent.setType("image/jpeg");
                    context.startActivity(Intent.createChooser(shareIntent,"Share with"));

                }


            } catch (NullPointerException nu) {

            }


        }
    }



    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) hexString.append(Integer.toHexString(0xFF & b));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    void share_button_action(int position){


        Dexter.withContext(context)
                .withPermission(WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        new bitmapfromurlshare(md5(quotes.get(position))+".jpeg").execute(quotes.get(position));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        PermissionListener dialogPermissionListener =
                                DialogOnDeniedPermissionListener.Builder
                                        .withContext(context)
                                        .withTitle("Storage Permission")
                                        .withMessage("Storage Permission is needed to save The image")
                                        .withButtonText(android.R.string.ok)
                                        .build();


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();

    }
    void download_button_action(int position){
        Dexter.withContext(context)
                .withPermission(WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        new bitmapfromurl(md5(quotes.get(position))+".jpeg").execute(quotes.get(position));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        PermissionListener dialogPermissionListener =
                                DialogOnDeniedPermissionListener.Builder
                                        .withContext(context)
                                        .withTitle("Storage Permission")
                                        .withMessage("Storage Permission is needed to save The image")
                                        .withButtonText(android.R.string.ok)
                                        .build();


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();


    }

}
