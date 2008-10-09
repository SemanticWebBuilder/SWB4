<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.model.*,java.util.*,java.io.*"%>
<%!
   void addChild(WebPage page, JspWriter out) throws IOException
   {
        Iterator<WebPage> it=page.listVisibleChilds(null);
        while(it.hasNext())
        {
            WebPage child=it.next();
            if(child.listVisibleChilds(null).hasNext())
            {
                out.println("		<li dojoType=\"dijit.PopupMenuItem\" iconClass=\"swbIconWebPage\">");
                out.println("			<span>"+child.getTitle()+"</span>");
                out.println("		<ul dojoType=\"dijit.Menu\">");
                addChild(child,out);
                out.println("		</ul>");
                out.println("		</li>");
            }else
            {
                out.println("            <li dojoType=\"dijit.MenuItem\" iconClass=\"swbIconWebPage\" onclick=\"addNewTab('"+child.getURI()+"','"+child.getTitle()+"');\">"+child.getTitle()+"</li>");
            }
        }
   }
%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache"); 
%>

<div id="semwblogo" dojoType="dijit.form.Button" style="width: 250px;"><font size="4">Semantic WebBuilder 4</font></div>
    <span dojoType="dijit.Tooltip" connectId="semwblogo">Semantic WebBuilder</span>

<%
    WebPage wp=SWBContext.getAdminWebSite().getWebPage("WBAd_mnu_Main");
    Iterator<WebPage> it=wp.listVisibleChilds(null);
    while(it.hasNext())
    {
        WebPage child=it.next();
%>
    <div id="<%=child.getId()%>" dojoType="dijit.form.DropDownButton" iconClass="swbIconWebPage">
        <script type="dojo/method" event="onClick">
        </script>
        <span><%=child.getTitle()%></span>
        <ul dojoType="dijit.Menu">
<%      addChild(child,out);%>
        </ul>
    </div>
    <span dojoType="dijit.Tooltip" connectId="<%=child.getId()%>">Click to download new mail.</span>
<%
    }
    
%>
    
<!--    

    <div id="getMail" dojoType="dijit.form.ComboButton" optionsTitle="Mail Source Options">
        <script type="dojo/method" event="onClick">
        </script>
        <span>Get Mail</span>
        <ul dojoType="dijit.Menu">
            <li dojoType="dijit.MenuItem" >Yahoo</li>
            <li dojoType="dijit.MenuItem" >GMail</li>
        </ul>
    </div>
    <span dojoType="dijit.Tooltip" connectId="getMail">Click to download new mail.</span>

    <button
        id="newMsg" dojoType="dijit.form.Button"
        iconClass="mailIconNewMessage">
        New Message
        <script type="dojo/method" event="onClick">
            /* make a new tab for composing the message */
        </script>
    </button>
    <span dojoType="dijit.Tooltip" connectId="newMsg">Click to compose new message.</span>

    <button id="options" dojoType="dijit.form.Button" iconClass="swbIconWebPage">
        &nbsp;Options
        <script type="dojo/method" event="onClick">
            dijit.byId('optionsDialog').show();
        </script>
    </button>
    <div dojoType="dijit.Tooltip" connectId="options">Set various options</div>
-->    