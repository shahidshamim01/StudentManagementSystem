import java.io.*;
import java.util.*;

class Student { String id, name, course; double grade;

public Student(String id, String name, String course, double grade) {
    this.id = id;
    this.name = name;
    this.course = course;
    this.grade = grade;
}

@Override
public String toString() {
    return "ID: " + id + ", Name: " + name + ", Course: " + course + ", Grade: " + grade;
}

}

public class StudentManagementSystem { private static final String FILE_NAME = "students.txt"; private static final List<Student> students = new ArrayList<>();

public static void main(String[] args) {
    loadStudents();
    Scanner scanner = new Scanner(System.in);
    
    while (true) {
        System.out.println("\n1. Add Student  2. View Students  3. Search Student  4. Update Student  5. Delete Student  6. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1 -> addStudent(scanner);
            case 2 -> viewStudents();
            case 3 -> searchStudent(scanner);
            case 4 -> updateStudent(scanner);
            case 5 -> deleteStudent(scanner);
            case 6 -> {
                saveStudents();
                System.out.println("Exiting...");
                return;
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }
}

private static void addStudent(Scanner scanner) {
    System.out.print("Enter ID: ");
    String id = scanner.nextLine();
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Course: ");
    String course = scanner.nextLine();
    System.out.print("Enter Grade: ");
    double grade = scanner.nextDouble();
    students.add(new Student(id, name, course, grade));
    System.out.println("Student added successfully.");
}

private static void viewStudents() {
    if (students.isEmpty()) {
        System.out.println("No students found.");
    } else {
        students.forEach(System.out::println);
    }
}

private static void searchStudent(Scanner scanner) {
    System.out.print("Enter Student ID: ");
    String id = scanner.nextLine();
    students.stream().filter(s -> s.id.equals(id)).findFirst()
            .ifPresentOrElse(System.out::println, () -> System.out.println("Student not found."));
}

private static void updateStudent(Scanner scanner) {
    System.out.print("Enter Student ID to update: ");
    String id = scanner.nextLine();
    for (Student s : students) {
        if (s.id.equals(id)) {
            System.out.print("Enter new Name: ");
            s.name = scanner.nextLine();
            System.out.print("Enter new Course: ");
            s.course = scanner.nextLine();
            System.out.print("Enter new Grade: ");
            s.grade = scanner.nextDouble();
            System.out.println("Student updated successfully.");
            return;
        }
    }
    System.out.println("Student not found.");
}

private static void deleteStudent(Scanner scanner) {
    System.out.print("Enter Student ID to delete: ");
    String id = scanner.nextLine();
    if (students.removeIf(s -> s.id.equals(id))) {
        System.out.println("Student deleted successfully.");
    } else {
        System.out.println("Student not found.");
    }
}

private static void saveStudents() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
        oos.writeObject(students);
    } catch (IOException e) {
        System.out.println("Error saving students.");
    }
}

private static void loadStudents() {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
        List<Student> loadedStudents = (List<Student>) ois.readObject();
        students.addAll(loadedStudents);
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("No previous student data found.");
    }
}

}