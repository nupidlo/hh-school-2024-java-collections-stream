package tasks;

import common.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    // Можно просто скипнуть первый элемент, на пустом стриме это тоже будет работать
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    // distinct() можно удалить, сет и так состоит из уникальных элементов. Отпадает необходимость в стриме
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // Склеивать стринги через += - дорого и вообще ужасно.
    // Используем стрим, чтобы не проверять руками каждое поле на null, да и собирать строку так гораздо удобнее
    return Stream.of(person.secondName(), person.firstName(), person.middleName())
        .filter(Objects::nonNull)
        .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Можно написать в одну строчку
    return persons.stream().distinct().collect(Collectors.toMap(Person::id, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    HashSet<Person> personSet1 = new HashSet<>(persons1);
    HashSet<Person> personSet2 = new HashSet<>(persons2);
    return !Collections.disjoint(personSet1, personSet2);
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Просто count()
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    /* Очень интересный вопрос. Я провел небольшое исследование, и вот, что выяснилось:
    * 1. Для оптимальной работы сета объекты, хранящиеся в нем, должны занимать не более 80% от его емкости.
    *    Java следит за этим, поэтому количество бакетов в инициализируемом сете точно не меньше,
    *    чем количество передаваемых в сет объектов.
    * 2. Согласно исходному коду, Integer::hashCode в качестве хэша возвращает значение самого числа.
    * 3. Метод toString выдает элементы по итератору.
    * 4. Итератор сета выдает объекты по возрастанию номеров бакетов.
    * 5. Номер бакета, в котором хранится объект в сете, соответствует некоторому числу последних битов хэша объекта.
    *    В нашем случае - хэшу целиком.
    * Суммируя все вышесказанное, получается, что когда сет создается, объектами он заполняется в порядке,
    * заданном в передаваемой коллекции. Но когда мы итерируем по сету, объекты выдаются в порядке бакетов, по которым
    * они лежат. И поскольку номер бакета == хэшу == числу в бакете, элементы выводятся в отсортированном по возрастанию
    * порядке. */
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
