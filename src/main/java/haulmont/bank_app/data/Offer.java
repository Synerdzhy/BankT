package haulmont.bank_app.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.UUID;

public class Offer {
    private UUID id;
    private Client client;
    private Credit credit;
    private double sum;
    private PayGraph payGraph;

    public Offer(UUID id, Client client, Credit credit, int date) {
        this.id = id;
        this.client = client;
        this.credit = credit;
        this.sum = credit.creditSum();
        this.payGraph = new PayGraph(date);
    }

    public Client getClient() {
        return client;
    }

    public Credit getCredit() {
        return credit;
    }

    public PayGraph getPayGraph() {
        return payGraph;
    }

    public UUID getId() {
        return id;
    }

    public String getFio() {
        return client.getFio();
    }

    public void setFio(String fio) {
        client.setFio(fio);
    }

    public double getSum() {
        double creditSum = sum;

        creditSum = Math.round(creditSum * 100);
        creditSum = creditSum / 100;
        return creditSum;
    }

    public String getTime() {
        LocalDate localDate = LocalDate.now();

        int dayOfMonth = localDate.getDayOfMonth();
        int monthValue = localDate.getMonthValue();
        int year = localDate.getYear();
        if (dayOfMonth < getDate()) {
            return getDate() + "." + monthValue + "." + year;
        }
        monthValue = localDate.plusMonths(1).getMonthValue();
        year = localDate.getYear();
        return getDate() + "." + monthValue + "." + year;

    }

    public int getDate() {
        return payGraph.getDate();
    }

    public double getOfferBody() {
        double repaymentBody = payGraph.getRepaymentBody();

        repaymentBody = Math.round(repaymentBody * 100);
        repaymentBody = repaymentBody / 100;
        return repaymentBody;
    }

    public double getOfferPercent() {
        double repaymentPercent = payGraph.getRepaymentPercent();

        repaymentPercent = Math.round(repaymentPercent * 100);
        repaymentPercent = repaymentPercent / 100;
        return repaymentPercent;
    }

    class PayGraph {
        private int date;
        private double payment;
        private double repaymentBody;
        private double repaymentPercent;

        public PayGraph(int date) {
            this.date = date;
            payment = credit.payment();
            repaymentBody = credit.repaymentBody();
            repaymentPercent = payment - repaymentBody;
        }

        public int getDate() {
            return date;
        }

        public double getPayment() {
            return payment;
        }

        public double getRepaymentBody() {
            return repaymentBody;
        }

        public double getRepaymentPercent() {
            return repaymentPercent;
        }

        @Override
        public String toString() {
            return "PayGraph{" +
                    "date=" + date +
                    ", payment=" + payment +
                    ", repaymentBody=" + repaymentBody +
                    ", repaymentPercent=" + repaymentPercent +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(id, offer.id) || Objects.equals(client, offer.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, credit, payGraph, sum);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", client=" + client +
                ", credit=" + credit +
                ", payGraph=" + payGraph +
                ", sum=" + sum +
                '}';
    }
}
