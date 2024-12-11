package tasks;

import common.Person;
import common.PersonService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  /* Comparator сортирует за O(n * log(n)). Предполагаю, что findPersons тоже работает не дольше, чем O(n * log(n))
  * (а скорее всего, за O(n)). Лист создается за линейное время.
  * Следовательно, время работы всей функции - O(n * log(n)). */
  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    List<Person> sortedPersons = new ArrayList<>(persons);
    sortedPersons.sort(Comparator.comparing(person -> personIds.indexOf(person.id())));
    return sortedPersons;
  }
}
