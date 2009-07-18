<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1" indent="yes"/> 
<xsl:template match="/">
	<xsl:if test="QUERY/result">
		<LINK href="{QUERY/@path}images/DataBaseResource.css" rel="stylesheet" type="text/css"></LINK>
		<table width="100%" border="0" cellpadding="5" cellspacing="0">
		<xsl:for-each select="QUERY/result/row">
			<xsl:for-each select="col">
				<xsl:variable name="i" select="position()"/>
				<tr>
					<td class="dbr_header"><xsl:value-of select="/QUERY/header/col_name[$i]" />:</td>
					<td class="dbr_data" width="100%"><xsl:value-of select="." /></td>
				</tr>
			</xsl:for-each>
		</xsl:for-each>
		<xsl:if test="QUERY/paging">
			<xsl:if test="QUERY/paging/results">
				<tr><td colspan="2" align="center"><font class="drs_footer">
					<xsl:value-of select="QUERY/paging/results/@caption"/> 
					<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
					<xsl:value-of select="QUERY/paging/results"/>
				</font></td></tr>
			</xsl:if>			
			<tr><td colspan="2" align="center"><font class="drs_footer">
				<xsl:if test="QUERY/paging/pages">
					<xsl:value-of select="QUERY/paging/pages/@caption"/>
					<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
				</xsl:if>
				<xsl:if test="QUERY/paging/first">
					<xsl:text disable-output-escaping="yes">&amp;nbsp;&lt;a class=&quot;dbr_link&quot; href=&quot;</xsl:text>
					<xsl:value-of select="QUERY/paging/first"/>
					<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="QUERY/paging/first/@caption"/>
					<xsl:text disable-output-escaping="yes">&lt;/a&gt;&amp;nbsp;|&amp;nbsp;</xsl:text>					
				</xsl:if>					
				<xsl:if test="QUERY/paging/back">
					<xsl:text disable-output-escaping="yes">&amp;nbsp;&lt;a class=&quot;dbr_link&quot; href=&quot;</xsl:text>
					<xsl:value-of select="QUERY/paging/back"/>
					<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="QUERY/paging/back/@caption"/>
					<xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
					<xsl:if test="not(QUERY/paging/pages) and QUERY/paging/next">
						<xsl:text disable-output-escaping="yes">&amp;nbsp;|&amp;nbsp;</xsl:text>
					</xsl:if>
				</xsl:if>
				<xsl:for-each select="QUERY/paging/pages/page">
					<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
					<xsl:if test="node()">
						<xsl:text disable-output-escaping="yes">&lt;a class=&quot;dbr_link&quot; href=&quot;</xsl:text>
						<xsl:value-of select="."/>
						<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					</xsl:if>
					<xsl:value-of select="@caption"/>
					<xsl:if test="node()">
						<xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
					</xsl:if>
				</xsl:for-each>
				<xsl:if test="QUERY/paging/next">
					<xsl:text disable-output-escaping="yes">&amp;nbsp;&lt;a class=&quot;dbr_link&quot; href=&quot;</xsl:text>
					<xsl:value-of select="QUERY/paging/next"/>
					<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="QUERY/paging/next/@caption"/>
					<xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>
					<xsl:if test="not(QUERY/paging/pages) ">
						<xsl:text disable-output-escaping="yes">&amp;nbsp;|&amp;nbsp;</xsl:text>
					</xsl:if>						
				</xsl:if>
				<xsl:if test="QUERY/paging/last">
					<xsl:text disable-output-escaping="yes">&amp;nbsp;&lt;a class=&quot;dbr_link&quot; href=&quot;</xsl:text>
					<xsl:value-of select="QUERY/paging/last"/>
					<xsl:text disable-output-escaping="yes">&quot;&gt;</xsl:text>
					<xsl:value-of select="QUERY/paging/last/@caption"/>
					<xsl:text disable-output-escaping="yes">&lt;/a&gt;</xsl:text>					
				</xsl:if>					
			</font></td></tr>							
		</xsl:if>
		</table>
	</xsl:if> 
</xsl:template>
</xsl:stylesheet>