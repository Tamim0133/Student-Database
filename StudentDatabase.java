import java.util.*;

class StudentInfoDataBase {
  Map < Student, Double > m1 = new HashMap < > ();

  public void addStudentInfoDataBase(Student student, double marks) {
    m1.put(student, marks * -1);
  }

  public void printInfo() {
    System.out.println("\nResult According to GPA : ");
    System.out.println("--------------------------------");

    Map < Student, Double > sortedInnerMap = new TreeMap < > (new Comparator < Student > () {
      @Override
      public int compare(Student s1, Student s2) {
        return m1.get(s1).compareTo(m1.get(s2));
      }
    });

    sortedInnerMap.putAll(m1);
    int count = 1;
    for (Map.Entry < Student, Double > entry: sortedInnerMap.entrySet()) {
      Student student = entry.getKey();
      Double marks = entry.getValue() * -1;
      System.out.print("0" + (count++) + " : " + student.getName() + ", GPA: ");
      System.out.printf("%.2f\n", marks); // 
    }
  }
}

class CourseInfoDataBase {

  Map < String, Map < Double, Student >> m1 = new HashMap < > ();

  public void addCourseInfoDataBase(String courseName, Student student, double gpa) {
    if (!m1.containsKey(courseName)) {
      m1.put(courseName, new HashMap < > ());
    }
    m1.get(courseName).put(gpa * -1, student);
  }

  public void printInfo() {
    System.out.println("\nCourse-Based Students Rank-List:");
    System.out.println("--------------------------------");

    for (String course: m1.keySet()) {

      System.out.println("Course: " + course);
      System.out.println("Students:");
      Map < Double, Student > sortedInnerMap = new TreeMap < > (m1.get(course));
      int count = 1;
      for (Map.Entry < Double, Student > entry: sortedInnerMap.entrySet()) {
        double marks = entry.getKey() * -1;
        Student student = entry.getValue();
        System.out.print("0" + count++ + ": " + student.getName() + ", Marks: ");
        System.out.printf("%.2f\n", marks); // 

      }

      System.out.println();
    }
  }

  public void studentsWhoEnrolled() {
    System.out.println("Students List of who have enrolled to each particular course : ");
    System.out.println("-----------------------------------------");
    for (String course: m1.keySet()) {

      System.out.println(" >> " + course + " >> ");
      Map < Double, Student > sortedInnerMap = new TreeMap < > (m1.get(course));
      for (Map.Entry < Double, Student > entry: sortedInnerMap.entrySet()) {
        Student student = entry.getValue();
        System.out.println("Name : " + student.getName() + ", Roll : " + student.getRoll());
      }
    }
  }
}

class Grade {
  private double Grade;
  public double findGrade(double marks) {
    if (marks >= 80 && marks <= 100) {
      return 4.0;
    } else if (marks >= 75 && marks < 80) {
      return 3.75;
    } else if (marks >= 70 && marks < 75) {
      return 3.5;
    } else if (marks >= 65 && marks < 70) {
      return 3.25;
    } else if (marks >= 60 && marks < 65) {
      return 3.0;
    } else if (marks >= 55 && marks < 60) {
      return 2.75;
    } else if (marks >= 50 && marks < 55) {
      return 2.5;
    } else if (marks >= 45 && marks < 50) {
      return 2.25;
    } else if (marks >= 40 && marks < 45) {
      return 2.0;
    } else {
      return 0.0;
    }
  }
}

class Midterm {
  private double midtermScore;

  Midterm(double fullMarks, double gotMarks) {
    midtermScore = (gotMarks / fullMarks) * 20;
  }

  public double getMidtermScore() {
    return midtermScore;
  }
}

class Finalxm {
  private double finalMarks;

  Finalxm(double fullMarks, double gotMarks) {
    finalMarks = (gotMarks / fullMarks) * 70;
  }

  public double getFinalScore() {
    return finalMarks;
  }
}

class RgegularAssesment {
  private double marks;

  RgegularAssesment(double attendence, double assignment) {
    if (attendence > 0)
      marks = (attendence + assignment);
    else
      marks = assignment;
  }

  public double getMarks() {
    return marks;
  }
}

abstract class Course {
  private String courseName;

  Course(String courseName) {
    this.courseName = courseName;
  }

  public abstract double calculateFinalScore(String courseName,
  double MidtermFullMarks, double MidtermScore,
  double FinalFullMarks, double FinalScore,
  double AttendenceMarks, double AssignmentMarks);

  public String getCourseName() {
    return this.courseName;
  }

}

class MajorCourse extends Course{
  private double credit = 3.0;
  private double fullScore = 0.0;

  MajorCourse(String name) {
    super(name);
  }
    @Override
    public double calculateFinalScore(String courseName, double MidtermFullMarks, double MidtermScore,
            double FinalFullMarks, double FinalScore, double AttendenceMarks, double AssignmentMarks) {
        // TODO Auto-generated method stub
        Midterm m = new Midterm(MidtermFullMarks, MidtermScore);
        Finalxm f = new Finalxm(FinalFullMarks, FinalScore);
        RgegularAssesment r = new RgegularAssesment(AttendenceMarks, AssignmentMarks);
    
        fullScore = m.getMidtermScore() + f.getFinalScore() + r.getMarks();
        return fullScore;
    }

  public double getFinalCreditScore() {
    Grade g = new Grade();
    return g.findGrade(fullScore) * credit;

  }
  public double getMarksScored() {
    return fullScore;
  }

}

class OptionalCourse extends Course {
    private double credit = 1.5;
    private double fullScore = 0.0;
  
    OptionalCourse(String name) {
      super(name);
    }
      @Override
      public double calculateFinalScore(String courseName, double MidtermFullMarks, double MidtermScore,
              double FinalFullMarks, double FinalScore, double AttendenceMarks, double AssignmentMarks) {
          // TODO Auto-generated method stub
          Midterm m = new Midterm(MidtermFullMarks, MidtermScore);
          Finalxm f = new Finalxm(FinalFullMarks, FinalScore);
          RgegularAssesment r = new RgegularAssesment(AttendenceMarks, AssignmentMarks);
      
          fullScore = m.getMidtermScore() + f.getFinalScore() + r.getMarks();
          return fullScore;
      }
  
    public double getFinalCreditScore() {
      Grade g = new Grade();
      return g.findGrade(fullScore) * credit;
  
    }
    public double getMarksScored() {
      return fullScore;
    }
}

class Student {
  private String name;
  private int roll;
  private String email;

  MajorCourse major1, major2, major3;
  OptionalCourse optional;

  Student(String name, int roll, String email) {
    this.name = name;
    this.roll = roll;
    this.email = email;
  };

  public void Setmajor1(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence) {
    major1= new MajorCourse(name);
    major1.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);
  }
  public void Setmajor2(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence) {
    major2= new MajorCourse(name);
    major2.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);
  }
  public void Setmajor3(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence) {
    major3 = new MajorCourse(name);
    major3.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);
  

  }

  public void OptionalCourse(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence) {
    optional = new OptionalCourse(name);
    optional.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);
  

  }



  public double GPA() {
    double num = (major1.getFinalCreditScore() + major2.getFinalCreditScore() + major3.getFinalCreditScore() + optional.getFinalCreditScore()) / (3 * 3 + 1.5);
    return num;
  }

  public String getName() {
    return name;
  }

  public int getRoll() {
    return roll;
  }
}

public class StudentDatabase {
  public static void main(String[] args) {

    CourseInfoDataBase c = new CourseInfoDataBase();
    StudentInfoDataBase s = new StudentInfoDataBase();
    
    Student student1 = new Student("Tamim", 11, "<email>");
    
    student1.Setmajor1("ArtificialIntelligence", 40, 30, 60, 40, 5, 5);
    student1.Setmajor2("Security", 20, 10, 70, 40, 5, 5);
    student1.Setmajor3("OperationalReasearch", 30, 2, 60, 40, 5, 5);
    student1.OptionalCourse("Networking", 40, 32, 60, 40, 5, 5);
    System.out.println("\n\n");
    System.out.print(student1.getName() + " er GPA : ");
    System.out.printf("%.2f\n", student1.GPA()); // Output: 3.14


    s.addStudentInfoDataBase(student1, student1.GPA()); // Output:;

    c.addCourseInfoDataBase("ArtificialIntelligence", student1, student1.major1.getMarksScored());

    c.addCourseInfoDataBase("Security", student1, student1.major2.getMarksScored());

    c.addCourseInfoDataBase("OperationalReasearch", student1, student1.major3.getMarksScored());

    c.addCourseInfoDataBase("Networking", student1, student1.optional.getMarksScored());

    Student student2 = new Student("Ifti", 12, "<email>");
    student2.Setmajor1("ArtificialIntelligence", 40, 37, 60, 40, 5, 5);
    student2.Setmajor2("Security", 30, 27, 70, 40, 5, 5);
    student2.Setmajor3("Networking", 40, 29, 60, 40, 5, 5);
    student2.OptionalCourse("EmbeddedSystems.", 20, 12, 70, 52, 5, 5);

    System.out.print(student2.getName() + " er GPA : ");
    System.out.printf("%.2f\n", student2.GPA()); // 

    s.addStudentInfoDataBase(student2, student2.GPA()); // Output:;

    c.addCourseInfoDataBase("ArtificialIntelligence", student2, student2.major1.getMarksScored());

    c.addCourseInfoDataBase("Security", student2, student2.major2.getMarksScored());

    c.addCourseInfoDataBase("Networking", student2, student2.major3.getMarksScored());

    c.addCourseInfoDataBase("EmbeddedSystems", student2, student2.optional.getMarksScored());

    c.printInfo();
    c.studentsWhoEnrolled();
    s.printInfo();
  }
}