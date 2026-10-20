import java.util.HashMap;

void main() {
    // Heavy: a map for exactly two values that are known in advance.
    HashMap<String, Object> person = new HashMap<>();
    person.put("name", "Ada");
    person.put("age", 36);
    IO.println(person.get("name") + " is " + person.get("age"));

    // Simple: two variables.
    String name = "Ada";
    int age = 36;
    IO.println(name + " is " + age);
}
