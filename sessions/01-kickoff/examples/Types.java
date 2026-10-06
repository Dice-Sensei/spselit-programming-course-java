void main() {
    int age = 17;
    double height = 1.75;
    boolean student = true;
    char grade = 'A';
    String name = "Ada";

    IO.println("name: " + name);
    IO.println("age: " + age);
    IO.println("height: " + height);
    IO.println("student: " + student);
    IO.println("grade: " + grade);

    // This line would not compile: an int cannot hold text.
    // int age = "seventeen";
}
