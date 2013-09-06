package controllers;

import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.DataJson;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.Md5Util;
import com.fever.liveppt.utils.StatusCode;
import org.codehaus.jackson.node.ObjectNode;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * 有关用户的数据接口
 *
 * @author 梁博文
 */
public class UserController extends Controller {
    UserService userService;

    public static String KEY_CTX_ARG_USER = "user";
    /**
     * 获取User
     *
     * @param ctx 传入Http.Context
     * @return
     */
    public static User getUser(Http.Context ctx) {
        // 获取session
        Session sess = ctx.session();
        User user = getUser(ctx);

        // 从Session中提取email字段
        String email = sess.get("email");

        // 若字段不存在则判定为未登录，否则为已登录
        if (email == null || email.equals("")) {
            Logger.info("Not logined!");
            user = null;
        } else {
            Logger.info("Logined " + email);
            user = User.find.where().eq("email", email).findUnique();
        }
        ctx.args.put(KEY_CTX_ARG_USER, user);
        return user;
    }

    public static Result login() {
        ObjectNode result = Json.newObject();

        // 获取POST参数
        Map<String, String[]> postData = request().body().asFormUrlEncoded();
        String email = postData.get("email")[0];
        String password = postData.get("password")[0];

        User user = User.isPasswordCorrect(email, password);
        if (user != null) {
            //用户验证成功
            result.put("isSuccess", true);

            Logger.info(user.email);
            Logger.info(user.displayname);

            // 更新session信息
            session("email", user.email);
            session("displayname", user.displayname);
        } else {
            //用户验证失败
            result.put("isSuccess", false);
            result.put("message", "用户名/密码不正确，或未注册。");
        }

        return ok(result);
    }

    public static Result signup2() {
        ObjectNode result = Json.newObject();
        Map<String, String[]> postData = request().body().asFormUrlEncoded();

        if (postData == null) {
            return ok("null");
        }

        // 获取POST参数
        String email = postData.get("email")[0];
        String password = postData.get("password")[0];
        String displayname = postData.get("displayname")[0];

        // 查找是否已经有相同email的用户，若有则返回错误
        if (User.isExistedByEmail(email)) {
            //相同email的用户已存在，拒绝注册

            result.put("isSuccess", false);
            result.put("message", "不好意思！<br>相同email地址用户已被注册啦。");

            return ok(result);
        } else {
            //相同email的用户未存在，接受注册

            // 组装新用户信息
            User user = new User(email, password, displayname);

            // 将用户存入表中
            user.save();

            result.put("isSuccess", true);

            // 更新session信息
            session("email", email);
            session("displayname", displayname);

            return ok(result);
        }
    }

    //必须要static方法
    public static Result signup() {
        ObjectNode result = Json.newObject();
        Map<String, String[]> postData = request().body().asFormUrlEncoded();

        if (postData == null) {
            return ok("null");
        }

        // 获取POST参数
        String email = postData.get("email")[0];
        String password = postData.get("password")[0];
        String displayname = postData.get("displayname")[0];
        if (displayname == null) {
            displayname = email;
        }
        //解密password
        // password = decryptAES(password,email); 此句又有问题
        // 查找是否已经有相同email的用户，若有则返回错误
        if (User.isExistedByEmail(email)) {
            //相同email的用户已存在，拒绝注册

            result.put("isSuccess", false);
            result.put("message", "不好意思！<br>相同email地址用户已被注册啦。");

            return ok(result);
        } else if (User.isEmailFormatValid(email) == false) {
            //电邮格式不正确
            //throw InvalidParamsException
            JsonResult results = new JsonResult(false, StatusCode.USER_EXISTED, null, "电邮格式不正确。");

            return ok(results);
        } else {
            //相同email的用户未存在，接受注册

            // 组装新用户信息
            User user = new User(email, password, displayname);

            // 将用户存入表中
            user.save();

            //生成token
            String token = Md5Util.getMd5(email);
            Map<String, String> map = new HashMap<String, String>();
            map.put("email", email);
            map.put("displayName", displayname);
            map.put("password", password);
            map.put("token", token);

            //封装json格式的data数据
            DataJson dataJson = new DataJson();
            dataJson.setStringField(map);
            JsonResult results = new JsonResult(true, StatusCode.SUCCESS, dataJson, "Sign up succefully");
            // UserService ss = null;
            // JsonResult results = ss.register(email,password,displayname)  ;
            //词句不成

            System.out.println(results.getMessage());
            System.out.println(results.getData().get("token"));
            // 更新session信息
            session("email", email);
            session("displayname", displayname);

            return ok(results);
        }
    }


    public static Result logout() {
        session().remove("email");
        return Controller.redirect(controllers.routes.Frontend.index());
    }
}
