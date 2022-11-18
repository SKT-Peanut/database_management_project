DROP TABLE IF EXISTS Organization_Donate_Card
DROP TABLE IF EXISTS Organization_Donate_Check
14
DROP TABLE IF EXISTS Organization_Donate
DROP TABLE IF EXISTS Organization_Donor
DROP TABLE IF EXISTS Sponsor
DROP TABLE IF EXISTS Business
DROP TABLE IF EXISTS Church
DROP TABLE IF EXISTS Affiliates
DROP TABLE IF EXISTS External_Organization
DROP TABLE IF EXISTS Donor_Donate_Card
DROP TABLE IF EXISTS Donor_Donate_Check
DROP TABLE IF EXISTS Donor_Donate
DROP TABLE IF EXISTS Donor
DROP TABLE IF EXISTS Reporting
DROP TABLE IF EXISTS Expenses
DROP TABLE IF EXISTS Leads
DROP TABLE IF EXISTS Employee
DROP TABLE IF EXISTS Volunteers
DROP TABLE IF EXISTS Serves
DROP TABLE IF EXISTS Cares
DROP TABLE IF EXISTS Team
DROP TABLE IF EXISTS Insurance_Policy
DROP TABLE IF EXISTS Needs
DROP TABLE IF EXISTS Client
DROP TABLE IF EXISTS Emergency
DROP TABLE IF EXISTS People
CREATE Table People(
SSN INT PRIMARY KEY NOT NULL
,
P_name VARCHAR
(64) ,
Dob INT
,
Race VARCHAR
(64),
Gender VARCHAR
(64) ,
Profession VARCHAR
(64) ,
M_address VARCHAR
(64) ,
E_address VARCHAR
(30) ,
H_number INT
,
W_number INT
,
C_number INT
,
On_list VARCHAR
(64
)
)
CREATE TABLE Emergency
(
SSN INT NOT NULL
,
E_name VARCHAR
(64),
Relationship VARCHAR
(64),
1
5
Email_address VARCHAR(30) NOT NULL,
Mailing_address VARCHAR(64) NOT NULL,
Home_number INT NOT NULL,
Work_number INT NOT NULL,
Cell_number INT NOT NULL,
PRIMARY KEY (SSN, Cell_number),
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE
)
CREATE TABLE Client(
SSN INT PRIMARY KEY,
d_name VARCHAR(64),
d_phone INT,
a_name VARCHAR(64),
a_phone INT,
date_first_assigned INT,
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE
)
CREATE TABLE Needs(
SSN INT,
needs VARCHAR(20),
importance_value INT,
PRIMARY KEY (SSN,Needs),
FOREIGN KEY (SSN) REFERENCES Client(SSN) ON DELETE CASCADE
)
CREATE TABLE Insurance_Policy(
SSN INT,
pol_id VARCHAR(64) PRIMARY KEY,
pro_id VARCHAR(64),
pro_address VARCHAR(64),
i_type VARCHAR(20),
FOREIGN KEY (SSN) REFERENCES Client(SSN) ON DELETE CASCADE,
)
CREATE TABLE Team(
t_name VARCHAR(64) PRIMARY KEY,
t_type VARCHAR(64),
date_formed VARCHAR(64),
)
CREATE TABLE Cares(
SSN INT,
t_name VARCHAR(64),
active VARCHAR(64),
16
PRIMARY KEY (SSN, t_name),
FOREIGN KEY (SSN) REFERENCES Client(SSN) ON DELETE CASCADE,
FOREIGN KEY (t_name) REFERENCES Team(t_name) ON DELETE CASCADE
)
CREATE TABLE Serves(
SSN INT,
t_name VARCHAR(64),
months INT,
hour INT,
active VARCHAR(64),
PRIMARY KEY (SSN, t_name, months),
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE,
FOREIGN KEY (t_name) REFERENCES Team(t_name) ON DELETE CASCADE
)
CREATE TABLE Volunteers(
SSN INT PRIMARY KEY,
date_first_join INT,
date_recent_train INT,
location_recent_train VARCHAR(64),
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE
)
CREATE TABLE Employee(
SSN INT PRIMARY KEY,
salary INT,
marital_Status VARCHAR(64),
hired_date INT,
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE
)
CREATE TABLE Leads(
SSN INT PRIMARY KEY,
t_name VARCHAR(64),
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE,
FOREIGN KEY (t_name) REFERENCES Team(t_name) ON DELETE CASCADE
)
CREATE TABLE Expenses(
SSN INT,
e_date INT,
amount INT,
e_description varchar(64),
PRIMARY KEY (SSN, e_date, amount, e_description),
FOREIGN KEY (SSN) REFERENCES Employee(SSN) ON DELETE CASCADE
17
)
CREATE TABLE Reporting(
SSN INT,
t_name VARCHAR(64),
r_date INT,
r_description VARCHAR(64),
PRIMARY KEY (t_name,SSN),
FOREIGN KEY (SSN) REFERENCES Employee(SSN) ON DELETE CASCADE,
FOREIGN KEY (t_name) REFERENCES Team(t_name) ON DELETE CASCADE
)
CREATE TABLE Donor(
SSN INT PRIMARY KEY,
anonymous VARCHAR(64),
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE
)
CREATE TABLE Donor_Donate(
SSN INT,
d_date INT,
d_amount INT,
d_type VARCHAR(64),
d_campaign VARCHAR(64),
PRIMARY KEY (SSN, d_date ,d_amount),
FOREIGN KEY (SSN) REFERENCES Donor(SSN) ON DELETE CASCADE
)
CREATE TABLE Donor_Donate_Check(
SSN INT,
d_date INT,
d_amount INT,
d_check_num VARCHAR(64),
PRIMARY KEY (SSN,d_date, d_amount, d_check_num),
FOREIGN KEY (SSN, d_date, d_amount) REFERENCES Donor_Donate(SSN, d_date, d_amount) ON
DELETE CASCADE
)
CREATE TABLE Donor_Donate_Card(
SSN INT,
d_date INT,
d_amount INT,
d_card_num INT,
d_card_type VARCHAR(64),
d_card_exp INT,
18
PRIMARY KEY (SSN,d_date, d_amount, d_card_num),
FOREIGN KEY (SSN, d_date, d_amount) REFERENCES Donor_Donate(SSN, d_date, d_amount) ON
DELETE CASCADE
)
CREATE TABLE External_Organization(
org_name VARCHAR(64) PRIMARY KEY,
org_mailing VARCHAR(64),
org_phone INT,
contact_People VARCHAR(64)
)
CREATE TABLE Affiliates(
SSN INT PRIMARY KEY,
org_name VARCHAR(64),
FOREIGN KEY (SSN) REFERENCES People(SSN) ON DELETE CASCADE,
FOREIGN KEY(org_name) REFERENCES External_Organization(org_name) ON DELETE CASCADE
)
CREATE TABLE Church(
org_name VARCHAR(64) PRIMARY KEY,
religious_affiliation VARCHAR(64),
FOREIGN KEY (org_name) REFERENCES External_Organization(org_name) ON DELETE CASCADE
)
CREATE TABLE Business(
org_name VARCHAR(64) PRIMARY KEY,
b_type VARCHAR(64),
size VARCHAR(64),
website VARCHAR(64),
FOREIGN KEY (org_name) REFERENCES External_Organization(org_name) ON DELETE CASCADE
)
CREATE TABLE Sponsor(
org_name VARCHAR(64),
t_name VARCHAR(64),
PRIMARY KEY (org_name, t_name),
FOREIGN KEY (org_name) REFERENCES External_Organization(org_name) ON DELETE CASCADE,
FOREIGN KEY (t_name) REFERENCES Team(t_name) ON DELETE CASCADE
)
CREATE TABLE Organization_Donor(
org_name VARCHAR(64) PRIMARY KEY,
anonymous VARCHAR(64),
FOREIGN KEY (org_name) REFERENCES External_Organization(org_name) ON DELETE CASCADE
)
19
CREATE TABLE Organization_Donate(
org_name VARCHAR(64),
donate_date INT,
donate_amount INT,
donate_type VARCHAR(64),
donate_campaign varchar(64),
PRIMARY KEY (org_name, donate_date, donate_amount),
FOREIGN KEY (org_name) REFERENCES Organization_Donor(org_name) ON DELETE CASCADE
)
CREATE TABLE Organization_Donate_Check(
org_name VARCHAR(64),
donate_date INT,
donate_amount INT,
donate_check_num VARCHAR(64),
PRIMARY KEY(org_name, donate_date, donate_amount, donate_check_num),
FOREIGN KEY (org_name, donate_date, donate_amount) REFERENCES
Organization_Donate(org_name, donate_date, donate_amount) ON DELETE CASCADE
)
CREATE TABLE Organization_Donate_Card(
org_name VARCHAR(64),
donate_date INT,
donate_amount INT,
donate_card_num INT,
donate_card_type VARCHAR(64),
donate_card_exp INT,
PRIMARY KEY(org_name, donate_date, donate_amount, donate_card_num),
FOREIGN KEY (org_name, donate_date, donate_amount) REFERENCES
Organization_Donate(org_name, donate_date, donate_amount) ON DELETE CASCADE
)
CREATE INDEX expense_amount_index
ON Expenses(amount)
CREATE INDEX team_date_index
ON Team(date_formed)