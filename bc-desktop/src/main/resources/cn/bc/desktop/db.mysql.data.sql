-- 系统桌面相关模块的初始化数据

-- 插入桌面快捷方式数据
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '01', 0, null, null, id, null from BC_SECURITY_MODULE where name='职务配置';
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '02', 0, null, null, id, null from BC_SECURITY_MODULE where name='单位配置';
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '03', 0, null, null, id, null from BC_SECURITY_MODULE where name='部门配置';
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '04', 0, null, null, id, null from BC_SECURITY_MODULE where name='岗位配置';
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '05', 0, null, null, id, null from BC_SECURITY_MODULE where name='人员配置';
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '06', 0, null, null, id, null from BC_SECURITY_MODULE where name='模块配置';
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '07', 0, null, null, id, null from BC_SECURITY_MODULE where name='角色配置';
    
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '90', 1, '谷歌搜索', 'http://www.google.com.hk/', 'i0204');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '91', 1, '百度搜索', 'http://www.baidu.com/', 'i0204');
