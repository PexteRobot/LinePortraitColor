import java.util.ArrayList;

public interface PlotterCommandStrategy {
    ArrayList<PlotterCommand> generatePlotterCommands(Color[][] image, ImagingConfig imagingConfig, Rect canvas, IndexedColorPalette indexedColorPalette);
}
