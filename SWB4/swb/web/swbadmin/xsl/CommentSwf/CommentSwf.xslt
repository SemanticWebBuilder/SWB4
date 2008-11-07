<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/form">
	<LINK href="{@path}images/CommentSwf.css" rel="stylesheet" type="text/css"></LINK>
	<xsl:choose>
		<xsl:when test="@email ='1'">
			<DIV class="com_box">
			<TABLE width="100%" border="0" align="center" cellSpacing="3" cellPadding="3"> 
			<TR><TD class="com_data">
				<FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><BR />
				----------------------------------------------------<BR />
				Sitio o portal: <B><xsl:value-of select="site" />.<xsl:value-of select="topic" /></B><BR />
				Solicitud: <B><xsl:value-of select="commenttype" /></B><BR />
				----------------------------------------------------<BR />
				<xsl:if test="count(headermsg) &gt; 0">
					<BR /><xsl:value-of select="headermsg" disable-output-escaping="yes" /><BR/><BR/>
				</xsl:if><BR/>
				<TABLE border="0" class="com_data">
				<TR>
					<TD colspan="2" ><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
						<xsl:value-of select="responsable" /><BR /> 
						<xsl:value-of select="area" /><BR /><BR />
					</FONT></TD>
				</TR>
				<TR>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
						Nombre:</B>
					</FONT></TD>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
						<xsl:value-of select="fromname" />
					</FONT></TD>
				</TR>
				<TR>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
						Correo electrónico:</B>
					</FONT></TD>
					<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
						<xsl:value-of select="fromemail" />
					</FONT></TD>
				</TR>
				<xsl:if test="count(phone) &gt; 0">
					<TR>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
							Teléfono(s):</B>
						</FONT></TD>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
							<xsl:value-of select="phone" />
						</FONT></TD>
					</TR>
				</xsl:if>
				<xsl:if test="count(fax) &gt; 0">
					<TR>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><B>
							Fax:</B>
						</FONT></TD>
						<TD><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2">
							<xsl:value-of select="fax" />
						</FONT></TD>
					</TR>
				</xsl:if>
				<TR>
					<TD colspan="2"><FONT face="Verdana, Arial, Helvetica, sans-serif" size="2"><BR /><BR />
						Y escribe:<BR /><BR />
						<xsl:value-of select="message" />
					</FONT></TD>
				</TR>
				</TABLE>
				<xsl:if test="count(footermsg) &gt; 0">
					<BR /><BR /><BR />
					<xsl:value-of select="footermsg" disable-output-escaping="yes" />
				</xsl:if>
				<BR /><BR /><BR /></FONT>
			</TD> </TR>		
			</TABLE>
			</DIV>
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