-- 系统桌面相关模块的初始化数据

-- 插入桌面快捷方式数据
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='职务配置'), null);
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='单位配置'), null);
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='部门配置'), null);
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='岗位配置'), null);
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='人员配置'), null);
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='模块配置'), null);
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,NAME,MID,AID) 
	values(1, 0, 1, null, (select id from BC_SECURITY_MODULE where name='角色配置'), null);
