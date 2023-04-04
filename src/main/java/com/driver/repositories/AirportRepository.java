package com.driver.repositories;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    public HashMap<String, Airport> airportDb = new HashMap<>();

    public HashMap<Integer, Flight> flightDb = new HashMap<>();

    public HashMap<Integer, List<Integer>> flightToPassengerDb = new HashMap<>();

    public HashMap<Integer, Passenger> passengerDb = new HashMap<>();

    public AirportRepository() {

        this.airportDb = new HashMap<String, Airport>();
        this.flightDb = new HashMap<Integer, Flight>();
        this.flightToPassengerDb = new HashMap<Integer, List<Integer>>();
        this.passengerDb = new HashMap<Integer, Passenger>();
    }

    public String newAirport(Airport airport){

        airportDb.put(airport.getAirportName(), airport);
        return "SUCCESS";
    }

    public String largestAirport(){

        String ans = "";
        int terminals = 0;

        for(Airport airport : airportDb.values()){

            if(airport.getNoOfTerminals()>terminals){
                ans = airport.getAirportName();
                terminals = airport.getNoOfTerminals();
            }else if(airport.getNoOfTerminals()==terminals){
                if(airport.getAirportName().compareTo(ans)<0){
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }

    public double shortestDuration(City fromCity, City toCity){

        double distance = 1000000000;

        for(Flight flight:flightDb.values()){
            if((flight.getFromCity().equals(fromCity))&&(flight.getToCity().equals(toCity))){
                distance = Math.min(distance,flight.getDuration());
            }
        }

        if(distance==1000000000){
            return -1;
        }
        return distance;
    }

    public int numberOfPeople(Date date, String airportName){

        Airport airport = airportDb.get(airportName);
        if(Objects.isNull(airport)){
            return 0;
        }
        City city = airport.getCity();
        int count = 0;

        for(Flight flight:flightDb.values()){
            if(date.equals(flight.getFlightDate()))
                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){

                    int flightId = flight.getFlightId();
                    count = count + flightToPassengerDb.get(flightId).size();
                }
        }
        return count;
    }

    public int fare(Integer flightId){

        int noOfPeopleBooked = flightToPassengerDb.get(flightId).size();
        return noOfPeopleBooked*50 + 3000;
    }

    public String bookTicket(Integer flightId, Integer passengerId){

        if(Objects.nonNull(flightToPassengerDb.get(flightId)) &&(flightToPassengerDb.get(flightId).size()<flightDb.get(flightId).getMaxCapacity())){

            List<Integer> passengers =  flightToPassengerDb.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }
            passengers.add(passengerId);
            flightToPassengerDb.put(flightId,passengers);
            return "SUCCESS";
        }
        else if(Objects.isNull(flightToPassengerDb.get(flightId))){
            flightToPassengerDb.put(flightId,new ArrayList<>());
            List<Integer> passengers =  flightToPassengerDb.get(flightId);

            if(passengers.contains(passengerId)){
                return "FAILURE";
            }

            passengers.add(passengerId);
            flightToPassengerDb.put(flightId,passengers);
            return "SUCCESS";

        }
        return "FAILURE";
    }

    public String cancelTicket(Integer flightId, Integer passengerId){

        List<Integer> passengers = flightToPassengerDb.get(flightId);

        if(passengers == null){
            return "FAILURE";
        }

        if(passengers.contains(passengerId)){
            passengers.remove(passengerId);
            return "SUCCESS";
        }
        return "FAILURE";
    }

    public int totalBookings(Integer passengerId){

        //We have a list from passenger To flights database:-
        int count = 0;
        for(Map.Entry<Integer,List<Integer>> entry: flightToPassengerDb.entrySet()){

            List<Integer> passengers  = entry.getValue();
            for(Integer passenger : passengers){
                if(passenger==passengerId){
                    count++;
                }
            }
        }
        return count;
    }

    public String newFlight(Flight flight){

        flightDb.put(flight.getFlightId(),flight);
        return "SUCCESS";
    }

    public String airportName(Integer flightId){
        if(flightDb.containsKey(flightId)){
            City city = flightDb.get(flightId).getFromCity();
            for(Airport airport:airportDb.values()){
                if(airport.getCity().equals(city)){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int revenue(Integer flightId){

        int noOfPeopleBooked = flightToPassengerDb.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }

    public String newPassenger(Passenger passenger){

        passengerDb.put(passenger.getPassengerId(),passenger);
        return null;
    }
}
