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
 * TMWBApplet.java
 *
 * Created on 3 de marzo de 2005, 04:41 PM
 */

package applets.mapsadm;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.applet.AppletContext;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class TMWBApplet extends javax.swing.JApplet implements TMWBContainer
{
    private static int bufferSize=8192;       
    private Hashtable langs=new Hashtable();
    private final String IDTPLANG = "IDM_WB";
    
    //private String cgi="";                    //cgi del gateway
    private String doc="/wb/";                  //cgi del admin
    private String jsess="";                    //session del usuario
    //private URL gurl=null;                    //url de gateway
    
    private int lastx;
    private int lasty;

    private final String backgroundParam = "background";
    private final String foregroundParam = "foreground";
    private final String backgroundSelectionParam = "backgroundSelection";
    private final String foregroundSelectionParam = "foregroundSelection";
    private final String cgiPathParam = "cgipath";
    private String cgiPath = null;
    protected URL url=null;
    
    private String urlbase="";
    private String urlpost="";
    
    
    
    private final String TMParam = "TM";
    private final String TPParam = "TP";
    private boolean isTMLoaded=false;
    
    private TMViewer viewer=null;

    private SelectTopic selectTop;
    protected AppObject edit,auxdrag;
    
    WBTopicMap tm=null;
    private Locale locale=Locale.getDefault();
    

    private javax.swing.JPanel jPanel1;
    
    private java.awt.PopupMenu topicMenu;    
    private java.awt.MenuItem topicItem0;
    private java.awt.MenuItem topicItem1;
    private java.awt.MenuItem topicItem3;
    private java.awt.MenuItem topicItem4;
    private java.awt.MenuItem topicItem5;
    private java.awt.MenuItem topicItem6;
    private java.awt.MenuItem topicItem7;
    private java.awt.MenuItem topicItem8;
    private java.awt.MenuItem topicItem9;
    
    
    /** Creates a new instance of TMWBApplet */
    public TMWBApplet()
    {
    }
    
    public void init() 
    {
        cgiPath = getParameter(cgiPathParam);
        try
        {
            if(cgiPath!=null)
                url=new URL(getCodeBase(),cgiPath);
                //url=new URL("http://comunidad.infotec.com.mx"+cgiPath);
        }catch(Exception e){}
        //System.out.println(url);
        
        jsess=this.getParameter("jsess");
        
        urlpost=this.getParameter("urlpost");
        urlbase=this.getParameter("urlbase");
        
        String loc=this.getParameter("locale");
        if(loc!=null)locale=new Locale(loc);
        
/*        
        getUserAttr();
        root=getTopicMaps();
*/
        initComponents();
        viewer=(TMViewer)jPanel1;
        viewer.setShowDocument(true);
        viewer.setUrlBase(urlbase);
        viewer.setUrlPost(urlpost);
        this.getContentPane().add(topicMenu);                           //dar de alta popup menu topics
        //timer=new WBTimer(this);                                        //timer retardo de popup
        //getTopicMaps();
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()
    {
        topicMenu = new java.awt.PopupMenu();
        topicItem1 = new java.awt.MenuItem();
        topicItem8 = new java.awt.MenuItem();
        topicItem0 = new java.awt.MenuItem();
        topicItem5 = new java.awt.MenuItem();
        topicItem6 = new java.awt.MenuItem();
        topicItem9 = new java.awt.MenuItem();
        topicItem3 = new java.awt.MenuItem();
        topicItem7 = new java.awt.MenuItem();
        topicItem4 = new java.awt.MenuItem();
        jPanel1 = new TMViewer(this, locale);
        
        topicMenu.setLabel("PopupMenu");
        topicMenu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                topicMenuActionPerformed(evt);
            }
        });

        topicItem1.setActionCommand("create");
        topicItem1.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.new"));
        topicMenu.add(topicItem1);

        topicItem8.setActionCommand("edit");
        topicItem8.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.edit"));
        topicItem8.setName("");
        topicMenu.add(topicItem8);

        topicItem0.setActionCommand("open");
        topicItem0.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.open"));
        topicMenu.add(topicItem0);

        //topicMenu.add(checkboxMenuItem1);

        topicMenu.addSeparator();
        topicItem5.setActionCommand("home");
        topicItem5.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.home"));
        topicItem5.setName("");
        topicMenu.add(topicItem5);

        topicItem6.setActionCommand("goto");
        topicItem6.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.goto"));
        topicMenu.add(topicItem6);

        topicMenu.addSeparator();
        topicItem9.setActionCommand("borrado");
        topicItem9.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.markDelete"));
        topicItem9.setName("");
        topicMenu.add(topicItem9);

        topicItem3.setActionCommand("delete");
        topicItem3.setEnabled(false);
        topicItem3.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.delete"));
        topicMenu.add(topicItem3);

        topicMenu.addSeparator();
        topicItem7.setActionCommand("update");
        topicItem7.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.reload"));
        topicMenu.add(topicItem7);

        topicItem4.setActionCommand("refresh");
        topicItem4.setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.refresh"));
        topicMenu.add(topicItem4);

        usePageParams();
        
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    }    
    
    /** Getter for property topicMenu.
     * @return Value of property topicMenu.
     */
    public java.awt.PopupMenu getTopicMenu()
    {
        return topicMenu;
    }    
    
    /** Setter for property topicMenu.
     * @param topicMenu New value of property topicMenu.
     */
    public void setTopicMenu(java.awt.PopupMenu topicMenu)
    {
        this.topicMenu = topicMenu;
    }
    
    private String getCData(String data)
    {
        StringBuffer cdata=new StringBuffer();
        int s=data.indexOf("<![CDATA[");
        while(s>0)
        {
            int f=data.indexOf("]]>",s);
            //System.out.println("l:"+data.length()+" s:"+s+" f:"+f);
            //System.out.println(data.substring(f-100,f+100));
            cdata.append(data.substring(s+9,f));
            //System.out.println(viewer.actual);
            s=data.indexOf("<![CDATA[", f);
            if(s>0)
            {
                String aux=data.substring(f+3,s);
                //String rpl=WBXMLParser.replaceStrTags(aux);
                //System.out.println(aux+" "+rpl);
                cdata.append(aux);
            }
        }
        return cdata.toString();
    }
    

    private String getData(String xml)
    {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        //System.out.println("getData:"+xml);
         String ret="";
        try
        {
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("Accept-Encoding", "gzip");
            urlconn.setRequestProperty("Content-Type","application/xml");            
            //System.out.println("JSESSIONID="+jsess);
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

//            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null)
//            {
//                ret.append(inputLine);
//                ret.append("\n");
//                //System.out.println(inputLine);
//            }
//            in.close();
            String accept=urlconn.getHeaderField("Content-Encoding");
            //System.out.println("Content-Encoding:"+accept);
            InputStream in;
            if (accept != null && accept.toLowerCase().indexOf("gzip") != -1)
            {
                in=new GZIPInputStream(urlconn.getInputStream(),bufferSize);
            }else
            {
                in=urlconn.getInputStream();
            }
            
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            byte[] bfile = new byte[bufferSize];
            int x;
            while ((x = in.read(bfile, 0, bufferSize)) > -1)
            {
                out.write(bfile, 0, x);
            }
            in.close();
            out.flush();
            out.close();
            ret=new String(out.toByteArray());            
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        //System.out.println("ret:"+ret.toString());
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        return ret;
    }
    
    public void setTopicMap(String tmid, String homeid)
    {
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+tmid+"</topicmap><cmd>getTopicMap4Adm</cmd></req>");
        //System.out.println("data:"+data);
        data=getCData(data);
        if(data.length()>0)
        {
            viewer.candraw=false;
            viewer.over=null;
            viewer.getMap(data);
            viewer.setActual(homeid);
            viewer.home=homeid;
            viewer.candraw=true;   
            //getLanguages(tmid);
            //System.out.println(viewer.actual);
        }        
    }    
    
    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt)
    {
/*            
        // Add your handling code here:
        if(auxdrag!=null)
        {
            if(!drag)
            {
                drag=true;
                setCursor(curdrag);
            }
            else if(types.isVisible() || tableRelations.isVisible())
            {
                int dragx=evt.getX()+(int)jPanel1.getLocationOnScreen().getX();
                int dragy=evt.getY()+(int)jPanel1.getLocationOnScreen().getY();
                int typex=(int)jScrollPane3.getLocationOnScreen().getX();
                int typey=(int)jScrollPane3.getLocationOnScreen().getY();
                int typew=(int)jScrollPane3.getWidth();
                int typeh=(int)jScrollPane3.getHeight();
                int relx=(int)jScrollPane2.getLocationOnScreen().getX();
                int rely=(int)jScrollPane2.getLocationOnScreen().getY();
                int relw=(int)jScrollPane2.getWidth();
                int relh=(int)jScrollPane2.getHeight();
                
                if((tableRelations.isVisible()&&dragx>relx&&dragy>rely&&dragx<relx+relw&&dragy<rely+relh) || (types.isVisible()&&dragx>typex&&dragy>typey&&dragx<typex+typew&&dragy<typey+typeh))
                {
                    if(getCursor()!=curdrag)setCursor(curdrag);
                }
                else
                {
                    if(getCursor()!=curdrag2)setCursor(curdrag2);
                }
            }
        }
 */
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

        //if(getParameter("Home")!=null)viewer.home = getParameter("Home");
        /**
         * Read the <PARAM NAME="label" VALUE="some string">,
         * <PARAM NAME="background" VALUE="rrggbb">,
         * and <PARAM NAME="foreground" VALUE="rrggbb"> tags from
         * the applet's HTML host.
         */
        backgroundValue = getParameter(backgroundParam);
        foregroundValue = getParameter(foregroundParam);
        backgroundSelectionValue = getParameter(backgroundSelectionParam);
        foregroundSelectionValue = getParameter(foregroundSelectionParam);

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
        
        


    }
    
    private Color stringToColor(String paramValue)
    {
        int red;
        int green;
        int blue;

        red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
        green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
        blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();

        return new Color(red,green,blue);
    }    
    
    
    private void topicMenuActionPerformed(java.awt.event.ActionEvent evt)
    {
/*        
        // Add your handling code here:
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        //System.out.println(evt);
            
        if(evt.getActionCommand().equals("create"))
        {
            frmCreateTopic(tm,viewer.auxedit);
        }
        if(evt.getActionCommand().equals("edit"))
        {
            setEditing(true,0,viewer.auxedit);            
            viewer.setActual(viewer.auxedit.getId());
        }
        else if(evt.getActionCommand().equals("open"))
        {
            try{
                this.getAppletContext().showDocument(new URL(url,doc+tm.getId()+"/"+viewer.auxedit.getId()),"_new");
            }catch(Exception e){System.out.println("Error opening section..."+e.getMessage());}            
        }
        else if(evt.getActionCommand().equals("delete"))
        {
            frmDeleteTopic(tm,viewer.auxedit);
        }
        else if(evt.getActionCommand().equals("deleteR"))
        {
            frmDeleteTopicR(tm,viewer.auxedit);
        }
        else if(evt.getActionCommand().equals("borrado"))
        {
            frmBorradoTopic(tm,viewer.auxedit);
        }
        else if(evt.getActionCommand().equals("goto"))
        {
            selectTop=new SelectTopic(viewer.objSort,viewer,locale);
            selectTop.show();
        }
        else if(evt.getActionCommand().equals("home"))
        {
            viewer.setActual(viewer.home);
        }
        else if(evt.getActionCommand().equals("refresh"))
        {
            viewer.organizeMap();
            viewer.changes=true;
        }
        else if(evt.getActionCommand().equals("update"))
        {
            String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><topicmap>"+tm.getId()+"</topicmap><cmd>getTopicMap4Adm</cmd></req>");
            data=getCData(data);
            if(data.length()>0)
            {
                viewer.candraw=false;
                String ant=viewer.actual.id;
                viewer.over=null;
                viewer.getMap(data);
                viewer.setActual(ant);
                viewer.candraw=true;
            }            
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
 */
    }    
    
    public void start()
    {
        viewer.start();
        if(!isTMLoaded)
        {
            isTMLoaded=true;
            setTopicMap(getParameter(TMParam),getParameter(TPParam));
        }
    }
    
    public void stop()
    {
        viewer.stop();
    }    
    
    private void jPanel1MouseReleased(java.awt.event.MouseEvent evt)
    {
        // Add your handling code here:
        //System.out.println("mouse release drag:"+drag+" "+auxdrag);
        
    }    
    
    public void mapChange()
    {
    }
    
    public void setAuxDrag(applets.mapsadm.AppObject auxdrag)
    {
    }
    
    public boolean isEditing()
    {
        return false;
    }
    
    public void setActual(String id)
    {
        viewer.setActual(id);
    }
    
    /** Getter for property actual.
     * @return Value of property actual.
     */
    public AppObject getActual()
    {
        return viewer.getActual();
    }
}
