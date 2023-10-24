package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.security.KeyStore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
//        task16();
//        task17();
//        task18();
//        task19();
        task20();
        task21();
        task22();
    }

    // TODO подумать еще...
    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .map(animal -> {
                    if (animal.getGender().equals("Female")) {
                        return animal.getBread().toUpperCase();
                    }
                    return animal.getBread();
                })
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30 && animal.getOrigin().startsWith("A"))
                .map(Animal::getOrigin)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count());
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .anyMatch(animal -> animal.getAge() >= 20
                        && animal.getAge() <= 30
                        && animal.getOrigin().equals("Hungarian")));
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().allMatch(animal -> animal.getGender().equals("Male") || animal.getGender().equals("Female")));
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream().anyMatch(animal -> animal.getOrigin().equals("Oceania")));
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparingInt(Animal::getAge))
                .ifPresent(animal -> System.out.println(animal.getAge()));

    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparingInt(c -> c.length))
                .ifPresent(chars -> System.out.println(chars.length));
        ;
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .mapToInt(Animal::getAge)
                .sum());
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToDouble(Animal::getAge)
                .average()
                .ifPresent(System.out::println);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(person -> person.getGender().equals("Male")
                        && person.getDateOfBirth().isAfter(LocalDate.of(1996, 10, 23))
                        && person.getDateOfBirth().isBefore(LocalDate.of(2005, 10, 23)))
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    //пенсионный возраст Женщины = 58, мужчины = 63
    //TODO подумать еще...
    public static void task13() {
        List<House> houses = Util.getHouses();

        List<Person> third = new ArrayList<>(); //для сбора третьей очереди эвакуации.

        List<Person> first = houses.stream()
                .sorted(Comparator.comparing(House::getBuildingType, Comparator.reverseOrder()))
                .flatMap(house -> {
                    if (house.getBuildingType().equals("Hospital")) {
                        return house.getPersonList().stream();
                    } else {
                        Stream<Person> secondWave = house.getPersonList().stream().filter(person ->
                                {
                                    LocalDate dateOfBirth = person.getDateOfBirth();
                                    return dateOfBirth.isAfter(LocalDate.of(2005, 10, 22))
                                            || (dateOfBirth.isBefore(LocalDate.of(1965, 10, 23))
                                            && person.getGender().equals("Female"))
                                            || (dateOfBirth.isBefore(LocalDate.of(1960, 10, 23))
                                            && person.getGender().equals("Male"));
                                }
                        );

                        Stream<Person> thirdWave = house.getPersonList().stream().filter(person ->
                                {
                                    LocalDate dateOfBirth = person.getDateOfBirth();
                                    return (person.getGender().equals("Female")
                                            && (dateOfBirth.isAfter(LocalDate.of(1965, 10, 23))
                                            && dateOfBirth.isBefore(LocalDate.of(2005, 10, 22))))
                                            || (person.getGender().equals("Male")
                                            && (dateOfBirth.isAfter(LocalDate.of(1960, 10, 23))
                                            && dateOfBirth.isBefore(LocalDate.of(2005, 10, 22))));
                                }
                        );
                        thirdWave.collect(Collectors.toCollection(() -> third));
                        return secondWave;
                    }
                })
                .toList();

        Stream.concat(first.stream(), third.stream())
                .limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();

        double sum = cars.stream()
                .map(car -> {
                    Map<String, Car> map = new HashMap<>();
                    if (car.getCarMake().equals("Jaguar") || car.getColor().equals("White")) {
                        map.put("Turkmenistan", car);
                    } else if (car.getMass() <= 1500
                            && (car.getCarMake().equals("BMW") || car.getCarMake().equals("Lexus")
                            || car.getCarMake().equals("Chrysler") || car.getCarMake().equals("Toyota"))) {
                        map.put("Uzbekistan", car);
                    } else if ((car.getColor().equals("Black") && car.getMass() > 4000)
                            || car.getCarMake().equals("GMC") || car.getCarMake().equals("Dodge")) {
                        map.put("Kazakhstan", car);
                    } else if (car.getReleaseYear() < 1982 || car.getCarModel().equals("Civic") || car.getCarModel().equals("Cherokee")) {
                        map.put("Kyrgyzstan", car);
                    } else if ((!car.getColor().equals("Yellow") && !car.getColor().equals("Red")
                            && !car.getColor().equals("Green") && !car.getColor().equals("Blue")) || car.getPrice() > 40000) {
                        map.put("Russia", car);
                    } else if (car.getVin().contains("59")) {
                        map.put("Mongolia", car);
                    }
                    return map;
                })
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet().stream()
                .mapToDouble(entry -> {
                    double cost = entry.getValue().stream().mapToDouble(Car::getMass).sum() * 7.14 / 1000;
                    System.out.println(entry.getKey() + ": cost = " + cost);
                    return cost;
                })
                .sum();

        System.out.printf("""
                ---------------------------------
                Total cost: %.2f
                ---------------------------------
                %n""", sum);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        String neededFlowersNameStartsWith = "CDEFGJKLMNOPQRS";

        double sum = flowers.stream()
                .sorted(Comparator.comparing(
                                Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice).reversed()
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> neededFlowersNameStartsWith.contains(String.valueOf(flower.getCommonName().charAt(0))))
                .filter(flower -> flower.isShadePreferred()
                        && (flower.getFlowerVaseMaterial().contains("Glass")
                        || flower.getFlowerVaseMaterial().contains("Aluminum")
                        || flower.getFlowerVaseMaterial().contains("Steel")))
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 5 * 365 / 1000 * 1.39)
                .sum();
        System.out.println(sum + "$");
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println("Student: " + student.getSurname() + ". His age: " + student.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getGroup))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry-> entry.getValue().stream().mapToDouble(Student::getAge).average().orElseThrow()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(e -> System.out.println("В группе " + e.getKey() + " средний балл = " + e.getValue()));
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        String groupName = "C-2";

        students.stream()
                .filter(student -> student.getGroup().equals(groupName))
                .filter(student -> examinations.stream()
                        .filter(examination -> examination.getExam3() > 4)
                        .anyMatch(ex -> ex.getStudentId() == student.getId()))
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
//        students.stream() Продолжить ...
    }
}
