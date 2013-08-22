package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.UserService;
import com.liveppt.utils.ResultJson;
import com.liveppt.utils.exception.user.*;
import com.liveppt.utils.models.UserJson;

import com.liveppt.utils.models.UserReader;
import play.mvc.*;

import java.util.HashMap;
import java.util.Map;

import views.html.*;

/**
 * 用户接口
 * Author 黎伟杰, 黄梓财
 */

public class UserController extends Controller {
    @Inject
    UserService userService;

    public static String KEY_ID = "userId";
    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String KEY_NEW_PASSWORD = "newPassword";
    public static String KEY_DISPLAY = "display";
    public static String KEY_NEW_DISPLAY = "newDisplay";
  
  	/**
  	 * 注册账号
  	 * @return 
  	 * last modified 黎伟杰
  	 */
    public Result regist() {

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;

        try {
            String email,password,display;
            email = params.get(KEY_EMAIL)[0];
            if (email==null) throw new EmailNotFoundException();
            password = params.get(KEY_PASSWORD)[0];
            if ( password==null) throw new PasswordNotFoundException();
            display = params.get(KEY_DISPLAY)[0];
            if (display ==null) throw new DisplayNotFoundException();
            UserReader userReader = new UserReader();
            userReader.setEmail(email);
            userReader.setPassword(password);
            userReader.setDisplay(display);
            userReader = userService.regist(userReader);
            //组装Data域信息
            Map<String,Object> keyValue = new HashMap<>();
            keyValue.put(KEY_EMAIL,userReader.getEmail());
            keyValue.put(KEY_DISPLAY,userReader.getDisplay());
            UserJson userJson = new UserJson(keyValue);
            //组装返回信息
            resultJson = new ResultJson(userJson);
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        System.out.println(resultJson.toString());
        return ok(resultJson);
    }

    /**
     * 登陆返回用户信息
     * @return 
     * last modified 黎伟杰
     */
    public Result login() {

        Map<String, String[]> params = request().queryString();
        ResultJson resultJson = null;
        try {
            //获取传入信息
            String email,password;
            email = params.get(KEY_EMAIL)[0];
            if (email==null) throw new EmailNotFoundException();
            password = params.get(KEY_PASSWORD)[0];
            if ( password==null) throw new PasswordNotFoundException();

            //组装UserReader传递至Service
            UserReader userReader = new UserReader();
            userReader.setEmail(email).setPassword(password);
            userReader = userService.login(userReader);

            //组装返回结果中data域信息
            //data域无信息包含
            UserJson userJson = new UserJson();
            resultJson = new ResultJson(userJson);
            //传入session
            session(KEY_ID, String.valueOf(userReader.getId()));
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 注销登陆用户信息
     * @return
     * last modified 黎伟杰
     */
    public Result logout() {
        session().remove(KEY_ID);
        return ok();
    }

    /**
     * 更新用户的密码
     * @return 
     * last modified 黎伟杰
     */
    public Result updatePassword() {

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_ID);
            if (s_id==null) throw new UserNoLoginException();
            Long id = Long.valueOf(s_id);

            //获取传入信息
            String password,newPassword;
            password = params.get(KEY_PASSWORD)[0];
            if ( password==null) throw new PasswordNotFoundException();
            newPassword = params.get(KEY_NEW_PASSWORD)[0];
            if ( newPassword==null) throw new NewPasswordNotFoundException();

            //组装UserReader传递至Service
            UserReader userReader = new UserReader();
            userReader.setId(id).setPassword(password).setNewPassword(newPassword);
            //更新密码
            userReader = userService.updatePassword(userReader);

            //组装返回结果中data域信息
            //data域无信息包含
            UserJson userJson = new UserJson();
            resultJson = new ResultJson(userJson);
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 更新用户的显示名称
     * @return 
     * last modified 黎伟杰
     */
    public Result updateDisplay() {

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_ID);
            if (s_id==null) throw new UserNoLoginException();
            Long id = Long.valueOf(s_id);

            //获取传入信息
            String password,newDisplay;
            password = params.get(KEY_PASSWORD)[0];
            if ( password==null) throw new PasswordNotFoundException();
            newDisplay = params.get(KEY_NEW_PASSWORD)[0];
            if ( newDisplay==null) throw new NewDisplayNotFoundException();

            //组装UserReader传递至Service
            UserReader userReader = new UserReader();
            userReader.setId(id).setPassword(password).setNewDisplay(newDisplay);
            //更新显示名
            userReader = userService.updateDisplay(userReader);

            //组装返回结果中data域信息
            //data域无信息包含
            UserJson userJson = new UserJson();
            resultJson = new ResultJson(userJson);
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

//    /**
//     * 测试用
//     * @return
//     * last modified 黎伟杰
//     */
//    public Result test() {
//        return ok(testD.render());
//    }

}
