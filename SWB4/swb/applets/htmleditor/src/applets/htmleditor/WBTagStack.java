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
 * @(#)TagStack.java	1.9 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package applets.htmleditor;

import java.util.BitSet;
import java.util.Vector;
import java.io.*;

import javax.swing.text.html.parser.*;

/**
 * A stack of tags. Used while parsing an HTML document.
 * It, together with the ContentModelStates, defines the
 * complete state of the parser while reading a document.
 * When a start tag is encountered an element is pushed onto
 * the stack, when an end tag is enountered an element is popped
 * of the stack.
 *
 * @see Parser
 * @see DTD
 * @see WBContentModelState
 * @version 	1.9, 01/23/03
 * @author 	Arthur van Hoff
 */
final class WBTagStack implements DTDConstants {
    WBTagElement tag;
    WBElement elem;
    WBContentModelState state;
    WBTagStack next;
    BitSet inclusions;
    BitSet exclusions;
    boolean net;
    boolean pre;

    /**
     * Construct a stack element.
     */
    WBTagStack(WBTagElement tag, WBTagStack next) {
	this.tag = tag;
	this.elem = tag.getElement();
	this.next = next;

	WBElement elem = tag.getElement();
	if (elem.getContent() != null) {
	    this.state = new WBContentModelState(elem.getContent());
	}

	if (next != null) {
	    inclusions = next.inclusions;
	    exclusions = next.exclusions;
	    pre = next.pre;
	}
	if (tag.isPreformatted()) {
	    pre = true;
	}

	if (elem.inclusions != null) {
	    if (inclusions != null) {
		inclusions = (BitSet)inclusions.clone();
		inclusions.or(elem.inclusions);
	    } else {
		inclusions = elem.inclusions;
	    }
	}
	if (elem.exclusions != null) {
	    if (exclusions != null) {
		exclusions = (BitSet)exclusions.clone();
		exclusions.or(elem.exclusions);
	    } else {
		exclusions = elem.exclusions;
	    }
	}
    }

    /**
     * Return the element that must come next in the
     * input stream.
     */
    public WBElement first() {
	return (state != null) ? state.first() : null;
    }

    /**
     * Return the ContentModel that must be satisfied by
     * what comes next in the input stream.
     */
    public WBContentModel contentModel() {
	if (state == null) {
	    return null;
	} else {
	    return state.getModel();
	}
    }

    /**
     * Return true if the element that is contained at
     * the index specified by the parameter is part of
     * the exclusions specified in the DTD for the element
     * currently on the TagStack.
     */
    boolean excluded(int elemIndex) {
	return (exclusions != null) && exclusions.get(elem.getIndex());
    }

    /**
     * Update the Vector elemVec with all the elements that
     * are part of the inclusions listed in DTD for the element
     * currently on the TagStack.
     */
    boolean included(Vector elemVec, DTD dtd) {

	for (int i = 0 ; i < inclusions.size(); i++) {
	    if (inclusions.get(i)) {
		elemVec.addElement(dtd.getElement(i));
		System.out.println("WBElement add thru' inclusions: " + dtd.getElement(i).getName());
	    }
	}
	return (!elemVec.isEmpty());
    }


    /**
     * Advance the state by reducing the given element.
     * Returns false if the element is not legal and the
     * state is not advanced.
     */
    boolean advance(WBElement elem) {
	if ((exclusions != null) && exclusions.get(elem.getIndex())) {
	    return false;
	}
	if (state != null) {
	    WBContentModelState newState = state.advance(elem);
	    if (newState != null) {
		state = newState;
		return true;
	    }
	} else if (this.elem.getType() == ANY) {
	    return true;
	}
	return (inclusions != null) && inclusions.get(elem.getIndex());
    }

    /**
     * Return true if the current state can be terminated.
     */
    boolean terminate() {
	return (state == null) || state.terminate();
    }

    /**
     * Convert to a string.
     */
    public String toString() {
	return (next == null) ?
	    "<" + tag.getElement().getName() + ">" :
	    next + " <" + tag.getElement().getName() + ">";
    }
}

class NPrintWriter extends PrintWriter {

    private int numLines = 5;
    private int numPrinted = 0;

    public NPrintWriter (int numberOfLines) {
	super(System.out);
	numLines = numberOfLines;
    }

    public void println(char[] array) {
	if (numPrinted >= numLines) {
	    return;
	}

	char[] partialArray = null;

	for (int i = 0; i < array.length; i++) {
	    if (array[i] == '\n') {
		numPrinted++;
	    }

	    if (numPrinted == numLines) {
		System.arraycopy(array, 0, partialArray, 0, i);
	    }
	}

	if (partialArray != null) {
	    super.print(partialArray);
	}

	if (numPrinted == numLines) {
	    return;
	}

	super.println(array);
	numPrinted++;
    }
}


