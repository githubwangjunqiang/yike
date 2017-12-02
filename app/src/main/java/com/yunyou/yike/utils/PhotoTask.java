package com.yunyou.yike.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${王俊强} on 2017/2/15.
 */

public class PhotoTask extends AsyncTask<Void, Void, List<String>> {
    public interface TaskCallback {
        void onPreExecute();

        void onProgressUpdate(Object bitmap);

        void onError();

        void onSuccess(List<String> string);
    }

    public TaskCallback callback;
    private Reference<Context> context;

    public void setUrl(String url) {
        this.fileUpload = url;
    }

    public String fileUpload;
    private List<Uri> mUris;

    public PhotoTask(Context context, String fileUpload, List<Uri> uris, TaskCallback callback) {
        this.callback = callback;
        this.context = new WeakReference<Context>(context);
        this.fileUpload = fileUpload;
        this.mUris = uris;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        if (mUris == null || mUris.isEmpty()) {
            LogUtils.d("给的原路径uri为空...");
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mUris.size(); i++) {
            LogUtils.d("图片的uri=" + i + "-" + mUris.get(i));
            String path = toOutImage(mUris.get(i));

            if (!TextUtils.isEmpty(path)) {
                list.add(path);
            } else {
                LogUtils.d("返回的=null");
            }
        }

        return list;
    }

    /**
     * 上传图片逻辑
     *
     * @param uri
     * @return
     */
    private String toOutImage(Uri uri) {

        try {
            if (context.get() == null) {
                return null;
            }
            Bitmap bitmapFormUri = BitmapUtils_My.getBitmapFormUri(context.get(), uri);
            int degree = BitmapUtils_My.getBitmapDegree(uri.getPath());
            Bitmap newbitmap = BitmapUtils_My.rotateBitmapByDegree(bitmapFormUri, degree);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (newbitmap == null) {
                return null;
            }
            newbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
            baos.close();


            String boundary = "----WebKitFormBoundaryzrfXlGEP1Y8Qmzxf";
            String user_id = "0";
            String user_token = "1";
            String user_type = "1";
            URL url = new URL(fileUpload);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            //conn.setRequestProperty("Accept-Encoding", "gzip,deflate,lzma,sdch");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
            conn.setRequestProperty("Cache-Control", "max-age=0");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Cookie",
                    "JSESSIONID=aaaj_rgTbZT8o-BVGu2yu");
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.132 Safari/537.36 OPR/21.0.1432.67");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            StringBuilder sb = new StringBuilder();
//			sb.append("--");
//			sb.append(boundary);
//			sb.append("\r\n");
//			sb.append("Content-Disposition: form-data; name=\"user_id\"\r\n\r\n");
//			sb.append("Content-Type: image/jpeg\r\n\r\n");

            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"user_id\"\r\n\r\n");

            sb.append(user_id);
            sb.append("\r\n");
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"user_token\"\r\n\r\n");

            sb.append(user_token);
            sb.append("\r\n");
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"user_type\"\r\n\r\n");

            sb.append(user_type);
            sb.append("\r\n");
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"test.jpg\"\r\n");
            sb.append("Content-Type: image/jpeg\r\n\r\n");


            os.write(sb.toString().getBytes());
            byte[] bs = new byte[1024];
            int len = 0;
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            is.close();
            os.write("\r\n".getBytes());
            os.write(("--" + boundary + "--").getBytes());
            os.flush();

            BufferedReader input = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String oneLine;
            while ((oneLine = input.readLine()) != null) {
                response.append(oneLine);
            }
            String res = response.toString();
            byte[] b = res.getBytes();
            res = new String(b, 0, b.length, "utf-8");
            LogUtils.d("上传图片" + res);
            JSONObject object = new JSONObject(res);
            int retcode = object.getInt("retcode");
            String msg = object.getString("msg");
            if (2000 == retcode) {
                return res;
            } else {
                LogUtils.e("上传图片失败" + msg);
                return null;
            }
        } catch (Exception e) {
            LogUtils.d(e.getMessage() + "\n");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<String> stringList) {
        super.onPostExecute(stringList);
        if (stringList == null || stringList.isEmpty() || stringList.size() != mUris.size()) {

            LogUtils.d("返回的list为null" + stringList.size());
            callback.onError();
            return;
        }
        callback.onSuccess(stringList);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        callback.onProgressUpdate(null);
    }
}
