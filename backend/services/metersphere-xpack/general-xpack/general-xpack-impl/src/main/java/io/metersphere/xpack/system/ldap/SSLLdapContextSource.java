package io.metersphere.xpack.system.ldap;

import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import java.util.Hashtable;
import org.springframework.ldap.core.support.LdapContextSource;

/* JADX INFO: compiled from: f */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/ldap/SSLLdapContextSource.class */
public class SSLLdapContextSource extends LdapContextSource {
    public Hashtable<String, Object> getAnonymousEnv() {
        Hashtable<String, Object> anonymousEnv = super.getAnonymousEnv();
        anonymousEnv.put(AuthSourceRequest.ALLATORIxDEMO("C\u0018_\u0018\u0007\u0017H\u0014@\u0017NWZ\u001cJ\f[\u0010]\u0000\u0007\t[\u0016]\u0016J\u0016E"), ProjectVersionService.ALLATORIxDEMO("b\u0019}"));
        anonymousEnv.put(AuthSourceRequest.ALLATORIxDEMO("C\u0018_\u0018\u0007\u0017H\u0014@\u0017NWE\u001dH\t\u0007\u001fH\u001a]\u0016[\u0000\u0007\nF\u001aB\u001c]"), CustomSSLSocketFactory.class.getName());
        anonymousEnv.put(ProjectVersionService.ALLATORIxDEMO("{\u000bg\u000b?\u0004p\u0007x\u0004vDw\u000br\u001e~\u0018hDx\u0004x\u001ex\u000b}"), AuthSourceRequest.ALLATORIxDEMO("\u001aF\u0014\u0007\n\\\u0017\u0007\u0013G\u001d@WE\u001dH\t\u00075M\u0018Y:]\u0001o\u0018J\rF\u000bP"));
        return anonymousEnv;
    }
}
