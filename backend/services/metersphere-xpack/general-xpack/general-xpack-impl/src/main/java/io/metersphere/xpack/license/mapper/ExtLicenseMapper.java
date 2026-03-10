package io.metersphere.xpack.license.mapper;

import io.metersphere.system.domain.License;
import org.apache.ibatis.annotations.Param;

/* JADX INFO: compiled from: d */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/license/mapper/ExtLicenseMapper.class */
public interface ExtLicenseMapper {
    License selectLicenseCode(@Param("code") String str);

    int deleteByPrimaryKey(String str);

    void update(@Param("record") License license);

    License get();

    void insert(@Param("record") License license);
}
