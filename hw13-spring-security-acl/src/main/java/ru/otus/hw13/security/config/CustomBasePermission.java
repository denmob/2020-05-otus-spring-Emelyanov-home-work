package ru.otus.hw13.security.config;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;

public class CustomBasePermission extends BasePermission {

  public static final Permission CUSTOM = new CustomBasePermission(32, 'E');

  protected CustomBasePermission(int mask) {
    super(mask);
  }

  protected CustomBasePermission(int mask, char code) {
    super(mask, code);
  }
}
