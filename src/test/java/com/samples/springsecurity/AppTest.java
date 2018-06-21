package com.samples.springsecurity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.samples.springsecurity.controller.HomeController;

public class AppTest    
{
	@Test
    public void testApp()
    {
        HomeController hc = new HomeController();
        String result = hc.index();
        assertEquals( result, "index");	
    }
}
