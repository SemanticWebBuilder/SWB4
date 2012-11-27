/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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

