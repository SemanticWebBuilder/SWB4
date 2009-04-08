package org.semanticwb.portal.resources.sem;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.*;
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
        SemanticObject obj=mgr.processForm(request);
        addComment((CmtComment)obj.createGenericInstance());
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
