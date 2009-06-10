/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources.demo;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.catalogs.LocationEntity;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.semanticwb.model.comm.MicroSite;


/**
 *
 * @author serch
 */
public class ListaCommun extends GenericResource {
    long refresh = 0;
    String buffer = "Cargando...... ";
    private static final String NL = System.getProperty("line.separator");

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        WebPage temas = paramRequest.getTopic().getWebSite().getWebPage("Temas");
        Model model = paramRequest.getTopic().getSemanticObject().getModel().getRDFModel();
        WebPage locate  = paramRequest.getTopic().getWebSite().getWebPage("Tlalpan");
        PrintWriter out = response.getWriter();
        out.println(doSearchComm(temas, (LocationEntity)locate, model));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        super.processAction(request, response);
    }

    private String doSearch(WebPage temas, LocationEntity localidad, Model model){
        String prolog = "PREFIX  swb:  <http://www.semanticwebbuilder.org/swb4/ontology#>"+NL+
        "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+NL+
        "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+NL+
        "PREFIX swbcomm: <http://www.semanticwebbuilder.org/swb4/community#>"+NL+
        "PREFIX swbc: <http://www.semanticwebbuilder.org/swbcatalogs#>"+NL;

        prolog += "SELECT ?x WHERE {?x rdf:type swbcomm:TematicComm ";
        prolog += ". ?x swbcomm:locatedIn <"+localidad.getURI()+"> ";
        prolog += ". ?x swbcomm:about <"+temas.getURI()+"> ";
        prolog += "}";

        Query query = QueryFactory.create(prolog);

        // System.out.println(getId());
        // Print with line numbers
        // query.serialize(new IndentedWriter(System.out,true)) ;
        // System.out.println() ;

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try
        {
            ResultSet rs = qexec.execSelect();
            
            for (; rs.hasNext();)
            {
                QuerySolution rb = rs.nextSolution();
                String current = rb.get("x").toString();

            }
        } catch (Exception e) {}


        return "";
    }

    private String doSearchComm(WebPage temas, LocationEntity localidad, Model model)
    {
        SortedSet<Contenedor> set = new TreeSet<Contenedor>();
        Hashtable<String,Contenedor> tabla = new Hashtable<String, Contenedor>();
        WebPage contenedor = temas.getWebSite().getWebPage("Comunidades");
        GenericIterator<WebPage>git = contenedor.listChilds();
        while (git.hasNext()){
            MicroSite act = (MicroSite)git.next();
            //System.out.println("====== "+act.getDisplayName());
            if (null==act.getAbout()) 
            {
                //System.out.println("******************** "+ act.getDisplayName() +" trae about null");
                continue;
            }
            String name = act.getAbout().getDisplayName();
            String url = act.getAbout().getRealUrl();
            LocationEntity location = act.getLocatedIn();
            if (localidad.equals(location)){
                if (tabla.containsKey(name)){
                Contenedor este = tabla.get(name);
                este.incVal();
                //tabla.put(name, este);
                } else
                {
                    tabla.put(name, new Contenedor(name, url));
                }
            }
        }
        Enumeration<Contenedor> enucon = tabla.elements();
        while (enucon.hasMoreElements()) set.add(enucon.nextElement());
        Iterator<Contenedor> it = set.iterator();
        String ret = "<ul>\n";
        int cnt = 0;
        while (it.hasNext() && cnt<12) {
            cnt++;
        Contenedor este = it.next();
        ret += "<li><p><a href=\""+este.getUrl()+"\">"+este.getId()+" ("+este.getCount()+")</a></p></li>\n";
        }
        ret += "</ul>";
        //System.out.println(set.toString());
        return ret;
    }

    /**
     *  <ul>
          <li><p>Cine (24)</p></li>
          <li><p>Danza (3)</p></li>
          <li><p>Teatro (7)</p></li>
          <li><p>Artes pl·sticas (55)</p></li>
          <li><p>Literatura (46)</p></li>
          <li><p>Automovilismo (24)</p></li>
		  <li><p>Deportes (3)</p></li>
		  <li><p>Campismo (7)</p></li>
		  <li><p>M˙sica (55)</p></li>
		  <li><p>Viajes (46)</p></li>
        </ul>
     */



}



    class Contenedor implements Comparable{
    private String name=null;
    private int cont = 0;
    private String url = null;
        public Contenedor(String name, String url){
            this.name = name;
            this.url = url;
            cont=1;
        }
        public void incVal(){
        cont++;
        }
        public String getId(){
            return name;
        }
        public int getCount(){
            return cont;
        }

        public String getUrl()
        {
            return url;
        }

        public int compareTo(Object o)
        {
            Contenedor other = (Contenedor)o;
            if (other.getCount()>getCount()) return 1;
            if (other.getCount()==getCount()) return other.getId().compareTo(getId());
            return -1;
        }

        @Override
        public boolean equals(Object obj)
        {
            return name.equals(obj);
        }

        @Override
        public int hashCode()
        {
            return name.hashCode();
        }


    }
