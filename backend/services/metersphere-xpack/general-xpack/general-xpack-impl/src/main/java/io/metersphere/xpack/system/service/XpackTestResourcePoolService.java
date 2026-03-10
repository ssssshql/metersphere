package io.metersphere.xpack.system.service;

import io.metersphere.project.domain.ProjectTestResourcePoolExample;
import io.metersphere.project.mapper.ProjectTestResourcePoolMapper;
import io.metersphere.sdk.constants.HttpMethodConstants;
import io.metersphere.sdk.constants.ResourcePoolTypeEnum;
import io.metersphere.sdk.exception.MSException;
import io.metersphere.sdk.util.CommonBeanFactory;
import io.metersphere.sdk.util.JSON;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.domain.TestResourcePool;
import io.metersphere.system.domain.TestResourcePoolBlob;
import io.metersphere.system.domain.TestResourcePoolOrganizationExample;
import io.metersphere.system.dto.pool.TestResourceDTO;
import io.metersphere.system.dto.pool.TestResourcePoolDTO;
import io.metersphere.system.dto.pool.TestResourcePoolRequest;
import io.metersphere.system.log.constants.OperationLogType;
import io.metersphere.system.log.dto.LogDTO;
import io.metersphere.system.mapper.TestResourcePoolBlobMapper;
import io.metersphere.system.mapper.TestResourcePoolMapper;
import io.metersphere.system.mapper.TestResourcePoolOrganizationMapper;
import io.metersphere.system.service.TestResourcePoolService;
import io.metersphere.system.uid.IDGenerator;
import io.metersphere.xpack.project.service.ProjectVersionService;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* JADX INFO: compiled from: y */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/system/service/XpackTestResourcePoolService.class */
@Transactional(rollbackFor = {Exception.class})
@Service
public class XpackTestResourcePoolService {

    @Resource
    protected TestResourcePoolService testResourcePoolService;

    @Resource
    private TestResourcePoolOrganizationMapper testResourcePoolOrganizationMapper;

    @Resource
    private TestResourcePoolBlobMapper testResourcePoolBlobMapper;

    @Resource
    private TestResourcePoolValidateServiceImpl testResourcePoolValidateService;

    @Resource
    private TestResourcePoolMapper testResourcePoolMapper;

    @Resource
    private ProjectTestResourcePoolMapper projectTestResourcePoolMapper;

    private /* synthetic */ void ALLATORIxDEMO(String a) {
        TestResourcePoolOrganizationExample testResourcePoolOrganizationExample = new TestResourcePoolOrganizationExample();
        testResourcePoolOrganizationExample.createCriteria().andTestResourcePoolIdEqualTo(a);
        this.testResourcePoolOrganizationMapper.deleteByExample(testResourcePoolOrganizationExample);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LogDTO updateLog(String a) {
        TestResourcePool testResourcePoolSelectByPrimaryKey = this.testResourcePoolMapper.selectByPrimaryKey(a);
        if (testResourcePoolSelectByPrimaryKey == null) {
            return null;
        }
        LogDTO logDTO = new LogDTO(ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), testResourcePoolSelectByPrimaryKey.getId(), (String) null, OperationLogType.UPDATE.name(), ProjectVersionService.ALLATORIxDEMO("9T>E#_-N9H9E/\\5C/B%D8R/N:^%]"), testResourcePoolSelectByPrimaryKey.getName());
        logDTO.setPath(ProjectVersionService.ALLATORIxDEMO(">\u001fa\u000ep\u001et"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        logDTO.setOriginalValue(JSON.toJSONBytes(testResourcePoolSelectByPrimaryKey));
        return logDTO;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public TestResourcePool addTestResourcePool(TestResourcePoolDTO a) throws MSException {
        this.testResourcePoolService = (TestResourcePoolService) CommonBeanFactory.getBean(TestResourcePoolService.class);
        String strNextStr = IDGenerator.nextStr();
        this.testResourcePoolService.checkTestResourcePool(a);
        TestResourcePoolBlob testResourcePoolBlob = new TestResourcePoolBlob();
        testResourcePoolBlob.setId(strNextStr);
        TestResourceDTO testResourceDTO = a.getTestResourceDTO();
        this.testResourcePoolService.checkAndSaveOrgRelation(a, strNextStr, testResourceDTO);
        this.testResourcePoolService.checkApiConfig(testResourceDTO, a, a.getType());
        if (CollectionUtils.isEmpty(testResourceDTO.getNodesList())) {
            testResourceDTO.setNodesList(new ArrayList());
        }
        if (StringUtils.equalsIgnoreCase(a.getType(), ResourcePoolTypeEnum.NODE.name())) {
            this.testResourcePoolValidateService.validateNodeList(testResourceDTO.getNodesList());
        }
        testResourcePoolBlob.setConfiguration(JSON.toJSONString(testResourceDTO).getBytes());
        buildTestPoolBaseInfo(a, strNextStr);
        this.testResourcePoolMapper.insert(a);
        this.testResourcePoolBlobMapper.insert(testResourcePoolBlob);
        a.setId(strNextStr);
        return a;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void unableTestResourcePool(String a) throws MSException {
        XpackTestResourcePoolService xpackTestResourcePoolService;
        TestResourcePool testResourcePoolSelectByPrimaryKey = this.testResourcePoolMapper.selectByPrimaryKey(a);
        if (testResourcePoolSelectByPrimaryKey != null) {
            testResourcePoolSelectByPrimaryKey.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
            Boolean enable = testResourcePoolSelectByPrimaryKey.getEnable();
            if (!enable.booleanValue()) {
                if (!this.testResourcePoolService.checkApiConfig((TestResourceDTO) JSON.parseObject(new String(this.testResourcePoolBlobMapper.selectByPrimaryKey(a).getConfiguration()), TestResourceDTO.class), testResourcePoolSelectByPrimaryKey, testResourcePoolSelectByPrimaryKey.getType())) {
                    throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("\u001et\u0019e5c\u000fb\u0005d\u0018r\u000fN\u001a~\u0005}5x\u0019N\u001cp\u0006x\u000eN\fp\u0003}")));
                }
            }
            if (enable.booleanValue()) {
                xpackTestResourcePoolService = this;
                testResourcePoolSelectByPrimaryKey.setEnable(false);
            } else {
                testResourcePoolSelectByPrimaryKey.setEnable(true);
                xpackTestResourcePoolService = this;
            }
            xpackTestResourcePoolService.testResourcePoolMapper.updateByPrimaryKeySelective(testResourcePoolSelectByPrimaryKey);
            return;
        }
        throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("e\u000fb\u001eN\u0018t\u0019~\u001fc\tt5a\u0005~\u0006N\u0004~\u001eN\u000fi\u0003b\u001eb")));
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public LogDTO deleteLog(String a) {
        TestResourcePool testResourcePoolSelectByPrimaryKey = this.testResourcePoolMapper.selectByPrimaryKey(a);
        if (testResourcePoolSelectByPrimaryKey != null) {
            LogDTO logDTO = new LogDTO(ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), a, testResourcePoolSelectByPrimaryKey.getCreateUser(), OperationLogType.DELETE.name(), ProjectVersionService.ALLATORIxDEMO("9T>E#_-N9H9E/\\5C/B%D8R/N:^%]"), testResourcePoolSelectByPrimaryKey.getName());
            logDTO.setPath(ProjectVersionService.ALLATORIxDEMO(">\u000et\u0006t\u001et"));
            logDTO.setMethod(HttpMethodConstants.POST.name());
            logDTO.setOriginalValue(JSON.toJSONBytes(testResourcePoolSelectByPrimaryKey));
            return logDTO;
        }
        return null;
    }

    public static void buildTestPoolBaseInfo(TestResourcePool a, String a2) {
        a.setId(a2);
        a.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        if (a.getEnable() == null) {
            a.setEnable(true);
        }
        a.setDeleted(false);
    }

    /* JADX INFO: renamed from: L */
    private /* synthetic */ void m1L(String a) {
        ProjectTestResourcePoolExample projectTestResourcePoolExample = new ProjectTestResourcePoolExample();
        projectTestResourcePoolExample.createCriteria().andTestResourcePoolIdEqualTo(a);
        this.projectTestResourcePoolMapper.deleteByExample(projectTestResourcePoolExample);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.metersphere.sdk.exception.MSException */
    public void deleteTestResourcePool(String a) throws MSException {
        this.testResourcePoolService = (TestResourcePoolService) CommonBeanFactory.getBean(TestResourcePoolService.class);
        TestResourcePool testResourcePoolSelectByPrimaryKey = this.testResourcePoolMapper.selectByPrimaryKey(a);
        if (testResourcePoolSelectByPrimaryKey != null) {
            if (!StringUtils.equalsIgnoreCase(testResourcePoolSelectByPrimaryKey.getId(), ProjectVersionService.ALLATORIxDEMO("[!Z!Z [!Z!Z "))) {
                ALLATORIxDEMO(a);
                m1L(a);
                testResourcePoolSelectByPrimaryKey.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
                testResourcePoolSelectByPrimaryKey.setEnable(false);
                testResourcePoolSelectByPrimaryKey.setDeleted(true);
                this.testResourcePoolMapper.updateByPrimaryKeySelective(testResourcePoolSelectByPrimaryKey);
                return;
            }
            throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("u\u000fw\u000bd\u0006e5e\u000fb\u001eN\u0018t\u0019~\u001fc\tt5a\u0005~\u0006N\u0003b5t\u0007a\u001eh")));
        }
        throw new MSException(Translator.get(ProjectVersionService.ALLATORIxDEMO("e\u000fb\u001eN\u0018t\u0019~\u001fc\tt5a\u0005~\u0006N\u0004~\u001eN\u000fi\u0003b\u001eb")));
    }

    public LogDTO addLog(TestResourcePoolRequest a) {
        LogDTO logDTO = new LogDTO(ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), ProjectVersionService.ALLATORIxDEMO("9H9E/\\"), a.getId(), (String) null, OperationLogType.ADD.name(), ProjectVersionService.ALLATORIxDEMO("9T>E#_-N9H9E/\\5C/B%D8R/N:^%]"), a.getName());
        logDTO.setPath(ProjectVersionService.ALLATORIxDEMO(">\u001et\u0019eEc\u000fb\u0005d\u0018r\u000f>\u001a~\u0005}Ep\u000eu"));
        logDTO.setMethod(HttpMethodConstants.POST.name());
        logDTO.setOriginalValue(JSON.toJSONBytes(a));
        return logDTO;
    }
}
