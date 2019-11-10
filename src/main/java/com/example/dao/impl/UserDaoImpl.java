package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.domain.User;
import com.example.utils.JdbcUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtil.getDataSource());

    @Override
    public User getUserByUserName(String userName) {
        User user = null;
        String sql = "select * from tab_user where username = ?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public void insertUser(User user) {
        String sql = "insert into tab_user(username, password, name, birthday, sex, telephone, email) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail());
    }

    @Override
    public boolean login(User user) {
        User mUser = null;
        String sql = "select * from tab_user where username = ? and password = ?";
        try {
            mUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),
                    user.getUsername(), user.getPassword());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return mUser != null;
    }
}
