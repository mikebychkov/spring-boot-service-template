package service.template.aop;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class MyRateLimiterTests {

    @Autowired
    private MockMvc mockMvc;

    MockHttpServletRequestBuilder ip1() {
        return get("/aop/test").header("X-Forwarded-For", "111.11.11.111");
    }

    MockHttpServletRequestBuilder ip2() {
        return get("/aop/test").header("X-Forwarded-For", "222.22.22.222");
    }

    @Test
    public void singleRequest() throws Exception {

        this.mockMvc.perform(ip1())
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void sequentialRequests() throws Exception {

        for (int i = 0; i < 10; i++) {
            log.info("RUN #{}", i);
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void sequentialRequestsWithExceed() throws Exception {

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        this.mockMvc.perform(ip1())
                .andDo(print())
                .andExpect(status().is(429));
    }

    @Test
    public void sequentialRequestsWithPause() throws Exception {

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void sequentialRequestsWithExceedPauseAndContinue() throws Exception {

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        this.mockMvc.perform(ip1())
                .andDo(print())
                .andExpect(status().is(429));

        Thread.sleep(1000);

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void sequentialRequestsDifferentIPs() throws Exception {

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip2())
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void sequentialRequestsDifferentIPsWithExceed() throws Exception {

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip1())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(ip2())
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        this.mockMvc.perform(ip1())
                .andDo(print())
                .andExpect(status().is(429));
    }
}
