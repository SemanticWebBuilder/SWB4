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
 * @(#)HTMLWriter.java	1.32 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package applets.htmleditor;

import javax.swing.text.html.*;
import javax.swing.text.*;
import java.io.Writer;
import java.util.Stack;
import java.util.Enumeration;
import java.util.Vector;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.net.URL;

/**
 * This is a writer for HTMLDocuments.
 *
 * @author  Sunita Mani
 * @version 1.26, 02/02/00
 */


public class WBHTMLWriter extends HTMLWriter {
    
    boolean inContent=false;
    private boolean inPre = false;
    private boolean completeDoc;
    private boolean wroteHead;
    
    /**
     * If true, the writer will emit CSS attributes in preference
     * to HTML tags/attributes (i.e. It will emit an HTML 4.0
     * style).
     */
    private boolean writeCSS = false;
    
    /**
     * Buffer for the purpose of attribute conversion
     */
    private MutableAttributeSet convAttr = new SimpleAttributeSet();
    
    
    
    /**
     * Creates a new HTMLWriter.
     *
     * @param w   a Writer
     * @param doc  an HTMLDocument
     *
     */
    public WBHTMLWriter(Writer w, HTMLDocument doc) {
	this(w, doc, 0, doc.getLength());
    }

    /**
     * Creates a new HTMLWriter.
     *
     * @param w  a Writer
     * @param doc an HTMLDocument
     * @param pos the document location from which to fetch the content
     * @param len the amount to write out
     */
    public WBHTMLWriter(Writer w, HTMLDocument doc, int pos, int len) {
	super(w, doc, pos, len);
	completeDoc = (pos == 0 && len == doc.getLength());
    }    
    
    
    /**
     * Writes out a start tag for the element.
     * Ignores all synthesized elements.
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     */
    protected void startTag(Element elem) throws IOException, BadLocationException 
    {
        
        if (synthesizedElement(elem)) {
	    return;
	}
        
	// Determine the name, as an HTML.Tag.
	AttributeSet attr = elem.getAttributes();
	Object nameAttribute = attr.getAttribute(StyleConstants.NameAttribute);
	HTML.Tag name;
	if (nameAttribute instanceof HTML.Tag) {
	    name = (HTML.Tag)nameAttribute;
	}
	else {
	    name = null;
	}
	if (name == HTML.Tag.PRE) {
	    inPre = true;
	}        
	if (inContent) { 
	    inContent = false;
	}
        
	if (completeDoc && name == HTML.Tag.BODY && !wroteHead) {
	    // If the head has not been output, output it and the styles.
	    wroteHead = true;
        }
        
        super.startTag(elem);
    }
    
    /**
     * Writes out an end tag for the element.
     *
     * @param elem    an Element
     * @exception IOException on any I/O error
     */
    protected void endTag(Element elem) throws IOException {
	if (synthesizedElement(elem)) {
	    return;
	}        
	if (matchNameAttribute(elem.getAttributes(), HTML.Tag.PRE)) {
	    inPre = false;
	}        
	if (inContent) { 
	    inContent = false;
	}
        super.endTag(elem);
    }    
    
    /**
     * Iterates over the 
     * Element tree and controls the writing out of
     * all the tags and its attributes.
     *
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     *
     */
    public void write() throws IOException, BadLocationException {
	inPre = false;
	wroteHead = false;
        super.write();
    }
	    
    
    /**
     * Writes out all empty elements (all tags that have no
     * corresponding end tag).
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void emptyTag(Element elem) throws BadLocationException, IOException {
	if (!inContent && !inPre) {
	    indent();
	}

	AttributeSet attr = elem.getAttributes();
	closeOutUnwantedEmbeddedTags(attr);
	writeEmbeddedTags(attr);

	if (matchNameAttribute(attr, HTML.Tag.CONTENT)) {
	    inContent = true;
	    text(elem);
	} else if (matchNameAttribute(attr, HTML.Tag.COMMENT)) {
	    comment(elem);
	}  else {
	    boolean isBlock = isBlockTag(elem.getAttributes());
	    if (inContent && isBlock ) {
		writeLineSeparator();
		indent();
	    }

	    Object nameTag = (attr != null) ? attr.getAttribute
		              (StyleConstants.NameAttribute) : null;
	    Object endTag = (attr != null) ? attr.getAttribute
		              (HTML.Attribute.ENDTAG) : null;

	    boolean outputEndTag = false;
	    // If an instance of an UNKNOWN Tag, or an instance of a 
	    // tag that is only visible during editing
	    //
	    if (nameTag != null && endTag != null &&
		(endTag instanceof String) &&
		((String)endTag).equals("true")) {
		outputEndTag = true;
	    }

	    if (completeDoc && matchNameAttribute(attr, HTML.Tag.HEAD)) {
		if (outputEndTag) {
		    // Write out any styles.
		    writeStyles(((HTMLDocument)getDocument()).getStyleSheet());
		}
		wroteHead = true;
	    }

	    write('<');
	    if (outputEndTag) {
		write('/');
	    }
	    write(elem.getName());
	    writeAttributes(attr);
            //JEI: se agrego la siguiente linea
	    if(attr.containsAttribute("_startEndTag_","true"))
            {
                //System.out.println(elem.getName()+" "+attr.getAttribute("_startEndTag_"));
                write('/');
            }
	    write('>');
	    if (matchNameAttribute(attr, HTML.Tag.TITLE) && !outputEndTag) {
		Document doc = elem.getDocument();
		String title = (String)doc.getProperty(Document.TitleProperty);
		write(title);
	    } else if (!inContent || isBlock) {
		writeLineSeparator();
		if (isBlock && inContent) {
		    indent();
		}
	    }
	}
    }    
    
    /**
     * JEI: Clase modificada por eleminiar el atributo _startEndTag_
     * Writes out the attribute set.  Ignores all
     * attributes with a key of type HTML.Tag,
     * attributes with a key of type StyleConstants,
     * and attributes with a key of type
     * HTML.Attribute.ENDTAG.
     *
     * @param attr   an AttributeSet
     * @exception IOException on any I/O error
     *
     */
    protected void writeAttributes(AttributeSet attr) throws IOException {
        SimpleAttributeSet set=new SimpleAttributeSet(attr);
        set.removeAttribute("_startEndTag_");
        super.writeAttributes(set);

    }    
    
    
    /**
     * Outputs the styles as a single element. Styles are not stored as
     * elements, but part of the document. For the time being styles are
     * written out as a comment, inside a style tag.
     */
    void writeStyles(StyleSheet sheet) throws IOException {
	if (sheet != null) {
	    Enumeration styles = sheet.getStyleNames();
	    if (styles != null) {
		boolean outputStyle = false;
		while (styles.hasMoreElements()) {
		    String name = (String)styles.nextElement();
		    // Don't write out the default style.
		    if (!StyleContext.DEFAULT_STYLE.equals(name) &&
			writeStyle(name, sheet.getStyle(name), outputStyle)) {
			outputStyle = true;
		    }
		}
		if (outputStyle) {
		    writeStyleEndTag();
		}
	    }
	}
    }
    
    /**
     * Outputs the named style. <code>outputStyle</code> indicates
     * whether or not a style has been output yet. This will return
     * true if a style is written.
     */
    boolean writeStyle(String name, Style style, boolean outputStyle)
	         throws IOException{
	boolean didOutputStyle = false;
	Enumeration attributes = style.getAttributeNames();
	if (attributes != null) {
	    while (attributes.hasMoreElements()) {
		Object attribute = attributes.nextElement();
		if (attribute instanceof CSS.Attribute) {
		    String value = style.getAttribute(attribute).toString();
		    if (value != null) {
			if (!outputStyle) {
			    writeStyleStartTag();
			    outputStyle = true;
			}
			if (!didOutputStyle) {
			    didOutputStyle = true;
			    indent();
			    write(name);
			    write(" {");
			}
			else {
			    write(";");
			}
			write(' ');
			write(attribute.toString());
			write(": ");
			write(value);
		    }
		}
	    }
	}
	if (didOutputStyle) {
	    write(" }");
	    writeLineSeparator();
	}
	return didOutputStyle;
    }

    void writeStyleStartTag() throws IOException {
	indent();
	write("<style type=\"text/css\">");
	incrIndent();
	writeLineSeparator();
	indent();
	write("<!--");
	incrIndent();
	writeLineSeparator();
    }

    void writeStyleEndTag() throws IOException {
	decrIndent();
	indent();
	write("-->");
	writeLineSeparator();
	decrIndent();
	indent();
	write("</style>");
	writeLineSeparator();
	indent();
    }
    
}
