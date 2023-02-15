package by.markov;

import by.markov.model.Animal;
import by.markov.model.Car;
import by.markov.model.Flower;
import by.markov.model.House;
import by.markov.model.Person;
import by.markov.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> "Japanese".equalsIgnoreCase(a.getOrigin()))
                .peek(a -> {
                    if ("Female".equalsIgnoreCase(a.getGender())) a.setBread(a.getBread().toUpperCase());
                })
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() > 30)
                .map(Animal::getOrigin)
                .distinct()
                .filter(a -> a.getBytes()[0] == 'A')
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .filter(a -> "Female".equalsIgnoreCase(a.getGender()))
                .count()
        );

    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(a -> "Hungarian".equalsIgnoreCase(a.getOrigin()))
        );
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .allMatch(a -> "Male".equalsIgnoreCase(a.getGender()) || "Female".equalsIgnoreCase(a.getGender()))
        );
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .noneMatch(a -> "Oceania".equalsIgnoreCase(a.getOrigin()))
        );
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max((Comparator.comparingInt(Animal::getAge)))
                .get()
                .getAge()
        );
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(((o1, o2) -> o1.length - o2.length))
                .get()
                .length
        );
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .mapToInt(Animal::getAge)
                .reduce(Integer::sum)
                .getAsInt()
        );
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals
                .stream()
                .filter(a -> "Indonesian".equalsIgnoreCase(a.getOrigin()))
                .mapToInt(Animal::getAge)
                .average()
                .getAsDouble()
        );
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        Predicate<Person> candidatesFilter = p -> "Male".equalsIgnoreCase(p.getGender())
                && (Period.between(p.getDateOfBirth(), LocalDate.now()).getYears()) >= 18
                && (Period.between(p.getDateOfBirth(), LocalDate.now()).getYears()) <= 27;
        people.stream()
                .filter(candidatesFilter)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Predicate<House> hospital = h -> "Hospital".equalsIgnoreCase(h.getBuildingType());
        Predicate<Person> youngAndOldFilter = p -> (Period.between(p.getDateOfBirth(), LocalDate.now()).getYears() < 18)
                || (Period.between(p.getDateOfBirth(), LocalDate.now()).getYears() > 65);
        Stream<Person> partFromHospital = houses.stream()
                .filter(hospital)
                .flatMap(h -> h.getPersonList().stream());
        Stream<Person> youngAndOldPart = houses.stream()
                .filter(hospital.negate())
                .flatMap(h -> h.getPersonList().stream())
                .filter(youngAndOldFilter);
        Stream<Person> other = houses.stream()
                .filter(hospital.negate())
                .flatMap(h -> h.getPersonList().stream())
                .filter(youngAndOldFilter.negate());
        Stream.concat(Stream.concat(partFromHospital, youngAndOldPart), other).distinct().limit(500).forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        Predicate<Car> firstTrain = c -> "Jaguar".equalsIgnoreCase(c.getCarMake()) || "white".equalsIgnoreCase(c.getColor());
        Predicate<Car> secondTrain = c -> c.getMass() < 1500 || Arrays.asList(new String[]{"bmw", "lexus", "chrysler", "toyota"}).contains(c.getCarMake().toLowerCase());
        Predicate<Car> thirdTrain = c -> ("Black".equalsIgnoreCase(c.getColor()) && c.getMass() > 4000)
                || "GMC".equalsIgnoreCase(c.getCarMake())
                || "Dodge".equalsIgnoreCase(c.getCarMake());
        Predicate<Car> fourthTrain = c -> c.getReleaseYear() < 1982
                || "Civic".equalsIgnoreCase(c.getCarMake())
                || "Cherokee".equalsIgnoreCase(c.getCarMake());
        Predicate<Car> fifthTrain = c -> !(Arrays.asList(new String[]{"yellow", "red", "green", "blue"}).contains(c.getColor().toLowerCase()))
                || c.getPrice() > 40000;
        Predicate<Car> sixthTrain = c -> c.getVin().contains("59");

        List<Car> toTurkmenistan = cars.stream()
                .filter(firstTrain).collect(Collectors.toList());
        List<Car> toUzbekistan = cars.stream()
                .filter(firstTrain.negate())
                .filter(secondTrain).collect(Collectors.toList());
        List<Car> toKazakhstan = cars.stream()
                .filter(firstTrain.negate())
                .filter(secondTrain.negate())
                .filter(thirdTrain).collect(Collectors.toList());
        List<Car> toKyrgyzstan = cars.stream()
                .filter(firstTrain.negate())
                .filter(secondTrain.negate())
                .filter(thirdTrain.negate())
                .filter(fourthTrain).collect(Collectors.toList());
        List<Car> toRussia = cars.stream()
                .filter(firstTrain.negate())
                .filter(secondTrain.negate())
                .filter(thirdTrain.negate())
                .filter(fourthTrain.negate())
                .filter(fifthTrain).collect(Collectors.toList());
        List<Car> toMongolia = cars.stream()
                .filter(firstTrain.negate())
                .filter(secondTrain.negate())
                .filter(thirdTrain.negate())
                .filter(fourthTrain.negate())
                .filter(fifthTrain.negate())
                .filter(sixthTrain).collect(Collectors.toList());

        Map<String, Double> result = new LinkedHashMap<>();
        result.put("Туркменистан", toTurkmenistan.stream().mapToDouble(c -> c.getMass() * 0.00714).reduce((Double::sum)).getAsDouble());
        result.put("Узбекистан", toUzbekistan.stream().mapToDouble(c -> c.getMass() * 0.00714).reduce(Double::sum).getAsDouble());
        result.put("Казахстан", toKazakhstan.stream().mapToDouble(c -> c.getMass() * 0.00714).reduce(Double::sum).getAsDouble());
        result.put("Кыргызстан", toKyrgyzstan.stream().mapToDouble(c -> c.getMass() * 0.00714).reduce(Double::sum).getAsDouble());
        result.put("Россия", toRussia.stream().mapToDouble(c -> c.getMass() * 0.00714).reduce(Double::sum).getAsDouble());
        result.put("Монголия", toMongolia.stream().mapToDouble(c -> c.getMass() * 0.00714).reduce(Double::sum).getAsDouble());
        result.forEach((key, value) -> System.out.printf("%s : $%.2f\n", key, value));
        System.out.println(
                Stream.of(
                        toTurkmenistan.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        toUzbekistan.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        toKazakhstan.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        toKyrgyzstan.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        toRussia.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt(),
                        toMongolia.stream().mapToInt(Car::getPrice).reduce(Integer::sum).getAsInt()
                ).reduce(Integer::sum).get()
        );
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}