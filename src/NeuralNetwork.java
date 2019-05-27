import java.util.ArrayList;
import java.util.function.Function;

public class NeuralNetwork {

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
        int size = hiddenNodes.length + 2;

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
     * @param inputs a Matrix of input numbers
     * @return an ArrayList of output numbers
     */
    public Matrix feedforward(Matrix inputs) {
        return backpropogate(backpropogate(inputs, weights[0], bias[0]), weights[1], bias[1]);
    }

    public Matrix feedforward(ArrayList<Double> inputs) {
        return feedforward(Matrix.fromArray(inputs));
    }

    /*public Matrix getHidden(Matrix inputs){
        // Generate the hidden
        Matrix hidden = weightInputHidden.matrixProduct(inputs);
        hidden = hidden.add(biasHidden);
        // Activation
        return hidden.map(sigmoid);
    }*/

    /*public Matrix getOutput(Matrix hidden){
        // Generate the output
        Matrix output = weightHiddenOutput.matrixProduct(hidden);
        output = output.add(biasOutput);
        // Activation
        return output.map(sigmoid);
    }*/

    public Matrix backpropogate(Matrix inputs, Matrix weights, Matrix bias) {
        //Generate the output
        Matrix output = weights.matrixProduct(inputs);
        output = output.add(bias);
        // Activation
        return output.map(sigmoid);
    }

    /*public void train(Matrix inputs, Matrix targets) {

        }
    }*/

    public void train(Matrix inputs, Matrix targets) {
        Matrix hiddens = backpropogate(inputs, weights[0], bias[0]);
        Matrix outputs = backpropogate(hiddens, weights[1], bias[1]);

        // Error = targets - outputs
        // Calculate the output errors
        Matrix outputErrors = targets.subtract(outputs);

        // Gradient  = outputs * ( 1- outputs)
        // Calculate output gradient
        Matrix gradientsOutput = outputs.map(dSigmoid);
        gradientsOutput = gradientsOutput.multiply(outputErrors);
        gradientsOutput = gradientsOutput.multiply(learningRate);

        // Calculate hidden output deltas
        Matrix hiddensT = hiddens.transpose();
        Matrix weightHiddenOutputDeltas = gradientsOutput.matrixProduct(hiddensT);

        // Adjust the hidden output weights
        weights[1] = weights[1].add(weightHiddenOutputDeltas);

        // Adjusts the output bias's
        bias[1] = bias[1].add(gradientsOutput);

        // Calculate the hidden errors
        Matrix weightHiddenOutputT = weights[1].transpose();
        Matrix hiddenErrors = weightHiddenOutputT.matrixProduct(outputErrors);

        // Calculate hidden gradient
        Matrix gradientsHidden = hiddens.map(dSigmoid);
        gradientsHidden = gradientsHidden.multiply(hiddenErrors);
        gradientsHidden = gradientsHidden.multiply(learningRate);

        // Calculate input hidden deltas
        Matrix inputsT = inputs.transpose();
        Matrix weightInputHiddenDeltas = gradientsHidden.matrixProduct(inputsT);

        // Adjust the input hidden weights
        weights[0] = weights[0].add(weightInputHiddenDeltas);

        // Adjusts the hidden bias's
        bias[0] = bias[0].add(gradientsHidden);
    }


    public void train(ArrayList<Double> inputs, ArrayList<Double> targets) {
        train(Matrix.fromArray(inputs), Matrix.fromArray(targets));
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
