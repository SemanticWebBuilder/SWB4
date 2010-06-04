<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/mapa">
<style type="text/css">
    <xsl:text disable-output-escaping="yes">
    &lt;!--
    .wsm_box {
            width: 95%; 
            background: #FFFFFF; 
            color : #000000; 
            font-family : Verdana; 
            font-size: 12px; 
            padding : 5px; 
            margin: 10px; 
            border-color : #cccccc; 
            border-style : solid; 
            border-width : 1px; 
    }

    .wsm_title { 
            color: #428AD4; 
            text-decoration: none; 
            font-family: Verdana; 
            font-size: 18px; 
            font-weight: bold; 
    }

    .wsm_description { 
            color: #428AD4; 
            text-decoration: none; 
            font-family: Verdana;
            font-size: 14px; 
            font-weight: bold; 
    }

    .wsm_link { 
            color: #0000FF; 
            text-decoration: none; 
            font-family: Verdana; 
            font-size: 12px; 
    }    
    --&gt;
</xsl:text>
</style>
<script type="text/javascript">

	function showSiteMap(xmlUrl) {
		var sitemap = document.getElementById("wbsitemap");    
                sitemap.innerHTML  = getResponseXml(xmlUrl);
        }

	function getResponseXml(xmlUrl)
	{
		var xmlHttp = ajaxFunction();
		xmlHttp.open("GET", xmlUrl, false);
		xmlHttp.send(null);
		if ( xmlHttp.readyState  == 4) {
			return xmlHttp.responseText;
		}
	}

	function ajaxFunction()
	{
		var xmlHttp;
		try
		{    // Firefox, Opera 8.0+, Safari
		xmlHttp=new XMLHttpRequest();
		}
		catch (e)
		{
			// Internet Explorer
			try
			{
				xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e)
			{
				try
				{
					xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e)
				{
					alert("Your browser does not support AJAX!");
					return false;
				}
			}
		}
		return xmlHttp;
	}
</script>
<!-- NO CAMBIAR, ni quitar el div id="wbsitemap" -->
<div id="wbsitemap"> 
<div class="wsm_box">
<table border="0" cellpadding="0" cellspacing="0" width="100%" >
	<tr><td colspan="2" align="center" class="wsm_title">
		<xsl:value-of select="@title" />
	</td></tr>
      	<xsl:for-each select="padre">
		<tr><td>
			<xsl:if test="@mas ='2'">		
                                <a href="#" onclick="javascript:showSiteMap('{@urlajax}');return false;">
					<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/ico_parent_off.gif" align="middle" ></img>
				</a>
			</xsl:if>
			<xsl:if test="@mas ='1'">
                                <a href="#" onclick="javascript:showSiteMap('{@urlajax}');return false;">
					<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/ico_parent_on.gif" align="middle" ></img>
				</a>
			</xsl:if>			
			<xsl:if test="@mas ='0'">		
				<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/ico_child.gif" align="middle" ></img>
			</xsl:if>				
			<a href="{@url}" class="wsm_link">
				<xsl:value-of select="@nombre" />
			</a>
		</td></tr>
		<xsl:for-each select="hijo">
			<tr><td>
				<table border="0" cellpadding="0" cellspacing="0" width="100%" >
				<tr>
					<xsl:for-each select="hijospc">
						<td width="{@width}">
							<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/line_parent.gif" align="middle" ></img>
							<img height="5" alt="" width="{@width}" border="0" src="{/mapa/@path}images/pixel.gif" ></img>
						</td>
					</xsl:for-each>
					<td width="19" valign="top">
						<xsl:if test="@mas ='2'">		
							<a href="#" onclick="javascript:showSiteMap('{@urlajax}');return false;">
								<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/ico_parent_off.gif" align="middle" ></img>
							</a>
						</xsl:if>
						<xsl:if test="@mas ='1'">		
							<a href="#" onclick="javascript:showSiteMap('{@urlajax}');return false;">
								<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/ico_parent_on.gif" align="middle" ></img>
							</a>
						</xsl:if>						
						<xsl:if test="@mas ='0'">		
							<img border="0" alt="" height="15" width="19" src="{/mapa/@path}images/ico_child.gif" align="middle" ></img>
						</xsl:if>
					</td>
					<td valign="top">
						<a href="{@url}" class="wsm_link">
							<xsl:value-of select="@nombre" />
						</a>
					</td>
				</tr>
				</table>
			</td></tr>
		</xsl:for-each>	
	</xsl:for-each>
</table>
</div>
</div>
</xsl:template>
</xsl:stylesheet>