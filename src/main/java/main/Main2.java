package main;

import java.util.*;
import java.util.stream.Collectors;

public class Main2 {
    public static void main(String[] args) {
        new Main2().run();
    }

    private void run() {

        List<Student> studentList = createList();
        printCollection(studentList);
        System.out.println("--------");

        // rating >= 74
        List<Student> list74 = findWithRatingNotLess(studentList,74);
        System.out.println("All with rating >= 74");
        printCollection(list74);

        // list of Faculties
        Set<String> faculties = findAllFaculties(studentList);
        printCollection(faculties);

        //find all students by faculties
       // Map<String,List<Student>> map = findAllStudentsByFaculties(studentList);
        Map<String,List<Student>> map = findAllStudentsByFacultiesStream(studentList);
        printMap(map);
    }

    private void printMap(Map<String, List<Student>> map) {
        for (Map.Entry<String,List<Student>> entry:map.entrySet()) {
            System.out.println(entry.getKey());
            for (Student student : entry.getValue()) {
                System.out.println("   " + student);
            }
        }
    }

    private Map<String, List<Student>> findAllStudentsByFacultiesStream(List<Student> studentList) {
        return studentList.stream().collect(Collectors.groupingBy(Student::getFaculty));
    }

    private Map<String, List<Student>> findAllStudentsByFaculties(List<Student> studentList) {

        Map<String,List<Student>> result = new HashMap<>();
        for (Student student : studentList) {
            String faculty = student.getFaculty();
            if (result.containsKey(faculty)) {
                result.get(faculty).add(student);
            } else  {
                result.put(faculty,new ArrayList<>(List.of(student)));
            }
        }
        return result;
    }

    private Set<String> findAllFaculties2(List<Student> students) {
        return students.stream().map(Student::getFaculty).collect(Collectors.toSet());
    }

    private Set<String> findAllFaculties(List<Student> studentList) {

        Set<String> result = new LinkedHashSet<>();
        for (Student student : studentList) {
            result.add(student.getFaculty());
        }
        return result;
    }

    private List<Student> findWithRatingNotLess(List<Student> studentList, int min) {

        List<Student> result = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getRating() >= min) {
                result.add(student);
            }
        }
        return result;
    }

    private List<Student> createList() {
        List<Student> studentList = new ArrayList<>(List.of(
                new Student(1,"Ivan","CS",95),
                new Student(2,"Nastya","FVS",87),
                new Student(3,"Dimon","CS",100),
                new Student(4,"Oleg","FVS",65),
                new Student(5,"Ivan","CS",73),
                new Student(6,"Sasha","CS",92),
                new Student(7,"Katya","KN",100)
        ));
        return studentList;
    }

    public void printCollection(Collection<?> collection) {

        for (Object obj : collection) {
            System.out.println(obj);
        }
    }
}
