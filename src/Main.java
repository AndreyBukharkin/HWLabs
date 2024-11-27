import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main extends JPanel{
    private final List<PointColor> points;
    public Main(List<PointColor> points) {
        this.points = points;
    }

    public static void main(String[] args) {
        double[][] num = {{4, 0, 0}, {8, 6, 0}, {1, 5, 0}, {3, 7, 0},
                          {8, 8, 0}, {9, 6, 0}, {8, 6, 0}, {7, 6, 0},
                          {6, 6, 0}, {4, 3, 0}, {5, 1, 1}, {4, 2, 1},
                          {4, 7, 1}, {0, 8, 1}, {4, 1, 1}, {6, 8, 1}};

        JFrame frame = new JFrame();
        Main panel = new Main(fillPoints(num));
        frame.add(panel);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    private static List<PointColor> fillPoints(double[][] nums){
        List<PointColor> result = new ArrayList<>();
        for (double [] point : nums) {
            result.add(new PointColor(point[0], point[1], point[2] == 1 ? ColorP.RED : point[2] == 0 ? ColorP.BLUE : ColorP.COLORLESS));
        }
            // Math.random() * 10  Math.round(Math.random() * 10)/10 Math.round(number * count) / count

        for (double x = 0; x < 200; x++) {
            for (double y = 0; y < 200; y++) {
                result.add(new PointColor(x/20, y/20, ColorP.COLORLESS));
            }
        }

        return result;
    }

    protected void paintComponent(Graphics g){
        Graphics2D paintPoints = (Graphics2D) g;

        HashSet<PointColor> value = new HashSet<>();

        for (PointColor point : points) {
            double x = point.getX(), y = -point.getY();
            ColorP color = point.getColor();

            if(value.isEmpty()){
                value.add(point);
                paintPoints.setColor(color == ColorP.RED ? Color.RED : Color.BLUE);
                // 100 + x * 50 700 + y * 50
                g.drawOval((int)(100 + x * 70), (int)(750 + y * 70), 4,4);
            }else{
                boolean isToAdd = true;
                for (PointColor a : value) {
                    if(a.getX() == x && -a.getY() == y){
                        isToAdd = false;
                        break;
                    }
                }
                if(isToAdd){

                    if(color == ColorP.COLORLESS){
                        double ab;
                        double z = 0;
                        HashMap<PointColor, Double> distance = new HashMap<>();
                        for (PointColor colorResult : value) {
                            if(colorResult == point){
                                continue;
                            }
                            ab = (int)(Math.sqrt(Math.pow(colorResult.getX() - x,2)) + Math.pow(colorResult.getY() + y,2));
                            distance.put(colorResult, ab);
                        }

                        var pointsMinDistance = distance.entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .limit(3)
                                .toList();
                        for (var nearPoint : pointsMinDistance) {
                            if (nearPoint.getColor() == ColorP.BLUE){
                                z += 1;
                            }else if (nearPoint.getColor() == ColorP.RED){
                                z -= 1;
                            }
                        }
                        if(z > 0){
                            color = ColorP.BLUE;
                        }else if (z < 0){
                            color = ColorP.RED;
                        }
                        point = new PointColor(x, y, color);
                    }
                    value.add(point);
                    paintPoints.setColor(color == ColorP.RED ? Color.RED : Color.BLUE);
                    g.drawOval((int)(100 + x * 70), (int)(750 + y * 70), 4,4);
                }
            }
        }
    }
}