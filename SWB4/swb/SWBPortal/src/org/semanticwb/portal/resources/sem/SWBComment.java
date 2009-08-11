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
 
package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;

public class SWBComment extends org.semanticwb.portal.resources.sem.base.SWBCommentBase 
{

    public SWBComment()
    {
    }

    public SWBComment(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        Iterator<CmtComment> it=listComments();
        out.println("<pre>");
        while(it.hasNext())
        {
            CmtComment cmt=it.next();
            out.println("Titulo:"+cmt.getTitle());
            out.println("Description:"+cmt.getDescription());
            out.println("Fecha de Creacion:"+cmt.getCreated());
            out.println("Creador:"+cmt.getCreator());
            out.println("**************************************");
        }
        out.println("</pre>");
        out.println("<a href=\""+paramRequest.getRenderUrl().setMode("ADD")+"\">Agregar Comentario</a>");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if(paramRequest.getMode().equals("ADD"))
        {
            doAdd(request, response, paramRequest);
        }else
        {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        SWBFormMgr mgr=new SWBFormMgr(CmtComment.sclass, getSemanticObject(), null);
        try
        {
            SemanticObject obj=mgr.processForm(request);
            addComment((CmtComment)obj.createGenericInstance());
        }catch(FormValidateException e)
        {
            e.printStackTrace();
            //Validar error
        }
        response.setMode(response.Mode_VIEW);
    }



    public void doAdd(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        SWBFormMgr mgr=new SWBFormMgr(CmtComment.sclass, getSemanticObject(), null);
        mgr.setAction(paramRequest.getActionUrl().toString());
        mgr.addButton("<button label=\"Reset\"/>");
        String ret=mgr.renderForm(request);
        out.println(ret);
    }


}
