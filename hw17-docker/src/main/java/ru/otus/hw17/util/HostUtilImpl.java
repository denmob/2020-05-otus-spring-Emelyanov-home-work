package ru.otus.hw17.util;

import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

@Component
public class HostUtilImpl implements HostUtil {

  private final String hostIp = getHostIp(getLocalHostAddress());

  public String getHostIp() {
    return hostIp;
  }

  @Override
  public String getHost() {
    String pidHost = getMXBeanName();
    int index = pidHost.indexOf('@');
    String retHost = String.valueOf(System.currentTimeMillis());
    if (index == -1) {
      retHost = pidHost;
    } else if (index + 1 <= pidHost.length()) {
      retHost = pidHost.substring(index + 1);
    }
    return retHost.replaceAll("\\.", "_");
  }

  private String getMXBeanName() {
    String pidHost = ManagementFactory.getRuntimeMXBean().getName();
    if (Strings.isNullOrEmpty(pidHost)) {
      return String.valueOf(System.currentTimeMillis());
    }
    return pidHost;
  }

  private InetAddress getLocalHostAddress() {
    try {
      InetAddress candidateAddress = null;
      for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
           networkInterfaces.hasMoreElements(); ) {
        NetworkInterface networkInterface = networkInterfaces.nextElement();
        for (Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
             inetAddresses.hasMoreElements(); ) {
          InetAddress inetAddr = inetAddresses.nextElement();
          if (!inetAddr.isLoopbackAddress()) {
            if (inetAddr.isSiteLocalAddress()) {
              return inetAddr;
            } else if (candidateAddress == null) {
              candidateAddress = inetAddr;
            }
          }
        }
      }
      if (candidateAddress != null) {
        return candidateAddress;
      }
      InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
      if (jdkSuppliedAddress == null) {
        throw new UnknownHostException(
            "The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
      }
      return jdkSuppliedAddress;
    } catch (Exception e) {
      throw new RuntimeException("Failed to determine LAN address: " + e);
    }
  }

  private String getHostIp(InetAddress netAddress) {
    if (null == netAddress) {
      return null;
    }
    return netAddress.getHostAddress();
  }
}
