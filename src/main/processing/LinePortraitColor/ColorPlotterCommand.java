class ColorPlotterCommand implements PlotterCommand {
    final int indexedColor;
    ColorPlotterCommand(int indexedColor) {
        this.indexedColor = indexedColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColorPlotterCommand that = (ColorPlotterCommand) o;
        return indexedColor == that.indexedColor;
    }

    @Override
    public int hashCode() {
        return indexedColor;
    }

    @Override
    public String toString() {
        return "color[" + indexedColor + "]";
    }
}
