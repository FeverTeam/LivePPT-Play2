package controllers;

import com.fever.liveppt.models.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * 用于检查用户是否已登录的Action
 *
 * @author 梁博文
 */
public class CheckLoginAction extends Action.Simple {

    public static String KEY_CTX_ARG_USER = "user";
    public static String KEY_CTX_ARG_TOKEN = "token";

    /**
     * 从cookie获取用户对象
     *
     * @param ctx 传入Http.Context
     * @return
     */
    public static User getUser(Http.Context ctx) {
        return (User) ctx.args.get(KEY_CTX_ARG_USER);
    }

    /**
     * 获取token
     *
     * @param ctx 传入Http.Context
     * @return
     */
    public static String getToken(Http.Context ctx) {
        Object token = ctx.args.get(KEY_CTX_ARG_TOKEN);
        if (token != null) {
            return token.toString();
        } else {
            return null;
        }

    }

    /**
     * 从cookie获取用户对象
     *
     * @param ctx 传入Http.Context
     * @return
     */
    private static User getUserFromCookie(Http.Context ctx) {
        Http.Cookie cookie = ctx.request().cookie("uemail");
        if (cookie == null || cookie.value() == null) {
            return null;
        }

        String uemail = cookie.value();
        User user = User.find.where().eq("email", uemail).findUnique();
        if (user != null) {
            user.password = null;
        }
        return user;
    }

    /**
     * 获取token
     *
     * @param ctx 传入Http.Context
     * @return
     */
    private static String getTokenFromCookie(Http.Context ctx) {
        Http.Cookie cookie = ctx.request().cookie("token");
        if (cookie == null || cookie.value() == null) {
            return null;
        }
        String token = cookie.value();
        return token;
    }

    @Override
    public Result call(Http.Context ctx) throws Throwable {

        // 判断是否已经登陆
        User user = getUserFromCookie(ctx);
        String token = getTokenFromCookie(ctx);

        ctx.args.put(KEY_CTX_ARG_USER, user);
        ctx.args.put(KEY_CTX_ARG_TOKEN, token);

        return delegate.call(ctx);
    }

}
