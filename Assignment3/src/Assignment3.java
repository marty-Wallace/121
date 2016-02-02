
import java.util.*;
/**
 * Lab assignment 3 for COSC 121
 * - practicing exception handing 
 * 
 * Jan 21 2016
 * 
 * 
 * @author Martin Wallace
 * <p>marty.wallace@ymail.com
 */
public class Assignment3{
    private final Scanner input;
    private ArrayList<Student> students;
    /**
     * Private Constructor for Assignment 3
     */
    private Assignment3(){
        this.input = new Scanner(System.in);
    } 

    /**
     * main method to Startup program
     */
    public static void main(String[]args){
        Assignment3 assignment3 = new Assignment3();
        assignment3.execute();
    }

    /**
     * main loop that runs until user is done
     */
    public void execute(){
        students = new ArrayList<Student>();
        students.add(new Student("Martin Wallace", 108));
        int choice;
        boolean running = true;
        while(running){
            System.out.print("Main menu\n\n1.Add a Student \n2.Edit student grades \n3.Exit \n");
            choice = getChoice(1,3);
            switch(choice){
                case 1:
                addStudent();
                break;
                case 2:
                editGrades();
                break;
                case 3:
                running = false;
                System.out.println("Thank you, Goodbye! ");
                input.close();
                System.exit(0);
                break;
                default:
                System.out.println("\f");
                System.out.println("Error - Not a viable choice");
                break;
            }
        }
    }

    /**
     * takes user input for menu decisions and handles exceptions
     * returns a valid user choice
     * input is highest number of choice
     */
    public int getChoice(int lowerBound, int upperBound){
        boolean badInput = true;
        int choice = -1;
        do{
            try{
                choice = Integer.parseInt(input.nextLine());
                if(choice >= lowerBound && choice <= upperBound){
                    badInput = false;
                }else{
                    System.out.println("Please enter an integer between " + lowerBound + "-" + upperBound);
                }
            }catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter an integer");
            }
        }while(badInput);

        return choice;
    }

    /**
     * Method to add a new student to our ArrayList students handles all exceptions
     */
    public void addStudent(){
        System.out.println("Please enter the students name: ");
        String name = input.nextLine();
        System.out.println("Please enter a grade for " + name + ": ");
        int grade = getChoice(0,100);
        students.add(new Student(name, grade));
        Collections.sort(students);
        System.out.println("\f");
    }

    /**
     * Method to edit grades of already existing students handles all exceptions
     */
    public void editGrades(){
        int index, choice, size, grade;
        Student modStudent = new Student("",-1);
        boolean badInput = true;
        size = students.size();
        if(size == 0){
            System.out.println("\f");
            System.out.println("There are no students added yet. Please add students before attempting to edit grades.");
            return;
        }
        System.out.println("What student would you like to enter a grade for?");
        index = 0;
        for(Student current : students){
            index++;
            System.out.println(index+") " + current);
        }
        choice = getChoice(1,size);
        do{
            try{
                modStudent = students.get(choice-1);
                badInput = false;
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("You have entered an invalid number");
                System.out.println("Please try again");
                return;
            }
        }while(badInput);
        System.out.println("What grade would you like to enter for " + modStudent.getName() + ":");
        grade = getChoice(0,100);
        modStudent.setGrade(grade);
        System.out.println("\f");
    }
}
/**
 * Simple class to hold name and grade for students implementing comparable inteface for easy alphabetic sorting
 */
class Student implements Comparable<Student>{
    private String name;
    private int grade;
    public Student(String name, int grade){
        this.name = name;
        this.grade = grade;
    }
    public String toString(){
        return name + " - " + grade;
    }
    public void setGrade(int grade){
        this.grade = grade;
    }
    public String getName(){
        return this.name;
    }
    public int compareTo(Student s){
        return this.name.toLowerCase().compareTo(s.getName().toLowerCase());
    }
}