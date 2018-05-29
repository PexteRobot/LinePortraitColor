class PlotterVisualizer {
  final Plotter plotter;
  final Random random;
  int commandIndex;
  final int transformX;
  final static int GAP = 2;
  final static int TEXT_SIZE = 8;
    
  PlotterVisualizer(Plotter plotter){
    this.plotter = plotter;
    this.random = new Random();
    this.transformX = (int) Math.min(plotter.palette.x, plotter.water.x);
  }
  
  void draw(){
    scale(2);
    translate(-transformX, 0);
    if (commandIndex == 0){
      drawStaticObjects();
    }
    if (commandIndex >= plotter.getPlot().size()){
      return;
    }
    PlotterCommand plotterCommand = plotter.getPlot().get(commandIndex++);
    if(plotterCommand instanceof ColorPlotterCommand){
      ColorPlotterCommand colorPlotterCommand = (ColorPlotterCommand) plotterCommand;
      color c = plotter.indexedColorPalette.getColorByIndex(colorPlotterCommand.indexedColor);
      
      strokeWeight((int)plotter.brushSize * 2);
      stroke(c);
      
      Rect colorBucket = plotter.palette.getIndexedColorRect(colorPlotterCommand.indexedColor);
      point((float)colorBucket.x + random.nextFloat() * (colorBucket.width - 2 * GAP) + GAP, (float)colorBucket.y + random.nextFloat() * (colorBucket.height - 2 * GAP) + GAP);
    }
    else if(plotterCommand instanceof PathPlotterCommand){
      PathPlotterCommand pathPlotterCommand = (PathPlotterCommand) plotterCommand;
      strokeWeight((int)plotter.brushSize);
      line((float)pathPlotterCommand.x1, (float)pathPlotterCommand.y1, (float)pathPlotterCommand.x2, (float)pathPlotterCommand.y2);
    }
  }
  
  void draw(Rect rect1){
    rect(rect1.x, rect1.y, rect1.width, rect1.height);
  }
  
  void drawStaticObjects(){
    stroke(color(0, 0, 0));
    strokeWeight(1);
    fill(255, 255, 255);
    draw(plotter.canvas);
    color[] colors = plotter.palette.indexedColorPalette.colors;
    for(int i = 0; i < colors.length; i++){
      drawPaintBucket(plotter.palette.getIndexedColorRect(i)); 
    }
    draw(plotter.water);
    
    textSize(TEXT_SIZE);
    fill(0, 0, 0);
    text("paint", plotter.palette.x, plotter.palette.y - TEXT_SIZE);
    text("water", plotter.water.x, plotter.water.y - TEXT_SIZE);
    noFill();
  }
  
  void drawPaintBucket(Rect rect){
    if(rect != null) {
      draw(rect);
    }
  }
}
