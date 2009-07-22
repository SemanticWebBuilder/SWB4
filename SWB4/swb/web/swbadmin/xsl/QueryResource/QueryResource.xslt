<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/> 
<xsl:template match="/">
<xsl:if test="QUERY">
    <LINK href="{QUERY/@path}swb-estilo.css" rel="stylesheet" type="text/css"></LINK>
    <xsl:value-of select="QUERY/@styleClass" disable-output-escaping="yes" />
        <div class="swb-query">
		<table width="100%" border="0" cellpadding="5" cellspacing="0">
        <xsl:if test="QUERY/header">
            <thead>
                <tr class="qrs_header">
                <xsl:for-each select="QUERY/header/col_name">
                    <th><xsl:value-of select="." /></th>
                </xsl:for-each>
                </tr>
            </thead>
        </xsl:if>
        <xsl:if test="QUERY/result">
            <tbody>
            <xsl:for-each select="QUERY/result/row">
                <!--<xsl:number/>-->
                <xsl:text disable-output-escaping="yes">&lt;tr </xsl:text>
                <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
                <xsl:for-each select="col">
                    <td><xsl:value-of select="."/></td>
                </xsl:for-each>
                <xsl:text disable-output-escaping="yes">&lt;/tr&gt;</xsl:text>
            </xsl:for-each>
            </tbody>
        </xsl:if>
		</table>
        </div>
    <xsl:value-of select="QUERY/@styleClassClose" disable-output-escaping="yes" />
</xsl:if> 
</xsl:template>
</xsl:stylesheet>