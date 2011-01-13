<%@page contentType="text/html"%>
<%@page import="org.semanticwb.opensocial.model.*,org.semanticwb.opensocial.resources.*,java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.text.SimpleDateFormat, org.semanticwb.portal.api.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>

<%
    SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
%>
<%


        WebSite site=paramRequest.getWebPage().getWebSite();
        User user=paramRequest.getUser();

        SWBResourceURL add=paramRequest.getRenderUrl();
	add.setCallMethod(SWBResourceURL.Call_DIRECT);
        add.setMode(SocialContainer.Mode_LISTGADGETS);


        SWBResourceURL script=paramRequest.getRenderUrl();
        script.setCallMethod(SWBResourceURL.Call_DIRECT);
        script.setMode(SocialContainer.Mode_JAVASCRIPT);
        script.setParameter("script", "core_rpc_pubsub_container.js");
        String id=paramRequest.getUser().getId();
        
        
        SWBResourceURL metadata=paramRequest.getRenderUrl();
	metadata.setCallMethod(SWBResourceURL.Call_DIRECT);
        metadata.setMode(SocialContainer.Mode_METADATA);

        String defaultview="home";
        if(paramRequest.getWindowState().equals(paramRequest.WinState_MAXIMIZED))
        {
            defaultview="canvas";
        }
        String swbadmin=SWBPortal.getDistributorPath();
%>
<html>
<head>
<script type="text/javascript" src="<%=swbadmin%>/js/dojo/dojo/dojo.js" ></script>

<link rel="stylesheet" href="<%=swbadmin%>/jsp/opensocial/gadgets.css">
<script type="text/javascript" >
        var djConfig = {
            parseOnLoad: true,
            isDebug: false,
            locale: 'en-us',
            extraLocale: ['ja-jp']
        };
    </script>

<script type="text/javascript" >

dojo.require("dojo.parser");
dojo.addOnLoad(function(){
      dojo.parser.parse(); // or set djConfig.parseOnLoad = true
});
</script>
<script type="text/javascript" src="<%=script%>"></script>
<script type="text/javascript"> 


var parentUrl = document.location.href;
var viewMatches = /[?&]view=((?:[^#&]+|&amp;)+)/.exec(parentUrl);
  var current_view = (viewMatches)
      ? viewMatches[1]
      : "default";
      
      
var viewerId = '<%=id%>';
var ownerId = '<%=id%>';

<%
    StringBuilder _gadgets=new StringBuilder("[");
    SocialUser socialUser=SocialContainer.getSocialUser(user, session);
    
    //socialUser.clearUserPrefs(site);
    if(paramRequest.getWindowState().equals(paramRequest.WinState_NORMAL))
    {
        for(UserPrefs pref : socialUser.getUserPrefs(site))
        {
            Gadget g=pref.getGadget();
            if(g!=null)
            {
                g.reload();
                _gadgets.append("{url:\"");
                _gadgets.append(g.getUrl());
                _gadgets.append("\",moduleId:\"");
                _gadgets.append(pref.getModuleId());
                _gadgets.append("\"},");
            }
        }
        if(_gadgets.charAt(_gadgets.length()-1)==',')
        {
            _gadgets.deleteCharAt(_gadgets.length()-1);
        }
    }
    else if(paramRequest.getWindowState().equals(paramRequest.WinState_MAXIMIZED) && request.getParameter("moduleid")!=null)
    {
        String moduleid=request.getParameter("moduleid");
        for(UserPrefs pref : socialUser.getUserPrefs(site))
        {
            Gadget g=pref.getGadget();
            if(g!=null && moduleid.equalsIgnoreCase(pref.getModuleId()))
            {
                g.reload();
                _gadgets.append("{url:\"");
                _gadgets.append(g.getUrl());
                _gadgets.append("\",moduleId:\"");
                _gadgets.append(pref.getModuleId());
                _gadgets.append("\"},");
            }
        }
        if(_gadgets.charAt(_gadgets.length()-1)==',')
        {
            _gadgets.deleteCharAt(_gadgets.length()-1);
        }

    }
    _gadgets.append("]");
%>
var _gadgets = <%=_gadgets%>;

function generateSecureToken(gadgetUrl,appId) {
    
    var fields = [ownerId, viewerId, appId, "shindig", gadgetUrl, "0", "default"];
    for (var i = 0; i < fields.length; i++) {
      // escape each field individually, for metachars in URL
      fields[i] = escape(fields[i]);
    }
    return fields.join(":");
  };


function sendRequestToServer(url, method, opt_postParams, opt_callback, opt_excludeSecurityToken) {
    // TODO: Should re-use the jsoncontainer code somehow
    opt_postParams = opt_postParams || {};
    
    var makeRequestParams = {
      "CONTENT_TYPE" : "JSON",
      "METHOD" : method,
      "POST_DATA" : opt_postParams};

    if (!opt_excludeSecurityToken) {
      url = socialDataPath + url + "?st=" + gadget.secureToken;
    }
    
    gadgets.io.makeNonProxiedRequest(url,
      function(data) {
          
        data = data.data;
        if (opt_callback) {
            opt_callback(data);
        }
      },
      makeRequestParams,
      "application/javascript"
    );
  };

function requestGadgetMetaData(opt_callback) {

    
    var request = {
      context: {
        country: "default",
        language: "default",
        view: current_view,
        container: "default"
      },
      gadgets: _gadgets
    };    
    sendRequestToServer("<%=metadata%>", "POST",gadgets.json.stringify(request), opt_callback, true);
  };



function init() {
  shindig.container.layoutManager.setGadgetChromeIds(['gadget-chrome']);
  shindig.container.layoutManager =new shindig.FloatLeftLayoutManager('layout-root');
  shindig.container.setView('<%=defaultview%>');
  requestGadgetMetaData(generateGadgets);
  
};
function generateGadgets(metadata) 
{        
    for (var i = 0; i < metadata.gadgets.length; i++)
    {

            var url=metadata.gadgets[i].url;
            var title=metadata.gadgets[i].title;            
            var moduleId=metadata.gadgets[i].moduleId;
            var secureToken0=generateSecureToken(url);
            var gadget=shindig.container.createGadget({'title':title,'moduleId':moduleId,'secureToken':secureToken0,'specUrl': url,'userPrefs': metadata.gadgets[i].userPrefs});
            shindig.container.addGadget(gadget);
    }
    renderGadgets();
};
function renderGadgets() {  
  shindig.container.renderGadgets();
};

</script>
<link rel="stylesheet" type="text/css" media="all" href="/swb/swbadmin/js/dojo/dijit/themes/soria/soria.css" >
</head>

<script type="text/javascript">
  dojo.require("dijit.Dialog");
  formDlg = dijit.byId("formDialog");  
  function showDialogEmail()
  {
     var dlg=dijit.byId('formDialog');
     if(dlg)
     {
         dlg.show();
     }

  }
</script>

<body class="soria" onLoad="init();renderGadgets();">

    <div dojoType="dijit.Dialog" id="formDialog" title="Agregar un gadget" style="width: 600px;height: 500px">
    <script type="text/javascript">
        <!--
        document.write('<iframe src="<%=add%>" frameborder="0" width="580" height="500"></iframe>');
        -->
    </script>

</div>
        <p><a href="#" onclick="showDialogEmail();">add</a></p>
<div id="layout-root" class="gadgets-layout-root"></div>



</body>
</html>
