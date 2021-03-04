package ca.study.purpose.DBClasses;

public class Account {

    @Id
    private long no;
    private String type;
    private long rest;
    private boolean locked;

    public Account(){}

    public Account(long no, String type, long rest, boolean locked) {
        this.no = no;
        this.type = type;
        this.rest = rest;
        this.locked = locked;
    }

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", Type='" + type + '\'' +
                ", Rest=" + rest +
                ", locked=" + locked +
                '}';
    }
}
