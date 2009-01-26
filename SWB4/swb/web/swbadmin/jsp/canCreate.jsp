<%@page contentType="text/html"%><%@page pageEncoding="ISO-8859-1"%><%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%><%!
    boolean isValidId(String id)
    {
        boolean ret=true;
        if(id!=null)
        {
            for(int x=0;x<id.length();x++)
            {
                char ch=id.charAt(x);
                if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_'))
                {
                    ret=false;
                    break;
                }
            }
        }else
        {
            ret=false;
        }
        return ret;
    }

%><%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
    String id=request.getParameter("id");
    String clsid=request.getParameter("clsid");
    String model=request.getParameter("model");
    //System.out.println("id:"+id+" clsid:"+clsid+" model:"+model);
    if(id==null || id.length()==0 || id.indexOf(' ')>-1 || clsid==null || model==null)
    {
        out.println("false");
        //System.out.println("false");
    }else
    {
        if(isValidId(id))
        {
            SemanticModel m=SWBPlatform.getSemanticMgr().getModel(model);
            SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
            String uri=m.getObjectUri(id, scls);
            //out.println(uri);
            SemanticObject obj=SemanticObject.createSemanticObject(uri);
            if(obj!=null)
            {
                out.println("false");
                //System.out.println("false");
            }else
            {
                out.println("true");
                //System.out.println("true");
            }
        }else
        {
            out.println("false");
        }
    }
%>