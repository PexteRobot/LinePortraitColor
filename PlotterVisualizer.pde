class PlotterVisualizer{
  final Plotter plotter;
  final Random random;
  int commandIndex;
  final int transformX;
    
  PlotterVisualizer(Plotter plotter){
    this.plotter = plotter;
    this.random = new Random();
    this.transformX = (int)Math.min(plotter.palette.x, plotter.water.x);
  }
  
  void draw(){
    scale(2);
    translate(-transformX, 0);
    if (commandIndex == 0){
      draw(plotter.canvas);
      color[] colors = plotter.palette.indexedColorPalette.colors;
      for(int i = 0; i < colors.length; i++){
        color(colors[i]);
        Rect rect1 = plotter.palette.getIndexedColorRect(i);
        if(rect1 != null)
          draw(rect1);
      }
      draw(plotter.water);
    }
    if (commandIndex >= plotter.getPlot().size()){
      return;
    }
    PlotterCommand plotterCommand = plotter.getPlot().get(commandIndex++);
    if(plotterCommand instanceof ColorPlotterCommand){
      ColorPlotterCommand colorPlotterCommand = (ColorPlotterCommand) plotterCommand;
      color c = plotter.indexedColorPalette.getColorByIndex(colorPlotterCommand.indexedColor);
      
      strokeWeight((int)plotter.brushSize);
      stroke(c);
      
      Rect colorBucket = plotter.palette.getIndexedColorRect(colorPlotterCommand.indexedColor);
      point((float)colorBucket.x + random.nextFloat() * colorBucket.width, (float)colorBucket.y + random.nextFloat() * colorBucket.height);
    }
    else if(plotterCommand instanceof PathPlotterCommand){
      PathPlotterCommand pathPlotterCommand = (PathPlotterCommand) plotterCommand;
      line((float)pathPlotterCommand.x1, (float)pathPlotterCommand.y1, (float)pathPlotterCommand.x2, (float)pathPlotterCommand.y2);
    }
  }
  void draw(Rect rect1){
    rect(rect1.x, rect1.y, rect1.width, rect1.height);
  }
}
