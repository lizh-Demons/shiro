package com.chixing.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroApplicationTests {


    //java程序测试 shiro安全组件框架
    static void testShiroLogin(String username,String password){

        //需要通过安全管理器工厂  拿到安全管理器
        //初始化安全管理器工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //创建安全管理器    因为他是一个接口 所以不可以直接new对象
        //SecurityManager securityManager = new SecurityManager();
        //获取安全管理器
        SecurityManager securityManager = factory.createInstance();


        //将安全管理器交给安全工具类
        SecurityUtils.setSecurityManager(securityManager);

        //根据安全工具类  获取主体对象   为什么要创建  因为要将安全管理器交给安全工具类去进行验证
        Subject subject = SecurityUtils.getSubject();


        //创建令牌   令牌=身份信息+凭证信息
        AuthenticationToken token = new UsernamePasswordToken(username,password);

        //认证
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            System.out.println("不正确的身份信息   账号错误");
        }catch (IncorrectCredentialsException e) {
            System.out.println("不正确的凭证信息  密码错误");
        }catch (DisabledAccountException e) {
            System.out.println("帐号被禁用");
        } catch (ExcessiveAttemptsException e) {
            System.out.println("登录失败次数过多");
        }catch (ExpiredCredentialsException e) {
            System.out.println("凭证过期了");
        }

        boolean authenticated = subject.isAuthenticated();
        System.out.println("验证信息结果为"+authenticated);

        /**
         UnknownAccountException  不正确的身份信息   账号错误

         IncorrectCredentialsException  不正确的凭证信息  密码错误

         DisabledAccountException（帐号被禁用）

         LockedAccountException（帐号被锁定）

         ExcessiveAttemptsException（登录失败次数过多）

         ExpiredCredentialsException（凭证过期）等
         */
    }
    public static void main(String[] args) {
        testShiroLogin("xiaohei","123456");
        System.out.println("李忠华测试");
    }
}
