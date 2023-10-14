package com.example;

import java.util.List;

public class Front_Desk extends User{
    static String frontdeskfile = "前台V1.4.xlsx";
    protected static List<Front_Desk> front_Desks = FileManager.readUsersFromFile(frontdeskfile, Front_Desk.class);
    
    public Front_Desk(String username, String password, String userID, String phoneNumber, String email, UserRole role,String registerTime){
        super(username,password,userID,phoneNumber,email,role,registerTime);
    }

    // 列出电影信息
    protected void listMovieMes(List<Movie> movies){
        for(int i=0;i<movies.size();i++){
            Movie movie = movies.get(i);
            System.out.print((i+1) + ".");
            System.out.println("片名：" + movie.getMovieName());
            System.out.println("导演：" + movie.getMovieDirector());
            System.out.println("主演: " + movie.getMainActor());
            System.out.println("剧情简介： " + movie.getInformation());
            System.out.println("时长： " + movie.getTimeLength());
            System.out.println();
        }
    }

    // 列出电影场次
    protected void listAllSchedules(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            Movie movie = schedule.getMovie();
            Hall hall = schedule.getHall();
            String showTime = schedule.getTime();
            double price = schedule.getPrice();
            
            System.out.println("影片：" + movie.getMovieName());
            System.out.println("放映厅：" + hall.getHallNumber());
            System.out.println("时段：" + showTime);
            System.out.println("价格：" + price);
            System.out.println();
        }
    }

    // 列出指定电影和场次的座位信息
    protected void listSeatInfo(List<Schedule> schedules){
        System.out.println("请输入电影名：");
        String targetName = sc.nextLine();
        System.out.println("请输入场次(yyyy-MM-dd HH:mm)：");
        String targetTime = sc.nextLine();

        Schedule targetSchedule = null;
        for(Schedule schedule : schedules){
            if (schedule.getMovie().getMovieName().equals(targetName) && schedule.getTime().equals(targetTime)) {
                targetSchedule = schedule;
                break;
            }
        }
        if(targetSchedule == null){
            System.out.println("该电影的场次不存在！");
            return;
        }

        Hall hall = targetSchedule.getHall();
        hall.showSeat();
    }

    // 售票功能
    protected void sellTickets(List<Schedule> schedules, List<Customer> customers){
        System.out.println("请输入电影名：");
        String targetMovieName = sc.nextLine();
    
        System.out.println("请输入场次(yyyy-MM-dd HH:mm)：");
        String targetTime = sc.nextLine();
    
        Schedule targetSchedule = null;
        for (Schedule schedule : schedules) {
            if (schedule.getMovie().getMovieName().equals(targetMovieName) && schedule.getTime().equals(targetTime)) {
                targetSchedule = schedule;
                break;
            }
        }
    
        if (targetSchedule == null) {
            System.out.println("该电影的场次不存在！");
            return;
        }

        // 显示座位图
        Hall hall = targetSchedule.getHall();
        hall.showSeat();

        System.out.println("请输入想要的行号：");
        int row = sc.nextInt();
        sc.nextLine();
        System.out.println("请输入想要的列号：");
        int col = sc.nextInt();
        sc.nextLine();
        System.out.println("请输入用户名或手机号：");
        String identifier = sc.nextLine();
        Customer targetCustomer = null;
        for (Customer customer : customers) {
            if (customer.getUserName().equals(identifier) || customer.getPhoneNumber().equals(identifier)) {
                targetCustomer = (Customer)customer;
                break;
            }
        }
        if (targetCustomer == null) {
            System.out.println("未找到用户！");
            return;
        }

        // 计算折扣
        double discount = 1.0;
        if (targetCustomer.getUserLevel().equals("金牌用户")) {
            discount = 0.88;
        } else if (targetCustomer.getUserLevel().equals("银牌用户")) {
            discount = 0.95;
        }

        // 支付金额
        System.out.println("请输入支付金额：");
        double payment = sc.nextDouble();
        sc.nextLine();
        double ticketPrice = targetSchedule.getPrice() * discount;

        if(payment >= ticketPrice){
            System.out.println("应找" + (payment - ticketPrice) + "元");
        }

        // 生成电子ID编号
        String electronicID = generateElectronicID();
        // 输出票信息
        System.out.println("电影票信息：");
        System.out.println("电子ID编号：" + electronicID);
        System.out.println("电影名：" + targetMovieName);
        System.out.println("场次：" + targetTime);
        System.out.println("座位：" + row + "排" + col + "列");
        System.out.println("用户：" + targetCustomer.getUserName());
        System.out.println("应付金额：" + ticketPrice); 
        System.out.println("实付金额：" + payment); 
        System.out.println("找零：" + (payment - ticketPrice)); 
    }

    private String generateElectronicID(){
        long timeTemp = System.currentTimeMillis();
        int randomNum = (int) (Math.random() * 10000);
        return "EID-" + timeTemp + "-" + randomNum;
    }
}