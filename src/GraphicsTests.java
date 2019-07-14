import java.awt.*;

public class GraphicsTests {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NeuralNetwork nn = new NeuralNetwork(3, 3, 3);
                Graphics graphics = new Graphics(nn,"Test");
                graphics.setVisible(true);
            }
        });
    }
}
