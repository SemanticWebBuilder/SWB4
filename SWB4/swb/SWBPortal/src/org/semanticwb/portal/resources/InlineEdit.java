package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;


/** Objeto que se encarga de desplegar y administrar un texto est�tico, este texto
 * se agrega en la administraci�n del recurso, acepta tags de html para cambiar su
 * aspecto.
 *
 * Object that is in charge to unfold and to administer a static text, this text
 * it is added in the administration of the resource, accepts tags of HTML to change his
 * aspect.
 * @modified Carlos Ramos
 */

public class InlineEdit extends GenericResource
{

    /** Obtiene la vista del recurso.
     *
     * @param request El servlet container crea un objeto HttpServletRequest y
     *                      se pasa como argumento al m�todo del servlet.
     * @param response El servlet container crea un objeto HttpServletResponse y
     *                      se pasa como argumento al m�todo del servlet.
     * @param paramsRequest Argumentos de la solicitud del recurso.
     * @throws IOException
     * @exception com.infotec.appfw.exception.AFException Si se origina cualquier error en el recurso al traer el html.
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        
        SWBResourceURL url=paramRequest.getActionUrl();
        url.setCallMethod(url.Call_DIRECT);

        String url2=url.toString();

        String id=paramRequest.getResourceBase().getId();
        String name=paramRequest.getArgument("name");
        String data=null;
        if(name!=null)
        {
            data=paramRequest.getResourceBase().getData(name);
            id=id+name;
            url2=url2+"?name="+name+"&";
        }else
        {
            data=paramRequest.getResourceBase().getData();
            url2=url2+"?";
        }
        if(data==null)data=paramRequest.getLocaleString("click4edit");

        
        PrintWriter out = response.getWriter();
        out.println(
                "<script type=\"text/javascript\">" +
                "  dojo.require(\"dijit.InlineEditBox\");" +
                "  dojo.require(\"dojo.parser\");" +
                "  function editableHeaderOnChange"+id+"(arg)" +
                "  {    " +
                "      getSyncHtml(\""+url2+"txt=\"+arg);"+
//                "      alert(\"editableHeader changed with arguments \"+arg+\" "+url2+"?url=\"+arg);" +
                "  }" +
                "</script>"
        );
        out.println(
                "<span onChange=\"editableHeaderOnChange"+id+"(arguments[0])\" autosave=\"true\" dojotype=\"dijit.InlineEditBox\">"+data+"</span>");
    }

    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException 
    {
        String name=request.getParameter("name");
        if(name!=null)
        {
            response.getResourceBase().setData(name,request.getParameter("txt"));
        }else
        {
            response.getResourceBase().setData(request.getParameter("txt"));
        }
        //System.out.println("txt:"+request.getParameter("txt"));
    }
    
}