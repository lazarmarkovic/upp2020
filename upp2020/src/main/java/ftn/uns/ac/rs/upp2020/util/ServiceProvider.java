package ftn.uns.ac.rs.upp2020.util;

import ftn.uns.ac.rs.upp2020.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ServiceProvider {

    private static ServiceProvider instance;

    @Autowired
    UserService userService;

    @PostConstruct
    public void fillInstance() {instance = this;}

    public static UserService getUserService() { return instance.userService;}
}