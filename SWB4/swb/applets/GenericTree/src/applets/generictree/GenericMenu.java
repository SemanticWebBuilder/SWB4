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
 * GenericMenu.java
 *
 * Created on 25 de junio de 2004, 07:47 PM
 */
package applets.generictree;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import applets.commons.*;

/**
 * Applet que despliega el menu en la parte superior.
 *
 * Applet that shows the top menu.
 *
 * @author Javier Solis Gonzalez
 */
public class GenericMenu extends javax.swing.JApplet
{
    private static final String PRM_JSESS="jsess";
    private static final String PRM_CGIPATH="cgipath";
    private static final String ND_RES="res";
    private static final String ND_MENU="menu";
    private static final String ND_ICONS="icons";
    private static final String ATT_PATH="path";
    private static final String ATT_TEXT="text";
    private static final String ATT_ID="id";
    private static final String ATT_NAME="name";
    private static final String ATT_ACTION="action";
    private static final String ATT_TARGET="target";
    private static final String ATT_ICON="icon";
    private static final String ATT_SHORTCUT="shortCut";
    private static final String ND_SEPARATOR="separator";
    
    
    private HashMap menus=new HashMap();
    private HashMap options=new HashMap();
    private HashMap iconsMap=new HashMap();
    
    
    private String cgiPath="/gtw.jsp";
    private String jsess="";                    //session del usuario
    private URL url=null;
    
    private java.awt.event.ActionListener actList=new java.awt.event.ActionListener()
    {
        public void actionPerformed(java.awt.event.ActionEvent evt)
        {
            genMenuActionPerformed(evt);
        }
    };
    
    
    /** Initializes the applet GenericMenu */
    public void init()
    {
        jsess=this.getParameter(PRM_JSESS);
        cgiPath=this.getParameter(PRM_CGIPATH);
        try
        {
            url=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),cgiPath);
        }catch(Exception e){}
        initComponents();
        initWB();
        jPanel2.add(jMenuBar1);
        
    }

   private void addMenu(WBTreeNode node, MenuElement menu)
   {
        for(int x=0;x<node.getNodesSize();x++)
        {
            WBTreeNode opt=node.getNode(x);
            String icon=opt.getAttribute(ATT_ICON);
            String shortcut=opt.getAttribute(ATT_SHORTCUT);
            if(opt.getNodesSize()>0)
            {
                JMenu item=new JMenu(opt.getAttribute(ATT_NAME));
                if(menu instanceof JPopupMenu)((JPopupMenu)menu).add(item);
                else if(menu instanceof JMenu)((JMenu)menu).add(item);
                addMenu(opt,item);
                if(icon!=null)item.setIcon((ImageIcon)iconsMap.get(icon));
                //if(shortcut!=null)item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(shortcut));
            }else
            {
                if(opt.getName().equals(ND_SEPARATOR))
                {
                    if(menu instanceof JPopupMenu)((JPopupMenu)menu).addSeparator();
                    else if(menu instanceof JMenu)((JMenu)menu).addSeparator();
                }else 
                {
                    JMenuItem item=new JMenuItem(opt.getAttribute(ATT_NAME));
                    item.setActionCommand(opt.getAttribute(ATT_ID));
                    if(menu instanceof JPopupMenu)((JPopupMenu)menu).add(item);
                    else if(menu instanceof JMenu)((JMenu)menu).add(item);
                    options.put(opt.getAttribute(ATT_ID), opt);

                    if(icon!=null)item.setIcon((ImageIcon)iconsMap.get(icon));
                    if(shortcut!=null)item.setAccelerator(javax.swing.KeyStroke.getKeyStroke(shortcut));
                    item.addActionListener(actList);                
                    
                }
            }
        }
        menus.put(node.getAttribute(ATT_ID), menu);
   }
    
    
    private void fillMenu(JPanel jPannel, WBTreeNode node)
    {
        //ICONS
        WBTreeNode icons=node.findNode(ND_ICONS);
        Iterator it=icons.getNodes().iterator();
        while(it.hasNext())
        {
            WBTreeNode inode=(WBTreeNode)it.next();
            Image img=getImage(getClass().getResource(inode.getAttribute(ATT_PATH)));
            try
            {
                MediaTracker mt=new MediaTracker(this);
                mt.addImage(img,0);
                mt.waitForAll();
            }catch(InterruptedException e){e.printStackTrace(System.out);}
            Image buf=img;
            String txt=inode.getAttribute(ATT_TEXT);
            if(txt!=null)
            {
                int w=img.getWidth(this);
                int h=img.getHeight(this);
                buf=this.createImage(w,h);
                Graphics grp=buf.getGraphics();
                //grp.setColor(stringToColor(getParameter(backgroundParam)));
                grp.setColor(jMenuBar1.getBackground());
                grp.fillRect(0,0,w,h);
                grp.drawImage(img,0,0,this);
                
                Rectangle2D rec=grp.getFontMetrics().getStringBounds(txt,grp);
                int fw=(int)rec.getWidth();
                int fh=grp.getFontMetrics().getAscent()-grp.getFontMetrics().getDescent();
                int x=(int)((w-fw)/2);
                int y=fh+(h-fh)/2;
                
                grp.setColor(jMenuBar1.getForeground());
                grp.drawString(txt,x,y);
            }
            iconsMap.put(inode.getAttribute(ATT_ID), new ImageIcon(buf));
        }
        
        WBTreeNode menu=node.findNode(ND_MENU);
        for(int x=0;x<menu.getNodesSize();x++)
        {
            WBTreeNode opt=menu.getNode(x);
            JMenu pmenu=new JMenu(opt.getAttribute(ATT_NAME));
            pmenu.setBackground(new java.awt.Color(224, 231, 220));
            pmenu.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(2, 5, 2, 5)));
            pmenu.setBorderPainted(false);
            pmenu.setMnemonic(opt.getAttribute(ATT_NAME).charAt(0));
            addMenu(opt,pmenu);
            this.jMenuBar1.add(pmenu);
        }
        
    }
    
    private void initWB()
    {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>initMenu</cmd></req>";
        String data=getData(xml);
        //System.out.println("data:"+data);
        WBTreeNode wbnode=new WBXMLParser().parse(data);
        //System.out.println("wbnode:"+wbnode);
        fillMenu(jPanel1,wbnode);
    }
    
    public void genMenuActionPerformed(java.awt.event.ActionEvent evt)
    {
        //System.out.println("Command:"+evt);
        String id=evt.getActionCommand();
        
        WBTreeNode node=(WBTreeNode)options.get(id);
        String action=node.getAttribute(ATT_ACTION);
        String target=node.getAttribute(ATT_TARGET);
        try
        {
            URL url=new URL(this.getDocumentBase(),action);
            //System.out.println("url:"+url);
            if(target!=null)
                this.getAppletContext().showDocument(url,target);
            else   
                this.getAppletContext().showDocument(url);
        }catch(Exception e){e.printStackTrace(System.out);}            
    }
    
    private String getData(String xml)
    {
        StringBuffer ret=new StringBuffer();
        try
        {
            //URL gurl=new URL(this.url,cgi);
            URLConnection urlconn=url.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            //System.out.println("JSESSIONID="+jsess);
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
                //System.out.println(inputLine);
            }
            in.close();
        }catch(Exception e){System.out.println("Error to open service..."+e);}
        return ret.toString();
    }    

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jMenuBar1 = new javax.swing.JMenuBar();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        jMenuBar1.setBackground(new java.awt.Color(224, 231, 220));

        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.X_AXIS));

        setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(224, 231, 220));
        jPanel2.setDoubleBuffered(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setDoubleBuffered(false);
        jPanel1.setOpaque(false);
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(203, 24));
        jPanel3.setPreferredSize(new java.awt.Dimension(203, 24));
        jPanel3.setEnabled(false);
        jPanel3.setOpaque(false);
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/generictree/images/wb3.gif")));
        jButton3.setAlignmentY(0.0F);
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setDoubleBuffered(true);
        jButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel3.add(jButton3, "card2");

        jPanel1.add(jPanel3, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel1, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel2);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
    
}
