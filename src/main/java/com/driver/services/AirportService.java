package com.driver.services;

import com.driver.repositories.AirportRepository;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport){
        airportRepository.newAirport(airport);
    }

    public String getLargestAirportName(){
        return airportRepository.largestAirport();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        return airportRepository.shortestDuration(fromCity, toCity);
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        return airportRepository.numberOfPeople(date, airportName);
    }

    public int calculateFlightFare(Integer flightId){
        return airportRepository.fare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId){
        return airportRepository.bookTicket(flightId, passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId){
        return airportRepository.cancelTicket(flightId, passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return airportRepository.totalBookings(passengerId);
    }

    public String addFlight(Flight flight){
        return airportRepository.newFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        return airportRepository.airportName(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        return airportRepository.revenue(flightId);
    }

    public String addPassenger(Passenger passenger){
        return airportRepository.newPassenger(passenger);
    }

}
