float calculateRectangleArea(float length, float width) {
    return length * width;
}

float calculateRectanglePerimeter(float length, float width) {
    return 2 * (length + width);
}

int calculateRectanglePerimeter(int length, int width) {
    return 2 * (length + width);
}

double calculateRectanglePerimeter(double length, double width) {
    return 2 * (length + width);
}

void changeImpossible(int number) {
    number = 20; // new value on stack, but original is still pointing to original stack
}

void changeImpossible2(String text) {
    text = "Changed"; // this will point to different memory location, but original is still pointing
                      // to original
}

void changePossible(int[] numbers) {
    numbers[0] = 10; // this will change the original array because it is a reference type
}

void main() {
    var myNumber = 10;
    IO.println("MyNumber: " + myNumber);
    changeImpossible(myNumber);
    IO.println("MyNumber: " + myNumber);

    var myText = "Original";
    IO.println("MyText: " + myText);
    changeImpossible2(myText);
    IO.println("MyText: " + myText);

    int[] numbers = { 1, 2, 3, 4, 5 };
    IO.println("Numbers: " + Arrays.toString(numbers));
    changePossible(numbers);
    IO.println("Numbers: " + Arrays.toString(numbers));

    float area = calculateRectangleArea(5.0f, 3.0f);
    float perimeter = calculateRectanglePerimeter(5.0f, 3.0f);
    int perimeterInt = calculateRectanglePerimeter(5, 3);

    var per = calculateRectanglePerimeter(5.0f, 3.0f);

    IO.println("The perimeter of a rectangle with length 5.0 and width 3.0 is: " + perimeter);
    IO.println("The area of a rectangle with length 5.0 and width 3.0 is: " + area);
    IO.println("The area of a rectangle with length 10.0 and width 2.5 is: " + calculateRectangleArea(10.0f, 2.5f));
}
