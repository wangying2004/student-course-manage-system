
import java.sql.*;
import java.util.Scanner;

public class StudentManager {
    //数据库连接信息
    static String url = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
    static String user = "root";
    static String pwd = "123456";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        try {
            //加载MySQL驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("===== 学生信息管理系统 =====");
            while(true){
                System.out.println("\n====功能菜单====");
                System.out.println("1.查询全部学生");
                System.out.println("2.新增学生");
                System.out.println("3.修改学生姓名");
                System.out.println("4.删除学生");
                System.out.println("5.查询学生选课与成绩");
                System.out.println("0.退出系统");
                System.out.print("请输入功能编号：");
                int op = sc.nextInt();
                if(op == 0){
                    System.out.println("系统退出");
                    break;
                }
                switch(op){
                    case 1:
                        queryStudent(conn);
                        break;
                    case 2:
                        addStudent(conn,sc);
                        break;
                    case 3:
                        updateStudent(conn,sc);
                        break;
                    case 4:
                        delStudent(conn,sc);
                        break;
                    case 5:
                        queryScore(conn,sc);
                        break;
                    default:
                        System.out.println("输入编号错误，请重新输入！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(conn != null) conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            sc.close();
        }
    }

    //功能1：查询全部学生
    public static void queryStudent(Connection conn) throws SQLException{
        String sql = "select * from student";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        System.out.println("\n学号\t姓名\t性别\t生日\t班级编号");
        while(rs.next()){
            System.out.printf("%s\t%s\t%s\t%s\t%d\n",
                    rs.getString("s_id"),
                    rs.getString("s_name"),
                    rs.getString("s_gender"),
                    rs.getDate("s_birth"),
                    rs.getInt("class_id"));
        }
        rs.close();
        pstmt.close();
    }

    //功能2：新增学生
    public static void addStudent(Connection conn,Scanner sc) throws SQLException{
        System.out.print("输入学号：");
        String sid = sc.next();
        System.out.print("输入姓名：");
        String name = sc.next();
        System.out.print("输入性别：");
        String gender = sc.next();
        System.out.print("输入生日(yyyy-MM-dd)：");
        String birth = sc.next();
        System.out.print("输入班级id：");
        int cid = sc.nextInt();

        String sql = "insert into student(s_id,s_name,s_gender,s_birth,class_id) values (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,sid);
        pstmt.setString(2,name);
        pstmt.setString(3,gender);
        pstmt.setString(4,birth);
        pstmt.setInt(5,cid);
        int row = pstmt.executeUpdate();
        if(row>0) System.out.println("✅新增学生成功");
        pstmt.close();
    }

    //功能3：修改学生姓名
    public static void updateStudent(Connection conn,Scanner sc) throws SQLException{
        System.out.print("输入要修改的学号：");
        String sid = sc.next();
        System.out.print("输入新姓名：");
        String newName = sc.next();
        String sql = "update student set s_name=? where s_id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,newName);
        pstmt.setString(2,sid);
        int row = pstmt.executeUpdate();
        if(row>0) System.out.println("✅修改成功");
        else System.out.println("❌未找到该学号学生");
        pstmt.close();
    }

    //功能4：删除学生
    public static void delStudent(Connection conn,Scanner sc) throws SQLException{
        System.out.print("输入要删除的学号：");
        String sid = sc.next();
        String sql = "delete from student where s_id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,sid);
        int row = pstmt.executeUpdate();
        if(row>0) System.out.println("✅删除成功");
        else System.out.println("❌没有找到该学生");
        pstmt.close();
    }

    //功能5：查询学生选课和成绩
    public static void queryScore(Connection conn,Scanner sc) throws SQLException{
        System.out.print("输入学生学号：");
        String sid = sc.next();
        String sql = "select s.s_name,c.c_name,sc.score " +
                "from student s, course c, sc " +
                "where s.s_id=sc.s_id and c.c_id=sc.c_id and s.s_id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,sid);
        ResultSet rs = pstmt.executeQuery();
        System.out.println("\n姓名\t课程名称\t成绩");
        boolean hasData = false;
        while(rs.next()){
            hasData = true;
            System.out.printf("%s\t%s\t%.2f\n",
                    rs.getString("s_name"),
                    rs.getString("c_name"),
                    rs.getDouble("score"));
        }
        if(!hasData){
            System.out.println("该学生暂无选课记录");
        }
        rs.close();
        pstmt.close();
    }
}