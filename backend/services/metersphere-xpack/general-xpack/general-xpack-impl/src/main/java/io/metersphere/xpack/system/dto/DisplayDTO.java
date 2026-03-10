package io.metersphere.xpack.system.dto;

import io.metersphere.system.domain.SystemParameter;
import lombok.Generated;
import org.springframework.web.multipart.MultipartFile;

/* JADX INFO: compiled from: ya */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/dto/DisplayDTO.class */
public class DisplayDTO extends SystemParameter {
    private MultipartFile file;
    private String fileName;
    private boolean original;

    @Generated
    public MultipartFile getFile() {
        return this.file;
    }

    @Generated
    public boolean isOriginal() {
        return this.original;
    }

    @Generated
    public void setOriginal(boolean z) {
        this.original = z;
    }

    @Generated
    public void setFile(MultipartFile a) {
        this.file = a;
    }

    @Generated
    public void setFileName(String a) {
        this.fileName = a;
    }

    @Generated
    public String getFileName() {
        return this.fileName;
    }
}
