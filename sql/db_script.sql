CREATE TABLE videoDB.Users(
	user_id SERIAL NOT NULL,
	user_name VARCHAR (30) UNIQUE NOT NULL,
	user_password VARCHAR (50) NOT NULL,
	PRIMARY KEY (user_id)
	);

CREATE TABLE videoDB.Videos(
	video_id SERIAL NOT NULL,
	user_id INTEGER NOT NULL REFERENCES videoDB.Users,
	video_name VARCHAR(30) UNIQUE NOT NULL,
	video_token VARCHAR (50) NOT NULL,
	is_visible boolean,
	PRIMARY KEY (video_id)
	);

INSERT INTO videoDB.Users(user_name,user_password) VALUES ('julia','6d932c406fa15164ee48ff5a52f81dae');
INSERT INTO videoDB.Users(user_name,user_password) VALUES ('ania','ed71c5d55af657bc2413020e5580d4dd');
INSERT INTO videoDB.Users(user_name,user_password) VALUES ('michal','daa9bda719032ae88abadb9cda4aa846');
INSERT INTO videoDB.Users(user_name,user_password) VALUES ('anton','4bc0550cd0afc7bbe97be48a36303f6e');

INSERT INTO videoDB.Videos(user_id,video_name,video_token,is_visible) VALUES (2,'LOL2',null,TRUE);
commit;


http://localhost:8080/dp_server_test_war_exploded/delete?username=Julia&videoname=LOL