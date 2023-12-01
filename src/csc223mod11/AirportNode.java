package csc223mod11;

public class AirportNode {
	
	 private String airportCode;
     private int distance;

     public AirportNode(String airportCode, int distance) {
         this.airportCode = airportCode;
         this.distance = distance;
     }

     public String getAirportCode() {
         return airportCode;
     }

     public int getDistance() {
         return distance;
     }
}
