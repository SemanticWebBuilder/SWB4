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
 * WBHTMLEditoKit.java
 *
 * Created on 5 de octubre de 2004, 01:22 PM
 */

package applets.htmleditor;

import java.lang.reflect.Method;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.TextUI;
import java.util.*;
import javax.accessibility.*;
import java.lang.ref.*;
import javax.swing.text.html.*;
import java.text.DateFormat;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBHTMLEditoKit extends HTMLEditorKit
{
    HTMLEditorKit.Parser defaultParser=null;
    
    // purely to make this method public
    public HTMLEditorKit.Parser getParser()
    {
        //return super.getParser();
	if (defaultParser == null) {
	    try {
                //Class c = Class.forName("javax.swing.text.html.parser.ParserDelegator");
                Class c = Class.forName("applets.htmleditor.WBParserDelegator");
                defaultParser = (Parser) c.newInstance();
	    } catch (Throwable e) {
                e.printStackTrace(System.out);
	    }
	}
	return defaultParser;        
    }
    
    /** Method for returning a ViewFactory which handles the image rendering.
     */
    public ViewFactory getViewFactory()
    {
        return new WBHTMLFactory();
    }
    
    
    /**
     * Create an uninitialized text storage model
     * that is appropriate for this type of editor.
     *
     * @return the model
     */
    public Document createDefaultDocument() {
	StyleSheet styles = getStyleSheet();
	StyleSheet ss = new StyleSheet();

	ss.addStyleSheet(styles);

	WBHTMLDocument doc = new WBHTMLDocument(ss);
	doc.setParser(getParser());
	doc.setAsynchronousLoadPriority(4);
	doc.setTokenThreshold(100);
	return doc;
    }
    
    /**
     * A factory to build views for HTML.  The following 
     * table describes what this factory will build by
     * default.
     */
    public static class WBHTMLFactory extends HTMLFactory implements ViewFactory
    {
        /** Constructor
         */
        public WBHTMLFactory()
        {
            super();
        }
        
        /** Method to handle IMG tags and
         * invoke the image loader.
         */
        public View create(Element elem)
        {
            String kind = elem.getName();
            Object obj = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
            if(obj instanceof HTML.Tag)
            {
                HTML.Tag tagType = (HTML.Tag)obj;
                
                //System.out.println("WBHTMLFactory.create.element:"+kind);

                if(tagType instanceof HTML.UnknownTag )//&& elem.getAttributes().getAttribute(HTML.Attribute.ENDTAG)== null)
                {
                    if(kind.equalsIgnoreCase("resource"))
                    {
                        //return new ResourceView(elem);
                    }else if(kind.equalsIgnoreCase("content"))
                    {
                        //return new ResourceView(elem);
                    }else if(kind.equalsIgnoreCase("tbody"))
                    {
                        return new ComponentView(elem);
                        //return new HiddenTagView(elem);
                    }else if(kind.equalsIgnoreCase("o"))
                    {
                        return new ComponentView(elem);
                        //return new HiddenTagView(elem);
                    }else if(tagType == HTML.Tag.IMG)
                    {
                        //return new RelativeImageView(elem);
                        //System.out.println("IMG:"+tagType+" element:"+elem);
                        //return new ImageView(elem);
                    }
                }
            }

            return super.create(elem);
        }
    }
    
    /**
     * Write content from a document to the given stream
     * in a format appropriate for this kind of content handler.
     * 
     * @param out  the stream to write to
     * @param doc  the source for the write
     * @param pos  the location in the document to fetch the
     *   content
     * @param len  the amount to write out
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document
     */
    public void write(Writer out, Document doc, int pos, int len) 
	throws IOException, BadLocationException {

	if (doc instanceof HTMLDocument) {
	    WBHTMLWriter w = new WBHTMLWriter(out, (HTMLDocument)doc, pos, len);
	    w.write();
	} else if (doc instanceof StyledDocument) {
	    MinimalHTMLWriter w = new MinimalHTMLWriter(out, (StyledDocument)doc, pos, len);
	    w.write();
	} else {
	    super.write(out, doc, pos, len);
	}
    }    
    
}
