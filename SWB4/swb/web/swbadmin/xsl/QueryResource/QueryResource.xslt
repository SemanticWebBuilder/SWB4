<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/> 
<xsl:template match="/">
	<xsl:if test="QUERY">
		<LINK href="{QUERY/@path}images/QueryResource.css" rel="stylesheet" type="text/css"></LINK>
		<table width="100%" border="0" cellpadding="5" cellspacing="0">
			<xsl:if test="QUERY/header">
				<tr class="qrs_header">
					<xsl:for-each select="QUERY/header/col_name">
						<td><xsl:value-of select="." /></td>
					</xsl:for-each>
				</tr>
			</xsl:if> 
			<xsl:if test="QUERY/result">
				<xsl:for-each select="QUERY/result/row">
					<!--<xsl:number/>-->
					<xsl:text disable-output-escaping="yes">&lt;tr class=&quot;qrs_data&quot; </xsl:text>
	        			<xsl:choose>
		    				<xsl:when test="(position() mod 2) = 1"> 
		    					bgcolor=&quot;#EFEDEC&quot; 
		    				</xsl:when>	    					
		    				<xsl:otherwise>bgcolor=&quot;#FFFFFF&quot;</xsl:otherwise>
				        </xsl:choose>
				        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
					<xsl:for-each select="col">
						<td><xsl:value-of select="."/></td>
					</xsl:for-each>
					<xsl:text disable-output-escaping="yes">&lt;/tr&gt;</xsl:text>
				</xsl:for-each>
			</xsl:if> 
		</table>
	</xsl:if> 
</xsl:template>
</xsl:stylesheet>