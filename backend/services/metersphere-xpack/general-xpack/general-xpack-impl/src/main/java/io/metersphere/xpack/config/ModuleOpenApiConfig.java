package io.metersphere.xpack.config;

import io.metersphere.xpack.system.ldap.p000vo.LdapRequest;
import io.metersphere.xpack.system.service.lark.LarkLoginService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* JADX INFO: compiled from: kb */
/* JADX INFO: loaded from: general-xpack-impl-3.6.7-lts.jar:io/metersphere/xpack/config/ModuleOpenApiConfig.class */
@Configuration
@OpenAPIDefinition(info = @Info(title = "${spring.application.name}", version = "3.0"), servers = {@Server(url = "/")})
public class ModuleOpenApiConfig {
    private static final String prePackages = "io.metersphere.";

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi apiTestApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("(i 4=|:m")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("\u0012uUw\u001en\u001eh\bj\u0013\u007f\t\u007fU{\u000bs")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi planApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("m,j=49u(w")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("s\u00144\u0016\u007f\u000f\u007f\ti\u000br\u001eh\u001e4\u000bv\u001at")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi bugApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("+l.4$x'x.|$|'m")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("\u0012uUw\u001en\u001eh\bj\u0013\u007f\t\u007fUx\u000e}")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi xpackApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("a9x*r")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("\u0012uUw\u001en\u001eh\bj\u0013\u007f\t\u007fUb\u000b{\u0018q")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO(":`:m,tdj,m=p'~")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("s\u00144\u0016\u007f\u000f\u007f\ti\u000br\u001eh\u001e4\bc\bn\u001ew")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public OperationCustomizer customize() {
        return (a, a2) -> {
            if (LdapRequest.ALLATORIxDEMO("u&~ w").equals(a2.getMethod().getName())) {
                return a;
            }
            return a.addParametersItem(new Parameter().in(LarkLoginService.ALLATORIxDEMO("\u0013\u007f\u001a~\u001eh")).required(true).name(LdapRequest.ALLATORIxDEMO("\nJ\u001b_dM\u0006R\fW"))).addParametersItem(new Parameter().in(LarkLoginService.ALLATORIxDEMO("\u0013\u007f\u001a~\u001eh")).required(true).name(LdapRequest.ALLATORIxDEMO("\u00114\bL\u001dQdM\u0006R\fW")));
        };
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi projectApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("9k&s,z=4$x'x.|$|'m")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("\u0012uUw\u001en\u001eh\bj\u0013\u007f\t\u007fUj\tu\u0011\u007f\u0018n")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi caseApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("z(j,4$x'x.|$|'m")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("s\u00144\u0016\u007f\u000f\u007f\ti\u000br\u001eh\u001e4\u001do\u0015y\u000fs\u0014t\u001av")}).build();
    }

    @ConditionalOnProperty(name = {"springdoc.swagger-ui.enabled", "springdoc.api-docs.enabled"}, havingValue = "true")
    @Bean
    public GroupedOpenApi dashboardApi() {
        return GroupedOpenApi.builder().group(LdapRequest.ALLATORIxDEMO("}(j!{&x;}")).packagesToScan(new String[]{LarkLoginService.ALLATORIxDEMO("\u0012uUw\u001en\u001eh\bj\u0013\u007f\t\u007fU~\u001ai\u0013x\u0014{\t~")}).build();
    }
}
