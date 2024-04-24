insert into users(first_name, last_name, email, password, phone_number, date_of_birth, address, role, account_non_expired, account_non_locked, credentials_non_expired, enabled) 
values 
('John', 'Doe', 'jdoe@test.com', 'password', '121.077.123', '1999-12-12', 'NY, US', 'EMPLOYEE', true, true, true, true),
('Janne', 'Doe', 'janned@test.com', 'password', '121.077.321', '2005-11-10', 'CJ, RO', 'CUSTOMER', true, true, true, true);

insert into treatments(name, description, estimated_duration, price)
values
('Hairstyling Gold', 'Rococo style', 120, 350),
('Hairstyling Silver', 'Baroc style', 100, 300);

select * from users;