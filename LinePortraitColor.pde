import java.util.Random;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;

PImage img;
int steps = 5;
float threshold = 1.0; 
PlotterVisualizer plotterVisualizer;
void setup() {
//  size (210,290);
//  img = loadImage("monl.jpg");

  //size (600, 600);
  //img = loadImage("dali.jpg");

  size (600, 600);
  background(255);
  // img = loadImage("palitra12f.jpg");
  img = loadImage("palitra12f.png");

  Plotter plotter = new Plotter(2, 20);
  plotter.buildPlot(img);
  plotterVisualizer = new PlotterVisualizer(plotter);
  new GCodeExporter(plotter, 210, 290).export(new File("g-codes.txt"));
}
void draw() {
  plotterVisualizer.draw();
}
