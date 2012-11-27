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
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * The Class MapTree.
 * 
 * @author juan.fernandez
 */
public class MapTree extends GenericResource{

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equals("getData")){
            doData(request,response,paramsRequest);
        }else if(paramsRequest.getMode().equals("getChilds")){
            doChilds(request,response,paramsRequest);
        }else{
            super.processRequest(request,response,paramsRequest);
        }
    }

    
    /**
     * Do data.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doData(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException 
    {
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        out.print(getData(paramsRequest.getWebPage()));
    }
    
    /**
     * Do childs.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doChilds(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException 
    {
        response.setHeader("mimetype", "text/json-comment-filtered");
        PrintWriter out = response.getWriter();
        WebPage tp = paramsRequest.getWebPage();
        String ret = getOnlyChilds(tp);
        out.print(ret);
    }
    
    /**
     * Gets the data.
     * 
     * @param tp the tp
     * @return the data
     */
    public String getData(WebPage tp)
    {
        StringBuffer ret = new StringBuffer();

        WebSite tm = tp.getWebSite();
        WebPage tptmp =  tp;
        ret.append("{");
        ret.append("  identifier: 'id',");
        ret.append("  label: 'name',");
        ret.append("  url: 'tpurl',");
        ret.append("  children: 'childs',");
        ret.append("  loaded: 'boolean',");
        ret.append("  items: [");
        if(tp.getParent()==null) tptmp = tm.getHomePage();
        else tptmp = tp.getParent();
        
        ret.append(" {");
        ret.append(" id:'"+tptmp.getId()+"', carpeta:'true', name:'"+tptmp.getDisplayName()+"', tpurl:'"+tptmp.getUrl()+"', type:'home', loaded:'true' "); //"+tptmp.getLevel()+"
        
        Iterator<WebPage>  ittpb = tptmp.listChilds();
        if(ittpb.hasNext())
        {

            Iterator<WebPage>  ittp = tptmp.listChilds();

            if(ittp.hasNext())
            {
                ret.append(", children: [");

                while(ittp.hasNext())
                {
                    WebPage tp1 = ittp.next();
                    if(null!=tp1)
                    {
                        ret.append(" {");
                        ret.append(" id:'"+tp1.getId()+"', ");
                        if(tp1.listChilds().hasNext()) //ret.append(getChilds(tp1));
                        {
                            ret.append("carpeta:'true', loaded:'false', ");
                        }
                        else
                        {
                            ret.append("carpeta:'false', loaded:'true', ");
                        }
                        ret.append("name:'"+tp1.getTitle()+"', tpurl:'"+tp1.getUrl()+"',  type:'"+tp1.getParent().getId()+"' "); //type:'"+tp1.getType().getId()+"'
                        if(tp1.listChilds().hasNext()) //ret.append(getChilds(tp1));
                        {
                            ret.append(", children: []");
                        }
                        ret.append(" }");
                        if(ittp.hasNext()) ret.append(", ");
                    }
                }
                ret.append(" ]");
            }
        }
        ret.append("}");
        ret.append("]");
        ret.append("}");
        
        return ret.toString();
    }
    
    /**
     * Gets the childs.
     * 
     * @param tptmp the tptmp
     * @return the childs
     */
    public String getChilds(WebPage tptmp)
    {
        StringBuffer ret = new StringBuffer();
        Iterator<WebPage>  ittp = tptmp.listChilds();        
        if(ittp.hasNext())
        {
            ret.append(", children: [");    
            ret.append("]");
        }        
        return ret.toString();
    }
    
    /**
     * Gets the only childs.
     * 
     * @param tptmp the tptmp
     * @return the only childs
     */
    public String getOnlyChilds(WebPage tptmp)
    {
        StringBuffer ret = new StringBuffer();
        Iterator<WebPage>  ittp = tptmp.listChilds();        
        if(ittp.hasNext())
        {      
            while(ittp.hasNext())
            {
                WebPage tp1 = ittp.next();
                if(null!=tp1)
                {
                    ret.append(" {");
                    ret.append(" id:'"+tp1.getId()+"', ");
                    if(tp1.listChilds().hasNext())
                    {
                        ret.append("carpeta:'true', loaded:'false', ");
                    }
                    else
                    {
                        ret.append("carpeta:'false', loaded:'true', ");
                    }
                    ret.append("name:'"+tp1.getTitle()+"', tpurl:'"+tp1.getUrl()+"', type:'"+tp1.getParent().getId()+"'");
                    if(tp1.listChilds().hasNext())
                    {
                        ret.append(", children: [] ");
                    }
                    
                    ret.append(" }");
                    if(ittp.hasNext()) ret.append(", ");
                }
            }
        }        
        return ret.toString();
    }

    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        String path = SWBPlatform.getContextPath();
        WebPage tp = paramsRequest.getWebPage();
        out.println("<html>");
        out.println("    <head>");
        out.println("       <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");

        /*out.println("<style type=\"text/css\">");
        out.println("        @import \""+path+"dojo1/dijit/themes/tundra/tundra.css\";");
        out.println("        @import \""+path+"dojo1/dojo/resources/dojo.css\";");
        out.println("        @import \""+path+"dojo1/dojo/resources/dnd.css\";");
        out.println("        @import \""+path+"dojo1/dijit/themes/tundra/tundra_rtl.css\";");
        out.println("        @import \""+path+"dojo1/dijit/tests/css/dijitTests.css\";");
        out.println("</style>");*/

        /*out.println("<script type=\"text/javascript\" src=\""+path+"dojo1/dojo/dojo.js\" djConfig=\"parseOnLoad:true, usePlainJson:true\"></script>");*/
        
        out.println("<script language=\"JavaScript\" type=\"text/javascript\">");
        out.println("  dojo.require(\"dojo.data.ItemFileReadStore\"); ");
        out.println("  dojo.require(\"dojo.data.ItemFileWriteStore\"); ");
        out.println("  dojo.require(\"dojo.dnd.Source\");");
        out.println("  dojo.require(\"dojo.dnd.common\");");
        //out.println("  dojo.require(\"dojo.parser\"); ");
        out.println("  dojo.require(\"dijit.Tree\"); ");
        out.println("  dojo.require(\"dijit.Menu\"); ");
        out.println("  dojo.require(\"dijit.MenuItem\"); ");
        out.println("  dojo.require(\"dijit.PopupMenuBarItem\"); ");
        out.println("  dojo.require(\"dijit.Editor\");");        


        out.println(" dojo.addOnLoad(function(){ ");

out.println("	                        var tree1 = dijit.byId('mytreetp'); ");
out.println("	                }); ");
	
	                //custom function to handle drop for tree two.  Items dropped onto nodes here should be applied to the items attribute in this particular store.
out.println("	                function tree2CustomDrop(source,nodes,copy){ ");
out.println("	                        if (this.containerState==\"Over\"){ ");
out.println("	                                this.isDragging=false; ");
out.println("	                                var target= this.current; ");
out.println("	                                var items = this.itemCreator(nodes, target); ");

out.println("	                                if (!target || target==this.tree.domNode){ ");
out.println("	                                        for (var i=0; i<items.length;i++){ ");
out.println("	                                                this.tree.store.newItem(items[i],null); ");
out.println("	                                        } ");
out.println("	                                }else { ");
out.println("	                                        for (var i=0; i<items.length;i++){ ");
out.println("	                                                pInfo={parent:dijit.getEnclosingWidget(target).item, attribute:\"items\"}; ");
out.println("	                                                this.tree.store.newItem(items[i],pInfo); ");
out.println("	                                        } ");
out.println("	                                } ");
out.println("	                        } ");
out.println("	                } ");
	
	                //on tree two, we only want to drop on containers, not on items in the containers
out.println("	                function tree2CheckItemAcceptance(node,source) { ");
out.println("	                        //return true; ");
out.println("	                        var item = dijit.getEnclosingWidget(node).item; "); //var item = this.tree.getEnclosingWidget(node).item;
out.println("	                        if (!item.root){ ");
out.println("	                                return true; ");
out.println("	                        } ");

out.println("	                        return false; ");
out.println("	                } ");

out.println("	                function tree2ItemCreator(nodes){ ");
out.println("	                        var items = [] ");
	
out.println("	                        for(var i=0;i<nodes.length;i++){ ");
out.println("	                                items.push({ ");
out.println("	                                        \"name\":nodes[i].name, ");
out.println("	                                        \"id\": nodes[i].id, ");
out.println("	                                        \"tpurl\":nodes[i].tpurl, ");
out.println("	                                        \"type\": nodes[i].type ");
out.println("	                                }); ");
out.println("	                        } ");
out.println("	                        return items; ");
out.println("	                } ");
	
out.println("	                function dndAccept(source,nodes){ ");
out.println("	                        return true; ");
out.println("	                        if (this.tree.id==\"mytreetp\"){ ");
out.println("	                                return true; ");
out.println("	                        } ");
out.println("	                        return false; ");
out.println("	                } ");
	
        
        out.println("  function intoNode(node, url){");
        out.println("   alert('tpurl:'+url);");
        out.println("    return {");
        out.println("      url: url,");
        out.println("      node: dojo.byId(node),");
        out.println("      load: loadFromQuery");
        //out.println("      load: getData");
        out.println("    };");
        out.println("  } ");
        
        out.println("  function loadFromQuery(url){ ");
        out.println("    var get = dojo.xhrGet({ url: url+'/_mod/getChilds/_rid/57/_mto/3' }); ");
        out.println("    return function(node){ ");
        out.println("      get.addCallback(function(data){ ");
        out.println("        node.innerHTML = data; ");
        out.println("        return data; ");
        out.println("      }); ");
        out.println("    } ");
        out.println("  } ");

        out.println("   function getData(url,item)"); // Cargando con AJAX los hijos del t�pico
        out.println("   {");
        out.println("       var jsonurl = url+'/_rid/"+base.getId()+"/_mod/getChilds/_mto/3/'; ");
        out.println("       dojo.xhrGet( { ");
        out.println("           url: jsonurl, ");
        out.println("           handleAs: 'text', ");
        out.println("           load: function(responseObject, ioArgs) { ");
        out.println("               var items = eval('['+ responseObject+']');" );
        out.println("               for (var i=0; i<items.length;i++){ ");
        out.println("                   pInfo={parent:item, attribute:\"children\"}; ");
        out.println("                   storetp.newItem(items[i],pInfo); ");
        out.println("               } ");
        out.println("               item['loaded']=eval('[true]');");
        out.println("               storetp.save();");
        out.println("               return responseObject;");
        out.println("           }");
        out.println("       }); ");
        out.println("       return; ");
        out.println("   } ");
        
        out.println("</script>");

        out.println("    </head>");
        
        out.println("    <body class=\"tundra\">");
              
        SWBResourceURL url = paramsRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode("getData");

        // Para el �rbol de t�picos
        
        out.println("    <div dojoType=\"dojo.data.ItemFileWriteStore\" jsId=\"storetp\" url=\""+url+"\" ></div>"); 
        
        out.println("    <ul dojoType=\"dijit.Menu\" id=\"tree_menu\" style=\"display: none;\" args=\"item\"> ");
        out.println("      <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Hello world');\">Enabled Item</li> ");
        out.println("      <li dojoType=\"dijit.MenuItem\" disabled=\"true\">Disabled Item</li> ");
        out.println("      <li dojoType=\"dijit.MenuItem\" onClick=\"return getData(storetp.getValue(item, 'tpurl'), item)\">Reload</li> ");
        out.println("      <li dojoType=\"dijit.MenuItem\" iconClass=\"dijitEditorIcon dijitEditorIconCut\" ");
        out.println("        onClick=\"alert('not actually cutting anything, just a test!')\">Cut</li> ");
        out.println("      <li dojoType=\"dijit.MenuItem\" iconClass=\"dijitEditorIcon dijitEditorIconCopy\" ");
        out.println("        onClick=\"alert('not actually copying anything, just a test!')\">Copy</li> ");
        out.println("      <li dojoType=\"dijit.MenuItem\" iconClass=\"dijitEditorIcon dijitEditorIconPaste\" ");
        out.println("        onClick=\"alert('not actually pasting anything, just a test!')\">Paste</li> ");
        out.println("      <li dojoType=\"dijit.PopupMenuItem\"> ");
        out.println("        <span>Enabled Submenu</span> ");
        out.println("        <ul dojoType=\"dijit.Menu\" id=\"submenu2\"> ");
        out.println("          <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Submenu 1!')\">Submenu Item One</li> ");
        out.println("          <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Submenu 2!')\">Submenu Item Two</li> ");
        out.println("          <li dojoType=\"dijit.PopupMenuItem\"> ");
        out.println("            <span>Deeper Submenu</span> ");
        out.println("            <ul dojoType=\"dijit.Menu\" id=\"submenu4\"> ");
        out.println("              <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Sub-submenu 1!')\">Sub-sub-menu Item One</li> ");
        out.println("              <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Sub-submenu 2!')\">Sub-sub-menu Item Two</li> ");
        out.println("            </ul> ");
        out.println("          </li> ");
        out.println("        </ul> ");
        out.println("      </li> ");
        out.println("      <li dojoType=\"dijit.PopupMenuItem\" disabled=\"true\"> ");
        out.println("        <span>Disabled Submenu</span> ");
        out.println("        <ul dojoType=\"dijit.Menu\" id=\"submenu3\" style=\"display: none;\"> ");
        out.println("          <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Submenu 1!')\">Submenu Item One</li> ");
        out.println("          <li dojoType=\"dijit.MenuItem\" onClick=\"alert('Submenu 2!')\">Submenu Item Two</li> ");
        out.println("        </ul> ");
        out.println("      </li> ");
        out.println("    </ul> ");
        
        
	out.println("    <div dojoType=\"dijit.tree.ForestStoreModel\" jsId=\"modeltp\" store=\"storetp\" >"); 
//		<!-- Override all the data access functions to work from the I18N data store -->
    out.println("    	<script type=\"dojo/method\" event=\"getChildren\" args=\"item, onComplete\">");
	out.println("    		switch(item.root ?  \"top\" : storetp.getValue(item, \"type\")){");
	out.println("    			case \"top\":");
	out.println("    				return storetp.fetch({query: {type:'home'}, onComplete: onComplete});");
	out.println("    			default:");
	out.println("    				return dijit.tree.ForestStoreModel.prototype.getChildren.apply(this, arguments);");
	out.println("    		}");
	out.println("    	</script>");

	out.println("    	<script type=\"dojo/method\" event=\"mayHaveChildren\" args=\"item\">");
	out.println("    		if(item.root){ return true; }");	// top level
	out.println("    		var folder = storetp.getValue(item, \"carpeta\");");
	out.println("    		return (folder=='true');");
	out.println("    	</script>");
	out.println("    </div>");
        


        
        out.println("<table width=\"100%\">");

        out.println("	<tr>");
	out.println("		<td width=\"30%\" style=\"vertical-align: top;\">");
        
	out.println("			<div dojoType=\"dijit.Tree\" id=\"mytreetp\" model=\"modeltp\" class=\"container\" childrenAttr=\"children\" dndController=\"dijit._tree.dndSource\" checkAcceptance=\"dndAccept\" checkItemAcceptance=\"tree2CheckItemAcceptance\"  >"); //dndController=\"dijit._tree.dndSource\" //query=\"{type: 'topic'}\" labelAttr=\"name\" label=\"WebSite\"
        
        out.println("	 <script type=\"dojo/connect\" args=\"item\"> ");
        out.println("	    var menu = dijit.byId(\"tree_menu\");");
            // when we right-click anywhere on the tree, make sure we open the menu
        out.println("	    menu.bindDomNode(this.domNode);");
        out.println("	    dojo.connect(menu, \"_openMyself\", this, function(e, item){");

              // IMPLEMENT CUSTOM MENU BEHAVIOR HERE
        out.println("	    });");
        out.println("	  </script>");
        

	out.println("				<script type=\"dojo/method\" event=\"getIconClass\" args=\"item\">");
	out.println("					var icon = dijit.Tree.prototype.getIconClass.apply(this, arguments);");
	out.println("					return icon;");
	out.println("				</script>");
	out.println("				<script type=\"dojo/method\" event=\"getLabel\" args=\"item\">");
	out.println("					if(item.root){ ");
        out.println("                                       return \""+tp.getWebSite().getTitle()+"\"; ");
        out.println("					 }");
        out.println("					return  storetp.getValue(item,\"name\");");
	out.println("				</script>");

        out.println("				<script type=\"dojo/method\" event=\"onClick\" args=\"item, node\">");
        out.println("                               if(item){");
        out.println("					if(item.root){ ");
        out.println("                                       window.location='"+tp.getWebSite().getHomePage().getUrl()+"';");
        out.println("                                       ");
        out.println("					 }");
        out.println("					var tipo = storetp.getValue(item,\"carpeta\");");
        out.println("					var cargado = storetp.getValue(item,\"loaded\");");
        out.println("					if(tipo=='true' && cargado=='false' ){ ");
        out.println("                                       return getData(storetp.getValue(item, \"tpurl\"), item);");
        out.println("                                   }");
        out.println("                                     window.location=storetp.getValue(item,\"tpurl\");");
        out.println("                               }");
        out.println("                           </script>");

	out.println("			</div>");
	out.println("		</td>");
	out.println("	</tr>");
        out.println("	<tr>");
        out.println("		<td style=\"vertical-align: top;\">");
        out.println("Data:");
        out.println("<textarea id=\"resultado\" name=\"resultado\" width=\"500px\" >"); //plugins=\"['copy','cut','paste','|','bold']\" dojoType=\"dijit.Editor\" 
        out.println(getData(paramsRequest.getWebPage()));
        out.println("</textarea>");
	out.println("		</td>");
	out.println("	</tr>");
	out.println("</table>");
        
        out.println("    </body>");
        out.println("</html>");
        
    }    
        
}

