package csc223mod11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class AirportGraphs {

	public static void main(String[] args) {

		Graph graph = loadDataFromFile("Airport_AdjacencyList.txt");
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
				calculateFlightPath(graph);
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
		
		// check getAllRoutes
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
			System.out.print(route.getSource() + " - " + route.getDestination() + " ");
			totalDistance += route.getDistance();
		}
		System.out.println("\nThe total flight distance is " + totalDistance + " miles.");
	}

	public static Graph loadDataFromFile(String filename) {

		Graph graph = new Graph();
		
		URL url = AirportGraphs.class.getResource(filename);
		if (url != null) {
		    Path path;
			try {
				path = Paths.get(url.toURI());
			    List<String> lines = Files.readAllLines(path);
			    // Process lines
		        for (String line : lines) {
		            // Process the line
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
			} catch (URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
//		if (inputStream != null) {
//		    // Read the file using the inputStream
//		    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//		        String line;
//		        while ((line = reader.readLine()) != null) {
//		            // Process the line
//					String[] parts = line.split(": ");
//					String sourceCode = parts[0];
//					graph.addAirport(sourceCode);
//
//					if (parts.length > 1) {
//						String[] connections = parts[1].split(", ");
//						for (String connection : connections) {
//							String[] routeParts = connection.split("-");
//							String destinationCode = routeParts[0];
//							int distance = Integer.parseInt(routeParts[1]);
//							graph.addRoute(sourceCode, destinationCode, distance);
//						}
//					}
//		        }
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//		}
		
		return graph;
	}
}
