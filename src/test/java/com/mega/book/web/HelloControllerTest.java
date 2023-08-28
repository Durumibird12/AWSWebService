package com.mega.book.web;

import com.mega.book.springboot.config.auth.SecurityConfig;
import com.mega.book.springboot.web.HelloController;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        //로그인 과정이 필요 없기 때문에 SecurityConfig 제외 시킴
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
public class HelloControllerTest{
    @Autowired
    private MockMvc mvc;
    @Test
    @WithMockUser(roles = "USER")
    public void helloReturn() throws Exception{
        String hello = "hello";
        /*
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

         */
        mvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(hello));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDtoReturn() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(MockMvcRequestBuilders.get("/hello/dto").param("name",name)
                        .param("amount",String.valueOf(amount)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(amount)));

/*
        mvc.perform(get("/hello/dto").param("name",name)
                        .param("amount",String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(name)))
                .andExpect(jsonPath("$.amount", Matchers.is(amount)));

 */
        //$를 기준으로 필드명을 명시
        //name과 amount $.name, $.amount


    }
}