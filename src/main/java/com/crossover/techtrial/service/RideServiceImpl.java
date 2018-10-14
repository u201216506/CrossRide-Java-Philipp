/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Person;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.PageRequest;

/**
 * @author crossover
 *
 */
@Service
public class RideServiceImpl implements RideService{

  private final RideRepository rideRepository;
   
    /**
     * Autowired constructor
     *
     * @param rideRepository {@link RideRepository} repository     
     */
    @Autowired
    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
  
  public Ride save(Ride ride) {
    return rideRepository.save(ride);
  }
  
  public Ride findById(Long rideId) {
    Optional<Ride> optionalRide = rideRepository.findById(rideId);
    if (optionalRide.isPresent()) {
      return optionalRide.get();
    }else return null;
  }
  
  public List<TopDriverDTO> getTopDrivers(LocalDateTime startTime, LocalDateTime endTime, int count) {
        return rideRepository.findTopDrivers(startTime, endTime, PageRequest.of(0, count));
    }

}
