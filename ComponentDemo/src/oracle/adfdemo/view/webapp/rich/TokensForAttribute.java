/*
** Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
*/

package oracle.adfdemo.view.webapp.rich;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.myfaces.trinidad.util.Args;

/**
 * A test class that goes with PerspecSysSimulator to tokenize and detokenize request/response.
 * We use very simple tokenize / detokenize method by adding / removing prefix and suffix.
 */

final class TokensForAttribute
{
  // each protected attribute is assigned a unique attrKey
  // (which could just be the index of the protected attribute),
  // as a result, the generated tokens are globally unique
  public TokensForAttribute(String attributeName, String attrKey)
  {
    _attributeName = attributeName;
    _prefix = new StringBuilder(20).append(TKN_PREFIX).append(attrKey).append("_").toString();
  }

  // detokenize the passage. Find if there are any strings in this passage that are tokenized by me,
  // if there are, detokenize them.
  public String detokenize(String passage)
  {
    if (passage == null)
      return null;
    
    StringBuilder sb = new StringBuilder();
    
    int startIndex = 0;
    int endIndex = passage.length();
    int startPatternIndex = passage.indexOf(_prefix);
    int endPatternIndex = startPatternIndex > 0 ? passage.indexOf(TKN_SUFFIX, startPatternIndex + _prefix.length()) : -1;
    
    while (startPatternIndex > 0 && endPatternIndex > 0)
    {
      sb.append(passage.substring(startIndex, startPatternIndex));
      sb.append(_getValue(passage.substring(startPatternIndex, endPatternIndex + TKN_PREFIX.length())));
      startIndex = endPatternIndex + TKN_SUFFIX.length();
      startPatternIndex = passage.indexOf(_prefix, startIndex);
      endPatternIndex = startPatternIndex > 0 ? passage.indexOf(TKN_SUFFIX, startPatternIndex) : -1;
    }

    if (startIndex >= 0 && endIndex > startIndex)
      sb.append(passage.substring(startIndex, endIndex));

    return sb.toString();
  }

  // convert the token into the value. Basically strip out prefix and suffix
  private String _getValue(String token)
  {
    if (token == null)
      return null;

    int prefixIndex = token.indexOf(_prefix, 0);
    if (prefixIndex != 0)
      return null;

    int postfixIndex = token.indexOf(TKN_SUFFIX);
    if (postfixIndex < 0 || postfixIndex + TKN_SUFFIX.length() != token.length())
      return null;

    return token.substring(_prefix.length(), postfixIndex);
  }

  // contruct a token for the given value, basically add prefix and suffix.
  public String getToken(String value)
  {
    return _createToken(value);
  }

  private String _createToken(String value)
  {
    // create a token for the value of this attribute.
    // a more efficient implementation would encode the index in another base (like base 64 minus underscores)
    // but we don't care for testing purposes.
    return new StringBuilder().append(_prefix).append(value).append(TKN_SUFFIX).toString();
  }

  @Override
  public String toString()
  {
    return super.toString() + "attribute=" + _attributeName + ", prefix="+ _prefix + ", suffix= " + TKN_SUFFIX + ".";
  }

  public static final String TKN_PREFIX = "$TKN_";
  public static final String TKN_SUFFIX = "_NKT$";

  private final String _attributeName;
  private final String _prefix;
}