import java.util.*;
/*----------------------------------------------------------------
                     Students Database
  ----------------------------------------------------------------*/
class StudentInfoDataBase {
  List < Student > students = new ArrayList < > ();

  public void addStudentInfoDataBase(Student student) {
    students.add(student);
  }

  public void result_according_to_gpa() {
    System.out.println("\nResult According to GPA : ");
    System.out.println("--------------------------------");

    for (int i = 0; i < students.size(); i++) {
      for (int j = i; j < students.size(); j++) {
        if (students.get(j).GPA() > students.get(i).GPA() || students.get(j).GPA() == students.get(i).GPA() && students.get(j).getTotNum() > students.get(i).getTotNum()) {
          Student temp = students.get(i);
          students.set(i, students.get(j));
          students.set(j, temp);
        }
      }
    }

    int count = 1;
    for (Student student: students) {
      System.out.print("0" + (count++) + " : " + student.getName() + ", GPA: ");
      System.out.printf("%.2f\n", student.GPA());
    }
  }

  public void printStudentsTakenCourses() {
    for (Student student: students) {
      student.printStudentsInfo();
    }
    System.out.println();
  }
}
/*----------------------------------------------------------------
                     Course Database
  ----------------------------------------------------------------*/
class CourseInfoDataBase {

  Map < String, Map < Double, Student >> m1 = new HashMap < > ();

  public void addCourseInfoDataBase(String courseName, Student student, double marks) {
    if (!m1.containsKey(courseName)) {
      m1.put(courseName, new HashMap < > ());
    }
    m1.get(courseName).put(marks * -1, student);
  }

  public void course_based_rank_list() {
    System.out.println("\nCourse-Based Students Rank-List:");
    System.out.println("--------------------------------");

    for (String course: m1.keySet()) {

      System.out.println("Course: " + course);
      System.out.println("Students:");

      // Gpa er upor base kore sort korte bolse , Marks er upor base kre sort krle gpa er moto sort hobe and ensure hobe gpa same hole marks jar age se age thakbe
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

      System.out.println("\n >> " + course + " >> ");
      Map < Double, Student > sortedInnerMap = new TreeMap < > (m1.get(course));
      for (Map.Entry < Double, Student > entry: sortedInnerMap.entrySet()) {
        Student student = entry.getValue();
        System.out.println(student.getName() + ", Roll : " + student.getRoll());
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
/*----------------------------------------------------------------
                     Xm Types  
  ----------------------------------------------------------------*/
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
    if (attendence >= 0)
      marks = (attendence + assignment);
    else
      marks = assignment;
  }

  public double getMarks() {
    return marks;
  }
}

/*----------------------------------------------------------------
                     Courses Class
  ----------------------------------------------------------------*/

abstract class Course {
  private String courseName;
  public int[][] all_possible_evaluation_combinations = {
    {
      30,
      60,
      5,
      5
    },
    {
      30,
      60,
      10,
      0
    },
    {
      20,
      70,
      10,
      0
    },
    {
      20,
      70,
      5,
      5
    }
  }; // Midterm, Final, (Assignment, Attendence),

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

class MajorCourse extends Course {
  private double credit = 3.0;
  private double fullScore = 0.0;
  private int assesmentCriteria;

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

  public void setAssesmentCriteria(int n) {
    assesmentCriteria = n;
  }

  public void getAssesmentCriteria() {
    System.out.println("Midterm : " + all_possible_evaluation_combinations[assesmentCriteria][0] + " , Final : " + all_possible_evaluation_combinations[assesmentCriteria][1] + " , Assignment : " + all_possible_evaluation_combinations[assesmentCriteria][2] + " , Attendence : " + all_possible_evaluation_combinations[assesmentCriteria][3]);
  }

}

class OptionalCourse extends Course {
  private double credit = 1.5;
  private double fullScore = 0.0;
  private int assesmentCriteria;

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

  public void setAssesmentCriteria(int n) {
    assesmentCriteria = n;
  }

  public void getAssesmentCriteria() {
    System.out.println("Midterm : " + all_possible_evaluation_combinations[assesmentCriteria][0] + " , Final : " + all_possible_evaluation_combinations[assesmentCriteria][1] + " , Assignment : " + all_possible_evaluation_combinations[assesmentCriteria][2] + " , Attendence : " + all_possible_evaluation_combinations[assesmentCriteria][3]);
  }
}

/*----------------------------------------------------------------
                     Student Class
  ----------------------------------------------------------------*/

class Student {
  private String name;
  private int roll;
  private String email;

  MajorCourse major1, major2, major3;
  OptionalCourse optional;

  private double totScore;

  Student(String name, int roll, String email) {
    this.name = name;
    this.roll = roll;
    this.email = email;
  };

  public void Setmajor1(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence, int assesmentCriteria) {
    major1 = new MajorCourse(name);
    major1.setAssesmentCriteria(assesmentCriteria);
    major1.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);
  }
  public void Setmajor2(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence, int assesmentCriteria) {
    major2 = new MajorCourse(name);
    major2.setAssesmentCriteria(assesmentCriteria);
    major2.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);
  }
  public void Setmajor3(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence, int assesmentCriteria) {
    major3 = new MajorCourse(name);
    major3.setAssesmentCriteria(assesmentCriteria);
    major3.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);

  }

  public void OptionalCourse(String name, double midFullMarks, double midScore, double FinalFull, double FinalScore, double assignment, double attendence, int assesmentCriteria) {
    optional = new OptionalCourse(name);
    optional.setAssesmentCriteria(assesmentCriteria);
    optional.calculateFinalScore(name, midFullMarks, midScore, FinalFull, FinalScore, attendence, assignment);

  }

  public double GPA() {
    totScore = (major1.getMarksScored() + major2.getMarksScored() + major3.getMarksScored() + optional.getMarksScored());
    double num = (major1.getFinalCreditScore() + major2.getFinalCreditScore() + major3.getFinalCreditScore() + optional.getFinalCreditScore()) / (3 * 3 + 1.5);
    return num;
  }

  public String getName() {
    return name;
  }

  public int getRoll() {
    return roll;
  }

  public double getTotNum() {
    return totScore;
  }

  public void printStudentsInfo() {
    System.out.println("\n" + name);
    System.out.println("Roll :" + roll);
    System.out.println("Email : " + email);

    System.out.println("Major Courses : ");
    System.out.println(major1.getCourseName());
    major1.getAssesmentCriteria();
    System.out.println(major2.getCourseName());
    major2.getAssesmentCriteria();
    System.out.println(major3.getCourseName());
    major3.getAssesmentCriteria();

    System.out.println("Optional Course : ");
    System.out.println(optional.getCourseName());
    optional.getAssesmentCriteria();
  }

}

public class StudentDatabase {

  private static final String[] FIRST_NAMES = {
    "Tauseef",
    "Tamzid",
    "Shahriar",
    "Ashraful",
    "Faiaz",
    "Mr.",
    "Anik",
    "Tamim",
    "Amio",
    "Mithu",
    "Manob",
    "Niloy",
    "Rana",
    "Rohan",
    "Tamzid",
    "Fahim",
    "Kawser",
    "Rohul",
    "Naimul",
    "Hemal"
  };
  private static final String[] LAST_NAMES = {
    "Rahman",
    "Alam",
    "Dewan",
    "bin Tariq",
    "Hasan",
    "Kabir",
    "Mahmud",
    "Mahmud Ifti",
    "Sheikh",
    "Karim",
    "Amin"
  };
  private static final String[] EMAIL_DOMAINS = {
    "gmail.com",
    "yahoo.com",
    "hotmail.com",
    "outlook.com",
    "example.com"
  };
  private static final String[] courses = {
    "ArtificialIntelligence",
    "Security",
    "OperationResearch",
    "Networking",
    "EmbeddedSystems"
  };
  private static final int[][] all_possible_evaluation_combinations = {
    {
      30,
      60,
      5,
      5
    },
    {
      30,
      60,
      10,
      0
    },
    {
      20,
      70,
      10,
      0
    },
    {
      20,
      70,
      5,
      5
    }
  }; // Midterm, Final, (Assignment, Attendence),

  /*----------------------------------------------------------------
                      Random Student Generator Function
   ----------------------------------------------------------------*/

  static void generate_random_students_and_add_them(CourseInfoDataBase c, StudentInfoDataBase s, int no_of_students) {
    Random random = new Random();
    Random random2 = new Random();
    Random random3 = new Random();
    Random random4 = new Random();
    for (int i = 0; i < no_of_students; i++) {
      String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
      String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
      String emailDomain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];
      String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + emailDomain;

      // Roll has to unique, so taking roll not as random but rather as the counter "i", which is bound to be unique
      int roll = i + 1;

      Student student = new Student(firstName + " " + lastName, roll, email);

      // Student will choose 4 subjects , 3 main , 1 major . Now , 1 subject is not going to be selected. So , I can randomly choose that not selecting subject which will ultimately result in choosing subjects randomly.
      int course_no = random.nextInt(10);
      // System.out.println("courseNo " + course_no); //

      int evaluation_process_to_choose = random.nextInt(4);
      // random.double 0 theke 1 er moddhe ekta double value dibe , oita diye multiply kore score calc hocche
      student.Setmajor1(courses[course_no % 5], all_possible_evaluation_combinations[evaluation_process_to_choose][0], all_possible_evaluation_combinations[evaluation_process_to_choose][0] * random.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][1], all_possible_evaluation_combinations[evaluation_process_to_choose][1] * random.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][2] * random.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][3], evaluation_process_to_choose);
      course_no++;

      evaluation_process_to_choose = random.nextInt(4);
      // random.double 0 theke 1 er moddhe ekta double value dibe , oita diye multiply kore score calc hocche
      student.Setmajor2(courses[course_no % 5], all_possible_evaluation_combinations[evaluation_process_to_choose][0], all_possible_evaluation_combinations[evaluation_process_to_choose][0] * random2.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][1], all_possible_evaluation_combinations[evaluation_process_to_choose][1] * random2.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][2] * random2.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][3], evaluation_process_to_choose);
      course_no++;

      evaluation_process_to_choose = random.nextInt(4);
      // random.double 0 theke 1 er moddhe ekta double value dibe , oita diye multiply kore score calc hocche
      student.Setmajor3(courses[course_no % 5], all_possible_evaluation_combinations[evaluation_process_to_choose][0], all_possible_evaluation_combinations[evaluation_process_to_choose][0] * random3.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][1], all_possible_evaluation_combinations[evaluation_process_to_choose][1] * random3.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][2] * random3.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][3], evaluation_process_to_choose);
      course_no++;

      evaluation_process_to_choose = random.nextInt(4);
      // random.double 0 theke 1 er moddhe ekta double value dibe , oita diye multiply kore score calc hocche
      student.OptionalCourse(courses[course_no % 5], all_possible_evaluation_combinations[evaluation_process_to_choose][0], all_possible_evaluation_combinations[evaluation_process_to_choose][0] * random4.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][1], all_possible_evaluation_combinations[evaluation_process_to_choose][1] * random4.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][2] * random4.nextDouble(), all_possible_evaluation_combinations[evaluation_process_to_choose][3], evaluation_process_to_choose);
      course_no++;

      s.addStudentInfoDataBase(student); // Output:;

      c.addCourseInfoDataBase(student.major1.getCourseName(), student, student.major1.getMarksScored());
      c.addCourseInfoDataBase(student.major2.getCourseName(), student, student.major2.getMarksScored());
      c.addCourseInfoDataBase(student.major3.getCourseName(), student, student.major3.getMarksScored());
      c.addCourseInfoDataBase(student.optional.getCourseName(), student, student.optional.getMarksScored());

    }

  }

  public static void printStudentsInformations(StudentInfoDataBase s) {
    s.printStudentsTakenCourses();
  }

  public static void printCourseRankBasedList(CourseInfoDataBase c) {
    c.course_based_rank_list();
  }
  public static void printTotalGradeForEachStudent(StudentInfoDataBase s) {
    s.result_according_to_gpa();
  }

  /*----------------------------------------------------------------
                      Main Function
   ----------------------------------------------------------------*/
  public static void main(String[] args) {

    CourseInfoDataBase c = new CourseInfoDataBase();
    StudentInfoDataBase s = new StudentInfoDataBase();

    generate_random_students_and_add_them(c, s, 10);

    printStudentsInformations(s);
    printCourseRankBasedList(c);
    printTotalGradeForEachStudent(s);

  }
}