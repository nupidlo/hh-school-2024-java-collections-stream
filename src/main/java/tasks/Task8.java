package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    // Словарь для быстрого поиска персоны по ее id
    Map<Integer, Person> personsById = persons.stream()
        .collect(Collectors.toMap(Person::id, Function.identity()));

    // Получаем сет резюме для всех персон
    Set<Resume> resumes = personService.findResumes(persons.stream().map(Person::id).toList());

    // Группируем резюме по id персоны
    Map<Integer, List<Resume>> personResumes = resumes.stream()
        .collect(Collectors.groupingBy(Resume::personId));
    // Добавляем в словарь персон без резюме
    for (Integer id : personsById.keySet()) {
      if (!personResumes.containsKey(id)) {
        personResumes.put(id, Collections.emptyList());
      }
    }

    // Делаем сет требуемых объектов из словаря
    return personResumes.entrySet().stream()
        .map(entry -> new PersonWithResumes(
            personsById.get(entry.getKey()),
            new HashSet<>(entry.getValue())))
        .collect(Collectors.toSet());
  }
}
