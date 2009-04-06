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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;

/**
 *
 * @author javier.solis
 */
public class GenericSemResource extends GenericResource
{
    private static Logger log=SWBUtils.getLogger(GenericSemResource.class);

    private static SemanticModel model=null;

    public GenericSemResource()
    {
        if(model==null)
        {
            String cls=getClass().getName();
            String path="/"+SWBUtils.TEXT.replaceAll(cls, ".", "/")+".owl";
            InputStream in=getClass().getResourceAsStream(path);
            if(in!=null)
            {
                log.debug("Reading:"+path);
                Model m=ModelFactory.createDefaultModel();
                m.read(in, null);
                model=new SemanticModel(cls,m);
                SWBPlatform.getSemanticMgr().getSchema().addSubModel(model,false);
                semanticInit();
                //System.out.println(cls);
            }
        }
    }

    protected void semanticInit()
    {
        //To override
    }


    public SemanticClass getSemanticClass()
    {
        String clsname=getClass().getName();
        return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassByJavaName(clsname);
    }

    public SemanticObject getResourceData()
    {
        SemanticObject obj=getResourceBase().getResourceData();
        if(obj==null)
        {
            org.semanticwb.platform.SemanticModel model=getResourceBase().getSemanticObject().getModel();
            SemanticClass cls=getSemanticClass();
            if(cls!=null)
            {
                obj=model.createSemanticObject(model.getObjectUri(getResourceBase().getId(), cls), cls);
                getResourceBase().setResourceData(obj);
            }else
            {
                log.error(new SWBResourceException("Class Name:"+getClass()+" not found..."));
            }
        }
        return obj;
    }


    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        SemanticObject obj=getResourceData();
        out.println("<div id=\""+obj.getURI()+"/admform\" dojoType=\"dijit.layout.ContentPane\">");
        SWBFormMgr mgr=new SWBFormMgr(obj, null, SWBFormMgr.MODE_EDIT);
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
//        SWBFormMgr mgr=new SWBFormMgr(getResourceBase().getResourceData(), null, SWBFormMgr.MODE_EDIT);
//        mgr.processForm(request);
    }

}
