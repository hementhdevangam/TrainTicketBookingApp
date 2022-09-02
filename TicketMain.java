package  TicketMain;

import Passenger.Passenger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;

class Ticket extends Passenger{
    public static String name;
    public static int age;
    public static String gender;
    public static int counter;
    public void insertTicket(Connection connection, String pnr_no, String travel_date, int train_no, String train_name, String source, String destination,double ticket_price,double total_ticket_price) throws SQLException {
        String query = "insert into ticket(pnr_no,travel_date,train_no,train_name,source,destination,ticket_price,total_ticket_price) values(?,?,?,?,?,?,?,?)";
        PreparedStatement p = connection.prepareStatement(query);
        p.setString(1, pnr_no);
        p.setString(2,travel_date);
        p.setInt(3,train_no );
        p.setString(4, train_name);
        p.setString(5,source);
        p.setString(6,destination);
        p.setDouble(7,ticket_price);
        p.setDouble(8,total_ticket_price);
        p.executeUpdate();
    }
    public void printTicket() throws IOException {
        Path p = Paths.get("passenger.txt");
        if (Files.exists(p)){
            Files.delete(p);
        }
        p = Files.createFile(p);
        Files.write(p,("-----------------------------------------------" + "\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("TICKET DETAILS" +"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("-----------------------------------------------" + "\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("PNR:" +pnr+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("Train NO:" +trainNo+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("Train Name:"  + trainName+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("From:" + sourc+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("To:" +dest+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("Travel Date:" + travel_date+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("-----------------------------------------------" +"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("Passengers:"+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("-----------------------------------------------" + "\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("Name:"+"          "+"Age:"+"         "+"Gender:"+"         "+"Fare:"  +"\n").getBytes(), StandardOpenOption.APPEND);
        int size= name1.size();
                        for(int i=0;i<size;i++) {
                            String str = name1.get(i);
                            int ag = age1.get(i);
                            String gend = gender1.get(i);
                            double res = res1.get(i);
                            Files.write(p,( str+"    "+ag+"     "+gend+"     "+res+"\n").getBytes(), StandardOpenOption.APPEND);
                        }
        Files.write(p,("-----------------------------------------------" + "\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("Total_price:"+total_ticket_price+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("-----------------------------------------------" + "\n").getBytes(), StandardOpenOption.APPEND);

    }
    public String getCounter(Connection connection) throws SQLException {

        try {
            String query = "select * from passenger";
            Statement statement = connection.createStatement();


            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                name=result.getString("name");
                age=result.getInt("age");
                gender=result.getString("gender");
                counter=result.getInt("counter");

            }
            return null;
        }catch ( Exception e){
            throw  new RuntimeException(e);
        }
    }

}

class TicketMain extends Ticket {
    public static void main(String[] args) throws SQLException {
        Connection connection;
        try {
            String dbURL = "jdbc:mysql://localhost:3306/ticketbooking";
            connection = DriverManager.getConnection(dbURL, "root", "Hemanth@540");
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong");
        }
        System.out.println("Success");

        Passenger p = new Passenger();
        p.Inputinfo(connection);
        TicketMain t = new TicketMain();
        t.getCounter(connection);
        pnr=sourc.charAt(0)+""+dest.charAt(0)+"_"+travel_date+"_"+counter;
        t.insertTicket(connection,pnr,travel_date,trainNo,trainName,sourc,dest,price,total_ticket_price);
        System.out.println("Total Price:"+total_ticket_price);
        try {
            t.printTicket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}