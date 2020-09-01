package ru.otus.hw13.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.otus.hw13.security.acls.dao.AclRepository;
import ru.otus.hw13.security.acls.service.MongoDBMutableAclService;
import ru.otus.hw13.security.acls.service.MongoLookupStrategy;


@Configuration
@RequiredArgsConstructor
public class AclConfig {

  private final AclRepository aclRepository;

  @Bean
  public PermissionGrantingStrategy permissionGrantingStrategy() {
    return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
  }

  @Bean
  public AclAuthorizationStrategy aclAuthorizationStrategy() {
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Bean
  public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
    expressionHandler.setPermissionEvaluator(permissionEvaluator);
    expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
    return expressionHandler;
  }

  @Bean
  public LookupStrategy lookupStrategy() {
    return new MongoLookupStrategy(aclAuthorizationStrategy(), permissionGrantingStrategy());
  }

  @Bean
  public MongoDBMutableAclService aclService() {
    return new MongoDBMutableAclService(aclRepository, lookupStrategy());
  }
}
