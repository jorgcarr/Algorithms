final public class LeapBirthDay {

    private final int day;
    private final int month;
    private final int year;

    public LeapBirthDay(int day, int month, int year) {

        if(day == 29 && month ==2) {
            day = 1;
            month = 3;
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public static void main(String[] args) {
        LeapBirthDay leap = new LeapBirthDay(29, 2, 2021);
        leap.getDay();
        leap.getMonth();
        leap.getYear();
    }
}
