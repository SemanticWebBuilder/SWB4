<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/> 
<xsl:template match="/">
	<xsl:if test="QUERY">
		<LINK href="{QUERY/@path}swb-estilo.css" rel="stylesheet" type="text/css"></LINK>
                <div id="swb-query">
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
            <xsl:if test="QUERY/pages">
                <tfoot align="center">
                    <tr>
                        <td colspan="{QUERY/@ncol}">
                        <xsl:for-each select="QUERY/pages/pageback">
                           <a href="{@link}" ><xsl:value-of select="@texto"/></a>&#160;
                        </xsl:for-each>
                        <xsl:for-each select="QUERY/pages/page">
                            <xsl:value-of select="@link"  disable-output-escaping="yes"/>&#160;
                        </xsl:for-each>
                        <xsl:for-each select="QUERY/pages/pagenext">
                           <a href="{@link}" ><xsl:value-of select="@texto"/></a>
                        </xsl:for-each>
                        </td>
                    </tr>
                </tfoot>
            </xsl:if>
          </table>
        </div>
	</xsl:if> 
</xsl:template>
</xsl:stylesheet>