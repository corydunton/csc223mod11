package csc223mod11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

        routesBySourceAirportCode.get(sourceCode).add(route);
        // For undirected graph, add reverse route as well
        routesBySourceAirportCode.get(destCode).add(new Route(destCode, sourceCode, distance));
    }
    
    public List<Route> findShortestPath(String startCode, String endCode) {
        // Initialize distances and previous nodes
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
        
        for (String airport : airports.values()) {
            distances.put(airport, Integer.MAX_VALUE);
            previous.put(airport, null);
            queue.add(airport);
        }

        distances.put(airports.get(startCode), 0);

        while (!queue.isEmpty()) {
            Airport current = queue.poll();

            for (Route route : adjList.get(current.getCode())) {
                Airport neighbor = route.getDestination();
                int distanceThroughCurrent = distances.get(current) + route.getDistance();

                if (distanceThroughCurrent < distances.get(neighbor)) {
                    distances.put(neighbor, distanceThroughCurrent);
                    previous.put(neighbor, current);
                    // Update queue
                }
            }
        }

        // Reconstruct path from startCode to endCode using 'previous'
        // Return the list of routes representing the shortest path
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
    	
//    	return airports;
    }
}
