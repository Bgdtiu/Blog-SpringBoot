package com.tfh.blog.config;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tfh.blog.mapper.UserMapper;
import com.tfh.blog.pojo.User;
import com.tfh.blog.service.UserService;
import com.tfh.blog.utils.jwt.Token;
import com.tfh.blog.utils.redis.Redis;
import com.tfh.blog.utils.result.Result;
import com.tfh.blog.utils.result.ResultType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/18 18:12
 * TODO: 访问API权限配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    private Token token;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private Redis redis;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config -> config
                .requestMatchers("/login",
                        "/user/registered",
                        "/user/test",
                        "/article/publicArticle")
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        http.formLogin(config -> config
                .loginPage("/login")
                .usernameParameter("userName")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json; charset=utf-8");
                    String userName = request.getParameter("userName");

                    long userId = userMapper.getIdBasedOnUserName(userName);
                    List<String> userAuthentication = userMapper.getUserAuthentication(userId);
                    StringBuilder stringBuilder = new StringBuilder();
                    userAuthentication.forEach(str -> {
                        stringBuilder.append(str).append(",");
                    });

                    String token = this.token.obtainToken(userId, stringBuilder.toString());

                    response.setHeader(Token.getAUTHORIZATION(), token);

                    redis.set(Token.getREDIS_PREFIX() + userId, token, 7 * 24 * 60 * 60 * 1000);

                    String ok = JSONUtil.toJsonPrettyStr(Result.ok());
                    response.getWriter().println(ok);

                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json; charset=utf-8");

                    String error = JSONUtil.toJsonPrettyStr(Result.error());
                    response.getWriter().println(error);
                })
        );

        http.logout(config -> config
                .logoutUrl("/logout")

                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json; charset=utf-8");
                    String bearer = request.getHeader(Token.getAUTHORIZATION());
                    Jws<Claims> claimsJws = token.parseToken(bearer);
                    if (claimsJws != null) {
                        long userId = Long.parseLong((String) claimsJws.getPayload().get("userId"));
                        redis.del(Token.getREDIS_PREFIX() + userId);
                        String ok = JSONUtil.toJsonPrettyStr(Result.ok());
                        response.getWriter().println(ok);
                        return;
                    }
                    String error = JSONUtil.toJsonPrettyStr(Result.error(ResultType.LOGOUT_ERROR, null));
                    response.getWriter().println(error);
                })
        );

        http.exceptionHandling(config -> config
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json; charset=utf-8");
                    String jsonPrettyStr = JSONUtil.toJsonPrettyStr(Result.error(authException.getMessage()));
                    response.getWriter().println(jsonPrettyStr);
                }));

        http.addFilterBefore(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String bearer = request.getHeader(Token.getAUTHORIZATION());
                Jws<Claims> claimsJws = token.parseToken(bearer);
                if (claimsJws != null) {
                    Claims payload = claimsJws.getPayload();
                    long userId = Long.parseLong((String) payload.get("userId"));
                    String authority = (String) payload.get("authority");

                    User user = userService.getById(userId);

                    List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

                    UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user.getUserName(), null, grantedAuthorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticated);
                }

                filterChain.doFilter(request, response);
            }
        }, UsernamePasswordAuthenticationFilter.class);


        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);


        http.cors(cors -> cors.configurationSource(configurationSource()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // 允许的源
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 允许的方法
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type","Access-Control-Allow-Origin")); // 允许的头
        configuration.setExposedHeaders(Arrays.asList("Authorization"));//允许客户端获取那些字段
        configuration.setAllowCredentials(true); // 是否允许携带cookie
        configuration.setMaxAge(3600L); // 预检请求的有效期，单位秒

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 应用于所有路径
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {

            User user = userMapper.getUserBasedOnUserName(username);

            if (user == null) {
                throw new UsernameNotFoundException(username);
            }

            StringBuilder stringBuilder = new StringBuilder();
            List<String> authenticate = userMapper.getUserAuthentication(user.getUserId());
            authenticate.forEach(str -> {
                stringBuilder.append(str).append(",");
            });

            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringBuilder.toString());

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserName())
                    .username(user.getUserName())
                    .password(user.getUserPassword())
                    .authorities(grantedAuthorities)
                    .build();
        };
    }

}
