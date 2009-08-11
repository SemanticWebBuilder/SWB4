<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>
<xsl:template match="/menu">
<LINK href="{@path}images/WBMenu.css" rel="stylesheet" type="text/css"></LINK>
<DIV class="wmn_box">
<TABLE border="0" cellpadding="0" cellspacing="2" width="100%" >
	<xsl:for-each select="parent">
		<TR><TD>
			<A href="{@path}" class="wmn_title" ><xsl:value-of select="@name"/></A>
		</TD></TR>
	</xsl:for-each>
	<xsl:for-each select="brothers">
		<xsl:for-each select="brother">
			<TR><TD>
				<A href="{@path}" class="wmn_link"><xsl:value-of select="@name"/></A>
			</TD></TR>
			<xsl:if test="@current ='1'">
				<xsl:for-each select="child">
					<TR><TD class="wmn_data">::
						<A href="{@path}" class="wmn_link"><xsl:value-of select="@name"/></A>
					</TD></TR>
				</xsl:for-each>
			</xsl:if>
		</xsl:for-each>
	</xsl:for-each>
</TABLE>
</DIV>
</xsl:template>
</xsl:stylesheet>