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
 * JActivityFlowPanel.java
 *
 * Created on 9 de noviembre de 2004, 09:48 PM
 */

package applets.workflowadmin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
/**
 * Clase uqe maneja graficamente las actividades, esta clase esta asociada con el
 * modelo de actividades gr�ficas y con ActiviG.
 * @author Victor Lorenzana
 */
public class JActivityFlowPanel extends JPanel implements MouseListener,MouseMotionListener{
    
    /** Creates a new instance of JActivityFlowPanel */
    jTableActivitiesModel model;    
    JTable table;
    ActivityGModel modelg=null;
    Locale locale;
    String activityName;
    public JActivityFlowPanel(jTableActivitiesModel model,JTable table,Locale locale,String  activityName,boolean edit) {
       this.locale=locale;
       modelg=new ActivityGModel(locale);
       this.model=model;     
       this.table=table;                   
       this.activityName=activityName;
       if(this.activityName==null)
       {
           this.activityName="";           
       }
       if(edit)
       {
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
       }
       
    }
     public JActivityFlowPanel(jTableActivitiesModel model,JTable table,Locale locale) {
       this(model, table, locale,null,true);       
    }
    public void paint(Graphics g)
    {   
        modelg.clear();        
        table.setModel(modelg);        
        
        this.setBackground(Color.WHITE);
        g.clearRect(0,0,this.getWidth(), this.getHeight());
        int width=((this.model.size()+2)*100);
        int step=1;
        Color green=new Color(0,102,51);
        this.setPreferredSize(new Dimension(width,300));
        Color blue=new Color(0,153,204);
        Color red=new Color(255,51,51);
        int y=40;         
        int x=50;        
        ActivityG init=new ActivityG(x, y,new AuthorActivity(locale),String.valueOf(step),"inicio.gif");        
        modelg.addActivity(init);
        if(model.size()>0)
        {
            g.setColor(green);
            g.drawArc(init.getX()+25, init.getY()-25, 100, 50, 0, 180);
        }
        init.setColor(blue);        
        for(int i=1;i<this.model.size()+1;i++)
        {
            step++;
            x=(100*i)+50;            
            Activity activity=this.model.getActivity(i-1);
            if(activity.getName().equals(this.activityName))
            {
                g.setColor(new Color(0,128,128));
                //g.drawRect(x,y, 40, 50);
                Graphics2D g2d = ( Graphics2D ) g;
                g2d.fill(new Rectangle2D.Float(x+5,y, 40,50));
                g2d.fill(new Rectangle2D.Float(10,140, 10,10));
                g.drawString(java.util.ResourceBundle.getBundle("applets/workflowadmin/JActivityFlowPanel",locale).getString("leyend"),30,150);
            }
            ActivityG act=new ActivityG(x, y,activity,String.valueOf(step),"paso.gif");
            modelg.addActivity(act);            
            act.setColor(new Color(51,102,255));
        }
        step++;
        x=(100*(this.model.size()+1))+50;      
        ActivityG fin=new ActivityG(x, y,new EndActivity(locale),String.valueOf(step),"final.gif");
        modelg.addActivity(fin);
        fin.setColor(red);        
        Iterator it=modelg.iterator();
        while(it.hasNext())
        {
            ActivityG act=(ActivityG)it.next();
            act.paint(g);
            Iterator links=act.getLinks().iterator();
            while(links.hasNext())
            {
                Link link=(Link)links.next();                
                Color color=green;
                if(!link.getType().equals("authorized"))
                {
                    color=red;
                }                
                Activity to=link.getActivityTo();
                Iterator activities=modelg.iterator();
                while(activities.hasNext())
                {
                    ActivityG gto=(ActivityG)activities.next();
                    if(gto.equals(to))
                    {
                        g.setColor(color);
                        if(color==red)
                        {
                            if((gto.getX()-act.getX())>0)
                            {
                                g.drawArc(act.getX()+25, act.getY()+25, gto.getX()-act.getX(), 50, 0, -180);
                                
                            }
                            else
                            {
                                g.drawArc(gto.getX()+25, gto.getY()+25, act.getX()-gto.getX(), 50, 0, -180);
                            }
                        }
                        else
                        {
                            if((gto.getX()-act.getX())>0)
                            {
                                g.drawArc(act.getX()+25, act.getY()-25, gto.getX()-act.getX(), 50, 0, 180);                                
                                //g.drawLine(gto.getX()+25, gto.getY()-25, 0, 0);
                            }
                            else
                            {
                                g.drawArc(gto.getX()+25, gto.getY()-25, act.getX()-gto.getX(), 50, 0, 180);
                            }
                        }
                    }
                }
            }
        }
        this.table.getColumnModel().getColumn(0).setWidth(50);       
        this.table.getColumnModel().getColumn(0).setMinWidth(50);
        this.table.getColumnModel().getColumn(0).setMaxWidth(50);
        this.table.updateUI();
    }
    
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==e.BUTTON1 && e.getClickCount()==2)
        {
           int l=this.modelg.getRowCount();
           for(int i=0;i<l;i++)
           {
               ActivityG activityg=this.modelg.getActivity(i);
               if(activityg.has(e.getX(),e.getY()))
               {
                    Activity activity=activityg.getActivity();
                    if(!(activity instanceof EndActivity) && !(activity instanceof AuthorActivity))
                    {
                        ActivityEdition frm=new ActivityEdition(activity,this.model,locale);
                        frm.setModal(true);
                        frm.setLocation(200,200);
                        frm.setSize(500,400);
                        frm.show(); 
                        this.updateUI();
                        this.table.updateUI();
                    }                   
               }
           }
        }
    }    
    
    public void mouseEntered(MouseEvent e) {
    }    
    
    public void mouseExited(MouseEvent e) {
    }    
       
    public void mousePressed(MouseEvent e) {
    }    
    
    public void mouseReleased(MouseEvent e) {
    }    
    
    public void mouseDragged(MouseEvent e) {
    }    
    
    public void mouseMoved(MouseEvent e) {
       int l=this.modelg.getRowCount();
       for(int i=0;i<l;i++)
       {
           ActivityG activityg=this.modelg.getActivity(i);
           if(activityg.has(e.getX(),e.getY()))
           {
                Activity activity=activityg.getActivity();
                if(!(activity instanceof EndActivity) && !(activity instanceof AuthorActivity))
                {                   
                    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    return;
                }                                   
           }
       }
       this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
}
