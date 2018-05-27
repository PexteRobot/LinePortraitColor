class Palette extends Rect {
  final static int width = 20;
  final static int bucketHeight = 18;
  final static int bucketGap = 2;
  final IndexedColorPalette indexedColorPalette;
  Palette(int x, int y, IndexedColorPalette indexedColorPalette){
    super(x, y, width, indexedColorPalette.colors.length * (bucketHeight + bucketGap));
    this.indexedColorPalette = indexedColorPalette;
  }
  Point2D getIndexedColorPosition(int index){
    return new Point2D(x, y + bucketHeight * index);
  }
  Rect getIndexedColorRect(int index){
    if(index == indexedColorPalette.skippedColorIndex)
      return null;
    return new Rect(x, y + (bucketHeight + bucketGap) * (index - 1) , width, bucketHeight);
  }
}
