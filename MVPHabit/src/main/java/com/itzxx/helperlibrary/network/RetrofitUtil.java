package com.itzxx.helperlibrary.network;

import android.text.TextUtils;

import com.itzxx.helperlibrary.constants.GlobalConstans;
import com.itzxx.helperlibrary.base.HelperConfig;
import com.itzxx.helperlibrary.network.Interceptor.AddCookiesInterceptor;
import com.itzxx.helperlibrary.network.Interceptor.ReceivedCookiesInterceptor;
import com.itzxx.helperlibrary.network.converter.FastJsonConverterFactory;
import com.itzxx.helperlibrary.utils.SP;

import java.io.File;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitUtil {

    private RetrofitUtil() {
    }

    /**
     * Retrofit 使用 持久化清除cookie
     */
    public static void clearCookie() {
        SP.getInstance(HelperConfig.getContext()).putString(GlobalConstans.SET_COOKIE,"");
    }

    /**
     * Retrofit 使用 OkHttp 上传时的 RequestBody
     *
     * @param value 参数
     * @return
     */
    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    /**
     * Retrofit 使用 OkHttp 上传时的 file 包装
     *
     * @param key  参数
     * @param file 文件名
     * @return
     */
    public static MultipartBody.Part getRequestPart(String key, File file) {
        RequestBody fileBody = MultipartBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }

    /**
     * @param key
     * @param file
     * @return
     */
    public static MultipartBody.Part getRequestPartFile(String key, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        return MultipartBody.Part.createFormData(key, file.getName(), fileBody);
    }

    /**
     * @param key   参数
     * @param files 文件名列表
     * @return
     */
    public static MultipartBody.Part[] getRequestParts(String key, File... files) {
        MultipartBody.Part[] parts = new MultipartBody.Part[files.length];
        for (int i = 0; i < files.length; i++) {
            parts[i] = getRequestPart(key, files[i]);
        }
        return parts;
    }

    /**
     * @param host   服务器地址（必须以 / 结尾）
     * @return
     */
    private static Retrofit.Builder getService(String host) {
        if (TextUtils.isEmpty(host)) {
            host = "http://localhost/";
        }
        return new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(FastJsonConverterFactory.create())  // 添加 fastJson 解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static class Builder{
        private Retrofit.Builder retrofit;
        private OkHttpClient.Builder okHttpClient;

        private Builder(String host){
            this.okHttpClient = new OkHttpClient.Builder();
            this.retrofit = getService(host);
        }

        /**
         * 创建构造器
         * @param host
         * @return
         */
        public static Builder create(String host){
            return new Builder(host);
        }

        /**
         * 添加cookie持久化
         * @return
         */
        public Builder addCookiePersistence() {
            try {
                this.okHttpClient.addInterceptor(new ReceivedCookiesInterceptor());
                this.okHttpClient.addInterceptor(new AddCookiesInterceptor());
            } catch (Exception e) {
                throw new RuntimeException("retrofit addCookiePersistence Exception,"+e.toString());
            }
            return this;
        }

        /**
         * 自定义拦截器
         * @return
         */
        public <T extends Interceptor,E extends Interceptor>Builder addCustomInterceptor(T receiced, E request) {
            if (receiced == null||request==null) {
                throw new RuntimeException("retrofit addCustomInterceptor param is null,Please add interceptor!");
            }
            try {
                this.okHttpClient.addInterceptor(receiced);
                this.okHttpClient.addInterceptor(request);
            } catch (Exception e) {
                throw new RuntimeException("retrofit addCustomInterceptor Exception !"+e.toString());
            }
            return this;
        }

        /**
         * 自定义插值器
         * @return
         */
        public <T extends Converter.Factory>Builder addCustomConverter(T t) {
            if (t == null) {
                throw new RuntimeException("retrofit addCustomConverter param is null,Please add Converter!");
            }
            try {
                this.retrofit.addConverterFactory(t); // 添加插解析器
            } catch (Exception e) {
                throw new RuntimeException("retrofit addCustomConverter Exception !"+e.toString());
            }
            return this;
        }


        /**
         * 添加https请求协议
         * @return
         */
        public Builder addSSL() {
            HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory();
            this.okHttpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            return this;
        }

        /**
         * 添加https请求协议
         * @param ssl cart公钥
         * @return
         */
        public Builder addSSL(String ssl) {
            HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(new Buffer()
                    .writeUtf8(ssl)
                    .inputStream());
            this.okHttpClient.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            return this;
        }


        /**
         * 最后创建apiService.class
         * @param tClass
         * @param <T>
         * @return
         */
        public <T> T build(Class<T> tClass) {
            if (tClass == null) {
                throw new RuntimeException("Api class is null!");
            }
            return retrofit.client(okHttpClient.build()).build().create(tClass);
        }
    }
}
