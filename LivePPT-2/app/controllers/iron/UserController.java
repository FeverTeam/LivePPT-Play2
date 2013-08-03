package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.UserService;
import com.liveppt.utils.exception.params.ParamsException;
import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;
import play.mvc.*;

import java.util.Map;

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

        Map<String, String[]> params = request().queryString();
        UserJson userJson = null;
        try {
            userJson = userService.regist(params);
        } catch (ParamsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            userJson = new UserJson(e.getStatus());
        }
        System.out.println(userJson.toString());
        return ok(userJson);

    }

    /**
     * 登陆返回用户信息
     * @return 
     * last modified 黎伟杰
     */
    public Result login() {

        Map<String, String[]> params = request().queryString();
        UserJson userJson = null;
        try {
            userJson = userService.login(params);
        } catch (ParamsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.out.println(e.getMessage());
            userJson = new UserJson(e.getStatus());
        }
        System.out.println(userJson.toString());
        return ok(userJson);

    }

    /**
     * 更新用户email，password，display
     * @return 
     * last modified 黎伟杰
     */
    public Result upToProfile() {
        return TODO;
    }
  	


}
