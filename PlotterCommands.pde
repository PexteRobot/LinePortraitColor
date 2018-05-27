interface PlotterCommand{}

class PathPlotterCommand implements PlotterCommand {
  final double x1;
  final double y1;
  final double x2;
  final double y2;
  PathPlotterCommand(double x1, double y1, double x2, double y2){
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
}

class ColorPlotterCommand implements PlotterCommand {
  final int indexedColor;
  ColorPlotterCommand(int indexedColor){
    this.indexedColor = indexedColor;
  }
}
