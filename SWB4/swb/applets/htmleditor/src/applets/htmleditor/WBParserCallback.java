/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * WBParcerCallback.java
 *
 * Created on 5 de octubre de 2004, 02:05 PM
 */

package applets.htmleditor;

import javax.swing.text.html.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.font.TextAttribute;
import java.util.*;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import java.text.Bidi;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBParserCallback extends HTMLEditorKit.ParserCallback
{
  private Writer out;
  private String lineSeparator;

  public WBParserCallback(Writer out) {
    this(out, System.getProperty("line.separator", "\r\n")); 
  }  
  
  public WBParserCallback(Writer out, String lineSeparator) {
    this.out = out; 
    this.lineSeparator = lineSeparator;
  }      
    
    
  public void handleStartTag(HTML.Tag tag, MutableAttributeSet attrset, int pos) {
    System.out.println("Start TAG ("+tag+")");
    if (tag.equals(HTML.Tag.A)) {
      String attr = (String)attrset.getAttribute(HTML.Attribute.HREF);
      System.out.println("A href = " + attr);
    }
  }

  public void handleEndTag(HTML.Tag tag, int pos) {
    System.out.println("End TAG ("+tag+")");
  }

  public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attrset, int pos) {
    System.out.println("Simple TAG ("+tag+")");
  }

  public void handleText(char[] data, int pos) {
    System.out.println("Text = ("+ new String(data) +")");
  }
  
  public void handleError(String errorMsg, int pos) {
    System.out.println("Error = ("+ errorMsg +")");
  }
  
    
/*    
  
  public void handleText(char[] text, int position) {
    try {
      out.write(text);
      out.flush();
    }
    catch (IOException e) {
      System.err.println(e); 
    }
  }
  
  public void handleEndTag(HTML.Tag tag, int position) {

    try {
      if (tag.isBlock()) {
        out.write(lineSeparator);
        out.write(lineSeparator);
      }
      else if (tag.breaksFlow()) {
        out.write(lineSeparator);
      }
    }
    catch (IOException e) {
      System.err.println(e); 
    }
    
  }
  public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, 
   int position) {
    
    try {
      if (tag.isBlock()) {
        out.write(lineSeparator);
        out.write(lineSeparator);
      }
      else if (tag.breaksFlow()) {
        out.write(lineSeparator);
      }
      else {
        out.write(' '); 
      }
    }
    catch (IOException e) {
      System.err.println(e); 
    }
 
  }
 */
}
    
