<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
    <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="text/html; charset=UTF-8" >
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" >
    <head>
        <title>Semantic WebBuilder 4</title>

        <style type="text/css">
            @import "/swbadmin/js/dojo/dojo/resources/dojo.css";
            /*@import "/swbadmin/js/dojo/dojo/resources/dnd.css";*/
            @import "/swbadmin/js/dojo/dijit/themes/soria/soria.css";
            @import "/swbadmin/css/swb.css";
        </style>

        <script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
        <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js" ></script>
        <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js" ></script>
        <script type="text/javascript" charset="utf-8" src="/swbadmin/js/schedule.js" ></script>
    </head>
    <body class="soria" >

    <!-- data for tree and combobox -->
    <div dojoType="dojo.data.ItemFileWriteStore" jsId="mtreeStore" url="/swbadmin/jsp/AdminFilterJSON.jsp?id=mtree"></div>
    <div dojoType="dijit.tree.ForestStoreModel" jsId="mtreeModel" store="mtreeStore" rootId="root"  childrenAttrs="children"></div>
    <!-- tree widget -->
    <div dojoType="dijit.Tree" id="mtree" model="mtreeModel" betweenThreshold_="8" persist="false" showRoot="false">
        <script type="dojo/connect">
            mtreeStore.controllerURL="/swbadmin/jsp/AdminFilterJSON.jsp";
        </script>
        <script type="dojo/method" event="onClick" args="item, node">
            if(item)
            {
                act_treeNode=node;
                executeTreeNodeEvent(mtreeStore,item,"onClick");
                item.selected=true;
                node._updateItemClasses(item);
                reloadTreeNode(mtreeStore,item);
            }
        </script>
        <script type="dojo/method" event="onOpen" args="item, node">
            if(item)
            {
                act_treeNode=node;
                executeTreeNodeEvent(mtreeStore,item,"onOpen");
            }
        </script>
        <script type="dojo/method" event="onDblClick" args="item, node">
            if(item)
            {
                act_treeNode=node;
                executeTreeNodeEvent(mtreeStore,item,"onDblClick");
                //printObjProp(mtree,false);
            }
        </script>
        <script type="dojo/method" event="getIconClass" args="item, opened">
            if(item)
            {
                try
                {
                    return mtreeStore.getValue(item, "icon");
                }catch(err)
                {
                    return "swbIconTemplateGroup";
                }
            }
        </script>
        <script type="dojo/method" event="getLabelStyle" args="item, opened">
            if(item)
            {
                if(item.selected==true)
                {
                    printObjProp(item,true);
                    return {color: "blue", background: "#e0e0e0"};
                }
            }
        </script>
    </div>

    </body>
</html>