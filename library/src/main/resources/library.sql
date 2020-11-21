USE library
;
CREATE TABLE book (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT "ID",
  card_id VARCHAR (20) COMMENT "图书编号",
  search_id VARCHAR (30) COMMENT "检索号",
  book_name VARCHAR (50) COMMENT "书名",
  cid INT COMMENT "类别",
  author VARCHAR (20) COMMENT "作者",
  cover VARCHAR (100) COMMENT "封面",
  press VARCHAR (50) COMMENT "出版社",
  press_date DATE COMMENT "出版时间",
  book_desc VARCHAR (250) COMMENT "书的描述",
  book_shelf INT COMMENT "所在书架",
  book_floor INT COMMENT "所在楼层",
  countint COMMENT "书的数量" DEFAULT 0,
  price DOUBLE COMMENT "书的价格" DEFAULT 0,
  is_lend INT COMMENT "是否可借，0表示不可借，1表示可借" DEFAULT 0,
  day_click_count INT COMMENT "每日点击量" DEFAULT 0,
  month_click_count INT COMMENT "每月点击量" DEFAULT 0,
  total_click_count INT COMMENT "总点击量" DEFAULT 0,
  publish_date DATE COMMENT "上架时间"
) CREATE TABLE category (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT "ID",
  category_name VARCHAR (20) COMMENT "类别名称",
  category_desc VARCHAR (255) COMMENT "类别详情",
  floorint COMMENT "该类别所在楼层",
  parent_id INT COMMENT "父类别ID"
) CREATE TABLE fine (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT "ID",
  uid INT COMMENT "用户ID",
  price DOUBLE COMMENT "罚款金额",
  fine_desc VARCHAR (255) COMMENT "罚款描述即原因",
  pay_date DATE COMMENT "付款时间"
) CREATE TABLE Notice (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT "ID",
  message VARCHAR (255) COMMENT "消息",
  STATUS INT COMMENT "为0的话，就表示全发，不为0就发用户",
  publish_date DATE COMMENT "发布消息时间"
) CREATE TABLE notice_user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  uid INT,
  nid INT,
  STATUS INT COMMENT " 代表未读，1代表已经读"
) CREATE TABLE sys_user (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT "ID",
  username VARCHAR (20) COMMENT "用户名(账户)",
  PASSWORD VARCHAR (20) COMMENT "用户密码",
  telephone VARCHAR (12) COMMENT "联系方式",
  email VARCHAR (30) COMMENT "邮箱",
  head_image VARCHAR (100) COMMENT "头像",
  STATUS INT DEFAULT 0 COMMENT "身份状态---0表示普通用户，1表示管理员",
  register_date DATE COMMENT "注册时间"
) CREATE TABLE user_book (
  id INT PRIMARY KEY AUTO_INCREMENT COMMENT "ID",
  uid INT COMMENT "借书用户ID",
  bid INT COMMENT "书ID",
  STATUS INT COMMENT "是否返还，0表示未返还，1表示已返回",
  lend_date DATE COMMENT "借阅时间",
  return_date DATE COMMENT "返还时间"
)