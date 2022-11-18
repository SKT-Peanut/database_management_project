import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class sample {
// Database credentials
final static String HOSTNAME = "<username>-sql-server.database.windows.net";
final static String DBNAME = "cs-dsa-4513-sql-db";
final static String USERNAME = "<username>";
final static String PASSWORD = "<password>";
static Connection conn;
// Database connection string
final static String URL =
String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trust
ServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
HOSTNAME, DBNAME, USERNAME, PASSWORD);
public static Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
final static String Query1 = "INSERT INTO Team(t_name, t_type, date_formed) VALUES
(?,?,?)";
final static String Query2 = "INSERT INTO CLIENT (SSN, d_name, d_phone, a_name,
a_phone, date_first_assigned) VALUES (?,?,?,?,?,?)";
final static String Query2_care = "INSERT INTO Cares (SSN, t_name, active) VALUES
(?,?,?)";
final static String Query2_insurance = "INSERT INTO Insurance_Policy (SSN, pol_id,
pro_id, pro_address, i_type) VALUES (?,?,?,?,?)";
final static String Query2_need = "INSERT INTO Needs (SSN, needs, importance_value)
VALUES (?,?,?)";
final static String Query3 = "INSERT INTO Volunteers (SSN, date_first_join,
date_recent_train, location_recent_train) VALUES (?,?,?,?)";
final static String Query34_serve = "INSERT INTO Serves (SSN, t_name, months, hour,
active) VALUES (?,?,?,?,?)";
final static String Query34_active = "INSERT INTO Active (SSN, t_name, active) VALUES
(?,?,?)"; /* might not need */
final static String Query5 = "INSERT INTO Employee (SSN,salary,marital_Status,hired_date)
VALUES (?,?,?,?)";
final static String Query5_report = "INSERT INTO Reporting
(SSN,t_name,r_date,r_description) VALUES (?,?,?,?)";
final static String Query6 = "INSERT INTO Expenses (SSN,e_date,amount,e_description)
VALUES (?,?,?,?)";
final static String Query7 = "INSERT INTO External_Organization
(org_name,org_mailing,org_phone,contact_People) VALUES (?,?,?,?)";
final static String Query7_sponsor = "INSERT INTO Sponsor (org_name, t_name) VALUES
(?,?)";
final static String Query8 = "INSERT INTO Donor (SSN, anonymous) VALUES (?,?)";
final static String Query8_Donate = "INSERT INTO Donor_Donate (SSN, d_date,
d_amount,d_type,d_campaign) VALUES (?,?,?,?,?)";
final static String Query8_Card = "INSERT INTO Donor_Donate_Card (SSN, d_date,
d_amount, d_card_num, d_card_type, d_card_exp) VALUES (?,?,?,?,?,?)";
final static String Query8_Check = "INSERT INTO Donor_Donate_Check (SSN, d_date,
d_amount, d_check_num) VALUES (?,?,?,?)";
final static String Query9 = "INSERT INTO Organization_Donor VALUES (?,?)";
final static String Query9_donate = "INSERT INTO Organization_Donate VALUES
(?,?,?,?,?)";
final static String Query9_Card = "INSERT INTO Organization_Donate_Card VALUES
(?,?,?,?,?,?)";
final static String Query9_Check = "INSERT INTO Organization_Donate_Check VALUES
(?,?,?,?)";
// User input prompt//
final static String PROMPT =
"Enter you option(1-17):\n" +
"1. Enter a new team into the database\n" +
"2. Enter a new client into the database and associate him or her
with one or more teams\n" +
"3. Enter a new volunteer into the database and associate him or
her with one or more teams\n" +
"4. Enter the number of hours a volunteer worked this month for a
particular team\n" +
"5. Enter a new employee into the database and associate him or
her with one or more teams\n" +
"6. Enter an expense charged by an employee\n" +
"7. Enter a new organization and associate it to one or more PAN
teams\n" +
"8. Enter a new donor and associate him or her with several
donations.\n" +
"9. Enter a new organization and associate it with several
donations\n" +
"10. Retrieve the name and phone number of the doctor of a
particular client\n" +
"11. Retrieve the total amount of expenses charged by each
employee for a particular period of time.\r\n" +
"The list should be sorted by the total amount of expenses\n" +
"12. Retrieve the list of volunteers that are members of teams that
support a particular client\n" +
"13. Retrieve the names and contact information of the clients that
are supported by teams sponsored by\r\n" +
"an organization whose name starts with a letter between B and K.
The client list should be sorted by name\n" +
"14. Retrieve the name and total amount donated by Donor that are
also employees. The list should be\r\n" +
"sorted by the total amount of the donations, and indicate if each
donor wishes to remain anonymous\n" +
"15. Retrieve the names of all teams that were founded after a
particular date(1/month)\n" +
"16. Increase the salary by 10% of all employees to whom more
than one team must report\n"+
"17. Delete all clients who do not have health insurance and whose
value of importance for transportation\r\n" +
"is less than 5\n"+
"18. Import\n"+
"19. Export\n" +
"20. Add a Person\n" +
"21. Close";
public static void main(String[] args) throws SQLException {
conn = DriverManager.getConnection(URL);
String option = ""; // Initialize user option selection as nothing
while (!option.equals("21")) { // As user for options until option 4 is selected
System.out.println(PROMPT); // Print the available options
option = sc.nextLine(); // Read in the user option selection
switch (option) { // Switch between different options
case "1": // Insert a new faculty with first salary calculation method option
Q1();
break;
case "2":
Q2();
break;
case "3":
Q3();
break;
case "4":
Q4();
break;
case "5":
Q5();
break;
case "6":
Q6();
break;
case "7":
Q7();
break;
case "8":
Q8();
break;
case "9":
Q9();
break;
case "10":
Q10();
break;
case "11":
Q11();
break;
case "12":
Q12();
break;
case "13":
Q13();
break;
case "14":
Q14();
break;
case "15":
Q15();
break;
case "16":
Q16();
break;
case "17":
Q17();
break;
case "18":
Q18();
break;
case "19":
Q19();
break;
case "20":
Q20();
break;
case "21":
sc.close();
System.out.println("Later!");
break;
default:
System.out.println("Sorry, Unrecognized input");
break;
}
}
sc.close(); // Close the scanner before exiting the application
}
public static void Q1() throws SQLException
{
System.out.println("Please enter team name:");
String name = sc.nextLine();
//sc.nextLine();
System.out.println("Please enter team type:");
String type = sc.nextLine();
System.out.println("Please enter date formed: 010112 = Jan 1, 2012");
String date = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query1))
{
// Populate the query template with the data collected from the user
statement.setString(1, name);
statement.setString(2, type);
statement.setString(3, date);
final int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayTeam();
}
/* TO TEST YOU NEED to insert FOREIGN KEY before running*/
public static void Q2() throws SQLException
{
/* Client */
System.out.println("Enter client SSN from People table:");
int SSN = Integer.parseInt(sc.nextLine());
System.out.println("Enter Doctor's name");
String d_name = sc.nextLine();
System.out.println("Enter Doctor's phone number: ex - 4051234567");
int d_phone = Integer.parseInt(sc.nextLine());
System.out.println("Enter Attorney's name");
String a_name = sc.nextLine();
System.out.println("Enter Attorney's phone number: ex - 4051234567");
int a_phone = Integer.parseInt(sc.nextLine());
System.out.println("Enter date client was first assigned: 010112 = Jan 1, 2012");
int date = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, d_name);
statement.setInt(3, d_phone);
statement.setString(4, a_name);
statement.setInt(5, a_phone);
statement.setInt(6, date);
final int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
catch(SQLException e)
{
e.printStackTrace();
}
displayClient();
/* Team Association & Active */
System.out.println("Enter Team Name Associated With:");
String team = sc.nextLine();
System.out.println("Client active or inactive?");
String active = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query2_care))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, team);
statement.setString(3, active);
final int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
catch(SQLException e)
{
e.printStackTrace();
}
displayCares();
/* Insurance */
System.out.println("Enter Insurance Info");
System.out.println("Enter Policy ID:");
int pol_id = Integer.parseInt(sc.nextLine());
System.out.println("Enter Provider ID:");
int pro_id = Integer.parseInt(sc.nextLine());
System.out.println("Enter Provider Address:");
String address = sc.nextLine();
String type = "";
boolean constraint_met = false;
while(!constraint_met)
{
System.out.println("Type: \n1.Auto\n2.Life\n3.Health\n4.Home");
String typed = sc.nextLine();
switch(typed) {
case "1":
type = "Auto";
constraint_met = true;
break;
case "2":
type = "Life";
constraint_met = true;
break;
case "3":
type = "Health";
constraint_met = true;
break;
case "4":
type = "Home";
constraint_met = true;
break;
default:
System.out.println("Choose 1, 2, 3, or 4 ONLY");
break;
}
}
// Insert into insurance
try (final PreparedStatement statement = conn.prepareStatement(Query2_insurance))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setInt(2, pol_id);
statement.setInt(3, pro_id);
statement.setString(4, address);
statement.setString(5, type);
final int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
catch(SQLException e)
{
e.printStackTrace();
}
displayInsurance();
boolean need_stuff = false;
int importance_value = 0;
// Need choices option for multiple needs and a quit option
while (!need_stuff)
{
String need = "";
System.out.println("Select
Need\n1.Visiting\n2.Shopping\n3.HouseKeeping\n4.Transportation\n5.Yard
Work\n6.Food\n7.Enter yourself\n8.No more needs");
int select =Integer.parseInt(sc.nextLine());
switch (select)
{
case 1:
need = "Visiting";
System.out.println("Enter importance value");
importance_value = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);

int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
break;
case 2:
need = "Shopping";
System.out.println("Enter importance value");
importance_value = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
break;
case 3:
need = "HouseKeeping";
System.out.println("Enter importance value");
importance_value = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
break;
case 4:
need = "Transportation";
System.out.println("Enter importance value");
importance_value =Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
break;
case 5:
need = "Yard Work";
System.out.println("Enter importance value");
importance_value =Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
break;
case 6:
need = "Food";
System.out.println("Enter importance value");
importance_value = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));

}
break;
case 7:
System.out.println("What do you need?");
need = sc.nextLine();
System.out.println("Enter importance value");
importance_value = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query2_need))
{
// Populate the query template with the data collected from the user
statement.setInt(1, SSN);
statement.setString(2, need);
statement.setInt(3, importance_value);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
break;
case 8:
need_stuff = true;
break;
}
}
// display Need table for project report purpose
displayNeeds();
}
public static void Q3() throws SQLException
{
// Volunteer
System.out.println("1. Enter Volunteer\n2. Add Leader");
int ad = Integer.parseInt(sc.nextLine());
switch (ad)
{
case 1:
System.out.println("Enter Volunteer SSN:");
int ssn = Integer.parseInt(sc.nextLine());
System.out.println("Enter date first join: 010112 - Jan 1, 2012");
int date_join = Integer.parseInt(sc.nextLine());
System.out.println("Enter Recent Training Date");
int date_train = Integer.parseInt(sc.nextLine());
System.out.println("Enter Recent Training Location");
String location = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query3))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn);
statement.setInt(2, date_join);
statement.setInt(3, date_train);
statement.setString(4, location);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
// display for pdf purpose
displayVolunteer();
// Serve table (relation between team and volunteer)
System.out.println("Enter Team Associated With:");
String t_name = sc.nextLine();
boolean correct = false;
int month = 0;
while (!correct)
{
System.out.println("Enter month worked");
month = sc.nextInt();
if (month > 0 && month < 13)
{
correct = true;
}
else
{
System.out.println("Enter a correct month: 4 - April");
}
}
System.out.println("Enter hours worked ");
int hour = sc.nextInt();
sc.nextLine();
System.out.println("Active or inactive?");
String active = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query34_serve))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn);
statement.setString(2, t_name);
statement.setInt(3, month);
statement.setInt(4, hour);
statement.setString(5, active);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
// display volunteer/team relation table
displayServe();
case 2:
// add a leader to a team (add to table Leads)
System.out.println("Enter SSN:");
int ssn2 = Integer.parseInt(sc.nextLine());
System.out.println("Team name:");
String name2 = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement("INSERT INTO
Leads VALUES (" + ssn2 + ",'" + name2 + "')"))
{
// Populate the query template with the data collected from the user
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
Statement statement2 = conn.createStatement();
ResultSet r2 = statement2.executeQuery("SELECT * FROM Leads");
System.out.println("Leads Table");
while(r2.next()){
System.out.println(String.format("SSN: %s| d_name: %s| ",
r2.getString(1),
r2.getString(2)));
}
}
}
public static void Q4() throws SQLException
{
System.out.println("Enter Volunteer SSN:");
int ssn2 = sc.nextInt();
sc.nextLine();
System.out.println("Enter Team Associated With:");
String t_name2 = sc.nextLine();
boolean correct2 = false;
int month2 = 0;
while (!correct2)
{
System.out.println("Enter month worked");
month2 = sc.nextInt();
if (month2 > 0 && month2 < 13)
{
correct2 = true;
}
else
{
System.out.println("Enter a correct month: 4 - April");
}
}
System.out.println("Enter hours worked ");
int hour2 = sc.nextInt();
sc.nextLine();
System.out.println("Active or inactive?");
String active2 = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query34_serve))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn2);
statement.setString(2, t_name2);
statement.setInt(3, month2);
statement.setInt(4, hour2);
statement.setString(5, active2);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayServe();
}
public static void Q5() throws SQLException
{
System.out.println("1. Enter Employee\n2. Associated with more Team");
int chose = sc.nextInt();
sc.nextLine();
switch(chose)
{
// Employee table
case 1:
System.out.println("Enter Employee SSN:");
int ssn = Integer.parseInt(sc.nextLine());
System.out.println("Enter salary:");
int salary = Integer.parseInt(sc.nextLine());
System.out.println("Enter martial status");
String status = sc.nextLine();
System.out.println("Enter hired date: 010112 - Jan 1, 2012");
int date = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement = conn.prepareStatement(Query5))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn);
statement.setInt(2, salary);
statement.setString(3, status);
statement.setInt(4, date);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayEmployee();
/* // Team associate
System.out.println("Enter team associated with:");
String team = sc.nextLine();
System.out.println("Enter report date: 010112 - Jan 1,2012");
int report_date = Integer.parseInt(sc.nextLine());
System.out.println("Enter report description:");
String des = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query5_report))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn);
statement.setString(2, team);
statement.setInt(3, report_date);
statement.setString(4, des);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayReport();*/
break;
case 2:
// reporting table for employee and team
System.out.println("Enter Employee SSN:");
int ssn2 = Integer.parseInt(sc.nextLine());
System.out.println("Enter team associated with");
String team2 = sc.nextLine();
System.out.println("Enter report description");
String des2 = sc.nextLine();
System.out.println("Enter report date ");
int report_date2 = Integer.parseInt(sc.nextLine());
//execute query
try (final PreparedStatement statement = conn.prepareStatement(Query5_report))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn2);
statement.setString(2, team2);
statement.setInt(3, report_date2);
statement.setString(4, des2);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayReport();
break;
}
}
public static void Q6() throws SQLException
{
// Expenses table
System.out.println("Enter Employee SSN:");
int ssn = sc.nextInt();
sc.nextLine();
System.out.println("Enter date:");
int date = sc.nextInt();
sc.nextLine();
System.out.println("Enter amount:");
int amount = sc.nextInt();
sc.nextLine();
System.out.println("Enter description:");
String des = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query6))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn);
statement.setInt(2, date);
statement.setInt(3, amount);
statement.setString(4, des);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayExpense();
}
public static void Q7() throws SQLException
{
System.out.println("1. Enter a new organization\n2. Associate with team\n3. Affiliate
with a person");
int choice = Integer.parseInt(sc.nextLine());
switch(choice)
{
case 1:
// External Org table
System.out.println("Enter Orgazniation's Name:");
String name = sc.nextLine();
System.out.println("Enter Address:");
String address = sc.nextLine();
System.out.println("Enter phone number:");
int phone = Integer.parseInt(sc.nextLine());
System.out.println("Enter contact People");
String contact = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query7))
{
// Populate the query template with the data collected from the user
statement.setString(1, name);
statement.setString(2, address);
statement.setInt(3, phone);
statement.setString(4, contact);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
// display External Org table after insert
displayOrg();
break;
case 2:
// Sponsor table
System.out.println("Enter Orgazniation Name:");
String name2 = sc.nextLine();
System.out.println("Enter Team Name:");
String t_name = sc.nextLine();
try (final PreparedStatement statement =
conn.prepareStatement(Query7_sponsor))
{
// Populate the query template with the data collected from the user
statement.setString(1, name2);
statement.setString(2, t_name);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
// display sponsor table after insert
displaySponsor();
break;
case 3:
break;
}
}
public static void Q8() throws SQLException
{
//displayDCard();
System.out.println("1. Enter a new donor\n2. Associate donor with 1 or more
Donation(s)");
int choice = Integer.parseInt(sc.nextLine());
switch (choice)
{
case 1:
// Donor table
System.out.println("Enter SSN:");
int ssn = Integer.parseInt(sc.nextLine());
System.out.println("Is it anonymous");
String ano = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query8))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn);
statement.setString(2, ano);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayDonor();
break;
case 2:
// info for donor card or check
System.out.println("Donor SSN:");
int ssn2 = Integer.parseInt(sc.nextLine());
System.out.println("Date:");
int date = Integer.parseInt(sc.nextLine());
System.out.println("Amount");
int amount = Integer.parseInt(sc.nextLine());
System.out.println("Campaign:");
String camp = sc.nextLine();
System.out.println("Type:\n1. Card\n2. Check");
int type = Integer.parseInt(sc.nextLine());
boolean chose = false;
while (!chose)
{
switch (type)
{
case 1:
// donor card table
String c = "card";
System.out.println("Card number:");
int card = Integer.parseInt(sc.nextLine());
System.out.println("Card type:");
String c_type = sc.nextLine();
System.out.println("Exp date: 010112 - Jan 1, 2012");
int exp = Integer.parseInt(sc.nextLine());
// add to the donor donate table
try (final PreparedStatement statement =
conn.prepareStatement(Query8_Donate))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn2);
statement.setInt(2, date);
statement.setInt(3, amount);
statement.setString(4, c);
statement.setString(5, camp);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
// add to donor donate card table
try (final PreparedStatement statement =
conn.prepareStatement(Query8_Card))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn2);
statement.setInt(2, date);
statement.setInt(3, amount);
statement.setInt(4, card);
statement.setString(5, c_type);
statement.setInt(6, exp);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
chose = true;
displayDCard();
break;
case 2:
// add to donor check info
String ch = "check";
System.out.println("Check number:");
int check = Integer.parseInt(sc.nextLine());
// add to table donor donate
try (final PreparedStatement statement =
conn.prepareStatement(Query8_Donate))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn2);
statement.setInt(2, date);
statement.setInt(3, amount);
statement.setString(4, ch);
statement.setString(5, camp);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayDD();
// add to table donor donate check
try (final PreparedStatement statement =
conn.prepareStatement(Query8_Check))
{
// Populate the query template with the data collected from the user
statement.setInt(1, ssn2);
statement.setInt(2, date);
statement.setInt(3, amount);
statement.setInt(4, check);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
chose = true;
displayDCH();
break;
default:
System.out.println("Chose 1 or 2");
break;
}
}
break;
}
}
// basically mirroring Q8 except adding into different tables
public static void Q9() throws SQLException
{
System.out.println("1. Enter new org that donates \n2. Associated with 1 or more
Donation(s)");
int choice = Integer.parseInt(sc.nextLine());
switch (choice)
{
case 1:
// Org Donate table
System.out.println("Enter Org name:");
String name =sc.nextLine();
System.out.println("Is it anonymous");
String ano = sc.nextLine();
try (final PreparedStatement statement = conn.prepareStatement(Query9))
{
// Populate the query template with the data collected from the user
statement.setString(1, name);
statement.setString(2, ano);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayOD();
break;
case 2:
System.out.println("Org Name:");
String o_name = sc.nextLine();
System.out.println("Date:");
int d = Integer.parseInt(sc.nextLine());
System.out.println("Amount");
int amount = Integer.parseInt(sc.nextLine());
System.out.println("Campaign:");
String camp = sc.nextLine();
System.out.println("Type:\n1. Card\n2. Check");
int type = Integer.parseInt(sc.nextLine());
boolean chose = false;
while (!chose)
{
switch (type)
{
case 1:
String c = "card";
System.out.println("Card number:");
int card = Integer.parseInt(sc.nextLine());
System.out.println("Card type:");
String c_type = sc.nextLine();
System.out.println("Exp date: 010112 - Jan 1, 2012");
int exp = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement =
conn.prepareStatement(Query9_donate))
{
// Populate the query template with the data collected from the user
statement.setString(1, o_name);
statement.setInt(2, d);
statement.setInt(3, amount);
statement.setString(4, c);
statement.setString(5, camp);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayOD();
try (final PreparedStatement statement =
conn.prepareStatement(Query9_Card))
{
// Populate the query template with the data collected from the user
statement.setString(1, o_name);
statement.setInt(2, d);
statement.setInt(3, amount);
statement.setInt(4, card);
statement.setString(5, c_type);
statement.setInt(6, exp);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
chose = true;
displayODCard();
break;
case 2:
String ch = "check";
System.out.println("Check number:");
int check = Integer.parseInt(sc.nextLine());
try (final PreparedStatement statement =
conn.prepareStatement(Query9_donate))
{
// Populate the query template with the data collected from the user
statement.setString(1, o_name);
statement.setInt(2, d);
statement.setInt(3, amount);
statement.setString(4, ch);
statement.setString(5, camp);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
displayOD();
try (final PreparedStatement statement =
conn.prepareStatement(Query9_Check))
{
// Populate the query template with the data collected from the user
statement.setString(1, o_name);
statement.setInt(2, d);
statement.setInt(3, amount);
statement.setInt(4, check);
int rows_inserted = statement.executeUpdate();
System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
}
chose = true;
displayODCheck();
break;
default:
System.out.println("Chose 1 or 2");
break;
}
}
break;
}
}
public static void Q10() throws SQLException
{
System.out.println("Enter SSN of Client:");
int SSN = sc.nextInt();
String q10 = "SELECT SSN,d_name,d_phone FROM Client WHERE SSN ="+SSN;
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(q10);
//display
while(r.next()){
System.out.println(String.format("SSN: %s|Doctor name: %s | Phone %s
|",
r.getString("SSN"),
r.getString("d_name"),
r.getString("d_phone")));
}
}
public static void Q11() throws SQLException
{
System.out.println("Expense date:");
int date = Integer.parseInt(sc.nextLine());
String q10 = "SELECT SSN, amount FROM Expenses WHERE e_date ="+date;
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(q10);
//display
while(r.next()){
System.out.println(String.format("SSN: %s|Amount: %s |",
r.getString("SSN"),
r.getString("amount")));
}
}
public static void Q12() throws SQLException
{
System.out.println("Enter client's SSN");
int ssn = Integer.parseInt(sc.nextLine());
String q12 = "SELECT DISTINCT (SSN) FROM Serves WHERE t_name in(SELECT
t_name from Cares WHERE SSN in(SELECT SSN FROM Client where SSN="+ ssn +"))";
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(q12);
// display
while(r.next()){
System.out.println(String.format("SSN: %s|",
r.getString("SSN")));
}
}
public static void Q13() throws SQLException
{
String q13 = "Select P_name, M_address, E_address, H_number, W_number, C_number
FROM People where SSN IN(SELECT SSN from Cares where t_name " +
"IN(SELECT t_name FROM Sponsor WHERE org_name IN(SELECT
org_name FROM External_Organization WHERE SUBSTRING(org_name,1,1) between 'B' and
'K'))) " +
"ORDER BY P_name";
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(q13);
while(r.next()){
System.out.println(String.format("name: %s mail: %s email: %s home: %s work:
%s cell: %s",
r.getString("P_name"),
r.getString("M_address"),
r.getString("E_address"),
r.getString("H_number"),
r.getString("W_number"),
r.getString("C_number")));
}
}
public static void Q14() throws SQLException
{
String q13 = "SELECT People.P_name, SUM(Donor_Donate.d_amount) AS
total_amount, Donor.anonymous FROM ((Donor INNER JOIN Donor_Donate ON Donor.SSN =
Donor_Donate.SSN)" +
" INNER JOIN People ON Donor.SSN = People.SSN) WHERE
Donor.SSN IN(SELECT SSN FROM Employee) GROUP BY Donor.anonymous,
People.P_name Order by total_amount";
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(q13);
while(r.next()){
System.out.println(String.format("name: %s| total amount: %s| anonymous: %s",
r.getString("P_name"),
r.getString("total_amount"),
r.getString("anonymous")));
}
}
public static void Q15() throws SQLException
{
System.out.println("Enter date:");
String date = sc.nextLine();//sc.nextLine();
// get first 2 number for month
char m1 = date.charAt(0);
char m2 = date.charAt(1);
// combine string
String month = String.valueOf(m1) + String.valueOf(m2);
//back to int for comparison
int m = Integer.parseInt(month);
// repeat for day
char d1 = date.charAt(2);
char d2 = date.charAt(3);
String day = String.valueOf(d1) + String.valueOf(d2);
int d = Integer.parseInt(day);
// repeat for year
char y1 = date.charAt(4);
char y2 = date.charAt(5);
String year = String.valueOf(y1) + String.valueOf(y2);
int y = Integer.parseInt(year);
String q15 = "SELECT * FROM Team";
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(q15);
while(r.next()){
String date_formed = r.getString("date_formed");
//System.out.println(date_formed);
char m12 = date_formed.charAt(0);
char m22 = date_formed.charAt(1);
String month2 = String.valueOf(m12) + String.valueOf(m22);
int mon = Integer.parseInt(month2);
char d12 = date_formed.charAt(2);
char d22 = date_formed.charAt(3);
String day2 = String.valueOf(d12) + String.valueOf(d22);
int da = Integer.parseInt(day2);
char y12 = date_formed.charAt(4);
char y22 = date_formed.charAt(5);
String year2 = String.valueOf(y12) + String.valueOf(y22);
int ye = Integer.parseInt(year2);
// compare input date and date in table
if (ye > y || (ye > y && mon > m) || (ye > y && mon > m && da > d))
{
System.out.println(String.format("name: %s|",
r.getString("t_name")));
}
}
}
public static void Q16() throws SQLException
{
String q16 = "UPDATE Employee SET salary=salary*1.10 WHERE SSN IN(SELECT
SSN FROM Reporting GROUP BY SSN HAVING count(t_name)>1)";
Statement statement = conn.createStatement();
statement.executeUpdate(q16);
ResultSet r = statement.executeQuery("SELECT * FROM Employee");
while(r.next()){
System.out.println(String.format("SSN: %s| Salary: %s|",
r.getString("SSN"),
49
r.getString("salary")));
}
}
public static void Q17() throws SQLException
{
String q17 = "Delete FROM Client WHERE (Client.SSN IN(SELECT SSN FROM
Needs WHERE Client.SSN = Needs.SSN AND Needs.needs = 'Transportation' AND
Needs.importance_value < 5)) " +
"AND (Client.SSN NOT IN(SELECT SSN FROM Insurance_Policy
WHERE i_type = 'Health'))";
Statement statement = conn.createStatement();
statement.executeUpdate(q17);
ResultSet r = statement.executeQuery("SELECT * FROM Client");
while(r.next()){
System.out.println(String.format("SSN: %s| d_name: %s| d_phone: %s| a_name:
%s| a_name: %s| type: %s|",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5),
r.getString(5)));
}
}
public static void Q18() throws SQLException
{
Statement statement = conn.createStatement();
String name;
String type;
String date;
try {
BufferedReader bufferedReader = new BufferedReader(new FileReader(new
File("file.txt")));
String line = null;
// read till there's nothing to read
while ((line = bufferedReader.readLine()) != null) {
String tmp[]=line.split(",");
name=tmp[0];
type=tmp[1];
date=tmp[2];
String q18 = "INSERT INTO Team
(t_name,t_type,date_formed) values ('" + name + "','" + type + "',"+ date + ")";
statement.executeUpdate(q18);
}
bufferedReader.close();
} catch (IOException e) {
e.printStackTrace();
}
// display table with data add in
ResultSet r = statement.executeQuery("SELECT * FROM Team");
while(r.next()){
System.out.println(String.format("Name: %s| type: %s| date: %s| ",
r.getString(1),
r.getString(2),
r.getString(3)));
}
}
public static void Q19() throws SQLException
{
Statement statement = conn.createStatement();
String file_export = "export.txt";
File file = new File(file_export);
FileWriter fw = null;
try {
fw = new FileWriter(file.getAbsoluteFile());
BufferedWriter bw = new BufferedWriter(fw);
// All the information you need from someone XD
String sql = "SELECT SSN, P_name, E_address, C_number FROM
People WHERE on_list='yes'";
try {
ResultSet r = statement.executeQuery(sql);
while(r.next()) {
bw.write(r.getString(1) + "," +r.getString(2) + ","
+r.getString(3) + "," +r.getString(4) +"\n");
System.out.println("Write in file export");
}
}catch(IOException | SQLException e ) {
//bw.close();
e.printStackTrace();
}finally {
try {bw.close();} catch (Exception ex) {}
}
}catch(IOException e) {
e.printStackTrace();
}
}
public static void Q20() throws SQLException
{
Statement statement = conn.createStatement();
System.out.println("1. Enter Person\n2. Affiliate with an Org");
int choice = Integer.parseInt(sc.nextLine());
switch (choice)
{
case 1:
System.out.println("Enter SSN");
int ssn = Integer.parseInt(sc.nextLine());
System.out.println("Enter name");
String name = sc.nextLine();
System.out.println("Enter date of birth: 010101-Jan 1,2001");
int dob = Integer.parseInt(sc.nextLine());
System.out.println("Enter race");
String race = sc.nextLine();
System.out.println("Enter gender");
String gender = sc.nextLine();
System.out.println("Enter profession");
String prof = sc.nextLine();
System.out.println("Enter mail address");
String m_a = sc.nextLine();
System.out.println("Enter email address");
String e_a = sc.nextLine();
System.out.println("Enter home phone number");
String h_num = sc.nextLine();
System.out.println("Enter work phone number");
String w_num = sc.nextLine();
System.out.println("Enter Cell phone number");
String c_num = sc.nextLine();
System.out.println("On mailling list?");
String onlist = sc.nextLine();
String q12 = "INSERT INTO People VALUES
("+ssn+",'"+name+"',"+dob+",'"+race+"','"+gender+"','"+prof+"','"+m_a+"','"+e_a+"',"
+ h_num+ ","+w_num+","+c_num+",'"+onlist+"')";
int re = statement.executeUpdate(q12);
System.out.println(String.format("Done. %d rows inserted.", re));
ResultSet r = statement.executeQuery("SELECT * FROM People");
while(r.next()){
System.out.println(String.format("SSN: %s| name: %s| dob: %s| race: %s| gender:
%s| prof: %s|" +
"mail: %s| email: %s| home: %s| work: %s| cel: %s|",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5),
r.getString(6),
r.getString(7),
r.getString(8),
r.getString(9),
r.getString(10),
r.getString(11)));
}
break;
case 2:
System.out.println("Enter SSN:");
int ssn2 = Integer.parseInt(sc.nextLine());
System.out.println("Enter Org name");
String org = sc.nextLine();
int re2 = statement.executeUpdate("INSERT INTO Affiliates VALUES (" + ssn2
+ ",'" + org + "')");
System.out.println(String.format("Done. %d rows inserted.", re2));
ResultSet r2 = statement.executeQuery("SELECT * FROM Affiliates");
while(r2.next()){
System.out.println(String.format("SSN: %s| org: %s|",
r2.getString(1),
r2.getString(2)));
}
}
}
public static void displayDCard() throws SQLException
{
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery("SELECT * from Donor_Donate_Card");
//display
while(r.next())
{
System.out.println("\nContent of Donor Card table");
System.out.println("\nSSN | date | amount|card num|card type| exp date|");
System.out.println(String.format("%s | %s | %s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5),
r.getString(6)));
}
}
public static void displayDCH()
{
String sql = "Select * from Donor_Donate_Check";
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Donor Check table");
System.out.println("\nSSN | date | amount | check num|");
while(r.next()) {
System.out.println(String.format("%s | %s |%s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayDD()
{
String sql = "Select * from Donor_Donate";
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Donate table");
System.out.println("\nSSN | date | amount|type|camp");
while(r.next()) {
System.out.println(String.format("%s | %s |%s | %s |%s | ",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayDonor()
{
String sql = "Select * from Donor";
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Donor table");
System.out.println("\nSSN | anonymous |");
while(r.next()) {
System.out.println(String.format("%s | %s |",
r.getString(1),
r.getString(2)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displaySponsor()
{
String sql = "Select * from Sponsor";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Sponsor table");
System.out.println("\nOrg | Team |");
while(r.next()) {
System.out.println(String.format("%s | %s |",
r.getString(1),
r.getString(2)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayOrg()
{
String sql = "Select * from External_Organization";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Org table");
System.out.println("\nName | address | phone| contact |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayExpense()
{
String sql = "Select * from Expenses";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Expense table");
System.out.println("\nSSN | date | Amount| description |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
/* for result purpose */
public static void displayTeam()
{
String sql = "Select * from Team";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Team table");
System.out.println("\nTeam Name | Team Type | Date Formed");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | ",
r.getString(1),
r.getString(2),
r.getString(3)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayClient()
{
String sql = "SELECT * FROM Client";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Client table");
System.out.println("\nSSN | d_name | d_phone | a_name | a_phone | date
first assigned");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5),
r.getString(6)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayCares()
{
String sql = "SELECT * FROM Cares";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Care table");
System.out.println("\nSSN | Team Name | Active");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | ",
r.getString(1),
r.getString(2),
r.getString(3)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayNeeds()
{
String sql = "SELECT * FROM Needs";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Needs table");
System.out.println("\nSSN | Needs | Importance Value");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | ",
r.getString(1),
r.getString(2),
r.getString(3)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayInsurance()
{
String sql = "SELECT * FROM Insurance_Policy";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Insurance Policy table");
System.out.println("\nSSN | pol_id | pro_id | pro_ad | i_type |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayVolunteer()
{
String sql = "SELECT * FROM Volunteers";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Volunteer table");
System.out.println("\nSSN | date_first join | date recent train | location |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayServe()
{
String sql = "SELECT * FROM Serves";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Insurance Policy table");
System.out.println("\nSSN | name | month | hour | active |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayActive()
{
String sql = "SELECT * FROM Active";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Active table");
System.out.println("\nSSN | team | active |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayEmployee()
{
String sql = "SELECT * FROM Employee";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Employee table");
System.out.println("\nSSN | salary | martial status | hire date |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayReport()
{
String sql = "SELECT * FROM Reporting";
/* display team for image report purpose */
try (final Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery(sql)) {
//display
System.out.println("\nContent of Reporting table");
System.out.println("\nSSN | team | date | description |");
while(r.next()) {
System.out.println(String.format("%s | %s | %s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
catch(SQLException e)
{
e.printStackTrace();
}
}
public static void displayOD() throws SQLException
{
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery("SELECT * FROM Organization_Donor");
//display
while(r.next())
{
System.out.println("\nContent of Donor Card table");
System.out.println("\nOrg Name | anonymous |");
System.out.println(String.format("%s | %s |",
r.getString(1),
r.getString(2)));
}
}
public static void displayODCheck() throws SQLException
{
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery("SELECT * FROM Organization_Donate_Check");
//display
while(r.next())
{
System.out.println("\nContent of Org Card table");
System.out.println("\nSSN | date | amount | check num|");
System.out.println(String.format("%s | %s |%s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4)));
}
}
public static void displayODCard() throws SQLException
{
Statement statement = conn.createStatement();
ResultSet r = statement.executeQuery("SELECT * FROM Organization_Donate_Card");
//display
while(r.next())
{
System.out.println("\nContent of Org Card table");
System.out.println("\nOrg Name | date | amount|card num|card type| exp
date|");
System.out.println(String.format("%s | %s |%s | %s |%s | %s |",
r.getString(1),
r.getString(2),
r.getString(3),
r.getString(4),
r.getString(5),
r.getString(6)));
}
}
}
