<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%><%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    String lang="es";
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("suri");
    SemanticOntology ont=SWBPlatform.getSemanticMgr().getOntology();
    com.hp.hpl.jena.rdf.model.Resource res=ont.getResource(id);
    //System.out.println("suri:"+id);
    if(res==null)return;
    SemanticClass cls=ont.getSemanticObjectClass(res);
    GenericObject obj=ont.getGenericObject(id,cls);
    out.println("<div dojoType=\"dijit.layout.TabContainer\" region=\"center\" style=\"width=100%;height=100%;\" id=\""+id+"/tab2\" _tabPosition=\"bottom\" onSelected=\"alert('hola');\" nested=\"true\" _selectedChild=\"btab1\">");
    //out.println("    <script type=\"dojo/method\" event=\"onClick\" args=\"item\">");
    //out.println("       alert(item);");
    //out.println("    </script>");

    Iterator<ObjectBehavior> obit=SWBComparator.sortSermanticObjects(ObjectBehavior.swbxf_ObjectBehavior.listGenericInstances(true));
    //Iterator<ObjectBehavior> obit=SWBComparator.sortSermanticObjects(new GenericIterator(ObjectBehavior.swbxf_ObjectBehavior, obj.getSemanticObject().getModel().listInstancesOfClass(ObjectBehavior.swbxf_ObjectBehavior)));
    while(obit.hasNext())
    {
        ObjectBehavior ob=obit.next();
        if(!ob.isVisible())continue;

        String title=ob.getDisplayName(lang);
        //DisplayObject dpobj=ob.getDisplayObject();
        SemanticObject interf=ob.getInterface();
        boolean refresh=ob.isRefreshOnShow();
        //String url=ob.getParsedURL();
        String url=ob.getUrl();
        //System.out.println("ob:"+ob.getTitle(lang)+" "+ob.getDisplayObject()+" "+ob.getInterface()+" "+ob.getURL());

        String params="suri="+URLEncoder.encode(obj.getURI());

        String bp=ob.getBehaviorParams();
        if(bp!=null)
        {
            params+="&"+SWBUtils.TEXT.replaceAll(bp, "swb:", URLEncoder.encode(SemanticVocabulary.URI));
        }
//        Iterator<ResourceParameter> prmit=ob.listParams();
//        while(prmit.hasNext())
//        {
//            ResourceParameter rp=prmit.next();
//            params+="&"+rp.getName()+"="+rp.getValue().getEncodedURI();
//        }
        //System.out.println("params:"+params);
        //Genericos
        boolean addDiv=false;
        //if(dpobj==null)
        {
            if(interf==null)
            {
                addDiv=true;
            }else
            {
                SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(interf.getURI());
                if(scls!=null)
                {
                    if(scls.getObjectClass().isInstance(obj))
                    {
                        addDiv=true;
                    }
                }
            }
        }
        if(addDiv)
        {
            //out.println("<div dojoType=\"dojox.layout.ContentPane\" title=\""+title+"\" _style=\"display:true;padding:10px;\" refreshOnShow=\""+refresh+"\" href=\""+url+"?"+params+"\" executeScripts=\"true\">");
            out.println("<div dojoType=\"dijit.layout.ContentPane\" title=\""+title+"\" refreshOnShow=\""+refresh+"\" href=\""+url+"?"+params+"\" _onLoad=\"runScript(this);\">");
            //request.getRequestDispatcher((url+"?"+params).substring(4)).include(request, response);
            out.println("</div>");
        }
    }

    out.println("</div><!-- end Bottom TabContainer -->");
%>