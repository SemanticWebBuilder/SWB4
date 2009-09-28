<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/reltopics">
<LINK href="{@path}images/RelatedTopics.css" rel="stylesheet" type="text/css"></LINK>
<DIV class="rtp_box">
<TABLE border="0" cellpadding="0" cellspacing="2" width="100%" >
<TR>
<xsl:for-each select="padre">
	<xsl:if test="count(hijo) &gt; 0">
	<TD valign="top">
		<xsl:if test="count(@icon) &gt; 0">
			<!-- Cuando viene el atributo de ICON en el XML en el PADRE -->	
			<IMG border="0" src="{@icon}" ></IMG>
		</xsl:if>
		<P class="rtp_title"><xsl:value-of select="@nombre"/></P>
		<xsl:for-each select="hijo">		
			<xsl:choose>
				<xsl:when test="@target ='1'">
					<A href="{@url}" target="_newrtp" class="rtp_link">
					<xsl:choose>
						<xsl:when test="count(@icon) &gt; 0">
							<!-- Cuando viene el atributo de ICON en el XML en el HIJO -->	
							<IMG border="0" src="{@icon}"></IMG>
						</xsl:when>
						<xsl:otherwise>- </xsl:otherwise>
					</xsl:choose>
					<xsl:value-of select="@nombre" />
					</A><BR/>
				</xsl:when>
				<xsl:otherwise>
					<A href="{@url}" class="rtp_link">
					<xsl:choose>
						<xsl:when test="count(@icon) &gt; 0">
							<!-- Cuando viene el atributo de ICON en el XML en el HIJO -->	
							<IMG border="0" src="{@icon}"></IMG>
						</xsl:when>
						<xsl:otherwise>- </xsl:otherwise>
					</xsl:choose>
					<xsl:value-of select="@nombre" />
					</A><BR/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	 </TD>
	</xsl:if>
</xsl:for-each>		
</TR>
</TABLE>
</DIV>
</xsl:template>
</xsl:stylesheet>