package com.multimedia.access.input;

import com.multimedia.access.DataInput;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class SimpleHttpClient implements DataInput {
    private static OkHttpClient sClient = null;

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

    private HttpUrl mUrl;
    private Headers mRequestHeaders;

    private boolean mIsSupportRangeRequests = false;

    private long mContentLength;
    private long mReadPosition;

    private InputStream mContentStream;

    public SimpleHttpClient(URI uri) {
        this(uri, null);
    }

    public SimpleHttpClient(URI uri, Map<String, String> properties) {
        mUrl = HttpUrl.get(uri);

        if (properties != null) {
            Headers.Builder builder = new Headers.Builder();

            for (String key : properties.keySet()) {
                builder.add(key, properties.get(key));
            }

            mRequestHeaders = builder.build();
        }
    }

    @Override
    public void open() throws IOException {
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
        mReadPosition = 0;

        mContentStream = response.body().byteStream();
    }

    @Override
    public long size() {
        if (!isConnected()) {
            throw new IllegalStateException("not connect yet");
        }

        return mContentLength;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length < 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid read length");
        }

        if (!isConnected()) {
            throw new IllegalStateException("not connect yet");
        }

        if (length == 0) {
            return 0;
        }
        else {
            int ret = mContentStream.read(buffer, offset, length);
            if (ret > 0) {
                mReadPosition += ret;
            }

            return ret;
        }
    }

    @Override
    public long offset() {
        if (!isConnected()) {
            throw new IllegalStateException("not connect yet");
        }

        return mReadPosition;
    }

    @Override
    public void seek(long position) throws IOException {
        if (!isConnected()) {
            throw new IllegalStateException("not connect yet");
        }

        if (!mIsSupportRangeRequests) {
            throw new IllegalStateException("not support range requests");
        }

        if (position < 0 || position >= mContentLength) {
            throw new IllegalArgumentException("invalid position");
        }

        try {
            mContentStream.close();
        }
        catch (IOException e) {
            //ignore
        }

        Request request = createRequest(position);

        Response response = getCallFactory().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("connect fail, code is " + response.code());
        }

        mReadPosition = position;

        mContentStream = response.body().byteStream();
    }

    @Override
    public void close() {
        if (!isConnected()) {
            throw new IllegalStateException("not connect yet");
        }

        try {
            mContentStream.close();
        }
        catch (IOException e) {
            //ignore
        }
    }

    private Request createRequest(long offset) {
        Request.Builder builder = new Request.Builder();

        builder.get();
        builder.url(mUrl);

        if (mRequestHeaders != null) {
            builder.headers(mRequestHeaders);
        }

        if (offset > 0) {
            builder.addHeader("Range", "bytes=" + offset + "-");
        }

        return builder.build();
    }

    private boolean isConnected() {
        return mContentStream != null;
    }
}
