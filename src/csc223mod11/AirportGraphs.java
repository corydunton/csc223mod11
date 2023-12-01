package csc223mod11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class AirportGraphs {

	public static void main(String[] args) {

		Graph graph = loadDataFromFile("AdjacencyList.txt");
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("\nAirport Graph Application");
			System.out.println("1. Summarize Airport Dataset");
			System.out.println("2. Calculate Flight Path");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");

			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				summarizeDataset(graph);
				break;
			case 2:
				calculateFlightPath();
				break;
			case 3:
				System.out.println("Exiting application.");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void summarizeDataset(Graph graph) {
		
		// Assuming methods in Graph to get number of airports and routes
		int numberOfAirports = graph.getAirports().size();
		int numberOfRoutes = graph.getAllRoutes().size();
		System.out.println("Number of airports: " + numberOfAirports);
		System.out.println("Number of direct flights: " + numberOfRoutes);
	}

	private static void calculateFlightPath(Graph graph) {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter First Airport code: ");
		String start = scan.next();
		System.out.print("Enter Second Airport code: ");
		String end = scan.next();

		List<Route> path = graph.findShortestPath(start, end);
		if (path.isEmpty()) {
			System.out.println("No path available between " + start + " and " + end);
			return;
		}

		System.out.print("The flight path is: ");
		int totalDistance = 0;
		for (Route route : path) {
			System.out.print(route.getSource().getCode() + " - " + route.getDestination().getCode() + " ");
			totalDistance += route.getDistance();
		}
		System.out.println("\nThe total flight distance is " + totalDistance + " miles.");
	}

	public static Graph loadDataFromFile(String filename) {

		Graph graph = new Graph();

		try {
			List<String> lines = Files.readAllLines(Paths.get(filename));

			for (String line : lines) {
				String[] parts = line.split(": ");
				String sourceCode = parts[0];
				graph.addAirport(sourceCode);

				if (parts.length > 1) {
					String[] connections = parts[1].split(", ");
					for (String connection : connections) {
						String[] routeParts = connection.split("-");
						String destinationCode = routeParts[0];
						int distance = Integer.parseInt(routeParts[1]);
						graph.addRoute(sourceCode, destinationCode, distance);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return graph;
	}

}
