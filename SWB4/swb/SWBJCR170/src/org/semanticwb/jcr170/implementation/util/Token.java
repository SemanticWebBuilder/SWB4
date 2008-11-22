/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation.util;

/**
 *
 * @author victor.lorenzana
 */
public class Token extends NormalizedString
{

   public Token()
   {
      super();
   }

   /**
    * ctor for Token
    *
    * @throws IllegalArgumentException will be thrown if validation fails
    */
   public Token(String stValue) throws IllegalArgumentException
   {
      try
      {
         setValue(stValue);
      }
      catch (IllegalArgumentException e)
      {
         // recast normalizedString exception as token exception
         throw new IllegalArgumentException("badToken00" + "data=[" +
                 stValue + "]");
      }
   }

   /**
    * validate the value against the xsd definition
    * <p/>
    * The value space of token is the set of strings that do not
    * contain the line feed (#xA) nor tab (#x9) characters, that
    * have no leading or trailing spaces (#x20) and that have no
    * internal sequences of two or more spaces. The lexical space
    * of token is the set of strings that do not contain the line
    * feed (#xA) nor tab (#x9) characters, that have no leading or
    * trailing spaces (#x20) and that have no internal sequences of two
    * or more spaces.
    */
   public boolean isValid(String stValue)
   {
      int scan;
      // check to see if we have a string to review
      if ((stValue == null) || (stValue.length() == 0))
         return true;

      // no leading space
      if (stValue.charAt(0) == 0x20)
         return false;

      // no trail space
      if (stValue.charAt(stValue.length() - 1) == 0x20)
         return false;

      for (scan = 0; scan < stValue.length(); scan++)
      {
         char cDigit = stValue.charAt(scan);
         switch (cDigit)
         {
            case 0x09:
            case 0x0A:
               return false;
            case 0x20:
               // no doublspace
               if (scan + 1 < stValue.length())
                  if (stValue.charAt(scan + 1) == 0x20)
                  {
                     return false;
                  }
            default:
               break;
         }
      }
      return true;
   }
}

