package io.metersphere.xpack.license.enums;

import io.metersphere.xpack.project.service.ProjectVersionService;
import lombok.Generated;

/* JADX INFO: compiled from: xb */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/license/enums/LicenseEnum.class */
public enum LicenseEnum {
    VALID(ProjectVersionService.ALLATORIxDEMO("g\u000b}\u0003u")),
    ERROR(ProjectVersionService.ALLATORIxDEMO("T\u0018c\u0005c"));

    private final String name;

    /* synthetic */ LicenseEnum(String a) {
        this.name = a;
    }

    @Generated
    public String getName() {
        return this.name;
    }
}
