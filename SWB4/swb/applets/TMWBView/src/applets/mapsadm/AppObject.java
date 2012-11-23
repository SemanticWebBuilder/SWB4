/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
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
public class AppObject
{
    public float x,y;
    public int nx,ny;
    public float incx;
    public float incy;
    public int rx,ry;
    public int w,h,fos;
    public int type=0;
    public int maxX,maxY;           //tam�o del applet
    public int maxW;                //tama�o maximo del objeto
    public boolean over=false;      //mouse sobre el objecto
    public int ifr,ifg,ifb;                //incrementos en colores
    public int ibr,ibg,ibb;                //incrementos en colores
    public int isf=0,wsf;                      //incremento y tama�o del scroll del fade
    public Color cfonta;                    //color de fuente topico activo
    public Color cfontd;                    //color de fuente topico desactivo
    public Color cfontr;                    //color de fuente topico borrado
    public Color cfontb;                    //color de fuente por defecto
    public Color coverfont;
    public Color cactfont;
    public Color cauxfont;
    public Color coverback;
    public Color cback;
    public Color cauxback;
    public Color cract;
    public Color crover;
    public boolean visible=false;   //visible object
    public boolean actual=false;   //visible object
    public boolean clicked=false;   //visible object
    public boolean netscape=false;
    
    public int olg=6;   //espacio entre el circulo y el texto


    public int ncf=30;             //numero de frames del fade
    public float nf=15;             //numero de frames para el movimiento


    public Vector assoc=new Vector();
    public String assname;
    public String assnameAct;

    private Graphics2D g;
    private Font f;       //font
    
    private Composite composite=null;
    private Composite composite2=null;
    private Composite composite_orig=null;
    
    private int fosize;   //original font size
    private float fasize;   //actual font size
    public String name;
    public String id;
    public Vector names;
    public int active=2;
    public int borrado=0;
    public String url=null;
    

    /** Creates new AppObject */
    public AppObject(String id)
    {
        this.id=id;
        name="Topic";
        names=new Vector();
        x=0;
        y=0;

        cfonta=new Color(134,185,203);
        cfontd=new Color(134,185,203);
        cfontr=new Color(150,150,150);
        cfontb=new Color(255,255,255);
        coverfont=new Color(119,174,77);
        cactfont=new Color(63,136,180);
        cauxfont=cfontd;
        coverback=new Color(255,255,255);
        cback=new Color(61,126,187);
        cauxback=cback;
        cract=new Color(63,136,180);
        crover=new Color(119,174,77);
    }

    public AppObject(String id,String name)
    {
        this.id=id;
        this.name=name;
        names=new Vector();
    }

    public String toString()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
        setFont(f);
    }

    public void setGraphics(Graphics2D g)
    {
        this.g=g;
        f=g.getFont();
    }
    
    public void setComposite(Composite normal, Composite special, Composite special2)
    {
        composite=special;
        composite_orig=normal;
        composite2=special2;
    }

    public void setFontSize(int size)
    {
        //System.out.println("setFontSize:"+size+"name"+name);
        f=new Font(f.getFontName(),0,size);
        //w=g.getFontMetrics(f).stringWidth(name);
        w=getWidth(f);
        h=g.getFontMetrics(f).getHeight();
        fos=g.getFontMetrics(f).getDescent()+g.getFontMetrics(f).getLeading();
        move(x,y);
    }

    public void setFont(Font f)
    {
        //System.out.println("setFont:"+f+" name:"+name+" size="+f.getSize()+" maxW:"+maxW);
        this.f=f;
        fosize=f.getSize();
        fasize=f.getSize();
        //w=g.getFontMetrics(f).stringWidth(name);
        w=getWidth(f);
        h=g.getFontMetrics(f).getHeight();
        fos=g.getFontMetrics(f).getDescent()+g.getFontMetrics(f).getLeading();
        
        //System.out.println("w="+w+" h="+h+" x="+x+" y="+y+" name="+name);
        
        move(x,y);
    }

    public void setVisible(boolean visible)
    {
        //System.out.println("setVisible:"+name);
        //w=g.getFontMetrics(f).stringWidth(name);
        w=getWidth(f);
        h=g.getFontMetrics(f).getHeight();
        this.visible=visible;
    }

    public void go(int x,int y)
    {
        this.nx=x;
        this.ny=y;
        incx=((float)nx-this.x)/nf;
        incy=((float)ny-this.y)/nf;
    }

    public void move(float x,float y)
    {
        this.x=x;
        this.y=y;
        rx=(int)x-w/2;
        ry=(int)y-h/2;
    }

    public void show(int x,int y)
    {
        move(x,y);
        if(over||actual)showOver();
        else show();
    }

    public Color fadeColor(Color aux,Color to,int incr,int incg,int incb)
    {
        //System.out.println("1->incr:"+incr+" incg:"+incg+" incb:"+incb);
        if(aux!=to)
        {
            int r,g,b,tr,tg,tb;
            r=aux.getRed();
            g=aux.getGreen();
            b=aux.getBlue();
            tr=to.getRed();
            tg=to.getGreen();
            tb=to.getBlue();
            if(r!=tr)
            {
                r+=incr;
                if(Math.abs(tr-r)<Math.abs(incr)||incr==0)r=tr;
            }
            if(g!=tg)
            {
                g+=incg;
                if(Math.abs(tg-g)<Math.abs(incg)||incg==0)g=tg;
            }
            if(b!=tb)
            {
                b+=incb;
                if(Math.abs(tb-b)<Math.abs(incb)||incb==0)b=tb;
            }
            //System.out.println("2->r:"+r+" g:"+g+" b:"+b+" tr:"+tr+" tg:"+tg+" tb:"+tb);
            return new Color(r,g,b);
        }else return aux;
    }

    public void setOverBackColor()
    {
        Color cback;
        if(borrado==1)
        {
            cback=cfontr;
            g.setComposite(composite);            
        }
        else if(active==1)
        {
            cback=cfonta;
            g.setComposite(composite_orig);            
        }
        else if(active==0)
        {
            cback=cfontd;
            g.setComposite(composite);            
        }
        else cback=cfontb;        
        
        g.setColor(coverback);
        if(cauxback!=coverback)
        {
            g.setColor(coverback);
            cauxback=coverback;
            ibr=(cback.getRed()-coverback.getRed())/ncf;
            //if(ibr==0)ibr=1;
            ibg=(cback.getGreen()-coverback.getGreen())/ncf;
            //if(ibg==0)ibg=1;
            ibb=(cback.getBlue()-coverback.getBlue())/ncf;
            //if(ibb==0)ibb=1;
            //System.out.println("ir:"+ibr+" ig:"+ibg+" ib:"+ibb);
            isf=w/ncf+1;
            wsf=w;
        }
    }

    public void setOverFontColor()
    {
        Color cfont;
        if(borrado==1)cfont=cfontr;
        else if(active==1)cfont=cfonta;
        else if(active==0)cfont=cfontd;
        else cfont=cfontb;
        
        if(over)
        {
            g.setColor(coverfont);
            if(cauxfont!=coverfont)
            {
                cauxfont=coverfont;
                ifr=(cfont.getRed()-coverfont.getRed())/(ncf*2);
                //if(ifr==0)ifr=1;
                ifg=(cfont.getGreen()-coverfont.getGreen())/(ncf*2);
                //if(ifg==0)ifg=1;
                ifb=(cfont.getBlue()-coverfont.getBlue())/(ncf*2);
                //if(ifb==0)ifb=1;
                //System.out.println("ir:"+ifr+" ig:"+ifg+" ib:"+ifb);
            }
        }else
        {
            g.setColor(cactfont);
            if(cauxfont!=cactfont)
            {
                cauxfont=cactfont;
                ifr=(cfont.getRed()-cactfont.getRed())/(ncf*2);
                ifg=(cfont.getGreen()-cactfont.getGreen())/(ncf*2);
                ifb=(cfont.getBlue()-cactfont.getBlue())/(ncf*2);
            }            
        }
    }

    public void drawName(int x,int y,Color c)
    {
        g.setFont(f);
        g.setColor(c);
        try
        {
            g.drawString(name,x-w/2,y+fos);
        }catch(java.lang.IllegalArgumentException e ){System.out.println("Error in DrawString:"+e);}
    }
    
    public int getWidth(Font f)
    {
        int w=g.getFontMetrics(f).stringWidth(name);
        int n=0;
        while(w>maxW && maxW>0)
        {
            n++;
            if(name.length()-n>0)
                w=g.getFontMetrics(f).stringWidth(name.substring(0,name.length()-n)+"...");
            else w=maxW;
        }        
        return w;
    }
    
    public int getWidth(int olg)
    {
        int w=g.getFontMetrics().stringWidth(name)+olg;
        int n=0;
        while(w>maxW && maxW>0)
        {
            n++;
            if(name.length()-n>0)
                w=g.getFontMetrics().stringWidth(name.substring(0,name.length()-n)+"...")+olg;
            else w=maxW;
        }        
        return w;
    }

    public boolean drawName()
    {
        //System.out.println("drawName:"+name);
        boolean changes=false;
        if(fasize==1)return false;

        if(type==1)g.setFont(new Font(f.getName(),3,f.getSize()));
        else g.setFont(f);

        w=getWidth(olg);
        rx=(int)x-w/2;
        
        g.setComposite(composite);

        Color cfont;
        if(borrado==1)cfont=cfontr;
        else if(active==1)
        {
            cfont=cfonta;
            g.setComposite(composite_orig);
        }
        else if(active==0)cfont=cfontd;
        else cfont=cfontb;        
        
        if(visible && wsf>0)
        {
            g.setColor(cauxback);
            //g.fillRoundRect(rx,ry,wsf,h/2,2,2);
            //g.fillRoundRect(rx+w-wsf,ry+h/2,wsf,h/2,2,2);
            g.fillRoundRect(rx,ry,w,h,h/2,h/2);
            if(cauxback.getRGB()!=cfont.getRGB())
            {
                cauxback=fadeColor(cauxback,cfont,ibr,ibg,ibb);
            }else
            {
                wsf-=isf;
            }
            changes=true;
        }else
        {
            g.setColor(cfont);
            g.fillRoundRect(rx,ry,w,h,h/2,h/2);
        }

        g.setColor(coverback);
        //if(active==1)g.setColor(Color.WHITE);
        //else if(active==0)g.setColor(new Color(255,200,200));
        
        g.setComposite(composite_orig);
 
/*        
        if(cauxfont.getRGB()!=cfont.getRGB())
        {
            //System.out.println("cauxfont:"+cauxfont+" cfont:"+cfont);
            cauxfont=fadeColor(cauxfont,cfont,ifr,ifg,ifb);
        }
*/ 
        try{
            
            int aw=g.getFontMetrics().stringWidth(name)+olg;
            int n=0;
            while(aw>maxW)
            {
                n++;
                if(name.length()-n>0)
                    aw=g.getFontMetrics().stringWidth(name.substring(0,name.length()-n)+"...")+olg;
                else aw=maxW;
            }                    
            
            if(n>0)
                if(name.length()-n>0)
                    g.drawString(name.substring(0,name.length()-n)+"...",rx+olg/2,ry+h-fos);
                else
                    g.drawString("...",rx+olg/2,ry+h-fos);
            else
                g.drawString(name,rx+olg/2,ry+h-fos);
        }catch(java.lang.IllegalArgumentException e ){System.out.println("Error in DrawString:"+e);}
        return changes;
    }

    public boolean move()
    {
        boolean moving=false;
        if(Math.round(x)!=nx || Math.round(y)!=ny)
        {
            if(Math.round(x)!=nx)x+=incx;
            if(Math.round(y)!=ny)y+=incy;
            move(x,y);
            moving=true;
        }        

        if(over||actual)
        {
            //showOver();
            if(over)return true;
        }

        if(visible)
        {
            if(fasize<fosize)
            {
                fasize+=1;
                if(!netscape)setFontSize((int)fasize);
            }
            if(visible && wsf>0)
            {
                Color cfont;
                if(borrado==1)cfont=cfontr;
                else if(active==1)cfont=cfonta;
                else if(active==0)cfont=cfontd;
                else cfont=cfontb;        
                if(cauxback.getRGB()!=cfont.getRGB())
                {
                    cauxback=fadeColor(cauxback,cfont,ibr,ibg,ibb);
                }else
                {
                    wsf-=isf;
                }
                //moving=true;
            }            
        }
        else
        {
            if(moving)
            {
                if(fasize>1)
                {
                    fasize-=1;
                    if(!netscape)setFontSize((int)fasize);
                }
                if(visible && wsf>0)
                {
                    Color cfont;
                    if(borrado==1)cfont=cfontr;
                    else if(active==1)cfont=cfonta;
                    else if(active==0)cfont=cfontd;
                    else cfont=cfontb;        
                    if(cauxback.getRGB()!=cfont.getRGB())
                    {
                        cauxback=fadeColor(cauxback,cfont,ibr,ibg,ibb);
                    }else
                    {
                        wsf-=isf;
                    }
                    //moving=true;
                }            
            }
        }
      
        return moving;
    }
    
    public boolean show()
    {
        boolean changes=false;
        boolean moving=false;

        if(Math.round(x)!=nx || Math.round(y)!=ny)
        {
            if(Math.round(x)!=nx)x+=incx;
            if(Math.round(y)!=ny)y+=incy;
            move(x,y);
            moving=true;
        }        
        
        if(over||actual||type==1)
        {
            if(!(type==1 && !visible))showOver();
            if(over)return true;
            else return false;
        }

        if(visible)
        {
            if(fasize<fosize)
            {
                fasize+=1;
                if(!netscape)setFontSize((int)fasize);
            }
            if(drawName())changes=true;
        }
        else
        {
            if(moving)
            {
                if(fasize>1)
                {
                    fasize-=1;
                    if(!netscape)setFontSize((int)fasize);
                }
                if(drawName())changes=true;
            }
        }
        
        return changes||moving;
    }

    public void showOver()
    {
        //System.out.println("showOver:"+name);
        if(fasize<fosize)
        {
            fasize+=1;
            if(!netscape)setFontSize((int)fasize);
        }
        if(type==1 && visible)
        {
            g.setFont(new Font(f.getFontName(),3,(int)fasize));
        }
        else g.setFont(f);
        int w=g.getFontMetrics().stringWidth(name)+olg;
        int rx=this.rx;
        if(rx+w>maxX)rx=maxX-w;

        Color cfont;
        if(borrado==1)cfont=cfontr;
        else if(active==1)cfont=cfonta;
        else if(active==0)cfont=cfontd;
        else cfont=cfontb;
        //g.setColor(cauxfont);
        
        setOverBackColor();
        if(actual||over)
        {
            g.setComposite(composite_orig);
        }

        if(type==1)
        {
            g.setComposite(composite);
            g.setColor(cract);
            //g.drawLine(rx, ry+h-3,rx+w, ry+h-3);
            g.drawLine((int)x-(int)(maxW/2)+5, ry+h-3,(int)x+(int)(maxW/2)-5, ry+h-3);
        }
        else g.fillRoundRect(rx,ry,w,h,h/2,h/2);
        
        g.setComposite(composite_orig);
        //g.setColor(Color.WHITE);
        setOverFontColor();
        
        try{
            g.drawString(name,rx+olg/2,ry+h-fos);
        }catch(java.lang.IllegalArgumentException e ){System.out.println("Error in DrawString:"+e);}
        
        if(type==1)return;
        
        g.setComposite(composite);
        if(actual&&!over)
        {
           g.setColor(cract);
           g.drawRoundRect(rx,ry,w,h,h/2,h/2);
        }else
        {
           g.setColor(crover);
           g.drawRoundRect(rx,ry,w,h,h/2,h/2);
        }
    }

    public void showAss(AppObject actual)
    {
        if(over&&assname.length()>0)
        {
            g.setFont(f);
            int aw=g.getFontMetrics().stringWidth(assname)+olg;
            int rx=this.rx;
            if(rx+w+aw>maxX)rx=maxX-w-aw-1;
            g.setColor(coverback);
            g.setComposite(composite2);
            g.fillRoundRect(rx+w,ry-h,aw,h,h/2,h/2);
            g.setColor(cfonta);
            try{
                g.setComposite(composite_orig);
                g.drawString(assname,rx+w+olg/2,ry+h-fos-20);
                g.setComposite(composite);
            }catch(java.lang.IllegalArgumentException e ){System.out.println("Error in DrawString:"+e);}
            g.setColor(cfonta);
            g.drawRoundRect(rx+w,ry-h,aw,h,h/2,h/2);
            if(this!=actual)
            {
                actual.over=true;
                actual.assname=this.assnameAct;
                actual.showAss(actual);
                actual.over=false;
                actual.assname="";
            }
        }
    }

    public boolean inSide(int ax, int ay)
    {
        if(ax>=rx && ax<=(rx+w) && ay>=ry && ay<=(ry+h)) return true;
        else return false;
    }

    /** Getter for property active.
     * @return Value of property active.
     */
    public int getActive()
    {
        return active;
    }
    
    /** Setter for property active.
     * @param active New value of property active.
     */
    public void setActive(int active)
    {
        this.active = active;
    }
    
    /** Getter for property borrado.
     * @return Value of property borrado.
     */
    public int getBorrado()
    {
        return borrado;
    }
    
    /** Setter for property borrado.
     * @param borrado New value of property borrado.
     */
    public void setBorrado(int borrado)
    {
        this.borrado = borrado;
    }
    
    public boolean isChildof(AppObject topic)
    {
        Iterator aux=this.getTypes();
        ArrayList arr=new ArrayList();
        while(aux.hasNext())
        {
            AppObject tp=(AppObject)aux.next();
            if(tp==topic)return true;
            if(arr.contains(tp))return false;
            arr.add(tp);
            aux=tp.getTypes();
        }
        return false;
    }
    
    public boolean isParentof(AppObject topic)
    {
        Iterator aux=topic.getTypes();
        ArrayList arr=new ArrayList();
        while(aux.hasNext())
        {
            AppObject tp=(AppObject)aux.next();
            if(tp==this)return true;
            if(arr.contains(tp))return false;
            arr.add(tp);
            aux=tp.getTypes();
        }
        return false;
    }
    
    public Iterator getTypes()
    {
        Vector arr=new Vector();
        Iterator aux=this.assoc.iterator();
        while(aux.hasNext())
        {
            AppAssoc as=(AppAssoc)aux.next();
            if(as.type==0)
            {
                arr.add(as.topic);
            }
        }
        return arr.iterator();
    }
    
    /**
     * Getter for property url.
     * @return Value of property url.
     */
    public java.lang.String getUrl()
    {
        return url;
    }
    
    /**
     * Setter for property url.
     * @param url New value of property url.
     */
    public void setUrl(java.lang.String url)
    {
        this.url = url;
    }
    
}
