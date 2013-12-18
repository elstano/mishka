create or replace package body pkg_admin AS
  PROCEDURE purge_tables IS
    stmt varchar2(4000) := '';
    BEGIN
      for rec in (select table_name, constraint_name from USER_CONSTRAINTS where constraint_type = 'R') loop
        stmt := 'alter table ' || rec.table_name || ' drop constraint ' || rec.constraint_name;
        execute immediate stmt;
      end loop;

      for rec in (select table_name from user_tables) loop
        stmt := 'drop table ' || rec.table_name;
        EXECUTE IMMEDIATE stmt;
      end loop;

      for rec in (select sequence_name from user_sequences) loop
        stmt := 'drop sequence ' || rec.sequence_name;
        EXECUTE IMMEDIATE stmt;
      end loop;
    END;
end pkg_admin;
