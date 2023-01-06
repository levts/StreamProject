package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        Long countUnderAge;
        List<Person> workers = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        countUnderAge = persons.stream()                                    //Считаем количество несовершеннолетних
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println(countUnderAge);

        List<String> recruitList = persons.stream()                                      //Получаем список призывников
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 27)
                .map(Person::getFamily)
                .toList();

        List<Person> workersLambda = new ArrayList<>(persons.stream()       //если использовать лямбду
                .filter(person -> person.getAge() >= 18)
                .filter(person -> {
                    if ((person.getSex() == Sex.MAN) && (person.getAge() <= 65)) {
                        return true;
                    }
                    return (person.getSex() == Sex.WOMAN) && (person.getAge() <= 60);
                })
                .filter(person -> person.getEducation() == Education.HIGHER)
                .toList());


        workers.addAll(persons.stream()                                      //если не использовать лямбду
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() <= 65)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .toList());
        workers.addAll(persons.stream()
                .filter(person -> person.getSex() == Sex.WOMAN)
                .filter(person -> person.getAge() <= 60)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .toList());
        workers = workers.stream()
                .filter(person -> person.getAge() >= 18)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
    }
}