package controllers;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Result;

import com.fever.liveppt.models.User;

/**
 * 用于检查用户是否已登录的Action
 * 
 * @author 梁博文
 * 
 */
public class CheckLoginAction extends Action.Simple {
	private static String URL_MYPPT = controllers.routes.Frontend.myppt().url();
	private static String URL_LOGIN = controllers.routes.Frontend.login().url();
	private static String URL_SIGNUP = controllers.routes.Frontend.signup()
			.url();
	// private static String URL_MYMEETING =
	// controllers.routes.Frontend.mymeeting().url();

	public static String KEY_CTX_ARG_USER = "user";

	public Result call(Http.Context ctx) throws Throwable {

		// 判断是否已经登陆
		User user = getUser(ctx);
		String requestUrl = ctx.request().uri();
		if (user != null) {
			// 将User放入ctx
			ctx.args.put(KEY_CTX_ARG_USER, user);

			if (requestUrl.equals(URL_LOGIN)) {
				return redirect(URL_MYPPT);
			} else {
				return delegate.call(ctx);
			}

		} else {
			// 未登录，则继续页面请求

			if (requestUrl.equals(URL_LOGIN) || requestUrl.equals(URL_SIGNUP)) {
				// 请求login和signup
				return delegate.call(ctx);
			} else {
				// 指向login页面，引导用户登录
				return redirect(URL_LOGIN);
			}
		}
	}

	/**
	 * 判断是否已经登陆
	 * 
	 * @param ctx
	 *            传入Http.Context
	 * @return
	 */
	public static User getUser(Http.Context ctx) {
		// 获取session
		Session sess = ctx.session();

		// 从Session中提取email字段
		String email = sess.get("email");

		// 若字段不存在则判定为未登录，否则为已登录
		if (email == null || email.equals("")) {
			Logger.info("Not logined!");
			return null;
		} else {
			Logger.info("Logined " + email);
			return User.find.where().eq("email", email).findUnique();
		}
	}
}
