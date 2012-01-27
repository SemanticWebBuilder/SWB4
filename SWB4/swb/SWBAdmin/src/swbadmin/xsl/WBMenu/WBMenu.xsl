<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    
    <xsl:template match="/menu">
        <div class="swb-menu">
            <ul>
                <xsl:for-each select="parent">
                    <li class="swb-menu-pr">
                        <a href="{@path}" target="{@target}" title="{@name}">
                            <strong><xsl:value-of select="@name"/></strong>
                        </a>

                    </li>
                </xsl:for-each>
                <li><xsl:call-template name="nodes" /></li>
            </ul>
        </div>
    </xsl:template>

    <xsl:template name="nodes">
        <xsl:for-each select="brothers">
            <ul>
                <xsl:for-each select="brother">
                    <xsl:choose>
                        <xsl:when test="@current = '1'">
                            <li class="swb-menu-current">
                                <a href="{@path}" class="swb-menu-current" target="{@target}" title="{@name}">
                                    <xsl:value-of select="@name"/>
                                </a>
                                <ul>
                                    <xsl:for-each select="child">
                                        <li class="swb-menu-child">
                                            <a href="{@path}" target="{@target}" title="{@name}">
                                                <xsl:value-of select="@name"/>
                                            </a>
                                        </li>
                                    </xsl:for-each>
                                </ul>
                            </li>
                        </xsl:when>
                        <xsl:otherwise>
                            <li class="swb-menu-active">
                                <a href="{@path}" target="{@target}" title="{@name}">
                                    <xsl:value-of select="@name"/>
                                </a>
                            </li>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </ul>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>