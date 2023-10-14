package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.example.User.UserRole;

public class FileManager {
    public static List<Admin> readAdminsFromFile(String filePath) {
        List<Admin> admins = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // 假设管理员信息位于第一个工作表

            for(Row row : sheet){
                if(row.getRowNum() == 0){
                    continue; //跳过标题行
                }

                Cell adminNameCell = row.getCell(0);
                Cell adminPasswordCell = row.getCell(1);
                Cell userIdCell = row.getCell(2);
                Cell phoneNumberCell = row.getCell(3);
                Cell emailCell = row.getCell(4);
                Cell roleCell = row.getCell(5);
                Cell registerTimeCell = row.getCell(6);

                String adminName = adminNameCell.getStringCellValue();
                String adminPassword = adminPasswordCell.getStringCellValue();
                String userId = userIdCell.getStringCellValue();
                String phoneNumber = phoneNumberCell.getStringCellValue();
                String email = emailCell.getStringCellValue();
                UserRole role = UserRole.valueOf(roleCell.getStringCellValue());
                String registerTime = registerTimeCell.getStringCellValue();

                Admin admin = new Admin(adminName, adminPassword, userId, phoneNumber, email, role, registerTime);
                admins.add(admin);
            }
            workbook.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return admins;
    }

    public static void writeAdminsToFile(String filePath, List<Admin> admins) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Admins"); // 创建一个名为"Admins"的工作表
   
        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("用户名");
        headerRow.createCell(1).setCellValue("密码");
        headerRow.createCell(2).setCellValue("用户ID");
        headerRow.createCell(3).setCellValue("电话号码");
        headerRow.createCell(4).setCellValue("电子邮件");
        headerRow.createCell(5).setCellValue("角色");
        headerRow.createCell(6).setCellValue("注册时间");
   
        int rowNum = 1;
        for (Admin admin : admins) {
            Row row = sheet.createRow(rowNum++);
   
            // 在每个单元格中写入管理员信息
            row.createCell(0).setCellValue(admin.getUserName());
            row.createCell(1).setCellValue(admin.getPassword());
            row.createCell(2).setCellValue(admin.getUserID());
            row.createCell(3).setCellValue(admin.getPhoneNumber());
            row.createCell(4).setCellValue(admin.getEmail());
            row.createCell(5).setCellValue(admin.getRole().toString());
            row.createCell(6).setCellValue(admin.getRegisterTime());
        }
   
        // 将工作簿写入文件
        try (FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
            workbook.write(outputStream);
            System.out.println("管理员信息已写入Excel文件：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public static <T extends User> List<T> readUsersFromFile(String filePath, Class<T> userType) {
        List<T> users = new ArrayList<>();

        try {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // 假设用户信息位于第一个工作表

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // 跳过标题行
            }

            Cell userNameCell = row.getCell(0);
            Cell userPasswordCell = row.getCell(1);
            Cell userIdCell = row.getCell(2);
            Cell phoneNumberCell = row.getCell(3);
            Cell emailCell = row.getCell(4);
            Cell roleCell = row.getCell(5);
            Cell registerTimeCell = row.getCell(6);

            String userName = userNameCell.getStringCellValue();
            String userPassword = userPasswordCell.getStringCellValue();
            String userId = userIdCell.getStringCellValue();
            String phoneNumber = phoneNumberCell.getStringCellValue();
            String email = emailCell.getStringCellValue();
            UserRole role = UserRole.valueOf(roleCell.getStringCellValue());
            String registerTime = registerTimeCell.getStringCellValue();

            // 根据传入的用户类型创建用户对象
            T user = null;
            try {
                Constructor<T> constructor = userType.getConstructor(String.class, String.class, String.class, String.class, String.class, UserRole.class, String.class);
                user = constructor.newInstance(userName, userPassword, userId, phoneNumber, email, role, registerTime);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }

            if (user != null) {
                users.add(user);
            }
        }

        workbook.close();
        fileInputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return users;
}

    public static <T extends User> void writeUsersToFile(String filePath, List<T> users) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users"); // 创建一个名为"Users"的工作表
    
            // 创建标题行
            Row headerRow = sheet.createRow(0);
            String[] headers = {"用户名", "密码", "用户ID", "电话号码", "电子邮件", "角色", "注册时间"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
    
            // 写入用户数据
            for (int i = 0; i < users.size(); i++) {
                Row row = sheet.createRow(i + 1);
                T user = users.get(i);
                row.createCell(0).setCellValue(user.getUserName());
                row.createCell(1).setCellValue(user.getPassword());
                row.createCell(2).setCellValue(user.getUserID());
                row.createCell(3).setCellValue(user.getPhoneNumber());
                row.createCell(4).setCellValue(user.getEmail());
                row.createCell(5).setCellValue(user.getRole().toString());
                row.createCell(6).setCellValue(user.getRegisterTime());
            }
    
            // 将数据写入Excel文件
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
    
            workbook.close();
            System.out.println("用户信息已写入Excel文件：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCustomersToExcel(String filePath, List<Customer> customers) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Customers");

            // 创建标题行
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "用户名", "密码", "用户ID", "电话号码", "邮箱",
                "角色", "注册时间", "用户等级", "消费总数", "消费次数"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 填充数据行
            int rowNum = 1;
            for (Customer customer : customers) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(customer.getUserName());
                dataRow.createCell(1).setCellValue(customer.getPassword());
                dataRow.createCell(2).setCellValue(customer.getUserID());
                dataRow.createCell(3).setCellValue(customer.getPhoneNumber());
                dataRow.createCell(4).setCellValue(customer.getEmail());
                dataRow.createCell(5).setCellValue(customer.getRole().toString());
                dataRow.createCell(6).setCellValue(customer.getRegisterTime());
                dataRow.createCell(7).setCellValue(customer.getUserLevel());
                dataRow.createCell(8).setCellValue(customer.getAccumulatedConsume());
                dataRow.createCell(9).setCellValue(customer.getAccumulatedTimes());
            }

            // 保存到文件
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                System.out.println("已成功保存。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> readCustomersFromExcel(String filePath) {
        List<Customer> customers = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // 假设客户信息位于第一个工作表

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // 跳过标题行
                }

                Cell usernameCell = row.getCell(0);
                Cell passwordCell = row.getCell(1);
                Cell userIDCell = row.getCell(2);
                Cell phoneNumberCell = row.getCell(3);
                Cell emailCell = row.getCell(4);
                Cell roleCell = row.getCell(5);
                Cell registerTimeCell = row.getCell(6);
                Cell userLevelCell = row.getCell(7);
                Cell accumulatedConsumeCell = row.getCell(8);
                Cell accumulatedTimesCell = row.getCell(9);

                String username = usernameCell.getStringCellValue();
                String password = passwordCell.getStringCellValue();
                String userID = userIDCell.getStringCellValue();
                String phoneNumber = phoneNumberCell.getStringCellValue();
                String email = emailCell.getStringCellValue();
                UserRole role = UserRole.valueOf(roleCell.getStringCellValue());
                String registerTime = registerTimeCell.getStringCellValue();
                String userLevel = userLevelCell.getStringCellValue();
                double accumulatedConsume = accumulatedConsumeCell.getNumericCellValue();
                int accumulatedTimes = (int) accumulatedTimesCell.getNumericCellValue();

                Customer customer = new Customer(username, password, userID, phoneNumber, email, role, registerTime);
                customer.setUserLevel(userLevel);
                customer.setAccumulatedConsume(accumulatedConsume);
                customer.setAccumulatedTimes(accumulatedTimes);

                customers.add(customer);
            }

            workbook.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static void writeMoviesToFile(String filePath, List<Movie> movies) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Movies"); // 创建一个名为"Movies"的工作表
    
            // 创建标题行
            Row headerRow = sheet.createRow(0);
            String[] headers = {"电影名称", "导演", "主演", "简介", "电影时长"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
    
            // 写入电影数据
            for (int i = 0; i < movies.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Movie movie = movies.get(i);
                row.createCell(0).setCellValue(movie.getMovieName());
                row.createCell(1).setCellValue(movie.getMovieDirector());
                row.createCell(2).setCellValue(movie.getMainActor());
                row.createCell(3).setCellValue(movie.getInformation());
                row.createCell(4).setCellValue(movie.getTimeLength());
            }
    
            // 将数据写入Excel文件
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
    
            workbook.close();
            System.out.println("电影信息已写入Excel文件：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Movie> readMoviesFromFile(String filePath) {
        List<Movie> movies = new ArrayList<>();

    try {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // 假设电影信息位于第一个工作表

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // 跳过标题行
            }

            Cell titleCell = row.getCell(0);
            Cell directorCell = row.getCell(1);
            Cell actorsCell = row.getCell(2);
            Cell plotCell = row.getCell(3);
            Cell durationCell = row.getCell(4);

            String title = titleCell.getStringCellValue();
            String director = directorCell.getStringCellValue();
            String actors = actorsCell.getStringCellValue();
            String plot = plotCell.getStringCellValue();
            String duration = durationCell.getStringCellValue();

            Movie movie = new Movie(title, director, actors, plot, duration);
            movies.add(movie);
        }

        workbook.close();
        fileInputStream.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return movies;
}

    public static void writeSchedulesToFile(String filePath, List<Schedule> schedules) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Schedules"); // 创建一个名为"Schedules"的工作表
    
            // 创建标题行
            Row headerRow = sheet.createRow(0);
            String[] headers = {"电影名称", "放映厅", "电影时长", "价格"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
    
            // 写入场次数据
            for (int i = 0; i < schedules.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Schedule schedule = schedules.get(i);
                row.createCell(0).setCellValue(schedule.getMovie().getMovieName());
                row.createCell(1).setCellValue(schedule.getHall().hallNumtoString());
                row.createCell(2).setCellValue(schedule.getTime());
                row.createCell(3).setCellValue(schedule.getPrice());
            }
    
            // 将数据写入Excel文件
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
    
            workbook.close();
            System.out.println("场次信息已写入Excel文件：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Schedule> readSchedulesFromFile(String filePath, List<Movie> movies, List<Hall> halls) {
        List<Schedule> schedules = new ArrayList<>();

    try {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // 假设排片信息位于第一个工作表

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // 跳过标题行
            }

            Cell movieNameCell = row.getCell(0);
            Cell hallNameCell = row.getCell(1);
            Cell timeCell = row.getCell(2);
            Cell priceCell = row.getCell(3);

            String movieName = movieNameCell.getStringCellValue();
            String hallName = hallNameCell.getStringCellValue();
            String time = timeCell.getStringCellValue();
            double price = priceCell.getNumericCellValue();

            // 根据电影名称查找电影对象
            Movie movie = findMovieByName(movies, movieName);
            // 根据放映厅名称查找放映厅对象
            Hall hall = findHallByName(halls, hallName);

            if (movie != null && hall != null) {
                Schedule schedule = new Schedule(movie, hall, time, price);
                schedules.add(schedule);
            }
        }

        workbook.close();
        fileInputStream.close();
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
        try {
            FileInputStream sourceFileInputStream = new FileInputStream(new File(sourceFilePath));
            Workbook sourceWorkbook = new XSSFWorkbook(sourceFileInputStream);
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0); // 假设数据位于第一个工作表
    
            Workbook destinationWorkbook = new XSSFWorkbook();
            Sheet destinationSheet = destinationWorkbook.createSheet("CopiedData"); // 创建一个新的工作表用于复制数据
    
            int destinationRowIndex = 0;
    
            for (Row sourceRow : sourceSheet) {
                boolean foundKeyword = false;
                for (Cell cell : sourceRow) {
                    if (cell.getCellType() == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        if (cellValue.contains(keyword)) {
                            foundKeyword = true;
                            Row destinationRow = destinationSheet.createRow(destinationRowIndex++);
                            for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
                                Cell destinationCell = destinationRow.createCell(i);
                                destinationCell.setCellValue(sourceRow.getCell(i).getStringCellValue());
                            }
                            break; // 不需要再继续查找关键字，已找到
                        }
                    }
                }
    
                if (!foundKeyword) {
                    // 清空源行
                    sourceRow.getCell(0).setCellValue("");
                }
            }
    
            // 将复制的数据写入目标文件
            try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath)) {
                destinationWorkbook.write(fileOutputStream);
            }
    
            sourceFileInputStream.close();
            destinationWorkbook.close();
            return true; // 复制成功
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 复制失败
        }
    }

    // 清空文件的函数
    public static boolean clearFile(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
    
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        cell.setCellValue("");
                    }
                }
            }
    
            // 将清空后的数据写回文件
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
            }
    
            fileInputStream.close();
            workbook.close();
            return true; // 清空成功
        } catch (IOException e) {
            e.printStackTrace();
            return false; // 清空失败
        }
    }
}
