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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PathPlotterCommand that = (PathPlotterCommand) o;

        if (Double.compare(that.x1, x1) != 0) {
            return false;
        }
        if (Double.compare(that.y1, y1) != 0) {
            return false;
        }
        if (Double.compare(that.x2, x2) != 0) {
            return false;
        }
        return Double.compare(that.y2, y2) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x1);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("path[").append((int)x1).append(", ").append((int)y1).append(" -> ").append((int)x2).append(", ").append((int)y2).append("]").toString();
    }
}