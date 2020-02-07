# Neural Network Library
A custom Java library for building simple neural networks. It can uses for suppurvied learning and a feed forward algorithm.
Supports custom ranges of inputs, hidden nodes, and outputs with options to change the learning rate. It uses sigmoid as its
activation function.

## Installation

Add the provided jar as a library.  
[NeuralHelper.jar](https://github.com/budde25/NeuralNetworkLibrary/releases/latest)

## Usage
`NeuralNetwork(int inputs, int[] hidden nodes, int outputs, double learningRate)`  
int input nodes is the amount of inputs for the neural network    
int hidden nodes is an array of ints containing the amount of layers and hidden nodes for the neural network  
int output nodes is the amount of outputs for the neural network  
double learning rate the seed at which the network learns (optional, default 0.1)  

`feedforward(double[] data)` gets the output for a given input  
`train(double[] data)` trains the network with the given input  

## Author
Ethan Budd

## Acknowledgments
* Daniel Shiffman [The Nature of Code](https://natureofcode.com/book/chapter-10-neural-networks/)
