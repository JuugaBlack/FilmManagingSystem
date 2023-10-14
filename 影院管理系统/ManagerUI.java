package FilmHubTutorialV11;

import java.util.Scanner;

public class ManagerUI {
    private Manager manager;

    public ManagerUI(Manager manager){
        this.manager = manager;
    }

    public void managerUI(){
        Scanner sc = new Scanner(System.in);
        System.out.println("经理登录……");
        boolean managerLogin = manager.login();

        if(managerLogin){
            System.out.println("经理登录成功!");

            while(true){
                System.out.println("经理操作菜单：");
                System.out.println("1. 修改密码");
                System.out.println("2. 重置消费者密码");
                System.out.println("3. 列出影片信息");
                System.out.println("4. 添加影片信息");
                System.out.println("5. 修改影片信息");
                System.out.println("6. 删除影片信息");
                System.out.println("7. 查询影片信息");
                System.out.println("8. 增加场次");
                System.out.println("9. 修改场次");
                System.out.println("10. 删除场次");
                System.out.println("11. 列出所有场次");
                System.out.println("12. 列出所有消费者信息");
                System.out.println("13. 查询用户信息");
                System.out.println("14. 退出登录");

                int managerChoice = sc.nextInt();
                sc.nextLine(); 

                switch(managerChoice){
                    case 1:
                        manager.changePassword();
                        break;
                    case 2:
                        System.out.print("请输入要重置的用户名：");
                        String targetUserName = sc.nextLine();
                        manager.resetPassword(targetUserName);
                        break;
                    case 3:
                        manager.listMovieMes(Movie.movieList);
                        break;   
                    case 4:
                        manager.addMovieMes();
                        break;  
                    case 5:
                        manager.changeMovieMes();
                        break;
                    case 6:
                        manager.deleteMovieMes();   
                        break;  
                    case 7:
                        manager.searchMovies();
                        break;
                    case 8:
                        manager.addMovieSchedule(Schedule.schedulesList, Hall.halls);
                        break;
                    case 9:
                        manager.changeMovieSchedule(Schedule.schedulesList, Hall.halls);
                        break;
                    case 10:
                        manager.deleteMovieSchedule(Schedule.schedulesList);
                        break;
                    case 11:
                        manager.listAllSchedules(Schedule.schedulesList);
                        break;
                    case 12:
                        manager.listCustomerMes();
                        break;
                    case 13:
                        manager.searchCustomerMes();
                        break;
                    case 14:
                        manager.logout();
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
