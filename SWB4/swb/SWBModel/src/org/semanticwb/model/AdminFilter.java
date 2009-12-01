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
 
package org.semanticwb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class AdminFilter extends org.semanticwb.model.base.AdminFilterBase 
{
    public static String ACTION_ADD="add";
    public static String ACTION_DELETE="delete";
    public static String ACTION_EDIT="edit";
    public static String ACTION_ACTIVE="active";

    private ArrayList<WebPage> pages=null;
    private ArrayList<WebPage> vpages=null;
    private HashMap<SemanticClass,ArrayList> classes=null;
    private boolean allClasses=false;

    public AdminFilter(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        init();
    }
    
    public void init()
    {
        pages=new ArrayList();
        vpages=new ArrayList();
        classes=new HashMap();

        //Menus
        {
            Document dom=getDom();
            NodeList list=dom.getElementsByTagName("menus");
            if(list.getLength()>0)list=((Element)list.item(0)).getElementsByTagName("node");
            for(int x=0;x<list.getLength();x++)
            {
                Element ele=(Element)list.item(x);
                String val=ele.getAttribute("reload");
                if(val.startsWith("getTopic.SCA|"))  //Es un submenu de una clase
                {
                    String aux=val.substring(13);
                    StringTokenizer st=new StringTokenizer(aux,"|");
                    String clsid=st.nextToken();
                    String act=st.nextToken();
                    SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
                    if(cls!=null)
                    {
                        ArrayList arr=classes.get(cls);
                        if(arr==null)
                        {
                            arr=new ArrayList();
                            classes.put(cls, arr);
                            //Se agregan subclases x performance en busqueda
                            Iterator<SemanticClass> it=cls.listSubClasses();
                            while (it.hasNext())
                            {
                                SemanticClass semanticClass = it.next();
                                classes.put(semanticClass, arr);
                            }
                        }
                        arr.add(act);
                        System.out.println(cls+":"+act);
                    }
                }else if(val.startsWith("getTopic.SC|"))  //Es un submenu de una clase
                {
                    String clsid=val.substring(12);
                    SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
                    if(cls!=null)
                    {
                        ArrayList arr=classes.get(cls);
                        if(arr==null)
                        {
                            arr=new ArrayList();
                            classes.put(cls, arr);
                            //Se agregan subclases x performance en busqueda
                            Iterator<SemanticClass> it=cls.listSubClasses();
                            while (it.hasNext())
                            {
                                SemanticClass semanticClass = it.next();
                                classes.put(semanticClass, arr);
                            }
                        }
                        arr.add("add");
                        arr.add("edit");
                        arr.add("delete");
                        arr.add("active");
                        System.out.println(cls+":todo");
                    }
                }else if(val.startsWith("getTopic."))  //es un menu
                {
                    String id=ele.getAttribute("id");
                    String model=ele.getAttribute("topicmap");
                    WebPage page=SWBContext.getWebSite(model).getWebPage(id);
                    if(page!=null)
                    {
                        pages.add(page);
                        addMenu(page.getParent());
                        //Valida si tiene acceso a todas las classes
                        if(page.getId().equals("WBAd_Menus") || page.getId().equals("WBAd_mnu_PopUp"))
                        {
                            allClasses=true;
                        }
                    }
                }
            }
        }

        //Behaviors
        {
            Document dom=getDom();
            NodeList list=dom.getElementsByTagName("elements");
            if(list.getLength()>0)list=((Element)list.item(0)).getElementsByTagName("node");
            for(int x=0;x<list.getLength();x++)
            {
                Element ele=(Element)list.item(x);
                String val=ele.getAttribute("reload");

                if(val.startsWith("getTopic."))
                {
                    String id=ele.getAttribute("id");
                    String model=ele.getAttribute("topicmap");
                    WebPage page=SWBContext.getWebSite(model).getWebPage(id);
                    if(page!=null)
                    {
                        pages.add(page);
                    }
                }
            }
        }

    }
    
    private void addMenu(WebPage page)
    {
        if(page!=null)
        {
            if(!vpages.contains(page))
            {
                vpages.add(page);
                addMenu(page.getParent());
            }
        }
    }

    public boolean haveAccessToSemanticObject(SemanticObject obj)
    {
        boolean ret=false;

        return ret;
    }

    public boolean haveClassAction(SemanticClass cls, String act)
    {
        boolean ret=false;
        if(!allClasses)
        {
            if(cls.isSubClass(FilterableClass.swb_FilterableClass))
            {
                ArrayList arr=classes.get(cls);
                if(arr!=null)
                {
                    if(arr.contains(act))
                    {
                        ret=true;
                    }
                }
            }else
            {
                ret=true;
            }
        }else
        {
            ret=true;
        }
        return ret;
    }

    public boolean haveAccessToWebPage(WebPage page)
    {
        boolean ret=false;
        //System.out.print("haveAccessToWebPage:"+page);
        Iterator<WebPage> it=pages.iterator();
        while (it.hasNext())
        {
            WebPage webPage = it.next();
            if(page.equals(webPage) || page.isChildof(webPage))
            {
                ret=true;
                break;
            }
        }
        //System.out.print(" retet1:"+ret);
        if(!ret)
        {
            Iterator<WebPage> it2=vpages.iterator();
            while (it2.hasNext())
            {
                WebPage webPage = it2.next();
                if(page.equals(webPage))
                {
                    ret=true;
                    break;
                }
            }
        }
        //System.out.println(" retet2:"+ret);
        return ret;
    }

    @Override
    public void setXml(String value) {
        super.setXml(value);
        init();
    }

    public Document getDom()
    {
        return getSemanticObject().getDomProperty(swb_xml);
    }

}
