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
 * @(#)WBDTD.java	1.16 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package applets.htmleditor;

import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.util.Hashtable;
import java.util.Vector;
import java.util.BitSet;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Properties;
import java.net.URL;

import javax.swing.text.html.parser.*;

/**
 * The representation of an SGML DTD.  DTD describes a document
 * syntax and is used in parsing of HTML documents.  It contains
 * a list of elements and their attributes as well as a list of
 * entities defined in the DTD.
 *
 * @see WBElement
 * @see AttributeList
 * @see WBContentModel
 * @see Parser
 * @author Arthur van Hoff
 * @version 1.16 01/23/03
 */
public class WBDTD implements DTDConstants {
    public String name;
    public Vector elements = new Vector();
    public Hashtable elementHash = new Hashtable();
    public Hashtable entityHash = new Hashtable();
    public final WBElement pcdata = getElement("#pcdata");
    public final WBElement html = getElement("html");
    public final WBElement meta = getElement("meta");
    public final WBElement base = getElement("base");
    public final WBElement isindex = getElement("isindex");
    public final WBElement head = getElement("head");
    public final WBElement body = getElement("body");
    public final WBElement applet = getElement("applet");
    public final WBElement param = getElement("param");
    public final WBElement p = getElement("p");
    public final WBElement title = getElement("title");
    final WBElement style = getElement("style");
    final WBElement link = getElement("link");

    public static int FILE_VERSION = 1;

    /**
     * Creates a new WBDTD with the specified name.
     * @param name the name, as a <code>String</code> of the new WBDTD
     */
    protected WBDTD(String name) {
	this.name = name;
	defEntity("#RE", GENERAL, '\r');
	defEntity("#RS", GENERAL, '\n');
	defEntity("#SPACE", GENERAL, ' ');
	defineElement("unknown", EMPTY, false, true, null, null, null, null);
    }

    /**
     * Gets the name of the WBDTD.
     * @return the name of the WBDTD
     */
    public String getName() {
	return name;
    }

    /**
     * Gets an entity by name.
     * @return the <code>Entity</code> corresponding to the 
     *   <code>name</code> <code>String</code>
     */
    public Entity getEntity(String name) {
	return (Entity)entityHash.get(name);
    }

    /**
     * Gets a character entity.
     * @return the <code>Entity</code> corresponding to the
     *    <code>ch</code> character
     */
    public Entity getEntity(int ch) {
	return (Entity)entityHash.get(new Integer(ch));
    }

    /**
     * Returns <code>true</code> if the element is part of the WBDTD,
     * otherwise returns <code>false</code>.
     *
     * @param  name the requested <code>String</code>
     * @return <code>true</code> if <code>name</code> exists as
     *   part of the WBDTD, otherwise returns <code>false</code>
     */
    boolean elementExists(String name) {
	WBElement e = (WBElement)elementHash.get(name);
	return ((e == null) ? false : true);
    }

    /**
     * Gets an element by name. A new element is
     * created if the element doesn't exist.
     *
     * @param name the requested <code>String</code>
     * @return the <code>WBElement</code> corresponding to
     *   <code>name</code>, which may be newly created
     */
    public WBElement getElement(String name) {
	WBElement e = (WBElement)elementHash.get(name);
	if (e == null) {
	    e = new WBElement(name, elements.size());
	    elements.addElement(e);
	    elementHash.put(name, e);
	}
	return e;
    }

    /**
     * Gets an element by index.
     *
     * @param index the requested index
     * @return the <code>WBElement</code> corresponding to
     *   <code>index</code>
     */
    public WBElement getElement(int index) {
	return (WBElement)elements.elementAt(index);
    }

    /**
     * Defines an entity.  If the <code>Entity</code> specified
     * by <code>name</code>, <code>type</code>, and <code>data</code>
     * exists, it is returned; otherwise a new <code>Entity</code>
     * is created and is returned.
     *
     * @param name the name of the <code>Entity</code> as a <code>String</code>
     * @param type the type of the <code>Entity</code>
     * @param data the <code>Entity</code>'s data
     * @return the <code>Entity</code> requested or a new <code>Entity</code>
     *   if not found
     */
    public Entity defineEntity(String name, int type, char data[]) {
	Entity ent = (Entity)entityHash.get(name);
	if (ent == null) {
	    ent = new Entity(name, type, data);
	    entityHash.put(name, ent);
	    if (((type & GENERAL) != 0) && (data.length == 1)) {
		switch (type & ~GENERAL) {
		  case CDATA:
		  case SDATA:
		    entityHash.put(new Integer(data[0]), ent);
		    break;
		}
	    }
	}
	return ent;
    }

    /**
     * Returns the <code>WBElement</code> which matches the
     * specified parameters.  If one doesn't exist, a new
     * one is created and returned.
     *
     * @param name the name of the <code>WBElement</code>
     * @param type the type of the <code>WBElement</code>
     * @param omitStart <code>true</code if start should be omitted
     * @param omitEnd  <code>true</code> if end should be omitted
     * @param content  the <code>WBContentModel</code>
     * @param atts the <code>AttributeList</code> specifying the 
     *    <code>WBElement</code>
     * @return the <code>WBElement</code> specified
     */
    public WBElement defineElement(String name, int type,
		       boolean omitStart, boolean omitEnd, WBContentModel content,
		       BitSet exclusions, BitSet inclusions, AttributeList atts) {
	WBElement e = getElement(name);
	e.type = type;
	e.oStart = omitStart;
	e.oEnd = omitEnd;
	e.content = content;
	e.exclusions = exclusions;
	e.inclusions = inclusions;
	e.atts = atts;
	return e;
    }

    /**
     * Returns the <code>WBElement</code> which matches the
     * specified <code>AttributeList</code>. 
     * If one doesn't exist, a new one is created and returned.
     *
     * @param name the name of the <code>WBElement</code>
     * @param atts the <code>AttributeList</code> specifying the 
     *    <code>WBElement</code>
     * @return the <code>WBElement</code> specified
     */
    public void defineAttributes(String name, AttributeList atts) {
	WBElement e = getElement(name);
	e.atts = atts;
    }

    /**
     * Creates and returns a character <code>Entity</code>.
     * @param name the entity's name
     * @return the new character <code>Entity</code>
     */
    public Entity defEntity(String name, int type, int ch) {
	char data[] = {(char)ch};
	return defineEntity(name, type, data);
    }

    /**
     * Creates and returns an <code>Entity</code>.
     * @param name the entity's name
     * @return the new <code>Entity</code>
     */
    protected Entity defEntity(String name, int type, String str) {
	int len = str.length();
	char data[] = new char[len];
	str.getChars(0, len, data, 0);
	return defineEntity(name, type, data);
    }

    /**
     * Creates and returns an <code>WBElement</code>.
     * @param name the element's name
     * @return the new <code>WBElement</code>
     */
    protected WBElement defElement(String name, int type,
		       boolean omitStart, boolean omitEnd, WBContentModel content,
		       String[] exclusions, String[] inclusions, AttributeList atts) {
	BitSet excl = null;
	if (exclusions != null && exclusions.length > 0) {
	    excl = new BitSet();
	    for (int i = 0; i < exclusions.length; i++) {
		String str = exclusions[i];
		if (str.length() > 0) {
		    excl.set(getElement(str).getIndex());
		}
	    }
	}
	BitSet incl = null;
	if (inclusions != null && inclusions.length > 0) {
	    incl = new BitSet();
	    for (int i = 0; i < inclusions.length; i++) {
		String str = inclusions[i];
		if (str.length() > 0) {
		    incl.set(getElement(str).getIndex());
		}
	    }
	}
	return defineElement(name, type, omitStart, omitEnd, content, excl, incl, atts);
    }

    /**
     * Creates and returns an <code>AttributeList</code>.
     * @param name the attribute list's name
     * @return the new <code>AttributeList</code>
     */
    protected AttributeList defAttributeList(String name, int type, int modifier, String value, String values, AttributeList atts) {
	Vector vals = null;
	if (values != null) {
	    vals = new Vector();
	    for (StringTokenizer s = new StringTokenizer(values, "|") ; s.hasMoreTokens() ;) {
		String str = s.nextToken();
		if (str.length() > 0) {
		    vals.addElement(str);
		}
	    }
	}
	return new AttributeList(name, type, modifier, value, vals, atts);
    }

    /**
     * Creates and returns a new content model.
     * @param type the type of the new content model
     * @return the new <code>WBContentModel</code>
     */
    protected WBContentModel defContentModel(int type, Object obj, WBContentModel next) {
	return new WBContentModel(type, obj, next);
    }

    /**
     * Returns a string representation of this WBDTD.
     * @return the string representation of this WBDTD
     */
    public String toString() {
	return name;
    }

    /**
     * The hashtable of DTDs.
     */
    static Hashtable dtdHash = new Hashtable();

  public static void putDTDHash(String name, WBDTD dtd) {
    dtdHash.put(name, dtd);
  }
    /**
     * Returns a WBDTD with the specified <code>name</code>.  If
     * a WBDTD with that name doesn't exist, one is created
     * and returned.  Any uppercase characters in the name
     * are converted to lowercase.
     *
     * @param name the name of the WBDTD
     * @return the WBDTD which corresponds to <code>name</code>
     */
    public static WBDTD getDTD(String name) throws IOException {
	name = name.toLowerCase();
	WBDTD dtd = (WBDTD)dtdHash.get(name);
	if (dtd == null)
	  dtd = new WBDTD(name);

	return dtd;
    }

    /**
     * Recreates a WBDTD from an archived format.
     * @param in  the <code>DataInputStream</code> to read from
     */
    public void read(DataInputStream in) throws IOException {
	if (in.readInt() != FILE_VERSION) {
	}

	//
	// Read the list of names
	//
	String[] names = new String[in.readShort()];
	for (int i = 0; i < names.length; i++) {
	    names[i] = in.readUTF();
	}


	//
	// Read the entities
	//
	int num = in.readShort();
	for (int i = 0; i < num; i++) {
	    short nameId = in.readShort();
	    int type = in.readByte();
	    String name = in.readUTF();
	    defEntity(names[nameId], type | GENERAL, name);
	}

	// Read the elements
	//
	num = in.readShort();
	for (int i = 0; i < num; i++) {
	    short nameId = in.readShort();
	    int type = in.readByte();
	    byte flags = in.readByte();
	    WBContentModel m = readContentModel(in, names);
	    String[] exclusions = readNameArray(in, names);
	    String[] inclusions = readNameArray(in, names);
	    AttributeList atts = readAttributeList(in, names);
	    defElement(names[nameId], type,
		       ((flags & 0x01) != 0), ((flags & 0x02) != 0),
		       m, exclusions, inclusions, atts);
	}
    }

    private WBContentModel readContentModel(DataInputStream in, String[] names)
		throws IOException {
	byte flag = in.readByte();
	switch(flag) {
	    case 0:		// null
		return null;
	    case 1: {		// content_c
		int type = in.readByte();
		WBContentModel m = readContentModel(in, names);
		WBContentModel next = readContentModel(in, names);
		return defContentModel(type, m, next);
	    }
	    case 2: {		// content_e
		int type = in.readByte();
		WBElement el = getElement(names[in.readShort()]);
		WBContentModel next = readContentModel(in, names);
		return defContentModel(type, el, next);
	    }
	default:
		throw new IOException("bad bdtd");
	}
    }

    private String[] readNameArray(DataInputStream in, String[] names)
		throws IOException {
	int num = in.readShort();
	if (num == 0) {
	    return null;
	}
	String[] result = new String[num];
	for (int i = 0; i < num; i++) {
	    result[i] = names[in.readShort()];
	}
	return result;
    }


    private AttributeList readAttributeList(DataInputStream in, String[] names)
		throws IOException  {
	AttributeList result = null;
	for (int num = in.readByte(); num > 0; --num) {
	    short nameId = in.readShort();
	    int type = in.readByte();
	    int modifier = in.readByte();
	    short valueId = in.readShort();
	    String value = (valueId == -1) ? null : names[valueId];
	    Vector values = null;
	    short numValues = in.readShort();
	    if (numValues > 0) {
		values = new Vector(numValues);
		for (int i = 0; i < numValues; i++) {
		    values.addElement(names[in.readShort()]);
		}
	    }
result = new AttributeList(names[nameId], type, modifier, value,
				       values, result);
	    // We reverse the order of the linked list by doing this, but
	    // that order isn't important.
	}
	return result;
    }

}
