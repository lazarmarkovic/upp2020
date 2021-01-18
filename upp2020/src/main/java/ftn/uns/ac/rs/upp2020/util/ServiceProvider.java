package ftn.uns.ac.rs.upp2020.util;

import ftn.uns.ac.rs.upp2020.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ServiceProvider {

    private static ServiceProvider instance;

    @Autowired
    UserServiceImpl userService;

    @PostConstruct
    public void fillInstance() {instance = this;}

    public static UserServiceImpl getUserService() { return instance.userService;}
}