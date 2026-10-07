void doubleInt(int n) {
    n = n * 2;
}

void doubleAll(int[] a) {
    for (int i = 0; i < a.length; i++) {
        a[i] = a[i] * 2;
    }
}

void replace(int[] a) {
    a = new int[]{0, 0, 0};
}

void main() {
    int x = 5;
    doubleInt(x);
    IO.println(x);

    int[] nums = {1, 2, 3};
    doubleAll(nums);
    IO.println(Arrays.toString(nums));

    int[] alias = nums;
    alias[0] = 99;
    IO.println(Arrays.toString(nums));

    replace(nums);
    IO.println(Arrays.toString(nums));
}
