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
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '80', 0, null, null, id, null from BC_SECURITY_MODULE where name='个性化设置';
    
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '90', 1, '谷歌搜索', 'http://www.google.com.hk/', 'i0204');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '91', 1, '百度搜索', 'http://www.baidu.com/', 'i0204');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,MID,AID) 
	select 1, 0, '00', 0, null, null, id, null from BC_SECURITY_MODULE where name='分页设计';
	
-- 视图设计
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '71', 0, '分页设计', '/bc/duty/paging', 'i0000');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '72', 0, '无分页设计', '/bc/duty/list', 'i0000');

-- 报表
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '81', 0, '饼图', '/bc/chart/pie', 'i0300');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '82', 0, '柱图', '/bc/chart/bar', 'i0300');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '83', 0, '动态曲线图', '/bc/chart/spline', 'i0300');
insert into BC_DESKTOP_SHORTCUT (STATUS_,INNER_,ORDER_,STANDALONE,NAME,URL,ICONCLASS) 
	values(1, 0, '84', 0, '综合图表', '/bc/chart/mix', 'i0300');

-- 插入全局配置信息
insert into BC_DESKTOP_PERSONAL (STATUS_,INNER_,FONT,THEME,AID) 
	values(1, 0, '12', 'flick', null);
-- insert into BC_DESKTOP_PERSONAL (STATUS_,INNER_,FONT,THEME,AID) 
-- 	select 1, 0, '14', 'flick', id from BC_IDENTITY_ACTOR where code='admin';
