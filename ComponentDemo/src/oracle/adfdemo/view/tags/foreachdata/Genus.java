/** Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.tags.foreachdata;

import java.io.Serializable;


public class Genus
  implements Serializable
{
  public Genus(
    String     name,
    Species... members)
  {
    _name = name;
    _members = members;
  }

  public String getName()
  {
    return _name;
  }

  public Species[] getMembers()
  {
    return _members;
  }
  private final String _name;
  private final Species[] _members;

  @SuppressWarnings("compatibility:-870203693204164355")
  private static final long serialVersionUID = 1L;
}
