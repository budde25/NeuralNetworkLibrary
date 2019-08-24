public class GeneticNetwork extends NeuralNetwork{

    private static int idGen = 0;

    final private int id;
    final private int generation;
    final private double mutationRate;
    private int fitness;
    private boolean alive;

    public GeneticNetwork(int inputNodes, int[] hiddenNodes, int outputNodes, double learningRate, int generation, double mutationRate) {
        super(inputNodes, hiddenNodes, outputNodes, learningRate);
        if (mutationRate >= 0.0 && mutationRate <= 1.0){
            this.mutationRate = mutationRate;
        } else {
            this.mutationRate = 0.0;
        }

        this.generation = generation;
        this.id = idGen;
        this.alive = true;
        idGen++;
    }

    public GeneticNetwork(int inputNodes, int hiddenNodes, int outputNodes, double learningRate, int generation, double mutationRate) {
        this(inputNodes, new int[] {hiddenNodes}, outputNodes, learningRate, generation, mutationRate);
    }

    //public GeneticNetwork Breed(GeneticNetwork other){
     //   GeneticNetwork geneticNetwork = new GeneticNetwork(this.)
    // }

    public void kill(int fitness){
        this.fitness = fitness;
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getId() {
        return id;
    }

    public int getFitness() {
        return fitness;
    }

    public int getGeneration() {
        return generation;
    }
}
