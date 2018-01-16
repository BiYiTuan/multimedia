package com.multimedia.source;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpDataSource implements DataSource {
    private static OkHttpClient sClient = null;

    private Map<String, String> mDefaultRequestHeaders;
    private HttpUrl mUrl;

    private long mContentLength = -1;
    private InputStream mContentStream = null;

    public HttpDataSource() {
        this(null);
    }

    public HttpDataSource(Map<String, String> defaultRequestHeaders) {
        mDefaultRequestHeaders = defaultRequestHeaders;
    }

    public void open(URI uri) throws IOException {
        mUrl = HttpUrl.get(uri);
        Request request = createRequest(0);

        Response response = getCallFactory().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("connect fail, code is " + response.code());
        }

        mContentLength = response.body().contentLength();
        mContentStream = response.body().byteStream();
    }

    public long getSize() {
        return mContentLength;
    }

    public void seek(long position) throws IOException {
        if (mContentLength == -1) {
            throw new IllegalStateException("can not seek");
        }

        if (position < 0 || position >= mContentLength) {
            throw new IllegalArgumentException("invalid file position");
        }

        Request request = createRequest(position);

        Response response = getCallFactory().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("connect fail, code is " + response.code());
        }

        mContentStream = response.body().byteStream();
    }

    public void close() throws IOException {
        mContentStream.close();
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid read length");
        }

        if (length == 0) {
            return 0;
        }
        else {
            return mContentStream.read(buffer, offset, length);
        }
    }

    private static Call.Factory getCallFactory() {
        if (sClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.connectTimeout(5, TimeUnit.SECONDS);
            builder.readTimeout(5, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.SECONDS);

            sClient = builder.build();
        }

        return sClient;
    }

    private Request createRequest(long position) {
        Request.Builder builder = new Request.Builder();

        builder.get();
        builder.url(mUrl);

        if (mDefaultRequestHeaders != null) {
            for (String key : mDefaultRequestHeaders.keySet()) {
                builder.addHeader(key, mDefaultRequestHeaders.get(key));
            }
        }

        if (position > 0) {
            builder.addHeader("Range", "bytes=" + position + "-");
        }

        return builder.build();
    }
}
