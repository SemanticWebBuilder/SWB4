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
package org.semanticwb.servlet.internal;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.sun.msv.verifier.regexp.StringToken;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleSiteMap.
 * 
 * @author jei
 */
public class LinkedData implements InternalServlet {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(LinkedData.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config) throws ServletException
    {
        log.event("Initializing InternalServlet LinkedData...");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        String acont=request.getContextPath();
        String ruri=request.getRequestURI();
        
        //System.out.println(ruri);
        //System.out.println(request.getRequestURL());
        
        String base=request.getRequestURL().substring(0,request.getRequestURL().indexOf(ruri));
        //System.out.println(base);
        
        int lcont=0;
        if(acont!=null)
        {
            lcont=acont.length();
            if(lcont==1)lcont=0;
        }
        String path=ruri.substring(lcont);
        path=SWBUtils.IO.normalizePath(path);
        
        if(path !=null)
        {
            StringTokenizer st=new StringTokenizer(path,"/");
            String dist = null;
            String m = null;
            String sid = null;
                        
            if(st.hasMoreTokens())dist = st.nextToken();
            if(st.hasMoreTokens())m = st.nextToken();
            if(st.hasMoreTokens())sid = st.nextToken();
                
            if(m==null)
            {
                response.sendError(404, path);
                return;
            }
            SemanticModel site=SWBPlatform.getSemanticMgr().getModel(m);

            if(site.getModelObject().instanceOf(UserRepository.sclass))
            {
                response.sendError(404, path);
                return;
            }
                
            SemanticObject obj=null;
            if(sid==null)obj=site.getModelObject();
            else
            {
                String uri=site.getNameSpace()+sid;
                obj=site.getSemanticObject(uri);
            }

            ByteArrayOutputStream bout=new ByteArrayOutputStream();

            Model model=ModelFactory.createDefaultModel();
            model.add(obj.getRDFResource().listProperties());

            Iterator<Statement> its=site.getRDFModel().listStatements(null, null, obj.getRDFResource());
            while(its.hasNext())
            {
                Statement sta=(Statement)its.next();
                SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(sta.getPredicate());
                Resource res=sta.getSubject();
                if(res!=null)
                {
                    if(prop.isInverseOf())
                    {
                        model.add(obj.getRDFResource(), prop.getInverse().getRDFProperty(), res);        
                    }
                }
            }     

            // "RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE" and "N3"
            model.write(bout, "RDF/XML");

            String txt=bout.toString("UTF8"); 

            txt=txt.replace("http://www.semanticwebbuilder.org/", "http://www.semanticwebbuilder.org.mx/");

            Iterator<SWBModel> it=SWBContext.listSWBModels(false);
            while(it.hasNext())
            {
                SWBModel smodel=it.next();
                txt=txt.replace(smodel.getSemanticModel().getNameSpace(), base+"/resource/"+smodel.getId()+"/");
            } 
            
            response.setContentType("text/xml");
            try
            {
                response.setCharacterEncoding("UTF-8");
                response.getOutputStream().print(txt);            
            }catch(NoSuchMethodError e)
            {
                response.getOutputStream().print(SWBUtils.TEXT.encode(txt, "UTF-8"));            
            }            
        }else
        {
            response.sendError(404, path);
        }
        
    }

}
