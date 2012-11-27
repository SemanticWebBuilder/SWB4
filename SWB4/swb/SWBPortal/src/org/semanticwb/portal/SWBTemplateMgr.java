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
package org.semanticwb.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBTemplateMgr.
 * 
 * @author Jei
 */
public class SWBTemplateMgr 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBTemplateMgr.class);
    
    /** The templates. */
    private HashMap<String,TemplateImp> templates;                  

    /**
     * Instantiates a new sWB template mgr.
     */
    public SWBTemplateMgr()
    {
        log.event("Initializing SWBTemplateMgr...");
    }
    
    /**
     * Inits the.
     */
    public void init()
    {
        templates=new HashMap();
    }

    /**
     * Reload template.
     * 
     * @param tpl the tpl
     */
    public void reloadTemplate(Template tpl)
    {
        Template tp=tpl.getWebSite().getTemplate(tpl.getId());
        TemplateImp ret=new TemplateImp(tpl);
        templates.put(ret.getURI(), ret);
    }
    
    /**
     * Gets the template imp.
     * 
     * @param tpl the tpl
     * @return the template imp
     */
    public Template getTemplateImp(Template tpl)
    {
        //TODO:revisar cache para eliminar elementos o reparsear
        TemplateImp ret=templates.get(tpl.getURI());
        if(ret==null || ret.getSemanticObject()!=tpl.getSemanticObject())
        {
            ret=new TemplateImp(tpl);
            templates.put(tpl.getURI(), ret);
        }
        return ret;
    }
    
    /**
     * Gets the template.
     * 
     * @param user the user
     * @param topic the topic
     * @return the template
     * @return
     */
    public Template getTemplate(User user, WebPage topic)
    {
        ArrayList<Template> ret = null;
        //Template tpl = null;
//        RuleMgr ruleMgr = RuleMgr.getInstance();

//        Date today = new Date();
        HashMap tplpri=new HashMap();
        Iterator<TemplateRef> tpls = topic.listInheritTemplateRefs();
        while (tpls.hasNext())
        {
            TemplateRef ref=tpls.next();
            //System.out.println("ref:"+ref+" "+ref.getTemplate()+" "+ref.getTemplate().isValid());
            //valida tipo de herencia
            if(topic.hasTemplateRef(ref))
            {
                if(ref.getInherit()==TemplateRef.INHERIT_CHILDS)continue;
            }else
            {
                if(ref.getInherit()==TemplateRef.INHERIT_ACTUAL)continue;
            }
            if(!ref.getTemplate().isValid())continue;
            try
            {
                Template tpl=getTemplateImp(ref.getTemplate());
                //validar lenguage de la plantilla
                Language ltpl=tpl.getLanguage();
                if(ltpl!=null && !ltpl.getId().equals(user.getLanguage()))continue;
                //validar dispositivo de la plantilla
                Device dtpl=tpl.getDevice();
                if(dtpl!=null)
                {
                    Device ud=user.getDevice();
                    if(ud==null || !(dtpl.equals(ud) || dtpl.hasChild(ud)))continue;
                }

                boolean passrules = user.haveAccess(tpl);
                //if (passrules == true && !intereval.eval(today, tpl)) passrules = false;
                //System.out.println("ref:"+ ref+" rule:"+passrules+" "+tpl.getURI());
                if (passrules)
                {
                    ArrayList<Template> aux=(ArrayList)tplpri.get(new Integer(ref.getPriority()));
                    if(aux==null)
                    {
                        aux=new ArrayList<Template>();
                        tplpri.put(new Integer(ref.getPriority()),aux);
                    }
                    aux.add(tpl);
                    //System.out.println("add:"+tpl.getURI()+" "+ref.getPriority());
                }
            } catch (Exception e)
            {
                log.error("getTemplate:"+topic.getURI(),e);
            }
        }
        
        for(int x=5;x>=0;x--)
        {
            ret=(ArrayList)tplpri.get(new Integer(x));
            //System.out.println("x:"+x+" ret:"+ret);
            if(ret!=null && !ret.isEmpty())break;
        }
        
        if (ret!=null && !ret.isEmpty())
        {
            return (Template) ret.get((int) (Math.random() * ret.size()));
        } else
        {
            return null;
        }
    }

}
