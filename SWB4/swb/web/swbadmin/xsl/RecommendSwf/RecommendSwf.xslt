<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}images/RecommendSwf.css" rel="stylesheet" type="text/css"></LINK>
	<xsl:choose>
		<xsl:when test="@email ='1'">
        <xsl:value-of select="styleClass"/>
    	<DIV class="swb-recomendar">
			<TR><TD class="rec_data">
				<BR/><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
				----------------------------------------------------------------------<BR/>
				Recomendación especial<BR/>
				----------------------------------------------------------------------<BR/>
				<xsl:if test="count(headermsg) &gt; 0">
					<BR/><xsl:value-of select="headermsg" disable-output-escaping="yes" /><BR/><BR/>
				</xsl:if><BR/>
				Hola <I><xsl:value-of select="toname"/></I>, <BR/><BR/>
				Tu amigo(a) <I><xsl:value-of select="fromname"/></I>
				te recomienda que visites la siguiente dirección:<BR/>
				<xsl:text disable-output-escaping="yes">&lt;A href=&quot;</xsl:text>
				<xsl:value-of select="topicurl"/>
				<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="topic"/>
				<xsl:text disable-output-escaping="yes">&lt;/A&gt;</xsl:text>
				<xsl:if test="count(message) &gt; 0">
					<BR /><BR /><xsl:value-of select="message" />
				</xsl:if>
				<xsl:if test="count(footermsg) &gt; 0">
					<BR/><BR/><BR/><xsl:value-of select="footermsg" disable-output-escaping="yes" />
				</xsl:if>
				<BR/><BR/>----------------------------------------------------------------------<BR />
				Te invitamos a que visites nuestro portal en la siguiente dirección:<BR/>
				<xsl:text disable-output-escaping="yes">&lt;A href=&quot;</xsl:text>
				<xsl:value-of select="siteurl"/>
				<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="site"/>
				<xsl:text disable-output-escaping="yes">&lt;/A&gt;</xsl:text>
				</FONT><BR/><BR/>
			</TD></TR>
    	</DIV>
    	<xsl:value-of select="styleClassClose"/>
		</xsl:when>
		<xsl:otherwise>
			<BODY leftmargin="0" topmargin="0">
				<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="580" height="380">
					<param name="movie" value="{@swf}" ></param>
					<param name="quality" value="high" ></param>
					<param name="SCALE" value="NOBORDER" ></param>
					<param name="FlashVars" value="{@swfvars}" ></param>
					<embed id="{@swf}" name="{@swf}" src="{@swf}" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="580" height="380" scale="NOBORDER" >
					</embed>
				</OBJECT>
			</BODY>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>