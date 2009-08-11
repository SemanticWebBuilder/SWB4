<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/resource">
	<!-- <xsl:value-of select="@currentlang" /> -->
<LINK href="{@path}images/Language.css" rel="stylesheet" type="text/css" ></LINK>	
	<TABLE width="100%" border="0" align="center" cellSpacing="3" cellPadding="3" > 
	<TR><TD>	
		<xsl:for-each select="language">
			&#160;
				<a href="{@ref}" class="lng_link"><xsl:value-of select="@title"/>
					<xsl:if test="@lang = /resource/@currentlang">*</xsl:if>
				</a>
			&#160;
		</xsl:for-each>
	</TD></TR>
	</TABLE>
</xsl:template>
</xsl:stylesheet>