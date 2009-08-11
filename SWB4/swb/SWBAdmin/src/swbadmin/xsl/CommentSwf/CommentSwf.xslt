<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}swb-estilo.css" rel="stylesheet" type="text/css"></LINK>
	<xsl:choose>
		<xsl:when test="@email ='1'">
            <xsl:value-of select="styleClass" />
			<DIV class="swb-comentar">
			<TABLE align="center"> 
			<TR><TD>
				<label><BR />
				----------------------------------------------------<BR />
				Sitio o portal: <B><xsl:value-of select="site" />.<xsl:value-of select="topic" /></B><BR />
				Solicitud: <B><xsl:value-of select="commenttype" /></B><BR />
				----------------------------------------------------<BR />
				<xsl:if test="count(headermsg) &gt; 0">
					<BR /><xsl:value-of select="headermsg" disable-output-escaping="yes" /><BR/><BR/>
				</xsl:if><BR/>
                                </label>
				<TABLE>
				<TR>
					<TD colspan="2" ><label>
						<xsl:value-of select="responsable" /><BR /> 
						<xsl:value-of select="area" /><BR /><BR />
					</label></TD>
				</TR>
				<TR>
					<TD><label><B>Nombre:</B></label></TD>
					<TD><label><xsl:value-of select="fromname" /></label></TD>
				</TR>
				<TR>
					<TD><label><B>Correo electrónico:</B></label></TD>
					<TD><label><xsl:value-of select="fromemail" /></label></TD>
				</TR>
				<xsl:if test="count(phone) &gt; 0">
					<TR>
						<TD><label><B>Teléfono(s):</B></label></TD>
						<TD><label><xsl:value-of select="phone" /></label></TD>
					</TR>
				</xsl:if>
				<xsl:if test="count(fax) &gt; 0">
					<TR>
						<TD><label><B>Fax:</B></label></TD>
						<TD><label><xsl:value-of select="fax" /></label></TD>
					</TR>
				</xsl:if>
				<TR>
					<TD colspan="2"><label><BR /><BR />
						Y escribe:<BR /><BR />
						<xsl:value-of select="message" />
					</label></TD>
				</TR>
				</TABLE>
                                <label>
				<xsl:if test="count(footermsg) &gt; 0">
					<BR /><BR /><BR />
					<xsl:value-of select="footermsg" disable-output-escaping="yes" />
				</xsl:if>
				<BR /><BR /><BR /></label>
			</TD> </TR>
			</TABLE>
			</DIV>
            <xsl:value-of select="styleClassClose" />
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