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
 * @(#)WBContentModelState.java	1.10 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package applets.htmleditor;

import javax.swing.text.html.parser.*;

/**
 * A content model state. This is basically a list of pointers to
 * the BNF expression representing the model (the ContentModel).
 * Each element in a DTD has a content model which describes the
 * elements that may occur inside, and the order in which they can
 * occur.
 * <p>
 * Each time a token is reduced a new state is created.
 * <p>
 * See Annex H on page 556 of the SGML handbook for more information.
 *
 * @see Parser
 * @see DTD
 * @see WBElement
 * @see WBContentModel
 * @author Arthur van Hoff
 * @version 	1.10 01/23/03
 */
class WBContentModelState {
    WBContentModel model;
    long value;
    WBContentModelState next;

    /**
     * Create a content model state for a content model.
     */
    public WBContentModelState(WBContentModel model) {
	this(model, null, 0);
    }

    /**
     * Create a content model state for a content model given the
     * remaining state that needs to be reduce.
     */
    WBContentModelState(Object content, WBContentModelState next) {
	this(content, next, 0);
    }

    /**
     * Create a content model state for a content model given the
     * remaining state that needs to be reduce.
     */
    WBContentModelState(Object content, WBContentModelState next, long value) {
	this.model = (WBContentModel)content;
	this.next = next;
	this.value = value;
    }

    /**
     * Return the content model that is relevant to the current state.
     */
    public WBContentModel getModel() {
	WBContentModel m = model;
	for (int i = 0; i < value; i++) {
	    if (m.next != null) {
		m = m.next;
	    } else {
		return null;
	    }
	}
        return m;
    }

    /**
     * Check if the state can be terminated. That is there are no more
     * tokens required in the input stream.
     * @return true if the model can terminate without further input
     */
    public boolean terminate() {
	switch (model.type) {
	  case '+':
	    if ((value == 0) && !(model).empty()) {
		return false;
	    }
	  case '*':
	  case '?':
	    return (next == null) || next.terminate();

	  case '|':
	    for (WBContentModel m = (WBContentModel)model.content ; m != null ; m = m.next) {
		if (m.empty()) {
		    return (next == null) || next.terminate();
		}
	    }
	    return false;

	  case '&': {
	    WBContentModel m = (WBContentModel)model.content;

	    for (int i = 0 ; m != null ; i++, m = m.next) {
		if ((value & (1L << i)) == 0) {
		    if (!m.empty()) {
			return false;
		    }
		}
	    }
	    return (next == null) || next.terminate();
	  }

	  case ',': {
	    WBContentModel m = (WBContentModel)model.content;
	    for (int i = 0 ; i < value ; i++, m = m.next);

	    for (; (m != null) && m.empty() ; m = m.next);
	    if (m != null) {
		return false;
	    }
	    return (next == null) || next.terminate();
	  }

	default:
	  return false;
	}
    }

    /**
     * Check if the state can be terminated. That is there are no more
     * tokens required in the input stream.
     * @return the only possible element that can occur next
     */
    public WBElement first() {
	switch (model.type) {
	  case '*':
	  case '?':
	  case '|':
	  case '&':
	    return null;

	  case '+':
	    return model.first();

	  case ',': {
	      WBContentModel m = (WBContentModel)model.content;
	      for (int i = 0 ; i < value ; i++, m = m.next);
	      return m.first();
	  }

	  default:
	    return model.first();
	}
    }

    /**
     * Advance this state to a new state. An exception is thrown if the
     * token is illegal at this point in the content model.
     * @return next state after reducing a token
     */
    public WBContentModelState advance(Object token) {
	switch (model.type) {
	  case '+':
	    if (model.first(token)) {
		return new WBContentModelState(model.content,
		        new WBContentModelState(model, next, value + 1)).advance(token);
	    }
	    if (value != 0) {
		if (next != null) {
		    return next.advance(token);
		} else {
		    return null;
		}
	    }
	    break;

	  case '*':
	    if (model.first(token)) {
		return new WBContentModelState(model.content, this).advance(token);
	    }
	    if (next != null) {
		return next.advance(token);
	    } else {
		return null;
	    }

	  case '?':
	    if (model.first(token)) {
		return new WBContentModelState(model.content, next).advance(token);
	    }
	    if (next != null) {
		return next.advance(token);
	    } else {
		return null;
	    }

	  case '|':
	    for (WBContentModel m = (WBContentModel)model.content ; m != null ; m = m.next) {
		if (m.first(token)) {
		    return new WBContentModelState(m, next).advance(token);
		}
	    }
	    break;

	  case ',': {
	    WBContentModel m = (WBContentModel)model.content;
	    for (int i = 0 ; i < value ; i++, m = m.next);

	    if (m.first(token) || m.empty()) {
		if (m.next == null) {
		    return new WBContentModelState(m, next).advance(token);
		} else {
		    return new WBContentModelState(m,
			    new WBContentModelState(model, next, value + 1)).advance(token);
		}
	    }
	    break;
	  }

	  case '&': {
	    WBContentModel m = (WBContentModel)model.content;
	    boolean complete = true;

	    for (int i = 0 ; m != null ; i++, m = m.next) {
		if ((value & (1L << i)) == 0) {
		    if (m.first(token)) {
			return new WBContentModelState(m,
			        new WBContentModelState(model, next, value | (1L << i))).advance(token);
		    }
		    if (!m.empty()) {
			complete = false;
		    }
		}
	    }
	    if (complete) {
		if (next != null) {
		    return next.advance(token);
		} else {
		    return null;
		}
	    }
	    break;
	  }

	  default:
	    if (model.content == token) {
                if (next == null && (token instanceof WBElement) &&
                    ((WBElement)token).content != null) {
                    return new WBContentModelState(((WBElement)token).content);
                }
		return next;
	    }
            // PENDING: Currently we don't correctly deal with optional start 
            // tags. This can most notably be seen with the 4.01 spec where
            // TBODY's start and end tags are optional.
            // Uncommenting this and the PENDING in ContentModel will
            // correctly skip the omit tags, but the delegate is not notified.
            // Some additional API needs to be added to track skipped tags,
            // and this can then be added back.
/*
            if ((model.content instanceof WBElement)) {
                WBElement e = (WBElement)model.content;

                if (e.omitStart() && e.content != null) {
                    return new WBContentModelState(e.content, next).advance(
                                           token);
                }
            }
*/
	}

	// We used to throw this exception at this point.  However, it
	// was determined that throwing this exception was more expensive
	// than returning null, and we could not justify to ourselves why
	// it was necessary to throw an exception, rather than simply
	// returning null.  I'm leaving it in a commented out state so
	// that it can be easily restored if the situation ever arises.
	//
	// throw new IllegalArgumentException("invalid token: " + token);
	return null;
    }
}
