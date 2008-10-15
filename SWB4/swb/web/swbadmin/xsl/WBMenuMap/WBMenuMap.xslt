<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>

<xsl:template name="nodetpl">
	<TR><TD class="wmn_data" >
		<img src="/wbadmin/icons/trans.gif" alt="" height="1">
			<xsl:attribute name="width"><xsl:value-of select="@level * 10"/></xsl:attribute>
		</img>
                <xsl:if test="@inPath = 'true'">
                    <xsl:text disable-output-escaping="yes">
                        &lt;b>
                    </xsl:text>
                </xsl:if>
		<xsl:if test="@current = 'false'">
			<A href="{@path}" class="wmn_link" ><xsl:value-of select="@name"/></A>
		</xsl:if>
		<xsl:if test="@current = 'true'">
			<b><i><xsl:value-of select="@name"/></i></b>
		</xsl:if>
                <xsl:if test="@inPath = 'true'">
                    <xsl:text  disable-output-escaping="yes">
                        &lt;/b>
                    </xsl:text>
                </xsl:if>                
	</TD></TR>
	<xsl:for-each select="node">
		<xsl:call-template name="nodetpl" />
	</xsl:for-each>
</xsl:template>

<xsl:template match="/menu">
<LINK href="{@path}images/WBMenuMap.css" rel="stylesheet" type="text/css" ></LINK>
<DIV class="wmn_box">
<TABLE border="0" cellpadding="0" cellspacing="2" width="100%" >
        <xsl:for-each select="node">
                <xsl:call-template name="nodetpl" />
        </xsl:for-each>
</TABLE>
</DIV>
</xsl:template>
</xsl:stylesheet>