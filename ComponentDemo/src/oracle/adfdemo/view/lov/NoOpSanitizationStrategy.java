/* Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.*/

package oracle.adfdemo.view.lov;

import oracle.adf.view.rich.sanitized.SanitizationContext;
import oracle.adf.view.rich.sanitized.SanitizationStrategy;


public class NoOpSanitizationStrategy extends SanitizationStrategy
{
  public String sanitize(SanitizationContext context, String toSanitize)
  {
    // demo implementation - Just let through the toSanitize string
    return toSanitize;
  }
}
