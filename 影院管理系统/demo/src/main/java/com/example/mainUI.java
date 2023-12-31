package com.example;

import java.util.Scanner;

import com.example.User.UserRole;

public class mainUI {
	public void mainUI(){
        Scanner sc = new Scanner(System.in);
		System.out.println("\n==================================== ");
        System.out.println("| WelCome to Cinema Management System |");
        System.out.println("|            (Version 1.4)            |");
        System.out.println("====================================== ");
        System.out.println(" 1.Admin                               ");
        System.out.println(" 2.Customer                            ");
        System.out.println(" 3.Front_Desk                          ");
        System.out.println(" 4.Manager                             ");
        System.out.println("====================================== ");
        System.out.println("请选择你的角色： ");

        int roleChoice = sc.nextInt();
        sc.nextLine();

        switch(roleChoice){
            case 1:
                AdminUI.adminUI();
                break;
            case 2:
                // 创建一个示例顾客对象，使用示例数据初始化
                Customer customer = new Customer("", "", "", "", "", UserRole.CUSTOMER, "");
                CustomerUI customerUI = new CustomerUI(customer);
                customerUI.customerUI();
                break;
            case 3:
                Front_Desk front_Desk = new Front_Desk("", "", "", "", "", UserRole.FRONT_DESK, "");
                FrontDeskUI frontDeskUI = new FrontDeskUI(front_Desk);
                frontDeskUI.frontdeskUI();
                break;
            case 4:
                Manager manager = new Manager("", "", "", "", "", UserRole.MANAGER, "");
                ManagerUI managerUI = new ManagerUI(manager);
                managerUI.managerUI();
                break;
            default:
                System.out.println("选择无效，请重新选择");
                return;
        }
	}
}
