package controllers;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Result;

/**
 * 用于检查用户是否已登录的Action
 * 
 * @author 梁博文
 * 
 */
public class CheckLoginAction extends Action.Simple {
	private static String URL_MYPPT = controllers.routes.Frontend.myppt().url();
	private static String URL_LOGIN = controllers.routes.Frontend.login().url();
	private static String URL_SIGNUP = controllers.routes.Frontend.signup().url();

	public Result call(Http.Context ctx) throws Throwable {

		// 判断是否已经登陆
		if (isLogined(ctx)) {
			// 已登录，则跳转到myppt页面
			String requestUrl = ctx.request().uri();
			// 判断是否请求myppt页面，避免多层重定向
			if (requestUrl.equals(URL_MYPPT)) {
				return delegate.call(ctx);
			}
			return redirect(URL_MYPPT);
		} else {
			// 未登录，则继续页面请求

			String requestUrl = ctx.request().uri();

			Logger.info(requestUrl);

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
	public static boolean isLogined(Http.Context ctx) {
		// 获取session
		Session sess = ctx.session();

		// 从Session中提取email字段
		String email = sess.get("email");

		// 若字段不存在则判定为未登录，否则为已登录
		if (email == null || email.equals("")) {
			Logger.info("Not logined!");
			return false;
		} else {
			Logger.info("Logined " + email);
			return true;
		}
	}
}
