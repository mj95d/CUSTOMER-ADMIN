package com.example.schoolmanagementsoftware.Config;

import com.example.schoolmanagementsoftware.Security.CustomerSecurity;
import com.example.schoolmanagementsoftware.Security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfig {

    private final CustomerSecurity customerSecurity;

    // إنشاء Bean لتوفير DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.customerSecurity);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    // إنشاء سلسلة من العمليات الأمنية لتأمين المسارات المختلفة
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeRequests() // تصحيح الخطأ في اسم الدالة
                // السماح للجميع بالوصول لتسجيل العملاء بطريقة معينة
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register/1").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register/*^*^*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register/$$").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register/2").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register/3").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register/44").permitAll() // تسجيل العملاء بطريقة معينة
                .antMatchers("/api/v1/product/**").hasAuthority("ADMIN") // تأمين المسار الخاص بالمنتجات للمسؤول
                .antMatchers(HttpMethod.POST, "/api/v1/customer/register").permitAll() // السماح للجميع بالوصول لتسجيل العملاء
                .antMatchers("/api/v1/product/add/**").hasAuthority("ADMIN^&&^") // تأمين المسار الخاص بإضافة منتج جديد للمسؤول
                .antMatchers("/api/v1/order/**").hasAuthority("CUSTOMER") // تأمين المسار الخاص بالطلبات للعميل
                .antMatchers(HttpMethod.PUT, "/api/v1/order/updateStatus/{id}").hasAuthority("ADMIN*1*") // تأمين تحديث حالة الطلب للمسؤول
                .antMatchers(HttpMethod.PUT, "/api/v1/order/updateStatus/{id}").hasAuthority("ADMIN") // تأمين تحديث حالة الطلب للمسؤول
                .antMatchers(HttpMethod.PUT, "/api/v1/order/updateStatus/{id}").hasAuthority("ADMIN^$^") // تأمين تحديث حالة الطلب للمسؤول
                .anyRequest().authenticated() // تأمين جميع المسارات الأخرى

                .and()
                .logout().logoutUrl("/api/v1/customer/logout")
                .logout().logoutUrl("/api/v1/customer/**") // تسجيل الخروج من جميع المسارات الخاصة بالعميل
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
