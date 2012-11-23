/**  
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
**/ 
 


/*
 * FixedNumericDocument.java
 *
 * Created on 24 de noviembre de 2004, 06:24 PM
 */

package applets.workflowadmin;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * Clase utilerìa para que en campos de texto, se pueda determinar la longitud de
 * ese campo y si es de tipo n�mero o alfanumerico.
 */
public class FixedNumericDocument extends PlainDocument {

   private int maxLength = 9999;
   private boolean numericOnly=false;

   public FixedNumericDocument(int maxLength, boolean numericOnly) {
      super();
      this.maxLength = maxLength;
      this.numericOnly = numericOnly;
   }

   //this is where we'll control all input to our document.  
   //If the text that is being entered passes our criteria, then we'll just call
   //super.insertString(...)
   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (getLength() + str.length() > maxLength) {
         Toolkit.getDefaultToolkit().beep();
         return;
      }
      else {
         try {
            if (numericOnly) {
               //check if str is numeric only
               Integer.parseInt(str);
               //if we get here then str contains only numbers
               //so it's ok to insert
            }
            super.insertString(offset, str, attr);
         }
         catch(NumberFormatException exp) {
            Toolkit.getDefaultToolkit().beep();
            return;
         }
      }
      return;
   }
}


