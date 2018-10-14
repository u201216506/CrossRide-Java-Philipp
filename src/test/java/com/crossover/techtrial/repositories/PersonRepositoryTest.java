package com.crossover.techtrial.repositories;

import com.crossover.techtrial.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PersonRepository personRepository;

    Long personID ;

    @Before
    public void insertCompany(){
        Person person = new Person();
        person.setEmail("Test@test.com");
        person.setName("mohame motyim");
        person.setRegistrationNumber("01114786614");
        person = entityManager.persist(person);
        personID = person.getId();
    }

    @Test
    public void testFindByID() {
        Optional<Person> personOpt = personRepository.findById(personID);
        assertTrue(personOpt.isPresent());
        Person person = personOpt.get();
        assertEquals(person.getName(),"mohame motyim");
        assertEquals(person.getRegistrationNumber(),"01114786614");
        assertEquals(person.getEmail(),"Test@test.com");
    }


    @Test
    public void testNotFindByID() {
        Optional<Person> personOpt = personRepository.findById(2L);
        assertFalse(personOpt.isPresent());
    }

}