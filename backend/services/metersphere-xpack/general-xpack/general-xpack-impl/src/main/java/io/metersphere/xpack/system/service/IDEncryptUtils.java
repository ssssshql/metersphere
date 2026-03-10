package io.metersphere.xpack.system.service;

import io.metersphere.sdk.util.CodingUtils;
import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;

/* JADX INFO: compiled from: a */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/IDEncryptUtils.class */
public class IDEncryptUtils extends CodingUtils {
    private static final String iv7 = "1";

    /* JADX INFO: renamed from: k6 */
    private static final String f5k6 = "zy";
    private static final String iv5 = "9";
    private static final String iv1 = "12";

    /* JADX INFO: renamed from: k3 */
    private static final String f6k3 = "cloud";
    private static final String iv8 = "23";

    /* JADX INFO: renamed from: k4 */
    private static final String f7k4 = "ms";

    /* JADX INFO: renamed from: k1 */
    private static final String f8k1 = "fit";
    private static final String iv2 = "34";
    private static final String iv9 = "456";
    private static final String iv6 = "0";
    private static final String iv3 = "56";

    /* JADX INFO: renamed from: k5 */
    private static final String f9k5 = "io";
    private static final String iv4 = "78";

    /* JADX INFO: renamed from: k2 */
    private static final String f10k2 = ".";

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String aesEncrypt(Object a) {
        if (a != null) {
            return aesEncrypt(a.toString(), LdapRequest.ALLATORIxDEMO("\tt:z%v<} v/p=73`"), LdapRequest.ALLATORIxDEMO("},\u007f({*},\u007f.q y({*"));
        }
        return null;
    }
}
