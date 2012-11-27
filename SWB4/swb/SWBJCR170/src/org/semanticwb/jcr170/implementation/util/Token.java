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

