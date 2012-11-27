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

