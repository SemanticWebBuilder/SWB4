/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.api;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;

/**
 *
 * @author javier.solis
 */
public class GenericSemResource extends GenericResource implements org.semanticwb.model.GenericObject
{
    private static Logger log=SWBUtils.getLogger(GenericSemResource.class);

    private static SemanticModel model=null;
    private SemanticObject m_obj=null;

    public GenericSemResource(SemanticObject obj)
    {
        this.m_obj=obj;
        try
        {
            Iterator<SemanticObject> it=obj.getModel().listSubjects(Resource.swb_resourceData, obj);
            while(it.hasNext())
            {
                setResourceBase((Resource)it.next().createGenericInstance());
                break;
            }
        }catch(Exception e){log.error(e);}
    }

    public GenericSemResource()
    {
    }

    public SemanticClass getSemanticClass()
    {
        SemanticClass cls=null;
        if(m_obj==null)
        {
            String clsname=getClass().getName();
            cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassByJavaName(clsname);
        }else
        {
            cls=m_obj.getSemanticClass();
        }
        return cls;
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("<div id=\""+m_obj.getURI()+"/admform\" dojoType=\"dijit.layout.ContentPane\">");
        SWBFormMgr mgr=new SWBFormMgr(m_obj, null, SWBFormMgr.MODE_EDIT);
        if("update".equals(paramRequest.getAction()))
        {
            mgr.processForm(request);
            response.sendRedirect(paramRequest.getRenderUrl().setAction(null).toString());
        }else
        {
            mgr.setAction(paramRequest.getRenderUrl().setAction("update").toString());
            out.print(mgr.renderForm(request));
        }
        out.println("</div>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        //TO Override
    }

    public String getURI() {
        return m_obj.getURI();
    }

    public String getId() {
        return m_obj.getId();
    }

    public SemanticObject getSemanticObject()
    {
        if(m_obj==null)
        {
            Resource res=getResourceBase();
            if(res!=null)
            {
                m_obj=res.getResourceData();
                if(m_obj==null)
                {
                    org.semanticwb.platform.SemanticModel model=getResourceBase().getSemanticObject().getModel();
                    SemanticClass cls=getSemanticClass();
                    if(cls!=null)
                    {
                        m_obj=model.createSemanticObject(model.getObjectUri(getResourceBase().getId(), cls), cls);
                        getResourceBase().setResourceData(m_obj);
                    }else
                    {
                        log.error(new SWBResourceException("Class Name:"+getClass()+" not found..."));
                    }
                }
            }
        }
        return m_obj;
    }

    /**
     * Asigna la propiedad con el valor especificado
     * @param prop Propiedad a modificar
     * @param value Valor a asignar
     * @return SemanticObject para cascada
     */
    public GenericObject setProperty(String prop, String value)
    {
        m_obj.setProperty(_getProperty(prop), value);
        return this;
    }

    public GenericObject removeProperty(String prop)
    {
        m_obj.removeProperty(_getProperty(prop));
        return this;
    }

    public String getProperty(String prop)
    {
        return  getProperty(prop, null);
    }

    public String getProperty(String prop, String defValue)
    {
        return getSemanticObject().getProperty(_getProperty(prop), defValue);
    }

    private SemanticProperty _getProperty(String prop)
    {
        return new SemanticProperty(m_obj.getModel().getRDFModel().createProperty(m_obj.getModel().getNameSpace()+"prop_"+prop));
    }

     /**
     * Regresa ruta de trabajo del objeto relativa al directorio work
     * ejemplo: /sep/Template/1
     *          /dominio/Objeto/id
     *
     * @return String con la ruta relativa al directorio work
     */
    public String getWorkPath() {
        return getResourceBase().getWorkPath();
    }

}
