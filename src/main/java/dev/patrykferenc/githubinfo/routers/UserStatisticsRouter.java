package dev.patrykferenc.githubinfo.routers;

import dev.patrykferenc.githubinfo.handlers.UserStatisticsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class UserStatisticsRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserStatisticsHandler handler) {
        return RouterFunctions.route()
                .GET("/stats/{username}", handler::getUserStatistics)
                .build();
    }

}
