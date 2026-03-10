package io.metersphere.xpack.license.service;

import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.LogUtils;
import io.metersphere.sdk.util.RsaUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.domain.License;
import io.metersphere.system.dto.sdk.LicenseDTO;
import io.metersphere.system.dto.sdk.LicenseInfoDTO;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.system.log.service.OperationLogService;
import io.metersphere.system.service.LicenseService;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.xpack.license.enums.LicenseEnum;
import io.metersphere.xpack.license.mapper.ExtLicenseMapper;
import io.metersphere.xpack.license.util.Command;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import jakarta.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/* JADX INFO: compiled from: ma */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/license/service/LicenseServiceImpl.class */
@Service
public class LicenseServiceImpl implements LicenseService {

    @Resource(name = "redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private OperationLogService operationLogService;

    @Resource
    private ExtLicenseMapper extLicenseMapper;
    private final String project = "MeterSphere";
    private final String LICENSE = "LICENSE";

    public synchronized LicenseDTO refreshLicense() {
        LicenseDTO licenseDTOALLATORIxDEMO;
        LicenseDTO licenseDTO = new LicenseDTO();
        licenseDTO.setStatus(LicenseEnum.ERROR.getName());
        licenseDTO.setLicense(new LicenseInfoDTO());
        License license = this.extLicenseMapper.get();
        if (license != null && (licenseDTOALLATORIxDEMO = ALLATORIxDEMO(new Command().exeCmd(license.getLicenseCode()))) != null && licenseDTOALLATORIxDEMO.getLicense() != null && StringUtils.equalsIgnoreCase(licenseDTOALLATORIxDEMO.getLicense().getProduct(), "MeterSphere")) {
            licenseDTO = licenseDTOALLATORIxDEMO;
        }
        this.redisTemplate.opsForValue().set("LICENSE", licenseDTO);
        return licenseDTO;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String getCode(String a) {
        License license = this.extLicenseMapper.get();
        if (license != null) {
            try {
                return RsaUtils.publicEncrypt(license.getLicenseCode(), a);
            } catch (NoSuchAlgorithmException e) {
                LogUtils.error(e);
                return null;
            }
        }
        return null;
    }

    private /* synthetic */ LicenseDTO ALLATORIxDEMO(String a) {
        return (LicenseDTO) JSON.parseObject(a, LicenseDTO.class);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LicenseDTO validate() {
        LicenseDTO licenseDTO = (LicenseDTO) this.redisTemplate.opsForValue().get("LICENSE");
        if (licenseDTO != null) {
            return licenseDTO;
        }
        return refreshLicense();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    public LicenseDTO addLicense(String a, String a2) throws MSException {
        LicenseServiceImpl licenseServiceImpl;
        String strExeCmd = new Command().exeCmd(a);
        if (!StringUtils.isEmpty(strExeCmd)) {
            LicenseDTO licenseDTOALLATORIxDEMO = ALLATORIxDEMO(strExeCmd);
            if (licenseDTOALLATORIxDEMO == null || !StringUtils.equalsIgnoreCase(licenseDTOALLATORIxDEMO.getLicense().getProduct(), "MeterSphere")) {
                throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("}\u0003r\u000f\u007f\u0019t5g\u000b}\u0003u5}\u0003r\u000f\u007f\u0019t5t\u0018c\u0005c")));
            }
            License license = this.extLicenseMapper.get();
            License license2 = license;
            if (license != null) {
                licenseServiceImpl = this;
                license2.setLicenseCode(a);
                licenseServiceImpl.extLicenseMapper.update(license2);
            } else {
                license2 = new License();
                licenseServiceImpl = this;
                license2.setId(IDGenerator.nextStr());
                license2.setCreateTime(Long.valueOf(System.currentTimeMillis()));
                license2.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
                license2.setLicenseCode(a);
                licenseServiceImpl.extLicenseMapper.insert(license2);
            }
            licenseServiceImpl.refreshLicense();
            LogDTO logDTO = new LogDTO(LarkLoginService.ALLATORIxDEMO("(C(N>W"), ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), license2.getId(), a2, OperationLogType.ADD.name(), LarkLoginService.ALLATORIxDEMO("(_/N2T<E(C(N>W$[.N3U)S!_?E6[5[<_6_5N"), ProjectVersionService.ALLATORIxDEMO("]\u0003r\u000f\u007f\u0019t揢杒"));
            logDTO.setPath(LarkLoginService.ALLATORIxDEMO("Tv\u0012y\u001et\b\u007fT{\u001f~"));
            logDTO.setMethod(HttpMethodConstants.POST.name());
            this.operationLogService.add(logDTO);
            return licenseDTOALLATORIxDEMO;
        }
        throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("}\u0003r\u000f\u007f\u0019t5g\u000b}\u0003u5}\u0003r\u000f\u007f\u0019t5t\u0018c\u0005c")));
    }
}
