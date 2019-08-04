package spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@SpringBootApplication
public class SpringJdbcTempateApplication  implements CommandLineRunner{
	
	@Autowired
	AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTempateApplication.class, args);
	
	}	

	@Override
	public void run(String... args) throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(appConfig.dataSource());
		
		// mapping object using BeanPropertyRowMapper
		User user = jdbcTemplate.queryForObject("select * from user where id=?", new Object[] {1},
				new BeanPropertyRowMapper<User>(User.class));
		
		// List of objects 
		List<User> userList =jdbcTemplate.query("select * from user",new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setFirstName(rs.getString("firstName"));
				user.setId(rs.getInt("id"));
				user.setLastName(rs.getString("lastName"));
				return user;
			}
		});
		System.out.println(userList);
	}

}
