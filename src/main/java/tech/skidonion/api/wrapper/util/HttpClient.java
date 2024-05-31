package tech.skidonion.api.wrapper.util;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpClient {

    private final Map<String, String> commonHeader = new HashMap<>();
    private Proxy proxy;

    public HttpClient() {
        commonHeader.put("User-agent", "Mozilla/5.0 AppIeWebKit");
    }

    public void addHeader(String key,String value){
        this.commonHeader.put(key,value);
    }

    public void setProxy(String host, int port) {
        SocketAddress proxyAddress = new InetSocketAddress(host, port);
        this.proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
    }

    private HttpURLConnection createUrlConnection(URL url, int time) throws IOException {
        HttpURLConnection connection;
        if (proxy != null) {
            connection = (HttpURLConnection) url.openConnection(proxy);
        } else {
            connection = (HttpURLConnection) url.openConnection();
        }
        connection.setConnectTimeout(time);
        connection.setReadTimeout(time);
        connection.setUseCaches(false);
        return connection;
    }

    public String post(String url) {
        try {
            return post(new URL(url), 5000);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String post(URL url) {
        return post(url, 5000);
    }

    public String post(URL url, int time) {
        try {
            HttpURLConnection connection = createUrlConnection(url, time);
            InputStream inputStream = null;
            connection.setRequestMethod("POST");
            commonHeader.forEach(connection::setRequestProperty);

            String result;
            try {
                try {
                    inputStream = connection.getInputStream();
                    return toString(inputStream);
                } catch (IOException e) {
                    closeQuietly(inputStream);
                    inputStream = connection.getErrorStream();
                    if (inputStream == null) throw e;
                }
                result = toString(inputStream);
            } finally {
                closeQuietly(inputStream);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
        }
    }

    private String toString(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
        scanner.useDelimiter("\\A");
        String result = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return result;
    }
}
