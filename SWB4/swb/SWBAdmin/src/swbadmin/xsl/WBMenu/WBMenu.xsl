<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" encoding="ISO-8859-1"/>

<xsl:template name="children">
    <xsl:for-each select="brothers">
        <ul>
            <xsl:for-each select="brother">
                <li class="swb-menu-ch">
                    <xsl:if test="@current ='1'">
                        <a href="{@path}"><em><xsl:value-of select="@name"/></em></a>
                        <ul>
                            <xsl:for-each select="child">
                                <li class="swb-menu-gs">
                                    <a href="{@path}"><xsl:value-of select="@name"/></a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </xsl:if>
                    <xsl:if test="@current ='0'">
                        <a href="{@path}"><xsl:value-of select="@name"/></a>
                    </xsl:if>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:for-each>
</xsl:template>

<xsl:template match="/menu">
    <div class="swb-menu">
        <ul>
            <xsl:for-each select="parent">
                <li class="swb-menu-pr">
                    <a href="{@path}">
                    <strong><xsl:value-of select="@name"/></strong>
                    </a>

                </li>
            </xsl:for-each>
            <li><xsl:call-template name="children" /></li>
        </ul>
    </div>
</xsl:template>
</xsl:stylesheet>