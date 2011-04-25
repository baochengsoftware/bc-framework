-- 插入职务数据
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0101','董事长');
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0201','总经理');
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0202','副总经理');
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0203','主管');
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0204','主任');
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0205','副主任');
insert into BC_IDENTITY_DUTY (STATUS_,INNER_,CODE, NAME) values(1, 0, '0901','职员');

-- 插入职务编码自动增长数据
insert into BC_IDENTITY_IDGENERATOR (TYPE_,VALUE, FORMAT) values('duty.code', 1, 'DUTY-${V}');
