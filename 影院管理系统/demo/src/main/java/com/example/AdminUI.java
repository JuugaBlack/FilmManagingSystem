package com.example;

import java.util.List;
import java.util.Scanner;

import com.example.User.UserRole;

public class AdminUI {
    static String adminfilepath = "管理员V1.4.xlsx";
    protected static List<Admin> adminList = FileManager.readAdminsFromFile(adminfilepath);
    protected static Admin admin = adminList.get(0);
    
    public static void adminUI(){
        Scanner sc = new Scanner(System.in);
        System.out.println("管理员登录……");
        System.out.println("请输入管理员账号：");
        String adminName = sc.nextLine();
        System.out.println("请输入管理员密码：");
        String adminPassword = sc.nextLine();

        boolean adminLogIn = admin.login(adminName, adminPassword);

        if (adminLogIn){
            System.out.println("管理员登录成功！");

        while (true) {
            System.out.println("管理员操作菜单：");
            System.out.println("1. 修改密码");
            System.out.println("2. 重置密码");
            System.out.println("3. 列出所有用户信息");
            System.out.println("4. 删除影城方用户信息");
            System.out.println("5. 查询影城方用户信息");
            System.out.println("6. 增加影城方用户信息");
            System.out.println("7. 修改影城方用户信息");
            System.out.println("8. 退出登录");

            int adminChoice = sc.nextInt();
            sc.nextLine(); 

            switch (adminChoice) {
                case 1:
                    admin.changePassword();
                    break;
                case 2:
                    System.out.println("请输入要重置密码的用户名（仅限经理和前台）:");
                    String targetName = sc.nextLine();
                    System.out.println("请选择该账号的角色：");
                    System.out.println("1. Maneger");
                    System.out.println("2. Front_Desk");
                    UserRole targetRole = null;
                    
                    int roleChoice = sc.nextInt();
                    sc.nextLine();

                    switch(roleChoice){
                        case 1:
                        targetRole = UserRole.MANAGER;
                        break;

                        case 2:
                        targetRole = UserRole.FRONT_DESK;
                    }
                    
                    admin.resetPassword(targetName, targetRole);
                    break;
                case 3:
                    admin.listCinemaStaff();
                    break;
                case 4:
                    admin.deleteCinemaStaff();
                    break;
                case 5:
                    admin.searchCinemaStaff();
                    break;
                case 6:
                    admin.addCinemaStaff();
                    break;
                case 7:
                    admin.changeCinemaStaff();
                    break;
                case 8:
                    admin.logout();
                    System.out.println("管理员已注销，即将返回上一级界面。");
                    return;
                default:
                    System.out.println("无效的选择，请重新选择操作。");
                    break;
                }
            }
        }else {
            System.out.println("管理员登录失败！");
        }
    }
}
