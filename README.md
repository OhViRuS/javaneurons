# Iris Flower Classification — Neural Network in Java

A university project implementing a feedforward neural network from scratch in Java to classify Iris flowers into three species using a custom evolutionary algorithm.

## Features

- Three-layer neural network with a **4 → 6 → 1** architecture.
- Sigmoid activation in the hidden layer and linear activation in the output layer.
- Min-Max normalization of input features.
- Custom evolutionary optimization using elitism, parent selection, and Gaussian mutation.
- Adaptive mutation rate.
- Mean Squared Error (MSE) as the fitness function.
- Classification accuracy evaluation.

## Technologies

**Java** · Neural Networks · Machine Learning · Evolutionary Algorithms · CSV Data Processing

## Dataset

The model uses the Iris dataset, containing 150 samples with four input features:

- Sepal length
- Sepal width
- Petal length
- Petal width

The network classifies flowers into three species: Iris-setosa, Iris-versicolor, and Iris-virginica.

## Training Configuration

| Parameter | Value |
|---|---:|
| Population size | 500 |
| Generations | 1,000 |
| Hidden-layer neurons | 6 |
| Elitism | Top 10% |
| Initial mutation rate | 0.35 |
| Final mutation rate | Approximately 0.10 |
| Fitness function | Mean Squared Error (MSE) |

## Documentation

See the [project report (PDF)](Neuroninis_tinklas.pdf) for details about the neural network architecture, data preprocessing, evolutionary algorithm, experimental results, and conclusions.

## Author

[OhViRuS](https://github.com/OhViRuS)
