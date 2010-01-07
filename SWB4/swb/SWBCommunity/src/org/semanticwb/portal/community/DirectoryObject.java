package org.semanticwb.portal.community;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.TemplateImp;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;


public class DirectoryObject extends org.semanticwb.portal.community.base.DirectoryObjectBase 
{
    private static Logger log = SWBUtils.getLogger(DirectoryObject.class);
    static {
        SWBPortal.getIndexMgr().getDefaultIndexer().registerParser(DirectoryObject.class, new DirectoryObjectParser());
    }
    
    public DirectoryObject(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Muestra la información utilizada por las herramientas o recursos generales para
     * los elementos de comunidades, como calificar y reporte de abuso sobre el elemento
     * o comentarios relacionados al mismo.
     * @param request petición del usuario a atender
     * @param out para agregar los datos a desplegar
     * @param paramRequest para obtener referencias a objetos internos
     * @throws org.semanticwb.portal.api.SWBResourceException
     * @throws java.io.IOException
     */
    public void renderGenericElements(HttpServletRequest request,
            HttpServletResponse response, SWBParamRequest paramRequest)
            throws SWBResourceException, IOException {


        String path="/swbadmin/jsp/microsite/RenderGenericElements/RenderDirObjElements.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("DirectoryObject", this);
            dis.include(request, response);
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }

    /**
     * Indica si el <code>User</code> recibido puede ver y agregar comentarios
     * @param ele
     * @param mem
     * @return <code>true</code> si el usuario tiene derecho a ver y agregar el elemento,
     * <code>false</code> de lo contrario.
     */
    public boolean canComment(User mem) {

        boolean ret = false;
        if (mem.isSigned()) {
            ret = true;
        }
        return ret;
    }

    /**
     * Indica si el <code>User</code> recibido puede reclamar el elemento.
     * @param mem
     * @return <code>true</code> si el usuario tiene derecho a reclamar el elemento,
     * <code>false</code> en otro caso.
     */
    public boolean canClaim(User mem) {
        if (isClaimable()) {
            boolean isClaimable = getSemanticObject().getBooleanProperty(Claimable.swbcomm_claimable);            
            if (isClaimable) {
                if (mem.isSigned()) {
                    if (!getCreator().equals(mem)) {
                        SemanticObject soClaimer = getSemanticObject().getObjectProperty(Claimable.swbcomm_claimer);
                        if (soClaimer != null) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Indica si el elemento puede ser reclamado por algun usuario.
     * @return <code>true</code> si el elemento puede ser reclamado,
     * <code>false</code> en otro caso.
     */
    public boolean isClaimable() {
        if (getSemanticObject().instanceOf(Claimable.swbcomm_Claimable) &&
                getSemanticObject().getBooleanProperty(Claimable.swbcomm_claimable)) {
            return true;
        }
        return false;
    }

    /**
     * Indica si el elemento ha sido reclamado por algun usuario.
     * @return <code>true</code> si el elemento ha sido reclamado,
     * <code>false</code> en otro caso.
     */
    public boolean isClaimed () {
        if (isClaimable()) {
            if (getSemanticObject().getObjectProperty(Claimable.swbcomm_claimer) != null) {
                return true;
            }
        }
        return false;
    }

    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest)
    {
        DirectoryObject dob = null;
        SemanticObject so = SemanticObject.createSemanticObject(request.getParameter("uri"));
        if (so != null) {
            dob = (DirectoryObject) so.createGenericInstance();
        }

        if(str==null || str.trim().length()==0)
            return "";

        str=str.trim();
        Iterator it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,paramRequest)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,paramRequest)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{template.getArgument(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{template.getArgument(\""+s+"\")}", (String)paramRequest.getArgument(replaceTags(s,request,paramRequest)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,paramRequest)));
        }

        if (dob != null)
        {
            str=SWBUtils.TEXT.replaceAll(str, "{direlement.title}", dob.getTitle());
            str=SWBUtils.TEXT.replaceAll(str, "{direlement.description}", dob.getDescription());
            str=SWBUtils.TEXT.replaceAll(str, "{direlement.claimjustify}", ((Claimable)dob).getClaimJustify());
            str=SWBUtils.TEXT.replaceAll(str, "{direlement.encodeduri}", dob.getEncodedURI());
            str=SWBUtils.TEXT.replaceAll(str, "{direlement.uri}", dob.getURI());
        }

        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.fullname}", paramRequest.getUser().getFullName());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
        str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{websiteid}", paramRequest.getWebPage().getWebSiteId());
        if(str.indexOf("{templatepath}")>-1)
        {
            //TODO:pasar template por paramrequest
            TemplateImp template=(TemplateImp)SWBPortal.getTemplateMgr().getTemplate(paramRequest.getUser(), paramRequest.getWebPage());
            str=SWBUtils.TEXT.replaceAll(str, "{templatepath}", template.getActualPath());
        }
        return str;
    }

    public boolean isValid() {
        return true;
    }
}
