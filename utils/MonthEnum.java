package account.utils;

public enum MonthEnum {
    January, February, March, April, May, June,
    July, August, September, October, November, December;

    public static String validateDate(String date) {
        String[] parts = date.split("-");
        if (parts.length != 2) {
            return "invalid";
        }

        int month = Integer.parseInt(parts[0]);
        if (month < 1 || month > 12) {
            return "invalid";
        }
        MonthEnum monthName = MonthEnum.values()[month - 1];
        return monthName + "-" + parts[1];
    }
}
