/** Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.tags.foreachdata;

import java.io.Serializable;


public class Tribe
  implements Serializable
{
  public Tribe(
    String    familiarName,
    String    name,
    Genus ... members)
  {
    _familiarName = familiarName;
    _name = name;
    _members = members;
  }

  public String getName()
  {
    return _name;
  }

  public Genus[] getMembers()
  {
    return _members;
  }

  private final String _familiarName;
  private final String _name;
  private final Genus[] _members;
}
