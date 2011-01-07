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
%>
<html>
<head>
<link rel="stylesheet" href="/swb/swbadmin/jsp/opensocial/gadgets.css">
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
    System.out.println("socialUser.getUserPrefs(site): "+socialUser.getUserPrefs(site).length);
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
    _gadgets.append("]");
%>
var _gadgets = <%=_gadgets%>;//[{url:"http://www.google.com/ig/modules/horoscope/horoscope.xml",moduleId:"1"},{url:"http://www.google.com/ig/modules/test_setprefs_multiple_ifpc.xml",moduleId:"2"}];
//'http://www.google.com/ig/modules/horoscope/horoscope.xml';

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
  requestGadgetMetaData(generateGadgets);
  
};
function generateGadgets(metadata) 
{
	for (var i = 0; i < metadata.gadgets.length; i++) 
	{
		var url=metadata.gadgets[i].url;		
		var secureToken0=generateSecureToken(url);
                var pref=gadgets.json.stringify(metadata.gadgets[i].userPrefs);
                //alert('pref: '+pref+' url: '+url);
		//var gadget=shindig.container.createGadget({'secureToken':secureToken0,'specUrl': url,'userPrefs': metadata.gadgets[i].userPrefs});
                var gadget=shindig.container.createGadget({'secureToken':secureToken0,'specUrl': url});
		shindig.container.addGadget(gadget);
	}
	renderGadgets();
};
function renderGadgets() {  
  shindig.container.renderGadgets();
};

</script>
</head>

<body onLoad="init();renderGadgets();">
    <p><a href="<%=add%>" target="_blank">add</a></p>
<div id="layout-root" class="gadgets-layout-root"></div>






<%-- <link rel="stylesheet" href="/swb/swbadmin/jsp/opensocial/gadgets.css">
<style type="text/css">
  body {
    font-family: arial, sans-serif;
  }

  #headerDiv {
    padding: 10px;
    margin-bottom: 20px;
    background-color: #e5ecf9;
    color: #3366cc;
    font-size: larger;
    font-weight: bold;
  }

  .subTitle {
    font-size: smaller;
    float: right;
  }

  .gadgets-gadget-chrome {
    width: 60%;
    float: none;
    margin: auto;
  }

  .gadgets-gadget {
    width: 100%;
  }

</style>

<script type="text/javascript" src="<%=script%>"></script>
<%
        script=paramRequest.getRenderUrl();
        script.setCallMethod(SWBResourceURL.Call_DIRECT);
        script.setMode(SocialContainer.Mode_JAVASCRIPT);
        script.setParameter("script", "samplecontainer.js");
%>

<script type="text/javascript" src="<%=script%>"></script>

</head>
<body onLoad="shindig.samplecontainer.initSampleContainer();
    shindig.samplecontainer.unpackFormState(); shindig.samplecontainer.initGadget();">
  <div id="headerDiv">
    <div style="float:left">Gadget testing container</div>
    <div class="subTitle">
      Displaying gadget: <input type="text" size="75" id="gadgetUrl"/>
      <input type="checkbox" id="useCacheCheckbox" checked="true"
       /><label for="useCacheCheckbox">use cache</label>
      <input type="checkbox" id="useCajaCheckbox"
       /><label for="useCajaCheckbox">use caja</label>
      <input type="checkbox" id="useDebugCheckbox"
       /><label for="useDebugCheckbox">use debug</label>

      <br/>

      Using state: <input type="text" size="75" id="stateFileUrl"/>
      <input type="checkbox" id="doEvilCheckbox"
       /><label for="doEvilCheckbox">do evil</label>

      <br/>
      <br/>
      Viewer id: <input type="text" size="20" id="viewerId"/>
      Owner id: <input type="text" size="20" id="ownerId"/>

      <br/>

      <input type="button" value="reset all" onclick="shindig.samplecontainer.changeGadgetUrl();"/>
      <input type="button" value="dump state" onclick="shindig.samplecontainer.dumpStateFile();"/>
      <input type="button" value="Send Hello" onclick="shindig.samplecontainer.sendHello();"/>
    </div>
    <div style="clear:both; height: 1px;">&nbsp;</div>
  </div>

  <div id="gadgetState" style="font-size:smaller"></div>
  <div id="gadget-chrome" class="gadgets-gadget-chrome"></div>
   --%>
</body>
</html>
