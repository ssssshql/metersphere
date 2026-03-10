package io.metersphere.xpack.client;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/* JADX INFO: compiled from: ob */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/client/QrCodeClient.class */
@Service
public class QrCodeClient {
    private static final int CONNECT_TIMEOUT = 10000;
    private static final RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        simpleClientHttpRequestFactory.setReadTimeout(CONNECT_TIMEOUT);
        restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String get(String a) throws MSException {
        try {
            return ALLATORIxDEMO(restTemplate.getForEntity(a, Object.class, new Object[0]));
        } catch (Exception e) {
            LogUtils.error(LarkLoginService.ALLATORIxDEMO("3n\u000fj8v\u0012\u007f\u0015n枞诸奊贿"), e);
            throw new MSException("HttpClient查询失败: " + e.getMessage());
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String exchange(String a, String a2, String a3, MediaType a4, MediaType a5) throws MSException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(a4);
        if (a5 != null) {
            httpHeaders.setAccept(Collections.singletonList(a5));
        }
        if (StringUtils.isNotBlank(a3)) {
            httpHeaders.set(a3, a2);
        }
        try {
            return ALLATORIxDEMO(restTemplate.exchange(a, HttpMethod.GET, new HttpEntity(httpHeaders), Object.class, new Object[0]));
        } catch (Exception e) {
            LogUtils.error(ProjectVersionService.ALLATORIxDEMO("\"e\u001ea)}\u0003t\u0004e枏诳奛贴"), e);
            throw new MSException("HttpClient查询失败: " + e.getMessage());
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String exchangeString(String a, String a2, String a3, MediaType a4, MediaType a5) throws MSException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(a4);
        if (a5 != null) {
            httpHeaders.setAccept(Collections.singletonList(a5));
        }
        if (StringUtils.isNotBlank(a3)) {
            httpHeaders.set(a3, a2);
        }
        ResponseEntity responseEntityExchange = restTemplate.exchange(a, HttpMethod.GET, new HttpEntity(httpHeaders), String.class, new Object[0]);
        try {
            if (responseEntityExchange.getStatusCode().value() >= 400) {
                throw new Exception("StatusCode: " + String.valueOf(responseEntityExchange.getStatusCode()));
            }
            return JSON.toJSONString(responseEntityExchange.getBody());
        } catch (Exception e) {
            LogUtils.error(LarkLoginService.ALLATORIxDEMO("3n\u000fj8v\u0012\u007f\u0015n枞诸奊贿"), e);
            throw new MSException("HttpClient查询失败: " + e.getMessage());
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String postExchange(String a, String a2, String a3, Object a4, MediaType a5, MediaType a6) throws MSException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(a5);
        if (a6 != null) {
            httpHeaders.setAccept(Collections.singletonList(a6));
        }
        if (StringUtils.isNotBlank(a3)) {
            httpHeaders.set(a3, a2);
        }
        try {
            return ALLATORIxDEMO(restTemplate.exchange(a, HttpMethod.POST, new HttpEntity(a4, httpHeaders), Object.class, new Object[0]));
        } catch (Exception e) {
            LogUtils.error(ProjectVersionService.ALLATORIxDEMO("\"e\u001ea)}\u0003t\u0004e枏诳奛贴"), e);
            throw new MSException("HttpClient查询失败: " + e.getMessage());
        }
    }

    private static /* synthetic */ String ALLATORIxDEMO(ResponseEntity<Object> responseEntity) throws Exception {
        if (responseEntity.getStatusCode().value() < 400) {
            return JSON.toJSONString(responseEntity.getBody());
        }
        throw new Exception("StatusCode: " + String.valueOf(responseEntity.getStatusCode()));
    }
}
