/* Create Indexes */

CREATE INDEX sys_dict_value ON sys_dict (value ASC);
CREATE INDEX sys_dict_label ON sys_dict (label ASC);
CREATE INDEX sys_log_create_by ON sys_log (create_by ASC);
CREATE INDEX sys_log_type ON sys_log (type ASC);
CREATE INDEX sys_log_create_date ON sys_log (create_date ASC);
CREATE INDEX sys_menu_parent_id ON sys_menu (parent_id ASC);
CREATE INDEX sys_user_login_name ON sys_user (login_name ASC);
CREATE INDEX sys_user_update_date ON sys_user (update_date ASC);
CREATE INDEX group_user_code ON group_user (code ASC);