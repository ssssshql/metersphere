package io.metersphere.xpack.system.service;

import io.metersphere.sdk.constants.DefaultRepositoryDir;
import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.constants.StorageType;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.file.FileRequest;
import io.metersphere.sdk.file.MinioRepository;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.SystemParameter;
import io.metersphere.system.domain.SystemParameterExample;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.system.mapper.SystemParameterMapper;
import io.metersphere.xpack.system.dto.DisplayDTO;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/DisplayService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class DisplayService {

    @Resource
    private SystemParameterMapper systemParameterMapper;
    private static final String BASE_URL = "/base-display/get/";

    @Resource
    private MinioRepository repository;

    public List<SystemParameter> getParamList(String a) {
        SystemParameterExample systemParameterExample = new SystemParameterExample();
        systemParameterExample.createCriteria().andParamKeyLike(a + "%");
        return this.systemParameterMapper.selectByExample(systemParameterExample);
    }

    private /* synthetic */ void ALLATORIxDEMO(DisplayDTO a, FileRequest a2) {
        a2.setFileName(a.getFileName());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c1 A[PHI: r8
  0x00c1: PHI (r8v1 byte) = (r8v0 byte), (r8v0 byte), (r8v2 byte), (r8v0 byte), (r8v0 byte), (r8v0 byte) binds: [B:6:0x0045, B:23:0x00bb, B:24:0x00be, B:19:0x00a5, B:15:0x0091, B:8:0x0079] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<DisplayDTO> uiInfo(String a) {
        byte b = -1;
        List<SystemParameter> paramList = getParamList(a);
        ArrayList arrayList = new ArrayList();
        Iterator<SystemParameter> it = paramList.iterator();
        while (it.hasNext()) {
            SystemParameter next = it.next();
            DisplayDTO displayDTO = new DisplayDTO();
            BeanUtils.copyBean(displayDTO, next);
            String paramKey = next.getParamKey();
            byte b2 = -1;
            switch (paramKey.hashCode()) {
                case -2123174132:
                    b = !paramKey.equals(AuthSourceRequest.ALLATORIxDEMO("\\\u0010\u0007\u0015F\u001e@\u0017`\u0014H\u001eL")) ? b2 : (byte) 1;
                    break;
                case -484482157:
                    if (paramKey.equals(LarkLoginService.ALLATORIxDEMO("o\u00124\u0012y\u0014t"))) {
                        b = 0;
                        break;
                    }
                    break;
                case 347243994:
                    if (paramKey.equals(LarkLoginService.ALLATORIxDEMO("\u000esUv\u0014}\u0012t7u\u001cu"))) {
                        b = 2;
                        break;
                    }
                    break;
                case 1706435672:
                    if (paramKey.equals(AuthSourceRequest.ALLATORIxDEMO("\\\u0010\u0007\u0015F\u001eF)E\u0018]\u001fF\u000bD"))) {
                        b2 = 3;
                    }
                    break;
            }
            switch (b) {
                case 0:
                    displayDTO.setFileName(LarkLoginService.ALLATORIxDEMO("Tx\u001ai\u001e7\u001fs\bj\u0017{\u00025\u001c\u007f\u000f5\u0012y\u0014t"));
                    break;
                case 1:
                    displayDTO.setFileName(AuthSourceRequest.ALLATORIxDEMO("\u0006\u001bH\nLTM\u0010Z\tE\u0018PVN\u001c]VE\u0016N\u0010GT@\u0014H\u001eL"));
                    break;
                case 2:
                    displayDTO.setFileName(LarkLoginService.ALLATORIxDEMO("Tx\u001ai\u001e7\u001fs\bj\u0017{\u00025\u001c\u007f\u000f5\u0017u\u001cs\u00157\u0017u\u001cu"));
                    break;
                case 3:
                    displayDTO.setFileName(AuthSourceRequest.ALLATORIxDEMO("\u0006\u001bH\nLTM\u0010Z\tE\u0018PVN\u001c]VE\u0016N\u0016\u0004\tE\u0018]\u001fF\u000bD"));
                    break;
                default:
                    displayDTO.setFileName("");
                    break;
            }
            arrayList.add(displayDTO);
            it = it;
        }
        return arrayList;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private /* synthetic */ boolean ALLATORIxDEMO(List<DisplayDTO> list, List<MultipartFile> list2) throws MSException {
        if (CollectionUtils.isEmpty(list2)) {
            return false;
        }
        for (MultipartFile multipartFile : list2) {
            if (!multipartFile.isEmpty()) {
                String[] strArrSplit = ((String) Objects.requireNonNull(multipartFile.getOriginalFilename())).split(LarkLoginService.ALLATORIxDEMO("6"));
                String str = strArrSplit[1];
                if (StringUtils.endsWithAny(str, new CharSequence[]{AuthSourceRequest.ALLATORIxDEMO("C\tN"), LarkLoginService.ALLATORIxDEMO("\u0011j\u001e}"), AuthSourceRequest.ALLATORIxDEMO("Y\u0017N"), LarkLoginService.ALLATORIxDEMO("i\r}"), AuthSourceRequest.ALLATORIxDEMO("@\u001aF")})) {
                    list.stream().filter(a -> {
                        return a.getParamKey().equalsIgnoreCase(strArrSplit[0]);
                    }).forEach(a2 -> {
                        a2.setFileName(str);
                        a2.setFile(multipartFile);
                    });
                } else {
                    throw new MSException(LarkLoginService.ALLATORIxDEMO("O\u000bv\u0014{\u001f\u007f\u001f:\u0012w\u001a}\u001ei[~\u0014:\u0015u\u000f:\u0016\u007f\u001en[n\u0013\u007f[s\u0016{\u001c\u007f[|\u0014h\u0016{\u000f:\t\u007f\no\u0012h\u001ew\u001et\u000fi"));
                }
            }
        }
        return false;
    }

    private /* synthetic */ FileRequest ALLATORIxDEMO(DisplayDTO a) {
        SystemParameter systemParameterSelectByPrimaryKey = this.systemParameterMapper.selectByPrimaryKey(a.getParamKey());
        FileRequest fileRequest = new FileRequest();
        if (systemParameterSelectByPrimaryKey != null && StringUtils.isNotEmpty(systemParameterSelectByPrimaryKey.getParamValue())) {
            fileRequest.setFileName(systemParameterSelectByPrimaryKey.getParamValue());
        }
        fileRequest.setFolder(DefaultRepositoryDir.getSystemRootDir());
        fileRequest.setStorage(StorageType.MINIO.name());
        return fileRequest;
    }

    private /* synthetic */ List<SystemParameter> ALLATORIxDEMO(List<DisplayDTO> list) {
        SystemParameterExample systemParameterExample = new SystemParameterExample();
        ArrayList arrayList = new ArrayList();
        list.forEach(a -> {
            systemParameterExample.createCriteria().andParamKeyEqualTo(a.getParamKey());
            arrayList.addAll(this.systemParameterMapper.selectByExample(systemParameterExample));
            systemParameterExample.clear();
        });
        return arrayList;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 4, instructions: 4 */
    public void save(List<DisplayDTO> list, List<MultipartFile> list2) throws MSException {
        if (!ALLATORIxDEMO(list, list2)) {
            for (DisplayDTO displayDTO : list) {
                MultipartFile file = displayDTO.getFile();
                if (displayDTO.getType().equalsIgnoreCase(AuthSourceRequest.ALLATORIxDEMO("\u001f@\u0015L"))) {
                    FileRequest fileRequestALLATORIxDEMO = ALLATORIxDEMO(displayDTO);
                    try {
                        this.repository.delete(fileRequestALLATORIxDEMO);
                        if (file != null) {
                            try {
                                ALLATORIxDEMO(displayDTO, fileRequestALLATORIxDEMO);
                                this.repository.saveFile(file, fileRequestALLATORIxDEMO);
                                displayDTO.setParamValue(displayDTO.getFileName());
                            } catch (Exception unused) {
                                throw new MSException(AuthSourceRequest.ALLATORIxDEMO("Z\u0018_\u001c\t\u001f@\u0015LYL\u000b[\u0016["));
                            }
                        }
                        if (file == null && StringUtils.isEmpty(displayDTO.getFileName())) {
                            displayDTO.setParamValue(null);
                        }
                    } catch (Exception unused2) {
                        throw new MSException(LarkLoginService.ALLATORIxDEMO("~\u001ev\u001en\u001e:\u001ds\u0017\u007f[\u007f\th\u0014h"));
                    }
                }
                this.systemParameterMapper.deleteByPrimaryKey(displayDTO.getParamKey());
                if (!displayDTO.isOriginal()) {
                    this.systemParameterMapper.insert(displayDTO);
                }
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public LogDTO updateLog(List<DisplayDTO> list, List<MultipartFile> list2) throws MSException {
        ALLATORIxDEMO(list, list2);
        List<SystemParameter> listALLATORIxDEMO = ALLATORIxDEMO(list);
        LogDTO logDTO = new LogDTO(AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), LarkLoginService.ALLATORIxDEMO("(C(N>W"), AuthSourceRequest.ALLATORIxDEMO("*p*}<d"), (String) null, OperationLogType.UPDATE.name(), LarkLoginService.ALLATORIxDEMO("(_/N2T<E(C(N>W$J:H:W>N>H$J:]>E8U5\\2]"), AuthSourceRequest.ALLATORIxDEMO("电靋诇罇"));
        logDTO.setPath(LarkLoginService.ALLATORIxDEMO("5\u001fs\bj\u0017{\u00025\b{\r\u007f"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        logDTO.setOriginalValue(JSON.toJSONBytes(listALLATORIxDEMO));
        return logDTO;
    }
}
