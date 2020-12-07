package com.Xangars;


import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.*;

public class EasySSLProtocolSocketFactory implements SecureProtocolSocketFactory {

    private SSLContext sslcontext = null;

    /**
     * Constructor for com.yesbank.EasySSLProtocolSocketFactory.
     */
    public EasySSLProtocolSocketFactory() {
        super();
    }

    private static SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(
                    null,
                    new TrustManager[] {new EasyX509TrustManager(null)},
                    null);
            return context;
        } catch (Exception e) {

            throw new HttpClientError(e.toString());
        }
    }

    private SSLContext getSSLContext() {
        if (this.sslcontext == null) {
            this.sslcontext = createEasySSLContext();
        }
        return this.sslcontext;
    }



    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {

        return getSSLContext().getSocketFactory().createSocket(
                socket,
                host,
                port,
                autoClose
        );


    }

    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(
                s,
                i,
                inetAddress,
                i1
        );
    }

    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams httpConnectionParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        if (httpConnectionParams == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }

        int timeout = httpConnectionParams.getConnectionTimeout();
        SocketFactory socketfactory = getSSLContext().getSocketFactory();
        if (timeout == 0) {
            return socketfactory.createSocket(host, port, localAddress, localPort);
        } else {
            Socket socket = socketfactory.createSocket();
            SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
            SocketAddress remoteaddr = new InetSocketAddress(host, port);
            socket.bind(localaddr);
            socket.connect(remoteaddr, timeout);
            return socket;
        }
    }

    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {

        return getSSLContext().getSocketFactory().createSocket(
                s,
                i
        );

    }

    public boolean equals(Object obj) {
        return ((obj != null) && obj.getClass().equals(EasySSLProtocolSocketFactory.class));
    }

    public int hashCode() {
        return EasySSLProtocolSocketFactory.class.hashCode();
    }

}
