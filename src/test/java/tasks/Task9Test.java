package tasks;

import common.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task9Test {

  private Person person1;
  private Person person2;
  private Person person3;
  private Person person4;
  private Person person5;

  private Task9 task;

  @BeforeEach
  void before() {
    Instant time = Instant.now();
    person1 = new Person(1, "Oleg", "Ivanov", null, time);
    person2 = new Person(2, "Vasya", "Petrov", null, time);
    person3 = new Person(3, "Oleg", "Petrov", null, time.plusSeconds(1));
    person4 = new Person(4, "Oleg", "Ivanov", null, time.plusSeconds(1));
    person5 = new Person(5, "Victor", "Victorov", "Victorovich", time);

    task = new Task9();
  }

  @Test
  public void getNamesTest() {
    assertEquals(List.of("Vasya", "Oleg", "Oleg"), task.getNames(List.of(person1, person2, person3, person4)));
    assertEquals(Collections.emptyList(), task.getNames(List.of(person3)));
  }

  @Test
  public void getNamesTestEmpty() {
    assertEquals(Collections.emptyList(), task.getNames(Collections.emptyList()));
  }

  @Test
  public void convertPersonToStringTest() {
    assertEquals("Victorov Victor Victorovich", task.convertPersonToString(person5));
  }

  @Test
  public void convertPersonToStringTestEmpty() {
    assertEquals("Ivanov Oleg", task.convertPersonToString(person1));
  }

  @Test
  public void getPersonNamesTest() {
    assertEquals(
        Map.of(2, "Petrov Vasya", 4, "Ivanov Oleg"),
        task.getPersonNames(List.of(person2, person4))
    );
  }

  @Test
  public void getPersonNamesTestEmpty() {
    assertEquals(
        Collections.emptyMap(),
        task.getPersonNames(Collections.emptyList())
    );
  }

  @Test
  public void getPersonNamesTestDuplicates() {
    assertEquals(
        Map.of(2, "Petrov Vasya"),
        task.getPersonNames(List.of(person2, person2))
    );
  }

  @Test
  public void hasSamePersonsTest() {
    assertTrue(task.hasSamePersons(List.of(person1, person2), List.of(person2, person3)));
    assertFalse(task.hasSamePersons(List.of(person1, person2), List.of(person4, person3)));
  }

  @Test
  public void countEvenTest() {
    assertEquals(3, task.countEven(Stream.of(1, 2, 3, 4, 5, 6)));
    assertEquals(0, task.countEven(Stream.of(1, 3, 5, -1)));
  }
}
