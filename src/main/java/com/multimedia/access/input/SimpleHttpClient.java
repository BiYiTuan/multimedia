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

    private InputStream mContentStream = null;

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
    public long open(long offset) throws IOException {
        if (offset < 0) {
            throw new IllegalArgumentException("invalid offset");
        }

        Request request = createRequest(offset);

        Response response = getCallFactory().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("connect fail, code is " + response.code());
        }

        mContentStream = response.body().byteStream();

        return response.body().contentLength();
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return read(buffer, 0, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        if (offset < 0 || offset >= buffer.length) {
            throw new IllegalArgumentException("invalid buffer offset");
        }

        if (length <= 0 || offset + length > buffer.length) {
            throw new IllegalArgumentException("invalid read length");
        }

        if (!isConnected()) {
            throw new IllegalStateException("not connect yet");
        }

        return mContentStream.read(buffer, offset, length);
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
