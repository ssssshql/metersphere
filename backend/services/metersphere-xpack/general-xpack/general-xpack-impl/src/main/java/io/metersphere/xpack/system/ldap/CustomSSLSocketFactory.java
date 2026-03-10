package io.metersphere.xpack.system.ldap;

import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: compiled from: q */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/ldap/CustomSSLSocketFactory.class */
public class CustomSSLSocketFactory extends SSLSocketFactory {
    private SSLSocketFactory socketFactory;

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress a, int a2, InetAddress a3, int a4) throws IOException {
        return this.socketFactory.createSocket(a, a2, a3, a4);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public CustomSSLSocketFactory() {
        try {
            SSLContext sSLContext = SSLContext.getInstance(AuthSourceRequest.ALLATORIxDEMO("}5z"));
            sSLContext.init(null, new TrustManager[]{new DummyTrustmanager()}, new SecureRandom());
            this.socketFactory = sSLContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String a, int a2, InetAddress a3, int a4) throws IOException {
        return this.socketFactory.createSocket(a, a2, a3, a4);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.socketFactory.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.socketFactory.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String a, int a2) throws IOException {
        return this.socketFactory.createSocket(a, a2);
    }

    public static SocketFactory getDefault() {
        return new CustomSSLSocketFactory();
    }

    /* JADX INFO: compiled from: q */
    /* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/ldap/CustomSSLSocketFactory$DummyTrustmanager.class */
    public static class DummyTrustmanager implements X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress a, int a2) throws IOException {
        return this.socketFactory.createSocket(a, a2);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket a, String a2, int a3, boolean z) throws IOException {
        return this.socketFactory.createSocket(a, a2, a3, z);
    }
}
