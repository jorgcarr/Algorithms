import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Transaction {

    public Account account = new Account();
    List<String> transactions = new Vector<String>();

    synchronized void deposit(int money) {
        transactions.add(account.deposit(money));
    }

    synchronized void withdraw(int money) {
        transactions.add(account.withdraw(money));
    }

    List<String> getTransactions() {
        return transactions;
    }

    public static void main(String[] args) {
        Transaction transaction = new Transaction();
        transaction.deposit(100);
        transaction.withdraw(50);
        System.out.println(transaction.getTransactions());
        System.out.println("balance " + transaction.account.getBalance());
    }
}
