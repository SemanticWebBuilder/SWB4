<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.*,org.semanticwb.model.*,java.util.*,java.io.*"%>
<%!
   void addChild(WebPage page, JspWriter out, String lang) throws IOException
   {
        Iterator<WebPage> it=page.listVisibleChilds(lang);
        while(it.hasNext())
        {
            WebPage child=it.next();
            if(child.listVisibleChilds(lang).hasNext())
            {
                out.println("		<div dojoType=\"dijit.PopupMenuItem\" iconClass_=\"swbIconWebPage\">");
                out.println("			<span>"+child.getDisplayName(lang)+"</span>");
                out.println("		<div dojoType=\"dijit.Menu\">");
                addChild(child,out,lang);
                out.println("		</div>");
                out.println("		</div>");
            }else
            {
                //System.out.println("mnu:"+child.getClass());
                if(child.getSemanticObject().instanceOf(MenuItem.sclass))
                {
                    String show=((MenuItem)child.getSemanticObject().createGenericInstance()).getShowAs();
                    if(show!=null && show.equals("DIALOG"))
                    {
                        out.println("            <div dojoType=\"dijit.MenuItem\" iconClass_=\"swbIconWebPage\" onclick=\"showDialog('"+child.getUrl()+"','"+child.getDisplayName(lang)+"');\">"+child.getDisplayName(lang)+"</div>");
                    }else
                    {
                        out.println("            <div dojoType=\"dijit.MenuItem\" iconClass_=\"swbIconWebPage\" onclick=\"addNewTab('"+child.getURI()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/menuTab.jsp"+"','"+child.getDisplayName(lang)+"');\">"+child.getDisplayName(lang)+"</div>");
                    }
                }else
                {
                    //out.println("            <div dojoType=\"dijit.MenuItem\" accelKey=\"Ctrl+S\" iconClass_=\"swbIconWebPage\" onclick=\"addNewTab('"+child.getURI()+"','"+child.getTitle()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/menuTab.jsp"+"');\">"+child.getTitle()+"</div>");
                    out.println("            <div dojoType=\"dijit.MenuItem\" iconClass_=\"swbIconWebPage\" onclick=\"addNewTab('"+child.getURI()+"','"+SWBPlatform.getContextPath()+"/swbadmin/jsp/menuTab.jsp"+"','"+child.getDisplayName(lang)+"');\">"+child.getDisplayName(lang)+"</div>");
                }
            }
        }
   }
%>
<%
    response.setHeader("Cache-Control", "no-cache"); 
    response.setHeader("Pragma", "no-cache");
    User user=SWBPortal.getSessionUser();
    String lang=user.getLanguage();
%>

<div id="semwblogo" class="dijitReset dijitInline swbLogo"></div>
<!--    <span dojoType="dijit.Tooltip" connectId="semwblogo">Semantic WebBuilder</span>-->

<%
    WebPage wp=SWBContext.getAdminWebSite().getWebPage("WBAd_mnu_Main");
    Iterator<WebPage> it=wp.listVisibleChilds(lang);
    while(it.hasNext())
    {
        WebPage child=it.next();
%>
    <div id="<%=child.getId()%>" dojoType="dijit.form.DropDownButton" iconClass_="swbIconWebPage">
        <script type="dojo/method" event="onClick">
        </script>
        <span><%=child.getDisplayName(lang)%></span>
        <div dojoType="dijit.Menu" onOpen="hideApplet(true);" onClose="hideApplet(false);">
<%      addChild(child,out,lang);%>
        </div>
    </div>
<%
        String desc=child.getDescription(lang);
        if(desc!=null)
        {
%>
    <span dojoType="dijit.Tooltip" connectId="<%=child.getId()%>"><%=desc%></span>
<%
        }

    }
%>
<span id="swblogout"><%=user.getFullName()%> | <a href="<%=SWBPlatform.getContextPath()%>/login?wb_logout=true">logout</a></span>
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