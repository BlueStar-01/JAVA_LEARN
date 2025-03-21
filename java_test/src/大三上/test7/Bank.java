package 大三上.test7;

public class Bank {
    static class card {
        private String name;

        private int balance;

        public card(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }

        public synchronized void mulitee() {
            System.out.println("同步方法");
            setBalance(getBalance() - 10000);
        }


        public void setBalance(int balance) {
            this.balance = balance;
            try {
                Thread.sleep(500);
                System.out.println("取款成功：" + getBalance() + " : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class pople extends Thread {
        private card card;

        public pople(card card) {
            this.card = card;
        }

        public synchronized void run() {
            card.mulitee();
        }
    }


    public static void main(String[] args) {
        card card = new card("卡1", 100000);
        //同步方法
        new pople(card).start();
        new pople(card).start();

        //同步方法块
        new pople(card) {
            public void run() {
                synchronized (card) {
                    System.out.println("同步代码块");
                    int balance = card.getBalance();
                    card.setBalance(balance - 20000);
                }
            }
        }.start();
        new pople(card) {
            public void run() {
                synchronized (card) {
                    System.out.println("同步方法块");
                    int balance = card.getBalance();
                    card.setBalance(balance - 20000);
                }
            }
        }.start();
    }
}