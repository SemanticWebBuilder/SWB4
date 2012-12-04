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
 * GraphElement.java
 *
 * Created on 16 de enero de 2006, 12:42 PM
 */

package applets.modeler.elements;

import applets.modeler.GraphView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class GraphElement
{
    private String title="Element";
    private String content=null;
    private String toolTipText=null;
    private Image image=null;
    
    public static GraphElement focus;
    private Point dragDif=null;
    
    private int _eleVSpace=15;
    private int _eleHSpace=20;
    
    private int _x=0;
    private int _y=0;
    private int _w=50;
    private int _h=50;
    
    private int OPT_CLOSE_SEP=5;                      //separacion de la opcion de minimizar de la esquina superior izquierda
    private int OPT_CLOSE_SIZE=8;                    //Tamaño de la opcion de minimizar
    private boolean overClose=false;                 //Se encuentra sobre la opcion de minimizar
    private boolean closed=false;
    
    //private Point location=new Point();
    //private Dimension size=new Dimension();
    
    private int numChilds=1;    
    private int minChilds=1;    
    private int maxChilds=1;    
    
    private GraphElement parent=null;    
    
    private GraphElement childs[]=null;//           new GraphElement[maxChilds];
    
    private GraphElement extParent=null;            //external parent
    private GraphElement intChild=null;             //internal Child    
    
    private GraphView container=null;
    
    private boolean deletable=true;
    
    /** Creates a new instance of GraphElement */
    public GraphElement()
    {
        
    }
    
    public void _init()
    {
        init();
        if(minChilds>numChilds)minChilds=numChilds;
        if(numChilds>maxChilds)
        {
            maxChilds=numChilds;
            childs=new GraphElement[maxChilds];
        }
        if(childs==null)childs=new GraphElement[maxChilds];
    }
    
    public void init()
    {
    }
    
    public void paint(Graphics g)
    {
        paintLink(g);
        paintElement(g);
        if(canHaveChild()||haveChild())
        {        
            paintMinOptions(g);
        }
        if(canHaveChild())
        {        
            paintEmptyChilds(g);        
        }
    }
    
    public void paintElement(Graphics g)
    {
        Rectangle rec=getBounds();
        int x=(int)rec.getX();
        int y=(int)rec.getY();
        int w=(int)rec.getWidth();
        int h=(int)rec.getHeight();        
        
        if(image==null)
        {
            //g.setColor(Color.BLUE);
            //g.fillRoundRect(x, y, w, h,10,10);
            if(haveFocus())
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLUE);
            g.drawRoundRect(x, y, w, h,20,20);
            g.setColor(Color.BLACK);
            int l=g.getFontMetrics().stringWidth(title);
            g.drawString(title, x+(w-l)/2,y+h/2+5);
        }else
        {
            g.drawImage(image,x,y,w,h,null);
        }
        
//        g.setColor(Color.lightGray);
//        Rectangle r=getContainerBounds();
//        g.drawRoundRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight(),(int)r.getWidth()/3,(int)r.getHeight()/3);
        
    }
    
    public void paintMinOptions(Graphics g)
    {
        Rectangle rec=getBounds();
        int x=(int)rec.getMinX()+OPT_CLOSE_SEP;
        int y=(int)rec.getMinY()+OPT_CLOSE_SEP;
        int w=OPT_CLOSE_SIZE;
        int h=OPT_CLOSE_SIZE;
        if(overClose)
        {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x,y,w,h);
            g.setColor(Color.GRAY);
            g.drawRect(x,y,w,h);
            g.setColor(Color.WHITE);
            g.drawLine(x+2, y+h/2, x+w-2,y+h/2);
            if(closed)
            {
                g.drawLine(x+w/2, y+2, x+w/2,y+h-2);
            }
        }
        else
        {
            g.setColor(Color.WHITE);
            g.fillRect(x,y,w,h);
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x,y,w,h);
            g.setColor(Color.BLACK);
            g.drawLine(x+2, y+h/2, x+w-2,y+h/2);
            if(closed)
            {
                g.drawLine(x+w/2, y+2, x+w/2,y+h-2);
            }
        }
    }
    
    public void paintEmptyChilds(Graphics g)
    {
        if(!closed)
        {
            for(int x=0;x<numChilds;x++)
            {
                GraphElement e=childs[x];
                if(e==null)
                {
                    e=new EmptyElement();
                    e.setParent(this);
                    e.setWidth(this.getWidth());
                    e.setHeight(this.getHeight());
                    childs[x]=e;
                }
                if(e instanceof EmptyElement)
                {
                    e.paint(g);
                }
            }            
        }
    }
    
    public void paintLink(Graphics g)
    {
        GraphElement p=getParent();
        if(p!=null)
        {
            int x1=p.getX();
            int y1=p.getY()+p.getHeight()/2;
            int x2=getX();
            int y2=getY()-getHeight()/2;
            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x1, y1+getElementsVSpace()/2);
            g.drawLine(x1, y1+getElementsVSpace()/2, x2, y1+getElementsVSpace()/2);
            g.drawLine(x2, y1+getElementsVSpace()/2, x2, y2);
            
            g.drawLine(x2, y2,x2-3, y2-3);
            g.drawLine(x2, y2,x2+3, y2-3);
        }else
        {
            p=getExternalParent();
            if(p!=null)
            {
                int x1=p.getX();
                int y1=p.getY()-p.getHeight()/2;
                int x2=getX();
                int y2=getY()-getHeight()/2;
                g.setColor(Color.BLACK);
                g.drawLine(x1, y1, x1, y1+getElementsVSpace()/2);
                g.drawLine(x1, y1+getElementsVSpace()/2, x2, y1+getElementsVSpace()/2);
                g.drawLine(x2, y1+getElementsVSpace()/2, x2, y2);

                g.drawLine(x2, y2,x2-3, y2-3);
                g.drawLine(x2, y2,x2+3, y2-3);
            }
        }
        
    }
    
    
    /**
     * Getter for property location.
     * @return Value of property location.
     */
    public java.awt.Point getLocation()
    {
        return new Point(getX(),getY());
    }    

    /**
     * Setter for property location.
     * @param location New value of property location.
     */
    public void setLocation(java.awt.Point location)
    {
        setX((int)location.getX());
        setY((int)location.getY());
    }    

    /**
     * Setter for property location.
     * @param location New value of property location.
     */
    public void setLocation(int x, int y)
    {
        setX(x);
        setY(y);
    }    
    
    public int getX()
    {
        GraphElement e=getParent();
        if(e!=null)
        {
            if(e.getNumChilds()>1)
            {
                int i=e.getChildIndex(this);
                if(i>0)
                {
                    GraphElement ant=e.getChildsArray()[i-1];
                    //Rectangle arec=ant.getContainerBounds();
                    int r=(int)ant.getX()+ant.getContainerWidth()/2+(int)getContainerWidth()/2;
                    r+=getElementsHSpace();
                    return r; 
                }else
                {
                    return e.getX()-e.getContainerWidth()/2+getContainerWidth()/2;
                }
            }else
            {
                return e.getX();
            }
        }
        
        e=getExternalParent();
        if(e!=null)
        {
            return e.getX();
        }
        return _x;
    }

    public int getY()
    {
        GraphElement e=getParent();
        if(e!=null)
        {
            return e.getY()+e.getHeight()/2+getHeight()/2+_eleVSpace;
        }
        
        e=getExternalParent();
        if(e!=null)
        {
            return e.getY()-e.getHeight()/2+getHeight()/2+_eleVSpace;
        }
        
        return _y;
    }
    
    public void setX(int x)
    {
        if(x<0)x=0;
        _x=x;
    }

    public void setY(int y)
    {
        if(y<0)y=0;
        _y=y;
    }
        
    
    /**
     * Getter for property size.
     * @return Value of property size.
     */
    public java.awt.Dimension getSize()
    {
        return new Dimension(_w,_h);
    }    
    
    public int getWidth()
    {
        if(intChild==null)
            return _w;
        else
            return (int)intChild.getContainerWidth()+_eleHSpace+_eleHSpace;        
    }
    
    public int getHeight()
    {
        if(intChild==null)
            return _h;
        else
            return (int)intChild.getContainerHeight()+_eleVSpace+_eleVSpace;        
    }
    
    public void setWidth(int w)
    {
        _w=w;
    }
    
    public void setHeight(int h)
    {
        _h=h;
    }
    

    /**
     * Setter for property size.
     * @param size New value of property size.
     */
    public void setSize(java.awt.Dimension size)
    {
        _w=(int)size.getWidth();
        _h=(int)size.getHeight();        
    }    
    
    /**
     * Setter for property size.
     * @param size New value of property size.
     */
    public void setSize(int w, int h)
    {
        _w=w;
        _h=h;
    }      
    
    public boolean contains(Point p)
    {
        return contains((int)p.getX(),(int)p.getY());
    }
    
    public boolean contains(int x, int y)
    {
        return getBounds().contains(x,y);
//        if(getX()<x && x<getX()+_w)
//        {
//            if(getY()<y && y<getY()+_h)
//            {
//                return true;
//            }
//        }
//        return false;
    }
    
    
    /**
     * Getter for property focus.
     * @return Value of property focus.
     */
    public boolean haveFocus()
    {
        return (focus==this);
    }
    
    /**
     * Setter for property focus.
     * @param focus New value of property focus.
     */
    public void setFocus()
    {
        focus = this;
    }
    
    public void startDrag(Point p)
    {
        dragDif=new Point((int)(p.getX()-getX()),(int)(p.getY()-getY()));
    }
    
    public void drag(Point p)
    {
        if(dragDif!=null)
        {
            setX((int)p.getX()-(int)dragDif.getX());
            setY((int)p.getY()-(int)dragDif.getY());
        }
    }
    
    public void endDrag(Point p)
    {
        dragDif=null;
    }    
    
    /**
     * Getter for property title.
     * @return Value of property title.
     */
    public java.lang.String getTitle()
    {
        return title;
    }
    
    /**
     * Setter for property title.
     * @param title New value of property title.
     */
    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }
    
    /**
     * Getter for property child.
     * @return Value of property child.
     */
    public GraphElement[] getChildsArray()
    {
        return childs;
    }
    
    /**
     * Getter for property child.
     * @return Value of property child.
     */
    public Iterator getChilds()
    {
        ArrayList ret=new ArrayList();
        for(int x=0;x<numChilds;x++)
        {
            GraphElement e=childs[x];
            if(!isFree(e))
            {
                ret.add(e);
            }
        }        
        return ret.iterator();
    }
    
    
    /**
     * Setter for property child.
     * @param child New value of property child.
     */
    public void setChild(int index,applets.modeler.elements.GraphElement child)
    {
        if(this.childs[index]!=null && !(this.childs[index] instanceof EmptyElement))
        {
            this.childs[index].setParent(null);
        }
        if(child==null)
        {
            child=new EmptyElement();
            child.setWidth(this.getWidth());
            child.setHeight(this.getHeight());            
        }
        child.setParent(this);
        this.childs[index] = child;
        if(index>=numChilds)numChilds=index+1;
    }
    
    public void removeChild(GraphElement child)
    {
        for(int x=0;x<numChilds;x++)
        {
            GraphElement e=childs[x];
            if(e==child)
            {
                setChild(x,null);
            }
        }
    }
    
    public int getChildIndex(GraphElement child)
    {
        int r=-1;
        for(int x=0;x<numChilds;x++)
        {
            GraphElement e=childs[x];
            if(e==child)
            {
                r=x;
            }
        }     
        return r;
    }
    
    /**
     * Getter for property parent.
     * @return Value of property parent.
     */
    public GraphElement getParent()
    {
        return parent;
    }
    
    /**
     * Setter for property parent.
     * @param parent New value of property parent.
     */
    public void setParent(applets.modeler.elements.GraphElement parent)
    {
        this.parent = parent;
    }
    
    public boolean canHaveChild()
    {
        boolean ret=false;
        for(int x=0;x<numChilds;x++)
        {
            GraphElement e=childs[x];        
            if(isFree(e))ret=true;
        }
        return ret;
    }
    
    public boolean haveChild()
    {
        boolean ret=false;
        for(int x=0;x<numChilds;x++)
        {
            GraphElement e=childs[x];        
            if(!isFree(e))ret=true;
        }
        return ret;
    }    
    
    public boolean isFree(applets.modeler.elements.GraphElement ele)
    {
        return (ele==null || ele instanceof EmptyElement);
    }
    
    public boolean canHaveParent()
    {
        if(getParent()==null)return true;
        return false;
    }    
    
    public boolean linkChild(GraphElement child, Point point)
    {
        //System.out.println("linking:"+getTitle());
        if(canHaveChild() && child.canHaveParent())
        {
            for(int x=0;x<numChilds;x++)
            {
                GraphElement e=childs[x];       
                //System.out.println("link child:"+x+"->"+e);
                if(e instanceof EmptyElement && e.contains(point))
                {
                    //System.out.println("link:"+x);
                    setChild(x, child);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void unLinkParent()
    {
        if(getParent()!=null)
        {
            getParent().removeChild(this);
        }
    }
    
    public void _onMouseMoved(java.awt.event.MouseEvent evt)
    {
        if(canHaveChild()||haveChild())
        {
            boolean aux=isOverOptMinimize(evt.getPoint());
            if(aux!=overClose)
            {
                if(aux)
                {
                    overClose=true;
                    paintMinOptions(getContainer().getGraphics());
                }else
                {
                    overClose=false;
                    paintMinOptions(getContainer().getGraphics());
                }
            }
        }
        onMouseMoved(evt);
    }
    
    
    public void _onMouseClicked(java.awt.event.MouseEvent evt)
    {
        boolean aux=isOverOptMinimize(evt.getPoint());
        if(aux)
        {
            closed=!closed;
            getContainer().updateUI();
        }else
        {
            onMouseClicked(evt);
        }
    }   
    
    String style="<style>"
                +"BODY, P, DIV, H1, H2, H3, H4, H5, H6, ADDRESS, OL, UL, LI, TITLE, TD, OPTION, SELECT {"
                +" font-family: Verdana "
                +"}"
                +"BODY, P, DIV, ADDRESS, OL, UL, LI, TITLE, TD, OPTION, SELECT {  "
                +"  font-size: 10.0pt;"
                +"  margin-top:0pt;  "
                +"  margin-bottom:0pt; "
                +"} "
                +"BODY, P {"
                +"  margin-left:0pt; "
                +"  margin-right:0pt;"
                +"}"
                +"BODY {"
                +"  background: white;"
                +"  margin: 6px;"
                +"  padding: 0px;"
                +"}"
                +"a img {	border: 0; }"
                +"</style>";    
    
    public void _onMouseEntered(java.awt.event.MouseEvent evt)
    {
        getContainer().setCursor(new Cursor(Cursor.HAND_CURSOR));
        getContainer().setToolTipText(getToolTipText());  
        onMouseEntered(evt);
    }    
    
    public void _onMouseExited(java.awt.event.MouseEvent evt)
    {
        getContainer().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        getContainer().setToolTipText(null);
        onMouseExited(evt);
    }         
    
    public void onMouseMoved(java.awt.event.MouseEvent evt)
    {
    }
    
    
    public void onMouseClicked(java.awt.event.MouseEvent evt)
    {
    }   
    
    public void onMouseEntered(java.awt.event.MouseEvent evt)
    {
    }    
    
    public void onMouseExited(java.awt.event.MouseEvent evt)
    {
    }             
    
    public void onIDECreated()
    {
        
    }
    
    public boolean isOverOptMinimize(Point p)
    {
        Rectangle rec=getBounds();
        rec.setSize(OPT_CLOSE_SIZE,OPT_CLOSE_SIZE);
        rec.translate(OPT_CLOSE_SEP, OPT_CLOSE_SEP);
        return rec.contains(p);
    }
    
    /**
     * Regresa espacio vertical entre elementos
     * @return Value of property _eleSpace.
     */
    public int getElementsVSpace()
    {
        return _eleVSpace;
    }
    
    /**
     * Regresa espacio horizontal entre elementos
     * @return Value of property _eleHSpace.
     */
    public int getElementsHSpace()
    {
        return _eleHSpace;
    }
    
    public Rectangle getBounds()
    {
        Rectangle rec=new Rectangle(getWidth(),getHeight());
        rec.setLocation(getX()-getWidth()/2,getY()-getHeight()/2);
        return rec;
    }
    
    public int getContainerWidth()
    {
        int w=getWidth();
        if(!closed)
        {
            int cw=0;
            for(int x=0;x<numChilds;x++)
            {
                GraphElement e=childs[x];     
                if(e!=null)
                {
                    if(cw>0)cw+=getElementsHSpace();
                    cw+=e.getContainerWidth();
                }
            }
            if(w<cw)w=cw;
        }
        return w;        
    }
    
    public int getContainerHeight()
    {
        int h=getHeight();
        if(!closed)
        {
            int ch=0;
            for(int x=0;x<numChilds;x++)
            {
                GraphElement e=childs[x];     
                if(e!=null)
                {
                    int aux=e.getContainerHeight();
                    if(aux>ch)ch=aux;
                }
            }
            if(ch>0)ch+=getElementsVSpace();
            h+=ch;
        }
        return h;        
    }    
    
    public Rectangle getContainerBounds()
    {
        Rectangle rec=getBounds();
        double x1=rec.getMinX();
        double y1=rec.getMinY();
        double x2=rec.getMaxX();
        double y2=rec.getMaxY();
        
        if(!closed)
        {
            //System.out.println("x1:"+x1+" y1:"+y1+" x2:"+x2+" y2:"+y2);
            //System.out.println("x:"+rec.getX()+" y:"+rec.getY()+" w:"+rec.getWidth()+" h:"+rec.getHeight());

            for(int x=0;x<numChilds;x++)
            {
                GraphElement e=childs[x];     
                if(e!=null)
                {
                    Rectangle crec=e.getContainerBounds();
                    if(x1>crec.getMinX())x1=crec.getMinX();
                    if(y1>crec.getMinY())y1=crec.getMinY();
                    if(x2<crec.getMaxX())x2=crec.getMaxX();
                    if(y2<crec.getMaxY())y2=crec.getMaxY();
                }
            }
        }
        Rectangle r=new Rectangle((int)(x2-x1),(int)(y2-y1));
        r.setLocation((int)x1,(int)y1);
        return r;
    }
    
    /**
     * Getter for property numChilds.
     * @return Value of property numChilds.
     */
    public int getNumChilds()
    {
        return numChilds;
    }    
    
    /**
     * Setter for property numChilds.
     * @param numChilds New value of property numChilds.
     */
    public void setNumChilds(int numChilds)
    {
        this.numChilds = numChilds;
    }    
    
    /**
     * Getter for property content.
     * @return Value of property content.
     */
    public java.lang.String getContent()
    {
        return content;
    }
    
    /**
     * Setter for property content.
     * @param content New value of property content.
     */
    public void setContent(java.lang.String content)
    {
        this.content = content;
    }
    
    /**
     * Getter for property close.
     * @return Value of property close.
     */
    public boolean isClosed()
    {
        return closed;
    }
    
    /**
     * Setter for property close.
     * @param close New value of property close.
     */
    public void setClosed(boolean close)
    {
        this.closed = closed;
    }
    
    /**
     * Getter for property toolTipText.
     * @return Value of property toolTipText.
     */
    public java.lang.String getToolTipText()
    {
        return toolTipText;
    }
    
    public java.lang.String getToolTipText(String pre, String intr, String pos)
    {
        return pre+toolTipText+pos;
    }
    
    /**
     * Setter for property toolTipText.
     * @param toolTipText New value of property toolTipText.
     */
    public void setToolTipText(java.lang.String toolTipText)
    {
        this.toolTipText = toolTipText;
    }
    
    /**
     * Getter for property container.
     * @return Value of property container.
     */
    public applets.modeler.GraphView getContainer()
    {
        return container;
    }
    
    /**
     * Setter for property container.
     * @param container New value of property container.
     */
    public void setContainer(applets.modeler.GraphView container)
    {
        this.container = container;
    }
    
    public boolean isParentClosed()
    {
        GraphElement p=getParent();
        if(p==null)
        {
            p=getExternalParent();
            if(p==null)
            {
                return false;
            }else
            {
                p=p.getParent();
                if(p==null)
                {
                    return false;
                }                
            }
        }
        if(p.isClosed())return true;
        else return p.isParentClosed();
    }
    
    public boolean isChildOf(GraphElement parent)
    {
        GraphElement p=getParent();
        if(p==null)
        {
            p=getExternalParent();
            if(p==null)
            {
                return false;
            }
        }
        if(p==parent)return true;
        else return p.isChildOf(parent);
    }    
    
    public boolean isParentOf(GraphElement child)
    {
        return child.isChildOf(this);
    }     
    
    /**
     * Getter for property intChild.
     * @return Value of property intChild.
     */
    public GraphElement getInternalChild()
    {
        return intChild;
    }
    
    /**
     * Setter for property intChild.
     * @param intChild New value of property intChild.
     */
    public void setInternalChild(GraphElement intChild)
    {
        this.intChild = intChild;
        intChild.setExternalParent(this);
    }
    
    /**
     * Getter for property extParent.
     * @return Value of property extParent.
     */
    public applets.modeler.elements.GraphElement getExternalParent()
    {
        return extParent;
    }
    
    /**
     * Setter for property extParent.
     * @param extParent New value of property extParent.
     */
    public void setExternalParent(applets.modeler.elements.GraphElement extParent)
    {
        this.extParent = extParent;
    }
    
    /**
     * Getter for property image.
     * @return Value of property image.
     */
    public java.awt.Image getImage()
    {
        return image;
    }
    
    /**
     * Setter for property image.
     * @param image New value of property image.
     */
    public void setImage(java.awt.Image image)
    {
        this.image = image;
        MediaTracker tracker = new MediaTracker(getContainer().getApplet());
        tracker.addImage(image, 1);
        try
        {
            tracker.waitForAll();
        }catch( InterruptedException noe ) {}
    }
    
    /**
     * Getter for property deletable.
     * @return Value of property deletable.
     */
    public boolean isDeletable()
    {
        return deletable;
    }
    
    /**
     * Setter for property deletable.
     * @param deletable New value of property deletable.
     */
    public void setDeletable(boolean deletable)
    {
        this.deletable = deletable;
    }
    
    /**
     * Getter for property minChilds.
     * @return Value of property minChilds.
     */
    public int getMinChilds()
    {
        return minChilds;
    }
    
    /**
     * Setter for property minChilds.
     * @param minChilds New value of property minChilds.
     */
    public void setMinChilds(int minChilds)
    {
        this.minChilds = minChilds;
    }
    
    /**
     * Getter for property maxChilds.
     * @return Value of property maxChilds.
     */
    public int getMaxChilds()
    {
        return maxChilds;
    }
    
    /**
     * Setter for property maxChilds.
     * @param maxChilds New value of property maxChilds.
     */
    public void setMaxChilds(int maxChilds)
    {
        childs=new GraphElement[maxChilds];
        this.maxChilds = maxChilds;
    }
    
    public void addChildSpace()
    {
        if(numChilds<maxChilds)
        {
            numChilds++;
        }
    }
    
    public void removeChildSpace()
    {
        if(numChilds>minChilds && canHaveChild())
        {
            int z=0;
            for(int x=numChilds-1;x>=0;x--)
            {
                if(isFree(childs[x]))
                {
                    z=x;
                    break;
                }
            }
                        
            for(int x=z;x<numChilds-1;x++)
            {
                childs[x]=childs[x+1];
            }               
            childs[numChilds-1]=null;
            numChilds--;
        }
    }    
}
