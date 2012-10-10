<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.zkoss.org/jsp/zul" prefix="z" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <z:zkhead />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>  

        <z:page>
            
            <z:zscript>
                import org.semanticwb.model.*;
                import org.semanticwb.*;

                System.out.println("fire when load page..." );
                Date current_date=new Date();
                String initKey="Hola2";

                List webpages=SWBUtils.Collections.copyIterator(WebPage.getWebPageClassMgr().listWebPages());
                WebPage selected = (WebPage)webpages.get(0);                
            </z:zscript> 
                
            <z:window id="mainPanel" width="600px"  title="RSS Reader with Aggregate Feed"  border="normal" >

                <z:caption id="mainCap" label="Add New Feed">
                    <z:button id="cancelBtn" label="test">
                        <z:attribute name="onClick">
                            System.out.println("zscript.pageVariable()= "+current_date +" "+initKey);
                        </z:attribute>
                    </z:button>
                </z:caption>

                test initiator variable: <z:label style="color:red;font-weight:bold" value="@{initKey}"/>

                <z:listbox model="@{webpages}" mold="paging" selectedItem="@{selected}" rows="10">
                    <z:listhead >
                        <z:listheader label="Title"/>	
                        <z:listheader label="Description"/>	
                    </z:listhead>
                    <z:listitem self="@{each=webpage}">
                        <z:listcell label="@{webpage.title}" />
                        <z:listcell label="@{webpage.description}" />
                    </z:listitem>
                </z:listbox>        
            </z:window>

        </z:page>

    </body>
</html>
