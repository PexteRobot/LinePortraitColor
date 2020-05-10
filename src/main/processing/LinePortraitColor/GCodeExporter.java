import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class GCodeExporter {
  final Plotter plotter;
  final int plotterAreaWidth;
  final int plotterAreaHeight;
  final float MARGIN = 0.2F;
  final float ANTI_MARGIN = 1 - MARGIN;
  final int UP = 8;
  final double CANVAS_DOWN = -0.2;
  final double BUCKET_DOWN = -2;
  final double WATER_DOWN = -4;

  GCodeExporter(Plotter plotter, int plotterAreaWidth, int plotterAreaHeight){
    this.plotter = plotter;
    this.plotterAreaWidth = plotterAreaWidth;
    this.plotterAreaHeight = plotterAreaHeight;
  }
  void export(File file) {
    System.out.println("Exporting to " + file.getAbsolutePath());
    try (FileWriter fileWriter = new FileWriter(file); BufferedWriter writer = new BufferedWriter(fileWriter)){
      initPlotter(writer);
      go(writer, 0, plotterAreaHeight, UP);
      int currentColor = -1;
      for(PlotterCommand plotterCommand : plotter.getPlot()){
        if(plotterCommand instanceof ColorPlotterCommand){
          ColorPlotterCommand colorPlotterCommand = (ColorPlotterCommand) plotterCommand;
          if(colorPlotterCommand.indexedColor != currentColor) {
            currentColor = colorPlotterCommand.indexedColor;
            putIntoWater(writer);
          }
          putIntoPaint(writer, colorPlotterCommand);
        }
        else if(plotterCommand instanceof PathPlotterCommand){
          PathPlotterCommand pathPlotterCommand = (PathPlotterCommand) plotterCommand;
          drawLine(writer, pathPlotterCommand);
        }
      }
      closePlotter(writer);
    }
    catch(IOException e){
      throw new RuntimeException(e);
    }
  }
  void putIntoWater(BufferedWriter writer) throws IOException {
    goDownShakeUp(writer, plotter.water, WATER_DOWN);
  }
  
  void putIntoPaint(BufferedWriter writer, ColorPlotterCommand colorPlotterCommand) throws IOException {
    Rect colorBucket = plotter.palette.getIndexedColorRect(colorPlotterCommand.indexedColor);
    goDownUp(writer, colorBucket, BUCKET_DOWN);
  }
  void drawLine(BufferedWriter writer, PathPlotterCommand pathPlotterCommand) throws IOException {
    go(writer, pathPlotterCommand.x1, pathPlotterCommand.y1, UP);
    go(writer, pathPlotterCommand.x1, pathPlotterCommand.y1, CANVAS_DOWN);
    if(pathPlotterCommand.x1 != pathPlotterCommand.x2 && pathPlotterCommand.y1 != pathPlotterCommand.y2) {
      go(writer, pathPlotterCommand.x2, pathPlotterCommand.y2, CANVAS_DOWN);
    }
    go(writer, pathPlotterCommand.x2, pathPlotterCommand.y2, UP);
  }
  void goDownUp(BufferedWriter writer, Rect rect, double down) throws IOException {
    goFast(writer, rect.x + rect.width * 0.5, rect.y + rect.height * 0.5, UP);
    goFast(writer, rect.x + rect.width * 0.5, rect.y + rect.height * 0.5, down);
    goFast(writer, rect.x + rect.width * 0.5, rect.y + rect.height * 0.5, UP);
  }
  void goDownShakeUp(BufferedWriter writer, Rect rect, double down) throws IOException {
    if(rect.width >= rect.height){
      goFast(writer, rect.x + rect.width * MARGIN, rect.y + rect.height / 2, UP);
      goFast(writer, rect.x + rect.width * MARGIN, rect.y + rect.height / 2, down);
      goFast(writer, rect.x + rect.width, rect.y + rect.height / 2, down);
      goFast(writer, rect.x + rect.width * ANTI_MARGIN, rect.y + rect.height / 2, down);
      goFast(writer, rect.x + rect.width * ANTI_MARGIN, rect.y + rect.height / 2, UP);
    }
    else{
      goFast(writer, rect.x + rect.width / 2, rect.y + rect.height * MARGIN, UP);
      goFast(writer, rect.x + rect.width / 2, rect.y + rect.height * MARGIN, down);
      goFast(writer, rect.x + rect.width / 2, rect.y + rect.height, down);
      goFast(writer, rect.x + rect.width / 2, rect.y + rect.height * ANTI_MARGIN, down);
      goFast(writer, rect.x + rect.width / 2, rect.y + rect.height * ANTI_MARGIN, UP);
    }
  }

  void initPlotter(BufferedWriter writer) throws IOException {
    writer.write("G90\n" +
            "G21\n" +
            "G00 X0 Y0 Z0\n" +
            "F8000\n");
  }

  void goFast(BufferedWriter writer, double x, double y, double z) throws IOException {
    writer.write("G00 X");
    finishGoLine(writer, x, y, z);
  }

  void go(BufferedWriter writer, double x, double y, double z) throws IOException {
    writer.write("G01 X");
    finishGoLine(writer, x, y, z);
  }
  
  void finishGoLine(BufferedWriter writer, double x, double y, double z) throws IOException {
    writer.write(String.valueOf((int)x));
    writer.write(" Y");
    writer.write(String.valueOf(plotterAreaHeight - (int)y));
    writer.write(" Z");
    writer.write(String.valueOf(z));
    writer.newLine();
  }
  
    void closePlotter(BufferedWriter writer) throws IOException {
    writer.write("G00 Z5\n" +
            "G00 X0 Y0\n");
  }
}
