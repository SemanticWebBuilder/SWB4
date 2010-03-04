/*
 * Styles.fx
 *
 * Created on 26/02/2010, 12:30:02 PM
 */

package org.semanticwb.publishflow;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
/**
 * @author victor.lorenzana
 */

public-read var stylesheets = "{__DIR__}style.css";
//public-read var color="#6060FF";
public-read var color="#03689a";
public-read var color_authorize="#33CC00";
public-read var color_no_authorize="#CC0033";
public-read var color_over="#999999";
public-read var color_focused="#FF8080";
public-read var color_fill="#f5f5ff";
public-read var color_fill_pool="#d5d5ff";

public-read var color_endEvent="#660000";
public-read var color_authorEvent="#CC6600";
public-read var color_iniEvent="#3c8e3c";
public-read var color_interEvent="#5e7a9e";

public-read var color_gateway="#cc9900";

public-read var style_task="fill: {color_fill}; stroke: {color}; strokeWidth: 2; arcWidth: 15; arcHeight: 15;";
public-read var style_task_text="font-size: 14px; font-family: \"'Verdana'\"; fill: #000000;font-weight: bold;";
//var style_task_textbox="font-size: 14px; font-family: \"Helvetica, Arial\"; font-weight: bold; border-fill:transparent; background-fill:transparent; focus-fill:transparent; shadow-fill:transparent";
public-read var style_task_textbox="font-size: 14px; font-family: \"Helvetica, Arial\"; font-weight: bold; focus-fill:transparent; shadow-fill:transparent";
public-read var style_gateway="fill: {color_fill}; stroke: {color_gateway}; strokeWidth: 2;";
public-read var style_message="fill: {color_fill}; stroke: {color}; strokeWidth: 2;";
public-read var style_simbol="fill: {color_fill}; stroke: {color_gateway}; strokeWidth: 4;";
public-read var style_event="fill: {color_fill}; stroke: {color}; strokeWidth: 2;";

public-read var style_connection="stroke: {color}; strokeWidth: 2;";
public-read var style_connection_row="stroke: {color}; strokeWidth: 2;";

public-read var style_connection_authorize="stroke: {color_authorize}; strokeWidth: 2;";
public-read var style_connection_arrow_authorize="stroke: {color_authorize}; strokeWidth: 2;";

public-read var style_connection_over="stroke: {color_over}; strokeWidth: 3;";
public-read var style_connection_row_over="stroke: {color_over}; strokeWidth: 3;";


public-read var style_connection_not_authorize="stroke: {color_no_authorize}; strokeWidth: 2;";
public-read var style_connection_arrow_not_authorize="stroke: {color_no_authorize}; strokeWidth: 2;";
public-read var style_toolbar="fill: #f0f0f0; stroke: #909090; strokeWidth: 2;";
public-read var style_pool="fill: {color_fill_pool}; stroke: {color}; strokeWidth: 1;";

public-read var msg_timer=Image{url: "{__DIR__}images/ico_reloj.png"}
public-read var msg_message=Image{url: "{__DIR__}images/ico_sobre.png"}
public-read var msg_rule=Image{url: "{__DIR__}images/ico_algo.png"}
public-read var msg_link=Image{url: "{__DIR__}images/ico_flecha.png"}
public-read var msg_multiple=Image{url: "{__DIR__}images/ico_pentagono.png"}

public-read var dropShadow = DropShadow {
    offsetX: 3
    offsetY: 3
    radius: 10.0
    color: Color.web("#707070")
}

