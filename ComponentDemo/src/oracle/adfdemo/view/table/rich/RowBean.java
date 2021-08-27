/** Copyright (c) 2008, 2014, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfdemo.view.table.rich;

public class RowBean
{
  public RowBean()
  {
    // This isn't thread-safe.  I don't care. :)
    _int = _sCount++;
    _string = "String " + _int;
    _boolean = ((_int % 2) == 0);
  }

  public String action()
  {
    System.out.println("CLICKED ON ROW " + _int + ", " + _string);
    return "success";
  }

  public boolean getBoolean()
  {
    return _boolean;
  }

  public void setBoolean(boolean aBoolean)
  {
    _boolean = aBoolean;
  }


  public int getInt()
  {
    return _int;
  }

  public void setInt(int anInt)
  {
    _int = anInt;
  }

  public String getString()
  {
    return _string;
  }

  public void setString(String aString)
  {
    _string = aString;
  }

  private int _int;
  private boolean  _boolean;
  private String _string;

  static private int _sCount = 0;
}
