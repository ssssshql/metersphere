package io.metersphere.xpack.project.invoker;

import io.metersphere.xpack.project.service.VersionResourceService;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* JADX INFO: compiled from: pa */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/project/invoker/ProjectVersionServiceInvoker.class */
@Component
public class ProjectVersionServiceInvoker {
    private final List<VersionResourceService> versionResourceServices;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public boolean invokeCheckExist(String a) {
        Iterator<VersionResourceService> it = this.versionResourceServices.iterator();
        while (it.hasNext()) {
            if (it.next().checkResourceExist(a)) {
                return true;
            }
        }
        return false;
    }

    @Autowired
    public ProjectVersionServiceInvoker(List<VersionResourceService> list) {
        this.versionResourceServices = list;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public void invokeSetLatest(String a) {
        Iterator<VersionResourceService> it = this.versionResourceServices.iterator();
        while (it.hasNext()) {
            it.next().setLatest(a);
            it = it;
        }
    }
}
