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
 * WBTextEditorPane.java
 *
 * Created on 10 de octubre de 2004, 09:49 PM
 */


package applets.htmleditor;

import java.applet.Applet;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.plaf.*;
import java.util.*;
import java.util.regex.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import netscape.javascript.JSObject;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBTextEditorPane extends JTextPane implements CaretListener, KeyListener, MouseListener, MouseMotionListener
{
    private Style STYLE_HTMLTAG;
    private Style STYLE_HTMLATT;
    private Style STYLE_HTMLSTR;
    private Style STYLE_HTMLCMT;
    private Style STYLE_TEXT;
    private Style STYLE_HTMLCHREF;
    
    private Style STYLE_RESOURCE;
    private Style STYLE_RESHTMLTAG;
    private Style STYLE_RESHTMLATT;
    private Style STYLE_RESHTMLSTR;
    
    int oldPos=0;
    
    private boolean undoEnable=true;
    private UndoManager undoMngr=null;
    private UndoAction undoAction;
    private RedoAction redoAction;     
    
    private ArrayList resarr=new ArrayList();    
    
    private Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

    Applet applet=null;

    /** Creates a new instance of WBTextEditorPane */
    public WBTextEditorPane()
    {
        super();
        //setEditorKit(new WBStyledEditorKit());
        setEditorKit(new StyledEditorKit());
        setStyle();
        addKeyListener(this);
        addCaretListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        /* Set up the undo features */
        undoMngr = new UndoManager();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        getDocument().addUndoableEditListener(new CustomUndoableEditListener());   
        
        resarr.add("resource");
        resarr.add("wbobject");
        resarr.add("content");
        resarr.add("topic");
        resarr.add("user");
        resarr.add("template");
        resarr.add("request");
        resarr.add("response");
        resarr.add("include");

        resarr.add("/resource");
        resarr.add("/wbobject");
        resarr.add("/content");
        resarr.add("/topic");
        resarr.add("/user");
        resarr.add("/template");
        resarr.add("/request");        
        resarr.add("/response");   
        resarr.add("/include");
        
        resarr.add("resource/");
        resarr.add("wbobject/");
        resarr.add("content/");
        resarr.add("topic/");
        resarr.add("user/");
        resarr.add("template/");
        resarr.add("request/");
        resarr.add("response/");
        resarr.add("include/");
    }

    public Applet getApplet() {
        return applet;
    }

    public void setApplet(Applet applet) {
        this.applet = applet;
    }
    
    public void setStyle()
    {
        StyledDocument textDoc = (StyledDocument)this.getDocument();
        
        STYLE_HTMLTAG = textDoc.addStyle("STYLE_HTMLTAG", null);
        StyleConstants.setFontFamily(STYLE_HTMLTAG, "Monospaced");
        StyleConstants.setForeground(STYLE_HTMLTAG, Color.BLUE);
        //StyleConstants.setItalic(HTMLTAG, true);
        //StyleConstants.setBold(HTMLTAG, true);
        //StyleConstants.setFontSize(HTMLTAG, 30);

        STYLE_RESOURCE = textDoc.addStyle("STYLE_RESOURCE", null);
        StyleConstants.setFontFamily(STYLE_RESOURCE, "Monospaced");
        //StyleConstants.setItalic(STYLE_RESOURCE, true);
        StyleConstants.setBold(STYLE_RESOURCE, true);
        
        STYLE_HTMLATT = textDoc.addStyle("STYLE_HTMLATT", null);
        StyleConstants.setFontFamily(STYLE_HTMLATT, "Monospaced");
        StyleConstants.setForeground(STYLE_HTMLATT, new Color(0,124,0));
        
        STYLE_HTMLSTR = textDoc.addStyle("STYLE_HTMLSTR", null);
        StyleConstants.setFontFamily(STYLE_HTMLSTR, "Monospaced");
        StyleConstants.setForeground(STYLE_HTMLSTR, new Color(153,0,107));
        
        STYLE_HTMLCMT = textDoc.addStyle("STYLE_HTMLCMT", null);
        StyleConstants.setFontFamily(STYLE_HTMLCMT, "Monospaced");
        StyleConstants.setItalic(STYLE_HTMLCMT, true);
        StyleConstants.setForeground(STYLE_HTMLCMT, new Color(128,128,128));        
        
        STYLE_TEXT = textDoc.addStyle("STYLE_TEXT", null);
        StyleConstants.setFontFamily(STYLE_TEXT, "Monospaced");
        StyleConstants.setForeground(STYLE_TEXT, Color.BLACK);        

        STYLE_HTMLCHREF = textDoc.addStyle("STYLE_HTMLCHREF", null);
        StyleConstants.setFontFamily(STYLE_HTMLCHREF, "Monospaced");
        StyleConstants.setForeground(STYLE_HTMLCHREF, new Color(178,0,0));
        
    }

    public void caretUpdate(CaretEvent e)
    {

    }
    
    public void updateSyntax()
    {
        //System.out.println("updateSyntax"+" pos:"+getCaretPosition());
        try
        {
            StyledDocument sDoc = (StyledDocument) getDocument();
            int cPos = getCaretPosition();
            if(cPos<oldPos)oldPos=cPos;
            int length = sDoc.getLength();
            String text = sDoc.getText(0, length);
            //int lineStart = text.substring(0, cPos).lastIndexOf("\n") + 1;
            int lineStart = text.substring(0, oldPos).lastIndexOf(">") + 1;
            //int lineEnd = text.indexOf("\n", cPos);
            int lineEnd = text.indexOf("<", cPos);
            if(lineEnd < 0)
            {
                lineEnd = length;
            }
            setMarks(sDoc, lineStart, lineEnd - lineStart);
        }
        catch(Exception ex)
        {}        
    }

  /**
   */
  public void setText(String t) {
    //System.out.println("setText...");  
    undoEnable=false;
    this.setLogicalStyle(STYLE_TEXT);            
    removeCaretListener(this);
    super.setText(t);
    StyledDocument sDoc = (StyledDocument) getDocument();
    setMarks(sDoc, 0, sDoc.getLength());
    setCaretPosition(0);
    purgeUndos();
    undoEnable=true;
  }    
    
    /**
     * Apply Syntax Colorizing to all HTML tags found in the given
     * area of the given document
     *
     * @param doc  the document to apply syntax highlighting to
     * @param offset  the position inside the given document to start to apply syntax highlighting to
     * @param len  the number of characters to apply syntax highlighting to
     */
    public void setMarks(StyledDocument sDoc, int offset, int len)
    {
        SwingUtilities.invokeLater(new SyntaxColorizing(this, sDoc, offset, len));
    }
    
    public void syntaxColorizing()
    {
        StyledDocument sDoc = (StyledDocument) getDocument();
        setMarks(sDoc, 0, sDoc.getLength());
    }
    
    /**
     * overridden from JEditorPane
     * to suppress line wraps
     *
     * @see setSize
     */
    @Override
    public boolean getScrollableTracksViewportWidth()
    {
        return false;
    }
    
    /**
     * overridden from JEditorPane
     * to suppress line wraps
     *
     * @see getScrollableTracksViewportWidth
     */
    public void setSize(Dimension d)
    {
        if(d.width < getParent().getSize().width)
        {
            d.width = getParent().getSize().width;
        }
        super.setSize(d);
    }
    
    /**
     * display a wait cursor for lengthy operations
     */
    private void cursor()
    {
        Component gp = getRootPane().getGlassPane();
        if(!gp.isVisible())
        {
            gp.setCursor(waitCursor);
            gp.setVisible(true);
        }
        else
        {
            gp.setVisible(false);
        }
    }
    
    /**
     * Getter for property cpos.
     * @return Value of property cpos.
     *
    public int getCaretPos()
    {
        return cpos;
    }
    
    /**
     * Setter for property cpos.
     * @param cpos New value of property cpos.
     *
    public void setCaretPos(int cpos)
    {
        this.cpos = cpos;
    }
    */
    
    public void findResourceTag()
    {
        try
        {
            int pos=getCaretPosition();
            Document doc=getDocument();
            int len=doc.getLength();
            String txt=doc.getText(pos,len-pos).toLowerCase();
            int y=0;
            int x=0;
            boolean find=false;
            //System.out.println("x:"+x);
            while(!find && (x=(txt.indexOf('<',x)+1))>0)
            {
                if(x>len)break;
                //System.out.println("x:"+x+" find:"+find);
                Iterator it=resarr.iterator();
                while(!find && it.hasNext())
                {
                    String tag=(String)it.next();
                    if(txt.startsWith(tag,x))
                    {
                        find=true;
                    }
                }

            }
            if(find)
            {
                //setCaretPosition(x);
                int z=txt.indexOf('>',x);
                if(z>0)
                    select(pos+x-1, pos+z+1);
                else
                    setCaretPosition(pos+x);
                //setStatus("");
            }else
            {
                //setStatus("End of the Document..");
                setCaretPosition(0);
            }
        }catch(Exception ex){System.out.println(ex);}        
    }

    public void keyPressed(KeyEvent e)
    {
        oldPos=getCaretPosition();
        //System.out.println("keyPressed:"+e.getKeyCode()+" pos:"+getCaretPosition());
        /*
        System.out.println("keyTyped:"+e);
        System.out.println("getKeyChar:"+(int)e.getKeyChar()+" "+e.getKeyChar());
        System.out.println("getKeyCode:"+e.getKeyCode());
        System.out.println("getModifiers:"+e.getModifiers());
        System.out.println("getModifiersEx:"+e.getModifiersEx());
        System.out.println("getKeyText:"+e.getKeyText(e.getKeyCode()));        
        *
        if(e.getKeyCode()==e.VK_F4)
        {
            findResourceTag();
        }
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
         **/
        //updateSyntax();
    }

    public void mouseClicked(MouseEvent e) {
        //System.out.println("mouseClicked:"+e);
    }

    public void mousePressed(MouseEvent e) {
        //System.out.println("mousePressed:"+e);
    }

    public void mouseReleased(MouseEvent e) {
        //System.out.println("mouseReleased:"+e);
    }

    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered:"+e.getModifiersEx()+" "+e.BUTTON1);
        try
        {
            if(e.getModifiersEx()>0)
            {
                System.out.println("cur:"+JSObject.getWindow(applet).eval("dojo.dnd.manager().source.tree"));
                System.out.println("cur:"+JSObject.getWindow(applet).eval("dijit.getEnclosingWidget(dojo.dnd.manager().nodes[0]).item.id"));
                JSObject.getWindow(applet).eval("dojo.dnd.manager().source.onDndCancel()");
                JSObject.getWindow(applet).eval("dojo.dnd.manager().source.containerState=''");

                //JSObject.getWindow(applet).eval("dojo.dnd.manager().events=[]");
                //JSObject.getWindow(applet).eval("dojo.dnd.manager().avatar.destroy()");
                //JSObject.getWindow(applet).eval("dojo.dnd.manager().avatar=null");
                //JSObject.getWindow(applet).eval("dojo.dnd.manager().source=null");
                //JSObject.getWindow(applet).eval("dojo.dnd.manager().target=null");
                //JSObject.getWindow(applet).eval("dojo.publish(\"/dnd/cancel\")");
                //JSObject.getWindow(applet).eval("dojo.dnd.manager().stopDrag()");
                //JSObject.getWindow(applet).eval("dojo.dnd.manager().nodes=[]");
            }
            //applet.getAppletContext().showDocument(new URL("javascript:alert(\"hola\")"));
        }catch(Exception ex){ex.printStackTrace();}

    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("mouseExited:"+e);
    }

    public void mouseDragged(MouseEvent e) {
        //System.out.println("mouseDragged:"+e);
    }

    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouseMoved:"+e);
    }

    /**
     * StyleUpdater does the actual syntax highlighting work
     * and can be used in SwingUtilities.invokeLater()
     */
    private class SyntaxColorizing implements Runnable
    {
        
        /** the document to apply syntax highlighting to */
        private StyledDocument doc;
        
        /** the position inside the given document to start to apply syntax highlighting to */
        private int offset;
        
        /** the number of characters to apply syntax highlighting to */
        private int len;
        
        /** the SyntaxPane this StyleUpdater works on */
        private WBTextEditorPane textEditor;
        
        
        /**
         * construct a <code>StyleUpdater</code>
         *
         * @param sp  the SyntaxPane this StyleUpdater works on
         * @param doc  the document to apply syntax highlighting to
         * @param offset  the position inside the given document to start to apply syntax highlighting to
         * @param len  the number of characters to apply syntax highlighting to
         */
        public SyntaxColorizing(WBTextEditorPane textEditor, StyledDocument doc, int offset, int len)
        {
            this.doc = doc;
            this.offset = offset;
            this.len = len;
            this.textEditor = textEditor;
        }
        
        /**
         * apply snytax highlighting
         */
        public void run()
        {
            if(len<0)return;
            
            boolean tag=false;
            boolean att=false;
            boolean val=false;
            boolean chref=false;
            boolean comm=false;
            boolean res=false;
            
            int ini=0;
            
            String txt="";
            cursor();
            textEditor.removeCaretListener(textEditor);            
            try
            {
                txt=doc.getText(offset,len);
                
                //System.out.println("style:"+offset+" "+(doc.getCharacterElement(offset+1).getAttributes()==STYLE_HTMLCMT));
                
                doc.setCharacterAttributes(offset,len, STYLE_TEXT,true);                
                
                for(int x=0;x<txt.length();x++)
                {
                    char ch=txt.charAt(x);
                    
                    if(ch=='<' && !comm)
                    {
                        ini=x;
                        if(txt.length()>x+3 && txt.charAt(x+1)=='!' && txt.charAt(x+2)=='-' && txt.charAt(x+3)=='-')
                        {
                            comm=true;
                        }
                        else
                        {
                            tag=true;
                        }
                    }else if(ch=='>')
                    {
                        if(tag)
                        {
                            if(att)
                            {
                                doc.setCharacterAttributes(offset+ini,x-ini, STYLE_HTMLATT,true);
                                if(res)doc.setCharacterAttributes(offset+ini,x-ini, STYLE_RESOURCE,false);
                                doc.setCharacterAttributes(offset+x,1, STYLE_HTMLTAG,true);
                                if(res)doc.setCharacterAttributes(offset+x,1, STYLE_RESOURCE,false);
                            }else if(val)
                            {
                                doc.setCharacterAttributes(offset+ini,x-ini, STYLE_HTMLSTR,true);
                                if(res)doc.setCharacterAttributes(offset+ini,x-ini, STYLE_RESOURCE,false);
                                doc.setCharacterAttributes(offset+x,1, STYLE_HTMLTAG,true);
                                if(res)doc.setCharacterAttributes(offset+x,1, STYLE_RESOURCE,false);
                            }else
                            {
                                //System.out.println("tagName:"+txt.substring(offset+ini+1,offset+ini+x-ini));
                                if(resarr.contains(txt.substring(ini+1,x).toLowerCase()))
                                {
                                    res=true;
                                }                                
                                doc.setCharacterAttributes(offset+ini,x-ini+1, STYLE_HTMLTAG,true);
                                if(res)doc.setCharacterAttributes(offset+ini,x-ini+1, STYLE_RESOURCE,false);
                            }
                            ini=x;
                            tag=false;
                            att=false;
                            val=false;
                            res=false;
                        }else if(comm && txt.charAt(x-1)=='-' && txt.charAt(x-2)=='-')
                        {
                            doc.setCharacterAttributes(offset+ini,x-ini+1, STYLE_HTMLCMT,true);
                            ini=x;
                            comm=false;
                        }
                    }else if(!comm && ch=='=')
                    {
                        if(tag && att)
                        {
                            doc.setCharacterAttributes(offset+ini,x-ini+1, STYLE_HTMLATT,true);
                            if(res)doc.setCharacterAttributes(offset+ini,x-ini+1, STYLE_RESOURCE,false);
                            ini=x+1;
                            att=false;
                            val=true;
                        }
                    }else if(!comm && ch==' ')
                    {
                        if(tag && !att && !val)
                        {
                            //System.out.println("tagName:"+txt.substring(offset+ini+1,offset+ini+x-ini));
                            if(resarr.contains(txt.substring(ini+1,x).toLowerCase()))
                            {
                                res=true;
                                //System.out.println("tagName:"+txt.substring(offset+ini+1,offset+ini+x-ini));
                            }
                            att=true;
                            doc.setCharacterAttributes(offset+ini,x-ini, STYLE_HTMLTAG,true);
                            if(res)doc.setCharacterAttributes(offset+ini,x-ini, STYLE_RESOURCE,false);
                            ini=x;
                        }else if(tag && att)
                        {
                            doc.setCharacterAttributes(offset+ini,x-ini, STYLE_HTMLATT,true);
                            if(res)doc.setCharacterAttributes(offset+ini,x-ini, STYLE_RESOURCE,false);
                            ini=x;
                            att=false;
                        }else if(tag && val)
                        {
                            doc.setCharacterAttributes(offset+ini,x-ini, STYLE_HTMLSTR,true);
                            if(res)doc.setCharacterAttributes(offset+ini,x-ini, STYLE_RESOURCE,false);
                            ini=x;
                            val=false;
                            att=true;
                        }
                    }else if(!comm && ch=='&')
                    {
                        ini=x;
                        chref=true;
                        //System.out.println("&");
                    }else if(!comm && ch==';')
                    {
                        if(chref)
                        {
                            doc.setCharacterAttributes(offset+ini,x-ini+1, STYLE_HTMLCHREF,true);
                            ini=x;
                            chref=false;
                            //System.out.println("ini:"+ini+" x:"+x);
                        }
                        //System.out.println(";");
                    }
                }
            }catch(Exception e)
            {e.printStackTrace(System.out);}
            textEditor.addCaretListener(textEditor);
            cursor();
        }
    }
    
    public void purgeUndos()
    {
            //System.out.println("purgeUndos...");
            if(undoMngr != null)
            {
                    undoMngr.discardAllEdits();
                    undoAction.updateUndoState();
                    redoAction.updateRedoState();
            }
    }
    
    public boolean canUndo()
    {
        return undoMngr.canUndo();
    }
    
    public boolean canRedo()
    {
        return undoMngr.canRedo();
    }
    
    public void undo()
    {
        if(canUndo())
            undoAction.actionPerformed(null);
    }

    public void redo()
    {
        if(canRedo())
            redoAction.actionPerformed(null);
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
                        //System.out.println("updateUndoState:"+Action.NAME+" "+undoMngr.getUndoPresentationName());
                        setEnabled(true);
                        putValue(Action.NAME, undoMngr.getUndoPresentationName());
                        updateSyntax();
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
               
                    //System.out.println("undoableEditHappened:"+uee.getEdit());
                    //System.out.println("canRedo:"+uee.getEdit().canRedo());
                    //System.out.println("canUndo:"+uee.getEdit().canUndo());
                    //System.out.println("getPresentationName:"+uee.getEdit().getPresentationName());
                    //System.out.println("isSignificant:"+uee.getEdit().isSignificant());
                    //System.out.println("getSource:"+uee.getEdit().getClass());
                
                    if(undoEnable && uee.getEdit() instanceof AbstractDocument.DefaultDocumentEvent)
                    {
                        AbstractDocument.DefaultDocumentEvent evt=(AbstractDocument.DefaultDocumentEvent)uee.getEdit();
                        if(evt.getType()==DocumentEvent.EventType.INSERT || evt.getType()==DocumentEvent.EventType.REMOVE)
                        {
                            //System.out.println("getPresentationName:"+uee.getEdit().getPresentationName());
                            undoMngr.addEdit(evt);
                            undoAction.updateUndoState();
                            redoAction.updateRedoState();
                        }
                    }
            }
    }       

}
