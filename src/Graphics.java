import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graphics extends JFrame{

    private int height;
    private int width;
    private String name;
    private NeuralNetwork neuralNetwork;
    private ArrayList<ArrayList<Point>> points;

    public Graphics(NeuralNetwork nn, String name) {
        this(nn, name, 800, 800);
    }

    public Graphics(NeuralNetwork nn, String name, int x, int y) {
        this.name = name;
        this.width = x;
        this.height = y;
        this.neuralNetwork = nn;

        initUI();

        points = new ArrayList<>(points.size());

        for (int i = 0; i < nn.getSize(); i++) {
           points.add(new ArrayList<>(nn.getNodes(i)));
           double w = width/(nn.getSize() + 1.0);
           for (int j = 0; j < nn.getNodes(i); j++) {
               double h = height/(nn.getNodes(i) + 1.0);
               points.get(i).add(new Point(w * (i + 1), h * (j + 1)));
           }
        }
    }

    private void initUI() {
        add(new Surface());

        setTitle(name);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ArrayList<ArrayList<Point>> getPoints() {
        return points;
    }
}

class Surface extends JPanel {

    private void doDrawing(java.awt.Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawString("hey",50, 50);
        //for (int i = 0; i < )
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }
}


/**
 * Point class, stores standard x, y coordinates
 */
class Point {

    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

}