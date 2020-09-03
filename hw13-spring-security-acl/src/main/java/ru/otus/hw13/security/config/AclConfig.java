package ru.otus.hw13.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.otus.hw13.config.changelog.AclChangelog;
import ru.otus.hw13.security.acls.dao.AclRepository;
import ru.otus.hw13.security.acls.service.MongoDBMutableAclService;
import ru.otus.hw13.security.acls.service.MongoLookupStrategy;

@Configuration
@RequiredArgsConstructor
public class AclConfig {

  private final AclRepository aclRepository;

  private final MongoTemplate mongoTemplate;

  @Bean
  public AclAuthorizationStrategy aclAuthorizationStrategy() {
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL"));
  }

  @Bean
  public PermissionFactory permissionFactory() {
    return new DefaultPermissionFactory(CustomBasePermission.class);
  }

  @Bean
  public PermissionGrantingStrategy permissionGrantingStrategy() {
    ConsoleAuditLogger consoleAuditLogger = new ConsoleAuditLogger();
    return new DefaultPermissionGrantingStrategy(consoleAuditLogger);
  }

  @Bean
  public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
    permissionEvaluator.setPermissionFactory(permissionFactory());
    expressionHandler.setPermissionEvaluator(permissionEvaluator);
    expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
    return expressionHandler;
  }

  @Bean
  public LookupStrategy lookupStrategy(){
    MongoLookupStrategy lookupStrategy = new MongoLookupStrategy(mongoTemplate, aclAuthorizationStrategy(), permissionGrantingStrategy(),aclCache());
    lookupStrategy.setPermissionFactory(permissionFactory());
    return lookupStrategy;
  }

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager("test");
  }

  @Bean
  public AclCache aclCache() {
    Cache springCache = cacheManager().getCache("test");
    return new SpringCacheBasedAclCache(springCache, permissionGrantingStrategy(), aclAuthorizationStrategy());
  }

  @Bean
  public MongoDBMutableAclService aclService() {
    return new MongoDBMutableAclService(aclRepository, lookupStrategy(), aclCache());
  }
}
