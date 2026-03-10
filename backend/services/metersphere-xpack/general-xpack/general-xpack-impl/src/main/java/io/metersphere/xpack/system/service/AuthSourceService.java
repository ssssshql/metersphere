package io.metersphere.xpack.system.service;

import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.BeanUtils;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.domain.AuthSource;
import io.metersphere.system.domain.AuthSourceExample;
import io.metersphere.system.dto.AuthSourceDTO;
import io.metersphere.system.dto.sdk.BasePageRequest;
import io.metersphere.system.mapper.AuthSourceMapper;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.xpack.project.service.ProjectVersionService;
import io.metersphere.xpack.system.dto.request.AuthSourceRequest;
import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: z */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/AuthSourceService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class AuthSourceService {

    @Resource
    private AuthSourceMapper authSourceMapper;

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public AuthSourceRequest updateAuthSource(AuthSourceRequest a) throws MSException {
        checkAuthSource(a);
        AuthSource authSourceSelectByPrimaryKey = this.authSourceMapper.selectByPrimaryKey(a.getId());
        if (authSourceSelectByPrimaryKey != null) {
            authSourceSelectByPrimaryKey.setName(a.getName());
            authSourceSelectByPrimaryKey.setDescription(a.getDescription());
            authSourceSelectByPrimaryKey.setConfiguration(a.getConfiguration().getBytes());
            authSourceSelectByPrimaryKey.setType(a.getType());
            authSourceSelectByPrimaryKey.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
            this.authSourceMapper.updateByPrimaryKeySelective(authSourceSelectByPrimaryKey);
        }
        return a;
    }

    public List<String> getAuthSourceList() {
        AuthSourceExample authSourceExample = new AuthSourceExample();
        authSourceExample.createCriteria().andEnableEqualTo(true);
        return this.authSourceMapper.selectByExample(authSourceExample).stream().map((v0) -> {
            return v0.getType();
        }).distinct().toList();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public AuthSource addAuthSource(AuthSourceRequest a) throws MSException {
        checkAuthSource(a);
        AuthSource authSourceALLATORIxDEMO = ALLATORIxDEMO(a);
        this.authSourceMapper.insertSelective(authSourceALLATORIxDEMO);
        return authSourceALLATORIxDEMO;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public void checkAuthSource(AuthSourceRequest a) throws MSException {
        String name = a.getName();
        AuthSourceExample authSourceExample = new AuthSourceExample();
        AuthSourceExample.Criteria criteriaCreateCriteria = authSourceExample.createCriteria();
        criteriaCreateCriteria.andNameEqualTo(name);
        if (StringUtils.isNotBlank(a.getId())) {
            criteriaCreateCriteria.andIdNotEqualTo(a.getId());
        }
        if (this.authSourceMapper.countByExample(authSourceExample) > 0) {
            throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("\u000bd\u001ey\u0019~\u001fc\tt5\u007f\u000b|\u000fN\u000b}\u0018t\u000bu\u0013N\u000fi\u0003b\u001eb")));
        }
        if (StringUtils.isBlank(a.getConfiguration().toString())) {
            throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("\u000bd\u001ey\u0019~\u001fc\tt5r\u0005\u007f\fx\rd\u0018p\u001ex\u0005\u007f5x\u0019N\u0004d\u0006}")));
        }
    }

    public AuthSource updateStatus(String a, Boolean a2) {
        if (BooleanUtils.isTrue(a2)) {
            AuthSource authSourceSelectByPrimaryKey = this.authSourceMapper.selectByPrimaryKey(a);
            AuthSourceExample authSourceExample = new AuthSourceExample();
            authSourceExample.createCriteria().andIdNotEqualTo(a).andTypeEqualTo(authSourceSelectByPrimaryKey.getType()).andEnableEqualTo(true);
            List listSelectByExample = this.authSourceMapper.selectByExample(authSourceExample);
            if (CollectionUtils.isNotEmpty(listSelectByExample)) {
                AuthSource authSource = (AuthSource) listSelectByExample.get(0);
                authSource.setEnable(false);
                this.authSourceMapper.updateByPrimaryKeySelective(authSource);
            }
        }
        AuthSource authSource2 = new AuthSource();
        authSource2.setId(a);
        authSource2.setEnable(Boolean.valueOf(BooleanUtils.toBooleanDefaultIfNull(a2, false)));
        authSource2.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        this.authSourceMapper.updateByPrimaryKeySelective(authSource2);
        return authSource2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public AuthSourceDTO getAuthSource(String a) throws MSException {
        AuthSource authSourceSelectByPrimaryKey = this.authSourceMapper.selectByPrimaryKey(a);
        if (authSourceSelectByPrimaryKey == null) {
            throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("\u000bd\u001ey\u0019~\u001fc\tt5x\u0019N\u000et\u0006t\u001et")));
        }
        AuthSourceDTO authSourceDTO = new AuthSourceDTO();
        BeanUtils.copyBean(authSourceDTO, authSourceSelectByPrimaryKey);
        authSourceDTO.setConfiguration(new String(authSourceSelectByPrimaryKey.getConfiguration(), StandardCharsets.UTF_8));
        return authSourceDTO;
    }

    public void deleteAuthSource(String a) {
        this.authSourceMapper.deleteByPrimaryKey(a);
    }

    public List<AuthSource> list(BasePageRequest a) {
        AuthSourceExample authSourceExample = new AuthSourceExample();
        authSourceExample.createCriteria().andNameLike("%" + a.getKeyword() + "%");
        return this.authSourceMapper.selectByExample(authSourceExample);
    }

    private /* synthetic */ AuthSource ALLATORIxDEMO(AuthSourceRequest a) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        AuthSource authSource = new AuthSource();
        authSource.setName(a.getName());
        authSource.setConfiguration(a.getConfiguration().getBytes(StandardCharsets.UTF_8));
        authSource.setDescription(a.getDescription());
        authSource.setType(a.getType());
        authSource.setCreateTime(Long.valueOf(jCurrentTimeMillis));
        authSource.setUpdateTime(Long.valueOf(jCurrentTimeMillis));
        authSource.setId(IDGenerator.nextStr());
        return authSource;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public AuthSourceDTO getAuthSourceByType(String a) throws MSException {
        AuthSourceExample authSourceExample = new AuthSourceExample();
        authSourceExample.createCriteria().andEnableEqualTo(true).andTypeEqualTo(a);
        List listSelectByExampleWithBLOBs = this.authSourceMapper.selectByExampleWithBLOBs(authSourceExample);
        if (!CollectionUtils.isEmpty(listSelectByExampleWithBLOBs)) {
            AuthSource authSource = (AuthSource) listSelectByExampleWithBLOBs.get(0);
            AuthSourceDTO authSourceDTO = new AuthSourceDTO();
            BeanUtils.copyBean(authSourceDTO, authSource);
            authSourceDTO.setConfiguration(new String(authSource.getConfiguration(), StandardCharsets.UTF_8));
            return authSourceDTO;
        }
        throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("\u000bd\u001ey\u0019~\u001fc\tt5x\u0019N\u000et\u0006t\u001et")));
    }
}
