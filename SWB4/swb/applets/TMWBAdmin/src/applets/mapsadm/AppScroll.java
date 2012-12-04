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
 * AppObject.java
 *
 * Created on 9 de noviembre de 2001, 19:12
 */
package applets.mapsadm;

import java.awt.*;
import java.util.*;
/**
 *
 * @author  Administrador
 * @version
 */
public class AppScroll
{
    public int max;
    public int min;
    public float pos;
    public int x,y;
    public int h,w=10;
    public AppObject topic;
    public Vector ltopics;
    public int topicType;
    private Graphics g;
    boolean visible=false;
    Color c=new Color(45,145,239);
    public int maxX,maxY;

    private boolean drag=false;
    private int dx,dy;                      //drag posicion inicial
    private float dpos;                     //drag posicion inicial
    private int hfont;                      //height font

    public AppScroll(int x,int y,int w,int h,Graphics g)
    {
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.g=g;
        hfont=g.getFontMetrics().getHeight()+1;
    }

    public void draw()
    {
        int tx[]={x+w/2,x+w,x};
        int ty[]={y-w,y,y};
        int tx2[]={x,x+w,x+w/2};
        int ty2[]={y+h,y+h,y+h+w};
        if(!visible)return;
        float ax=(float)h/max;
        g.setColor(c);
        g.drawRect(x,y-w,w,h+w*2);
        //g.setColor(c);
        //g.fillRect(x,Math.round(y+(max-min-pos)*ax),w,Math.round(min*ax));
        g.fillRect(x,Math.round(y+pos*ax),w,Math.round(min*ax));
        g.fillPolygon(tx,ty,3);
        g.fillPolygon(tx2,ty2,3);
    }

    public void redistribute()
    {
        int axc=0;
        int h=hfont;//g.getFontMetrics().getHeight()+1;
        if(topicType==0)
        {
            int w=maxX/3;
            int x=w/2+2*w,y=h/2;
            if(ltopics==null)return;
            Enumeration ass=ltopics.elements();
            while(ass.hasMoreElements())
            {
                AppObject auxass=(AppObject)ass.nextElement();
                if(axc>=Math.round(pos))
                {
                    if(y<=maxY/2)
                    {
                        auxass.go(x-this.w,y);
                        auxass.maxW=w-this.w;
                        auxass.setVisible(true);
                    }else{
                        auxass.setVisible(false);
                        auxass.go(maxX/2,maxY/2);
                    }
                    y+=h;
                }else{
                    auxass.setVisible(false);
                    auxass.go(maxX/2,maxY/2);
                }
                axc++;
            }
        }
        else if(topicType==1)
        {
            int w=maxX/3;
            int x=w/2,y=h/2;
            Enumeration ass=ltopics.elements();
            while(ass.hasMoreElements())
            {
                AppObject auxass=(AppObject)ass.nextElement();
                if(axc>=Math.round(pos))
                {
                    if(y<=maxY/2)
                    {
                        auxass.go(x+this.w,y);
                        auxass.maxW=w-this.w;
                        auxass.setVisible(true);
                    }else{
                        auxass.setVisible(false);
                        auxass.go(maxX/2,maxY/2);
                    }
                    y+=h;
                }else{
                    auxass.setVisible(false);
                    auxass.go(maxX/2,maxY/2);
                }
                axc++;
            }
        }
        else if(topicType==2)
        {
            int w=maxX/4;
            int x=w/2,y=maxY/2+h;
            y=3*(maxY/4)+15;
            y=y-Math.round(min*((float)h/2));
            Enumeration ass=ltopics.elements();
            while(ass.hasMoreElements())
            {
                AppObject auxass=(AppObject)ass.nextElement();
                if(axc/4>=Math.round(pos))
                {
                    if(y<maxY-h/2)
                    {
                        auxass.go(x-this.w,y);
                        auxass.maxW=w-this.w;
                        auxass.setVisible(true);
                    }else
                    {
                        auxass.setVisible(false);
                        auxass.go(maxX/2,maxY/2);
                    }
                    x+=w;
                    if(x>maxX)
                    {
                        x=w/2;
                        y+=h;
                    }
                }else{
                    auxass.setVisible(false);
                    auxass.go(maxX/2,maxY/2);
                }
                axc++;
            }
        }
    }

    public void click(int cx,int cy)
    {
        float ax=(float)h/max;
        if(cx>x&&cx<x+w&&cy>y-w&&cy<y&&pos>0)
        {
            pos--;
            redistribute();
        }
        if(cx>x&&cx<x+w&&cy>y+h&&cy<y+h+w&&pos<max-min)
        {
            pos++;
            redistribute();
        }
        if(cx>x&&cx<x+w&&cy>y&&cy<y+h)
        {
            if(cy<Math.round(y+pos*ax))
            {
                pos=pos-min;
            }
            if(cy>Math.round(y+(pos+min)*ax))
            {
                pos=pos+min;
            }
            if(pos<0)pos=0;
            if(pos>max-min)pos=max-min;
            redistribute();
        }
    }

    public void drag(int cx,int cy)
    {
        float ax=(float)h/max;
        if((cx>=x&&cx<=x+w&&cy>=Math.round(y+pos*ax)&&cy<=Math.round(y+(pos+min)*ax))||drag)
        {
            if(!drag)
            {
                drag=true;
                dx=cx;
                dy=cy;
                dpos=pos;
            }else
            {
                pos=dpos+((cy-dy)/ax);
                if(pos<0)pos=0;
                if(pos>max-min)pos=max-min;
                redistribute();
            }
        }
    }

    public void move(int cx,int cy)
    {
        if(drag)drag=false;
    }
}
