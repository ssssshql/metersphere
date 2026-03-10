package io.metersphere.xpack.config;

import com.fit2cloud.quartz.anno.QuartzScheduled;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.xpack.license.service.LicenseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/* JADX INFO: compiled from: cb */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/config/LicenseConfig.class */
@Configuration
public class LicenseConfig implements ApplicationRunner {

    @Resource
    private LicenseServiceImpl licenseServiceImpl;

    public void run(ApplicationArguments applicationArguments) throws Exception {
        LogUtils.info("初始化LICENSE状态: " + this.licenseServiceImpl.refreshLicense().getStatus());
    }

    @QuartzScheduled(cron = "0 2 0 * * ?")
    public void checkLicenseTask() {
        LogUtils.info("刷新LICENSE状态: " + this.licenseServiceImpl.refreshLicense().getStatus());
    }
}
