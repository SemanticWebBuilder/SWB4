<html>
<meta charset="utf-8">
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
<script src="../d3.layout.cloud.js" charset="utf-8"></script>

<body>
<script>
var fill = d3.scale.category10(); 
 
  var jWord = ["abc","def","ghi","jkl"];
  var jCount = [2, 5, 3, 8];
   
  var maxCount = d3.max(jCount);
  var s = d3.scale.linear().range([10,50]).domain([0, maxCount]); 

  d3.layout.cloud().size([650, 140])
      .words(d3.zip(jWord, jCount).map(function(d) {
            return {text: d[0], size: s(d[1]) };
       }))
      .rotate(function() { return 0; })
      //.rotate(function() { return ~~(Math.random() * 2) * 90; })
      .fontSize(function(d) { return d.size; })
      .on("end", draw)
      .start();
     

  function draw(words) {
    d3.select("body").append("svg")
        .attr("width", 300)
        .attr("height", 300)
      .append("g")
        .attr("transform", "translate(150,150)")
      .selectAll("text")
        .data(words)
      .enter().append("a").attr("xlink:href", "http://www.google.com/")   
      .append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("font-family", "Impact")
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
  }
</script>

</body>

</html>
