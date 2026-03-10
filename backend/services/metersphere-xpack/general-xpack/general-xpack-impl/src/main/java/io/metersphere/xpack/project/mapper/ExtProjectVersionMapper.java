package io.metersphere.xpack.project.mapper;

import io.metersphere.xpack.project.dto.ProjectVersionDTO;
import io.metersphere.xpack.project.dto.request.ProjectVersionRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/* JADX INFO: compiled from: h */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/mapper/ExtProjectVersionMapper.class */
public interface ExtProjectVersionMapper {
    List<ProjectVersionDTO> list(@Param("request") ProjectVersionRequest projectVersionRequest);
}
