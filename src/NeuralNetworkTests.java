import java.util.ArrayList;
import java.util.Random;

class NeuralNetworkTests {
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(2, new int[] {4, 4}, 1, 0.1);
        Random random = new Random();

        for (int i = 0; i < 50000; i++) {
            int a = random.nextInt(2);
            int b = random.nextInt(2);
            ArrayList<Double> inputs = new ArrayList<>();
            ArrayList<Double> targets = new ArrayList<>();
            inputs.add((double)a);
            inputs.add((double)b);
            targets.add((double)answer(a,b));

            nn.trainDouble(inputs, targets);
        }

        ArrayList<Double> in = new ArrayList<>();
        in.add((double)1);
        in.add((double)1);
        System.out.println(nn.feedforward(in));
        in = new ArrayList<>();
        in.add((double)0);
        in.add((double)0);
        System.out.println(nn.feedforward(in));
        in = new ArrayList<>();
        in.add((double)1);
        in.add((double)0);
        System.out.println(nn.feedforward(in));
        in = new ArrayList<>();
        in.add((double)0);
        in.add((double)1);
        System.out.println(nn.feedforward(in));
        System.out.println("result after loading");
        in = new ArrayList<>();
        in.add((double)1);
        in.add((double)1);
        System.out.println(nn.feedforward(in));
        in = new ArrayList<>();
        in.add((double)0);
        in.add((double)0);
        System.out.println(nn.feedforward(in));
        in = new ArrayList<>();
        in.add((double)1);
        in.add((double)0);
        System.out.println(nn.feedforward(in));
        in = new ArrayList<>();
        in.add((double)0);
        in.add((double)1);
        System.out.println(nn.feedforward(in));
    }

    public static int answer(int a, int b) {
        if (a == 0 && b == 0) return 0;
        if (a == 1 && b == 1) return 0;
        if (a == 0 && b == 1) return 1;
        if (a == 1 && b == 0) return 1;
        else return -1;
    }
}
