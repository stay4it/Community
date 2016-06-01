package com.xylife.community.api;

import com.xylife.community.bean.Advertisement;
import com.xylife.community.bean.Exercise;
import com.xylife.community.bean.JavaResponse;
import com.xylife.community.bean.Response;
import com.xylife.community.bean.TestResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit 2.0中我们还可以在@Url里面定义完整的URL：这种情况下Base URL会被忽略。
 */
public interface APIService {

    @GET("avatardata/mingrenmingyan/lookup")
    Observable<Response<List<Exercise>>> getFamousResult(@Header("apiKey") String apiKey,
                                                         @Query("keyword") String keyword,
                                                         @Query("page") int page,
                                                         @Query("rows") int rows);

    @GET("http://ip.taobao.com/service/getIpInfo.php")
    Observable<TestResponse> getTestResult(@Query("ip") String keyword);

    @GET("http://203.130.41.104:8050/guizi-app-jiqimao/haier/user/userLoginNew.json")
    Observable<JavaResponse> login(@Query("phone") String name,
                                   @Query("password") String password,
                                   @Query("userType") int userType);

    @GET("http://203.130.41.104:8050/guizi-app-jiqimao/haier/version/findAdvertImg.json?attType=5")
    Observable<JavaResponse<List<Advertisement>>> getBannerImages();
}
