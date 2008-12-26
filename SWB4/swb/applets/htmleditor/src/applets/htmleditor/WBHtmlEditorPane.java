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
 * WBHtmlEditorPane.java
 *
 * Created on 14 de noviembre de 2004, 01:59 PM
 */

package applets.htmleditor;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.text.*;
import javax.swing.plaf.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.text.html.HTMLDocument;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBHtmlEditorPane  extends JTextPane implements CaretListener, ActionListener, KeyListener
{
    WBHTMLEditoKit editorKit=null;
    private UndoManager undoMngr=null;
    private UndoAction undoAction;
    private RedoAction redoAction;    
    //private boolean undoEnable=true;
    private boolean change=false;
    
    /** Creates a new instance of WBHtmlEditorPane */
    public WBHtmlEditorPane()
    {
        super();
        editorKit=new WBHTMLEditoKit();
        setEditorKit(editorKit);
        editorKit.setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));
        addKeyListener(this);
        addCaretListener(this);
        /* Set up the undo features */
        undoMngr = new UndoManager();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        getDocument().addUndoableEditListener(new CustomUndoableEditListener());        
    }
    
    public HTMLDocument getHTMLDocument()
    {
        return (HTMLDocument)getDocument();
    }
    
    public boolean isChange()
    {
        return undoMngr.canUndo()||change;
    }
    
    public void setChange(boolean change)
    {
        this.change=change;
    }
    
    public void caretUpdate(CaretEvent e)
    {
        System.out.println("caretUpdate:"+e);
    }
    
    public void setInitText(String txt)
    {
        setText(txt);
        change=false;
    }
    
    public void setText(String txt)
    {
        removeCaretListener(this);
        Document doc = getDocument();
        EditorKit kit = getEditorKit();
        try
        {
            //undoEnable=false;
            doc.remove(0, doc.getLength());
            if (!(txt == null || txt.equals("")))
            {
                Reader r = new StringReader(txt);
                //undoEnable=true;
                kit.read(r, doc, 0);
            }
        } catch (BadLocationException e)
        {
            throw new RuntimeException(e.getMessage());
        } catch (ChangedCharSetException e1)
        {
            String charSetSpec = e1.getCharSetSpec();
            if (e1.keyEqualsCharSet())
            {
                putClientProperty("charset", charSetSpec);
            } else
            {
                setCharsetFromContentTypeParameters(charSetSpec);
            }
            try
            {
                //undoEnable=false;
                doc.remove(0, doc.getLength());
            } catch (BadLocationException e){}
            try
            {
                doc.putProperty("IgnoreCharsetDirective", Boolean.valueOf(true));
                Reader r = new StringReader(txt);
                //undoEnable=true;
                kit.read(r, doc, 0);
            }catch (BadLocationException e)
            {
                throw new RuntimeException(e.getMessage());
            }catch (IOException e)
            {
                throw new RuntimeException(e.getMessage());
            }
        }catch (IOException e)
        {
            throw new RuntimeException(e.getMessage());
        } 
        //undoEnable=true;
        purgeUndos();
        change=true;
    }   
    
    /**
     * Fetches a stream for the given URL, which is about to 
     * be loaded by the <code>setPage</code> method.  By
     * default, this simply opens the URL and returns the
     * stream.  This can be reimplemented to do useful things
     * like fetch the stream from a cache, monitor the progress
     * of the stream, etc.
     * <p>
     * This method is expected to have the the side effect of
     * establishing the content type, and therefore setting the
     * appropriate <code>EditorKit</code> to use for loading the stream.
     * <p>
     * If this the stream was an http connection, redirects
     * will be followed and the resulting URL will be set as
     * the <code>Document.StreamDescriptionProperty</code> so that relative
     * URL's can be properly resolved.
     *
     * @param page  the URL of the page
     */
    protected InputStream getStreamAndSetURL(URL page) throws IOException {
	URLConnection conn = page.openConnection();
	if (conn instanceof HttpURLConnection) {
	    HttpURLConnection hconn = (HttpURLConnection) conn;
	    hconn.setInstanceFollowRedirects(false);
	    int response = hconn.getResponseCode();
	    boolean redirect = (response >= 300 && response <= 399);

	    /*
	     * In the case of a redirect, we want to actually change the URL
	     * that was input to the new, redirected URL
	     */
	    if (redirect) {
		String loc = conn.getHeaderField("Location");
		if (loc.startsWith("http", 0)) {
		    page = new URL(loc);
		} else {
		    page = new URL(page, loc);
		}
		return getStream(page);
	    }
	}
	String type = conn.getContentType();
	if (type != null) {
	    setContentType(type);
	    getDocument().putProperty("content-type", type);
	}
	getDocument().putProperty(Document.StreamDescriptionProperty, page);
	String enc = conn.getContentEncoding();
	if (enc != null) {
	    getDocument().putProperty("content-encoding", enc);
	}
	InputStream in = conn.getInputStream();
	return in;
    }
    
    /**
     * This method gets the charset information specified as part
     * of the content type in the http header information.
     */
    private void setCharsetFromContentTypeParameters(String paramlist) {
	String charset = null;
	try {
	    // paramlist is handed to us with a leading ';', strip it.
	    int semi = paramlist.indexOf(';');
	    if (semi > -1 && semi < paramlist.length()-1) {
		paramlist = paramlist.substring(semi + 1);
	    }

	    if (paramlist.length() > 0) {
		// parse the paramlist into attr-value pairs & get the
		// charset pair's value
		HeaderParser hdrParser = new HeaderParser(paramlist);
		charset = hdrParser.findValue("charset");
		if (charset != null) {
		    putClientProperty("charset", charset);
		}
	    }
	}
	catch (IndexOutOfBoundsException e) {
	    // malformed parameter list, use charset we have
	}
	catch (NullPointerException e) {
	    // malformed parameter list, use charset we have
	}
	catch (Exception e) {
	    // malformed parameter list, use charset we have; but complain
	    System.err.println("JEditorPane.getCharsetFromContentTypeParameters failed on: " + paramlist);
	    e.printStackTrace();
	}
    }        
    
    public void actionPerformed(ActionEvent e)
    {
    }
       
    public void keyPressed(KeyEvent e)
    {
        /*
        System.out.println("keyTyped:"+e);
        System.out.println("getKeyChar:"+(int)e.getKeyChar()+" "+e.getKeyChar());
        System.out.println("getKeyCode:"+e.getKeyCode());
        System.out.println("getModifiers:"+e.getModifiers());
        System.out.println("getModifiersEx:"+e.getModifiersEx());
        System.out.println("getKeyText:"+e.getKeyText(e.getKeyCode()));   
         */     
    }
    
    public void keyReleased(KeyEvent e)
    {
    }
    
    public void keyTyped(KeyEvent e)
    {
        /*
        if(e.getKeyChar()==26 && e.getModifiers()==2)
        {
            undo();
        }else if(e.getKeyChar()==25 && e.getModifiers()==2)
        {
            redo();
        }
        */
    }
    
    public void purgeUndos()
    {
            if(undoMngr != null)
            {
                    undoMngr.discardAllEdits();
                    undoAction.updateUndoState();
                    redoAction.updateRedoState();
            }
    }    
    
    public void purgeLastUndo()
    {
        if(undoMngr != null)
        {
            if(undoMngr.canUndo())
            {
                undoMngr.discardAllEdits();
                undoAction.updateUndoState();
                redoAction.updateRedoState();
            }
        }        
    }
    
    public void undo()
    {
        if(undoMngr.canUndo())
        {
            undoAction.actionPerformed(null);
        }
    }

    public void redo()
    {
        if(undoMngr.canRedo())
        {
            redoAction.actionPerformed(null);
        }
    }    

    /** Class for implementing Undo as an autonomous action
      */
    class UndoAction extends AbstractAction
    {
            public UndoAction()
            {
                    super("Undo");
                    setEnabled(false);
            }

            public void actionPerformed(ActionEvent e)
            {
                    //System.out.println("UndoAction:"+e);
                    try
                    {
                            undoMngr.undo();
                    }
                    catch(CannotUndoException ex)
                    {
                            ex.printStackTrace();
                    }
                    updateUndoState();
                    redoAction.updateRedoState();
            }

            protected void updateUndoState()
            {
                    if(undoMngr.canUndo())
                    {
                            setEnabled(true);
                            putValue(Action.NAME, undoMngr.getUndoPresentationName());
                    }
                    else
                    {
                            setEnabled(false);
                            putValue(Action.NAME, "Undo");
                    }
            }
    }

    /** Class for implementing Redo as an autonomous action
      */
    class RedoAction extends AbstractAction
    {
            public RedoAction()
            {
                    super("Redo");
                    setEnabled(false);
            }

            public void actionPerformed(ActionEvent e)
            {
                    //System.out.println("RedoAction:"+e);
                    try
                    {
                            undoMngr.redo();
                    }
                    catch(CannotUndoException ex)
                    {
                            ex.printStackTrace();
                    }
                    updateRedoState();
                    undoAction.updateUndoState();
            }

            protected void updateRedoState()
            {
                    if(undoMngr.canRedo())
                    {
                            setEnabled(true);
                            putValue(Action.NAME, undoMngr.getRedoPresentationName());
                    }
                    else
                    {
                            setEnabled(false);
                            putValue(Action.NAME, "Redo");
                    }
            }
    }    
    
    /** Class for implementing the Undo listener to handle the Undo and Redo actions
      */
    class CustomUndoableEditListener implements UndoableEditListener
    {
            public void undoableEditHappened(UndoableEditEvent uee)
            {
                //System.out.println("undoableEditHappened:"+undoEnable+"-"+uee.getEdit());
                //if(undoEnable)
                {
                    undoMngr.addEdit(uee.getEdit());
                    undoAction.updateUndoState();
                    redoAction.updateRedoState();
                }
            }
    }        
    
}
