// The same problem twice: loose variables first, then an object.

int deposit(int balance, int amount) {
    return balance + amount;
}

class Account {
    int balance;

    void deposit(int amount) {
        balance = balance + amount;
    }
}

void main() {
    // Loose variables: the data and the operation are separate.
    int adaBalance = 100;
    int graceBalance = 20;
    adaBalance = deposit(adaBalance, 50);
    graceBalance = deposit(graceBalance, 5);
    IO.println("Ada: " + adaBalance);
    IO.println("Grace: " + graceBalance);

    // Objects: each account carries its own data and its own deposit.
    Account ada = new Account();
    ada.balance = 100;
    Account grace = new Account();
    grace.balance = 20;
    ada.deposit(50);
    grace.deposit(5);
    IO.println("Ada: " + ada.balance);
    IO.println("Grace: " + grace.balance);
}
