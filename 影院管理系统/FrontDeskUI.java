package FilmHubTutorialV10;

import java.util.Scanner;

public class FrontDeskUI {
    private Front_Desk front_Desk;

    public FrontDeskUI(Front_Desk front_Desk){
        this.front_Desk = front_Desk;
    }

    public void frontdeskUI(){
        Scanner sc = new Scanner(System.in);
        System.out.println("前台登录……");
        boolean frontdeskLogin = front_Desk.login();

        if(frontdeskLogin){
            System.out.println("前台登录成功!");

            while(true){
                System.out.println("前台操作菜单：");
                System.out.println("1. 列出上映的影片信息");
                System.out.println("2. 列出所有场次信息");
                System.out.println("3. 列出指定电影和场次的信息");
                System.out.println("4. 进行售票");
                System.out.println("5. 退出登录");

                int frontdeskChoice = sc.nextInt();
                sc.nextLine(); 

                switch(frontdeskChoice){
                    case 1:
                        front_Desk.listMovieMes(Movie.movieList);
                        break;
                    case 2:
                        front_Desk.listAllSchedules(Schedule.schedulesList);
                        break;
                    case 3:
                        front_Desk.listSeatInfo(Schedule.schedulesList);
                        break;
                    case 4:
                        front_Desk.sellTickets(Schedule.schedulesList, Customer.customers);
                        break;
                    case 5:
                        front_Desk.logout();
                        System.out.println("退出成功");
                        return;
                    default:
                        System.out.println("无效的选择，请重新选择操作。");
                        break;
                }
            }
        }else {
            System.out.println("前台登录失败！");
        }
    }
}
