<%-- 
    Document   : treeWidget
    Created on : 30/12/2008, 05:09:54 PM
    Author     : Jei
--%>
<%
    String id=request.getParameter("id");
    //System.out.println("id:"+id);
    if(id==null)id="tree";
    String store=id+"Store";
    String menu=id+"Menu";
%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!-- menu -->
<ul dojoType="dijit.Menu" id="<%=menu%>" style="display: none;"></ul>
<!-- data for tree and combobox -->
<div dojoType="dojo.data.ItemFileWriteStore" jsId="<%=store%>" url="/swb/swbadmin/jsp/Tree.jsp?id=<%=id%>"></div>
<!-- tree widget -->
<div id="<%=id%>" dojoType="dijit.Tree" refreshOnExpand_="true" model_="tmodel" store="<%=store%>" persist="false" query_="{type:'WebSite'}" showRoot="false" label_="Sitios">
<!--
    <script type="dojo/method" event="onClick" args="item">
        if(item){
            //var newTab = dijit.byId("vprop");
            var id=this.store.getValue(item, "id");
            //newTab.href='/swb/swbadmin/jsp/viewProps.jsp';
            getHtml('/swb/swbadmin/jsp/viewProps.jsp?id='+encodeURIComponent(id), "vprop");
            //alert("onClick:"+id);
        }
    </script>
-->
    <script type="dojo/method" event="onOpen" args="item">
        if(item)
        {
            //alert("onOpen");
            executeTreeNodeEvent(<%=store%>,item,"onOpen");
        }
    </script>
    <script type="dojo/method" event="onDblClick" args="event">
        //alert("onDblClick:"+event);
        var domElement = event.target;
        var nodeWidget = dijit.getEnclosingWidget(domElement);
        if(nodeWidget && nodeWidget.isTreeNode){
            executeTreeNodeEvent(<%=store%>,nodeWidget.item,"onDblClick");
        }
    </script>
    <script type="dojo/method" event="onEnterKey" args="event">
        alert("onEnterKey"+event);
        var domElement = event.target;
        var nodeWidget = dijit.getEnclosingWidget(domElement);
        if(nodeWidget && nodeWidget.isTreeNode){
            executeTreeNodeEvent(<%=store%>,nodeWidget.item,"onDblClick");
        }
    </script>
    <script type="dojo/method" event="getIconClass" args="item, opened">
        if(item)
        {
            return <%=store%>.getValue(item, "icon");
        }
    </script>
    <script type="dojo/connect">
        var menuEmpty = dijit.byId("<%=menu%>");

        menuEmpty.bindDomNode(this.domNode);

        registerStore(<%=store%>);

        dojo.connect(menuEmpty, "_openMyself", this, function(e)
        {
            var treeNode = dijit.getEnclosingWidget(e.target);

            var ch = menuEmpty.getChildren();
            //console.log("menu children is "+ch);
            if (ch && ch != "undefined")
            {
                dojo.forEach(ch, function(child)
                {
                    //console.log("Remving old child "+child);
                    menuEmpty.removeChild(child);
                });
            }

            if(treeNode.item.menus)
            {
                //console.log("Adding new submenus");
                for (var m in treeNode.item.menus)
                {
                    var menu = treeNode.item.menus[m];
                    //console.log("Adding submenu " + mstr);
                    var mi = document.createElement('div')
                    var sm;
                    if(menu.title=="_")
                    {
                        sm = new dijit.MenuSeparator();
                    }else
                    {
                        sm = new dijit.MenuItem(
                        {
                            label: menu.title,
                            iconClass:menu.icon,
                            action: menu.action
                        }, mi);
                        sm.onClick = function(ele)
                        {
                            var m=dijit.getEnclosingWidget(ele.target);
                            //console.log("e.target:"+e.target);
                            //console.log("this"+this);
                            //console.log("this.store:"+this.store);
                            executeAction(<%=store%>, treeNode.item, m.action)
                        };
                    }
                    menuEmpty.addChild(sm);
                }
            }
        });
    </script>
</div>
