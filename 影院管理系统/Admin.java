package FilmHubTutorialV11;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Admin extends User{
    private Admin admin;
    Scanner sc = new Scanner(System.in);

    public Admin(String username, String password, String userID, String phoneNumber, String email, UserRole role, String registerTime) {
        super(username, password, userID, phoneNumber, email, role, registerTime);
        this.admin = this;
    }    
    
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void changePassword(){
		// 提示用户输入新密码
		System.out.println("请输入新密码：");
		String newPassword = sc.nextLine();
		
		// 提示用户再次输入新密码以确认
		System.out.println("请再次输入新密码以确认：");
		String newPasswordConfirm = sc.nextLine();
	
		if (newPassword.equals(newPasswordConfirm)) {
            AdiminUI.adminList.get(0).setPassword(newPassword);
            System.out.println("密码修改成功！");
		} else {
			System.out.println("两次输入的新密码不一致，密码修改失败！");
		}
        FileManager.writeAdminsToFile(AdiminUI.adminfilepath, AdiminUI.adminList);
	}
    
    public boolean login(String username, String password){
        // 检查用户名和密码是否匹配管理员账户
        if (username.equalsIgnoreCase(admin.getUserName()) && password.equals(admin.getPassword())) {
            System.out.println("登录成功！");
            return true;
        } else {
            System.out.println("登录失败，用户名或密码不正确！");
        }

        return false;
    }

    protected void resetPassword(String targetName,UserRole targetRole){
        boolean foundUser = false;
        for(User user : User.users){
            if(user.getUserName().equals(targetName) && user.getRole() == targetRole){
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

    // 列出影城方用户信息
    protected void listCinemaStaff(){
        System.out.println("影城员工信息：");
        for (User user : User.users) {
            if (user.getRole() == UserRole.MANAGER || user.getRole() == UserRole.FRONT_DESK) {
                System.out.println("用户ID: " + user.getUserID());
                System.out.println("用户名: " + user.getUserName());
                System.out.println("用户注册时间: " + user.getRegisterTime());
                System.out.println("用户类型: " + user.getRole());
                System.out.println("用户手机号: " + user.getPhoneNumber());
                System.out.println("用户邮箱: " + user.getEmail());
                System.out.println("-----------------------------");
            }
        }
    }

    // 删除影城方用户
    protected void deleteCinemaStaff() {
        System.out.println("请输入要删除的用户名：");
        String targetName = sc.nextLine();
        boolean isDeleted = false;
    
        Iterator<User> userIterator = User.users.iterator();
            while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.getUserName().equals(targetName)) {
                System.out.println("删除后不可恢复，确认删除吗（y/n）：");
                String resure = sc.nextLine();
                if(resure.equals("y")){
                    userIterator.remove(); // 从User.users集合中删除
                    isDeleted = true;
                    System.out.println("删除成功！");
                    FileManager.writeUsersToFile(User.userfile, User.users);
                }
                break;
        }
    }
        if (!isDeleted) {
                System.out.println("用户名或身份不正确，请重新确认！");
            }

        
            if(User.users.size() == 0){
                FileManager.clearFile(Manager.managerfile);
                FileManager.clearFile(Front_Desk.frontdeskfile);
                FileManager.clearFile(Customer.customerfile);
            }
        for(User user : User.users){
            if(user.getRole().equals(UserRole.MANAGER)){
                FileManager.copyFileContent(User.userfile, Manager.managerfile, UserRole.MANAGER.toString());
            }else if(user.getRole().equals(UserRole.FRONT_DESK)){
                FileManager.copyFileContent(User.userfile, Front_Desk.frontdeskfile, UserRole.FRONT_DESK.toString());
            }
        }
    }

    // 查询影城方用户
    protected void searchCinemaStaff(){
        System.out.println("请选择查询方式：");
        System.out.println("1. 根据用户ID查询");
        System.out.println("2. 根据用户名查询");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.println("请输入用户ID：");
            String targetID = sc.nextLine();
            displayCinemaStaff(targetID, "ID");
        } else if (choice == 2) {
            System.out.println("请输入用户名：");
            String targetName = sc.nextLine();
            displayCinemaStaff(targetName, "用户名");
        } else {
            System.out.println("选择错误！");
        }
    }
    
    private void displayCinemaStaff(String target, String targetType) {
        boolean found = false;
        for (User user : User.users) {
            if (user.getUserID().equals(target) || user.getUserName().equals(target)) {
                System.out.println("查询结果：");
                System.out.println("用户ID: " + user.getUserID());
                System.out.println("用户名: " + user.getUserName());
                System.out.println("用户注册时间: " + user.getRegisterTime());
                System.out.println("用户类型: " + user.getRole());
                System.out.println("用户手机号: " + user.getPhoneNumber());
                System.out.println("用户邮箱: " + user.getEmail());
                System.out.println("-----------------------------");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("未找到此用户");
        }
    }

    // 增加影城方用户
    protected void addCinemaStaff(){
        System.out.println("请输入添加的用户类型(FRONT_DESK or MANAGER)：");
        String userType = sc.nextLine();

        if(!(userType.equalsIgnoreCase("FRONT_DESK") || userType.equalsIgnoreCase("MANAGER"))){
            System.out.println("用户类型错误，请重新输入！");
            return;
        }else{
            System.out.println("请输入用户名：");
            String newUsername = sc.nextLine();
            if (isUserNameTaken(newUsername)) {
                System.out.println("用户名已存在，请重新输入！");
                return;
        }

            System.out.println("请输入用户手机号：");
            String newPhoneNumber = sc.nextLine();
            if (isPhoneNumberTaken(newPhoneNumber)) {
                System.out.println("用户手机号已存在，请重新输入！");
                return;
        }

            System.out.println("请输入用户邮箱：");
            String newEmail = sc.nextLine();
            if (isEmailTaken(newEmail)) {
                System.out.println("用户邮箱已存在，请重新输入！");
                return;
        }

        String userID = generateID(10);
        // 获取当前时间作为注册时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String registerTime = dateFormat.format(new Date());
        String password = "123456";
        UserRole role;
        if(userType.equalsIgnoreCase("FRONT_DESK")){
            role = UserRole.FRONT_DESK;
        }else{
            role = UserRole.MANAGER;
        }

        if(userType.equalsIgnoreCase("FRONT_DESK")){
            Front_Desk newCinemaStaff = new Front_Desk(newUsername, password, userID, newPhoneNumber, newEmail, role, registerTime);
            Front_Desk.front_Desks.add(newCinemaStaff);
            User.users.add(newCinemaStaff);
            FileManager.writeUsersToFile(Front_Desk.frontdeskfile, Front_Desk.front_Desks);
            FileManager.writeUsersToFile(User.userfile, User.users);
        }else{
            Manager newCinemaStaff = new Manager(newUsername, password, userID, newPhoneNumber, newEmail, role, registerTime);
            Manager.managers.add(newCinemaStaff);
            User.users.add(newCinemaStaff);
            FileManager.writeUsersToFile(Manager.managerfile, Manager.managers);
            FileManager.writeUsersToFile(User.userfile, User.users);
        }

        System.out.println("影城方用户添加成功！初始密码为123456");
        } 
    }

    protected void changeCinemaStaff(){
        System.out.println("请输入要修改的用户名：");
        String targetName = sc.nextLine();
        User target = null;

        for(User user : User.users){
            if(user.getUserName().equals(targetName) && (user.getRole() == UserRole.FRONT_DESK || user.getRole() == UserRole.MANAGER)){
                target = user;
                break;
            }
        }
        if(target == null){
            System.out.println("未找到用户！");
            return;
        }

        System.out.println("请选择要修改的信息：");
        System.out.println("1.用户类型 ");
        System.out.println("2.用户手机号 ");
        System.out.println("3.用户邮箱 ");

        int choose = sc.nextInt();
        sc.nextLine();

        switch(choose){
            case 1: 
            System.out.println("填写新的用户类型（FRONT_DESK or MANAGER）：");
            String newRole = sc.nextLine();
            if(newRole.equals("FRONT_DESK") || newRole.equals("MANAGER")){
                target.setRole(UserRole.valueOf(newRole));
                System.out.println("更新完成！");
            }else{
                System.out.println("用户类型错误！");
            }
            break;

            case 2:
            System.out.println("填写新的用户手机号：");
            String newPhoneNumber = sc.nextLine();
            target.setPhoneNumber(newPhoneNumber);
            System.out.println("更新完成！");
            break;

            case 3:
            System.out.println("填写新的用户邮箱：");
            String newEmail = sc.nextLine();
            target.setEmail(newEmail);
            System.out.println("更新完成！");
            break;

            default:
            System.out.println("请重新选择！");
            break;
        }
        FileManager.writeUsersToFile(User.userfile, User.users);
        FileManager.copyFileContent(User.userfile, Manager.managerfile, UserRole.MANAGER.toString());
        FileManager.copyFileContent(User.userfile, Front_Desk.frontdeskfile, UserRole.FRONT_DESK.toString());
    }
}
