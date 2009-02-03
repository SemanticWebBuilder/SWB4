/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 *
 * @author Jei
 */
public class SWBTemplateMgr 
{
    private static Logger log = SWBUtils.getLogger(SWBTemplateMgr.class);
    
    private HashMap<String,TemplateImp> templates;                  

    public SWBTemplateMgr()
    {
        log.event("Initializing SWBTemplateMgr...");
    }
    
    public void init()
    {
        templates=new HashMap();
    }

    public void reloadTemplate(Template tpl)
    {
        Template tp=tpl.getWebSite().getTemplate(tpl.getId());
        TemplateImp ret=new TemplateImp(tpl);
        templates.put(ret.getURI(), ret);
    }
    
    public Template getTemplateImp(Template tpl)
    {
        //TODO:revisar cache para eliminar elementos o reparsear
        TemplateImp ret=templates.get(tpl.getURI());
        if(ret==null)
        {
            ret=new TemplateImp(tpl);
            templates.put(tpl.getURI(), ret);
        }
        return ret;
    }
    
    /**
     * @param user
     * @param topic
     * @throws AFException
     * @return  */
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
            try
            {
                Template tpl=getTemplateImp(ref.getTemplate());
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
