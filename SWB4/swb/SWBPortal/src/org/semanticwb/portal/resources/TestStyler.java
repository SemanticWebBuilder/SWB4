
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceException;


import org.semanticwb.portal.admin.resources.StyleInner;

public class TestStyler extends GenericResource {
    private static Logger log = SWBUtils.getLogger(TestStyler.class);
    private StyleInner si;

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String staticText = replaceTags(base.getAttribute("text"), request, paramRequest);
        PrintWriter out = response.getWriter();
        out.println(staticText);
        out.flush();
    }

    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest)
    {
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

        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
        str=SWBUtils.TEXT.replaceAll(str, "{webpath}", SWBPortal.getContextPath());
        str=SWBUtils.TEXT.replaceAll(str, "{distpath}", SWBPortal.getDistributorPath());
        str=SWBUtils.TEXT.replaceAll(str, "{webworkpath}", SWBPortal.getWebWorkPath());
        str=SWBUtils.TEXT.replaceAll(str, "{workpath}", SWBPortal.getWorkPath());
        return str;
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        String staticText = replaceTags(base.getAttribute("text"), request, paramRequest);
        PrintWriter out = response.getWriter();
        out.print(staticText);
        out.flush();
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base  = paramRequest.getResourceBase();
        System.out.println("this.getClass().getName()="+this.getClass().getName());
        System.out.println("base.getClass().getName()="+getResourceBase().getClass().getName());

        String cssResPath = "/"+SWBUtils.TEXT.replaceAll(getClass().getName(), ".", "/")+".css";
        si = new StyleInner(getResourceBase());
        String script = null;
        try {
            script = si.render(paramRequest, cssResPath);
        }catch(NullPointerException e) {
            log.error("Tal vez no exite el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
        }catch(IOException e) {
            log.error("Error al leer el archivo "+cssResPath+" en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
        }catch(Exception e) {
            log.error("Error in resource: "+base.getId() +"-"+ base.getTitle(), e);
        }
        PrintWriter out = response.getWriter();
        out.println(script);
        out.flush();
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillStyle")) {
            doEditStyle(request,response,paramsRequest);
        }else {
            super.processRequest(request, response, paramsRequest);
        }
    }

    public void doEditStyle(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("\n************ doEditStyle ************");
        Resource base = getResourceBase();
        String stel = request.getParameter("stel");
        System.out.println("stel="+stel);
        String[] tkns = stel.split("@",3);
        System.out.println("tkns[0]="+tkns[0]);
        System.out.println("tkns[1]="+tkns[1]);
        System.out.println("tkns[2]="+tkns[2]);
        System.out.println("\n\n");

        //HashMap matriz = (HashMap)mm.get(base.getId());
        HashMap tabs = (HashMap)si.getMm(base.getId());
        if(tabs != null) {
            try {
                HashMap t = (HashMap)tabs.get(tkns[0]);
                t.put(tkns[1], tkns[2]);
                //printMatriz();
                StringBuilder css = new StringBuilder();
                Iterator<String> ittabs = tabs.keySet().iterator();
                while(ittabs.hasNext()) {
                    String tab = ittabs.next();
                    css.append("."+tab);
                    css.append(" {\n");
                    HashMap selectors = (HashMap)tabs.get(tab);
                    Iterator<String> its = selectors.keySet().iterator();
                    while(its.hasNext()) {
                        String l = its.next();
                        css.append(l+":"+selectors.get(l)+";\n");
                    }
                    css.append("}\n");
                }
                System.out.println("\n******************\nestilo=\n"+css);

                /*String editaccess = request.getParameter("editar");
                if(editaccess!=null) {
                    base.setAttribute("editRole", editaccess);
                }
                try{
                    base.updateAttributesToDB();
                }catch(Exception e){
                    log.error("Error al guardar atributos en el recurso: "+base.getId() +"-"+ base.getTitle(), e);
                }*/



            }catch(IndexOutOfBoundsException iobe) {
                System.out.println("\n error... "+iobe);
            }
        }else {
            System.out.println("matriz es nulo");
        }
    }

    private void printMatriz() {
        si.printMatriz(getResourceBase().getId());
    }

    @Override
    public void processAction(javax.servlet.http.HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        System.out.println("\nprocessAction...");
    }
}
