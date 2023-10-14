package FilmHubTutorialV11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FilmHubTutorialV11.User.UserRole;

public class FileManager {
    public static List<Admin> readAdminsFromFile(String filePath) {
        List<Admin> admins = new ArrayList<>();

        try {
            File file = new File(filePath);

            // 检查文件是否存在
            if (!file.exists()) {
                System.err.println("文件不存在：" + filePath);
                return admins; // 返回空的管理员列表
            }

            // 创建文件输入流
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] adminInfo = line.split(",");
                if (adminInfo.length == 7) {
                    String adminName = adminInfo[0].trim();
                    String adminPassword = adminInfo[1].trim();
                    String userId = adminInfo[2].trim();
                    String phoneNumber = adminInfo[3].trim();
                    String email = adminInfo[4].trim();
                    UserRole role = UserRole.valueOf(adminInfo[5].trim());
                    String registerTime = adminInfo[6].trim();

                    Admin admin = new Admin(adminName, adminPassword, userId, phoneNumber, email, role, registerTime);
                    admins.add(admin);
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return admins;
    }

    public static void writeAdminsToFile(String filePath, List<Admin> admins) {
        try {
            File file = new File(filePath);

            // 创建文件输出流
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Admin admin : admins) {
                // 将管理员信息以逗号分隔的形式写入文件
                String adminInfo = String.format("%s,%s,%s,%s,%s,%s",
                        admin.getUserName(),
                        admin.getPassword(),
                        admin.getUserID(),
                        admin.getPhoneNumber(),
                        admin.getEmail(),
                        admin.getRole(),
                        admin.getRegisterTime());

                bufferedWriter.write(adminInfo);
                bufferedWriter.newLine(); // 写入换行符，以便下一行数据
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends User> List<T> readUsersFromFile(String filePath, Class<T> userType) {
        List<T> users = new ArrayList<>();

        try {
            File file = new File(filePath);

            // 检查文件是否存在
            if (!file.exists()) {
                System.err.println("文件不存在：" + filePath);
                return users; // 返回空的用户列表
            }

            // 创建文件输入流
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 7) {
                    String userName = userInfo[0].trim();
                    String userPassword = userInfo[1].trim();
                    String userId = userInfo[2].trim();
                    String phoneNumber = userInfo[3].trim();
                    String email = userInfo[4].trim();
                    UserRole role = UserRole.valueOf(userInfo[5].trim());
                    String registerTime = userInfo[6].trim();

                    // 根据传入的用户类型创建用户对象
                    T user = null;
                    if (userType.equals(User.class) || userType.equals(Manager.class) || userType.equals(Front_Desk.class) || userType.equals(Customer.class)) {
                        user = userType.getConstructor(String.class, String.class, String.class, String.class, String.class, UserRole.class, String.class)
                                .newInstance(userName, userPassword, userId, phoneNumber, email, role, registerTime);
                    }

                    if (user != null) {
                        users.add(user);
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static <T extends User> void writeUsersToFile(String filePath, List<T> users) {
        try {
            File file = new File(filePath);

            // 创建文件输出流
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (T user : users) {
                // 将用户信息以逗号分隔的形式写入文件
                String userInfo = String.format("%s,%s,%s,%s,%s,%s,%s",
                        user.getUserName(),
                        user.getPassword(),
                        user.getUserID(),
                        user.getPhoneNumber(),
                        user.getEmail(),
                        user.getRole(),
                        user.getRegisterTime());

                        

                bufferedWriter.write(userInfo);
                bufferedWriter.newLine(); // 写入换行符，以便下一行数据
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomersToFile(String filePath, List<Customer> customers) {
        try {
            File file = new File(filePath);
    
            // 创建文件输出流
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    
            for (Customer customer : customers) {
                // 将客户信息以逗号分隔的形式写入文件
                String customerInfo = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        customer.getUserName(),
                        customer.getPassword(),
                        customer.getUserID(),
                        customer.getPhoneNumber(),
                        customer.getEmail(),
                        customer.getRole(),
                        customer.getRegisterTime(),
                        customer.getUserLevel(),
                        customer.getAccumulatedConsume(),
                        customer.getAccumulatedTimes());
    
                bufferedWriter.write(customerInfo);
                bufferedWriter.newLine(); // 写入换行符，以便下一行数据
            }
    
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Customer> readCustomersFromFile(String filePath) {
        List<Customer> customers = new ArrayList<>();
    
        try {
            File file = new File(filePath);
    
            // 检查文件是否存在
            if (!file.exists()) {
                System.err.println("文件不存在：" + filePath);
                return customers; // 返回空的客户列表
            }
    
            // 创建文件输入流
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
    
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo.length == 10) {
                    String userName = customerInfo[0].trim();
                    String password = customerInfo[1].trim();
                    String userID = customerInfo[2].trim();
                    String phoneNumber = customerInfo[3].trim();
                    String email = customerInfo[4].trim();
                    UserRole role = UserRole.valueOf(customerInfo[5].trim());
                    String registerTime = customerInfo[6].trim();
                    String userLevel = customerInfo[7].trim();
                    double accumulatedConsume = Double.parseDouble(customerInfo[8].trim());
                    int accumulatedTimes = Integer.parseInt(customerInfo[9].trim());
    
                    Customer customer = new Customer(userName, password, userID, phoneNumber, email, role, registerTime);
                    customer.setUserLevel(userLevel);
                    customer.setAccumulatedConsume(accumulatedConsume);
                    customer.setAccumulatedTimes(accumulatedTimes);
    
                    customers.add(customer);
                }
            }
    
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return customers;
    }    

    public static void writeMoviesToFile(String filePath, List<Movie> movies) {
        try {
            File file = new File(filePath);

            // 创建文件输出流
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Movie movie : movies) {
                // 将电影信息以逗号分隔的形式写入文件
                String movieInfo = String.format("%s,%s,%s,%s,%s",
                        movie.getMovieName(),
                        movie.getMovieDirector(),
                        movie.getMainActor(),
                        movie.getInformation(),
                        movie.getTimeLength());

                bufferedWriter.write(movieInfo);
                bufferedWriter.newLine(); // 写入换行符，以便下一行数据
            }

            bufferedWriter.close();
            System.out.println("电影信息已写入文件：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Movie> readMoviesFromFile(String filePath) {
        List<Movie> movies = new ArrayList<>();

        try {
            File file = new File(filePath);

            // 检查文件是否存在
            if (!file.exists()) {
                System.err.println("文件不存在：" + filePath);
                return movies; // 返回空的电影列表
            }

            // 创建文件输入流
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] movieInfo = line.split(",");
                if (movieInfo.length == 5) {
                    String title = movieInfo[0].trim();
                    String director = movieInfo[1].trim();
                    String actors = movieInfo[2].trim();
                    String plot = movieInfo[3].trim();
                    String duration = movieInfo[4].trim();

                    Movie movie = new Movie(title, director, actors, plot, duration);
                    movies.add(movie);
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static void writeSchedulesToFile(String filePath, List<Schedule> schedules) {
        try {
            File file = new File(filePath);

            // 创建文件输出流
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Schedule schedule : schedules) {
                // 将场次信息以逗号分隔的形式写入文件
                String ScheduleInfo = String.format("%s,%s,%s,%.2f",
                        schedule.getMovie().getMovieName(),
                        schedule.getHall().hallNumtoString(),
                        schedule.getTime(),
                        schedule.getPrice());

                bufferedWriter.write(ScheduleInfo);
                bufferedWriter.newLine(); // 写入换行符，以便下一行数据
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Schedule> readSchedulesFromFile(String filePath, List<Movie> movies, List<Hall> halls) {
        List<Schedule> schedules = new ArrayList<>();

        try {
            File file = new File(filePath);

            // 检查文件是否存在
            if (!file.exists()) {
                System.err.println("文件不存在：" + filePath);
                return schedules; // 返回空的场次列表
            }

            // 创建文件输入流
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] scheduleInfo = line.split(",");
                if (scheduleInfo.length == 4) {
                    String movieName = scheduleInfo[0].trim();
                    String hallName = scheduleInfo[1].trim(); // 读取放映厅名称
                    String time = scheduleInfo[2].trim();
                    double price = Double.parseDouble(scheduleInfo[3].trim());

                    // 根据电影名称查找电影对象
                    Movie movie = findMovieByName(movies, movieName);
                    // 根据放映厅名称查找放映厅对象
                    Hall hall = findHallByName(halls, hallName);

                    if (movie != null && hall != null) {
                        Schedule schedule = new Schedule(movie, hall, time, price);
                        schedules.add(schedule);
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return schedules;
    }

    private static Movie findMovieByName(List<Movie> movies, String movieName) {
        for (Movie movie : movies) {
            if (movie.getMovieName().equals(movieName)) {
                return movie;
            }
        }
        return null;
    }

    private static Hall findHallByName(List<Hall> halls, String hallNum) {
        for (Hall hall : halls) {
            if (hall.getHallNumber() == Integer.parseInt(hallNum)) {
                return hall;
            }
        }
        return null;
    }

    // 复制文件内容的函数
    public static boolean copyFileContent(String sourceFilePath, String destinationFilePath, String keyword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {

            String line;
            boolean foundKeyword = false;
            while ((line = reader.readLine()) != null) {
                // 根据条件选择是否复制该行内容
                if (line.contains(keyword)) {
                    writer.write(line);
                    writer.newLine(); // 写入新行
                    foundKeyword = true;
                }

                if(!foundKeyword){
                    writer.write("");
                }
            }

            return true; // 复制成功
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 复制失败
        }
    }

    // 清空文件的函数
    public static boolean clearFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // 将文件内容清空
            writer.write("");
            return true; // 清空成功
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 清空失败
        }
    }
}
