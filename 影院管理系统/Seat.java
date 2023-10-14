package FilmHubTutorialV11;

public class Seat {

    private int row;           // 座位所在行号
    private int seatNumber;    // 座位号
    private boolean isOccupied; // 座位是否被占用
    private boolean isLocked; // 座位是否被锁定

    public Seat(int row, int seatNumber) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.isOccupied = false;
        this.isLocked = false;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean occupy() {
        if (!isOccupied && lock()) {
            isOccupied = true;
            return true; 
        }
        return false;
    }

    public void available() {
        isOccupied = false;
    }

    public boolean isLocked() {
        return isLocked;
    }

    // 锁定座位
    public boolean lock() {
        if (!isOccupied && !isLocked) {
            isLocked = true;
            return true; 
        }
        return false; 
    }

    // 释放座位
    public boolean release() {
        if (isLocked) {
            isLocked = false;
            return true; 
        }
        return false; 
    }

}
