public class HelloOld {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");

        System.out.println(args.length);

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        for (int i = 0; i < Integer.parseInt(args[2]); i++) {
            System.out.println(i);
        }
    }
}
