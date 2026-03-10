package io.metersphere.xpack.license.util;

import io.metersphere.sdk.util.LogUtils;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: aa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/license/util/Command.class */
public class Command {
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public String exeCmd(String a) {
        Process processExec;
        BufferedReader bufferedReader;
        StringBuilder sb;
        BufferedReader bufferedReader2;
        try {
            if (!StringUtils.containsAnyIgnoreCase(System.getProperty(LarkLoginService.ALLATORIxDEMO("u\b4\u0015{\u0016\u007f")), new CharSequence[]{LarkLoginService.ALLATORIxDEMO("m\u0012t\u001fu\fi")})) {
                processExec = Runtime.getRuntime().exec("./validator_linux " + a, (String[]) null, new File(LarkLoginService.ALLATORIxDEMO("To\bhTv\u0014y\u001avTx\u0012t")));
            } else {
                processExec = Runtime.getRuntime().exec("d:\\opt\\metersphere\\validator_windows.exe " + a);
            }
            bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            try {
                sb = new StringBuilder();
                bufferedReader2 = bufferedReader;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
        while (true) {
            String line = null;
            try {
                line = bufferedReader2.readLine();
                if (line != null) {
                    sb.append(line + "\n");
                    bufferedReader2 = bufferedReader;
                } else {
                    String string = sb.toString();
                    bufferedReader.close();
                    return string;
                }
            } catch (IOException e) {
                LogUtils.error(e.getMessage());
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}
