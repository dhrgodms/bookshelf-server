package bookshelf.renewal;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi boardGroupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/v1/**")
                .addOpenApiCustomizer(
                        openApi -> openApi.setInfo(
                                new Info()
                                        .title("bookshelf api")
                                        .description("책 소장 관리 프로젝트")
                                        .version("1.0.0")

                        )
                )
                .build();

    }

}
