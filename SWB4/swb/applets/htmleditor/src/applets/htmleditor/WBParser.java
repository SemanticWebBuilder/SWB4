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
 * WBParser.java
 *
 * Created on 7 de octubre de 2004, 05:11 PM
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
public class WBParser implements DTDConstants 
{

    private char text[] = new char[1024];
    private int textpos = 0;
    private WBTagElement last;
    private boolean space;

    private char str[] = new char[128];
    private int strpos = 0;

    protected WBDTD dtd = null;

    private int ch;
    private int ln;
    private Reader in;

    private WBElement recent;
    private WBTagStack stack;
    private boolean skipTag = false;
    private WBTagElement lastFormSent = null;
    private SimpleAttributeSet attributes = new SimpleAttributeSet();

    // State for <html>, <head> and <body>.  Since people like to slap
    // together HTML documents without thinking, occasionally they
    // have multiple instances of these tags.  These booleans track
    // the first sightings of these tags so they can be safely ignored
    // by the parser if repeated.
    private boolean seenHtml = false;
    private boolean seenHead = false;
    private boolean seenBody = false;

    /**
     * The html spec does not specify how spaces are coalesced very well.
     * If strict == false, ignoreSpace is used to try and mimic the behavior
     * of the popular browsers.
     * <p>
     * The problematic scenarios are:
     * '&lt;b>blah &lt;i> &lt;strike> foo' which can be treated as:
     * '&lt;b>blah &lt;i>&lt;strike>foo'
     * as well as:
     * '&lt;p>&lt;a href="xx"> &lt;em>Using&lt;/em>&lt;/a>&lt;/p>'
     * which appears to be treated as:
     * '&lt;p>&lt;a href="xx">&lt;em>Using&lt;/em>&lt;/a>&lt;/p>'
     * <p>
     * When a tag that breaks flow, or trailing whitespace is encountered
     * ignoreSpace is set to true. From then on, all whitespace will be
     * ignored.
     * ignoreSpace will be set back to false the first time a
     * non whitespace character is encountered. This appears to give
     * behavior closer to the popular browsers.
     */
    private boolean ignoreSpace;

    /**
     * This flag determines whether or not the Parser will be strict
     * in enforcing SGML compatibility.  If false, it will be lenient
     * with certain common classes of erroneous HTML constructs.
     * Strict or not, in either case an error will be recorded.
     *
     */
    protected boolean strict = false;


    /** Number of \r\n's encountered. */
    private int crlfCount;
    /** Number of \r's encountered. A \r\n will not increment this. */
    private int crCount;
    /** Number of \n's encountered. A \r\n will not increment this. */
    private int lfCount;

    //
    // To correctly identify the start of a tag/comment/text we need two
    // ivars. Two are needed as handleText isn't invoked until the tag
    // after the text has been parsed, that is the parser parses the text,
    // then a tag, then invokes handleText followed by handleStart.
    //
    /** The start position of the current block. Block is overloaded here,
     * it really means the current start position for the current comment,
     * tag, text. Use getBlockStartPosition to access this. */
    private int currentBlockStartPos;
    /** Start position of the last block. */
    private int lastBlockStartPos;

    public WBParser(WBDTD dtd) {
	this.dtd = dtd;
    }


    /**
     * @return the line number of the line currently being parsed
     */
    protected int getCurrentLine() {
	return ln;
    }

    /**
     * Returns the start position of the current block. Block is
     * overloaded here, it really means the current start position for
     * the current comment tag, text, block.... This is provided for
     * subclassers that wish to know the start of the current block when
     * called with one of the handleXXX methods.
     */
    int getBlockStartPosition() {
	return Math.max(0, lastBlockStartPos - 1);
    }

    /**
     * Makes a WBTagElement.
     */
    protected WBTagElement makeTag(WBElement elem, boolean fictional) {
	return new WBTagElement(elem, fictional);
    }

    protected WBTagElement makeTag(WBElement elem) {
	return makeTag(elem, false);
    }

    protected SimpleAttributeSet getAttributes() {
	return attributes;
    }

    protected void flushAttributes() {
	attributes.removeAttributes(attributes);
    }

    /**
     * Called when PCDATA is encountered.
     */
    protected void handleText(char text[]) {
    }

    /**
     * Called when an HTML title tag is encountered.
     */
    protected void handleTitle(char text[]) {
	// default behavior is to call handleText. Subclasses
	// can override if necessary.
	handleText(text);
    }

    /**
     * Called when an HTML comment is encountered.
     */
    protected void handleComment(char text[]) {
    }

    protected void handleEOFInComment() {
	// We've reached EOF.  Our recovery strategy is to
	// see if we have more than one line in the comment;
	// if so, we pretend that the comment was an unterminated
	// single line comment, and reparse the lines after the
	// first line as normal HTML content.

	int commentEndPos = strIndexOf('\n');
	if (commentEndPos >= 0) {
	    handleComment(getChars(0, commentEndPos));
	    try {
		in.close();
		in = new CharArrayReader(getChars(commentEndPos + 1));
		ch = '>';
	    } catch (IOException e) {
		error("ioexception");
	    }

	    resetStrBuffer();
	} else {
	    // no newline, so signal an error
	    error("eof.comment");
	}
    }

    /**
     * Called when an empty tag is encountered.
     */
    protected void handleEmptyTag(WBTagElement tag) throws ChangedCharSetException {
    }

    /**
     * Called when a start tag is encountered.
     */
    protected void handleStartTag(WBTagElement tag) {
    }

    /**
     * Called when an end tag is encountered.
     */
    protected void handleEndTag(WBTagElement tag) {
    }

    /**
     * An error has occurred.
     */
    protected void handleError(int ln, String msg) {
	
	//Thread.dumpStack();
	//System.out.println("**** " + stack);
	System.out.println("line " + ln + ": error: " + msg);
	//System.out.println();
	
    }

    /**
     * Output text.
     */
    void handleText(WBTagElement tag) {
	if (tag.breaksFlow()) {
	    space = false;
            if (!strict) {
                ignoreSpace = true;
            }
	}
	if (textpos == 0) {
	    if ((!space) || (stack == null) || last.breaksFlow() ||
		!stack.advance(dtd.pcdata)) {
		last = tag;
		space = false;
		lastBlockStartPos = currentBlockStartPos;
		return;
	    }
	}
	if (space) {
            if (!ignoreSpace) {
                // enlarge buffer if needed
                if (textpos + 1 > text.length) {
                    char newtext[] = new char[text.length + 200];
                    System.arraycopy(text, 0, newtext, 0, text.length);
                    text = newtext;
                }

                // output pending space
                text[textpos++] = ' ';
                if (!strict) {
                    ignoreSpace = true;
                }
            }
            space = false;
	}
	char newtext[] = new char[textpos];
	System.arraycopy(text, 0, newtext, 0, textpos);
	// Handles cases of bad html where the title tag
	// was getting lost when we did error recovery.
	if (tag.getElement().getName().equals("title")) {
	    handleTitle(newtext);
	} else {
	    handleText(newtext);
	}
	lastBlockStartPos = currentBlockStartPos;
	textpos = 0;
	last = tag;
	space = false;
    }

    /**
     * Invoke the error handler.
     */
    protected void error(String err, String arg1, String arg2,	String arg3) {
        //System.out.println("Error:"+err+" "+arg1+" "+arg2+" "+arg3);
	// big hack, but this should never get used...
	handleError (ln, err + arg1 + arg2 + arg3);
    }

    protected void error(String err, String arg1, String arg2) {
	error(err, arg1, arg2, "?");
    }
    protected void error(String err, String arg1) {
	error(err, arg1, "?", "?");
    }
    protected void error(String err) {
	error(err, "?", "?", "?");
    }


    /**
     * Handle a start tag. The new tag is pushed
     * onto the tag stack. The attribute list is
     * checked for required attributes.
     */
    protected void startTag(WBTagElement tag) throws ChangedCharSetException {
	WBElement elem = tag.getElement();

	// If the tag is an empty tag and texpos != 0
	// this implies that there is text before the
	// start tag that needs to be processed before
	// handling the tag.
	//
	if (!elem.isEmpty() || textpos != 0) {
	    handleText(tag);
	} else {
	    // this variable gets updated in handleText().
	    // Since in this case we do not call handleText()
	    // we need to update it here.
	    //
	    last = tag;
	    // Note that we should really check last.breakFlows before
	    // assuming this should be false.
	    space = false;
	}
	lastBlockStartPos = currentBlockStartPos;

	// check required attributes
	for (AttributeList a = elem.atts ; a != null ; a = a.next) {

	    if ((a.modifier == REQUIRED) && ((attributes.isEmpty()) || (!attributes.isDefined(a.name)))) {
		error("req.att ", a.getName(), elem.getName());
	    }
	}

	if (elem.isEmpty()) {
	    handleEmptyTag(tag);
            /*
	} else if (elem.getName().equals("form")) {
	    handleStartTag(tag);
            */
	} else {
	    recent = elem;
	    stack = new WBTagStack(tag, stack);
	    handleStartTag(tag);
	}
    }

    /**
     * Handle an end tag. The end tag is popped
     * from the tag stack.
     */
    protected void endTag(boolean omitted) {
	handleText(stack.tag);

	if (omitted && !stack.elem.omitEnd()) {
	    error("end.missing", stack.elem.getName());
	} else if (!stack.terminate()) {
	    error("end.unexpected", stack.elem.getName());
	}

	// handle the tag
	handleEndTag(stack.tag);
	stack = stack.next;
	recent = (stack != null) ? stack.elem : null;
    }


    boolean ignoreElement(WBElement elem) {

        String stackElement = stack.elem.getName();
	String elemName = elem.getName();
	/* We ignore all elements that are not valid in the context of
	   a table except <td>, <th> (these we handle in
	   legalElementContext()) and #pcdata.  We also ignore the
	   <font> tag in the context of <ul> and <ol> We additonally
	   ignore the <meta> and the <style> tag if the body tag has
	   been seen. **/
	if ((elemName.equals("html") && seenHtml) ||
	    (elemName.equals("head") && seenHead) ||
	    (elemName.equals("body") && seenBody)) {
	    return true;
	}
	if (elemName.equals("dt") || elemName.equals("dd")) {
	    WBTagStack s = stack;
	    while (s != null && !s.elem.getName().equals("dl")) {
		s = s.next;
	    }
	    if (s == null) {
		return true;
	    }
	}

	if (((stackElement.equals("table")) &&
	     (!elemName.equals("#pcdata")) && (!elemName.equals("input"))) ||
	    ((elemName.equals("font")) &&
	     (stackElement.equals("ul") || stackElement.equals("ol"))) ||
	    (elemName.equals("meta") && stack != null) ||
	    elemName.equals("style") ||
	    (stackElement.equals("table") && elemName.equals("a"))) {
	    return true;
	}
	return false;
    }


    /**
     * Marks the first time a tag has been seen in a document
     */

    protected void markFirstTime(WBElement elem) {
	String elemName = elem.getName();
	if (elemName.equals("html")) {
	    seenHtml = true;
	} else if (elemName.equals("head")) {
	    seenHead = true;
	} else if (elemName.equals("body")) {
            if (buf.length == 1) {
                // Refer to note in definition of buf for details on this.
                char[] newBuf = new char[256];

                newBuf[0] = buf[0];
                buf = newBuf;
            }
	    seenBody = true;
	}
    }

    /**
     * Create a legal content for an element.
     */
    boolean legalElementContext(WBElement elem) throws ChangedCharSetException {

	// System.out.println("-- legalContext -- " + elem);

	// Deal with the empty stack
	if (stack == null) {
	    // System.out.println("-- stack is empty");
	    if (elem != dtd.html) {
		// System.out.println("-- pushing html");
		startTag(makeTag(dtd.html, true));
		return legalElementContext(elem);
	    }
	    return true;
	}

	// Is it allowed in the current context
	if (stack.advance(elem)) {
	    // System.out.println("-- legal context");
	    markFirstTime(elem);
	    return true;
	}
	boolean insertTag = false;

	// The use of all error recovery strategies are contingent
	// on the value of the strict property.
	//
	// These are commonly occuring errors.  if insertTag is true,
	// then we want to adopt an error recovery strategy that
	// involves attempting to insert an additional tag to
	// legalize the context.  The two errors addressed here
	// are:
	// 1) when a <td> or <th> is seen soon after a <table> tag.
	//    In this case we insert a <tr>.
	// 2) when any other tag apart from a <tr> is seen
	//    in the context of a <tr>.  In this case we would
	//    like to add a <td>.  If a <tr> is seen within a
	//    <tr> context, then we will close out the current
	//    <tr>.
	//
	// This insertion strategy is handled later in the method.
	// The reason for checking this now, is that in other cases
	// we would like to apply other error recovery strategies for example
	// ignoring tags.
	//
	// In certain cases it is better to ignore a tag than try to
	// fix the situation.  So the first test is to see if this
	// is what we need to do.
	//
	String stackElemName = stack.elem.getName();
	String elemName = elem.getName();


	if (!strict &&
	    ((stackElemName.equals("table") && elemName.equals("td")) ||
	     (stackElemName.equals("table") && elemName.equals("th")) ||
	     (stackElemName.equals("tr") && !elemName.equals("tr")))){
	     insertTag = true;
	}


	if (!strict && !insertTag && (stack.elem.getName() != elem.getName() ||
				      elem.getName().equals("body"))) {
	    if (skipTag = ignoreElement(elem)) {
	        error("tag.ignore", elem.getName());
		return skipTag;
	    }
	}

	// Check for anything after the start of the table besides tr, td, th
	// or caption, and if those aren't there, insert the <tr> and call
	// legalElementContext again.
	if (!strict && stackElemName.equals("table") &&
	    !elemName.equals("tr") && !elemName.equals("td") &&
	    !elemName.equals("th") && !elemName.equals("caption")) {
	    WBElement e = dtd.getElement("tr");
	    WBTagElement t = makeTag(e, true);
	    legalTagContext(t);
	    startTag(t);
	    error("start.missing", elem.getName());
	    return legalElementContext(elem);
	}

	// They try to find a legal context by checking if the current
	// tag is valid in an enclosing context.  If so
	// close out the tags by outputing end tags and then
	// insert the curent tag.  If the tags that are
	// being closed out do not have an optional end tag
	// specification in the WBDTD then an html error is
	// reported.
	//
	if (!insertTag && stack.terminate() && (!strict || stack.elem.omitEnd())) {
	    for (WBTagStack s = stack.next ; s != null ; s = s.next) {
		if (s.advance(elem)) {
		    while (stack != s) {
			endTag(true);
		    }
		    return true;
		}
		if (!s.terminate() || (strict && !s.elem.omitEnd())) {
		    break;
		}
	    }
	}

	// Check if we know what tag is expected next.
	// If so insert the tag.  Report an error if the
	// tag does not have its start tag spec in the WBDTD as optional.
	//
	WBElement next = stack.first();
	if (next != null && (!strict || next.omitStart()) &&
	   !(next==dtd.head && elem==dtd.pcdata) ) {
	    // System.out.println("-- omitting start tag: " + next);
	    WBTagElement t = makeTag(next, true);
	    legalTagContext(t);
	    startTag(t);
	    if (!next.omitStart()) {
		error("start.missing", elem.getName());
	    }
	    return legalElementContext(elem);
	}


	// Traverse the list of expected elements and determine if adding
	// any of these elements would make for a legal context.
	//

	if (!strict) {
	    WBContentModel content = stack.contentModel();
	    Vector elemVec = new Vector();
	    if (content != null) {
		content.getElements(elemVec);
		for (Enumeration v = elemVec.elements(); v.hasMoreElements();) {
		    WBElement e = (WBElement)v.nextElement();

		    // Ensure that this element has not been included as
		    // part of the exclusions in the WBDTD.
		    //
		    if (stack.excluded(e.getIndex())) {
			continue;
		    }

		    boolean reqAtts = false;

		    for (AttributeList a = e.getAttributes(); a != null ; a = a.next) {
		        if (a.modifier == REQUIRED) {
			    reqAtts = true;
			    break;
		        }
		    }
		    // Ensure that no tag that has required attributes
		    // gets inserted.
	            //
		    if (reqAtts) {
		        continue;
		    }

		    WBContentModel m = e.getContent();
		    if (m != null && m.first(elem)) {
			// System.out.println("-- adding a legal tag: " + e);
			WBTagElement t = makeTag(e, true);
			legalTagContext(t);
			startTag(t);
			error("start.missing", e.getName());
			return legalElementContext(elem);
		    }
		}
	    }
	}

	// Check if the stack can be terminated.  If so add the appropriate
	// end tag.  Report an error if the tag being ended does not have its
	// end tag spec in the WBDTD as optional.
	//
	if (stack.terminate() && (stack.elem != dtd.body) && (!strict || stack.elem.omitEnd())) {
	    // System.out.println("-- omitting end tag: " + stack.elem);
	    if (!stack.elem.omitEnd()) {
		error("end.missing", elem.getName());
	    }

	    endTag(true);
	    return legalElementContext(elem);
	}

	// At this point we know that something is screwed up.
	return false;
    }

    /**
     * Create a legal context for a tag.
     */
    void legalTagContext(WBTagElement tag) throws ChangedCharSetException {
	if (legalElementContext(tag.getElement())) {
	    markFirstTime(tag.getElement());
	    return;
	}

	// Avoid putting a block tag in a flow tag.
	if (tag.breaksFlow() && (stack != null) && !stack.tag.breaksFlow()) {
	    endTag(true);
	    legalTagContext(tag);
	    return;
	}

	// Avoid putting something wierd in the head of the document.
	for (WBTagStack s = stack ; s != null ; s = s.next) {
	    if (s.tag.getElement() == dtd.head) {
		while (stack != s) {
		    endTag(true);
		}
		endTag(true);
		legalTagContext(tag);
		return;
	    }
	}

	// Everything failed
	error("tag.unexpected", tag.getElement().getName());
    }

    /**
     * Error context. Something went wrong, make sure we are in
     * the document's body context
     */
    void errorContext() throws ChangedCharSetException {
	for (; (stack != null) && (stack.tag.getElement() != dtd.body) ; stack = stack.next) {
	    handleEndTag(stack.tag);
	}
	if (stack == null) {
	    legalElementContext(dtd.body);
	    startTag(makeTag(dtd.body, true));
	}
    }

    /**
     * Add a char to the string buffer.
     */
    void addString(int c) {
	if (strpos  == str.length) {
	    char newstr[] = new char[str.length + 128];
	    System.arraycopy(str, 0, newstr, 0, str.length);
	    str = newstr;
	}
	str[strpos++] = (char)c;
    }

    /**
     * Get the string that's been accumulated.
     */
    String getString(int pos) {
	char newStr[] = new char[strpos - pos];
	System.arraycopy(str, pos, newStr, 0, strpos - pos);
	strpos = pos;
	return new String(newStr);
    }

    char[] getChars(int pos) {
	char newStr[] = new char[strpos - pos];
	System.arraycopy(str, pos, newStr, 0, strpos - pos);
	strpos = pos;
	return newStr;
    }

    char[] getChars(int pos, int endPos) {
	char newStr[] = new char[endPos - pos];
	System.arraycopy(str, pos, newStr, 0, endPos - pos);
	// REMIND: it's not clear whether this version should set strpos or not
	// strpos = pos;
	return newStr;
    }

    void resetStrBuffer() {
	strpos = 0;
    }

    int strIndexOf(char target) {
	for (int i = 0; i < strpos; i++) {
	    if (str[i] == target) {
		return i;
	    }
	}

	return -1;
    }

    /**
     * Skip space.
     * [5] 297:5
     */
    void skipSpace() throws IOException {
	while (true) {
	    switch (ch) {
	      case '\n':
		ln++;
		ch = readCh();
		lfCount++;
		break;

	      case '\r':
		ln++;
		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
		break;
	      case ' ':
	      case '\t':
		ch = readCh();
		break;

	      default:
		return;
	    }
	}
    }

    /**
     * Parse identifier. Uppercase characters are folded
     * to lowercase when lower is true. Returns falsed if
     * no identifier is found. [55] 346:17
     */
    boolean parseIdentifier(boolean lower) throws IOException {
        //System.out.println("\n parseIdentifier");        
	switch (ch) {
	  case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
	  case 'G': case 'H': case 'I': case 'J': case 'K': case 'L':
	  case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R':
	  case 'S': case 'T': case 'U': case 'V': case 'W': case 'X':
	  case 'Y': case 'Z':
	    if (lower) {
		ch = 'a' + (ch - 'A');
	    }

	  case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
	  case 'g': case 'h': case 'i': case 'j': case 'k': case 'l':
	  case 'm': case 'n': case 'o': case 'p': case 'q': case 'r':
	  case 's': case 't': case 'u': case 'v': case 'w': case 'x':
	  case 'y': case 'z':
	    break;

	  default:
	    return false;
	}

	while (true) {
	    addString(ch);

	    switch (ch = readCh()) {
	      case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
	      case 'G': case 'H': case 'I': case 'J': case 'K': case 'L':
	      case 'M': case 'N': case 'O': case 'P': case 'Q': case 'R':
	      case 'S': case 'T': case 'U': case 'V': case 'W': case 'X':
	      case 'Y': case 'Z':
		if (lower) {
		    ch = 'a' + (ch - 'A');
		}

	      case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
	      case 'g': case 'h': case 'i': case 'j': case 'k': case 'l':
	      case 'm': case 'n': case 'o': case 'p': case 'q': case 'r':
	      case 's': case 't': case 'u': case 'v': case 'w': case 'x':
	      case 'y': case 'z':

	      case '0': case '1': case '2': case '3': case '4':
	      case '5': case '6': case '7': case '8': case '9':

	      case '.': case '-':

	      case '_': // not officially allowed
		break;

	      default:
		return true;
	    }
	}
    }

    /**
     * Parse an entity reference. [59] 350:17
     */
    private char[] parseEntityReference() throws IOException {
        //System.out.println("\n parseEntityReference");        
	int pos = strpos;

	if ((ch = readCh()) == '#') {
	    int n = 0;
	    ch = readCh();
	    if ((ch >= '0') && (ch <= '9')) {
		while ((ch >= '0') && (ch <= '9')) {
		    n = (n * 10) + ch - '0';
		    ch = readCh();
		}
		switch (ch) {
		  case '\n':
		    ln++;
		    ch = readCh();
		    lfCount++;
		    break;

		  case '\r':
		    ln++;
		    if ((ch = readCh()) == '\n') {
			ch = readCh();
			crlfCount++;
		    }
		    else {
			crCount++;
		    }
		    break;

		  case ';':
		    ch = readCh();
		    break;
		}
		char data[] = {(char)n};
		return data;
	    }
	    addString('#');
	    if (!parseIdentifier(false)) {
		error("ident.expected");
		strpos = pos;
		char data[] = {'&', '#'};
		return data;
	    }
	} else if (!parseIdentifier(false)) {
	    char data[] = {'&'};
	    return data;
	}
	switch (ch) {
	  case '\n':
	    ln++;
	    ch = readCh();
	    lfCount++;
	    break;

	  case '\r':
	    ln++;
	    if ((ch = readCh()) == '\n') {
		ch = readCh();
		crlfCount++;
	    }
	    else {
		crCount++;
	    }
	    break;

	  case ';':
	    ch = readCh();
	    break;
	}

	String nm = getString(pos);
	Entity ent = dtd.getEntity(nm);

	// entities are case sensitive - however if strict
	// is false then we will try to make a match by
	// converting the string to all lowercase.
	//
	if (!strict && (ent == null)) {
	    ent = dtd.getEntity(nm.toLowerCase());
	}
	if ((ent == null) || !ent.isGeneral()) {

	    if (nm.length() == 0) {
		error("invalid.entref", nm);
		return new char[0];
	    }
	    /* given that there is not a match restore the entity reference */
	    String str = "&" + nm;

	    char b[] = new char[str.length()];
	    str.getChars(0, b.length, b, 0);
	    return b;
	}
	return ent.getData();
    }

    /**
     * Parse a comment. [92] 391:7
     */
    void parseComment() throws IOException {
        //System.out.println("\n parseComment");        

	while (true) {
	    int c = ch;
	    switch (c) {
	      case '-':
		  /** Presuming that the start string of a comment "<!--" has
		      already been parsed, the '-' character is valid only as
		      part of a comment termination and further more it must
		      be present in even numbers. Hence if strict is true, we
		      presume the comment has been terminated and return.
		      However if strict is false, then there is no even number
		      requirement and this character can appear anywhere in the
		      comment.  The parser reads on until it sees the following
		      pattern: "-->" or "--!>".
		   **/
		if (!strict && (strpos != 0) && (str[strpos - 1] == '-')) {
		    if ((ch = readCh()) == '>') {
			return;
		    }
		    if (ch == '!') {
			if ((ch = readCh()) == '>') {
			    return;
			} else {
			    /* to account for extra read()'s that happened */
			    addString('-');
			    addString('!');
			    continue;
			}
		    }
		    break;
		}

		if ((ch = readCh()) == '-') {
		    ch = readCh();
		    if (strict || ch == '>') {
			return;
		    }
		    if (ch == '!') {
			if ((ch = readCh()) == '>') {
			    return;
			} else {
			    /* to account for extra read()'s that happened */
			    addString('-');
			    addString('!');
			    continue;
			}
		    }
		    /* to account for the extra read() */
		    addString('-');
		}
		break;

	      case -1:
		  handleEOFInComment();
		  return;

	      case '\n':
		ln++;
		ch = readCh();
		lfCount++;
		break;

	      case '>':
		ch = readCh();
		break;

	      case '\r':
		ln++;
		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
		c = '\n';
		break;
	      default:
		ch = readCh();
		break;
	    }

	    addString(c);
	}
    }

    /**
     * Parse literal content. [46] 343:1 and [47] 344:1
     */
    void parseLiteral(boolean replace) throws IOException {
        //System.out.println("\n parseLiteral");        
	while (true) {
	    int c = ch;
	    switch (c) {
	      case -1:
		error("eof.literal", stack.elem.getName());
		endTag(true);
		return;

	      case '>':
		ch = readCh();
		int i = textpos - (stack.elem.name.length() + 2), j = 0;

		// match end tag
		if ((i >= 0) && (text[i++] == '<') && (text[i] == '/')) {
		    while ((++i < textpos) &&
			   (Character.toLowerCase(text[i]) == stack.elem.name.charAt(j++)));
		    if (i == textpos) {
			textpos -= (stack.elem.name.length() + 2);
			if ((textpos > 0) && (text[textpos-1] == '\n')) {
			    textpos--;
			}
			endTag(false);
			return;
		    }
		}
		break;

	      case '&':
		char data[] = parseEntityReference();
		if (textpos + data.length > text.length) {
		    char newtext[] = new char[Math.max(textpos + data.length + 128, text.length * 2)];
		    System.arraycopy(text, 0, newtext, 0, text.length);
		    text = newtext;
		}
		System.arraycopy(data, 0, text, textpos, data.length);
		textpos += data.length;
		continue;

	      case '\n':
		ln++;
		ch = readCh();
		lfCount++;
		break;

	      case '\r':
		ln++;
		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
                c = '\n';
		break;
	      default:
		ch = readCh();
		break;
	    }

	    // output character
	    if (textpos == text.length) {
		char newtext[] = new char[text.length + 128];
		System.arraycopy(text, 0, newtext, 0, text.length);
		text = newtext;
	    }
	    text[textpos++] = (char)c;
	}
    }

    /**
     * Parse attribute value. [33] 331:1
     */
    String parseAttributeValue(boolean lower) throws IOException {
        //System.out.println("\n parseAttributeValue");        
	int delim = -1;

	// Check for a delimiter
	switch(ch) {
	  case '\'':
	  case '"':
	    delim = ch;
	    ch = readCh();
	    break;
	}

	// Parse the rest of the value
	while (true) {
	    int c = ch;

	    switch (c) {
	      case '\n':
		ln++;
		ch = readCh();
		lfCount++;
		if (delim < 0) {
		    return getString(0);
		}
		break;

	      case '\r':
		ln++;

		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
		if (delim < 0) {
		    return getString(0);
		}
		break;

	      case '\t':
		  if (delim < 0)
		      c = ' ';
	      case ' ':
		ch = readCh();
		if (delim < 0) {
		    return getString(0);
		}
		break;

	      case '>':
	      case '<':
		if (delim < 0) {
		    return getString(0);
		}
		ch = readCh();
		break;

	      case '\'':
	      case '"':
		ch = readCh();
		if (c == delim) {
		    return getString(0);
		} else if (delim == -1) {
		    error("attvalerr");
		    if (strict || ch == ' ') {
			return getString(0);
		    } else {
			continue;
		    }
		}
		break;

	    case '=':
		if (delim < 0) {
		    /* In SGML a construct like <img src=/cgi-bin/foo?x=1>
		       is considered invalid since an = sign can only be contained
		       in an attributes value if the string is quoted.
		       */
		    error("attvalerr");
		    /* If strict is true then we return with the string we have thus far.
		       Otherwise we accept the = sign as part of the attribute's value and
		       process the rest of the img tag. */
		    if (strict) {
			return getString(0);
		    }
		}
		ch = readCh();
		break;

	      case '&':
		if (strict && delim < 0) {
		    ch = readCh();
		    break;
		}

		char data[] = parseEntityReference();
		for (int i = 0 ; i < data.length ; i++) {
		    c = data[i];
		    addString((lower && (c >= 'A') && (c <= 'Z')) ? 'a' + c - 'A' : c);
		}
		continue;

	      case -1:
		return getString(0);

	      default:
		if (lower && (c >= 'A') && (c <= 'Z')) {
		    c = 'a' + c - 'A';
		}
		ch = readCh();
		break;
	    }
	    addString(c);
	}
    }


    /**
     * Parse attribute specification List. [31] 327:17
     */
    void parseAttributeSpecificationList(WBElement elem) throws IOException {
        //System.out.println("\n parseAttributeSpecificationList");        

	while (true) {
	    skipSpace();

	    switch (ch) {
	      case '/':
	      case '>':
	      case '<':
	      case -1:
		return;

	      case '-':
		if ((ch = readCh()) == '-') {
		    ch = readCh();
		    parseComment();
		    strpos = 0;
		} else {
		    error("invalid.tagchar", "-", elem.getName());
		    ch = readCh();
		}
		continue;
	    }

	    AttributeList att = null;
	    String attname = null;
	    String attvalue = null;

	    if (parseIdentifier(true)) {
		attname = getString(0);
		skipSpace();
		if (ch == '=') {
		    ch = readCh();
		    skipSpace();
		    att = elem.getAttribute(attname);
//  Bug ID 4102750
//  Load the NAME of an Attribute Case Sensitive
//  The case of the NAME  must be intact
//  MG 021898
		    attvalue = parseAttributeValue((att != null) && (att.type != CDATA) && (att.type != NOTATION) && (att.type != NAME));
//		    attvalue = parseAttributeValue((att != null) && (att.type != CDATA) && (att.type != NOTATION));
		} else {
		    attvalue = attname;
		    att = elem.getAttributeByValue(attvalue);
		    if (att == null) {
			att = elem.getAttribute(attname);
			if (att != null) {
			    attvalue = att.getValue();
			}
			else {
			    // Make it null so that NULL_ATTRIBUTE_VALUE is
			    // used
			    attvalue = null;
			}
		    }
		}
	    } else if (!strict && ch == ',') { // allows for comma separated attribute-value pairs
		ch = readCh();
		continue;
	    } else if (!strict && ch == '"') { // allows for quoted attributes
		ch = readCh();
		skipSpace();
		if (parseIdentifier(true)) {
		    attname = getString(0);
		    if (ch == '"') {
			ch = readCh();
		    }
		    skipSpace();
		    if (ch == '=') {
			ch = readCh();
			skipSpace();
			att = elem.getAttribute(attname);
			attvalue = parseAttributeValue((att != null) &&
						(att.type != CDATA) &&
						(att.type != NOTATION));
		    } else {
			attvalue = attname;
			att = elem.getAttributeByValue(attvalue);
			if (att == null) {
			    att = elem.getAttribute(attname);
			    if (att != null) {
				attvalue = att.getValue();
			    }
			}
		    }
		} else {
		    char str[] = {(char)ch};
		    error("invalid.tagchar", new String(str), elem.getName());
		    ch = readCh();
		    continue;
		}
	    } else if (!strict && (attributes.isEmpty()) && (ch == '=')) {
		ch = readCh();
		skipSpace();
		attname = elem.getName();
		att = elem.getAttribute(attname);
		attvalue = parseAttributeValue((att != null) &&
					       (att.type != CDATA) &&
					       (att.type != NOTATION));
	    } else if (!strict && (ch == '=')) {
		ch = readCh();
		skipSpace();
		attvalue = parseAttributeValue(true);
		error("attvalerr");
		return;
	    } else {
		char str[] = {(char)ch};
		error("invalid.tagchar", new String(str), elem.getName());
		if (!strict) {
		    ch = readCh();
		    continue;
		} else {
		    return;
		}
	    }

	    if (att != null) {
		attname = att.getName();
	    } else {
		error("invalid.tagatt", attname, elem.getName());
	    }

	    // Check out the value
	    if (attributes.isDefined(attname)) {
		error("multi.tagatt", attname, elem.getName());
	    }
	    if (attvalue == null) {
		attvalue = ((att != null) && (att.value != null)) ? att.value :
		    HTML.NULL_ATTRIBUTE_VALUE;
	    } else if ((att != null) && (att.values != null) && !att.values.contains(attvalue)) {
		error("invalid.tagattval", attname, elem.getName());
	    }
	    HTML.Attribute attkey = HTML.getAttributeKey(attname);
	    if (attkey == null) {
		attributes.addAttribute(attname, attvalue);
	    } else {
		attributes.addAttribute(attkey, attvalue);
	    }
	}
    }

    /**
     * Parses th Document Declaration Type markup declaration.
     * Currently ignores it.
     */
    public String parseDTDMarkup() throws IOException {
        //System.out.println("\n parseDTDMarkup");        

	StringBuffer strBuff = new StringBuffer();
	ch = readCh();
	while(true) {
	    switch (ch) {
	    case '>':
		ch = readCh();
		return strBuff.toString();
	    case -1:
		error("invalid.markup");
		return strBuff.toString();
	    case '\n':
		ln++;
		ch = readCh();
		lfCount++;
		break;
	    case '"':
		ch = readCh();
		break;
	    case '\r':
		ln++;
		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
		break;
	    default:
		strBuff.append((char)(ch & 0xFF));
		ch = readCh();
		break;
	    }
	}
    }

    /**
     * Parse markup declarations.
     * Currently only handles the Document Type Declaration markup.
     * Returns true if it is a markup declaration false otherwise.
     */
    protected boolean parseMarkupDeclarations(StringBuffer strBuff) throws IOException {
        //System.out.println("\n parseMarkupDeclarations");        

	/* Currently handles only the DOCTYPE */
	if ((strBuff.length() == "DOCTYPE".length()) &&
	    (strBuff.toString().toUpperCase().equals("DOCTYPE"))) {
	    parseDTDMarkup();
	    return true;
	}
	return false;
    }

    /**
     * Parse an invalid tag.
     */
    void parseInvalidTag() throws IOException {
        //System.out.println("\n parseInvalidTag");        
	// ignore all data upto the close bracket '>'
	while (true) {
	    skipSpace();
	    switch (ch) {
	      case '>':
	      case -1:
		  ch = readCh();
	        return;
	      case '<':
		  return;
	      default:
		  ch = readCh();

	    }
	}
    }

    /**
     * Parse a start or end tag.
     */
    void parseTag() throws IOException {
        //System.out.println("parseTag");
	WBElement elem = null;
	boolean net = false;
	boolean warned = false;
	boolean unknown = false;

        
	switch (ch = readCh()) {
	  case '!':
	    switch (ch = readCh()) {
	      case '-':
		// Parse comment. [92] 391:7
		while (true) {
		    if (ch == '-') {
			if (!strict || ((ch = readCh()) == '-')) {
			    ch = readCh();
			    if (!strict && ch == '-') {
				ch = readCh();
			    }
			    // send over any text you might see
			    // before parsing and sending the
			    // comment
			    if (textpos != 0) {
				char newtext[] = new char[textpos];
				System.arraycopy(text, 0, newtext, 0, textpos);
				handleText(newtext);
				lastBlockStartPos = currentBlockStartPos;
				textpos = 0;
			    }
			    parseComment();
			    handleComment(getChars(0));
			    continue;
			} else if (!warned) {
			    warned = true;
			    error("invalid.commentchar", "-");
			}
		    }
		    skipSpace();
		    switch (ch) {
		      case '-':
			continue;
		      case '>':
			ch = readCh();
		      case -1:
			return;
		      default:
			ch = readCh();
			if (!warned) {
			    warned = true;
			    error("invalid.commentchar",
				  String.valueOf((char)ch));
			}
			break;
		    }
		}

	      default:
 		// deal with marked sections
		StringBuffer strBuff = new StringBuffer();
		while (true) {
		    strBuff.append((char)ch);
		    if (parseMarkupDeclarations(strBuff)) {
			return;
		    }
		    switch(ch) {
		      case '>':
			ch = readCh();
		      case -1:
			error("invalid.markup");
			return;
		      case '\n':
			ln++;
			ch = readCh();
			lfCount++;
			break;
		      case '\r':
			ln++;
			if ((ch = readCh()) == '\n') {
			    ch = readCh();
			    crlfCount++;
			}
			else {
			    crCount++;
			}
			break;

		      default:
			ch = readCh();
			break;
		    }
		}
	    }

	  case '/':
	    // parse end tag [19] 317:4
	    switch (ch = readCh()) {
	      case '>':
		ch = readCh();
	      case '<':
		// empty end tag. either </> or </<
		if (recent == null) {
		    error("invalid.shortend");
		    return;
		}
		elem = recent;
		break;

	      default:
		if (!parseIdentifier(true)) {
		    error("expected.endtagname");
		    return;
		}
		skipSpace();
		switch (ch) {
		  case '>':
		    ch = readCh();
		  case '<':
		    break;

		  default:
		    error("expected", "'>'");
		    while ((ch != -1) && (ch != '\n') && (ch != '>')) {
			ch = readCh();
		    }
		    if (ch == '>') {
			ch = readCh();
		    }
		    break;
		}
		String elemStr = getString(0);
		if (!dtd.elementExists(elemStr)) {
		    error("end.unrecognized", elemStr);
		    // Ignore RE before end tag
		    if ((textpos > 0) && (text[textpos-1] == '\n')) {
			textpos--;
		    }
		    elem = dtd.getElement("unknown");
		    elem.name = elemStr;
		    unknown = true;
		} else {
		    elem = dtd.getElement(elemStr);
		}
		break;
	    }


	    // If the stack is null, we're seeing end tags without any begin
	    // tags.  Ignore them.

	    if (stack == null) {
		error("end.extra.tag", elem.getName());
		return;
	    }

	    // Ignore RE before end tag
            if ((textpos > 0) && (text[textpos-1] == '\n')) {
		// In a pre tag, if there are blank lines
		// we do not want to remove the newline
		// before the end tag.  Hence this code.
		//
		if (stack.pre) {
		    if ((textpos > 1) && (text[textpos-2] != '\n')) {
			textpos--;
		    }
		} else {
		    textpos--;
		}
	    }

	    // If the end tag is a form, since we did not put it
	    // on the tag stack, there is no corresponding start
	    // start tag to find. Hence do not touch the tag stack.
	    //

            /*
	    if (!strict && elem.getName().equals("form")) {
		if (lastFormSent != null) {
		    handleEndTag(lastFormSent);
		    return;
		} else {
		    // do nothing.
		    return;
		}
	    }
            */

	    if (unknown) {
		// we will not see a corresponding start tag
		// on the the stack.  If we are seeing an
		// end tag, lets send this on as an empty
		// tag with the end tag attribute set to
		// true.
		WBTagElement t = makeTag(elem);
		handleText(t);
		attributes.addAttribute(HTML.Attribute.ENDTAG, "true");
		handleEmptyTag(makeTag(elem));
		unknown = false;
		return;
	    }

	    // find the corresponding start tag

	    // A commonly occuring error appears to be the insertion
	    // of extra end tags in a table.  The intent here is ignore
	    // such extra end tags.
	    //
	    if (!strict) {
		String stackElem = stack.elem.getName();

		if (stackElem.equals("table")) {
		    // If it isnt a valid end tag ignore it and return
		    //
		    if (!elem.getName().equals(stackElem)) {
			error("tag.ignore", elem.getName());
			return;
		    }
		}



		if (stackElem.equals("tr") ||
		    stackElem.equals("td")) {
		    if ((!elem.getName().equals("table")) &&
			(!elem.getName().equals(stackElem))) {
			error("tag.ignore", elem.getName());
			return;
		    }
		}
	    }
	    WBTagStack sp = stack;

	    while ((sp != null) && (elem != sp.elem)) {
		sp = sp.next;
	    }
	    if (sp == null) {
		error("unmatched.endtag", elem.getName());
		return;
	    }

 	    // People put font ending tags in the darndest places.
 	    // Don't close other contexts based on them being between
 	    // a font tag and the corresponding end tag.  Instead,
 	    // ignore the end tag like it doesn't exist and allow the end
	    // of the document to close us out.
	    String elemName = elem.getName();
 	    if (stack != sp &&
		(elemName.equals("font") ||
		 elemName.equals("center"))) {

		// Since closing out a center tag can have real wierd
		// effects on the formatting,  make sure that tags
		// for which omitting an end tag is legimitate
		// get closed out.
		//
		if (elemName.equals("center")) {
		    while(stack.elem.omitEnd() && stack != sp) {
			endTag(true);
		    }
		    if (stack.elem == elem) {
			endTag(false);
		    }
		}
		return;
	    }
	    // People do the same thing with center tags.  In this
	    // case we would like to close off the center tag but
	    // not necessarily all enclosing tags.



	    // end tags
	    while (stack != sp) {
		endTag(true);
	    }

	    endTag(false);
	    return;

	  case -1:
	    error("eof");
	    return;
	}

	// start tag [14] 314:1
	if (!parseIdentifier(true)) {
	    elem = recent;
	    if ((ch != '>') || (elem == null)) {
		error("expected.tagname");
		return;
	    }
	} else {
	    String elemStr = getString(0);

	    if (elemStr.equals("image")) {
		elemStr = new String("img");
	    }

	    /* determine if this element is part of the dtd. */

	    if (!dtd.elementExists(elemStr)) {
		//		parseInvalidTag();
		error("tag.unrecognized ", elemStr);
		elem = dtd.getElement("unknown");
		elem.name = elemStr;
		unknown = true;
	    } else {
		elem = dtd.getElement(elemStr);
	    }
	}

	// Parse attributes
	parseAttributeSpecificationList(elem);

	switch (ch) {
	  case '/':
	    net = true;
            // JEI: se agregaron la dos siguientes linea 
            attributes.addAttribute("_startEndTag_", "true");
            ch = readCh();
	  case '>':
	    ch = readCh();
	  case '<':
	    break;

	  default:
	    error("expected", "'>'");
	    break;
	}

	if (!strict) {
	  if (elem.getName().equals("script")) {
	    error("javascript.unsupported");
	  }
	}

	// ignore RE after start tag
	//
	if (!elem.isEmpty())  {
	    if (ch == '\n') {
		ln++;
		lfCount++;
		ch = readCh();
	    } else if (ch == '\r') {
		ln++;
		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
	    }
	}

	// ensure a legal context for the tag
	WBTagElement tag = makeTag(elem, false);
        
        //System.out.println("Tag:"+tag.getElement());


	/** In dealing with forms, we have decided to treat
	    them as legal in any context.  Also, even though
	    they do have a start and an end tag, we will
	    not put this tag on the stack.  This is to deal
	    several pages in the web oasis that choose to
	    start and end forms in any possible location. **/

        /*
	if (!strict && elem.getName().equals("form")) {
	    if (lastFormSent == null) {
		lastFormSent = tag;
	    } else {
		handleEndTag(lastFormSent);
		lastFormSent = tag;
	    }
	} else {
        */
	    // Smlly, if a tag is unknown, we will apply
	    // no legalTagContext logic to it.
	    //
	    if (!unknown) {
		legalTagContext(tag);

		// If skip tag is true,  this implies that
		// the tag was illegal and that the error
		// recovery strategy adopted is to ignore
		// the tag.
		if (!strict && skipTag) {
		    skipTag = false;
		    return;
		}
	    }
            /*
	}
            */

	startTag(tag);

	if (!elem.isEmpty()) {
	    switch (elem.getType()) {
	      case CDATA:
		parseLiteral(false);
		break;
	      case RCDATA:
		parseLiteral(true);
		break;
	      default:
		if (stack != null) {
		    stack.net = net;
		}
		break;
	    }
	}
        //System.out.println("parseTag END");
    }

    /**
     * Parse Content. [24] 320:1
     */
    void parseContent() throws IOException {
        //System.out.println("parseContent");
	Thread curThread = Thread.currentThread();

	for (;;) {
	    if (curThread.isInterrupted()) {
                curThread.interrupt(); // resignal the interrupt
                break;
            }

	    int c = ch;
            //System.out.println("\n Content:"+(char)c);
	    currentBlockStartPos = currentPosition;
	    switch (c) {
	      case '<':
		parseTag();
		lastBlockStartPos = currentPosition;
		continue;

	      case '/':
		ch = readCh();
                //System.out.println("Content:"+ch);
                //if(ch=='>')ch=readCh();
		if ((stack != null) && stack.net) {
		    // null end tag.
		    endTag(false);
		    continue;
		}
		break;

	      case -1:
		return;

	      case '&':
		if (textpos == 0) {
		    if (!legalElementContext(dtd.pcdata)) {
			error("unexpected.pcdata");
		    }
		    if (last.breaksFlow()) {
			space = false;
		    }
		}
		char data[] = parseEntityReference();
		if (textpos + data.length + 1 > text.length) {
		    char newtext[] = new char[Math.max(textpos + data.length + 128, text.length * 2)];
		    System.arraycopy(text, 0, newtext, 0, text.length);
		    text = newtext;
		}
		if (space) {
		    space = false;
		    text[textpos++] = ' ';
		}
		System.arraycopy(data, 0, text, textpos, data.length);
		textpos += data.length;
		continue;

	      case '\n':
		ln++;
		lfCount++;
		ch = readCh();
		if ((stack != null) && stack.pre) {
		    break;
		}
		if (textpos == 0) {
		    lastBlockStartPos = currentPosition;
		}
                if (!ignoreSpace) {
                    space = true;
                }
		continue;

	      case '\r':
		ln++;
		c = '\n';
		if ((ch = readCh()) == '\n') {
		    ch = readCh();
		    crlfCount++;
		}
		else {
		    crCount++;
		}
		if ((stack != null) && stack.pre) {
		    break;
		}
		if (textpos == 0) {
		    lastBlockStartPos = currentPosition;
		}
                if (!ignoreSpace) {
                    space = true;
                }
		continue;


	      case '\t':
	      case ' ':
		ch = readCh();
		if ((stack != null) && stack.pre) {
		    break;
		}
		if (textpos == 0) {
		    lastBlockStartPos = currentPosition;
		}
                if (!ignoreSpace) {
                    space = true;
                }
		continue;

	      default:
		if (textpos == 0) {
		    if (!legalElementContext(dtd.pcdata)) {
			error("unexpected.pcdata");
		    }
		    if (last.breaksFlow()) {
			space = false;
		    }
		}
		ch = readCh();
		break;
	    }

	    // enlarge buffer if needed
	    if (textpos + 2 > text.length) {
		char newtext[] = new char[text.length + 128];
		System.arraycopy(text, 0, newtext, 0, text.length);
		text = newtext;
	    }

	    // output pending space
	    if (space) {
		if (textpos == 0) {
		    lastBlockStartPos--;
		}
		text[textpos++] = ' ';
		space = false;
	    }
	    text[textpos++] = (char)c;
            ignoreSpace = false;
	}
    }

    /**
     * Returns the end of line string. This will return the end of line
     * string that has been encountered the most, one of \r, \n or \r\n.
     */
    String getEndOfLineString() {
	if (crlfCount >= crCount) {
	    if (lfCount >= crlfCount) {
		return "\n";
	    }
	    else {
		return "\r\n";
	    }
	}
	else {
	    if (crCount > lfCount) {
		return "\r";
	    }
	    else {
		return "\n";
	    }
	}
    }

    /**
     * Parse an HTML stream, given a WBDTD.
     */
    public synchronized void parse(Reader in) throws IOException {
        //System.out.println("parse");        
	this.in = in;

	this.ln = 1;

	seenHtml = false;
	seenHead = false;
	seenBody = false;

	crCount = lfCount = crlfCount = 0;

	try {
	    try {
		ch = readCh();
		text = new char[1024];
		str = new char[128];

		parseContent();
		// NOTE: interruption may have occurred.  Control flows out
		// of here normally.
		while (stack != null) {
		    endTag(true);
		}
	    } finally {
		in.close();
	    }

	} catch (IOException e) {
	    errorContext();
	    error("ioexception");
	    throw e;
	} catch (RuntimeException e) {
	    errorContext();
	    error("exception", e.getClass().getName(), e.getMessage());
	    e.printStackTrace();
            throw e;
	} catch (ThreadDeath e) {
	    errorContext();
	    error("terminated");
	    e.printStackTrace();
	    throw e;
	} finally {
	    for (; stack != null ; stack = stack.next) {
		handleEndTag(stack.tag);
	    }

	    text = null;
	    str = null;
	}

    }


    /*
     * Input cache.  This is much faster than calling down to a synchronized
     * method of BufferedReader for each byte.  Measurements done 5/30/97
     * show that there's no point in having a bigger buffer:  Increasing
     * the buffer to 8192 had no measurable impact for a program discarding
     * one character at a time (reading from an http URL to a local machine).
     * NOTE: If the current encoding is bogus, and we read too much
     * (past the content-type) we may suffer a MalformedInputException. For
     * this reason the initial size is 1 and when the body is encountered the
     * size is adjusted to 256.
     */
    private char buf[] = new char[1];
    private int pos;
    private int len;
    /*
	tracks position relative to the beginning of the
	document.
    */
    private int currentPosition;


    private final int readCh() throws IOException {

	if (pos >= len) {

	    // This loop allows us to ignore interrupts if the flag
	    // says so
	    for (;;) {
		try {
		    len = in.read(buf);
		    break;
		} catch (InterruptedIOException ex) {
		    throw ex;
		}
	    }

	    if (len <= 0) {
		return -1;	// eof
	    }
	    pos = 0;
	}
	++currentPosition;
        //System.out.print(buf[pos]);
	return buf[pos++];
    }


    protected int getCurrentPos() {
	return currentPosition;
    }
}
