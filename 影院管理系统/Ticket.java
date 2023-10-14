package FilmHubTutorialV11;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Ticket {
    private String electronicID;  // 电子ID编号
    private String seatInfo;      // 座位信息
    private static String purchaseTime; // 购票时间
    private boolean getted;    // 是否已取票
    private Schedule selectedSchedule;
    private String ticketUserName;
    private int row;
    private int col;
    protected static List<Ticket> purchaseHistory = new ArrayList<>();

    public Ticket(String electronicID, String purchaseTime, String ticketUserName, Schedule selectedSchedule, int row, int col) {
        this.electronicID = electronicID;
        this.purchaseTime = purchaseTime;
        this.getted = false; 
        this.ticketUserName = ticketUserName;
        this.selectedSchedule = selectedSchedule;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col){
        this.col = col;
    }

    public String getElectronicID() {
        return electronicID;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public static String getPurchaseTime() {
        return purchaseTime;
    }

    public boolean isGetted() {
        return getted;
    }

    public void setElectronicID(String electronicID) {
        this.electronicID = electronicID;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    public static void setPurchaseTime() {
        // 获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        purchaseTime = dateFormat.format(new Date());
    }

    public void setGetted(boolean getted) {
        this.getted = getted;
    }

    public Schedule getSchedule() {
        return selectedSchedule;
    }

    public void setSchedule(Schedule selectedSchedule) {
        this.selectedSchedule = selectedSchedule;
    }

    // 随机电子ID
    public static String generateElectronicID() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 12; // 生成的电子ID编号长度
    
        StringBuilder electronicID = new StringBuilder();
    
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            electronicID.append(characters.charAt(random.nextInt(characters.length())));
        }
    
        return electronicID.toString();
    }

    // 验证取票的方法
    public boolean gettTicket() {
        if (!getted) {
            getted = true;
            return true; 
        }
        return false; 
    }

    public String getTicketUserName() {
        return ticketUserName;
    }

}
