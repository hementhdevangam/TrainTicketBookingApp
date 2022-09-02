package Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class Passenger1 {
    public static void insertPassenger(Connection connection, String name, int age, String gender) throws SQLException {
        String query = "insert into passenger(name,age,gender) values(?,?,?)";
        PreparedStatement p = connection.prepareStatement(query);
        p.setString(1, name);
        p.setInt(2, age);
        p.setString(3, gender);
        p.executeUpdate();
    }

}
class Passenger2{
    public static int trainNo;
    public static String trainName;
    public static String sourc;
    public static String dest;
    public static double ticPrice;
    public String getTrainDetails(Connection connection,int train_no) throws SQLException {

        try {
            String query = "select * from trains where train_no= " + train_no;
            Statement statement = connection.createStatement();


            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                trainNo = result.getInt("train_no");
                trainName = result.getString("train_name");
                sourc = result.getString("source");
                dest = result.getString("destination");
                ticPrice = result.getInt("ticket_price");

                System.out.println(ticPrice);
                String tr= String.valueOf(trainNo);


            }
            return null;
        }catch ( Exception e){
            throw  new RuntimeException("Enter available train numbers only");
        }
    }
}
public class Passenger extends Passenger2  {
    public static double total_ticket_price=0;

    public static double price;

    public static int passenger_count;

    public static ArrayList<String> name1= new ArrayList<>();

    public static ArrayList<Integer> age1= new ArrayList<>();
    public static ArrayList<Double> res1= new ArrayList<>();
    public static ArrayList<String> gender1= new ArrayList<>();

    static Scanner sc = new Scanner(System.in);
    public static String travel_date;
    public static int train_no;
    public static String pnr;

    public static double res;


    public void Inputinfo(Connection connection) throws SQLException {


        System.out.print("Enter Train Number : ");
        train_no = sc.nextInt();

        getTrainDetails(connection, train_no);

        System.out.print("Enter Travel Date(YYYYMMDD) : ");
        travel_date = sc.next();

        System.out.print("Enter Number Of Passengers : ");
        passenger_count = sc.nextInt();

        for (int i = 0; i < passenger_count; i++) {

            System.out.print("Enter Passenger Name : ");
            String name = sc.next();
            name1.add(name);

            System.out.print("Enter Passenger Age : ");
            int age = sc.nextInt();
            age1.add(age);

            System.out.print("Enter Passenger Gender(M/F) : ");
            String gender = sc.next();
            gender1.add(gender);

            if(age<=12){
                res=ticPrice*0.5;
            } else if (age>=60) {
                res=ticPrice*0.6;

            } else if ((gender.equalsIgnoreCase("F"))&&(age<60 && age>12) ) {
                res=ticPrice*0.75;
            }else{
                res=ticPrice*1;
            }

            res1.add(res);
            price = ticPrice;
            total_ticket_price+=res;
            System.out.println("=====================");

            Passenger1.insertPassenger(connection, name, age, gender);

        }


    }


        public static void main(String[] args) throws SQLException {
         Connection connection ;
        try {
            String dbURL = "jdbc:mysql://localhost:3306/ticketbooking";
            connection=DriverManager.getConnection(dbURL,"root","Hemanth@540");


        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");

        }
        System.out.println("Success");

        Passenger ps = new Passenger();
            ps.Inputinfo(connection);
            int i=100;
            pnr = sourc.charAt(0)+""+dest.charAt(0)+"_"+travel_date+"_"+i++;
            System.out.println(pnr);

        }

}


