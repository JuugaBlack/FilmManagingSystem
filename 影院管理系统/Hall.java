package FilmHubTutorialV12;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    private int hallNumber; // 放映厅编号
    int numRows = 7;    // 座位排数
    int seatsPerRow = 12; // 每排座位数
    private Seat[][] seats; // 座位二维数组
    protected static List<Hall> halls = new ArrayList<>();

    public Hall(int hallNumber) {
        this.hallNumber = hallNumber;
        this.seats = new Seat[numRows][seatsPerRow];
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = new Seat(i + 1, j + 1);
            }
        }
    }

    static {
        for (int hallNumber = 1; hallNumber <= 5; hallNumber++) {
            Hall hall = new Hall(hallNumber);
            halls.add(hall);
        }
    }

    public void showSeat() {
        // 打印列号
        for (int i = 0; i < seatsPerRow; i++) {
            System.out.printf("\t%d", i + 1);
        }
        System.out.println();

        // 打印行号与座位
        for (int i = 0; i < numRows; i++) {
            System.out.printf("%d\t", i + 1);

            for (int j = 0; j < seatsPerRow; j++) {
                Seat seat = seats[i][j];
                System.out.print(seat.isOccupied() ? "X" : "O");
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public Seat getSeat(int row, int column) {
        if (row >= 1 && row <= numRows && column >= 1 && column <= seatsPerRow) {
            return seats[row - 1][column - 1];
        } else {
            return null;
        }
    }

    public static List<Hall> getHalls() {
        return halls;
    }

    // 自定义toString()方法
    public String hallNumtoString() {
        return String.valueOf(getHallNumber());
    }
}
