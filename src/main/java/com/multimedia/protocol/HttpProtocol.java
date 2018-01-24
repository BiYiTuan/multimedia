package com.multimedia.protocol;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class HttpProtocol implements Protocol {
    private static OkHttpClient sClient = null;

    private Map<String, String> mDefaultRequestHeaders;
    private HttpUrl mUrl;

    private boolean mIsSupportRangeRequests = false;

    private long mContentLength;
    private InputStream mContentStream;

    public HttpProtocol() {
        this(null);
    }

    public HttpProtocol(Map<String, String> defaultRequestHeaders) {
        mDefaultRequestHeaders = defaultRequestHeaders;
    }

    @Override
    public void open(URL url) throws IOException {
        mUrl = HttpUrl.get(url);
        Request request = createRequest(0);

        Response response = getCallFactory().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("connect fail, code is " + response.code());
        }

        String value = response.header("Accept-Ranges", "none");
        if (value.equals("bytes")) {
            mIsSupportRangeRequests = true;
        }

        mContentLength = response.body().contentLength();
        mContentStream = response.body().byteStream();
    }

    @Override
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

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        throw new IllegalStateException("not support");
    }

    @Override
    public void seek(long position) throws IOException {
        if (!mIsSupportRangeRequests) {
            throw new IllegalStateException("not support range requests");
        }

        if (position < 0 || position >= mContentLength) {
            throw new IllegalArgumentException("invalid file position");
        }

        mContentStream.close();

        Request request = createRequest(position);

        Response response = getCallFactory().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("connect fail, code is " + response.code());
        }

        mContentStream = response.body().byteStream();
    }

    @Override
    public void close() throws IOException {
        if (mContentStream != null) {
            mContentStream.close();
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
