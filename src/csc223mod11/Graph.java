package csc223mod11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Graph {
	private List<String> airports;
	private Map<String, List<Route>> routesBySourceAirportCode;

	public Graph() {
		airports = new ArrayList<>();
		routesBySourceAirportCode = new HashMap<>();
	}

	public void addAirport(String airportCode) {
		airports.add(airportCode);
		routesBySourceAirportCode.putIfAbsent(airportCode, new ArrayList<>());
	}

	public void addRoute(String sourceCode, String destCode, int distance) {
		Route route = new Route(sourceCode, destCode, distance);
		routesBySourceAirportCode.put(sourceCode, new ArrayList<>());
		routesBySourceAirportCode.put(destCode, new ArrayList<>());
		routesBySourceAirportCode.get(sourceCode).add(route);
		// For undirected graph, add reverse route as well
		routesBySourceAirportCode.get(destCode).add(new Route(destCode, sourceCode, distance));
	}

	public List<Route> findShortestPath(String startCode, String endCode) {
		Map<String, Integer> distances = new HashMap<>();
		Map<String, Route> previous = new HashMap<>();
		PriorityQueue<Route> queue = new PriorityQueue<>(Comparator.comparing(Route::getDistance));

		for (String airport : airports) {
			distances.put(airport, Integer.MAX_VALUE);
		}
		distances.put(startCode, 0);
		queue.add(new Route(startCode, startCode, 0));

		while (!queue.isEmpty()) {
			Route current = queue.poll();
			String currentAirport = current.getDestination();

			if (currentAirport.equals(endCode)) {
				break;
			}

			for (Route neighbor : routesBySourceAirportCode.getOrDefault(currentAirport, new ArrayList<>())) {
				int altDistance = distances.get(currentAirport) + neighbor.getDistance();
				if (altDistance < distances.get(neighbor.getDestination())) {
					distances.put(neighbor.getDestination(), altDistance);
					previous.put(neighbor.getDestination(),
							new Route(currentAirport, neighbor.getDestination(), altDistance));
					queue.add(new Route(currentAirport, neighbor.getDestination(), altDistance));
				}
			}
		}

		return buildPath(previous, endCode);
	}

	private List<Route> buildPath(Map<String, Route> previous, String endCode) {
		LinkedList<Route> path = new LinkedList<>();
		Route step = previous.get(endCode);

		// Check if a path exists
		if (step == null) {
			return path; // No path
		}
		path.add(step);
		while (previous.containsKey(step.getSource())) {
			step = previous.get(step.getSource());
			path.add(step);
		}
		Collections.reverse(path);
		return path;
	}

	public Map<String, List<Route>> getRoutesBySourceAirportCode() {
		return routesBySourceAirportCode;
	}

	public List<String> getAirports() {
		return airports;
	}

	public List<Route> getAllRoutes() {

		List<Route> allRoutes = new ArrayList<>();

		for (String airportCode : routesBySourceAirportCode.keySet()) {
			List<Route> routes = routesBySourceAirportCode.get(airportCode);
			routes.addAll(routes);
		}

		return allRoutes;
	}
}
