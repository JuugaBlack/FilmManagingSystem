package FilmHubTutorialV12;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private String userLevel; 
    private double accumulatedConsume;  
    private int accumulatedTimes; 
    static String customerfile = "顾客V1.2.txt";
    protected static List<Customer> customers = FileManager.readCustomersFromFile(customerfile);

    public Customer(String username, String password, String userID, String phoneNumber, String email, UserRole role, String registerTime){
        super(username, password, userID, phoneNumber, email, role,registerTime);
    }

    public boolean register(){
		// 提示用户输入用户名
		System.out.println("请输入用户名 >");
		String newUsername = sc.nextLine();
		if(isUserNameTaken(newUsername)){
			System.out.println("用户名已存在 > ");
			return false;
		}else if(newUsername.length() < 5){
            System.out.println("用户名长度不小于5个字符，请重新输入！");
            return false;
        }
	
		// 提示用户输入密码
		System.out.println("请输入密码 > ");
		String newPassword = sc.nextLine();
        if(!isVailedPassword(newPassword)){
            System.out.println("密码长度需大于8个字符，且必须是大小写字母、数字、符合的组合");
            return false;
        }
	
		// 提示用户确认密码
		System.out.println("请再次输入密码以确认 > ");
		String aPassword = sc.nextLine();
		if(!newPassword.equals(aPassword)){
			System.out.println("两次输入密码输入不一致 > ");
			return false;
		}
	
		// 提示用户输入电话号码
		System.out.println("请输入电话号码 > ");
		String newPhoneNumber = sc.nextLine();
	
		// 提示用户输入电子邮箱
		System.out.println("请输入电子邮箱 > ");
		String newEmail = sc.nextLine();
		
		// 读取已存在的数据
customers = FileManager.readCustomersFromFile(Customer.customerfile);

// 创建对象
String hashedPassword = User.hashPassword(newPassword);
Customer newCustomer = new Customer(newUsername, hashedPassword, generateID(10), newPhoneNumber, newEmail, UserRole.CUSTOMER, registerTime);
newCustomer.setUserLevel("铜牌用户");
newCustomer.setAccumulatedConsume(0.0);
newCustomer.setAccumulatedTimes(0);

// 将新对象添加到列表
customers.add(newCustomer);

// 将新数据写入文件，包括已存在的数据
FileManager.writeCustomersToFile(Customer.customerfile, customers);

System.out.println("注册成功！");

		return true;
	}

    public void setUserLevel(String userLevel){
        this.userLevel = userLevel;
    }
    public String getUserLevel(){
        return userLevel;
    }

    public void setAccumulatedConsume(double accumulatedConsume){
        this.accumulatedConsume = accumulatedConsume;
    }
    public double getAccumulatedConsume(){
        return accumulatedConsume;
    }

    public void setAccumulatedTimes(int accumulatedTimes){
        this.accumulatedTimes = accumulatedTimes;
    }
    public int getAccumulatedTimes(){
        return accumulatedTimes;
    }

    protected boolean isVailedPassword(String newPassword){
        
        // 检查密码长度
        if (newPassword.length() < 8) {
            return false;
        }
        
        // 检查是否包含大写字母
        if (!newPassword.chars().anyMatch(Character::isUpperCase)) {
            return false;
        }
        
        // 检查是否包含小写字母
        if (!newPassword.chars().anyMatch(Character::isLowerCase)) {
            return false;
        }
        
        // 检查是否包含数字
        if (!newPassword.chars().anyMatch(Character::isDigit)) {
            return false;
        }
        
        // 检查是否包含特殊字符（标点符号等）
        if (!newPassword.chars().anyMatch(ch -> "!@#$%^&*()-_+=<>?{}|\\[:;\"',./']".indexOf(ch) >= 0)) {
            return false;
        }
        
        return true;
    }

    // 顾客登录
    public boolean login(){
        System.out.println("开始登录，用户名或密码错误5次后账户将被锁定");
        int attempt = 0;
        int maxAttempt = 5;

        while(attempt < maxAttempt){
            System.out.println("请输入用户名：");
            String userName = sc.nextLine();
            System.out.println("请输入登录密码：");
            String password = sc.nextLine();
            String hashedPassword = User.hashPassword(password);

            Customer customer = findByName(userName);
            if(customer != null && hashedPassword.equals(customer.getPassword())){
                System.out.println("登录成功！");
                return true;
            }else{
                attempt++;
                System.out.println("用户名或密码错误。剩余尝试次数" + (maxAttempt - attempt));
            }
        }

        System.out.println("用户名或密码错误达到上线,账户已被锁定");
        return false;
    }

    private Customer findByName(String name){
        for(Customer customer : customers){
            if(customer.getUserName().equals(name)){
                return customer;
            }
        }

        return null;
    }

    // 修改密码
    public void changePassword(){
        // 提示输入用户名
		System.out.println("请输入用户名：");
		String name = sc.nextLine();
		// 提示用户输入新密码
		System.out.println("请输入新密码：");
		String newPassword = sc.nextLine();
		isVailedPassword(newPassword);
		// 提示用户再次输入新密码以确认
		System.out.println("请再次输入新密码以确认：");
		String newPasswordConfirm = sc.nextLine();
	
		if (newPassword.equals(newPasswordConfirm)) {
            String hasedPassword = User.hashPassword(newPassword);
			for(User user : User.users){
				if(user.getUserName().equals(name)){
					user.setPassword(hasedPassword);
				}else{
					System.out.println("输入的用户名不存在，请重新确认！");
				}
			}
			System.out.println("密码修改成功！");
		} else {
			System.out.println("两次输入的新密码不一致，密码修改失败！");
		}
        FileManager.writeUsersToFile(User.userfile, User.users);
        for(User user : User.users){
            if(user.getRole().equals(UserRole.CUSTOMER)){
                FileManager.copyFileContent(User.userfile, Customer.customerfile, UserRole.CUSTOMER.toString());
            }
        }
    }

    // 忘记密码
    public void forgetPassword(List<Customer> customers){
        System.out.println("请输入用户名：");
		String name = sc.nextLine();
        System.out.println("请输入用户邮箱地址：");
		String email = sc.nextLine();

        for(User customer : User.users){
            if(customer.getUserName().equals(name) && customer.getEmail().equals(email)){
                // 模拟发送重置密码邮件
                boolean emailSent = false;
                if(emailSent){
                    System.out.println("密码重置邮件已发送到邮箱，请登录查看新密码");
                }else{
                    System.out.println("发送邮件失败，请稍后重试");
                }
            }else{
                System.out.println("用户名或邮箱错误，请重新输入");
            }
        }
        FileManager.writeUsersToFile(User.userfile, User.users);
        for(User user : User.users){
            if(user.getRole().equals(UserRole.CUSTOMER)){
                FileManager.copyFileContent(User.userfile, Customer.customerfile, UserRole.CUSTOMER.toString());
            }
        }
    }

    // 查看电影信息
    public void listMovieMes(List<Movie> movies){
        if(movies == null){
            System.out.println("暂无信息！");
            return;
        }
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

    // 查询影片信息
    public void searchMovies(List<Movie> movies) {
        if(movies == null){
            System.out.println("暂无信息！");
            return;
        }
        System.out.println("请选择查询方式：");
        System.out.println("1. 根据名称查询");
        System.out.println("2. 根据导演查询");
        System.out.println("3. 根据主演查询");
        System.out.println("4. 组合查询（名称、导演、主演）");

        int choice = sc.nextInt();
        sc.nextLine(); 

        switch (choice) {
            case 1:
                System.out.println("请输入影片名称：");
                String targetMovieName = sc.nextLine();
                listMovieMes(searchMoviesByName(movies, targetMovieName));
                break;

            case 2:
                System.out.println("请输入导演名称：");
                String targetDirector = sc.nextLine();
                listMovieMes(searchMoviesByDirector(movies, targetDirector));
                break;

            case 3:
                System.out.println("请输入主演名称：");
                String targetMainActor = sc.nextLine();
                listMovieMes(searchMoviesByMainActor(movies, targetMainActor));
                break;

            case 4:
                System.out.println("请输入影片名称：");
                String nameQuery = sc.nextLine();
                System.out.println("请输入导演名称：");
                String directorQuery = sc.nextLine();
                System.out.println("请输入主演名称：");
                String mainActorQuery = sc.nextLine();
                listMovieMes(searchMoviesByCriteria(movies, nameQuery, directorQuery, mainActorQuery));
                break;

            default:
                System.out.println("选择无效，请重新选择！");
                break;
        }
    }

    private List<Movie> searchMoviesByName(List<Movie> movies, String name) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieName().equalsIgnoreCase(name)) {
                result.add(movie);
            }
        }
        return result;
    }

    private List<Movie> searchMoviesByDirector(List<Movie> movies, String director) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieDirector().equalsIgnoreCase(director)) {
                result.add(movie);
            }
        }
        return result;
    }

    private List<Movie> searchMoviesByMainActor(List<Movie> movies, String mainActor) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMainActor().equalsIgnoreCase(mainActor)) {
                result.add(movie);
            }
        }
        return result;
    }

    private List<Movie> searchMoviesByCriteria(List<Movie> movies, String name, String director, String mainActor) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if ((name.isEmpty() || movie.getMovieName().equalsIgnoreCase(name)) &&
                (director.isEmpty() || movie.getMovieDirector().equalsIgnoreCase(director)) &&
                (mainActor.isEmpty() || movie.getMainActor().equalsIgnoreCase(mainActor))) {
                result.add(movie);
            }
        }
        return result;
    }

    // 购票
    public void buyTickets(List<Schedule> schedules, List<Movie> movies) {
        String customerUsername = this.username;
        System.out.println("请选择电影：");
        listMovieMes(movies);

        if (movies == null || movies.isEmpty()) {
            System.out.println("没有可用的电影！");
            return;
        }
    
        int movieChoice = sc.nextInt();
        sc.nextLine();
    
        if (movieChoice < 1 || movieChoice > movies.size()) {
            System.out.println("选择无效");
            return;
        }
    
        Movie selectedMovie = movies.get(movieChoice - 1);
    
        List<Schedule> availableSchedules = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (schedule.getMovie().equals(selectedMovie)) {
                availableSchedules.add(schedule);
            }
        }
    
        if (availableSchedules.isEmpty()) {
            System.out.println("该电影没有场次！");
            return;
        }
    
        // 选择场次
        System.out.println("请选择场次：");
        listSchedules(availableSchedules);
    
        int scheduleChoice = sc.nextInt();
        sc.nextLine();
    
        if (scheduleChoice < 1 || scheduleChoice > availableSchedules.size()) {
            System.out.println("选择无效");
            return;
        }
    
        Schedule selectedSchedule = availableSchedules.get(scheduleChoice - 1);
    
        // 显示场次的座位信息
        System.out.println("场次座位信息：");
        selectedSchedule.getHall().showSeat();
    
        // 输入购票数量
        System.out.println("请输入购票的数量：");
        int ticketsNum = sc.nextInt();
        sc.nextLine();
    
        // 为每一张票选定一个座位
        for (int i = 0; i < ticketsNum; i++) {
            System.out.println("请输入第 " + (i + 1) + " 张票的座位号(行号，列号)：");
            String input = sc.nextLine();
    
            String[] jiexi = input.split(",");
            if (jiexi.length != 2) {
                System.out.println("无效的座位号格式，请使用行号和列号，用逗号隔开。");
                i--; // 重新输入
                continue;
            }
    
            int row = Integer.parseInt(jiexi[0]);
            int col = Integer.parseInt(jiexi[1]);
            
            // 检查座位是否可用
            if (!areSeatsAvailable(selectedSchedule, row, col)) {
                System.out.println("选定的座位不可用！");
                i--; 
                continue;
            }
    
            // 创建电影票对象
            Ticket.setPurchaseTime();
            Ticket ticket = new Ticket(Ticket.generateElectronicID(), Ticket.getPurchaseTime(), customerUsername, selectedSchedule, row, col);
    
            // 将电影票添加到用户的购票历史中
            addToPurchaseHistory(ticket);
    
            // 将座位设为已占用
            occupySeat(selectedSchedule, row, col);
        }
    
        // 输入支付金额
        System.out.println("请输入支付金额：");
        double paymentAmount = sc.nextDouble();
        sc.nextLine(); 

        // 根据用户级别计算折扣
        double discount = 1.0; // 默认无折扣

        if ("金牌用户".equals(getUserLevel())) {
            discount = 0.88; // 金牌用户享受 88 折
        } else if ("银牌用户".equals(getUserLevel())) {
            discount = 0.95; // 银牌用户享受 95 折
        }
        double pay = selectedSchedule.getPrice() * discount;
    
        // 模拟支付操作，如果支付成功
    if (simulatePayment(pay)) {
        System.out.println("支付成功！");
        System.out.println("找零 " + (paymentAmount - pay) + "元");
    } else {
        System.out.println("支付失败或用户取消购票，座位已释放。");
        // 释放已锁定的座位
        releaseSeats(selectedSchedule);
    }
    }

    // 添加到购票历史
    private void addToPurchaseHistory(Ticket ticket) {
        Ticket.purchaseHistory.add(ticket);
    }
    
    private boolean areSeatsAvailable(Schedule schedule, int row, int col) {
        Hall hall = schedule.getHall();
        Seat seat = hall.getSeat(row, col);
        return !seat.isOccupied();
    }
    
    private void occupySeat(Schedule schedule, int row, int col) {
        Hall hall = schedule.getHall();
        Seat seat = hall.getSeat(row, col);
        seat.occupy();
    }
    
    private void releaseSeats(Schedule schedule) {
        Hall hall = schedule.getHall();
        
        for (int row = 1; row <= hall.numRows; row++) {
            for (int col = 1; col <= hall.seatsPerRow; col++) {
                Seat seat = hall.getSeat(row, col);
                if (seat.isLocked()) {
                    seat.release(); 
                }
            }
        }
    }
    
    private boolean simulatePayment(double amount) {
        if (amount > 0) {
            System.out.println("支付成功！");
            return true;
        } else {
            System.out.println("支付失败！");
            return false;
        }
    }    

    // 列出可用场次
private void listSchedules(List<Schedule> schedules) {
    int scheduleNumber = 1;
    for (Schedule schedule : schedules) {
        System.out.println(scheduleNumber + ". " + schedule.getTime() + " - " + schedule.getHall().getHallNumber());
        scheduleNumber++;
    }
}

    // 取票
    public void gettTicket(List<Ticket> tickets){
        System.out.println("请输入电影票的电子ID编号：");
        String electronicID = sc.nextLine();

        Ticket selectedTicket = null;
        for (Ticket ticket : tickets) {
            if (ticket.getElectronicID().equals(electronicID)) {
                selectedTicket = ticket;
                break;
            }
        }

        // 检查是否找到了对应的电影票
        if (selectedTicket == null) {
            System.out.println("无效的电子ID编号，未找到相应的电影票！");
        }else {
        // 检查电影票是否已经取出
        if (!selectedTicket.gettTicket()) {
            System.out.println("电影票已经被取出，不能重复取票！");
        } else {
            // 显示电影票信息
            System.out.println("电影票信息：");
            System.out.println("放映厅：" + selectedTicket.getSchedule().getHall().getHallNumber());
            System.out.println("放映时间：" + selectedTicket.getSchedule().getTime());
            System.out.println("片长：" + selectedTicket.getSchedule().getMovie().getTimeLength());
            System.out.println("片名：" + selectedTicket.getSchedule().getMovie().getMovieName());
            System.out.println("座位信息：" + "行" + selectedTicket.getRow() + " 列" + selectedTicket.getCol());

            // 标记电影票为已取出状态
            selectedTicket.setGetted(true);
            System.out.println("电影票已成功取出！");
        }
    }
    }

    // 查看购票历史
    public void seePurchaseHistory() {
        if (Ticket.purchaseHistory.isEmpty()) {
            System.out.println("您的购票历史为空！");
            return;
        } else {
            System.out.println("购票历史：");
            for (Ticket ticket : Ticket.purchaseHistory) {
                System.out.println("购票时间：" + ticket.getPurchaseTime());
                System.out.println("电子ID编号：" + ticket.getElectronicID());
                System.out.println("放映厅：" + ticket.getSchedule().getHall().getHallNumber());
                System.out.println("放映时间：" + ticket.getSchedule().getTime());
                System.out.println("片名：" + ticket.getSchedule().getMovie().getMovieName());
                System.out.println("座位信息：" + "行" + ticket.getRow() + " 列" + ticket.getCol());
                System.out.println();
            }
        }
    }
    
}
