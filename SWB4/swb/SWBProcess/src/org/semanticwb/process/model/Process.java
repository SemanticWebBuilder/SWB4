/*
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
 */
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

/**
 * Clase que encapsula las propiedades y funciones de un proceso.
 * @author Javier Solis
 */
public class Process extends org.semanticwb.process.model.base.ProcessBase 
{
    private static Logger log=SWBUtils.getLogger(Process.class);
    
    //Bloque estático para registrar un observador cuando se modifica la propiedad parentWebPage
    static 
    {
        swp_parentWebPage.registerObserver(new SemanticObserver() 
        {
            @Override
            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                //System.out.println("obj:"+obj+" prop:"+prop+" action:"+action);
                if(obj.instanceOf(Process.sclass))
                {
                    Process process=(Process)obj.createGenericInstance();                    
                    process.getProcessWebPage().removeAllVirtualParent();
                    if(action!=null && action.equals("SET"))
                    {
                        if(process.getParentWebPage()!=null)
                        {
                            process.getProcessWebPage().addVirtualParent(process.getParentWebPage());
                        }
                    }
                }
                        
            }
        });
    }
    
    /**
     * Constructor.
     * @param base 
     */
    public Process(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        getProcessWebPage();  //Crea pagina web de proceso
        
    }
    
    /**
     * Crea una instancia del proceso.
     * @return 
     */
    public ProcessInstance createInstance()
    {
        //System.out.println("createInstance process:"+this);
        ProcessInstance inst=null;
        inst=this.getProcessSite().createProcessInstance();
        inst.setProcessType(this);
        inst.setStatus(Instance.STATUS_INIT);

        return inst;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    @Override
    public List<ItemAware> listRelatedItemAware()
    {
        //System.out.println("getRelatedItemAwareClasses:"+this);
        ArrayList<ItemAware> arr=new ArrayList();
        Iterator<GraphicalElement> it=listContaineds();
        while (it.hasNext())
        {
            GraphicalElement graphicalElement = it.next();
            if(graphicalElement instanceof ItemAware)
            {
                ItemAware item=(ItemAware)graphicalElement;

                if(!item.listInputConnectionObjects().hasNext() && !item.listOutputConnectionObjects().hasNext())
                {
                    arr.add(item);
                }
            }
        }
        return arr;
    }

    /**
     * Regresa las ItemAware y la Classes relacionadas con el proceso (ItemAware Globales)
     * @return
     */
    @Override
    public List<ItemAware> listHerarquicalRelatedItemAware()
    {
        //System.out.println("getHerarquicalRelatedItemAwareClasses:"+this);
        return listRelatedItemAware();
    }
    
    /**
     * Obtiene una lista de todos los nodos contenidos en un proceso.
     * @return 
     */
    public Iterator<GraphicalElement> listAllContaineds()
    {
        ArrayList<GraphicalElement> arr=new ArrayList();
        Iterator<GraphicalElement> it=listContaineds();
        while (it.hasNext())
        {
            GraphicalElement gElement = it.next();
            arr.add(gElement);
            if(gElement instanceof SubProcess)
            {
                Iterator<GraphicalElement> it2=((SubProcess)(gElement)).listAllContaineds();
                while (it2.hasNext())
                {
                    GraphicalElement gElement2 = it2.next();
                    arr.add(gElement2);
                }
            }
        }
        return arr.iterator();
    }

    @Override
    public WrapperProcessWebPage getProcessWebPage() 
    {
        WrapperProcessWebPage wp=super.getProcessWebPage();
        if(wp==null)
        {
            wp=WrapperProcessWebPage.ClassMgr.createWrapperProcessWebPage(getId()+"_swp", getProcessSite());
            setProcessWebPage(wp);
            wp.setActive(true);
            //eliminar cache
            //getSemanticObject().removeCache(wp.getURI());
            //wp=super.getProcessWebPage();
        }
        return wp;
    }
    
    /**
     * Verifica si el usuario puede acceder al proceso.
     * @param user Usuario.
     * @return true si el usuario tiene permisos para ver el proceso, false en otro caso.
     */
    public boolean haveAccess(User user) {
        return (isValid() && user.haveAccess(this));
    }
    
    /**
     * Verifica si el usuario puede instanciar el proceso.
     * @param user Usuario.
     * @return true si el usuario puede instanciar el proceso, false en otro caso.
     */
    public boolean canInstantiate(User user) {
        boolean ret = false;
        boolean hasStart = false;
        Iterator<GraphicalElement> elements = listContaineds();
        while (elements.hasNext()) {
            GraphicalElement ge = elements.next();
            if (ge instanceof StartEvent) {
                hasStart = true;
                ret = user.haveAccess(ge);
                break;
            }
        }
        return hasStart && ret;
    }
}
