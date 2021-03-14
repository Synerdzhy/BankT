package haulmont.bank_app.data;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Credit {
    private UUID id;
    private int lim;
    private int percent;
    private int month;

    public Credit(UUID id, int lim, int percent, int month) {
        this.id = id;
        this.lim = lim;
        this.percent = percent;
        this.month = month;
    }

    public void setLim(int lim) {
        this.lim = lim;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double creditSum() {
        return (double) lim * (1 + (double) percent / 100);
    }

    public double payment() {
        return creditSum() / month;
    }

    public double repaymentBody() {
        return (double) lim / month;
    }

    public UUID getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getLim() {
        return lim;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "Лимит " + lim + " | " + percent + "% | " + month + " месяцев";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Objects.equals(id, credit.id) || (Objects.equals(lim, credit.lim) && Objects.equals(percent, credit.percent));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
