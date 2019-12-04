package ru.javawebinar.basejava;

public class MainDeadlock {
    public static void main(String[] args) throws InterruptedException {
        MainDeadlock deadlock = new MainDeadlock();
        Accounter accounter1 = deadlock.makeAccounter();
        Accounter accounter2 = deadlock.makeAccounter();
        Account acc1 = deadlock.makeAccount("D001");
        Account acc2 = deadlock.makeAccount("C002");

        System.out.println(acc1 + "=" + acc1.getAmount());
        System.out.println(acc2 + "=" + acc2.getAmount());

        Thread thread1 = new Thread(() -> accounter1.doLedger(acc1, acc2, 10));
        Thread thread2 = new Thread(() -> accounter2.doLedger(acc2, acc1, 20));
        thread1.start();
        thread2.start();
        Thread.currentThread().join();

        System.out.println(acc1 + "=" + acc1.getAmount());
        System.out.println(acc2 + "=" + acc2.getAmount());
    }

    private Account makeAccount(String code) {
        return new Account(code, 100);
    }

    public Accounter makeAccounter() {
        return new Accounter();

    }

    public class Accounter {
        void doLedger(Account accDebit, Account accCredit, int sum) {
            System.out.println("begin ledger " + accDebit + "<=" + accCredit + ":" + sum);
            synchronized (accCredit) {
                int balance = getBalance(accCredit);
                if (balance < sum) {
                    new IllegalStateException("balance on the account less requested amount");
                }
                accCredit.setAmount(balance - sum);
                synchronized (accDebit) {
                    balance = accDebit.getAmount();
                    accDebit.setAmount(balance + sum);
                }
            }
            System.out.println("end ledger " + accDebit + "<=" + accCredit + ":" + sum);
        }

        private int getBalance(Account acc) {
            try {
                Thread.sleep(10); // long-time query to DB
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return acc.getAmount();
        }
    }

    class Account {
        String code;
        int amount;

        public Account(String code, int amount) {
            this.code = code;
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "code='" + code + '\'' +
                    '}';
        }
    }
}

