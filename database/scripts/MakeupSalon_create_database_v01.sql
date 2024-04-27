-- Users Table
CREATE TABLE Users (
    User_ID SERIAL PRIMARY KEY,
    First_Name VARCHAR(50) NOT NULL,
    Last_Name VARCHAR(50) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255),
    Phone_Number VARCHAR(20),
    Date_Of_Birth DATE,
    Address VARCHAR(255),
    Role VARCHAR(50) CHECK (Role IN ('ADMIN', 'EMPLOYEE', 'CUSTOMER')),
    Picture_URL VARCHAR(1024),
	account_non_expired BOOLEAN,
	account_non_locked BOOLEAN,
	credentials_non_expired BOOLEAN,
	enabled BOOLEAN
);

-- Treatments Table
CREATE TABLE Treatments (
    Treatment_ID SERIAL PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Description VARCHAR(255),
    Estimated_Duration INT,
    Price DECIMAL(10, 2),
    Picture_URL VARCHAR(1024)
);

-- EmployeeTreatments Table
CREATE TABLE Employee_Treatments (
    Employee_Treatments_ID SERIAL PRIMARY KEY,
    Employee_ID INT,
    Treatment_ID INT,
    FOREIGN KEY (Employee_ID) REFERENCES Users(User_ID),
    FOREIGN KEY (Treatment_ID) REFERENCES Treatments(Treatment_ID)
);

-- Appointments Table
CREATE TABLE Appointments (
    Appointment_ID SERIAL PRIMARY KEY,
    Customer_ID INT,
    Start_Date_Time TIMESTAMP,
    End_Date_Time TIMESTAMP,
    Date_Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Approval_Status VARCHAR(50) CHECK (Approval_Status IN ('PENDING', 'APPROVED', 'REJECTED', 'EXPIRED')),
    Approved_By INT,
    FOREIGN KEY (Customer_ID) REFERENCES Users(User_ID),
    FOREIGN KEY (Approved_By) REFERENCES Users(User_ID)
);

-- AppointmentEmployeeTreatments Table
CREATE TABLE Appointment_Employee_Treatments (
	Appointment_Employee_Treatments_ID SERIAL PRIMARY KEY,
    Appointment_ID INT,
    Employee_Treatments_ID INT,
    FOREIGN KEY (Appointment_ID) REFERENCES Appointments(Appointment_ID) ON DELETE CASCADE,
    FOREIGN KEY (Employee_Treatments_ID) REFERENCES Employee_Treatments(Employee_Treatments_ID)
);

-- drop table public.appointment_employee_treatments;
-- drop table public.appointments;
-- drop table public.employee_treatments;
-- drop table public.treatments;
-- drop table public.users;
