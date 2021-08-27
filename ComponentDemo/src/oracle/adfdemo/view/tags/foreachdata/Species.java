/** Copyright (c) 2012, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.tags.foreachdata;

import java.io.Serializable;

public class Species
  implements Serializable
{
  public Species(
    String name,
    String scientificName)
  {
    _name = name;
    _scientificName = scientificName;
  }

  public String getName()
  {
    return _name;
  }

  public String getScientificName()
  {
    return _scientificName;
  }

  private final String _name;
  private final String _scientificName;

  @SuppressWarnings("compatibility:5994341289669835894")
  private static final long serialVersionUID = 1L;
}
