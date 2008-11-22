/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation.util;

import org.semanticwb.jcr170.implementation.util.XMLChar;

/**
 *
 * @author victor.lorenzana
 */
public class NCName extends Name
{

   public NCName()
   {
      super();
   }

   /**
    * ctor for NCName
    *
    * @throws IllegalArgumentException will be thrown if validation fails
    */
   public NCName(String stValue) throws IllegalArgumentException
   {
      try
      {
         setValue(stValue);
      }
      catch (IllegalArgumentException e)
      {
         // recast normalizedString exception as token exception
         throw new IllegalArgumentException("badNCNameType00" + "data=[" +
                 stValue + "]");
      }
   }


   /**
    * validate the value against the xsd definition
    * <p/>
    * NCName ::=  (Letter | '_') (NCNameChar)*
    * NCNameChar ::=  Letter | Digit | '.' | '-' | '_' | CombiningChar | Extender
    */
   public boolean isValid(String stValue)
   {
      int scan;
      boolean bValid = true;

      for (scan = 0; scan < stValue.length(); scan++)
      {
         if (scan == 0)
            bValid = XMLChar.isNCNameStart(stValue.charAt(scan));
         else
            bValid = XMLChar.isNCName(stValue.charAt(scan));
         if (bValid == false)
            break;
      }
      return bValid;
   }
}

