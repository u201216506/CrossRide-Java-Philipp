/**
 * 
 */
package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Ride;
import java.util.List;
import java.time.LocalDateTime;

/**
 * RideService for rides.
 * @author crossover
 *
 */
public interface RideService {
  
  public Ride save(Ride ride);
  
  public Ride findById(Long rideId);
  
  List<TopDriverDTO> getTopDrivers(LocalDateTime startTime, LocalDateTime endTime, int count);
}
