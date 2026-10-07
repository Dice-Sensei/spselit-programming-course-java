void main() {
    String a = "hi";
    String b = new String("hi");
    IO.println(a == b);
    IO.println(a.equals(b));
    IO.println(a.equalsIgnoreCase("HI"));
}
