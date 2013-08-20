package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.PptService;

import com.liveppt.utils.ResultJson;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.exception.ppt.PptFileErrorException;
import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.exception.user.UserNoLoginException;
import com.liveppt.utils.models.PptJson;
import com.liveppt.utils.models.UserJson;
import play.mvc.*;

import java.io.File;
import java.util.Map;

/**
 * Date: 13-8-18
 * Time: 下午4:05
 *
 * @author 黎伟杰
 */
public class PptController extends Controller{
    @Inject
    PptService pptService;

    /**
     * 上传ppt
     * @return
     * last modified 黎伟杰
     */
    public Result uploadPpt(){

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String id = ctx().session().get(UserJson.KEY_ID);
            if (id==null) throw new UserNoLoginException();
            params.put(UserJson.KEY_ID,new String[]{id});
            //提取文件
            Http.MultipartFormData.FilePart filePart = request().body().asMultipartFormData().getFile("PptFile");
            if (filePart==null) throw new PptFileErrorException();
            File file = filePart.getFile();

            PptJson pptJson = pptService.uploadPpt(params,file);

        } catch (PptException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        } catch (UserException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }

        return ok(resultJson);
    }



}
