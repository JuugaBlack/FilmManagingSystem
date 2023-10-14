package FilmHubTutorialV11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Manager extends User{
    static String managerfile = "经理V1.1.txt";
    protected static List<Manager> managers = FileManager.readUsersFromFile(managerfile, Manager.class);

    public Manager(String username, String password, String userID, String phoneNumber, String email, UserRole role, String registerTime) {
        super(username, password, userID, phoneNumber, email, role,registerTime);
    }
    
    protected void resetPassword(String targetName){
        UserRole targetRole = UserRole.CONSUMER;

        boolean foundUser = false;
        for(User user : User.users){
            if(user.getUserName().equals(targetName) && user.getRole().equals(targetRole)){
                user.setPassword("123456");
                foundUser = true;
                System.out.println("密码已重置为 123456 ");
                break;
            }
        }

        if(foundUser == false){
            System.out.println("未找到用户！");
        }
        FileManager.writeUsersToFile(User.userfile, User.users);
    }

    // 列出电影信息
    public void listMovieMes(List<Movie> movies){
        for(int i=0;i<movies.size();i++){
            Movie movie = movies.get(i);
            System.out.print((i+1) + ".");
            System.out.println("片名：" + movie.getMovieName());
            System.out.println("导演：" + movie.getMovieDirector());
            System.out.println("主演: " + movie.getMainActor());
            System.out.println("剧情简介： " + movie.getInformation());
            System.out.println("时长： " + movie.getTimeLength());
            System.out.println("--------------------------------");
        }
    }

    // 添加电影信息
    protected void addMovieMes(){
        Movie newMovie = new Movie();
        System.out.println("请依次输入你要添加的影片的信息：");

        System.out.print("片名：");
        String newMovieName = sc.nextLine();
        newMovie.setMovieName(newMovieName);

        System.out.print("导演：");
        String newMovieDirector = sc.nextLine();
        newMovie.setMovieDirector(newMovieDirector);

        System.out.print("主演: ");
        String newMovieMainActor = sc.nextLine();
        newMovie.setMainActor(newMovieMainActor);

        System.out.print("剧情简介： ");
        String newMovieInformation = sc.nextLine();
        newMovie.setInformation(newMovieInformation);
        
        System.out.print("时长： ");
        String newMovieTimeLength = sc.nextLine();
        newMovie.setTimeLength(newMovieTimeLength);

        Movie.movieList.add(newMovie);
        System.out.println("添加成功！");
        FileManager.writeMoviesToFile(Movie.moviefile, Movie.movieList);
    }

    // 修改电影信息
    protected void changeMovieMes(){
        System.out.println("请输入要修改的影片的片名：");
        String targetMovieName = sc.nextLine();

        for (Movie movie : Movie.movieList) {
            if (movie.getMovieName().equals(targetMovieName)) {
                System.out.println("请选择要修改的属性：");
                System.out.println("1. 片名");
                System.out.println("2. 导演");
                System.out.println("3. 主演");
                System.out.println("4.剧情简介");
                System.out.println("5.时长");

                int choice = sc.nextInt();
                sc.nextLine(); // 清除换行符

                switch (choice) {
                    case 1:
                        System.out.print("请输入新的片名：");
                        String newMovieName = sc.nextLine();
                        movie.setMovieName(newMovieName);
                        System.out.println("修改成功");
                        break;
                    case 2:
                        System.out.print("请输入新的导演：");
                        String newDirector = sc.nextLine();
                        movie.setMovieDirector(newDirector);
                        System.out.println("修改成功");
                        break;
                    case 3:
                        System.out.print("请输入新的主演：");
                        String newMainActor = sc.nextLine();
                        movie.setMainActor(newMainActor);
                        System.out.println("修改成功");
                        break;

                    case 4:
                        System.out.print("请输入新的剧情简介：");
                        String newInformation = sc.nextLine();
                        movie.setInformation(newInformation);
                        System.out.println("修改成功");
                        break;

                    case 5:
                        System.out.print("请输入新的时长：");
                        String newTimeLength = sc.nextLine();
                        movie.setTimeLength(newTimeLength);
                        System.out.println("修改成功");
                        break;

                    default:
                        System.out.println("请重新选择！");
                        break;
                }

                System.out.println("影片信息已更新！");
                break;
            }
        }
        FileManager.writeMoviesToFile(Movie.moviefile, Movie.movieList);
    }

   // 删除影片信息
   protected void deleteMovieMes(){
    System.out.println("请输入要删除的影片名：");
    String targetMovieName = sc.nextLine();

    Iterator<Movie> iterator = Movie.movieList.iterator();
    boolean found = false; // 用于标记是否找到匹配的影片

    while(iterator.hasNext()){
        Movie movie = iterator.next();

        if(movie.getMovieName().equals(targetMovieName)){
            found = true;
            System.out.println("找到影片：" + movie.getMovieName());
            System.out.println("删除后无法恢复，请确认是否删除（y/n）：");
            String resure = sc.nextLine();

            if(resure.equals("y")){
                iterator.remove();
                System.out.println("删除成功！");
            }else if(resure.equals("n")){
                System.out.println("取消删除操作。");
            }else{
                System.out.println("操作错误，请选择y/n");
            }

            break; // 找到匹配的影片后，跳出循环
        }
    }

    if (!found) {
        System.out.println("未找到该影片，请重新输入");
    }

    FileManager.writeMoviesToFile(Movie.moviefile, Movie.movieList);
} 

    // 查询影片信息
    protected void searchMovies() {
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
                listMovieMes(searchMoviesByName(targetMovieName));
                break;

            case 2:
                System.out.println("请输入导演名称：");
                String targetDirector = sc.nextLine();
                listMovieMes(searchMoviesByDirector(targetDirector));
                break;

            case 3:
                System.out.println("请输入主演名称：");
                String targetMainActor = sc.nextLine();
                listMovieMes(searchMoviesByMainActor(targetMainActor));
                break;

            case 4:
                System.out.println("请输入影片名称：");
                String nameQuery = sc.nextLine();
                System.out.println("请输入导演名称：");
                String directorQuery = sc.nextLine();
                System.out.println("请输入主演名称：");
                String mainActorQuery = sc.nextLine();
                listMovieMes(searchMoviesByCriteria(nameQuery, directorQuery, mainActorQuery));
                break;

            default:
                System.out.println("选择无效，请重新选择！");
                break;
        }
    }

    private List<Movie> searchMoviesByName(String name) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : Movie.movieList) {
            if (movie.getMovieName().equalsIgnoreCase(name)) {
                result.add(movie);
            }
        }
        return result;
    }

    private List<Movie> searchMoviesByDirector(String director) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : Movie.movieList) {
            if (movie.getMovieDirector().equalsIgnoreCase(director)) {
                result.add(movie);
            }
        }
        return result;
    }

    private List<Movie> searchMoviesByMainActor(String mainActor) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : Movie.movieList) {
            if (movie.getMainActor().equalsIgnoreCase(mainActor)) {
                result.add(movie);
            }
        }
        return result;
    }

    private List<Movie> searchMoviesByCriteria(String name, String director, String mainActor) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : Movie.movieList) {
            if ((name.isEmpty() || movie.getMovieName().equalsIgnoreCase(name)) &&
                (director.isEmpty() || movie.getMovieDirector().equalsIgnoreCase(director)) &&
                (mainActor.isEmpty() || movie.getMainActor().equalsIgnoreCase(mainActor))) {
                result.add(movie);
            }
        }
        return result;
    }

    // 添加电影场次
    public void addMovieSchedule(List<Schedule> schedules, List<Hall> halls) {
        listMovieMes(Movie.movieList);
        System.out.println("请选择要添加场次的电影：");
        int movieChoice = sc.nextInt();
        sc.nextLine();
        Movie selectedMovie = Movie.movieList.get(movieChoice - 1);
    
        System.out.println("请选择放映厅(1~5)：");
        int hallChoice = sc.nextInt();
        sc.nextLine();
        Hall selectedHall = halls.get(hallChoice - 1);
    
        System.out.println("请输入场次时间（yyyy-MM-dd HH:mm）：");
        String showTime = sc.nextLine();
    
        System.out.println("请输入电影价格：");
        double moviePrice = sc.nextDouble();
        sc.nextLine();
    
        // 创建新的 Schedule 对象并添加到列表中
        Schedule newSchedule = new Schedule();
        newSchedule.movie = selectedMovie;
        newSchedule.hall = selectedHall;
        newSchedule.setPrice(moviePrice);
        newSchedule.setTime(showTime);
    
        schedules.add(newSchedule);
    
        System.out.println("电影场次已成功添加！");
        FileManager.writeSchedulesToFile(Schedule.schedfile, schedules);
    }

    // 修改电影场次
    protected void changeMovieSchedule(List<Schedule> schedules, List<Hall> halls){
        listMovieSchedule(schedules);
        System.out.println("请选择要修改的场次：");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > schedules.size()) {
            System.out.println("选择无效");
            return;
        }

        Schedule selectedSchedule = schedules.get(choice - 1);
        System.out.println("请选择操作：");
        System.out.println("1. 修改放映电影");
        System.out.println("2. 修改放映厅");
        System.out.println("3. 修改时段");
        System.out.println("4. 修改价格");
        System.out.println("5. 空场");
        int operationChoice = sc.nextInt();
        sc.nextLine();

        switch(operationChoice){
            case 1:
            System.out.println("请选择新电影：");
            int movieChoice = sc.nextInt();
            sc.nextLine();
            if (movieChoice >= 1 && movieChoice <= Movie.movieList.size()) {
                Movie selectedMovie = Movie.movieList.get(movieChoice - 1);
                selectedSchedule.movie = selectedMovie;
                System.out.println("修改完成！");
            } else {
                System.out.println("选择无效");
            }
            break;

            case 2:
            System.out.println("请选择新影厅(1~5)：");
            int hallChoice = sc.nextInt();
            sc.nextLine();
            if(hallChoice >= 1 && hallChoice <= 5){
                Hall selectedHall = halls.get(hallChoice - 1);
                selectedSchedule.hall = selectedHall;
                System.out.println("修改完成！");
            }else{
                System.out.println("选择无效");
            }
            break;

            case 3:
            System.out.println("请填写新放映时段：");
            String timeChoice = sc.nextLine();
            selectedSchedule.setTime(timeChoice);
            System.out.println("修改完成！");
            break;

            case 4:
            System.out.println("请填写新价格：");
            double priceChoice = sc.nextDouble();
            sc.nextLine();
            selectedSchedule.setPrice(priceChoice);
            System.out.println("修改完成！");
            break;

            case 5:
            selectedSchedule.movie = null;
            selectedSchedule.hall = null;
            selectedSchedule.setTime("");
            selectedSchedule.setPrice(0.0);
            System.out.println("空场设置完成！");
            break;

            default:
            System.out.println("选择无效，请重新选择！");
        }
        FileManager.writeSchedulesToFile(Schedule.schedfile, schedules);
    }

    private void listMovieSchedule(List<Schedule> schedules) {
        for(int i=0;i<schedules.size();i++){
            Schedule schedule = schedules.get(i);
            System.out.println((i + 1) + ". " + schedule.movie.getMovieName() + " 在放映厅 " + schedule.hall.getHallNumber() + "，时间：" + schedule.getTime() + "，价格：" + schedule.getPrice());
        }
    }

    // 删除电影场次
    protected void deleteMovieSchedule(List<Schedule> schedules) {
        listAllSchedules(schedules);
        System.out.println("请选择要删除的场次：");
        int deleteChoice = sc.nextInt();
        sc.nextLine();
    
        Iterator<Schedule> iterator = schedules.iterator();
        int currentIndex = 1;
    
        while (iterator.hasNext()) {
            if (currentIndex == deleteChoice) {
                iterator.remove(); // 使用迭代器删除当前元素
                System.out.println("删除完成！");
                return; // 删除后退出循环
            }
            currentIndex++;
        }
    
        System.out.println("选择无效，请重新选择！");
        FileManager.writeSchedulesToFile(Schedule.schedfile, schedules);
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

    // 列出消费者信息
    protected void listCustomerMes(){
        for(Customer customer : Customer.customers){
            if (customer.getRole().equals(UserRole.CUSTOMER)) {
                System.out.println("用户ID：" + customer.getUserID());
                System.out.println("用户名：" + customer.getUserName());
                System.out.println("用户级别：" + customer.getUserLevel());
                System.out.println("用户注册时间：" + customer.getRegisterTime());
                System.out.println("用户累计消费总金额：" + customer.getAccumulatedConsume());
                System.out.println("用户累计消费次数：" + customer.getAccumulatedTimes());
                System.out.println("用户手机号：" + customer.getPhoneNumber());
                System.out.println("用户邮箱：" + customer.getEmail());
                System.out.println();
            }
        }
    }

    // 查询用户信息
    protected void searchCustomerMes(){
        System.out.println("请选择查询方式：");
        System.out.println("1. 根据用户ID查询");
        System.out.println("2. 根据用户名查询");
        System.out.println("3. 查询所有用户信息");

        int choice = sc.nextInt();
        sc.nextLine(); 

        switch (choice) {
            case 1:
                System.out.println("请输入用户ID：");
                String targetUserID = sc.nextLine();
                Customer foundByID = searchCustomerByID(Customer.customers, targetUserID);
                if (foundByID != null) {
                    displayCustomerInfo(foundByID);
                } else {
                    System.out.println("未找到该用户！");
                }
                break;
    
            case 2:
                System.out.println("请输入用户名：");
                String targetUsername = sc.nextLine();
                Customer foundByUsername = searchCustomerByUsername(Customer.customers, targetUsername);
                if (foundByUsername != null) {
                    displayCustomerInfo(foundByUsername);
                } else {
                    System.out.println("未找到该用户！");
                }
                break;
    
            case 3:
                listCustomerMes();
                break;
    
            default:
                System.out.println("选择无效，请重新选择！");
                break;
        }
    }
    
    private Customer searchCustomerByID(List<Customer> customers, String userID) {
        for (Customer customer : customers) {
            if (customer.getUserID().equals(userID)) {
                return customer;
            }
        }
        return null;
    }
    
    private Customer searchCustomerByUsername(List<Customer> customers, String username) {
        for (Customer customer : customers) {
            if (customer.getUserName().equals(username)) {
                return customer;
            }
        }
        return null;
    }
    
    private void displayCustomerInfo(Customer customer) {
        System.out.println("用户ID：" + customer.getUserID());
        System.out.println("用户名：" + customer.getUserName());
        System.out.println("用户级别：" + customer.getUserLevel());
        System.out.println("用户注册时间：" + customer.getRegisterTime());
        System.out.println("用户累计消费总金额：" + customer.getAccumulatedConsume());
        System.out.println("用户累计消费次数：" + customer.getAccumulatedTimes());
        System.out.println("用户手机号：" + customer.getPhoneNumber());
        System.out.println("用户邮箱：" + customer.getEmail());
        System.out.println();
    }

}