create table excel_categories (
  name varchar2(200) PRIMARY KEY
)

--next-sql-script

create table excel_files (
  file_name varchar2(4000) not null,
  content BLOB,
  order_number NUMBER(5),
  excel_category varchar2(100) not null
    constraint excel_category_fkey references excel_categories(NAME),
  PRIMARY KEY (file_name, excel_category)
)

--next-sql-script

create sequence excel_files_order

--next-sql-script

CREATE OR REPLACE TRIGGER excel_files_order_auto
BEFORE INSERT ON excel_files
FOR EACH ROW
BEGIN
  SELECT excel_files_order.NEXTVAL
  INTO   :NEW.order_number
  FROM   dual;
END;

--next-sql-script

insert into excel_categories (name) values ('factories')
--next-sql-script
insert into excel_categories (name) values ('products')
--next-sql-script
insert into excel_categories (name) values ('nsi')
--next-sql-script
insert into excel_categories (name) values ('la_schedule')
--next-sql-script
insert into excel_categories (name) values ('filter')
--next-sql-script
insert into excel_categories (name) values ('sgp_nzp')
--next-sql-script
insert into excel_categories (name) values ('chemistry')
--next-sql-script
insert into excel_categories (name) values ('transport')
--next-sql-script
