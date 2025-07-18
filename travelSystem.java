enum TicketType {
    SINGLE(3.0, 2 * 60 * 60 * 1000),        // 3 euros, valid for 2 hours
    DAY(8.0, 24 * 60 * 60 * 1000),          // 8 euros, valid for 24 hours
    MONTHLY(55.0, 30L * 24 * 60 * 60 * 1000); // 55 euros, valid for 30 days

    private final double price;
    private final long durationMillis;

    TicketType(double price, long durationMillis) {
        this.price = price;
        this.durationMillis = durationMillis;
    }

    public double getPrice() {
        return price;
    }

    public long getDurationMillis() {
        return durationMillis;
    }
}

class Ticket {
    private TicketType type;
    private long purchaseTimeMillis;

    public Ticket(TicketType type) {
        this.type = type;
        this.purchaseTimeMillis = System.currentTimeMillis();
    }

    public boolean isValid() {
        long currentTime = System.currentTimeMillis();
        return currentTime < purchaseTimeMillis + type.getDurationMillis();
    }

    public TicketType getType() {
        return type;
    }
}

class TravelCard {
    private double balance;
    private Ticket activeTicket;

    public void loadMoney(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        this.balance += amount;
    }

    public double getBalance() {
        return balance;
    }

    public boolean hasValidTicket() {
        return activeTicket != null && activeTicket.isValid();
    }

    public boolean buyTicket(TicketType type) {
        if (hasValidTicket()) {
            return false;
        }

        if (balance < type.getPrice()) {
            return false;
        }

        balance -= type.getPrice();
        activeTicket = new Ticket(type);
        return true;
    }
}
