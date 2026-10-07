void main() {
    int[][] table = new int[3][3];
    for (int i = 0; i < table.length; i++) {
        for (int j = 0; j < table[i].length; j++) {
            table[i][j] = (i + 1) * (j + 1);
        }
    }
    for (int i = 0; i < table.length; i++) {
        IO.println(Arrays.toString(table[i]));
    }
    IO.println(table.length + " rows, " + table[0].length + " columns");
}
