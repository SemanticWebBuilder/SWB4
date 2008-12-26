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
 * WBDocumentParser.java
 *
 * Created on 7 de octubre de 2004, 05:08 PM
 */
package applets.htmleditor;

import javax.swing.text.html.parser.*;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.ChangedCharSetException;

import java.util.*;
import java.io.*;
import java.net.*;

import sun.io.*;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBDocumentParser extends WBParser
{
    private int inbody;
    private int intitle;
    private int inhead;
    private int instyle;
    private boolean seentitle;
    private HTMLEditorKit.ParserCallback callback = null;
    private boolean ignoreCharSet = false;
    private static final boolean debugFlag = false;

    public WBDocumentParser(WBDTD dtd) {
	super(dtd);
    }
 
    public void parse(Reader in,  HTMLEditorKit.ParserCallback callback, boolean ignoreCharSet) throws IOException {
	this.ignoreCharSet = ignoreCharSet;
	this.callback = callback;
	parse(in);
	// end of line
	callback.handleEndOfLineString(getEndOfLineString());
    }

    /**
     * Handle Start Tag.
     */
    protected void handleStartTag(WBTagElement tag) {

	WBElement elem = tag.getElement();
	if (elem == dtd.body) {
	    inbody++;
	} else if (elem == dtd.html) {
	} else if (elem == dtd.head) {
	    inhead++;
	} else if (elem == dtd.title) {
	    intitle++;
	} else if (elem == dtd.style) {
	    instyle++;
	}	
	if (debugFlag) {
	    if (tag.fictional()) {
		debug("Start Tag: " + tag.getHTMLTag() + " pos: " + getCurrentPos());
	    } else {
		debug("Start Tag: " + tag.getHTMLTag() + " attributes: " + 
		      getAttributes() + " pos: " + getCurrentPos());
	    }
	}
	if (tag.fictional()) {
	    SimpleAttributeSet attrs = new SimpleAttributeSet();
	    attrs.addAttribute(HTMLEditorKit.ParserCallback.IMPLIED,
			       Boolean.TRUE);
	    callback.handleStartTag(tag.getHTMLTag(), attrs,
				    getBlockStartPosition());
	} else {
	    callback.handleStartTag(tag.getHTMLTag(), getAttributes(),
				    getBlockStartPosition());
	    flushAttributes();
	}
    }


    protected void handleComment(char text[]) {
	if (debugFlag) {
	    debug("comment: ->" + new String(text) + "<-"
		  + " pos: " + getCurrentPos());
	}
	callback.handleComment(text, getBlockStartPosition());
    }

    /**
     * Handle Empty Tag.
     */
    protected void handleEmptyTag(WBTagElement tag) throws ChangedCharSetException {

	WBElement elem = tag.getElement();
	if (elem == dtd.meta && !ignoreCharSet) {
	    SimpleAttributeSet atts = getAttributes();
	    if (atts != null) {
		String content = (String)atts.getAttribute(HTML.Attribute.CONTENT);
		if (content != null) {
		    if ("content-type".equalsIgnoreCase((String)atts.getAttribute(HTML.Attribute.HTTPEQUIV))) {
			throw new ChangedCharSetException(content, false);
		    } else if ("charset" .equalsIgnoreCase((String)atts.getAttribute(HTML.Attribute.HTTPEQUIV))) {
			throw new ChangedCharSetException(content, true);
		    }
		}
	    }
	}
	if (inbody != 0 || elem == dtd.meta || elem == dtd.base || elem == dtd.isindex || elem == dtd.style || elem == dtd.link) {
	    if (debugFlag) {
		if (tag.fictional()) {
		    debug("Empty Tag: " + tag.getHTMLTag() + " pos: " + getCurrentPos());
		} else {
		    debug("Empty Tag: " + tag.getHTMLTag() + " attributes: " 
			  + getAttributes() + " pos: " + getCurrentPos());
		}
	    }
	    if (tag.fictional()) {
		SimpleAttributeSet attrs = new SimpleAttributeSet();
		attrs.addAttribute(HTMLEditorKit.ParserCallback.IMPLIED,
				   Boolean.TRUE);
		callback.handleSimpleTag(tag.getHTMLTag(), attrs,
					 getBlockStartPosition());
	    } else {
		callback.handleSimpleTag(tag.getHTMLTag(), getAttributes(),
					 getBlockStartPosition());
		flushAttributes();
	    }
	}
    }

    /**
     * Handle End Tag.
     */
    protected void handleEndTag(WBTagElement tag) {
	WBElement elem = tag.getElement();
	if (elem == dtd.body) {
	    inbody--;
	} else if (elem == dtd.title) {
	    intitle--;
	    seentitle = true;
	} else if (elem == dtd.head) {
            inhead--;
	} else if (elem == dtd.style) {
            instyle--;
	}
	if (debugFlag) {
	    debug("End Tag: " + tag.getHTMLTag() + " pos: " + getCurrentPos());
	}
	callback.handleEndTag(tag.getHTMLTag(), getBlockStartPosition());

    }

    /**
     * Handle Text.
     */
    protected void handleText(char data[]) {
	if (data != null) {
	    if (inbody != 0 || ((instyle != 0) ||
				((intitle != 0) && !seentitle))) {
		if (debugFlag) {
		    debug("text:  ->" + new String(data) + "<-" + " pos: " + getCurrentPos());
		}
		callback.handleText(data, getBlockStartPosition());
	    }
	}
    }

    /*
     * Error handling.
     */
    protected void handleError(int ln, String errorMsg) {
	if (debugFlag) {
	    debug("Error: ->" + errorMsg + "<-" + " pos: " + getCurrentPos());
	}
	/* PENDING: need to improve the error string. */
	callback.handleError(errorMsg, getCurrentPos());
    }


    /*
     * debug messages
     */
    private void debug(String msg) {
	System.out.println(msg);
    }
}
