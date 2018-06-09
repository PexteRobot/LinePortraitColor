import java.util.ArrayList;

public interface PlotterCommandStrategy {
    ArrayList<PlotterCommand> generatePlotterCommands(Color[][] image, double pixelSize, ImagingConfig imagingConfig, Rect canvas, IndexedColorPalette indexedColorPalette);
}
