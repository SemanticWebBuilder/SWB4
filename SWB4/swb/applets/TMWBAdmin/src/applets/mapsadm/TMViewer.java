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
 * TMViewer.java
 *
 * Created on 15 de octubre de 2002, 18:04
 */

package applets.mapsadm;

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

import applets.commons.*;

/**
 *
 * @author  Administrador
 */
public class TMViewer extends JPanel implements Runnable
{
    private Thread thread;
    //private FontRenderContext frc=null;
    private JApplet parent;
    private TMWBContainer adm=null;

    protected String topicMap="";
    protected String home="";
    private boolean dead=false;
    protected boolean candraw=true;
    protected boolean rendering=false;
    protected boolean netscape=false;
    
    protected boolean showDocument=false;
    
    private Locale locale=Locale.getDefault();

    protected BufferedImage buffer;
    protected Graphics2D pad;
    //protected Image buffer;
    //protected Graphics pad;
    protected BufferedImage backbuffer;
    private Image back;
    private Image back2;
    private int backW;
    private int backH;
    private int back2W;
    private int back2H;

    private Font forig;                 //original font;
    private int hfont;                  //height font;

    protected Hashtable obj;
    protected Vector objSort;
    private Hashtable objrel;
    private Vector objup;
    private Vector objleft;
    private Vector objright;
    private Vector objdown;
    private Vector objroles;

    protected AppObject actual=null;
    protected AppObject actualchange=null;
    protected boolean changeactual=false;
    protected AppObject over;

    protected AppScroll scrLeft;
    protected AppScroll scrRight;
    protected AppScroll scrDown;
    private int srcw=12;

    protected int maxX,maxY;
    private int bufmaxX=1920,bufmaxY=1200;

    private final String backgroundParam = "background";
    private final String foregroundParam = "foreground";

    private Color cass=new Color(119,174,77);
    private Color coverass=new Color(119,174,77);

    private boolean cursor=false;
    protected boolean changes=false;

    protected int velmove=15;                       //numero de frames de movimiento de cambio
    protected int velmovefade=30;                   //numero de frames de movimiento de cambio

    private String cgi="/wb/";
    private int userhits;
    
    private AlphaComposite composite=null;
    private AlphaComposite composite2=null;
    private Composite composite_orig=null;
    private Stroke dashStroke=null;
    private Stroke normStroke=null;
    private Stroke lineStroke=null;
    
    private String urlbase="";
    private String urlpost="";    
    
    //admin
    AppObject edit,auxedit;
    
    /** Creates a new instance of TMViewer */
    public TMViewer(JApplet parent, Locale locale)
    {
        this.locale=locale;
        this.parent=parent;
        if(parent instanceof TMWBContainer)adm=(TMWBContainer)parent;
    }

    public void init()
    {
        obj=new Hashtable();
        objSort=new Vector();
        
        //base=getDocumentBase().getFile().substring(0,getDocumentBase().getFile().lastIndexOf('/'));
        //cgi=base+cgi;
        //System.out.println("Init");
        String s = System.getProperty("java.vendor");
        if(s.startsWith("Netscape"))netscape=true;

        maxX=this.getSize().width;
        maxY=this.getSize().height;

        initComponents();
        usePageParams();
        requestFocus();
        
        //buffer=(BufferedImage)this.createImage(bufmaxX,bufmaxY);
        //pad=buffer.getGraphics();
        pad=createGraphics2D(bufmaxX,bufmaxY);
        //frc = pad.getFontRenderContext();
        
        forig=new Font("Dialog",0,16);
        //forig=new Font("arial",Font.PLAIN,15);
        
        pad.setFont(forig);
        hfont=pad.getFontMetrics().getHeight()+1;
        
        composite_orig=pad.getComposite();
        composite=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        composite2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);

        Component object;

        scrLeft=new AppScroll(0,0,0,0,pad);
        scrRight=new AppScroll(0,0,0,0,pad);
        scrDown=new AppScroll(0,0,0,0,pad);
        
        back=getToolkit().getImage(getClass().getResource("images/grafico.gif"));
        back2=getToolkit().getImage(getClass().getResource("images/logo.gif"));
        MediaTracker mediatracker = new MediaTracker(this);
        mediatracker.addImage(back, 0);
        mediatracker.addImage(back2, 0);

        //getMap();
        if(obj.size()==0)
        {
            AppObject aux=new AppObject("WBHome","WebBuilder");
            aux.setGraphics(pad);
            aux.setComposite(composite_orig, composite, composite2);
            aux.setFont(pad.getFont());
            aux.move(maxX/2,maxY/2);
            obj.put("WBHome",aux);
            aux.setName("WebBuilder");
            aux.setVisible(false);
        }
        //setActual(home);
        try
        {
            mediatracker.waitForAll();
        }
        catch(Exception _ex) { }
        backW=back.getWidth(this);
        backH=back.getHeight(this);
        back2W=back2.getWidth(this);
        back2H=back2.getHeight(this);
        
        createBackImage();
        pad.drawImage(backbuffer,0,0,this);
    }

    
    public void setActual(String id)
    {
        //System.out.println("setActual2:"+id);
        AppObject aux=(AppObject)obj.get(id);
        if(aux==actual&&aux!=null)return;
        if(aux!=null)
        {
            if(actual!=null)actual.actual=false;
            actualchange=aux;
            changeactual=true;
            //actual=aux;
            aux.actual=true;
            //showDocument(cgi+aux.id+"?hit="+(userhits++));
            //showDocument(cgi+actual.id);
            //organizeMap();
        }else if(actual==null)
        {
            actual=(AppObject)obj.get(home);
            if(actual==null)actual=(AppObject)obj.elements().nextElement();
            actual.actual=true;
            //showDocument(cgi+actual.id+"?hit="+(userhits++));
            //showDocument(cgi+actual.id);
            organizeMap();
        }
        //System.out.println(actual.name);
    }

    public boolean hasTopic(Vector v,AppObject t)
    {
        Enumeration en=v.elements();
        while(en.hasMoreElements())
        {
            if(((AppAssoc)en.nextElement()).topic==t)return true;
        }
        return false;
    }

    public void initTopic(AppObject Topic)
    {
        Topic.setGraphics(pad);
        Topic.setComposite(composite_orig, composite, composite2);
        Topic.netscape=netscape;
        Topic.setFont(forig);
        Topic.move(maxX/2,maxY/2);
        Topic.go(maxX/2,maxY/2);
        Topic.setVisible(false);
        Topic.nf=velmove;
        Topic.ncf=velmovefade;
        Topic.over=false;
        Topic.actual=false;
    }

/*
    public void getMap()
    {
        obj=new Hashtable();
        objSort=new Vector();
        pad.setFont(this.forig);
        URL dir;
        BufferedReader in=null;
        try{
            dir=new URL(adm.url,"/getMap2.jsp");
            //TMWBAdmin.class.getResource("/getMap.jsp");
            //System.out.println(dir);
            in=new java.io.BufferedReader(new java.io.InputStreamReader(dir.openStream(),"UTF-8"));
            //in=new java.io.BufferedReader(new java.io.InputStreamReader(dir.openStream()));
            if(in!=null)
            {
                String aux;
                boolean topicmap=false;
                boolean topic=false;
                boolean assoc=false;
                boolean isnew=false;
                boolean bad=false;
                AppObject auxtopic=null;
                int asstype=0;

                //System.out.println("posx:"+maxX+" posy:"+maxY);
                int posx=maxX/2;
                int posy=maxY/2;
                int nass=0;
                AppObject mem[]=new AppObject[2];
                String assName="";
                String assTypeId="";
                String assRole[]=new String[2];
                while((aux=in.readLine())!=null)
                {
                    //System.out.println(aux);
                    if(aux.equals("TopicMap"))
                    {
                        topicmap=true;
                        topic=false;
                        assoc=false;
                        isnew=true;
                    }else if(aux.equals("Topic"))
                    {
                        topic=true;
                        assoc=false;
                        isnew=true;
                    }else if(aux.equals("Association"))
                    {
                        topic=false;
                        assoc=true;
                        isnew=true;
                        assName="";
                        assTypeId="";
                    }else if(aux.equals("cgi"))
                    {
                        cgi=in.readLine();
                    }
                    else isnew=false;

                    if(topic&&(!isnew))
                    {
                        if(aux.startsWith("i:"))
                        {
                            if(!aux.substring(2).equals("null"))
                            {
                                bad=false;
                                //System.out.println(aux.substring(2));
                                auxtopic = new AppObject(aux.substring(2));
                                initTopic(auxtopic);
                                obj.put(aux.substring(2),auxtopic);
                            }else bad=true;
                        }
                        else if(aux.startsWith("d:")&&!bad)
                        {
                            if(!aux.substring(2).equals("null"))
                                auxtopic.setName(WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                            else
                                auxtopic.setName("Topic");
                            //System.out.println(auxtopic.getName());

                        }
                        else if(aux.startsWith("a:")&&!bad)
                        {
                            try
                            {
                                auxtopic.setActive(Integer.parseInt(aux.substring(2)));
                            }catch(Exception e){}
                        }
                        else if(aux.startsWith("r:")&&!bad)
                        {
                            try
                            {
                                auxtopic.setBorrado(Integer.parseInt(aux.substring(2)));
                            }catch(Exception e){}
                        }
                        else if(aux.startsWith("n:")&&!bad)
                        {
                           auxtopic.names.addElement("n:"+WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                        }
                        else if(aux.startsWith("s:")&&!bad)
                        {
                           auxtopic.names.addElement("s:"+aux.substring(2));
                        }
                        else if(aux.startsWith("v:")&&!bad)
                        {
                           auxtopic.names.addElement("v:"+WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                        }
                    }

                    if(assoc&&(!isnew))
                    {
                        if(aux.startsWith("t:"))
                        {
                            bad=false;
                            nass=0;
                            if(!aux.substring(2).equals("null"))
                            {
                                assTypeId=aux.substring(2);
                                asstype=3;
                            }else
                            {
                                asstype=0;
                                assTypeId="";
                            }
                        }
                        if(aux.startsWith("n:"))
                        {
                            if(!aux.substring(2).equals("null"))
                            {
                                assName=aux.substring(2);
                            }else
                            {
                                assName="";
                            }
                        }
                        else if(aux.startsWith("r:"))
                        {
                            if(!aux.substring(2).equals("null"))
                            {
                                assRole[nass]=aux.substring(2);
                            }else assRole[nass]="";
                        }
                        else if(aux.startsWith("p:"))
                        {
                            mem[nass]=(AppObject)obj.get(aux.substring(2));
                            if(mem[nass]==null)bad=true;
                            nass++;
                            if(nass==2&&!bad)
                            {
                                if(asstype==0)
                                {
                                    if(!hasTopic(mem[0].assoc,mem[1]))mem[0].assoc.addElement(new AppAssoc(mem[1],2,assTypeId,assName,assRole[1],assRole[0]));
                                }
                                else
                                {
                                    if(!hasTopic(mem[0].assoc,mem[1]))mem[0].assoc.addElement(new AppAssoc(mem[1],1,assTypeId,assName,assRole[1],assRole[0]));
                                }
                                if(!hasTopic(mem[1].assoc,mem[0]))mem[1].assoc.addElement(new AppAssoc(mem[0],asstype,assTypeId,assName,assRole[0],assRole[1]));

                                if(assTypeId.length()>0)
                                {
                                    //AppObject rel=(AppObject)obj.get(assTypeId);
                                    //if(!hasTopic(rel.assoc,mem[0]))rel.assoc.addElement(new AppAssoc(mem[0],2,assTypeId,assName,assRole[0]));
                                    //if(!hasTopic(rel.assoc,mem[1]))rel.assoc.addElement(new AppAssoc(mem[1],2,assTypeId,assName,assRole[1]));
                                }
                            }
                        }
                    }

                }
            }
        }catch(Exception ex){ex.printStackTrace(System.out);}
        //Sort Associations
        sortTopics();
    }
*/
    
    public void getMap(String data)
    {
        //System.out.println("getMap2:"+this.getActual());
        obj=new Hashtable();
        objSort=new Vector();
        pad.setFont(this.forig);
        URL dir;
        BufferedReader in=null;
        try{
            in=new BufferedReader(new java.io.InputStreamReader(new ByteArrayInputStream(data.getBytes()),"UTF-8"));
            if(in!=null)
            {
                String aux;
                boolean topicmap=false;
                boolean topic=false;
                boolean assoc=false;
                boolean isnew=false;
                boolean bad=false;
                AppObject auxtopic=null;
                int asstype=0;

                //System.out.println("posx:"+maxX+" posy:"+maxY);
                int posx=maxX/2;
                int posy=maxY/2;
                int nass=0;
                AppObject mem[]=new AppObject[2];
                String assName="";
                String assTypeId="";
                String assRole[]=new String[2];
                while((aux=in.readLine())!=null)
                {
                    //System.out.println(aux);
                    if(aux.equals("TopicMap"))
                    {
                        topicmap=true;
                        topic=false;
                        assoc=false;
                        isnew=true;
                    }else if(aux.equals("Topic"))
                    {
                        topic=true;
                        assoc=false;
                        isnew=true;
                    }else if(aux.equals("Association"))
                    {
                        topic=false;
                        assoc=true;
                        isnew=true;
                        assName="";
                        assTypeId="";
                    }else if(aux.equals("cgi"))
                    {
                        cgi=in.readLine();
                    }
                    else isnew=false;

                    if(topic&&(!isnew))
                    {
                        if(aux.startsWith("i:"))
                        {
                            if(!aux.substring(2).equals("null"))
                            {
                                bad=false;
                                //System.out.println(aux.substring(2));
                                auxtopic = new AppObject(aux.substring(2));
                                initTopic(auxtopic);
                                obj.put(aux.substring(2),auxtopic);
                            }else bad=true;
                        }
                        else if(aux.startsWith("u:")&&!bad)
                        {
                            if(!aux.substring(2).equals("null"))
                                auxtopic.setUrl(WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                        }                        
                        else if(aux.startsWith("d:")&&!bad)
                        {
                            if(!aux.substring(2).equals("null"))
                                auxtopic.setName(WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                            else
                                auxtopic.setName("Topic");
                            //System.out.println(auxtopic.getName());

                        }
                        else if(aux.startsWith("a:")&&!bad)
                        {
                            try
                            {
                                auxtopic.setActive(Integer.parseInt(aux.substring(2)));
                            }catch(Exception e){}
                            //System.out.println(auxtopic.getName());
                        }
                        else if(aux.startsWith("r:")&&!bad)
                        {
                            try
                            {
                                auxtopic.setBorrado(Integer.parseInt(aux.substring(2)));
                            }catch(Exception e){}
                        }
                        else if(aux.startsWith("n:")&&!bad)
                        {
                           //System.out.println(aux);
                           auxtopic.names.addElement("n:"+WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                        }
                        else if(aux.startsWith("s:")&&!bad)
                        {
                           auxtopic.names.addElement("s:"+aux.substring(2));
                        }
                        else if(aux.startsWith("v:")&&!bad)
                        {
                           auxtopic.names.addElement("v:"+WBXMLParser.replaceAmpTags(new StringBuffer(aux.substring(2))));
                        }
                    }

                    if(assoc&&(!isnew))
                    {
                        if(aux.startsWith("t:"))
                        {
                            bad=false;
                            nass=0;
                            if(!aux.substring(2).equals("null"))
                            {
                                assTypeId=aux.substring(2);
                                asstype=3;
                            }else
                            {
                                asstype=0;
                                assTypeId="";
                            }
                        }
                        if(aux.startsWith("n:"))
                        {
                            if(!aux.substring(2).equals("null"))
                            {
                                assName=aux.substring(2);
                            }else
                            {
                                assName="";
                            }
                        }
                        else if(aux.startsWith("r:"))
                        {
                            if(!aux.substring(2).equals("null"))
                            {
                                assRole[nass]=aux.substring(2);
                            }else assRole[nass]="";
                        }
                        else if(aux.startsWith("p:"))
                        {
                            mem[nass]=(AppObject)obj.get(aux.substring(2));
                            if(mem[nass]==null)bad=true;
                            nass++;
                            if(nass==2&&!bad)
                            {
                                if(asstype==0)
                                {
                                    if(!hasTopic(mem[0].assoc,mem[1]))mem[0].assoc.addElement(new AppAssoc(mem[1],2,assTypeId,assName,assRole[1],assRole[0]));
                                }
                                else
                                {
                                    if(!hasTopic(mem[0].assoc,mem[1]))mem[0].assoc.addElement(new AppAssoc(mem[1],1,assTypeId,assName,assRole[1],assRole[0]));
                                }
                                if(!hasTopic(mem[1].assoc,mem[0]))mem[1].assoc.addElement(new AppAssoc(mem[0],asstype,assTypeId,assName,assRole[0],assRole[1]));

                                if(assTypeId.length()>0)
                                {
                                    //AppObject rel=(AppObject)obj.get(assTypeId);
                                    //if(!hasTopic(rel.assoc,mem[0]))rel.assoc.addElement(new AppAssoc(mem[0],2,assTypeId,assName,assRole[0]));
                                    //if(!hasTopic(rel.assoc,mem[1]))rel.assoc.addElement(new AppAssoc(mem[1],2,assTypeId,assName,assRole[1]));
                                }
                            }
                        }
                    }

                }
            }
        }catch(Exception ex){ex.printStackTrace(System.out);}
        
        //Sort Associations
        sortTopics();
    }
    
    

    public synchronized void organizeMap()
    {
        //System.out.println("organizeMap:"+this.actual);
        if(dead||obj==null)return;
        objrel=new Hashtable();
        objleft=new Vector();
        objright=new Vector();
        objup=new Vector();
        objdown=new Vector();
        objroles=new Vector();

        pad.setFont(forig);
        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            auxobj.setVisible(false);
            auxobj.go(maxX/2,maxY/2);
            auxobj.maxX=maxX;
            auxobj.maxY=maxY;
            auxobj.assname="";
            auxobj.assnameAct="";
            auxobj.over=false;
            auxobj.type=0;
        }

        //System.out.println("organizeMap2:"+this.actual);
        actual.go(maxX/2,maxY/2);
        actual.setVisible(true);

        Enumeration ass=actual.assoc.elements();
        while(ass.hasMoreElements())
        {
            AppAssoc auxass=(AppAssoc)ass.nextElement();
            auxass.topic.setVisible(true);
            if(auxass.role.length()>0)auxass.topic.assname=((AppObject)obj.get(auxass.role)).name;
            if(auxass.relrole.length()>0)auxass.topic.assnameAct=((AppObject)obj.get(auxass.relrole)).name;
            //auxass.topic.assname=auxass.name;
            //if(auxass.role.length()>0)auxass.topic.assname+=":"+auxass.role;
            if(auxass.type==1||auxass.type==3)
            {
                if(!objrel.containsKey(obj.get(auxass.idtype)))
                {
                    AppObject rel=(AppObject)obj.get(auxass.idtype);
                    if(rel!=null)
                    {
                        Vector v=new Vector();
                        objrel.put(rel,v);
                        v.addElement(auxass.topic);
                        rel.type=1;
                        rel.setVisible(true);
                        if(objright.contains(auxass.topic))
                        {
                            objright.remove(auxass.topic);
                        }
                    }
                }else
                {
                    Vector v=(Vector)objrel.get(obj.get(auxass.idtype));
                    v.addElement(auxass.topic);
                }
            }
            else if(auxass.type==2)
            {
                if(!objdown.contains(auxass.topic))objdown.addElement(auxass.topic);
            }
            else if(auxass.type==0)
            {
                if(!objup.contains(auxass.topic))objup.addElement(auxass.topic);
                Enumeration bro=auxass.topic.assoc.elements();
                while(bro.hasMoreElements())
                {
                    AppAssoc auxass2=(AppAssoc)bro.nextElement();
                    if(auxass2.topic!=actual&&auxass2.type==2)
                    {
                        if(!objright.contains(auxass2.topic) && auxass2.topic.visible==false)
                        {
                            objright.addElement(auxass2.topic);
                            auxass2.topic.setVisible(true);
                            //System.out.println(auxass2.topic.name);
                        }
                    }
                }
            }

        }

        Enumeration erx=obj.elements();
        while(erx.hasMoreElements())
        {
            AppObject x=(AppObject)erx.nextElement();
            Enumeration eay=x.assoc.elements();
            while(eay.hasMoreElements())
            {
                AppAssoc y=(AppAssoc)eay.nextElement();
                if(obj.get(y.relrole)==actual)
                {
                    if(!objdown.contains(x))
                    {
                        objdown.addElement(x);
                        //objroles.addElement(x);
                        x.setVisible(true);
                    }
                }
                if(obj.get(y.idtype)==actual)
                {
                    if(obj.get(y.role)!=null&&!objdown.contains(obj.get(y.role)))
                    {
                        objdown.addElement(obj.get(y.role));
                        objroles.addElement(obj.get(y.role));
                        ((AppObject)obj.get(y.role)).setVisible(true);
                    }
                }
            }
        }

        //System.out.println("tp:"+tp0+" tr:"+tr);
        Enumeration ex=objrel.keys();
        while(ex.hasMoreElements())
        {
            AppObject k=(AppObject)ex.nextElement();
            objleft.addElement(k);
            Enumeration ey=((Vector)objrel.get(k)).elements();
            while(ey.hasMoreElements())
            {
                AppObject j=(AppObject)ey.nextElement();
                objleft.addElement(j);
            }
        }

        //orgUp(objup.size(),objleft.size());
        //orgRel(objrel.size());
        orgRight(objright.size());
        orgLeft(objleft.size());
        orgUp(objup.size(),0);
        orgDown(objdown.size());
        
        if(adm!=null)
        {
            adm.mapChange();
        }
    }

    public void orgDown(int n)
    {
        if(n>0)
        {
            //int y=h/2;
            //int min=((maxY/2+h/2-1)/h);
            //if(n<min)y=h/2+(min-n)*h/2;
            //if((n*h-h/2)>maxY/2)

            int w=maxX/4;
            if(n<4)w=maxX/n;
            int h=hfont;//pad.getFontMetrics().getHeight()+1;
            int x=w/2,y=3*(maxY/4)+15;

            if((((n+3)/4)*h)>Math.round((float)maxY/2-(float)h/2))
            {
                scrDown.max=(n+3)/4;
                scrDown.min=Math.round(((float)maxY/2-(float)h/2-h)/h);
                scrDown.x=maxX-srcw-1;
                scrDown.y=maxY/2+2*srcw;
                scrDown.w=srcw;
                scrDown.h=maxY/2-3*srcw;
                scrDown.pos=0;
                scrDown.topic=actual;
                scrDown.ltopics=objdown;
                scrDown.topicType=2;
                scrDown.maxX=maxX;
                scrDown.maxY=maxY;
                y=y-Math.round(scrDown.min*((float)h/2));
                scrDown.visible=true;
            }else
            {
                y=y-Math.round(((n+3)/4)*((float)h/2));
                scrDown.visible=false;
            }

            Enumeration ass=objdown.elements();
            while(ass.hasMoreElements())
            {
                AppObject topic=(AppObject)ass.nextElement();
                {
                    if(y<maxY-h/2)
                    {
                        if(scrDown.visible)
                        {
                            topic.go(x-srcw,y);
                            topic.maxW=w-srcw;
                        }
                        else
                        {
                            topic.go(x,y);
                            topic.maxW=w;
                        }
                    }else
                    {
                        topic.setVisible(false);
                        topic.go(maxX/2,maxY/2);
                    }
                    x+=w;
                    if(x>maxX)
                    {
                        x=w/2;
                        y+=h;
                    }
                }
            }
        }else
        {
            scrDown.visible=false;
        }
    }

    public void orgUp(int n,int r)
    {
        if(n>0)
        {
            int h=hfont;//pad.getFontMetrics().getHeight()+1;
            int w;
            int x;
            if(r==0)
            {
                if(n==1)
                {
                    w=maxX/3;
                    x=maxX/2;
                }
                else
                {
                    w=maxX/4;
                    x=w+w/2;
                }
            }else
            {
                w=maxX/4;;
                x=2*w+w/2;
            }
            int y=((maxY/2-(n+1)/2*h)/2)+(n+1)/2*h;

            Enumeration ass=objup.elements();
            while(ass.hasMoreElements())
            {
                AppObject topic=(AppObject)ass.nextElement();
                {
                    topic.go(x,y);
                    topic.maxW=w;
                    if(r==0)
                    {
                        x+=w;
                        if(x+w>maxX)
                        {
                            x=w+w/2;
                            y-=h;
                        }
                    }else
                    {
                        y-=h;
                    }
                }
            }
        }
    }

    public void orgRel(int n)
    {
        if(n>0)
        {
            int w=maxX/4;
            int h=hfont;//pad.getFontMetrics().getHeight()+1;
            int x=w+w/2,y=((maxY/2-(n+1)/2*h)/2)+(n+1)/2*h;

            Enumeration ass=objrel.elements();
            while(ass.hasMoreElements())
            {
                AppObject rel=(AppObject)ass.nextElement();
                if(rel!=null)
                {
                    rel.maxW=w;
                    rel.go(x,y);
                    y-=h;
                }
            }
        }
    }

    public void orgLeft(int n)
    {
        if(n>0)
        {
            int w=maxX/3;
            int h=hfont;//pad.getFontMetrics().getHeight()+1;
            int x=w/2;
            int y=h/2;
            int min=((maxY/2+h/2-1)/h);
            if(n<min)y=h/2+(min-n)*h/2;

            if((n*h-h/2)>maxY/2)
            {
                scrLeft.max=n;
                scrLeft.min=min;
                scrLeft.x=0;
                scrLeft.y=srcw;
                scrLeft.w=srcw;
                scrLeft.h=maxY/2-srcw;
                scrLeft.pos=0;
                scrLeft.topic=actual;
                scrLeft.ltopics=objleft;
                scrLeft.topicType=1;
                scrLeft.maxX=maxX;
                scrLeft.maxY=maxY;
                scrLeft.visible=true;
            }else
            {
                scrLeft.visible=false;
            }


            Enumeration ass=objleft.elements();
            while(ass.hasMoreElements())
            {
                AppObject topic=(AppObject)ass.nextElement();
                {
                    if(y<=maxY/2)
                    {
                        if(scrLeft.visible)
                        {
                            topic.go(x+srcw,y);
                            topic.maxW=w-srcw;
                        }
                        else
                        {
                            topic.go(x,y);
                            topic.maxW=w;
                        }
                    }else{
                        topic.setVisible(false);
                        topic.go(maxX/2,maxY/2);
                    }
                    y+=h;
                }
            }
        }
        else scrLeft.visible=false;
    }

    public void orgRight(int n)
    {
        if(n>0)
        {
            int w=maxX/3;
            int h=hfont;//pad.getFontMetrics().getHeight()+1;
            int x=w/2+2*w;
            int y=h/2;
            int min=((maxY/2+h/2-1)/h);
            if(n<min)y=h/2+(min-n)*h/2;

            if((n*h-h/2)>maxY/2)
            {
                scrRight.max=n;
                scrRight.min=min;
                scrRight.x=maxX-srcw-1;
                scrRight.y=srcw;
                scrRight.w=srcw;
                scrRight.h=maxY/2-srcw;
                scrRight.pos=0;
                scrRight.topic=actual;
                scrRight.ltopics=objright;
                scrRight.topicType=0;
                scrRight.maxX=maxX;
                scrRight.maxY=maxY;
                scrRight.visible=true;
            }else
            {
                scrRight.visible=false;
            }

            Enumeration ass=objright.elements();
            while(ass.hasMoreElements())
            {
                AppObject topic=(AppObject)ass.nextElement();
                if(y<=maxY/2)
                {
                    if(scrRight.visible)
                    {
                        topic.go(x-srcw,y);
                        topic.maxW=w-srcw;
                    }
                    else
                    {
                        topic.go(x,y);
                        topic.maxW=w;
                    }
                }else{
                    topic.setVisible(false);
                    topic.go(maxX/2,maxY/2);
                }
                y+=h;
            }
        }else
        {
            scrRight.visible=false;
        }
    }

    public void drawRelation(AppObject inicio,AppObject fin)
    {
        if(!inicio.visible||!fin.visible)return;
        int dx1=0,dy1=0;
        int dx2=0,dy2=0;
        if(Math.round(fin.y)<=maxY/2)
        {
            if(fin.x<maxX/3+3||fin.x>(maxX/3)*2-3)
            {
                if(Math.round(inicio.x)<Math.round(fin.x))
                {
                    dx1=inicio.w/2;
                    dx2=-fin.w/2;
                }
                if(Math.round(inicio.x)>Math.round(fin.x))
                {
                    dx1=-inicio.w/2;
                    dx2=fin.w/2;
                }
            }else
            {
                dy1=-inicio.h/2;
                dy2=fin.h/2;
            }
        }else
        {
            if(Math.round(inicio.y)>maxY/2)
            {
                if(Math.round(inicio.x)<Math.round(fin.x))
                {
                    dx1=inicio.w/2;
                    dx2=-fin.w/2;
                }
                if(Math.round(inicio.x)>Math.round(fin.x))
                {
                    dx1=-inicio.w/2;
                    dx2=fin.w/2;
                }
            }
            else
            {
                dy1=inicio.h/2;
                dy2=-fin.h/2;
            }
        }
        if(inicio.over || fin.over)
        {
            pad.setColor(coverass);
            //Composite compaux=pad.getComposite();
            Stroke strokeaux=pad.getStroke();
            //pad.setComposite(composite_orig);
            pad.setStroke(lineStroke);
            pad.drawLine(Math.round(inicio.x)+dx1,Math.round(inicio.y)+dy1,Math.round(fin.x)+dx2,Math.round(fin.y)+dy2);
            //pad.setComposite(compaux);
            pad.setStroke(strokeaux);
        }else
        {
            pad.setColor(cass);
            pad.drawLine(Math.round(inicio.x)+dx1,Math.round(inicio.y)+dy1,Math.round(fin.x)+dx2,Math.round(fin.y)+dy2);
        }
        
        
    }

    
    public boolean move()
    {
        boolean changes=false;
        
        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            if(auxobj.move())changes=true;
            //if(auxobj.over)changes=true;
        }

        //if(actual!=null)actual.move();
        //if(over!=null)over.move();
        
        return changes;
    }
    
    
    public boolean render()
    {
        rendering=true;
        //System.out.println("render:"+actual);
        boolean changes=false;
        
        pad.setComposite(composite_orig);
        //pad.setComposite(composite);
        
        if(backbuffer==null)pad.clearRect(0,0,maxX,maxY);
        else 
        {
            pad.drawImage(backbuffer,0,0,this);
/*            
            if(backH<maxY)
            {
                pad.clearRect(backW,backH,maxX,maxY-backH);
            }
            if(backH<maxY)
            {
                pad.clearRect(0,0,backW,maxY-backH);
                pad.drawImage(back,0,maxY-backH,this);
            }
            else
            {
                pad.drawImage(back,0,0,this);
            }
            if(backW<maxX)
            {
                if((backW+back2W)<maxX)
                {
                    pad.clearRect(backW,0,maxX-backW-back2W,backH);
                    pad.drawImage(back2,maxX-back2W,0,this);
                }else
                {
                    pad.drawImage(back2,backW,0,this);
                }
            }
*/
        }
        
        pad.setComposite(composite);
        pad.setStroke(dashStroke);
        Enumeration ass=actual.assoc.elements();
        while(ass.hasMoreElements())
        {
            AppAssoc auxass=(AppAssoc)ass.nextElement();
            if(auxass.topic.visible)
                drawRelation(actual,auxass.topic);
        }

        Enumeration topic=obj.elements();
        while(topic.hasMoreElements())
        {
            AppObject auxtopic=(AppObject)topic.nextElement();
            if(auxtopic.visible)
            {
                ass=auxtopic.assoc.elements();
                while(ass.hasMoreElements())
                {
                    AppAssoc auxass=(AppAssoc)ass.nextElement();
                    if(auxass.type==1||auxass.type==2)
                    {
                        AppObject auxtopic2=auxass.topic;
                        if(auxtopic2.visible)
                        {
                            if(auxtopic2!=actual)
                                drawRelation(auxtopic,auxtopic2);
                        }
                    }
                }
            }
        }

        topic=objdown.elements();
        while(topic.hasMoreElements())
        {
            AppObject auxtopic=(AppObject)topic.nextElement();
            if(auxtopic.visible)
            {
                ass=auxtopic.assoc.elements();
                while(ass.hasMoreElements())
                {
                    AppAssoc auxass=(AppAssoc)ass.nextElement();
                    if(obj.get(auxass.relrole)==actual)
                        drawRelation(actual,auxtopic);
                }
            }
        }

        topic=objroles.elements();
        while(topic.hasMoreElements())
        {
            AppObject auxtopic=(AppObject)topic.nextElement();
            drawRelation(actual,auxtopic);
        }

        pad.setStroke(normStroke);
        
        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            //if(actual!=auxobj && over!=auxobj)
            {
                if(auxobj.show())changes=true;
            }
        }

        pad.setComposite(composite);

        scrLeft.draw();
        scrRight.draw();
        scrDown.draw();
        
        if(actual!=null)actual.show();
        if(over!=null)over.show();
        
        pad.setComposite(composite);

        it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            auxobj.showAss(actual);
        }
        rendering=false;
        return changes;
    }

    private void usePageParams()
    {
        final String defaultLabel = "Default label";
        final String defaultBackground = "C0C0C0";
        final String defaultForeground = "000000";
        String backgroundValue;
        String foregroundValue;

        home = parent.getParameter("Home");
        /**
         * Read the <PARAM NAME="label" VALUE="some string">,
         * <PARAM NAME="background" VALUE="rrggbb">,
         * and <PARAM NAME="foreground" VALUE="rrggbb"> tags from
         * the applet's HTML host.
        */
        backgroundValue = parent.getParameter(backgroundParam);
        foregroundValue = parent.getParameter(foregroundParam);

        if ((backgroundValue == null) || (foregroundValue == null))
        {
            /**
             * There was something wrong with the HTML host tags.
             * Generate default values.
             */
            backgroundValue = defaultBackground;
            foregroundValue = defaultForeground;
        }

        /**
         * Set the applet's string label, background color, and
         * foreground colors.
         */
        this.setBackground(stringToColor(backgroundValue));
        this.setForeground(stringToColor(foregroundValue));
    }

    /**
    * Converts a string formatted as "rrggbb" to an awt.Color object
    */
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
    
/*
    public String[][] getParameterInfo()
    {
        String[][] info =
        {
            { labelParam, "String", "Label string to be displayed" },
            { backgroundParam, "String", "Background color, format \"rrggbb\"" },
            { foregroundParam, "String", "Foreground color, format \"rrggbb\"" },
        };
        return info;
    }
*/
    void initComponents()
    {
        this.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                mouseClick(evt);
            }
            public void mousePressed(MouseEvent evt)
            {
                mousePress(evt);
            }
            public void mouseReleased(MouseEvent evt)
            {
                mouseRelease(evt);
            }
        }
        );
        this.addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseMoved(MouseEvent evt)
            {
                formMouseMoved(evt);
            }
            public void mouseDragged(MouseEvent evt)
            {
                formMouseDragged(evt);
            }
        }
        );
        this.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt)
            {
                formResized(evt);
            }
        }
        );
    }
    
    private void mouseClick(java.awt.event.MouseEvent evt)
    {
/*
        int x=evt.getX();
        int y=evt.getY();

        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            if(auxobj.inSide(x,y)&&auxobj.visible)
            {
                actual.actual=false;
                actual=auxobj;
                actual.actual=true;
                showDocument(cgi+actual.id+"?hit="+(userhits++));
                organizeMap();
                //System.out.println("x:"+actual.x+" y:"+actual.y);
            }
        }
        scrLeft.click(x,y);
        scrRight.click(x,y);
        scrDown.click(x,y);
 */
    }

    public void formMouseMoved(java.awt.event.MouseEvent evt)
    {

        int x=evt.getX();
        int y=evt.getY();

        boolean auxcursor=false;

        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            if(auxobj.inSide(x,y)&&auxobj.visible)
            {
                auxobj.over=true;
                over=auxobj;
                auxcursor=true;
            }else
            {
                auxobj.over=false;
            }
        }

        scrLeft.move(x,y);
        scrRight.move(x,y);
        scrDown.move(x,y);

        if(auxcursor&&!cursor)
        {
            cursor=true;
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        else if(!auxcursor&&cursor)
        {
            if(this.getCursor().getType()==Cursor.HAND_CURSOR)
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            cursor=false;
        }

    }

    protected void formMouseDragged(java.awt.event.MouseEvent evt)
    {
        int x=evt.getX();
        int y=evt.getY();
        scrLeft.drag(x,y);
        scrRight.drag(x,y);
        scrDown.drag(x,y);
    }
    
    public void showDocument(String doc)
    {
        showDocument(adm.getDocumentBase(),doc);
    }    

    public void showDocument(URL url,String doc)
    {
        if(!showDocument)return;
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            adm.getAppletContext().showDocument(new URL(url,doc),"content");
        }catch (MalformedURLException e){}
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void mousePress(java.awt.event.MouseEvent evt)
    {
        boolean pressed=false;
        if((evt.getModifiers()&evt.BUTTON1_MASK)>0)
        {
            int x=evt.getX();
            int y=evt.getY();

            Enumeration it=obj.elements();
            while(it.hasMoreElements())
            {
                AppObject auxobj=(AppObject)it.nextElement();
                if(auxobj.inSide(x,y)&&auxobj.visible)
                {
                    auxobj.clicked=true;
                    adm.setAuxDrag(auxobj);
                    pressed=true;
                }
            }
            if(!pressed)adm.setAuxDrag(null);
            scrLeft.click(x,y);
            scrRight.click(x,y);
            scrDown.click(x,y);
        }
    }

    public void mouseRelease(java.awt.event.MouseEvent evt)
    {
        //System.out.println("mouseRelease");
        if((evt.getModifiers()&evt.BUTTON3_MASK)>0)
        {
            int x=evt.getX();
            int y=evt.getY();

            auxedit=null;
            Enumeration it=obj.elements();
            while(it.hasMoreElements())
            {
                AppObject auxobj=(AppObject)it.nextElement();
                if(auxobj.inSide(x,y)&&auxobj.visible)
                {
                    auxedit=auxobj;
                    break;
                }
            }
            
            if(actual==null && adm!=null)
            {
                adm.getTopicMenu().disable();
            }
            else
            {
                adm.getTopicMenu().enable();
                if(adm!=null)
                {
                    if(auxedit==null || adm.isEditing())
                    {
                        adm.getTopicMenu().getItem(0).disable();
                        adm.getTopicMenu().getItem(1).disable();
                        adm.getTopicMenu().getItem(2).disable();
                        adm.getTopicMenu().getItem(3).disable();
                        adm.getTopicMenu().getItem(8).disable();
                        adm.getTopicMenu().getItem(9).disable();
                        adm.getTopicMenu().getItem(10).disable();
                        if(adm.isEditing())
                        {
                            adm.getTopicMenu().getItem(12).disable();
                            adm.getTopicMenu().getItem(13).disable();
                        }else
                        {
                            adm.getTopicMenu().getItem(12).enable();
                            adm.getTopicMenu().getItem(13).enable();
                        }
                    }else
                    {
                        if(auxedit.active==0)
                        {
                            ((CheckboxMenuItem)adm.getTopicMenu().getItem(3)).setState(false);
                            ((CheckboxMenuItem)adm.getTopicMenu().getItem(3)).setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.active"));
                        }else
                        {
                            ((CheckboxMenuItem)adm.getTopicMenu().getItem(3)).setState(true);
                            ((CheckboxMenuItem)adm.getTopicMenu().getItem(3)).setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.deactive"));
                        }

                        if(auxedit.borrado==1)
                        {
                            adm.getTopicMenu().getItem(0).disable();
                            adm.getTopicMenu().getItem(1).disable();
                            adm.getTopicMenu().getItem(2).disable();
                            adm.getTopicMenu().getItem(3).disable();
                            adm.getTopicMenu().getItem(8).setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.restoreDelete"));
                        }
                        else
                        {
                            adm.getTopicMenu().getItem(0).enable();
                            adm.getTopicMenu().getItem(1).enable();
                            adm.getTopicMenu().getItem(2).enable();
                            adm.getTopicMenu().getItem(3).enable();
                            adm.getTopicMenu().getItem(8).setLabel(java.util.ResourceBundle.getBundle("applets/mapsadm/locale",locale).getString("mnu.markDelete"));
                        }
                        adm.getTopicMenu().getItem(8).enable();
                        //adm.getTopicMenu().getItem(9).enable();
                        //adm.getTopicMenu().getItem(10).enable();
                    }
                }
            }
            adm.getTopicMenu().show(this,evt.getX(),evt.getY());
        }
        
        if((evt.getModifiers()&evt.BUTTON1_MASK)>0)
        {
            int x=evt.getX();
            int y=evt.getY();

            Enumeration it=obj.elements();
            while(it.hasMoreElements())
            {
                AppObject auxobj=(AppObject)it.nextElement();
                if(auxobj.inSide(x,y)&&auxobj.visible)
                {
                    if(auxobj.clicked==true)
                    {
                        //showDocument(cgi+auxobj.id+"?hit="+(userhits++));
                        if(auxobj.getUrl()==null)
                        {
                            showDocument(urlbase+cgi+auxobj.id+urlpost);
                        }else
                        {
                            if(auxobj.getUrl().indexOf("://")>-1)
                            {
                                showDocument(auxobj.getUrl());
                            }else
                            {
                                showDocument(urlbase+auxobj.getUrl()+urlpost);
                            }
                        }
                        actual.actual=false;
                        actualchange=auxobj;
                        changeactual=true;
                        //actual=auxobj;
                        auxobj.actual=true;
                        //organizeMap();
                        //System.out.println("x:"+actual.x+" y:"+actual.y);
                    }
                    auxobj.clicked=false;
                }
            }
        }
    }

    private void formResized(java.awt.event.ComponentEvent evt)
    {
        candraw=false;
        Component cp=evt.getComponent();
        maxX=cp.getSize().width;
        maxY=cp.getSize().height;
        if(actual!=null)organizeMap();
        createBackImage();
        //pad=createGraphics2D(maxX, maxY);
        
        if(actual==null)
        {
            pad.drawImage(backbuffer,0,0,this);
            candraw=true;
            repaint();
        }
        candraw=true;
        
        //System.out.println("formResized: X="+maxX+" Y="+maxY);
        
    }
/*    
    private void formResized(int w,int h)
    {
        //System.out.println("formResized");
        candraw=false;
        maxX=w;
        maxY=h;
        organizeMap();
        candraw=true;
    } 
*/
    private void quicksort(Vector a)
    {
        quicksort(a,0,a.size()-1);
    }

    protected void quicksort2(Vector a)
    {
        quicksort2(a,0,a.size()-1);
    }

    private void quicksort(Vector a, int low, int high)
    {
        if (low >= high) return;
        int left = low;
        int right = high;
        AppAssoc pivot = (AppAssoc)a.elementAt((low+high)/2);
        do{
            while(((AppAssoc)a.elementAt(left)).topic.name.compareToIgnoreCase(pivot.topic.name)<0)left++;
            while(((AppAssoc)a.elementAt(right)).topic.name.compareToIgnoreCase(pivot.topic.name)>0)right--;
            if (left <= right)
            {
                AppAssoc temp = (AppAssoc)a.elementAt(right);
                a.setElementAt(a.elementAt(left),right--);
                a.setElementAt(temp,left++);
            }
        }
        while (left <= right);
        if (low < right) quicksort(a,low,right);
        if (high > left) quicksort(a,left,high);
    }

    protected void quicksort2(Vector a, int low, int high)
    {
        if (low >= high) return;
        int left = low;
        int right = high;
        AppObject pivot = (AppObject)a.elementAt((low+high)/2);
        do{
            while(((AppObject)a.elementAt(left)).name.toLowerCase().compareToIgnoreCase(pivot.name.toLowerCase())<0)left++;
            while(((AppObject)a.elementAt(right)).name.toLowerCase().compareToIgnoreCase(pivot.name.toLowerCase())>0)right--;
            if (left <= right)
            {
                AppObject temp = (AppObject)a.elementAt(right);
                a.setElementAt(a.elementAt(left),right--);
                a.setElementAt(temp,left++);
            }
        }
        while (left <= right);
        if (low < right) quicksort2(a,low,right);
        if (high > left) quicksort2(a,left,high);
    }
    

    public void sortTopics()
    {
        objSort=new Vector();
        
        //Sort Associations
        Enumeration it=obj.elements();
        while(it.hasMoreElements())
        {
            AppObject auxobj=(AppObject)it.nextElement();
            objSort.addElement(auxobj);

//            System.out.println(auxobj.getName());
            
            Vector vec=auxobj.assoc;
            int z=vec.size();
            int y=0;
            for(int x=z-1;x>=y;x--)
            {
                AppAssoc ass=(AppAssoc)vec.get(x);
                if(ass.type==0)
                {
                    vec.remove(x);
                    vec.add(0, ass);
                    y++;
                    x++;
                }
            }
/*            
            Iterator it2=auxobj.assoc.iterator();
            while(it2.hasNext())
            {
                AppAssoc ass=(AppAssoc)it2.next();
                System.out.println("t:"+ass.type+" "+ass.topic.getName());
            }
*/            
            quicksort(auxobj.assoc,y,auxobj.assoc.size()-1);
            //quicksort(auxobj.assoc);
        }
        quicksort2(objSort);
    }

    public void run()
    {
        //System.out.println("Run");
        long newtime,t,dif=30;
        int frames=0;
        int delay=30;
        
        int count=0;
        boolean repaint=true;
        boolean moving=false;

        //newtime = System.currentTimeMillis() + 1000L;
        t=System.currentTimeMillis();

        while(!dead)
        {
            /*
            dif=t-System.currentTimeMillis();
            try
            {
                t+=delay;
                thread.sleep(Math.max(0,dif));
                if(dif<0)
                {
                    repaint=false;
                    //System.out.print("O");
                }
                else repaint=true;
            } catch (InterruptedException e) 
            {
                break;
            }
             **/
            if(!moving)dif=100;
            else dif=5;
            try {thread.sleep(dif);}catch(InterruptedException e) {break;}
            if(changeactual)
            {
                //System.out.println("changeActual...");
                actual=actualchange;
                changeactual=false;
                organizeMap();
            }
            if(actual!=null)
            {
                if((candraw && repaint && moving) || count>50)
                {
                    moving=render();
                }else
                {
                    if(!moving)
                    {
                        moving=move();
                    }else
                    {
                        move();
                    }
                }
                if(moving || count>50)
                {
                    //render();
                    if(candraw && repaint)repaint();
                    if(moving || count>50)count=0;
                }
                count++;
            }//else System.out.println(candraw+" "+actual);
        }
    }
 
    
    public void update(Graphics g)
    {
        //paint(g);
    }

    public void paint(Graphics g)
    {
        if(!rendering)
            g.drawImage(buffer,0,0,this);
        //else System.out.print("X");
    }
    
    public void start()
    {
        //System.out.println("Start");
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
        if(dead)dead = false;
        if(pad==null)init();
    }

    public void stop()
    {
        //System.out.println("Stop");
        dead = true;
        if (thread != null) {
            thread.interrupted();
            thread.stop();
            thread=null;
        }
    }    
    
    /** Getter for property actual.
     * @return Value of property actual.
     */
    public AppObject getActual()
    {
        return actual;
    }
    
    /** Setter for property actual.
     * @param actual New value of property actual.
     */
    public void setActual(AppObject actual)
    {
        actualchange=actual;
        changeactual=true;
        //System.out.println("setActual1:"+actual);
        //this.actual = actual;
    }
    
    public void removeAss(AppObject topic1,AppObject topic2)
    {
        Enumeration en=topic1.assoc.elements();
        while(en.hasMoreElements())
        {
            AppAssoc as=(AppAssoc)en.nextElement();
            if(as.topic==topic2)topic1.assoc.remove(as);
        }
        en=topic2.assoc.elements();
        while(en.hasMoreElements())
        {
            AppAssoc as=(AppAssoc)en.nextElement();
            if(as.topic==topic1)topic2.assoc.remove(as);
        }
    }
    
    

    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        buffer = (BufferedImage) createImage(w, h);
        g2 = buffer.createGraphics();
        g2.setBackground(getBackground());
        g2.clearRect(0, 0, w, h);

        normStroke=new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        lineStroke=new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        dashStroke=new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{3.1f}, 0.0f);
        
        //g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        //g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{0,6,0,6}, 0));
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        //g2.setComposite(AlphaComposite.SrcOver);
        //g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return g2;
    }
    
    public void createBackImage() {
        Graphics2D g2 = null;
        backbuffer = (BufferedImage) createImage(maxX, maxY);
        g2 = backbuffer.createGraphics();
        g2.setBackground(getBackground());
        g2.clearRect(0, 0, maxX, maxY);
        //g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          //g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
          //g2.setComposite(AlphaComposite.SrcOver);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.clearRect(0,0,maxX,maxY);
        if(back!=null) 
        {
            for(int x=0;x<8;x++)
            {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.20f+(float)Math.random()*.5f));
                float s=(float)(Math.random()*1.2+0.2);
                g2.drawImage(back,(int)(Math.random()*maxX)-(int)(s*backW/2),(int)(Math.random()*maxY)-(int)(s*backH/2),(int)(s*backW),(int)(s*backH),this);
            }
            //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
            //g2.drawImage(back,0,maxY-backH,this);
            //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            //g2.drawImage(back,80,maxY-backH-30,this);
            //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            //g2.drawImage(back,160,maxY-backH-60,this);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            float s=2f;
            g2.drawImage(back2,(int)(maxX-back2W/s)/2,5,(int)(back2W/s),(int)(back2H/s),this);
        }
        
    }
    
    /**
     * Getter for property showDocument.
     * @return Value of property showDocument.
     */
    public boolean isShowDocument()
    {
        return showDocument;
    }    
 
    /**
     * Setter for property showDocument.
     * @param showDocument New value of property showDocument.
     */
    public void setShowDocument(boolean showDocument)
    {
        this.showDocument = showDocument;
    }    
    
    /**
     * Getter for property urlbase.
     * @return Value of property urlbase.
     */
    public java.lang.String getUrlBase()
    {
        return urlbase;
    }
    
    /**
     * Setter for property urlbase.
     * @param urlbase New value of property urlbase.
     */
    public void setUrlBase(java.lang.String urlbase)
    {
        if(urlbase!=null)this.urlbase = urlbase;
    }
    
    /**
     * Getter for property urlpost.
     * @return Value of property urlpost.
     */
    public java.lang.String getUrlPost()
    {
        return urlpost;
    }
    
    /**
     * Setter for property urlpost.
     * @param urlpost New value of property urlpost.
     */
    public void setUrlPost(java.lang.String urlpost)
    {
        if(urlpost!=null)this.urlpost = urlpost;
    }
    
/*
    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(10);
            } catch (InterruptedException e) { break; }
        }
        thread = null;
    }
    
        public void drawDemo(int w, int h, Graphics2D g2) {
            render();
            g2.drawImage(buffer,0,0,this);
            g2.setColor(Color.red);
            g2.drawLine(0,0,w,h);
        }
    
    
        public Graphics2D createGraphics2D(int w, int h) {
            Graphics2D g2 = null;
            if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
                bimg = (BufferedImage) createImage(w, h);
            } 
            g2 = bimg.createGraphics();
            g2.setBackground(getBackground());
            g2.clearRect(0, 0, w, h);
            //g2.drawImage(back,0,0,this);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);
            return g2;
        }
    
    
        public void paint(Graphics g) {
            Dimension d = getSize();
            if(d.width!=maxX || d.height!=maxY)formResized(d.width,d.height);
            //Graphics2D g2 = createGraphics2D(d.width, d.height);
            //drawDemo(d.width, d.height, g2);
            //g2.dispose();
            //g.drawImage(bimg, 0, 0, this);
            render();
            g.drawImage(buffer, 0, 0, this);
        }
*/
    
}
