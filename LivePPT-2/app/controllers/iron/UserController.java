package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.UserService;
import com.liveppt.utils.ResultJson;
import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.exception.user.UserNoLoginException;
import com.liveppt.utils.models.UserJson;

import play.mvc.*;

import java.util.Map;

import views.html.*;

/**
 * 用户接口
 * Author 黎伟杰, 黄梓财
 */

public class UserController extends Controller {
    @Inject
    UserService userService;
  
  	/**
  	 * 注册账号
  	 * @return 
  	 * last modified 黎伟杰
  	 */
    public Result regist() {

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;
        try {
            resultJson = new ResultJson(userService.regist(params));
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
            resultJson = new ResultJson(userService.login(params));
            session(UserJson.KEY_ID, String.valueOf(resultJson.getData().get(UserJson.KEY_ID).asLong()));
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        System.out.println(resultJson.toString());
        return ok(resultJson);
    }

    /**
     * 注销登陆用户信息
     * @return
     * last modified 黎伟杰
     */
    public Result logout() {
        session().remove(UserJson.KEY_ID);
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
            String id = ctx().session().get(UserJson.KEY_ID);
            if (id==null) throw new UserNoLoginException();
            params.put(UserJson.KEY_ID,new String[]{id});
            //更新密码
            resultJson =  new ResultJson(userService.updatePassword(params));
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        System.out.println(resultJson.toString());
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
            String id = ctx().session().get(UserJson.KEY_ID);
            if (id==null) throw new UserNoLoginException();
            params.put(UserJson.KEY_ID,new String[]{id});
            //更新显示名
            resultJson = new ResultJson(userService.updateDisplay(params));
        } catch (UserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        System.out.println(resultJson.toString());
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
