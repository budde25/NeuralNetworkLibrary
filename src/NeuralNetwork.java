import java.util.ArrayList;
import java.util.function.Function;

public class NeuralNetwork {

    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;

    private double learningRate;

    private Matrix weightInputHidden;
    private Matrix weightHiddenOutput;

    private Matrix biasHidden;
    private Matrix biasOutput;

    private Sigmoid sigmoid;
    private DSigmoid dSigmoid;

    /**
     * Constructor
     * @param inputNodes number of inputs
     * @param hiddenNodes number of hidden nodes
     * @param outputNodes number of outputs
     */
    NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes) {
        this(inputNodes, hiddenNodes, outputNodes, 0.1);
    }

    /**
     * Constructor with learning rate
     * @param inputNodes number of inputs
     * @param hiddenNodes number of hidden nodes
     * @param outputNodes number of outputs
     * @param learningRate rate the network learns
     */
    NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, double learningRate) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.learningRate = learningRate;

        weightInputHidden = new Matrix(hiddenNodes, inputNodes);
        weightHiddenOutput = new Matrix(outputNodes, hiddenNodes);
        weightInputHidden.randomizeValues();
        weightHiddenOutput.randomizeValues();

        biasHidden = new Matrix(hiddenNodes, 1);
        biasOutput = new Matrix(outputNodes,1);
        biasHidden.randomizeValues();
        biasOutput.randomizeValues();

        sigmoid = new Sigmoid();
        dSigmoid = new DSigmoid();
    }

    /**
     * Simple feedforward algorithm for get outputs based on inputs
     * @param inputs a Matrix of input numbers
     * @return an ArrayList of output numbers
     */
    public Matrix feedforward(Matrix inputs) {
        return getOutput(getHidden(inputs));
    }

    public Matrix feedforward(ArrayList<Double> inputs) {
        return feedforward(Matrix.fromArray(inputs));
    }

    public Matrix getHidden(Matrix inputs){
        // Generate the hidden
        Matrix hidden = weightInputHidden.matrixProduct(inputs);
        hidden = hidden.add(biasHidden);
        // Activation
        return hidden.map(sigmoid);
    }

    public Matrix getOutput(Matrix hidden) {
        // Generate the output
        Matrix output = weightHiddenOutput.matrixProduct(hidden);
        output = output.add(biasOutput);
        // Activation
        return output.map(sigmoid);
    }

    public void train(Matrix inputs, Matrix targets) {
        Matrix hiddens = getHidden(inputs);
        Matrix outputs = getOutput(hiddens);

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
        weightHiddenOutput = weightHiddenOutput.add(weightHiddenOutputDeltas);

        // Adjusts the output bias's
        biasOutput = biasOutput.add(gradientsOutput);

        // Calculate the hidden errors
        Matrix weightHiddenOutputT = weightHiddenOutput.transpose();
        Matrix hiddenErrors = weightHiddenOutputT.matrixProduct(outputErrors);

        // Calculate hidden gradient
        Matrix gradientsHidden = hiddens.map(dSigmoid);
        gradientsHidden = gradientsHidden.multiply(hiddenErrors);
        gradientsHidden = gradientsHidden.multiply(learningRate);

        // Calculate input hidden deltas
        Matrix inputsT = inputs.transpose();
        Matrix weightInputHiddenDeltas = gradientsHidden.matrixProduct(inputsT);

        // Adjust the input hidden weights
        weightInputHidden = weightInputHidden.add(weightInputHiddenDeltas);

        // Adjusts the hidden bias's
        biasHidden = biasHidden.add(gradientsHidden);
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
