INSERT INTO Contact (id,emailAddress,streetLine, zipCode,city) values(1, 'Donnie@frank.the.bunny.com','frank street', '12345','DonnieTown');
INSERT INTO Contact (id,emailAddress,streetLine, zipCode,city) values(2, 'Linus@linux.org','tux street', '54321','Silicon Valley');
INSERT INTO Contact (id,emailAddress,streetLine, zipCode,city) values(3, 'frank@police_squad.org','naked gun street', '17273','No Where');

INSERT INTO Customer (id, firstName,lastName,contact_id) values (1, 'Donnie','Darko',1);
INSERT INTO Customer (id, firstName,lastName,contact_id) values (2, 'Linus','Torvalds',2);
INSERT INTO Customer (id, firstName,lastName,contact_id) values (3, 'Frank','Drebin',3);

INSERT INTO Orders (id,description,price,customer_id) values(1,'Bunny Mask',39.99,1);
INSERT INTO Orders (id,description,price,customer_id) values(2,'Tux',19.49,2);

