<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>

<xsl:template name="rest">
	<TR><TD class="wmn_data" >
		<img src="/wbadmin/icons/trans.gif" alt="">
			<xsl:attribute name="width"><xsl:value-of select="@caracter"/></xsl:attribute>
			<xsl:attribute name="height">1</xsl:attribute>
		</img>
		<xsl:if test="@current = '0'">
			<A href="{@path}" class="wmn_link" ><xsl:value-of select="@name"/></A>
		</xsl:if>
		<xsl:if test="@current = '1'">
			<b><i><xsl:value-of select="@name"/></i></b>
		</xsl:if>
	</TD></TR>
	<xsl:for-each select="child">
		<xsl:call-template name="rest" />
	</xsl:for-each>
</xsl:template>

<xsl:template match="/menu">
<LINK href="{@path}images/WBMenu.css" rel="stylesheet" type="text/css" ></LINK>
<DIV class="wmn_box">
<TABLE border="0" cellpadding="0" cellspacing="2" width="100%" >
	<xsl:for-each select="parent">
		<TR><TD >
			<A href="{@path}" class="wmn_title" ><xsl:value-of select="@name"/></A>
		</TD></TR>
	</xsl:for-each>
	<xsl:for-each select="brothers">
		<xsl:for-each select="brother">
			<TR><TD class="wmn_data">
					<img src="/wbadmin/icons/trans.gif" alt="">
						<xsl:attribute name="width"><xsl:value-of select="@caracter"/></xsl:attribute>
						<xsl:attribute name="height">1</xsl:attribute>
					</img>
				<xsl:if test="@current = '0'">
					<A href="{@path}" class="wmn_link" ><xsl:value-of select="@name"/></A>
				</xsl:if>
				<xsl:if test="@current = '1'">
					<b><i><xsl:value-of select="@name"/></i></b>
				</xsl:if>
			</TD></TR>
						<xsl:for-each select="child">
							<xsl:call-template name="rest" />
						</xsl:for-each>
		</xsl:for-each>
	</xsl:for-each>
</TABLE>
</DIV>
</xsl:template>
</xsl:stylesheet>