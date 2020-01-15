package com.demo.invoice.controllers;

import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Callable;

@Service
public class TestCallable  {

    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return Thread.currentThread().getName();
    }
}
