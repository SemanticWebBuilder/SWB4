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

public-read var style_infoWorkflow="-fx-fill: #F8F8F8  ; -fx-stroke: #404040; -fx-stroke-width: 2; -fx-arc-width: 5; -fx-arc-height: 5;";

public-read var style_tooltip="-fx-fill: #FFFF66; -fx-stroke: #000000; -fx-stroke-width: 0; -fx-arc-width: 0; -fx-arc-height: 0;";

public-read var style_task="-fx-fill: {color_fill}; -fx-stroke: {color}; -fx-stroke-width: 2; -fx-arc-width: 15; -fx-arc-height: 15;";
//public-read var style_task="fill: {color_fill}; stroke: {color}; strokeWidth: 2; arcWidth: 15; arcHeight: 15;";
//public-read var style_task_text="font-size: 14px; font-family: \"'Verdana'\"; fill: #000000;font-weight: bold;";
public-read var style_task_text="-fx-font-size: 14px; -fx-font-family: \"'Verdana'\"; -fx-fill: #000000; -fx-font-weight: bold;";

public-read var style_label_name_workflow="-fx-font-size: 14px; -fx-font-family: \"Helvetica, Arial\"; -fx-font-weight: bold; -fx-focus-fill:transparent; -fx-shadow-fill:transparent";
public-read var style_task_textbox="-fx-font-size: 14px; -fx-font-family: \"Helvetica, Arial\"; -fx-font-weight: bold; -fx-border-fill:transparent; -fx-background-fill:transparent; -fx-focus-fill:transparent; -fx-shadow-fill:transparent";

public-read var style_gateway="-fx-fill: {color_fill}; -fx-stroke: {color_gateway}; -fx-stroke-width: 2;";
public-read var style_message="-fx-fill: {color_fill}; -fx-stroke: {color}; -fx-stroke-width: 2;";
public-read var style_simbol="-fx-fill: {color_fill}; -fx-stroke: {color_gateway}; -fx-stroke-width: 4;";
public-read var style_event="-fx-fill: {color_fill}; -fx-stroke: {color}; -fx-stroke-width: 2;";

public-read var style_connection="-fx-stroke: {color}; -fx-stroke-width: 2;";
public-read var style_connection_row="-fx-stroke: {color}; -fx-stroke-width: 2;";

public-read var style_connection_authorize="-fx-stroke: {color_authorize}; -fx-stroke-width: 2;";
public-read var style_connection_arrow_authorize="-fx-stroke: {color_authorize}; -fx-stroke-width: 2;";

public-read var style_connection_over="-fx-stroke: {color_over}; -fx-stroke-width: 3;";
public-read var style_connection_row_over="-fx-stroke: {color_over}; stroke-width: 3;";


public-read var style_connection_not_authorize="-fx-stroke: {color_no_authorize}; -fx-stroke-width: 2;";
public-read var style_connection_arrow_not_authorize="-fx-stroke: {color_no_authorize}; -fx-stroke-width: 2;";
//public-read var style_toolbar="fill: #f0f0f0; stroke: #909090; strokeWidth: 2;";
public-read var style_toolbar="-fx-fill: #f0f0f0; -fx-stroke: #909090; -fx-stroke-width: 2;";
public-read var style_pool="-fx-fill: {color_fill_pool}; -fx-stroke: {color}; -fx-stroke-width: 1;";

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

