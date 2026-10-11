
object SortTop5 {

  case class Student(
                      id: Int,
                      name: String,
                      marks: Int,
                      course: String
                    )

  def main(args: Array[String]): Unit = {

    val students = List(
      Student(1, "Deepu", 85, "BSc CS"),
      Student(2, "Rahul", 72, "BCA"),
      Student(3, "Priya", 95, "BSc IT"),
      Student(4, "Amit", 68, "BSc CS"),
      Student(5, "Sneha", 91, "BCA"),
      Student(6, "Rohan", 78, "BSc IT"),
      Student(7, "Neha", 88, "BSc CS"),
      Student(8, "Vikas", 60, "BCA")
    )

    println("========== ORIGINAL DATASET ==========")
    println(f"${"ID"}%-5s ${"Name"}%-12s ${"Marks"}%-8s ${"Course"}%-10s")

    students.foreach { s =>
      println(f"${s.id}%-5d ${s.name}%-12s ${s.marks}%-8d ${s.course}%-10s")
    }

    val sortedStudents = students.sortBy(student => -student.marks)

    val top5 = sortedStudents.take(5)

    println("\n========== TOP 5 STUDENTS BY MARKS ==========")
    println(f"${"ID"}%-5s ${"Name"}%-12s ${"Marks"}%-8s ${"Course"}%-10s")

    top5.foreach { s =>
      println(f"${s.id}%-5d ${s.name}%-12s ${s.marks}%-8d ${s.course}%-10s")
    }
  }
}
