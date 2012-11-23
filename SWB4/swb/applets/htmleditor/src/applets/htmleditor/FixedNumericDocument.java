/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gesti?n de procesos de negocio mediante el uso de 
* tecnolog?a sem?ntica, que permite el modelado, configuraci?n, ejecuci?n y monitoreo de los procesos de negocio
* de una organizaci?n, as? como el desarrollo de componentes y aplicaciones orientadas a la gesti?n de procesos.
* 
* Mediante el uso de tecnolog?a sem?ntica, SemanticWebBuilder Process puede generar contextos de informaci?n
* alrededor de alg?n tema de inter?s o bien integrar informaci?n y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la informaci?n se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creaci?n original del Fondo de 
* Informaci?n y Documentaci?n para la Industria INFOTEC.
* 
* INFOTEC pone a su disposici?n la herramienta SemanticWebBuilder Process a trav?s de su licenciamiento abierto 
* al p?blico (?open source?), en virtud del cual, usted podr? usarlo en las mismas condiciones con que INFOTEC 
* lo ha dise?ado y puesto a su disposici?n; aprender de ?l; distribuirlo a terceros; acceder a su c?digo fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los t?rminos y 
* condiciones de la LICENCIA ABIERTA AL P?BLICO que otorga INFOTEC para la utilizaci?n de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garant?a sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni impl?cita ni 
* expl?cita, siendo usted completamente responsable de la utilizaci?n que le d? y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposici?n la
* siguiente direcci?n electr?nica: 
*  http://www.semanticwebbuilder.org.mx
**/
 


/*
 * FixedNumericDocument.java
 *
 * Created on 24 de enero de 2005, 10:51 AM
 */

package applets.htmleditor;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

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



