<%-- 
    Document   : lineChartByHour
    Created on : 2/05/2014, 07:01:56 PM
    Author     : francisco.jimenez
--%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>
<meta charset="utf-8">

<link href="/swbadmin/css/nv.d3.css" rel="stylesheet" type="text/css">

<style>

body {
  overflow-y:scroll;
}

text {
  font: 12px sans-serif;
}

svg {
  display: block;
}

#chart1 svg {
  height: 500px;
  min-width: 200px;
  min-height: 100px;
/*
  margin: 50px;
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/

}
#chart1 {
  margin-top: 200px;
  margin-left: 100px;
}
</style>
<body class='with-3d-shadow with-transitions'>

<div id="chart1" >
  <svg style="height: 500px;"></svg>
</div>

<script src="../../js/d3.v3.js"></script>
<script src="../../js/nv.d3.js"></script>


<script>
// Wrapping in nv.addGraph allows for '0 timeout render', stores rendered charts in nv.graphs, and may do more in the future... it's NOT required
var chart;

nv.addGraph(function() {
  chart = nv.models.lineChart()
  .options({
    margin: {left: 100, bottom: 100},
    x: function(d,i) { return i},
    showXAxis: true,
    showYAxis: true,
    transitionDuration: 250
  })
  ;

  // chart sub-models (ie. xAxis, yAxis, etc) when accessed directly, return themselves, not the parent chart, so need to chain separately
  chart.xAxis
    .axisLabel("Hora del dia (s)")
    .tickFormat(d3.format(',.1d'));

  chart.yAxis
    .axisLabel('Numero de posts')
    .tickFormat(d3.format("d"))
    ;

  d3.select('#chart1 svg')
    .datum(sinAndCos())
    .call(chart);

  //TODO: Figure out a good way to do this automatically
  nv.utils.windowResize(chart.update);
  //nv.utils.windowResize(function() { d3.select('#chart1 svg').call(chart) });

  chart.dispatch.on('stateChange', function(e) { nv.log('New State:', JSON.stringify(e)); });

  return chart;
});

function sinAndCos() {
  var rand = [];
  var positives =[];
  var negatives =[];
  var neutrals =[];
  for (var i = 0; i < 24; i++) {
	rand.push({x:i, y: Math.floor(Math.random()*101)});
	positives.push({x:i, y: Math.floor(Math.random()*11)});
    negatives.push({x:i, y: Math.floor(Math.random()*11) + 10});
	neutrals.push({x:i, y: Math.floor(Math.random()*11) + 10});
  }

  return [
    {
      values: rand,
	  positivos: positives,
	  negativos: negatives,
	  neutros: neutrals,
      key: "The random value",
      color: "#FF6600"
    }
  ];
}

</script>