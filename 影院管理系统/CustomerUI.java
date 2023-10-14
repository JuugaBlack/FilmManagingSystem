package FilmHubTutorialV12;

import java.util.Scanner;

public class CustomerUI {
    private Customer customer;

    public CustomerUI(Customer customer){
        this.customer = customer;
    }

    public void customerUI(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请选择注册或登录：");
        System.out.println("1. 注册");
        System.out.println("2. 登录");

        int choice = sc.nextInt();
        sc.nextLine();
        
        if(choice == 1){
            boolean registerSuccess = customer.register();
            if(registerSuccess){
                Customer.customers.add(customer);
            }else{
                System.out.println("注册失败！");
            }
        }else if(choice == 2){
            boolean customerLogin = customer.login();
            if(customerLogin){
                System.out.println("顾客登录成功！");

                while(true){
                    System.out.println("顾客操作菜单：");
                    System.out.println("1. 修改密码");
                    System.out.println("2. 忘记密码");
                    System.out.println("3. 查看所有电影信息");
                    System.out.println("4. 查看指定电影信息");
                    System.out.println("5. 购票");
                    System.out.println("6. 取票");
                    System.out.println("7. 查看购票历史");
                    System.out.println("8. 退出登录");

                    int customerChoice = sc.nextInt();
                    sc.nextLine();

                    switch(customerChoice){
                        case 1:
                            customer.changePassword();
                            break;
                        case 2:
                            customer.forgetPassword(Customer.customers);
                            break;
                        case 3:
                            customer.listMovieMes(Movie.movieList);
                            break;
                        case 4:
                            customer.searchMovies(Movie.movieList);
                            break;
                        case 5:
                            customer.buyTickets(Schedule.schedulesList, Movie.movieList);
                            break;
                        case 6:
                            customer.gettTicket(Ticket.purchaseHistory);
                            break;
                        case 7:
                            customer.seePurchaseHistory();
                            break;
                        case 8:
                            customer.logout();
                            System.out.println("退出成功");
                            return;
                        default:
                            System.out.println("无效的选择，请重新选择操作。");
                            break;
                    }
                }
            }else {
                System.out.println("顾客登录失败！");
            }
        }else{
            System.out.println("无效的选择，请重新选择操作。");
        }
    }
}
