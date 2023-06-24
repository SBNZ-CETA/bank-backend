insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (2,6, 7000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (3,4, 5000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (5,7, 32000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (8,12, 20000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (1,4, 2000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (9,16, 110000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (2,5, 2000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (3,6, 11000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (12,24, 500000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (1,12, 14000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (8,8, 10800);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (4,10, 35000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (4,7, 1000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (6,6, 20000);
insert into loans_table (minimum_payment_deadline,maximum_payment_deadline,loan_amount) values (7,14, 10000);

insert into users_table (id, name, surname, email, username, password, age, type) values (0, 'Isidora', 'Poznanovic', 'isipoz@gmail.com', 'isi', 'isi', 22, 'CLIENT')
insert into users_table (id, name, surname, email, username, password, age, type) values (1, 'Nemanja', 'Lukovic', 'necaa@gmail.com', 'neca', 'neca', 26, 'CLIENT')

insert into bank_account_table (user_id, ccv, expiration_date, balance) values (0, 223, DATE('2027-6-26'), 45000)
insert into bank_account_table (user_id, ccv, expiration_date, balance) values (0, 189, DATE('2024-10-14'), 87000)
insert into bank_account_table (user_id, ccv, expiration_date, balance) values (0, 503, DATE('2024-7-23'), 12000)
insert into bank_account_table (user_id, ccv, expiration_date, balance) values (1, 417, DATE('2025-8-2'), 76000)
