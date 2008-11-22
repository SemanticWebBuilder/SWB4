/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr170.implementation.util;

/**
 *
 * @author victor.lorenzana
 */
public class NormalizedString extends Object implements java.io.Serializable
{

   String m_value = null;   // JAX-RPC maps xsd:string to java.lang.String

   public NormalizedString()
   {
      super();
   }

   /**
    * ctor for NormalizedString
    *
    * @param stValue is the String value
    * @throws IllegalArgumentException if invalid format
    */
   public NormalizedString(String stValue) throws IllegalArgumentException
   {
      setValue(stValue);
   }

   /**
    * validates the data and sets the value for the object.
    *
    * @param normalizedString String value
    * @throws IllegalArgumentException if invalid format
    */
   public void setValue(String stValue) throws IllegalArgumentException
   {
      if (isValid(stValue) == false)
         throw new IllegalArgumentException("badNormalizedString00"+
                 " data=[" + stValue + "]");
      m_value = stValue;
   }

   public String toString()
   {
      return m_value;
   }

   public int hashCode()
   {
      return m_value.hashCode();
   }

   /**
    * validate the value against the xsd definition for the object
    * <p/>
    * The value space of normalizedString is the set of strings that
    * do not contain the carriage return (#xD), line feed (#xA) nor
    * tab (#x9) characters. The lexical space of normalizedString is
    * the set of strings that do not contain the carriage return (#xD)
    * nor tab (#x9) characters.
    *
    * @param the String to test
    * @returns true if valid normalizedString
    */
   public boolean isValid(String stValue)
   {
      int scan;

      for (scan = 0; scan < stValue.length(); scan++)
      {
         char cDigit = stValue.charAt(scan);
         switch (cDigit)
         {
            case 0x09:
            case 0x0A:
            case 0x0D:
               return false;
            default:
               break;
         }
      }
      return true;
   }
   @Override
   public boolean equals(Object object)
   {
      String s1 = object.toString();
      return s1.equals(m_value);
   }
}

