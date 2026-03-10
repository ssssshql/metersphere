package io.metersphere.xpack.system.service.platform;

import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.domain.PlatformSource;
import io.metersphere.system.domain.PlatformSourceExample;
import io.metersphere.system.dto.sdk.OptionDTO;
import io.metersphere.system.mapper.PlatformSourceMapper;
import io.metersphere.xpack.system.dto.PlatformSourceDTO;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: j */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/platform/PlatformParamService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class PlatformParamService {

    @Resource
    private PlatformSourceMapper platformSourceMapper;

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public List<PlatformSourceDTO> getPlatformInfo() {
        List<PlatformSourceDTO> arrayList = new ArrayList<>();
        List<PlatformSource> listSelectByExampleWithBLOBs = this.platformSourceMapper.selectByExampleWithBLOBs(new PlatformSourceExample());
        if (CollectionUtils.isNotEmpty(listSelectByExampleWithBLOBs)) {
            Iterator it = listSelectByExampleWithBLOBs.iterator();
            while (it.hasNext()) {
                PlatformSource platformSource = (PlatformSource) it.next();
                PlatformSourceDTO platformSourceDTO = new PlatformSourceDTO();
                BeanUtils.copyBean(platformSourceDTO, platformSource);
                String str = new String(platformSource.getConfig(), StandardCharsets.UTF_8);
                if (StringUtils.isNotBlank(str)) {
                    if (ObjectUtils.isNotEmpty(JSON.parseMap(str).get(AuthSourceRequest.ALLATORIxDEMO("H\tY*L\u001a[\u001c]")))) {
                        platformSourceDTO.setHasConfig(true);
                    } else {
                        platformSourceDTO.setHasConfig(true);
                    }
                } else {
                    platformSourceDTO.setHasConfig(false);
                }
                arrayList.add(platformSourceDTO);
                it = it;
            }
        }
        return arrayList.stream().sorted(Comparator.comparing((PlatformSourceDTO v0) -> {
            return v0.getPlatform();
        }).reversed()).toList();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public List<OptionDTO> getPlatformParam() {
        List<OptionDTO> arrayList = new ArrayList<>();
        PlatformSourceExample platformSourceExample = new PlatformSourceExample();
        platformSourceExample.createCriteria().andEnableEqualTo(true).andValidEqualTo(true);
        List listSelectByExample = this.platformSourceMapper.selectByExample(platformSourceExample);
        if (CollectionUtils.isNotEmpty(listSelectByExample)) {
            Iterator it = listSelectByExample.iterator();
            while (it.hasNext()) {
                PlatformSource platformSource = (PlatformSource) it.next();
                OptionDTO optionDTO = new OptionDTO();
                it = it;
                optionDTO.setId(platformSource.getPlatform());
                optionDTO.setName(platformSource.getEnable().toString());
                arrayList.add(optionDTO);
            }
        }
        return arrayList.stream().sorted(Comparator.comparing((OptionDTO v0) -> {
            return v0.getId();
        }).reversed()).toList();
    }
}
