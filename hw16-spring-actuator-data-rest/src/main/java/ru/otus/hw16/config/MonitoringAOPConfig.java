package ru.otus.hw16.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class MonitoringAOPConfig {

  private static final String SUCCESS = "SUCCESS";
  private static final String ERROR = "ERROR";
  private static final String TAG_COMPONENTCLASS = "componentClass";
  private static final String TAG_EXCEPTIONCLASS = "exceptionClass";
  private static final String TAG_METHODNAME = "methodName";
  private static final String TAG_OUTCOME = "outcome";
  private static final String TAG_TYPE = "componentType";
  private static final String TAG_VALUE_SERVICE_TYPE = "service";
  private static final String METER_COMPONENT_TIMER = "component.invocation.timer";
  private static final String METER_COMPONENT_COUNTER = "component.invocation.counter";
  private static final String METER_COMPONENT_EXCEPTION_COUNTER = "component.invocation.exception.counter";

  MeterRegistry registry;

  public MonitoringAOPConfig(MeterRegistry registry) {
    this.registry = registry;
  }

  @Pointcut("@target(ru.otus.hw16.config.MonitoredService) && within(ru.otus.hw16..*)")
  public void servicePointcut() {
  }

  @Around("servicePointcut()")
  public Object serviceResponseTimeAdvice(ProceedingJoinPoint pjp) throws Throwable {
    return monitorResponseTime(pjp, TAG_VALUE_SERVICE_TYPE);
  }

  @AfterThrowing(pointcut = "servicePointcut()", throwing = "ex")
  public void serviceExceptionLoggingAdvice(JoinPoint joinPoint, Exception ex) {
    monitorException(joinPoint, ex, TAG_VALUE_SERVICE_TYPE);
  }

  private Object monitorResponseTime(ProceedingJoinPoint pjp, String type) throws Throwable {
    long start = System.currentTimeMillis();
    // invoke the method
    Object obj = pjp.proceed();
    long end = System.currentTimeMillis();

    String serviceClass = getClassName(pjp.getThis().getClass().getName());
    String methodName = pjp.getSignature().getName();

    Timer timer = registry.timer(METER_COMPONENT_TIMER,
        TAG_COMPONENTCLASS, serviceClass, TAG_METHODNAME, methodName, TAG_OUTCOME, SUCCESS,
        TAG_TYPE, type);
    timer.record((end - start), TimeUnit.MILLISECONDS);

    Counter successCounter = registry.counter(METER_COMPONENT_COUNTER,
        TAG_COMPONENTCLASS, serviceClass, TAG_METHODNAME, methodName, TAG_OUTCOME, SUCCESS,
        TAG_TYPE, type);
    successCounter.increment();
    return obj;
  }

  private void monitorException(JoinPoint joinPoint, Exception ex, String type) {
    String serviceClass = getClassName(joinPoint.getThis().getClass().getName());
    String methodName = joinPoint.getSignature().getName();
    Counter failureCounter = registry.counter(METER_COMPONENT_EXCEPTION_COUNTER, TAG_EXCEPTIONCLASS,
        ex.getClass().getName(), TAG_COMPONENTCLASS, serviceClass, TAG_METHODNAME, methodName, TAG_OUTCOME, ERROR,
        TAG_TYPE, type);
    failureCounter.increment();
  }

  private String getClassName(String fullName) {
    String serviceClass = fullName;
    int first = serviceClass.lastIndexOf('.');
    int second = serviceClass.indexOf('$');

    if (first > 0 && second > first) {
      serviceClass = serviceClass.substring(first, second);
    }
    return serviceClass;
  }
}
