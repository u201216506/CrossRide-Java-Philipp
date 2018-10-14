package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository ;

    @Autowired
    @InjectMocks
    PersonService personService = new PersonServiceImpl(personRepository);

    //-------------------------------- Test FindByID -------------------------------------
    @Test
    public void TestNotExsistReturnNull() {
        when(personRepository.findById(2l)).thenReturn(Optional.empty());
        assertNull(personService.findById(2l));
    }

    @Test
    public void TestExsistReturnPerson() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(new Person()));
        Person person = personService.findById(3L);
        assertNotNull(person);
    }

    //-------------------------------- Test GetAll -------------------------------------
    @Test
    public void testGetAllEmpty(){
        when(personRepository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0,personService.getAll().size());
    }

    @Test
    public void testGetAllHasElements(){
        List list = Arrays.asList(new Person(),new Person(),new Person());
        when(personRepository.findAll()).thenReturn(list);
        assertEquals(3,personService.getAll().size());
    }

    //-------------------------------- Test Save -------------------------------------
    @Test
    public void testSavePerson(){
        Person person = new Person();
        person.setEmail("Test@test.com");
        person.setName("Jose");
        person.setRegistrationNumber("01114786614");

        when(personRepository.save(person)).thenReturn(person);
        assertEquals(person,personService.save(person));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSavePersonWithoutEmail(){
        Person person = new Person();
        person.setName("Cindy");
        person.setRegistrationNumber("01114786614");
        when(personRepository.save(person)).thenThrow(new ConstraintViolationException(null));
        personService.save(person);
    }

}