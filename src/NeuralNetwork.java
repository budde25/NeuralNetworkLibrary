import java.util.ArrayList;
import java.util.function.Function;

public class NeuralNetwork {

    // The amount of nodes columns
    private int size;

    // Array with the size of of each column of the neural network
    // Array size should be the amount of columns (min 3)
    // Array at index 0 is number of inputs, index size - 1 is number of outputs
    // Array at any other index is a hidden node
    private int[] nodes;

    // The rate at which the network learns
    private double learningRate;

    // Array with the weights between each column of the neural network
    // Array size should be the amount amount of columns - 1
    // Array at index 0 is input to hidden, index size - 1 hidden to output
    private Matrix[] weights;

    // Array with the bias for each column of the neural network expect the inputs
    // Array size should be the amount amount of columns - 1
    // Array at index 0 is hidden, index size - 1 is output
    private Matrix[] bias;

    private static Sigmoid sigmoid = new Sigmoid();
    private static DSigmoid dSigmoid = new DSigmoid();

    /**
     * Constructor
     * @param inputNodes number of inputs
     * @param hiddenNodes number of hidden nodes
     * @param outputNodes number of outputs
     */
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this(inputNodes, hiddenNodes, outputNodes, 0.1);
    }

    /**
     * Constructor with learning rate
     * @param inputNodes number of inputs
     * @param hiddenNodes number of hidden nodes
     * @param outputNodes number of outputs
     * @param learningRate rate the network learns
     */
    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, double learningRate) {
        this(inputNodes, new int[] {hiddenNodes}, outputNodes, learningRate);
    }

    /**
     * Constructor with learning rate
     * @param inputNodes number of inputs
     * @param hiddenNodes number of hidden nodes and amount of hidden columns
     * @param outputNodes number of outputs
     * @param learningRate rate the network learns
     */
    public NeuralNetwork(int inputNodes, int[] hiddenNodes, int outputNodes, double learningRate) {
        // size of neural network
        size = hiddenNodes.length + 2;

        // initialize the nodes
        nodes = new int[size];
        nodes[0] = inputNodes;
        for (int i = 1; i < size - 1; i++) {
            nodes[i] = hiddenNodes[i - 1];
        }
        nodes[size - 1] = outputNodes;

        // initialize the weights
        weights = new Matrix[size - 1];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Matrix(nodes[i + 1], nodes[i]);
            weights[i].randomizeValues();
        }

        // initialize the bias
        bias = new Matrix[size - 1];
        for (int i = 0; i < bias.length; i++) {
            bias[i] = new Matrix(nodes[i + 1], 1);
            bias[i].randomizeValues();
        }

        this.learningRate = learningRate;
    }

    /**
     * Simple feedforward algorithm for get outputs based on inputs
     * @param inputs a Matrix of input doubles
     * @return an Matrix of output doubles
     */
    public Matrix feedforward(Matrix inputs) {
        Matrix output = null;
        for (int i = 0; i < size - 1; i++) {
            if (i == 0) {
                output = backpropogate(inputs, weights[0], bias[0]);
            } else {
                output = backpropogate(output, weights[i], bias[i]);
            }
        }
        return output;
    }

    /**
     * Simple feedforward algorithm for get outputs based on inputs
     * @param inputs a ArrayList of input doubles
     * @return a Matrix of output doubles
     */
    public Matrix feedforward(ArrayList<Double> inputs) {
        return feedforward(Matrix.fromArray(inputs));
    }

    public Matrix backpropogate(Matrix inputs, Matrix weights, Matrix bias) {
        //Generate the output
        Matrix output = weights.matrixProduct(inputs);
        output = output.add(bias);
        // Activation
        return output.map(sigmoid);
    }

    /**
     * Trains the network given a Matrix of inputs and a Matrix of target Values
     * @param inputs a Matrix of inputs
     * @param targets a Matrix of correct answers
     */
    public void train(Matrix inputs, Matrix targets) {
        // Array with all the networks values starting at hidden 1 though the output
        Matrix[] values = new Matrix[size - 1];
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                values[0] = backpropogate(inputs, weights[0], bias[0]);
            } else {
                values[i] = backpropogate(values[i - 1], weights[i], bias[i]);
            }
        }

        // Main training loop
        Matrix previousError = null;
        for (int i = 0; i < values.length; i++) {
            // Error = target - output, then weight matrix product previous error
            // i = 0 = output error, i = 1 = highest hidden error, etc
            Matrix error;
            if (i == 0) {
                error = previousError = targets.subtract(values[values.length - 1]);
            } else {
                Matrix weight = weights[weights.length - i].transpose();
                error = weight.matrixProduct(previousError);
                previousError = error;
            }

            // gradient = (Error * ( 1 - node)) * learning rate
            // i = 0 = output, i = 1 = highest hidden, etc
            Matrix gradient = values[values.length - 1 - i].map(dSigmoid);
            gradient = gradient.multiply(error);
            gradient = gradient.multiply(learningRate);

            // delta = gradient matrix product transposed node
            // i = 0 = highest hidden to output, i = 1 = second highest hidden to highest hidden
            Matrix transposed;
            if (i == values.length - 1) {
                transposed = inputs.transpose();
            } else {
                transposed = values[values.length - 2 - i].transpose();
            }
            Matrix delta = gradient.matrixProduct(transposed);


            // Adjust the weights and the bias
            weights[weights.length - 1 - i] = weights[weights.length - 1 - i].add(delta);
            bias[bias.length - 1 - i] = bias[weights.length - 1 - i].add(gradient);
        }
    }

    /**
     * Trains the network given a ArrayList double of inputs and answers
     * @param inputs the double inputs
     * @param targets the correct values
     */
    public void trainDouble(ArrayList<Double> inputs, ArrayList<Double> targets) {
        train(Matrix.fromArray(inputs), Matrix.fromArray(targets));
    }

    /**
     * Trains the network given a ArrayList integer of inputs and answers
     * @param inputs the integer inputs
     * @param targets the correct values
     */
    public void trainInteger(ArrayList<Integer> inputs, ArrayList<Integer> targets) {
        ArrayList<Double> input = new ArrayList<>(inputs.size());
        ArrayList<Double> target = new ArrayList<>(targets.size());

        for (int i = 0; i < inputs.size(); i++) {
            input.add((double)inputs.get(i));
        }
        for (int i = 0; i < targets.size(); i++) {
            target.add((double)targets.get(i));
        }

        train(Matrix.fromArray(input), Matrix.fromArray(target));
    }

}

/**
 * Send in the Sigmoid function to normalize the data
 */
class Sigmoid implements Function<Double, Double> {
    @Override
    public Double apply(Double x) {
        return 1 / (1 + Math.exp(-x));
    }
}

/**
 * Gets the derivative of any already sigmoid data
 */
class DSigmoid implements Function<Double, Double> {
    @Override
    public Double apply(Double x) {
        return x * (1 - x);
    }
}
