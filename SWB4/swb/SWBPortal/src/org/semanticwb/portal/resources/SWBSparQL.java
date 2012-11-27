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
package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.semanticwb.SWBPlatform;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.platform.SemanticModel;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBSparQL.
 * 
 * @author juan.fernandez
 */
public class SWBSparQL extends GenericResource {

    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBSparQL.class);

    /** The encryptor. */
    private org.semanticwb.util.Encryptor encryptor = null;

    /** The Constant NL. */
    static public final String NL = System.getProperty("line.separator") ;

    /** The Constant PRM_QUERY. */
    public static final String PRM_QUERY="query";
    
    /** The Constant PRM_ONTTYPE. */
    public static final String PRM_ONTTYPE="onttype";
    
    /** The Constant PRM_MODELS. */
    public static final String PRM_MODELS="models";

    /** The Constant OT_RDF. */
    public static final String OT_RDF="rdf";
    
    /** The Constant OT_ONTOLOGY. */
    public static final String OT_ONTOLOGY="ontology";
    
    /** The Constant OT_ONTRDFINF. */
    public static final String OT_ONTRDFINF="ontrdfinf";

    /** The Constant DPL_HEAD. */
    public static final String DPL_HEAD="dplheader";
    
    /** The Constant DPL_ITERATOR. */
    public static final String DPL_ITERATOR="dpliterator";
    
    /** The Constant DPL_FOOT. */
    public static final String DPL_FOOT="dplfoot";

    /** The model. */
    private SemanticModel model=null;

    /**
     * Creates a new instance of WBADBQuery.
     */
    public SWBSparQL()
    {
        //pedir llave en la administracion
//        byte key[] = new java.math.BigInteger("05fe858d86df4b909a8c87cb8d9ad596", 16).toByteArray();
//        encryptor = new org.semanticwb.util.Encryptor(key);
    }

    /**
     * Creates the semantic model.
     */
    public void createSemanticModel()
    {
        model=null;
        String onttype=getResourceBase().getAttribute(PRM_ONTTYPE);
        String smodels=getResourceBase().getAttribute(PRM_MODELS);
        ArrayList<String> amodels=new ArrayList();
        if(smodels!=null)
        {
            StringTokenizer st=new StringTokenizer(smodels,"|");
            while(st.hasMoreTokens())
            {
                String tk=st.nextToken();
                amodels.add(tk);
            }
        }
        if(amodels.size()==1)
        {
            if(onttype.equals(OT_RDF))
            {
                model=SWBPlatform.getSemanticMgr().getModel(amodels.get(0));
            }
        }else if(amodels.size()>1)
        {

        }



    }

    /**
     * Gets the semantic model.
     * 
     * @return the semantic model
     */
    public SemanticModel getSemanticModel()
    {
        return model;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(org.semanticwb.model.Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        createSemanticModel();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doAdmin(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String query=getResourceBase().getAttribute(PRM_QUERY);
        String onttype=getResourceBase().getAttribute(PRM_ONTTYPE);
        String smodels=getResourceBase().getAttribute(PRM_MODELS);

        String dplhead=getResourceBase().getAttribute(DPL_HEAD);
        String dpliterator=getResourceBase().getAttribute(DPL_ITERATOR);
        String dplfoot=getResourceBase().getAttribute(DPL_FOOT);

        if(null==dplhead)
        {
            dplhead="<table style=\"font-family:verdana; font-size:10px;\">\n" +
                    "<thead>\n" +
                    "<tr style=\"background-color:blue; color:white;\"><th>Título</th><th>Descripción</th></tr>\n" +
                    "</thead>\n";
        }
        if(null==dplfoot) 
        {
            dplfoot="<tfoot style=\"background-color:blue; color:white;\">\n"+
                    "<tr><td>Registros encontrados: </td><td>{rows.number}</td></tr>\n"+
                    "<tr><td>Tiempo de ejecución: </td><td>{exec.time} milisegundos</td></tr>\n"+
                    "</tfoot>\n";
        }
        if(null==dpliterator) 
        {
            dpliterator="<tr style=\"color:blue;\"><td>{?title}</td><td>{?desc}</td></tr>"; 
        }
        if(null==query)
        {
            query   =   "PREFIX  swb:  <http://www.semanticwebbuilder.org/swb4/ontology#>\n"+
                        "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
                        "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n\n"+
                        "SELECT ?title ?desc WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage. ?x swb:description ?desc}";
        }


        ArrayList<String> amodels=new ArrayList();
        if(smodels!=null)
        {
            StringTokenizer st=new StringTokenizer(smodels,"|");
            while(st.hasMoreTokens())
            {
                String tk=st.nextToken();
                amodels.add(tk);
            }
        }

        String act=request.getParameter("act");
        if(act!=null)
        {
            query=request.getParameter(PRM_QUERY);
            getResourceBase().setAttribute(PRM_QUERY, query);
            onttype=request.getParameter(PRM_ONTTYPE);
            getResourceBase().setAttribute(PRM_ONTTYPE, onttype);
            String models[]=request.getParameterValues(PRM_MODELS);
            smodels="";
            amodels=new ArrayList();
            for(int x=0;x<models.length;x++)
            {
                smodels+=models[x];
                amodels.add(models[x]);
                if((x+1)<models.length)smodels+="|";
            }
            getResourceBase().setAttribute(PRM_MODELS, smodels);
            dplhead=request.getParameter(DPL_HEAD);
            getResourceBase().setAttribute(DPL_HEAD, dplhead);
            dpliterator=request.getParameter(DPL_ITERATOR);
            getResourceBase().setAttribute(DPL_ITERATOR, dpliterator);
            dplfoot=request.getParameter(DPL_FOOT);
            getResourceBase().setAttribute(DPL_FOOT, dplfoot);
            try
            {
                //System.out.println("oy:"+onttype+" sm:"+smodels+" q:"+query);
                getResourceBase().updateAttributesToDB();
            }catch(Exception e){log.error(e);}
            createSemanticModel();
        }

        if(onttype==null)onttype="";
        if(query==null)query="";

        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require(\"dijit.form.Form\");");
        out.println("  dojo.require(\"dijit.form.Button\");");
        out.println("</script>");

        out.println("<div class=\"swbform\">");
        out.println("<form dojoType=\"dijit.form.Form\" id=\""+getResourceBase().getId()+"/sparql\" action=\""+paramRequest.getRenderUrl()+"\" method=\"post\" >");
        out.println("<input type=\"hidden\" name=\"act\" value=\"upd\">");

        out.println("<fieldset>");
        out.println("SPARQL Example:");
        out.println("<PRE >");
        out.println("PREFIX  swb:  &lt;http://www.semanticwebbuilder.org/swb4/ontology#&gt;");
        out.println("PREFIX  rdfs: &lt;http://www.w3.org/2000/01/rdf-schema#&gt;");
        out.println("PREFIX  rdf:  &lt;http://www.w3.org/1999/02/22-rdf-syntax-ns#&gt;");
        out.println("<br>");
        out.println("SELECT ?title ?desc WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage. ?x swb:description ?desc}");
        out.println("</PRE>");
        out.println("Ontology Type:");
        out.println("<br/>");
        out.println("<select name='"+PRM_ONTTYPE+"'>");
        out.println("<option value='rdf' "+(onttype.equals("rdf")?"selected":"")+">RDF (No Inference)</option>");
        out.println("<option value='ontology' "+(onttype.equals("ontology")?"selected":"")+">Ontology</option>");
        out.println("<option value='ontrdfinf' "+(onttype.equals("ontrdfinf")?"selected":"")+">Ontology RDFS Inf.</option>");
        out.println("</select>");
        out.println("<br/>");
        out.println("Models:");
        out.println("<br/>");
        out.println("<select multiple name='"+PRM_MODELS+"'>");
        TreeSet set=new TreeSet();
        Iterator<Entry<String, SemanticModel>> it=SWBPlatform.getSemanticMgr().getModels().iterator();
        while(it.hasNext())
        {
            Entry<String,SemanticModel> ent=it.next();
            set.add(ent.getKey());
        }
        set.add("SWBSchema");
        Iterator <String>it2=set.iterator();
        while(it2.hasNext())
        {
            String key=it2.next();
            String sel="";
            if(amodels.contains(key))sel="selected";
            out.println("<option value='"+key+"' "+sel+">"+key+"</option>");
        }
        out.println("</select>");
        out.println("<br/>");

        out.println("SPARQL:");
        out.println("<br/>");
        out.print("<textarea name=\""+PRM_QUERY+"\" rows=10 cols=80>");
        out.print(query);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<legend>Configuracion Despliegue</legend>");
        out.println("<br/>");
        out.println("Encabezado:");
        out.println("<br/>");
        out.print("<textarea name=\""+DPL_HEAD+"\" rows=10 cols=80>");
        out.print(dplhead);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Iteración:");
        out.println("<br/>");
        out.print("<textarea name=\""+DPL_ITERATOR+"\" rows=10 cols=80>");
        out.print(dpliterator);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("Pie:");
        out.println("<br/>");
        out.print("<textarea name=\""+DPL_FOOT+"\" rows=10 cols=80>");
        out.print(dplfoot);
        out.println("</textarea>");
        out.println("<br/>");
        out.println("<font style=\"color: #428AD4; font-family: Verdana; font-size: 10px;\">");
		out.println("		<b>Tags:</b><br/>");
        out.println("       &nbsp;&nbsp;{?XXXXXX} Para la parte iteración.<BR>");
        out.println("       &nbsp;&nbsp;{user.login}<BR>");
        out.println("       &nbsp;&nbsp;{user.email}<BR>");
        out.println("       &nbsp;&nbsp;{user.language}<BR>");
        out.println("       &nbsp;&nbsp;{rows.number} Para la parte del pie.<BR>");
        out.println("       &nbsp;&nbsp;{exec.time}  Para la parte del pie.<BR>");
        out.println("       &nbsp;&nbsp;{getEnv(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{request.getParameter(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{session.getAttribute(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{encode(\"XXXXX\")}<BR>");
        out.println("       &nbsp;&nbsp;{encodeB64(\"XXXXX\")}<BR>");
        out.println("       <BR>&nbsp;&nbsp;<b>Note:</b> XXXXX=Text<BR><BR>");
        out.println("       <B>Samples:</B><BR>");
        out.println("       &nbsp;&nbsp;");//Single Sign-ON with Basic Authorization<BR>
        out.println("       &nbsp;&nbsp;Headers: Authorization=Basic {encodeB64(\"user:password\")}<BR>");
        out.println("       &nbsp;&nbsp;Iteración: {?title} {?description} <br/>");
		out.println("	</font>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"submit/btnSend\" >"+paramRequest.getLocaleString("send")+"</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        String queryString=getResourceBase().getAttribute("query");
        long time=System.currentTimeMillis();
        long extime = 0;
        try
        {
            if(queryString!=null && queryString.length()>0)
            {
                out.println("<fieldset>");
                out.println("<table border=0>");

                QueryExecution qexec = getSemanticModel().sparQLQuery(replaceTags(queryString,request,paramRequest,null));
                // Or QueryExecutionFactory.create(queryString, model) ;
                try {
                    // Assumption: it's a SELECT query.
                    ResultSet rs = qexec.execSelect() ;
                    int col = rs.getResultVars().size();
                    out.println(replaceTags(getResourceBase().getAttribute(DPL_HEAD),request,paramRequest,null));
                    int rows=0;
                    // The order of results is undefined.
                    if(DPL_ITERATOR!=null&&DPL_ITERATOR.trim().length()>0)
                    {
                        for ( ; rs.hasNext() ; )
                        {
                            QuerySolution rb = rs.nextSolution() ;
                            out.println(replaceTags(getResourceBase().getAttribute(DPL_ITERATOR),request,paramRequest,rb));
                            rows++;
                        }
                    }
                    extime = System.currentTimeMillis()-time;
                    request.setAttribute("extime", Long.toString(extime));
                    request.setAttribute("rowsnum", Integer.toString(rows));
                    out.println(replaceTags(getResourceBase().getAttribute(DPL_FOOT),request,paramRequest,null));
                }
                finally
                {
                    // QueryExecution objects should be closed to free any system resources

                    qexec.close() ;
                }
            }
        }catch(Exception e)
        {

            //Comentado para que no  muestre nada en caso de no recibir el parametro o
            //que este mal definida la expresión de sparql

//            out.println("<fieldset>");
//            out.println("Error: <BR>");
//            out.println("<textarea name=\"query\" rows=20 cols=80>");
//            e.printStackTrace(out);
//            out.println("</textarea>");
//            out.println("</fieldset>");
        }
    }

    /**
     * Replace tags.
     * 
     * @param str the str
     * @param request the request
     * @param paramRequest the param request
     * @param qs the qs
     * @return the string
     */
    public String replaceTags(String str, HttpServletRequest request, SWBParamRequest paramRequest, QuerySolution qs)
    {
        //System.out.print("\nstr:"+str+"-->");
        if(str==null || str.trim().length()==0)return null;
        str=str.trim();
        //TODO: codificar cualquier atributo o texto
        Iterator it=SWBUtils.TEXT.findInterStr(str, "{encode(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{encode(\""+s+"\")}", encryptor.encode(replaceTags(s,request,paramRequest,qs)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{encodeB64(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{encodeB64(\""+s+"\")}", SFBase64.encodeString(replaceTags(s,request,paramRequest,qs)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{request.getParameter(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{request.getParameter(\""+s+"\")}", request.getParameter(replaceTags(s,request,paramRequest,qs)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{session.getAttribute(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{session.getAttribute(\""+s+"\")}", (String)request.getSession().getAttribute(replaceTags(s,request,paramRequest,qs)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{getEnv(\"", "\")}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{getEnv(\""+s+"\")}", SWBPlatform.getEnv(replaceTags(s,request,paramRequest,qs)));
        }

        it=SWBUtils.TEXT.findInterStr(str, "{?", "}");
        while(it.hasNext())
        {
            String s=(String)it.next();
            str=SWBUtils.TEXT.replaceAll(str, "{?"+s+"}", qs.get(s).toString());
        }

        str=SWBUtils.TEXT.replaceAll(str, "{rows.number}", request.getAttribute("rowsnum")!=null?(String)request.getAttribute("rowsnum"):"N/A");
        str=SWBUtils.TEXT.replaceAll(str, "{exec.time}", (String)request.getAttribute("extime"));
        str=SWBUtils.TEXT.replaceAll(str, "{user.login}", paramRequest.getUser().getLogin());
        str=SWBUtils.TEXT.replaceAll(str, "{user.email}", paramRequest.getUser().getEmail());
        str=SWBUtils.TEXT.replaceAll(str, "{user.language}", paramRequest.getUser().getLanguage());
        //System.out.println(str);
        return str;
    }


//    public String cropStr(String initext, String endtext, String content)
//    {
//        if(initext==null && endtext==null)return content;
//        //ini
//        if(initext!=null)
//        {
//            StringTokenizer st = new StringTokenizer(initext, ";,");
//            while (st.hasMoreTokens())
//            {
//                String a1;
//                String a2;
//                String wp = st.nextToken();
//                int i=wp.indexOf("|");
//                if(i>-1)
//                {
//                    a1=wp.substring(0,i);
//                    a2=wp.substring(i+1);
//                }else
//                {
//                    a1=wp;
//                    a2="";
//                }
//                int ini=content.indexOf(a1);
//                if(ini>=0)
//                {
//                    content=a2+content.substring(ini+a1.length());
//                    break;
//                }
//            }
//        }
//        //end
//        if(endtext!=null)
//        {
//            StringTokenizer st = new StringTokenizer(endtext, ";,");
//            while (st.hasMoreTokens())
//            {
//                String a1;
//                String a2;
//                String wp = st.nextToken();
//                int i=wp.indexOf("|");
//                if(i>-1)
//                {
//                    a1=wp.substring(0,i);
//                    a2=wp.substring(i+1);
//                }else
//                {
//                    a1=wp;
//                    a2="";
//                }
//                int end=content.indexOf(a1);
//                if(end>=0)
//                {
//                    content=content.substring(0,end)+a2;
//                    break;
//                }
//            }
//        }
//        return content;
//    }
//
//    public String replaceStr(String replace, String content)
//    {
//        if(replace==null)return content;
//        StringTokenizer st = new StringTokenizer(replace, ";,");
//        while (st.hasMoreTokens())
//        {
//            String wp = st.nextToken();
//            int i=wp.indexOf("|");
//            if(i>-1)
//            {
//                String a1=wp.substring(0,i);
//                String a2=wp.substring(i+1);
//                content = content.replaceAll(a1, a2);
//            }else
//            {
//                content = content.replaceAll(wp, "");
//            }
//        }
//        return content;
//    }

}

