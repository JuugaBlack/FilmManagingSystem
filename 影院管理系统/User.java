package FilmHubTutorialV12;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class User {
	protected String username = null;
	protected String password = null;
	protected String userID = null;
	protected String phoneNumber = null;
	protected String email = null;
	public UserRole role;
	protected String registerTime;
	protected User currentUser = null;
	static String userfile = "所有用户V1.2.txt";
    protected static List<User> users = FileManager.readUsersFromFile(userfile, User.class);
	Scanner sc = new Scanner(System.in);
	
	public User(String username,String password,String userID,String phoneNumber,String email,UserRole role,String registerTime) {
		this.username = username;
        this.password = password;
        this.userID = userID;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
		// 记录注册时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        this.registerTime = dateFormat.format(new Date());
	}
	
	protected enum UserRole{
		ADMINISTRATOR, MANAGER, FRONT_DESK, CONSUMER, CUSTOMER
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	public String getUserName(){
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword(){
		return password;
	}

	public void setUserID() {
		this.userID = generateID(10);
	}
	public String getUserID(){
		return userID;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}

	public void setRole(UserRole role){
		this.role = role;
	}
	public UserRole getRole(){
		return role;
	}

	public void setRegisterTime(String registerTime){
		this.registerTime = registerTime;
	}
	public String getRegisterTime(){
		return registerTime;
	}

	// 产生随机ID
	public String generateID(int length){
		StringBuilder genID = new StringBuilder();
		Random random = new Random();

		for(int i=0;i<length;i++){
			int num = random.nextInt(10);
			genID.append(num);
		}

		return genID.toString();
	}

	public boolean login() {
        // 提示用户输入用户名
        System.out.println("请输入用户名: ");
        String inputUsername = sc.nextLine();

        // 提示用户输入密码
        System.out.println("请输入密码:");
        String inputPassword = sc.nextLine();

		String hashedPassword = User.hashPassword(inputPassword);

        // 查找用户
        User loginUser = null;
        for (User user : User.users) {
            if (user.getUserName().equals(inputUsername)) {
                loginUser = user;
                break;
            }
        }
		if(loginUser == null){
			System.out.println("未找到该用户");
		}

        // 登录成功与失败
        if (loginUser != null) {
            if (loginUser.getPassword().equals(hashedPassword)) {
                System.out.println("登陆成功！");
                // 设置当前登录用户
                currentUser = loginUser;
                return true;
            } else {
                System.out.println("密码错误，登录失败！");
                return false;
            }
        } else {
            System.out.println("用户名错误，登录失败！");
            return false;
        }
    }

		public void logout() {
			currentUser = null;
		}
	
		public User getCurrentUser() {
			return currentUser;
		}

	public void changePassword(){
		// 提示输入用户名
		System.out.println("请输入用户名：");
		String name = sc.nextLine();
		// 提示用户输入新密码
		System.out.println("请输入新密码：");
		String newPassword = sc.nextLine();
		
		// 提示用户再次输入新密码以确认
		System.out.println("请再次输入新密码以确认：");
		String newPasswordConfirm = sc.nextLine();
	
		if (newPassword.equals(newPasswordConfirm)) {
			for(User user : User.users){
				if(user.getUserName().equals(name)){
					String hashedPassword = User.hashPassword(newPasswordConfirm);
					user.setPassword(hashedPassword);
					System.out.println("密码修改成功！");
					break;
				}else{
					System.out.println("输入的用户名不存在，请重新确认！");
				}
			}
		} else {
			System.out.println("两次输入的新密码不一致，密码修改失败！");
		}
	}

	public boolean register(){
		// 提示用户输入用户名
		System.out.println("请输入用户名 >");
		String newUsername = sc.nextLine();
		if(isUserNameTaken(newUsername)){
			System.out.println("用户名已存在 > ");
			return false;
		}
	
		// 提示用户输入密码
		System.out.println("请输入密码：");
		String newPassword = sc.nextLine();
	
		// 提示用户确认密码
		System.out.println("请再次输入密码以确认：");
		String aPassword = sc.nextLine();
		String hasedPassword = User.hashPassword(aPassword);
		if(!newPassword.equals(aPassword)){
			System.out.println("两次输入密码输入不一致：");
			return false;
		}
	
		// 提示用户输入电话号码
		System.out.println("请输入电话号码：");
		String newPhoneNumber = sc.nextLine();
	
		// 提示用户输入电子邮箱
		System.out.println("请输入电子邮箱：");
		String newEmail = sc.nextLine();
		
		// 创建对象
		User newUser = new User(newUsername,hasedPassword,generateID(10),newPhoneNumber,newEmail,null,registerTime);
		User.users.add(newUser);
		System.out.println("注册成功！");

		
		return true;
	}

	// 检测用户名是否重复
	public boolean isUserNameTaken(String username){
		for(User user : User.users){
			if(user.getUserName().equals(username)){
				return true;
			}
		}

		return false;
	}

	// 检测手机号是否重复
	public boolean isPhoneNumberTaken(String phoneNumber){
		for(User user : User.users){
			if(user.getPhoneNumber().equals(phoneNumber)){
				return true;
			}
		}

		return false;
	}

	//检测邮箱是否重复
	public boolean isEmailTaken(String email){
		for(User user : User.users){
			if(user.getEmail().equals(email)){
				return true;
			}
		}

		return false;
	}

	// 使用MD5哈希密码的方法
    public static String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes = md.digest(passwordBytes);
            // 将哈希结果转换为十六进制字符串
            Formatter formatter = new Formatter();
            for (byte b : hashBytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
}
