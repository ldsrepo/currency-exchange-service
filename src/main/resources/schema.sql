CREATE TABLE CURRENCY_EXCHANGE
(
    id int not null,
    currency_from varchar(255),
    currency_to varchar(255),
    conversion_multiple int,
    environment varchar (255)

);