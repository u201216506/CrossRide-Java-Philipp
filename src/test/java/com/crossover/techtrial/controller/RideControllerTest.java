/**
 * 
 */
package com.crossover.techtrial.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;
import com.crossover.techtrial.util.CrossRideEnum;

/**
 * Unit tests for {@link RideController. Test cases for Ride api.}
 * 
 * @author PhILiPp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RideControllerTest {

  private static final int ERROR_REQUEST = 400;

private static final int SUCCESS = 200;

private static final int ERROR_NOT_FOUND = 404;

MockMvc mockMvc;
  
  @Mock
  private RideController rideController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  RideRepository rideRepository;

  @Autowired
  PersonRepository personRepository;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
    
    HttpEntity<Object> person = getHttpEntity(CrossRideEnum.DRIVER_100);
    template.postForEntity(CrossRideEnum.API_PERSON, person, Person.class);

    person = getHttpEntity(CrossRideEnum.RIDER_900);
    template.postForEntity(CrossRideEnum.API_PERSON, person, Person.class);

    HttpEntity<Object> ride = getHttpEntity(CrossRideEnum.RIDE_1000_OK);
	template.postForEntity(CrossRideEnum.API_RIDE, ride, Ride.class);
  }
  
  @Test
  public void testRideShouldBeRegistered() throws Exception {
    HttpEntity<Object> ride = getHttpEntity(CrossRideEnum.RIDE_100_OK);
    ResponseEntity<Ride> response = template.postForEntity(CrossRideEnum.API_RIDE, ride, Ride.class);
    Assert.assertEquals(SUCCESS,response.getStatusCode().value());
  }

  /**
   * Tests rides post api wont persist due the validation on endTime has to be greater than startTime.
   * @throws Exception
   */
  @Test
  public void testRideWithStartTimeGreaterThanEndTimeShouldFail() throws Exception {
    HttpEntity<Object> ride = getHttpEntity(CrossRideEnum.RIDE_100_WRONG_ENDTIME);
    ResponseEntity<Ride> response = template.postForEntity(CrossRideEnum.API_RIDE, ride, Ride.class);
    
    Assert.assertEquals(ERROR_REQUEST,response.getStatusCode().value());
  }

  /**
   * Tests rides post api wont persist due the driver referenced does not exists.
   * @throws Exception
   */
  @Test
  public void testRideWithInexistentDriverShouldFail() throws Exception {
    HttpEntity<Object> ride = getHttpEntity(CrossRideEnum.RIDE_200_WRONG_DRIVER_NOT_EXISTS);
    ResponseEntity<Ride> response = template.postForEntity(CrossRideEnum.API_RIDE, ride, Ride.class);
    
    Assert.assertEquals(ERROR_REQUEST,response.getStatusCode().value());
  }

  /**
   * Tests rides post api wont persist due the rider referenced does not exists.
   * @throws Exception
   */
  @Test
  public void testRideWithInexistentRiderShouldFail() throws Exception {
    HttpEntity<Object> ride = getHttpEntity(CrossRideEnum.RIDE_300_WRONG_RIDER_NOT_EXISTS);
    ResponseEntity<Ride> response = template.postForEntity(CrossRideEnum.API_RIDE, ride, Ride.class);
    
    Assert.assertEquals(ERROR_REQUEST,response.getStatusCode().value());
  }

  /**
   * Tests rides post api wont persist due null fields.
   * @throws Exception
   */
  @Test
  public void testRideWithNullFieldsShouldFail() throws Exception {
    HttpEntity<Object> ride = getHttpEntity(CrossRideEnum.RIDE_400_WRONG_NULL_FIELDS);
    ResponseEntity<Ride> response = template.postForEntity(CrossRideEnum.API_RIDE, ride, Ride.class);
    
    Assert.assertEquals(ERROR_REQUEST,response.getStatusCode().value());
  }

  /**
   * Tests get ride by id api.
   * @throws Exception
   */
  @Test
  public void testGetRideById() throws Exception {
	  
    ResponseEntity<Ride> response = template.getForEntity(CrossRideEnum.API_RIDE+"/1", Ride.class);
    
    Assert.assertEquals(SUCCESS,response.getStatusCode().value());
    Assert.assertEquals(1L,response.getBody().getDriver().getId().longValue());
    Assert.assertEquals(10L,response.getBody().getRider().getId().longValue());
  }

  /**
   * Tests get ride by id api is empty when ride doesnt exists.
   * @throws Exception
   */
  @Test
  public void testGetRideByIdReturnsNotFound() throws Exception {
    ResponseEntity<Ride> response = template.getForEntity(CrossRideEnum.API_RIDE+"/9999", Ride.class);
    
    Assert.assertEquals(ERROR_NOT_FOUND,response.getStatusCode().value());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

  @After
  public void after() throws Exception {
	// Delete drivers
	if (personRepository.findById(100L).isPresent()) {
		personRepository.deleteById(100L);
	}
	if (personRepository.findById(900L).isPresent()) {
		personRepository.deleteById(900L);
	}
	// Delete rides
	if (rideRepository.findById(100L).isPresent()) {
		rideRepository.deleteById(100L);
	}
	if (rideRepository.findById(1000L).isPresent()) {
		rideRepository.deleteById(1000L);
	}
  }

}
