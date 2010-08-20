/**
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
*
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
* del SemanticWebBuilder 4.0.
*
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
* de la misma.
*
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
* dirección electrónica:
*  http://www.semanticwebbuilder.org
**/ 
 


/*
 * HtmlEditor.java
 *
 * Created on 20 de septiembre de 2004, 06:02 PM
 */

package applets.htmleditor;

import applets.ftp.ftp;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class TemplateEditor extends javax.swing.JApplet
{
    WBHtmlEditorPane htmlEditor=null;
    WBTextEditorPane textEditor=null;
    WBHTMLEditoKit editorKit=null;
    
    Hashtable actions=null;
    
    private final String backgroundParam = "background";
    private final String foregroundParam = "foreground";
    private final String backgroundSelectionParam = "backgroundSelection";
    private final String foregroundSelectionParam = "foregroundSelection";
    private final String startviewParam = "startview";
    private final String tmParam = "tm";
    private final String idParam = "id";
    private final String tpParam = "tp";
    private final String verParam = "ver";
    private final String typeParam = "type";
    private final String canSaveParam = "canSave";
    
    
    private static final String PRM_JSESS="jsess";
    private static final String PRM_DOCUMENT="document";
    private static final String PRM_FILENAME="filename";
    private String document=null;
    private String filename="template.html";
    private String workpath=null;
    private String jsess=null;                    //session del usuario
    private URL upurl=null;
    private URL downurl=null;
    private URL gateway=null;
    private boolean canSave=true;
    
    private String tmValue=null;
    private String idValue=null;
    private String tpValue=null;
    private String verValue=null;
    private String typeValue=null;
    
    private String oldTab="text";
    private boolean htmlError=false;
    
    //find
    private String findStr=null;
    private boolean findMCase=false;
    private boolean findMWhole=false;
    
    
    private Locale locale=new Locale("es");
    
    // System Clipboard Settings
    private java.awt.datatransfer.Clipboard clipboard;
    
    public static File curDir=null;

    /** Initializes the applet HtmlEditor */
//    @Override
//    public void init()
//    {
//        Thread t=new Thread()
//        {
//            @Override
//            public void run()
//            {
//                init2();
//            }
//        };
//        t.start();
//    }

    
    /** Initializes the applet HtmlEditor */
    @Override
    public void init()
    {
        System.out.println("Applet Template Editor initialized...");
        jsess=this.getParameter(PRM_JSESS);
        filename=this.getParameter(PRM_FILENAME);
        document=this.getParameter(PRM_DOCUMENT);
        typeValue = getParameter(typeParam);
        
        if(getParameter(canSaveParam)!=null)
        {
            if(getParameter(canSaveParam).equals("false"))canSave=false;
        }

        if(document.lastIndexOf('/')>-1)
        {
            workpath=document.substring(0,document.lastIndexOf('/')+1);
        }else
        {
            workpath="";
        }
        //System.out.println("workpath:"+workpath);
        try
        {
            upurl=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),getParameter("upload"));
            downurl=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),getParameter("download"));
            gateway=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),getParameter("gateway"));
        }catch(Exception e)
        {}
        initComponents();
        textEditor.setApplet(this);
        
        mnuOpenURL.setVisible(false);
        mnuGoto.setVisible(false);
        
        if(!typeValue.equalsIgnoreCase("Template"))
        {
            jSeparator7.setVisible(false);
            mnuInsertResource.setVisible(false);
            mnuInsertContent.setVisible(false);
            jButton18.setVisible(false);
            jButton19.setVisible(false);
        }
        
        if(!canSave)
        {
            jButton3.setVisible(false);
            jButton3.setEnabled(false);
            jSeparator10.setVisible(false);
            mnuSave.setEnabled(false);
            mnuSave.setVisible(false);
        }
        
        checkEditButton();
    }
    
    private void usePageParams()
    {
        final String defaultBackground = "979fc3";
        final String defaultForeground = "000000";
        final String defaultBackgroundSelection = "666699";
        final String defaultForegroundSelection = "ffffff";
        String backgroundValue;
        String foregroundValue;
        String backgroundSelectionValue;
        String foregroundSelectionValue;
        
        backgroundValue = getParameter(backgroundParam);
        foregroundValue = getParameter(foregroundParam);
        backgroundSelectionValue = getParameter(backgroundSelectionParam);
        foregroundSelectionValue = getParameter(foregroundSelectionParam);
        
        if(getParameter("locale")!=null)locale=new Locale(getParameter("locale"));
        
        tmValue = getParameter(tmParam);
        idValue = getParameter(idParam);
        tpValue = getParameter(tpParam);
        verValue = getParameter(verParam);
        
        String startview=getParameter(startviewParam);
        
        if ((backgroundValue == null) || (foregroundValue == null))
        {
            /**
             * There was something wrong with the HTML host tags.
             * Generate default values.
             */
            backgroundValue = defaultBackground;
            foregroundValue = defaultForeground;
            backgroundSelectionValue = defaultBackgroundSelection;
            foregroundSelectionValue = defaultForegroundSelection;
        }
        
        // /**
        // * Set the applet's string label, background color, and
        // * foreground colors.
        // */
        this.setBackground(stringToColor(backgroundValue));
        this.setForeground(stringToColor(foregroundValue));
        
        /*
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(false);
        this.jButton5.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton1.setEnabled(false);
        this.jButton2.setEnabled(false);
         */
        
        //cursores
        //curdrag = getToolkit().createCustomCursor(getImage(getCodeBase(), "images/drag.gif"), new Point( 16, 8 ), "drop");
        //curdrag2 = getToolkit().createCustomCursor(getImage(getCodeBase(), "images/drag3.gif"), new Point( 15, 15 ), "drop2");
        
        
        try
        {
            URL url=new URL(this.getCodeBase(),document);
            String txt=readInputStream(htmlEditor.getStreamAndSetURL(url));
            //System.out.println(txt);
            textEditor.setText(txt.replaceAll(workpath,""));
            
            try
            {
                htmlEditor.setInitText(getHTML(textEditor.getText()));
                if("html".equals(startview))
                {
                    oldTab="html";
                    //if(htmlEditor.isEditable()==true)
                    //jTabbedPane1.setSelectedIndex(0);
                }else
                {
                    jTabbedPane1.setSelectedIndex(1);
                }
                htmlError=false;
            }catch(Exception e)
            {
                htmlError=true;
                htmlEditor.setEditable(false);
                JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("errShowingHTMLView")+", "+e.getMessage(),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
            }
            //si es editable
            {
                htmlEditor.setEditable(true);
            }
            setActions();
            editorKit=(WBHTMLEditoKit)htmlEditor.getEditorKit();
            
/*
            //********** parser
            HTMLEditorKit.Parser parser = htmlkit.getParser();
            HTMLEditorKit.ParserCallback callback = new WBParserCallback(new OutputStreamWriter(System.out));
            InputStream in = url.openStream();
            InputStreamReader r = new InputStreamReader(in);
            parser.parse(r, callback, true);
            //*********
 */
        }catch(Exception e)
        {System.out.println(e);}
        
        // Determine if system clipboard is available
        SecurityManager secManager = System.getSecurityManager();
        if(secManager != null)
        {
            try
            {
                secManager.checkSystemClipboardAccess();
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            }
            catch (SecurityException se)
            {
                clipboard = null;
            }
        }
        
    }
    
    public void setActions()
    {
        actions = new Hashtable();
        Action[] actionsArray = htmlEditor.getActions();
        for(int i = 0; i < actionsArray.length; i++)
        {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
            //System.out.println("Action:"+a.getValue(Action.NAME));
        }
/*
Action:set-read-only
Action:selection-down
Action:selection-begin-line
Action:activate-link-action
Action:next-link-action
Action:selection-page-right
Action:InsertOrderedListItem
Action:selection-page-down
Action:insert-content
Action:selection-previous-word
Action:selection-page-up
Action:toggle-componentOrientation
Action:selection-end-word
Action:insert-break
Action:caret-end-word
Action:InsertTable
Action:page-up
Action:beep
Action:InsertUnorderedList
Action:selection-page-left
Action:InsertUnorderedListItem
Action:selection-begin-word
Action:delete-previous
Action:caret-begin-line
Action:font-underline
Action:InsertHR
Action:InsertOrderedList
Action:selection-forward
Action:caret-forward
Action:default-typed
Action:cut-to-clipboard
Action:previous-link-action
Action:caret-end-paragraph
Action:selection-up
Action:caret-begin
Action:copy-to-clipboard
Action:InsertTableDataCell
Action:caret-up
Action:selection-end
Action:caret-next-word
Action:caret-down
Action:selection-next-word
Action:InsertPre
Action:delete-next
Action:selection-backward
Action:selection-end-line
Action:caret-begin-paragraph
Action:set-writable
Action:selection-begin
Action:page-down
Action:InsertTableRow
Action:caret-end
Action:caret-backward
Action:caret-end-line
Action:unselect
Action:paste-from-clipboard
Action:insert-tab
Action:dump-model
Action:selection-end-paragraph
Action:selection-begin-paragraph
Action:caret-begin-word
Action:caret-previous-word
 */
        //Edit
        //if(clipboard != null)
        {
            // System Clipboard versions of menu commands
            //mnuCut.setActionCommand("textcut");
            //mnuCopy.setActionCommand("textcopy");
            //mnuPaste.setActionCommand("textpaste");
        }
        //else
        {
            // DefaultEditorKit versions of menu commands
            mnuCut.setAction(new DefaultEditorKit.CutAction());
            mnuCopy.setAction(new DefaultEditorKit.CopyAction());
            mnuPaste.setAction(new DefaultEditorKit.PasteAction());
        }
        
        //mnuUndo.setAction(undoAction);
        //mnuRedo.setAction(redoAction);
        
        mnuSelectAll.setAction((Action)actions.get("select-all"));
        mnuSelectParagraph.setAction((Action)actions.get("select-paragraph"));
        mnuSelectLine.setAction((Action)actions.get("select-line"));
        mnuSelectWord.setAction((Action)actions.get("select-word"));
        
        //Font
        mnuFontBold.setAction((Action)actions.get("font-bold"));
        //butFontBold.setAction((Action)actions.get("font-bold"));
        mnuFontItalic.setAction((Action)actions.get("font-italic"));
        //butFontItalic.setAction((Action)actions.get("font-italic"));
        mnuFontUnderline.setAction((Action)actions.get("font-underline"));
        //butFontUnderline.setAction((Action)actions.get("font-underline"));
        
        mnuFont48.setAction((Action)actions.get("font-size-48"));
        mnuFont36.setAction((Action)actions.get("font-size-36"));
        mnuFont24.setAction((Action)actions.get("font-size-24"));
        mnuFont18.setAction((Action)actions.get("font-size-18"));
        mnuFont16.setAction((Action)actions.get("font-size-16"));
        mnuFont14.setAction((Action)actions.get("font-size-14"));
        mnuFont12.setAction((Action)actions.get("font-size-12"));
        mnuFont10.setAction((Action)actions.get("font-size-10"));
        mnuFont8.setAction((Action)actions.get("font-size-8"));
        
        mnuFontSerif.setAction((Action)actions.get("font-family-Serif"));
        mnuFontSansSerif.setAction((Action)actions.get("font-family-SansSerif"));
        mnuFontSerif.setAction((Action)actions.get("font-family-Serif"));
        mnuFontMonospaced.setAction((Action)actions.get("font-family-Monospaced"));
        
        //Format
        mnuFormatALeft.setAction((Action)actions.get("left-justify"));
        //butFormatALeft.setAction((Action)actions.get("left-justify"));
        mnuFormatACenter.setAction((Action)actions.get("center-justify"));
        //butFormatACenter.setAction((Action)actions.get("center-justify"));
        mnuFormatARight.setAction((Action)actions.get("right-justify"));
        //butFormatARight.setAction((Action)actions.get("right-justify"));
        //mnuFormatAJustify.setAction(new StyledEditorKit.AlignmentAction("AlignJustified", StyleConstants.ALIGN_JUSTIFIED));
        
        //Insert
        //mnuInsertTable.setAction((Action)actions.get("InsertTable"));
        
        mnuDelete.setAction((Action)actions.get("delete-next"));
        
        //jButton6.setAction((Action)actions.get("set-read-only"));
    }
    
    public boolean validateFile(File file)
    {
        String name=file.getName();
        for(int x=0;x<name.length();x++)
        {
            char ch=name.charAt(x);
            if(!((ch>='a' && ch<='z')
               ||(ch>='A' && ch<='Z')
               ||(ch>='0' && ch<='9')
               ||ch=='_'
               ||ch=='.'
               ||ch=='-'
                ))
            {
                return false;
            }
             
        }
        //System.out.println(name);
        return true;
    }
    
    public boolean openFile()
    {
        if(JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("msgConfirm"),"WebBuilder",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE)==2)return false;
        JFileChooser fc = new JFileChooser();
        fc.setLocale(locale);
        fc.setLocation(100,100);
        
        fc.setCurrentDirectory(curDir);
        int returnVal;
        boolean validate=true;
        do
        {
            returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                validate=validateFile(file);
                if(!validate)
                {
                    JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("validateFile"),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
                }
            }
        }while(returnVal == JFileChooser.APPROVE_OPTION && !validate);
        curDir = fc.getCurrentDirectory();
        
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            try
            {
                //System.out.println("Save File"+file.getPath());
                //FileInputStream fin=new FileInputStream(file);
                String str=loadFile(file);
                filename=file.getName();
                String ret=sendHTML(str,filename,false,true);
                String pt=file.getAbsolutePath().replace('\\','/');
                int x=pt.lastIndexOf('/');
                if(x>-1)pt=pt.substring(0,x+1);
                DragDrop dd=new DragDrop(new JFrame(),true,locale);
                String html=ret.substring(ret.indexOf('|')+1);
                String files=ret.substring(0,ret.indexOf('|'));
                dd.addHTMLFile(filename,html);
                StringTokenizer st=new StringTokenizer(files,";");
                while(st.hasMoreTokens())
                {
                    String sfile=st.nextToken();
                    try
                    {
                        File attach = new File(pt+sfile);                        
                        if(attach.getName().endsWith(".css") && attach.exists())
                        {                            
                            String cssbody=loadFile(attach);
                            String retcss=sendHTML(cssbody,attach.getName(),false,true);
                            if(retcss!=null && !retcss.trim().equals(""))
                            {
                                retcss=retcss.replace("|", ";");
                                if(retcss.endsWith(";"))
                                {
                                    retcss=retcss.substring(0, retcss.length()-1);
                                }
                                String ptcss=attach.getParentFile().getAbsolutePath().replace('\\','/');
                                if(!ptcss.endsWith("/"))
                                {
                                    ptcss+="/";
                                }
                                dd.addFiles(retcss,ptcss);
                            }
                        }
                    }
                    catch(Exception e){System.out.println(e);}
                 }
                dd.addFiles(files,pt);
                dd.setGateway(gateway);
                dd.setUpload(upurl);
                dd.setId(idValue);
                dd.setTopicMap(tmValue);
                dd.setVersion(verValue);
                dd.setType(typeValue);
                //dd.setDSize(300,100);
                //dd.show();
                dd.setVisible(true);

                if(dd.getReturnValue())
                {
                    sendHTML(html,filename,true,false);
                    if(textEditor.isShowing())
                    {
                        textEditor.setText(html);
                    }else
                    {
                        textEditor.setText(html);
                        htmlEditor.setInitText(getHTML(textEditor.getText()));
                    }
                }
            }catch(Exception e)
            {e.printStackTrace();}
            return true;
        } else
        {
            //debug("Save command cancelled by user");
            return false;
        }
    }
    private boolean isUTF8(File file)
    {
        int c3=-61;
        byte[] buffer=new byte[8192];
        FileInputStream fin=null;
        try
        {
                fin=new FileInputStream(file);
                int read=fin.read(buffer);
                while(read!=-1)
                {
                    for(int i=0;i<read;i++)
                    {
                        if(buffer[i]==c3)
                        {
                            return true;
                        }
                    }
                    read=fin.read(buffer);
                }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(fin!=null)
            {
                try
                {
                    fin.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    private String loadFile(File file)
    {
        boolean isUTF8=isUTF8(file);
        StringBuilder sb=new StringBuilder();
        try
        {            
            FileReader reader=new FileReader(file);            
            BufferedReader br=new BufferedReader(reader);
            String line=br.readLine();
            while(line!=null)
            {
                if(isUTF8)
                {
                    line=new String(line.getBytes(reader.getEncoding()),"utf-8");
                }                
                sb.append(line);
                sb.append("\r\n");
                line=br.readLine();
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void insertLink()
    {
        String selText = htmlEditor.getSelectedText();
        int textLength = -1;
        if(selText != null)
        {
            textLength = selText.length();
        }
        
        if(selText == null || textLength < 1)
        {
            //info msg debe seleccionar un texto
            JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("selectText"),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int caretOffset = htmlEditor.getSelectionStart();
        int internalTextLength = selText.length();
        String currentAnchor = "";
        
        SimpleAttributeSet sasText = null;
        for(int i = caretOffset; i < caretOffset + internalTextLength; i++)
        {
            htmlEditor.select(i, i + 1);
            sasText = new SimpleAttributeSet(htmlEditor.getCharacterAttributes());
            
            Enumeration attribEntries1 = sasText.getAttributeNames();
            while(attribEntries1.hasMoreElements() && currentAnchor.equals(""))
            {
                Object entryKey   = attribEntries1.nextElement();
                Object entryValue = sasText.getAttribute(entryKey);
                //System.out.println("entryKey:"+entryKey);
                //System.out.println("entryValue:"+entryValue);
                if(entryKey.toString().equals(HTML.Tag.A.toString()))
                {
                    if(entryValue instanceof SimpleAttributeSet)
                    {
                        Enumeration subAttributes = ((SimpleAttributeSet)entryValue).getAttributeNames();
                        while(subAttributes.hasMoreElements() && currentAnchor.equals(""))
                        {
                            Object subKey = subAttributes.nextElement();
                            //System.out.println("subKey:"+subKey);
                            if(subKey.toString().toLowerCase().equals("href"))
                            {
                                currentAnchor = ((SimpleAttributeSet)entryValue).getAttribute(subKey).toString();
                                break;
                            }
                        }
                    }
                }
            }
            if(!currentAnchor.equals(""))
            { break; }
        }
        htmlEditor.select(caretOffset, caretOffset + internalTextLength);
        currentAnchor=JOptionPane.showInputDialog(this,"URL",currentAnchor);//"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
        //System.out.println("link:"+currentAnchor);
        
        if (currentAnchor != null)
        {
            SimpleAttributeSet sasTag = new SimpleAttributeSet();
            SimpleAttributeSet sasAttr = new SimpleAttributeSet();
            
            sasAttr.addAttribute("href", currentAnchor);
            sasTag.addAttribute(HTML.Tag.A, sasAttr);
            
            htmlEditor.setCharacterAttributes(sasTag, false);
            htmlEditor.setText(htmlEditor.getText());

//                htmlEditor.replaceSelection(selText);
//                htmlEditor.getHTMLDocument().setCharacterAttributes(caretOffset, internalTextLength, sasTag, false);            
//                htmlEditor.updateUI();
        }
        htmlEditor.select(caretOffset, caretOffset + internalTextLength);
        htmlEditor.requestFocus();
    }
    
    public void insertImage()
    {
        WBDocumentFiles files=new WBDocumentFiles(new javax.swing.JFrame(), true,locale);
        files.setGateway(gateway);
        files.setUpload(upurl);
        files.setTopicMap(tmValue);
        files.setId(idValue);
        files.setVersion(verValue);
        files.setType(typeValue);
        files.init();
        files.setVisible(true);
        
        ImagePreview img=files.getSelectedImage();
        if(img!=null)
        {
            String simg="<img src=\""+img.getFilePath()+"\" width=\""+img.getImageWidth()+"\" height=\""+img.getImageHeight()+"\">";
            if(oldTab.equals("text"))
            {
                try
                {
                    textEditor.getDocument().insertString(textEditor.getCaret().getDot(), simg,null);
                }catch(Exception e)
                {e.printStackTrace();}
            }else
            {
                System.out.println("simg:"+simg);
                try
                {
                    HTMLDocument doc=(HTMLDocument)htmlEditor.getDocument();
                    doc.insertBeforeStart(doc.getCharacterElement(htmlEditor.getSelectionEnd()),simg);
                }catch(Exception e)
                {e.printStackTrace();}
            }
        }
        
        files.setVisible(false);
        files.dispose();
    }
    
    public void insertHTML(String html)
    {
        if(oldTab.equals("text"))
        {
            try
            {
                textEditor.getDocument().insertString(textEditor.getCaret().getDot(), html,null);
                //textEditor.updateSyntax();
            }catch(Exception e)
            {e.printStackTrace();}
        }else
        {
            try
            {
                HTMLDocument doc=(HTMLDocument)htmlEditor.getDocument();
                //doc.insertBeforeStart(doc.getCharacterElement(htmlEditor.getSelectionEnd()),html);
                //doc.insertString(htmlEditor.getCaretPosition(), html, htmlEditor.getInputAttributes());
                editorKit.insertHTML(doc, htmlEditor.getCaretPosition(), html,  0, 0, null);
            }catch(Exception e)
            {e.printStackTrace();}
        }
    }
    
    public void insertCustomHTML(String html)
    {
        if(oldTab.equals("text"))
        {
            insertHTML(html);
        }else
        {
            try
            {
                HTMLDocument doc=(HTMLDocument)htmlEditor.getDocument();
                int pos=htmlEditor.getCaretPosition();
                doc.insertString(htmlEditor.getCaretPosition(), "wb_rep_tag", htmlEditor.getInputAttributes());
                String txt=replaceBodyHTML(textEditor.getText(),getBodyHTML(htmlEditor.getText()));
                htmlEditor.setText(txt.replaceFirst("wb_rep_tag", html));
                htmlEditor.setCaretPosition(pos);
            }catch(Exception e)
            {e.printStackTrace();}
        }
    }
    
    
    public void insertResource()
    {
        Frame parent = new Frame();
        InsertResource ins=new InsertResource(parent,true);
        ins.setGateway(gateway);
        ins.setLocale(locale);
        ins.setTopicMap(tmValue);
        ins.setJSession(jsess);
        //ins.setTag(getSelectedText());
        //ins.setEditor(this);
        //ins.show();
        ins.setVisible(true);
        if(ins.getResultValue()==2)
        {
            insertCustomHTML(ins.getTag());
            //System.out.println("OK");
        }
    }
    
    public void insertTable()
    {
        Frame parent = new Frame();
        InsertTable ins=new InsertTable(parent,true);
        ins.setLocale(locale);
        //ins.setTag(getSelectedText());
        //ins.setEditor(this);
        //ins.show();
        ins.setVisible(true);
        if(ins.getResultValue()==2)
        {
            insertCustomHTML(ins.getTag().getHtml()+"<p style=\"margin-top: 0\">&#160;</p>");
            //System.out.println("OK");
        }
        
    }
    
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JEditorPane htmlEditor = new WBHtmlEditorPane();
        this.htmlEditor=(WBHtmlEditorPane)htmlEditor;
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        javax.swing.JTextPane textEditor = new WBTextEditorPane();
        this.textEditor=(WBTextEditorPane)textEditor;
        jToolBar1 = new javax.swing.JToolBar();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonAddFiles = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        butFontBold = new javax.swing.JButton();
        butFontItalic = new javax.swing.JButton();
        butFontUnderline = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        butFormatALeft = new javax.swing.JButton();
        butFormatACenter = new javax.swing.JButton();
        butFormatARight = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuNew = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        mnuOpenFile = new javax.swing.JMenuItem();
        mnuOpenURL = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JSeparator();
        mnuSave = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mnuUndo = new javax.swing.JMenuItem();
        mnuRedo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        mnuCut = new javax.swing.JMenuItem();
        mnuCopy = new javax.swing.JMenuItem();
        mnuPaste = new javax.swing.JMenuItem();
        mnuDelete = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        mnuFindRes = new javax.swing.JMenuItem();
        mnuFind = new javax.swing.JMenuItem();
        mnuFindNext = new javax.swing.JMenuItem();
        mnuReplace = new javax.swing.JMenuItem();
        mnuGoto = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        mnuSelectAll = new javax.swing.JMenuItem();
        mnuSelectParagraph = new javax.swing.JMenuItem();
        mnuSelectLine = new javax.swing.JMenuItem();
        mnuSelectWord = new javax.swing.JMenuItem();
        mnuFont = new javax.swing.JMenu();
        mnuFontBold = new javax.swing.JMenuItem();
        mnuFontItalic = new javax.swing.JMenuItem();
        mnuFontUnderline = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        mnuSize = new javax.swing.JMenu();
        mnuFont48 = new javax.swing.JMenuItem();
        mnuFont36 = new javax.swing.JMenuItem();
        mnuFont24 = new javax.swing.JMenuItem();
        mnuFont18 = new javax.swing.JMenuItem();
        mnuFont16 = new javax.swing.JMenuItem();
        mnuFont14 = new javax.swing.JMenuItem();
        mnuFont12 = new javax.swing.JMenuItem();
        mnuFont10 = new javax.swing.JMenuItem();
        mnuFont8 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        mnuFontSerif = new javax.swing.JMenuItem();
        mnuFontSansSerif = new javax.swing.JMenuItem();
        mnuFontMonospaced = new javax.swing.JMenuItem();
        mnuFormat = new javax.swing.JMenu();
        mnuFormatALeft = new javax.swing.JMenuItem();
        mnuFormatACenter = new javax.swing.JMenuItem();
        mnuFormatARight = new javax.swing.JMenuItem();
        mnuInsert = new javax.swing.JMenu();
        mnuInsertLink = new javax.swing.JMenuItem();
        mnuInsertHR = new javax.swing.JMenuItem();
        mnuInsertBR = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JSeparator();
        mnuInsertTable = new javax.swing.JMenuItem();
        mnuInsertImage = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        mnuInsertResource = new javax.swing.JMenuItem();
        mnuInsertContent = new javax.swing.JMenuItem();

        jTabbedPane1.setBackground(new java.awt.Color(225, 235, 251));
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        htmlEditor.setContentType("text/html");
        htmlEditor.setEditable(false);
        jScrollPane1.setViewportView(htmlEditor);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale); // NOI18N
        jTabbedPane1.addTab(bundle.getString("html"), jPanel1); // NOI18N

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(textEditor);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("text"), jPanel2); // NOI18N

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        usePageParams();
        jToolBar1.setBackground(new java.awt.Color(225, 235, 251));
        jToolBar1.setAlignmentY(0.5F);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/nuevo.gif"))); // NOI18N
        jButton5.setToolTipText(bundle.getString("new")); // NOI18N
        jButton5.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton5.setBorderPainted(false);
        jButton5.setFocusable(false);
        jButton5.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton5.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton5.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton5.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/abrir.gif"))); // NOI18N
        jButton6.setToolTipText(bundle.getString("openFromFile")); // NOI18N
        jButton6.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton6.setBorderPainted(false);
        jButton6.setFocusable(false);
        jButton6.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton6.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton6.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton6.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/guardar.gif"))); // NOI18N
        jButton3.setToolTipText(bundle.getString("save")); // NOI18N
        jButton3.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jButton3.setBorderPainted(false);
        jButton3.setFocusCycleRoot(true);
        jButton3.setFocusable(false);
        jButton3.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton3.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton3.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton3.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton3.setSelected(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/editar.gif"))); // NOI18N
        jButton1.setToolTipText(bundle.getString("edit")); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
        jButton1.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton1.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButtonAddFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/f_general.gif"))); // NOI18N
        jButtonAddFiles.setToolTipText(bundle.getString("add_files")); // NOI18N
        jButtonAddFiles.setBorderPainted(false);
        jButtonAddFiles.setFocusable(false);
        jButtonAddFiles.setMaximumSize(new java.awt.Dimension(32, 32));
        jButtonAddFiles.setMinimumSize(new java.awt.Dimension(25, 25));
        jButtonAddFiles.setPreferredSize(new java.awt.Dimension(25, 25));
        jButtonAddFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddFilesActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonAddFiles);

        jToolBar1.add(jPanel4);

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/cortar.gif"))); // NOI18N
        jButton15.setToolTipText(bundle.getString("cut")); // NOI18N
        jButton15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton15.setBorderPainted(false);
        jButton15.setFocusable(false);
        jButton15.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton15.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton15.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton15.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton15);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/copiar.gif"))); // NOI18N
        jButton13.setToolTipText(bundle.getString("copy")); // NOI18N
        jButton13.setBorderPainted(false);
        jButton13.setFocusable(false);
        jButton13.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton13.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton13.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton13);

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/pegar.gif"))); // NOI18N
        jButton14.setToolTipText(bundle.getString("paste")); // NOI18N
        jButton14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton14.setBorderPainted(false);
        jButton14.setFocusable(false);
        jButton14.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton14.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton14.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton14.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton14);

        jToolBar1.add(jPanel9);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Serif", "SansSerif", "Monospaced" }));
        jComboBox1.setToolTipText("Font");
        jComboBox1.setFocusable(false);
        jComboBox1.setMaximumSize(new java.awt.Dimension(100, 25));
        jComboBox1.setMinimumSize(new java.awt.Dimension(100, 25));
        jComboBox1.setPreferredSize(new java.awt.Dimension(100, 25));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8", "10", "12", "14", "16", "18", "24", "36", "48" }));
        jComboBox2.setSelectedIndex(3);
        jComboBox2.setToolTipText("Size");
        jComboBox2.setFocusable(false);
        jComboBox2.setMaximumSize(new java.awt.Dimension(50, 24));
        jComboBox2.setMinimumSize(new java.awt.Dimension(50, 24));
        jComboBox2.setPreferredSize(new java.awt.Dimension(50, 24));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel6.add(jComboBox2);

        butFontBold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/bold.gif"))); // NOI18N
        butFontBold.setToolTipText(bundle.getString("bold")); // NOI18N
        butFontBold.setBorderPainted(false);
        butFontBold.setFocusable(false);
        butFontBold.setMaximumSize(new java.awt.Dimension(32, 32));
        butFontBold.setMinimumSize(new java.awt.Dimension(25, 25));
        butFontBold.setPreferredSize(new java.awt.Dimension(25, 25));
        butFontBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFontBoldActionPerformed(evt);
            }
        });
        jPanel6.add(butFontBold);

        butFontItalic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/italic.gif"))); // NOI18N
        butFontItalic.setToolTipText(bundle.getString("italic")); // NOI18N
        butFontItalic.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        butFontItalic.setBorderPainted(false);
        butFontItalic.setFocusable(false);
        butFontItalic.setMargin(new java.awt.Insets(5, 5, 5, 5));
        butFontItalic.setMaximumSize(new java.awt.Dimension(32, 32));
        butFontItalic.setMinimumSize(new java.awt.Dimension(25, 25));
        butFontItalic.setPreferredSize(new java.awt.Dimension(25, 25));
        butFontItalic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFontItalicActionPerformed(evt);
            }
        });
        jPanel6.add(butFontItalic);

        butFontUnderline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/uline.gif"))); // NOI18N
        butFontUnderline.setToolTipText(bundle.getString("underline")); // NOI18N
        butFontUnderline.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        butFontUnderline.setBorderPainted(false);
        butFontUnderline.setFocusable(false);
        butFontUnderline.setMargin(new java.awt.Insets(5, 5, 5, 5));
        butFontUnderline.setMaximumSize(new java.awt.Dimension(32, 32));
        butFontUnderline.setMinimumSize(new java.awt.Dimension(25, 25));
        butFontUnderline.setPreferredSize(new java.awt.Dimension(25, 25));
        butFontUnderline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFontUnderlineActionPerformed(evt);
            }
        });
        jPanel6.add(butFontUnderline);

        jToolBar1.add(jPanel6);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        butFormatALeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/algnLft.gif"))); // NOI18N
        butFormatALeft.setToolTipText(bundle.getString("alignLeft")); // NOI18N
        butFormatALeft.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        butFormatALeft.setBorderPainted(false);
        butFormatALeft.setFocusable(false);
        butFormatALeft.setMargin(new java.awt.Insets(5, 5, 5, 5));
        butFormatALeft.setMaximumSize(new java.awt.Dimension(32, 32));
        butFormatALeft.setMinimumSize(new java.awt.Dimension(25, 25));
        butFormatALeft.setPreferredSize(new java.awt.Dimension(25, 25));
        butFormatALeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFormatALeftActionPerformed(evt);
            }
        });
        jPanel8.add(butFormatALeft);

        butFormatACenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/algnCtr.gif"))); // NOI18N
        butFormatACenter.setToolTipText(bundle.getString("alignCenter")); // NOI18N
        butFormatACenter.setBorderPainted(false);
        butFormatACenter.setFocusable(false);
        butFormatACenter.setMaximumSize(new java.awt.Dimension(32, 32));
        butFormatACenter.setMinimumSize(new java.awt.Dimension(25, 25));
        butFormatACenter.setPreferredSize(new java.awt.Dimension(25, 25));
        butFormatACenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFormatACenterActionPerformed(evt);
            }
        });
        jPanel8.add(butFormatACenter);

        butFormatARight.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/algnRt.gif"))); // NOI18N
        butFormatARight.setToolTipText(bundle.getString("alignRight")); // NOI18N
        butFormatARight.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        butFormatARight.setBorderPainted(false);
        butFormatARight.setFocusable(false);
        butFormatARight.setMargin(new java.awt.Insets(5, 5, 5, 5));
        butFormatARight.setMaximumSize(new java.awt.Dimension(32, 32));
        butFormatARight.setMinimumSize(new java.awt.Dimension(25, 25));
        butFormatARight.setPreferredSize(new java.awt.Dimension(25, 25));
        butFormatARight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFormatARightActionPerformed(evt);
            }
        });
        jPanel8.add(butFormatARight);

        jToolBar1.add(jPanel8);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/link.gif"))); // NOI18N
        jButton8.setToolTipText(bundle.getString("link")); // NOI18N
        jButton8.setBorderPainted(false);
        jButton8.setFocusable(false);
        jButton8.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton8.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton8.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton8);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/imagen.gif"))); // NOI18N
        jButton9.setToolTipText(bundle.getString("image")); // NOI18N
        jButton9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton9.setBorderPainted(false);
        jButton9.setFocusable(false);
        jButton9.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton9.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton9.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton9.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton9);

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/table.gif"))); // NOI18N
        jButton17.setToolTipText(bundle.getString("table")); // NOI18N
        jButton17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton17.setBorderPainted(false);
        jButton17.setFocusable(false);
        jButton17.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton17.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton17.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton17.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton17);

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/recurso.gif"))); // NOI18N
        jButton18.setToolTipText(bundle.getString("resource")); // NOI18N
        jButton18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton18.setBorderPainted(false);
        jButton18.setFocusable(false);
        jButton18.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton18.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton18.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton18.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton18);

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/recurso_2.gif"))); // NOI18N
        jButton19.setToolTipText(bundle.getString("content")); // NOI18N
        jButton19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton19.setBorderPainted(false);
        jButton19.setFocusable(false);
        jButton19.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jButton19.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton19.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton19.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton19);

        jToolBar1.add(jPanel7);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/syntax.gif"))); // NOI18N
        jButton2.setToolTipText(bundle.getString("syntaxColorizing")); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setFocusable(false);
        jButton2.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton2.setMinimumSize(new java.awt.Dimension(25, 25));
        jButton2.setPreferredSize(new java.awt.Dimension(25, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jToolBar1.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(225, 235, 251));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        jToolBar1.add(jPanel3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jToolBar2.setBackground(new java.awt.Color(225, 235, 251));
        jToolBar2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar2.setFloatable(false);
        jToolBar2.setPreferredSize(new java.awt.Dimension(4, 20));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jToolBar2.add(jLabel1);

        getContentPane().add(jToolBar2, java.awt.BorderLayout.SOUTH);

        jMenuBar2.setBackground(new java.awt.Color(225, 235, 251));

        mnuFile.setText(bundle.getString("file")); // NOI18N

        mnuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuNew.setText(bundle.getString("new")); // NOI18N
        mnuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewActionPerformed(evt);
            }
        });
        mnuFile.add(mnuNew);
        mnuFile.add(jSeparator9);

        mnuOpenFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mnuOpenFile.setText(bundle.getString("openFromFile")); // NOI18N
        mnuOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpenFileActionPerformed(evt);
            }
        });
        mnuFile.add(mnuOpenFile);

        mnuOpenURL.setText(bundle.getString("openFromURL")); // NOI18N
        mnuFile.add(mnuOpenURL);
        mnuFile.add(jSeparator10);

        mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSave.setText(bundle.getString("save")); // NOI18N
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSave);

        jMenuBar2.add(mnuFile);

        mnuEdit.setMnemonic('E');
        mnuEdit.setText(bundle.getString("edit")); // NOI18N
        mnuEdit.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                mnuEditMenuSelected(evt);
            }
        });

        mnuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        mnuUndo.setText(bundle.getString("undo")); // NOI18N
        mnuUndo.setToolTipText("Undo");
        mnuUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUndoActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuUndo);

        mnuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        mnuRedo.setText(bundle.getString("redo")); // NOI18N
        mnuRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRedoActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuRedo);
        mnuEdit.add(jSeparator1);

        mnuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnuCut.setText(bundle.getString("cut")); // NOI18N
        mnuEdit.add(mnuCut);

        mnuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnuCopy.setText(bundle.getString("copy")); // NOI18N
        mnuEdit.add(mnuCopy);

        mnuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mnuPaste.setText(bundle.getString("paste")); // NOI18N
        mnuEdit.add(mnuPaste);

        mnuDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        mnuDelete.setText(bundle.getString("delete")); // NOI18N
        mnuEdit.add(mnuDelete);
        mnuEdit.add(jSeparator2);

        mnuFindRes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        mnuFindRes.setText(bundle.getString("findRes")); // NOI18N
        mnuFindRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFindResActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuFindRes);

        mnuFind.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mnuFind.setText(bundle.getString("find")); // NOI18N
        mnuFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFindActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuFind);

        mnuFindNext.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        mnuFindNext.setText(bundle.getString("findNext")); // NOI18N
        mnuFindNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuFindNextActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuFindNext);

        mnuReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnuReplace.setText(bundle.getString("replace")); // NOI18N
        mnuReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuReplaceActionPerformed(evt);
            }
        });
        mnuEdit.add(mnuReplace);

        mnuGoto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        mnuGoto.setText(bundle.getString("goto")); // NOI18N
        mnuEdit.add(mnuGoto);
        mnuEdit.add(jSeparator3);

        mnuSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        mnuSelectAll.setText(bundle.getString("selectAll")); // NOI18N
        mnuEdit.add(mnuSelectAll);

        mnuSelectParagraph.setText(bundle.getString("selectParagraph")); // NOI18N
        mnuEdit.add(mnuSelectParagraph);

        mnuSelectLine.setText(bundle.getString("selectLine")); // NOI18N
        mnuEdit.add(mnuSelectLine);

        mnuSelectWord.setText(bundle.getString("selectWord")); // NOI18N
        mnuEdit.add(mnuSelectWord);

        jMenuBar2.add(mnuEdit);

        mnuFont.setText(bundle.getString("font")); // NOI18N

        mnuFontBold.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        mnuFontBold.setText(bundle.getString("bold")); // NOI18N
        mnuFont.add(mnuFontBold);

        mnuFontItalic.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnuFontItalic.setText(bundle.getString("italic")); // NOI18N
        mnuFont.add(mnuFontItalic);

        mnuFontUnderline.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        mnuFontUnderline.setText(bundle.getString("underline")); // NOI18N
        mnuFont.add(mnuFontUnderline);
        mnuFont.add(jSeparator4);

        mnuSize.setText(bundle.getString("size")); // NOI18N

        mnuFont48.setText("48");
        mnuSize.add(mnuFont48);

        mnuFont36.setText("36");
        mnuSize.add(mnuFont36);

        mnuFont24.setText("24");
        mnuSize.add(mnuFont24);

        mnuFont18.setText("18");
        mnuSize.add(mnuFont18);

        mnuFont16.setText("16");
        mnuSize.add(mnuFont16);

        mnuFont14.setText("14");
        mnuSize.add(mnuFont14);

        mnuFont12.setText("12");
        mnuSize.add(mnuFont12);

        mnuFont10.setText("10");
        mnuSize.add(mnuFont10);

        mnuFont8.setText("8");
        mnuSize.add(mnuFont8);

        mnuFont.add(mnuSize);
        mnuFont.add(jSeparator5);

        mnuFontSerif.setText("Serif");
        mnuFont.add(mnuFontSerif);

        mnuFontSansSerif.setText("SansSerif");
        mnuFont.add(mnuFontSansSerif);

        mnuFontMonospaced.setText("Monospaced");
        mnuFont.add(mnuFontMonospaced);

        jMenuBar2.add(mnuFont);

        mnuFormat.setText(bundle.getString("format")); // NOI18N

        mnuFormatALeft.setText(bundle.getString("alignLeft")); // NOI18N
        mnuFormat.add(mnuFormatALeft);

        mnuFormatACenter.setText(bundle.getString("alignCenter")); // NOI18N
        mnuFormat.add(mnuFormatACenter);

        mnuFormatARight.setText(bundle.getString("alignRight")); // NOI18N
        mnuFormat.add(mnuFormatARight);

        jMenuBar2.add(mnuFormat);

        mnuInsert.setText(bundle.getString("insert")); // NOI18N

        mnuInsertLink.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInsertLink.setText(bundle.getString("link")); // NOI18N
        mnuInsertLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertLinkActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertLink);

        mnuInsertHR.setText(bundle.getString("hr")); // NOI18N
        mnuInsertHR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertHRActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertHR);

        mnuInsertBR.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.ALT_MASK));
        mnuInsertBR.setText(bundle.getString("br")); // NOI18N
        mnuInsertBR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertBRActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertBR);
        mnuInsert.add(jSeparator8);

        mnuInsertTable.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInsertTable.setText(bundle.getString("table")); // NOI18N
        mnuInsertTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertTableActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertTable);

        mnuInsertImage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInsertImage.setText(bundle.getString("image")); // NOI18N
        mnuInsertImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertImageActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertImage);
        mnuInsert.add(jSeparator7);

        mnuInsertResource.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInsertResource.setText(bundle.getString("resource")); // NOI18N
        mnuInsertResource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertResourceActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertResource);

        mnuInsertContent.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuInsertContent.setText(bundle.getString("content")); // NOI18N
        mnuInsertContent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInsertContentActionPerformed(evt);
            }
        });
        mnuInsert.add(mnuInsertContent);

        jMenuBar2.add(mnuInsert);

        setJMenuBar(jMenuBar2);
    }// </editor-fold>//GEN-END:initComponents
    
    private void mnuReplaceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuReplaceActionPerformed
    {//GEN-HEADEREND:event_mnuReplaceActionPerformed
        // Add your handling code here:
        applets.htmleditor.Replace s=new Replace(new Frame(),false);
        s.setText(getSelectedText());
        s.setEditor(this);
        s.setLocale(locale);
        //s.show();
        s.setVisible(true);
        
    }//GEN-LAST:event_mnuReplaceActionPerformed
    
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBox2ActionPerformed
    {//GEN-HEADEREND:event_jComboBox2ActionPerformed
        // Add your handling code here:
        ((Action)actions.get("font-size-"+jComboBox2.getSelectedItem())).actionPerformed(evt);
    }//GEN-LAST:event_jComboBox2ActionPerformed
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBox1ActionPerformed
    {//GEN-HEADEREND:event_jComboBox1ActionPerformed
        // Add your handling code here:
        //System.out.println("evt:"+jComboBox1.getSelectedItem());
        ((Action)actions.get("font-family-"+jComboBox1.getSelectedItem())).actionPerformed(evt);
    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton8ActionPerformed
    {//GEN-HEADEREND:event_jButton8ActionPerformed
        // Add your handling code here:
        insertLink();
    }//GEN-LAST:event_jButton8ActionPerformed
    
    private void mnuInsertLinkActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertLinkActionPerformed
    {//GEN-HEADEREND:event_mnuInsertLinkActionPerformed
        // Add your handling code here:
        insertLink();
    }//GEN-LAST:event_mnuInsertLinkActionPerformed
    
    private void mnuInsertResourceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertResourceActionPerformed
    {//GEN-HEADEREND:event_mnuInsertResourceActionPerformed
        // Add your handling code here:
        insertResource();
    }//GEN-LAST:event_mnuInsertResourceActionPerformed
    
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton18ActionPerformed
    {//GEN-HEADEREND:event_jButton18ActionPerformed
        // Add your handling code here:
        insertResource();
    }//GEN-LAST:event_jButton18ActionPerformed
    
    private void mnuFindNextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuFindNextActionPerformed
    {//GEN-HEADEREND:event_mnuFindNextActionPerformed
        // Add your handling code here:
        if(findStr!=null)
        {
            findStr(findStr, findMCase, findMWhole);
        }
    }//GEN-LAST:event_mnuFindNextActionPerformed
    
    private void mnuFindActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuFindActionPerformed
    {//GEN-HEADEREND:event_mnuFindActionPerformed
        // Add your handling code here:
        applets.htmleditor.Search s=new Search(new Frame(),false);
        s.setText(getSelectedText());
        s.setEditor(this);
        s.setLocale(locale);
        //s.show();
        s.setVisible(true);
    }//GEN-LAST:event_mnuFindActionPerformed
    
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton14ActionPerformed
    {//GEN-HEADEREND:event_jButton14ActionPerformed
        // Add your handling code here:
        ((Action)actions.get("paste-from-clipboard")).actionPerformed(evt);
    }//GEN-LAST:event_jButton14ActionPerformed
    
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton13ActionPerformed
    {//GEN-HEADEREND:event_jButton13ActionPerformed
        // Add your handling code here:
        ((Action)actions.get("copy-to-clipboard")).actionPerformed(evt);
    }//GEN-LAST:event_jButton13ActionPerformed
    
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton15ActionPerformed
    {//GEN-HEADEREND:event_jButton15ActionPerformed
        // Add your handling code here:
        ((Action)actions.get("cut-to-clipboard")).actionPerformed(evt);
    }//GEN-LAST:event_jButton15ActionPerformed
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
    {//GEN-HEADEREND:event_jButton6ActionPerformed
        // Add your handling code here:
        openFile();
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
    {//GEN-HEADEREND:event_jButton5ActionPerformed
        // Add your handling code here:
        newHTML();
    }//GEN-LAST:event_jButton5ActionPerformed
    
    private void mnuNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuNewActionPerformed
    {//GEN-HEADEREND:event_mnuNewActionPerformed
        // Add your handling code here:
        newHTML();
    }//GEN-LAST:event_mnuNewActionPerformed
    
    public String getSelectedText()
    {
        if(textEditor.isShowing())
        {
            return textEditor.getSelectedText();
        }else
        {
            return htmlEditor.getSelectedText();
        }
    }
    
    public void goInit()
    {
        JTextComponent source=null;
        if(textEditor.isShowing())source=textEditor;
        else source=htmlEditor;
        
        source.setCaretPosition(0);
        
    }
    
    public void replaceStr(String str)
    {
        JTextComponent source=null;
        if(textEditor.isShowing())source=textEditor;
        else source=htmlEditor;
        
        String sel=source.getSelectedText();
        if(sel!=null && sel.length()>0)
        {
            source.replaceSelection(str);
        }
    }
    
    public boolean findStr(String str, boolean mcase, boolean mwhole)
    {
        
        boolean ret=false;
        findStr=str;
        findMCase=mcase;
        findMWhole=mwhole;
        //System.out.println("findStr:"+str+" mcase:"+mcase+" mwhole:"+mwhole);
        try
        {
            JTextComponent source=null;
            if(textEditor.isShowing())source=textEditor;
            else source=htmlEditor;
            
            {
                int pos=source.getCaretPosition();
                Document doc=source.getDocument();
                int len=doc.getLength();
                String txt="";
                if(mcase)
                {
                    txt=doc.getText(pos,len-pos);
                }else
                {
                    txt=doc.getText(pos,len-pos).toLowerCase();
                    str=str.toLowerCase();
                }
                int y=0;
                int x=0;
                boolean find=false;
                
                x=txt.indexOf(str,x);
                if(x>-1)
                {
                    if(mwhole)
                    {
                        find=checkWhole(txt,x,str.length());
                    }else
                    {
                        find=true;
                    }
                }
                if(find)
                {
                    //setCaretPosition(x);
                    int z=str.length();
                    if(z>0)
                        source.select(pos+x, pos+x+z);
                    else
                        source.setCaretPosition(pos+x);
                    setStatus("");
                    ret=true;
                }else
                {
                    setStatus(java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("documentEnd"));
                    source.setCaretPosition(0);
                }
            }
        }catch(Exception ex)
        {System.out.println(ex);}
        return ret;
    }
    
    private boolean checkWhole(String txt,int x,int l)
    {
        boolean ret=false;
        if(x>0)
        {
            char ch=txt.charAt(x-1);
            if(isValidChar(ch))return false;
            if(txt.length()>=x+l)
            {
                ch=txt.charAt(x+l);
                if(isValidChar(ch))return false;
            }
        }
        return true;
    }
    
    private boolean isValidChar(char ch)
    {
        if(ch>='0' && ch<='9')return true;
        if(ch>='a' && ch<='z')return true;
        if(ch>='A' && ch<='Z')return true;
        if(ch=='á' || ch=='é' || ch=='í' || ch=='ó' || ch=='ú')return true;
        if(ch=='Á' || ch=='É' || ch=='Í' || ch=='Ó' || ch=='Ú')return true;
        if(ch=='_' || ch=='ñ' || ch=='Ñ')return true;
        return false;
    }
    
    
    public void setStatus(String str)
    {
        jLabel1.setText(str);
    }
    
    private void newHTML()
    {
        // Add your handling code here:
        int ret=JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("msgConfirm"),"WebBuilder",JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(ret==2)return;
        String txt="<html>\n"
        +"  <head>\n"
        +"    <title><TOPIC METHOD=\"getDisplayName\" language=\"{user@getLanguage}\"/></title>\n"
        +"  </head>\n"
        +"  <body>\n"
        +"    <p style=\"margin-top: 0\">\n"
        +"    </p>\n"
        +"  </body>\n"
        +"</html>";
        
        if(textEditor.isShowing())
        {
            textEditor.setText(txt);
        }else
        {
            textEditor.setText(txt);
            htmlEditor.setInitText(getHTML(textEditor.getText()));
        }
    }
    
    private void saveHTML()
    {
        if(oldTab.equals("html") && htmlEditor.isChange())
        {
            textEditor.setText(replaceBodyHTML(textEditor.getText(),getBodyHTML(htmlEditor.getText())));
        }
        String ret=sendHTML(textEditor.getText(),filename);
    }
    
    public String getHTML()
    {
        if(oldTab.equals("html") && htmlEditor.isChange())
        {
            textEditor.setText(replaceBodyHTML(textEditor.getText(),getBodyHTML(htmlEditor.getText())));
        }
        return textEditor.getText();
    }
    
    
    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuSaveActionPerformed
    {//GEN-HEADEREND:event_mnuSaveActionPerformed
        // Add your handling code here:
        saveHTML();
    }//GEN-LAST:event_mnuSaveActionPerformed
    
    private void mnuOpenFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuOpenFileActionPerformed
    {//GEN-HEADEREND:event_mnuOpenFileActionPerformed
        // Add your handling code here:
        openFile();
    }//GEN-LAST:event_mnuOpenFileActionPerformed
    
    private void mnuEditMenuSelected(javax.swing.event.MenuEvent evt)//GEN-FIRST:event_mnuEditMenuSelected
    {//GEN-HEADEREND:event_mnuEditMenuSelected
        // Add your handling code here:
        //System.out.println("mnuEditMenuSelected");
        /*
        if(textEditor.isShowing())
        {
            mnuUndo.setEnabled(textEditor.canUndo());
            mnuRedo.setEnabled(textEditor.canRedo());
        }
         */
        
    }//GEN-LAST:event_mnuEditMenuSelected
    
    private void mnuInsertBRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertBRActionPerformed
    {//GEN-HEADEREND:event_mnuInsertBRActionPerformed
        // Add your handling code here:
        insertHTML("<BR>");
    }//GEN-LAST:event_mnuInsertBRActionPerformed
    
    private void mnuInsertHRActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertHRActionPerformed
    {//GEN-HEADEREND:event_mnuInsertHRActionPerformed
        // Add your handling code here:
        insertHTML("<HR>");
    }//GEN-LAST:event_mnuInsertHRActionPerformed
    
    private void mnuRedoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuRedoActionPerformed
    {//GEN-HEADEREND:event_mnuRedoActionPerformed
        // Add your handling code here:
        if(textEditor.isShowing())
        {
            textEditor.redo();
        }else
        {
            htmlEditor.redo();
        }
    }//GEN-LAST:event_mnuRedoActionPerformed
    
    private void mnuUndoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuUndoActionPerformed
    {//GEN-HEADEREND:event_mnuUndoActionPerformed
        // Add your handling code here:
        if(textEditor.isShowing())
        {
            textEditor.undo();
        }else
        {
            htmlEditor.undo();
        }
        
    }//GEN-LAST:event_mnuUndoActionPerformed
    
    private void mnuFindResActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuFindResActionPerformed
    {//GEN-HEADEREND:event_mnuFindResActionPerformed
        // Add your handling code here:
        if(textEditor.isShowing())
        {
            textEditor.findResourceTag();
        }
    }//GEN-LAST:event_mnuFindResActionPerformed
    
    private void butFormatARightActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFormatARightActionPerformed
    {//GEN-HEADEREND:event_butFormatARightActionPerformed
        // Add your handling code here:
        ((Action)actions.get("right-justify")).actionPerformed(evt);
    }//GEN-LAST:event_butFormatARightActionPerformed
    
    private void butFormatACenterActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFormatACenterActionPerformed
    {//GEN-HEADEREND:event_butFormatACenterActionPerformed
        // Add your handling code here:
        ((Action)actions.get("center-justify")).actionPerformed(evt);
    }//GEN-LAST:event_butFormatACenterActionPerformed
    
    private void butFontUnderlineActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFontUnderlineActionPerformed
    {//GEN-HEADEREND:event_butFontUnderlineActionPerformed
        // Add your handling code here:
        ((Action)actions.get("font-underline")).actionPerformed(evt);
    }//GEN-LAST:event_butFontUnderlineActionPerformed
    
    private void butFormatALeftActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFormatALeftActionPerformed
    {//GEN-HEADEREND:event_butFormatALeftActionPerformed
        // Add your handling code here:
        ((Action)actions.get("left-justify")).actionPerformed(evt);
    }//GEN-LAST:event_butFormatALeftActionPerformed
    
    private void mnuInsertContentActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertContentActionPerformed
    {//GEN-HEADEREND:event_mnuInsertContentActionPerformed
        // Add your handling code here:
        insertCustomHTML("<content/>");
    }//GEN-LAST:event_mnuInsertContentActionPerformed
    
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton19ActionPerformed
    {//GEN-HEADEREND:event_jButton19ActionPerformed
        // Add your handling code here:
        insertCustomHTML("<content/>");
    }//GEN-LAST:event_jButton19ActionPerformed
    
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton17ActionPerformed
    {//GEN-HEADEREND:event_jButton17ActionPerformed
        // Add your handling code here:
        insertTable();
    }//GEN-LAST:event_jButton17ActionPerformed
    
    private void mnuInsertTableActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertTableActionPerformed
    {//GEN-HEADEREND:event_mnuInsertTableActionPerformed
        // Add your handling code here:
        insertTable();
    }//GEN-LAST:event_mnuInsertTableActionPerformed
    
    private void mnuInsertImageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mnuInsertImageActionPerformed
    {//GEN-HEADEREND:event_mnuInsertImageActionPerformed
        // Add your handling code here:
        insertImage();
    }//GEN-LAST:event_mnuInsertImageActionPerformed
    
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton9ActionPerformed
    {//GEN-HEADEREND:event_jButton9ActionPerformed
        // Add your handling code here:
        insertImage();
    }//GEN-LAST:event_jButton9ActionPerformed
    
    private void butFontBoldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFontBoldActionPerformed
    {//GEN-HEADEREND:event_butFontBoldActionPerformed
        // Add your handling code here:
        ((Action)actions.get("font-bold")).actionPerformed(evt);
    }//GEN-LAST:event_butFontBoldActionPerformed
    
    private void butFontItalicActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_butFontItalicActionPerformed
    {//GEN-HEADEREND:event_butFontItalicActionPerformed
        // Add your handling code here:
        ((Action)actions.get("font-italic")).actionPerformed(evt);
    }//GEN-LAST:event_butFontItalicActionPerformed
    
    public void checkEditButton()
    {
        if(htmlEditor.isEditable()==true)
        {
            jButton1.setBorderPainted(true);
            jButton1.setBackground(new Color(200,200,255));
        }else
        {
            jButton1.setBackground(null);
            jButton1.setBorderPainted(false);
        }        
    }
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        // Add your handling code here:
        if(htmlError)return;
        htmlEditor.setEditable(!htmlEditor.isEditable());
        htmlEditor.updateUI();
        checkEditButton();
        // Add your handling code here:
        //String txt=textEditor.getText();
        //System.out.println("************************");
        //System.out.println(getBodyHTML(txt));
        //System.out.println("************************");
        //System.out.println(replaceBodyHTML(txt,"Hola JEI"));
        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        // Add your handling code here:
        ((WBTextEditorPane)textEditor).syntaxColorizing();
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        // Add your handling code here:
        saveHTML();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jTabbedPane1StateChanged
    {//GEN-HEADEREND:event_jTabbedPane1StateChanged
        // Add your handling code here:
        //System.out.println("jTabbedPane1StateChanged:"+evt);
        //System.out.println("jTabbedPane1StateChanged:"+jTabbedPane1.getSelectedIndex());
        try
        {
            if(jTabbedPane1.getSelectedComponent()==jPanel2)
            {
                if(oldTab.equals("html") && htmlEditor.isChange())
                {
                    textEditor.setText(replaceBodyHTML(textEditor.getText(),getBodyHTML(htmlEditor.getText())));
                }
                oldTab="text";
                
                jButton1.setVisible(false);
                mnuFont.setVisible(false);
                mnuFormat.setVisible(false);
                jPanel6.setVisible(false);
                jPanel8.setVisible(false);
                jPanel5.setVisible(true);
                
            }else if(jTabbedPane1.getSelectedComponent()==jPanel1)
            {
                if(oldTab.equals("text"))
                {
                    try
                    {
                        htmlEditor.setInitText(getHTML(textEditor.getText()));
                        htmlError=false;
                    }catch(Exception e)
                    {
                        htmlError=true;
                        htmlEditor.setEditable(false);
                        JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("errShowingHTMLView")+", "+e.getMessage(),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
                    }
                }
                oldTab="html";
                
                jButton1.setVisible(true);
                mnuFont.setVisible(true);
                mnuFormat.setVisible(true);
                jPanel6.setVisible(true);
                jPanel8.setVisible(true);
                jPanel5.setVisible(false);
            }
        }catch(Exception e)
        {e.printStackTrace(System.out);}
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jButtonAddFilesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddFilesActionPerformed
    {//GEN-HEADEREND:event_jButtonAddFilesActionPerformed
        
        String path="/work/models/"+tmValue+"/Template/"+idValue+"/"+verValue+"/";
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try
        {
            ftp ftp=new ftp(locale, jsess, upurl, downurl,path,gateway);
            ftp.setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jButtonAddFilesActionPerformed
    
    private Color stringToColor(String paramValue)
    {
        int red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
        int green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
        int blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();
        return new Color(red,green,blue);
    }
    
    public String readInputStream(InputStream in)
    {
        if (in == null) return null;
        StringBuilder ret = new StringBuilder();
        try
        {
            byte[] bfile = new byte[8192];
            int x;
            while ((x = in.read(bfile, 0, 8192)) > -1)
            {
                String data=new String(bfile, 0, x);
                System.out.println(data);
                ret.append(data);
                data=new String(bfile, 0, x,"utf-8");
                System.out.println(data);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                in.close();            
            }catch(Exception e2){e2.printStackTrace();}
        }

        return ret.toString();
    }
    
    public String getHTML(String html)
    {
        String txt=html;
        String txtl=txt.toLowerCase();
        int ini=0;
        int fin=txt.length();
        int aux=0;
        if((aux=txtl.indexOf("<html"))>-1)
        {
            ini=aux;
        }
        if((aux=txtl.lastIndexOf("</html"))>-1)
        {
            aux=txtl.indexOf('>',aux);
            if(aux>-1)fin=aux+1;
        }
        return txt.substring(ini,fin).trim();
    }
    
    public String getBodyHTML(String html)
    {
        String txt=html;
        String txtl=txt.toLowerCase();
        int ini=0;
        int fin=txt.length();
        int aux=0;
        if((aux=txtl.indexOf("<body"))>-1)
        {
            aux=txtl.indexOf('>',aux);
            if(aux>-1)ini=aux+1;
        }
        if((aux=txtl.lastIndexOf("</body"))>-1)
        {
            fin=aux;
        }
        return txt.substring(ini,fin).trim();
    }
    
    public String replaceBodyHTML(String html, String body)
    {
        String txt=html;
        String txtl=txt.toLowerCase();
        int ini=0;
        int fin=txt.length();
        int aux=0;
        if((aux=txtl.indexOf("<body"))>-1)
        {
            aux=txtl.indexOf('>',aux);
            if(aux>-1)ini=aux+1;
        }
        if((aux=txtl.indexOf("</body"))>-1)
        {
            fin=aux;
        }
        String ret=txt.substring(0,ini)+"\n"+body+"\n"+txt.substring(fin);
        if(ini==0)
        {
            ret="<html>\n  <head>\n  </head>\n  <body>"+ret;
        }
        if(fin==txt.length())
        {
            ret=ret+"  </body>\n</html>";
        }
        return ret;
    }
    
    private String sendHTML(String html, String name)
    {
        return sendHTML(html, name, false,false);
    }
    
    private String sendHTML(String html, String name, boolean replace, boolean findattaches)
    {
        StringBuilder ret=new StringBuilder();
        try
        {
            URLConnection urlconn=upurl.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("PATHFILEWB",name);
            if(name.endsWith(".css"))
            {
                urlconn.setRequestProperty("CSSTYPE","TRUE");
            }
            if(replace)
            {
                urlconn.setRequestProperty("DOCUMENT","REPLACE");
            }
            else if(findattaches)
            {
                urlconn.setRequestProperty("DOCUMENT","FINDATTACHES");
            }else
            {
                urlconn.setRequestProperty("DOCUMENT","RELOAD");
            }
            urlconn.setRequestProperty("TM",tmValue);
            urlconn.setRequestProperty("ID",idValue);
            if(tpValue!=null)urlconn.setRequestProperty("TP",tpValue);
            urlconn.setRequestProperty("VER",verValue);
            urlconn.setRequestProperty("TYPE", typeValue);
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            
            try
            {
                pout.print(html);
            } catch (Exception e)
            {
                System.out.println(e);
            }
            pout.flush();
            pout.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
            }
            in.close();
            if(!replace && !findattaches)JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("submited"),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e)
        {
            System.out.println("Error to open service..."+e);
            JOptionPane.showConfirmDialog(this,java.util.ResourceBundle.getBundle("applets/htmleditor/TemplateEditor",locale).getString("errSending")+", "+e.getMessage(),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
        }
        return ret.toString();
    }
    
    /**
     * center a <code>Component</code> relative to
     * another <code>Component</code>.
     *
     * @param parent  the <code>Component</code> to be used as the
     *                  basis for centering
     * @param comp  the <code>Component</code> to be centered within parent
     *
     */
    public static void center(Component parent, Component comp)
    {
        Dimension cSize = comp.getPreferredSize();
        Dimension fSize = parent.getSize();
        Point loc = parent.getLocation();
        comp.setLocation(((fSize.width - cSize.width) / 2) + loc.x,
        ((fSize.height - cSize.height) / 2) + loc.y);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butFontBold;
    private javax.swing.JButton butFontItalic;
    private javax.swing.JButton butFontUnderline;
    private javax.swing.JButton butFormatACenter;
    private javax.swing.JButton butFormatALeft;
    private javax.swing.JButton butFormatARight;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonAddFiles;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenuItem mnuCopy;
    private javax.swing.JMenuItem mnuCut;
    private javax.swing.JMenuItem mnuDelete;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuFind;
    private javax.swing.JMenuItem mnuFindNext;
    private javax.swing.JMenuItem mnuFindRes;
    private javax.swing.JMenu mnuFont;
    private javax.swing.JMenuItem mnuFont10;
    private javax.swing.JMenuItem mnuFont12;
    private javax.swing.JMenuItem mnuFont14;
    private javax.swing.JMenuItem mnuFont16;
    private javax.swing.JMenuItem mnuFont18;
    private javax.swing.JMenuItem mnuFont24;
    private javax.swing.JMenuItem mnuFont36;
    private javax.swing.JMenuItem mnuFont48;
    private javax.swing.JMenuItem mnuFont8;
    private javax.swing.JMenuItem mnuFontBold;
    private javax.swing.JMenuItem mnuFontItalic;
    private javax.swing.JMenuItem mnuFontMonospaced;
    private javax.swing.JMenuItem mnuFontSansSerif;
    private javax.swing.JMenuItem mnuFontSerif;
    private javax.swing.JMenuItem mnuFontUnderline;
    private javax.swing.JMenu mnuFormat;
    private javax.swing.JMenuItem mnuFormatACenter;
    private javax.swing.JMenuItem mnuFormatALeft;
    private javax.swing.JMenuItem mnuFormatARight;
    private javax.swing.JMenuItem mnuGoto;
    private javax.swing.JMenu mnuInsert;
    private javax.swing.JMenuItem mnuInsertBR;
    private javax.swing.JMenuItem mnuInsertContent;
    private javax.swing.JMenuItem mnuInsertHR;
    private javax.swing.JMenuItem mnuInsertImage;
    private javax.swing.JMenuItem mnuInsertLink;
    private javax.swing.JMenuItem mnuInsertResource;
    private javax.swing.JMenuItem mnuInsertTable;
    private javax.swing.JMenuItem mnuNew;
    private javax.swing.JMenuItem mnuOpenFile;
    private javax.swing.JMenuItem mnuOpenURL;
    private javax.swing.JMenuItem mnuPaste;
    private javax.swing.JMenuItem mnuRedo;
    private javax.swing.JMenuItem mnuReplace;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JMenuItem mnuSelectAll;
    private javax.swing.JMenuItem mnuSelectLine;
    private javax.swing.JMenuItem mnuSelectParagraph;
    private javax.swing.JMenuItem mnuSelectWord;
    private javax.swing.JMenu mnuSize;
    private javax.swing.JMenuItem mnuUndo;
    // End of variables declaration//GEN-END:variables
    
}
