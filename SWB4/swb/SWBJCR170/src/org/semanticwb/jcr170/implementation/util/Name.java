/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation.util;

import org.semanticwb.jcr170.implementation.util.XMLChar;
import org.semanticwb.jcr170.implementation.util.Token;

/**
 *
 * @author victor.lorenzana
 */
public class Name extends Token
{

   public Name()
   {
      super();
   }

   /**
    * ctor for Name
    *
    * @throws IllegalArgumentException will be thrown if validation fails
    */
   public Name(String stValue) throws IllegalArgumentException
   {
      try
      {
         setValue(stValue);
      }
      catch (IllegalArgumentException e)
      {
         // recast normalizedString exception as token exception
         throw new IllegalArgumentException("badNameType00" + "data=[" +
                 stValue + "]");
      }
   }


   /**
    * validate the value against the xsd definition
    * Name    ::=    (Letter | '_' | ':') ( NameChar)*
    * NameChar    ::=     Letter | Digit | '.' | '-' | '_' | ':' | CombiningChar | Extender
    */
   @Override
   public boolean isValid(String stValue)
   {
      int scan;
      boolean bValid = true;

      for (scan = 0; scan < stValue.length(); scan++)
      {
         if (scan == 0)
            bValid = XMLChar.isNameStart(stValue.charAt(scan));
         else
            bValid = XMLChar.isName(stValue.charAt(scan));
         if (bValid == false)
            break;
      }

      return bValid;
   }
}

