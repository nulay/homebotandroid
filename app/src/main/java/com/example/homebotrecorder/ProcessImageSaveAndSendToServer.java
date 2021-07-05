package com.example.homebotrecorder;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.media.Image;
import android.util.Base64;
import com.example.homebotrecorder.ui.JsonPlaceholderApi;
import com.example.homebotrecorder.ui.Post;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("NewApi")
public class ProcessImageSaveAndSendToServer implements ProcessImage {
    private JsonPlaceholderApi jsonPlaceholderApi, jsonPlaceholderApiMyServer;
    private Retrofit retrofit;
    private Retrofit retrofit2;

    public ProcessImageSaveAndSendToServer() {
        retrofit = new Retrofit.Builder().
                baseUrl(upLoadServerUri).
                addConverterFactory(GsonConverterFactory.create()).
                build();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        retrofit2 = new Retrofit.Builder().
                baseUrl(upLoadServerUriMyServer).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        jsonPlaceholderApiMyServer = retrofit2.create(JsonPlaceholderApi.class);
    }

    public void processImage(Image image) {
        //Process image data
        try {
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            save(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (image != null) {
                image.close();
            }
        }
    }

    private void save(byte[] bytes) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".jpg";
        String pathName = "/storage/sdcard/DCIM/" + imageName;
        OutputStream output = null;
        try {
            output = new FileOutputStream(new File(pathName));
            output.write(bytes);
        } finally {
            if (null != output) {
                output.close();
            }
        }
    }

    private static String upLoadServerUriMyServer = "http://192.168.0.102:8081/";
    private static String upLoadServerUri = "https://jsonplaceholder.typicode.com/";

    private void uploadFile(String imageName, byte[] content) {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), content);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", imageName, requestFile);

        try {
            final Call<Post> request = jsonPlaceholderApiMyServer.uploadAttachment(filePart);
            request.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uploadFile(File file) {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<Post> call = jsonPlaceholderApi.uploadAttachment(filePart);
    }

//    public void getMethod() {
//
//        Call<List<Post>> call = jsonPlaceholderApi.getPosts();
//
//
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if (!response.isSuccessful()) {
//                    ((TextView) findViewById(R.id.text)).setText("Code: " + response.code());
//                    return;
//                }
//                List<Post> posts = response.body();
//
//                for (Post post : posts) {
//                    String content = "";
//                    content += "Id: " + post.getId() + "\n";
//                    content += "User Id: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content += "Text: " + post.getBody() + "\n";
//                    ((TextView) findViewById(R.id.text)).append(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                ((TextView) findViewById(R.id.text)).setText(t.getMessage());
//            }
//        });
//    }

    private void saveImage1(byte[] data) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".txt";
        String pathName = "/storage/sdcard/DCIM/" + imageName;
        try {
            FileOutputStream fos = new FileOutputStream(pathName);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImage2(byte[] data) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG2_" + timeStamp + ".jpg";
        String pathName = "/storage/sdcard/DCIM/" + imageName;

        BitmapFactory.Options opts1 = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts1);

        Canvas canvas = new Canvas();
        try {
            canvas.setBitmap(bitmap.isMutable() ? bitmap : bitmap.copy(Bitmap.Config.RGB_565, true));
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {

            fOut = new FileOutputStream(new File(pathName), true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveImage3(byte[] data) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        int maxSize = 1080;
        picture.setHasAlpha(true);
        int bWidth = picture.getWidth();
        int bHeight = picture.getHeight();

        if (bWidth > bHeight) {
            int imageHeight = (int) Math.abs(maxSize * ((float) picture.getWidth() / (float) picture.getHeight()));
            picture = Bitmap.createScaledBitmap(picture, maxSize, imageHeight, true);
        } else {
            int imageWidth = (int) Math.abs(maxSize * ((float) picture.getWidth() / (float) picture.getHeight()));
            picture = Bitmap.createScaledBitmap(picture, imageWidth, maxSize, true);
        }

        OutputStream fOut = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "/storage/sdcard/DCIM/IMG_" + timeStamp + ".png";

        File file = new File(imageName);
        try {

            fOut = new FileOutputStream(file);

            picture.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImage4(byte[] data) {
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "IMG_" + timeStamp + ".txt";
        String pathName = "/storage/sdcard/DCIM/" + imageName;
        try {
            FileOutputStream fos = new FileOutputStream(pathName);
            fos.write(base64.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
